package com.sino.fas.res.db.web;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.base.system.entity.SysUser;
import com.sino.base.system.service.OrganService;
import com.sino.fas.res.db.entity.ResDataBases;
import com.sino.fas.res.db.mysqlPerformance.entity.DetectionIndicator;
import com.sino.fas.res.db.mysqlPerformance.entity.SqlCommandResult;
import com.sino.fas.res.db.mysqlPerformance.utils.ObtainDetectionIndicator;
import com.sino.fas.res.db.service.ResDataBasesService;
import com.sino.fas.res.host.entity.ResHosts;
import com.sino.fas.res.host.service.ResHostsService;
import com.sino.snmp.utils.JdbcConnection;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.utils.Identities;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @author fengyao
 * @date 2019-10-9上午9:26:42
 * @className ResDataBasesAction
 *
 * @Description TODO
 *
 */
@Controller
@RequestMapping(value = "/fas/res/db/index")
public class ResDataBasesAction {
	private static Logger logger = LoggerFactory.getLogger(ResDataBasesAction.class);
	private String viewPath = "/fas/res/db";
	
	@Autowired
	private ResDataBasesService resDataBasesService;
	@Autowired
	private ResHostsService resHostsService;
	@Autowired
	private OrganService organService;
	
//	=======================================================================
	
//	跳转到列表页面
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		logger.info( "Enter ResDataBasesAction main.do ..." );
		List<ResDataBases> list = resDataBasesService.getAll();
		String jsonStr = resDataBasesService.getJsonListStr(list);
		
