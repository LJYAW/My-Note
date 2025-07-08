package com.sino.bpm.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.bpm.dao.CmdbResIndSnmpOidDao;
import com.sino.bpm.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CmdbResIndSnmpOidService
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/18 14:30
 * @Version 1.0
 **/
@Service
@Transactional
public class CmdbResIndSnmpOidService {
    private static Logger logger= LoggerFactory.getLogger(CmdbResIndSnmpOidService.class);

    @Autowired
    private CmdbResIndSnmpOidDao cmdbResIndSnmpOidDao;

    private static String objAttNames ="resIndSnmpOid,vendorId,resTypeIndId,vendorId@CmdbVendor@vendorID@vendorName,resClassCode,resClassName,resTypeCode,resTypeName,prodModelID,prodModel,modelOID,osType,osVersion,osFeature,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indicatorItem,indItemName,mibFile,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status";
    private static String jsonAttNames="id,vendorId,resTypeIndId,vendorName,resClassCode,resClassName,resTypeCode,resTypeName,prodModelID,prodModel,modelOID,osType,osVersion,osFeature,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indicatorItem,indItemName,mibFile,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status";

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<CmdbResIndSnmpOid> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    public List<CmdbResIndSnmpOid> getSnmpInds(List<Integer> resTypeIndIdList, BpmTaskMonRes bpmTaskMonRes) {
        List<CmdbResIndSnmpOid> list = cmdbResIndSnmpOidDao.getSnmpInds(resTypeIndIdList,bpmTaskMonRes);
        return list;
    }
}
