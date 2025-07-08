package com.sino.base.system.dao;

import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.base.system.entity.SysParamItem;

@Component
public class SysParamItemDao extends BaseDao<SysParamItem, String> {
	
	public Integer getMaxNumber(final String grpId){
		String hql = "select max(paramNumber) from SysParamItem where sysParamGroup.paramGrpId=? ";
		Integer number = findUnique(hql, grpId);
		return number==null ? 0:number;		
	}
	
	public Integer getItemNumber(final String id){
		String hql = "select paramNumber from SysParamItem where paramId=?";
		Integer number = findUnique(hql, id);
		return number;		
	}

}
