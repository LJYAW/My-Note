package com.sino.fas.res.host.service;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.host.dao.ResCompHostsDao;
import com.sino.fas.res.host.entity.ResClusterHosts;
import com.sino.fas.res.host.entity.ResCompHosts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: SinoITOM_V2
 * @Package: com.sino.fas.res.host.service
 * @ClassName: ResCompHostsService
 * @auther: Mr.Lp
 * @date: 2020/5/12 18:06
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ResCompHostsService {

    private static Logger logger = LoggerFactory.getLogger(ResCompHostsService.class);

    private static String objAttNames = "id,compHostId,hostId";
    private static String jsonAttNames = "id,compHostId,hostId";

    @Autowired
    private ResCompHostsDao resCompHostsDao;

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<ResCompHosts> list){
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonObjStr(final ResCompHosts obj){
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    public List<ResCompHosts> getByCompHostId(Long compHostId){
        String hql = " from ResCompHosts where compHostId = ? ";
        return resCompHostsDao.find(hql,compHostId);
    }

}
