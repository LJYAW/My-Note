package com.sino.fas.res.mdlware.app.service;


import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.entity.OrgEmployee;
import com.sino.fas.res.mdlware.app.dao.*;
import com.sino.fas.res.mdlware.app.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Identities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class MiddlewareMonitorService {

    private static Logger logger = LoggerFactory.getLogger(MiddlewareMonitorService.class);

    private static String objAttNames = "id,devIpAddr,content,vmVendor,vmName,vmVersion,startTime,tomcatVersion,createTime,userName,psWord,uptime,vms,libraryPath,classPath,bootClassPath,status,intervaltime,monitorTime,middleType";
    private static String jsonAttNames = "id,devIpAddr,content,vmVendor,vmName,vmVersion,startTime,tomcatVersion,createTime,userName,psWord,uptime,vms,libraryPath,classPath,bootClassPath,status,intervaltime,monitorTime,middleType";

    @Autowired
    private MiddlewareMonitorDao middlewareMonitorDao;

    @Autowired
    private MiddlewareMonitorInfoDao middlewareMonitorInfoDao;

    @Autowired
    private MiddlewareMonitorHeapDao middlewareMonitorHeapDao;

    @Autowired
    private MiddlewareMonitorThreadDao middlewareMonitorThreadDao;

    @Autowired
    private MiddlewareMonitorCpuDao middlewareMonitorCpuDao;

    @Autowired
    private MiddlewareMonitorInfoService middlewareMonitorInfoService;

    @Autowired
    private MiddlewareMonitorHeapService middlewareMonitorHeapService;

    @Autowired
    private MiddlewareMonitorCpuService middlewareMonitorCpuService;

    @Autowired
    private MiddlewareMonitorThreadService middlewareMonitorThreadService;

    @Transactional(readOnly = true)
    public String getJsonObjStr(final MiddlewareMonitor obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<MiddlewareMonitor> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitor> getAll() {
        return middlewareMonitorDao.getAll();
    }

    @Transactional(readOnly = true)
    public MiddlewareMonitor getById(String id){
        return middlewareMonitorDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitor> getAllMiddlewareMonitor() {
        String hql = "from MiddlewareMonitor order by CreateTime desc";
        return middlewareMonitorDao.find(hql);
    }

    public void addMiddlewareMonitor(MiddlewareMonitor entity) {
        entity.setId(Identities.uuid());
        middlewareMonitorDao.save(entity);
        middlewareMonitorDao.flush();
    }

    @Transactional(readOnly = true)
    public MiddlewareMonitor getByIpAndContentAndTomcatVersion(String devIpAddr ,String content,String tomcatVersion) {
        String hql = "from MiddlewareMonitor where devIpAddr =? and content =? and tomcatVersion =? order by createTime";
//        return middlewareMonitorDao.getByIpAndContentAndTomcatVersion(devIpAddr,content,tomcatVersion);
        return middlewareMonitorDao.findUnique(hql,devIpAddr,content,tomcatVersion);
    }

    @Transactional(readOnly = true)
    public MiddlewareMonitor getByIpAndContent(String devIpAddr ,String content) {
        String hql = "from MiddlewareMonitor where devIpAddr =? and content =? ";
//        return middlewareMonitorDao.getByIpAndContentAndTomcatVersion(devIpAddr,content,tomcatVersion);
        return middlewareMonitorDao.findUnique(hql,devIpAddr,content);
    }


    public void updateMiddlewareMonitor(MiddlewareMonitor entity){
        if(entity!=null){
            String id = entity.getId();
            String monitorTime = entity.getMonitorTime();
            String startTime = entity.getStartTime();
            String uptime = entity.getUptime();
            middlewareMonitorDao.updateMiddlewareMonitor(id,monitorTime,startTime,uptime);
        }
    }

    public void batchSave (MiddlewareMonitor entity,MiddlewareMonitorInfo info, MiddlewareMonitorHeap heap, MiddlewareMonitorThread monitorthread, MiddlewareMonitorCpu cpu){
        MiddlewareMonitor monitor = new MiddlewareMonitor();
        if(entity!=null){
            monitor = this.getByIpAndContentAndTomcatVersion(entity.getDevIpAddr(),entity.getContent(),entity.getTomcatVersion());
        }
        if(monitor!=null){
            entity.setId(monitor.getId());
            this.updateMiddlewareMonitor(entity);
        }else{
        	this.addMiddlewareMonitor(entity);
        }
        info.setMonitorId(entity.getId());
        //middlewareMonitorInfoDao.save(info);
        middlewareMonitorInfoService.addMiddlewareMonitorInfo(info);
        //middlewareMonitorInfoDao.flush();
        //heap.setId(Identities.uuid());
        heap.setMonitorId(entity.getId());
        heap.setMiddleId(info.getMiddleId());
        middlewareMonitorHeapService.addMiddlewareMonitorHeap(heap);
        //middlewareMonitorHeapDao.save(heap);
       // middlewareMonitorHeapDao.flush();
        //monitorthread.setId(Identities.uuid());
        monitorthread.setMonitorId(entity.getId());
        monitorthread.setMiddleId(info.getMiddleId());
        middlewareMonitorThreadService.addMiddlewareMonitorThread(monitorthread);
        //middlewareMonitorThreadDao.save(monitorthread);
        //middlewareMonitorThreadDao.flush();
        //cpu.setId(Identities.uuid());
        cpu.setMonitorId(entity.getId());
        cpu.setMiddleId(info.getMiddleId());
        middlewareMonitorCpuService.addMiddlewareMonitorCpu(cpu);
        //middlewareMonitorCpuDao.save(cpu);
        //middlewareMonitorCpuDao.flush();
    }

    public void delete(String id){
        middlewareMonitorDao.delete(id);
    }

    public void batchDelete(String id, List<String> infoIds, List<String> heapIds, List<String> threadIds, List<String> cpuIds){
        delete(id);
        if(infoIds.size()>0){
        	middlewareMonitorInfoService.deletes(infoIds);
        }
        if(heapIds.size()>0){
        	middlewareMonitorHeapService.deletes(heapIds);
        }
		if(threadIds.size()>0){
			middlewareMonitorThreadService.deletes(threadIds);
		}
		if(cpuIds.size()>0){
			middlewareMonitorCpuService.deletes(cpuIds);
		}
    }

    public void save(MiddlewareMonitor entity){
        logger.info("save Entity");
        middlewareMonitorDao.save(entity);
    }

}
