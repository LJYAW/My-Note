package com.sino.bpm.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.bpm.dao.BpmResIndCmdParseDao;
import com.sino.bpm.dao.BpmResMonIndsDao;
import com.sino.bpm.entity.BpmResIndCmdParse;
import com.sino.bpm.entity.BpmResMonInds;
import com.sino.bpm.entity.BpmResIndCmdParse;
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
 * @ClassName BpmResIndCmdParseService
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/21 14:22
 * @Version 1.0
 **/
@Service
@Transactional
public class BpmResIndCmdParseService {
    private static Logger logger= LoggerFactory.getLogger(BpmResIndCmdParseService.class);

    @Autowired
    private BpmResIndCmdParseDao bpmResIndCmdParseDao;
    @Autowired
    private BpmResMonIndsDao bpmResMonIndsDao;

    private static String objAttNames ="resMonIndItemId,resId,chkItemId,resId@BpmTaskMonRes@resId@resIp,vendorID,vendorID@CmdbVendor@vendorID@vendorName,resClassCode,resClassName,resTypeCode,resTypeName,prodModel,modelOID,osType,osVersion,osFeature,resTypeIndId,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemEnName,indItemName,prodChkCmdId,checkCmd,resultSample,resultKeyWords,parseMode,regEx,regExGroupNum,startKeyWords,endKeyWords,delimiter,valueIndex,fileDelimiter,kvDelimiter,lineDelimiter,blankLineDelimiter,valueType,length,decimals,measureUnit,remark,status";
    private static String jsonAttNames="id,resId,chkItemId,resIp,vendorID,vendorName,resClassCode,resClassName,resTypeCode,resTypeName,prodModel,modelOID,osType,osVersion,osFeature,resTypeIndId,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemEnName,indItemName,prodChkCmdId,checkCmd,resultSample,resultKeyWords,parseMode,regEx,regExGroupNum,startKeyWords,endKeyWords,delimiter,valueIndex,fileDelimiter,kvDelimiter,lineDelimiter,blankLineDelimiter,valueType,length,decimals,measureUnit,remark,status";

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<BpmResIndCmdParse> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    public Page<BpmResIndCmdParse> findAjaxSearch(PageRequest pageRequest, Integer resClassCode, Integer  resTypeCode, String resId ){

        Map params=new HashMap();
        StringBuffer hql=new StringBuffer("from BpmResIndCmdParse ");
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

        Page pageinfo=bpmResIndCmdParseDao.findAjaxPage(pageRequest,hql.toString(), params);
        return pageinfo;

    }

    @Transactional(readOnly = true)
    public String getJsonPageList(final Page<BpmResIndCmdParse> page) {
        return JsonUtils.getJQJsonPageInfo(page, objAttNames, jsonAttNames);
    }

    public List<BpmResIndCmdParse> getRelatedList(String ids) {
        List<BpmResIndCmdParse> list = bpmResIndCmdParseDao.getRelatedList(ids);
        return list;
    }

    @Transactional
    public void deletes(String[] idArr) {
        if(idArr!=null && idArr.length>0){
            List<BpmResMonInds> bpmResMonIndsList = new ArrayList<>();
            List<BpmResIndCmdParse> bpmResIndCmdParseList = new ArrayList<>();

            for(String id : idArr){
                if(StringUtils.isNotBlank(id)){
//                    找到对应的那条指标
                    BpmResMonInds bpmResMonInds =  bpmResMonIndsDao.getByCmdParseId(id);
                    bpmResMonIndsList.add(bpmResMonInds);

                    bpmResIndCmdParseList.add(bpmResIndCmdParseDao.get(Long.parseLong(id)));
                }
            }

            if(bpmResMonIndsList!=null && bpmResIndCmdParseList.size()>0
                && bpmResIndCmdParseList!=null && bpmResIndCmdParseList.size()>0){
                bpmResMonIndsDao.delete(bpmResMonIndsList);
                bpmResMonIndsDao.flush();

                bpmResIndCmdParseDao.delete(bpmResIndCmdParseList);
                bpmResIndCmdParseDao.flush();
            }
        }
    }
}
