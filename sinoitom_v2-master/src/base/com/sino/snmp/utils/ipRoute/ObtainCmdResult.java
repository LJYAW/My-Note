package com.sino.snmp.utils.ipRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.sino.cli.ssh.SSHDConnection;
import com.sino.cli.telnet.TelnetConnection;
//import com.sino.snmp.utils.ipRoute.conn.SSHDConnection;


public class ObtainCmdResult {
	
//	定义一些关键字
	public static final String VIA = "via";
	public static final String CONNECTED = "is directly connected,";
//	这是获取系统的换行符
	public static final String lineSeparator = System.getProperty("lineSeparator");
	
	private String ip;//ip地址
	private String username;//用户名
	private String password;//密码
	private String enpassword;//特权密码
	private String accessMode;//连接方式
	private Integer port;//端口号
	private String cmdPrompt;
	private String privUserCmd;
	
//	全参构造
	public ObtainCmdResult(String ip, String username, String password,
			String enpassword, Integer port,String accessMode) {
		super();
		this.ip = ip;
		this.username = username;
		this.password = password;
		this.enpassword = enpassword;
		this.port = port;
		this.cmdPrompt=cmdPrompt;
		this.privUserCmd = privUserCmd;
		this.accessMode = accessMode;
	}
	
	public static void main(String[] args) throws Exception {
//		String ip ="192.168.111.100";
//		String username = "sinobaas";
//		String password = "baas~123";
//		String accessMode = "telnet";
//		int port = 23;//telnet端口号
		
		String ip ="192.168.100.254";
		String username = "sinobas";
		String password = "baas~123";
		String enpassword = "";
		int port = 22;//ssh端口号
		String accessMode = "ssh";
		
		String cmd = "show ip route";
		
		ObtainCmdResult ocr = new ObtainCmdResult(ip,username,password,enpassword,port,accessMode);
		Map<String, List<RoutingParam>> result = ocr.getResult(cmd);
		System.out.println(result);
	}

//	获取ssh连接对象
	private SSHDConnection getSSHInstance(){
		System.out.println("ip "+ip+" username "+username+" password "+password+" port "+port);
		SSHDConnection sshConn = new SSHDConnection(ip, username, password,port);
		
		if(sshConn.connect()){
			return sshConn;
		}
		return null;
	}
	
//	获取telnet对象
	private TelnetConnection getTelnetInstance() throws Exception{
//		TelnetConnection tel = new TelnetConnection(ip, username, password, enpassword, port);
//		if(tel.isConnect(ip)){
//			return tel;
//		}
		return null;
	}
	
	public Map<String,List<RoutingParam>> getResult(String cmd) throws Exception{
		
		String resultStr = "";
		Map<String,List<RoutingParam>> map = new HashMap<String, List<RoutingParam>>();
		String rmMoreCmd = "terminal length 0";//去move的命令
		
		if(accessMode.equalsIgnoreCase("ssh")){

				SSHDConnection sshdConn = getSSHInstance();
				sshdConn.sendCommand(rmMoreCmd, "#");
				
				resultStr = sshdConn.sendCommand(cmd, "#");//\r\nSINO-BJ-RT37-01#terminal length 0\r\n
				try {
					//关闭连接
					sshdConn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//		}
		
		if(accessMode.equalsIgnoreCase("telnet")){
			TelnetConnection telConn = getTelnetInstance();
			if(telConn!=null){
				resultStr = telConn.sendCommand(cmd);
				telConn.close();
			}
		}
		
		
		if(StringUtils.isNotBlank(resultStr)){
			List<RoutingParam> rpList = parseMethod(resultStr);
			if(rpList!=null && rpList.size()>0){
				map.put(ip, rpList);
			}
		}
		
		return map;
	}
	
	
	private List<RoutingParam> parseMethod(String resultStr){
		if(resultStr.contains("% Invalid input detected at '^' marker.")){
			System.out.println("% Invalid input detected at '^' marker.");
			System.out.println("无法执行该命令。。。");
			return null;
		}
		
		String[] resultStrSplit = resultStr.split(lineSeparator);
		List<RoutingParam> rpList = new ArrayList<RoutingParam>();
		Set<String> nextHopIpSet = new HashSet<String>();
		
		for (String tempString : resultStrSplit) {
			   tempString = tempString.replaceAll("\\s+", " ").replaceAll("\t+", " ");
			   if(StringUtils.isBlank(tempString)){
				   continue;
			   }
			   String[] split = tempString.split(" ");
			   String first = split[0];
			   
			   if((first.equals("S") || first.equals("S*")) && tempString.contains(VIA)){
				   int VIA_Index = tempString.indexOf(VIA);
				   String nextHopIp = tempString.substring(VIA_Index+VIA.length()+1, tempString.length()).trim();
				   nextHopIpSet.add(nextHopIp);
				   continue;
			   }
			   
			   RoutingParam rp = new RoutingParam();
			   
			   if(first.equals("L") && tempString.contains(CONNECTED)){
				   String infoStr = tempString.replace(CONNECTED, "").replace(first, "").replaceAll("\\s+", " ").trim();
				   String[] infoStrSplit = infoStr.split(" ");
				   rp.setIntfIp(infoStrSplit[0].split("/")[0].trim());
				   rp.setIfDescr(infoStrSplit[infoStrSplit.length-1].trim());
			   }
			   
			   if(first.equals("C") && tempString.contains(CONNECTED)){
				   String infoStr = tempString.replace(CONNECTED, "").replace(first, "").replaceAll("\\s+", " ").trim();
				   String[] infoStrSplit = infoStr.split(" ");
				   String[] split2 = infoStrSplit[0].split("/");
				   
				   rp.setNetIp(split2[0].trim());
				   rp.setMaskLength(Integer.parseInt(split2[1].trim()));
				   
				   rp.setIfDescr(infoStrSplit[infoStrSplit.length-1].trim());
			   }
			   
			   boolean flag = false;
			   
			   if(StringUtils.isNotBlank(rp.getIfDescr())){
				   if(rpList!=null && rpList.size()>0){
					   for (RoutingParam param : rpList) {
						   if(param.getIfDescr().equals(rp.getIfDescr())){//唯一的标准
							   flag = true;
							   if(StringUtils.isNotBlank(rp.getIntfIp())){
								   param.setIntfIp(rp.getIntfIp());
							   }
							   if(StringUtils.isNotBlank(rp.getNetIp())){
								   param.setNetIp(rp.getNetIp());
								   param.setMaskLength(rp.getMaskLength());
							   }
							   break;
						   }
					   }
				   }
				   if(!flag){
//				   		如果是rpList中没有就直接添加
					   rpList.add(rp);
				   }
			   }
		}
		
		 List<RoutingParam> rpFinalList = new ArrayList<RoutingParam>();
			if(nextHopIpSet!=null && nextHopIpSet.size()>0 && rpList!=null && rpList.size()>0){
				for(String nextHopIp : nextHopIpSet) {
					for (RoutingParam routingParam : rpList) {
						if(routingParam.getMaskLength()!=null && StringUtils.isNotBlank(routingParam.getNetIp())){
							String networkIp = NetworkUtils.getNetworkIp(nextHopIp, routingParam.getMaskLength());
							if(networkIp.equals(routingParam.getNetIp())){
								routingParam.setNextHopIp(nextHopIp);
								rpFinalList.add(routingParam);
								break;
							}
						}
					}
				}
			}
			
//			for (RoutingParam routingParam : rpFinalList) {
//				System.out.println(routingParam);
//			}

			return rpFinalList;
	}
	

}
