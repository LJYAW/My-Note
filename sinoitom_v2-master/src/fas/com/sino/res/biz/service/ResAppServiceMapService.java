package com.sino.res.biz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.fas.res.host.entity.ParamResAppService;
import com.sino.fas.res.host.entity.ResAppService;
import com.sino.res.biz.dao.ResAppServiceMapDao;
import com.sino.res.biz.entity.ResAppServiceMap;
import com.sino.res.biz.entity.ResBizSystem;

@Service
@Transactional
public class ResAppServiceMapService {

	private static String objAttNames ="Id,bizAppId,appSvcId";
	private static String jsonAttNames="id,bizAppId,appSvcId";

	@Autowired
	private ResAppServiceMapDao resAppServiceMapDao;
	
	public String getJsonListStr(final List<ResAppServiceMap> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	
	public List<ResAppServiceMap> getAll() {
		return resAppServiceMapDao.getAll();
	}
	
	@Transactional(readOnly = true)
	public List<ResAppServiceMap> getByBizAppId(Long bizAppId) {
		String hql = "from ResAppServiceMap where bizAppId = ?";
		return resAppServiceMapDao.find(hql ,bizAppId);
	}
	
	
	public void saveAppService(Long bizAppId,List<ParamResAppService> resList,List<ResAppServiceMap> mapList){
		List<ResAppServiceMap> appList=new ArrayList<ResAppServiceMap>();
		
//		String[] ids=idList.split(",");
		//页面左侧 已关联所有服务
		for(int i=0;i<resList.size();i++){
			ResAppServiceMap res=new ResAppServiceMap();
//			BeanUtils.copyProperties(r, res);
			//x循环中间表数据库数据
			for(int j=0;j<mapList.size();j++){
				if(mapList.get(j).getBizAppId().equals(bizAppId)){
					if(mapList.get(j).getAppSvcId().equals(resList.get(i).getId())){
						resList.remove(resList.get(i));
					}
					
				}
					
				
			}
			res.setBizAppId(bizAppId);
			res.setAppSvcId(resList.get(i).getId());
			appList.add(res);	
		
			
			
		}
		resAppServiceMapDao.batchSave(appList, 20);
	}
	
	
}
