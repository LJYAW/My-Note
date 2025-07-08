package com.sino.bpm.dao;

import com.sino.base.common.BaseDao;
import com.sino.bpm.entity.BpmResIndCmdParse;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BpmResIndCmdParseDao
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/19 11:31
 * @Version 1.0
 **/
@Component
public class BpmResIndCmdParseDao extends BaseDao<BpmResIndCmdParse,Long> {
    public List<BpmResIndCmdParse> getRelatedList(String ids) {
        String hql=" from BpmResIndCmdParse where resId in(SELECT resId FROM BpmTaskMonRes where taskMonResId in(:ids))";
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

    public List<BpmResIndCmdParse> getByResId(Long resId) {
        String hql = "from BpmResIndCmdParse where resId=:resId";
        Query query = this.getSession().createQuery(hql);
        query.setParameter("resId",resId);
        List list = query.list();
        return list;
    }

}
