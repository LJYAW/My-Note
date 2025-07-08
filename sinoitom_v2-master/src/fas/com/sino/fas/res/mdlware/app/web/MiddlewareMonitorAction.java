package com.sino.fas.res.mdlware.app.web;

import com.sino.base.common.BaseAction;
import com.sino.base.common.Global;
import com.sino.base.common.util.*;
import com.sino.fas.res.mdlware.app.entity.*;
import com.sino.fas.res.mdlware.app.service.*;
import com.sino.utils.common.Cryptos;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping(value = "/fas/res/mdlware/app/index")
public class MiddlewareMonitorAction extends BaseAction {

    private static Logger logger = LoggerFactory.getLogger(MiddlewareMonitorAction.class);

    private String viewPath = "/fas/res/mdlware/app/index";

    private static final String DES = "DES";

    @Autowired
    private MiddlewareMonitorService middlewareMonitorService;

    @Autowired
    private ParamMiddlewareMonitorService paramMiddlewareMonitorService;

    @Autowired
    private MiddlewareMonitorInfoService middlewareMonitorInfoService;

    @Autowired
    private MiddlewareMonitorHeapService middlewareMonitorHeapService;

    @Autowired
    private MiddlewareMonitorCpuService middlewareMonitorCpuService;

    @Autowired
    private MiddlewareMonitorThreadService middlewareMonitorThreadService;



    private Map<String ,Timer> tomcatSchedulerMap = new HashMap<String ,Timer>();
    private List<Long> cpulist = new ArrayList<Long>();


    @RequestMapping(value = "/main.do")
    public String main(String menuId,ModelMap map, String id) {
        logger.info( "Enter main.do ..." );
        List<MiddlewareMonitor> list = middlewareMonitorService.getAllMiddlewareMonitor();
        String jsonStr = middlewareMonitorService.getJsonListStr(list);
        map.put("jsonListData", jsonStr);
        WebFuncUtils.titleContent(menuId,map);
        map.put("id", id);

        return viewPath+"/main";
    }

    /**
     * @auther: Mr.Lp
     * @desc: 点击具体IP进入info
     * @data: 2019/6/25
     * @param: [map, devIpAddr, content, tomcatVersion]
     * @return:
     */
    @RequestMapping(value = "/info.do")
    public String info(ModelMap map, String devIpAddr, String content, String tomcatVersion) {
        logger.info( "Enter info.do ..." );
        List<MiddlewareMonitorInfo> list = middlewareMonitorInfoService.getByIpAndContentAndTomcatVersion(devIpAddr,content,tomcatVersion);
        String jsonStr = middlewareMonitorInfoService.getJsonListStr(list);
        map.put("jsonListData", jsonStr);
        map.put("devIpAddr",devIpAddr);
        map.put("content",content);
        map.put("tomcatVersion",tomcatVersion);

        return viewPath+"/info";
    }

    /**
     * @auther: Mr.Lp
     * @desc: Tomcat监测按钮点击跳转页面
     * @data: 2019/6/25
     * @param: [map]
     * @return: 
     */
    @RequestMapping(value="/showTomcatIndex.do")
    public String showTomcatIndex(ModelMap map){
        logger.info( "Enter main.do ..." );
        return viewPath+"/Search";
    }

    /**
     * @auther: Mr.Lp
     * @desc: Tomcat监测页面，输入完信息点击监测按钮
     * @data: 2019/6/25
     * @param: [devIpAddr, content, intervaltime, userName, psWord, map]
     * @return: 
     */
    @RequestMapping(value="/showTomcatMessage.do")
    public String showTomcatMessage(Integer middleType,String devIpAddr, String content, String intervaltime, String userName, String psWord, ModelMap map){
        logger.info( "Enter showTomcatMessage.do ..." );
         String service = null;
    //    "service:jmx:rmi:///jndi/rmi://192.168.102.95:9999/jmxrmi";
        if(devIpAddr!=null&&devIpAddr!=""&&content!=null&&content!=""){

            MiddlewareMonitor middlewareMonitor = middlewareMonitorService.getByIpAndContent(devIpAddr,content);
            if(middlewareMonitor!=null){
                map.put("result", "error");
                map.put("message", "服务器"+devIpAddr+"下的端口"+content+"已经开启监测，请重新输入要监测的服务器和端口号！");
                return viewPath+"/Search";
            }

            service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";
            System.out.println(service);
//            Timer timer = new Timer();
            String[] str ;
            int l = 0 ;
            if(intervaltime.contains("Min")){
               str = intervaltime.split("Min");
               System.out.println(str[0]);
               l = Integer.parseInt(str[0])*60*1000;
            }else if(intervaltime.contains("Hours")){
                str = intervaltime.split("Hours");
                System.out.println(str[0]);
                l = Integer.parseInt(str[0])*60*60*1000;
            }else if(intervaltime.contains("Second")){
                str = intervaltime.split("Second");
                System.out.println(str[0]);
                l = Integer.parseInt(str[0])*1*1000;
            }
            //System.out.println(l);

            String jsonStr = JMXmonitor(middleType,service,devIpAddr,content,l,userName,psWord);

            if(StringUtils.isNotBlank(jsonStr)){

                //定时器，暂时注销，可能还会再用
//            timer.schedule(new MiddlewareMonitorAction().new MonitorTask(service), 0, l);
                if(jsonStr.equals("连接失败")){
                    map.put("result", "error");
                    map.put("message", "服务器连接失败");
//                    String flag = devIpAddr+content;
//                    Timer timer = tomcatSchedulerMap.get(flag);
//                    
//                    tomcatSchedulerMap.remove(flag);
//                    System.out.println(tomcatSchedulerMap);
//                    
//                    timer.cancel();
                }else{
                	new TestListener().Auto(middleType,service,devIpAddr,content,l,userName,psWord);
                	
                    map.put("saveData", jsonStr);
                    map.put("result", "success");
                }

            }else{
                map.put("result", "error");
                map.put("message", "用户名或密码错误");


            }


//定时器，暂时注销，可能还会再用
//            timer.schedule(new MiddlewareMonitorAction().new MonitorTask(service), 0, l);

        }else{
            map.put("result", "error");
            map.put("message", "请检查服务器IP或监测端口号是否输入！");
        }

        return viewPath+"/Search";
    }
    
//    private class MonitorTask extends TimerTask {
//
//        private String service;
//        private String devIpAddr;
//        private String content;
//
//        public MonitorTask(String devIpAddr,String content,String service) {
//            this.service = service;
//            this.devIpAddr = devIpAddr;
//            this.content = content;
//        }
//
//        @Override
//        public void run() {
//
//            JMXmonitor(service,devIpAddr,content);
//        }
//
//    }

