/**
 * 
 */
package com.sino.fas.res.host.web;

import static org.springframework.util.StringUtils.split;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.cmdb.indicator.cmdCheckItems.service.JschUtils;
import com.sino.fas.res.host.entity.DcmHosts;
import com.sino.fas.res.host.entity.ResHostInterface;

/**
 * @author Mr.LP
 * @date 2019-8-21下午1:47:09
 * @className DcmHostsAction
 *
 * @Description TODO
 *
 */

@Controller
@RequestMapping(value = "/fas/res/host/dcmHosts")
public class DcmHostsAction {
	
	private static Logger logger = LoggerFactory.getLogger(DcmHostsAction.class);
	private String viewPath = "/fas/res/host/ipHost";
	
	
	//ＣＰＵ个数获取：指令：lscpu ————> CPU(s)：4
	
	public static void main(String[] args) {
		//IP巡检时用到的PING IP地址方法
		InetAddress address;
//		  try {
//		   address =InetAddress.getByName("192.168.99.160");
//		   System.out.println("Name:" + address.getHostName());
//		   System.out.println("Addr:" + address.getHostAddress());
//		   System.out.println("Reach:" + address.isReachable(3000)); //是否能通信 返回true或false
//		   System.out.println(address.toString());
//		  } catch (Exception e) {
//		   e.printStackTrace();
//		  }
		  
		  try {
			  JschUtils jschUtils = null;
//			  jschUtils = new JschUtils("10.1.2.198", "root", "Sino-BridgeBAS");
			  System.out.println("111");
			  if(jschUtils!=null){//使用ssh连接
				  jschUtils.initShellChannel();
				  jschUtils.getPrompt();
				  String cpuCmd = "cat /proc/cpuinfo";
//					获取命令结果
					String cpuCmdResult = jschUtils.sendCmd(cpuCmd);
					
					//System.out.println(cpuCmdResult);
					
					String cpus[] = getStrResult(cpuCmdResult);
					
					int num=0;
					//第一行为命令不进行解析
					if(cpus.length>1){
						ResHostInterface hostInterface = null;
						for (int j=1; j<cpus.length-1; j++) {
							
							//将所有一个或者多个空格转换成一个空格
				           	String newStr = cpus[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
							if(newStr.startsWith("processor")){
								hostInterface = new ResHostInterface();
				           		//hostInterface.setHostId(host.getHostId());
								num++;
							}
							if(newStr.startsWith("core id")){	//当前物理核在其所处CPU中的编号
								String coreId = split(newStr,": ")[1];
								System.out.println("cpu_"+coreId);
							}
							if(newStr.contains("vendor_id")){	//厂商
		                    	String cpuVendor = split(newStr,": ")[1];
		                    	System.out.println(cpuVendor);
		                    }
		                    if(newStr.contains("model name")){	//型号
		                    	String cpuModel = split(newStr,": ")[1];
		                    	System.out.println(cpuModel);
		                    }
		                    if(newStr.contains("cpu cores")){	//内核数量
		                    	String cpuCoreQty = split(newStr,": ")[1];
		                    	System.out.println(cpuCoreQty);
		                    }
						}
						
					}
					
					System.out.println(num);
					
					
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
	    * 服务器搜索添加按钮点击跳转页面
	    * @param map
	    * @return
	    */
	   @RequestMapping(value = "/searchAdd.do")
		public String searchAdd(ModelMap map) {
			logger.info( "Enter searchAdd.do ..." );
			map.put("action", "searchAdd");
			DcmHosts dcmHosts=new DcmHosts();
			map.put("dcmHosts", dcmHosts);
			return viewPath+"/searchadd";
		}
	   
	   @RequestMapping(value = "/searchAddsave.do")
		public String searchAddsave(String action, DcmHosts dcmHosts, ModelMap map) {
			logger.info( "Enter searchAddsave.do ..." );
			boolean validate = true;
			SysUser curUser = SystemUtils.getLoginUser();
			if( curUser == null ){
				validate = false;
				map.put("message", "系统登录超时！");			
			}
//			else if( !ipHostService.isIpHostUnique(ipHostId, ipHost.getMacAddr())&& !ipHost.getMacAddr().equals("null")){
//				validate = false;
//				map.put("message", "该IP设备已经存在！");
//			}
			else if( StringUtils.isBlank(dcmHosts.getIpAddress()) ){
				validate = false;
				map.put("message", "IP地址不能为空！");
			}
			
			if( validate ){
				String hostIp = dcmHosts.getIpAddress();
				String userName = dcmHosts.getIpAddress();
				String passWord = dcmHosts.getIpAddress();
				
				//IP巡检时用到的PING IP地址方法
				InetAddress address;
				  try {
				   address =InetAddress.getByName(hostIp);
				   System.out.println("Name:" + address.getHostName());
				   System.out.println("Addr:" + address.getHostAddress());
				   System.out.println("Reach:" + address.isReachable(3000)); //是否能通信 返回true或false
				   System.out.println(address.toString());
				   if(address.isReachable(3000)){		//可以ping通
					   JschUtils jschUtils = null;
//						初始化连接设备的工具类
						try {
//							jschUtils = new JschUtils(hostIp, userName, passWord);
							if(jschUtils!=null){
								map.put("result", "success");
				                map.put("message", "服务器登录成功！");
							}
							
						} catch (Exception e) {
							map.put("result", "error");
			                map.put("message", "服务器登录失败！请检查输入的账号、密码是否正确！");
							e.printStackTrace();
						}
						
				   }else{		//ping不通
					   map.put("result", "error");
	                   map.put("message", "服务器IP地址不能Ping通，请检查IP地址是否正确或是否有禁止Ping的安全策略!");
				   }
				  } catch (Exception e) {
				   e.printStackTrace();
				  }
				
//				onCheckSSH(hostIp,userName,passWord,map);
			} else{
				map.put("ipHost", dcmHosts);
				map.put("result", "error");			
			}
			
			map.put("action", action);
			return viewPath+"/searchadd";
		}
	   
	   
	   //服务器搜索添加——访问验证
	   @RequestMapping(value="/onCheckSSH.do")		//String accessTool:连接方式——暂时只有ssh连接，暂时未用
	   public String onCheckSSH(String hostIp, String userName, String passWord, ModelMap map){
	        logger.info( "Enter onCheckSSH.do ..." );
	        
	      //IP巡检时用到的PING IP地址方法
			InetAddress address;
			  try {
			   address =InetAddress.getByName(hostIp);
			   System.out.println("Name:" + address.getHostName());
			   System.out.println("Addr:" + address.getHostAddress());
			   System.out.println("Reach:" + address.isReachable(3000)); //是否能通信 返回true或false
			   System.out.println(address.toString());
			   if(address.isReachable(3000)){		//可以ping通
				   JschUtils jschUtils = null;
//					初始化连接设备的工具类
					try {
//						jschUtils = new JschUtils(hostIp, userName, passWord);
						if(jschUtils!=null){
							map.put("result", "success");
			                map.put("message", "服务器登录成功！");
						}
						
					} catch (Exception e) {
						map.put("result", "error");
		                map.put("message", "服务器登录失败！请检查输入的账号、密码是否正确！");
						e.printStackTrace();
					}
					
			   }else{		//ping不通
				   map.put("result", "error");
                map.put("message", "服务器IP地址不能Ping通，请检查IP地址是否正确或是否有禁止Ping的安全策略!");
			   }
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
	        
			  return viewPath+"/searchadd";
	   }
	   
	   
	 //服务器搜索添加——搜索添加
	   @RequestMapping(value="/onServiceSearch.do")
	   public String onServiceSearch(String hostIp, String userName, String passWord, DcmHosts hosts, ModelMap map){
		   
		   JschUtils jschUtils = null;
//			初始化连接设备的工具类
			try {
//				jschUtils = new JschUtils(hostIp, userName, passWord);
				if(jschUtils!=null){
					jschUtils.initShellChannel();
			        jschUtils.getPrompt();
					
					getPidandCpu(jschUtils);
						
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		   
			return viewPath+"/searchadd";
	   }
	   
	   
	   public void getPidandCpu(JschUtils jschUtils){
		   
		    String checkCmd = "cat /proc/cpuinfo";	//获取CPU相关的信息
//			获取命令结果
			String cmdResult = null;
			try {
				cmdResult = jschUtils.sendCmd(checkCmd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			System.out.println(cmdResult);
			
			if(StringUtils.isBlank(cmdResult)||cmdResult.contains("No such file or directory")||cmdResult.contains("command not found")){
				System.out.println("执行"+checkCmd+"命令后，结果为空，请检查该命令是否正确！");
			}

	        //将一个或者多个“\r”去掉
			cmdResult = cmdResult.replaceAll("\r+","");
	        //将一个或者多个“\n”转换成一个
			cmdResult = cmdResult.replaceAll("\n+","\n").trim();

	        //List<ParamItems> paramItemList = new ArrayList<ParamItems>();

	        String maipu[] = cmdResult.split("\n");
	        //第一行为命令不进行解析。
	        if(maipu.length>=2){
	        		//第二行为字段名所以从第三行开始
	                for (int j=2; j<maipu.length-1; j++) {
	                    //将所有一个或者多个空格转换成一个空格
	                    String newStr = maipu[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
	                    if(newStr.contains("vendor_id")){	//厂商
	                    	String pid = split(newStr,": ")[1];
	                    	System.out.println(pid);
	                    }
	                    if(newStr.contains("model name")){	//型号
	                    	String pid = split(newStr,": ")[1];
	                    	System.out.println(pid);
	                    }
	                    if(newStr.contains("cpu cores")){	//内核数量
	                    	String pid = split(newStr,": ")[1];
	                    	System.out.println(pid);
	                    }
	                }
	            
	        }
	        
	        String checkCmd1 = "getconf LONG_BIT";	//获取CPU位数
	        String cmdResult1 = null;
			try {
				cmdResult1 = jschUtils.sendCmd(checkCmd1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println(cmdResult1);
	        
	        String checkCmd2 = "grep -c 'processor' /proc/cpuinfo";	//获取CPU数量
	        String cmdResult2 = null;
			try {
				cmdResult2 = jschUtils.sendCmd(checkCmd2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println(cmdResult2);
	        

	    }

}
