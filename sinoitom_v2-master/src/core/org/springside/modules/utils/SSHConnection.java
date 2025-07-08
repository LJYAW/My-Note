package org.springside.modules.utils;

import static net.sf.expectit.filter.Filters.removeColors;
import static net.sf.expectit.filter.Filters.removeNonPrintable;
import static net.sf.expectit.matcher.Matchers.regexp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;


public class SSHConnection {
	private static Logger logger = Logger.getLogger(SSHConnection.class.getName());
	
	private SshClient client;
	private ClientSession session;
	private ClientChannel channel;
	private Expect expect;
	
	public String account;
	public String password;
	public String enablepassword;
	public String host;
	public int port = 22;
	public int timeout = 10000;
	
	public SSHConnection(String host) {
		super();
		this.host = host;
	}

	public SSHConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public SSHConnection(String host, int port, int timeout) {
		this.host = host;
		this.port = port;
		this.timeout = timeout;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnablepassword() {
		return enablepassword;
	}

	public void setEnablepassword(String enablepassword) {
		this.enablepassword = enablepassword;
	}
	
	/**
	 * SSH连接
	 * @author fengyao
	 * @date 2016-8-8 上午10:13:16
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2016
	 */
	public boolean connect(){
		client = SshClient.setUpDefaultClient();
		try {
			client.start();
			ConnectFuture cf = client.connect(account, host, port);
			if(cf.awaitUninterruptibly(timeout, TimeUnit.MILLISECONDS) && cf.isConnected()){
				session = cf.getSession();
				session.addPasswordIdentity(password);
				return session.auth().awaitUninterruptibly(timeout, TimeUnit.MILLISECONDS);
			}
		} catch (Exception e) {
			logger.info("SSH Connect Error --> {}");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获得Expect对象，该对用可以往SSH发送命令请求
	 * @author fengyao
	 * @date 2016-8-16 上午10:17:03
	 * @version V3.0
	 * @throws IOException 
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2016
	 */
	public void getExpect() throws IOException{
//		try {
			if (null == channel || channel.isClosed() || channel.isClosing()) {
				channel = session.createShellChannel();
				channel.open().awaitUninterruptibly();
			}
			if (channel.isOpen()) {
				expect = new ExpectBuilder()
					.withOutput(channel.getInvertedIn())
					.withInputs(channel.getInvertedOut(), channel.getInvertedErr())
					.withInputFilters(removeColors(), removeNonPrintable())
					.withExceptionOnFailure()
					.build();
			}
//		} catch (Exception e) {
//			logger.info("SSH Connect Error --> {}");
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 是否连接登录成功
	 * @return 是否登录成功；成功：true，失败：false
	 * @author fengyao
	 * @date 2016-8-16 上午10:48:53
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2016
	 */
	public boolean login(){
		return true;
	}
	
	/**
	 * 向终端发送命令
	 * @author fengyao
	 * <span style="font-family:Arial, Helvetica, sans-serif;">@date 2016-8-16 上午10:48:53</span>
	 * @version V3.0
	 * @throws IOException 
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2016
	 */
	public void write(String value) throws IOException {
		if (null == expect) {
			return;
		}
		try {
			expect.sendLine(value);
		} catch (Exception e) {
			logger.info("发命令异常:write({}){}");
			e.printStackTrace();
		}
	}
	
	/**
	 * 向终端发送命令
	 * @author fengyao
	 * @date 2016-8-16 上午10:25:29
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2016
	 */
	public String write(String value, String sprompt) {
		if (null == expect) {
			return null;
		}
		try {
			expect.sendLine(value);
			return expect.expect(regexp(sprompt)).getInput();
		} catch (Exception e) {
			logger.info("发命令异常:write({}){}");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 读取
	 * @author fengyao
	 * <span style="font-family:Arial, Helvetica, sans-serif;">@date 2016-8-16 上午10:48:53</span>
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2016
	 */
	public String read() {
		try {
		} catch (Exception e) {
			logger.info("读取回显信息异常！{}");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 关闭连接
	 * @author fengyao
	 * @date 2016-8-8 上午10:30:10
	 * @version V3.0
	 * @throws IOException 
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2016
	 */
	public void close() throws IOException{
//		try {
			if (null != expect) {
				expect.close();
			}
			if (channel.isOpen() || !channel.isClosing()) {
				channel.close(true);
			}
			if (null != session && session.isOpen()) {
				session.close(true);
			}
			if (null != client && client.isOpen()) {
				client.close(true);
			}
//		} catch (Exception e) {
//			logger.info("Close Error ---> {}");
//			e.printStackTrace();
//			
//		}
	}
	
	public static void main(String[] args) throws IOException {
		String host = "192.168.100.21";
		String account = "test";
		String password = "Qwer123";
//				enpwd = "Sino+bridge:80";
		String cmd_one = "show arp", cmd_two = "show version";
		
		SSHConnection base = new SSHConnection(host);
		base.setAccount(account);
		base.setPassword(password);
		base.connect();
		base.getExpect();
		base.login();
		
		String cmdPromt = ">";
		String privUserPromt = "#";
//		String enablePasswd =  "Sino+bridge:80";
		String enablePasswd =  null;
		String devPrompt = null;
		if (StringUtils.isNotBlank(enablePasswd)){
			System.out.println(base.write("en", "word:"));
			System.out.println(base.write("Sino+bridge:80", privUserPromt));
			
		}


		String write = base.write("\r\n", "#");
		String deviceName = write.trim().split("#")[0];
		base.write("terminal length 0", "terminal length 0\r\n"+deviceName+"#");

//		String result = base.write("show version","#");
//		String result = base.write("show process cpu | include CPU utilization","#");
		String result = base.write("show processes memory | include Used",deviceName+"#");
		System.out.println(result);

//		String write = base.write("\r", "#");
//		System.out.println(write);
//		System.out.println("第零条命令结果---"+base.write("\r", ">"));
		
//		System.out.println("第一条命令结果---"+base.write("terminal length 0", "terminal length 0\r\nSINO-BJ-RT37-01>"));
//		System.out.println("第一条命令结果---"+base.write("terminal length 0", ">"));
//		System.out.println("第二条命令结果---"+base.write(cmd_one, ">"));
		//有时候会有这个：不懂为什么，警告: exceptionCaught(ClientSessionImpl[sinobaas@/192.168.100.254:22])[state=Opened] SshException: Received SSH_MSG_CHANNEL_DATA on unknown channel 0
//		System.out.println("第三条命令结果---"+base.write(cmd_two, ">"));
//		System.out.println("第四条命令结果---"+base.write("", ">"));
//		System.out.println("第一条命令结果---"+base.write("terminal length 0", "terminal length 0\r\nB-BJ-C-S2960-01#"));
//		System.out.println("第一条命令结果---"+base.write("terminal length 0", "#"));
//		System.out.println("第二条命令结果---"+base.write("show ip int brief", devPrompt));
//		System.out.println("第二条命令结果---"+base.write("show running-config", "#"));
//		System.out.println("第三条命令结果---"+base.write(cmd_one, "#"));
		
		base.close();
	}
}
