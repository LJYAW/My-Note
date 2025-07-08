package com.sino.cmdb.vendor.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysUserOperation;
import com.sino.cmdb.indicator.cmdCheckItems.service.CmdbProdCmdCheckItemsService;
import com.sino.cmdb.indicator.group.service.IndGroupService;
import com.sino.cmdb.indicator.product.entity.ProdSnmpIndItems;
import com.sino.cmdb.indicator.product.service.ProdSnmpIndItemService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;
import com.sino.cmdb.vendor.entity.CmdbVendor;
import com.sino.cmdb.vendor.entity.VendorOSVersion;
import com.sino.cmdb.vendor.service.CmdbProdChkCmdsService;
import com.sino.cmdb.vendor.service.CmdbVendorService;
import com.sino.cmdb.vendor.service.VendorOSVersionService;

@Controller
@RequestMapping(value="/cmdb/prodChkCmds")
public class CmdbProdChkCmdsAction {
	
	private static Logger logger = LoggerFactory.getLogger(CmdbProdChkCmdsAction.class);
	private String viewPath = "/cmdb/prodChkCmds";
	@Autowired
	private CmdbProdChkCmdsService cmdbProdChkCmdsService;
	@Autowired
	private IndGroupService indicatorService;
	
	@Autowired
	private CmdbProdCmdCheckItemsService cmdbProdCmdCheckItemsService;
	
	@Autowired
	private ProdSnmpIndItemService prodSnmpIndItemService;
	
	@Autowired
	private CmdbVendorService cmdbVendorService;
	
	private static String  titlecontent="";
	
