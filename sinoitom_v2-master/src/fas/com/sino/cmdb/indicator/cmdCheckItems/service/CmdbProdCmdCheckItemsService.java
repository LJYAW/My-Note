package com.sino.cmdb.indicator.cmdCheckItems.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sino.base.common.ResponseResult;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.cmdCheckItems.dao.CmdbProdCmdCheckItemsDao;
import com.sino.cmdb.indicator.cmdCheckItems.entity.CmdbProdCmdChkItems;
import com.sino.cmdb.indicator.cmdCheckItems.web.CmdbProdCmdCheckItemsAction;
import com.sino.cmdb.indicator.items.entity.IndicatorFields;
import com.sino.cmdb.indicator.items.service.IndicatorFieldsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CmdbProdCmdCheckItemsService {

	private static Logger logger = LoggerFactory.getLogger(CmdbProdCmdCheckItemsService.class);

	private static String objAttNames = "chkItemId,vendorId,vendorId@CmdbVendor@vendorID@dispName,devClassCode,devClassName,devTypeCode,devTypeName,prodModelId,prodModel,modelOID,osType,osVersion,osFeature,indClassCode,indClassName,indGroupId,indGroupName,indItemID,indItemEnName,indItemName,prodChkCmdId,checkCmd,resultSample,resultKeyWords,parseMode,regEx,startKeyWords,endKeyWords,delimiter,valueIndex,valueType,length,decimals,measureUnit,remark,status,regExGroupNum,fileDelimiter,kvDelimiter,lineDelimiter,blankLineDelimiter";
	private static String jsonAttNames = "id,vendorId,vendorName,devClassCode,devClassName,devTypeCode,devTypeName,prodModelId,prodModel,modelOID,osType,osVersion,osFeature,indClassCode,indClassName,indGroupId,indGroupName,indItemID,indItemEnName,indItemName,prodChkCmdId,checkCmd,resultSample,resultKeyWords,parseMode,regEx,startKeyWords,endKeyWords,delimiter,valueIndex,valueType,length,decimals,measureUnit,remark,status,regExGroupNum,fileDelimiter,kvDelimiter,lineDelimiter,blankLineDelimiter";

	@Transactional(readOnly = true)
	public String getJsonListStr(final List<CmdbProdCmdChkItems> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}

	@Transactional(readOnly = true)
	public String getJsonObjStr(final CmdbProdCmdChkItems obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}

	@Autowired
	private CmdbProdCmdCheckItemsDao cmdbProdCmdCheckItemsDao;

	@Autowired
	private IndicatorFieldsService indicatorFieldsService;

	//	保存入数据库
	public void save(CmdbProdCmdChkItems item) {
		cmdbProdCmdCheckItemsDao.save(item);
	}

	//	获取所有
	public List<CmdbProdCmdChkItems> getAll() {
		List<CmdbProdCmdChkItems> list = cmdbProdCmdCheckItemsDao.getAll();
		return list;
	}

	public void batchSave(List<CmdbProdCmdChkItems> list) {
		cmdbProdCmdCheckItemsDao.batchSave(list, 10);
	}

	public CmdbProdCmdChkItems getCmdbProdChkCmdItemsById(Integer id) {
		return cmdbProdCmdCheckItemsDao.get(id);
	}

	public List<CmdbProdCmdChkItems> getByVerdorID(int vendorId) {
		String hql = " from CmdbProdCmdChkItems where vendorId=?";
		List<CmdbProdCmdChkItems> list = cmdbProdCmdCheckItemsDao.find(hql, vendorId);
		return list;
	}

	public List<CmdbProdCmdChkItems> getByVerdorIDAndClassCode(int vendorId, int devClassCode) {
		String hql = " from CmdbProdCmdChkItems where vendorId=? and devClassCode=?";
		List<CmdbProdCmdChkItems> list = cmdbProdCmdCheckItemsDao.find(hql, vendorId, devClassCode);
		return list;
	}

	public List<CmdbProdCmdChkItems> getByVerdorIDAndClassCodeAndDevType(int vendorId, int devClassCode, int devType) {
		String hql = " from CmdbProdCmdChkItems where vendorId=? and devClassCode=? and devTypeCode=?";
		List<CmdbProdCmdChkItems> list = cmdbProdCmdCheckItemsDao.find(hql, vendorId, devClassCode, devType);
		return list;
	}

	public void deletes(String[] ids) {
		logger.debug("Enter deletes...");
		for (String id : ids) {
			delete(Integer.parseInt(id));
		}
	}

	public void delete(Integer id) {
		cmdbProdCmdCheckItemsDao.delete(id);
	}

	public String checkResult(CmdbProdCmdChkItems items) {
		String resultSample = items.getResultSample();
		int parseMode = items.getParseMode();
		String result = "";
		switch (parseMode) {
			case 1:
				result = regexParse(resultSample, items);
				break;
			case 2:
				result = strMatchParse(resultSample, items);
				break;
			case 3:
				result = strSpiltParse(resultSample, items);
				break;
			case 4:
				result = this.templateParse(resultSample, items);
				break;
			default:
				break;
		}
		return result;
	}

	private String regexParse(String resultSample, CmdbProdCmdChkItems items) {
		String regEx = items.getRegEx();
		String regExGroupNum = items.getRegExGroupNum();
		String measureUnit = items.getMeasureUnit();
		String valueType = items.getValueType();
		logger.debug("{} - regexParse: 开始正则解析", this.getClass().getName());
		logger.debug("{} - regexParse: 正则解析规则：{}", this.getClass().getName(), regEx);
		long start = System.currentTimeMillis();
		if (StringUtils.isEmpty(regExGroupNum)) {
			regExGroupNum = "1";
		}
		if (StringUtils.isEmpty(regEx)) {
			return ResponseResult.error("正则表达式不能为空！").toString();
		}
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(resultSample);
		HashMap<String, HashMap<String, String>> regexResultMap = new HashMap<String, HashMap<String, String>>();
		int count = 0;
		while (matcher.find()) {
			count++;
			HashMap<String, String> map = new LinkedHashMap<String, String>();
			String[] num = new String[]{regExGroupNum};
			if (regExGroupNum.contains(",")) {
				num = regExGroupNum.split(",");
			}
			for (String s : num) {
				String value = matcher.group(Integer.parseInt(s));
				String unitValue = valueUnit(value, valueType, measureUnit);
				logger.debug(this.getClass().getName() + "- regexParse: 正则匹配到的结果：group" + s + ":" + unitValue);
				map.put(s, unitValue);
			}
			regexResultMap.put(String.valueOf(count), map);
		}
		long end = System.currentTimeMillis();
		logger.debug("{} - regexParse: 正则解析结束，花费：{}ms!", this.getClass().getName(), end - start);
		String result = "";
		try {
			result = regexResultMap.get("1").get("1");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.error("正则解析失败").toString();
		}
		return ResponseResult.ok(result, "正则解析成功").toString();
	}

	private String valueUnit(String value, String valueType, String measureUnit) {
		return (valueType.equalsIgnoreCase("Float") || valueType.equalsIgnoreCase("Integer")) ?
				value + (" " + measureUnit) :
				value;
	}

	private String strMatchParse(String resultSample, CmdbProdCmdChkItems items) {
		long start = System.currentTimeMillis();
		logger.debug("{} - strMatchParse: 开始字符串匹配", this.getClass().getName());
		String startKeyword = items.getStartKeyWords();
		String endKeyWords = items.getEndKeyWords();
		String resultKeyWords = items.getResultKeyWords();
		String measureUnit = items.getMeasureUnit();
		String valueType = items.getValueType();
		String[] msg = resultSample.split("\r\n");
		String res = "";
		for (String str : msg) {
			if (str.contains(resultKeyWords)) {
				res = str.trim();
				break;
			}
		}
		int begin = res.indexOf(startKeyword) + startKeyword.length();
		String result = res.substring(begin).trim();
		if (!isNullString(endKeyWords)) {
			int end = result.indexOf(endKeyWords);
			result = result.substring(0, end).trim();
		}
		result = valueUnit(result, valueType, measureUnit);
		long end = System.currentTimeMillis();
		logger.debug("{} - strMatchParse: 字符串匹配解析结束，花费：{}ms!", this.getClass().getName(), end - start);
		return ResponseResult.ok(result, "字符串匹配解析成功").toString();
	}

	private boolean isNullString(String str) {
		return str == null || str.trim().length() < 1 || str.equals("null");
	}

	private String strSpiltParse(String resultSample, CmdbProdCmdChkItems items) {
		long start = System.currentTimeMillis();
		logger.debug("{} - strSpiltParse: 开始字符串分隔解析", this.getClass().getName());
		String resultKeyWords = items.getResultKeyWords();
		String measureUnit = items.getMeasureUnit();
		String valueType = items.getValueType();
		String delimiter = items.getDelimiter();
		int valueIndex = items.getValueIndex();
		String[] msg = resultSample.split("\r\n");
		String res = "";
		for (String str : msg) {
			if (str.contains(resultKeyWords)) {
				res = str.trim();
				break;
			}
		}
		String[] results = res.split(delimiter);
		String result = results[valueIndex].trim();
		result = valueUnit(result, valueType, measureUnit);
		long end = System.currentTimeMillis();
		logger.debug("{} - strSpiltParse: 字符串分隔解析结束，花费：{}ms!", this.getClass().getName(), end - start);
		return ResponseResult.ok(result, "字符串分隔解析成功").toString();
	}

	private String templateParse(String sample, CmdbProdCmdChkItems items) {
		long start = System.currentTimeMillis();
		logger.debug("{} - templateParse: 模版解析处理中...", this.getClass().getName());
		String fileDelimiter = items.getFileDelimiter();
		String kvDelimiter = items.getKvDelimiter();
		String lineDelimiter = items.getLineDelimiter();
		String blankLineDelimiter = items.getBlankLineDelimiter();
		long indItemId = items.getIndItemID();
		String rSplit = "\\|";  //记录分割符，拆分成不同的记录（record/row）
		String handleSample = "";
		if (indItemId == -1L) {
			return ResponseResult.error("模版解析的指标id不能为空！").toString();
		}
		List<IndicatorFields> fieldList = indicatorFieldsService.getListFieldsByIndItemID(indItemId);
		//预处理
		if (StringUtils.isNotEmpty(blankLineDelimiter)) {
			handleSample = sample.replaceAll(blankLineDelimiter, rSplit).trim();
		}
		if (StringUtils.isNotEmpty(lineDelimiter)) {
			handleSample = handleSample.replaceAll(lineDelimiter, fileDelimiter).trim();
		}

		logger.debug("{} - templateParse: 预处理后的结果为：{}", this.getClass().getName(), handleSample);
		String[] sampleRow = handleSample.split(rSplit);
		String[] fields;
		String[] values;
		HashMap<String, HashMap<String, String>> templateMap = new HashMap<String, HashMap<String, String>>(sampleRow.length);
		for (int row = 0; row < sampleRow.length; row++) { //解析每一行
			fields = sampleRow[row].split(fileDelimiter);  //得到一条记录的每个字段名、字段值
			HashMap<String, String> filedMap = new LinkedHashMap<String, String>(fields.length);
			for (String field : fields) {
				for (IndicatorFields ff : fieldList) {
					if (field.contains(ff.getFieldNameEn())) { //键值对包含某个字段
						values = field.split(kvDelimiter);
						filedMap.put(ff.getFieldNameEn(), values[1]);
						break;
					}
				}
			}
			templateMap.put(String.valueOf(row + 1), filedMap);
		}
		long end = System.currentTimeMillis();
		logger.debug("{} - templateParse: 解析完毕, 花费 {}ms!", this.getClass().getName(), end - start);
		return ResponseResult.ok(templateMap, "模版解析成功").toString();
	}

	public static void main(String[] args) {
		String str = "{\"indGroupName\":\"硬件信息\",\"devClassName\":\"网络资源\",\"devTypeName\":\"路由器\",\"indClassName\":\"设备\",\"indItemEnName\":\"ModuleInfo\",\"prodModel\":\"所有型号\",\"modelOID\":\"X.X\",\"prodChkCmdId\":67,\"status\":0,\"flag\":\"true\",\"vendorId\":9,\"devClassCode\":\"13\",\"devTypeCode\":3,\"prodModelId\":-1,\"osType\":\"IOS\",\"osVersion\":\"15.X\",\"osFeature\":\"universal\",\"indClassCode\":1,\"indGroupId\":4181721550,\"indItemID\":2108,\"indItemName\":\"模块信息\",\"valueType\":\"Array2D\",\"measureUnit\":null,\"length\":3,\"decimals\":0,\"remark\":null,\"checkCmd\":\"show inventory\",\"resultSample\":\"NAME: CISCO3945-CHASSIS, DESCR: CISCO3945-CHASSIS\\r\\nPID: CISCO3945-CHASSIS , VID: V02, SN: FGL191010J5\\r\\n\\r\\nNAME: Cisco Services Performance Engine 250 for Cisco 3900 ISR on Slot 0, DESCR: Cisco Services Performance Engine 250 for Cisco 3900 ISR\\r\\nPID: C3900-SPE250/K9   , VID: V05 , SN: FOC19067V01\\r\\n\\r\\nNAME: Enhanced High Speed WAN Interface Card-1 Port Gigabit Ethernet SFP/Cu on Slot 0 SubSlot 0, DESCR: Enhanced High Speed WAN Interface Card-1 Port Gigabit Ethernet SFP/Cu\\r\\nPID: EHWIC-1GE-SFP-CU  , VID: V01, SN: FOC19069EXA\\r\\n\\r\\nNAME: C3900 AC Power Supply 1, DESCR: C3900 AC Power Supply 1\\r\\nPID: PWR-3900-AC       , VID: V04 , SN: QCS19011EN4\\r\\n\\r\\nNAME: C3900 AC Power Supply 2, DESCR: C3900 AC Power Supply 2\\r\\nPID: PWR-3900-AC       , VID: V04 , SN: QCS19051D8Z\",\"parseMode\":4,\"resultKeyWords\":null,\"blankLineDelimiter\":\"\\\\r\\\\n\\\\r\\\\n\",\"lineDelimiter\":\"\\\\r\\\\n\",\"fileDelimiter\":\",\",\"kvDelimiter\":\":\",\"regEx\":null,\"regExGroupNum\":null,\"startKeyWords\":null,\"endKeyWords\":null,\"delimiter\":null,\"valueIndex\":null}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			CmdbProdCmdChkItems cmdbProdCmdChkItems1 = mapper.readValue(str, CmdbProdCmdChkItems.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		CmdbProdCmdChkItems cmdbProdCmdChkItems = JSON.parseObject(str, CmdbProdCmdChkItems.class);
		System.out.println(cmdbProdCmdChkItems);
	}
	
}
