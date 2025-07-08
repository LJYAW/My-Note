package com.sino.base.common.util;

import com.sino.base.common.Global;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysLog;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.LogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

/**
 * 系统工具类
 *
 * @author .
 */
public class SystemUtil {
	public static String webAppRootKey = "webapp.root";

	private static Map<String, Object> mapLoginUser = new HashMap<String, Object>();
	
	private static int count=0;
	
	public static String getRootPath()
	{
		return System.getProperty(webAppRootKey);
	}
	
	public static String getRootPath1()
	{
		URL pathClass = Thread.currentThread().getContextClassLoader().getResource("");
		try
		{
			File file = new File(pathClass.toURI());
			String path = file.getAbsolutePath();
			if(path.indexOf("\\") >= 1)
			{
				path = path.replaceAll("\\\\","/");
			}
			int index = path.indexOf("/WEB-INF");
			if(index >= 1)
			{
				path = path.substring(0,index);
			}
			return path.trim();
		}catch(URISyntaxException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将当前活跃用户和当前时间放入到集合中，当前用户为键，时间为值
	 */
	public static synchronized void ActiveLoginUser(String loginUser) {
		Date now = new Date();
		mapLoginUser.put(loginUser, now);
	}
	
	public static synchronized void countInfs() {
		count++;
	}
	
	public static synchronized void initCount(){
		count=0;
	}
	
	public static synchronized int getCount() {
		return count;
	}

	/**
	 * 移除登录用户
	 */
	public static synchronized void RemoveLoginUser(String loginUser) {
		mapLoginUser.remove(loginUser);
	}

	/**
	 * 检查当前登录用户
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
	
	
	public static synchronized int GetLoginUserNum() {
		return mapLoginUser.size();
	}

	
	public static void setLoginUser(SysUser user, String clientIp) {
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute(Global.LOGIN_USER, user);		
		subject.getSession().setAttribute(Global.REMOTE_IP, clientIp);		
		setLoginUserMainOrg(user);
	}

	public static void setLoginUserMainOrg(SysUser user) {
		if( user == null ){
			return;
		}
		OrgOrganization org = ServiceUtil.getOrganService().getOrgan(user.getMainOrgId());
		setLoginUserMainOrg(org);		
	}
	
	public static void setLoginUserMainOrg(OrgOrganization org) {
		if( org == null ){
			return;
		}
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute(Global.LOGIN_USER_MAIN_ORGAN, org);		
		List<OrgOrganization> listOrg = ServiceUtil.getOrganService().findOrganByCode(org.getTreeCode());
		subject.getSession().setAttribute(Global.LOGIN_USER_SUB_ORGAN, listOrg);		
		setLoginUserSubOrgIds(listOrg);
	}
	
	public static void setLoginUserSubOrgIds(List<OrgOrganization> listOrg){
		if( listOrg == null || listOrg.size()==0 ){
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(" (");
		for( int i=0; i<listOrg.size(); i++  ){
			sb.append("'").append(listOrg.get(i).getOrgId()).append("'");
			if( i<listOrg.size()-1 ){
				sb.append(", ");
			}
		}
		sb.append(") ");
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute(Global.LOGIN_USER_SUB_ORGAN_IDS, sb.toString());		
	}
	
	public static SysUser getLoginUser() {
		Subject subject = SecurityUtils.getSubject();
		return (SysUser) subject.getSession().getAttribute(Global.LOGIN_USER);
	}
	
	public static String getLoginUserIp() {
		Subject subject = SecurityUtils.getSubject();
		return (String) subject.getSession().getAttribute(Global.REMOTE_IP);
	}
	
	public static boolean isSuperUser(SysUser user) {
		if( user.getUserType() == Global.USER_TYPE_DEV || user.getLoginName().equals(Global.USER_ADMIN) ){
			return true;		
		}
		return false;
	}
	
	/**
	 * getLoginUserMainOrg
	 * 获取当前用户所诉属的主机构
	 */
	public static OrgOrganization getLoginUserMainOrg() {
		Subject subject = SecurityUtils.getSubject();
		return (OrgOrganization) subject.getSession().getAttribute(Global.LOGIN_USER_MAIN_ORGAN);
	}
	
	/**
	 * getLoginUserSubOrg
	 * 获取当前用户所属的机构的子机构
	 */
	@SuppressWarnings("unchecked")
	public static List<OrgOrganization> getLoginUserSubOrg(){
		Subject subject = SecurityUtils.getSubject();
		List<OrgOrganization> listOrg = (List<OrgOrganization>)subject.getSession().getAttribute(Global.LOGIN_USER_SUB_ORGAN);
		return listOrg;
	}
	
	public static String getLoginUserSubOrgIds(){
		Subject subject = SecurityUtils.getSubject();
		return (String)subject.getSession().getAttribute(Global.LOGIN_USER_SUB_ORGAN_IDS);
	}

	public static boolean addLog(String title, String content){
		SysUser user = getLoginUser();
		if( user == null ){
			return false;
		}
		SysLog log = new SysLog();
		log.setLogTitle(title);
		log.setLogContent(content);
		long curTime = System.currentTimeMillis();
		log.setCreateTime(new Timestamp(curTime));
		log.setCreateTimeValue(curTime);
		log.setUserId(user.getUserId());
		log.setUserName(user.getUserName());
		log.setUserAccount(user.getLoginName());
		OrgOrganization org = getLoginUserMainOrg();
		if( org != null ){
			log.setOrganId(org.getOrgId());
			log.setOrganCode(org.getOrgCode());
			log.setOrganName(org.getOrgName());
		}
		log.setClientAddress(getLoginUserIp());
		LogService logService = ServiceUtil.getLogService();
		logService.addLog(log);
		
		return true;
	}

}
