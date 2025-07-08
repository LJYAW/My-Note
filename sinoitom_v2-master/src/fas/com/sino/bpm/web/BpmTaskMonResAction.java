package com.sino.bpm.web;

import com.alibaba.fastjson.JSON;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.bpm.entity.*;
import com.sino.bpm.service.*;
import com.sino.fas.res.net.entity.NcmDevAccessUser;
import com.sino.fas.res.net.service.NcmDevAccessUserService;
import com.sino.res.biz.entity.ResBizSystem;
import com.sinobridge.nms.controller.ack.AutoCheckController;
import com.sinobridge.nms.core.collection.ack.pojo.AckDevCheckData;
import com.sinobridge.nms.core.model.ResponseMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.utils.SSHConnection;
import smartlink.systemadmin.SystemManagement;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName BpmTaskMonResAction
 * @Description TODO
 * @Author fengyao
 * Date 2020/5/13 9:54
 * @Version 1.0
 **/
@RequestMapping(value="/bpm/taskMonRes")
@Controller
public class BpmTaskMonResAction {
    private String viewPath = "/bpm/taskMonRes";
    private static Logger logger= LoggerFactory.getLogger(BpmTaskMonResAction.class);

    @Autowired
    private BpmTaskMonResService bpmTaskMonResService;
    @Autowired
    private CmdbResIndCmdParseService cmdbResIndCmdParseService;
    @Autowired
    private CmdbResIndSnmpOidService cmdbResIndSnmpOidService;
    @Autowired
    private BpmResIndCmdParseService bpmResIndCmdParseService;
    @Autowired
    private BpmResIndSnmpOidsService bpmResIndSnmpOidsService;
    @Autowired
    private NcmDevAccessUserService ncmDevAccessUserService;

    /*
    * @author fengyao
    * @Description 列表
    * @Date 16:03 2020/5/13
    * @Param [menuId, map]
    * return java.lang.String
    **/
    @RequestMapping(value = "/main.do")
    public String main(String menuId,ModelMap map) {
        logger.info("Enter BpmTaskMonResAction main.do ...");
        List<BpmTaskMonRes> tasks = bpmTaskMonResService.getAll();
        String jsonStr = bpmTaskMonResService.getJsonListStr(tasks);
        map.put("jsonListData", jsonStr);
        WebFuncUtils.titleContent(menuId,map);
        return viewPath + "/main";
    }

    /*
    * @author fengyao
    * @Description 关联指标页面
    * @Date 16:05 2020/5/13
    * @Param [id, map]
    * return java.lang.String
    **/
    @RequestMapping(value = "/addResRelateInds.do")
    public String addResRelateInds(String ids,Integer resClassCode,Integer resTypeCode, ModelMap map) {
        logger.info("Enter BpmTaskMonResAction addResRelateInds.do ...");

//        List<CmdbResTypeIndicators> list = cmdbResTypeIndicatorsService.getByClassCodeAndTypeCode(ids,resClassCode,resTypeCode);
//        String jsonStr = cmdbResTypeIndicatorsService.getJsonListStr(list);
//        map.put("jsonListData", jsonStr);
        map.put("ids",ids);
        map.put("resClassCode",resClassCode);
        map.put("resTypeCode",resTypeCode);
        return viewPath + "/addInds";
    }