    /**
     * @auther: Mr.Lp
     * @desc: 开始监测获取各项监测指标的信息
     * @data: 2019/6/25
     * @param: [service, devIpAddr, content, l, userName, psWord]
     * @return: 
     */
    public String JMXmonitor(Integer middleType,String service,String devIpAddr,String content, int l, String userName, String psWord) {
        JMXConnector jmxConnector = null;
        String jsonStr = null;
        try {
            JMXServiceURL ServiceURL = new JMXServiceURL(service);
            Map<String, String[]> environment = new HashMap<String, String[]>();
            // 用户名密码，在jmxremote.password文件中的密码
            String[] credentials = new String[] { userName, psWord };   //monitorRole  QED
            environment.put("jmx.remote.credentials", credentials);
            jmxConnector = JMXConnectorFactory.connect(ServiceURL, environment);
            System.out.println(jmxConnector);
            MBeanServerConnection mBeanServerConnection = jmxConnector
                    .getMBeanServerConnection();

            //获取JIT编译器名称和编译时间jitName，jitTime
            ObjectName compilationName = null;
            try {
                compilationName = new ObjectName("java.lang:type=Compilation");
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String jitName = (String) mBeanServerConnection.getAttribute(compilationName, "Name");
            long jitTime = (Long) mBeanServerConnection.getAttribute(compilationName, "TotalCompilationTime");

            //**基本信息**

            //获取所监测的Tomcat版本号信息tomcatversion
            ObjectName serverName = null;
            try {
                serverName = new ObjectName("Catalina:type=Server");
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String tomcatversion = (String) mBeanServerConnection.getAttribute(serverName, "serverInfo");


            //获取连接名称，虚拟机，供应商等基本信息
            ObjectName runtimeObjName = null;
            try {
                runtimeObjName = new ObjectName("java.lang:type=Runtime");
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String version = (String) mBeanServerConnection.getAttribute(runtimeObjName, "VmVersion") ;
            String vendor = (String) mBeanServerConnection.getAttribute(runtimeObjName, "VmVendor") ;
            String name = (String) mBeanServerConnection.getAttribute(runtimeObjName, "VmName") ;
            String libraryPath = (String) mBeanServerConnection.getAttribute(runtimeObjName, "LibraryPath") ;
            String classPath = (String) mBeanServerConnection.getAttribute(runtimeObjName, "ClassPath") ;
            String bootClassPath = (String) mBeanServerConnection.getAttribute(runtimeObjName, "BootClassPath") ;
            String [] vms = (String[]) mBeanServerConnection.getAttribute(runtimeObjName, "InputArguments");
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<vms.length;i++)
            {

                sb.append(vms[i]);
                sb.append(" ");

            }

//            ObjectName CopyGCName = null;
//            try {
//                CopyGCName = new ObjectName("java.lang:type=GarbageCollector,name=Copy");
//            } catch (Exception e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            long copyCount = (long) mBeanServerConnection.getAttribute(CopyGCName,"CollectionCount");
//            long copyTime = (long) mBeanServerConnection.getAttribute(CopyGCName,"CollectionTime");
//
//            ObjectName MarkSweepCompactGCName = null;
//            try {
//                MarkSweepCompactGCName = new ObjectName("java.lang:type=GarbageCollector,name=MarkSweepCompact");
//            } catch (Exception e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            long markSweepCompactCount = (long) mBeanServerConnection.getAttribute(MarkSweepCompactGCName,"CollectionCount");
//            long markSweepCompactTime = (long) mBeanServerConnection.getAttribute(MarkSweepCompactGCName,"CollectionTime");


//            List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
//
//            mBeanServerConnection.getAttribute(runtimeObjName, "VmVersion") ;
//
//            for(GarbageCollectorMXBean garbage : garbages){
//                System.out.println("垃圾收集器：名称="+garbage.getName()+",收集="+garbage.getCollectionCount()+",总花费时间="
//                        +garbage.getCollectionTime()+",内存区名称="+Arrays.deepToString(garbage.getMemoryPoolNames()));
//            }

            System.out.println("厂商:"
                    + (String) mBeanServerConnection.getAttribute(runtimeObjName, "VmVendor"));
            System.out.println("程序:"
                    + (String) mBeanServerConnection.getAttribute(runtimeObjName, "VmName"));
            System.out.println("版本:"
                    + (String) mBeanServerConnection.getAttribute(runtimeObjName, "VmVersion"));
            Date starttime = new Date((Long) mBeanServerConnection.getAttribute(runtimeObjName,
                    "StartTime"));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            System.out.println("启动时间:" + df.format(starttime));

            Long timespan = (Long) mBeanServerConnection.getAttribute(runtimeObjName, "Uptime");
                System.out.println("连续工作时间:" + formatTimeSpan(timespan));


            //**基本信息**

            //获取操作系统以及服务器的物理内存、交换空间等基础信息
            com.sun.management.OperatingSystemMXBean sunOperatingSystemMXBean = null;

            ObjectName localObjectName = new ObjectName("java.lang:type=OperatingSystem");
            if ((sunOperatingSystemMXBean == null) &&
                    (mBeanServerConnection.isInstanceOf(localObjectName, "com.sun.management.OperatingSystemMXBean")))
            {
                sunOperatingSystemMXBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection, "java.lang:type=OperatingSystem", com.sun.management.OperatingSystemMXBean.class);

            }
            long freePhysicalMemorySize = sunOperatingSystemMXBean.getFreePhysicalMemorySize();

            // 获取MemoryMXBean
            System.out.println("\nMemory");
            MemoryMXBean memoryMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.MEMORY_MXBEAN_NAME,
                            MemoryMXBean.class);

            //**MemoryPool内存池old gen监控**

//            System.out.println("\n内存池old gen监控");
//             MemoryPoolMXBean memoryPoolMXBean = ManagementFactory
//                    .newPlatformMXBeanProxy(mBeanServerConnection,
//                            ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE,
//                            MemoryPoolMXBean.class);
//            memoryPoolMXBean.getName();
//            MemoryUsage usage = memoryPoolMXBean.getUsage();
//
//            System.out.println("poolused = " + convertKB(usage.getUsed()));

            //**MemoryPool内存池old gen监控**


            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            System.out.println("heapMemoryUsage :");
            System.out.println("committed = "
                    + convertKB(heapMemoryUsage.getCommitted()));
            System.out.println("init = " + convertKB(heapMemoryUsage.getInit()));
            System.out.println("max = " + convertKB(heapMemoryUsage.getMax()));
            System.out.println("used = " + convertKB(heapMemoryUsage.getUsed()));
            String str = convertKB(heapMemoryUsage.getUsed());
            double used = Double.parseDouble(str.split("MB")[0]);

            String str1 = convertKB(heapMemoryUsage.getCommitted());
            double committed = Double.parseDouble(str1.split("MB")[0]);

//            System.out.println("heap:" + (double) used * 100
//                    / committed + "%");// 堆使用率
            DecimalFormat dfs = new DecimalFormat("#.00");
            System.out.println(dfs.format(used * 100 / committed));
            String heap = dfs.format(used * 100 / committed) + "%" ;
            System.out.println("heap:" + heap);// 堆使用率

            MemoryUsage nonHeapMemoryUsage = memoryMXBean
                    .getNonHeapMemoryUsage();
            System.out.println("\nnonHeapMemoryUsage :");
            System.out.println("committed = "
                    + convertKB(nonHeapMemoryUsage.getCommitted()));
            System.out.println("init = "
                    + convertKB(nonHeapMemoryUsage.getInit()));
            System.out.println("max = "
                    + convertKB(nonHeapMemoryUsage.getMax()));
            System.out.println("used = "
                    + convertKB(nonHeapMemoryUsage.getUsed()));

            String str2 = convertKB(nonHeapMemoryUsage.getUsed());
            double noused = Double.parseDouble(str2.split("MB")[0]);

            String str3 = convertKB(nonHeapMemoryUsage.getCommitted());
            double nocommitted = Double.parseDouble(str3.split("MB")[0]);
//            String noheap = (double) noused * 100 / nocommitted + "%" ;
            String noheap = dfs.format(noused * 100 / nocommitted) + "%" ;
            System.out.println("noheap:" + noheap);// 非堆使用率

            // 获取 ThreadMXBean
            System.out.println("\nThread");
            ThreadMXBean threadMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.THREAD_MXBEAN_NAME,
                            ThreadMXBean.class);
            System.out
                    .println("ThreadCount = " + threadMXBean.getThreadCount());
            System.out.println("DaemonThreadCount = "
                    + threadMXBean.getDaemonThreadCount());
            System.out.println("PeakThreadCount = "
                    + threadMXBean.getPeakThreadCount());
            System.out.println("CurrentThreadCpuTime = "
                    + threadMXBean.getCurrentThreadCpuTime());
            System.out.println("CurrentThreadUserTime = "
                    + threadMXBean.getCurrentThreadUserTime());

            System.out.println("\nClassLoading");
            ClassLoadingMXBean classLoadingMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.CLASS_LOADING_MXBEAN_NAME,
                            ClassLoadingMXBean.class);
            // 当前加载到Java虚拟机中类的数量
            System.out.println("LoadedClassCount = "
                    + classLoadingMXBean.getLoadedClassCount());
            // Java 虚拟机开始执行到目前已经加载的类的总数。
            System.out.println("TotalLoadedClassCount = "
                    + classLoadingMXBean.getTotalLoadedClassCount());
            // Java 虚拟机开始执行到目前已经卸载的类的总数。
            System.out.println("UnloadedClassCount = "
                    + classLoadingMXBean.getUnloadedClassCount());


