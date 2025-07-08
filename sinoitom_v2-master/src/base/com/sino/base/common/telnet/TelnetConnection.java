package com.sino.base.common.telnet;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.telnet.TelnetClient;


public class TelnetConnection  {

	
	    private TelnetClient telnet = null;

		private InputStream in=null;
	 
	    private PrintStream out=null;
	 
	    private String prompt = "#";// 普通用户结束
	    private String hostIP = "10.75.53.141";
		private String userName = "admin";
		private String password = "cisco";
		private int hostPort = 23;
	 
	    
	    
	    //新增方法
	    public TelnetClient initTelnetClinet(){
	    	
	    	if(telnet==null){
	    		telnet=new TelnetClient();
	    	}
	    	return telnet;
	    }
	    
	    public boolean connect(String hostIP, int hostPort) {
			try {
				telnet.connect(hostIP, hostPort);
				in = telnet.getInputStream();
				out = new PrintStream(telnet.getOutputStream());
				return true;
			} catch (Exception e) {
				disconnect();
				return false;
			}
		}
	 
//	    public boolean isConnect(String hostIP, int hostPort){
//	    	try {
//	    		
//	    		InetAddress address=InetAddress.getByName(hostIP);
//	    		telnet.connect(address);
////	    		in = telnet.getInputStream();
////				out = new PrintStream(telnet.getOutputStream());
//	    		return true;
//			} catch (Exception e) {
//				return false;
//			}
//	    }
	    
		public boolean isConnect(String hostIP){
			try {
				Socket socket =new Socket(hostIP,hostPort);
				socket.close();
				return true;
			} catch (Exception e) {
				return false;

			}
		}
	    /**
	     * 登录
	     * 
	     * @param user
	     * @param password
	     */
	    
	    public  boolean login(String user, String password){
	        // read()Until("login:");
	        readUntil("login:");
	        write(user);
	        readUntil("Password:");
	        write(password);
//	        readUntil(prompt + "");
//	       System.out.println(loginInfo);
	        
	        if (!varifyPassword("incorrect")) {
	        	System.out.println("帐号或密码错误。。。。。");
				return false;
			}
	        return true;
	    }
	    
	    
	    public  boolean login(String user, String password,String usernamePrompt,String passwordPrompt){
	        // read()Until("login:");
	        readUntil(usernamePrompt);
	        write(user);
	        readUntil(passwordPrompt);
	        write(password);
//	        readUntil(prompt + "");
//	       System.out.println(loginInfo);
	        
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
					System.out.println(loginInfo);
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
	                if (ch == lastChar) {
	                    if (sb.toString().endsWith(pattern)) {
	                        return sb.toString();
	                    }
	                }
	                
	                ch = (char) in.read();
	                System.out.print(ch);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    public String readUntil2(String pattern) {
	        try {
	            char lastChar = pattern.charAt(pattern.length() - 1);
	            StringBuffer sb = new StringBuffer();
	            char ch = (char) in.read();
	            while (true) {
	                sb.append(ch);
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
	    
	    public String sendExitCommand(String command) {
			try {
				write(command);
				return readUntil2(prompt + "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	 
	    /**
	     * 关闭连接
	     */
	    public void disconnect() {
	        try {
	            telnet.disconnect();
//	            in.close();
//	            out.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public int connectTimeout(){
	    	return telnet.getConnectTimeout();
	    }
	    
	    public void initPrompt(String prompt)
	    {
	        this.prompt = prompt;
	    }
	 
	    public static void main(String[] args) {
//	      TelnetClient telnet = new TelnetClient();
	        try {
//	        	TelnetConnection she = new TelnetConnection("10.75.53.141", 23, "admin", "cisco2");
	        	final TelnetConnection she = new TelnetConnection();
	        	she.initTelnetClinet();
	        	she.connect("172.16.11.100", 23);
	        	boolean is=she.login("admin", "cisco", "login:", "Password:");
	        	 if(!is){
	        		 she.disconnect();
						if (she.in!=null)
							she.in.close();
						System.exit(1);
		            }else{
		            	she.initPrompt("#");
		            }
	        	
	        	
	            //执行的命令
	        	System.out.println("开始执行命令：");
	        	
	        	new Thread(new Runnable(){
					public void run() {
						 she.sendCommand("config");
				            she.sendCommand("vrf context t1");
				            she.sendCommand("end");
				            she.initPrompt("Copy complete.");
				            she.sendExitCommand("copy running startup");
				            she.initPrompt("#");
					}
	        	}).start();
	           
	            
	        	new Thread(new Runnable(){
					public void run() {
						 she.sendCommand("config");
				            she.sendCommand("vrf context t2");
				            she.sendCommand("end");
				            she.initPrompt("Copy complete.");
				            she.sendExitCommand("copy running startup");
				            she.initPrompt("#");
					}
	        	}).start();
	           
	          new Thread(new Runnable(){
				public void run() {
			            she.sendCommand("config");
			            she.sendCommand("vrf context t3");
			            she.sendCommand("end");
			            she.initPrompt("Copy complete.");
			            she.sendExitCommand("copy running startup");
			            she.initPrompt("#");
				}
       	}).start();
          
//	            
	           
	            
	          
//	            TelnetConnection she2 = new TelnetConnection();
//	        	she2.initTelnetClinet();
//	        	she2.connect("10.75.53.142", 23);
//	        	boolean is2=she2.login("admin", "cisco", "login:", "Password:");
//	        	 if(!is2){
//	        		 she2.disconnect();
//						if (she2.in!=null)
//							she2.in.close();
//						System.exit(1);
//		            }else{
//		            	she2.initPrompt("#");
//		            }
//	        	
//	        	
//	            //执行的命令
//	        	System.out.println("开始执行命令：");
//	            she2.sendCommand("config");
//	            she2.sendCommand("no vrf context t1");
//	            she2.initPrompt("Copy complete.");
//	            she2.sendCommand("copy running startup");
//	            she2.sendCommand("end");
//	            she2.sendCommand("edit");
//	            she2.disconnect();
//	 
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	 
	    }

		
	    public InputStream getIn() {
			return in;
		}

		public void setIn(InputStream in) {
			this.in = in;
		}

		public PrintStream getOut() {
			return out;
		}

		public void setOut(PrintStream out) {
			this.out = out;
		}
}
