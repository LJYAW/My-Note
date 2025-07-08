package com.sino.ack.devCheckItem.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;

import smartlink.controller.NpmConfiguration;

import com.sino.ack.devCheckItem.dao.AckDevCheckItemsDao;
import com.sino.ack.devCheckItem.entity.AckDevCheckItems;
import com.sino.ack.devCheckItem.service.AckDevCheckItemsService;
import com.sino.ack.resources.dao.AckResourcesDao;
import com.sino.ack.resources.entity.AckResources;
import com.sino.ack.resources.service.AckResourcesService;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.system.entity.JqPageBean;
import com.sino.cmdb.indicator.cmdCheckItems.entity.CmdbProdCmdChkItems;
import com.sino.cmdb.product.prodClass.entity.ParamTree;

@RequestMapping(value="/ack/devCheckItems")
@Controller
public class AckDevCheckItemsAction {

	private String viewPath = "/ack/devCheckItems";
	private String viewP = "/ack/resources";
	private static Logger logger= LoggerFactory.getLogger(AckDevCheckItemsAction.class);
	
	@Autowired
	private AckDevCheckItemsService ackDevCheckItemService ;
	
	@Autowired
	private AckResourcesService ackResourcesService;
	
	@Autowired
	private AckDevCheckItemsDao ackDevCheckItemDao ;
	
	@Autowired
	private AckResourcesDao ackResourcesDao;
	
	@RequestMapping(value = "/main.do")
		public String main(String id,ModelMap map){
			logger.info("Enter main.do..");
			map.put("id", id);
			return viewPath+"/devMain";
	}
	
	@RequestMapping(value="/getTree.do")
	public String getTree(ModelMap map,String type){
		logger.info("Enter getTree.do...");
		List<ParamTree> total = new ArrayList<ParamTree>();
		ParamTree  t=new ParamTree();
		t.setId("1_1");
		t.setText("网络设备");
		t.setPid("0");
		t.setIsexpand(true);
		total.add(t);
		List<Integer> typeCodes=ackDevCheckItemDao.getResTypeCodes();
		if(!typeCodes.isEmpty()){
			for(int i=0;i<typeCodes.size();i++){
				ParamTree pt=new ParamTree();
				if(typeCodes.get(i)==2){
					pt.setId(typeCodes.get(i).toString()+"_2");
					pt.setPid("1_1");
					pt.setText("交换机");
				}else if(typeCodes.get(i)==3){
					pt.setId(typeCodes.get(i).toString()+"_2");
					pt.setPid("1_1");
					pt.setText("路由器");
				}
				total.add(pt);
				List<String> resIps=ackDevCheckItemDao.getResIpByTypeCode(typeCodes.get(i));
				if(!resIps.isEmpty()){
					for(int j=0;j<resIps.size();j++){
						ParamTree ip=new ParamTree();
						ip.setId(resIps.get(j)+"_"+typeCodes.get(i).toString()+"_3");
						ip.setText(resIps.get(j));
						ip.setPid(typeCodes.get(i).toString()+"_2");
						total.add(ip);
					}
				}
			}
		}
		
		JSONArray json = JSONArray.fromObject(total);
		String treeData = json.toString();
		//System.out.println("**************" + treeData);
		map.put("treeData", treeData);
		return "/ack/resources/mainTab";
		
		
	}
	
	@RequestMapping(value="/search.do")
	public String search(String id,HttpServletRequest resquest,PageRequest pageRequest, JqPageBean page, HttpServletResponse response,ModelMap map) throws IOException{
		logger.info( "Enter search.do ..." );
		
		pageRequest.setPageNo(page.getPage());
		pageRequest.setPageSize(page.getRows());
		pageRequest.setOrderBy(page.getSidx());
		pageRequest.setOrderDir(page.getSord());
		String jsonStr="{}";
		
		List<AckDevCheckItems> list =new ArrayList<AckDevCheckItems>();
		String flag=id.substring(id.lastIndexOf("_")+1, id.length());
		
		Page<AckDevCheckItems> pages=null;
		
		
		
		if(flag.equals("1")){
			pages=ackDevCheckItemService.findAjaxSearch(pageRequest,null,null,null);
		}else if(flag.equals("2")){
			String [] classCodes=id.split("_");
			 pages=ackDevCheckItemService.findAjaxSearch(pageRequest,Integer.parseInt(classCodes[0]),null,null);
		}else if(flag.equals("3")){
			String [] typeCodes=id.split("_");
			pages=ackDevCheckItemService.findAjaxSearch(pageRequest,Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),null);
		}else if(flag.equals("4")){
			String [] typeCodes=id.split("_");
			pages=ackDevCheckItemService.findAjaxSearch(pageRequest,Integer.parseInt(typeCodes[0]),Integer.parseInt(typeCodes[1]),typeCodes[2]);
		}
		
