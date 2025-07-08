package com.sino.bpm.service;

import com.sino.ack.devCheckItem.entity.AckDevCheckItems;
import com.sino.base.common.ResponseResult;
import com.sino.base.common.util.JsonUtils;
import com.sino.bpm.dao.CmdbResIndCmdParseDao;
import com.sino.bpm.dao.CmdbResTypeIndicatorsDao;
import com.sino.bpm.entity.BpmResIndCmdParse;
import com.sino.bpm.entity.BpmTaskMonRes;
import com.sino.bpm.entity.CmdbResIndCmdParse;
import com.sino.bpm.entity.CmdbResTypeIndicators;
import com.sino.cmdb.indicator.items.entity.IndicatorFields;
import com.sino.cmdb.indicator.items.service.IndicatorFieldsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName CmdbResIndCmdParseService
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/18 14:30
 * @Version 1.0
 **/
@Service
@Transactional
public class CmdbResIndCmdParseService {
    private static Logger logger= LoggerFactory.getLogger(CmdbResIndCmdParseService.class);

    @Autowired
    private CmdbResIndCmdParseDao cmdbResIndCmdParseDao;

    private static String objAttNames ="chkItemId,vendorID,vendorID@CmdbVendor@vendorID@vendorName,resClassCode,resClassName,resTypeCode,resTypeName,prodModelID,prodModel,modelOID,osType,osVersion,osFeature,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemEnName,indItemName,prodChkCmdId,checkCmd,resultSample,resultKeyWords,parseMode,regEx,regExGroupNum,startKeyWords,endKeyWords,delimiter,valueIndex,fileDelimiter,kvDelimiter,lineDelimiter,blankLineDelimiter,valueType,length,decimals,measureUnit,remark,status";
    private static String jsonAttNames="id,vendorID,vendorName,resClassCode,resClassName,resTypeCode,resTypeName,prodModelID,prodModel,modelOID,osType,osVersion,osFeature,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemEnName,indItemName,prodChkCmdId,checkCmd,resultSample,resultKeyWords,parseMode,regEx,regExGroupNum,startKeyWords,endKeyWords,delimiter,valueIndex,fileDelimiter,kvDelimiter,lineDelimiter,blankLineDelimiter,valueType,length,decimals,measureUnit,remark,status";

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<CmdbResIndCmdParse> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    @Transactional(readOnly = true)
    public String getJsonObjStr(final CmdbResIndCmdParse obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    /*
    * @author fengyao
    * @Description
    * @Date 16:15 2020/5/18
    * @Param [resTypeIndIdList, bpmTaskMonRes]
    * return java.util.List<com.sino.bpm.entity.CmdbResIndCmdParse>
    **/
    public List<CmdbResIndCmdParse> getCmdInds(List<Integer> resTypeIndIdList, BpmTaskMonRes bpmTaskMonRes) {
        List<CmdbResIndCmdParse> list = cmdbResIndCmdParseDao.getCmdInds(resTypeIndIdList,bpmTaskMonRes);
        return list;
    }

    
    /*/**
     * @auther: Mr.Lp
     * @desc: 以下
     * @data: 2020/7/1
     * @param: 
     * @return: 
     */
    @Autowired
    private IndicatorFieldsService indicatorFieldsService;

    //	保存入数据库
    public void save(CmdbResIndCmdParse item) {
        cmdbResIndCmdParseDao.save(item);
    }

    //	获取所有
    public List<CmdbResIndCmdParse> getAll() {
        List<CmdbResIndCmdParse> list = cmdbResIndCmdParseDao.getAll();
        return list;
    }

    public void batchSave(List<CmdbResIndCmdParse> list) {
        cmdbResIndCmdParseDao.batchSave(list, 10);
    }

    public CmdbResIndCmdParse getCmdbProdChkCmdItemsById(Integer id) {
        return cmdbResIndCmdParseDao.get(id);
    }

    public List<CmdbResIndCmdParse> getByVerdorID(int vendorID) {
        String hql = " from CmdbResIndCmdParse where vendorID=?";
        List<CmdbResIndCmdParse> list = cmdbResIndCmdParseDao.find(hql, vendorID);
        return list;
    }

    public List<CmdbResIndCmdParse> getByVerdorIDAndClassCode(int vendorID, int resClassCode) {
        String hql = " from CmdbResIndCmdParse where vendorID=? and resClassCode=?";
        List<CmdbResIndCmdParse> list = cmdbResIndCmdParseDao.find(hql, vendorID, resClassCode);
        return list;
    }

    public List<CmdbResIndCmdParse> getByVerdorIDAndClassCodeAndDevType(int vendorID, int resClassCode, int resTypeCode) {
        String hql = " from CmdbResIndCmdParse where vendorID=? and resClassCode=? and resTypeCode=?";
        List<CmdbResIndCmdParse> list = cmdbResIndCmdParseDao.find(hql, vendorID, resClassCode, resTypeCode);
        return list;
    }

    public void deletes(String[] ids) {
        logger.debug("Enter deletes...");
        for (String id : ids) {
            delete(Integer.parseInt(id));
        }
    }

    public void delete(Integer id) {
        cmdbResIndCmdParseDao.delete(id);
    }

    public String checkResult(CmdbResIndCmdParse items) {
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

    private String regexParse(String resultSample, CmdbResIndCmdParse items) {
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
        return (valueType.equalsIgnoreCase("Float") || valueType.equalsIgnoreCase("Integer") || StringUtils.isNotEmpty(measureUnit)) ?
                value + (" " + measureUnit) :
                value;
    }

    private String strMatchParse(String resultSample, CmdbResIndCmdParse items) {
        long start = System.currentTimeMillis();
        logger.debug("{} - strMatchParse: 开始字符串匹配", this.getClass().getName());
        String startKeyword = items.getStartKeyWords();
        String endKeyWords = items.getEndKeyWords();
        String resultKeyWords = items.getResultKeyWords();
        String measureUnit = items.getMeasureUnit();
        String valueType = items.getValueType();
        String result = null;
        if(resultSample.contains("\n")){
            String[] msg = resultSample.split("\r\n");
            String res = "";
            for (String str : msg) {
                if (str.contains(resultKeyWords)) {
                    res = str.trim();
                    break;
                }
            }
            int begin = res.indexOf(startKeyword) + startKeyword.length();
            result = res.substring(begin).trim();
            if (!isNullString(endKeyWords)) {
                int end = result.indexOf(endKeyWords);
                result = result.substring(0, end).trim();
            }

        }else {
            result = resultSample;
        }
        result = valueUnit(result, valueType, measureUnit);
        long end = System.currentTimeMillis();
        logger.debug("{} - strMatchParse: 字符串匹配解析结束，花费：{}ms!", this.getClass().getName(), end - start);
        return ResponseResult.ok(result, "字符串匹配解析成功").toString();
    }

    private boolean isNullString(String str) {
        return str == null || str.trim().length() < 1 || str.equals("null");
    }

    private String strSpiltParse(String resultSample, CmdbResIndCmdParse items) {
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

    private String templateParse(String sample, CmdbResIndCmdParse items) {
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

}
