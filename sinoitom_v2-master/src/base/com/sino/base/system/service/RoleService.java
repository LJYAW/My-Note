package com.sino.base.system.service;

import com.sino.base.common.Global;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.dao.SysRoleDao;
import com.sino.base.system.entity.SysRole;
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
 * 系统角色管理业务处理层
 *
 * @author sujp
 * @date 2017年5月3日 上午11:31:42
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class RoleService {
	private static Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	private static String objAttNames = "roleId,roleName,roleCode,description";
	private static String jsonAttNames = "id,roleName,roleCode,description";

	@Autowired
	private SysRoleDao roleDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final SysRole obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	/**
	 * getJsonListStr
	 * @Description: 将数据转换为json串
	 * @param list 角色集合
	 * @return: String
	 */
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<SysRole> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	/**
	 * getAllRole
	 * @Description: 获取所有角色
	 * @return: List<SysRole>
	 */
	@Transactional(readOnly = true)
	public List<SysRole> getAllRole() {
		return roleDao.getAll("roleName", true);
	}

	@Transactional(readOnly = true)
	public List<SysRole> getAllUserRole(Integer userType) {
		if( userType == Global.USER_TYPE_DEV ){
			return roleDao.getAll("roleName", true);
		}

		String hql = "from SysRole where state=1 order by roleName";
		return roleDao.find(hql);
	}
	
	/**
	 * searchRole
	 * @Description: 根据条件搜索用户
	 * @param filters 条件
	 * @return: List<SysRole>
	 */
	@Transactional(readOnly = true)
	public List<SysRole> searchRole(final List<PropertyFilter> filters) {
		return roleDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<SysRole> searchRole(final PageRequest page, final List<PropertyFilter> filters) {
		return roleDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public SysRole getRole(String id) {
		return roleDao.get(id);
	}

	/**
	 * addRole
	 * @Description: 添加角色，并设置角色id
	 * @param entity 角色实体
	 * @return: void
	 */
	public void addRole(SysRole entity) {
		logger.debug("addRole...");
		entity.setRoleId(Identities.uuid());
		roleDao.save(entity);
	}

	/**
	 * saveRole
	 * @Description: 保存角色
	 * @param entity 角色实体类
	 * @return: void
	 */
	public void saveRole(SysRole entity) {
		logger.debug("saveRole...");
		roleDao.save(entity);
	}

	/**
	 * deleteRole
	 * @Description: 根据角色id删除角色
	 * @param id 角色id
	 * @return: void
	 */
	public void deleteRole(String id) {
		logger.debug("deleteRole(id:{})...", id);
		roleDao.delete(id);
	}
	
	/**
	 * isRoleNameUnique
	 * @Description: 判断角色名称是否唯一
	 * @param roleId 角色id
	 * @param roleName 角色名称
	 * @return: boolean
	 */
	@Transactional(readOnly = true)
	public boolean isRoleNameUnique(String roleId, String roleName) {
		return roleDao.isIdAttUnique(roleId, "roleName", roleName);
	}
	
}
