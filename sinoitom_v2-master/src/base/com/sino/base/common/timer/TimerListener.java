package com.sino.base.common.timer;

import java.util.Timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sino.base.common.TelnetPool;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.TelnetUtil;

import java.util.*;

public class TimerListener implements ServletContextListener {
	private Timer timer = null;
	//private Timer timer2=null;
	
	//private MyThread myThread; 
	
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		//timer2.cancel();
		event.getServletContext().log("定时器销毁");
	}

	public void contextInitialized(ServletContextEvent event) {
		// 在这里初始化监听器，在tomcat启动的时候监听器启动
		timer = new Timer(true);
		//timer2=new Timer(true);
		event.getServletContext().log("定时器已启动");// 添加日志，可在tomcat日志（一般在localhost）中查看到

		timer.schedule(new LoginTimerTask(event.getServletContext()), new Date(), 15*1000);
		//timer2.schedule(new SaveConfigTask(), new Date(), 60*1000);
		
//		myThread=new  MyThread();
//		myThread.start();
	}
}
