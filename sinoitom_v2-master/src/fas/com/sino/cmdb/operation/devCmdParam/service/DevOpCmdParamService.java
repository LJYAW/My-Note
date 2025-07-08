/*
 * 文件名： DevOpCmdParamService.java
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

import com.sino.cmdb.operation.devCmdParam.dao.DevOpCmdParamDao;
import com.sino.cmdb.operation.devCmdParam.entity.DevOpCmdParam;

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
public class DevOpCmdParamService {

	@Autowired
	private  DevOpCmdParamDao  devOpCmdParamDao;
	
	public void batchSave(List<DevOpCmdParam> cmdParams){
		devOpCmdParamDao.batchSave(cmdParams, 20);
	}
}
