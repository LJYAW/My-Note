package com.sino.bpm.service;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.bpm.dao.*;
import com.sino.bpm.entity.*;
import com.sino.cmdb.indicator.cmdCheckItems.dao.CmdbProdCmdCheckItemsDao;
import com.sino.cmdb.indicator.cmdCheckItems.entity.CmdbProdCmdChkItems;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.fas.res.net.dao.NcmDevAccessUserDao;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.SSHConnection;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName BpmTaskMonResService
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/13 9:58
 * @Version 1.0
 **/
@Service
@Transactional
public class BpmTaskMonResService {
    private static Logger logger= LoggerFactory.getLogger(BpmTaskMonResService.class);

    @Autowired
    private BpmTaskMonResDao bpmTaskMonResDao;
    @Autowired
    private BpmResMonIndsDao bpmResMonIndsDao;
    @Autowired
    private BpmResIndCmdParseDao bpmResIndCmdParseDao;
    @Autowired
    private BpmResIndSnmpOidsDao bpmResIndSnmpOidsDao;
    @Autowired
    private CmdbResIndCmdParseDao cmdbResIndCmdParseDao;
    @Autowired
    private CmdbResIndSnmpOidDao cmdbResIndSnmpOidDao;
    @Autowired
    private CmdbResTypeIndicatorsDao cmdbResTypeIndicatorsDao;
    @Autowired
    private CmdbProdCmdCheckItemsDao cmdbProdCmdCheckItemsDao;
    @Autowired
    private NcmDevAccessUserDao ncmDevAccessUserDao;

    private static String objAttNames ="taskMonResId,taskId,taskName,orgID,orgName,resId,resIp,resIpLong,resName,resClassCode,resClassName,resTypeCode,resTypeName,vendorID,vendorName,modelOID,prodModel,osClass,osType,osVersion,osRelease,osFeature,resAcsMode,resAcsUserId,indCollMode,snmpCredId,userName,snmpStr,flag";
    private static String jsonAttNames="id,taskId,taskName,orgID,orgName,resId,resIp,resIpLong,resName,resClassCode,resClassName,resTypeCode,resTypeName,vendorID,vendorName,modelOID,prodModel,osClass,osType,osVersion,osRelease,osFeature,resAcsMode,resAcsUserId,indCollMode,snmpCredId,userName,snmpStr,flag";

