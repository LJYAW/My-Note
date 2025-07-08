package com.sino.cmdb.indicator.group.web;

import com.sino.base.common.BaseAction;
import com.sino.base.common.util.ExpExcelUtil;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.indicator.group.entity.IndicatorGroup;
import com.sino.cmdb.indicator.group.service.IndGroupService;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.vendor.entity.CmdbProdChkCmds;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 *
 * @author <a href="mailto:niutl@sino-bridge.com">牛腾龙</a>
 *
 * @version $Revision$
 *
 * @since 2014-7-26
 */
@Controller
@RequestMapping(value = "/cmdb/indicator")
public class IndGroupAction extends BaseAction{
	private String viewPath = "/cmdb/indicator/group";
	private static Logger logger = LoggerFactory.getLogger(IndGroupAction.class);
	@Autowired
	private IndGroupService indicatorService;
	
	@Autowired
	private ResItemService resItemService;
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("监测对象分类");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		List<ResItemParam> classCodes=indicatorService.getAllClassCode();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
			}
		}
		
		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		map.put("treeData", treeData);
		return viewPath + "/groupTab";
		
	}
	
	
	@RequestMapping(value = "/main.do")
	public String mainTab(String menuId,ModelMap map) {
		logger.info( "Enter mainTab.do ..." );
		map.put("queryType", "indicator");
		WebFuncUtils.titleContent(menuId,map);
		return "/cmdb/indicator/main";
	}
	@RequestMapping(value = "/mainCheck.do")
	public String mainCheck(String menuId,ModelMap map) {
		logger.info( "Enter mainTab.do ..." );
		map.put("queryType", "indicator");
		WebFuncUtils.titleContent(menuId,map);
		return "/cmdb/indicator/mainCheck";
	}
	@RequestMapping(value = "/mainType.do")
	public String mainType(String menuId,ModelMap map) {
		logger.info( "Enter mainTab.do ..." );
		map.put("queryType", "indicator");
		WebFuncUtils.titleContent(menuId,map);
		return "/cmdb/indicator/mainType";
	}
	@RequestMapping(value = "/mainTab.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<IndicatorGroup> list = new ArrayList<IndicatorGroup>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			list = indicatorService.getIndicator();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = indicatorService.getByIndClassCode(Integer.parseInt(typeCodes[0]));
			map.put("classCode", Integer.parseInt(typeCodes[0]));
		}
		String jsonStr = indicatorService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		return viewPath+"/grouplist";
	}   
	
	@RequestMapping(value = "/add.do")
	public String add(Integer classCode,ModelMap map) {
		logger.info( "Enter add.do ..." );
		map.put("action", "add");
		IndicatorGroup indicator = new IndicatorGroup();
		map.put("classCode", classCode);
		map.put("indicator", indicator);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/save.do")
	public String save(String action, String id, IndicatorGroup indicatorGroup, ModelMap map) {
		logger.info( "Enter save.do ..." );
		map.put("action", action);
		boolean validate = true;
		if( validate ){
			IndicatorGroup saveGroup= null;
			String saveData ="";
			if( "add".equals(action)){
				saveGroup = indicatorGroup;
				String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(indicatorGroup.getIndClassCode()));
				saveGroup.setStatus(0);
				saveGroup.setIndClassName(resName);
				indicatorService.add(saveGroup);
				saveData = indicatorService.getJsonObjStr(saveGroup);
			}else{
				IndicatorGroup editIndicator = indicatorGroup;
				String  resName=resItemService.findResContent("Cmdb_IndicatorClass", String.valueOf(indicatorGroup.getIndClassCode()));
				editIndicator.setIndClassName(resName);
				editIndicator.setIndGroupID(Long.parseLong(id));		
				indicatorService.save(editIndicator);				
				saveData = indicatorService.getJsonObjStr(indicatorGroup);
			}
			map.put("saveData", saveData);
			map.put("result", "success");
			
		}else{
			map.put("result", "error");			
		}
		return viewPath+"/edit";
	}
	
	@RequestMapping(value = "/edit.do")
	public String edit(String id, ModelMap map) {
		logger.info( "Enter edit.do ..." );
		map.put("action", "edit");
		IndicatorGroup group = indicatorService.getGroup(id);
		if(null == group){
			group = new IndicatorGroup();
		}
		map.put("IndicatorGroup", group);
		map.put("id", id);
		return viewPath+"/edit";
	}
	
	@RequestMapping(value="/delete.do")
	public String delete(String indItemIds,ModelMap map, HttpServletResponse response) throws IOException{
		logger.info("Enter delete.do..");
	    
	    if(!indItemIds.isEmpty()){
			String [] ids=indItemIds.split(",");
			indicatorService.deletes(ids);
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
	public String saveAudit(String groupIds, HttpServletResponse response) throws IOException{
				
		List<IndicatorGroup> list=new ArrayList<IndicatorGroup>();
		if(!groupIds.isEmpty()){
			String [] ids=groupIds.split(",");
			for(String id:ids){
				IndicatorGroup prodChkCmds=indicatorService.getById(Long.parseLong(id));
				prodChkCmds.setStatus(1);
				list.add(prodChkCmds);
			}
			indicatorService.batchSave(list);
		}
		JSONObject jo = new JSONObject();
		jo.put("success", "0");
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
	    return null;
	}
	@RequestMapping(value="/getDataById.do")
	public String getDataById(HttpServletResponse response,String idnitemId) throws IOException{
		logger.info("Enter getDataById.do...");
		IndicatorGroup group=indicatorService.getById(Long.parseLong(idnitemId));
		JSONObject jo=new JSONObject();
		jo.put("group", group);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jo.toString());
		return null;
	}
	@RequestMapping(value = "/exportExcel.do")
	public String  exportExcel(ModelMap map,HttpServletResponse response)  {
		logger.info( "Enter exportExcel.do ..." );
		List<IndicatorGroup> list = indicatorService.getAllIndicatorGroup();
		String[] headers = {"指标组ID", "指标分类编号", "指标分类名称","指标组英文名称","指标组名称","说明"};//导出字段名称
		String attr="indGroupID,indClassCode,indClassName,groupEnName,groupName,description";//导出映射字段
		String fileName="Cmdb_IndicatorGroup_List"+".xls";//excel文件名

		try {
	          response.setContentType("octets/stream");
	          response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "UTF-8"));
	          OutputStream out = response.getOutputStream();  
	          ExpExcelUtil.expExcel("指标组", headers, list, attr, out);
	          
	         
	          out.close();
	           
	          
	      } catch (Exception e) {
	    	  System.out.println("导出excel出错。。。");
	          e.printStackTrace();  
	      }
		
		return null;
	}
	@RequestMapping(value = "/getExportExcelSize.do")
	public void  getExportExcelSize(ModelMap map,HttpServletResponse response) throws IOException {
		logger.info( "Enter exportExcel.do ..." );
		List<IndicatorGroup> list = indicatorService.getAllIndicatorGroup();
		
        response.getWriter().print(list.size());
		
	}
	@RequestMapping(value = "/importGo.do")
	public String importGo(ModelMap map) throws IOException{
		logger.info( "Enter importGo.do ..." );
		
		return viewPath+"/import";
		
	}
	@RequestMapping(value = "/importExcel.do")
	public String importExcel(MultipartFile impFile, ModelMap map) throws IOException{     
		logger.info( "Enter importExcel.do ..." );
		if( impFile.getSize()<=0 ){
			map.put("result", "error");
			map.put("message", "上传文件内容为空！");
		}
		else{
			if( indicatorService.importExcel(impFile.getInputStream())){
				map.put("result", "success");
			}
			else{
				map.put("result", "error");
			}			
			map.put("message", indicatorService.getLastMassage());
		}
		return viewPath+"/import";      
	}

}
