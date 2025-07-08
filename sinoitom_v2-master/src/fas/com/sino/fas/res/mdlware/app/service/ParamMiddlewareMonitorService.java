package com.sino.fas.res.mdlware.app.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.mdlware.app.entity.ParamMiddlewareMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.service
 * @ClassName: ParamMiddlewareMonitorService
 * @auther: Mr.Lp
 * @date 2019/5/29 17:17
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ParamMiddlewareMonitorService {

    private static Logger logger = LoggerFactory.getLogger(ParamMiddlewareMonitorService.class);

    private static String objAttNames = "heapcommitted,heapmax,heapused,threadCount,peakThreadCount,loadedClassCount,totalLoadedClassCount,cpu,heapcommittedKB,heapmaxKB,heapusedKB,unloadedClassCount";
    private static String jsonAttNames = "heapcommitted,heapmax,heapused,threadCount,peakThreadCount,loadedClassCount,totalLoadedClassCount,cpu,heapcommittedKB,heapmaxKB,heapusedKB,unloadedClassCount";

    @Transactional(readOnly = true)
    public String getJsonObjStr(final ParamMiddlewareMonitor obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<ParamMiddlewareMonitor> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

}
