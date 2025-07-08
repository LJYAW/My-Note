package com.sino.topo.objectType.service;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.topo.objectType.dao.TopoObjectTypeDao;
import com.sino.topo.objectType.entity.TopoObjectType;

@Service
@Transactional
public class TopoObjectTypeService {

	private static Logger logger = LoggerFactory.getLogger(TopoObjectTypeService.class);
	private static String objAttNames = "objTypeID,objType,objName,objIcon,classCode,className,typeCode,typeName,classEnName,description,status";
	private static String jsonAttNames = "id,objType,objName,objIcon,classCode,className,typeCode,typeName,classEnName,description,status";
	@Autowired
	private TopoObjectTypeDao topoObjectTypeDao;
	
	public List<ResItemParam> getAllClassCode() {
		return topoObjectTypeDao.getAllClassCode();
	}
	
	public List<TopoObjectType> getAll(){
		return topoObjectTypeDao.getAll();
	}
	
	
	@Transactional(readOnly = true)
	public String getJsonTopoObjectTypeObjStr(final TopoObjectType obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public String getJsonTopoObjectTypeListStr(final List<TopoObjectType> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public List<TopoObjectType> getAllTopoObjectType() {
		
		return topoObjectTypeDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public TopoObjectType getTopoObjectTypeByID(String id) {
		return topoObjectTypeDao.get(id);
	}
	
	public void addTopoObjectType(TopoObjectType entity) {
		logger.debug("addTopoObjectType()");
		entity.setObjTypeID(Identities.uuid());
		topoObjectTypeDao.save(entity);
	}
	
	public void saveTopoObjectType(TopoObjectType entity) {
		logger.debug("saveTopoObjectType()");
		
		topoObjectTypeDao.save(entity);
	}
	
	public void deleteTopoObjectType(String id) {
		logger.debug("deleteTopoObjectType(id:{})...", id);
		topoObjectTypeDao.delete(id);
	}
	
	public void deleteTopoObjectTypes(String [] ids) {
		logger.debug("deleteTopoObjectTypes(id:{})...", ids);
		for(String id:ids){
			topoObjectTypeDao.delete(id);
		}
		
	}
	public void editPictureName(String path,String editName, String imgName) throws IOException{
		String imgPath = path+imgName;   
        File file=new File(imgPath); 
        if (!file.exists()) {
         //文件不存在，不做修改文件名操作	
         return ;
        }
        File newFile = new File(path + File.separator + editName);
        
        file.renameTo(newFile);
           
    }
	
	public List<ResItemParam> getTreeByClassCodes() {
		return topoObjectTypeDao.getClassCodes();
	}
	public List<ResItemParam> getTreeByTypeCodes(int parseInt) {
		
		return topoObjectTypeDao.getTypeCodes(parseInt);
	}
	
	@Transactional(readOnly = true)
	public List<TopoObjectType> getByClassCode(int classCode) {
		logger.debug("Enter getByClassCode...");
		
		String hql=" from TopoObjectType where classCode=? order by classCode";
		return topoObjectTypeDao.find(hql, classCode);
	}
	@Transactional(readOnly = true)
	public List<TopoObjectType> getByClassAndType(int typeCode,int classCode) {
		logger.debug("Enter getByClassAndType...");
		
		String hql=" from TopoObjectType where  classCode=? and typeCode=?  order by classCode,typeCode";
		return topoObjectTypeDao.find(hql, classCode,typeCode);
	}
}
