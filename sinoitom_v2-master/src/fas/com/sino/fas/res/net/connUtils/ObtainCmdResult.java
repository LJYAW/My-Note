package com.sino.fas.res.net.connUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sino.base.common.util.StringUtil;


public class ObtainCmdResult {
	
	private static Logger logger = LoggerFactory.getLogger(ObtainCmdResult.class);
	
	private String ip;//ip地址
	private String username;//用户名
	private String password;//密码
	private String enpassword;//特权密码
//	private String accessMode;//连接方式
	private Integer port;//端口号
	private String cmdPrompt;
	private String privUserCmd;
	
//	全参构造
	public ObtainCmdResult(String ip, String username, String password,
			String enpassword, Integer port,String cmdPrompt,String privUserCmd) {
		super();
		this.ip = ip;
		this.username = username;
		this.password = password;
		this.enpassword = enpassword;
		this.port = port;
		this.cmdPrompt=cmdPrompt;
		this.privUserCmd = privUserCmd;
	}

//	获取ssh连接对象
	public SSHConnection getSSHInstance(){
		SSHConnection sshConn = new SSHConnection(ip);
		sshConn.setAccount(username);
		sshConn.setPassword(password);
		sshConn.setEnablepassword(enpassword);
		if(sshConn.connect()){
			sshConn.getExpect();
			return sshConn;
		}
		return null;
	}
	
//	获取telnet对象
	public TelnetConnection getTelnetInstance() throws Exception{
		TelnetConnection tel = new TelnetConnection(ip, username, password, enpassword, port);
		return tel;
	}
	
//	执行命令列表获取结果
	public  void verfyLineAccident(List<String> cmdList,SSHConnection sshConn,TelnetConnection telConn) throws Exception {
		String result = "";
		String sprompt = cmdPrompt;//结束符
		String devPrompt="";//交换机名+结束符
		String osType=System.getProperty("os.name");//操作系统的换行符
		
		String rmMoreCmd = "terminal length 0";//去move的命令
		String hostName="";
		if(sshConn!=null){
			String lineSplit="\r\n";
			if(StringUtils.isNotBlank(enpassword) && StringUtils.isNotBlank(this.privUserCmd)){
				sshConn.write(this.privUserCmd, "word:");
				sshConn.write(enpassword, sprompt);
			}
			devPrompt = sshConn.write(lineSplit, sprompt).replaceAll(lineSplit, "").trim();
			
			System.out.println("devPrompt******--------"+devPrompt);
		
			
			if(!StringUtil.isNullString(devPrompt)){
				String []array=devPrompt.split("!!");
				
				if(array.length>1){
					String hostTmp=array[1];
					if(hostTmp.split(sprompt).length>0){
							
						hostName=hostTmp.split(sprompt)[0]+sprompt;
					}
				}else{
					String []arr=devPrompt.split("\r");
					hostName=arr[arr.length-1];
					lineSplit="\r";
				}
				
			}
			
			System.out.println("hostName******--------"+hostName);
			
//			sshConn.write(rmMoreCmd, rmMoreCmd+lineSplit+devPrompt);
			sshConn.write(rmMoreCmd, rmMoreCmd+lineSplit+hostName);
			
			for (int i=0;i<cmdList.size();i++) {
				result = sshConn.write(cmdList.get(i),sprompt);
				String[] split = result.split(lineSplit);
				
				for (String str : split) {
					if(str.contains(cmdList.get(i))){
						if(i==0){
							str = "<h4>"+hostName+cmdList.get(i)+"</h4>";
							MessageCache.getInstance().updateMessage(str.replaceAll(" ", "&nbsp;")+"</br>");
							continue;
						}else{
							str = str.replace(cmdList.get(i), "");
						}
					}
					
					if(i<cmdList.size()-1 && str.contains(hostName)){
						str = "<h4>"+hostName+cmdList.get(i+1)+"</h4>";
					}
					
					MessageCache.getInstance().updateMessage(str.replaceAll(" ", "&nbsp;")+"</br>");
				}
			}
			
			MessageCache.getInstance().updateMessage("<h4>命令执行结束</h4>");
			sshConn.close();
		}
		
		if(telConn!=null){
			String lineSplit="\n";
			if(osType.indexOf("Windows")>=0){
				lineSplit="\r\n";
			}
			if(telConn.isConnect(ip)){
				devPrompt = telConn.sendCommand(rmMoreCmd).replace(rmMoreCmd, "").replace(lineSplit, "").trim();
				for (int i=0;i<cmdList.size();i++) {
					result = telConn.sendCommand(cmdList.get(i));
					String[] split = result.split(lineSplit);
					for (String str : split) {
						if(str.contains(cmdList.get(i))){
							if(i==0){
								str = "<h4>"+devPrompt+cmdList.get(i)+"</h4>";
								MessageCache.getInstance().updateMessage(str.replaceAll(" ", "&nbsp;")+"</br>");
								continue;
							}else{
								str = str.replace(cmdList.get(i), "");
							}
						}
						
						if(i<cmdList.size()-1 && str.contains(devPrompt)){
							str = "<h4>"+devPrompt+cmdList.get(i+1)+"</h4>";
						}
						
						MessageCache.getInstance().updateMessage(str.replaceAll(" ", "&nbsp;")+"</br>");
					}
				}
				MessageCache.getInstance().updateMessage("<h4>命令执行结束</h4>");
			}
			telConn.close();
		}
	}
	
	public static void main(String[] args) {
		
		List<String> cmdLine = new ArrayList<String>();
		
		cmdLine.add("terminal length 0");
		cmdLine.add("show interface Po19");
		cmdLine.add("show version");
		cmdLine.add("show ip int brief");
		cmdLine.add("show running");
		cmdLine.add("ping 9.9.9.9");
		
		String accessMode = "ssh";
		int port = 22;
//		ObtainCmdResult ocr = new ObtainCmdResult("192.168.100.254","sinobaas","baas~123","Sino+bridge:80",accessMode,22);
//		ObtainCmdResult ocr = new ObtainCmdResult("192.168.100.254","sinobaas","baas~123","",port,"#");
//		
		if(StringUtils.isNotBlank(accessMode) && accessMode.equals("ssh")){
//			SSHConnection sshInstance = ocr.getSSHInstance();
			try {
//				List<String> list = ocr.verfyLineAccident(cmdLine,sshInstance,null);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
//		if(StringUtils.isNotBlank(accessMode) && accessMode.equals("telnet")){
//			try {
//				TelnetConnection telnetInstance = ocr.getTelnetInstance();
//				List<String> verfyLineAccident = ocr.verfyLineAccident(cmdLine, null, telnetInstance);
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
	}

	

}
