package com.sino.fas.res.net.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.fas.res.net.dao.NcmDevAccessCmdDao;
import com.sino.fas.res.net.entity.NcmDevAccessCmd;

@Service
@Transactional
public class NcmDevAccessCmdService {

	private static Logger logger = LoggerFactory.getLogger(NcmDevAccessCmdService.class);
	
	private static String objAttNames ="devAcsCmdId,vendorId@CmdbVendor@vendorID@dispName,vendorOid,osType,osName,osVersion,userAcsPrompt,passwdPrompt,userModePrompt,privModeCmd,privPasswdPrompt,privModePrompt,cmdExecMode,errorCmdPrompt,logoutCmd,morePrompt,moreCmd,noMoreCmd,status,remark";
	private static String jsonAttNames ="id,vendorName,vendorOid,osType,osName,osVersion,userAcsPrompt,passwdPrompt,userModePrompt,privModeCmd,privPasswdPrompt,privModePrompt,cmdExecMode,errorCmdPrompt,logoutCmd,morePrompt,moreCmd,noMoreCmd,status,remark";
			
	
	@Autowired
	private  NcmDevAccessCmdDao devAccessCmdDao;
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final NcmDevAccessCmd obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<NcmDevAccessCmd> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevAccessCmd> getAll() {
		return devAccessCmdDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevAccessCmd> getDevAcsCmd(String vendorId) {
		String hql = "from NcmDevAccessCmd where vendorId=? order by osName";
		return devAccessCmdDao.find(hql, Integer.parseInt(vendorId));
	}
	
	@Transactional(readOnly = true)
	public NcmDevAccessCmd getById(String id) {
		return devAccessCmdDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public boolean isAccessCmdUnique(String id,int verdorID,String osType,String osName,String osVersion ) {
		NcmDevAccessCmd prompt=null;
		if(StringUtil.isNullString(id)){
			
			String hql=" from NcmDevAccessCmd where vendorId=? and osType=? and osName=? and osVersion=?";
			 prompt= devAccessCmdDao.findUnique(hql,verdorID, osType,osName,osVersion);
			 if(prompt==null){
					return true;
				}
		}else{
			String hql=" from NcmDevAccessCmd where devAcsCmdId=? and vendorId=? and osType=? and osName=? and osVersion=?";
			 prompt= devAccessCmdDao.findUnique(hql,id,verdorID, osType,osName,osVersion);
			 if(prompt!=null){
					return true;
				}
		}
		
		return false;
	}
	
	public void addDevPrompt(NcmDevAccessCmd entity) {
		logger.debug("add NcmDevAccessCmd...");
		entity.setDevAcsCmdId(Identities.uuid());
		devAccessCmdDao.save(entity);
	}

	public void saveDevPrompt(NcmDevAccessCmd entity) {
		logger.debug("save NcmDevAccessCmd...");
		devAccessCmdDao.save(entity);
	}

	public void deleteAccessCmd(String id) {
		logger.debug("delete NcmDevAccessCmd(id:{})...", id);
		devAccessCmdDao.delete(id);
	}
	
	@Transactional(readOnly = true)
	public List<NcmDevAccessCmd> searchAccessCmd(final List<PropertyFilter> filters) {
		return devAccessCmdDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public NcmDevAccessCmd getPromptByVendorAndOsNameAndOsVersion(int vendorID,String osName,String osVersion) {
		String hql = " from NcmDevAccessCmd where vendorID=? and osName=? and osVersion=? ";
		NcmDevAccessCmd prompt=devAccessCmdDao.findUnique(hql, vendorID,osName,osVersion);
		if(prompt==null){
			hql=" from NcmDevAccessCmd where vendorID=? and osName=? and (osVersion=? or osVersion='X.X')";
			List<NcmDevAccessCmd> prompts=devAccessCmdDao.find(hql, vendorID,osName,osVersion);
			if(!prompts.isEmpty()){
				prompt=prompts.get(0);
			}
		}
		return prompt;
	}
}
