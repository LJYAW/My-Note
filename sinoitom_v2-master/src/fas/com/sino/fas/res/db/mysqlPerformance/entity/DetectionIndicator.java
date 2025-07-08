package com.sino.fas.res.db.mysqlPerformance.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

//指标项
public class DetectionIndicator {
//	主键id
	private String detectionIndicatorId;
//	创建时间
	private String createTime;
	
//	版本
	private String version;
	//指标组id
	private String groupId;
	
	
//	缓存线程数
	private Integer threadsCached;
//	当前开放的线程数
	private Integer threadsConnected;
//	一创建线程数
	private Integer threadsCreated;
//	当前运行线程数
	private Integer threadsRunning;
	
	
//	设置的最大连接数
	private Integer maxConnections;
//	响应的连接数
	private Integer maxUsedConnections;
//	连接数利用率（理想值 ≈ 85%）
	private Double connectionUseRate;
	
	
//	客户端被异常中断的数值，即连接到mysql服务器的客户端没有正常地断开或关闭。
//	对于一些应用程序是没有影响的，但对于另一些应用程序可能你要跟踪该值，因为异常中断连接可能表明了一些应用程序有问题。
	private Integer abortedClients;
	
	
//	每秒查询数量
	private Integer questions;
	
	
//	主键联合执行次数
//	没有主键联合的执行。该值可能是零。这是捕获开发错误的好方法，因为一些这样的查询可能降低系统的性能。
	private Integer selectFullJoin;
	
	
//	执行全表搜索查询的数量
//	在某些情况下是没问题的，但占总查询数量该比值应该是常量（即Select_scan/总查询数量商应该是常数）。
//	如果你发现该值持续增长，说明需要优化，缺乏必要的索引或其他问题。
	private Integer selectScan;
	
	
//	提交的事务数量
	private Integer comCommit;
//	回滚的事务数量
	private Integer comRollback;
//	每秒的事务数量
	private Integer transactionPerSec;
	
	
	private Integer qcacheFreeBlocks;
	private Integer qcacheTotalBlocks;
	private Integer qcacheHits;
	private Integer qcacheInserts;
	private Long qcacheFreeMemory;
	
	
//	查询缓存的界限，超过此大小的查询将不缓存 
	private Long queryCacheLimit;
//	查询缓存块的最小的容量
	private Long queryCacheMinResUnit;
//	查询缓存大小 
	private Long queryCacheSize;
//	缓存类型，决定缓存什么样的查询，示例中表示不缓存 select sql_no_cache 查询 
	private String queryCacheType;
//	#当有其他客户端正在对MyISAM表进行写操作时，如果查询在query cache中，是否返回cache结果还是等写操作完成再读表获取结果。
	private String queryCacheWlockInvalidate;
	
//	查询缓存碎片率= Qcache_free_blocks / Qcache_total_blocks * 100%
	private Double qCacheFragmentationRate;
//	查询缓存命中率= (Qcache_hits – Qcache_inserts) / Qcache_hits * 100%
	private Double qCacheHitRate;
//	查询缓存利用率= (query_cache_size – Qcache_free_memory) / query_cache_size * 100%
	private Double qCacheUtilizationRate;
	
	
//	表示立即释放表锁数
	private Long tableLocksImmediate;
//	表示需要等待的表锁数
	private Long tableLocksWaited;
//	表锁性能：Table_locks_immediate / Table_locks_waited > 5000（临界值），
//	最好采用InnoDB引擎，因为InnoDB是行锁而MyISAM是表锁，对于高并发写入的应用InnoDB效果会好些
	private Double tableLocksRate;

//	行锁阻塞程度
//	rowLock=Innodb_row_lock_waits=3#针对innodb 行锁，如果值较大，可能是间隙锁造成
	private Integer rowLock;
	
	
//	表扫描率 ＝ Handler_read_rnd_next / Com_select=
//	如果表扫描率超过4000，说明进行了太多表扫描，很有可能索引没有建好，
//	增加read_buffer_size值会有一些好处，但最好不要超过8MB
	private Long handlerReadRndNext;
	private Long comSelect;
	private Double comSelectRate;
	
	
//	慢查询数量
	private Integer slowQueries;
	
	
	private Integer keyReads;
	private Integer keyWrites;
	private Integer keyReadRequests;
	private Integer keyWriteRequests;
//	key Buffer 读命中率= (1-key_reads / key_read_requests) * 100%
	private Double keyBufferReadHits;
//	key Buffer 写命中率= (1-key_writes / key_write_requests) * 100%
	private Double keyBufferWriteHits;
	
	
//	InnoDB Buffer读命中率= (1 - innodb_buffer_pool_reads / innodb_buffer_pool_read_requests) * 100%
	private Integer innodbBufferPoolReads;
	private Long innodbBufferPoolReadRequests;
	private Double innodbBufferReadHits;
		
	
//	索引未命中缓存的概率(MyISAM)=Key_reads / Key_read_requests * 100%
	private Double keyCacheMissRate ;
	
	
//	缓存簇(blocks)数利用率=Key_blocks_used / (Key_blocks_unused + Key_blocks_used) * 100%，
//	理想值 ≈ 80%
	private Integer keyBlocksUsed;
	private Integer keyBlocksUnused;
	private Double keyBlocksUnusedRate;
	
//	临时表磁盘利用率=Created_tmp_disk_tables / Created_tmp_tables * 100% 
//	（理想值<= 25%）
	private Integer createdTmpDiskTables;
	private Integer createdTmpTables;
	private Double tmpDiskRate;
	
	
//	open table利用率=Open_tables / Opened_tables * 100% 
//	理想值 （>= 85%） 
	private Integer openTables;
	private Integer openedTables;
	private Double openTableRate;
	
	
//	Sort_merge_passes 包括两步。MySQL 首先会尝试在内存中做排序，
//	使用的内存大小由系统变量Sort_buffer_size 决定，如果它的大小不够把所有的记录都读到内存中，
//	MySQL 就会把每次在内存中排序的结果存到临时文件中，等MySQL 找到所有记录之后，
//	再把临时文件中的记录做一次排序。这再次排序就会增加 Sort_merge_passes。
//	实际上，MySQL会用另一个临时文件来存再次排序的结果，
//	所以通常会看到 Sort_merge_passes增加的数值是建临时文件数的两倍。因为用到了临时文件，
//	所以速度可能会比较慢，增加 Sort_buffer_size 会减少Sort_merge_passes 和 创建临时文件的次数，
//	但盲目的增加Sort_buffer_size 并不一定能提高速度
	private Integer sortMergePasses ;
	
	
//	openFiles利用率=Open_files / open_files_limit * 100% 
//	理想值<= 75％
	private Integer openFiles;
	private Integer openFilesLimit;
	private Double openFilesRate;
	
	
//	（事务类）二进志日志缓存的已经存在硬盘的条数 
	private Integer binlogCacheDiskUse;
//	（事务类）二进制日志已缓存的条数（内存中）    注意，这个不是容量，而是事务个数。每次有一条事务提交，都会有一次增加
	private Integer binlogCacheUse;
//	（非事务类）二进志日志缓存的已经存在硬盘的条数  
	private Integer binlogStmtCacheDiskUse;
//	（非事务类）二进制日志已缓存的条数（内存中） 非事务型的语句，都存在这儿，比如MYISAM引擎的表，插入记录就存在这儿
	private Integer binlogStmtCacheUse;
	

