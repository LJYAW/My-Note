package com.sino.base.system.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;

import com.sino.base.system.dao.OrgEmployeeDao;
import com.sino.base.system.entity.OrgEmployee;
import com.sino.base.system.entity.SysUser;

/**
 * 职员管理类
 * 
 * @author sujp
 */
//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class EmployeeService {
	private static Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	
	private static String objAttNames = "empId,name,sex@sex,workNo,phone,mobile,email,mainOrgId@OrgOrganization@orgId@orgShortName,mainDptId@OrgOrganization@orgId@orgName";
	private static String jsonAttNames = "id,name,sex,workNo,phone,mobile,email,organ,mainDpt";
	private static String objMinAttNames = "empId,name";
	private static String jsonMinAttNames = "id,name";
	
	private static String excelAttNames = "mainOrgId@OrgOrganization@orgId@orgName,name,sex@sex,workNo,phone,mobile,email";
	
	private String lastMassage="";
	
	public String getLastMassage() {
		return lastMassage;
	}

	public void setLastMassage(String lastMassage) {
		this.lastMassage = lastMassage;
	}


	@Autowired
	private OrgEmployeeDao employeeDao;

	@Transactional(readOnly = true)
	public String getJsonObjStr(final OrgEmployee obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<OrgEmployee> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonMinListStr(final List<OrgEmployee> list) {
		return JsonUtils.getJsonListInfo(list, objMinAttNames, jsonMinAttNames);
	}

	@Transactional(readOnly = true)
	public List<OrgEmployee> getAllEmployee() {
		String hql = "from OrgEmployee order by mainOrgId, createTime";
		return employeeDao.find(hql);
	}
	
	public List<OrgEmployee> getOrgEmployee(String orgId) {
		String hql = "from OrgEmployee where mainOrgId=? order by createTime";
		return employeeDao.find(hql, orgId);
	}
	
	@Transactional(readOnly = true)
	public List<OrgEmployee> searchEmployee(final List<PropertyFilter> filters) {
		return employeeDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<OrgEmployee> searchEmployee(final PageRequest page, final List<PropertyFilter> filters) {
		return employeeDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public OrgEmployee getEmployee(String id) {
		return employeeDao.get(id);
	}
	
	public void addEmployee(OrgEmployee entity) {
		logger.debug("addEmployee(name:{})...", entity.getName());
		entity.setEmpId(Identities.uuid());
		employeeDao.save(entity);
	}

	public void saveEmployee(OrgEmployee entity) {
		logger.debug("saveEmployee(name:{})...", entity.getName());
		employeeDao.save(entity);
	}

	public void deleteEmployee(String id) {
		logger.debug("deleteEmployee(id:{})...", id);
		employeeDao.delete(id);
	}
	
	public OrgEmployee getEmpByName(String name) {
		if( StringUtils.isBlank(name) ){
			return null;
		}
		return employeeDao.findUniqueBy("name", name);
	}
	
	
	public boolean impExcel(InputStream stream) {
		this.lastMassage = "";
		ExcelUtil<OrgEmployee> excelUtil = new ExcelUtil<OrgEmployee>(OrgEmployee.class);
		List<OrgEmployee> emplist=new ArrayList<OrgEmployee>();
		List<OrgEmployee> list = excelUtil.getObjListFrom(stream, excelAttNames);
		if( list==null || list.size()==0 ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			return false;
		}
		SysUser curUser = SystemUtils.getLoginUser();
		Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		int addNum=0;
		//int updateNum=0;
		for(int i=0; i<list.size(); i++){
			OrgEmployee orgemp = list.get(i);
			if(StringUtil.isNullString(orgemp.getMainOrgId())){
				this.lastMassage = "导入失败！请检查第"+i+"行数据中 机构名称不能为空！";
				return false;
			}
			if(StringUtil.isNullString(orgemp.getName())){
				this.lastMassage = "导入失败！请检查第"+i+"行数据中 姓名不能为空！";
				return false;
			}
			orgemp.setEmpId(Identities.uuid());
			orgemp.setCreateTime(now);
			String operator = curUser.getLoginName();
			orgemp.setModifier(operator);
			orgemp.setCreator(operator);
			orgemp.setModifyTime(now);
			orgemp.setStatus(0);
			emplist.add(orgemp);
			addNum++;
		}
		employeeDao.batchSave(emplist, 20);
		this.lastMassage = String.format("导入完毕！新增%d条记录", addNum);
		return true;
	}
	
}
