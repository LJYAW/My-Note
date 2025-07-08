package com.sino.cmdb.threshold.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.severity.entity.Severity;
import com.sino.cmdb.severity.service.SeverityService;
import com.sino.cmdb.threshold.dao.ThresholdLevelDao;
import com.sino.cmdb.threshold.entity.ParamThresholdLevel;
import com.sino.cmdb.threshold.entity.ThresholdLevel;
import com.sino.cmdb.threshold.entity.ThresholdLevelInfo;
import com.sino.monitor.res.entity.ResSnmpIndicator;
import com.sino.monitor.res.service.ResSnmpIndicatorService;


@Service
@Transactional
public class ThresholdLevelService {
	
	private static String objAttNames = "iD,threshold_ID,threshold_Name,severity_ID,severityEName,severity_Name,lowValue,highValue,count,units,priority";
	private static String jsonAttNames = "id,threshold_ID,threshold_Name,severity_ID,severityEName,severity_Name,lowValue,highValue,count,units,priority";
	

	
	private static String objParamAttNames = "number,lowValue,compareLow,indiName,compareHigh,highValue,count,severity_ID,severity_Name,priority,severity_EName";
	private static String jsonParamAttNames = "number,lowValue,compareLow,indiName,compareHigh,highValue,count,severity_ID,severity_Name,priority,severityEName";
	
	
	@Autowired
	private ThresholdLevelDao thresholdLevelDao;
	
	@Autowired
	private SeverityService severityService;
	
	@Autowired
	private ResSnmpIndicatorService resSnmpIndicatorService;
	
	public void add(ThresholdLevel entity){
		thresholdLevelDao.save(entity);
	}

	public String getJsonListStr(List<ParamThresholdLevel> list) {
		return JsonUtils.getJsonListInfo(list, objParamAttNames, jsonParamAttNames);
	}

	public List<ThresholdLevel> getByThresholdId(String id) {
		String hql="from ThresholdLevel where threshold_ID = ? ";
		return thresholdLevelDao.find(hql,Integer.parseInt(id));
	}
	
	public List<ThresholdLevel> geListtById(int id) {
		String hql="from ThresholdLevel where threshold_ID = ? ";
		return thresholdLevelDao.find(hql,id);
	}
	
	public ThresholdLevel getByScope(int id,float readVal ) {
		String hql="from ThresholdLevel where threshold_ID = ? and (?>=lowValue and ?<highValue)";
		List<ThresholdLevel> list=thresholdLevelDao.find(hql,id,readVal,readVal);
		ThresholdLevel hold=null;
		if(!list.isEmpty()){
			hold=list.get(0);
		}
		return hold;
	}
	
	public Severity getSeverityByScope(int id,float readVal){
		ThresholdLevel hold=getByScope(id,readVal);
		Severity s=null;
		if(hold!=null){
			s=severityService.getById(hold.getSeverity_ID());
		}
		return s;
	}
	
	public List<ThresholdLevelInfo> getLevelInfoById(long id,String indicator ) {
		ResSnmpIndicator ind =resSnmpIndicatorService.getByResId(id, indicator);
		List<ThresholdLevelInfo> levellist=new ArrayList<ThresholdLevelInfo>();
		if(ind!=null&&ind.getThresholdID()!=null){
			List<ThresholdLevel> list=geListtById(ind.getThresholdID());
			for(ThresholdLevel level:list){
				Severity s=severityService.getById(level.getSeverity_ID());
				ThresholdLevelInfo info=new ThresholdLevelInfo(level,s);
				levellist.add(info);
			}
		}
		return levellist;
	}
	
	public void deleteByThresholdID(Integer id){
		thresholdLevelDao.deleteByThresholdID(id);
	}
	
}
