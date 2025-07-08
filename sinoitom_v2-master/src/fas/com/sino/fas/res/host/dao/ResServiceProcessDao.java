/**
 * 
 */
package com.sino.fas.res.host.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.host.entity.ResServiceProcess;

/**
 * @author Mr.LP
 * @date 2019-10-17下午2:27:56
 * @className ResServiceProcessDao
 *
 * @Description TODO
 *
 */

@Component
public class ResServiceProcessDao extends BaseDao<ResServiceProcess,Long>{

	public List<ResServiceProcess> getListByPids(List<Integer> pids){
		String sql = "FROM ResServiceProcess where pid in (:pid)";
		Query query=this.getSession().createQuery(sql);
		query.setParameterList("pid", pids); 
		return query.list();
	}
}
