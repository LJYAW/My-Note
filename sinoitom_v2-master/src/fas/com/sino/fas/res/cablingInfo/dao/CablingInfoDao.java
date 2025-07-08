package com.sino.fas.res.cablingInfo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.cablingInfo.entity.NcmCablingInfo;

@Component
public class CablingInfoDao  extends BaseDao<NcmCablingInfo,String>{
	@SuppressWarnings("unchecked")
	public List<NcmCablingInfo> getCablingInfoByIds(List<String> ids){
		Query query=this.getSession().createQuery("from NcmCablingInfo where cablingInfoID in (:ids) order by infoPointNo ");  
		query.setParameterList("ids", ids); 
		return query.list();
	}
	
}
