package com.sino.fas.res.mdlware.app.dao;

import com.sino.base.common.BaseDao;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorHeap;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.dao
 * @ClassName: MiddlewareMonitorInfoDao
 * @auther: Mr.Lp
 * @date 2019/5/28 15:15
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Component
public class MiddlewareMonitorInfoDao extends BaseDao<MiddlewareMonitorInfo, String> {


    @SuppressWarnings("unchecked")
    public List<MiddlewareMonitorInfo> getByIpAndContentAndMonitorTime(String devIpAddr ,String content,String tomcatVersion,String monitorTime){
//        String hql="from MiddlewareMonitorHeap where middleId in (:slist)";
        String hql = "from MiddlewareMonitorInfo where devIpAddr =:devIpAddr and content =:content and tomcatVersion =:tomcatVersion and monitorTime>:monitorTime order by monitorTime";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("devIpAddr",devIpAddr);
        query.setParameter("content",content);
        query.setParameter("tomcatVersion",tomcatVersion);
        query.setParameter("monitorTime",monitorTime);


        return query.list();
    }


    @SuppressWarnings("unchecked")
    public List<MiddlewareMonitorInfo> getByIpAndContentAndTomcatVersion(String devIpAddr ,String content,String tomcatVersion){
//        String hql="from MiddlewareMonitorHeap where middleId in (:slist)";
        String hql = "from MiddlewareMonitorInfo where devIpAddr =:devIpAddr and content =:content and tomcatVersion =:tomcatVersion order by monitorTime desc";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("devIpAddr",devIpAddr);
        query.setParameter("content",content);
        query.setParameter("tomcatVersion",tomcatVersion);


        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<MiddlewareMonitorInfo> getByMonitorId(String monitorId){
        String hql = "from MiddlewareMonitorInfo where monitorId =:monitorId order by monitorTime desc";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("monitorId",monitorId);
        return query.list();
    }

    public void deletes(List<String> ids) {
        String hql = " delete MiddlewareMonitorInfo where middleId in (:ids)";
        Query query = this.getSession().createQuery(hql);
        query.setParameterList("ids", ids);
        query.executeUpdate();
    }

}
