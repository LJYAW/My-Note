/**
 * 
 */
package com.sino.fas.res.host.web;

import com.sino.base.common.util.*;
import com.sino.base.system.entity.SysUser;
import com.sino.cmdb.indicator.cmdCheckItems.service.JschUtils;
import com.sino.fas.res.biz.entity.RfcServices;
import com.sino.fas.res.biz.service.RfcServicesService;
import com.sino.fas.res.host.entity.ParamResAppService;
import com.sino.fas.res.host.entity.ResAppService;
import com.sino.fas.res.host.entity.ResHosts;
import com.sino.fas.res.host.entity.ResServiceProcess;
import com.sino.fas.res.host.service.ParamResAppServiceService;
import com.sino.fas.res.host.service.ResAppServiceService;
import com.sino.fas.res.host.service.ResHostsService;
import com.sino.fas.res.host.service.ResServiceProcessService;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import com.sino.fas.res.net.service.NcmDevAccessUserService;
import com.sino.snmp.utils.JdbcConnection;
import net.sf.json.JSONObject;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.LP
 * @date 2019-10-17下午2:29:08
 * @className ResServiceProcessAction
 *
 * @Description TODO
 *
 */

@Controller
@RequestMapping(value = "/fas/res/host/resServiceProcess")
public class ResServiceProcessAction {
	
	private static Logger logger = LoggerFactory.getLogger(ResServiceProcessAction.class);
	private String viewPath = "/fas/res/compResource";
	private String viewPath2 = "/fas/res/serviceProcess";
	
	@Autowired
	private ResServiceProcessService resServiceProcessService;
	
	@Autowired
	private ResHostsService resHostsService;
	
	@Autowired
	private NcmDevAccessUserService ncmDevAccessUserService;
	
	@Autowired
	private ResAppServiceService resAppServiceService;
	
	@Autowired
	private ParamResAppServiceService paramResAppServiceService;
	
	@Autowired
	private RfcServicesService rfcServicesService;
	
