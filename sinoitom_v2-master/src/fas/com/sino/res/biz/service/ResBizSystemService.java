package com.sino.res.biz.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.ack.task.entity.AckTask;
import com.sino.base.common.util.JsonUtils;
import com.sino.res.biz.dao.ResBizSystemDao;
import com.sino.res.biz.entity.ResBizSystem;


@Service
@Transactional
public class ResBizSystemService {
	
private static Logger logger = LoggerFactory.getLogger(ResBizSystemService.class);
	
	private static String objAttNames ="orgId,orgName,resId,resClassCode,resClassName,resTypeCode,resTypeName,bizTypeCode,bizTypeName,sysEnName,sysName,bizDesc,accessMode,accessProt,svrType,svrIpAddr,svrId,svcName,svcPort,bizURL,status,creator,createTime,modifier,modifyTime,bizURLStatus";
	private static String jsonAttNames="orgId,orgName,id,resClassCode,resClassName,resTypeCode,resTypeName,bizTypeCode,bizTypeName,sysEnName,sysName,bizDesc,accessMode,accessProt,svrType,svrIpAddr,svrId,svcName,svcPort,bizURL,status,creator,createTime,modifier,modifyTime,bizURLStatus";

	@Autowired
	private ResBizSystemDao resBizSystemDao;
	
	public String getJsonListStr(final List<ResBizSystem> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public ResBizSystem getById(Long id){
		return resBizSystemDao.get(id);
	}
	
	public List<ResBizSystem> getAll() {
		return resBizSystemDao.getAll();
	}
	
	public void deletes(String [] ids){
		for(String id:ids){
			delete(Long.parseLong(id));
		}
	}
	public void delete(Long id){
		resBizSystemDao.delete(id);
	}
	public void save(ResBizSystem entity){
		resBizSystemDao.save(entity);
	}
	
	public List<ResBizSystem> getByResTypeCode(Integer typeCode){
		String hql=" from ResBizSystem where resTypeCode=?";
		return resBizSystemDao.find(hql, typeCode);
	}
/*
* @author fengyao
* @Description 获取所有“启用”状态的未关联的业务系统
* @Date 14:31 2020/5/13
* @Param []
* return java.util.List<com.sino.res.biz.entity.ResBizSystem>
**/
	public List<ResBizSystem> getEnableBizs(Integer id) {
		List<ResBizSystem> list = resBizSystemDao.getEnableBizs(id);
		return list;
	}
}
