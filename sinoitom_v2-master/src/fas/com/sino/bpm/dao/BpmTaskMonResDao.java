package com.sino.bpm.dao;

import com.sino.cmdb.product.prodType.entity.ResItemParam;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.sino.base.common.BaseDao;
import com.sino.bpm.entity.BpmTaskMonRes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class BpmTaskMonResDao extends BaseDao<BpmTaskMonRes,Integer>{

    public List<BpmTaskMonRes> getByResId(Long resId) {
        String hql = "from BpmTaskMonRes where resId=:resId";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("resId", resId);
        List<BpmTaskMonRes> list = query.list();
        return list;
    }

    public List<ResItemParam> getResClassCodes() {
        String hql="select distinct resClassCode,resClassName from BpmTaskMonRes order by resClassCode ";
        Query query = this.getSession().createQuery(hql);
        List list=query.list();
        Iterator it  =  list.iterator();
        List<ResItemParam> paramList = new ArrayList<ResItemParam>();
        while(it.hasNext()){
            Object[] tuple  =  (Object[]) it.next();
            ResItemParam itemParam = new ResItemParam();
            itemParam.setId(tuple[0].toString());
            itemParam.setText(tuple[1].toString());
            paramList.add(itemParam);
        }
        return paramList;
    }

    public List<ResItemParam> getResTypeCodes(int resClassCode) {
        String hql="select distinct resTypeCode,resTypeName from BpmTaskMonRes where resClassCode=:resClassCode order by resTypeCode ";
        Query query = this.getSession().createQuery(hql);
        query.setParameter("resClassCode", resClassCode);
        List list=query.list();
        Iterator it  =  list.iterator();
        List<ResItemParam> paramList = new ArrayList<ResItemParam>();
        while(it.hasNext()){
            Object[] tuple  =  (Object[]) it.next();
            ResItemParam itemParam = new ResItemParam();
            itemParam.setId(tuple[0].toString());
            itemParam.setText(tuple[1].toString());
            paramList.add(itemParam);
        }
        return paramList;
    }

    public List<Object[]> getResNameByTypeCode(int code) {
        String hql="select distinct resId,resName from BpmTaskMonRes where resTypeCode=:code order by resName";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("code", code);
        return query.list();
    }
}
