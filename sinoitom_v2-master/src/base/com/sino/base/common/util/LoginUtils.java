package com.sino.base.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class LoginUtils {
	private static Map<String, Object> mapLoginUser = new HashMap<String, Object>();

	
	/**
	 * ActiveLoginUser
	 * @Description: 添加登录用户
	 * @param loginUser	登录用户
	 * @return: void
	 */
	public static synchronized void ActiveLoginUser(String loginUser) {
		Date now = new Date();
		mapLoginUser.put(loginUser, now);
	}

	/**
	 * RemoveLoginUser
	 * @Description: 移除登录用户
	 * @param loginUser	登录用户
	 * @return: void
	 */
	public static synchronized void RemoveLoginUser(String loginUser) {
		mapLoginUser.remove(loginUser);
	}

	/**
	 * CheckLoginUser
	 * @Description: 检查用户最后在线时间，超过1分钟，移除用户
	 * @return: void
	 */
	public static synchronized void CheckLoginUser() {
		Iterator<Entry<String, Object>> iter = mapLoginUser.entrySet().iterator();    
		while(iter.hasNext()) {    
		    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();    
		    Date value = (Date)entry.getValue();    
		    Date now = new Date();
		    Long intv = now.getTime() - value.getTime();
		    if( intv > 60000 ){
		    	iter.remove();
		    }
		} 
	}

	/**
	 * GetLoginUserNum
	 * @Description: 获取登录用户数
	 * @return: int
	 */
	public static synchronized int GetLoginUserNum() {
		return mapLoginUser.size();
	}
}
