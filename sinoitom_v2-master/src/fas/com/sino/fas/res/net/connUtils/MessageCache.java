package com.sino.fas.res.net.connUtils;

public class MessageCache {

	private  StringBuffer sb=null;
	
	private static MessageCache cache=null;
	
	public static MessageCache getInstance(){
		if(cache==null){
			cache=new MessageCache();
		}
		
		return cache;
	}
	
	public void clearMessage(){
		sb=new StringBuffer();
	}
	
	public MessageCache(){
		sb=new StringBuffer();
	}
	
	
	public synchronized void updateMessage(String str){
		sb.append(str);
	}
	
	
	public StringBuffer getMessage(){
		return sb;
	}
}