            // 全部应用
//            ObjectName managerObjName = new ObjectName("Catalina:type=Manager,*");
//            Set<ObjectName> s = mBeanServerConnection.queryNames(managerObjName, null);
//            for (ObjectName obj : s) {
//                System.out.println("应用名:" + obj.getKeyProperty("path"));
//                ObjectName objname = new ObjectName(obj.getCanonicalName());
//                System.out.println("最大会话数:" + mBeanServerConnection.getAttribute(objname, "maxActiveSessions"));
//                System.out.println("会话数:" + mBeanServerConnection.getAttribute(objname, "activeSessions"));
//                System.out.println("活动会话数:" + mBeanServerConnection.getAttribute(objname, "sessionCounter"));
//            }


            //RequestProcessor
            ObjectName requestObjName = new ObjectName("Catalina:type=RequestProcessor,*");
            Set<ObjectName> requestObjNameSet = mBeanServerConnection.queryNames(requestObjName, null);
            Integer aliveSocketsCount = 0;
            Long maxProcessingTime = 0L;
            Long processingTime = 0L;
            Long requstCount = 0L;
            Long errorCount = 0L;
            BigDecimal bytesReceived = BigDecimal.ZERO;
            BigDecimal bytesSend = BigDecimal.ZERO;
            for (ObjectName obj : requestObjNameSet) {
                if (mBeanServerConnection.getAttribute(obj, "stage").toString().trim().equals("1"))
                    aliveSocketsCount++;
                long nowMaxProcessingTime = Long.parseLong(mBeanServerConnection.getAttribute(obj, "maxTime").toString());
                if (maxProcessingTime < nowMaxProcessingTime)
                    maxProcessingTime = nowMaxProcessingTime;
                processingTime += Long.parseLong(mBeanServerConnection.getAttribute(obj, "processingTime").toString());
                requstCount += Long.parseLong(mBeanServerConnection.getAttribute(obj, "requestCount").toString());
                errorCount += Long.parseLong(mBeanServerConnection.getAttribute(obj, "errorCount").toString());
                bytesReceived = bytesReceived.add(new BigDecimal(mBeanServerConnection.getAttribute(obj, "bytesReceived").toString()));
                bytesSend = bytesSend.add(new BigDecimal(mBeanServerConnection.getAttribute(obj, "bytesSent").toString()));
            }
            System.out.println("活动sockets计数:"+ aliveSocketsCount.toString());
            System.out.println("最大处理时间:"+ maxProcessingTime.toString());
            processingTime = processingTime / 1000;
            System.out.println("总处理时间:"+ processingTime.toString());
            System.out.println("请求总数:"+ requstCount.toString());
            System.out.println("错误总数:"+ errorCount.toString());
            System.out.println("接收字节数:"+ bytesReceived.divide(new BigDecimal(1024L * 1024))
                    .setScale(2, RoundingMode.HALF_UP).toPlainString());
            System.out.println("发送字节数:"+
                    bytesSend.divide(new BigDecimal(1024L * 1024)).setScale(2, RoundingMode.HALF_UP).toPlainString());


//			OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
//			// What % CPU load this current JVM is taking, from 0.0-1.0
//			System.out.println(osBean.getProcessCpuLoad());//指CPU的负载情况
//
//			// What % load the overall system is at, from 0.0-1.0
//			System.out.println(osBean.getSystemCpuLoad());//指系统的负载情况

