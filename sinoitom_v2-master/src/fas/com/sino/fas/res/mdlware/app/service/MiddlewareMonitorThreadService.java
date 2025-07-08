package com.sino.fas.res.mdlware.app.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorDao;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorThreadDao;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitor;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Identities;

import java.util.List;

/**
 * @ProjectName: SinoITOM_BSB
 * @Package: com.sino.fas.res.tom.service
 * @ClassName: MiddlewareMonitorThreadService
 * @auther: Mr.Lp
 * @date 2019/5/28 15:37
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class MiddlewareMonitorThreadService {

    private static Logger logger = LoggerFactory.getLogger(MiddlewareMonitorThreadService.class);

    private static String objAttNames = "id,monitorId,middleId,threadCount,daemonThreadCount,peakThreadCount,currentThreadCpuTime,currentThreadUserTime,aliveSocketsCount,maxProcessingTime,processingTime,requstCount,errorCount,bytesReceived,bytesSend,totalStartedThreadCount,monitorTime";
    private static String jsonAttNames = "id,monitorId,middleId,threadCount,daemonThreadCount,peakThreadCount,currentThreadCpuTime,currentThreadUserTime,aliveSocketsCount,maxProcessingTime,processingTime,requstCount,errorCount,bytesReceived,bytesSend,totalStartedThreadCount,monitorTime";


    @Autowired
    private MiddlewareMonitorThreadDao middlewareMonitorThreadDao;

    @Transactional(readOnly = true)
    public String getJsonObjStr(final MiddlewareMonitorThread obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<MiddlewareMonitorThread> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorThread> getAll() {
        return middlewareMonitorThreadDao.getAll();
    }

    public void addMiddlewareMonitorThread(MiddlewareMonitorThread entity) {
        entity.setId(Identities.uuid());
        middlewareMonitorThreadDao.save(entity);
        middlewareMonitorThreadDao.flush();
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorThread> getThreadInMiddleIds(List<String> slist) {
        return middlewareMonitorThreadDao.getByMiddleIds(slist);
    }


    @Transactional(readOnly = true)
    public MiddlewareMonitorThread getThreadByMiddleId(String middleId) {
        String hql = "from MiddlewareMonitorThread where middleId = ?";
        return middlewareMonitorThreadDao.findUnique(hql, middleId);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorThread> getByMonitorId(String monitorId) {
        return middlewareMonitorThreadDao.getByMonitorId(monitorId);
    }

    public void deletes(List<String> ids) {
        middlewareMonitorThreadDao.deletes(ids);
    }

}
