/**
 * 
 */
package com.sino.fas.res.mdlware.app.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.mdlware.app.entity.ResMdlWare;

/**
 * @author Mr.LP
 * @date 2019-11-19下午5:26:23
 * @className ResMdlWareDao
 *
 * @Description TODO
 *
 */

@Component
public class ResMdlWareDao extends BaseDao<ResMdlWare, Long> {
	
	public void updateResMdlWare(Long resId,String monTime){
        String hql="update ResMdlWare set monTime=:monTime,monStatus=1 where resId=:resId ";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("resId", resId);
        query.setParameter("monTime", monTime);

        query.executeUpdate();
    }
}
