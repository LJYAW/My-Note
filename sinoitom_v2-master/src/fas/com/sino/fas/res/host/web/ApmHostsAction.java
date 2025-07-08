/**
 * 
 */
package com.sino.fas.res.host.web;

import static org.springframework.util.StringUtils.split;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.Global;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysUser;
import com.sino.cmdb.indicator.cmdCheckItems.service.JschUtils;
import com.sino.fas.res.host.entity.ApmHosts;
import com.sino.fas.res.host.entity.DcmHosts;
import com.sino.fas.res.host.service.ApmHostsService;
import com.sino.utils.common.Cryptos;

/**
 * @author Mr.LP
 * @date 2019-8-21下午2:45:30
 * @className ApmHostsAction
 *
 * @Description TODO
 *
 */

@Controller
@RequestMapping(value = "/fas/res/host/apmHosts")
public class ApmHostsAction {
	
	private static Logger logger = LoggerFactory.getLogger(ApmHostsAction.class);
	private String viewPath = "/fas/res/host/ipHost";
	
	@Autowired
	private ApmHostsService apmHostsService;
	

	public static void main(String[] args) {
//		String ip = "192.168.99.160";
//		if (isConnect(ip)) {
//		System.out.println("网络状态："+"网络能ping通");
//		} else {
//		System.out.println("网络状态："+"网络ping不通");
//		}
		
		String str = "CentOS Linux release 7.5.1804 (Core)";
		//str.substring("release", ".");
//		int s = str.indexOf("release");
//		int s2 = str.indexOf(".");
//		System.out.println(s);
//		System.out.println(s2);
//		String st = str.substring(s, s2);
//		st = st.split("release ")[1];
//		System.out.println(st);
		str = str.split("release ")[1];
		//str = str.split(".")[1];
		int s = str.indexOf(".");
		System.out.println(s);
		str = str.substring(0,s);
		System.out.println(str);
		
		String s1 = "root 1312 1 0 Sep05 ? 00:02:19 /sbin/rsyslogd -i /var/run/syslogd.pid -c 5";
		String[] sss = s1.split(" ");
		int sq = s1.indexOf(sss[7]);
		System.out.println(sq);
		System.out.println(s1.substring(sq));
		
		
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
	
	
	
	/**
	    * 服务器搜索添加按钮点击跳转页面
	    * @param map
	    * @return
	    */
	   @RequestMapping(value = "/searchAdd.do")
		public String searchAdd(ModelMap map) {
			logger.info( "Enter searchAdd.do ..." );
			ApmHosts apmHosts=new ApmHosts();
			map.put("ipHost", apmHosts);
			return viewPath+"/searchadd";
		}
	   
//	   @RequestMapping(value = "/searchAddsave.do")
//		public String searchAddsave(String action, ApmHosts apmHosts, ModelMap map) {
//			logger.info( "Enter searchAddsave.do ..." );
//			ApmHosts hosts = new ApmHosts();
//			boolean validate = true;
//			SysUser curUser = SystemUtils.getLoginUser();
//			if( curUser == null ){
//				validate = false;
//				map.put("message", "系统登录超时！");			
//			}
////			else if( !ipHostService.isIpHostUnique(ipHostId, ipHost.getMacAddr())&& !ipHost.getMacAddr().equals("null")){
////				validate = false;
////				map.put("message", "该IP设备已经存在！");
////			}
//			else if( StringUtils.isBlank(apmHosts.getIpAddress()) ){
//				validate = false;
//				map.put("message", "IP地址不能为空！");
//			}
//			else if( StringUtils.isBlank(apmHosts.getUsername()) ){
//				validate = false;
//				map.put("message", "登录名不能为空！");
//			}
//			else if( StringUtils.isBlank(apmHosts.getPassword()) ){
//				validate = false;
//				map.put("message", "密码不能为空！");
//			}
//			
//			if( validate ){
//				String hostIp = apmHosts.getIpAddress();
//				String userName = apmHosts.getUsername();
//				String passWord = apmHosts.getPassword();
//				
//				//IP巡检时用到的PING IP地址方法
//				InetAddress address;
//				  try {
//				   address =InetAddress.getByName(hostIp);
//				   System.out.println("Name:" + address.getHostName());
//				   System.out.println("Addr:" + address.getHostAddress());
//				   System.out.println("Reach:" + address.isReachable(3000)); //是否能通信 返回true或false
//				   System.out.println(address.toString());
//				   if(address.isReachable(3000)){		//可以ping通
//					   JschUtils jschUtils = null;
////						初始化连接设备的工具类
//						try {
//							jschUtils = new JschUtils(hostIp, userName, passWord);
//							if(jschUtils!=null){
//								
//								if(action.equals("Check")){		//访问验证按钮
//									map.put("result", "CheckSuccess");
//					                map.put("message", "服务器登录成功！");
//								}else{		//搜索添加按钮
//									jschUtils.initShellChannel();
//							        jschUtils.getPrompt();
//									
//							        hosts = getCmdResult(jschUtils,apmHosts);		//获取命令结果解析并存储到实体类中
//									if(hosts!=null){
//										hosts.setHostId(UUID.randomUUID().toString());
////										String psWord = hosts.getPassword();
////							            //密码加密Base64
////							            if(StringUtils.isNotBlank(psWord)){
////							            	hosts.setPassword(Cryptos.desEncryptToBase64(passWord, Global.AES_KEY));
////							            }
//										apmHostsService.save(hosts);
//										map.put("result", "searchSuccess");
//									}
//								}
//								
//							}
//							
//						} catch (Exception e) {
//							map.put("result", "error");
//			                map.put("message", "服务器登录失败！请检查输入的账号、密码是否正确！");
//							e.printStackTrace();
//						}
//						
//				   }else{		//ping不通
//					   map.put("result", "error");
//	                   map.put("message", "服务器IP地址不能Ping通，请检查IP地址是否正确或是否有禁止Ping的安全策略!");
//				   }
//				  } catch (Exception e) {
//				   e.printStackTrace();
//				  }
//				
////				onCheckSSH(hostIp,userName,passWord,map);
//			} else{
//				
//				map.put("result", "error");			
//			}
//			
//			map.put("ipHost", apmHosts);
//			map.put("action", action);
//			return viewPath+"/searchadd";
//		}
	   
	   
//	   //服务器搜索添加——访问验证
//	   @RequestMapping(value="/onCheckSSH.do")		//String accessTool:连接方式——暂时只有ssh连接，暂时未用
//	   public String onCheckSSH(String hostIp, String userName, String passWord, ModelMap map){
//	        logger.info( "Enter onCheckSSH.do ..." );
//	        
//	      //IP巡检时用到的PING IP地址方法
//			InetAddress address;
//			  try {
//			   address =InetAddress.getByName(hostIp);
//			   System.out.println("Name:" + address.getHostName());
//			   System.out.println("Addr:" + address.getHostAddress());
//			   System.out.println("Reach:" + address.isReachable(3000)); //是否能通信 返回true或false
//			   System.out.println(address.toString());
//			   if(address.isReachable(3000)){		//可以ping通
//				   JschUtils jschUtils = null;
////					初始化连接设备的工具类
//					try {
//						jschUtils = new JschUtils(hostIp, userName, passWord);
//						if(jschUtils!=null){
//							map.put("result", "success");
//			                map.put("message", "服务器登录成功！");
//						}
//						
//					} catch (Exception e) {
//						map.put("result", "error");
//		                map.put("message", "服务器登录失败！请检查输入的账号、密码是否正确！");
//						e.printStackTrace();
//					}
//					
//			   }else{		//ping不通
//				   map.put("result", "error");
//                map.put("message", "服务器IP地址不能Ping通，请检查IP地址是否正确或是否有禁止Ping的安全策略!");
//			   }
//			  } catch (Exception e) {
//			   e.printStackTrace();
//			  }
//	        
//			  return viewPath+"/searchadd";
//	   }
	   
	   
	 //服务器搜索添加——搜索添加
//	   @RequestMapping(value="/onServiceSearch.do")
//	   public String onServiceSearch(String hostIp, String userName, String passWord, DcmHosts hosts, ModelMap map){
//		   
//		   JschUtils jschUtils = null;
////			初始化连接设备的工具类
//			try {
//				jschUtils = new JschUtils(hostIp, userName, passWord);
//				if(jschUtils!=null){
//					jschUtils.initShellChannel();
//			        jschUtils.getPrompt();
//					
//			        //getCmdResult(jschUtils);
//						
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		   
//			return viewPath+"/searchadd";
//	   }
	   
	   
	   public ApmHosts getCmdResult(JschUtils jschUtils,ApmHosts hosts){
		   
		   ApmHosts apmHosts = new ApmHosts();
		   apmHosts = hosts;
		    String checkCmd = "cat /proc/cpuinfo";	//获取CPU相关的信息
//			获取命令结果
			String cmdResult = null;
			try {
				cmdResult = jschUtils.sendCmd(checkCmd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			if(StringUtils.isBlank(cmdResult)||cmdResult.contains("No such file or directory")||cmdResult.contains("command not found")){
				System.out.println("执行"+checkCmd+"命令后，结果为空，请检查该命令是否正确！");
			}
			
			String maipu[] = getStrResult(cmdResult);
	        
	        //第一行为命令不进行解析。
	        if(maipu.length>=2){
	                for (int j=1; j<maipu.length-1; j++) {
	                    //将所有一个或者多个空格转换成一个空格
	                    String newStr = maipu[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
	                    if(newStr.contains("vendor_id")){	//厂商
	                    	String cpuVendor = split(newStr,": ")[1];
	                    	apmHosts.setCpuVendor(cpuVendor);
	                    }
	                    if(newStr.contains("model name")){	//型号
	                    	String cpuModel = split(newStr,": ")[1];
	                    	apmHosts.setCpuModel(cpuModel);
	                    }
	                    if(newStr.contains("cpu cores")){	//内核数量
	                    	String cpuCoreQty = split(newStr,": ")[1];
	                    	apmHosts.setCpuCoreQty(Integer.parseInt(cpuCoreQty));
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
	        
	        String str[] = getStrResult(cmdResult1);
	      //第一行为命令不进行解析。
	        if(str.length>=2){
	        	apmHosts.setCpuWidth(Integer.parseInt(str[1]));
	        }
	        
	        
	        String checkCmd2 = "grep -c 'processor' /proc/cpuinfo";	//获取CPU数量
	        String cmdResult2 = null;
			try {
				cmdResult2 = jschUtils.sendCmd(checkCmd2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        String str1[] = getStrResult(cmdResult1);
		    //第一行为命令不进行解析。
		    if(str1.length>=2){
		        apmHosts.setCpuQty(Integer.parseInt(str1[1]));
		    }
		    
		    
		    String checkCmd3 = "cat /etc/redhat-release";	//获取linux版本
	        String cmdResult3 = null;
			try {
				cmdResult3 = jschUtils.sendCmd(checkCmd3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        String str3[] = getStrResult(cmdResult3);
		    //第一行为命令不进行解析。
		    if(str3.length>=2){
		        apmHosts.setOsRelase(str3[1]);
		        if(str3[1].contains("6.")){
		        	apmHosts.setOsVersion("CentOS 6.X");
		        }
		        else if(str3[1].contains("7.")){
		        	apmHosts.setOsVersion("CentOS 7.X");
		        }
		    }
		    
		    
		    String checkCmd4 = "uname -n";	//获取主机名称
	        String cmdResult4 = null;
			try {
				cmdResult4 = jschUtils.sendCmd(checkCmd4);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        String str4[] = getStrResult(cmdResult4);
		    //第一行为命令不进行解析。
		    if(str4.length>=2){
		        apmHosts.setOsName(str4[1]);
		    }
		    
		    
		    String checkCmd5 = "cat /proc/meminfo";	//获取CPU相关的信息
//			获取命令结果
			String cmdResult5 = null;
			try {
				cmdResult5 = jschUtils.sendCmd(checkCmd5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			String str5[] = getStrResult(cmdResult5);
	        
	        //第一行为命令不进行解析。
	        if(str5.length>=2){
	                for (int j=1; j<str5.length-1; j++) {
	                    //将所有一个或者多个空格转换成一个空格
	                    String newStr = str5[j].replaceAll("\\s+", " ").replaceAll("\t+", " ").trim();
	                    if(newStr.contains("MemTotal")){	//总内存
	                    	String memTotal = split(newStr,": ")[1].split(" kB")[0];
	                    	apmHosts.setMemTotal(Long.parseLong(memTotal));
	                    }
	                    if(newStr.contains("MemFree")){	//空闲内存
	                    	String memFree = split(newStr,": ")[1].split(" kB")[0];
	                    	apmHosts.setMemFree(Long.parseLong(memFree));
	                    }
	                }
	            
	        }
		    
		    apmHosts.setResClassCode(11);
		    return apmHosts;

	    }
	   
	   
	   
	   /**
	    * 将命令结果转换成String型
	    * @param cmdResult
	    * @return
	    */
	   public String[] getStrResult(String cmdResult){
		 //将一个或者多个“\r”去掉
			cmdResult = cmdResult.replaceAll("\r+","");
	        //将一个或者多个“\n”转换成一个
			cmdResult = cmdResult.replaceAll("\n+","\n").trim();

	        //List<ParamItems> paramItemList = new ArrayList<ParamItems>();

	        String maipu[] = cmdResult.split("\n");
		   return maipu;
	   }

}