            System.out.println("\nCpu");
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                            OperatingSystemMXBean.class);
            System.out.println("AvailableProcessors = "
                    + operatingSystemMXBean.getAvailableProcessors());
            double ratio = 0.0;
            long start = System.currentTimeMillis();
            long startC;
            try {
                startC = (Long) mBeanServerConnection
                        .getAttribute(operatingSystemMXBean.getObjectName(),
                                "ProcessCpuTime");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long end = System.currentTimeMillis();
                long endC = (Long) mBeanServerConnection
                        .getAttribute(operatingSystemMXBean.getObjectName(),
                                "ProcessCpuTime");

                int availableProcessors = operatingSystemMXBean
                        .getAvailableProcessors();
                ratio = (endC - startC) / 1000000.0 / (end - start)
                        / availableProcessors;

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            System.out.println("CUP使用率" + round(ratio * 100) + "%");
            System.out.println("\n");

//****将得到的Tomcat指标信息添加到实体中，并存放到数据库中

            String now = df.format(new Date());

            MiddlewareMonitor entity = new MiddlewareMonitor();
            entity.setDevIpAddr(devIpAddr);
            entity.setContent(content);
            entity.setVmVersion(version);
            entity.setVmVendor(vendor);
            entity.setVmName(name);
            entity.setIntervaltime(l);
            entity.setTomcatVersion(tomcatversion);
            entity.setStartTime(df.format(starttime));
            entity.setUptime(formatTimeSpan(timespan));
            entity.setCreateTime(now);
            entity.setMonitorTime(now);
            entity.setUserName(userName);
            //密码加密Base64
            if(StringUtils.isNotBlank(psWord)){
                entity.setPsWord(Cryptos.desEncryptToBase64(psWord, Global.AES_KEY));
            }
            entity.setVmName(name);
            entity.setVmVendor(vendor);
            entity.setVmVersion(version);
            entity.setVms(sb.toString());
            entity.setClassPath(classPath);
            entity.setBootClassPath(bootClassPath);
            entity.setLibraryPath(libraryPath);
//            entity.setCopyCount((int)copyCount);
//            entity.setCopyTime((int)copyTime);
//            entity.setMarkSweepCompactCount((int)markSweepCompactCount);
//            entity.setMarkSweepCompactTime((int)markSweepCompactTime);
            entity.setStatus(1);
            entity.setMiddleType(middleType);


            MiddlewareMonitorInfo info = new MiddlewareMonitorInfo();
            info.setContent(content);
            info.setDevIpAddr(devIpAddr);
            info.setIntervaltime(l);
            info.setMonitorTime(now);
            info.setTomcatVersion(tomcatversion);
            info.setJitName(jitName);
            info.setJitTime((int)jitTime);

            info.setSwapFree(sunOperatingSystemMXBean.getFreeSwapSpaceSize()/1024+"KB");
            info.setSwapTotal(sunOperatingSystemMXBean.getTotalSwapSpaceSize()/1024+"KB");
            info.setTotalMem(sunOperatingSystemMXBean.getTotalPhysicalMemorySize()/1024+"KB");
            info.setFreeMem(freePhysicalMemorySize/1024+"KB");
            info.setOsName(sunOperatingSystemMXBean.getName()+" "+sunOperatingSystemMXBean.getVersion());
            info.setOsArch(sunOperatingSystemMXBean.getArch());
            info.setAvailableProcessors(sunOperatingSystemMXBean.getAvailableProcessors());
            info.setCommittedMem(sunOperatingSystemMXBean.getCommittedVirtualMemorySize()/1024+"KB");



            MiddlewareMonitorHeap monitorHeap = new MiddlewareMonitorHeap();
            monitorHeap.setHeap(heap);
            monitorHeap.setHeapcommitted(convertKB(heapMemoryUsage.getCommitted()));
            monitorHeap.setHeapinit(convertKB(heapMemoryUsage.getInit()));
            monitorHeap.setHeapmax(convertKB(heapMemoryUsage.getMax()));
            monitorHeap.setHeapused(convertKB(heapMemoryUsage.getUsed()));
            monitorHeap.setNoheap(noheap);
            monitorHeap.setNoHeapcommitted(convertKB(nonHeapMemoryUsage.getCommitted()));
            monitorHeap.setNoHeapinit(convertKB(nonHeapMemoryUsage.getInit()));
            monitorHeap.setNoHeapmax(convertKB(nonHeapMemoryUsage.getMax()));
            monitorHeap.setNoHeapused(convertKB(nonHeapMemoryUsage.getUsed()));
            monitorHeap.setMonitorTime(now);

            MiddlewareMonitorThread thread = new MiddlewareMonitorThread();
            thread.setTotalStartedThreadCount(threadMXBean.getTotalStartedThreadCount());
            thread.setAliveSocketsCount(aliveSocketsCount);
            thread.setBytesReceived(bytesReceived);
            thread.setBytesSend(bytesSend);
            thread.setCurrentThreadCpuTime(threadMXBean.getCurrentThreadCpuTime());
            thread.setCurrentThreadUserTime(threadMXBean.getCurrentThreadUserTime());
            thread.setDaemonThreadCount(threadMXBean.getDaemonThreadCount());
            thread.setErrorCount(errorCount);
            thread.setMaxProcessingTime(maxProcessingTime);
            thread.setPeakThreadCount(threadMXBean.getPeakThreadCount());
            thread.setProcessingTime(processingTime);
            thread.setRequstCount(requstCount);
            thread.setThreadCount(threadMXBean.getThreadCount());
            thread.setMonitorTime(now);

            MiddlewareMonitorCpu cpu = new MiddlewareMonitorCpu();
            cpu.setCpu(round(ratio * 100) + "%");
            cpu.setLoadedClassCount(classLoadingMXBean.getLoadedClassCount());
            cpu.setTotalLoadedClassCount(Integer.parseInt(String.valueOf(classLoadingMXBean.getTotalLoadedClassCount())));
            cpu.setUnloadedClassCount(Integer.parseInt(String.valueOf(classLoadingMXBean.getUnloadedClassCount())));
            cpu.setMonitorTime(now);

            middlewareMonitorService.batchSave(entity,info,monitorHeap,thread,cpu);

            jsonStr = middlewareMonitorService.getJsonObjStr(entity);

        } catch (Exception e) {
            e.printStackTrace();
            jsonStr = "连接失败";
        } finally {
            try {
                if (jmxConnector != null) {
                    jmxConnector.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonStr;

    }

    private static String convertKB(long src) {

        if (src <= 0L) {
            return "0KB";
        }
        double conversrc = src / 1024 / 1024;

        return round(conversrc) + "MB";
    }

    private static float round(double src) {
        return (float) (Math.round(src * 100)) / 100;
    }

    public static String formatTimeSpan(long span) {
        long minseconds = span % 1000;

        span = span / 1000;
        long seconds = span % 60;

        span = span / 60;
        long mins = span % 60;

        span = span / 60;
        long hours = span % 24;

        span = span / 24;
        long days = span;
        return (new Formatter()).format("%1$d天 %2$02d:%3$02d:%4$02d.%5$03d",
                days, hours, mins, seconds, minseconds).toString();
    }

    /**
     * @auther: Mr.Lp
     * @desc: 定时器
     * @data: 2019/6/25
     * @param: 
     * @return: 
     */
    private class TestListener implements ServletContextListener {

    	private Integer middleType;
        private String service;
        private String devIpAddr;
        private String content;
        private int l;      //间隔时间
        private Timer timer;

        private String userName;
        private String psWord;

        public TestListener(Integer middleType,String devIpAddr,String content,String service,int l,String userName,String psWord) {
            this.middleType = middleType;
            this.service = service;
            this.devIpAddr = devIpAddr;
            this.content = content;
            this.l = l;
            this.userName = userName;
            this.psWord = psWord;
        }

        public TestListener() {

        }

        private MiddlewareMonitorInfoService middlewareMonitorInfoService;


        public void contextInitialized(ServletContextEvent arg0) {
            timer = new Timer(true);
            arg0.getServletContext().log("定时器已启动");// 添加日志，可在tomcat日志（一般在localhost）中查看到

            middlewareMonitorInfoService = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean(MiddlewareMonitorInfoService.class);
            Auto(middleType,service,devIpAddr,content,l,userName,psWord);
        }

        public void contextDestroyed(ServletContextEvent arg0) {
            timer.cancel();
            arg0.getServletContext().log("定时器销毁");
        }

        /**
         * 自动监控方法
         */
        public void  Auto(final Integer middleType,final String service,final String devIpAddr,final String content,final int l,final String userName,final String psWord) {

            String flag = devIpAddr+content;
            Timer timer = new Timer();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MILLISECOND, l);//秒设置
            timer.schedule(new TimerTask() {
                public void run() {
                    try{
                        JMXmonitor(middleType,service,devIpAddr,content,l,userName,psWord);
                    }catch(Exception e){
                        System.out.println("监控出现异常");
                    }
                }
            }, c.getTime(), l);
//            timer.cancel();
            //声明全局变量map，将每个监控的
            tomcatSchedulerMap.put(flag,timer);
            System.out.println(tomcatSchedulerMap);
        }


    }


    /**
     * @auther: Mr.Lp
     * @desc: ”线图展示“tab页显示
     * @data: 2019/5/30
     * @param: [map, id, devIpAddr, content]
     * @return:
     */
    @RequestMapping(value = "/lineTab.do")
    public String mainTab(ModelMap map,String id,String devIpAddr,String content) {
        logger.info( "Enter lineTab.do ..." );
//        String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";
        map.put("id",id);
        map.put("devIpAddr",devIpAddr);
        map.put("content",content);
//        map.put("service",service);
        return viewPath+"/lineTab";
    }
    
    public String checkJMXConn(String service,String devIpAddr,String content, int l, String userName, String psWord) {
        JMXConnector jmxConnector = null;
        String jsonStr = null;
        try {
            JMXServiceURL ServiceURL = new JMXServiceURL(service);
            Map<String, String[]> environment = new HashMap<String, String[]>();
            // 用户名密码，在jmxremote.password文件中的密码
            String[] credentials = new String[] { userName, psWord };   //monitorRole  QED
            environment.put("jmx.remote.credentials", credentials);
            jmxConnector = JMXConnectorFactory.connect(ServiceURL, environment);
            System.out.println(jmxConnector);

            jsonStr = "连接成功";

        } catch (Exception e) {
            e.printStackTrace();
            jsonStr = "连接失败";
        } finally {
            try {
                if (jmxConnector != null) {
                    jmxConnector.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonStr;

    }
    
    
    /**
     * 实时监测和开启监测前检查服务器连接是否正确
     * @param id
     * @param devIpAddr
     * @param content
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/checkMonitorConn.do")
    public String checkMonitorConn(String id,String devIpAddr,String content, HttpServletResponse response) throws IOException {
        logger.info( "Enter checkMonitorConn.do ..." );
        String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";
        MiddlewareMonitor entity = middlewareMonitorService.getById(id);
        String userName = entity.getUserName();
        String psWord = entity.getPsWord();
        if(StringUtils.isNotBlank(psWord)){
            psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
        }
        Integer l = entity.getIntervaltime();
        String jsonStr = checkJMXConn(service,devIpAddr,content,l,userName,psWord);

        if(StringUtils.isNotBlank(jsonStr)){

            //定时器，暂时注销，可能还会再用
//        timer.schedule(new MiddlewareMonitorAction().new MonitorTask(service), 0, l);
            if(jsonStr.equals("连接失败")){
//                map.put("result", "error");
//                map.put("message", "服务器连接失败");
            	
//                String flag = devIpAddr+content;
//                Timer timer = tomcatSchedulerMap.get(flag);
//
//                timer.cancel();
                
                String error = "{\"result\":\"false\", \"message\":\"服务器连接失败\"}";
            	response.getWriter().print(error);
            }else{
            	//new TestListener().Auto(service,devIpAddr,content,l,userName,psWord);
            	response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
            }

        }else{
            String error = "{\"result\":\"false\", \"message\":\"用户名或密码错误\"}";
            response.getWriter().print(error);


        }
        
        return null;
    }


    /**
     * @auther: Mr.Lp
     * @desc: 展示”内存“页面
     * @data: 2019/5/30
     * @param: [map, id, devIpAddr, content]
     * @return:
     */
    @RequestMapping(value="/showMemory.do")
    public String showMemory(ModelMap map,String id,String devIpAddr,String content){
        logger.info( "Enter showMemory.do ..." );
        System.out.println(id);
        System.out.println(devIpAddr);
        System.out.println(content);

        String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";

//        MiddlewareMonitorInfo info = middlewareMonitorInfoService.getById(id);
        MiddlewareMonitor entity = middlewareMonitorService.getById(id);
        if(entity!=null){
            map.put("userName",entity.getUserName());
            map.put("psWord",entity.getPsWord());
        }

        map.put("devIpAddr",devIpAddr);
        map.put("content",content);
        map.put("service",service);

        return viewPath+"/memory";
    }



    /**
     * @auther: Mr.Lp
     * @desc: 展示”线程“页面
     * @data: 2019/5/30
     * @param: [map, id, devIpAddr, content]
     * @return:
     */
    @RequestMapping(value="/showThread.do")
    public String showThread(ModelMap map,String id,String devIpAddr,String content){
        logger.info( "Enter showThread.do ..." );
        System.out.println(id);
        System.out.println(devIpAddr);
        System.out.println(content);

        String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";

        MiddlewareMonitor entity = middlewareMonitorService.getById(id);
        if(entity!=null){
            map.put("userName",entity.getUserName());
            map.put("psWord",entity.getPsWord());
        }

//        MiddlewareMonitorInfo info = middlewareMonitorInfoService.getById(id);
//        if(info!=null){
//            map.put("userName",info.getUserName());
//            map.put("psWord",info.getPsWord());
//        }

        map.put("devIpAddr",devIpAddr);
        map.put("content",content);
        map.put("service",service);

        return viewPath+"/thread";
    }


    /**
     * @auther: Mr.Lp
     * @desc: 展示”类“页面
     * @data: 2019/5/30
     * @param: [map, id, devIpAddr, content]
     * @return:
     */
    @RequestMapping(value="/showLoadedClass.do")
    public String showLoadedClass(ModelMap map,String id,String devIpAddr,String content){
        logger.info( "Enter showLoadedClass.do ..." );
        System.out.println(id);
        System.out.println(devIpAddr);
        System.out.println(content);

        String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";

        MiddlewareMonitor entity = middlewareMonitorService.getById(id);
        if(entity!=null){
            map.put("userName",entity.getUserName());
            map.put("psWord",entity.getPsWord());
        }

//        MiddlewareMonitorInfo info = middlewareMonitorInfoService.getById(id);
//        if(info!=null){
//            map.put("userName",info.getUserName());
//            map.put("psWord",info.getPsWord());
//        }

        map.put("devIpAddr",devIpAddr);
        map.put("content",content);
        map.put("service",service);

        return viewPath+"/loadedClass";
    }


    /**
     * @auther: Mr.Lp
     * @desc: 展示”概况“页面
     * @data: 2019/5/30
     * @param: [map, id, devIpAddr, content]
     * @return:
     */
    @RequestMapping(value="/showLine.do")
    public String showLine(ModelMap map,String id,String devIpAddr,String content){
        logger.info( "Enter showLine.do ..." );

        String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";

        MiddlewareMonitor entity = middlewareMonitorService.getById(id);
        if(entity!=null){
            map.put("userName",entity.getUserName());
            map.put("psWord",entity.getPsWord());
        }

//        MiddlewareMonitorInfo info = middlewareMonitorInfoService.getById(id);
//        if(info!=null){
//            map.put("userName",info.getUserName());
//            map.put("psWord",info.getPsWord());
//        }

        map.put("devIpAddr",devIpAddr);
        map.put("content",content);
        map.put("service",service);

        return viewPath+"/line";
    }

/**
 * @auther: Mr.Lp
 * @desc:   历史趋势图
 * @data: 2019/6/12
 * @param: [map, id, devIpAddr, content, historyLineTime]
 * @return:
 */
    @RequestMapping(value="/historyLineview.do")
    public String historyLineview(ModelMap map,String id,String devIpAddr,String content,String tomcatVersion,String historyLineTime){
        logger.info( "Enter historyLineview.do ..." );

        String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";

        String[] str ;
        int l = 0 ;
        if(historyLineTime.contains("Min")){
            str = historyLineTime.split("Min");
            System.out.println(str[0]);
            l = Integer.parseInt(str[0])*60*1000;
        }else if(historyLineTime.contains("Hours")){
            str = historyLineTime.split("Hours");
            System.out.println(str[0]);
            l = Integer.parseInt(str[0])*60*60*1000;
        }else if(historyLineTime.contains("Second")){
            str = historyLineTime.split("Second");
            System.out.println(str[0]);
            l = Integer.parseInt(str[0])*1*1000;
        }else if(historyLineTime.contains("Day")){
            str = historyLineTime.split("Day");
            System.out.println(str[0]);
            l = Integer.parseInt(str[0])*60*60*1000*24;
        }

        //当前系统毫秒数
        long now = System.currentTimeMillis();

        long time = now-l;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        //得到监测的时间范围
        String monitorTime = formatter.format(calendar.getTime());

        List<MiddlewareMonitorInfo> list =  middlewareMonitorInfoService.getByIpAndContentAndMonitorTime(devIpAddr,content,tomcatVersion,monitorTime);

        List<MiddlewareMonitorHeap> heaplist = new ArrayList<MiddlewareMonitorHeap>();
        List<MiddlewareMonitorThread> threadlist = new ArrayList<MiddlewareMonitorThread>();
        List<MiddlewareMonitorCpu> cpulist = new ArrayList<MiddlewareMonitorCpu>();
        if(list.size()==0){ //可能当前所选时间段内没有监测数据
            map.put("",heaplist);
            map.put("",threadlist);
            map.put("",cpulist);
        }else{
            List<String> slist = new ArrayList<String>();
            for(int i=0;i<list.size();i++){
                slist.add(list.get(i).getMiddleId());
            }
            heaplist = middlewareMonitorHeapService.getHeapInMiddleIds(slist);
            threadlist = middlewareMonitorThreadService.getThreadInMiddleIds(slist);
            cpulist = middlewareMonitorCpuService.getCpuInMiddleIds(slist);

            map.put("heaplist",heaplist);
            map.put("threadlist",threadlist);
            map.put("cpulist",cpulist);

        }
        map.put("devIpAddr",devIpAddr);
        map.put("content",content);
        map.put("tomcatVersion",tomcatVersion);
        map.put("service",service);
        map.put("historyLineTime",historyLineTime);

        return viewPath+"/historyline";
    }



    @RequestMapping(value="/testButtonview.do")
    public String testButtonview(ModelMap map,String id){
        logger.info( "Enter testButtonview.do ..." );

        System.out.println("id="+id);
        System.out.println();

        map.put("id",id);
        return viewPath+"/testButton";
    }

    @RequestMapping(value="/Buttonview.do")
    public String Buttonview(ModelMap map,String id,HttpServletResponse response) throws IOException{
        logger.info( "Enter Buttonview.do ..." );

//        System.out.println("id="+id);
//        System.out.println();

        MiddlewareMonitor entity = middlewareMonitorService.getById(id);

        List<MiddlewareMonitorInfo> infoList = middlewareMonitorInfoService.getByMonitorId(id);

        String content = createHtmContent(entity,infoList);

        String htmlReport = this.getHtml(content);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(htmlReport);
        return null;
    }

    private String createHtmContent(MiddlewareMonitor entity,List<MiddlewareMonitorInfo> infoList)
    {

        StringBuffer sb = new StringBuffer();

        int wid = 100;
        int wid1 = 200;
        int wid2 = 50;
        int wid3 = 125;

        // 创建开头
        sb.append("<table align=\"center\" style=\"width:900px;border-collapse:collapse;border:0;word-wrap:break-word;word-break:break-all;font:12px/normal;\"> \r\n");
        sb.append("<tr height=\"25\"> \r\n");
        sb.append("<td colspan=\"6\" style=\"border:1px solid #000;\">").append("<STRONG>"+entity.getDevIpAddr()+"</STRONG>").append("<STRONG>——设备巡检结果</STRONG>").append("</td> \r\n");
        sb.append("</tr> \r\n");

        sb.append("<tr> \r\n");
        sb.append("<td style=\"width:").append(wid).append("px;border:1px solid #000;\"><STRONG>厂商：</STRONG>").append("</td> \r\n");
        sb.append("<td style=\"width:").append(wid1).append("px;border:1px solid #000;\">").append(entity.getVmVendor()).append("</td> \r\n");
        sb.append("<td  style=\"width:").append(wid).append("px;border:1px solid #000;\"><STRONG>版本：</STRONG>").append("</td> \r\n");
        sb.append("<td style=\"width:").append(wid1).append("px;border:1px solid #000;\">").append(entity.getVmVersion()).append("</td> \r\n");
        sb.append("<td  style=\"width:").append(wid).append("px;border:1px solid #000;\"><STRONG>启动时间：</STRONG>").append("</td> \r\n");
        sb.append("<td style=\"width:").append(wid1).append("px;border:1px solid #000;\">").append(entity.getStartTime()).append("</td> \r\n");
        sb.append("</tr> \r\n");

        sb.append("<tr> \r\n");
        sb.append("<td  style=\"border:1px solid #000;\"><STRONG>虚拟机：</STRONG>").append("</td> \r\n");
        sb.append("<td style=\"border:1px solid #000;\">").append(entity.getVmName()).append("</td> \r\n");

        sb.append("<td nowrap=\"nowrap\" style=\"border:1px solid #000;\"><STRONG>连续工作时间：</STRONG>").append("</td> \r\n");
        sb.append("<td style=\"border:1px solid #000;\">").append(entity.getUptime()).append("</td> \r\n");

        sb.append("<td nowrap=\"nowrap\" style=\"border:1px solid #000;\"><STRONG>监测时间间隔：</STRONG>").append("</td> \r\n");
        sb.append("<td style=\"border:1px solid #000;\">").append(entity.getMonitorTime()).append("</td> \r\n");

        sb.append("</tr> \r\n");

        sb.append("</table> \r\n");

        sb.append("<table align=\"center\" style=\"width:900px;border-collapse:collapse;border:0;word-wrap:break-word;word-break:break-all;font:12px/normal;\"> \r\n");

        for(int i=0;i<2;i++){
            int size = infoList.size();

            sb.append("<tr> \r\n");
            sb.append("<td nowrap=\"nowrap\" rowspan=\"").append(size + 2).append("\" style=\"width:").append(wid3).append("px;border:1px solid #000;font-weight:bold;\">");
            sb.append(infoList.get(i).getDevIpAddr()).append("</td> \r\n");
            sb.append("</tr> \r\n");
            sb.append("<tr align=\"center\" style=\"font-weight:bold;\"> \r\n");
            sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid2).append("px;border:1px solid #000;\">序号</td> \r\n");
            sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid1).append("px;border:1px solid #000;\">监测时间</td> \r\n");
            sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid1).append("px;border:1px solid #000;\">Tomcat版本</td> \r\n");
            sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid).append("px;border:1px solid #000;\">总物理内存</td> \r\n");
            sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid).append("px;border:1px solid #000;\">总交换空间</td> \r\n");
            sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid3).append("px;border:1px solid #000;\">提交的虚拟内存</td> \r\n");
            sb.append("</tr> \r\n");

            for(int j=0;j<size;j++){
                sb.append("<tr align=\"center\"> \r\n");
                sb.append("<td style=\"border:1px solid #000;\">").append(j + 1).append("</td> \r\n");
                sb.append("<td style=\"border:1px solid #000;\" align=\"left\">").append(infoList.get(j).getMonitorTime()).append("</td> \r\n");
                sb.append("<td style=\"border:1px solid #000;\" align=\"left\">").append(infoList.get(j).getTomcatVersion() ).append("</td> \r\n");
                sb.append("<td style=\"border:1px solid #000;\" align=\"left\">").append(infoList.get(j).getTotalMem()).append("</td> \r\n");
                sb.append("<td style=\"border:1px solid #000;\">").append(infoList.get(j).getSwapTotal()).append("</td> \r\n");
                sb.append("<td style=\"border:1px solid #000;\">").append(infoList.get(j).getCommittedMem()).append("</td> \r\n");
                sb.append("</tr> \r\n");
            }

        }

        sb.append("</table> \r\n");

        System.out.println(sb.toString());
        System.out.println();

        return sb.toString();
    }

    @RequestMapping(value="/downloadCPWord.do")
    public String downloadCPWord(String id,HttpServletResponse response) throws Exception
    {
//        System.out.println("id="+id);
//        System.out.println();

        MiddlewareMonitor entity = middlewareMonitorService.getById(id);

        List<MiddlewareMonitorInfo> infoList = middlewareMonitorInfoService.getByMonitorId(id);

        String content = createHtmContent(entity,infoList);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String now = df.format(new Date()); //系统当前时间
        String fileName = "任务报告"+now+".doc";

//        response.addHeader("Content-Disposition","attachment;fileName="+ fileName);        //名称中没有中文时候可如此设置
//        response.setHeader("content-type", "text/html;charset=UTF-8");

        fileName = new String(fileName.getBytes(), "ISO-8859-1");   //名称中有中文时候如此设置
        response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);

        response.setContentType("application/octet-stream");
        ServletOutputStream out = response.getOutputStream();
        out.write(content.getBytes("UTF-8"));
        out.flush();
        return null;
    }

