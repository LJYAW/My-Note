package com.sino.fas.res.mdlware.app.dao;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorCpu;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorThread;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.dao
 * @ClassName: MiddlewareMonitorThreadDao
 * @auther: Mr.Lp
 * @date 2019/5/28 15:16
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Component
public class MiddlewareMonitorThreadDao extends BaseDao<MiddlewareMonitorThread, String> {

    @SuppressWarnings("unchecked")
    public List<MiddlewareMonitorThread> getByMiddleIds(List<String> slist){
        String hql="from MiddlewareMonitorThread where middleId in (:slist) order by monitorTime";
        Query query=this.getSession().createQuery(hql);
        query.setParameterList("slist", slist);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<MiddlewareMonitorThread> getByMonitorId(String monitorId){
        String hql = "from MiddlewareMonitorThread where monitorId =:monitorId";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("monitorId",monitorId);
        return query.list();
    }

    public void deletes(List<String> ids) {
        String hql = " delete MiddlewareMonitorThread where id in (:ids)";
        Query query = this.getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.executeUpdate();
    }
}
