/*
 * 文件名： ProdTypeDao.java
 * 
 * 创建日期： 2014-1-16
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.product.prodDirectory.dao;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.product.prodDirectory.entity.ProdDirectory;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">蒋祥胜</a>
 *
 * @version $Revision: 1.2 $
 *
 * @since 2014-1-16
 */
@Component
public class ProdDirectoryDao extends  BaseDao<ProdDirectory, String> {

//    @SuppressWarnings({ "rawtypes" })
//    public List<ResItemParam> getAllVendor() {
//        StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
//        hql.append("from ProdDirectory a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID");
//        Query query=this.getSession().createQuery(hql.toString());
//        List list=query.list();
//        Iterator it  =  list.iterator();
//        List<ResItemParam> paramList = new ArrayList<ResItemParam>();
//        while(it.hasNext()){
//            Object[] tuple  =  (Object[]) it.next();
//            ResItemParam itemParam = new ResItemParam();
//            itemParam.setId(tuple[0].toString());
//            itemParam.setText(tuple[1].toString());
//            paramList.add(itemParam);
//        }
//
//        return paramList;
//    }
//
//    @SuppressWarnings({ "rawtypes" })
//    public List<ResItemParam> getVendor() {
//        StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
//        hql.append("from ProdDirectory a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID,a.prodClassCode,a.prodSeries ");
//        Query query=this.getSession().createQuery(hql.toString());
//        List list=query.list();
//        Iterator it  =  list.iterator();
//        List<ResItemParam> paramList = new ArrayList<ResItemParam>();
//        while(it.hasNext()){
//            Object[] tuple  =  (Object[]) it.next();
//            ResItemParam itemParam = new ResItemParam();
//            itemParam.setId(tuple[0].toString());
//            itemParam.setText(tuple[1].toString());
//            paramList.add(itemParam);
//        }
//
//        return paramList;
//    }
//
//    @SuppressWarnings({ "rawtypes" })
//    public List<ResItemParam> getByVendor(int vendorID) {
//        StringBuffer hql=new StringBuffer("select distinct prodClassCode,prodClassName ");
//        hql.append("from ProdDirectory where vendorID =:vendorID  order by vendorID,prodClassCode,prodSeries");
//        Query query=this.getSession().createQuery(hql.toString());
//        query.setParameter("vendorID", vendorID);
//        List list=query.list();
//        Iterator it  =  list.iterator();
//        List<ResItemParam> paramList = new ArrayList<ResItemParam>();
//        while(it.hasNext()){
//            Object[] tuple  =  (Object[]) it.next();
//            ResItemParam itemParam = new ResItemParam();
//            itemParam.setId(tuple[0].toString());
//            itemParam.setText(tuple[1].toString());
//            paramList.add(itemParam);
//        }
//
//        return paramList;
//    }
//
//    @SuppressWarnings({ "rawtypes" })
//    public List<ResItemParam> getByClassCode(int vendorID, int prodClassCode) {
//        StringBuffer hql=new StringBuffer("select distinct typeCode,typeName ");
//        hql.append("from ProdDirectory where vendorID =:vendorID and prodClassCode=:prodClassCode  order by vendorID,prodClassCode,prodTypeCode,prodSeries");
//        Query query=this.getSession().createQuery(hql.toString());
//        query.setParameter("vendorID", vendorID);
//        query.setParameter("prodClassCode", prodClassCode);
//        List list=query.list();
//        Iterator it  =  list.iterator();
//        List<ResItemParam> paramList = new ArrayList<ResItemParam>();
//        while(it.hasNext()){
//            Object[] tuple  =  (Object[]) it.next();
//            ResItemParam itemParam = new ResItemParam();
//            itemParam.setId(tuple[0].toString());
//            itemParam.setText(tuple[1].toString());
//            paramList.add(itemParam);
//        }
//
//        return paramList;
//    }
//
//    @SuppressWarnings({ "rawtypes" })
//    public List<ResItemParam> getBySeries(int vendorID, int prodClassCode,int typeCode) {
//        StringBuffer hql=new StringBuffer("select distinct prodSeries,prodSeries ");
//        hql.append("from ProdDirectory where vendorID =:vendorID and prodClassCode=:prodClassCode and prodTypeCode=:prodTypeCode order by vendorID,prodClassCode,prodTypeCode,prodSeries");
//        Query query=this.getSession().createQuery(hql.toString());
//        query.setParameter("vendorID", vendorID);
//        query.setParameter("prodClassCode", prodClassCode);
//        query.setParameter("prodTypeCode", typeCode);
//        List list=query.list();
//        Iterator it  =  list.iterator();
//        List<ResItemParam> paramList = new ArrayList<ResItemParam>();
//        while(it.hasNext()){
//            Object[] tuple  =  (Object[]) it.next();
//            ResItemParam itemParam = new ResItemParam();
//            itemParam.setId(tuple[0].toString());
//            itemParam.setText(tuple[0].toString());
//            paramList.add(itemParam);
//        }
//
//        return paramList;
//    }

