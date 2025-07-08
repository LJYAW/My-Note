package com.sino.fas.res.db.mysqlPerformance.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sino.fas.res.db.mysqlPerformance.entity.DetectionIndicator;
import com.sino.fas.res.db.mysqlPerformance.entity.SqlCommandResult;

public class SetValue2DetectionIndicator {
	
	public static void setValue(List<SqlCommandResult> sqlResultList,DetectionIndicator detectionIndicator){
		detectionIndicator.setThreadsCached(sqlResultList);
		detectionIndicator.setThreadsConnected(sqlResultList);
		detectionIndicator.setThreadsCreated(sqlResultList);
		detectionIndicator.setThreadsRunning(sqlResultList);
		detectionIndicator.setMaxConnections(sqlResultList);
		detectionIndicator.setMaxUsedConnections(sqlResultList);
		detectionIndicator.setAbortedClients(sqlResultList);
		detectionIndicator.setQuestions(sqlResultList);
		detectionIndicator.setSelectFullJoin(sqlResultList);
		detectionIndicator.setSelectScan(sqlResultList);
		detectionIndicator.setComCommit(sqlResultList);
		detectionIndicator.setComRollback(sqlResultList);
		detectionIndicator.setQueryCacheLimit(sqlResultList);
		detectionIndicator.setQueryCacheMinResUnit(sqlResultList);
		detectionIndicator.setQueryCacheSize(sqlResultList);
		detectionIndicator.setQueryCacheType(sqlResultList);
		detectionIndicator.setQueryCacheWlockInvalidate(sqlResultList);
		detectionIndicator.setTableLocksImmediate(sqlResultList);
		detectionIndicator.setTableLocksWaited(sqlResultList);
		detectionIndicator.setRowLock(sqlResultList);
		detectionIndicator.setSlowQueries(sqlResultList);
		
		// 。。。。。。。。。。。。。没有写完，太长了，
		
		System.out.println("ThreadsCreated--"+detectionIndicator.getThreadsCreated());
		System.out.println("ThreadsConnected"+detectionIndicator.getThreadsConnected());
		System.out.println("ThreadsCreated--"+detectionIndicator.getThreadsCreated());
		System.out.println("ThreadsRunning--"+detectionIndicator.getThreadsRunning());
		
	}


}
