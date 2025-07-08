package com.sino.base.common.telnet;

public class CliSessionResult {

	public CliSessionResult() {
		loginInfos = new StringBuffer();
		commandInfos = new StringBuffer();
	}

	public static CliSessionResult getInstance() {
		if (handler == null)
			handler = new CliSessionResult();
		return handler;
	}

	public void setLoginInfo(StringBuffer info) {
		loginInfos = info;
	}

	public void updateLoginInfo(String info) {
		loginInfos.append(info);
	}

	public void setCommandInfo(StringBuffer info) {
		commandInfos = info;
	}

	public void updteCommandInfo(String info) {
		commandInfos.append(info);
	}

	public StringBuffer getLoginInfo() {
		return loginInfos;
	}

	public StringBuffer getCommandInfo() {
		return commandInfos;
	}

	public void clearLoginResult() {
		loginInfos = new StringBuffer();
	}

	public void clearCommandResult() {
		commandInfos = new StringBuffer();
	}

	public void clearResult() {
		loginInfos = new StringBuffer();
		commandInfos = new StringBuffer();
	}

	private StringBuffer loginInfos;
	private StringBuffer commandInfos;
	private static CliSessionResult handler = new CliSessionResult();
}