package com.sino.bpm.dao;

import com.sino.base.common.BaseDao;
import com.sino.bpm.entity.BpmResIndSnmpOids;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BpmResIndSnmpOidsDao
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/19 11:31
 * @Version 1.0
 **/
@Component
public class BpmResIndSnmpOidsDao extends BaseDao<BpmResIndSnmpOids,Integer> {
    public List<BpmResIndSnmpOids> getRelatedList(String ids) {
        String hql=" from BpmResIndSnmpOids where resId in(SELECT resId FROM BpmTaskMonRes where taskMonResId in(:ids))";
        Query query=this.getSession().createQuery(hql);

        List<Integer> idsList = new ArrayList<>();
        String[] split = ids.split(",");
        for(String item : split){
            idsList.add(Integer.parseInt(item));
        }
        query.setParameterList("ids",idsList);
        List list = query.list();
        return list;

    }

    public List<BpmResIndSnmpOids> getByResId(Long resId) {
        String hql = "from BpmResIndSnmpOids where resId=:resId";
        Query query = this.getSession().createQuery(hql);
        query.setParameter("resId",resId);
        List list = query.list();
        return list;
    }
}
