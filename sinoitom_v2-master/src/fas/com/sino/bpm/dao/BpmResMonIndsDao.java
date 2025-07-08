package com.sino.bpm.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.bpm.entity.BpmResMonInds;

import java.util.List;

@Component
public class BpmResMonIndsDao  extends BaseDao<BpmResMonInds,Long>{

    public BpmResMonInds getByCmdParseId(String id) {
        String hql = "select i from BpmResMonInds i,BpmResIndCmdParse c " +
                "where i.resId = c.resId AND i.resTypeIndId = c.resTypeIndId " +
                "AND c.resMonIndItemId=:resMonIndItemId ";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("resMonIndItemId",Long.parseLong(id));
        BpmResMonInds bpmResMonInds=(BpmResMonInds)query.uniqueResult();
        return bpmResMonInds;
    }
}