		map.put("jsonData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	} 
	
//	跳转到添加页面
	@RequestMapping(value = "/toAdd.do")
	public String toAdd(ModelMap map){
		logger.info( "Enter ResDataBasesAction toAdd.do ..." );
		map.put("action", "add");
		return viewPath+"/edit";
	}
	
//	跳转到修改页面
	@RequestMapping(value = "/toEdit.do")
	public String toEdit(Long dbsId,ModelMap map){
		logger.info( "Enter ResDataBasesAction toEdit.do ..." );
		ResDataBases dataBases = resDataBasesService.getById(dbsId);
		map.put("resDB", dataBases);
		map.put("action", "edit");
		return viewPath+"/edit";
	}
	
//	跳转到详情页面
	@RequestMapping(value = "/detail.do")
	public String detail(Long dbsId,ModelMap map){
		logger.info( "Enter ResDataBasesAction detail.do ..." );
		ResDataBases dataBases = resDataBasesService.getById(dbsId);
		String orgId = dataBases.getOrgId();
		if(StringUtils.isNotBlank(orgId)){
			OrgOrganization organ = organService.getOrgan(orgId);
			String orgName = organ.getOrgName();
			map.put("orgName", orgName);
		}
		
		map.put("resDB", dataBases);
		return viewPath+"/detail";
	}
	
	
//	根据orgId获取hostIP列表
	@RequestMapping(value = "/getHostIpByOrgId.do")
	public String getHostIpByOrgId(String orgID,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info( "Enter ResDataBasesAction getHostIpByOrgId.do ..." );
		List<ResHosts> list = resHostsService.getHostsByOrgId(orgID);
		String jsonListStr = resHostsService.getJsonListStr(list);
		
		JSONObject jo = new JSONObject();
		jo.put("jsonListStr", jsonListStr);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	
//	保存
	@RequestMapping(value="/save.do")
	public String save(String action,ModelMap map,ResDataBases resDataBases){
		logger.info( "Enter ResDataBasesAction save.do ..." );
//		当前登陆人
		SysUser curUser = SystemUtils.getLoginUser();
		String creator = curUser.getLoginName();
//		当前时间
		Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		if(action.equals("add")){
//			获取id
			resDataBases.setDbsId(new JdbcConnection().getDeviceResourceUniqId());
			
			resDataBases.setCreator(creator);
			resDataBases.setCreateTime(now);
			resDataBases.setAuditStatus(0);//未审核
			Integer verifyStatus = resDataBases.getVerifyStatus();
			if(verifyStatus==null){
				resDataBases.setVerifyStatus(0);//未验证
			}
			
//			保存
			resDataBasesService.save(resDataBases);
		}else{
			ResDataBases dataBases = resDataBasesService.getById(resDataBases.getDbsId());
			String[] arr = {"creator","createTime"};
			BeanUtils.copyProperties(resDataBases, dataBases,arr);
			dataBases.setModifyTime(now);
			dataBases.setModifier(creator);
			resDataBasesService.save(resDataBases);
		}
		map.put("result", "success");
		return viewPath+"/edit";
	}
	
//	删除
	@RequestMapping(value = "/delete.do")
	public String delete(Long id, HttpServletResponse response) throws IOException {
		logger.info( "Enter ResDataBasesAction delete.do ..." );
		
		resDataBasesService.delete(id);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
	
//	访问验证
	@RequestMapping(value = "/accessVerify.do")
	public String accessVerify(Long id,ResDataBases dataBases, HttpServletResponse response) throws IOException  {
		logger.info( "Enter ResDataBasesAction accessVerify.do ..." );

		String result = "";
		
		if(id!=null){
			dataBases = resDataBasesService.getById(id);
		}
			
		Connection conn = null; // 表示数据库的连接的对象
		String dbVersion = "";
		try {
			if(dataBases!=null){
				String jdbcDriver = dataBases.getJdbcDriver();
				String jdbcUrl = dataBases.getJdbcUrl();
				String userName = dataBases.getUserName();
				String password = dataBases.getPassword();
				
				// 1、使用Class类加载驱动程序
				Class.forName(jdbcDriver);
				// 2、连接数据库
				conn = DriverManager.getConnection(jdbcUrl, userName, password);
				// 3、Statement接口需要通过Connection接口进行实例化操作
				if(conn!=null){
//					获取版本信息
					dbVersion = getVersion(conn);
					result = "success";
					if(id!=null){
						dataBases.setVerifyStatus(1);
						resDataBasesService.save(dataBases);
					}
				}
			}
		} catch (Exception e) {
			result = "fail";
			System.out.println(result);
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		jo.put("dbVersion", dbVersion);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;    
	}
	
	private String getVersion(Connection conn) {
		PreparedStatement pst = null;  
		ResultSet rs = null;
		String command="select version()";
		String versionName = "";
		try {
	//		准备执行语句
			pst = conn.prepareStatement(command);
			
	//		执行sql查询语句，得到结果集  
			rs = pst.executeQuery();
	        while (rs.next()) {  
	        	versionName = rs.getString(1);  
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return versionName;
	}

//	审核
	@RequestMapping(value="/audit.do")
	public String audit(Long id, HttpServletResponse response) throws IOException{
		logger.info( "Enter ResDataBasesAction audit.do ..." );
		
		ResDataBases resDataBases = resDataBasesService.getById(id);
		resDataBases.setAuditStatus(1);
		resDataBasesService.save(resDataBases);
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	    return null;         
	}
//	跳转到获取检测指标页面
	@RequestMapping(value = "/getDetectionIndicator.do")
	public String getDetectionIndicator(Long dbsId,ModelMap map){
		logger.info( "Enter ResDataBasesAction getDetectionIndicator.do ..." );
		ResDataBases dataBases = resDataBasesService.getById(dbsId);
		DetectionIndicator detectionIndicator = ObtainDetectionIndicator.getDetectionIndicator(dataBases);
		map.put("indicator", detectionIndicator);
		return viewPath+"/detectionIndicator";
	}
//	跳转到运行状况页面
	@RequestMapping(value = "/toOperaStatus.do")
	public String toOperaStatus(Long dbsId,ModelMap map){
		logger.info( "Enter ResDataBasesAction toOperaStatus.do ..." );
		map.put("dbsId", dbsId);
		return viewPath+"/mainTab";
	}
	
//	跳转到配置参数页面
	@RequestMapping(value="toGetConfigParam.do")
	public String toGetConfigParam(Long dbsId,ModelMap map){
		logger.info( "Enter ResDataBasesAction toGetConfigParam.do ..." );
		map.put("dbsId", dbsId);
		return viewPath+"/getConfigParam";
	}
	
//	获取配置参数数据
	@RequestMapping(value="getConfigParamData.do")
	@ResponseBody
	public String getConfigParamData(Long dbsId){
		logger.info( "Enter ResDataBasesAction getConfigParamData.do ..." );
		ResDataBases dataBases = resDataBasesService.getById(dbsId);
		String command = "show variables;";
		
		List<SqlCommandResult> resultList = getResultList(dataBases,command);
		String jsonStr = resDataBasesService.getSqlCommandResultJsonListStr(resultList);
		
		return jsonStr;
	}
	
	
//	跳转到运行状态页面
	@RequestMapping(value="toGetOperaStatus.do")
	public String toGetOperaStatus(Long dbsId,ModelMap map){
		logger.info( "Enter ResDataBasesAction toGetOperaStatus.do ..." );
		map.put("dbsId", dbsId);
		return viewPath+"/getOperaStatus";
	}
	
//	获取配置参数数据
	@RequestMapping(value="getOperaStatusData.do")
	@ResponseBody
	public String getOperaStatusData(Long dbsId){
		logger.info( "Enter ResDataBasesAction getOperaStatusData.do ..." );
		ResDataBases dataBases = resDataBasesService.getById(dbsId);
		String command = "show STATUS;";
		
		List<SqlCommandResult> resultList = getResultList(dataBases,command);
		String jsonStr = resDataBasesService.getSqlCommandResultJsonListStr(resultList);
		
		return jsonStr;
	}
	
//	跳转到性能指标页面
	@RequestMapping(value="toGetPerformance.do")
	public String toGetPerformance(Long dbsId,ModelMap map){
		logger.info( "Enter ResDataBasesAction toGetPerformance.do ..." );
		map.put("dbsId", dbsId);
		return viewPath+"/getPerformance";
	}
	
//	获取性能指标数据
	@RequestMapping(value="getPerformanceData.do")
	@ResponseBody
	public String getPerformanceData(Long dbsId){
		logger.info( "Enter ResDataBasesAction getPerformanceData.do ..." );
		ResDataBases dataBases = resDataBasesService.getById(dbsId);
		
		List<SqlCommandResult> resultList = this.getPerResultList(dataBases);
		String jsonStr = resDataBasesService.getSqlCommandResultJsonListStr(resultList);
		
		return jsonStr;
	}

//	计算性能指标
	private List<SqlCommandResult> getPerResultList(ResDataBases dataBases) {
		String command2 = "show STATUS;";
		List<SqlCommandResult> operStatusReList = this.getResultList(dataBases, command2);
		
		List<SqlCommandResult> perforReList = getPerReList(operStatusReList);
		return perforReList;
	}
	
//	获取QpsAndTps
	private List<SqlCommandResult> getPerReList(List<SqlCommandResult> operStatusReList){
		List<SqlCommandResult> perforReList = new ArrayList<SqlCommandResult>();

		Long Com_select = 0L;
		Long Com_insert = 0L;
		Long Com_update = 0L;
		Long Com_delete = 0L;
		Long qps = 0L;
		Long tps = 0L;
		
		Long Innodb_buffer_pool_reads = 0L;
		Long Innodb_buffer_pool_read_requests = 0L;
		Long Innodb_buffer_pool_pages_free = 0L;
		Long Innodb_buffer_pool_pages_total = 0L;
		BigDecimal innodb_buffer_read_hit_ratio = new BigDecimal(0);
		BigDecimal Innodb_buffer_usage = new BigDecimal(0);
		
		Long Key_reads = 0L;
		Long Key_read_requests = 0L;
		Long Key_writes = 0L;
		Long Key_write_requests = 0L;
		BigDecimal Key_read_hit_ratio = new BigDecimal(0);
		BigDecimal Key_write_hit_ratio = new BigDecimal(0);
		
		Long Created_tmp_disk_tables = 0L;
		Long Created_tmp_tables = 0L;
		BigDecimal Created_tmp_tables_ratio = new BigDecimal(0);
		
		if(operStatusReList!=null && operStatusReList.size()>0){
			for (SqlCommandResult operStatusRe : operStatusReList) {
				String variableName = operStatusRe.getVariableName();
				String value = operStatusRe.getValue();
				if(StringUtils.isNotBlank(variableName) && StringUtils.isNotBlank(value)){
					if("Com_select".equalsIgnoreCase(variableName)){
						Com_select = Long.parseLong(value);
					}
					if("Com_insert".equalsIgnoreCase(variableName)){
						Com_insert = Long.parseLong(value);
					}
					if("Com_update".equalsIgnoreCase(variableName)){
						Com_update = Long.parseLong(value);
					}
					if("Com_delete".equalsIgnoreCase(variableName)){
						Com_delete = Long.parseLong(value);
					}
					if("Innodb_buffer_pool_reads".equalsIgnoreCase(variableName)){
						Innodb_buffer_pool_reads = Long.parseLong(value);
					}
					if("Innodb_buffer_pool_read_requests".equalsIgnoreCase(variableName)){
						Innodb_buffer_pool_read_requests = Long.parseLong(value);
					}
					if("Innodb_buffer_pool_pages_free".equalsIgnoreCase(variableName)){
						Innodb_buffer_pool_pages_free = Long.parseLong(value);
					}
					if("Innodb_buffer_pool_pages_total".equalsIgnoreCase(variableName)){
						Innodb_buffer_pool_pages_total = Long.parseLong(value);
					}
					if("Key_reads".equalsIgnoreCase(variableName)){
						Key_reads = Long.parseLong(value);
					}
					if("Key_read_requests".equalsIgnoreCase(variableName)){
						Key_read_requests = Long.parseLong(value);
					}
					if("Key_writes".equalsIgnoreCase(variableName)){
						Key_writes = Long.parseLong(value);
					}
					if("Key_write_requests".equalsIgnoreCase(variableName)){
						Key_write_requests = Long.parseLong(value);
					}
					if("Created_tmp_disk_tables".equalsIgnoreCase(variableName)){
						Created_tmp_disk_tables = Long.parseLong(value);
					}
					if("Created_tmp_tables".equalsIgnoreCase(variableName)){
						Created_tmp_tables = Long.parseLong(value);
					}
				}
			}
		}
		
//		qps 每秒处理的查询数 qps=Com_select/s + Com_insert/s + Com_update/s + Com_delete/s
//		tps 每秒处理的事务数 tps= Com_insert/s + Com_update/s + Com_delete/s
//		IOPS 每秒磁盘进行的I/O操作次数
		
		qps=Com_select + Com_insert + Com_update + Com_delete;
		tps= Com_insert + Com_update + Com_delete;
		
		SqlCommandResult qpsCommandResult = new SqlCommandResult();
		qpsCommandResult.setIndicatorId(Identities.uuid());
		qpsCommandResult.setVariableName("qps");
		qpsCommandResult.setValue(qps+"");
		qpsCommandResult.setRemarks("每秒处理的查询数");
		perforReList.add(qpsCommandResult);
		
		SqlCommandResult tpsCommandResult = new SqlCommandResult();
		tpsCommandResult.setIndicatorId(Identities.uuid());
		tpsCommandResult.setVariableName("tps");
		tpsCommandResult.setValue(tps+"");
		tpsCommandResult.setRemarks("每秒处理的事务数");
		perforReList.add(tpsCommandResult);
		
//		innodb缓冲池的读命中率
//		innodb_buffer_read_hit_ratio = ( 1 - Innodb_buffer_pool_reads/Innodb_buffer_pool_read_requests) * 100
		
//		Innodb缓冲池的利用率
//		Innodb_buffer_usage =  ( 1 - Innodb_buffer_pool_pages_free / Innodb_buffer_pool_pages_total) * 100
		
		if(Innodb_buffer_pool_read_requests!=null && Innodb_buffer_pool_read_requests!=0L){
			innodb_buffer_read_hit_ratio = 
			new BigDecimal(1).subtract( new BigDecimal(Innodb_buffer_pool_reads).divide(new BigDecimal(Innodb_buffer_pool_read_requests),2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		}
		if(Innodb_buffer_pool_pages_total!=null && Innodb_buffer_pool_pages_total!=0L){
			Innodb_buffer_usage = 
			new BigDecimal(1).subtract( new BigDecimal(Innodb_buffer_pool_pages_free).divide(new BigDecimal(Innodb_buffer_pool_pages_total),2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		}
		
		SqlCommandResult innodb_buffer_read_hit_ratio_SCR = new SqlCommandResult();
		innodb_buffer_read_hit_ratio_SCR.setIndicatorId(Identities.uuid());
		innodb_buffer_read_hit_ratio_SCR.setVariableName("innodb_buffer_read_hit_ratio");
		innodb_buffer_read_hit_ratio_SCR.setValue(innodb_buffer_read_hit_ratio+"%");
		innodb_buffer_read_hit_ratio_SCR.setRemarks("innodb缓冲池的读命中率");
		perforReList.add(innodb_buffer_read_hit_ratio_SCR);
		
		SqlCommandResult Innodb_buffer_usage_SCR = new SqlCommandResult();
		Innodb_buffer_usage_SCR.setIndicatorId(Identities.uuid());
		Innodb_buffer_usage_SCR.setVariableName("Innodb_buffer_usage");
		Innodb_buffer_usage_SCR.setValue(Innodb_buffer_usage+"%");
		Innodb_buffer_usage_SCR.setRemarks("Innodb缓冲池的利用率");
		perforReList.add(Innodb_buffer_usage_SCR);

//		MyISAM平均每秒key buffer读命中率
//		Key_read_hit_ratio=(1-Key_reads/Key_read_requests)*100

//		MyISAM平均每秒key buffer写命中率
//		Key_write_hit_ratio =(1-Key_writes/Key_write_requests)*100
		if(Key_read_requests!=null && Key_read_requests!=0L){
			Key_read_hit_ratio = 
			new BigDecimal(1).subtract( new BigDecimal(Key_reads).divide(new BigDecimal(Key_read_requests),2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		}
		if(Key_write_requests!=null && Key_write_requests!=0L){
			Key_write_hit_ratio = 
			new BigDecimal(1).subtract( new BigDecimal(Key_writes).divide(new BigDecimal(Key_write_requests),2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
		}
		
		
		SqlCommandResult Key_read_hit_ratio_SCR = new SqlCommandResult();
		Key_read_hit_ratio_SCR.setIndicatorId(Identities.uuid());
		Key_read_hit_ratio_SCR.setVariableName("Key_read_hit_ratio");
		Key_read_hit_ratio_SCR.setValue(Key_read_hit_ratio+"%");
		Key_read_hit_ratio_SCR.setRemarks("MyISAM平均每秒key buffer读命中率");
		perforReList.add(Key_read_hit_ratio_SCR);
		
		SqlCommandResult Key_write_hit_ratio_SCR = new SqlCommandResult();
		Key_write_hit_ratio_SCR.setIndicatorId(Identities.uuid());
		Key_write_hit_ratio_SCR.setVariableName("Key_write_hit_ratio");
		Key_write_hit_ratio_SCR.setValue(Key_write_hit_ratio+"%");
		Key_write_hit_ratio_SCR.setRemarks("MyISAM平均每秒key buffer写命中率");
		perforReList.add(Key_write_hit_ratio_SCR);
		
//		Created_tmp_disk_tables/Created_tmp_tables比值最好不要超过10%，如果Created_tmp_tables值比较大，可能是排序句子过多或者连接句子不够优化
		if(Created_tmp_tables!=null && Created_tmp_tables!=0L){
			Created_tmp_tables_ratio = 
			new BigDecimal(Created_tmp_disk_tables).divide(new BigDecimal(Created_tmp_tables),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
		}
		
		SqlCommandResult Created_tmp_tables_ratio_SCR = new SqlCommandResult();
		Created_tmp_tables_ratio_SCR.setIndicatorId(Identities.uuid());
		Created_tmp_tables_ratio_SCR.setVariableName("Created_tmp_tables_ratio");
		Created_tmp_tables_ratio_SCR.setValue(Created_tmp_tables_ratio+"%");
		Created_tmp_tables_ratio_SCR.setRemarks("比值最好不要超过10%，如果Created_tmp_tables值比较大，可能是排序句子过多或者连接句子不够优化");
		perforReList.add(Created_tmp_tables_ratio_SCR);
		
		return perforReList;
	}

	//	执行SQL语句获取命令行执行结果列表
	private List<SqlCommandResult> getResultList(ResDataBases dataBases,String command){
		List<SqlCommandResult> list = new ArrayList<SqlCommandResult>();
		
		Connection conn = null; // 表示数据库的连接的对象
		PreparedStatement pst = null;  
		ResultSet rs = null;
		
		try {
			if(dataBases!=null){
				String jdbcDriver = dataBases.getJdbcDriver();
				String jdbcUrl = dataBases.getJdbcUrl();
				String userName = dataBases.getUserName();
				String password = dataBases.getPassword();
				
				// 1、使用Class类加载驱动程序
				Class.forName(jdbcDriver);
				// 2、连接数据库
				conn = DriverManager.getConnection(jdbcUrl, userName, password);
				// 3、Statement接口需要通过Connection接口进行实例化操作
				if(conn!=null){
		  //		准备执行语句
					pst = conn.prepareStatement(command);
			//		执行sql查询语句，得到结果集  
					rs = pst.executeQuery();
					while (rs.next()) {  
					    String variable = rs.getString(1);  
					    String value = rs.getString(2);
					    if(StringUtils.isNotBlank(variable)){
					    	SqlCommandResult result = new SqlCommandResult();
					    	result.setVariableName(variable);
					    	result.setValue(value);
					    	result.setIndicatorId(Identities.uuid());
					    	list.add(result);
					    }
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return list;
	}
	
}
