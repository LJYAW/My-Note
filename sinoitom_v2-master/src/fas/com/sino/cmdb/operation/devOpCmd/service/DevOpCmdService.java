package com.sino.cmdb.operation.devOpCmd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.operation.devOp.service.DevOperationService;
import com.sino.cmdb.operation.devOpCmd.dao.DevOpCmdDao;
import com.sino.cmdb.operation.devOpCmd.entity.DevOpCmd;
import com.sino.cmdb.operation.devOpCmd.entity.DevOpCmdParam;

/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-2-12
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class DevOpCmdService {
	private static Logger logger = LoggerFactory.getLogger(DevOperationService.class);

	private static String objAttNames = "devOpID,opCmdID,devOpID@DevOperation@devOpID@devTypeName,devOpID@DevOperation@devOpID@operateName,snmpEntID,snmpEntID@CmdbVendor@vendorID@dispName,osID,devModel,modelOID,osName,osVersion,cmdOrder,cmdTypeCode,cmdType,command,expectPrompt,paramFlag,description,status";

	private static String jsonAttNames = "devOpID,id,devTypeName,operateName,snmpEntID,dispName,osID,devModel,modelOID,osName,osVersion,cmdOrder,cmdTypeCode,cmdType,command,expectPrompt,paramFlag,description,status";
	@Autowired
	private DevOpCmdDao devOpCmdDao;
	@Transactional(readOnly = true)
	public String getJsonObjStr(final DevOpCmd obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<DevOpCmd> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getParamJsonListStr(final List<DevOpCmdParam> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public void save(DevOpCmd entity){
		logger.info("save Entity");
		devOpCmdDao.save(entity);
	}
	public DevOpCmd getByID(long id){
		return devOpCmdDao.get(id);
	}
	public List<DevOpCmd> getDevOpCmdList(Long id){
		String hql="from DevOpCmd where devOpID=?";
		return devOpCmdDao.find(hql,id);
	}
	public List<DevOpCmdParam> getAll(){
		logger.debug("Enter getAll...");
		return devOpCmdDao.getAllDev();
	}
	public void delete(long id){
		devOpCmdDao.delete(id);
	}
	public List<DevOpCmd> getDataBy(Integer id,Integer vendorID,Integer osID,String oSName,String osVersion,String modelOID){
		return devOpCmdDao.getData(id,vendorID,osID,oSName,osVersion,modelOID);
		/*		StringBuilder hql=new StringBuilder();
		hql.append("from DevOpCmd where operationID=? and snmpEntID=? and osID=?");
		if(!"请选择".equals(osVersion)&&!"-1".equals(modelOID)){
			hql.append(" and osVersion=? and modelOID=? order by cmdOrder");
			return devOpCmdDao.find(hql.toString(),id,vendorID,osID,osVersion,modelOID);
		}else if(!"请选择".equals(osVersion)&&"-1".equals(modelOID)){
			hql.append(" and osVersion=? order by cmdOrder");
			return devOpCmdDao.find(hql.toString(),id,vendorID,osID,osVersion);
		}else if("请选择".equals(osVersion)&&!"-1".equals(modelOID)){
			hql.append(" and modelOID=? order by cmdOrder");
			return devOpCmdDao.find(hql.toString(),id,vendorID,osID,modelOID);
		}else{
			hql.append(" order by cmdOrder");
			return devOpCmdDao.find(hql.toString(),id,vendorID,osID);
		}*/
	}
	
	public long getCurIntId(){
		String hql="select MAX(opCmdID) from DevOpCmd";
		return devOpCmdDao.findUnique(hql);
	}

	public List<com.sino.cmdb.operation.devOpCmd.entity.DevOpCmdParam> getByOperation(
			int parseInt) {
		return devOpCmdDao.getByOperation(parseInt);
	}

	public List<com.sino.cmdb.operation.devOpCmd.entity.DevOpCmdParam> getByOperationAndVendor(
			int parseInt, int parseInt2) {
		return devOpCmdDao.getByOperationAndVendor(parseInt,parseInt2);
	}

	public List<com.sino.cmdb.operation.devOpCmd.entity.DevOpCmdParam> getByOperationAndVendorAndDevType(
			int parseInt, int parseInt2, int parseInt3) {
		return devOpCmdDao.getByOperationAndVendorAndDevType(parseInt,parseInt2,parseInt3);
	}
}
