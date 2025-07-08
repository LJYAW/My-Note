package com.sino.cmdb.indicator.snmpoid.web;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.indicator.device.dao.DevIndicatorItemsDao;
import com.sino.cmdb.indicator.device.entity.DevIndicatorItems;
import com.sino.cmdb.indicator.group.entity.IndicatorGroup;
import com.sino.cmdb.indicator.group.service.IndGroupService;
import com.sino.cmdb.indicator.items.entity.IndicatorItems;
import com.sino.cmdb.indicator.items.service.IndItemsService;
import com.sino.cmdb.indicator.product.entity.ProdSnmpIndItems;
import com.sino.cmdb.indicator.snmpoid.dao.ResIndSnmpOidDao;
import com.sino.cmdb.indicator.snmpoid.entity.ResIndSnmpOid;
import com.sino.cmdb.indicator.snmpoid.service.ResIndSnmpOidService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.monitor.res.dao.MonResourceDao;
import com.sino.monitor.res.entity.MonResource;
import com.sino.monitor.res.entity.ParamSnmpRes;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: SinoITOM_V2
 * @Package: com.sino.cmdb.indicator.snmpoid.web
 * @ClassName: ResIndSnmpOidAction
 * @auther: Mr.Lp
 * @date: 2020/6/8 15:59
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Controller
@RequestMapping(value="/cmdb/snmpOid")
public class ResIndSnmpOidAction {

    private String viewPath = "/cmdb/indicator/snmpOid";
    private static Logger logger = LoggerFactory.getLogger(ResIndSnmpOidAction.class);

    @Autowired
    private ResIndSnmpOidService resIndSnmpOidService;

    @Autowired
    private IndGroupService indGroupService;

    @Autowired
    private IndItemsService indItemsService;

    @Autowired
    private ResItemService resItemService;

    @Autowired
    private DevIndicatorItemsDao devIndicatorItemsDao;

    @Autowired
    private ResIndSnmpOidDao resIndSnmpOidDao;

    @Autowired
    private MonResourceDao monResourceDao;