    @SuppressWarnings({ "rawtypes" })
    public List<ResItemParam> getAllVendor() {
        StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
        hql.append("from VendorProdModel a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID");
        Query query=this.getSession().createQuery(hql.toString());
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

    @SuppressWarnings({ "rawtypes" })
    public List<ResItemParam> getVendor() {
        StringBuffer hql=new StringBuffer("select distinct b.vendorID,b.dispName ");
        hql.append("from VendorProdModel a,CmdbVendor b where a.vendorID = b.vendorID  order by b.vendorID,a.prodClassCode,a.prodTypeCode,a.prodSeries ");
        Query query=this.getSession().createQuery(hql.toString());
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

    @SuppressWarnings({ "rawtypes" })
    public List<ResItemParam> getByVendor(int vendorID) {
        StringBuffer hql=new StringBuffer("select distinct prodClassCode,prodClassName ");
        hql.append("from VendorProdModel where vendorID =:vendorID  order by vendorID,prodClassCode,prodTypeCode,prodSeries");
        Query query=this.getSession().createQuery(hql.toString());
        query.setParameter("vendorID", vendorID);
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

    @SuppressWarnings({ "rawtypes" })
    public List<ResItemParam> getByClassCode(int vendorID, int prodClassCode) {
        StringBuffer hql=new StringBuffer("select distinct prodTypeCode,typeName ");
        hql.append("from VendorProdModel where vendorID =:vendorID and prodClassCode=:prodClassCode  order by vendorID,prodClassCode,prodTypeCode,prodSeries");
        Query query=this.getSession().createQuery(hql.toString());
        query.setParameter("vendorID", vendorID);
        query.setParameter("prodClassCode", prodClassCode);
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

    @SuppressWarnings({ "rawtypes" })
    public List<ResItemParam> getBySeries(int vendorID, int prodClassCode,int typeCode) {
        StringBuffer hql=new StringBuffer("select distinct prodSeries,prodSeries ");
        hql.append("from VendorProdModel where vendorID =:vendorID and prodClassCode=:prodClassCode and prodTypeCode=:typeCode order by vendorID,prodClassCode,prodTypeCode,prodSeries");
        Query query=this.getSession().createQuery(hql.toString());
        query.setParameter("vendorID", vendorID);
        query.setParameter("prodClassCode", prodClassCode);
        query.setParameter("typeCode", typeCode);
        List list=query.list();
        Iterator it  =  list.iterator();
        List<ResItemParam> paramList = new ArrayList<ResItemParam>();
        while(it.hasNext()){
            Object[] tuple  =  (Object[]) it.next();
            ResItemParam itemParam = new ResItemParam();
            itemParam.setId(tuple[0].toString());
            itemParam.setText(tuple[0].toString());
            paramList.add(itemParam);
        }

        return paramList;
    }
}
