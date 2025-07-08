package com.sino.area.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.area.entity.AdvCounty;
import com.sino.base.common.BaseDao;

/**
* @ClassName: AdvCountyDao 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:09:37 
*  
*/ 
@Component
public class AdvCountyDao  extends BaseDao<AdvCounty, Integer>{
//	根据cityCode查找所有的区
	public List<AdvCounty> getByCity(Integer cityCode) {
		String hql="from AdvCounty where cityCode = ?  ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter(0, cityCode);
		List<AdvCounty> list = query.list();
		if(list != null){
			return list;
		}
		return null;
	}
}
