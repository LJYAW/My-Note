package com.sino.fas.res.mdlware.app.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorCpuDao;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorHeapDao;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorInfoDao;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorThreadDao;
import com.sino.fas.res.mdlware.app.entity.*;
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
 * @ClassName: MiddlewareMonitorInfoService
 * @auther: Mr.Lp
 * @date 2019/5/28 15:02
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class MiddlewareMonitorInfoService {

    private static Logger logger = LoggerFactory.getLogger(MiddlewareMonitorInfoService.class);

    private static String objAttNames = "middleId,devIpAddr,content,monitorTime,totalMem,freeMem,swapTotal,swapFree,osName,osArch,availableProcessors,jitName,jitTime,committedMem,tomcatVersion,intervaltime,monitorId";
    private static String jsonAttNames = "id,devIpAddr,content,monitorTime,totalMem,freeMem,swapTotal,swapFree,osName,osArch,availableProcessors,jitName,jitTime,committedMem,tomcatVersion,intervaltime,monitorId";

    @Autowired
    private MiddlewareMonitorInfoDao middlewareMonitorInfoDao;

    @Autowired
    private MiddlewareMonitorHeapDao middlewareMonitorHeapDao;

    @Autowired
    private MiddlewareMonitorThreadDao middlewareMonitorThreadDao;

    @Autowired
    private MiddlewareMonitorCpuDao middlewareMonitorCpuDao;

    @Transactional(readOnly = true)
    public String getJsonObjStr(final MiddlewareMonitorInfo obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<MiddlewareMonitorInfo> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorInfo> getAll() {
        return middlewareMonitorInfoDao.getAll();
    }

    @Transactional(readOnly = true)
    public MiddlewareMonitorInfo getById(String id){
        return middlewareMonitorInfoDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorInfo> getAllMiddlewareMonitorInfo() {
        String hql = "from MiddlewareMonitorInfo order by MonitorTime desc";
        return middlewareMonitorInfoDao.find(hql);
    }

    public void addMiddlewareMonitorInfo(MiddlewareMonitorInfo entity) {
        entity.setMiddleId(Identities.uuid());
        middlewareMonitorInfoDao.save(entity);
        middlewareMonitorInfoDao.flush();
    }

    public void batchSave (MiddlewareMonitorInfo info, MiddlewareMonitorHeap heap, MiddlewareMonitorThread monitorthread, MiddlewareMonitorCpu cpu){
        addMiddlewareMonitorInfo(info);
        heap.setId(Identities.uuid());
        heap.setMiddleId(info.getMiddleId());
        middlewareMonitorHeapDao.save(heap);
        monitorthread.setId(Identities.uuid());
        monitorthread.setMiddleId(info.getMiddleId());
        middlewareMonitorThreadDao.save(monitorthread);
        cpu.setId(Identities.uuid());
        cpu.setMiddleId(info.getMiddleId());
        middlewareMonitorCpuDao.save(cpu);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorInfo> getByIpAndContentAndMonitorTime(String devIpAddr ,String content,String tomcatVersion,String monitorTime) {
//        String hql = "from MiddlewareMonitorInfo where devIpAddr =? and content =? order by MonitorTime";
        return middlewareMonitorInfoDao.getByIpAndContentAndMonitorTime(devIpAddr,content,tomcatVersion,monitorTime);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorInfo> getByIpAndContentAndTomcatVersion(String devIpAddr ,String content,String tomcatVersion) {
        return middlewareMonitorInfoDao.getByIpAndContentAndTomcatVersion(devIpAddr,content,tomcatVersion);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorInfo> getByMonitorId(String monitorId) {
        return middlewareMonitorInfoDao.getByMonitorId(monitorId);
    }

    public void deletes(List<String> ids) {
        middlewareMonitorInfoDao.deletes(ids);
    }

}
