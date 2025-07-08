package com.sino.cmdb.indicator.cmdCheckItems.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JschUtils {

	// private String charset = "UTF-8"; //
	private String userName; //
	private String passwd; //
	private String hostIp; //
	private String privCmd;
	private String privPwd;
	private String prop="#";
	
	private int sshPort = 22;
	private int timeout = 5 * 1000; //ms
	private JSch jsch;
	static private Session session;
	static private ChannelShell shellChannel = null;
	static private ChannelExec execChannel = null;
	static private InputStream in = null;
	static private OutputStream out = null;
	
	
	String path = null;
	String retn = null;
	public static void main(String[] args) throws Exception {
		JschUtils jschUtils = new JschUtils("192.168.100.15","sinobaas","baas~123","enable","Sino+bridge:80","#");
		
		String cmd = "show process cpu | include CPU utilization";
//		String cmd = "show version";
		
		String cmdResult = jschUtils.getCmdResult(cmd);
		System.out.println(cmdResult);
		
	}
	
	public JschUtils(String hostIp, String userName, String passWord,String privCmd,String privPwd,String prop) throws Exception {
		this.hostIp = hostIp;
		this.sshPort = 22;
		this.userName = userName;
		this.passwd = passWord;
		this.privCmd = privCmd;
		this.privPwd = privPwd;
		this.prop = prop;
		try {
			JSch jsch = new JSch();
		
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session = jsch.getSession(userName, hostIp, sshPort);
			session.setPassword(passWord);
			session.setConfig(config);
			session.setTimeout(timeout);
			session.connect();
		} catch (Exception exception) {
			throw new Exception();
		}
	}
	
	public JschUtils(String hostIp, String userName, String passWord,Integer port) throws Exception {
		this.hostIp = hostIp;
		this.sshPort = port;
		this.userName = userName;
		this.passwd = passWord;
		this.privCmd = privCmd;
		this.privPwd = privPwd;
		this.prop = prop;
		try {
			JSch jsch = new JSch();
		
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session = jsch.getSession(userName, hostIp, sshPort);
			session.setPassword(passWord);
			session.setPort(port);
			session.setConfig(config);
			session.setTimeout(timeout);
			session.connect();
		} catch (Exception exception) {
			throw new Exception();
		}
	}
	
	public String getCmdResult(String cmd){
		String result = "";
		try {
			initShellChannel();
			getPrompt();
			sendCmd(privCmd);
			sendCmd(privPwd);//Sino+bridge:80
			sendCmd("terminal length 0");//
			result = sendCmdExpect(cmd);
			sendCmd("exit");
			close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return result ;
	}
	public void initShellChannel() throws IOException {
		if (session == null) {
			throw new RuntimeException("SSH Session is null!");
		}

		try {
			shellChannel = (ChannelShell) session.openChannel("shell");
			in = shellChannel.getInputStream();
			shellChannel.setPty(true);
			shellChannel.connect();
			out = shellChannel.getOutputStream();
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close() throws IOException{
		if (in != null)  in.close();
		if (out != null)  out.close();
		if (shellChannel!=null)  shellChannel.disconnect();
		if (execChannel!=null)   execChannel.disconnect();
		if (session!=null) session.disconnect();
	}
	
public String sendCmdExpect(String cmd) throws IOException{
		
		byte[] inBuf = new byte[2048];
		String tmpResult = null;
		StringBuffer result = new StringBuffer();
		boolean flag = true;
		out.write((cmd + "\r").getBytes());
		out.flush();
		while (flag) {
			while (in.available() > 0) {
				int i = in.read(inBuf, 0, 2048);
				tmpResult = new String(inBuf, 0, i);
				if (tmpResult != null){
//					System.out.print(tmpResult);
					result.append(tmpResult);
				}
				if ((i < 0) || ((tmpResult != null) && tmpResult.contains(this.prop))){
					flag = false;
					break;
				}
			}
			if (flag){
				try {
					Thread.sleep(100); //0.5秒
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (shellChannel.isClosed()) {
//				System.out.println("exit-status: " + shellChannel.getExitStatus());
				break;
			}
		}
		return result.toString();
	}
	
	public String sendCmd(String cmd) throws IOException{
		
		byte[] inBuf = new byte[2048];
		String tmpResult = null;
		StringBuffer result = new StringBuffer();
		boolean flag = true;
		out.write((cmd + "\r").getBytes());
		out.flush();
		while (flag) {
			while (in.available() > 0) {
				int i = in.read(inBuf, 0, 2048);
				tmpResult = new String(inBuf, 0, i);
				if (tmpResult != null){
//					System.out.print(tmpResult);
					result.append(tmpResult);
				}
				
				if ((i < 0) ||(in.available() <= 0)) {
					flag = false;
					break;
				}
			}
			if (flag){
				try {
					Thread.sleep(500); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (shellChannel.isClosed()) {
//				System.out.println("exit-status: " + shellChannel.getExitStatus());
				break;
			}
		}
		return result.toString();
	}
	public String sendCmd(String cmd,int timeout) throws IOException{
		
		byte[] inBuf = new byte[2048];
		String tmpResult = null;
		StringBuffer result = new StringBuffer();
		boolean flag = true;
		out.write((cmd + "\r").getBytes());
		out.flush();
		while (flag) {
			while (in.available() > 0) {
				int i = in.read(inBuf, 0, 2048);
				tmpResult = new String(inBuf, 0, i);
				if (tmpResult != null){
//					System.out.print(tmpResult);
					result.append(tmpResult);
				}
				
				if ((i < 0) ||(in.available() <= 0)) {
//					System.out.print("in.available()=" + in.available());
					flag = false;
					break;
				}
			}
			if (flag){
				try {
					Thread.sleep(timeout); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (shellChannel.isClosed()) {
//				System.out.println("exit-status: " + shellChannel.getExitStatus());
				break;
			}
		}
		return result.toString();
	}
	
	public String getPrompt() throws IOException {
		byte[] inBuf = new byte[512];
		String result = null;
		boolean flag = true;
//		out.write(("\r").getBytes());
//		out.flush();
		while (flag) {
			while (in.available() > 0) {
				int i = in.read(inBuf, 0, 512);
				if (i < 0)
					break;
				result = new String(inBuf, 0, i);
				flag = false;
			}
			if (flag){
				try {
					Thread.sleep(500); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
}