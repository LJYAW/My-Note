/*
 * 文件名： DevCmdParamService.java
 * 
 * 创建日期： 2014-9-15
 *
 * Copyright(C) 2014, by 崔雷.
 *
 * 原始作者: <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 */
package com.sino.cmdb.operation.devCmdParam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.cmdb.operation.devCmdParam.dao.DevCmdParamDao;
import com.sino.cmdb.operation.devCmdParam.entity.DevCmdParam;

/**
 * 
 *
 * @author <a href="mailto:cuil@sino-bridge.com">崔雷</a>
 *
 * @version $Revision$
 *
 * @since 2014-9-15
 */
@Service
@Transactional
public class DevCmdParamService {

	
	@Autowired
	private DevCmdParamDao devCmdParamDao;
	
	public List<DevCmdParam> getAll(){
		return devCmdParamDao.getAll();
	}
}
