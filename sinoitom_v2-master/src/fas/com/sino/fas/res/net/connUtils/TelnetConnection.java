package com.sino.fas.res.net.connUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.telnet.TelnetClient;

public class TelnetConnection  {
	
	    private TelnetClient telnet = new TelnetClient();
	 
	    private InputStream in;
	 
	    private PrintStream out;
	 
	    private String prompt = "#";// 普通用户结束
	 
	    public TelnetConnection(String ip, String user, String password,String enablepwd,int port) throws Exception {
	            telnet.connect(ip, port);
	            in = telnet.getInputStream();
	            out = new PrintStream(telnet.getOutputStream());
	            // 根据root用户设置结束符
	            if(StringUtils.isBlank(enablepwd)){
	            	this.prompt = ">";
	            }else{
	            	this.prompt = "#";
	            }
	            boolean is=login(user,password,enablepwd);
	            if(!is){
	            	telnet.disconnect();
					if (in!=null)
						in.close();
//					System.exit(1);//非正常退出，无论程序正常执行与否都退出
					throw new Exception("用户名或密码或特权密码不正确，请确认！");
	            }
	            
	    }
	    
	    public  boolean login(String user, String password,String enablepwd) {
	        // read()Until("login:");
	    	 readUntil("sername: ");
             write(user);
             readUntil("assword: ");
             write(password);
             // Advance to a prompt
             prompt = ">";
             String readUntil = readUntil(prompt + "");
             if(readUntil.contains("Authentication")){
            	 System.out.println("帐号或密码错误。。。。。");
            	 return false;
             }

             if(StringUtils.isNotBlank(enablepwd)){
            	 write("en");
                 readUntil("assword: ");
                 write(enablepwd);
                 prompt = "#";
                 readUntil(prompt + "");
             }
	        
             if (!varifyPassword("incorrect")) {
 	        	System.out.println("帐号或密码错误。。。。。");
 				return false;
 			}
	        return true;
	    }
	    
		public  boolean  varifyPassword(String errorInfo) {
			try {
				byte[] b = new byte[256];
				Thread.sleep(3000);
				if (in.available()>0){
					int n = in.read(b);
					String loginInfo = new String(b, 0, n);
//					System.out.println(loginInfo);
					if (loginInfo.contains(errorInfo)) {
						
						return false;
					}
				}
//				System.out.println("未等到返回信息");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	 
	    /**
	     * 读取分析结果
	     * 
	     * @param pattern
	     * @return
	     */
	    public String readUntil(String pattern) {
	        try {
	            char lastChar = pattern.charAt(pattern.length() - 1);
	            StringBuffer sb = new StringBuffer();
	            char ch = (char) in.read();
	            while (true) {
	                sb.append(ch);
	                String string = sb.toString();
	                if(sb.toString().contains("Authentication")){
                    	return sb.toString();
                    }
	                if (ch == lastChar) {
	                    if (sb.toString().endsWith(pattern)) {
	                        return sb.toString();
	                    }
	                    
	                }
	                ch = (char) in.read();
//	                System.out.print(ch);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	    /**
	     * 写操作
	     * 
	     * @param value
	     */
	    public void write(String value) {
	        try {
	            out.println(value);
	            out.flush();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	    /**
	     * 向目标发送命令字符串
	     * 
	     * @param command
	     * @return
	     */
	    public String sendCommand(String command) {
	        try {
	            write(command);
	            return readUntil(prompt + "");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	    /**
	     * 关闭连接
	     */
	    public void close() {
	        try {
	            telnet.disconnect();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    public boolean isConnect(String hostIP){
			try {
				Socket socket =new Socket(hostIP,23);
				socket.setSoTimeout(3000);
				socket.close();
				return true;
			} catch (Exception e) {
				return false;

			}
		}
	 
	    public static void main(String[] args) {
	        
	    	try {
	    		String ip ="192.168.100.11";
	    		String username ="sinobaas";
	    		String password ="baas~123";
	    		String enpassword = "Sino+bridge:80";
//	    		String enpassword = "";
	    		int port =23;
	    		
				TelnetConnection tel = new TelnetConnection(ip, username, password, enpassword, port);
				if(tel.isConnect(ip)){
//					String result = tel.sendCommand("sh run");
//					System.out.println(result);
					
//					System.out.println("第零条命令结果---"+tel.sendCommand("terminal length 0"));
					System.out.println("第零条命令结果---"+tel.sendCommand("sh ip interface bri"));
//					System.out.println("第零条命令结果---"+tel.sendCommand("terminal length 0").replace("terminal length 0", "").replace("\r\n", "").trim());
//					System.out.println("第一条命令结果---"+tel.sendCommand("sh run"));
//					System.out.println("第一条命令结果---"+tel.sendCommand("terminal length 0"));
//					System.out.println("第二条命令结果---"+tel.sendCommand("show arp"));
					
					
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	 
	    }

		
		
}
