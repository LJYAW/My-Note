package com.sino.fas.res.mdlware.app.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorDao;
import com.sino.fas.res.mdlware.app.dao.MiddlewareMonitorHeapDao;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitor;
import com.sino.fas.res.mdlware.app.entity.MiddlewareMonitorHeap;
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
 * @ClassName: MiddlewareMonitorHeapService
 * @auther: Mr.Lp
 * @date 2019/5/28 15:29
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class MiddlewareMonitorHeapService {

    private static Logger logger = LoggerFactory.getLogger(MiddlewareMonitorHeapService.class);

    private static String objAttNames = "id,monitorId,middleId,heapcommitted,heapinit,heapmax,heapused,heap,noHeapcommitted,noHeapinit,noHeapmax,noHeapused,noheap,monitorTime";
    private static String jsonAttNames = "id,monitorId,middleId,heapcommitted,heapinit,heapmax,heapused,heap,noHeapcommitted,noHeapinit,noHeapmax,noHeapused,noheap,monitorTime";


    @Autowired
    private MiddlewareMonitorHeapDao middlewareMonitorHeapDao;

    @Transactional(readOnly = true)
    public String getJsonObjStr(final MiddlewareMonitorHeap obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<MiddlewareMonitorHeap> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorHeap> getAll() {
        return middlewareMonitorHeapDao.getAll();
    }

    public void addMiddlewareMonitorHeap(MiddlewareMonitorHeap entity) {
        entity.setId(Identities.uuid());
        middlewareMonitorHeapDao.save(entity);
        middlewareMonitorHeapDao.flush();
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorHeap> getHeapInMiddleIds(List<String> slist) {
        return middlewareMonitorHeapDao.getByMiddleIds(slist);
    }


    @Transactional(readOnly = true)
    public MiddlewareMonitorHeap getHeapByMiddleId(String middleId) {
        String hql = "from MiddlewareMonitorHeap where middleId = ?";
        return middlewareMonitorHeapDao.findUnique(hql, middleId);
    }

    @Transactional(readOnly = true)
    public List<MiddlewareMonitorHeap> getByMonitorId(String monitorId) {
        return middlewareMonitorHeapDao.getByMonitorId(monitorId);
    }

    public void deletes(List<String> ids) {
        middlewareMonitorHeapDao.deletes(ids);
    }

}
