package com.sino.base.common.telnet;

import java.util.LinkedList;
import java.util.Queue;

public class TelnetQueue {

	private static Queue<Integer> queue =null;   
	 
	public Queue initQueue(){
		if(queue==null){
			queue = new LinkedList<Integer>();
		}
		return queue;
	}
}
