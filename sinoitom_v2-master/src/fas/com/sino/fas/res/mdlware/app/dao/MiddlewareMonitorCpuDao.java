package com.sino.fas.res.mdlware.app.dao;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorCpu;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.dao
 * @ClassName: MiddlewareMonitorCpuDao
 * @auther: Mr.Lp
 * @date 2019/5/28 15:17
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Component
public class MiddlewareMonitorCpuDao extends BaseDao<MiddlewareMonitorCpu, String> {

    @SuppressWarnings("unchecked")
    public List<MiddlewareMonitorCpu> getByMiddleIds(List<String> slist){
        String hql="from MiddlewareMonitorCpu where middleId in (:slist) order by monitorTime";
        Query query=this.getSession().createQuery(hql);
        query.setParameterList("slist", slist);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<MiddlewareMonitorCpu> getByMonitorId(String monitorId){
        String hql = "from MiddlewareMonitorCpu where monitorId =:monitorId";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("monitorId",monitorId);
        return query.list();
    }

    public void deletes(List<String> ids) {
        String hql = " delete MiddlewareMonitorCpu where id in (:ids)";
        Query query = this.getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.executeUpdate();
    }
}
