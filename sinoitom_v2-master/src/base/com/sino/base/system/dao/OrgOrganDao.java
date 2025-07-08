package com.sino.base.system.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.tree.TreeDao;
import com.sino.base.system.entity.OrgOrganization;

@Component
public class OrgOrganDao extends TreeDao<OrgOrganization, String> {

//	获取当前用户所在运营商的机构以及子机构
	public List<OrgOrganization> getCurrentUserAllOperator(String treeCode) {
		String hql="from OrgOrganization where OrgClass=1 and treeCode like ?  order by treeCode asc" ;
		Query query=this.getSession().createQuery(hql);
		query.setParameter(0, treeCode+"%");
		List<OrgOrganization> list = query.list();
		if(list != null){
			return list;
		}
		return null;
	}

//	获取所有的运营商机构信息
	public List<OrgOrganization> getAllOperator() {
		String hql="from OrgOrganization where OrgClass=1 order by treeCode asc" ;
		Query query=this.getSession().createQuery(hql);
		List<OrgOrganization> list = query.list();
		if(list != null){
			return list;
		}
		return null;
	}
	
	public List<OrgOrganization> getAllExceptDepart(Integer orgClass) {
		String hql="from OrgOrganization where state=1 and orgType<9 and orgClass=? order by treeCode asc" ;
		Query query=this.getSession().createQuery(hql);
		query.setParameter(0, orgClass);
		List<OrgOrganization> list = query.list();
		if(list != null){
			return list;
		}
		return null;
	}

}
