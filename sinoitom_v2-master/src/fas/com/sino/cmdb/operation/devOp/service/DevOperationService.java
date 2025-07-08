package com.sino.cmdb.operation.devOp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.PropertyFilter;

import smartlink.utils.DBQueryUtil;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.device.entity.DevIndicatorItems;
import com.sino.cmdb.operation.devOp.dao.DevOperationDao;
import com.sino.cmdb.operation.devOp.entity.DevOperation;
/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2013-2-11
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class DevOperationService {
	private static Logger logger = LoggerFactory.getLogger(DevOperationService.class);

	private static String objAttNames = "devOpID,opID,devClassCode,devClassName,devTypeCode,devTypeName,accessModeCode,accessMode,opTypeCode,opTypeName,operation,operateName,devOpName,description,status";

	private static String jsonAttNames = "id,opID,devClassCode,devClassName,devTypeCode,devTypeName,accessModeCode,accessMode,opTypeCode,opTypeName,operation,operateName,devOpName,description,status";
	@Autowired
	private DevOperationDao devOperationDao; 
	@Transactional(readOnly = true)
	public String getJsonObjStr(final DevOperation obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<DevOperation> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	public void save(DevOperation entity){
		logger.info("save Entity");
		//entity.setOperationID(DBQueryUtil.getInstance().getDeviceResourceUniqId());
		devOperationDao.save(entity);
	}
	@Transactional(readOnly = true)
	public DevOperation getDevOperationById(int id){
		return devOperationDao.get(id);
	}
	public void delete(int id){
		devOperationDao.delete(id);
	}
	@Transactional(readOnly = true)
	public List<DevOperation> searchDevOperation(final List<PropertyFilter> filters) {
		return devOperationDao.find(filters);
	}

	public List<DevOperation> getAll() {
		logger.debug("Enter getAll...");
		String hql=" from DevOperation order by accessModeCode,devOpID";
		return devOperationDao.find(hql);
	}
	
	public List<DevOperation> getDevOperationBySnmp(String opID) {
		logger.debug("Enter getDevOperationBySnmp...");
		String hql=" from DevOperation where opID=? and accessMode='Snmp'";
		return devOperationDao.find(hql,Integer.valueOf(opID));
	}
	
	public void batchSave(List<Integer> opIds){
		logger.debug("Enter batchSave...");
		devOperationDao.batchAudit(opIds);
	}

	public List<DevOperation> getByClassCode(int parseInt) {
		logger.debug("Enter getByClassCode...");
		String hql=" from DevOperation where accessModeCode=? order by accessModeCode,devOpID";
		return devOperationDao.find(hql, parseInt);
	}

	public List<DevOperation> getByClassAndType(int parseInt,int parseLong) {
		logger.debug("Enter getByClassAndType...");
		String hql=" from DevOperation where accessModeCode=? and opID=? order by accessModeCode,devOpID ";
		return devOperationDao.find(hql, parseInt,parseLong);
	}
	
	public List<DevOperation> getByParams(int opID,int devClassCode,int devTypeCode,String accessCode){
		String hql=" from DevOperation where opID=? and devClassCode=? and (devTypeCode=? or devTypeCode=-1)  and accessMode=?";
		return devOperationDao.find(hql, opID,devClassCode,devTypeCode,accessCode);
	}
}