	/**
	 * main函数测试
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			  JschUtils jschUtils = null;
			  jschUtils = new JschUtils("192.168.99.160", "root", "Sino-BridgeACS",22);
			  System.out.println("111");
			  if(jschUtils!=null){//使用ssh连接
				  jschUtils.initShellChannel();
				  jschUtils.getPrompt();
				  
				  
				  //可用来扩展ＣＰＵ的信息（一个服务器有一个或多个CPU的情况可用）
				  String cpuCmd = "ps -efww ";
//					获取命令结果
					String cpuCmdResult = jschUtils.sendCmd(cpuCmd);
					
					System.out.println("***"+cpuCmdResult);
					
					String cpus[] = getStrResult(cpuCmdResult);
					
					//第一行为命令不进行解析
					if(cpus.length>1){
						for (int j=2; j<cpus.length-1; j++) {
							
							//将所有一个或者多个空格转换成一个空格
				           	String newStr = cpus[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
				           	
				           	//System.out.println(newStr);
				           	
//				           	String[] s = newStr.split(" ");
//				           	for (int k = 0; k < s.length; k++) {
//				           		System.out.println(s[k]+"k");
//							}
				           	
						}
						
					}
					
					
					
//					if(cmdResult.contains("No such file or directory")||cmdResult.contains("command not found")){
//						System.out.println("执行"+checkCmd+"命令后，结果为空，请检查该命令是否正确！");
//					}
//					
////					如果结果是空字符串，那么就跳过，去执行下一个命令
//					if(StringUtils.isBlank(cmdResult)){
//						System.out.println("执行"+checkCmd+"命令后，结果为空，请检查该命令是否正确！");
//					}
					
				}else{
					System.out.println("222");
				}
		} catch (Exception e) {
			System.out.println("cuowu");
			e.printStackTrace();
			// TODO: handle exception
		}
		  
	}
	
	/**
	    * 将命令结果转换成String型
	    * @param cmdResult
	    * @return
	    */
	   public static String[] getStrResult(String cmdResult){
		 //将一个或者多个“\r”去掉
			cmdResult = cmdResult.replaceAll("\r+","");
	        //将一个或者多个“\n”转换成一个
			cmdResult = cmdResult.replaceAll("\n+","\n").trim();

	        //List<ParamItems> paramItemList = new ArrayList<ParamItems>();

	        String maipu[] = cmdResult.split("\n");
		   return maipu;
	   }
	   
	   
	   /**
	    * 获取服务进程
	    * @param id
	    * @param response
	    * @param map
	    * @return
	    * @throws IOException
	    */
	   @RequestMapping(value = "/getServiceProcess.do")
		public String getServiceProcess(String id,HttpServletResponse response,ModelMap map) throws IOException {
			logger.info( "Enter getServiceProcess.do ..." );
			
			boolean validate = true;
			JSONObject jo = new JSONObject();
			SysUser curUser = SystemUtils.getLoginUser();
			if( curUser == null ){
				validate = false;
				jo.put("message", "系统登录超时,同步失败！");
				response.getWriter().print(jo.toString());
			}
			if( validate ){
				if(!StringUtil.isNullString(id)){
					ResHosts resHosts =resHostsService.getById(Long.parseLong(id));
					String hostIp = resHosts.getIpAddress();
					int port = resHosts.getAccessPort();
					
					NcmDevAccessUser user = ncmDevAccessUserService.getById(resHosts.getDevAcsUserId());
					String userName = user.getUserName();
					String passWord = user.getPassWord();
					
					JschUtils jschUtils = null;
					List<ResServiceProcess> list = new ArrayList<ResServiceProcess>();
					try {
						   jschUtils = new JschUtils(hostIp, userName, passWord,port);
						   
						   jschUtils.initShellChannel();
					        jschUtils.getPrompt();
							
					        list = getCmdResult(jschUtils);		//获取命令结果解析并存储到实体类中
							if(list.size()>0){
								String jsonStr = resServiceProcessService.getJsonListStr(list);
								map.put("hostIp", hostIp);
								map.put("userName", userName);
								map.put("passWord", passWord);
								map.put("port", port);
								map.put("hostId", id);
								map.put("jsonListData", jsonStr);
						        //response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
							}
					   } catch (Exception e) {
							e.printStackTrace();
						}
					
				}
			}
			
			return viewPath+"/serviceProcess";
		}
	   
