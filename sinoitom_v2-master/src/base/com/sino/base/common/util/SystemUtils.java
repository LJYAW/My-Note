package com.sino.base.common.util;

import com.sino.base.common.Global;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysLog;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.entity.SysUserOperation;
import com.sino.base.system.service.LogService;
import com.sino.base.system.service.UserOperationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.sql.Timestamp;
import java.util.List;

/**
 * 系统工具类，包括获取当前登录用户，登录用户客户端信息等
 *
 * @author .
 * @date 2017年5月3日 上午9:40:12
 */
public class SystemUtils {

	/**
	 * setLoginUser
	 * 将用户信息，登录的IP存放到session中
	 *
	 * @param user     当前登录用户
	 * @param clientIp 用户客户端IP
	 */
	public static void setLoginUser(SysUser user, String clientIp) {
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute(Global.LOGIN_USER, user);
		subject.getSession().setAttribute(Global.REMOTE_IP, clientIp);
		setLoginUserMainOrg(user);
	}


	/**
	 * setLoginUserMainOrg
	 * 设置登录用户的所属机构
	 *
	 * @param user 当前登录用户
	 * @return: void
	 */
	public static void setLoginUserMainOrg(SysUser user) {
		if (user == null) {
			return;
		}
		OrgOrganization org = ServiceUtils.getOrganService().getOrgan(user.getMainOrgId());
		setLoginUserMainOrg(org);
	}

	/**
	 * setLoginUserMainOrg
	 * 将用户所属机构和机构树存储到session中，页面获取后显示
	 *
	 * @param org 当前登录用户所属机构
	 * @return: void
	 */
	public static void setLoginUserMainOrg(OrgOrganization org) {
		if (org == null) {
			return;
		}
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute(Global.LOGIN_USER_MAIN_ORGAN, org);
		List<OrgOrganization> listOrg = ServiceUtils.getOrganService().findOrganByCode(org.getTreeCode());
		subject.getSession().setAttribute(Global.LOGIN_USER_SUB_ORGAN, listOrg);
		setLoginUserSubOrgIds(listOrg);
	}

	/**
	 * setLoginUserSubOrgIds
	 * 设置登录用户所属子机构
	 *
	 * @param listOrg 用户所属机构集合
	 * @return void
	 */
	public static void setLoginUserSubOrgIds(List<OrgOrganization> listOrg) {
		if (listOrg == null || listOrg.size() == 0) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(" (");
		for (int i = 0; i < listOrg.size(); i++) {
			sb.append("'").append(listOrg.get(i).getOrgId()).append("'");
			if (i < listOrg.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(") ");
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute(Global.LOGIN_USER_SUB_ORGAN_IDS, sb.toString());
	}

	/**
	 * getLoginUser
	 * <p>
	 * 获取登录用户信息
	 *
	 * @return SysUser 登录用户
	 */
	public static SysUser getLoginUser() {
		Subject subject = SecurityUtils.getSubject();
		return (SysUser) subject.getSession().getAttribute(Global.LOGIN_USER);
	}

	//网站用户
	/**
	 * getLoginUserIp
	 * <p>
	 * 获取登录用户的IP地址
	 *
	 * @return String    返回IP地址
	 */
	public static String getLoginUserIp() {
		Subject subject = SecurityUtils.getSubject();
		return (String) subject.getSession().getAttribute(Global.REMOTE_IP);
	}

	public static String getwebLoginUserIp() {
		Subject subject = SecurityUtils.getSubject();
		return (String) subject.getSession().getAttribute(Global.WEB_REMOTE_IP);
	}

	/**
	 * getLoginUserMainOrg
	 * 获取登录用户主机构
	 *
	 * @return OrgOrganization 返回主机构
	 */
	public static OrgOrganization getLoginUserMainOrg() {
		Subject subject = SecurityUtils.getSubject();
		return (OrgOrganization) subject.getSession().getAttribute(Global.LOGIN_USER_MAIN_ORGAN);
	}

	/**
	 * getLoginUserSubOrg
	 * 获取登录用户所有子机构
	 *
	 * @return: List<OrgOrganization> 返回子机构集合
	 */
	@SuppressWarnings("unchecked")
	public static List<OrgOrganization> getLoginUserSubOrg() {
		Subject subject = SecurityUtils.getSubject();
		List<OrgOrganization> listOrg = (List<OrgOrganization>) subject.getSession()
				.getAttribute(Global.LOGIN_USER_SUB_ORGAN);
		return listOrg;
	}

	/**
	 * getLoginUserSubOrgIds
	 * 获取登录用户所有子机构IDs
	 *
	 * @return: String 返回子机构IDs
	 */
	public static String getLoginUserSubOrgIds() {
		Subject subject = SecurityUtils.getSubject();
		return (String) subject.getSession().getAttribute(Global.LOGIN_USER_SUB_ORGAN_IDS);
	}

	/**
	 * isSuperUser
	 * 判断用户是否是开发用户：用户类型为9或者用户名为admin
	 *
	 * @param user 当前登录用户
	 * @return: boolean 是否
	 */
	public static boolean isSuperUser(SysUser user) {
		if (user.getUserType() == Global.USER_TYPE_DEV || user.getLoginName().equals(Global.USER_ADMIN)) {
			return true;
		}
		return false;
	}

	/**
	 * addLog
	 * 添加日志，可以设置操作类型
	 *
	 * @param title       日志标题
	 * @param content     日志内容
	 * @param operateType 0:登录退出，1:路由器操作，2:交换机操作
	 * @return: boolean 是否成功
	 */
	public static boolean addLog(String title, String content, int operateType) {
		SysUser user = getLoginUser();
		if (user == null) {
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
		log.setOperateType(operateType);
		OrgOrganization org = getLoginUserMainOrg();
		if (org != null) {
			log.setOrganId(org.getOrgId());
			log.setOrganCode(org.getOrgCode());
			log.setOrganName(org.getOrgName());
		}
		log.setClientAddress(getLoginUserIp());
		LogService logService = ServiceUtils.getLogService();
		logService.addLog(log);
		return true;
	}

	/**
	 * operation
	 * 添加日志，可以设置操作类型
	 *
	 * @param operation 操作对象
	 * @return: boolean 是否成功
	 */
	public static boolean addUserOperLog(SysUserOperation operation) {
		SysUser user = getLoginUser();
		if (user == null) {
			return false;
		}
		operation.setUserId(user.getUserId());
		operation.setUserName(user.getUserName());

		long curTime = System.currentTimeMillis();
		operation.setOpTime(new Timestamp(curTime));

		UserOperationService operService = ServiceUtils.getUserOperationService();
		operService.save(operation);
		return true;
	}

	/**
	 * addLog
	 * 添加日志记录
	 *
	 * @param title   日志标题
	 * @param content 日志内容
	 * @return: boolean 是否添加成功
	 */
	public static boolean addLog(String title, String content) {
		SysUser user = getLoginUser();
		if (user == null) {
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
		log.setOperateType(0);
		OrgOrganization org = getLoginUserMainOrg();
		if (org != null) {
			log.setOrganId(org.getOrgId());
			log.setOrganCode(org.getOrgCode());
			log.setOrganName(org.getOrgName());
		}
		log.setClientAddress(getLoginUserIp());
		LogService logService = ServiceUtils.getLogService();
		logService.addLog(log);

		return true;
	}

}
