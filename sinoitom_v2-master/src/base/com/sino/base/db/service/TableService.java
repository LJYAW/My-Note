package com.sino.base.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.db.dao.QueryDB;
import com.sino.base.db.entity.DBEntity;
import com.sino.base.db.entity.TableInfo;

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class TableService {
	private static String objAttNames = "column_name,columu_type,column_key,column_default,is_nullable,column_comment";
	private static String jsonAttNames = "column_name,columu_type,column_key,column_default,is_nullable,column_comment";
	@Autowired
	private QueryDB queryDB;
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<TableInfo> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	public List<TableInfo> queryTable(String dbName,String tableName){
		return queryDB.queryTable(dbName,tableName);
	}
}