    @RequestMapping(value="/main.do")
    public String main(ModelMap map,String id){
        logger.info( "Enter main.do ..." );
        List<ResIndSnmpOid> list =new ArrayList<ResIndSnmpOid>();
        String flag=id.substring(id.lastIndexOf("_")+1, id.length());
        if(flag.equals("1")){
            list = resIndSnmpOidService.getAllOrderByVendor();
        }else if(flag.equals("2")){
            String [] typeCodes=id.split("_");
            list = resIndSnmpOidService.getByVendorId(Integer.parseInt(typeCodes[0]));
        }else if(flag.equals("3")){
            String [] typeCodes=id.split("_");
            list = resIndSnmpOidService.getByVendorAndClass(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]));
        }else if(flag.equals("4")){
            String [] typeCodes=id.split("_");
            list = resIndSnmpOidService.getByVendorAndClassAndType(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),Integer.parseInt(typeCodes[2]));
        }

        String jsonStr = resIndSnmpOidService.getJsonListStr(list);
        map.put("jsonListData", jsonStr);
        map.put("pid", id);
        return viewPath+"/main";
    }

    @RequestMapping(value="/getTree.do")
    public String getTree(String menuId,ModelMap map){
        logger.info("Enter getTree.do...");
        List<ParamTree> total = new ArrayList<ParamTree>();
        ParamTree  t=new ParamTree();
        t.setId("1_1");
        t.setText("设备类型");
        t.setPid("0");
        t.setIsexpand(true);
        total.add(t);

        List<ResItemParam> classCodes=resIndSnmpOidService.getTreeByVendor();
        if(classCodes != null){
            for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
                ParamTree pc=new ParamTree();
                ResItemParam paramClass=classCodes.get(i);
                pc.setId(paramClass.getId()+"_2");
                pc.setPid("1_1");
                pc.setText(paramClass.getText());
                total.add(pc);
                List<ResItemParam> typeCodes=resIndSnmpOidService.getTreeVendor(Integer.parseInt(paramClass.getId()));
                if(!typeCodes.isEmpty()){
                    for(int j=0;j<typeCodes.size();j++){//设备类型 3级节点
                        ParamTree pt=new ParamTree();
                        ResItemParam paramType = typeCodes.get(j);
                        pt.setId(paramClass.getId()+"_"+paramType.getId()+"_3");
                        pt.setPid(paramClass.getId()+"_2");
                        pt.setText(paramType.getText());
                        total.add(pt);
                        List<ResItemParam> groupNames=resIndSnmpOidService.getTreeByClassCode(Integer.parseInt(paramType.getId()),Integer.parseInt(paramClass.getId()));
                        if(!groupNames.isEmpty()){
                            for(int k=0;k<groupNames.size();k++){//指标组名称 4级节点
                                ParamTree pg=new ParamTree();
                                ResItemParam paramGroup = groupNames.get(k);
                                pg.setId(paramClass.getId()+"_"+paramType.getId()+"_"+paramGroup.getId()+"_4");
                                pg.setText(paramGroup.getText());
                                pg.setPid(paramClass.getId()+"_"+paramType.getId()+"_3");
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
        WebFuncUtils.titleContent(menuId,map);
        return viewPath + "/productTab";

    }


    @RequestMapping(value="/relationProdIndItems.do")
    public String relationProdIndItems(String devIndItemsIds,ModelMap map){
        map.put("devIndItemsIds", devIndItemsIds);
        return viewPath+"/relationProdIndItems";
    }

    //关联产品厂商
    @RequestMapping(value="/saveRelaProdIndItems.do")
    public String saveRelaProdIndItems(String devIndItemsIds,ResIndSnmpOid resIndSnmpOid,ModelMap map){
        logger.info("Enter saveRelaProdIndItems.do..");
        List<Integer> devIndItemIds=new ArrayList<Integer>();
        List<ResIndSnmpOid> prodIndItemList=new ArrayList<ResIndSnmpOid>();

        String []ids=devIndItemsIds.split(";");
        for(String id:ids){
            devIndItemIds.add(Integer.parseInt(id));
        }

        List<DevIndicatorItems> items=devIndicatorItemsDao.getDataByIds(devIndItemIds);

        for(int i=0;i<items.size();i++){
            ResIndSnmpOid item=new ResIndSnmpOid();

            BeanUtils.copyProperties(resIndSnmpOid, item);
            BeanUtils.copyProperties(items.get(i), item);
            item.setStatus(0);
            prodIndItemList.add(item);
        }
        resIndSnmpOidService.batchSave(prodIndItemList);
        String saveData = resIndSnmpOidService.getJsonListStr(prodIndItemList);
        map.put("saveData", saveData);
        map.put("result", "success");
        return viewPath+"/relationProdIndItems";

    }

    @RequestMapping(value = "/add.do")
    public String add(String pid,ModelMap map) {
        logger.info( "Enter add.do ..." );
        ResIndSnmpOid prodInd = new ResIndSnmpOid();
        String flag=pid.substring(pid.lastIndexOf("_")+1, pid.length());
        String [] typeCodes=pid.split("_");
        if(flag.equals("2")){
            prodInd.setVendorId(Integer.parseInt(typeCodes[0]));
        }else if(flag.equals("3")){
            prodInd.setVendorId(Integer.parseInt(typeCodes[0]));
            prodInd.setResClassCode(Integer.parseInt(typeCodes[1]));
        }else if(flag.equals("4")){
            prodInd.setVendorId(Integer.parseInt(typeCodes[0]));
            prodInd.setResClassCode(Integer.parseInt(typeCodes[1]));
            prodInd.setResTypeCode(Integer.parseInt(typeCodes[2]));
        }
        map.put("prodInd", prodInd);
        map.put("action", "add");
        return viewPath+"/edit";
    }

    @RequestMapping(value="/edit.do")
    public String edit(ModelMap map,String id){
        logger.info("Enter edit.do...");
        ResIndSnmpOid prodInd = resIndSnmpOidService.getById(Integer.parseInt(id));
        map.put("prodInd", prodInd);
        map.put("action", "edit");
        map.put("id", id);
        return viewPath+"/edit";
    }

    /**
     * @auther: Mr.Lp
     * @desc: 指标扩展
     * @data: 2020/6/18
     * @param: [map, id]
     * @return: 
     */
    @RequestMapping(value="/check.do")
    public String check(ModelMap map,String id){
        logger.info("Enter check.do...");
        ResIndSnmpOid prodInd = resIndSnmpOidService.getById(Integer.parseInt(id));
        map.put("prodInd", prodInd);
        map.put("action", "edit");
        map.put("id", id);
        return viewPath+"/check";
    }

    /**
     * @auther: Mr.Lp
     * @desc: 指标扩展保存
     * @data: 2020/6/18
     * @param: [resIndSnmpOid, map, indcheck]
     * @return: 
     */
    @RequestMapping(value="/checkSave.do")
    public String checkSave(ResIndSnmpOid resIndSnmpOid, ModelMap map,String indcheck ){
        logger.info("Enter checkSave.do...");
        boolean validate = true;
        if( validate ){
            ResIndSnmpOid saveResIndSnmpOid= null;
            if(indcheck.equals("0")){
                resIndSnmpOid.setVendorId(-1);
                resIndSnmpOid.setResClassCode(-1);
                resIndSnmpOid.setResClassName("所有分类");
                resIndSnmpOid.setResTypeCode(-1);
                resIndSnmpOid.setResTypeName("所有类型");
                resIndSnmpOid.setProdModelID(-1L);
                resIndSnmpOid.setProdModel("所有型号");
                resIndSnmpOid.setOsType("AnyOS");
                resIndSnmpOid.setOsVersion("V.R");
            }

            saveResIndSnmpOid = resIndSnmpOid;
            String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(resIndSnmpOid.getIndClassCode()));
            saveResIndSnmpOid.setIndClassName(resName);
            resIndSnmpOid.setOsFeature("default");
            saveResIndSnmpOid.setStatus(0);
            resIndSnmpOidService.save(saveResIndSnmpOid);
            String saveData = resIndSnmpOidService.getJsonObjStr(saveResIndSnmpOid);
            map.put("saveData", saveData);
            map.put("result", "success");
        }else{
            map.put("result", "error");
        }
        map.put("prodIndItem", resIndSnmpOid);
        return viewPath+"/check";
    }

    @RequestMapping(value="/detail.do")
    public String detail(ModelMap map,String id){
        logger.info("Enter edit.do...");
        ResIndSnmpOid prodInd = resIndSnmpOidService.getById(Integer.parseInt(id));
        map.put("prodInd", prodInd);
        map.put("action", "edit");
        map.put("id", id);
        return viewPath+"/detail";
    }

    @RequestMapping(value="/delete.do")
    public String delete(String prodIndItemIds,ModelMap map, HttpServletResponse response) throws IOException{
        logger.info("Enter delete.do..");
        if(!prodIndItemIds.isEmpty()){
            String [] ids=prodIndItemIds.split(",");
            resIndSnmpOidService.deletes(ids);
        }
        JSONObject jo = new JSONObject();
        jo.put("success", "0");
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }
    //批量审核
    @RequestMapping(value="/saveAudit.do")
    public String saveAudit(String prodIndItemIds, HttpServletResponse response) throws IOException{

        List<ResIndSnmpOid> list=new ArrayList<ResIndSnmpOid>();
        if(!prodIndItemIds.isEmpty()){
            String [] ids=prodIndItemIds.split(",");
            for(String id:ids){
                ResIndSnmpOid prodChkCmds=resIndSnmpOidService.getById(Integer.parseInt(id));
                prodChkCmds.setStatus(1);
                list.add(prodChkCmds);
            }
            resIndSnmpOidService.batchSave(list);
        }
        JSONObject jo = new JSONObject();
        jo.put("success", "0");
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value = "/batchAudit.do")
    public String batchAudit(String prodIndItemIds, ModelMap map,
                             HttpServletResponse response) throws IOException {
        logger.info("Enter CmdbOperation batchAudit.do...");
        List<Integer> operIds = new ArrayList<Integer>();
        String[] arrOpIds = prodIndItemIds.split(";");
        for (String id : arrOpIds) {
            operIds.add(Integer.parseInt(id));
        }
        resIndSnmpOidService.batchAudit(operIds);
        resIndSnmpOidDao.flush();
        JSONObject jo = new JSONObject();
        jo.put("success", "0");
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value="/search.do")
    public String search(HttpServletRequest resquest, HttpServletResponse response, ModelMap map) throws IOException{
        logger.info( "Enter main.do ..." );
        List<ResIndSnmpOid> list = resIndSnmpOidService.getAll();
        String jsonStr = resIndSnmpOidService.getJsonListStr(list);
        response.getWriter().print(jsonStr);
        return null;
    }

    @RequestMapping(value="/getIndGroup.do")
    public String getIndGroup(String indClassCode,HttpServletResponse response) throws IOException{
        logger.info("Enter getIndGroup.do");
        List<IndicatorGroup> indGroupList=indGroupService.getByIndClassCode(Integer.parseInt(indClassCode));
        String jsonStr=indGroupService.getJsonListStr(indGroupList);
        JSONObject jo = new JSONObject();

        jo.put("indGroups", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }


    @RequestMapping(value="/getIndItems.do")
    public String getIndItems(String indClassCode,String indGroupID,HttpServletResponse response) throws IOException{
        logger.info("Enter getIndItems.do");
        List<IndicatorItems> indItemsList=indItemsService.getByIndClassCodeAndGroupID(Integer.parseInt(indClassCode),Long.parseLong(indGroupID));
        JSONObject jo = new JSONObject();
        //JSONArray indItems = JSONArray.fromObject(indItemsList);
        String jsonString=indItemsService.getJsonListStr(indItemsList);
        jo.put("indItems", jsonString);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }


    @RequestMapping(value="/save.do")
    public String save(String action, String id, ResIndSnmpOid resIndSnmpOid, ModelMap map,String indcheck ){
        logger.info("Enter save.do...");
        boolean validate = true;
        if( validate ){
            ResIndSnmpOid saveResIndSnmpOid= null;
            if(indcheck.equals("0")){
                resIndSnmpOid.setVendorId(-1);
                resIndSnmpOid.setResClassCode(-1);
                resIndSnmpOid.setResClassName("所有分类");
                resIndSnmpOid.setResTypeCode(-1);
                resIndSnmpOid.setResTypeName("所有类型");
                resIndSnmpOid.setProdModelID(-1L);
                resIndSnmpOid.setProdModel("所有型号");
                resIndSnmpOid.setOsType("AnyOS");
                resIndSnmpOid.setOsVersion("V.R");

            }

            if( "add".equals(action)){
                resIndSnmpOid.setOsFeature("default");
                saveResIndSnmpOid = resIndSnmpOid;
                String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(resIndSnmpOid.getIndClassCode()));
                saveResIndSnmpOid.setIndClassName(resName);

            }else if( "edit".equals(action)){
                //	ProdIndItem eidtProdIndItem=prodIndItemService.getById(Integer.parseInt(id));
                saveResIndSnmpOid = resIndSnmpOid;
                saveResIndSnmpOid.setResIndSnmpOid(Integer.parseInt(id));
                String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(resIndSnmpOid.getIndClassCode()));
                saveResIndSnmpOid.setIndClassName(resName);

            }else if( "saveAndContinue".equals(action)){
                resIndSnmpOid.setOsFeature("default");
                saveResIndSnmpOid = resIndSnmpOid;
                String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(resIndSnmpOid.getIndClassCode()));
                saveResIndSnmpOid.setIndClassName(resName);
                ResIndSnmpOid snmpOid = new ResIndSnmpOid();
                snmpOid.setVendorId(resIndSnmpOid.getVendorId());
                snmpOid.setResClassCode(resIndSnmpOid.getResClassCode());
                snmpOid.setResClassName(resIndSnmpOid.getResClassName());
                snmpOid.setResTypeCode(resIndSnmpOid.getResTypeCode());
                snmpOid.setResTypeName(resIndSnmpOid.getResTypeName());
                snmpOid.setProdModelID(resIndSnmpOid.getProdModelID());
                snmpOid.setProdModel(resIndSnmpOid.getProdModel());
                snmpOid.setIndClassCode(resIndSnmpOid.getIndClassCode());
                snmpOid.setIndClassName(resIndSnmpOid.getIndClassName());
                snmpOid.setIndGroupID(resIndSnmpOid.getIndGroupID());
                snmpOid.setIndGroupName(resIndSnmpOid.getIndGroupName());
                snmpOid.setIndItemID(resIndSnmpOid.getIndItemID());
                map.put("prodInd", snmpOid);
                map.put("indcheck", indcheck);
                map.put("action", "saveAndContinue");
            }
            resIndSnmpOidService.save(saveResIndSnmpOid);
            String saveData = resIndSnmpOidService.getJsonObjStr(saveResIndSnmpOid);
            map.put("saveData", saveData);
            map.put("result", "success");
        }else{
            map.put("result", "error");
        }
        map.put("prodIndItem", resIndSnmpOid);
        map.put("action", action);
        return viewPath+"/edit";
    }

    @RequestMapping(value="/getByVendorIDAndModelOID.do")
    public List<ProdSnmpIndItems> getByVendorIDAndModelOID(String jsonStrs,String vendorId,HttpServletResponse response) throws IOException{
        logger.info("Enter getByVendorIDAndModelOID.do...");
        List<ParamSnmpRes> reslist = JsonUtils.getDTOList(jsonStrs,ParamSnmpRes.class);
        List<String> modelOIDs=new ArrayList<String>();
        for(ParamSnmpRes res:reslist){
            modelOIDs.add(res.getModelOID());
        }

        List<ResIndSnmpOid> pItems=resIndSnmpOidDao.getByVendorIDAndModelOID(vendorId, modelOIDs);
        String jsonStr = resIndSnmpOidService.getJsonListStr(pItems);
        JSONObject jo=new JSONObject();
        jo.put("jsonStr", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }




    @RequestMapping(value="/getByResIDs.do")
    public List<ProdSnmpIndItems> getByResIDs(String ids,String vendorID,String resTypeCode,HttpServletResponse response) throws IOException{
        logger.info("Enter getByVendorIDAndModelOID.do...");
        List<Long> idList=new ArrayList<Long>();
        String [] idArray=ids.split(";");
        for(String id:idArray){
            idList.add(Long.valueOf(id));
        }

        List<MonResource> monResList=monResourceDao.getDevicesByIds(idList, Integer.valueOf(vendorID));

        List<String> modelOIDs=new ArrayList<String>();
        for(MonResource res:monResList){
            modelOIDs.add(res.getModelOID());
        }

        List<ResIndSnmpOid> pItems=resIndSnmpOidDao.getByVendorIDAndModelOID(vendorID,resTypeCode, modelOIDs);
        String jsonStr = resIndSnmpOidService.getJsonListStr(pItems);
        JSONObject jo=new JSONObject();
        jo.put("jsonStr", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }
    //巡检设备页面使用
//	@RequestMapping(value="/getListByResIDs.do")
//	public List<ProdSnmpIndItems> getListByResIDs(String ids,String vendorID,String resTypeCode,HttpServletResponse response) throws IOException{
//		logger.info("Enter getByVendorIDAndModelOID.do...");
//		List<Long> idList=new ArrayList<Long>();
//		String [] idArray=ids.split(";");
//		for(String id:idArray){
//			idList.add(Long.valueOf(id));
//		}
//
//		List<AckResources> monResList=ackResourcesdao.getDevicesByIds(idList, Integer.valueOf(vendorID));
//
//		List<String> modelOIDs=new ArrayList<String>();
//		for(AckResources res:monResList){
//			modelOIDs.add(res.getModelOID());
//		}
//
//		List<ProdSnmpIndItems> pItems=prodSnmpIndItemDao.getByVendorIDAndModelOID(vendorID,resTypeCode, modelOIDs);
//		String jsonStr = prodSnmpIndItemService.getJsonListStr(pItems);
//		JSONObject jo=new JSONObject();
//		jo.put("jsonStr", jsonStr);
//		response.setContentType("text/json");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().print(jo.toString());
//	    return null;
//	}



    @RequestMapping(value="/getByResType.do")
    public List<ProdSnmpIndItems> getByResType(String ids,String resType,HttpServletResponse response) throws IOException{
        logger.info("Enter getByVendorIDAndModelOID.do...");
        List<Long> idList=new ArrayList<Long>();
        String [] idArray=ids.split(";");
        for(String id:idArray){
            idList.add(Long.valueOf(id));
        }

        List<ResIndSnmpOid> pItems=resIndSnmpOidService.getByDevTypeCode(resType);
        String jsonStr = resIndSnmpOidService.getJsonListStr(pItems);
        JSONObject jo=new JSONObject();
        jo.put("jsonStr", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value="/getByResTypeAndIndClass.do")
    public List<ProdSnmpIndItems> getByResTypeAndIndClass(String ids,String resType,HttpServletResponse response) throws IOException{
        logger.info("Enter getByVendorIDAndModelOID.do...");
        List<Long> idList=new ArrayList<Long>();
        String [] idArray=ids.split(";");
        for(String id:idArray){
            idList.add(Long.valueOf(id));
        }

        List<ResIndSnmpOid> pItems=resIndSnmpOidService.getByIndClass();
        String jsonStr = resIndSnmpOidService.getJsonListStr(pItems);
        JSONObject jo=new JSONObject();
        jo.put("jsonStr", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }



    @RequestMapping(value="/getIfIndItemsByVendorAndmodelOID.do")
    public List<ProdSnmpIndItems> getIfIndItemsByVendorAndmodelOID(String modelOID,String vendorId,HttpServletResponse response) throws IOException{
        logger.info("Enter getIfIndItemsByVendorAndmodelOID.do...");

        List<ResIndSnmpOid> pItems=resIndSnmpOidService.getIfIndItemsByVendorAndmodelOID(vendorId,modelOID);
        String jsonStr = resIndSnmpOidService.getJsonListStr(pItems);
        JSONObject jo = new JSONObject();
        jo.put("jsonStr", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }

    @RequestMapping(value="/getIfIndItemsByVendorAndmodelOIDAndItemName.do")
    public List<ProdSnmpIndItems> getIfIndItemsByVendorAndmodelOIDAndItemName(String modelOID,String vendorId,HttpServletResponse response) throws IOException{
        logger.info("Enter getIfIndItemsByVendorAndmodelOID.do...");

        List<ResIndSnmpOid> pItems=resIndSnmpOidService.getIfIndItemsByVendorAndmodelOIDAndItemName(vendorId,modelOID);
        String jsonStr = resIndSnmpOidService.getJsonListStr(pItems);
        JSONObject jo = new JSONObject();
        jo.put("jsonStr", jsonStr);
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jo.toString());
        return null;
    }
    @RequestMapping(value = "/getExportExcelSize.do")
    public void  getExportExcelSize(ModelMap map,HttpServletResponse response) throws IOException {
        logger.info( "Enter exportExcel.do ..." );
        List<ResIndSnmpOid> list = resIndSnmpOidService.getAllOrderByVendor();
        response.getWriter().print(list.size());
    }
    @RequestMapping(value = "/exportExcel.do")
    public String  exportExcel(ModelMap map,HttpServletResponse response)  {
        logger.info( "Enter exportExcel.do ..." );
        List<ResIndSnmpOid> list = resIndSnmpOidService.getAllOrderByVendor();
        String[] headers = {"主键ID", "厂商ID", "产品型号ID","产品型号","Model OID","产品分类ID","产品分类名称","产品类型ID","产品类型名称","指标分类编码","指标分类名称","指标组ID","指标组","指标项ID","指标中文名称","指标英文名称","Snmp对象名称","指标对象SnmpOID","SnmpGet、SnmpWalk","OID标志","取值类型","取值长度","取值精度","取值单位","备注","状态","mib文件"};//导出字段名称
        String attr="resIndSnmpOid,vendorId,prodModelID,prodModel,modelOID,resClassCode,resClassName,resTypeCode,resTypeName,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indItemName,indicatorItem,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status,mibFile";//导出映射字段
        String fileName="Cmdb_ResIndSnmpOid_List"+".xls";//excel文件名

        try {
            response.setContentType("octets/stream");
            response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
            OutputStream out = response.getOutputStream();
            ExpExcelUtil.expExcel("指标采集OID", headers, list, attr, out);
            out.close();
        } catch (Exception e) {
            System.out.println("导出excel出错。。。");
            e.printStackTrace();
        }

        return null;
    }
    @RequestMapping(value = "/importGo.do")
    public String importGo(ModelMap map) throws IOException {
        logger.info( "Enter importGo.do ..." );

        return viewPath+"/import";

    }
    @RequestMapping(value = "/importExcel.do")
    public String importExcel(MultipartFile impFile, ModelMap map) throws IOException, SQLException {
        logger.info( "Enter importExcel.do ..." );
        if( impFile.getSize()<=0 ){
            map.put("result", "error");
            map.put("message", "上传文件内容为空！");
        }
        else{
            if( resIndSnmpOidService.importExcel(impFile.getInputStream())){
                map.put("result", "success");
            }
            else{
                map.put("result", "error");
            }
            map.put("message", resIndSnmpOidService.getLastMassage());
        }
        return viewPath+"/import";
    }

}
