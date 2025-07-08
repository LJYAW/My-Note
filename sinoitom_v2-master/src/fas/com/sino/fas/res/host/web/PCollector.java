package com.sino.fas.res.host.web;

import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.ResHosts;
import com.sino.fas.res.host.service.ResHostsService;
import com.sino.snmp.utils.JdbcConnection;
import com.vmware.vim25.*;
import org.springframework.beans.factory.annotation.Autowired;
import smartlink.utils.Util;

import javax.net.ssl.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhujunlong on 2017/1/23.
 */
public class PCollector {

    //存放定时器
    private static Map<String ,Timer> tomcatSchedulerMap = new HashMap<String ,Timer>();
    HashMap<String,Timer> map = new HashMap<>();

    @Autowired
    private static ResHostsService resHostsService;


    /*/**
     * @auther: Mr.Lp
     * @desc: 根据页面选择确定VMtype
     * @data: 2020/5/7
     * @param: [flagName, vimPort, serviceContent, perfManager, propCollectorRef]
     * @return:
     */
    private static List<ResHosts> getVMProperties(ResHosts entity, IpHost ipHost,VimPortType vimPort, ServiceContent serviceContent, ManagedObjectReference perfManager, ManagedObjectReference propCollectorRef) throws Exception {

        String vmType = null;
        String flagName = "foundVM";
        List<ResHosts> list= new ArrayList<>();

//        if(flagName.equals("notFound")){
//            vmType = "HostSystem";
//            //宿主机指标获取
//            collectProperties(entity,flagName,vmType,vimPort, serviceContent ,perfManager,propCollectorRef);
//        }else if(flagName.equals("onlyFound")){
//            vmType = "VirtualMachine";
//            collectProperties(entity,flagName,vmType,vimPort, serviceContent ,perfManager,propCollectorRef);
//        }else
        if(flagName.equals("foundVM")){
            int i=0;
            vmType = "HostSystem";
            while(i<2){

                List<ResHosts> hostlist = collectProperties(entity,ipHost,flagName,vmType,vimPort, serviceContent ,perfManager,propCollectorRef);
                i++;
                vmType = "VirtualMachine";
                list.addAll(hostlist);
            }
        }

        return list;

    }

