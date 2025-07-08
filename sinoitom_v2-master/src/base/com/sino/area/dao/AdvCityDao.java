package com.sino.area.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.area.entity.AdvCity;
import com.sino.base.common.BaseDao;

/**
* @ClassName: AdvCityDao 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:08:31 
*  
*/ 
@Component
public class AdvCityDao  extends BaseDao<AdvCity, Integer>{

//	根据provinceCode查找市
	public List<AdvCity> getByProV(Integer provinceCode) {
		String hql="from AdvCity where provCode = ?  ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter(0, provinceCode);
		List<AdvCity> list = query.list();
		if(list != null){
			return list;
		}
		return null;
	}

}
