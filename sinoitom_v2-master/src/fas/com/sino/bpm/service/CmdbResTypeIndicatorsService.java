package com.sino.bpm.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.bpm.dao.BpmResMonIndsDao;
import com.sino.bpm.dao.CmdbResTypeIndicatorsDao;
import com.sino.bpm.entity.BpmResMonInds;
import com.sino.bpm.entity.CmdbResTypeIndicators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CmdbResTypeIndicatorsService
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/13 17:17
 * @Version 1.0
 **/
@Service
@Transactional
public class CmdbResTypeIndicatorsService {
    private static Logger logger= LoggerFactory.getLogger(CmdbResTypeIndicatorsService.class);

    @Autowired
    private CmdbResTypeIndicatorsDao cmdbResTypeIndicatorsDao;

    private static String objAttNames ="resTypeIndId,resTypeIndId,resClassCode,resClassName,resTypeCode,resTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,valueType,length,decimals,measureUnit,remark,status";
    private static String jsonAttNames="id,resTypeIndId,resClassCode,resClassName,resTypeCode,resTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,valueType,length,decimals,measureUnit,remark,status";

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<CmdbResTypeIndicators> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public CmdbResTypeIndicators getByTypeCodeAndClassCodeAndItemId(int resTypeCode,int resClassCode,long indItemID){
        String hql = "from CmdbResTypeIndicators where resTypeCode=? and resClassCode=? and indItemID=?";
        return cmdbResTypeIndicatorsDao.findUnique(hql,resTypeCode,resClassCode,indItemID);
    }

}
