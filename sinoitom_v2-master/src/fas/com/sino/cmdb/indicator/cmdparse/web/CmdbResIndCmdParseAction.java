package com.sino.cmdb.indicator.cmdparse.web;

import com.sino.ack.devCheckItem.entity.AckDevCheckItems;
import com.sino.ack.resources.dao.AckResourcesDao;
import com.sino.ack.resources.entity.AckResources;
import com.sino.base.common.util.StringUtil;
import com.sino.bpm.dao.CmdbResIndCmdParseDao;
import com.sino.bpm.entity.CmdbResIndCmdParse;
import com.sino.bpm.entity.CmdbResTypeIndicators;
import com.sino.bpm.service.CmdbResIndCmdParseService;
import com.sino.bpm.service.CmdbResTypeIndicatorsService;
import com.sino.cmdb.indicator.cmdCheckItems.service.AckDevCheckItemService;
import com.sino.cmdb.indicator.device.entity.DevIndicatorItems;
import com.sino.cmdb.indicator.device.service.DevIndicatorItemsService;
import com.sino.cmdb.indicator.product.service.ProdSnmpIndItemService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.CmdbVendor;
import com.sino.cmdb.vendor.service.CmdbVendorService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @ProjectName: SinoITOM_V2
 * @Package: com.sino.cmdb.indicator.cmdparse.web
 * @ClassName: CmdbResIndCmdParseAction
 * @auther: Mr.Lp
 * @date: 2020/7/1 14:16
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@RequestMapping(value = "/cmdb/indicator/resIndCmdParse")
@Controller
public class CmdbResIndCmdParseAction {

    private String viewPath = "/cmdb/indicator/cmdParse";
    private static Logger logger = LoggerFactory.getLogger(CmdbResIndCmdParseAction.class);

    @Autowired
    private CmdbResIndCmdParseService cmdbResIndCmdParseService;

    @Autowired
    private AckDevCheckItemService ackDevCheckItemService;
    @Autowired
    private ProdSnmpIndItemService prodSnmpIndItemService;

    @Autowired
    private CmdbVendorService cmdbVendorService;

    @Autowired
    private DevIndicatorItemsService devIndicatorItemsService;

    @Autowired
    private CmdbResTypeIndicatorsService cmdbResTypeIndicatorsService;

    @Autowired
    private AckResourcesDao ackResourcesdao;

    @Autowired
    private CmdbResIndCmdParseDao cmdbResIndCmdParseDao;

    @RequestMapping(value = "/getTree.do")
    public String getTree(ModelMap map) {
        logger.info("Enter getTree.do...");
        List<ParamTree> total = new ArrayList<ParamTree>();
        ParamTree t = new ParamTree();
        t.setId("1_1");
        t.setText("设备类型");
        t.setPid("0");
        t.setIsexpand(true);
        total.add(t);

        List<ResItemParam> classCodes = prodSnmpIndItemService.getTreeByVendor();
        if (classCodes != null) {
            for (int i = 0; i < classCodes.size(); i++) {//设备分类  2级节点
                ParamTree pc = new ParamTree();
                ResItemParam paramClass = classCodes.get(i);
                pc.setId(paramClass.getId() + "_2");
                pc.setPid("1_1");
                pc.setText(paramClass.getText());
                total.add(pc);
                List<ResItemParam> typeCodes = prodSnmpIndItemService
                        .getTreeVendor(Integer.parseInt(paramClass.getId()));
                if (!typeCodes.isEmpty()) {
                    for (int j = 0; j < typeCodes.size(); j++) {//设备类型 3级节点
                        ParamTree pt = new ParamTree();
                        ResItemParam paramType = typeCodes.get(j);
                        pt.setId(paramClass.getId() + "_" + paramType.getId() + "_3");
                        pt.setPid(paramClass.getId() + "_2");
                        pt.setText(paramType.getText());
                        total.add(pt);
                        List<ResItemParam> groupNames = prodSnmpIndItemService
                                .getTreeByClassCode(Integer.parseInt(paramType.getId()),
                                        Integer.parseInt(paramClass.getId()));
                        if (!groupNames.isEmpty()) {
                            for (int k = 0; k < groupNames.size(); k++) {//指标组名称 4级节点
                                ParamTree pg = new ParamTree();
                                ResItemParam paramGroup = groupNames.get(k);
                                pg.setId(
                                        paramClass.getId() + "_" + paramType.getId() + "_" + paramGroup.getId() + "_4");
                                pg.setText(paramGroup.getText());
                                pg.setPid(paramClass.getId() + "_" + paramType.getId() + "_3");
                                total.add(pg);
                            }
                        }
                    }
                }
            }
        }

        JSONArray json = JSONArray.fromObject(total);
        String treeData = json.toString();
        map.put("treeData", treeData);
        return viewPath + "/cmdCheckItemsTab";
    }