    @Transactional(readOnly = true)
    public String getJsonListStr(final List<BpmTaskMonRes> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    /*
    * @author fengyao
    * @Description 获取所有
    * @Date 9:53 2020/5/14
    * @Param []
    * return java.util.List<com.sino.bpm.entity.BpmTaskMonRes>
    **/
    public List<BpmTaskMonRes> getAll() {
        logger.info("Enter BpmTaskMonResService.getAll ...");
        List<BpmTaskMonRes> all = bpmTaskMonResDao.getAll();
        return all;
    }

    /*
    * @author fengyao
    * @Description 将关联的指标保存入库
    * @Date 9:54 2020/5/14
    * @Param [ids, indCollMode,parseIds]
    * return void
    **/
    public void resRelateInds(String ids,String indCollMode, String parseIds) {
        logger.info("Enter BpmTaskMonResService.resRelateInds ...");
//        ids--main页面选的BpmTaskMonRes主键集合
//        parseIds--addInds页面选的CmdbResIndSnmpOid或者CmdbResIndCmdParse主键集合
//        indCollMode--cmd/snmp
        String[] idsSplit = ids.split(",");
        String[] parseIdsSplit = parseIds.split(";");

        if(idsSplit!=null && idsSplit.length>0
                && parseIdsSplit!=null && parseIdsSplit.length>0){

//            1、根据ids获取BpmTaskMonRes集合
            List<Integer> taskMonResIdList = new ArrayList<>();
            for(String item:idsSplit){
                taskMonResIdList.add(Integer.parseInt(item));
            }
            List<BpmTaskMonRes> bpmTaskMonResList = bpmTaskMonResDao.get(taskMonResIdList);

//            根据parseIds获取id集合
            List<Integer> parseIdIdList = new ArrayList<>();
            for(String item:parseIdsSplit){
                parseIdIdList.add(Integer.parseInt(item));
            }

//            2、保存数据到Bpm_ResMonInds、Bpm_ResIndCmdParse/Bpm_ResIndSnmpOid
            if(bpmTaskMonResList!=null && bpmTaskMonResList.size()>0
                    && parseIdIdList!=null && parseIdIdList.size()>0){
                if(indCollMode.equals("cmd")){
                    saveCmdData(bpmTaskMonResList,parseIdIdList);
                }

                if(indCollMode.equals("snmp")){
                    saveSnmpData(bpmTaskMonResList,parseIdIdList);
                }
            }
        }
    }

    private void saveSnmpData(List<BpmTaskMonRes> bpmTaskMonResList, List<Integer> parseIdIdList) {
        List<BpmResMonInds> bpmResMonIndsList = new ArrayList<>();
        List<BpmResIndSnmpOids> bpmResIndSnmpOidsList = new ArrayList<>();

        List<CmdbResIndSnmpOid> cmdbResIndSnmpOids = cmdbResIndSnmpOidDao.get(parseIdIdList);
//        理论上，页面列的列表cmdb里面一个指标有多个解析方式，但是选择的时候一个指标只选择一种解析方式，
//        那么选的指标和方式都是不重复的
        if(cmdbResIndSnmpOids!=null && cmdbResIndSnmpOids.size()>0){
            for(BpmTaskMonRes bpmTaskMonRes : bpmTaskMonResList) {
                for (CmdbResIndSnmpOid cmdbResIndSnmpOid : cmdbResIndSnmpOids) {
                    if (bpmTaskMonRes != null && cmdbResIndSnmpOid != null) {
//                        组装指标
                        BpmResMonInds bpmResMonInds = new BpmResMonInds();

                        CmdbResTypeIndicators cmdbResTypeIndicators = cmdbResTypeIndicatorsDao.get(cmdbResIndSnmpOid.getResTypeIndId());
                        BeanUtils.copyProperties(cmdbResTypeIndicators,bpmResMonInds);

                        bpmResMonInds.setTaskId(bpmTaskMonRes.getTaskId());
                        bpmResMonInds.setTaskName(bpmTaskMonRes.getTaskName());
                        bpmResMonInds.setResId(bpmTaskMonRes.getResId());
                        bpmResMonInds.setResIp(bpmTaskMonRes.getResIp());
                        bpmResMonInds.setResIpLong(bpmTaskMonRes.getResIpLong());
                        bpmResMonInds.setIndCollMode(bpmTaskMonRes.getIndCollMode());
                        bpmResMonInds.setIndItemId(cmdbResTypeIndicators.getIndItemID());
                        bpmResMonInds.setIndItemEnName(cmdbResTypeIndicators.getIndicatorItem());
                        bpmResMonInds.setFlag(0);//默认状态“未验证”
                        bpmResMonInds.setIndCollMode("snmp");

                        bpmResMonIndsList.add(bpmResMonInds);

//                        组装解析规则
                        BpmResIndSnmpOids bpmResIndSnmpOids = new BpmResIndSnmpOids();

                        BeanUtils.copyProperties(cmdbResIndSnmpOid,bpmResIndSnmpOids);

                        bpmResIndSnmpOids.setResId(bpmTaskMonRes.getResId());
                        bpmResIndSnmpOids.setVendorId(bpmTaskMonRes.getVendorID());

                        // osType、osVersion、osFeature、modelOID
                        bpmResIndSnmpOids.setOsType(bpmTaskMonRes.getOsType());
                        bpmResIndSnmpOids.setOsVersion(bpmTaskMonRes.getOsVersion());
                        bpmResIndSnmpOids.setOsFeature(bpmTaskMonRes.getOsFeature());
                        bpmResIndSnmpOids.setModelOID(bpmTaskMonRes.getModelOID());
                        bpmResIndSnmpOids.setProdModel(bpmTaskMonRes.getProdModel());
                        bpmResIndSnmpOids.setStatus(0);//默认为“未验证”状态

                        bpmResIndSnmpOidsList.add(bpmResIndSnmpOids);
                    }
                }
            }
            //              4、将关联对象保存入库
            bpmResMonIndsDao.batchSave(bpmResMonIndsList,20);
            bpmResMonIndsDao.flush();

            bpmResIndSnmpOidsDao.batchSave(bpmResIndSnmpOidsList,20);
            bpmResIndCmdParseDao.flush();
        }

    }

    /*
    * @author fengyao
    * @Description 保存Bpm_ResMonInds、Bpm_ResIndCmdParse
    * @Date 11:15 2020/5/19
    * @Param [bpmTaskMonResList, parseIdsSplit]
    * return void
    **/
    private void saveCmdData(List<BpmTaskMonRes> bpmTaskMonResList, List<Integer> parseIdIdList) {
        List<BpmResMonInds> bpmResMonIndsList = new ArrayList<>();
        List<BpmResIndCmdParse> bpmResIndCmdParseList = new ArrayList<>();

        List<CmdbResIndCmdParse> cmdParseList = cmdbResIndCmdParseDao.get(parseIdIdList);
//        重写了CmdbResIndCmdParse类的equals方法，
//          以resClassCode, resTypeCode, indClassCode, indGroupID, indItemID分类--为了获取指标
        Set<CmdbResIndCmdParse> cmdParseSet = new HashSet<>(cmdParseList);

//        获取指标bpmResMonIndsList
        if(cmdParseList!=null && cmdParseList.size()>0){
            for(BpmTaskMonRes bpmTaskMonRes : bpmTaskMonResList){

//                组装指标
                for(CmdbResIndCmdParse cmdbResIndCmdParse : cmdParseSet){
                    if(bpmTaskMonRes!=null && cmdbResIndCmdParse!=null){
                        BpmResMonInds bpmResMonInds = new BpmResMonInds();

                        CmdbResTypeIndicators cmdbResTypeIndicators = cmdbResTypeIndicatorsDao.get(cmdbResIndCmdParse.getResTypeIndId());
                        BeanUtils.copyProperties(cmdbResTypeIndicators,bpmResMonInds);

                        bpmResMonInds.setTaskId(bpmTaskMonRes.getTaskId());
                        bpmResMonInds.setTaskName(bpmTaskMonRes.getTaskName());
                        bpmResMonInds.setResId(bpmTaskMonRes.getResId());
                        bpmResMonInds.setResIp(bpmTaskMonRes.getResIp());
                        bpmResMonInds.setResIpLong(bpmTaskMonRes.getResIpLong());
                        bpmResMonInds.setIndCollMode(bpmTaskMonRes.getIndCollMode());
                        bpmResMonInds.setIndItemId(cmdbResTypeIndicators.getIndItemID());
                        bpmResMonInds.setIndItemEnName(cmdbResTypeIndicators.getIndicatorItem());
                        bpmResMonInds.setFlag(0);//默认状态“未验证”
                        bpmResMonInds.setIndCollMode("cmd");

                        bpmResMonIndsList.add(bpmResMonInds);
                    }
                }

//                组装命令
                for(CmdbResIndCmdParse cmdbResIndCmdParse2 : cmdParseList){
                    if(bpmTaskMonRes!=null && cmdbResIndCmdParse2!=null){
                        BpmResIndCmdParse bpmResIndCmdParse = new BpmResIndCmdParse();

                        BeanUtils.copyProperties(cmdbResIndCmdParse2,bpmResIndCmdParse);

                        bpmResIndCmdParse.setResId(bpmTaskMonRes.getResId());
                        bpmResIndCmdParse.setVendorID(bpmTaskMonRes.getVendorID());

                        // osType、osVersion、osFeature、modelOID
                        bpmResIndCmdParse.setOsType(bpmTaskMonRes.getOsType());
                        bpmResIndCmdParse.setOsVersion(bpmTaskMonRes.getOsVersion());
                        bpmResIndCmdParse.setOsFeature(bpmTaskMonRes.getOsFeature());
                        bpmResIndCmdParse.setModelOID(bpmTaskMonRes.getModelOID());
                        bpmResIndCmdParse.setProdModel(bpmTaskMonRes.getProdModel());
                        bpmResIndCmdParse.setStatus(0);//默认为“未验证”状态

                        bpmResIndCmdParseList.add(bpmResIndCmdParse);
                    }
                }
            }

//            System.out.println(bpmResMonIndsList);
//            System.out.println(bpmResIndCmdParseList);
            //              4、将关联对象保存入库
            bpmResMonIndsDao.batchSave(bpmResMonIndsList,20);
            bpmResMonIndsDao.flush();

            bpmResIndCmdParseDao.batchSave(bpmResIndCmdParseList,20);
            bpmResIndCmdParseDao.flush();
        }
    }

    /*
    * @author fengyao
    * @Description 根据Id获取对象
    * @Date 16:10 2020/5/18
    * @Param [s]
    * return com.sino.bpm.entity.BpmTaskMonRes
    **/
    public BpmTaskMonRes getById(Integer id) {
        BpmTaskMonRes bpmTaskMonRes = bpmTaskMonResDao.get(id);
        return bpmTaskMonRes;
    }

    public List<ResItemParam> getResClassCodes() {
        List<ResItemParam> ripList  = bpmTaskMonResDao.getResClassCodes();
        return ripList;
    }

    public List<ResItemParam> getResTypeCodes(int resClassCode) {
        List<ResItemParam> list = bpmTaskMonResDao.getResTypeCodes(resClassCode);
        return list;
    }

    public List<Object[]> getResNameByTypeCode(int code) {
        List<Object[]> list = bpmTaskMonResDao.getResNameByTypeCode(code);
        return list;
    }

    /*
    * @author fengyao
    * @Description 指标采集验证
    * @Date 16:03 2020/6/17
    * @Param [id]
    * return java.util.List<com.sino.bpm.entity.IndValueVO>
    **/
    public List<IndValueVO> indsCollectVerification(Integer id) {
        List<IndValueVO> indValueVOList = new ArrayList<>();

//        获取该设备
        BpmTaskMonRes bpmTaskMonRes = bpmTaskMonResDao.get(id);
//        map<cmd,Cmdb_ProdChkCmds.prodChkCmdId>--cmd解析
        Map<String,Integer> cmdMap = new HashMap<String,Integer>();
        String ip = "";
        String username = "";
        String pwd = "";
        String privModePasswd = "";

        if(bpmTaskMonRes!=null){
            Long resId = bpmTaskMonRes.getResId();
            ip = bpmTaskMonRes.getResIp();

            if(resId!=null){
//                连接设备的信息
                NcmDevAccessUser ncmDevAccessUser = ncmDevAccessUserDao.getByResId(resId);
                if(ncmDevAccessUser!=null){
                    username = ncmDevAccessUser.getUserName();
                    pwd = ncmDevAccessUser.getPassWord();
                    privModePasswd = ncmDevAccessUser.getPrivModePasswd();
                }

//                cmd解析规则
                List<BpmResIndCmdParse> bpmResIndCmdParseList = bpmResIndCmdParseDao.getByResId(resId);
                if(bpmResIndCmdParseList!=null && bpmResIndCmdParseList.size()>0){
                    for(BpmResIndCmdParse bpmResIndCmdParse : bpmResIndCmdParseList){
                        if(bpmResIndCmdParse!=null){
                            String checkCmd = bpmResIndCmdParse.getCheckCmd();
                            Integer chkItemId = bpmResIndCmdParse.getChkItemId();
                            if(chkItemId!=null && StringUtils.isNotBlank(checkCmd) && !cmdMap.containsKey(checkCmd)){
                                cmdMap.put(checkCmd,chkItemId);
                            }
                        }
                    }

                    if(StringUtils.isNotBlank(ip) && ncmDevAccessUser!=null){
                        Map<String, String> cmdResultMap = getCmdResutl(ip, username, pwd, privModePasswd, cmdMap);
                        if(cmdResultMap!=null && cmdResultMap.size()>0){
                            indValueVOList = parseCmdResult(bpmResIndCmdParseList,cmdResultMap);
                        }
                    }
                }
//                snmp解析规则
                List<BpmResIndSnmpOids> bpmResIndSnmpOidsList = bpmResIndSnmpOidsDao.getByResId(resId);
                if(bpmResIndSnmpOidsList!=null && bpmResIndSnmpOidsList.size()>0){
//                    TODO:snmp解析暂时先不做
                }
            }
        }

        return indValueVOList;
    }

    /*
    * @author fengyao
    * @Description 根据解析规则对获取的cmd执行结果进行解析提取，封装到对象中
    * @Date 11:08 2020/6/18
    * @Param [bpmResIndCmdParseList, indResultVOList]
    * return java.util.List<com.sino.bpm.entity.IndValueVO>
    **/
    private List<IndValueVO> parseCmdResult(List<BpmResIndCmdParse> bpmResIndCmdParseList, Map<String, String> cmdResultMap) {
        List<IndValueVO> indValueVOList = new ArrayList<>();
        List<BpmResIndCmdParse> bpmResIndCmdParses = new ArrayList<>();

        for(BpmResIndCmdParse bpmResIndCmdParse:bpmResIndCmdParseList){
            IndValueVO indValueVO = new IndValueVO();

            Integer chkItemId = bpmResIndCmdParse.getChkItemId();
            String checkCmd = bpmResIndCmdParse.getCheckCmd();
//            获取解析规则
            CmdbProdCmdChkItems cmdbProdCmdChkItems = cmdbProdCmdCheckItemsDao.get(chkItemId);
//            '解析方式（1：正则表达式；2：字符串匹配；3：字符分割-split；4. 模版解析）',
            Integer parseMode = cmdbProdCmdChkItems.getParseMode();

//            找到该条指标对应的cmd执行结果
            if(cmdResultMap.containsKey(checkCmd)){
                String indGroupName = bpmResIndCmdParse.getIndGroupName();
                String indItemEnName = bpmResIndCmdParse.getIndItemEnName();
                String indItemName = bpmResIndCmdParse.getIndItemName();
                String measureUnit = bpmResIndCmdParse.getMeasureUnit();

                indValueVO.setIndGroupName(indGroupName);
                indValueVO.setIndItemName(indItemName);
                indValueVO.setIndItemEnName(indItemEnName);

//                cmd执行结果
                String cmdResult = cmdResultMap.get(checkCmd);

                if(parseMode==1){//正则表达式
                    String regEx = cmdbProdCmdChkItems.getRegEx();
                    String resultKeyWords = cmdbProdCmdChkItems.getResultKeyWords();
                    // 创建 Pattern 对象
                    Pattern r = Pattern.compile(regEx);

                    // 现在创建 matcher 对象
                    Matcher m = r.matcher(cmdResult);
                    if (m.find( )) {
                        String group = m.group(0).trim();//Processor Pool Total:   73910696
                        int startIndex = group.indexOf(resultKeyWords)+resultKeyWords.length()+1;
                        String result = group.substring(startIndex).trim();

                        indValueVO.setValue(result);
                        indValueVO.setMeasureUnit(measureUnit);
                    }
                }
                if(parseMode==2){//字符串匹配
                    String startKeyWords = cmdbProdCmdChkItems.getStartKeyWords();
                    String endKeyWords = cmdbProdCmdChkItems.getEndKeyWords();

                    int startIndex = cmdResult.indexOf(startKeyWords);
                    String substring = cmdResult.substring(startIndex);

                    startIndex = substring.indexOf(startIndex)+ startKeyWords.length()+1;
                    int endIndex = substring.indexOf(endKeyWords);
                    String value = substring.substring(startIndex, endIndex).trim();

                    indValueVO.setValue(value);
                    indValueVO.setMeasureUnit(measureUnit);
                }
                if(parseMode==3){//字符分割-split


                }
                if(parseMode==4){//模版解析

                }

//                如果通过解析规则获取到了指标值--说明通过验证，否则就是未通过验证
                if(StringUtils.isNotBlank(indValueVO.getValue())){
                    bpmResIndCmdParse.setStatus(1);
                    bpmResIndCmdParses.add(bpmResIndCmdParse);
                }

                indValueVOList.add(indValueVO);
            }
        }

//        保存“验证通过”的状态
        bpmResIndCmdParseDao.batchSave(bpmResIndCmdParses,20);
        bpmResIndCmdParseDao.flush();

        return indValueVOList;
    }


    /*
    * @author fengyao
    * @Description ssh连接到设备，发送cmd，获取结果并封装
    * @Date 11:07 2020/6/18
    * @Param
    * return
    **/
    private Map<String,String> getCmdResutl(String ip, String username, String pwd, String privModePasswd, Map<String, Integer> cmdMap) {

        Map<String,String> map = new HashedMap();

        if(cmdMap!=null && cmdMap.size()>0 && StringUtils.isNotBlank(ip)){
            SSHConnection base = new SSHConnection(ip);
            base.setAccount(username);
            base.setPassword(pwd);
            base.connect();

            String privUserPromt = ">";
            String  cmdPromt= "#";

            try {
                base.getExpect();

                base.login();
                String removeCmd = "terminal length 0";
                String write = base.write("\r\n", cmdPromt);
                String deviceName = write.trim().split(cmdPromt)[0];
                base.write(removeCmd, removeCmd+"\r\n"+deviceName+cmdPromt);

                for(String cmd: cmdMap.keySet()){
                    String result = base.write(cmd,cmdPromt).replace(cmd,"").replaceAll(deviceName+cmdPromt,"").trim();
//                    System.out.println(result);
                    map.put(cmd,result);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public List<MonitorIndsVO> getMonitorInds(String ids) {
        List<MonitorIndsVO> monitorIndsVOList = new ArrayList<>();

        List<BpmResIndCmdParse> bpmResIndCmdParseList = bpmResIndCmdParseDao.getRelatedList(ids);
        List<BpmResIndSnmpOids> bpmResIndSnmpOidsList = bpmResIndSnmpOidsDao.getRelatedList(ids);

        if(bpmResIndCmdParseList!=null && bpmResIndCmdParseList.size()>0){
            for(BpmResIndCmdParse bpmResIndCmdParse : bpmResIndCmdParseList){
                MonitorIndsVO monitorIndsVO = new MonitorIndsVO();

                BeanUtils.copyProperties(bpmResIndCmdParse, monitorIndsVO);

                BpmTaskMonRes bpmTaskMonRes = bpmTaskMonResDao.getByResId(bpmResIndCmdParse.getResId()).get(0);
                String resIp = bpmTaskMonRes.getResIp();
                String vendorName = bpmTaskMonRes.getVendorName();

                monitorIndsVO.setResIp(resIp);
                monitorIndsVO.setVendorName(vendorName);
                monitorIndsVO.setIndCollMode("cmd");

                monitorIndsVOList.add(monitorIndsVO);
            }
        }

        if(bpmResIndSnmpOidsList!=null && bpmResIndSnmpOidsList.size()>0){
            for(BpmResIndSnmpOids bpmResIndSnmpOids : bpmResIndSnmpOidsList){
                MonitorIndsVO monitorIndsVO = new MonitorIndsVO();

                BeanUtils.copyProperties(bpmResIndSnmpOids, monitorIndsVO);
                monitorIndsVO.setIndItemEnName(bpmResIndSnmpOids.getIndicatorItem());

                BpmTaskMonRes bpmTaskMonRes = bpmTaskMonResDao.getByResId(bpmResIndSnmpOids.getResId()).get(0);
                String resIp = bpmTaskMonRes.getResIp();
                String vendorName = bpmTaskMonRes.getVendorName();

                monitorIndsVO.setResIp(resIp);
                monitorIndsVO.setVendorName(vendorName);
                monitorIndsVO.setIndCollMode("snmp");

                monitorIndsVOList.add(monitorIndsVO);
            }
        }

        return monitorIndsVOList;
    }

}