	public String getDetectionIndicatorId() {
		return detectionIndicatorId;
	}

	public void setDetectionIndicatorId(String detectionIndicatorId) {
		this.detectionIndicatorId = detectionIndicatorId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getThreadsCached() {
		return threadsCached;
	}

	public void setThreadsCached(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Threads_cached")){
							this.threadsCached = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getThreadsConnected() {
		return threadsConnected;
	}

	public void setThreadsConnected(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Threads_connected")){
							this.threadsConnected = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getThreadsCreated() {
		return threadsCreated;
	}

	public void setThreadsCreated(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Threads_created")){
							this.threadsCreated = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getThreadsRunning() {
		return threadsRunning;
	}

	public void setThreadsRunning(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Threads_running")){
							this.threadsRunning = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("max_connections")){
							this.maxConnections = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getMaxUsedConnections() {
		return maxUsedConnections;
	}

	public void setMaxUsedConnections(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Max_used_connections")){
							this.maxUsedConnections = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getConnectionUseRate() {
		return connectionUseRate;
	}

	public void setConnectionUseRate() {
		if(this.maxConnections!=0){
			this.connectionUseRate = (double) (this.maxUsedConnections/this.maxConnections);
		}else{
			this.connectionUseRate = 0.0;
		}
	}

	public Integer getAbortedClients() {
		return abortedClients;
	}

	public void setAbortedClients(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Aborted_clients")){
							this.abortedClients = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getQuestions() {
		return questions;
	}

	public void setQuestions(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Questions")){
							this.questions = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getSelectFullJoin() {
		return selectFullJoin;
	}

	public void setSelectFullJoin(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Select_full_join")){
							this.selectFullJoin = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getSelectScan() {
		return selectScan;
	}

	public void setSelectScan(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Select_scan")){
							this.selectScan = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getComCommit() {
		return comCommit;
	}

	public void setComCommit(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Com_commit")){
							this.comCommit = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getComRollback() {
		return comRollback;
	}

	public void setComRollback(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Com_rollback")){
							this.comRollback = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getTransactionPerSec() {
		return transactionPerSec;
	}

	public void setTransactionPerSec() {//最好有个时间段来计算,这里直接相加了
		this.transactionPerSec = this.comCommit+this.comRollback;
	}

	public Integer getQcacheFreeBlocks() {
		return qcacheFreeBlocks;
	}

	public void setQcacheFreeBlocks(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Qcache_free_blocks")){
							this.qcacheFreeBlocks = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getQcacheTotalBlocks() {
		return qcacheTotalBlocks;
	}

	public void setQcacheTotalBlocks(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Qcache_total_blocks")){
							this.qcacheTotalBlocks = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getQcacheHits() {
		return qcacheHits;
	}

	public void setQcacheHits(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Qcache_hits")){
							this.qcacheHits = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getQcacheInserts() {
		return qcacheInserts;
	}

	public void setQcacheInserts(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Qcache_inserts")){
							this.qcacheInserts = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Long getQcacheFreeMemory() {
		return qcacheFreeMemory;
	}

	public void setQcacheFreeMemory(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Qcache_free_memory")){
							this.qcacheFreeMemory = Long.parseLong(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getqCacheFragmentationRate() {
		return qCacheFragmentationRate;
	}

	public void setqCacheFragmentationRate(Double qCacheFragmentationRate) {
//		Qcache_free_blocks / Qcache_total_blocks * 100%=100%
		if(this.qcacheTotalBlocks!=0){
			this.qCacheFragmentationRate = (double)this.qcacheFreeBlocks/qcacheTotalBlocks;
		}else{
			this.qCacheFragmentationRate = 0.0;
		}
		
	}

	public Double getqCacheHitRate() {
		return qCacheHitRate;
	}

	public void setqCacheHitRate(Double qCacheHitRate) {
//		(Qcache_hits – Qcache_inserts) / Qcache_hits * 100%=0
		if(this.qcacheHits!=0){
			this.qCacheHitRate = (double)((this.qcacheHits-this.qcacheInserts)/this.qcacheHits);
		}else{
			this.qCacheHitRate = 0.0;
		}
	}


	public Long getQueryCacheLimit() {
		return queryCacheLimit;
	}

	public void setQueryCacheLimit(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("query_cache_limit")){
							this.queryCacheLimit = Long.parseLong(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Long getQueryCacheMinResUnit() {
		return queryCacheMinResUnit;
	}

	public void setQueryCacheMinResUnit(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Qcache_free_blocks")){
							this.queryCacheMinResUnit = Long.parseLong(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Long getQueryCacheSize() {
		return queryCacheSize;
	}

	public void setQueryCacheSize(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("query_cache_size")){
							this.queryCacheSize = Long.parseLong(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public String getQueryCacheType() {
		return queryCacheType;
	}

	public void setQueryCacheType(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("query_cache_type")){
							this.queryCacheType = value;
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public String getQueryCacheWlockInvalidate() {
		return queryCacheWlockInvalidate;
	}

	public void setQueryCacheWlockInvalidate(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("query_cache_wlock_invalidate")){
							this.queryCacheWlockInvalidate = value;
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}
	
	public Double getqCacheUtilizationRate() {
		return qCacheUtilizationRate;
	}

//	(query_cache_size – Qcache_free_memory) / query_cache_size * 100%
	public void setqCacheUtilizationRate() {
		if(this.queryCacheSize!=0){
			this.qCacheUtilizationRate = (double)((this.queryCacheSize-this.qcacheFreeMemory)/this.queryCacheSize);
		}else{
			this.qCacheUtilizationRate =0.0;
		}
	}

	public Long getTableLocksImmediate() {
		return tableLocksImmediate;
	}

	public void setTableLocksImmediate(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Table_locks_immediate")){
							this.tableLocksImmediate = Long.parseLong(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Long getTableLocksWaited() {
		return tableLocksWaited;
	}

	public void setTableLocksWaited(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Table_locks_waited")){
							this.tableLocksWaited = Long.parseLong(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getTableLocksRate() {
		return tableLocksRate;
	}

//	table_locks_waited/table_locks_immediate
	public void setTableLocksRate() {
		if(this.tableLocksImmediate!=0){
			this.tableLocksRate = (double)(this.tableLocksWaited/this.tableLocksImmediate);
		}else{
			this.tableLocksRate =0.0;
		}
	}
	
	public Integer getRowLock() {
		return rowLock;
	}

	public void setRowLock(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Innodb_row_lock_waits")){
							this.rowLock = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Long getHandlerReadRndNext() {
		return handlerReadRndNext;
	}

	public void setHandlerReadRndNext(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Handler_read_rnd_next")){
							this.handlerReadRndNext = Long.parseLong(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Long getComSelect() {
		return comSelect;
	}

	public void setComSelect(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Com_select")){
							this.comSelect = Long.parseLong(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getComSelectRate() {
		return comSelectRate;
	}

//	Handler_read_rnd_next / Com_select
	public void setComSelectRate(Double comSelectRate) {
		if(this.comSelect!=0){
			this.comSelectRate = (double)(handlerReadRndNext/this.comSelect);
		}else{
			this.comSelectRate = 0.0;
		}
		
	}

	public Integer getSlowQueries() {
		return slowQueries;
	}

	public void setSlowQueries(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Slow_queries")){
							this.slowQueries = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getKeyReads() {
		return keyReads;
	}

	public void setKeyReads(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Key_reads")){
							this.keyReads = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getKeyWrites() {
		return keyWrites;
	}

	public void setKeyWrites(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Key_writes")){
							this.keyWrites = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getKeyReadRequests() {
		return keyReadRequests;
	}

	public void setKeyReadRequests(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Key_read_requests")){
							this.keyReadRequests = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getKeyWriteRequests() {
		return keyWriteRequests;
	}

	public void setKeyWriteRequests(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Key_write_requests")){
							this.keyWriteRequests = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getKeyBufferReadHits() {
		return keyBufferReadHits;
	}

	public void setKeyBufferReadHits() {
//		(1-key_reads / key_read_requests)
		if(this.keyReadRequests!=0){
			this.keyBufferReadHits = (double)((1-this.keyReads / this.keyReadRequests));
		}else{
			this.keyBufferReadHits = 0.0;
		}
		
	}

	public Double getKeyBufferWriteHits() {
		return keyBufferWriteHits;
	}

	public void setKeyBufferWriteHits() {
//		(1-key_writes / key_write_requests)
		if(this.keyWriteRequests!=0){
			this.keyBufferWriteHits = (double)((1-this.keyWrites / this.keyWriteRequests));
		}else{
			this.keyBufferWriteHits = 0.0;
		}
	}

	public Integer getInnodbBufferPoolReads() {
		return innodbBufferPoolReads;
	}

	public void setInnodbBufferPoolReads(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Innodb_buffer_pool_reads")){
							this.innodbBufferPoolReads = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Long getInnodbBufferPoolReadRequests() {
		return innodbBufferPoolReadRequests;
	}

	public void setInnodbBufferPoolReadRequests(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Innodb_buffer_pool_read_requests")){
							this.innodbBufferPoolReadRequests = Long.parseLong(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getInnodbBufferReadHits() {
		return innodbBufferReadHits;
	}

	public void setInnodbBufferReadHits() {
//		(1 - innodb_buffer_pool_reads / innodb_buffer_pool_read_requests)
		if(this.innodbBufferPoolReadRequests!=0){
			this.innodbBufferReadHits = (double)(1-this.innodbBufferPoolReads/innodbBufferPoolReadRequests);
		}else{
			this.innodbBufferReadHits = 0.0;
		}
		
	}

	public Double getKeyCacheMissRate() {
		return keyCacheMissRate;
	}

	public void setKeyCacheMissRate(Double keyCacheMissRate) {
//		Key_reads / Key_read_requests
		if(this.keyReadRequests!=0){
			this.keyCacheMissRate = (double)(this.keyReads / this.keyReadRequests);
		}else{
			this.keyCacheMissRate = 0.0;
		}
		
	}

	public Integer getKeyBlocksUsed() {
		return keyBlocksUsed;
	}

	public void setKeyBlocksUsed(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Key_blocks_used")){
							this.keyBlocksUsed = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getKeyBlocksUnused() {
		return keyBlocksUnused;
	}

	public void setKeyBlocksUnused(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Key_blocks_unused")){
							this.keyBlocksUnused = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getKeyBlocksUnusedRate() {
		return keyBlocksUnusedRate;
	}

	public void setKeyBlocksUnusedRate() {
//		Key_blocks_used / (Key_blocks_unused + Key_blocks_used)
		if(this.keyBlocksUsed!=0 && this.keyBlocksUnused!=0){
			this.keyBlocksUnusedRate = (double)(this.keyBlocksUsed/(this.keyBlocksUsed+this.keyBlocksUnused));
		}
		
	}

	public Integer getCreatedTmpDiskTables() {
		return createdTmpDiskTables;
	}

	public void setCreatedTmpDiskTables(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Created_tmp_disk_tables")){
							this.createdTmpDiskTables = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getCreatedTmpTables() {
		return createdTmpTables;
	}

	public void setCreatedTmpTables(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Created_tmp_tables")){
							this.createdTmpTables = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getTmpDiskRate() {
		return tmpDiskRate;
	}

	public void setTmpDiskRate(Double tmpDiskRate) {
//		Created_tmp_disk_tables / Created_tmp_tables
		if(this.createdTmpTables!=0){
			this.tmpDiskRate = (double)(this.createdTmpDiskTables/this.createdTmpTables);
		}else{
			this.tmpDiskRate = 0.0;
		}
	}

	public Integer getOpenTables() {
		return openTables;
	}

	public void setOpenTables(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Open_tables")){
							this.openTables = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getOpenedTables() {
		return openedTables;
	}

	public void setOpenedTables(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Opened_tables")){
							this.openedTables = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getOpenTableRate() {
		return openTableRate;
	}

	public void setOpenTableRate(Double openTableRate) {
//		Open_tables / Opened_tables
		if(this.openedTables!=0){
			this.openTableRate = (double)(openTables/openedTables);
		}else{
			this.openTableRate = 0.0;
		}
		
	}

	public Integer getSortMergePasses() {
		return sortMergePasses;
	}

	public void setSortMergePasses(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Sort_merge_passes")){
							this.sortMergePasses = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getOpenFiles() {
		return openFiles;
	}

	public void setOpenFiles(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Open_files")){
							this.openFiles = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getOpenFilesLimit() {
		return openFilesLimit;
	}

	public void setOpenFilesLimit(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("open_files_limit")){
							this.openFilesLimit = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Double getOpenFilesRate() {
		return openFilesRate;
	}

	public void setOpenFilesRate(Double openFilesRate) {
//		Open_files / open_files_limit
		if(openFilesLimit!=0){
			this.openFilesRate = (double)(openFiles/openFilesLimit);
		}else{
			this.openFilesRate = 0.0;
		}
		
	}

	public Integer getBinlogCacheDiskUse() {
		return binlogCacheDiskUse;
	}

	public void setBinlogCacheDiskUse(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Binlog_cache_disk_use")){
							this.binlogCacheDiskUse = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getBinlogCacheUse() {
		return binlogCacheUse;
	}

	public void setBinlogCacheUse(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Binlog_cache_use")){
							this.binlogCacheUse = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getBinlogStmtCacheDiskUse() {
		return binlogStmtCacheDiskUse;
	}

	public void setBinlogStmtCacheDiskUse(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Binlog_stmt_cache_disk_use")){
							this.binlogStmtCacheDiskUse = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

	public Integer getBinlogStmtCacheUse() {
		return binlogStmtCacheUse;
	}

	public void setBinlogStmtCacheUse(List<SqlCommandResult> sqlResultList) {
//		遍历结果集
		if(sqlResultList!=null &&sqlResultList.size()>0){
			for (SqlCommandResult sqlCommandResult : sqlResultList) {
				if(sqlCommandResult!=null){
					String variableName = sqlCommandResult.getVariableName();
					String value = sqlCommandResult.getValue();
					
					if(StringUtils.isNotBlank(variableName)){
						if(variableName.equals("Binlog_stmt_cache_use")){
							this.binlogStmtCacheUse = Integer.parseInt(value);
							sqlResultList.remove(sqlCommandResult);
							break;
						}
					}
				}
			}
		}
	}

}
