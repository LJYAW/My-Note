package com.sino.base.system.dao;

import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.base.system.entity.SysResGroup;

@Component
public class SysResGroupDao extends BaseDao<SysResGroup, String> {

	public Integer getMaxNumber(){
		String hql = "select max(grpNumber) from SysResGroup";
		Integer number = findUnique(hql);
		return number==null ? 0:number;		
	}
	
	public Integer getGrpNumber(final String id){
		String hql = "select grpNumber from SysResGroup where resGrpId=?";
		Integer number = findUnique(hql, id);
		return number;		
	}
	
	public Integer getGrpType(final String id){
		String hql = "select grpType from SysResGroup where resGrpId=?";
		Integer number = findUnique(hql, id);
		return number;		
	}

}
