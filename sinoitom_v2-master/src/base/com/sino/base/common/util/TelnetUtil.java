package com.sino.base.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.telnet.TelnetClient;
import org.springside.modules.utils.PropertiesLoader;

import smartlink.cmdb.devoperation.CmdbDeviceOperation;


import com.sino.base.common.telnet.TelnetWrapper;


public class TelnetUtil {
	
	private static String prompt = "#";//
	
	private static String usernamePrompt = "login:";
	private static String passwordPrompt = "Password:";
	private static String hostIP="10.75.53.141";
	private static String userName="admin";
	private static String password="cisco";
	private static int hostPort=23;
	
	private static  TelnetWrapper telnet=new TelnetWrapper();
	
	public TelnetWrapper initTelnetConnect(){
		
		 Properties pingResult =CmdbDeviceOperation.getInstance().getNetworkDevicePing(hostIP);
		 boolean isPing = Boolean.parseBoolean(pingResult.get("Ping_Result").toString());
		 if(isPing){
			 try {
					if(telnet.connect(hostIP, hostPort)){
						if(telnet.login(userName, password, usernamePrompt,passwordPrompt)){
							telnet.initPrompt(prompt);
							 return telnet;
						}else{
							System.out.println("host:"+hostIP+"登录失败！");
						}
					}else{
						System.out.println("host:"+hostIP+"暂时无法连接，连接失败！");
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }else{
			 System.out.println("host:"+hostIP+"ping 不通！");
		 }
		
		return null;
	}
	
	
	public static boolean isConnect(String hostIP){
		try {
			Socket socket =new Socket(hostIP,hostPort);
			socket.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
}
