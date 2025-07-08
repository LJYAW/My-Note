package com.sino.base.system.service;

import com.sino.base.common.ServiceException;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.dao.SysUserDao;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import java.util.List;


/**
 * 系统用户管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class UserService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	private static String objAttNames = "userId,userType@userType,loginName,userName,mainOrgId@OrgOrganization@orgId@orgShortName";
	private static String jsonAttNames = "id,userType,loginName,userName,mainOrgan";

	public String lastMassage = "";

	@Autowired
	private SysUserDao userDao;

	@Autowired(required = false)
	private ShiroDbRealm shiroRealm;

	//-- User Manager --//
	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysUser obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysUser> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public List<SysUser> getAllUser() {
		return userDao.getAll("createTime", true);
	}

	@Transactional(readOnly = true)
	public List<SysUser> findAllManageUser(SysUser user, OrgOrganization organ) {
		String hql = "select a from SysUser a, OrgOrganization b where  a.mainOrgId = b.orgId and b.treeCode like ? and (a.userType<=? or a.userId=?) order by b.treeCode, a.createTime";
		List<SysUser> list = userDao.find(hql, organ.getTreeCode()+"%", user.getUserType(),user.getUserId());
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<SysUser> searchUser(final List<PropertyFilter> filters) {
		return userDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysUser> searchUser(final PageRequest page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public SysUser getUser(String id) {
		return userDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public boolean loadUserAtts(SysUser entity, String attNames){
		return userDao.loadObjAtts(entity, attNames);
	}

	public void addUser(SysUser entity) {
		logger.debug("addUser...");
		entity.setUserId(Identities.uuid());
		userDao.save(entity);
	}

	public void saveUser(SysUser entity) {
		logger.debug("saveUser...");
		userDao.save(entity);
		
		shiroRealm.clearCachedAuthorizationInfo(entity.getLoginName());
	}

	
	/**
	 * deleteUser
	 * @Description: 删除用户,如果尝试删除超级管理员将抛出异常.
	 * @param id
	 * @return: void
	 */
	public void deleteUser(String id) {
		logger.debug("deleteUser(id:{})...", id);
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SecurityUtils.getSubject().getPrincipal());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}
	
	/**
	 * isLoginNameUnique
	 * @Description: 判断用户名是否唯一
	 * @param userId
	 * @param loginName
	 * @return: boolean
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String userId, String loginName) {
		return userDao.isIdAttUnique(userId, "loginName", loginName);
	}

	
	/**
	 * isSupervisor
	 * @Description: 判断是否是管理员
	 * @param id
	 * @return: boolean
	 */
	private boolean isSupervisor(String id) {
		return "devadmin".endsWith(id);
	}

	/**
	 * findUserByLoginName
	 * @Description: 根据登录账号查找用户
	 * @param loginName
	 * @return: SysUser
	 */
	@Transactional(readOnly = true)
	public SysUser findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}
	
	/**
	 * getByOrgId
	 * @Description: 根据orgID查找用户列表
	 * @param orgId
	 * @return: List<SysUser>
	 */
	@Transactional(readOnly = true)
	public List<SysUser> getByOrgId(String orgId) {
		String hql=" from SysUser where mainOrgId=?";
		return userDao.find(hql, orgId);
	}

    public List<SysUser> getAllPoUser() {
		String hql = "from SysUser where userType <> ? and userType <> ?";
		List<SysUser> list = userDao.find(hql, 8, 9);
		return list;
	}

	public List<SysUser> findByType() {
		String hql = "from SysUser where userType < ?";
		List<SysUser> list = userDao.find(hql, 8);
		return list;
	}
	
	public List<SysUser> getByOrgIds(List<String> orgIds){
		List<SysUser> list = userDao.getByOrgIds(orgIds);
		return list;
	}

	public List<SysUser> getAllOperatorUser() {
		String hql = "from SysUser where MainOrgId in (select orgId from OrgOrganization where orgClass=1 )";
		List<SysUser> list = userDao.find(hql);
		return list;
	}
}
