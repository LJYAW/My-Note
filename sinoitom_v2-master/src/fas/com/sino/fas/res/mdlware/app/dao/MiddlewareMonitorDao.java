package com.sino.fas.res.mdlware.app.dao;


import com.sino.base.common.BaseDao;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitor;
import org.hibernate.Query;
import org.springframework.stereotype.Component;


@Component
public class MiddlewareMonitorDao extends BaseDao<MiddlewareMonitor, String> {

    public void updateMiddlewareMonitor(String id,String monitorTime,String startTime,String uptime){
        String hql="update MiddlewareMonitor set monitorTime=:monitorTime,startTime=:startTime,uptime=:uptime,status=1 where id=:id ";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("id", id);
        query.setParameter("monitorTime", monitorTime);
        query.setParameter("startTime", startTime);
        query.setParameter("uptime", uptime);


        query.executeUpdate();
    }

}
