package com.sino.cmdb.indicator.snmpoid.dao;

import com.sino.base.common.BaseDao;
import com.sino.cmdb.indicator.product.entity.ProdSnmpIndItems;
import com.sino.cmdb.indicator.snmpoid.entity.ResIndSnmpOid;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ProjectName: SinoITOM_V2
 * @Package: com.sino.cmdb.indicator.snmpoid.dao
 * @ClassName: ResIndSnmpOidDao
 * @auther: Mr.Lp
 * @date: 2020/6/8 15:29
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */
@Component
public class ResIndSnmpOidDao extends BaseDao<ResIndSnmpOid,Integer> {

    public List<ResIndSnmpOid> getByVendorIDAndModelOID(String vendorID, String resTypeCode, List<String> modelOIDs){
        //String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
        String hql=" from ResIndSnmpOid where status=1 and (vendorId=:vendorID or vendorId= -1) and (resTypeCode=:resTypeCode or resTypeCode = -1) and  (modelOID='X' or modelOID in(:modelOIDs)) and indClassCode<>8 and indClassCode<>9 ";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("vendorId", Integer.parseInt(vendorID));
        query.setParameter("resTypeCode", Integer.parseInt(resTypeCode));
        query.setParameterList("modelOIDs", modelOIDs);
        return query.list();
    }

    public List<ResIndSnmpOid> getDataByVendorIDAndModelOID(String vendorID,String resTypeCode,List<String> modelOIDs){
        //String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
        String hql="select distinct indItemName from ResIndSnmpOid where status=1 and (vendorId=:vendorID or vendorId= -1) and (resTypeCode=:resTypeCode or resTypeCode = -1) and  (modelOID='X' or modelOID in(:modelOIDs)) and indClassCode<>8 and indClassCode<>9 ";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("vendorId", Integer.parseInt(vendorID));
        query.setParameter("resTypeCode", Integer.parseInt(resTypeCode));
        query.setParameterList("modelOIDs", modelOIDs);
        return query.list();
    }

    public List<ResIndSnmpOid> getByVendorIDAndModelOID(String vendorID,List<String> modelOIDs){
        String hql=" from ResIndSnmpOid where vendorId=:vendorID and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("vendorId", Integer.parseInt(vendorID));
        query.setParameterList("modelOIDs", modelOIDs);
        return query.list();
    }

    public void batchAudit(List<Integer> ids) {
        String hql = "update ResIndSnmpOid set status=1 where resIndSnmpOid in(:ids)";
        Query queryItem = this.getSession().createQuery(hql);
        queryItem.setParameterList("ids", ids);
        queryItem.executeUpdate();
    }

    @SuppressWarnings({ "rawtypes" })
    public List<ResItemParam> getVendor() {
        StringBuffer hql=new StringBuffer("select distinct a.vendorID,a.dispName ");
        hql.append("from CmdbVendor a,CmdbProdCmdChkItems b where a.vendorID=b.vendorId order by b.vendorId asc,b.devClassCode asc,b.devTypeCode asc");
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
    public List<ResItemParam> getByVendor(int vendor) {
        String hql="select distinct devClassCode,devClassName from CmdbProdCmdChkItems where vendorId=:vendor";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("vendor", vendor);
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
    public List<ResItemParam> getByClassCode(int code,int vendor) {
        String hql="select distinct devTypeCode,devTypeName from CmdbProdCmdChkItems where devClassCode=:code and vendorId=:vendor";
        Query query=this.getSession().createQuery(hql);
        query.setParameter("code", code);
        query.setParameter("vendor", vendor);
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

}
