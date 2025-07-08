package com.sino.bpm.dao;

import com.sino.base.common.BaseDao;
import com.sino.bpm.entity.BpmResIndSnmpOids;
import com.sino.bpm.entity.BpmTaskMonRes;
import com.sino.bpm.entity.CmdbResIndCmdParse;
import com.sino.bpm.entity.CmdbResIndSnmpOid;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName CmdbResIndSnmpOidDao
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/18 14:29
 * @Version 1.0
 **/
@Component
public class CmdbResIndSnmpOidDao extends BaseDao<CmdbResIndSnmpOid,Integer> {

    public List<CmdbResIndSnmpOid> getSnmpInds(List<Integer> resTypeIndIdList, BpmTaskMonRes bpmTaskMonRes) {
        String modelOID = bpmTaskMonRes.getModelOID();
        StringBuilder sb = new StringBuilder();

        sb.append("from CmdbResIndSnmpOid where (resClassCode=:resClassCode or resClassCode=-1) and (resTypeCode=:resTypeCode or resTypeCode=-1) ");
        sb.append("and (osType=:osType or osType='' or osType is null) and (osVersion=:osVersion or osVersion='V.R')  and (osFeature=:osFeature or osFeature='all') ");
        sb.append("and (modelOID='X.X' or modelOID=:modelOID) and indClassCode<>8 and indClassCode<>9 and status=1 and (vendorId=:vendorId or vendorId=-1)");
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

        List<CmdbResIndSnmpOid> list = query.list();
        return list;

    }
}