    @RequestMapping(value = "/main.do")
    public String main(ModelMap map, String id) {
        logger.info("Enter main.do ...");
        List<CmdbResIndCmdParse> list = new ArrayList<CmdbResIndCmdParse>();
        if (!StringUtil.isNullString(id)) {
            if (id.split("_").length == 2) {  //查所有
                if (id.split("_")[0].equals("-1") || id.split("_")[0].equals("1")) {
                    list = cmdbResIndCmdParseService.getAll();
                } else {
                    list = cmdbResIndCmdParseService.getByVerdorID(Integer.parseInt(id.split("_")[0]));
                }

            } else if (id.split("_").length == 3) {
                list = cmdbResIndCmdParseService.getByVerdorIDAndClassCode(Integer.parseInt(id.split("_")[0]),
                        Integer.parseInt(id.split("_")[1]));
            } else if (id.split("_").length == 4) {
                list = cmdbResIndCmdParseService
                        .getByVerdorIDAndClassCodeAndDevType(Integer.parseInt(id.split("_")[0]),
                                Integer.parseInt(id.split("_")[1]), Integer.parseInt(id.split("_")[2]));
            }

            String jsonStr = cmdbResIndCmdParseService.getJsonListStr(list);
            map.put("jsonListData", jsonStr);
            map.put("pid", id);
        }
        return viewPath + "/main";
    }

    //	添加设备指标采集命令与解析规则
    @RequestMapping(value = "/toAdd.do")
    public String toAdd(String pid, ModelMap map) {
        logger.info("Enter add.do ...");
        CmdbResIndCmdParse prodCmdCheckItems = new CmdbResIndCmdParse();
        String flag = pid.substring(pid.lastIndexOf("_") + 1);
        String[] typeCodes = pid.split("_");
        if (flag.equals("2")) {
            prodCmdCheckItems.setVendorID(Integer.parseInt(typeCodes[0]));
        } else if (flag.equals("3")) {
            prodCmdCheckItems.setVendorID(Integer.parseInt(typeCodes[0]));
            prodCmdCheckItems.setResClassCode(Integer.parseInt(typeCodes[1]));
        } else if (flag.equals("4")) {
            prodCmdCheckItems.setVendorID(Integer.parseInt(typeCodes[0]));
            prodCmdCheckItems.setResClassCode(Integer.parseInt(typeCodes[1]));
            prodCmdCheckItems.setResTypeCode(Integer.parseInt(typeCodes[2]));
        }
        map.put("prodCmdCheckItems", prodCmdCheckItems);
        map.put("action", "add");
        return viewPath + "/add";
    }