    /*/**
     * @auther: Mr.Lp
     * @desc:
     * @data: 2020/5/7
     * @param: [flagName, vmType, vimPort, serviceContent, perfManager, propCollectorRef]
     * @return:
     */
    private static List<ResHosts> collectProperties(ResHosts entity, IpHost ipHost,String flagName, String vmType, VimPortType vimPort, ServiceContent serviceContent, ManagedObjectReference perfManager, ManagedObjectReference propCollectorRef) throws Exception {

        List<ResHosts> hostList = new ArrayList<>();

        //get references to the ViewManager and PropertyCollector
        ManagedObjectReference viewMgrRef = serviceContent.getViewManager();
        ManagedObjectReference propcoll = serviceContent.getPropertyCollector();

//        String vmType = null;
//        if(flagName.equals("notFound")){
//            vmType = "HostSystem";
//        }else if(flagName.equals("onlyFound")){
//            vmType = "VirtualMachine";
//        }else if(flagName.equals("foundVM")){
//            vmType = "VirtualMachine";
//        }

        //use a container view for virtual machines to define the traversal
        //- invoke the VimPortType method createContainerView (corresponds
        //to the ViewManager method) -pass the viewManager Mor and
        //- createContainerView takes a string[] for the type parameter;
        //declare an arraylist and add the type string to it
        List<String> vmList = new ArrayList<String>();

        //虚拟机获取
//        vmList.add("VirtualMachine");

        //宿主机获取
//        vmList.add("HostSystem");
        vmList.add(vmType);

        ManagedObjectReference cViewRef = vimPort.createContainerView(viewMgrRef,
                serviceContent.getRootFolder(),
                vmList,
                true);

        //create an object spec to define the beginning of the traversal;
        //container view is the root object for this traversal
        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(cViewRef);
        oSpec.setSkip(true);

        // create a traversal spec to select all objects in the view
        TraversalSpec tSpec = new TraversalSpec();
        tSpec.setName("traverseEntities");
        tSpec.setPath("view");
        tSpec.setSkip(false);
        tSpec.setType("ContainerView");

        //add the traversal spec to the object spec;
        //the accessor method (getSelectSet) returns a reference
        //to the mapped XML representation of the list; using this
        //reference to add the spec will update the list
        oSpec.getSelectSet().add(tSpec);

        //specify the property for retrieval (virtual machine name)
        PropertySpec pSpec = new PropertySpec();

        //虚拟机获取
//        pSpec.setType("VirtualMachine");
//        pSpec.getPathSet().add("summary");

        //宿主机获取
//        pSpec.setType("HostSystem");
//        pSpec.getPathSet().add("summary");

        pSpec.setType(vmType);
        pSpec.getPathSet().add("summary");

        //create a PropertyFilterSpec and add the object and
        //property specs to it; use the getter method to reference
        //the mapped XML representation of the lists and the specs
        //directly to the list
        PropertyFilterSpec fSpec = new PropertyFilterSpec();
        fSpec.getObjectSet().add(oSpec);
        fSpec.getPropSet().add(pSpec);

        //create a list for the filters and add the spec to it
        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();
        fSpecList.add(fSpec);

        //get the data from the server
        RetrieveOptions ro = new RetrieveOptions();
        RetrieveResult props = vimPort.retrievePropertiesEx(propcoll, fSpecList, ro);

        List<String> slist = new ArrayList<>();

        //go through the returned list and print out the data
        if (props != null) {
            for (ObjectContent oc : props.getObjects()) {

                String path = null;
                List<DynamicProperty> dps = oc.getPropSet();
                if (dps != null) {
                    for (DynamicProperty dp : dps) {

                        SysUser curUser = SystemUtils.getLoginUser();
                        String operator = curUser.getLoginName();
                        Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间


                        if(vmType.equals("HostSystem")){
                            //宿主机
                            HostListSummary vmSummary = null;
                            vmSummary = (HostListSummary)dp.getVal();
                            HostConfigSummary config = vmSummary.getConfig();
                            String hostName = config.getName();
                            AboutInfo product = config.getProduct();
                            String version = product.getVersion();
//                            String osType = product.getOsType();
                            String fullName = product.getFullName();

                            HostHardwareSummary hardware = vmSummary.getHardware();
                            String vendor = hardware.getVendor();   //厂商
                            String model = hardware.getModel(); //型号
                            long memorySize = hardware.getMemorySize(); //内存大小
                            String cpuModel = hardware.getCpuModel();   //CPU型号
                            int cpuMhz = hardware.getCpuMhz();  //CPU频率
                            int numCpuCores = hardware.getNumCpuCores();//cpu核数
                            short numCpuThreads = hardware.getNumCpuThreads();//CPU线程数
                            int numNics = hardware.getNumNics();    //网卡数

                            HostRuntimeInfo runtime = vmSummary.getRuntime();
                            XMLGregorianCalendar bootTime = runtime.getBootTime();  //启动时间

                            HostListSummaryQuickStats quickStats = vmSummary.getQuickStats();
                            Integer overallCpuUsage = quickStats.getOverallCpuUsage();  //总CPU使用
                            Integer overallMemoryUsage = quickStats.getOverallMemoryUsage();    //总内存使用量
                            Integer uptime = quickStats.getUptime();    //运行时间

                            //cpu利用率
                            double d = getHostCpuUsageByHostName(hostName,vimPort,serviceContent,perfManager,propCollectorRef);

                            entity.setHostName(hostName);
                            entity.setVendorName(vendor);
                            entity.setProdModel(model);
                            entity.setCpuModel(cpuModel);
                            entity.setCpuCoreQty(numCpuCores+"");
                            entity.setMemorySize(memorySize);
                            entity.setCreator(operator);
                            entity.setCreateTime(now);
                            entity.setPowerStatus(1);
                            entity.setHostStatus(1);
                            entity.setIpLong(Util.ip2long(entity.getIpAddress()));
//                            entity.setOsName("Linux");
                            entity.setOsFeature("default");
                            entity.setResClassCode(11);
                            entity.setResClassName("计算资源");
                            entity.setOsRelease(version);
                            String str = version.substring(0,version.indexOf("."));
                            entity.setOsVersionType(str+".X");
                            entity.setOsVersion(fullName);

                            if(ipHost!=null){
                                entity.setHostId(ipHost.getIpHostId());
                            }else{
                                entity.setHostId(JdbcConnection.getInstance().getDeviceResourceUniqId());
                            }
//                            entity.setHostId(JdbcConnection.getInstance().getDeviceResourceUniqId());
                            hostList.add(entity);
//                            resHostsService.save(entity);




                            System.out.println("*****"+hostName+"厂商"+vendor+"型号"+model+"CPU型号"+cpuModel+"CPU频率"
                                    +cpuMhz+"cpu核数"+numCpuCores+"CPU线程数"+numCpuThreads+"网卡数"+numNics+"启动时间"
                                    +bootTime+"总CPU使用"+overallCpuUsage+"总内存使用量"+overallMemoryUsage+"运行时间"+uptime+"CPU使用率"+d+"%");

                        }else if(vmType.equals("VirtualMachine")){  //虚拟机

                            VirtualMachineSummary vmSummary = null;
                            vmSummary = (VirtualMachineSummary)dp.getVal();
                            String vmName = vmSummary.getConfig().getName();

                            if(flagName.equals("onlyFound")){
                                slist.add(vmName);

                            }else if(flagName.equals("foundVM")){

                                VirtualMachineRuntimeInfo runtime = vmSummary.getRuntime();
                                Integer maxMemoryUsage = runtime.getMaxMemoryUsage();   //最大内存使用量
                                Integer cpuUsage = runtime.getMaxCpuUsage();    //最大CPU使用量
                                VirtualMachinePowerState powerState = runtime.getPowerState();
                                String powervalue = powerState.value();  //电源状态

                                VirtualMachineConfigSummary config = vmSummary.getConfig();
                                String vmPathName = config.getVmPathName();
                                Integer memorySizeMB = config.getMemorySizeMB();    //内存大小
                                Integer numCpu = config.getNumCpu();    //CPU核数
                                Integer numEthernetCards = config.getNumEthernetCards();    //以太网卡数量
                                Integer numVirtualDisks = config.getNumVirtualDisks();  //虚拟磁盘数
                                String uuid = config.getUuid();
                                String guestId = config.getGuestId();
                                String guestFullName = config.getGuestFullName();

                                VirtualMachineStorageSummary storage = vmSummary.getStorage();
                                XMLGregorianCalendar timestamp = storage.getTimestamp();    //虚拟机启动时间

                                VirtualMachineQuickStats quickStats = vmSummary.getQuickStats();
                                Integer overallCpuUsage = quickStats.getOverallCpuUsage();  //总CPU使用量
                                Integer hostMemoryUsage = quickStats.getHostMemoryUsage();  //主机内存使用量
                                Integer uptimeSeconds = quickStats.getUptimeSeconds();  //虚机运行时间

                                //cpu利用率
//                                double d = getHostCpuUsageByHostName(vmName,vimPort,serviceContent,perfManager,propCollectorRef);

//                                System.out.println("++++++"+d+"%");
                                System.out.println("vm name is:" + vmName + ", vm memory usage:" + maxMemoryUsage + ", vm cpu usage:" +
                                        cpuUsage + ", power: " + powervalue + ", memorySizeMB: " + memorySizeMB + ", numCPU: " + numCpu +
                                        ", numEthernetCards: " + numEthernetCards + ", numVirtualDisks: " + numVirtualDisks + ", timestamp: " +
                                        timestamp + ", overallCpuUsage: " + overallCpuUsage + ", hostMemoryUsage: " + hostMemoryUsage +
                                        ", uptimeSeconds " + uptimeSeconds);

                                ResHosts host = new ResHosts();
                                host.setHostType(0);
                                host.setHomedHostIp(entity.getIpAddress());
                                host.setHomedHostId(entity.getHostId());
                                host.setCreator(operator);
                                host.setCreateTime(now);
                                if(powervalue.equals("poweredOff")){
                                    host.setPowerStatus(0);
                                }else if(powervalue.equals("poweredOn")){
                                    host.setPowerStatus(1);
                                }
                                host.setHostStatus(1);
                                host.setOrgID(entity.getOrgID());
                                host.setVmName(vmName);
                                host.setMemorySize((long)memorySizeMB*1024);
                                host.setCpuQty(numCpu);
                                host.setHostId(JdbcConnection.getInstance().getDeviceResourceUniqId());
//                                host.setOsName("Linux");
                                host.setOsFeature("default");
                                host.setResClassCode(11);
                                host.setResClassName("计算资源");
                                host.setResTypeCode(1);
                                host.setResTypeName("服务器");
                                hostList.add(host);

//                                resHostsService.save(host);

//                                getVmCpuNumByVmName(vmName,vimPort,serviceContent);
                            }

                        }

                    }
                }
            }
        }

        return hostList;

    }

