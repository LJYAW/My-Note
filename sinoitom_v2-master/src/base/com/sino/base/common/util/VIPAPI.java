package com.sino.base.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;


public class VIPAPI {
	
	public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  3000 ;  //超时应该在3钞以上        
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }
	
	public static String configure_release(String url,String userName,String password,String cmd) {
		String lineStr = "";
		String pyPath=SystemUtil.getRootPath1();
		try {
			System.out.println("Get response from Device by NX-API......");
			// 在单独的进程中执行指定的自字符串命令
			String pythonURL = "/WEB-INF/vipapi/viptela.py";
			pythonURL = pyPath+pythonURL;
			System.out.println("pythonURL:="+pythonURL);
			String python_cmd="python "+pythonURL+" --url \""+url+"\" --userName \""+userName+"\" --password \""+password+"\" --cmd \""+cmd+"\" ";
			System.out.println("python cmdline:\n"+ python_cmd);
			
			String[] args = new String[] { "python", pythonURL,url, userName, password,cmd };
			
			Process pr = Runtime.getRuntime().exec(args);
			
			//System.out.println("python "+pythonURL+" --url \""+url+"\" --userName \""+userName+"\" --password \""+password+"\" --cmd \""+cmd+"\" ");
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			System.out.println("bufferReader=in:" + in);
			System.out.println("in.readLine:");
			int i=0;
			while ((line = in.readLine()) != null) {
				lineStr = line.replaceAll("u'", "'");
//				System.out.println("i=" + i +": " + lineStr);
			}
			in.close();
			String cmdStr = cmd.replaceAll(" ;", "\n");
			System.out.println("Call NX-API finished ! the config commands are:\n"+cmdStr);
			// 导致当前线程等待，如有必要，一直要等到由该process对象表示的进程已经终止，如果已经终止该子进程，此方法立即返回
			// 如果没有终止该子进程，调用的线程将被阻塞，知道退出子进程，根据惯例，0表示正常终止
			pr.waitFor();
			System.out.println("end");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(lineStr);
		return lineStr;
	}
	
	
	public static String showConfigure(String url,String userName,String password,String cmd) {
		String lineStr = "";
		String pyPath=SystemUtil.getRootPath1();
		try {
			System.out.println("Get response from Device by NX-API......");
			// 在单独的进程中执行指定的自字符串命令
			String pythonURL = "/WEB-INF/nxapi/showConfigure.py";
			pythonURL = pyPath+pythonURL;
			System.out.println("pythonURL:="+pythonURL);
			
			if(!StringUtil.isNullString(url)){
				if(!StringUtil.isNullString(userName)&&!StringUtil.isNullString(password)){
					String python_cmd="python "+pythonURL+" --url \""+url+"\" --userName \""+userName+"\" --password \""+password+"\" --cmd \""+cmd+"\" ";
					System.out.println("python cmdline:\n"+ python_cmd);
					
					String[] args = new String[] { "python", pythonURL,url, userName, password,cmd };
					
					Process pr = Runtime.getRuntime().exec(args);
					
					BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
					String line;
					System.out.println("bufferReader=in:" + in);
					System.out.println("in.readLine:");
					int i=0;
					while ((line = in.readLine()) != null) {
						lineStr = line.replaceAll("u'", "'");
//						System.out.println("i=" + i +": " + lineStr);
					}
					in.close();
					System.out.println("Call NX-API finished ! the config commands are:\n"+cmd);
					// 导致当前线程等待，如有必要，一直要等到由该process对象表示的进程已经终止，如果已经终止该子进程，此方法立即返回
					// 如果没有终止该子进程，调用的线程将被阻塞，知道退出子进程，根据惯例，0表示正常终止
					pr.waitFor();
					System.out.println("end");
				}else{
					System.out.println("用户名密码不能为空");
				}
			}
			
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return lineStr;
	}
	public static void main(String[] args) {
		VIPAPI.configure_release("192.168.99.201", "admin", "admin", "device");
	}

}
