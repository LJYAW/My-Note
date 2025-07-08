package com.sino.fas.res.mdlware.app.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorCpuDao;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorDao;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitor;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorCpu;
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
 * @ClassName: MiddlewareMonitorCpuService
 * @auther: Mr.Lp
 * @date 2019/5/28 15:43
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class MiddlewareMonitorCpuService {

    private static Logger logger = LoggerFactory.getLogger(MiddlewareMonitorCpuService.class);

    private static String objAttNames = "id,monitorId,middleId,loadedClassCount,totalLoadedClassCount,unloadedClassCount,cpu,monitorTime";
    private static String jsonAttNames = "id,monitorId,middleId,loadedClassCount,totalLoadedClassCount,unloadedClassCount,cpu,monitorTime";


    @Autowired
    private MiddlewareMonitorCpuDao middlewareMonitorCpuDao;

    @Transactional(readOnly = true)
    public String getJsonObjStr(final MiddlewareMonitorCpu obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<MiddlewareMonitorCpu> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorCpu> getAll() {
        return middlewareMonitorCpuDao.getAll();
    }

    public void addMiddlewareMonitorCpu(MiddlewareMonitorCpu entity) {
        entity.setId(Identities.uuid());
        middlewareMonitorCpuDao.save(entity);
        middlewareMonitorCpuDao.flush();
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorCpu> getCpuInMiddleIds(List<String> slist) {
        return middlewareMonitorCpuDao.getByMiddleIds(slist);
    }


    @Transactional(readOnly = true)
    public MiddlewareMonitorCpu getCpuByMiddleId(String middleId) {
        String hql = "from MiddlewareMonitorCpu where middleId = ?";
        return middlewareMonitorCpuDao.findUnique(hql, middleId);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorCpu> getByMonitorId(String monitorId) {
        return middlewareMonitorCpuDao.getByMonitorId(monitorId);
    }

    public void deletes(List<String> ids) {
        middlewareMonitorCpuDao.deletes(ids);
    }
}