	@RequestMapping(value = "/mainTab.do")
	public String mainTab(ModelMap map) {
		logger.info( "Enter main.do ..." );
		map.put("queryType", "CmdbProdChkCmds");
		return "/cmdb/mainTab";
	}
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map,String title){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("设备类型");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		
		List<ResItemParam> classCodes=cmdbProdChkCmdsService.getTreeByVendor();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> typeCodes=cmdbProdChkCmdsService.getTreeVendor(Integer.parseInt(paramClass.getId()));
				if(!typeCodes.isEmpty()){
					for(int j=0;j<typeCodes.size();j++){//设备类型 3级节点
						ParamTree pt=new ParamTree();
						ResItemParam paramType = typeCodes.get(j);
						pt.setId(paramClass.getId()+"_"+paramType.getId()+"_3");
						pt.setPid(paramClass.getId()+"_2");
						pt.setText(paramType.getText());
						total.add(pt);
						List<ResItemParam> groupNames=cmdbProdChkCmdsService.getTreeByClassCode(Integer.parseInt(paramType.getId()),Integer.parseInt(paramClass.getId()));
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
		if(!StringUtil.isNullString(titlecontent)){
			try {
				titlecontent=new String(title.getBytes("iso-8859-1"), "UTF-8");
				titlecontent=titlecontent.substring(titlecontent.indexOf("：")+1, titlecontent.indexOf("</div>"));
				map.put("titlecontent", titlecontent);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return viewPath + "/prodChkCmdsTab";
	}
	

	@RequestMapping(value = "/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter mainTab.do ..." );
		List<CmdbProdChkCmds> list = new ArrayList<CmdbProdChkCmds>();
		if(!StringUtil.isNullString(id)){
			if(id.split("_").length==2){  //查所有
				if(id.split("_")[0].equals("-1")||id.split("_")[0].equals("1")){
					list = cmdbProdChkCmdsService.getAll();
				}else{
					list = cmdbProdChkCmdsService.getByVerdorID(Integer.parseInt(id.split("_")[0]));
				}
				
			}else if(id.split("_").length==3){
				list = cmdbProdChkCmdsService.getByVerdorIDAndClassCode(Integer.parseInt(id.split("_")[0]),Integer.parseInt(id.split("_")[1]));
			}else if(id.split("_").length==4){
				list = cmdbProdChkCmdsService.getByVerdorIDAndClassCodeAndDevType(Integer.parseInt(id.split("_")[0]),Integer.parseInt(id.split("_")[1]),Integer.parseInt(id.split("_")[2]));
			}
			
			
			String jsonStr = cmdbProdChkCmdsService.getJsonListStr(list);
			map.put("jsonListData", jsonStr);
			map.put("pid", id);
		}
		
		return viewPath+"/main";
	}
	
	
	@RequestMapping(value = "/add.do")
	public String add(String pid,ModelMap map) {
		logger.info( "Enter add.do ..." );
		CmdbProdChkCmds prodChkCmds = new CmdbProdChkCmds();
		String flag=pid.substring(pid.lastIndexOf("_")+1, pid.length());
		String [] typeCodes=pid.split("_");
		if(flag.equals("2")){
			prodChkCmds.setVendorId(Integer.parseInt(typeCodes[0]));
		}else if(flag.equals("3")){
			prodChkCmds.setVendorId(Integer.parseInt(typeCodes[0]));
			prodChkCmds.setDevClassCode(Integer.parseInt(typeCodes[1]));
		}else if(flag.equals("4")){
			prodChkCmds.setVendorId(Integer.parseInt(typeCodes[0]));
			prodChkCmds.setDevClassCode(Integer.parseInt(typeCodes[1]));
			prodChkCmds.setDevTypeCode(Integer.parseInt(typeCodes[2]));
		}
		map.put("prodChkCmds", prodChkCmds);
		map.put("action", "add");
		return viewPath+"/add";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String pid,Integer id,ModelMap map) {
		logger.info( "Enter edit.do ..." );
		CmdbProdChkCmds prodChkCmds = cmdbProdChkCmdsService.getCmdbProdChkCmdsById(id);
		String flag=pid.substring(pid.lastIndexOf("_")+1, pid.length());
		String [] typeCodes=pid.split("_");
		if(flag.equals("2")){
			prodChkCmds.setVendorId(Integer.parseInt(typeCodes[0]));
		}else if(flag.equals("3")){
			prodChkCmds.setVendorId(Integer.parseInt(typeCodes[0]));
			prodChkCmds.setDevClassCode(Integer.parseInt(typeCodes[1]));
		}else if(flag.equals("4")){
			prodChkCmds.setVendorId(Integer.parseInt(typeCodes[0]));
			prodChkCmds.setDevClassCode(Integer.parseInt(typeCodes[1]));
			prodChkCmds.setDevTypeCode(Integer.parseInt(typeCodes[2]));
		}
		map.put("prodChkCmds", prodChkCmds);
		map.put("id", id);
		map.put("action", "edit");
		return viewPath+"/add";
	}
	
	@RequestMapping(value="/save.do")
	public String save(String action, String id, CmdbProdChkCmds prodChkCmds, ModelMap map,String indcheck ){
		logger.info("Enter save.do...");
	
		SysUserOperation oper=new SysUserOperation();
		oper.setFunMenu(titlecontent);
		String flag="success";
		String opera="";
		try {
			if(action.equals("add")){
				cmdbProdChkCmdsService.save(prodChkCmds);
				String saveData = cmdbProdChkCmdsService.getJsonObjStr(prodChkCmds);
				map.put("saveData", saveData);
				map.put("prodChkCmds", prodChkCmds);
				opera="添加巡检命令";
			}else if(action.equals("edit")){
				CmdbProdChkCmds prod=cmdbProdChkCmdsService.getCmdbProdChkCmdsById(Integer.parseInt(id));
				BeanUtils.copyProperties(prodChkCmds, prod);
				cmdbProdChkCmdsService.save(prod);
				opera="修改巡检命令";
			}
		} catch (Exception e) {
			oper.setErrorCause(e.getMessage());
			flag="fail";
		}
		map.put("result", flag);
		map.put("action", action);
		oper.setMessage(flag);
		oper.setOperation(opera);
		SystemUtils.addUserOperLog(oper);
		return viewPath+"/add";
	}
	
	@RequestMapping(value="/saveEdit.do")
	public String savesaveEdit(String action, Integer id, CmdbProdChkCmds prodChkCmds, ModelMap map,String indcheck ){
		logger.info("Enter save.do...");
	
			
//			String saveData = cmdbProdChkCmdsService.getJsonObjStr(prodChkCmds);
//			map.put("saveData", saveData);
			map.put("result", "success");
		
//			map.put("prodChkCmds", prodChkCmds);
			map.put("action", action);
			return viewPath+"/edit";
	}
	
	@RequestMapping(value="/delete.do")
	public String delete(String prodChkCmdsIds,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
		SysUserOperation oper=new SysUserOperation();
		oper.setFunMenu(titlecontent);
		oper.setOperation("删除巡检命令");
		
		try {
			if(!prodChkCmdsIds.isEmpty()){
				String [] ids=prodChkCmdsIds.split(",");
				cmdbProdChkCmdsService.deletes(ids);
			}
		} catch (Exception e) {
			oper.setErrorCause(e.getMessage());
			oper.setMessage("fail");
		}
		
		oper.setMessage("success");
		oper.setSqlSentence("delete from CmdbProdChkCmds where id in ("+prodChkCmdsIds+")");
		SystemUtils.addUserOperLog(oper);
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null; 
	}
	
	//详情
		@RequestMapping(value = "/detail.do")
		public String detail(Integer id, ModelMap map) {
			logger.info( "Enter detail ..." );
			map.put("action", "detail");
			CmdbProdChkCmds prodChkCmds=cmdbProdChkCmdsService.getCmdbProdChkCmdsById(id);
			Integer vonderId=prodChkCmds.getVendorId();   
			CmdbVendor cmdbVendor = cmdbVendorService.getCmdbVendorById(Integer.valueOf(vonderId));			
			String jsonData=cmdbProdChkCmdsService.getJsonObjStr(prodChkCmds);
			map.put("cmdbVendor", cmdbVendor);
			map.put("prodChkCmds", prodChkCmds);
			map.put("jsonData", jsonData);
			return viewPath+"/view";
		}
	
		//审核
		@RequestMapping(value="/audit.do")
		public String verify(Integer id,ModelMap map){
			CmdbProdChkCmds prodChkCmds=cmdbProdChkCmdsService.getCmdbProdChkCmdsById(id);
			map.put("prodChkCmds", prodChkCmds);
			map.put("id", id);
			return viewPath+"/audit";
		}
		
		//批量审核
		@RequestMapping(value="/saveAudit.do")
		public String saveAudit(String prodChkCmdsIds, HttpServletResponse response) throws IOException{
			
			List<CmdbProdChkCmds> list=new ArrayList<CmdbProdChkCmds>();
			if(!prodChkCmdsIds.isEmpty()){
				String [] ids=prodChkCmdsIds.split(",");
				for(String id:ids){
					CmdbProdChkCmds prodChkCmds=cmdbProdChkCmdsService.getCmdbProdChkCmdsById(Integer.parseInt(id));
					prodChkCmds.setStatus(1);
					list.add(prodChkCmds);
				}
				cmdbProdChkCmdsService.batchSave(list);
			}
			JSONObject jo = new JSONObject();
			jo.put("success", "0");
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null;
		}
		
		//获取osFeature
		@RequestMapping(value="/getOsFeature.do")
		public String getOsFeature(Integer vendorId,Integer devClassCode,Integer devTypeCode,String modelOID,String osType,String osVersion,ModelMap map,HttpServletResponse response) throws IOException{
			List<CmdbProdChkCmds> list=cmdbProdChkCmdsService.getOsFeature(vendorId,devClassCode,devTypeCode,modelOID,osType,osVersion);
			JSONObject jo = new JSONObject();
			JSONArray cmdbProdChkCmdsList = JSONArray.fromObject(list);
			jo.put("list", cmdbProdChkCmdsList);
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null;         
		}
		
	// 获取采集命令
	@RequestMapping(value = "/getCheckCmdAndCmdUsage.do")
	public String getCheckCmdAndCmdUsage(Integer vendorId,Integer devClassCode, Integer devTypeCode, String modelOID,String osType, String osVersion,String osFeature, ModelMap map,HttpServletResponse response) throws IOException {
		List<CmdbProdChkCmds> list = cmdbProdChkCmdsService.getCheckCmdAndCmdUsage(vendorId, devClassCode, devTypeCode,modelOID, osType, osVersion,osFeature);
		JSONObject jo = new JSONObject();
		JSONArray cmdbProdChkCmdsList = JSONArray.fromObject(list);
		jo.put("list", cmdbProdChkCmdsList);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
		
}
