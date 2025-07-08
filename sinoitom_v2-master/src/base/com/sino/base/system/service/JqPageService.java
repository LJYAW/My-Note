package com.sino.base.system.service;

import com.sino.base.common.util.CommonUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.entity.JqPageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JqPageService<T> {

    private final JqPageResponse<T> entity = new JqPageResponse<T>();
    private final String objAttName = CommonUtils.getObjEntity(entity) ;
    private final String jsonAttName = CommonUtils.getEntityJson(entity);

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<JqPageResponse> list) {
        return JsonUtils.getJsonListInfo(list, objAttName, jsonAttName);
    }

    @Transactional(readOnly = true)
    public String getJsonObjStr(JqPageResponse entity) {
        return JsonUtils.getJsonObjInfo(entity, objAttName, jsonAttName);
    }
}