	   public List<ResServiceProcess> getCmdResult(JschUtils jschUtils){
		   List<ResServiceProcess> list = new ArrayList<ResServiceProcess>();
		   String cpuCmd = "ps -efww";
//			获取命令结果
			String cpuCmdResult = null;
			try {
				cpuCmdResult = jschUtils.sendCmd(cpuCmd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(cpuCmdResult);
			
			String str[] = getStrResult(cpuCmdResult);
			int num = 0;
			if(str.length>1){
				//第一行为命令不进行解析
				for (int j=2; j<str.length-1; j++) {
					ResServiceProcess entity = new ResServiceProcess();
					//将所有一个或者多个空格转换成一个空格
		           	String newStr = str[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
		           	
		           	//System.out.println(newStr);
		           	String[] s = newStr.split(" ");
		           	for (int k = 0; k < s.length; k++) {
		           		entity.setPid(Integer.parseInt(s[1]));
		           		entity.setPpid(Integer.parseInt(s[2]));
		           		
		           		int index = newStr.indexOf(s[7]);
		           		String startCmd = newStr.substring(index);
		           		
		           		entity.setStartCmd(startCmd);
		           		entity.setSvcProcId((long)num);
					}
		           	
		           	list.add(entity);
		           	num++;
				}
				
			}
			return list;
	   }
	   
	   
	   public List<ResAppService> getProcessCmdResult(JschUtils jschUtils,List<ResServiceProcess> serviceProcessList){
		   List<ResAppService> list = new ArrayList<ResAppService>();
		   String cpuCmd = null;
		   for (int i = 0; i < serviceProcessList.size(); i++) {
			   cpuCmd = "netstat -tunlp | grep " + serviceProcessList.get(i).getPid();
//				获取命令结果
				String cpuCmdResult = null;
				try {
					cpuCmdResult = jschUtils.sendCmd(cpuCmd);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//System.out.println(cpuCmdResult);
				
				String str[] = getStrResult(cpuCmdResult);
				//第一行为命令不进行解析
				if(str.length>1){
					for (int j=1; j<str.length-1; j++) {
						ResAppService entity = new ResAppService();
						entity.setPid(serviceProcessList.get(i).getPid());
						//将所有一个或者多个空格转换成一个空格
			           	String newStr = str[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
			           	
			           	//System.out.println(newStr);
			           	String[] s = newStr.split(" ");
			           	
			           	for (int k = 0; k < s.length; k++) {
			           		entity.setProtName(s[0]);
			           		int indexSvcPort = s[3].lastIndexOf(":");
			           		String svcPort = s[3].substring(indexSvcPort+1);
			           		entity.setSvcPort(Integer.parseInt(svcPort));
			           		List<RfcServices> rfcSvcList = rfcServicesService.getBySvcPort(Integer.parseInt(svcPort));
			           		if(rfcSvcList.size()>0){
			           			entity.setSvcName(rfcSvcList.get(0).getServiceName());
			           		}
			           		if(s[0].contains("tcp")){
//			           			int index = s[6].indexOf("/");
			           			int index = s[6].lastIndexOf("/");
			           			String command = s[6].substring(index+1);
			           			entity.setCommand(command);
			           			entity.setProtNo(6);
			           		}else{
			           			int index = s[5].lastIndexOf("/");
			           			String command = s[5].substring(index+1);
			           			entity.setCommand(command);
			           			entity.setProtNo(17);
			           		}
			           		entity.setSvcProcId(serviceProcessList.get(i).getSvcProcId());
						}
			           	
			           	list.add(entity);
					}
					
				}
		   }

			return list;
	   }
	   
	   /**
	    * 进程检测
	    * @param id
	    * @param response
	    * @return
	    * @throws IOException
	    */
	   @RequestMapping(value = "/checkProcStatus.do")
		public String checkProcStatus(String id, HttpServletResponse response) throws IOException {
			logger.info( "Enter checkProcStatus.do ..." );
			String message = "";
			if(!StringUtil.isNullString(id)){
				JSONObject jo = new JSONObject();
				
				ResServiceProcess resServiceProcess =resServiceProcessService.getById(Long.parseLong(id));
				String cmd = resServiceProcess.getStartCmd();
				Long hostId =  resServiceProcess.getHostId();
				String hostIp = resServiceProcess.getHostIp();
				Integer OldPid = resServiceProcess.getPid();
				String procName = resServiceProcess.getProcName();
				
				ResHosts resHosts = null;
				resHosts =resHostsService.getById(hostId);
				int port = resHosts.getAccessPort();

				NcmDevAccessUser user = ncmDevAccessUserService.getById(resHosts.getDevAcsUserId());
				String userName = user.getUserName();
				String passWord = user.getPassWord();

				//IP巡检时用到的PING IP地址方法(此方法和isConnect方法有时候会出问题，两种留存作为备用)
				InetAddress address;
				address =InetAddress.getByName(hostIp);

				  try {
					  if(address.isReachable(3000)){		//可以ping通

					   JschUtils jschUtils = null;
//						初始化连接设备的工具类
						try {
							jschUtils = new JschUtils(hostIp, userName, passWord, port);
							if(jschUtils!=null){

								jschUtils.initShellChannel();
						        jschUtils.getPrompt();

								try {
									String result = checkProcessResult(jschUtils,cmd);
									if(result.equals("失活")){
										//resServiceProcessService.delete(Long.parseLong(id));
										resServiceProcess.setRunStatus(0);
										resServiceProcessService.save(resServiceProcess);
										message = "服务器"+hostIp+"下的进程："+procName+"已失活！";
									}else{
										String[] str = result.split(",");
										Integer pid = Integer.parseInt(str[0]);
										Integer ppid = Integer.parseInt(str[1]);
										resServiceProcess.setPid(pid);
										resServiceProcess.setPpid(ppid);
										resServiceProcess.setRunStatus(1);
										resServiceProcessService.save(resServiceProcess);
										if(pid.equals(OldPid)){
											message = "服务器"+hostIp+"下的进程："+procName+"检测正常！";
										}else{
											message = "服务器"+hostIp+"下的进程："+procName+"PID由"+OldPid+"改变为"+pid;
										}
									}
									//JSONObject jo = new JSONObject();
							        jo.put("success", message);

								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									jo.put("error", "服务器登录失败！");
								}

							}

						} catch (Exception e) {
			                jo.put("error", "服务器登录失败！\n"+"请检查服务器用户名密码是否更改！");
							//e.printStackTrace();
						}

				   }else{		//ping不通
			               jo.put("error", "服务器登录失败！\n"+"服务器IP地址不能Ping通，请检查服务器是否开启！");
				   }
				  } catch (Exception e) {
				   e.printStackTrace();
				   jo.put("error", "服务器登录失败！");
				  }
				response.setContentType("text/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(jo.toString());
			}
			
		    return null;
		}
	   
	   
	   /**
		* 根据ip判断当前ip是否能够ping通
		* 
		* @param ip
		* @return
		*/
		public static boolean isConnect(String ip) {
			boolean bool = false;
			Runtime runtime = Runtime.getRuntime();
			try {
				Process process = runtime.exec("ping " + ip);
				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null) {
					sb.append(line);
					// 优化速度
					if (line.indexOf("请求超时") >= 0) {
					// System.out.println(ip + "网络断开，时间 " + new Date());
					return false;
					}
				}
				is.close();
				isr.close();
				br.close();
		
		
				if (null != sb && !sb.toString().equals("")) {
					if (sb.toString().indexOf("TTL") > 0) {
						// 网络畅通
						// System.out.println(ip + "网络正常 ，时间" + new Date());
						bool = true;
					} else {
						// 网络不畅通
						// System.out.println(ip + "网络断开，时间 " + new Date());
						bool = false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bool;
		}
	   
	   //进程检测——根据命令获取Pid
	   public String checkProcessResult(JschUtils jschUtils,String cmd){
		   String result = null;
		   String procCmd = null;
		   procCmd = "ps -ef |grep " + "'"+cmd+"'"  + " |grep -v 'grep'";
		   System.out.println(procCmd);
//				获取命令结果
				String cpuCmdResult = null;
				try {
					cpuCmdResult = jschUtils.sendCmd(procCmd);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//System.out.println(cpuCmdResult);
				
				String str[] = getStrResult(cpuCmdResult);
				//第一行为命令不进行解析
				if(str.length>2){
						//将所有一个或者多个空格转换成一个空格
			           	String newStr = str[1].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
			           	
			           	//System.out.println(newStr);
			           	String[] s = newStr.split(" ");
			           	
//			           	for (int k = 0; k < s.length; k++) {
			           		result = s[1]+","+s[2];
//						}
			           	
				}else{
					result = "失活";
				}

			return result;
	   }
	   
	   
	   @RequestMapping(value = "/getServeProcRows.do")
		public String getServeProcRows(String jsonStr, String hostIp, String userName, String passWord,String port,String hostId,HttpServletResponse response) throws IOException {
			logger.info( "Enter getServeProcRows.do ..." );
			ResServiceProcess entity = new ResServiceProcess();
				System.out.println(jsonStr);
				//jsonStr = "[" + jsonStr + "]";
				if(jsonStr.contains("@")){
					jsonStr = jsonStr.replaceAll("@", "%");
				}
				List<ResServiceProcess> list=JsonUtils.getDTOList(jsonStr, ResServiceProcess.class);
				SysUser curUser = SystemUtils.getLoginUser();
				ResHosts hosts = resHostsService.getById(Long.parseLong(hostId));
				String orgId = hosts.getOrgID();
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setHostId(Long.parseLong(hostId));
					list.get(i).setOrgID(orgId);
					list.get(i).setHostIp(hostIp);
					list.get(i).setFlag(1);
					list.get(i).setStatus(0);
					list.get(i).setRunStatus(0);
					list.get(i).setCreator(curUser.getUserName());
					Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
					list.get(i).setCreateTime(now);
					list.get(i).setResTypeCode(1);
					list.get(i).setResTypeName("服务器");
					list.get(i).setResClassCode(25);
					list.get(i).setResClassName("服务进程");
					list.get(i).setSvcProcId(JdbcConnection.getInstance().getDeviceResourceUniqId());
					list.get(i).setAdminStatus(0);
					list.get(i).setProcName("Proc_"+hostIp+"_"+list.get(i).getPid());
					list.get(i).setProcType("应用服务");
				}
				
				resServiceProcessService.batchSave(list);
				
				JschUtils jschUtils = null;
				try {
					jschUtils = new JschUtils(hostIp, userName, passWord,Integer.parseInt(port));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				   
				jschUtils.initShellChannel();
			    jschUtils.getPrompt();
			    
			    List<ResAppService> appServiceList = getProcessCmdResult(jschUtils,list);
			    
			    for (int i = 0; i < appServiceList.size(); i++) {
			    	appServiceList.get(i).setHostId(Long.parseLong(hostId));
			    	appServiceList.get(i).setOrgID(orgId);
			    	appServiceList.get(i).setHostIp(hostIp);
			    	appServiceList.get(i).setResTypeCode(1);
			    	appServiceList.get(i).setResTypeName("服务器");
			    	appServiceList.get(i).setResClassCode(26);
			    	appServiceList.get(i).setResClassName("应用服务");
			    	appServiceList.get(i).setAppSvcId(JdbcConnection.getInstance().getDeviceResourceUniqId());
			    	appServiceList.get(i).setSvcStatus(1);
			    	appServiceList.get(i).setAdminStatus(0);
			    	appServiceList.get(i).setAppSvcName("Svc_192.168.99.160_mysqld_TCP/13306"+hostIp+"_"+appServiceList.get(i).getCommand()+"/"+appServiceList.get(i).getSvcPort());
				}
			    
			    resAppServiceService.batchSave(appServiceList);
				
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
			return null;
	   }
	   
	   
	   @RequestMapping(value = "/getServAndProc.do")
	    public String getServAndProc(ModelMap map, String id) {
	        logger.info( "Enter getServAndProc.do ..." );
	        List<ResServiceProcess> list = resServiceProcessService.getByHostId(Long.parseLong(id));
	        String jsonStr = resServiceProcessService.getJsonListStr(list);
	        map.put("jsonListData", jsonStr);
	        map.put("id", id);

	        return viewPath+"/servAndProc";
	    }
	   
	   
	   @RequestMapping(value = "/getAppServiceData.do")
	    public String getAppServiceData(String id, HttpServletResponse response) throws IOException {
	        logger.info("Enter getAppServiceData.do ...");

	        List<ResAppService> list = new ArrayList<ResAppService>();
	        List<ParamResAppService> paramlist = new ArrayList<ParamResAppService>();
	        list = resAppServiceService.getBySvcProcId(Long.parseLong(id));
	        ResServiceProcess serviceProcess = new ResServiceProcess();
	        serviceProcess = resServiceProcessService.getById(Long.parseLong(id));
	        for (int i = 0; i < list.size(); i++) {
	        	ParamResAppService paramResApp = new ParamResAppService();
	        	paramResApp.setHostId(list.get(i).getHostId());
	        	paramResApp.setHostIp(list.get(i).getHostIp());
	        	paramResApp.setProtNo(list.get(i).getProtNo());
	        	paramResApp.setProtName(list.get(i).getProtName());
	        	paramResApp.setSvcPort(list.get(i).getSvcPort());
	        	paramResApp.setCommand(list.get(i).getCommand());
	        	paramResApp.setPid(serviceProcess.getPid());
	        	paramResApp.setId(list.get(i).getAppSvcId());
	        	paramResApp.setSvcClass(list.get(i).getSvcClass());
	        	paramResApp.setAppSvcName(list.get(i).getAppSvcName());
	        	paramlist.add(paramResApp);
			}
	        String jsonStr = paramResAppServiceService.getJsonListStr(paramlist);

	        response.getWriter().print(jsonStr);
	        return null;
	    }
	   
	   /**
	    * 维护进程信息
	    * @param id
	    * @param map
	    * @return
	    */
	   @RequestMapping(value = "/editServiceProcess.do")
		public String editServiceProcess(String id, ModelMap map){
			logger.info( "Enter editServiceProcess.do ..." );
			ResServiceProcess resServiceProcess = resServiceProcessService.getById(Long.parseLong(id));
			
	        map.put("resServiceProcess", resServiceProcess);
	        map.put("id", id);
	        return viewPath+"/editServiceProcess";
		}
	   
	   /**
	    * 进程信息维护保存
	    * @param id
	    * @param resServiceProcess
	    * @param map
	    * @return
	    * @throws JsonParseException
	    * @throws JsonMappingException
	    * @throws IOException
	    */
	   @RequestMapping(value = "/serviceProcessSave.do")
	    public String serviceProcessSave(String id, ResServiceProcess resServiceProcess, ModelMap map) throws JsonParseException, JsonMappingException, IOException {
	        logger.info( "Enter serviceProcessSave.do ..." );
	        ResServiceProcess saveServiceProcess = new ResServiceProcess();
	        saveServiceProcess = resServiceProcessService.getById(Long.parseLong(id));
	        if(id!=null&&resServiceProcess!=null){
	        	
	            BeanUtils.copyProperties(resServiceProcess, saveServiceProcess);

//	            saveMiddlewareMonitor = middlewareMonitor;
	            resServiceProcessService.save(saveServiceProcess);
	            map.put("result", "success");
	            map.put("resServiceProcess", saveServiceProcess);

	        }else{
	            map.put("result", "error");
	            map.put("message", "维护失败！");

	        }

	        return viewPath+"/editServiceProcess";
	    }
	   
	   /**
	    * 维护服务信息
	    * @param id
	    * @param map
	    * @return
	    */
	   @RequestMapping(value = "/editAppService.do")
		public String editAppService(String id, ModelMap map){
			logger.info( "Enter editAppService.do ..." );
			ResAppService resAppService = resAppServiceService.getById(Long.parseLong(id));
			
	        map.put("resAppService", resAppService);
	        map.put("id", id);
	        return viewPath+"/editAppService";
		}
	   
	   /**
	    * 服务信息维护保存
	    * @param id
	    * @param resAppService
	    * @param map
	    * @return
	    * @throws JsonParseException
	    * @throws JsonMappingException
	    * @throws IOException
	    */
	   @RequestMapping(value = "/appServiceSave.do")
	    public String appServiceSave(String id, ResAppService resAppService, ModelMap map) throws JsonParseException, JsonMappingException, IOException {
	        logger.info( "Enter appServiceSave.do ..." );
	        ResAppService saveAppService = new ResAppService();
	        saveAppService = resAppServiceService.getById(Long.parseLong(id));
	        if(id!=null&&resAppService!=null){
	        	
	            BeanUtils.copyProperties(resAppService, saveAppService);

//	            saveMiddlewareMonitor = middlewareMonitor;
	            resAppServiceService.save(saveAppService);
	            map.put("result", "success");
	            map.put("resAppService", saveAppService);

	        }else{
	            map.put("result", "error");
	            map.put("message", "维护失败！");

	        }

	        return viewPath+"/editAppService";
	    }
	   
	   
	   @RequestMapping(value = "/main.do")
		public String main(String menuId,ModelMap map) {
			
			logger.info( "Enter main.do ..." );
			List<ResServiceProcess> hostsList=resServiceProcessService.getAll();
			String jsonStr = resServiceProcessService.getJsonListStr(hostsList);
			map.put("jsonListData", jsonStr);
			WebFuncUtils.titleContent(menuId,map);
			return viewPath2 + "/main";
		}
	   
	   @RequestMapping(value = "/add.do")
		public String add(ModelMap map) {
			logger.info( "Enter add.do ..." );
			ResServiceProcess resServiceProcess=new ResServiceProcess();
			String action = "add";
			map.put("resServiceProcess", resServiceProcess);
			map.put("action", action);
			return viewPath2+"/add";
		}
		
		@RequestMapping(value = "/edit.do")
		public String edit(String id, ModelMap map){
			logger.info( "Enter edit.do ..." );
			ResServiceProcess resServiceProcess = resServiceProcessService.getById(Long.parseLong(id));
			String action = "edit";
			map.put("resServiceProcess", resServiceProcess);
	        map.put("id", id);
	        map.put("action", action);
	        return viewPath2+"/edit";
		}
		
		@RequestMapping(value = "/save.do")
	    public String save(String action,String id, ResServiceProcess resServiceProcess, ModelMap map) throws JsonParseException, JsonMappingException, IOException {
	        logger.info( "Enter save.do ..." );
	        
	        boolean validate = true;
			SysUser curUser = SystemUtils.getLoginUser();
			if( curUser == null ){
				validate = false;
				map.put("message", "系统登录超时！");			
			}
			
			if( validate ){
				ResServiceProcess saveServiceProcess = new ResServiceProcess();
				if( "add".equals(action)){
					resServiceProcess.setPid(0);
					resServiceProcess.setPpid(0);
					
					ResHosts resHosts =resHostsService.getByIpAddress(resServiceProcess.getHostIp());
					
					resServiceProcess.setHostId(resHosts.getHostId());
					
					Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
					resServiceProcess.setCreateTime(now);
					resServiceProcess.setCreator(curUser.getUserName());
					resServiceProcess.setResTypeCode(1);
					resServiceProcess.setResTypeName("服务器");
					resServiceProcess.setResClassCode(25);
					resServiceProcess.setResClassName("服务进程");
					resServiceProcess.setRunStatus(0);
					resServiceProcess.setAdminStatus(0);
					resServiceProcess.setSvcProcId(JdbcConnection.getInstance().getDeviceResourceUniqId());
					resServiceProcessService.save(resServiceProcess);
					map.put("result", "success");
		            map.put("resServiceProcess", resServiceProcess);
				} else{
					saveServiceProcess = resServiceProcessService.getById(Long.parseLong(id));
			        if(id!=null&&resServiceProcess!=null){
			        	
			        	BeanUtils.copyProperties(resServiceProcess, saveServiceProcess);

//			            saveMiddlewareMonitor = middlewareMonitor;
			            resServiceProcessService.save(saveServiceProcess);
			            map.put("result", "success");
			            map.put("resServiceProcess", saveServiceProcess);

			        }else{
			            map.put("result", "error");
			            map.put("message", "编辑失败！");

			        }
					
				}
			} 
			
	        return viewPath2+"/edit";
	    }
		
		@RequestMapping(value="/getHostIp.do")
		public String getHostIp(HttpServletResponse response) throws IOException{
			logger.info("Enter getHostIp.do...");
			List<ResHosts> resHosts=resHostsService.getAll();
			JSONObject jo=new JSONObject();
			jo.put("resHosts", resHosts);
		    response.getWriter().print(jo.toString());
		    return null;
		}
		
		@RequestMapping(value = "/delete.do")
		public String delete(String ids, HttpServletResponse response) throws IOException {
			logger.info( "Enter delete.do ..." );
			
			if(!StringUtil.isNullString(ids)){
				String[] id = ids.split(",");
				for (int i = 0; i < id.length; i++) {
					resServiceProcessService.delete(Long.parseLong(id[i]));
				}
				response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
			}
		    return null;
		}
		
		@RequestMapping(value = "/view.do")
		public String view(String id, ModelMap map) throws IOException{     
			logger.info( "Enter view.do ..." );
			
			if(!StringUtil.isNullString(id)){
				ResServiceProcess resServiceProcess =resServiceProcessService.getById(Long.parseLong(id));
				map.put("resServiceProcess", resServiceProcess);
			}
			return viewPath2+"/view";
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
				ResServiceProcess resServiceProcess =resServiceProcessService.getById(Long.parseLong(id));
				resServiceProcess.setAdminStatus(1);
				resServiceProcessService.save(resServiceProcess);
			}
			response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
		    return null;
		}

}