//    @RequestMapping(value="/downloadCPPDF.do")
//    public String downloadCPPDF(String id,HttpServletResponse response) throws Exception
//    {
//
//        MiddlewareMonitor entity = middlewareMonitorService.getById(id);
//
//        List<MiddlewareMonitorInfo> infoList = middlewareMonitorInfoService.getByMonitorId(id);
//
//        String content = createHtmContent(entity,infoList);
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//        String now = df.format(new Date()); //系统当前时间
//        String fileName = "巡检报告"+now+".pdf";
//
//        response.addHeader("Content-Disposition","attachment; filename=rename.pdf");
//
////        fileName = new String(fileName.getBytes(), "ISO-8859-1");   //名称中有中文时候如此设置
////        response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);
//
//        response.setContentType("application/octet-stream");
//
//        ServletOutputStream out = response.getOutputStream();
//        HtmlConvertUtil.convert2PDFString(out,content);
//        out.flush();
//        return null;
//    }

    private String getHtml(String content) throws IOException
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<html> \r\n");
        sb.append("<head> \r\n");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> \r\n");
        sb.append("<title>巡检报告</title> \r\n");
        sb.append("<style type=\"text/css\"> \r\n");
        sb.append("@page { \r\n");
        sb.append("size: 1000px 1000px; \r\n");
        sb.append("} \r\n");
        sb.append("</style> \r\n");
        sb.append("</head> \r\n");
        sb.append("<body style=\"font-family:SimSun;font-size:22px;\"> \r\n");  //字体倾斜：font-style:italic;
