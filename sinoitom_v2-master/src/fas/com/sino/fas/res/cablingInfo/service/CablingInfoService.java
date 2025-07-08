package com.sino.fas.res.cablingInfo.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.utils.Identities;

import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.fas.res.cablingInfo.dao.CablingInfoDao;
import com.sino.fas.res.cablingInfo.entity.NcmCablingInfo;
import com.sino.fas.res.cablingInfo.entity.NcmCablingInfoParam;

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class CablingInfoService {
	
	private static Logger logger = LoggerFactory.getLogger(CablingInfoService.class);
	
	private static String objAttNames = "cablingInfoID,infoPointNo,buildingNo,roomNo,mediaType,cableType,bandWidth,baudRate,length,cabinetNo,moduleNo,modulePort,remark,switchID,switchIP,ifIndex,ifName,verifyFlag";
	private static String jsonAttNames = "id,infoPointNo,buildingNo,roomNo,mediaType,cableType,bandWidth,baudRate,length,cabinetNo,moduleNo,modulePort,remark,switchID,switchIP,ifIndex,ifName,verifyFlag";
	
	private static String objParamAttNames = "cablingInfoID,infoPointNo,buildingNo,roomNo,mediaType,cableType,bandWidth,baudRate,length,cabinetNo,moduleNo,modulePort,remark,switchID,switchIP,ifIndex,ifName,verifyFlag,unVerifyIfName,flag";
	private static String jsonParamAttNames = "cablingInfoID,infoPointNo,buildingNo,roomNo,mediaType,cableType,bandWidth,baudRate,length,cabinetNo,moduleNo,modulePort,remark,switchID,switchIP,ifIndex,ifName,verifyFlag,unVerifyIfName,flag";
	
	
	private static String excelAttNames = "orgId@OrgOrganization@orgId@orgName,infoPointNo,buildingNo,roomNo,mediaType,cableType,baudRate,length,cabinetNo,moduleNo,modulePort,switchIP,ifName,remark";
	
	private String lastMassage = "";
	
	@Autowired
	private CablingInfoDao cablingInfoDao;
	
//	@Autowired
//	private SwitchService switchService;
//	
//	@Autowired
//	private SwitchPortService switchPortService;
	
	@Transactional(readOnly = true)
	public String getLastMassage() {
		return lastMassage;
	}
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<NcmCablingInfo> list){
		logger.debug("getJsonListStr...");
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getParamJsonListStr(final List<NcmCablingInfoParam> list){
		logger.debug("getJsonListStr...");
		return JsonUtils.getJsonListInfo(list, objParamAttNames, jsonParamAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final NcmCablingInfo obj){
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public List<NcmCablingInfo> searchInfo(final List<PropertyFilter> filters) {
		return cablingInfoDao.find(filters);
	}
	
	@Transactional(readOnly = true)
	public List<NcmCablingInfo>  getAll(){
		return cablingInfoDao.getAll("infoPointNo", true);
	}
	
	public void deleteInfo(String id){
		cablingInfoDao.delete(id);
	}
	
	@Transactional(readOnly = true)
	public NcmCablingInfo getById(String id){
		return cablingInfoDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public String getInfoNo(long sid,long ifIndexId){
		String no="";
		String hql="from NcmCablingInfo where switchID=? and ifIndex=?";
		NcmCablingInfo info=cablingInfoDao.findUnique(hql,sid,ifIndexId);
		if(info!=null){
			no=info.getInfoPointNo();
		}
		return no;
	}
	
	@Transactional(readOnly = true)
	public NcmCablingInfo getCablingInfoBy(long sid,long ifIndexId){
		String hql="from NcmCablingInfo where switchID=? and ifIndex=?";
		List<NcmCablingInfo> infos=cablingInfoDao.find(hql,sid,ifIndexId);
		NcmCablingInfo info=new NcmCablingInfo();
		if(!infos.isEmpty()){
			info= infos.get(0);
		}
		return info;
	}
	
	@Transactional(readOnly = true)
	public List<NcmCablingInfo> getByIds(List<String> ids){
		return cablingInfoDao.getCablingInfoByIds(ids);
	}
	
	@Transactional(readOnly = true)
	public NcmCablingInfo getByNo(String infoNo){
		String hql="from NcmCablingInfo where infoPointNo=?";
		return cablingInfoDao.findUnique(hql,infoNo);
	}
	
	public void addInfo(NcmCablingInfo entity) {
		logger.debug("addIpHost...");
		entity.setCablingInfoID(Identities.uuid());
		cablingInfoDao.save(entity);
	}
	
	public void saveInfo(NcmCablingInfo entity) {
		cablingInfoDao.save(entity);
	}
	
	public boolean impExcel(InputStream stream) {
		this.lastMassage = "";
		ExcelUtil<NcmCablingInfo> excelUtil = new ExcelUtil<NcmCablingInfo>(NcmCablingInfo.class);
		List<NcmCablingInfo> infolist=new ArrayList<NcmCablingInfo>();
		List<NcmCablingInfo> list = excelUtil.getObjListFrom(stream, excelAttNames);
		if( list==null || list.size()==0 ){
			this.lastMassage = "导入失败！请检查导入文件数据！";
			return false;
		}
		int addNum=0;
		//int updateNum=0;
		for(int i=0; i<list.size(); i++){
			NcmCablingInfo info = list.get(i);
			if(StringUtil.isNullString(info.getInfoPointNo())){
				this.lastMassage = "导入失败！请检查第"+i+"行数据中 信息点编号不能为空！";
				return false;
			}
			if(!StringUtil.isNullString(info.getBaudRate())){
				info.setBandWidth(StringUtil.getBandwidth(info.getBaudRate()));
			}
			/*if(!StringUtil.isNullString(info.getIfName())&&!StringUtil.isNullString(info.getSwitchIP())){
				NcmSwitch ncmswitch=switchService.getSwitchByIp(info.getSwitchIP());
				if(ncmswitch!=null){
					NcmSwitchPort port=switchPortService.getSwitchPort(info.getIfName(), ncmswitch.getSwitchId());
					if(port!=null){
						NcmSwitchPortId id=port.getId();
						info.setSwitchID(id.getSwitchId());
						info.setIfIndex(id.getIfIndex());
					}
				}
				
			}*/
			info.setVerifyFlag(0);
			info.setCablingInfoID(Identities.uuid());
			System.out.println("get the roomno:>>"+info.getOrgId());
			infolist.add(info);
			addNum++;
		}
		cablingInfoDao.batchSave(infolist, 20);
		this.lastMassage = String.format("导入完毕！新增%d条记录", addNum);
		return true;
	}
	
		//批量保存楼宇信息
		public void batchSave(List<NcmCablingInfoParam> list) {
			logger.debug("batchSave...");
			List<NcmCablingInfo> entityList = new ArrayList<NcmCablingInfo>();
			for (int i = 0, n = list.size(); i < n; i++) {
				NcmCablingInfoParam paraminfo = list.get(i);
				NcmCablingInfo info =cablingInfoDao.get(paraminfo.getCablingInfoID());
				if(info!=null){
					info.setSwitchID(paraminfo.getSwitchID());
					info.setIfIndex(paraminfo.getIfIndex());
					info.setIfName(paraminfo.getUnVerifyIfName());
					if(paraminfo.isFlag()){
						info.setVerifyFlag(1);
					}
				}
				entityList.add(info);
			}
			if (entityList != null && !entityList.isEmpty())
				cablingInfoDao.batchSave(entityList, 20);
		}
}
