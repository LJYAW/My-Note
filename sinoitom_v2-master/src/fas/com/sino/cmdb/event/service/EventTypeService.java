/*
 * 文件名： EventTypeService.java
 * 
 * 创建日期： 2014-9-13
 *
 * Copyright(C) 2014, by 牛腾龙.
 *
 * 原始作者: <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 */
package com.sino.cmdb.event.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.event.dao.EventTypeDao;
import com.sino.cmdb.event.entity.EventType;
import com.sino.cmdb.product.prodType.entity.ResItemParam;

/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-13
 */
@Service
@Transactional
public class EventTypeService {
	private static Logger logger = LoggerFactory.getLogger(EventTypeService.class);
	
	private static String objAttNames = "eventTypeID,eventClassCode,eventClassName,eventTypeCode,eventTypeName,status,description";
	private static String jsonAttNames = "id,eventClassCode,eventClassName,eventTypeCode,eventTypeName,status,description";
	
	@Autowired
	private EventTypeDao eventTypeDao;
	
	@Transactional(readOnly = true)
	public List<EventType> getAll() {
		return eventTypeDao.getAll();
	}
	
	public String getJsonListStr(final List<EventType> event) {
		return JsonUtils.getJsonListInfo(event, objAttNames, jsonAttNames);
	}

	public void add(EventType saveType) {
		logger.info("save Entity");
		eventTypeDao.save(saveType);
		
	}
	
	
	public EventType getByTypeCode(int typeCode){
		String hql=" from EventType where eventTypeCode=?";
		return eventTypeDao.findUnique(hql, typeCode);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final EventType saveType) {
		return JsonUtils.getJsonObjInfo(saveType, objAttNames, jsonAttNames);
	}
	
	public List<EventType> getByClassCode(int classCode){
		String hql=" from EventType where eventClassCode=?";
		return eventTypeDao.find(hql, classCode);
	}

	public EventType getByID(String id) {
		return eventTypeDao.get(Integer.parseInt(id));
	}

	public void deletes(String [] ids){
		for(String id:ids){
			delete(Integer.parseInt(id));
		}
	}
	
	public void delete(Integer id){
		eventTypeDao.delete(id);
	}
	
	public List<ResItemParam> getEventClass(){
		return eventTypeDao.getEventClass();
	}

}
