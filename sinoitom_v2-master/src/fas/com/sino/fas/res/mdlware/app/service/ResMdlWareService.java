/**
 * 
 */
package com.sino.fas.res.mdlware.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.mdlware.app.dao.ResMdlWareDao;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorCpu;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorHeap;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorInfo;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorThread;
import com.sino.fas.res.mdlware.app.entity.ResMdlWare;
import com.sino.snmp.utils.JdbcConnection;

/**
 * @author Mr.LP
 * @date 2019-11-19下午5:22:02
 * @className ResMdlWareService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResMdlWareService {
	
	private static Logger logger = LoggerFactory.getLogger(ResMdlWareService.class);

    private static String objAttNames = "orgID,orgID@OrgOrganization@orgId@orgShortName,resId,resClassCode,resClassName,resTypeCode,resTypeName,svrType,homedHostId,homedHostIp,svrIpAddr,svrIpLong,vendorName,brandName,prodName,userName,passWord,svcUrl,auditStatus,monStatus,monPort,prodVersion,monTime";
    private static String jsonAttNames = "orgID,orgName,id,resClassCode,resClassName,resTypeCode,resTypeName,svrType,homedHostId,homedHostIp,svrIpAddr,svrIpLong,vendorName,brandName,prodName,userName,passWord,svcUrl,auditStatus,monStatus,monPort,prodVersion,monTime";

    @Autowired
    private ResMdlWareDao resMdlWareDao;
    
    @Autowired
    private MiddlewareMonitorInfoService middlewareMonitorInfoService;

    @Autowired
    private MiddlewareMonitorHeapService middlewareMonitorHeapService;

    @Autowired
    private MiddlewareMonitorCpuService middlewareMonitorCpuService;

    @Autowired
    private MiddlewareMonitorThreadService middlewareMonitorThreadService;
    
    @Transactional(readOnly = true)
    public String getJsonObjStr(final ResMdlWare obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<ResMdlWare> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public List<ResMdlWare> getAll() {
        return resMdlWareDao.getAll();
    }

    @Transactional(readOnly = true)
    public ResMdlWare getById(Long id){
        return resMdlWareDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<ResMdlWare> getAllResMdlWare() {
        String hql = "from ResMdlWare order by CreateTime desc";
        return resMdlWareDao.find(hql);
    }
    
    @Transactional(readOnly = true)
    public ResMdlWare getByIpAndPortAndProdVersion(String svrIpAddr ,Integer monPort,String prodVersion) {
        String hql = "from ResMdlWare where svrIpAddr =? and monPort =? and prodVersion =? ";
        return resMdlWareDao.findUnique(hql,svrIpAddr,monPort,prodVersion);
    }
    
    @Transactional(readOnly = true)
    public ResMdlWare getByIpAndPort(String svrIpAddr ,Integer monPort) {
        String hql = "from ResMdlWare where svrIpAddr =? and monPort =? ";
        return resMdlWareDao.findUnique(hql,svrIpAddr,monPort);
    }
    
    public void addResMdlWare(ResMdlWare entity) {
        resMdlWareDao.save(entity);
        resMdlWareDao.flush();
    }
    
    public void updateResMdlWare(ResMdlWare entity){
        if(entity!=null){
            Long resId = entity.getResId();
            String monTime = entity.getMonTime();
            resMdlWareDao.updateResMdlWare(resId,monTime);
        }
    }
    
    public void batchSave (ResMdlWare entity,MiddlewareMonitorInfo info, MiddlewareMonitorHeap heap, MiddlewareMonitorThread monitorthread, MiddlewareMonitorCpu cpu){
    	ResMdlWare mdlWare = new ResMdlWare();
        if(entity!=null){
        	String svrIpAddr = entity.getSvrIpAddr();
        	Integer monPort = entity.getMonPort();
        	String prodVersion = entity.getProdVersion();
        	mdlWare = getByIpAndPortAndProdVersion(svrIpAddr,monPort,prodVersion);
        }
        if(mdlWare!=null){
            entity.setResId(mdlWare.getResId());
            
            if(entity.getAuditStatus()==null){
            	entity.setAuditStatus(0);
            	updateResMdlWare(entity);
            }
            	
        }else{
        	entity.setAuditStatus(0);
        	entity.setResId(JdbcConnection.getInstance().getDeviceResourceUniqId());
        	this.addResMdlWare(entity);
        }
        info.setMonitorId(entity.getResId().toString());
        //middlewareMonitorInfoDao.save(info);
        middlewareMonitorInfoService.addMiddlewareMonitorInfo(info);
        //middlewareMonitorInfoDao.flush();
        //heap.setId(Identities.uuid());
        heap.setMonitorId(entity.getResId().toString());
        heap.setMiddleId(info.getMiddleId());
        middlewareMonitorHeapService.addMiddlewareMonitorHeap(heap);
        //middlewareMonitorHeapDao.save(heap);
       // middlewareMonitorHeapDao.flush();
        //monitorthread.setId(Identities.uuid());
        monitorthread.setMonitorId(entity.getResId().toString());
        monitorthread.setMiddleId(info.getMiddleId());
        middlewareMonitorThreadService.addMiddlewareMonitorThread(monitorthread);
        //middlewareMonitorThreadDao.save(monitorthread);
        //middlewareMonitorThreadDao.flush();
        //cpu.setId(Identities.uuid());
        cpu.setMonitorId(entity.getResId().toString());
        cpu.setMiddleId(info.getMiddleId());
        middlewareMonitorCpuService.addMiddlewareMonitorCpu(cpu);
        //middlewareMonitorCpuDao.save(cpu);
        //middlewareMonitorCpuDao.flush();
    }
    
    public void save(ResMdlWare entity){
        logger.info("save Entity");
        resMdlWareDao.save(entity);
    }
    
    public void batchDelete(String id, List<String> infoIds, List<String> heapIds, List<String> threadIds, List<String> cpuIds){
        delete(Long.parseLong(id));
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
    
    public void delete(Long id){
    	resMdlWareDao.delete(id);
    }

}
