/*
 * 文件名： ProdTypeAction.java
 * 
 * 创建日期： 2014-1-16
 *
 * Copyright(C) 2014, by 蒋祥胜.
 *
 * 原始作者: <a href="mailto:jiangxiangsheng@sino-bridge.com">蒋祥胜</a>
 *
 */
package com.sino.cmdb.product.prodDirectory.web;

import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.service.ResItemService;
import com.sino.cmdb.product.prodClass.dao.ProdClassDao;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodClass.service.ProdClassService;
import com.sino.cmdb.product.prodDirectory.entity.ProdDirectory;
import com.sino.cmdb.product.prodDirectory.service.ProdDirectoryService;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.cmdb.product.prodType.service.ProdTypeService;
import com.sino.cmdb.product.prodType.web.ProdTypeAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author <a href="mailto:jiangxs@sino-bridge.com">赵琛</a>
 *
 * @version $Revision: 1.3 $
 *
 * @since 2017年11月08日16:30:19
 */
@Controller
@RequestMapping(value = "/cmdb/vendorProdDirectory")
public class ProdDirectoryAction {
	
	private static Logger logger = LoggerFactory.getLogger(ProdTypeAction.class);
	private static String viewPath = "/cmdb/prodDirectory";
	
	@Autowired
	private ProdDirectoryService prodDirectoryService;

	@Autowired
	private ProdTypeService prodTypeService;
	
	@Autowired
	private ProdClassDao prodClassDao;
	
	@Autowired
	private ProdClassService prodClassService;
	
