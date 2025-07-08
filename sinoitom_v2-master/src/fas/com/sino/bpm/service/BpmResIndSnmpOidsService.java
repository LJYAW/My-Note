package com.sino.bpm.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.bpm.dao.BpmResIndCmdParseDao;
import com.sino.bpm.dao.BpmResIndSnmpOidsDao;
import com.sino.bpm.dao.BpmResMonIndsDao;
import com.sino.bpm.entity.BpmResIndCmdParse;
import com.sino.bpm.entity.BpmResIndSnmpOids;
import com.sino.bpm.entity.BpmResMonInds;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BpmResIndSnmpOidsService
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/22 11:24
 * @Version 1.0
 **/
@Service
@Transactional
public class BpmResIndSnmpOidsService {
    private static Logger logger= LoggerFactory.getLogger(BpmResIndSnmpOidsService.class);

    @Autowired
    private BpmResIndSnmpOidsDao bpmResIndSnmpOidsDao;
    @Autowired
    private BpmResMonIndsDao bpmResMonIndsDao;

    private static String objAttNames ="resMonIndSnmpOid,resId,resIndSnmpOid,resId@BpmTaskMonRes@resId@resIp,vendorId,vendorId@CmdbVendor@vendorID@vendorName,resClassCode,resClassName,resTypeCode,resTypeName,prodModel,modelOID,resTypeIndId,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indicatorItem,indItemName,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status,osType,osVersion,osFeature";
    private static String jsonAttNames="id,resId,resIndSnmpOid,resIp,vendorId,vendorName,resClassCode,resClassName,resTypeCode,resTypeName,prodModel,modelOID,resTypeIndId,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indicatorItem,indItemName,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status,osType,osVersion,osFeature";

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<BpmResIndSnmpOids> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    public Page<BpmResIndSnmpOids> findAjaxSearch(PageRequest pageRequest, Integer resClassCode, Integer  resTypeCode, String resId ){

        Map params=new HashMap();
        StringBuffer hql=new StringBuffer("from BpmResIndSnmpOids ");
        if(resClassCode!=null){
            hql.append(" where resClassCode=:resClassCode ");
            params.put("resClassCode", resClassCode);
        }
        if(resTypeCode!=null){
            hql.append(" and resTypeCode=:resTypeCode ");
            params.put("resTypeCode", resTypeCode);
        }
        if(resId!=null){
            hql.append(" and resId=:resId ");
            params.put("resId", Long.parseLong(resId));
        }

        Page pageinfo=bpmResIndSnmpOidsDao.findAjaxPage(pageRequest,hql.toString(), params);
        return pageinfo;

    }

    @Transactional(readOnly = true)
    public String getJsonPageList(final Page<BpmResIndSnmpOids> page) {
        return JsonUtils.getJQJsonPageInfo(page, objAttNames, jsonAttNames);
    }

    public List<BpmResIndSnmpOids> getRelatedList(String ids) {
        List<BpmResIndSnmpOids> list = bpmResIndSnmpOidsDao.getRelatedList(ids);
        return list;
    }

    public void deletes(String[] idArr) {
        if(idArr!=null && idArr.length>0){
            List<BpmResMonInds> bpmResMonIndsList = new ArrayList<>();
            List<BpmResIndSnmpOids> bpmResIndSnmpOidsList = new ArrayList<>();

            for(String id : idArr){
                if(StringUtils.isNotBlank(id)){
//                    找到对应的那条指标
                    BpmResMonInds bpmResMonInds =  bpmResMonIndsDao.getByCmdParseId(id);
                    bpmResMonIndsList.add(bpmResMonInds);

                    bpmResIndSnmpOidsList.add(bpmResIndSnmpOidsDao.get(Integer.parseInt(id)));
                }
            }

            if(bpmResMonIndsList!=null && bpmResMonIndsList.size()>0
                    && bpmResIndSnmpOidsList!=null && bpmResIndSnmpOidsList.size()>0){
                bpmResMonIndsDao.delete(bpmResMonIndsList);
                bpmResMonIndsDao.flush();

                bpmResIndSnmpOidsDao.delete(bpmResIndSnmpOidsList);
                bpmResIndSnmpOidsDao.flush();
            }
        }
    }
}
