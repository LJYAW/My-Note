/**
 * 
 */
package com.sino.fas.res.host.web;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.fas.res.host.entity.ResAppService;
import com.sino.fas.res.host.service.ResAppServiceService;
import com.sino.res.biz.entity.ResAppServiceMap;
import com.sino.res.biz.service.ResAppServiceMapService;
import com.sino.snmp.utils.JdbcConnection;

/**
 * @author Mr.LP
 * @date 2019-10-31下午1:55:56
 * @className ResAppServiceAction
 *
 * @Description TODO
 *
 */

@Controller
@RequestMapping(value = "/fas/res/host/appService")
public class ResAppServiceAction {
	
	private static Logger logger = LoggerFactory.getLogger(ResAppServiceAction.class);
	private String viewPath = "/fas/res/appService";
	
	@Autowired
	private ResAppServiceService resAppServiceService;
	
	@Autowired
	private ResAppServiceMapService resAppServiceMapService;
	
	static long t1 = 0;
	
	public static List<ResAppService> checkPortAppList = new ArrayList<ResAppService>();
	
	
	
	public List<ResAppService> getCheckPortAppList() {
		return checkPortAppList;
	}

	public void setCheckPortAppList(List<ResAppService> checkPortAppList) {
		this.checkPortAppList = checkPortAppList;
	}

	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		
		logger.info( "Enter main.do ..." );
		List<ResAppService> hostsList=resAppServiceService.getAll();
		String jsonStr = resAppServiceService.getJsonListStr(hostsList);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}
	
	@RequestMapping(value = "/add.do")
	public String add(ModelMap map) {
		logger.info( "Enter add.do ..." );
		ResAppService resAppService=new ResAppService();
		String action = "add";
		map.put("resAppService", resAppService);
		map.put("action", action);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map){
		logger.info( "Enter edit.do ..." );
		ResAppService resAppService = resAppServiceService.getById(Long.parseLong(id));
		String action = "edit";
        map.put("resAppService", resAppService);
        map.put("id", id);
        map.put("action", action);
        return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/appServiceSave.do")
    public String appServiceSave(String action,String id, ResAppService resAppService, ModelMap map) throws JsonParseException, JsonMappingException, IOException {
        logger.info( "Enter appServiceSave.do ..." );
        
        boolean validate = true;
		SysUser curUser = SystemUtils.getLoginUser();
		if( curUser == null ){
			validate = false;
			map.put("message", "系统登录超时！");			
		}
		
		if( validate ){
			//IpHost ipHostEdit=null;
			ResAppService saveAppService = new ResAppService();
			if( "add".equals(action)){
				resAppService.setOrgID("1");
				resAppService.setResTypeCode(1);
				resAppService.setResTypeName("服务器");
				resAppService.setResClassCode(26);
				resAppService.setResClassName("应用服务");
				resAppService.setAppSvcId(JdbcConnection.getInstance().getDeviceResourceUniqId());
				resAppService.setSvcStatus(1);
				resAppService.setAdminStatus(0);
				resAppServiceService.save(resAppService);
				map.put("result", "success");
	            map.put("resAppService", resAppService);
			} else{
				saveAppService = resAppServiceService.getById(Long.parseLong(id));
		        if(id!=null&&resAppService!=null){
		        	
		            BeanUtils.copyProperties(resAppService, saveAppService);

//		            saveMiddlewareMonitor = middlewareMonitor;
		            resAppServiceService.save(saveAppService);
		            map.put("result", "success");
		            map.put("resAppService", saveAppService);

		        }else{
		            map.put("result", "error");
		            map.put("message", "编辑失败！");

		        }
				
			}
		} 
		
        return viewPath+"/edit";
    }
	
	@RequestMapping(value = "/delete.do")
	public String delete(String ids, HttpServletResponse response) throws IOException {
		logger.info( "Enter delete.do ..." );
		
		if(!StringUtil.isNullString(ids)){
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				resAppServiceService.delete(Long.parseLong(id[i]));
			}
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		}
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
			ResAppService appService = resAppServiceService.getById(Long.parseLong(id));
			appService.setAdminStatus(1);
			resAppServiceService.save(appService);
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;
	}
	
	@RequestMapping(value = "/view.do")
	public String view(String id, ModelMap map) throws IOException{     
		logger.info( "Enter view.do ..." );
		
		if(!StringUtil.isNullString(id)){
			ResAppService resAppService =resAppServiceService.getById(Long.parseLong(id));
			map.put("resAppService", resAppService);
		}
		return viewPath+"/view";
	}
	
	/**
	 * 服务端口检查时确定所选的所有端口号是否来自同一IP
	 * @param ids
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkSvcPortByIp.do")
	public String checkSvcPortByIp(String ids, HttpServletResponse response) throws IOException {
		logger.info( "Enter checkSvcPortByIp.do ..." );
		
		HashSet<String> set = new HashSet<String>(); 
		if(!StringUtil.isNullString(ids)){
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				ResAppService resAppService = resAppServiceService.getById(Long.parseLong(id[i]));
				String ip = resAppService.getHostIp();
				set.add(ip);
			}
			if(set.size()==1){
				response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
			}else{
				response.getWriter().print(JsonUtils.JSON_RESULT_FALSE);
			}
			
		}
	    return null;
	}
	
	/**
	 * 服务端口检查
	 * @param ids
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/refreshPortStatus.do")
	public String refreshPortStatus(String ids, HttpServletResponse response) throws IOException {
		logger.info( "Enter refreshPortStatus.do ..." );
		
		checkPortAppList = new ArrayList<ResAppService>();
		
		String ip = null;
		String message = "";
		if(!StringUtil.isNullString(ids)){
			List<Integer> list = new ArrayList<Integer>();
			List<Long> idlist = new ArrayList<Long>();
//			Set<Integer> portSet = new LinkedHashSet<Integer>();	//Set集合未用是应为，set中数据不能重复，可能会有相同的port来自不同的进程ID，所以采用list
			String[] id = ids.split(",");
			Integer[] ports = new Integer[id.length];
			for (int i = 0; i < id.length; i++) {
				ResAppService resAppService = resAppServiceService.getById(Long.parseLong(id[i]));
				int svcPort = resAppService.getSvcPort();
				ip = resAppService.getHostIp();
				ports[i] = svcPort;
				idlist.add(Long.parseLong(id[i]));
			}
			list.addAll(Arrays.asList(ports));
	        scanLargePorts(ip, list,idlist, 5, 400);
	        for (int i = 0; i < checkPortAppList.size(); i++) {// //int i = 0; i < idlist.size(); i++
	        	ResAppService appService = new ResAppService();
	        	appService = checkPortAppList.get(i);
	        	String status = "";
	        	if(appService.getSvcStatus()==1){
	        		status = "Active";
	        	}else if(appService.getSvcStatus()==0){
	        		status = "InActive";
	        	}
	        	message += "端口号"+appService.getSvcPort() + "服务状态：" + status+"，响应时长："+appService.getRespTime()+"毫秒;\n";
				
			}
	        message = "服务器"+ ip +"：\n" + message;
	        //System.out.println(message);
	        
	        JSONObject jo = new JSONObject();
	        jo.put("success", message);
	        response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
			//response.getWriter().print(JsonUtils.JSON_RESULT_MESSAGE,message);
//			String result = String.format(JsonUtils.JSON_RESULT_MESSAGE, message);
//			response.getWriter().print(result);
			
		}
	    return null;
	}
	
	/**
     * 多线程扫描目标主机指定Set端口集合的开放情况
     * 
     * @param ip
     *            待扫描IP
     * @param list
     *            待扫描的端口的List集合
     * @param idlist
     *            待扫描的端口的id集合
     * @param threadNumber
     *            线程数
     * @param timeout
     *            连接超时时间
     * */
	public void scanLargePorts(String ip, List<Integer> list,List<Long> idlist,
            int threadNumber, int timeout) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNumber; i++) {
        	t1 = System.currentTimeMillis();
            ScanMethod2 scanMethod2 = new ScanMethod2(ip, list,idlist,
                    threadNumber, i, timeout);
            threadPool.execute(scanMethod2);
        }
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                System.out.println("扫描结束");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	
	/**	内部类
     * 扫描方式：针对一个待扫描的端口的Set集合进行扫描
     * 
     * */
    class ScanMethod2 implements Runnable {
        private String ip; // 目标IP
        private List<Integer> list; // 待扫描的端口的List集合
        private List<Long> idlist; // 待扫描的端口的id集合
        private int threadNumber, serial, timeout; // 线程数，这是第几个线程，超时时间
        
        public ScanMethod2(String ip, List<Integer> list, List<Long> idlist,int threadNumber,
                int serial, int timeout) {
            this.ip = ip;
            this.list = list;
            this.idlist = idlist;
            this.threadNumber = threadNumber;
            this.serial = serial;
            this.timeout = timeout;
        }

        public void run() {
        	
            int port = 0;
            ResAppServiceAction app=new ResAppServiceAction();
            Integer[] ports = list.toArray(new Integer[list.size()]); // List转数组
            try {
                InetAddress address = InetAddress.getByName(ip);
                Socket socket;
                SocketAddress socketAddress;
                if (ports.length < 1)
                    return;
                for (port = 0 + serial; port <= ports.length - 1; port += threadNumber) {
                	
//                	ResAppService resAppService = resAppServiceService.getByHostIpAndSvcPort(ip,ports[port]);
                	ResAppService resAppService = resAppServiceService.getById(idlist.get(port));
                    socket = new Socket();
                    socketAddress = new InetSocketAddress(address, ports[port]);
                    try {
                        socket.connect(socketAddress, timeout);
                        socket.close();
                        long t2=System.currentTimeMillis();
                        
                        resAppService.setSvcStatus(1);
                        resAppService.setRespTime((int) (t2-t1));
                        
//                        System.out.println("port Listener " + ports[port]);
//                        System.out.println(ports[port] + "耗时："+ (t2-t1));
                    } catch (IOException e) {
                    	long t2=System.currentTimeMillis();
//                    	System.out.println(e);
                    	resAppService.setSvcStatus(0);
                        resAppService.setRespTime((int) (t2-t1));
//                        System.out.println("port close———— " + ports[port]);
//                        System.out.println(ports[port] + "耗时："+ (t2-t1));
                    }
                    app.getCheckPortAppList().add(resAppService);
                }
                
                resAppServiceService.batchSave(app.getCheckPortAppList());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            
        }

    }
    
	
//  业务系统添加页面使用 获取hostIp下拉框
 @RequestMapping(value = "/getHostIp.do")
	public String getHostIp(HttpServletResponse response) throws IOException{     
		logger.info( "Enter getSvrIpAddr.do ..." );
		List<ResAppService> list=resAppServiceService.getHostIp();
		
		JSONObject jo = new JSONObject();
		JSONArray hostList = JSONArray.fromObject(list);
		jo.put("list", hostList);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;         
	}
 
//根据hostIp获取对应数据  右边
@RequestMapping(value = "/getListByHostIp.do")
	public String getListByHostIp(String hostIp,Long resId,String row2Ids,HttpServletResponse response) throws IOException{     
		logger.info( "Enter getSvrIpAddr.do ..." );
		
		List<ResAppService> list= new ArrayList<ResAppService>();
		List<Long> svcIdList = new ArrayList<Long>();
		
		if(row2Ids!=null&&!row2Ids.equals("undefined")){
			String[] svcIds = row2Ids.split(",");
			for (int i = 0; i < svcIds.length; i++) {
				String svcId = svcIds[i];
				svcIdList.add(Long.parseLong(svcId));
			}
		}
		
		if(hostIp==null||hostIp.equals("undefined")){
			list=resAppServiceService.getDataList(resId);
		}else if (hostIp.equals("-1")){
			if(svcIdList.size()>0){
				list=resAppServiceService.getByResIdAndSvcId(resId,svcIdList);
			}else{
				list=resAppServiceService.getDataList(resId);
			}
		} else{
			if(svcIdList.size()>0){
				list=resAppServiceService.getByHostIpAndResIdAndSvcId(hostIp,resId,svcIdList);
			}else{
				list=resAppServiceService.getListByHostIp(hostIp,resId);
			}
		}
		
		String jsonStr = resAppServiceService.getJsonListStr(list);
		response.getWriter().print(jsonStr);
	    return null;         
	}

//获取已关联的数据 左边
@RequestMapping(value = "/getListByAppSvcId.do")
public String getListByAppSvcId(String resId,HttpServletResponse response) throws IOException{     
	logger.info( "Enter getSvrIpAddr.do ..." );
	List<ResAppService> list= new ArrayList<ResAppService>();
	List<ResAppServiceMap> svcIdList= resAppServiceMapService.getByBizAppId(Long.parseLong(resId));
	if(svcIdList.size()>0){
		for(int i=0;i<svcIdList.size();i++){
			long appId = svcIdList.get(i).getAppSvcId();
			//System.out.println(appId);
			ResAppService resAppService=resAppServiceService.getById(appId);
			list.add(resAppService);
		}
	}
	 
	String jsonStr = resAppServiceService.getJsonListStr(list);
	response.getWriter().print(jsonStr);
    return null;         
}



}
