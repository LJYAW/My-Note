/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Threads.java,v 1.1 2012/10/30 05:50:38 sujp Exp $
 */
package org.springside.modules.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程相关工具类.
 * 
 * @author calvin
 */
public class Threads {

	private Threads() {
	}

	/**
	 * sleep等待,单位毫秒,忽略InterruptedException.
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Ignore.
		}
	}

	/**
	 * 按照ExecutorService JavaDoc示例代码编写的Graceful Shutdown方法.
	 * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
	 * 如果超时, 则调用shutdownNow, 取消在workQueue中Pending的任务,并中断所有阻塞函数.
	 * 另对在shutdown时线程本身被调用中断做了处理.
	 */
	public static void gracefulShutdown(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout,
			TimeUnit timeUnit) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
					System.err.println("Pool did not terminate");
				}
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 直接调用shutdownNow的方法, 取消在workQueue中Pending的任务,并中断所有阻塞函数.
	 */
	public static void normalShutdown(ExecutorService pool, int timeout, TimeUnit timeUnit) {
		try {
			pool.shutdownNow();
			if (!pool.awaitTermination(timeout, timeUnit)) {
				System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}

}
