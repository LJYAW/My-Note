package com.sino.base.system.dao;

import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.base.system.entity.SysParamGroup;

@Component
public class SysParamGroupDao extends BaseDao<SysParamGroup, String> {

	public Integer getMaxNumber(){
		String hql = "select max(grpNumber) from SysParamGroup";
		Integer number = findUnique(hql);
		return number==null ? 0:number;		
	}
	
	public Integer getGrpNumber(final String id){
		String hql = "select grpNumber from SysParamGroup where paramGrpId=?";
		Integer number = findUnique(hql, id);
		return number;		
	}
	
}
