package com.sino.base.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sino.base.common.telnet.TelnetConnection;

public class ThreadPool {

	private static ExecutorService executorService = Executors.newSingleThreadExecutor();
	
//	private static int worker_num=5;
	
//	private static ExecutorService executorService2 = Executors.newFixedThreadPool(worker_num);
	
	private static Map<String,ExecutorService> threadMap=new HashMap<String,ExecutorService>();
	
	

	synchronized public static void execute(Runnable command) {
		executorService.execute(command);
    }
    
    public static void createThread(String tenantId,Runnable task){
    	Set<String> keys = threadMap.keySet();
		if(keys.contains(tenantId)){
			 ExecutorService executor= threadMap.get(tenantId);
			 executor.execute(task);
//			 if(flag){
//				 executor.shutdown();
//				 while(true){
//					 if(executor.isTerminated()){
//						 System.out.println("任务执行完毕。。。。1");
//						 threadMap.remove(tenantId);
//						 break;
//					 }
//				 }
//			 }
			 
		}else{
			ExecutorService executor = Executors.newSingleThreadExecutor();
			threadMap.put(tenantId, executor);
			executor.execute(task);
		}
    }
    
    
    public static void main(String[] args) {
    	
    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c11", new MyRunnable2("315d9a7e913c42979b3b755707393c11","vrf context t123"));
//    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c11", new MyRunnable("315d9a7e913c42979b3b755707393c11","t123","3000","192.168.1.253","192.168.1.1"));
//    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c12", new MyRunnable2("315d9a7e913c42979b3b755707393c12","vrf context t456"));
//    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c12", new MyRunnable("315d9a7e913c42979b3b755707393c12","t456","3001","192.168.2.253","192.168.2.1"));
//    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c13", new MyRunnable2("315d9a7e913c42979b3b755707393c13","vrf context t789"));
//    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c11", new MyRunnable2("315d9a7e913c42979b3b755707393c11","no interface vlan 3000"));
//    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c12", new MyRunnable2("315d9a7e913c42979b3b755707393c12","no interface vlan 3001"));
//    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c13", new MyRunnable2("315d9a7e913c42979b3b755707393c13","no vrf context t789"));
//    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c12", new MyRunnable2("315d9a7e913c42979b3b755707393c12","no vrf context t456"));
//    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c11", new MyRunnable2("315d9a7e913c42979b3b755707393c11","no vrf context t123"));
    	
    	
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c11",new MyRunnable3("315d9a7e913c42979b3b755707393c11"));
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c12",new MyRunnable3("315d9a7e913c42979b3b755707393c12"));
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c13",new MyRunnable3("315d9a7e913c42979b3b755707393c13"));
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c11",new MyRunnable3("315d9a7e913c42979b3b755707393c11"));
//    	ThreadPool.createThread("315d9a7e913c42979b3b755707393c12",new MyRunnable3("315d9a7e913c42979b3b755707393c12"));
    	
    	
    	
    	
    	ThreadPoolExecutor threadPool =new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1)); 
    	threadPool.execute(new MyRunnable4());
    	threadPool.execute(new MyRunnable4());
    	threadPool.execute(new MyRunnable4());
    	threadPool.execute(new MyRunnable4());
    	
    	while(true){
    		System.out.println(threadPool.getActiveCount());
    	}
    	
	}
    
}


class MyRunnable implements Runnable{
	
	 private String vrf;
	 private String vlanNo;
	 private String tenantId;
	 private String ip1;
	 private String ip2;
	 public MyRunnable(String tenantId,String vrf,String vlanNo,String ip1,String ip2){
		 this.vrf=vrf;
		 this.vlanNo=vlanNo;
		 this.tenantId=tenantId;
		 this.ip1=ip1;
		 this.ip2=ip2;
	 }
	 
	 public MyRunnable(String tenantId,String vrf){
		 this.vrf=vrf;
		 this.tenantId=tenantId;
		
	 }
	
	public void run() {
		try {
			final TelnetConnection telnet = TelnetPool.getTelnet(this.tenantId,"172.16.11.100");
			if (telnet != null) {
				if(telnet.isConnect("172.16.11.100")){
					telnet.initPrompt("#");
					  telnet.sendCommand("config");
					  telnet.sendCommand("vlan "+this.vlanNo);
					  telnet.sendCommand("interface Vlan "+this.vlanNo);
					  telnet.sendCommand("vrf member "+this.vrf);
					  telnet.sendCommand("ip address "+this.ip1+" 255.255.255.0");
					  telnet.sendCommand("hsrp version 2");
					  telnet.sendCommand("hsrp "+this.vlanNo);
					  telnet.sendCommand("ip "+this.ip2);
					  telnet.sendCommand("exit");
					  telnet.sendCommand("no shutdown");
					  telnet.sendCommand("end");
					  
					  ThreadPool.execute(new Runnable(){

						public void run() {
//							  telnet.initPrompt("Copy complete.");
//					          telnet.sendExitCommand("copy running startup");
//					          telnet.sendCommand("copy running startup");
						}
					  });
			         
//			          
				}
			}
		} catch (Exception e) {
			System.out.println("Exception");
		}

	}
	
}


class MyRunnable2 implements Runnable{
	
	 private String vrf;
	 private String vlanNo;
	 private String tenantId;
	 private String ip1;
	 private String ip2;
	 public MyRunnable2(String tenantId,String vlanNo,String ip1,String ip2){
		 this.vlanNo=vlanNo;
		 this.tenantId=tenantId;
		 this.ip1=ip1;
		 this.ip2=ip2;
	 }
	 
	 
	 public MyRunnable2(String tenantId,String vrf){
		 this.vrf=vrf;
		 this.tenantId=tenantId;
		
	 }
	
	public void run() {
		try {
			final TelnetConnection telnet = TelnetPool.getTelnet(this.tenantId,"172.16.11.100");
			if (telnet != null) {
				if(telnet.isConnect("172.16.11.100")){
					telnet.initPrompt("#");
					  telnet.sendCommand("config");
			          telnet.sendCommand(this.vrf);
			          telnet.sendCommand("end");
			          ThreadPool.execute(new Runnable(){

							public void run() {
//								  telnet.initPrompt("Copy complete.");
//						          telnet.sendExitCommand("copy running startup");
//						          telnet.sendCommand("copy running startup");
							}
						  });
			          
				}
			}
		} catch (Exception e) {
			System.out.println("Exception");
		}
		
	}
	
}

class MyRunnable3 implements Runnable{
	
	 private String tenantId;
	 public MyRunnable3(String tenantId){
		 this.tenantId=tenantId;
		
	 }
	 
	public void run() {
		final TelnetConnection telnet = TelnetPool.getTelnet(this.tenantId,"172.16.11.100");
		if(telnet.isConnect("172.16.11.100")){
//			while(true){
//			System.out.println(this.tenantId);
			try {
				
				System.out.println(this.tenantId);
				telnet.sendCommand("conf");
				telnet.initPrompt("Copy complete.");
		        telnet.sendCommand("copy running startup");
//		        telnet.sendCommand("sh ver");
		      
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
				
//			}
			
		}
		
		
	}
	
}


class MyRunnable4 implements Runnable{
	public void run() {
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		final TelnetConnection telnet = TelnetPool.getTelnet("315d9a7e913c42979b3b755707393c11","172.16.11.100");
		if(telnet.isConnect("172.16.11.100")){
			try {
				
				telnet.initPrompt("Copy complete.");
//		        telnet.sendCommand("copy running startup");
		        telnet.sendExitCommand("copy running startup");
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
		
	}
	
}