//        sb.append("<div align=\"center\" style=\"font-size:25px; margin-left:auto; margin-right:auto; margin-top:20px; margin-bottom:20px;\">任务报告</div> \r\n");
        sb.append("<div align=\"center\" >").append("<h2>巡检报告</h2>").append("</div> \r\n");
        sb.append(content);
        sb.append("</body> \r\n");
        sb.append("</html> \r\n");

        return sb.toString();
    }


    @RequestMapping(value="/showTomcatView.do")
    public String showTomcatView(ModelMap map,String id,String devIpAddr,String content){
        logger.info( "Enter showTomcatView.do ..." );

        String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";

        MiddlewareMonitorInfo info =  middlewareMonitorInfoService.getById(id);
        MiddlewareMonitor entity = new MiddlewareMonitor();
        MiddlewareMonitorHeap heap = new MiddlewareMonitorHeap();
        MiddlewareMonitorThread thread = new MiddlewareMonitorThread();
        MiddlewareMonitorCpu monitorCpu = new MiddlewareMonitorCpu();
        if(info!=null){
            entity = middlewareMonitorService.getById(info.getMonitorId());
            heap = middlewareMonitorHeapService.getHeapByMiddleId(info.getMiddleId());
            thread = middlewareMonitorThreadService.getThreadByMiddleId(info.getMiddleId());
            monitorCpu = middlewareMonitorCpuService.getCpuByMiddleId(info.getMiddleId());
        }

//        //获取当前年月日
//        DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//        long now = System.currentTimeMillis();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(now);
//        String nowTime = formatter.format(calendar.getTime());
//        //获取当前星期数
//        Date date = new Date();
//        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
//        String currSun = dateFm.format(date);
//        //获取当前系统时间以及星期数
//        String viewDate =nowTime+" "+currSun;

        //获取时间以及星期数
        String viewDate = null;
        String time = info.getMonitorTime();
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(sdf.parse(time));
            int i =calendar.get(Calendar.DAY_OF_WEEK);
            //i为1~7，当i==1时是周日，i为其他数字时星期数为i-1
            switch (i) {
                case 1:
                    viewDate = time+" 星期日";
                    break;
                case 2:
                    viewDate = time+" 星期一";
                    break;
                case 3:
                    viewDate = time+" 星期二";
                    break;
                case 4:
                    viewDate = time+" 星期三";
                    break;
                case 5:
                    viewDate = time+" 星期四";
                    break;
                case 6:
                    viewDate = time+" 星期五";
                    break;
                case 7:
                    viewDate = time+" 星期六";
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        map.put("viewDate",viewDate);
        map.put("info",info);
        map.put("entity",entity);
        map.put("heap",heap);
        map.put("thread",thread);
        map.put("monitorCpu",monitorCpu);

        return viewPath+"/view";
    }


    /**
     * @auther: Mr.Lp
     * @desc: 计算CPU利用率并返回到前台
     * @data: 2019/5/30
     * @param: [service, intervalTime, response]
     * @return:
     */
    @RequestMapping(value="/JMXmonitorLineViewCPU.do")
    public String JMXmonitorLineViewCPU(String service,String intervalTime, HttpServletResponse response, String userName, String psWord) throws IOException {
        JMXConnector jmxConnector = null;
        String data = null;
        try {
            JMXServiceURL ServiceURL = new JMXServiceURL(service);
            Map<String, String[]> environment = new HashMap<String, String[]>();
            // 用户名密码，在jmxremote.password文件中的密码
            if(StringUtils.isNotBlank(psWord)){
                psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
            }
            String[] credentials = new String[] { userName, psWord };       //"monitorRole", "QED"
            environment.put("jmx.remote.credentials", credentials);
            jmxConnector = JMXConnectorFactory.connect(ServiceURL, environment);

            MBeanServerConnection mBeanServerConnection = jmxConnector
                    .getMBeanServerConnection();


            //CPU
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                            OperatingSystemMXBean.class);


                double ratio = 0.0;

            try {
                long endC = (Long) mBeanServerConnection
                        .getAttribute(operatingSystemMXBean.getObjectName(),
                                "ProcessCpuTime");

                int availableProcessors = operatingSystemMXBean
                        .getAvailableProcessors();

//                System.out.println(cpulist.size());
//                System.out.println(cpulist);

                //API只提供了获取cpu的使用时间，在两次系统时间间隔内获取两次CPU的使用时间，得到在该时间间隔内cpu使用的时间，相除即得到CPU的使用率
                ratio = (endC - cpulist.get(0)) / 1000000.0 / Long.parseLong(intervalTime)
                        / availableProcessors;

//                System.out.println(ratio);

                cpulist.clear();
                cpulist.add(endC);

            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


//****将得到的Tomcat指标信息添加到实体中
            ParamMiddlewareMonitor entity = new ParamMiddlewareMonitor();
            entity.setCpu(round(ratio * 100) + "%");

            data = paramMiddlewareMonitorService.getJsonObjStr(entity);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (jmxConnector != null) {
                    jmxConnector.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(data);
        return null;
    }


    /**
     * @auther: Mr.Lp
     * @desc: 获取CPU利用率时候的起始ＣＰＵ使用时间（存放在全局变量的cpulist中，在计算cpu利用率时候使用）
     * @data: 2019/5/30
     * @param: [service, response]
     * @return:
     */
    @RequestMapping(value="/JMXmonitorLineStartCpu.do")
    public String JMXmonitorLineStartCpu(String service, HttpServletResponse response, String userName, String psWord) throws IOException {
        JMXConnector jmxConnector = null;
        String data = null;
        try {
            JMXServiceURL ServiceURL = new JMXServiceURL(service);
            Map<String, String[]> environment = new HashMap<String, String[]>();
            // 用户名密码，在jmxremote.password文件中的密码
            if(StringUtils.isNotBlank(psWord)){
                psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
            }
            String[] credentials = new String[] { userName, psWord };       //"monitorRole", "QED"
            environment.put("jmx.remote.credentials", credentials);
            jmxConnector = JMXConnectorFactory.connect(ServiceURL, environment);

            MBeanServerConnection mBeanServerConnection = jmxConnector
                    .getMBeanServerConnection();


            //CPU
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                            OperatingSystemMXBean.class);


            double ratio = 0.0;
            long startC;

            try {
                startC = (Long) mBeanServerConnection
                        .getAttribute(operatingSystemMXBean.getObjectName(),
                                "ProcessCpuTime");
                cpulist.clear();
                cpulist.add(startC);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            ParamMiddlewareMonitor entity = new ParamMiddlewareMonitor();
            entity.setCpu(round(ratio * 100) + "%");

            data = paramMiddlewareMonitorService.getJsonObjStr(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (jmxConnector != null) {
                    jmxConnector.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(data);
        return null;
    }


    /**
     * @auther: Mr.Lp
     * @desc: tomcat监测功能
     * @data: 2019/5/30
     * @param: [service, response]
     * @return: 
     */
    @RequestMapping(value="/JMXmonitorLineView.do")
    public String JMXmonitorLineView(String service, HttpServletResponse response, String userName, String psWord) throws IOException {
        JMXConnector jmxConnector = null;
        String data = null;
        try {
            JMXServiceURL ServiceURL = new JMXServiceURL(service);
            Map<String, String[]> environment = new HashMap<String, String[]>();
            // 用户名密码，在jmxremote.password文件中的密码
            if(StringUtils.isNotBlank(psWord)){
                psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
            }
            String[] credentials = new String[] { userName, psWord };       //"monitorRole", "QED"
            environment.put("jmx.remote.credentials", credentials);
            jmxConnector = JMXConnectorFactory.connect(ServiceURL, environment);

            MBeanServerConnection mBeanServerConnection = jmxConnector
                    .getMBeanServerConnection();

            // 获取MemoryMXBean
            MemoryMXBean memoryMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.MEMORY_MXBEAN_NAME,
                            MemoryMXBean.class);

            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

            // 获取 ThreadMXBean
            ThreadMXBean threadMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.THREAD_MXBEAN_NAME,
                            ThreadMXBean.class);
            ClassLoadingMXBean classLoadingMXBean = ManagementFactory
                    .newPlatformMXBeanProxy(mBeanServerConnection,
                            ManagementFactory.CLASS_LOADING_MXBEAN_NAME,
                            ClassLoadingMXBean.class);



//****将得到的Tomcat指标信息添加到实体中
            ParamMiddlewareMonitor entity = new ParamMiddlewareMonitor();
            entity.setTotalLoadedClassCount(Integer.parseInt(String.valueOf(classLoadingMXBean.getTotalLoadedClassCount())));
            entity.setLoadedClassCount(classLoadingMXBean.getLoadedClassCount());
            entity.setPeakThreadCount(threadMXBean.getPeakThreadCount());
            entity.setThreadCount(threadMXBean.getThreadCount());
            entity.setHeapused(convertKB(heapMemoryUsage.getUsed()));
            entity.setHeapmax(convertKB(heapMemoryUsage.getMax()));
            entity.setHeapcommitted(convertKB(heapMemoryUsage.getCommitted()));
            entity.setHeapusedKB(heapMemoryUsage.getUsed()/1024+"");
            entity.setHeapmaxKB(heapMemoryUsage.getMax()/1024+"");
            entity.setHeapcommittedKB(heapMemoryUsage.getCommitted()/1024+"");
            entity.setUnloadedClassCount(Integer.parseInt(String.valueOf(classLoadingMXBean.getUnloadedClassCount()))+"");

            data = paramMiddlewareMonitorService.getJsonObjStr(entity);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (jmxConnector != null) {
                    jmxConnector.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(data);
        return null;
    }

    @RequestMapping(value = "/delete.do")
    public String delete(String id, HttpServletResponse response) throws IOException {
        logger.info( "Enter delete.do ..." );
        List<String> infoIds = new ArrayList<String>();
        List<String> heapIds = new ArrayList<String>();
        List<String> threadIds = new ArrayList<String>();
        List<String> cpuIds = new ArrayList<String>();

        if(!StringUtil.isNullString(id)){

            List<MiddlewareMonitorInfo> infolist = middlewareMonitorInfoService.getByMonitorId(id);
            List<MiddlewareMonitorHeap> heaplist = middlewareMonitorHeapService.getByMonitorId(id);
            List<MiddlewareMonitorThread> threadlist = middlewareMonitorThreadService.getByMonitorId(id);
            List<MiddlewareMonitorCpu> cpulist = middlewareMonitorCpuService.getByMonitorId(id);

            for(int i=0;i<infolist.size();i++){
                infoIds.add(infolist.get(i).getMiddleId());
            }
            for(int i=0;i<heaplist.size();i++){
                heapIds.add(heaplist.get(i).getId());
            }
            for(int i=0;i<threadlist.size();i++){
                threadIds.add(threadlist.get(i).getId());
            }
            for(int i=0;i<cpulist.size();i++){
                cpuIds.add(cpulist.get(i).getId());
            }

            middlewareMonitorService.batchDelete(id,infoIds,heapIds,threadIds,cpuIds);

        }
        response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
        return null;
    }

    @RequestMapping(value = "/edit.do")
    public String edit(String id, ModelMap map) {
        logger.info( "Enter edit.do ..." );
        MiddlewareMonitor middlewareMonitor = middlewareMonitorService.getById(id);
        String psWord = middlewareMonitor.getPsWord();
        if(StringUtils.isNotBlank(psWord)){
            psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
            map.put("psWord", psWord);

        }
        map.put("action", "edit");
        map.put("middlewareMonitor", middlewareMonitor);
        map.put("id", id);
        return viewPath+"/edit";
    }


    @RequestMapping(value = "/save.do")
    public String save(String action, String id, String time, MiddlewareMonitor middlewareMonitor, ModelMap map) throws JsonParseException, JsonMappingException, IOException {
        logger.info( "Enter save.do ..." );
        map.put("action", action);
        MiddlewareMonitor saveMiddlewareMonitor = new MiddlewareMonitor();
        MiddlewareMonitor editMiddlewareMonitor = middlewareMonitorService.getById(id);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(new Date());
        String str[];
        int l = 0 ;
        if(time.contains("Min")){
            str = time.split("Min");
            System.out.println(str[0]);
            l = Integer.parseInt(str[0])*60*1000;
        }else if(time.contains("Hours")){
            str = time.split("Hours");
            System.out.println(str[0]);
            l = Integer.parseInt(str[0])*60*60*1000;
        }else if(time.contains("Second")){
            str = time.split("Second");
            System.out.println(str[0]);
            l = Integer.parseInt(str[0])*1*1000;
        }
        if(id!=null&&middlewareMonitor!=null){
            String passWord = middlewareMonitor.getPsWord();
            //密码加密Base64
            if(StringUtils.isNotBlank(passWord)){
                editMiddlewareMonitor.setPsWord(Cryptos.desEncryptToBase64(passWord, Global.AES_KEY));
            }
            editMiddlewareMonitor.setUserName(middlewareMonitor.getUserName());
            editMiddlewareMonitor.setIntervaltime(l);
            editMiddlewareMonitor.setStartTime(now);
            BeanUtils.copyProperties(editMiddlewareMonitor, saveMiddlewareMonitor);

//            saveMiddlewareMonitor = middlewareMonitor;
            middlewareMonitorService.save(saveMiddlewareMonitor);
            map.put("result", "success");
            map.put("middlewareMonitor", saveMiddlewareMonitor);

        }else{
            map.put("result", "error");
            map.put("message", "修改失败！");

        }

        return viewPath+"/edit";
    }


    /**
     * @auther: Mr.Lp
     * @desc: 启动Tomcat监测(监测开启)
     * @data: 2019/6/24
     * @param: [id, response]
     * @return: 
     */
    @RequestMapping(value = "/startTomcatMonitor.do")
    public String startTomcatMonitor(String id, HttpServletResponse response) throws IOException {
        logger.info( "Enter startTomcatMonitor.do ..." );

        if(!StringUtil.isNullString(id)){
            MiddlewareMonitor middlewareMonitor = middlewareMonitorService.getById(id);
            
            Integer middleType = middlewareMonitor.getMiddleType();

            String devIpAddr = middlewareMonitor.getDevIpAddr();

            String content = middlewareMonitor.getContent();

            String userName = middlewareMonitor.getUserName();

            String psWord = middlewareMonitor.getPsWord();
            if(StringUtils.isNotBlank(psWord)){
                psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
            }

            int l = middlewareMonitor.getIntervaltime();

            String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";


            String jsonStr = JMXmonitor(middleType,service,devIpAddr,content,l,userName,psWord);

            if(StringUtils.isNotBlank(jsonStr)){
                new TestListener().Auto(middleType,service,devIpAddr,content,l,userName,psWord);
            }

        }
        response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
        return null;
    }

    
    /**
     * @auther: Mr.Lp
     * @desc: 关闭Tomcat监测
     * @data: 2019/6/24
     * @param: [id, response]
     * @return: 
     */
    @RequestMapping(value = "/shutdown.do")
    public String shutdown(String id, HttpServletResponse response) throws IOException {
        logger.info( "Enter shutdown.do ..." );
        MiddlewareMonitor saveMiddlewareMonitor = new MiddlewareMonitor();
        if(!StringUtil.isNullString(id)){
            MiddlewareMonitor middlewareMonitor = middlewareMonitorService.getById(id);

            String devIpAddr = middlewareMonitor.getDevIpAddr();

            String content = middlewareMonitor.getContent();

            String flag = devIpAddr+content;
            Timer timer = tomcatSchedulerMap.get(flag);

	          for(Iterator<String> iterator = tomcatSchedulerMap.keySet().iterator(); iterator.hasNext(); ) {
	      	  String key = iterator.next();
	      	  if(key.equals(flag)) {
	      	    iterator.remove();
	      	  }
	      	}
	          
            //tomcatSchedulerMap.remove(flag);
            System.out.println(tomcatSchedulerMap);
            
            timer.cancel();
            
//            timer.schedule(new TimerTask() {
//    			
//    			@Override
//    			public void run() {
//    				// TODO Auto-generated method stub
//    				System.out.println("learn...");
//    				timer.cancel();
//    			}
//    		}, 3000);
            

            BeanUtils.copyProperties(middlewareMonitor, saveMiddlewareMonitor);
            saveMiddlewareMonitor.setStatus(0);

            middlewareMonitorService.save(saveMiddlewareMonitor);
        }

        response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
        return null;
    }
    
//    for(Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
//    	  Integer key = iterator.next();
//    	  if(key != 1) {
//    	    iterator.remove();
//    	  }
//    	}复制代码



}
