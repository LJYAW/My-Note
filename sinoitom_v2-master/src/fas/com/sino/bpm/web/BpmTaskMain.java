package com.sino.bpm.web;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import com.sino.bpm.service.BpmTaskService;

public class BpmTaskMain {
	public static void main(String[] args) throws IOException {
		Integer taskId=3;
		String bizIdListStr = "1,";
		
		BpmTaskAction action = new BpmTaskAction();
		
		action.taskRelateBiz(taskId, bizIdListStr, null);
//		BpmTaskService bpmTaskService = new BpmTaskService();
//		
//		if(taskId!=null && StringUtils.isNotBlank(bizIdListStr)){
//			bpmTaskService.taskRelateBiz(taskId,bizIdListStr);
//			System.out.println("定时任务与业务系统关联成功，相关资源保存入库。。。");
//		}
	}
}
