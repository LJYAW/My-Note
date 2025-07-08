package com.sino.base.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.db.dao.QueryDB;
import com.sino.base.db.entity.DBEntity;

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class DBService {
	private static String objAttNames = "table_schema,engine,table_name,table_rows,avg_row_length,table_comment";
	private static String jsonAttNames = "table_schema,engine,table_name,table_rows,avg_row_length,table_comment";
	@Autowired
	private QueryDB queryDB;
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<DBEntity> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	public List<DBEntity> queryDB(){
		return queryDB.queryDB();
	}
	
}
