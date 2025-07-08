/**
 * 
 */
package com.sino.cmdb.indicator.items.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.items.entity.ParamIndicatorFields;

/**
 * @author Mr.LP
 * @date 2019-9-23下午2:29:24
 * @className ParamIndicatorFieldsService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class ParamIndicatorFieldsService {
	
	private static Logger logger = LoggerFactory.getLogger(ParamIndicatorFieldsService.class);

    private static String objAttNames = "fieldNameEn,fieldName,valueType,borderStyle,rowNo,colNo,tableWidth,height,color,font,size,isBold";
    private static String jsonAttNames = "fieldNameEn,fieldName,valueType,borderStyle,rowNo,colNo,tableWidth,height,color,font,size,isBold";

    @Transactional(readOnly = true)
    public String getJsonObjStr(final ParamIndicatorFields obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<ParamIndicatorFields> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

}