    @RequestMapping(value = "/getFilterInds.do")
    @ResponseBody
    public String getFilterInds(String ids,Integer resClassCode,Integer resTypeCode,String indCollMode) {
       if(StringUtils.isNotBlank(ids) && resClassCode!=null && resTypeCode!=null && StringUtils.isNotBlank(indCollMode)){
           String jsonListStr = "";

//        找到已经关联的指标的cmd和snmp的id的集合
           List<Integer> resTypeIndIdList = getRelatedParseId(ids);

//           所选资源的resClassCode、resTypeCode、modelOID、osType、osVersion、osFeature应该是一样的
           String[] split = ids.split(",");
           if(split!=null && split.length>0){
               BpmTaskMonRes bpmTaskMonRes = bpmTaskMonResService.getById(Integer.parseInt(split[0]));
               if(bpmTaskMonRes!=null ){
                   if(indCollMode.equals("cmd")){
                       List<CmdbResIndCmdParse> cmdbResIndCmdParseList = cmdbResIndCmdParseService.getCmdInds(resTypeIndIdList,bpmTaskMonRes);
                       List<CmdbResIndCmdParse> sortedCmdbResIndCmdParseList = sortCmdbResIndCmdParseList(cmdbResIndCmdParseList);
                       jsonListStr = cmdbResIndCmdParseService.getJsonListStr(sortedCmdbResIndCmdParseList);
                   }

                   if(indCollMode.equals("snmp")){
                       List<CmdbResIndSnmpOid> cmdbResIndSnmpOidList = cmdbResIndSnmpOidService.getSnmpInds(resTypeIndIdList,bpmTaskMonRes);
                       List<CmdbResIndSnmpOid> sortedCmdbResIndSnmpOidList = sortCmdbResIndSnmpOidList(cmdbResIndSnmpOidList);
                       jsonListStr = cmdbResIndSnmpOidService.getJsonListStr(sortedCmdbResIndSnmpOidList);
                   }
               }
           }
           return jsonListStr;
       }
        return null;
    }

    private List<CmdbResIndCmdParse> sortCmdbResIndCmdParseList(List<CmdbResIndCmdParse> cmdbResIndCmdParseList) {
//        以resTypeIndId为条件重写CmdbResIndCmdParse的equals方法，以resTypeIndId作为去重条件
        Set<CmdbResIndCmdParse> cmdbResIndCmdParseSet = new HashSet<>(cmdbResIndCmdParseList);
//        说明这里面指标都是唯一的，没的选，只能全部列出来
        if(cmdbResIndCmdParseSet.size()==cmdbResIndCmdParseList.size()){
            return cmdbResIndCmdParseList;
        }

//        获取主键id的集合
        List<Integer> chkItemIdList = new ArrayList<>();
        for(CmdbResIndCmdParse cmdbResIndCmdParse:cmdbResIndCmdParseSet){
            chkItemIdList.add(cmdbResIndCmdParse.getChkItemId());
        }

//        map<chkItemId,指标重复的对象的集合>
        Map<Integer,List<CmdbResIndCmdParse>> map = new HashMap<>();
//        找出指标有重复的项，如果没重复就不管了
        for(Integer chkItemId : chkItemIdList){
            List<CmdbResIndCmdParse> list = new ArrayList<>();
            for(CmdbResIndCmdParse cmdbResIndCmdParse:cmdbResIndCmdParseList){
                if(chkItemId==cmdbResIndCmdParse.getChkItemId()){
                    list.add(cmdbResIndCmdParse);
                }
            }
            if(list!=null && list.size()>1){//不止一个的
                map.put(chkItemId,list);
            }
        }

//        找到排序优先级较低的集合，最后将这些数据从原集合中remove掉
        List<CmdbResIndCmdParse> allOutList = new ArrayList<>();
        for(Integer key : map.keySet()){
            List<CmdbResIndCmdParse> cmdbResIndCmdParses = map.get(key);
            List<CmdbResIndCmdParse> outList = findCmdOutList(cmdbResIndCmdParses);
            if(outList!=null && outList.size()>0){
                allOutList.addAll(outList);
            }
        }

        if(allOutList!=null && allOutList.size()>0){
            cmdbResIndCmdParseList.removeAll(allOutList);
            return cmdbResIndCmdParseList;
        }

        return cmdbResIndCmdParseList;
    }

