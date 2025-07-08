package com.sino.bpm.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.bpm.dao.BpmResMonIndsDao;
import com.sino.bpm.entity.BpmResMonInds;
import com.sino.bpm.entity.BpmTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName BpmResMonIndsService
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/13 9:58
 * @Version 1.0
 **/
@Service
@Transactional
public class BpmResMonIndsService {
    private static Logger logger= LoggerFactory.getLogger(BpmResMonIndsService.class);

    @Autowired
    private BpmResMonIndsDao bpmResMonIndsDao;

    private static String objAttNames ="resMonIndId,taskId,taskName,resId,resIp,resIpLong,resClassCode,resClassName,resTypeCode,resTypeName,resTypeIndId,indCollMode,indItemId,IndGroupName,indItemEnName,indItemName,valueType,length,decimals,measureUnit,flag";
    private static String jsonAttNames="id,taskId,taskName,resId,resIp,resIpLong,resClassCode,resClassName,resTypeCode,resTypeName,resTypeIndId,indCollMode,indItemId,IndGroupName,indItemEnName,indItemName,valueType,length,decimals,measureUnit,flag";

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<BpmResMonInds> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    /*
    * @author fengyao
    * @Description 获取所有
    * @Date 16:56 2020/5/13
    * @Param []
    * return java.util.List<com.sino.bpm.entity.BpmResMonInds>
    **/
    public List<BpmResMonInds> getAll() {
        logger.info("Enter BpmResMonIndsService.getAll ...");
        List<BpmResMonInds> all = bpmResMonIndsDao.getAll();
        return all;
    }
}