    @RequestMapping(value = "/saveProdCmdCheckItems.do")
    public void saveProdCmdCheckItems(CmdbResIndCmdParse item, HttpServletResponse response) throws IOException {
        logger.info("Enter "+this.getClass().getName()+".saveProdCmdCheckItems ...");
        boolean result = true;
        if(item!=null){
            Integer resTypeCode = item.getResTypeCode();
            Integer resClassCode = item.getResClassCode();
            Long indItemId = item.getIndItemID();
            if(resTypeCode!=null&&resClassCode!=null&&indItemId!=null){
                CmdbResTypeIndicators cmdbResTypeIndicators = cmdbResTypeIndicatorsService.getByTypeCodeAndClassCodeAndItemId(resTypeCode, resClassCode, indItemId);
                if(cmdbResTypeIndicators!=null){
                    item.setResTypeIndId(cmdbResTypeIndicators.getResTypeIndId());
                }
            }
        }
        try {
            cmdbResIndCmdParseService.save(item);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(result);
    }

    @RequestMapping(value = "/check.do")
    public String check(Long devId, String result, ModelMap map) {
        boolean checkCmdParseRule = ackDevCheckItemService.checkCmdParseRule(devId, result);
        map.put("result", checkCmdParseRule);
        return viewPath + "/prodCmdCheckItems";
    }

    //详情
    @RequestMapping(value = "/detail.do")
    public String detail(Integer id, ModelMap map) {
        logger.info("Enter detail ...");
        map.put("action", "detail");
        CmdbResIndCmdParse prodChkCmdItems = cmdbResIndCmdParseService.getCmdbProdChkCmdItemsById(id);
        Integer vonderId = prodChkCmdItems.getVendorID();
        CmdbVendor cmdbVendor = cmdbVendorService.getCmdbVendorById(Integer.valueOf(vonderId));
        String jsonData = cmdbResIndCmdParseService.getJsonObjStr(prodChkCmdItems);
        map.put("cmdbVendor", cmdbVendor);
        map.put("prodChkCmdItems", prodChkCmdItems);
        map.put("jsonData", jsonData);
        return viewPath + "/view";
    }

    //批量审核
    @RequestMapping(value = "/saveAudit.do")
    public String saveAudit(String checkItemsIds, HttpServletResponse response) throws IOException {

        List<CmdbResIndCmdParse> list = new ArrayList<CmdbResIndCmdParse>();
        if (!checkItemsIds.isEmpty()) {
            String[] ids = checkItemsIds.split(",");
            for (String id : ids) {
                CmdbResIndCmdParse prodChkCmds = cmdbResIndCmdParseService
                        .getCmdbProdChkCmdItemsById(Integer.parseInt(id));
                prodChkCmds.setStatus(1);
                list.add(prodChkCmds);
            }
            cmdbResIndCmdParseService.batchSave(list);
        }
        JSONObject jo = new JSONObject();
        jo.put("success", "0");
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    //另存为
    @RequestMapping(value = "/toSaveAs.do")
    public String toSaveAs(Integer id, ModelMap map) {
        logger.info("Enter toSaveAs ...");

        CmdbResIndCmdParse prodChkCmdItems = cmdbResIndCmdParseService.getCmdbProdChkCmdItemsById(id);
        Integer vonderId = prodChkCmdItems.getVendorID();
        CmdbVendor cmdbVendor = cmdbVendorService.getCmdbVendorById(Integer.valueOf(vonderId));
        String jsonData = cmdbResIndCmdParseService.getJsonObjStr(prodChkCmdItems);
        map.put("cmdbVendor", cmdbVendor);
        map.put("prodChkCmdItems", prodChkCmdItems);
        map.put("jsonData", jsonData);
        return viewPath + "/saveAs";
    }

    @RequestMapping(value = "/saveAs.do")
    public String saveAs(CmdbResIndCmdParse prodChkCmdItems, ModelMap map) {

        if(prodChkCmdItems!=null){
            Integer resTypeCode = prodChkCmdItems.getResTypeCode();
            Integer resClassCode = prodChkCmdItems.getResClassCode();
            Long indItemId = prodChkCmdItems.getIndItemID();
            if(resTypeCode!=null&&resClassCode!=null&&indItemId!=null){
                CmdbResTypeIndicators cmdbResTypeIndicators = cmdbResTypeIndicatorsService.getByTypeCodeAndClassCodeAndItemId(resTypeCode, resClassCode, indItemId);
                if(cmdbResTypeIndicators!=null){
                    prodChkCmdItems.setResTypeIndId(cmdbResTypeIndicators.getResTypeIndId());
                }
            }
        }
        cmdbResIndCmdParseService.save(prodChkCmdItems);
        map.put("action", "saveAs");
        map.put("result", "success");
        return viewPath + "/saveAs";
    }

    @RequestMapping(value = "/delete.do")
    public String delete(String checkItemsIds, ModelMap map, HttpServletResponse response) throws IOException {
        logger.info("Enter delete.do..");
        if (!checkItemsIds.isEmpty()) {
            String[] ids = checkItemsIds.split(",");
            cmdbResIndCmdParseService.deletes(ids);
        }
        JSONObject jo = new JSONObject();
        jo.put("success", "0");
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value = "/getIndGroup.do")
    public String getIndGroup(int resClassCode, int resTypeCode, int indClassCode, HttpServletResponse response)
            throws IOException {
        logger.info("Enter getIndGroup.do");
        List<DevIndicatorItems> indGroupList = devIndicatorItemsService
                .getIndGroupList(resClassCode, resTypeCode, indClassCode);
        String jsonStr = devIndicatorItemsService.getJsonListStr(indGroupList);
        JSONObject jo = new JSONObject();
        jo.put("indGroups", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value = "/getIndItems.do")
    public String getIndItems(int resClassCode, int resTypeCode, int indClassCode, long indGroupID,
                              HttpServletResponse response) throws IOException {
        logger.info("Enter getIndGroup.do");
        List<DevIndicatorItems> indGroupList = devIndicatorItemsService
                .getIndItemList(resClassCode, resTypeCode, indClassCode, indGroupID);
        String jsonStr = devIndicatorItemsService.getJsonListStr(indGroupList);
        JSONObject jo = new JSONObject();
        jo.put("indItems", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    //巡检设备页面使用
    @RequestMapping(value = "/getListByResIDs.do")
    public List<CmdbResIndCmdParse> getListByResIDs(String ids, String vendorID, String resTypeCode, String osType,
                                                     String osVersion, String osFeature, HttpServletResponse response) throws IOException {
        logger.info("Enter getByVendorIDAndModelOID.do...");
        List<Long> idList = new ArrayList<Long>();
        String[] idArray = ids.split(";");
        for (String id : idArray) {
            idList.add(Long.valueOf(id));
        }
        List<CmdbResIndCmdParse> pItems = null;
        if (!StringUtil.isNullString(vendorID)) {//网络设备
            List<AckResources> monResList = ackResourcesdao.getDevicesByIds(idList, Integer.valueOf(vendorID));

            List<String> modelOIDs = new ArrayList<String>();
            for (AckResources res : monResList) {
                modelOIDs.add(res.getModelOID());
            }

            if (idList.size() > 1) {
                pItems = cmdbResIndCmdParseDao.getByVendorIDAndModelOID(vendorID, resTypeCode, modelOIDs, osType, osVersion, osFeature);

                if(pItems!=null){

                }

            } else {  //size等于1
                List<AckDevCheckItems> list = ackDevCheckItemService.getByDevId(idList.get(0));
                List<Long> itemIds = new ArrayList<Long>();
                if (!list.isEmpty()) {
                    for (AckDevCheckItems item : list) {
                        itemIds.add(item.getIndItemID());
                    }
                }

                pItems = cmdbResIndCmdParseDao.getByVendorIDAndModelOID(vendorID, resTypeCode, modelOIDs, osType, osVersion, osFeature,itemIds);


            }

        } else {//服务器设备
            if (idList.size() > 1) {
                pItems = cmdbResIndCmdParseDao.getByOsTypeAndOsVersion(osType, osVersion);

            } else {
                List<AckDevCheckItems> list = ackDevCheckItemService.getByDevId(idList.get(0));
                List<Long> itemIds = new ArrayList<Long>();
                if (!list.isEmpty()) {
                    for (AckDevCheckItems item : list) {
                        itemIds.add(item.getIndItemID());
                    }
                }
                pItems = cmdbResIndCmdParseDao.getByOsTypeAndOsVersion(osType, osVersion, itemIds);


            }

        }
        String jsonStr = cmdbResIndCmdParseService.getJsonListStr(pItems);
        JSONObject jo = new JSONObject();
        jo.put("jsonStr", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value = "/checkResult.do",method = RequestMethod.POST , consumes = "application/json")
    @ResponseBody
    public String checkResult(@RequestBody CmdbResIndCmdParse cmdbResIndCmdParse) {
        logger.info("Enter "+this.getClass().getName()+".checkResult ...");
        return cmdbResIndCmdParseService
                .checkResult(cmdbResIndCmdParse);
    }

    //指标项去重
    public List<CmdbResIndCmdParse> checkChItem(List<CmdbResIndCmdParse> pItem,String osVersion,String osFeature){


        List<CmdbResIndCmdParse> newItemList=new ArrayList<>();//指标现象ID有重复的集合

        Map<Long,List<CmdbResIndCmdParse>> itemMap=new HashMap<>();

        Set<Long> set = new TreeSet<>();
        Set<Long> exist = new TreeSet<>();

        for(CmdbResIndCmdParse item:pItem){
            if (set.contains(item.getIndItemID())) {
                exist.add(item.getIndItemID());
            } else {
                set.add(item.getIndItemID());
            }
        }

        for(CmdbResIndCmdParse item:pItem){
            if(exist.contains(item.getIndItemID())){
                newItemList.add(item);
            }
        }

        //同类指标项 的 osversion osFeature modelOID  汇总
        Map<Long,Set<String>> osVersionMap=new HashMap<>();
        Map<Long,Set<String>> osFeatureMap=new HashMap<>();

        for(Long id:exist){
            for(CmdbResIndCmdParse item:pItem){
                if(item.getIndItemID().longValue()==id){
                    //
                    Set<String> osversionset=osVersionMap.get(id);
                    if(osversionset==null){
                        osversionset=new TreeSet<>();
                    }
                    osversionset.add(item.getOsVersion());
                    osVersionMap.put(id, osversionset);
                    //
                    Set<String> osFeatureset=osFeatureMap.get(id);
                    if(osFeatureset==null){
                        osFeatureset=new TreeSet<>();
                    }
                    osFeatureset.add(item.getOsFeature());
                    osFeatureMap.put(id, osFeatureset);
                }
            }
        }

        for(Long id:exist){
            Set<String> osversionset=osVersionMap.get(id);
            if(osversionset.size()>1){
                if(osversionset.contains("V.R")&&osversionset.contains(osVersion)){
                    Iterator<CmdbResIndCmdParse> it = pItem.iterator();
                    while(it.hasNext()){
                        CmdbResIndCmdParse item=it.next();
                        if(item.getIndItemID()==id&&item.getOsVersion().equals("V.R")){
                            it.remove();
                        }
                    }
                }
            }
            Set<String> osFeatureset=osFeatureMap.get(id);
            if(osFeatureset.size()>1){
                if(osFeatureset.contains("default")&&osFeatureset.contains(osVersion)){
                    Iterator<CmdbResIndCmdParse> it = pItem.iterator();
                    while(it.hasNext()){
                        CmdbResIndCmdParse item=it.next();
                        if(item.getIndItemID()==id&&item.getOsFeature().equals("default")){
                            it.remove();
                        }
                    }
                }
            }
        }
        return pItem;
    }

}