    private List<CmdbResIndCmdParse> findCmdOutList(List<CmdbResIndCmdParse> cmdbResIndCmdParses) {

        List<CmdbResIndCmdParse> allConformList = new ArrayList<>();//按照优先级，这个集合不为空优先返回这个集合
        List<CmdbResIndCmdParse> threeConformList = new ArrayList<>();//allConformList为空返回这个集合，以下以此类推
        List<CmdbResIndCmdParse> twoConformList = new ArrayList<>();
        List<CmdbResIndCmdParse> oneConformList = new ArrayList<>();
        List<CmdbResIndCmdParse> zoroConformList = new ArrayList<>();

        for(CmdbResIndCmdParse cmdbResIndCmdParse : cmdbResIndCmdParses){
            if(cmdbResIndCmdParse!=null){
                String osVersion = cmdbResIndCmdParse.getOsVersion();
                String osFeature = cmdbResIndCmdParse.getOsFeature();
                String modelOID = cmdbResIndCmdParse.getModelOID();
                Integer vendorID = cmdbResIndCmdParse.getVendorID();

//                    4个条件都是确切的
                if((StringUtils.isNotBlank(osVersion) && !osVersion.equalsIgnoreCase("V.R"))
                        && (StringUtils.isNotBlank(osFeature) && !osFeature.equalsIgnoreCase("all"))
                        && (StringUtils.isNotBlank(modelOID) && !modelOID.equalsIgnoreCase("X.X"))
                        && (vendorID!=null && vendorID!=-1)){
                    allConformList.add(cmdbResIndCmdParse);
                }

//                    前三个条件是确切的
                else if((StringUtils.isNotBlank(osVersion) && !osVersion.equalsIgnoreCase("V.R"))
                        && (StringUtils.isNotBlank(osFeature) && !osFeature.equalsIgnoreCase("all"))
                        && (StringUtils.isNotBlank(modelOID) && !modelOID.equalsIgnoreCase("X.X"))
                ){
                    threeConformList.add(cmdbResIndCmdParse);
                }

//                    前两个条件是确切的
                else if((StringUtils.isNotBlank(osVersion) && !osVersion.equalsIgnoreCase("V.R"))
                        && (StringUtils.isNotBlank(osFeature) && !osFeature.equalsIgnoreCase("all"))
                ){
                    twoConformList.add(cmdbResIndCmdParse);
                }

//                    前一个条件是确切的
                else if(StringUtils.isNotBlank(osVersion) && !osVersion.equalsIgnoreCase("V.R")){
                    oneConformList.add(cmdbResIndCmdParse);
                }

//                    没有一个条件是确切的
                else{
                    zoroConformList.add(cmdbResIndCmdParse);
                }
            }
        }

        if(allConformList!=null && allConformList.size()>0){
//            获得除这个集合外的所有项
            cmdbResIndCmdParses.removeAll(allConformList);
            return cmdbResIndCmdParses;
        }
        if(threeConformList!=null && threeConformList.size()>0){
            cmdbResIndCmdParses.removeAll(threeConformList);
            return cmdbResIndCmdParses;
        }
        if(twoConformList!=null && twoConformList.size()>0){
            cmdbResIndCmdParses.removeAll(twoConformList);
            return cmdbResIndCmdParses;
        }
        if(oneConformList!=null && oneConformList.size()>0){
            cmdbResIndCmdParses.removeAll(oneConformList);
            return cmdbResIndCmdParses;
        }
        if(zoroConformList!=null && zoroConformList.size()>0){
            cmdbResIndCmdParses.removeAll(zoroConformList);
            return cmdbResIndCmdParses;
        }
        return null;
    }