	@Autowired
	private ResItemService resItemService;

	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("产品系列");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);

		List<ResItemParam> classCodes=prodDirectoryService.getVendor();
		if(classCodes != null){
			for(int i=0;i<classCodes.size();i++){//设备分类  2级节点
				ParamTree pc=new ParamTree();
				ResItemParam paramClass=classCodes.get(i);
				pc.setId(paramClass.getId()+"_2");
				pc.setPid("1_1");
				pc.setText(paramClass.getText());
				total.add(pc);
				List<ResItemParam> typeCodes=prodDirectoryService.getByVendor(Integer.parseInt(paramClass.getId()));
				if(!typeCodes.isEmpty()){
					for(int j=0;j<typeCodes.size();j++){//设备类型 3级节点
						ParamTree pt=new ParamTree();
						ResItemParam paramType = typeCodes.get(j);
						pt.setId(paramClass.getId()+"_"+paramType.getId()+"_3");
						pt.setPid(paramClass.getId()+"_2");
						pt.setText(paramType.getText());
						total.add(pt);
						List<ResItemParam> groupNames=prodDirectoryService.getByClassCode(Integer.parseInt(paramClass.getId()),Integer.parseInt(paramType.getId()));
						if(!groupNames.isEmpty()){
							for(int k=0;k<groupNames.size();k++){//指标组名称 4级节点
								ParamTree pg=new ParamTree();
								ResItemParam paramGroup = groupNames.get(k);
								pg.setId(paramClass.getId()+"_"+paramType.getId()+"_"+paramGroup.getId()+"_4");
								pg.setText(paramGroup.getText());
								pg.setPid(paramClass.getId()+"_"+paramType.getId()+"_3");
								total.add(pg);
								List<ResItemParam> seriesNames=prodDirectoryService.getBySeries(Integer.parseInt(paramClass.getId()),Integer.parseInt(paramType.getId()),Integer.parseInt(paramGroup.getId()));
								if(!seriesNames.isEmpty()){
									for (int l = 0; l < seriesNames.size(); l++) {
										ParamTree sg=new ParamTree();
										ResItemParam paramSeries = seriesNames.get(l);
										sg.setId(paramClass.getId()+"_"+paramType.getId()+"_"+paramGroup.getId()+"_"+paramSeries.getId()+"_5");
										sg.setText(paramSeries.getText());
										sg.setPid(paramClass.getId()+"_"+paramType.getId()+"_"+paramGroup.getId()+"_4");
										total.add(sg);
									}
								}
							}
						}
					}
				}
			}
		}

		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		map.put("treeData", treeData);
		return viewPath+"/prodDirectoryTab";
	}

	@RequestMapping(value="/main.do")
	public String main(ModelMap map,String id) {
		logger.info( "Enter main.do ..." );
		List<ProdDirectory> list = new ArrayList<ProdDirectory>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		if(flag.equals("1")){
			list = prodDirectoryService.getAll();
		}else if(flag.equals("2")){
			String [] typeCodes=id.split("_");
			list = prodDirectoryService.getDataByVendorID(Integer.parseInt(typeCodes[0]));
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			list = prodDirectoryService.getByVendorAndClassCode(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]));
		}else if(flag.equals("4")){
			String [] typeCodes=id.split("_");
			list = prodDirectoryService.getByVendorAndClassCodeAndTypeCode(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),Integer.parseInt(typeCodes[2]));
		}else if(flag.equals("5")){
			String [] typeCodes=id.split("_");
			list = prodDirectoryService.getByVenAndClassAndTypeAndSeries(Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),Integer.parseInt(typeCodes[2]),typeCodes[3]);
		}

		String jsonStr = prodDirectoryService.getJsonListStr(list);
		map.put("jsonListData", jsonStr);
		map.put("id", id);
		return viewPath+"/main";
	}

	@RequestMapping(value="/add.do")
	public String add(ModelMap map,String id){
		logger.info("Enter add.do..");
		ProdDirectory prodDirectory=new ProdDirectory();
		if(!StringUtils.isNotBlank(id) && !"all".equals(id)){
			String flag=id.substring(id.lastIndexOf("_")+1, id.length());
			String [] typeCodes=id.split("_");
			if(flag.equals("2")){
				prodDirectory.setVendorID(typeCodes[0]);
			}else if(flag.equals("3")){
				prodDirectory.setVendorID(typeCodes[0]);
				prodDirectory.setProdClassCode(Integer.parseInt(typeCodes[1]));
			}else if(flag.equals("4")||flag.equals("5")){
				prodDirectory.setVendorID(typeCodes[0]);
				prodDirectory.setProdClassCode(Integer.parseInt(typeCodes[1]));
				prodDirectory.setProdTypeCode(Integer.parseInt(typeCodes[2]));
			}
		}
		map.put("prodDirectory", prodDirectory);
		map.put("action", "add");
		return viewPath+"/edit";
	}

	@RequestMapping(value="/edit.do")
	public String edit(String id,ModelMap map){
		try {
			logger.info("Enter edit.do...");
			ProdDirectory prodDirectory=prodDirectoryService.getById(id);
			map.put("action", "edit");
			map.put("id", id);
			map.put("prodDirectory", prodDirectory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewPath+"/edit";
	}


	@RequestMapping(value = "save.do")
	public String save(ProdDirectory prodDirectory , String action , HttpServletResponse response,ModelMap map){

		logger.info("Enter ProdirectoryACtion save.do ...");
		try {
			map.put("action",action);
			ProdDirectory newProDirectory = null;
			if(action.equals("add")){
                newProDirectory = prodDirectory;
                prodDirectoryService.add(newProDirectory);
                String saveData = prodDirectoryService.getJsonObjStr(newProDirectory);
                map.put("saveData", saveData);
                map.put("result","success");
            }else{
    			newProDirectory = prodDirectory;
				prodDirectoryService.save(newProDirectory);
				String saveData = prodDirectoryService.getJsonObjStr(newProDirectory);
				map.put("saveData", saveData);
				map.put("result","success");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return viewPath+"/edit";
	}

	@RequestMapping(value="/delete.do")
	public void delete(String id,HttpServletResponse response) throws IOException {
		logger.info("Enter delete.do...");
		try {
			String [] ids=id.split(",");
			for(int i=0;i<ids.length;i++){
                prodDirectoryService.delete(ids[i]);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(JsonUtils.JSON_RESULT_SUCCESS);
	}

	@RequestMapping(value = "checkProdNo.do")
	public String checkProdNo(HttpServletResponse response , String productNo){
		logger.info("Enter checkProdModel.do...");
		try {
			List<ProdDirectory> prodDirectories=prodDirectoryService.getProductByNo(productNo);
			JSONObject jo=new JSONObject();
			jo.put("prodDirectories", prodDirectories);
			response.getWriter().print(jo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
