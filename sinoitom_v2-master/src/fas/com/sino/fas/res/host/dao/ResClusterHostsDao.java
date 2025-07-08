/**
 * 
 */
package com.sino.fas.res.host.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.host.entity.ResClusterHosts;
import com.sino.fas.res.host.entity.ResHosts;

/**
 * @author Mr.LP
 * @date 2019-12-11上午11:10:19
 * @className ResClusterHostsDao
 * @version V1.0
 * @Description TODO
 *
 */

@Component
public class ResClusterHostsDao extends BaseDao<ResClusterHosts,Long>{
	
	
	@SuppressWarnings("unchecked")
	public List<ResHosts> getLegalHosts(){
		StringBuffer hql=new StringBuffer("from ResHosts where hostType!=3 and hostType!=2 and hostId not in (select hostId from ResClusterHosts ) order by ipLong");
		Query query=this.getSession().createQuery(hql.toString());  
		return query.list();
	}

}