    private List<CmdbResIndSnmpOid> sortCmdbResIndSnmpOidList(List<CmdbResIndSnmpOid> cmdbResIndSnmpOidList) {
//        以resTypeIndId为条件重写CmdbResIndCmdParse的equals方法，以resTypeIndId作为去重条件
        Set<CmdbResIndSnmpOid> cmdbResIndSnmpOidSet = new HashSet<>(cmdbResIndSnmpOidList);
//        说明这里面指标都是唯一的，没的选，只能全部列出来
        if(cmdbResIndSnmpOidSet.size()==cmdbResIndSnmpOidSet.size()){
            return cmdbResIndSnmpOidList;
        }

//        获取主键id的集合
        List<Integer> resIndSnmpOidList = new ArrayList<>();
        for(CmdbResIndSnmpOid cmdbResIndSnmpOid:cmdbResIndSnmpOidSet){
            resIndSnmpOidList.add(cmdbResIndSnmpOid.getResIndSnmpOid());
        }

//        map<chkItemId,指标重复的对象的集合>
        Map<Integer,List<CmdbResIndSnmpOid>> map = new HashMap<>();
//        找出指标有重复的项，如果没重复就不管了
        for(Integer resIndSnmpOid : resIndSnmpOidList){
            List<CmdbResIndSnmpOid> list = new ArrayList<>();
            for(CmdbResIndSnmpOid cmdbResIndSnmpOid:cmdbResIndSnmpOidList){
                if(resIndSnmpOid==cmdbResIndSnmpOid.getResIndSnmpOid()){
                    list.add(cmdbResIndSnmpOid);
                }
            }
            if(list!=null && list.size()>1){//不止一个的
                map.put(resIndSnmpOid,list);
            }
        }

//        找到排序优先级较低的集合，最后将这些数据从原集合中remove掉
        List<CmdbResIndSnmpOid> allOutList = new ArrayList<>();
        for(Integer key : map.keySet()){
            List<CmdbResIndSnmpOid> cmdbResIndSnmpOids = map.get(key);
            List<CmdbResIndSnmpOid> outList = findSnmpOutList(cmdbResIndSnmpOids);
            if(outList!=null && outList.size()>0){
                allOutList.addAll(outList);
            }
        }

        if(allOutList!=null && allOutList.size()>0){
            cmdbResIndSnmpOidList.removeAll(allOutList);
            return cmdbResIndSnmpOidList;
        }

        return cmdbResIndSnmpOidList;
    }

    private List<CmdbResIndSnmpOid> findSnmpOutList(List<CmdbResIndSnmpOid> cmdbResIndSnmpOids) {
        List<CmdbResIndSnmpOid> allConformList = new ArrayList<>();//按照优先级，这个集合不为空优先返回这个集合
        List<CmdbResIndSnmpOid> threeConformList = new ArrayList<>();//allConformList为空返回这个集合，以下以此类推
        List<CmdbResIndSnmpOid> twoConformList = new ArrayList<>();
        List<CmdbResIndSnmpOid> oneConformList = new ArrayList<>();
        List<CmdbResIndSnmpOid> zoroConformList = new ArrayList<>();

        for(CmdbResIndSnmpOid cmdbResIndSnmpOid : cmdbResIndSnmpOids){
            if(cmdbResIndSnmpOid!=null){
                String osVersion = cmdbResIndSnmpOid.getOsVersion();
                String osFeature = cmdbResIndSnmpOid.getOsFeature();
                String modelOID = cmdbResIndSnmpOid.getModelOID();
                Integer vendorID = cmdbResIndSnmpOid.getVendorId();

//                    4个条件都是确切的
                if((StringUtils.isNotBlank(osVersion) && !osVersion.equalsIgnoreCase("V.R"))
                        && (StringUtils.isNotBlank(osFeature) && !osFeature.equalsIgnoreCase("all"))
                        && (StringUtils.isNotBlank(modelOID) && !modelOID.equalsIgnoreCase("X.X"))
                        && (vendorID!=null && vendorID!=-1)){
                    allConformList.add(cmdbResIndSnmpOid);
                }

//                    前三个条件是确切的
                else if((StringUtils.isNotBlank(osVersion) && !osVersion.equalsIgnoreCase("V.R"))
                        && (StringUtils.isNotBlank(osFeature) && !osFeature.equalsIgnoreCase("all"))
                        && (StringUtils.isNotBlank(modelOID) && !modelOID.equalsIgnoreCase("X.X"))
                ){
                    threeConformList.add(cmdbResIndSnmpOid);
                }

//                    前两个条件是确切的
                else if((StringUtils.isNotBlank(osVersion) && !osVersion.equalsIgnoreCase("V.R"))
                        && (StringUtils.isNotBlank(osFeature) && !osFeature.equalsIgnoreCase("all"))
                ){
                    twoConformList.add(cmdbResIndSnmpOid);
                }

//                    前一个条件是确切的
                else if(StringUtils.isNotBlank(osVersion) && !osVersion.equalsIgnoreCase("V.R")){
                    oneConformList.add(cmdbResIndSnmpOid);
                }

//                    没有一个条件是确切的
                else{
                    zoroConformList.add(cmdbResIndSnmpOid);
                }
            }
        }

        if(allConformList!=null && allConformList.size()>0){
//            获得除这个集合外的所有项
            cmdbResIndSnmpOids.removeAll(allConformList);
            return cmdbResIndSnmpOids;
        }
        if(threeConformList!=null && threeConformList.size()>0){
            cmdbResIndSnmpOids.removeAll(threeConformList);
            return cmdbResIndSnmpOids;
        }
        if(twoConformList!=null && twoConformList.size()>0){
            cmdbResIndSnmpOids.removeAll(twoConformList);
            return cmdbResIndSnmpOids;
        }
        if(oneConformList!=null && oneConformList.size()>0){
            cmdbResIndSnmpOids.removeAll(oneConformList);
            return cmdbResIndSnmpOids;
        }
        if(zoroConformList!=null && zoroConformList.size()>0){
            cmdbResIndSnmpOids.removeAll(zoroConformList);
            return cmdbResIndSnmpOids;
        }
        return null;
    }

