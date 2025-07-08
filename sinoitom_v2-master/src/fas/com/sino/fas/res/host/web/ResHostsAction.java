/**
 * 
 */
package com.sino.fas.res.host.web;

import com.sino.base.common.util.*;
import com.sino.base.system.entity.SysUser;
import com.sino.cmdb.indicator.cmdCheckItems.service.JschUtils;
import com.sino.fas.res.host.entity.*;
import com.sino.fas.res.host.service.*;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import com.sino.fas.res.net.service.NcmDevAccessUserService;
import com.sino.snmp.utils.JdbcConnection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import smartlink.utils.Util;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.util.StringUtils.split;

/**
 * @author Mr.LP
 * @date 2019-8-21下午2:45:30
 * @className ApmHostsAction
 *
 * @Description TODO
 *
 */

@Controller
@RequestMapping(value = "/fas/res/host/resHosts")
public class ResHostsAction {
	
	private static Logger logger = LoggerFactory.getLogger(ResHostsAction.class);
	//private String viewPath = "/fas/res/host/ipHost";
	private String viewPath = "/fas/res/compResource";
	
	@Autowired
	private ResHostsService resHostsService;
	
	@Autowired
	private ResHostDisksService resHostDisksService;
	
	@Autowired
	private ResHostInterfaceService resHostInterfaceService;
	
	@Autowired
	private ResHostFileSystemService resHostFileSystemService;
	
	@Autowired
	private ResHostCpuService resHostCpuService;
	
	@Autowired
	private NcmDevAccessUserService ncmDevAccessUserService;
	
	@Autowired
	private IpHostService ipHostService;
	
	@Autowired
	private ResServiceProcessService resServiceProcessService;
	
	@Autowired
	private ResAppServiceService resAppServiceService;
	
	@Autowired
	private NcmDevAccessUserService devAccessUserService;
	
	@Autowired
	private ParamPerformanceService paramPerformanceService;
	
