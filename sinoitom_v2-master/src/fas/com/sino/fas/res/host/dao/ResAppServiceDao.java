/**
 * 
 */
package com.sino.fas.res.host.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.BaseDao;
import com.sino.fas.res.host.entity.ResAppService;

/**
 * @author Mr.LP
 * @date 2019-10-17下午3:04:31
 * @className ResAppServiceDao
 *
 * @Description TODO
 *
 */

@Component
public class ResAppServiceDao extends BaseDao<ResAppService,Long>{
	
	public List<ResAppService> getDataList(Long resId) {
		String sql = "from ResAppService where appSvcId not in (select appSvcId from ResAppServiceMap where bizAppId=:resId)";
		Query query=this.getSession().createQuery(sql);
		query.setParameter("resId", resId);
		return query.list();
		
	
	}
	
	public List<ResAppService> getListByHostIp(String hostIp,Long resId) {
		String sql = "from ResAppService where hostIp=:hostIp and appSvcId not in (select appSvcId from ResAppServiceMap where bizAppId=:resId)";
		Query query=this.getSession().createQuery(sql);
		query.setParameter("hostIp", hostIp);
		query.setParameter("resId", resId);
		return query.list();
		
	
	}
	
	
	public List<ResAppService> getByHostIpAndResIdAndSvcId(String hostIp,Long resId,List<Long> svcIdList) {
		String sql = "from ResAppService where hostIp=:hostIp and appSvcId not in (select appSvcId from ResAppServiceMap where bizAppId=:resId) and appSvcId not in (:svcIdList)";
		Query query=this.getSession().createQuery(sql);
		query.setParameter("hostIp", hostIp);
		query.setParameter("resId", resId);
		query.setParameterList("svcIdList", svcIdList); 
		return query.list();
		
	
	}
	
	
	public List<ResAppService> getByResIdAndSvcId(Long resId,List<Long> svcIdList) {
		String sql = "from ResAppService where appSvcId not in (select appSvcId from ResAppServiceMap where bizAppId=:resId) and appSvcId not in (:svcIdList)";
		Query query=this.getSession().createQuery(sql);
		query.setParameter("resId", resId);
		query.setParameterList("svcIdList", svcIdList); 
		return query.list();
		
	
	}
	
	public List<ResAppService> getBybizAppId(Long bizAppId){
		String hql=" FROM ResAppService where appSvcId in (SELECT appSvcId from ResAppServiceMap where bizAppId=:bizAppId)";
		Query query=this.getSession().createQuery(hql);
		query.setParameter("bizAppId", bizAppId);
		return query.list();
	}

}
