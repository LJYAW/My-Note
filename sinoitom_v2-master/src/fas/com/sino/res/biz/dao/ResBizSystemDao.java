package com.sino.res.biz.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.res.biz.entity.ResBizSystem;

import java.util.List;

@Component
public class ResBizSystemDao extends BaseDao<ResBizSystem, Long> {

    public List<ResBizSystem> getEnableBizs(Integer id) {
        String hql="from ResBizSystem where status=1 and resId not in(select bizId from BpmTaskMonBiz where taskId=:taskId)";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("taskId",id);
        List list = query.list();
        return list;
    }
}
