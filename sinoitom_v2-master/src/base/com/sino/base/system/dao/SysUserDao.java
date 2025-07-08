package com.sino.base.system.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.base.system.entity.SysUser;

@Component
public class SysUserDao extends BaseDao<SysUser, String> {

	public List<SysUser> getByOrgIds(List<String> orgIds) {
		String hql = "from SysUser where mainOrgId in (:orgIds)";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("orgIds", orgIds);
		List<SysUser> list = query.list();
		return list;
	}

	public void deleteByOrgIds(List<String> ids) {
		String hql = "delete from SysUser where mainOrgId in (:orgIds)";
		Query query=this.getSession().createQuery(hql);
		query.setParameterList("orgIds", ids);
		query.executeUpdate();
	}
}
