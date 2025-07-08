package com.sino.bpm.dao;

import com.sino.base.common.BaseDao;
import com.sino.bpm.entity.BpmResIndCmdParse;
import com.sino.bpm.entity.BpmTaskMonRes;
import com.sino.bpm.entity.CmdbResIndCmdParse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName CmdbResIndCmdParseDao
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/18 14:28
 * @Version 1.0
 **/
@Component
public class CmdbResIndCmdParseDao   extends BaseDao<CmdbResIndCmdParse,Integer> {
    public List<CmdbResIndCmdParse> getCmdInds(List<Integer> resTypeIndIdList, BpmTaskMonRes bpmTaskMonRes) {
        String modelOID = bpmTaskMonRes.getModelOID();
        StringBuilder sb = new StringBuilder();

        sb.append("from CmdbResIndCmdParse where (resClassCode=:resClassCode or resClassCode=-1) and (resTypeCode=:resTypeCode or resTypeCode=-1) ");
        sb.append("and osType=:osType and (osVersion=:osVersion or osVersion='V.R')  and (osFeature=:osFeature or osFeature='all') ");
        sb.append("and (modelOID='X.X' or modelOID=:modelOID) and indClassCode<>8 and indClassCode<>9 and status=1 and (vendorID=:vendorId or vendorID=-1) ");
        if(resTypeIndIdList!=null && resTypeIndIdList.size()>0){
            sb.append("and resTypeIndId not in(:resTypeIndIdList) ");
        }

        Query query=this.getSession().createQuery(sb.toString());

        query.setParameter("resClassCode", bpmTaskMonRes.getResClassCode());
        query.setParameter("resTypeCode",bpmTaskMonRes.getResTypeCode());
        query.setParameter("modelOID",modelOID);

        query.setParameter("osType",bpmTaskMonRes.getOsType());
        query.setParameter("osVersion",bpmTaskMonRes.getOsVersion());
        query.setParameter("osFeature",bpmTaskMonRes.getOsFeature());
        if(resTypeIndIdList!=null && resTypeIndIdList.size()>0){
            query.setParameterList("resTypeIndIdList",resTypeIndIdList);
        }
        query.setParameter("vendorId",bpmTaskMonRes.getVendorID());

        List<CmdbResIndCmdParse> list = query.list();
        return list;
    }

    
    /*/**
     * @auther: Mr.Lp
     * @desc: 以下
     * @data: 2020/7/1
     * @param: [vendorID, resTypeCode, modelOIDs, osType, osVersion, osFeature]
     * @return: 
     */
    public List<CmdbResIndCmdParse> getByVendorIDAndModelOID(String vendorID,String resTypeCode,List<String> modelOIDs,String osType,String osVersion,String osFeature){
        //String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
        String hql=" from CmdbResIndCmdParse where status=1 " +
                "and (vendorId=:vendorID or vendorID= -1) " +
                "and (resTypeCode=:resTypeCode or resTypeCode = -1) " +
                "and osType=:osType "+
                "and (osVersion='V.R' or osVersion=:osVersion) "+
                "and (osFeature='default' or osFeature=:osFeature) "+
                "and  (modelOID='X.X' or modelOID in(:modelOIDs)) " +
                "and indClassCode<>8 and indClassCode<>9 ";
        System.out.println("hql---"+hql);
        Query query=this.getSession().createQuery(hql);
        query.setParameter("vendorID", Integer.parseInt(vendorID));
        query.setParameter("resTypeCode", Integer.parseInt(resTypeCode));
        query.setParameterList("modelOIDs", modelOIDs);
        query.setParameter("osType", osType);
        query.setParameter("osVersion", osVersion);
        query.setParameter("osFeature", osFeature);
        return query.list();
    }

    public List<CmdbResIndCmdParse> getByVendorIDAndModelOID(String vendorID,String resTypeCode,List<String> modelOIDs,String osType,String osVersion,String osFeature,List<Long> indItemIds){
        //String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
        StringBuffer hql=new StringBuffer(" from CmdbResIndCmdParse where status=1 " +
                "and (vendorID=:vendorID or vendorId= -1) " +
                "and (resTypeCode=:resTypeCode or resTypeCode = -1) " +
                "and osType=:osType "+
                "and (osVersion='V.R' or osVersion=:osVersion) "+
                "and (osFeature='all' or osFeature=:osFeature) "+
                "and  (modelOID='X.X' or modelOID in(:modelOIDs)) " +
                "and indClassCode<>8 and indClassCode<>9 ");
        if(!indItemIds.isEmpty()){
            hql.append("and indItemID not in(:indItemIds)");
        }
        System.out.println("hql---"+hql.toString());
        Query query=this.getSession().createQuery(hql.toString());
        query.setParameter("vendorID", Integer.parseInt(vendorID));
        query.setParameter("resTypeCode", Integer.parseInt(resTypeCode));
        query.setParameterList("modelOIDs", modelOIDs);
        query.setParameter("osType", osType);
        query.setParameter("osVersion", osVersion);
        query.setParameter("osFeature", osFeature);
        if(!indItemIds.isEmpty()){
            query.setParameterList("indItemIds", indItemIds);
        }
        return query.list();
    }

    public List<CmdbResIndCmdParse> getByOsTypeAndOsVersion(String osType,String osVersion){
        //String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
        String hql=" from CmdbResIndCmdParse where status=1 " +
                "and osType=:osType "+
                "and (osVersion='V.R' or osVersion=:osVersion) "+
                "and indClassCode<>8 and indClassCode<>9 ";
        System.out.println("hql---"+hql);
        Query query=this.getSession().createQuery(hql);
        query.setParameter("osType", osType);
        query.setParameter("osVersion", osVersion);
        return query.list();
    }

    public List<CmdbResIndCmdParse> getByOsTypeAndOsVersion(String osType, String osVersion, List<Long> indItemIds){
        //String hql=" from ProdSnmpIndItems where vendorID=:vendorID and devTypeCode=:resTypeCode and  (modelOID='X' or modelOID in(:modelOIDs)) and status=1 and indClassCode<>8 ";
        StringBuffer hql=new StringBuffer(" from CmdbResIndCmdParse where status=1 " +
                "and osType=:osType "+
                "and (osVersion='V.R' or osVersion=:osVersion) "+
                "and indClassCode<>8 and indClassCode<>9 ");

        if(!indItemIds.isEmpty()){
            hql.append("and indItemID not in(:indItemIds)");
        }
        System.out.println("hql---"+hql.toString());
        Query query=this.getSession().createQuery(hql.toString());

        query.setParameter("osType", osType);
        query.setParameter("osVersion", osVersion);
        if(!indItemIds.isEmpty()){
            query.setParameterList("indItemIds", indItemIds);
        }
        return query.list();
    }

}
