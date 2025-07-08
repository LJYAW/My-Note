/**
 * 
 */
package com.sino.fas.res.mdlware.app.web;

import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import smartlink.utils.Util;

import com.sino.base.common.Global;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.OrganService;
import com.sino.cmdb.indicator.cmdCheckItems.service.JschUtils;
import com.sino.fas.res.host.entity.IpHost;
import com.sino.fas.res.host.entity.IpHostInfo;
import com.sino.fas.res.host.entity.ResAppService;
import com.sino.fas.res.host.entity.ResHosts;
import com.sino.fas.res.host.entity.ResServiceProcess;
import com.sino.fas.res.host.service.IpHostService;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitor;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorCpu;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorHeap;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorInfo;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorThread;
import com.sino.fas.res.mdlware.app.entity.ParamMiddlewareMonitor;
import com.sino.fas.res.mdlware.app.entity.ResMdlWare;
import com.sino.fas.res.mdlware.app.service.MiddlewareMonitorCpuService;
import com.sino.fas.res.mdlware.app.service.MiddlewareMonitorHeapService;
import com.sino.fas.res.mdlware.app.service.MiddlewareMonitorInfoService;
import com.sino.fas.res.mdlware.app.service.MiddlewareMonitorThreadService;
import com.sino.fas.res.mdlware.app.service.ParamMiddlewareMonitorService;
import com.sino.fas.res.mdlware.app.service.ResMdlWareService;
import com.sino.snmp.utils.JdbcConnection;
import com.sino.utils.common.Cryptos;

/**
 * @author Mr.LP
 * @date 2019-11-20上午9:19:36
 * @className ResMdlWareAction
 *
 * @Description TODO
 *
 */

@Controller
@RequestMapping(value = "/fas/res/mdlware")
public class ResMdlWareAction {
	
	private static Logger logger = LoggerFactory.getLogger(ResMdlWareAction.class);

    private String viewPath = "/fas/res/mdlware";

    private static final String DES = "DES";

    @Autowired
    private ResMdlWareService resMdlWareService;

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

    @Autowired
	private IpHostService ipHostService;

    @Autowired
    private OrganService organService;
    private Map<String ,Timer> tomcatSchedulerMap = new HashMap<String ,Timer>();
    private List<Long> cpulist = new ArrayList<Long>();


    @RequestMapping(value = "/main.do")
    public String main(String menuId,ModelMap map, String id) {
        logger.info( "Enter main.do ..." );
        List<ResMdlWare> list = resMdlWareService.getAll();
        String jsonStr = resMdlWareService.getJsonListStr(list);
        map.put("jsonListData", jsonStr);
        WebFuncUtils.titleContent(menuId,map);
        map.put("id", id);

        return viewPath+"/main";
    }
    
    /**
     * 添加跳转页面
     * @param map
     * @return
     */
    @RequestMapping(value="/add.do")
    public String add(ModelMap map){
        logger.info( "Enter add.do ..." );
        return viewPath+"/add";
    }
    
