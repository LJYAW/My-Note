package com.sino.base.system.dao;

import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.base.system.entity.SysPower;

@Component
public class SysPowerDao extends BaseDao<SysPower, String> {

	public Integer getMaxNumber(final String grpId){
		String hql = "select max(powNumber) from SysPower where sysPowerGroup.powGrpId=?";
		Integer number = findUnique(hql, grpId);
		return number==null ? 0:number;
	}
	
	public Integer getPowNumber(final String id){
		String hql = "select powNumber from SysPower where powId=?";
		Integer number = findUnique(hql, id);
		return number;		
	}

}