		jsonStr=ackDevCheckItemService.getJsonPageList(pages);
		
		response.getWriter().print(jsonStr);
	    return null; 
	}
	
	@RequestMapping(value="/indInfoMain.do")
	public String indInfoMain(String id,ModelMap map){
		logger.info("Enter indInfoMain.do..");
		map.put("id", id);
		return viewPath+"/indInfoMain";
	}
	
	@RequestMapping(value="/saveDevCheckItems.do")
	public String saveDevCheckItems(String resIds,String indJsonStrs,ModelMap map){
		logger.info("Enter save.do..");
		List<Long> idList=new ArrayList<Long>();
		indJsonStrs = indJsonStrs.replaceAll("id", "chkItemId");
		String [] idArray=resIds.split(";");
		for(String id:idArray){
			idList.add(Long.valueOf(id));
		}
		List<AckResources> monResList=ackResourcesDao.getDataByIds(idList);
		
		List<CmdbProdCmdChkItems> itemList=JsonUtils.getDTOList(indJsonStrs, CmdbProdCmdChkItems.class);
		
		ackDevCheckItemService.saveResSnmpInd(monResList, itemList);
		
		//通知后台更新业务
		Set<Long> set=new TreeSet<Long>();//认为有重复的项赋值给set
//		for(AckResources mon: monResList){
//			if(mon.getCollector_ID()!=null){
//				set.add(mon.getCollector_ID());
//			}
//		}
		Hashtable items =new Hashtable();
		for(Long cid:set){//重新組裝接口的值
			Hashtable subitems =new Hashtable();
			for(AckResources mon: monResList){
				List<Long> psiids=new ArrayList<Long>();
				for(CmdbProdCmdChkItems psi:itemList){
					psiids.add(new Long(psi.getIndItemID()));
				}
				subitems.put(mon.getMgmtIP(), psiids);
			}
			items.put(cid, subitems);
		}
		
		String result ="";
		boolean isflag=false;
		NpmConfiguration client =new NpmConfiguration();
		isflag=client.updateDeviceMonitorItem(items);
		if(isflag)
			result ="success";
		else
			result ="false";
		
		map.put("result",result);
		return viewP+"/addAckCheckItems";
	}
	
	
	//详情
		@RequestMapping(value = "/view.do")
		public String view(Long id, ModelMap map) {
			logger.info( "Enter detail ..." );
			map.put("action", "detail");
			AckDevCheckItems ackDevCheckItems=ackDevCheckItemService.getById(id);
			AckResources ackResources=ackResourcesService.getByResId(ackDevCheckItems.getDevId());
//			String jsonData=ackDevCheckItemService.getJsonObjStr(ackDevCheckItems);
			map.put("ackDevCheckItems", ackDevCheckItems);
			map.put("ackResources", ackResources);
//			map.put("jsonData", jsonData);
			return viewPath+"/view";
		}
		
		//删除
		@RequestMapping(value="/delete.do")
		public String delete(String devChkItemIds,ModelMap map, HttpServletResponse response) throws IOException{
			logger.info("Enter delete.do..");
			if(!devChkItemIds.isEmpty()){
				String [] ids=devChkItemIds.split(",");
				ackDevCheckItemService.deletes(ids);
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
		public String saveAudit(String devChkItemIds, HttpServletResponse response) throws IOException{
			
			List<AckDevCheckItems> list=new ArrayList<AckDevCheckItems>();
			if(!devChkItemIds.isEmpty()){
				String [] ids=devChkItemIds.split(",");
				for(String id:ids){
					AckDevCheckItems ackDevCheckItems=ackDevCheckItemService.getById(Long.parseLong(id));
					ackDevCheckItems.setStatus(1);
					list.add(ackDevCheckItems);
				}
				ackDevCheckItemService.batchSave(list);
			}
			JSONObject jo = new JSONObject();
			jo.put("success", "0");
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null;
		}
}
