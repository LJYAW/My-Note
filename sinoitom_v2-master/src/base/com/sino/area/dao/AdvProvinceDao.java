package com.sino.area.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.area.entity.AdvProvince;
import com.sino.base.common.BaseDao;


/**
* @ClassName: AdvProvinceDao 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:09:39 
*  
*/ 
@Component
public class AdvProvinceDao  extends BaseDao<AdvProvince, Integer>{

//	根据国家查找省
	public List<AdvProvince> getByCountry(String countryCode) {
		String hql="from AdvProvince where countryCode = ?  ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter(0, countryCode);
		List<AdvProvince> list = query.list();
		if(list != null){
			return list;
		}
		return null;
	}

}
