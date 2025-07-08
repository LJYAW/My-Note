package com.sino.cmdb.vendor.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.PropertyFilter;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.product.prodClass.dao.ProdClassDao;
import com.sino.cmdb.vendor.dao.CmdbVendorDao;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;
import com.sino.cmdb.vendor.entity.CmdbVendor;
/**
 * 
 *
 * @author <a href="mailto:wangam@sino-bridge.com">王阿明</a>
 *
 * @version $Revision: 1.7 $
 *
 * @since 2013-1-13
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class CmdbVendorService {
	private static Logger logger = LoggerFactory.getLogger(CmdbVendorService.class);

	private static String objAttNames = "vendorID,vendorName,dispName,fullName,webURL,description,flag,status";

	private static String jsonAttNames = "id,vendorName,dispName,fullName,webURL,description,flag,status";
	
	private static String childAttName = "children";
	
	private static String objMinAttNames = "vendorID,dispName";
	private static String jsonMinAttNames = "id,dispName";
	@Autowired
	private CmdbVendorDao cmdbVendorDao;
	
	@Autowired
	private ProdClassDao prodClassDao;
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final CmdbVendor obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<CmdbVendor> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonMinListStr(final List<CmdbVendor> list) {
		return JsonUtils.getJsonListInfo(list, objMinAttNames, jsonMinAttNames);
	}

	public void save(CmdbVendor entity){
		logger.info("save Entity");
		cmdbVendorDao.save(entity);
	}
	
	@Transactional(readOnly = true)
	public CmdbVendor getCmdbVendorById(Integer id){
		return cmdbVendorDao.get(id);
	}
	public void delete(Integer id){
		cmdbVendorDao.delete(id);
	}
	@Transactional(readOnly = true)
	public List<CmdbVendor> searchCmdbVendor(final List<PropertyFilter> filters) {
		return cmdbVendorDao.find(filters);
	}
	
	public List<CmdbVendor> getAll(){
		String hql=" from CmdbVendor where vendorID > 0 order by vendorID ";
		return cmdbVendorDao.find(hql);
	}
	public void batchSave(List<CmdbVendor> list){
		cmdbVendorDao.batchSave(list, 10);
	}
	public String getJsonVendor(){
		List<CmdbVendor> listItem = getAll();
		return getJsonListStr(listItem);
	}
	
	public List<CmdbVendor> getVendorDispNameList(int flag){
		flag=1;
		String hql=" from CmdbVendor where Flag = 1 order by DispName";
		return cmdbVendorDao.find(hql);
	}
	
	public List<CmdbVendor> getVendorByProdClass(){
		String hql=" from ProdClass as a, CmdbVendor as b where a.vendorID=b.vendorID GROUP BY a.vendorID ORDER BY b.dispName";
		return prodClassDao.find(hql);
	}

	
	public List<CmdbVendor> getVendorById(String id) {
		String hql=" from CmdbVendor where vendorID=? and vendorID > 0 order by vendorID";
		return cmdbVendorDao.find(hql,Integer.parseInt(id));
	}

	public CmdbVendor findVendor(int id) {
		String hql = " from CmdbVendor where vendorID=? ";
		return cmdbVendorDao.findUnique(hql,id);
	}
}
