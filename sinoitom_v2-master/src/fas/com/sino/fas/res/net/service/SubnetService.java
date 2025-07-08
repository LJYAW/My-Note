package com.sino.fas.res.net.service;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
import org.springside.modules.utils.Reflections;

import smartlink.utils.DBQueryUtil;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.NetworkUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysUser;
import com.sino.fas.res.net.dao.SubnetDao;
import com.sino.fas.res.net.entity.NcmSubNetwork;
import com.sino.fas.res.net.entity.ParamSubnetwork;


//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class SubnetService {

	private static Logger logger = LoggerFactory.getLogger(SubnetService.class);
	
	private static String objAttNames = "netId,subnetName,subnetAddr,subnetMask,subnetType@Subnet_Type,adminStatus@Subnet_AdminStatus,operStatus@Subnet_OperStatus,orgId@OrgOrganization@orgId@orgShortName,subnetUsage@Subnet_Usage";
	private static String jsonAttNames = "id,subnetName,subnetAddr,subnetMask,subnetType,adminStatus,operStatus,organ,subnetUsage";
	private static String childName = "children";
	
	private static String excelAttNames = "orgId@OrgOrganization@orgId@orgName@orgName,netIpMask,subnetType@Subnet_Type,subnetUsage@Subnet_Usage,adminStatus@Subnet_AdminStatus,operStatus@Subnet_OperStatus,vlanName,subnetName,subnetDesc";

	private String lastMassage = "";

	@Autowired
	private SubnetDao subnetDao;


	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}

	//-- Subnet Manager --//
	@Transactional(readOnly = true)
	public String getJsonObjStr(final NcmSubNetwork obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<NcmSubNetwork> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonTreeStr(final List<NcmSubNetwork> list) {
		String[] nodeAtts = objAttNames.split(",");
		String[] jsonAtts = (jsonAttNames==null || "".equals(jsonAttNames) ) ? nodeAtts:jsonAttNames.split(",");
		assertEquals(nodeAtts.length, jsonAtts.length);

		int attNum = nodeAtts.length;
		
		List<Long> lastIpEnds = new ArrayList<Long>();
		int nodeNum = list.size();
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i = 0; i < nodeNum; i++)
		{
			if( i>0 ){
				if( list.get(i).getHostIpStart()<list.get(i-1).getHostIpEnd() ){
					lastIpEnds.add(list.get(i-1).getHostIpEnd());
					sb.append(", \"").append(childName).append("\": [ ");
				}
				else{
					for( int j=lastIpEnds.size()-1; j>=0; j-- ){
						if( list.get(i).getHostIpStart().compareTo(lastIpEnds.get(j))>0 ){
							lastIpEnds.remove(j);
							sb.append("} ]");
						}
					}
					sb.append(" }, ");
				}
			}
			for( int j=0; j<attNum; j++ )
			{
				String value = JsonUtils.getJsonColValue(list.get(i), nodeAtts[j]);
				if( j==0 ){
					sb.append("{ \"");
				}
				else{
					sb.append(", \"");
				}
				sb.append(jsonAtts[j]).append("\":").append(value);
			}
		}
		for( int j=lastIpEnds.size()-1; j>=0; j-- ){
			sb.append(" } ]");
		}

		if( nodeNum>0 ){
			sb.append(" }");			
		}	
		sb.append(" ]");
		
		return sb.toString();
	}
	
	public List<ParamSubnetwork> getTreeData(List<NcmSubNetwork> list){
		
		List<ParamSubnetwork> plist=new ArrayList<ParamSubnetwork>();
		List<Long> lastIpEnds = new ArrayList<Long>();
		for(NcmSubNetwork subnet:list){
			ParamSubnetwork st=new ParamSubnetwork();
			BeanUtils.copyProperties(subnet, st);
			plist.add(st);
		}
		
		for (int i = 0; i < plist.size(); i++)
		{
			if( i>0 ){
				if( list.get(i).getHostIpStart()<list.get(i-1).getHostIpEnd() ){
					lastIpEnds.add(list.get(i-1).getHostIpEnd());
					
					plist.get(i).setParentId(plist.get(i-1).getNetId());
				}
				else{
					for( int j=lastIpEnds.size()-1; j>=0; j-- ){
						if( list.get(i).getHostIpStart().compareTo(lastIpEnds.get(j))>0 ){
							lastIpEnds.remove(j);
						}
					}
				}
			}
		}
		
		return plist;
		
	}

	@Transactional(readOnly = true)
	public List<NcmSubNetwork> getAllSubnet() {
		String hql = "from NcmSubNetwork order by hostIpStart, hostIpEnd desc";
		return subnetDao.find(hql);
	}
	
	
	public String getClientOrg(long clientIp,List<NcmSubNetwork> sublist){
	    String clientorgName = "未知";
		long minstartIp = -1L;
		long minendIp = 1000000000000000000L;
		for(NcmSubNetwork sub:sublist)
		{
			String corgName = sub.getOrgName();
			long startIp =  sub.getHostIpStart();
			long endIp = sub.getHostIpEnd();
			if(clientIp >= startIp && clientIp <= endIp)
			{
				if(minstartIp <= startIp && minendIp >= endIp)
				{
					minstartIp = startIp;
					clientorgName=corgName;
					minendIp = endIp;
				}
			}
		}
	    return clientorgName;
	}
	
	@Transactional(readOnly = true)
	public List<NcmSubNetwork> searchSubnet(final List<PropertyFilter> filters) {
		return subnetDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<NcmSubNetwork> searchSubnet(final PageRequest page, final List<PropertyFilter> filters) {
		return subnetDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public NcmSubNetwork getSubnet(Long id) {
		return subnetDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public NcmSubNetwork getSubnetBy(String subnetAddr, String subnetMask) {
		if( StringUtils.isBlank(subnetAddr) || StringUtils.isBlank(subnetMask) ){
			return null;
		}
			
		String hql = "from NcmSubNetwork where subnetAddr=? and subnetMask=?";
		return subnetDao.findUnique(hql, subnetAddr, subnetMask);
	}
	
	@Transactional(readOnly = true)
	public boolean loadSubnetAtts(NcmSubNetwork entity, String attNames){
		return subnetDao.loadObjAtts(entity, attNames);
	}

	public void addSubnet(NcmSubNetwork entity) {
		logger.debug("addSubnet...");
		entity.setNetId(DBQueryUtil.getInstance().getDeviceResourceUniqId());
		subnetDao.save(entity);
	}

	public void saveSubnet(NcmSubNetwork entity) {
		logger.debug("saveSubnet...");
		subnetDao.save(entity);
	}

	public void deleteSubnet(Long id) {
		logger.debug("deleteSubnet(id:{})...", id);
		subnetDao.delete(id);
	}
	
	
	public void deleteSubnet(NcmSubNetwork subnet) {
		logger.debug("deleteSubnet(id:{})...");
		subnetDao.deleteSubNetwork(subnet);
	}

	@Transactional(readOnly = true)
	public boolean isSubnetUnique(Long subnetId, String subnetAddr, String subnetMask) {
		return subnetDao.isIdAttUnique(subnetId, new String[]{"subnetAddr","subnetMask"}, new String[]{subnetAddr,subnetMask});
	}

	private boolean getIpMaskInfo(String netIpMask, NcmSubNetwork subnet){
		String warmMsg="";
		String split[] = netIpMask.split("/");
		if( split.length != 2 ){
			warmMsg = "子网IP掩码不正确！";
			return false;
		}
		if( !NetworkUtils.isValidIP(split[0]) ){
			warmMsg = "子网IP不正确！";
			return false;
		}
		String netMask = null;
		if( split[1].indexOf('.')<0 ){
			if( !NetworkUtils.isNumeric(split[1]) ){
				warmMsg = "子网掩码不正确！";
				return false;				
			}
			netMask = NetworkUtils.getMask(Byte.parseByte(split[1]));
		}else{
			if(!"255.255.255.255".equals(split[1])&&!NetworkUtils.isValidMask(split[1]) ){
				warmMsg = "子网掩码不正确！";
				return false;
			}	
			netMask = split[1];
		}
		subnet.setSubnetAddr(split[0]);
		subnet.setSubnetMask(netMask);
		return true;
	}
	
	public boolean importExcel(InputStream stream){
		this.lastMassage = "";
		String warmMsg="";
		ExcelUtil<NcmSubNetwork> excelUtil = new ExcelUtil<NcmSubNetwork>(NcmSubNetwork.class);
		List<NcmSubNetwork> list = excelUtil.getObjListFrom(stream, excelAttNames);
		if( list==null || list.size()==0 ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			return false;
		}
		SysUser curUser = SystemUtils.getLoginUser();
		String operator = curUser.getLoginName();
		Date now = new Date();//获取系统当前时间
		int addNum=0;
		int updateNum=0;
		List<NcmSubNetwork> sublist=new ArrayList<NcmSubNetwork>();
		for(int i=0; i<list.size(); i++){
			NcmSubNetwork subnet = list.get(i);
			
			if(subnet.getOrgId()==null){
				this.lastMassage = "导入失败！第"+i+"行数据中 机构名称不能为空，或有空数据行，请检查！";
				return false;
			}
			if(subnet.getSubnetType()==null){
				this.lastMassage = "导入失败！第"+i+"行数据中 子网地址类型不能为空，或有空数据行，请检查！";
				return false;
			}
			if(subnet.getSubnetUsage()==null){
				this.lastMassage = "导入失败！第"+i+"行数据中 子网类型不能为空，或有空数据行，请检查！";
				return false;
			}
			if(subnet.getAdminStatus()==null){
				this.lastMassage = "导入失败！第"+i+"行数据中 管理状态不能为空，或有空数据行，请检查！";
				return false;
			}
			if(subnet.getOperStatus()==null){
				this.lastMassage = "导入失败！第"+i+"行数据中 工作状态不能为空，或有空数据行，请检查！";
				return false;
			}
			Object ipMask = Reflections.getFieldValue(subnet, "netIpMask");
			if(ipMask==null){
				this.lastMassage = "导入失败！第"+i+"行数据中 IP子网不能为空，或有空数据行，请检查！";
				return false;
			}
			boolean flag=getIpMaskInfo(ipMask.toString(), subnet);
			if(!flag){
				warmMsg=warmMsg+(i+1)+",";
				continue;
			}
			long addr = NetworkUtils.ip2long(subnet.getSubnetAddr());
			long mask = NetworkUtils.ip2long(subnet.getSubnetMask());
			long[] range = NetworkUtils.getNetworkRange(addr, mask);
			long size = range[1] - range[0] +1;
			subnet.setSubnetAddr(NetworkUtils.str_addr(range[0]));
			subnet.setHostIpStart(range[0]);
			subnet.setHostIpEnd(range[1]);
			subnet.setSubnetSize(size);

			subnet.setModifier(operator);
			subnet.setModifyTime(now.getTime());
			subnet.setIsCollection(1);
			NcmSubNetwork subnetFind = getSubnetBy(subnet.getSubnetAddr(), subnet.getSubnetMask());
			if( subnetFind == null ){
				subnet.setNetId(DBQueryUtil.getInstance().getDeviceResourceUniqId());
				subnet.setCreator(operator);
				subnet.setCreateTime(now.getTime());
				sublist.add(subnet);
				addNum++;
			}
			else{
				BeanUtils.copyProperties(subnet, subnetFind);
				sublist.add(subnetFind);
				updateNum++;
			}
		}
		subnetDao.batchSave(sublist, 50);
		this.lastMassage = String.format("导入完毕！新增%d条记录，更新%d条记录。", addNum, updateNum);
		if(!StringUtil.isNullString(warmMsg)){
			this.lastMassage =this.lastMassage +"第"+warmMsg.substring(0, warmMsg.length()-1)+"行子网IP或子网掩码有问题，请检查！";
		}
		return true;
	}
	
	@Transactional(readOnly = true)
	public boolean exportOrganXml(){
		
		return true;
	}
}