    //    求已经关联的指标主键id的集合
    private List<Integer> getRelatedParseId(String ids) {
        Set<Integer> resTypeIndIdSet = new HashSet<>();

//           找到已经关联的cmd命令，
        List<BpmResIndCmdParse> bricpList = bpmResIndCmdParseService.getRelatedList(ids);
        if(bricpList!=null && bricpList.size()>0){
            for(BpmResIndCmdParse bpmResIndCmdParse:bricpList){
                Integer resTypeIndId = bpmResIndCmdParse.getResTypeIndId();
                resTypeIndIdSet.add(resTypeIndId);
            }
        }
//           找到已经关联的snmp命令，
        List<BpmResIndSnmpOids> brisoList = bpmResIndSnmpOidsService.getRelatedList(ids);
        if(brisoList!=null && brisoList.size()>0){
            for(BpmResIndSnmpOids bpmResIndSnmpOids:brisoList){
                Integer resTypeIndId = bpmResIndSnmpOids.getResTypeIndId();
                resTypeIndIdSet.add(resTypeIndId);
            }
        }

//        取并集
        List<Integer> resTypeIndIdList = new ArrayList<>(resTypeIndIdSet);
        return resTypeIndIdList;
    }

    /*
    * @author fengyao
    * @Description 资源关联指标
    * @Date 9:52 2020/5/14
    * @Param [ids, indsIds, map]
    * return java.lang.String
    **/
    @RequestMapping(value="/resRelateInds.do")
    public String resRelateInds(String ids,String indCollMode,String parseIds,ModelMap map) throws IOException {
        logger.info("Enter BpmTaskMonResAction resRelateInds.do..");
        try {
            if(StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(parseIds) && StringUtils.isNotBlank(indCollMode)){
                bpmTaskMonResService.resRelateInds(ids,indCollMode,parseIds);
                map.put("result", "success");
            }else{
                map.put("result", "error");
            }
        } catch (Exception e) {
            map.put("result", "error");
            e.printStackTrace();
        }
        map.put("jsonListData","[{}]");
        return viewPath+"/addInds";
    }
/*
* @author fengyao
* @Description 访问验证
* @Date 15:44 2020/7/1
* @Param [id, response]
* return java.lang.String
**/
    @RequestMapping(value = "/accessVerification.do")
    @ResponseBody
    public String accessVerification(Integer id, HttpServletResponse response) throws IOException {
        logger.info("BpmTaskMonResAction enter accessVerification method");

        String result="验证失败！";
        JSONObject jo=new JSONObject();

        if(id==null){
            result="主键id不能为空！";
            jo.put("result", result);
            return jo.toString();
        }

        BpmTaskMonRes bpmTaskMonRes = bpmTaskMonResService.getById(id);
        if(bpmTaskMonRes==null){
            result="数据库中找到不到该资源！";
            jo.put("result", result);
            return jo.toString();
        }

        String resIp = bpmTaskMonRes.getResIp();
        if(StringUtils.isBlank(resIp)){
            result="ip为空！";
            jo.put("result", result);
            return jo.toString();
        }

        StringBuffer pingresult=new StringBuffer();
        SystemManagement sysmgt = new SystemManagement();
        pingresult = sysmgt.getPingResult(resIp);
        if(pingresult==null || StringUtils.isBlank(pingresult.toString())){
            result="ping不通，请检查网络！";
            jo.put("result", result);
            return jo.toString();
        }
        Long resId = bpmTaskMonRes.getResId();
        if(resId!=null){
            NcmDevAccessUser devAccessUser = ncmDevAccessUserService.getByResId(resId);
            if(devAccessUser==null){
                result="查询不到连接信息！";
                jo.put("result", result);
                return jo.toString();
            }
            String userName = devAccessUser.getUserName();
            String passWord = devAccessUser.getPassWord();

            try {
                SSHConnection base = new SSHConnection(resIp);
                base.setAccount(userName);
                base.setPassword(passWord);
                base.connect();

                base.login();
                result="访问验证成功！";
                jo.put("result", result);
                return jo.toString();
            }catch (Exception e){
                result="ssh连接失败！";
                jo.put("result", result);
                e.printStackTrace();
                return jo.toString();
            }
        }

        jo.put("result", result);
        return jo.toString();
    }

/*
* @author fengyao
* @Description 指标采集验证
* @Date 15:45 2020/7/1
* @Param [id, response]
* return void
**/
    @RequestMapping(value = "/indsCollectVerification.do")
    public void indsCollectVerification(Integer id, HttpServletResponse response) throws IOException {
        logger.info("BpmTaskMonResAction enter indsCollectVerification method");

        JSONObject jo=new JSONObject();

        if(id!=null){
            List<IndValueVO> list = bpmTaskMonResService.indsCollectVerification(id);
            if(list!=null && list.size()>0){
                JSONArray ja= JSONArray.fromObject(list);

                jo.put("indTestResult", ja.toString());
                jo.put("result", "success");
            }else{
                jo.put("indTestResult", "");
                jo.put("result", "指标采集验证失败！请检查！");
            }
        }else{
            jo.put("indTestResult", "");
            jo.put("result", "指标采集验证失败！请检查！");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
    }

    /*
    * @author fengyao
    * @Description 跳转到指标采集验证页面
    * @Date 15:45 2020/7/1
    * @Param []
    * return java.lang.String
    **/
    @RequestMapping(value = "/toIndsCollectVerification.do")
    public String experiment() {
        return viewPath + "/indsCollectVerification";
    }

    /*
    * @author fengyao
    * @Description 监测指标页面
    * @Date 15:50 2020/7/1
    * @Param []
    * return java.lang.String
    **/
    @RequestMapping(value = "/getMonitorInds.do")
    public String getMonitorInds(String ids,ModelMap map){
        logger.info("BpmTaskMonResAction enter getMonitorInds method");
        if(StringUtils.isNotBlank(ids)){
            List<MonitorIndsVO> list = bpmTaskMonResService.getMonitorInds(ids);

            JSONArray ja= JSONArray.fromObject(list);
            map.put("jsonListData", ja.toString());

            String[] idArr = ids.split(",");
            if(idArr!=null && idArr.length>0){
                if(idArr.length==1){//只选了一个资源
                    return viewPath+"/monitorInds";
                }else{//不止一个资源
                    return viewPath+"/multiResMonitorInds";
                }
            }
        }

        return viewPath+"/monitorInds";
    }
}