	/**
	 * main函数测试
	 * @param args
	 */
	public static void main(String[] args) {
		
		DecimalFormat df = new DecimalFormat("0.0000");//格式化小数  
		long usedMemorySize = 45126206;
		long memorySize = 853678162;
		String cylinders = df.format((float)usedMemorySize/memorySize*100);//返回的是String类型 
		System.out.println(cylinders);
		
		try {
			  JschUtils jschUtils = null;
			  jschUtils = new JschUtils("192.168.99.160", "root", "Sino-BridgeACS",22);
			  System.out.println("111");
			  if(jschUtils!=null){//使用ssh连接
				  jschUtils.initShellChannel();
				  jschUtils.getPrompt();
				  
				  
				  //可用来扩展ＣＰＵ的信息（一个服务器有一个或多个CPU的情况可用）
				  String cpuCmd = "top -bn 1 -i";
//					获取命令结果
					String cpuCmdResult = jschUtils.sendCmdExpect(cpuCmd);
					
					//System.out.println(cpuCmdResult);
					
					String cpus[] = LinuxTargetUtil.getStrResult(cpuCmdResult);
					
					int num=0;
					//第一行为命令不进行解析
					if(cpus.length>1){
						ResHostInterface hostInterface = null;
						for (int j=1; j<cpus.length-1; j++) {
							
							//将所有一个或者多个空格转换成一个空格
				           	String newStr = cpus[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
				           	
				           	System.out.println(newStr);
				           	
//							if(newStr.startsWith("processor")){
//								hostInterface = new ResHostInterface();
//				           		//hostInterface.setHostId(host.getHostId());
//								num++;
//								
//								String cpuWidthCmd = "getconf LONG_BIT";	//获取CPU位数
//						        String cpuWidthCmdResult = null;
//								try {
//									cpuWidthCmdResult = jschUtils.sendCmd(cpuWidthCmd);
//								} catch (IOException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//						        
//						        String cpuWidth[] = getStrResult(cpuWidthCmdResult);
//						      //第一行为命令不进行解析。
//						        if(cpuWidth.length>=2){
//						        	//hostCpu.setCpuWidth(Integer.parseInt(cpuWidth[1]));
//						        	System.out.println("位数："+cpuWidth[1]);
//						        }
//								
//							}
//							if(newStr.startsWith("core id")){	//当前物理核在其所处CPU中的编号
//								String coreId = split(newStr,": ")[1];
//								System.out.println("编号："+"cpu_"+coreId);
//							}
//							if(newStr.contains("vendor_id")){	//厂商
//		                    	String cpuVendor = split(newStr,": ")[1];
//		                    	System.out.println("厂商："+cpuVendor);
//		                    }
//		                    if(newStr.contains("model name")){	//型号
//		                    	String cpuModel = split(newStr,": ")[1];
//		                    	System.out.println("型号："+cpuModel);
//		                    }
//		                    if(newStr.contains("cpu cores")){	//内核数量
//		                    	String cpuCoreQty = split(newStr,": ")[1];
//		                    	System.out.println("内核："+cpuCoreQty);
//		                    }
						}
						
					}
					
					//System.out.println(num);
					
					
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
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		
		logger.info( "Enter main.do ..." );
		List<ResHosts> hostsList=resHostsService.getAll();
		String jsonStr = resHostsService.getJsonListStr(hostsList);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}
	
	
	/**
	    * 服务器搜索添加按钮点击跳转页面
	    * @param map
	    * @return
	    */
	   @RequestMapping(value = "/searchAdd.do")
		public String searchAdd(ModelMap map) {
			logger.info( "Enter searchAdd.do ..." );
			ResHosts resHosts=new ResHosts();
			map.put("ipHost", resHosts);
			return viewPath+"/searchadd";
		}
	   
	   
	   @RequestMapping(value = "/edit.do")
		public String edit(Long id,ModelMap map) {
			logger.info( "Enter edit.do ..." );
			ResHosts resHosts=resHostsService.getById(id);
			map.put("ipHost", resHosts);
			map.put("id", id);
			return viewPath+"/edit";
		}
	   
	   
	   @RequestMapping(value = "/editSave.do")
		public String editSave(Long id,ResHosts resHosts,ModelMap map) {
		   logger.info( "Enter editSave.do ..." );
		   ResHosts editHost = new ResHosts();
		   System.out.println(resHosts);
		   boolean validate = true;
			SysUser curUser = SystemUtils.getLoginUser();
			if( curUser == null ){
				validate = false;
				map.put("message", "系统登录超时！");			
			}
			
			if(validate){
		        editHost = resHostsService.getById(id);
		        if(id!=null&&editHost!=null){
		        	BeanUtils.copyProperties(resHosts, editHost);
		        	resHostsService.save(editHost);
		        	map.put("result", "success");
		        }else{
		            map.put("result", "error");
		            map.put("message", "编辑失败！");
		        }
			}
		   
		   return viewPath+"/edit";
	   }
	   
	   
	   /**
	    * 点击“访问验证”
	    * @param map
	    * @param devAcsUserId
	    * @param ipAddress
	    * @param response
	    * @return
	    * @throws IOException
	    */
	   @RequestMapping(value="/checkForm.do")
		public String checkForm(ModelMap map,String devAcsUserId,String ipAddress, String flagForm,HttpServletResponse response) throws IOException{
			logger.info("Enter checkForm.do..");
			JSONObject jo = new JSONObject();
			
			boolean validate = true;
			SysUser curUser = SystemUtils.getLoginUser();
			if( curUser == null ){
				validate = false;
				jo.put("message", "系统登录超时！");
			}
			
			if( validate ){
				
				String hostIp = ipAddress;
				
				NcmDevAccessUser user = ncmDevAccessUserService.getById(devAcsUserId);
				String userName = user.getUserName();
				String passWord = user.getPassWord();
				int port = user.getAccessPort();
				
				//IP巡检时用到的PING IP地址方法(此方法和isConnect方法有时候会出问题，两种留存作为备用)
				InetAddress address;
				address =InetAddress.getByName(hostIp);
//				   System.out.println("Name:" + address.getHostName());
//				   System.out.println("Addr:" + address.getHostAddress());
//				   System.out.println("Reach:" + address.isReachable(3000)); //是否能通信 返回true或false
//				   System.out.println(address.toString());
				
				  try {
					  if(address.isReachable(3000)||LinuxTargetUtil.isConnect(hostIp)){		//可以ping通
					
				   //if(isConnect(hostIp)){		//可以ping通
					   JschUtils jschUtils = null;
//						初始化连接设备的工具类
						try {
							jschUtils = new JschUtils(hostIp, userName, passWord, port);
							if(jschUtils!=null){
								
								//获取Linux版本
								String linuxOSType = LinuxTargetUtil.getLinuxOSType(jschUtils);
								
							    	if(linuxOSType.equals("Red Hat")){
							    		jo.put("linuxOSType", "Red Hat");
							    	}else if(linuxOSType.equals("CentOS")){
							    		jo.put("linuxOSType", "CentOS");
							    	}else if(linuxOSType.equals("SUSE")){
							    		jo.put("linuxOSType", "SUSE");
							    	}else if(linuxOSType.equals("Ubuntu")){
							    		jo.put("linuxOSType", "Ubuntu");
							    	}	
							    	
							    
								if(flagForm.equals("subForm")){
									ResHosts hosts = new ResHosts();
									hosts = resHostsService.getByIpAddress(ipAddress);
									if(hosts!=null){
										jo.put("error", "此设备已添加计算资源，请更换IP重新添加！");
									}else{
										jo.put("success", "服务器登录成功，通过验证！");
									}
								}else{
									jo.put("success", "服务器登录成功，通过验证！");
								}
							}
							
						} catch (Exception e) {
			                jo.put("error", "服务器登录失败！\n"+"请检查所选用户凭证信息与所填写IP地址是否匹配！");
							//e.printStackTrace();
						}
						
				   }else{		//ping不通
					   JschUtils jschUtils = null;
//						初始化连接设备的工具类
						try {
							jschUtils = new JschUtils(hostIp, userName, passWord, port);
							if(jschUtils!=null){
								//获取Linux版本
								String linuxOSType = LinuxTargetUtil.getLinuxOSType(jschUtils);
								
							    	if(linuxOSType.equals("Red Hat")){
							    		jo.put("linuxOSType", "Red Hat");
							    	}else if(linuxOSType.equals("CentOS")){
							    		jo.put("linuxOSType", "CentOS");
							    	}else if(linuxOSType.equals("SUSE")){
							    		jo.put("linuxOSType", "SUSE");
							    	}else if(linuxOSType.equals("Ubuntu")){
							    		jo.put("linuxOSType", "Ubuntu");
							    	}
							    	
							    	if(flagForm.equals("subForm")){
										ResHosts hosts = new ResHosts();
										hosts = resHostsService.getByIpAddress(ipAddress);
										if(hosts!=null){
											jo.put("error", "此设备已添加计算资源，请更换IP重新添加！");
										}else{
											jo.put("success", "服务器登录成功，通过验证！\n"+"但是服务器IP地址不能Ping通，IP地址可能有禁止Ping的安全策略！");
										}
									}else{
										jo.put("success", "服务器登录成功，通过验证！\n"+"但是服务器IP地址不能Ping通，IP地址可能有禁止Ping的安全策略！");
									}
							    	
							}
							
						} catch (Exception e) {
			                jo.put("error", "服务器登录失败！\n"+"服务器IP地址不能Ping通，请检查IP地址是否正确或者所选用户凭证信息与所填写IP地址是否匹配！");
							//e.printStackTrace();
						}
						
	                   //jo.put("error", "服务器IP地址不能Ping通，请检查IP地址是否正确或是否有禁止Ping的安全策略！");
				   }
				  } catch (Exception e) {
				   e.printStackTrace();
				  }
				
			}
			
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null; 
		}
	   

	   /**
	    * 点击“搜索添加”按钮	    
	    * @param action
	    * @param resHosts
	    * @param osClassName
	    * @param map
	    * @return
	    */
	   @RequestMapping(value = "/searchAddsave.do")
		public String searchAddsave(String action,String username,String password, String orgName,ResHosts resHosts, String homedHostIp,String typeFlag,String osClassName, ModelMap map) {
		   logger.info( "Enter searchAddsave.do ..." );
		   
		   if(action.equals("addForm")){
			   NcmDevAccessUser devAccess = new NcmDevAccessUser();
			   SysUser curUser = SystemUtils.getLoginUser();
			   String operator = curUser.getLoginName();
			   devAccess.setOrgId(resHosts.getOrgID());
			   devAccess.setAccessTool(resHosts.getAccessmode());
			   devAccess.setAccessPort(resHosts.getAccessPort());
			   devAccess.setUserName(username);
			   devAccess.setPassWord(password);
			   devAccess.setAcsUserName(orgName+"-"+resHosts.getIpAddress()+"-"+resHosts.getAccessmode()+"-"+"普通用户");
				Date now = new Date();
				devAccess.setModifyTime(now);
			   devAccess.setCreator(operator);
			   devAccess.setCreateTime(now);
			   devAccess.setStatus(1);
			   devAccess.setUserType(0);
			   devAccessUserService.addDevAccess(devAccess);	
			   map.put("result", "searchSuccess");
		   }else{
			   ResHosts hosts = new ResHosts();
			   ResHosts host = new ResHosts();
			   if(typeFlag.equals("非服务器")){
				   homedHostIp = "";
				   resHosts.setHostType(1);
				   resHosts.setHomedHostId(null);
				   resHosts.setHomedHostIp("");
			   }else if(typeFlag.equals("非宿主机")){
				   resHosts.setHomedHostId(null);
				   resHosts.setHomedHostIp("");
			   }
			   String hostIp = resHosts.getIpAddress();
				
				NcmDevAccessUser user = ncmDevAccessUserService.getById(resHosts.getDevAcsUserId());
				String userName = user.getUserName();
				String passWord = user.getPassWord();
				int port = user.getAccessPort();
				
			   JschUtils jschUtils = null;
			   try {
				   jschUtils = new JschUtils(hostIp, userName, passWord,port);
				   
				   jschUtils.initShellChannel();
			        jschUtils.getPrompt();
					
			        hosts = LinuxTargetUtil.getCmdResult(jschUtils,resHosts);		//获取命令结果解析并存储到实体类中
					if(hosts!=null){
						
						hosts.setIpLong(Util.ip2long(hostIp));
						resHosts.setOsClass(osClassName);
				        resHosts.setOsName(osClassName);
				        
				        ResHosts resHost = resHostsService.getByIpAddress(hostIp);
						IpHost ipHost = ipHostService.getIpHostByIpAddr(hostIp);
						if(resHost!=null){
							BeanUtils.copyProperties(hosts, resHost);
							host = resHost;
							
							SysUser curUser = SystemUtils.getLoginUser();
							host.setModifier(curUser.getUserName());
					        Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
					        host.setModifyTime(now);
							
							List<ResHostFileSystem> fileList = resHostFileSystemService.getByHostId(host.getHostId());
							resHostFileSystemService.deleteList(fileList);
							
							List<ResHostInterface> interfaceList = resHostInterfaceService.getByHostId(host.getHostId());
							resHostInterfaceService.deleteList(interfaceList);
							
							List<ResHostDisks> hostDiskList = resHostDisksService.getByHostId(host.getHostId());
							resHostDisksService.deleteList(hostDiskList);
						}else{
							
							 SysUser curUser = SystemUtils.getLoginUser();
						     resHosts.setCreator(curUser.getUserName());
						     Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
						     resHosts.setCreateTime(now);
						     
							if(ipHost!=null){
								hosts.setHostId(ipHost.getIpHostId());
							}else{
								hosts.setHostId(JdbcConnection.getInstance().getDeviceResourceUniqId());
							}
							host = hosts;
						}
						
						
						List<ResHostCpu> cpulist = LinuxTargetUtil.getCpus(jschUtils,host);
						List<ResHostFileSystem> fileSyslist = LinuxTargetUtil.getfileSys(jschUtils,host);
						List<ResHostInterface> infoList = LinuxTargetUtil.getInterfaces(jschUtils,host);
						List<ResHostDisks> disksList = null;
						if(host.getOsType().equals("SUSE")){		//(操作系统类型为：SUSE)
							disksList = LinuxTargetUtil.getSUSEDisks(jschUtils,host);
						}else{		//(操作系统类型为：CentOS，redhat)
							disksList = LinuxTargetUtil.getDisks(jschUtils,host);
						}
						host.setOsFeature("default");
				        resHostsService.batchSave(host, cpulist,fileSyslist, infoList, disksList);
						//resHostsService.save(hosts);
						map.put("result", "searchSuccess");
					}
			   } catch (Exception e) {
					e.printStackTrace();
				}
		   }
		   
		   map.put("action", action);
		   return viewPath+"/searchadd";
	   }
	   
	   
	   /**
	    * 性能指标
	    * @param id
	    * @param map
	    * @return
	    * @throws IOException
	    */
	   @RequestMapping(value = "/getPerformance.do")
		public String getPerformance(String id, ModelMap map) throws IOException{     
			logger.info( "Enter getPerformance.do ..." );
			
			ParamPerformance paramPerformance = new ParamPerformance();
			List<ParamPerformance> plist = new ArrayList<ParamPerformance>();
			
			if(!StringUtil.isNullString(id)){
				ResHosts resHosts = resHostsService.getById(Long.parseLong(id));
				String hostIp = resHosts.getIpAddress();
				
				NcmDevAccessUser user = ncmDevAccessUserService.getById(resHosts.getDevAcsUserId());
				String userName = user.getUserName();
				String passWord = user.getPassWord();
				int port = user.getAccessPort();
				
			   JschUtils jschUtils = null;
			   try {
				   jschUtils = new JschUtils(hostIp, userName, passWord,port);
				   
				   jschUtils.initShellChannel();
			       jschUtils.getPrompt();
			       
			       paramPerformance = LinuxTargetUtil.getPerformance(jschUtils);
			       plist.add(paramPerformance);
			       String plistJson = paramPerformanceService.getJsonListStr(plist);
			       
			       List<ResHostFileSystem> fileSysList =  LinuxTargetUtil.getfileSys(jschUtils,resHosts);
			       String fileListJson = resHostFileSystemService.getJsonListStr(fileSysList);
			       
			       map.put("plistJson", plistJson);
			       map.put("fileListJson", fileListJson);
			        
			   }catch (Exception e) {
				// TODO: handle exception
				   e.printStackTrace();
			   }
			}
			return viewPath+"/performance";
		}
	   
	   
	   /**
	    * 删除(级联删除)
	    * @param id
	    * @param response
	    * @return
	    * @throws IOException
	    */
	   @RequestMapping(value = "/delete.do")
		public String delete(String id, HttpServletResponse response) throws IOException {
			logger.info( "Enter delete.do ..." );
			
			if(!StringUtil.isNullString(id)){
				
		//以下为子表的级联删除操作，注销是因为直接在表中添加了外键级联（在数据库表中删除主表数据后会直接自动删除相关子表数据），如果不注销会报错
				
//				List<ResHostCpu> hostCpulist = new ArrayList<ResHostCpu>();
//				hostCpulist = resHostCpuService.getByHostId(Long.parseLong(id));
//				
//				List<ResHostDisks> hostDisklist = new ArrayList<ResHostDisks>();
//				hostDisklist = resHostDisksService.getByHostId(Long.parseLong(id));
//				
//				List<ResHostFileSystem> hostFilelist = new ArrayList<ResHostFileSystem>();
//				hostFilelist = resHostFileSystemService.getByHostId(Long.parseLong(id));
//				
//				List<ResHostInterface> hostInfolist = new ArrayList<ResHostInterface>();
//				hostInfolist = resHostInterfaceService.getByHostId(Long.parseLong(id));
//				
//				resHostsService.batchDelete(Long.parseLong(id),hostCpulist,hostDisklist,hostFilelist,hostInfolist);
				resHostsService.delete(Long.parseLong(id));
				List<ResServiceProcess> processList = new ArrayList<ResServiceProcess>();
				processList = resServiceProcessService.getByHostId(Long.parseLong(id));
				resServiceProcessService.batchDelete(processList);
				List<ResAppService> appList = new ArrayList<ResAppService>();
				appList = resAppServiceService.getByHostId(Long.parseLong(id));
				resAppServiceService.batchDelete(appList);
				response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
			}
		    return null;
		}
	   
	   
	   /**
	    * 详情
	    * @param id
	    * @param map
	    * @return
	    * @throws IOException
	    */
	   @RequestMapping(value = "/view.do")
		public String view(String id, ModelMap map) throws IOException{     
			logger.info( "Enter view.do ..." );
			
			ResHosts resHosts =resHostsService.getById(Long.parseLong(id));
			
			List<ResHostCpu> hostCpulist = new ArrayList<ResHostCpu>();
			hostCpulist = resHostCpuService.getByHostId(Long.parseLong(id));
			String cpuListJson = resHostCpuService.getJsonListStr(hostCpulist);
			
			List<ResHostDisks> hostDisklist = new ArrayList<ResHostDisks>();
			hostDisklist = resHostDisksService.getByHostId(Long.parseLong(id));
			String diskListJson = resHostDisksService.getJsonListStr(hostDisklist);
			
			List<ResHostFileSystem> hostFilelist = new ArrayList<ResHostFileSystem>();
			hostFilelist = resHostFileSystemService.getByHostId(Long.parseLong(id));
			String fileListJson = resHostFileSystemService.getJsonListStr(hostFilelist);
			
			List<ResHostInterface> hostInfolist = new ArrayList<ResHostInterface>();
			hostInfolist = resHostInterfaceService.getByHostId(Long.parseLong(id));
			String infoListJson = resHostInterfaceService.getJsonListStr(hostInfolist);
			
			map.put("cpuListJson", cpuListJson);
			map.put("diskListJson", diskListJson);
			map.put("fileListJson", fileListJson);
			map.put("infoListJson", infoListJson);
			map.put("resHosts", resHosts);
			return viewPath+"/view";
		}
	   
	   
	   /**
	    * 获取服务进程————之前先进行检查服务器是否开启以及用户名密码是否更改
	    * @param map
	    * @param devAcsUserId
	    * @param ipAddress
	    * @param response
	    * @return
	    * @throws IOException
	    */
	   @RequestMapping(value="/checkIpAndPasWord.do")
		public String checkIpAndPasWord(ModelMap map,String devAcsUserId,String ipAddress,HttpServletResponse response) throws IOException{
			logger.info("Enter checkIpAndPasWord.do..");
			JSONObject jo = new JSONObject();
			
			boolean validate = true;
			SysUser curUser = SystemUtils.getLoginUser();
			if( curUser == null ){
				validate = false;
				jo.put("message", "系统登录超时！");
			}
			
			if( validate ){
				
				String hostIp = ipAddress;
				
				NcmDevAccessUser user = ncmDevAccessUserService.getById(devAcsUserId);
				String userName = user.getUserName();
				String passWord = user.getPassWord();
				int port = user.getAccessPort();
				
				//IP巡检时用到的PING IP地址方法(此方法和isConnect方法有时候会出问题，两种留存作为备用)
				InetAddress address;
				address =InetAddress.getByName(hostIp);
				
				  try {
					  if(address.isReachable(3000)||LinuxTargetUtil.isConnect(hostIp)){		//可以ping通
					
					   JschUtils jschUtils = null;
//						初始化连接设备的工具类
						try {
							jschUtils = new JschUtils(hostIp, userName, passWord, port);
							if(jschUtils!=null){
								
								jschUtils.initShellChannel();
						        jschUtils.getPrompt();
								
								String osRelaseCmd = "cat /etc/redhat-release";	//获取linux版本	(centOS可用SUSE不可用)
						        String osRelaseCmdResult = null;
								try {
									osRelaseCmdResult = jschUtils.sendCmd(osRelaseCmd);
									jo.put("success", "服务器登录成功，通过验证！");
								} catch (IOException e) {
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
				
			}
			
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null; 
		}
	   
	   
	   /**
	    * Host资源同步
	    * @param id
	    * @param response
	    * @return
	    * @throws IOException
	    */
	   @RequestMapping(value = "/hostResSync.do")
		public String hostResSync(String id, HttpServletResponse response) throws IOException {
			logger.info( "Enter hostResSync.do ..." );
			
			boolean validate = true;
			JSONObject jo = new JSONObject();
			SysUser curUser = SystemUtils.getLoginUser();
			if( curUser == null ){
				validate = false;
				jo.put("message", "系统登录超时,同步失败！");
				response.getWriter().print(jo.toString());
			}
			if( validate ){
				//新的
				ResHosts hosts = new ResHosts();
				
				if(!StringUtil.isNullString(id)){
					//原来的
					ResHosts resHosts = resHostsService.getById(Long.parseLong(id));
					String hostIp = resHosts.getIpAddress();
					
					NcmDevAccessUser user = ncmDevAccessUserService.getById(resHosts.getDevAcsUserId());
					String userName = user.getUserName();
					String passWord = user.getPassWord();
					int port = user.getAccessPort();
					
				   JschUtils jschUtils = null;
				   try {
					   jschUtils = new JschUtils(hostIp, userName, passWord,port);
					   
					   jschUtils.initShellChannel();
				        jschUtils.getPrompt();
						
				        hosts = LinuxTargetUtil.getCmdResult(jschUtils,resHosts);		//获取命令结果解析并存储到实体类中
						if(hosts!=null){
							BeanUtils.copyProperties(hosts, resHosts);
							
							//SysUser curUser = SystemUtils.getLoginUser();
							resHosts.setModifier(curUser.getUserName());
					        Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
					        resHosts.setModifyTime(now);
							resHosts.setOsClass("Linux");
					        
					        List<ResHostCpu> cpuList = resHostCpuService.getByHostId(resHosts.getHostId());
					        resHostCpuService.deleteList(cpuList);
							
							List<ResHostFileSystem> fileList = resHostFileSystemService.getByHostId(resHosts.getHostId());
							resHostFileSystemService.deleteList(fileList);
							
							List<ResHostInterface> interfaceList = resHostInterfaceService.getByHostId(resHosts.getHostId());
							resHostInterfaceService.deleteList(interfaceList);
							
							List<ResHostDisks> hostDiskList = resHostDisksService.getByHostId(resHosts.getHostId());
							resHostDisksService.deleteList(hostDiskList);
							
							List<ResHostCpu> hostCpulist = LinuxTargetUtil.getCpus(jschUtils,resHosts);
							List<ResHostFileSystem> fileSyslist = LinuxTargetUtil.getfileSys(jschUtils,resHosts);
							List<ResHostInterface> infoList = LinuxTargetUtil.getInterfaces(jschUtils,resHosts);
							List<ResHostDisks> disksList = null;
							if(resHosts.getOsType().equals("SUSE")){		//(操作系统类型为：SUSE)
								disksList = LinuxTargetUtil.getSUSEDisks(jschUtils,resHosts);
							}else{		//(操作系统类型为：CentOS，redhat)
								disksList = LinuxTargetUtil.getDisks(jschUtils,resHosts);
							}
//							List<ResHostDisks> disksList = getDisks(jschUtils,resHosts);
							
					        resHostsService.batchSave(resHosts, hostCpulist,fileSyslist, infoList, disksList);
					        response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
						}
				   } catch (Exception e) {
						//e.printStackTrace();
						jo.put("message", "请检查服务器是否开启，或账号密码是否有变动！");
						response.getWriter().print(jo.toString());
					}
					
					
				}
			}
			
		    return null;
		}
	   
	   
	   @RequestMapping(value = "/getHostByHostType.do")
		public String getHostByHostType(HttpServletResponse response) throws IOException{     
			logger.info( "Enter getHostByHostType.do ..." );
			List<ResHosts> list = resHostsService.getHostsByHostType();
			JSONObject jo = new JSONObject();
			JSONArray hostList = JSONArray.fromObject(list);
			jo.put("list", hostList);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null;         
		}
//	    业务系统添加页面使用
	   @RequestMapping(value = "/getSvrIpAddr.do")
		public String getSvrIpAddr(Long homedHostId,Integer hostType, HttpServletResponse response) throws IOException{     
			logger.info( "Enter getSvrIpAddr.do ..." );
			List<ResHosts> list=new ArrayList<ResHosts>();
			if(homedHostId!=null){
				 list = resHostsService.getSvrIpAddrByIdAndType(homedHostId,hostType);
			}else{
				 list = resHostsService.getSvrIpAddrByType(hostType);
			}
			
			JSONObject jo = new JSONObject();
			JSONArray hostList = JSONArray.fromObject(list);
			jo.put("list", hostList);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null;         
		}

}
