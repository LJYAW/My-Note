package com.sino.ack.taskMapRes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.ack.resources.entity.AckResources;
import com.sino.ack.resources.entity.ParamSnmpRes;
import com.sino.ack.taskMapRes.dao.AckTaskMapResDao;
import com.sino.ack.taskMapRes.entity.AckTaskMapRes;
import com.sino.ack.taskMapRes.entity.ParamTaskMapRes;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;


@Service
@Transactional
public class AckTaskMapResService {
	
	private static String objAttNames ="taskResId,ackTaskId,resId";
	private static String jsonAttNames="id,ackTaskId,resId";

	@Autowired
	private AckTaskMapResDao ackTaskMapResDao ;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<AckTaskMapRes> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	public List<AckTaskMapRes> getAll() {
		return ackTaskMapResDao.getAll();
	}
	
	public void saveAckResources(Integer ackTaskId,List<AckResources> resList){
		List<AckTaskMapRes> resourceList=new ArrayList<AckTaskMapRes>();
		for(int i=0;i<resList.size();i++){
			AckResources r=(resList.get(i));
			AckTaskMapRes res=new AckTaskMapRes();
//			BeanUtils.copyProperties(r, res);
			
			res.setAckTaskId(ackTaskId);
			res.setResId(r.getResId());
			resourceList.add(res);
		}
		ackTaskMapResDao.batchSave(resourceList, 20);
	}

}