    /**
     * @auther: Mr.Lp
     * @desc: 宿主机CPU利用率
     * @data: 2020/4/22
     * @param: [hostName, vimPort, serviceContent, perfManager, propCollectorRef]
     * @return:
     */
    public static double getHostCpuUsageByHostName(String hostName,VimPortType vimPort, ServiceContent serviceContent,
                                                   ManagedObjectReference perfManager,ManagedObjectReference propCollectorRef) throws RuntimeFaultFaultMsg
    {
        double ans = 0.0;
        List<List<Long>> list = getHostData(hostName, "usage", "cpu",vimPort, serviceContent,perfManager,propCollectorRef);
        long maxInner = 0;
        int times = 0;
        for (List<Long> listOuter : list)
        {
            long tempInner = 0;
            for (long inner : listOuter)
            {
                tempInner += inner;
            }
            if (tempInner > maxInner)
            {
                maxInner = tempInner;
                times = listOuter.size();
            }
        }
        if (times != 0)
        {
            ans = (double) maxInner / times;
        }
        ans = ans / 100;
//        System.out.println("++++++++++使用率"+ans);
        return ans;
    }

    //获取虚机CPU个数
    public static int getVmCpuNumByVmName(String hostName,VimPortType vimPort, ServiceContent serviceContent) throws RuntimeFaultFaultMsg, InvalidPropertyFaultMsg
    {
        int ans = 0;
        String cpuNum = getHostPropertyByHostName("summary.config.numCpu", hostName,vimPort,serviceContent);
        ans = Integer.valueOf(cpuNum);
        System.out.println("---------个数"+ans);
        return ans;
    }

