package com.sino.area.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.area.entity.AdvCountry;
import com.sino.base.common.BaseDao;

/**
* @ClassName: AdvCountryDao 
* @Description: TODO 
* @author fengyao
* @date 2017-11-10 上午11:09:33 
*  
*/ 
@Component
public class AdvCountryDao  extends BaseDao<AdvCountry, String>{

	public List<AdvCountry> getAllbyFlag(Integer flag) {
		String hql="from AdvCountry where flag = ?  ";
		Query query=this.getSession().createQuery(hql);
		query.setParameter(0, 1);
		List<AdvCountry> list = query.list();
		if(list != null){
			return list;
		}
		return null;
	}
	
	
	/**
	 * 根据countryCode获取国家
	 * @param flag
	 * @param customerType	Mr.Lp
	 * @return
	 */
	public List<AdvCountry> getAllbyFlagAndCountryCode(Integer flag, Integer customerType) {
		String hql= null;
		if(customerType==1){
			hql="from AdvCountry where flag=? and countryCode = 'CN' ";
			
		}else{
			hql="from AdvCountry where flag=? and countryCode != 'CN' ";
		}
		
		Query query=this.getSession().createQuery(hql);
		query.setParameter(0, 1);
		List<AdvCountry> list = query.list();
		if(list != null){
			return list;
		}
		return null;
	}


}
