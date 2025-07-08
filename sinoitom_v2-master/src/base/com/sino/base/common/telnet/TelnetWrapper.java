package com.sino.base.common.telnet;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;


public class TelnetWrapper extends TelnetProtocolHandler {

	public TelnetWrapper() {
		scriptHandler = new ScriptHandler();
		port = 23;
		script = new Vector();
	}

	public void initPrompt(String prompt) {
		this.prompt = prompt;
	}

	public boolean connect(String host, int port) throws IOException {
		try {
			socket = new Socket(host, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			reset();
			return true;
		} catch (Exception e) {
			disconnect();
			return false;
		}
	}

	public void disconnect() throws IOException {
		if (socket != null)
			socket.close();
	}

	public void notifyEndOfRecord() {
	}

	public boolean login(String user, String pwd) throws IOException {
		try {
			waitfor("login:");
			send(user);
			waitfor("Password:");
			send(pwd);
			return true;
		} catch (IOException io) {
			return false;
		}
	}

	public boolean login(String user, String pwd, String userPrompt, String passwdPrompt) throws IOException {
		try {
			String ss = waitfor(userPrompt);
			ss = send(user);

			ss = waitfor(passwdPrompt);
			ss = send(pwd);
			return true;
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		}
	}

	public String login(String user, String pwd, boolean user1, boolean pwd1, String userPrompt, String pwdPrompt,
			String prompt) throws IOException { // common login user - common mode
		String logininfo = null;
		try {
			if ((user == null || user.length() == 0) && (pwd == null || pwd.length() == 0)) {
				logininfo = waitfor(prompt);
			} else if (user != null && user.length() > 0 && (pwd == null || pwd.length() == 0)) {
				if (user1)
					waitfor(userPrompt);
				send(user);
				logininfo = waitfor(pwdPrompt);
			} else if (pwd != null && pwd.length() > 0 && (user == null || user.length() == 0)) {
				if (pwd1) {
					waitfor(pwdPrompt);
				}
				send(pwd);
				logininfo = waitfor(prompt);
			}

			return logininfo;
		} catch (IOException io) {
			io.printStackTrace();
			return null;
		}
	}


	public String execCommand(String cmd) {
		String cmdResult = null;
		try {
			cmdResult = send(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cmdResult);
		return cmdResult;
	}

	public String execCommands(String[] cmd) {
		String cmdResult = null;
		try {
			for (int i = 0; i < cmd.length; i++) {
				cmdResult = send(cmd[i]);
				if (i == cmd.length - 1) {
					try {
						//    					Thread.sleep(5000L); //
						cmdResult = waitfor("Copy complete.");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.print(cmdResult);
			}

			cmdResult = send("end");
			// cmdReturnInfo = waitfor(prompt);
			System.out.print(cmdResult);
			String exit = send("exit");
			//            cmdReturnInfo = waitfor(prompt);
			System.out.println("exit");

			return cmdResult;
		} catch (IOException io) {
			io.printStackTrace();
			return null;
		}

	}

	public String login(String user, String pwd, String userPrompt, String passwdPrompt, String test)
			throws IOException {  //common login user - common mode
		String logininfo = null;
		String cmdReturnInfo = null;
		//        String cmd = "show version";
		StringBuffer loginInfos = new StringBuffer();
		try {
			String userInfo = waitfor(userPrompt);
			System.out.print(userInfo);
			//loginInfos.append(userInfo);
			String passPrompt = send(user);
			//loginInfos.append(user);
			//String passwdInfo = waitfor(passwdPrompt);
			System.out.print(passPrompt);
			loginInfos.append(passPrompt);
			logininfo = send(pwd);
			//loginInfos.append(pwd);
			//logininfo = waitfor(prompt);
			System.out.print(logininfo);

			String exit = send("exit");
			//            cmdReturnInfo = waitfor(prompt);
			System.out.println("exit");

			return cmdReturnInfo;
		} catch (IOException io) {
			io.printStackTrace();
			return null;
		}
	}

	public String login(String user, String pwd, String userPrompt, String passwdPrompt, String prompt,
			String enablecmd, String enablepwd, String enablepwdPrompt, String enablePrompt)
			throws IOException { // enable privileges user login (global)
		String logininfo = null;
		StringBuffer loginInfos = new StringBuffer();
		try {
			String userInfo = waitfor(userPrompt);
			loginInfos.append(userInfo);
			send(user);
			//loginInfos.append(user);
			String passwdInfo = waitfor(passwdPrompt);
			loginInfos.append(passwdInfo);
			send(pwd);
			//loginInfos.append(pwd);
			String enablepwd1Info = waitfor(prompt);
			loginInfos.append(enablepwd1Info);

			//08-01 cuilei updates
			if (enablecmd != null && enablecmd != "") {
				send(enablecmd);
				String enablepwdInfo = waitfor(enablepwdPrompt);
				loginInfos.append(enablepwdInfo);
				send(enablepwd);
				logininfo = waitfor(enablePrompt);
				loginInfos.append(logininfo);
			}


			//loginInfos.append(enablepwd);

			CliSessionResult.getInstance().updateLoginInfo(loginInfos.toString());

			return logininfo;
		} catch (IOException io) {
			io.printStackTrace();
			return null;
		}
	}

	public String login(String user, String pwd, String userPrompt, String passwdPrompt, String prompt,
			String enablecmd, String enablepwd, String enablepwdPrompt, String enablePrompt, String configCommand,
			String configPrompt) throws IOException {  //configure mode login (global and configure and interface mode)
		String logininfo = null;
		StringBuffer loginInfos = new StringBuffer();
		try {
			String userInfo = waitfor(userPrompt);
			loginInfos.append(userInfo);
			send(user);
			//loginInfos.append(user);
			String passwdInfo = waitfor(passwdPrompt);
			loginInfos.append(passwdInfo);
			send(pwd);
			//loginInfos.append(pwd);
			String enablepwd1Info = waitfor(prompt);
			loginInfos.append(enablepwd1Info);
			send(enablecmd);
			String enablepwdInfo = waitfor(enablepwdPrompt);
			loginInfos.append(enablepwdInfo);
			send(enablepwd);
			//loginInfos.append(enablepwd);
			String enables = waitfor(enablePrompt);
			loginInfos.append(enables);
			send(configCommand);
			//loginInfos.append(configCommand);
			logininfo = waitfor(configPrompt);
			loginInfos.append(logininfo);
			CliSessionResult.getInstance().updateLoginInfo(loginInfos.toString());

			return logininfo;
		} catch (IOException io) {
			io.printStackTrace();
			return null;
		}
	}

	public boolean enable(String enablecmd, String enablepwd, String enablepwdPrompt, String enablePrompt)
			throws IOException {
		try {
			send(enablecmd);
			waitfor(enablepwdPrompt);
			send(enablepwd);
			waitfor(enablePrompt);
			return true;
		} catch (IOException io) {
			return false;
		}
	}

	public String enable(String enableCmd, String enapwd, String enablepwdPrompt, String prompt, boolean flag)
			throws IOException {
		String logininfo = null;
		try {
			send(enableCmd);
			waitfor(enablepwdPrompt);
			send(enapwd);
			logininfo = waitfor(prompt);
			return logininfo;
		} catch (IOException io) {
			io.printStackTrace();
			return null;
		}
	}

	public String send(String s, String s1) throws IOException {
		String result = null;
		String ss = send(s);
		result = waitfor(s1);
		return result;
	}

	public String sendCommand(String command, String exitPrompt, String terminallength) {
		String result = null;
		try {

			send(terminallength);
			waitfor(exitPrompt);
			send(command);
			result = waitfor(exitPrompt); //lfc debug delete
			if (result != null) {
				StringBuffer newresult = new StringBuffer();
				StringTokenizer st = new StringTokenizer(result, "\n");
				if (st != null) {
					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						token = token.trim();
						String lines = token.toString();
						if (!lines.equalsIgnoreCase(command)) {
							newresult.append(lines);
							newresult.append("\n");
						}
					}
					result = newresult.toString();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String send(String cmd) throws IOException {
		byte arr[] = (new StringBuilder()).append(cmd).append("\n").toString().getBytes();
		transpose(arr);
		if (prompt != null)
			return waitfor(prompt);
		else
			return null;
	}

	public String waitfor(String searchElements[]) throws IOException {
		ScriptHandler handlers[] = new ScriptHandler[searchElements.length];
		for (int i = 0; i < searchElements.length; i++) {
			handlers[i] = new ScriptHandler();
			handlers[i].setup(searchElements[i]);
		}

		byte b[] = new byte[256];
		int n = 0;
		StringBuffer ret = new StringBuffer();
		do {
			if (n < 0)
				break;
			n = read(b);
			if (n > 0) {
				String current = new String(b, 0, n);
				ret.append(current);
				int i = 0;
				while (i < handlers.length) {
					if (handlers[i].match(b, n))
						return ret.toString();
					i++;
				}
			}
		} while (true);
		return "";
	}

	public String waitfor(String match) throws IOException {
		String matches[] = new String[1];
		matches[0] = match;
		return waitfor(matches);
	}

	public int read(byte b[]) throws IOException {
		int n = negotiate(b);
		if (n > 0)
			return n;
		for (; n <= 0; n = negotiate(b)) {
			do {
				n = negotiate(b);
				if (n > 0)
					return n;
			} while (n == 0);
			n = in.read(b);
			if (n < 0)
				return n;
			inputfeed(b, n);
		}

		return n;
	}

	public void write(byte b[]) throws IOException {
		out.write(b);
	}


	public String getTerminalType() {
		return "dumb";
	}

	public Dimension getWindowSize() {
		return new Dimension(1000, 24);
	}

	public void setLocalEcho(boolean flag) {
	}

	private static final int debug = 0;
	protected ScriptHandler scriptHandler;
	private Thread reader;
	protected InputStream in;
	protected OutputStream out;
	protected Socket socket;
	protected String host;
	protected int port;
	protected Vector script;
	private String prompt;
}