    private static String getHostPropertyByHostName(String property, String hostName,VimPortType vimPort, ServiceContent serviceContent) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg
    {
        String ans = null;
        RetrieveResult props = getRetrieveResultObjectWithProperty("HostSystem", property,vimPort,serviceContent);

        if (props != null)
        {
            Boolean flag = false;
            if (property.compareToIgnoreCase("name") < 0)
            {
                for (ObjectContent oc : props.getObjects())
                {
                    if (flag == true)
                    {
                        break;
                    }
                    String path = null;
                    List<DynamicProperty> dps = oc.getPropSet();

                    if (dps != null)
                    {
                        for (DynamicProperty dp : dps)
                        {
                            path = dp.getName();
                            if (path.equalsIgnoreCase(property))
                            {
                                String val = String.valueOf(dp.getVal());
                                ans = val;
                            }
                            if (path.equalsIgnoreCase("name"))
                            {
                                String value = (String) dp.getVal();
                                if (value.equals(hostName))
                                {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                for (ObjectContent oc : props.getObjects())
                {
                    if (flag == true)
                    {
                        break;
                    }
                    String path = null;
                    List<DynamicProperty> dps = oc.getPropSet();

                    if (dps != null)
                    {
                        for (DynamicProperty dp : dps)
                        {
                            path = dp.getName();
                            if (path.equalsIgnoreCase("name"))
                            {
                                String value = (String) dp.getVal();
                                if (value.equals(hostName))
                                {
                                    flag = true;
                                }
                            }
                            if (path.equalsIgnoreCase(property))
                            {
                                String val = String.valueOf(dp.getVal());
                                if (flag == true)
                                {
                                    ans = val;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return ans;
    }

    private static RetrieveResult getRetrieveResultObjectWithProperty(String MorName, String property,VimPortType vimPort, ServiceContent serviceContent) throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg
    {
        ManagedObjectReference viewMgrRef = serviceContent.getViewManager();
        ManagedObjectReference propColl = serviceContent.getPropertyCollector();

        List<String> vmList = new ArrayList<String>();
        vmList.add(MorName);

        ManagedObjectReference cViewRef = vimPort.createContainerView(viewMgrRef, serviceContent.getRootFolder(), vmList, true);

        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(cViewRef);
        oSpec.setSkip(true);

        TraversalSpec tSpec = new TraversalSpec();
        tSpec.setName("traversalEntities");
        tSpec.setPath("view");
        tSpec.setSkip(false);
        tSpec.setType("ContainerView");

        oSpec.getSelectSet().add(tSpec);

        PropertySpec pSpec = new PropertySpec();
        pSpec.setType(MorName);
        pSpec.getPathSet().add("name");

        PropertySpec pSpecRPr = new PropertySpec();
        pSpecRPr.setType(MorName);
        pSpecRPr.getPathSet().add(property);

        PropertyFilterSpec fSpec = new PropertyFilterSpec();
        fSpec.getObjectSet().add(oSpec);
        fSpec.getPropSet().add(pSpec);
        fSpec.getPropSet().add(pSpecRPr);

        List<PropertyFilterSpec> fSpecList = new ArrayList<PropertyFilterSpec>();
        fSpecList.add(fSpec);

        RetrieveOptions ro = new RetrieveOptions();
        RetrieveResult props = vimPort.retrievePropertiesEx(propColl, fSpecList, ro);

        return props;
    }



    private static List<List<Long>> getHostData(String hostName, String nameInfo, String groupInfo,VimPortType vimPort, ServiceContent serviceContent,
                                                ManagedObjectReference perfManager,ManagedObjectReference propCollectorRef) throws RuntimeFaultFaultMsg
    {
        List<List<Long>> list = new ArrayList<List<Long>>();
        ManagedObjectReference vmmor = getHostByHostName(hostName,serviceContent,vimPort,propCollectorRef);
        if (vmmor != null)
        {
            List<PerfCounterInfo> cInfo = getPerfCounters(perfManager,vimPort,propCollectorRef);
            List<PerfCounterInfo> vmCpuCounters = new ArrayList<PerfCounterInfo>();
            for (int i = 0; i < cInfo.size(); ++i)
            {
                vmCpuCounters.add(cInfo.get(i));
            }

            int i = 0;
            Map<Integer, PerfCounterInfo> counters = new HashMap<Integer, PerfCounterInfo>();
            for (Iterator<PerfCounterInfo> it = vmCpuCounters.iterator(); it.hasNext();)
            {
                PerfCounterInfo pcInfo = (PerfCounterInfo) it.next();
                counters.put(new Integer(pcInfo.getKey()), pcInfo);
            }

            List<PerfMetricId> listpermeid = vimPort.queryAvailablePerfMetric(perfManager, vmmor, null, null, new Integer(20));

            ArrayList<PerfMetricId> mMetrics = new ArrayList<PerfMetricId>();
            if (listpermeid != null)
            {
                for (int index = 0; index < listpermeid.size(); ++index)
                {
                    if (counters.containsKey(new Integer(listpermeid.get(index).getCounterId())))
                    {
                        mMetrics.add(listpermeid.get(index));
                    }
                }
            }

            PerfQuerySpec qSpec = new PerfQuerySpec();
            qSpec.setEntity(vmmor);
            qSpec.setMaxSample(new Integer(10));
            qSpec.getMetricId().addAll(mMetrics);
            qSpec.setIntervalId(new Integer(20));

            List<PerfQuerySpec> qSpecs = new ArrayList<PerfQuerySpec>();
            qSpecs.add(qSpec);

            List<PerfEntityMetricBase> listpemb = vimPort.queryPerf(perfManager, qSpecs);
            List<PerfEntityMetricBase> pValues = listpemb;

            // System.out.println("pValues.size() = "+pValues.size());
            for (i = 0; i < pValues.size(); i++)
            {
                List<PerfMetricSeries> listpems = ((PerfEntityMetric) pValues.get(i)).getValue();
                // System.out.println("listpems.size() = "+listpems.size());
                for (int vi = 0; vi < listpems.size(); ++vi)
                {
                    String printInf = "";
                    PerfCounterInfo pci = (PerfCounterInfo) counters.get(new Integer(listpems.get(vi).getId().getCounterId()));

                    if (pci != null)
                    {
                        if (pci.getNameInfo().getKey().equalsIgnoreCase(nameInfo) && pci.getGroupInfo().getKey().equalsIgnoreCase(groupInfo))
                        {
                            printInf += vi + ":" + pci.getNameInfo().getSummary() + ":" + pci.getNameInfo().getKey() + ":" + pci.getNameInfo().getLabel() + ":"
                                    + pci.getGroupInfo().getKey() + ":" + pci.getGroupInfo().getLabel() + ":" + pci.getGroupInfo().getSummary() + " ";

                            if (listpems.get(vi) instanceof PerfMetricIntSeries)
                            {
                                PerfMetricIntSeries val = (PerfMetricIntSeries) listpems.get(vi);
                                List<Long> lislon = val.getValue();
                                for (Long k : lislon)
                                {
                                    printInf += k + " ";
                                }
                                list.add(lislon);
                            }
                            printInf += "   " + pci.getUnitInfo().getKey() + " " + pci.getUnitInfo().getLabel() + " " + pci.getUnitInfo().getSummary();
//                            System.out.println(printInf);
                        }
                    }
                }
            }

        }

        return list;
    }

    public static List<PerfCounterInfo> getPerfCounters(ManagedObjectReference perfManager,VimPortType vimPort,ManagedObjectReference propCollectorRef)
    {
        List<PerfCounterInfo> pciArr = null;

        try
        {
            PropertySpec propertySpec = new PropertySpec();
            propertySpec.setAll(Boolean.FALSE);
            propertySpec.getPathSet().add("perfCounter");
            propertySpec.setType("PerformanceManager");
            List<PropertySpec> propertySpecs = new ArrayList<PropertySpec>();
            propertySpecs.add(propertySpec);

            ObjectSpec objectSpec = new ObjectSpec();
            objectSpec.setObj(perfManager);
            List<ObjectSpec> objectSpecs = new ArrayList<ObjectSpec>();
            objectSpecs.add(objectSpec);

            PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
            propertyFilterSpec.getPropSet().add(propertySpec);
            propertyFilterSpec.getObjectSet().add(objectSpec);

            List<PropertyFilterSpec> propertyFilterSpecs = new ArrayList<PropertyFilterSpec>();
            propertyFilterSpecs.add(propertyFilterSpec);

            List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(10);
            listpfs.add(propertyFilterSpec);
            //检索属性所有对象——所有虚机名称
            List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs,vimPort,propCollectorRef);

            if (listobjcont != null)
            {
                for (ObjectContent oc : listobjcont)
                {
                    List<DynamicProperty> dps = oc.getPropSet();
                    if (dps != null)
                    {
                        for (DynamicProperty dp : dps)
                        {
                            List<PerfCounterInfo> pcinfolist = ((ArrayOfPerfCounterInfo) dp.getVal()).getPerfCounterInfo();
                            pciArr = pcinfolist;
                        }
                    }
                }
            }
        }
        catch (SOAPFaultException sfe)
        {
            printSoapFaultException(sfe);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return pciArr;
    }

    private static ManagedObjectReference getHostByHostName(String hostName,ServiceContent serviceContent,VimPortType vimPort,ManagedObjectReference propCollectorRef)
    {
        ManagedObjectReference retVal = null;
        ManagedObjectReference rootFolder = serviceContent.getRootFolder();
        try
        {
            TraversalSpec tSpec = getHostSystemTraversalSpec();
            PropertySpec propertySpec = new PropertySpec();
            propertySpec.setAll(Boolean.FALSE);
            propertySpec.getPathSet().add("name");
            propertySpec.setType("HostSystem");

            ObjectSpec objectSpec = new ObjectSpec();
            objectSpec.setObj(rootFolder);
            objectSpec.setSkip(Boolean.TRUE);
            objectSpec.getSelectSet().add(tSpec);

            PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
            propertyFilterSpec.getPropSet().add(propertySpec);
            propertyFilterSpec.getObjectSet().add(objectSpec);

            List<PropertyFilterSpec> listpfs = new ArrayList<PropertyFilterSpec>(1);
            listpfs.add(propertyFilterSpec);
            List<ObjectContent> listobjcont = retrievePropertiesAllObjects(listpfs,vimPort,propCollectorRef);

            if (listobjcont != null)
            {
                for (ObjectContent oc : listobjcont)
                {
                    ManagedObjectReference mr = oc.getObj();
                    String hostnm = null;
                    List<DynamicProperty> listDynamicProps = oc.getPropSet();
                    DynamicProperty[] dps = listDynamicProps.toArray(new DynamicProperty[listDynamicProps.size()]);
                    if (dps != null)
                    {
                        for (DynamicProperty dp : dps)
                        {
                            hostnm = (String) dp.getVal();
                        }
                    }
                    if (hostnm != null && hostnm.equals(hostName))
                    {
                        retVal = mr;
                        break;
                    }
                }
            }
            else
            {
                System.out.println("The Object Content is Null");
            }
        }
        catch (SOAPFaultException sfe)
        {
            printSoapFaultException(sfe);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return retVal;
    }

    private static TraversalSpec getHostSystemTraversalSpec()
    {
        SelectionSpec ss = new SelectionSpec();
        ss.setName("VisitFolders");

        TraversalSpec computeResourceToHostSystem = new TraversalSpec();
        computeResourceToHostSystem.setName("computeResourceToHostSystem");
        computeResourceToHostSystem.setType("ComputeResource");
        computeResourceToHostSystem.setPath("host");
        computeResourceToHostSystem.setSkip(false);
        computeResourceToHostSystem.getSelectSet().add(ss);

        TraversalSpec hostFolderToComputeResource = new TraversalSpec();
        hostFolderToComputeResource.setName("hostFolderToComputeResource");
        hostFolderToComputeResource.setType("Folder");
        hostFolderToComputeResource.setPath("childEntity");
        hostFolderToComputeResource.setSkip(false);
        hostFolderToComputeResource.getSelectSet().add(ss);

        TraversalSpec dataCenterToHostFolder = new TraversalSpec();
        dataCenterToHostFolder.setName("DataCenterToHostFolder");
        dataCenterToHostFolder.setType("Datacenter");
        dataCenterToHostFolder.setPath("hostFolder");
        dataCenterToHostFolder.setSkip(false);
        dataCenterToHostFolder.getSelectSet().add(ss);

        TraversalSpec traversalSpec = new TraversalSpec();
        traversalSpec.setName("VisitFolders");
        traversalSpec.setType("Folder");
        traversalSpec.setPath("childEntity");
        traversalSpec.setSkip(false);

        List<SelectionSpec> sSpecArr = new ArrayList<SelectionSpec>();
        sSpecArr.add(ss);
        sSpecArr.add(dataCenterToHostFolder);
        sSpecArr.add(hostFolderToComputeResource);
        sSpecArr.add(computeResourceToHostSystem);
        traversalSpec.getSelectSet().addAll(sSpecArr);
        return traversalSpec;
    }


    //检索属性所有对象
    private static List<ObjectContent> retrievePropertiesAllObjects(
            List<PropertyFilterSpec> listpfs,VimPortType vimPort,ManagedObjectReference propCollectorRef) throws Exception {

        RetrieveOptions propObjectRetrieveOpts = new RetrieveOptions();

        List<ObjectContent> listobjcontent = new ArrayList<ObjectContent>();

        try {
            RetrieveResult rslts =
                    vimPort.retrievePropertiesEx(propCollectorRef, listpfs,
                            propObjectRetrieveOpts);
            if (rslts != null && rslts.getObjects() != null
                    && !rslts.getObjects().isEmpty()) {
                listobjcontent.addAll(rslts.getObjects());
            }
            String token = null;
            if (rslts != null && rslts.getToken() != null) {
                token = rslts.getToken();
            }
            while (token != null && !token.isEmpty()) {
                rslts =
                        vimPort.continueRetrievePropertiesEx(propCollectorRef, token);
                token = null;
                if (rslts != null) {
                    token = rslts.getToken();
                    if (rslts.getObjects() != null && !rslts.getObjects().isEmpty()) {
                        listobjcontent.addAll(rslts.getObjects());
                    }
                }
            }
        } catch (SOAPFaultException sfe) {
            printSoapFaultException(sfe);
        } catch (Exception e) {
            System.out.println(" : Failed Getting Contents");
            e.printStackTrace();
        }

        return listobjcontent;
    }

    private static void printSoapFaultException(SOAPFaultException sfe) {
        System.out.println("SOAP Fault -");
        if (sfe.getFault().hasDetail()) {
            System.out.println(sfe.getFault().getDetail().getFirstChild()
                    .getLocalName());
        }
        if (sfe.getFault().getFaultString() != null) {
            System.out.println("\n Message: " + sfe.getFault().getFaultString());
        }
    }



    private static class TrustAllManager implements TrustManager, X509TrustManager {

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }

    // 宿主机/虚拟机监测
    public static List<ResHosts> hostCompMonitor (ResHosts entity, IpHost ipHost,String username, String password, String url) throws Exception{
        ManagedObjectReference SVC_INST_REF = new ManagedObjectReference();
        VimService vimService;
        VimPortType vimPort;
        ServiceContent serviceContent;

        //declare a host name vertifier that will automatically enable
        //the connection. the host name verifier is invoked during
        //the ssl handshake.
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };

        //create the trust manager
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new TrustAllManager();
        trustAllCerts[0] = tm;

        //create the ssl context
        SSLContext sc = SSLContext.getInstance("SSL");

        //create the session context
        SSLSessionContext sslsc = sc.getServerSessionContext();

        //initialize the contexts; the session context takes the trus manager.
        sslsc.setSessionTimeout(0);
        sc.init(null, trustAllCerts, null);

        //use the default socket factory to create the socket for the secure connection
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        //set the default name verifier to enable the connection.
        HttpsURLConnection.setDefaultHostnameVerifier(hv);

        //set up the manufactured managed object reference for the ServiceInstance
        SVC_INST_REF.setType("ServiceInstance");
        SVC_INST_REF.setValue("ServiceInstance");

        vimService = new VimService();
        vimPort = vimService.getVimPort();

        Map<String, Object> ctxt = ((BindingProvider)vimPort).getRequestContext();
        ctxt.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        ctxt.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

        serviceContent = vimPort.retrieveServiceContent(SVC_INST_REF);
        try{
            vimPort.login(serviceContent.getSessionManager(), username, password, null);
        }catch(Exception e){
//            e.printStackTrace();
            return null;
        }

        ManagedObjectReference perfManager = serviceContent.getPerfManager();
        ManagedObjectReference propCollectorRef = serviceContent.getPropertyCollector();

        //宿主机指标获取
//        collectProperties(flagName,vimPort, serviceContent ,perfManager,propCollectorRef);

        //根据页面选择确定VMtype
        List<ResHosts> hostList = getVMProperties(entity,ipHost,vimPort, serviceContent ,perfManager,propCollectorRef);
        vimPort.logout(serviceContent.getSessionManager());

        return hostList;

    }

    public static void main(String[] args) throws Exception {
        String serverName = "192.168.99.200";
        String username = "root";
        String password = "root123";
        String url = "https://" + serverName + "/sdk/vimService";

        //宿主机
        String flagName = "foundVM";
//        hostCompMonitor(flagName,username,password,url);

        Integer intervalTime = 30;  //间隔时间

        /**使用Executors工具快速构建对象*/
        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();
        System.out.println("3秒后开始执行计划线程池服务..." + new Date());
        /**每间隔intervalTime秒执行一次任务*/
        scheduledExecutorService.scheduleAtFixedRate(new MyRunable(flagName,username,password,url),
                0, intervalTime, TimeUnit.SECONDS);


    }

    public static class MyRunable implements Runnable {
        private AtomicInteger atomicInteger = null;
        private Random random = null;

        private String flagName;
        private String username;
        private String password;
        private String url;

        public MyRunable(String flagName,String username,String password,String url){
            this.flagName = flagName;
            this.username = username;
            this.password = password;
            this.url = url;
        }

        public MyRunable() {
            atomicInteger = new AtomicInteger(0);
            random = new Random();
        }

        @Override
        public void run() {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("1-任务执行开始:" + new Date() + ":" + threadName);

                System.out.println(flagName+"***");
                System.out.println(username+"***");
                System.out.println(password+"***");
                System.out.println(url+"***");
//                hostCompMonitor(flagName,username,password,url);

//                /**使用随机延时[0-3]秒来模拟执行任务*/
//                int sleepNumber = random.nextInt(5);
//                TimeUnit.SECONDS.sleep(sleepNumber);
//                if (atomicInteger.getAndAdd(1) == 3) {
//                    /** 故意抛出一个异常*/
//                    int error = 10 / 0;
//                }
                String flag = url;
                Timer timer = new Timer();
                tomcatSchedulerMap.put(flag,timer);
                System.out.println(tomcatSchedulerMap+"+++++++");
                System.out.println("2-任务执行完毕:" + new Date() + ":" + threadName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