    /**
     * 访问验证(添加页面的按钮)
     * @param svrIpAddr
     * @param monPort
     * @param userName
     * @param passWord
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/accessVerify.do")
	public String accessVerify(String svrIpAddr,Integer monPort, String userName, String passWord,HttpServletResponse response) throws IOException {
		logger.info( "Enter accessVerify.do ..." );
		
		JSONObject jo = new JSONObject();
		String service = null;
	    //    "service:jmx:rmi:///jndi/rmi://192.168.102.95:9999/jmxrmi";
	        if(svrIpAddr!=null&&svrIpAddr!=""&&monPort!=null){

	            service = "service:jmx:rmi:///jndi/rmi://"+svrIpAddr+":"+monPort+"/jmxrmi";
	            System.out.println(service);

	            String jsonStr = CheckJMXmonitor(service,svrIpAddr,monPort,userName,passWord);

	            if(StringUtils.isNotBlank(jsonStr)){

	                if(jsonStr.contains("连接失败")){
	                	if(jsonStr.contains("Invalid username or password")){
	                		jo.put("message", "服务器连接失败,无效的用户名或密码！");
	                	}else if(jsonStr.contains("Connection timed out: connect")){
	                		jo.put("message", "服务器连接失败，连接超时！");
	                	}
	    				response.getWriter().print(jo.toString());
	                }else{
	                    response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	                }

	            }else{
	                jo.put("message", "服务器连接失败！");
					response.getWriter().print(jo.toString());
	            }

	        }else{
	        	jo.put("message", "请检查服务器IP或监测端口号是否输入！");
				response.getWriter().print(jo.toString());
	        }
		
		return null;
   }
    
    /**
     * 访问验证(main页面的按钮)
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/mainAccessVerify.do")
	public String mainAccessVerify(Long id,HttpServletResponse response) throws IOException {
		logger.info( "Enter mainAccessVerify.do ..." );
		ResMdlWare mdlWare = resMdlWareService.getById(id);
		String svrIpAddr = mdlWare.getSvrIpAddr();
        Integer monPort = mdlWare.getMonPort();
        String userName = mdlWare.getUserName();
        String passWord = mdlWare.getPassWord();
		JSONObject jo = new JSONObject();
		String service = null;
	    //    "service:jmx:rmi:///jndi/rmi://192.168.102.95:9999/jmxrmi";
	        if(svrIpAddr!=null&&svrIpAddr!=""&&monPort!=null){

	            service = "service:jmx:rmi:///jndi/rmi://"+svrIpAddr+":"+monPort+"/jmxrmi";
	            System.out.println(service);

	            String jsonStr = CheckJMXmonitor(service,svrIpAddr,monPort,userName,passWord);

	            if(StringUtils.isNotBlank(jsonStr)){

	                if(jsonStr.contains("连接失败")){
	                	if(jsonStr.contains("Invalid username or password")){
	                		jo.put("message", "服务器连接失败,无效的用户名或密码！");
	                	}else if(jsonStr.contains("Connection timed out: connect")){
	                		jo.put("message", "服务器连接失败，连接超时！");
	                	}else{
	                		jo.put("message", "服务器连接失败！");
	                	}
	    				response.getWriter().print(jo.toString());
	                }else{
	                    response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	                }

	            }else{
	                jo.put("message", "服务器连接失败！");
					response.getWriter().print(jo.toString());
	            }

	        }else{
	        	jo.put("message", "请检查服务器IP或监测端口号是否输入！");
				response.getWriter().print(jo.toString());
	        }
		
		return null;
   }
    
    /**
     * 访问验证（连接检查）
     * @param service
     * @param devIpAddr
     * @param content
     * @param l
     * @param userName
     * @param psWord
     * @return
     */
    public String CheckJMXmonitor(String service,String devIpAddr,Integer content, String userName, String psWord) {
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
            //e.printStackTrace();
            System.out.println(e);
            jsonStr = "连接失败" + e;
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
     * 添加保存
     * @param mdlWare
     * @param map
     * @return
     */
    @RequestMapping(value="/save.do")
    public String save(ResMdlWare mdlWare,ModelMap map){
        logger.info( "Enter save.do ..." );
        if(mdlWare.getHomedHostIp()==""||mdlWare.getHomedHostIp()==null){
        	mdlWare.setHomedHostId(null);
        }
        
         //    "service:jmx:rmi:///jndi/rmi://192.168.102.95:9999/jmxrmi";
         String svrIpAddr = mdlWare.getSvrIpAddr();
         Integer monPort = mdlWare.getMonPort();
        
             if(svrIpAddr!=null&&svrIpAddr!=""&&monPort!=null){

            	 ResMdlWare newMdlWare = resMdlWareService.getByIpAndPort(svrIpAddr,monPort);
                 if(newMdlWare!=null){
                     map.put("result", "error");
                     map.put("message", "服务器"+svrIpAddr+"下的端口"+monPort+"已经开启监测，请重新输入要监测的服务器和端口号！");
                     return viewPath+"/add";
                 }

                 String jsonStr = JMXmonitor(mdlWare);

                 if(StringUtils.isNotBlank(jsonStr)){

                     	new TestListener().Auto(mdlWare);
                     	
                         map.put("saveData", jsonStr);
                         map.put("result", "success");

                 }

             }else{
                 map.put("result", "error");
                 map.put("message", "请检查服务器IP或监测端口号是否输入！");
             }

        return viewPath+"/add";
    }
    
    public String JMXmonitor(ResMdlWare mdlWare) {
    	
    	String svrIpAddr = mdlWare.getSvrIpAddr();
        Integer monPort = mdlWare.getMonPort();
    	String service = "service:jmx:rmi:///jndi/rmi://"+svrIpAddr+":"+monPort+"/jmxrmi";
    	String userName = mdlWare.getUserName();
        String psWord = mdlWare.getPassWord();
        JMXConnector jmxConnector = null;
        String jsonStr = null;
        //间隔时间（暂时写死）
        int intervaltime = 5*60*1000;

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

              
            System.out.println("\nCpu");
            
          //获取远程opretingsystemmxbean  
            com.sun.management.OperatingSystemMXBean opMXbean =   
            (com.sun.management.OperatingSystemMXBean) ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,                 
            ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, com.sun.management.OperatingSystemMXBean.class); 
            
            double ratio = 0.0;
            long start1 = System.currentTimeMillis();  
            long startT = opMXbean.getProcessCpuTime();  
            /**    Collect data every 5 seconds      */  
            try {  
                TimeUnit.SECONDS.sleep(5);  
            } catch (InterruptedException e) {  
                logger.error("InterruptedException occurred while MemoryCollector sleeping...");  
            }  
            Long end1 = System.currentTimeMillis();  
            long endT = opMXbean.getProcessCpuTime();  
    //end - start 即为当前采集的时间单元，单位ms  
    //endT - startT 为当前时间单元内cpu使用的时间，单位为ns  
    //所以：double ratio = (entT-startT)/1000000.0/(end-start)/opMXbean.getAvailableProcessors()  
            ratio = (endT-startT)/1000000.0/(end1-start1)/opMXbean.getAvailableProcessors();
            
            //下面注销方法与上面获取CPU利用率方法类同
            
//          OperatingSystemMXBean operatingSystemMXBean = ManagementFactory
//          .newPlatformMXBeanProxy(mBeanServerConnection,
//                  ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
//                  OperatingSystemMXBean.class);
//  System.out.println("AvailableProcessors = "
//          + operatingSystemMXBean.getAvailableProcessors());
            
//            long start = System.currentTimeMillis();
//            long startC;
//            try {
//                startC = (Long) mBeanServerConnection.getAttribute(operatingSystemMXBean.getObjectName(),"ProcessCpuTime");
//                try {
//                    TimeUnit.SECONDS.sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                long end = System.currentTimeMillis();
//                long endC = (Long) mBeanServerConnection.getAttribute(operatingSystemMXBean.getObjectName(),"ProcessCpuTime");
//
//                int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
//                ratio = (endC - startC) / 1000000.0 / (end - start) / availableProcessors;
//
//            } catch (Exception e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }

            System.out.println("CUP使用率" + round(ratio * 100) + "%");
            System.out.println("\n");

//****将得到的Tomcat指标信息添加到实体中，并存放到数据库中

            String now = df.format(new Date());

            ResMdlWare newMdlWare = new ResMdlWare();
            newMdlWare = mdlWare ;
            newMdlWare.setSvrIpLong(Util.ip2long(newMdlWare.getSvrIpAddr()));
            
//            IpHost ipHost = ipHostService.getIpHostByIpAddr(newMdlWare.getSvrIpAddr());
//            if(ipHost!=null){
//            	newMdlWare.setResId(ipHost.getIpHostId());
//			}else{
//				newMdlWare.setResId(JdbcConnection.getInstance().getDeviceResourceUniqId());
//			}
            
            newMdlWare.setResClassCode(24);
            newMdlWare.setSvcUrl(service);
            newMdlWare.setProdVersion(tomcatversion);
            newMdlWare.setMonStatus(1);
            newMdlWare.setMonTime(now);
            //密码加密Base64
//            if(StringUtils.isNotBlank(psWord)){
//                entity.setPsWord(Cryptos.desEncryptToBase64(psWord, Global.AES_KEY));
//            }


            MiddlewareMonitorInfo info = new MiddlewareMonitorInfo();
            info.setContent(newMdlWare.getMonPort().toString());
            info.setDevIpAddr(newMdlWare.getSvrIpAddr());
            info.setIntervaltime(intervaltime);
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

            resMdlWareService.batchSave(newMdlWare,info,monitorHeap,thread,cpu);

            jsonStr = resMdlWareService.getJsonObjStr(newMdlWare);

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
    
    /**
     * @auther: Mr.Lp
     * @desc: 定时器
     * @data: 2019/6/25
     * @param: 
     * @return: 
     */
    private class TestListener implements ServletContextListener {

//        private String service;
//        private String devIpAddr;
//        private String content;
//        private int l;      //间隔时间
        private Timer timer;

//        private String userName;
//        private String psWord;
        
        private ResMdlWare resMdlWare;

        public TestListener(ResMdlWare resMdlWare) {
            this.resMdlWare = resMdlWare;
        }

        public TestListener() {

        }

        private MiddlewareMonitorInfoService middlewareMonitorInfoService;


        public void contextInitialized(ServletContextEvent arg0) {
            timer = new Timer(true);
            arg0.getServletContext().log("定时器已启动");// 添加日志，可在tomcat日志（一般在localhost）中查看到

            middlewareMonitorInfoService = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean(MiddlewareMonitorInfoService.class);
            Auto(resMdlWare);
        }

        public void contextDestroyed(ServletContextEvent arg0) {
            timer.cancel();
            arg0.getServletContext().log("定时器销毁");
        }

        /**
         * 自动监控方法
         */
        public void  Auto(final ResMdlWare resMdlWare) {

        	String devIpAddr = resMdlWare.getSvrIpAddr();
        	Integer content = resMdlWare.getMonPort();
            String flag = devIpAddr+content;
            Timer timer = new Timer();
            Calendar c = Calendar.getInstance();
            int l = 5*60*1000;
            c.set(Calendar.MILLISECOND, l);//秒设置
            timer.schedule(new TimerTask() {
                public void run() {
                    try{
                        JMXmonitor(resMdlWare);
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
     * 修改
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit.do")
    public String edit(Long id, ModelMap map) {
        logger.info( "Enter edit.do ..." );
        ResMdlWare resMdlMare = resMdlWareService.getById(id);
        String psWord = resMdlMare.getPassWord();
        String orgID = resMdlMare.getOrgID();
        OrgOrganization org = organService.getOrgan(orgID);
        String orgName = org.getOrgShortName();
        
        map.put("orgName", orgName);
        map.put("psWord", psWord);
        map.put("action", "edit");
        map.put("resMdlMare", resMdlMare);
        map.put("id", id);
        return viewPath+"/edit";
    }
    
    /**
     * 修改保存
     * @param resMdlWare
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value="/editSave.do")
    public String editSave(ResMdlWare resMdlWare, Long id, ModelMap map){
        logger.info( "Enter editSave.do ..." );
        
        Integer monPort = resMdlWare.getMonPort();
        String svrIpAddr = resMdlWare.getSvrIpAddr();
        String svcUrl = "service:jmx:rmi:///jndi/rmi://"+svrIpAddr+":"+monPort+"/jmxrmi";
        resMdlWare.setSvcUrl(svcUrl);
        
        ResMdlWare editResMdlMare = resMdlWareService.getById(id);
        if( editResMdlMare != null ){
        	BeanUtils.copyProperties(resMdlWare, editResMdlMare);
        	resMdlWareService.save(editResMdlMare);
		}
		map.put("result", "success");
		map.put("resMdlMare", editResMdlMare);
        
        return viewPath+"/edit";
    }
    
    /**
     * 停止监测
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/shutdown.do")
    public String shutdown(String id, HttpServletResponse response) throws IOException {
        logger.info( "Enter shutdown.do ..." );
        ResMdlWare saveResMdlWare = new ResMdlWare();
        if(!StringUtil.isNullString(id)){
            ResMdlWare resMdlWare = resMdlWareService.getById(Long.parseLong(id));

            String devIpAddr = resMdlWare.getSvrIpAddr();

            int content = resMdlWare.getMonPort();

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
            
            BeanUtils.copyProperties(resMdlWare, saveResMdlWare);
            saveResMdlWare.setMonStatus(0);

            resMdlWareService.save(saveResMdlWare);
        }

        response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
        return null;
    }
    
    /**
     * 实时监测和开启监测前检查服务器连接是否正确
     * @param id
     * @param svrIpAddr
     * @param monPort
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/checkMonitorConn.do")
    public String checkMonitorConn(String id,String svrIpAddr,String monPort, HttpServletResponse response) throws IOException {
        logger.info( "Enter checkMonitorConn.do ..." );
        String service = "service:jmx:rmi:///jndi/rmi://"+svrIpAddr+":"+monPort+"/jmxrmi";
        ResMdlWare entity = resMdlWareService.getById(Long.parseLong(id));
        String userName = entity.getUserName();
        String psWord = entity.getPassWord();
//        if(StringUtils.isNotBlank(psWord)){
//            psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
//        }
        String jsonStr = checkJMXConn(service,svrIpAddr,monPort,userName,psWord);

        if(StringUtils.isNotBlank(jsonStr)){

            if(jsonStr.equals("连接失败")){
                
                String error = "{\"result\":\"false\", \"message\":\"服务器连接失败\"}";
            	response.getWriter().print(error);
            }else{
            	response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
            }

        }else{
            String error = "{\"result\":\"false\", \"message\":\"用户名或密码错误\"}";
            response.getWriter().print(error);
        }
        return null;
    }
    
    public String checkJMXConn(String service,String devIpAddr,String content, String userName, String psWord) {
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
            //e.printStackTrace();
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
     * 开启监测
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/startTomcatMonitor.do")
    public String startTomcatMonitor(String id, HttpServletResponse response) throws IOException {
        logger.info( "Enter startTomcatMonitor.do ..." );

        if(!StringUtil.isNullString(id)){
//            MiddlewareMonitor middlewareMonitor = middlewareMonitorService.getById(id);
            ResMdlWare entity = resMdlWareService.getById(Long.parseLong(id));
            
            String jsonStr = JMXmonitor(entity);

            if(StringUtils.isNotBlank(jsonStr)){
                new TestListener().Auto(entity);
            }

        }
        response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
        return null;
    }
    
    /**
     * 审核
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/examAppService.do")
	public String examAppService(String id, HttpServletResponse response) throws IOException {
		logger.info( "Enter examAppService.do ..." );
		
		if(!StringUtil.isNullString(id)){
			ResMdlWare resMdlWare =resMdlWareService.getById(Long.parseLong(id));
			resMdlWare.setAuditStatus(1);
			resMdlWareService.save(resMdlWare);
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;
	}

    /**
     * 删除
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
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

            resMdlWareService.batchDelete(id,infoIds,heapIds,threadIds,cpuIds);

        }
        response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
        return null;
    }
    
    /**
     * 历史趋势图
     * @param map
     * @param id
     * @param svrIpAddr
     * @param monPort
     * @param prodVersion
     * @param historyLineTime
     * @return
     */
    @RequestMapping(value="/historyLineview.do")
    public String historyLineview(ModelMap map,String id,String svrIpAddr,String monPort,String prodVersion,String historyLineTime){
        logger.info( "Enter historyLineview.do ..." );

        String service = "service:jmx:rmi:///jndi/rmi://"+svrIpAddr+":"+monPort+"/jmxrmi";

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

        List<MiddlewareMonitorInfo> list =  middlewareMonitorInfoService.getByIpAndContentAndMonitorTime(svrIpAddr,monPort,prodVersion,monitorTime);

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
        map.put("devIpAddr",svrIpAddr);
        map.put("content",monPort);
        map.put("tomcatVersion",prodVersion);
        map.put("service",service);
        map.put("historyLineTime",historyLineTime);

        return viewPath+"/historyline";
    }
    
    
    @RequestMapping(value = "/lineTab.do")
    public String mainTab(ModelMap map,String id,String svrIpAddr,String monPort) {
        logger.info( "Enter lineTab.do ..." );
//        String service = "service:jmx:rmi:///jndi/rmi://"+devIpAddr+":"+content+"/jmxrmi";
        map.put("id",id);
        map.put("devIpAddr",svrIpAddr);
        map.put("content",monPort);
//        map.put("service",service);
        return viewPath+"/lineTab";
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

        ResMdlWare entity = resMdlWareService.getById(Long.parseLong(id));
        if(entity!=null){
            map.put("userName",entity.getUserName());
            map.put("psWord",entity.getPassWord());
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
//        MiddlewareMonitor entity = middlewareMonitorService.getById(id);
        ResMdlWare entity = resMdlWareService.getById(Long.parseLong(id));
        if(entity!=null){
            map.put("userName",entity.getUserName());
            map.put("psWord",entity.getPassWord());
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

//        MiddlewareMonitor entity = middlewareMonitorService.getById(id);
        ResMdlWare entity = resMdlWareService.getById(Long.parseLong(id));
        if(entity!=null){
            map.put("userName",entity.getUserName());
            map.put("psWord",entity.getPassWord());
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

//        MiddlewareMonitor entity = middlewareMonitorService.getById(id);
        ResMdlWare entity = resMdlWareService.getById(Long.parseLong(id));
        if(entity!=null){
            map.put("userName",entity.getUserName());
            map.put("psWord",entity.getPassWord());
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
//            // 用户名密码，在jmxremote.password文件中的密码
//            if(StringUtils.isNotBlank(psWord)){
//                psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
//            }
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
//            // 用户名密码，在jmxremote.password文件中的密码
//            if(StringUtils.isNotBlank(psWord)){
//                psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
//            }
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
//            // 用户名密码，在jmxremote.password文件中的密码
//            if(StringUtils.isNotBlank(psWord)){
//                psWord = Cryptos.desDecryptFromBase64(psWord, Global.AES_KEY);
//            }
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

}
