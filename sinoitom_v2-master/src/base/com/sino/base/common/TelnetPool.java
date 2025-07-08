package com.sino.base.common;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.net.telnet.TelnetClient;

import com.sino.base.common.telnet.TelnetConnection;

public class TelnetPool {

	private static String usernamePrompt = "login:";
	private static String passwordPrompt = "Password:";
	private static String userName = "admin";
	private static String password = "cisco";
	private static int hostPort = 23;

	private static Map<String, TelnetConnection> telnetmap = null;
	private static Map<String, TelnetConnection> telnetmap2 = null;

	public static Map getTelnetMap() {
		if (telnetmap == null) {
			telnetmap = new HashMap<String, TelnetConnection>();
		}
		return telnetmap;
	}
	
	public static Map getTelnetMap2() {
		if (telnetmap2 == null) {
			telnetmap2 = new HashMap<String, TelnetConnection>();
		}
		return telnetmap2;
	}

	// 如何池子里有新的telnet事例就初始化连接
	public static TelnetConnection initTelnetConn(String tenantId,String ip) {
		TelnetConnection telnetcon = new TelnetConnection();
		telnetcon.initTelnetClinet();
		if (telnetcon.connect(ip, hostPort)) {
			if (telnetcon.login(userName, password, usernamePrompt,
					passwordPrompt)) {
				try {
					getTelnetMap().put(tenantId+ip, telnetcon);
					return telnetcon;
				} catch (Exception e) {
					System.out.println(ip + ":------>连接已断开");
				} finally {
//					System.out.println("==============");
				}
			} else {
				System.out.println(ip + ":------->登录失败");
			}
		} else {
			System.out.println(ip + ":------->连接失败");
		}
		return null;
	}
	
	public static TelnetConnection initTelnetConn2(String ip) {
		TelnetConnection telnetcon = new TelnetConnection();
		telnetcon.initTelnetClinet();
		if (telnetcon.connect(ip, hostPort)) {
			if (telnetcon.login(userName, password, usernamePrompt,
					passwordPrompt)) {
				try {
					getTelnetMap2().put(ip, telnetcon);
					return telnetcon;
				} catch (Exception e) {
					System.out.println(ip + ":------>连接已断开");
				} finally {
//					System.out.println("==============");
				}
			} else {
				System.out.println(ip + ":------->登录失败");
			}
		} else {
			System.out.println(ip + ":------->连接失败");
		}
		return null;
	}

	public static TelnetConnection getTelnet(String tenantId,String ip) {
		TelnetConnection telnet = (TelnetConnection) TelnetPool.getTelnetMap().get(tenantId+ip);
		if (telnet == null) {
			telnet = initTelnetConn(tenantId,ip);
		}
		return telnet;
	}
	
	public static TelnetConnection getTelnet(String ip) {
		TelnetConnection telnet = (TelnetConnection) TelnetPool.getTelnetMap2().get(ip);
		if (telnet == null) {
			telnet = initTelnetConn2(ip);
		}
		return telnet;
	}

	public static void main(String[] args) throws IOException {
		
//		TelnetConnection telnet = TelnetPool.getTelnet("172.16.11.100");
//		if (telnet != null) {
//			if (telnet.isConnect("172.16.11.100")) {
//				telnet.sendCommand("conf");
//				telnet.sendCommand("vrf context t123456");
//				telnet.sendCommand("end");
//			}
//		}
//		
//		
//		TelnetConnection telnet1 = TelnetPool.getTelnet("172.16.11.100");
//		if (telnet1 != null) {
//			if (telnet1.isConnect("172.16.11.100")) {
//				telnet1.sendCommand("conf");
//				telnet1.sendCommand("vrf context t654321");
//				telnet1.sendCommand("end");
//			}
//		}

	}

}
