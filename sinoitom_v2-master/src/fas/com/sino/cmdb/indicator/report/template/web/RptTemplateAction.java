/**
 * 
 */
package com.sino.cmdb.indicator.report.template.web;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sino.ack.task.entity.AckTask;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.cmdb.indicator.items.entity.IndicatorFields;
import com.sino.cmdb.indicator.items.entity.IndicatorItems;
import com.sino.cmdb.indicator.items.entity.ParamIndicatorFields;
import com.sino.cmdb.indicator.items.service.IndItemsService;
import com.sino.cmdb.indicator.items.service.IndicatorFieldsService;
import com.sino.cmdb.indicator.items.service.ParamIndicatorFieldsService;
import com.sino.cmdb.indicator.report.template.entity.RptTemplate;
import com.sino.cmdb.indicator.report.template.entity.RptTmplFieldsCell;
import com.sino.cmdb.indicator.report.template.service.RptTemplateService;
import com.sino.cmdb.indicator.report.template.service.RptTmplFieldsCellService;
import com.sino.cmdb.indicator.report.tmplcell.entity.RptTmplCell;
import com.sino.cmdb.indicator.report.tmplcell.service.RptTmplCellService;

/**
 * @author Mr.LP
 * @date 2019-7-22下午5:59:59
 * @className RptTemplateAction
 *
 * @Description TODO
 *
 */

@RequestMapping(value="/cmdb/indicator/report")
@Controller
public class RptTemplateAction {
	
	private String viewPath = "/ack/rptTemplate";
	private static Logger logger= LoggerFactory.getLogger(RptTemplateAction.class);
	
	@Autowired
	private RptTemplateService rptTemplateService;
	
	@Autowired
	private RptTmplCellService rptTmplCellService;
	
	@Autowired
	private IndItemsService indItemsService;
	
	@Autowired
	private IndicatorFieldsService indicatorFieldsService;
	
	@Autowired
	private ParamIndicatorFieldsService paramIndicatorFieldsService;
	
	@Autowired
	private RptTmplFieldsCellService rptTmplFieldsCellService;
	
	@RequestMapping(value = "/main.do")
	public String main(String menuId,ModelMap map) {
		
		logger.info( "Enter main.do ..." );
		List<RptTemplate> rptTemplate=rptTemplateService.getAll();
		String jsonStr = rptTemplateService.getJsonListStr(rptTemplate);
		map.put("jsonListData", jsonStr);
		WebFuncUtils.titleContent(menuId,map);
		return viewPath+"/main";
	}
	
	//报告模板----添加
		@RequestMapping(value = "/add.do")
		public String add(ModelMap map) {
			logger.info( "Enter add.do ..." );
			RptTemplate rptTemplate =new RptTemplate();
			map.put("rptTemplate", rptTemplate);
			map.put("action", "add");
			map.put("id", "");
			return viewPath+"/add";
		}
		
		/**
		 * 判断模板名称是否重复
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/getTmplName.do")
		public String getTmplName(HttpServletResponse response) throws IOException{
			logger.info("Enter getTmplName.do..");
			List<RptTemplate> rptTemplate=rptTemplateService.getAll();
			String jsonStr = rptTemplateService.getJsonListStr(rptTemplate);
		    response.getWriter().print(jsonStr);
			return null;
		}
		
		/**
		 * 报告模板添加后保存
		 * @param action
		 * @param id
		 * @param rptTemplate
		 * @param map
		 * @return
		 */
		@RequestMapping(value = "/save.do")
		public String save(String action ,String id,RptTemplate rptTemplate, ModelMap map) {
			logger.info( "Enter save.do ..." );
			if("add".equals(action)){
				if(rptTemplate!=null){
					rptTemplate.setStatus(0);
					rptTemplateService.save(rptTemplate);
				}
			}else{
				System.out.println(rptTemplate);
				RptTemplate template =new RptTemplate();
				template = rptTemplateService.getById(Integer.parseInt(id));
				BeanUtils.copyProperties(rptTemplate, template);
				rptTemplateService.save(template);
			}
			
			map.put("result", "success");
			return viewPath+"/add";
		}
		
		@RequestMapping(value = "/edit.do")
		public String edit(int id, ModelMap map) {
			logger.info( "Enter edit.do ..." );
			RptTemplate rptTemplate = rptTemplateService.getById(id);
			int tdkey = 0 ;
			int tdvalue = 0 ;
			int tdheight = 0 ;
			
			List<RptTmplCell> tmplCellList = rptTmplCellService.getByTmplId(id);
			List<String> slist = new ArrayList<String>();
			List<String> typelist = new ArrayList<String>();
			Set<Integer> set = new HashSet<Integer>();
			for (int i = 0; i < tmplCellList.size(); i++) {
				set.add(tmplCellList.get(i).getRowNo());
				slist.add(tmplCellList.get(i).getCellDispName()+",");
				typelist.add(tmplCellList.get(i).getValueType()+",");
				tdkey = tmplCellList.get(i).getKeyWidth();
				tdvalue = tmplCellList.get(i).getValueWidth();
				tdheight = tmplCellList.get(i).getHeight();
			}
			map.put("row", set.size());
			map.put("col", tmplCellList.size() / set.size());
			map.put("rptTemplate", rptTemplate);
			map.put("slist", JSONArray.fromObject(slist).toString());
			map.put("typelist", JSONArray.fromObject(typelist).toString());
			map.put("id", id);
			map.put("tdkey", tdkey);
			map.put("tdvalue", tdvalue);
			map.put("tdheight", tdheight);
			
			List<RptTmplFieldsCell> fieldsCell = rptTmplFieldsCellService.getByTmplId(id);
			if(fieldsCell!=null&&fieldsCell.size()>0){
				String action = "fields";
				map.put("action", action);
				IndicatorItems indItems = indItemsService.getById(fieldsCell.get(0).getIndItemID());
				map.put("indItems", indItems);
				int height = fieldsCell.get(0).getHeight();
				map.put("height", height);
			}
			
			return viewPath+"/editCell";
		}
		
		
		/**
		 * 审核
		 * @param id
		 * @param map
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/audit.do")
		public String audit(String id,ModelMap map, HttpServletResponse response) throws IOException{
			logger.info("Enter audit.do..");
			JSONObject jo = null;
			if(!id.isEmpty()){
				RptTemplate template = new RptTemplate();
				template = rptTemplateService.getById(Integer.parseInt(id));
				template.setStatus(1);
				rptTemplateService.save(template);
				jo = new JSONObject();
				jo.put("success", "0");
			}
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null; 
		}
		
		/**
		 * 模板删除功能（级联删除，删除模板的同时删除Rpt_TmplCell表对应的模板样式以及表Rpt_TmplFieldsCell）
		 * @param id
		 * @param map
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/delete.do")
		public String delete(String id,ModelMap map, HttpServletResponse response) throws IOException{
			logger.info("Enter delete.do..");
			JSONObject jo = null;
			if(!id.isEmpty()){
				rptTemplateService.batchDelete(Integer.parseInt(id));
				jo = new JSONObject();
				jo.put("success", "0");
			}
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null; 
		}
		
		/**
		 * 检查模板是否设置样式
		 * @param id
		 * @param map
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/checkTmplCell.do")
		public String checkTmplCell(String id,ModelMap map, HttpServletResponse response) throws IOException{
			logger.info("Enter checkTmplCell.do..");
			JSONObject jo = new JSONObject();
			if(!id.isEmpty()){
				List<RptTmplCell> tmplCellList = rptTmplCellService.getByTmplId(Integer.parseInt(id));
				if(tmplCellList!=null&&tmplCellList.size()>0){
					jo.put("success", "0");
				}else{
					jo.put("no", "no");
				}
				
			}
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jo.toString());
		    return null; 
		}
		
		/**
		 * 跳转“模板样式页面效果展示”页面
		 * @param id
		 * @param map
		 * @return
		 */
		@RequestMapping(value = "/showTable.do")
		public String showTable(int id, ModelMap map) {
			logger.info( "Enter showTable.do ..." );
			map.put("id", id);
			return viewPath+"/table";
		}
		
		/**
		 * 模板样式页面效果展示
		 * @param map
		 * @param id
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/showTableView.do")
	    public String showTableView(ModelMap map,String id,HttpServletResponse response) throws IOException{
	        logger.info( "Enter showTableView.do ..." );

	        String tableView = this.getTableViewHtml(id);
	        
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(tableView);
	        return null;
	    }
		
		/**
		 * 模板样式"页面效果展示"时生成的table
		 * @param id
		 * @return
		 * @throws IOException
		 */
		private String getTableViewHtml(String id) throws IOException {
			
			List<RptTmplCell> rptList = rptTmplCellService.getByTmplId(Integer.parseInt(id));
			
			List<RptTmplFieldsCell> fieldsCellList = rptTmplFieldsCellService.getByTmplId(Integer.parseInt(id));
			
			RptTemplate template = new RptTemplate();
			template = rptTemplateService.getById(Integer.parseInt(id));
			
			StringBuffer sb = new StringBuffer();

	        // 创建开头
	        sb.append("<table align=\"center\" style=\"border-collapse:collapse;border:0;word-wrap:break-word;word-break:break-all;\"> \r\n");	//width:900px;
	        
//	        sb.append("<div align=\"left\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
//		    sb.append("<h5 align=\"right\" style=\"width:").append(wid1).append("px;\">指标项表格样式:</h5> \r\n");
//		    sb.append("</div> \r\n");
		    
		    sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");
	        sb.append("<td align=\"left\" > ").append("指标项表格样式:").append("</td> \r\n");
	        sb.append("</tr> \r\n");
	        
	        int rowNo = 0 ;
	    	int colNo = 0 ;
	        for(int i=0;i<rptList.size()-1;i++){
	        	
	        	if(rptList.get(i+1).getRowNo()>rowNo){
	        		rowNo = rptList.get(i+1).getRowNo();
	        	}
	        	if(rptList.get(i+1).getColNo()>colNo){
	        		colNo = rptList.get(i+1).getColNo();
	        	}
	        }
	        
	        int all = rptList.size();	//key的条数		8
	        int col = colNo ;
	        if(colNo%2>0){
	        	col = colNo+1;
	        }
			int avg = all % (col/2);
			
	        for (int i=0; i< rowNo;i++) {
	        	sb.append("<tr> \r\n");
				for (int j=0; j< col/2;j++) {
					if(i==rowNo-1){
						if(avg!=0&&j==avg){
							j=col/2;
							continue;
						}
						
					}
					System.out.print("　　"+rptList.get((col/2)*i+j).getCellDispName());
					//******key****
					if(rptList.get((col/2)*i+j).getIsBold()==1){
						sb.append("<td align=\"").append(rptList.get((col/2)*i+j).getAlign()).append("\" style=\"width:").append(rptList.get((col/2)*i+j).getKeyWidth()).append("px;").append("height:").append(rptList.get((col/2)*i+j).getHeight()).append("px;font-size:").append(rptList.get((col/2)*i+j).getSize()).append("px;color:").append(rptList.get((col/2)*i+j).getColor()).append(";font-weight:bold;font-family:").append(rptList.get((col/2)*i+j).getFont()).append(";border:1px solid #000;\">").append(rptList.get((col/2)*i+j).getCellDispName()).append(":").append("</td> \r\n");
					}else{
						sb.append("<td align=\"").append(rptList.get((col/2)*i+j).getAlign()).append("\" style=\"width:").append(rptList.get((col/2)*i+j).getKeyWidth()).append("px;").append("height:").append(rptList.get((col/2)*i+j).getHeight()).append("px;font-size:").append(rptList.get((col/2)*i+j).getSize()).append("px;color:").append(rptList.get((col/2)*i+j).getColor()).append(";font-family:").append(rptList.get((col/2)*i+j).getFont()).append(";border:1px solid #000;\">").append(rptList.get((col/2)*i+j).getCellDispName()).append(":").append("</td> \r\n");
					}
					//******key****
					
					//******value****
//					if(rptList.get(2*j+1).getIsBold()==1){
//						sb.append("<td align=\"").append(rptList.get((col/2)*i+j+1).getAlign()).append("\" style=\"width:").append(rptList.get((col/2)*i+j+1).getWidth()).append("px;").append("height:").append(rptList.get((col/2)*i+j+1).getHeight()).append("px;font-size:").append(rptList.get((col/2)*i+j+1).getSize()).append("px;color:").append(rptList.get((col/2)*i+j+1).getColor()).append(";font-weight:bold;font-family:").append(rptList.get((col/2)*i+j).getFont()).append(";border:1px solid #000;\">").append(rptList.get((col/2)*i+j+1).getCellDispName()).append("</td> \r\n");
//					}else{
						sb.append("<td align=\"").append("left").append("\" style=\"width:").append(rptList.get((col/2)*i+j).getValueWidth()).append("px;").append("height:").append(rptList.get((col/2)*i+j).getHeight()).append("px;font-size:").append(rptList.get((col/2)*i+j).getSize()).append("px;color:").append(rptList.get((col/2)*i+j).getColor()).append(";font-family:").append(rptList.get((col/2)*i+j).getFont()).append(";border:1px solid #000;\">").append("value"+i+j).append("</td> \r\n");
//					}
					//******value****
					
				}
				sb.append("</tr> \r\n");
			}
	        sb.append("</table> \r\n");
	        
			int wid = 0;	//列表模板手动添加的列宽
			
			if(fieldsCellList!=null&&fieldsCellList.size()>0){
	        	for (int i = 0; i < fieldsCellList.size(); i++) {
	        		wid = wid+fieldsCellList.get(i).getTableWidth();//列表模板手动添加的总列宽
				}
	        }
	        
	        DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
			
	        if(fieldsCellList!=null&&fieldsCellList.size()>0){
	        	IndicatorItems indItems = indItemsService.getById(fieldsCellList.get(0).getIndItemID());
	        	
		        sb.append("<table align=\"center\" style=\"border-collapse:collapse;border:0;word-wrap:break-word;word-break:break-all;\"> \r\n");	//width:900px;
		        
//		        sb.append("<div align=\"left\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
//			    sb.append("<h5 align=\"center\" > \r\n").append(indItems.getIndItemName()).append("</h5> \r\n");
//			    sb.append("</div> \r\n");
			    
			    sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");
		        sb.append("<td style=\"font-weight:bold;border:1px solid #000;\" colspan=\" ").append(fieldsCellList.size()).append(" \">").append(indItems.getIndItemName()).append("</td> \r\n");
		        sb.append("</tr> \r\n");		    
		    
		    	int width = template.getTableWidth();	//table的总宽度
		        
		    	sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
		    	for (int i = 0; i < fieldsCellList.size(); i++) {
		    		String cylinders = df.format((float)(fieldsCellList.get(i).getTableWidth())/wid*width);//返回的是String类型 
		    		cylinders = cylinders.substring(0,cylinders.indexOf("."));
		    		sb.append("<td nowrap=\"nowrap\" style=\"width:").append(Integer.parseInt(cylinders)).append("px;").append("height:").append(fieldsCellList.get(i).getHeight()).append("px;font-size:").append(fieldsCellList.get(i).getSize()).append("px;color:").append(fieldsCellList.get(i).getColor()).append(";font-family:").append(fieldsCellList.get(i).getFont()).append(";border:1px solid #000;\">").append(fieldsCellList.get(i).getFieldName()).append("</td> \r\n");
		    	}
			    sb.append("</tr> \r\n");
			    
			    sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
		    	for (int i = 0; i < fieldsCellList.size(); i++) {
		    		String cylinders = df.format((float)(fieldsCellList.get(i).getTableWidth())/wid*width);//返回的是String类型 
		    		cylinders = cylinders.substring(0,cylinders.indexOf("."));
		    		sb.append("<td style=\"width:").append(Integer.parseInt(cylinders)).append("px;").append("height:").append(fieldsCellList.get(i).getHeight()).append("px;font-size:").append(fieldsCellList.get(i).getSize()).append("px;color:").append(fieldsCellList.get(i).getColor()).append(";font-family:").append(fieldsCellList.get(i).getFont()).append(";border:1px solid #000;\">").append("value"+i).append("</td> \r\n");
		    	}
			    sb.append("</tr> \r\n");
			    
			    for (int j = 0; j < 2; j++) {
			    	sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
			    	for (int i = 0; i < fieldsCellList.size(); i++) {
			    		String cylinders = df.format((float)(fieldsCellList.get(i).getTableWidth())/wid*width);//返回的是String类型 
			    		cylinders = cylinders.substring(0,cylinders.indexOf("."));
			    		sb.append("<td style=\"width:").append(Integer.parseInt(cylinders)).append("px;").append("height:").append(fieldsCellList.get(i).getHeight()).append("px;font-size:").append(fieldsCellList.get(i).getSize()).append("px;color:").append(fieldsCellList.get(i).getColor()).append(";font-family:").append(fieldsCellList.get(i).getFont()).append(";border:1px solid #000;\">").append("...").append("</td> \r\n");
			    	}
				    sb.append("</tr> \r\n");
				}
			    
			    sb.append("</table> \r\n");
		    }
		    
	        
	        StringBuffer sb2 = new StringBuffer();
	        sb2.append("<html> \r\n");
	        sb2.append("<head> \r\n");
	        sb2.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> \r\n");
	        sb2.append("<title>模板展示</title> \r\n");
	        sb2.append("<style type=\"text/css\"> \r\n");
	        sb2.append("@page { \r\n");
	        sb2.append("size: 950px 200px; \r\n");
	        sb2.append("} \r\n");
	        sb2.append("</style> \r\n");
	        sb2.append("</head> \r\n");
	        sb2.append("<body style=\"font-family:SimSun;font-size:22px;\"> \r\n");  //字体倾斜：font-style:italic;
//	        sb.append("<div align=\"center\" style=\"font-size:25px; margin-left:auto; margin-right:auto; margin-top:20px; margin-bottom:20px;\">任务报告</div> \r\n");
	        sb2.append("<div align=\"center\" >").append("<h2>").append(template.getTabTitle()).append("</h2>").append("</div> \r\n");	//模板展示
	        sb2.append(sb.toString());
	        sb2.append("</body> \r\n");
	        sb2.append("</html> \r\n");

	        
	        return sb2.toString();
		}
		
		
		@RequestMapping(value = "/editStyle.do")
		public String editStyle(ModelMap map,String id) {
			logger.info( "Enter editStyle.do ..." );
			
			List<RptTmplCell> tmplCellList = rptTmplCellService.getByTmplId(Integer.parseInt(id));
			List<String> slist = new ArrayList<String>();
			Set<Integer> set = new HashSet<Integer>();
			for (int i = 0; i < tmplCellList.size(); i++) {
				set.add(tmplCellList.get(i).getRowNo());
				slist.add(tmplCellList.get(i).getCellDispName()+",");
			}
			List<RptTmplFieldsCell> fieldsCell = rptTmplFieldsCellService.getByTmplId(Integer.parseInt(id));
			if(fieldsCell!=null&&fieldsCell.size()>0){
				String action = "fields";
				map.put("action", action);
			}
			
			map.put("id", id);
			map.put("row", set.size());
			map.put("col", tmplCellList.size() / set.size());
			return viewPath+"/editStyle";
		}
		
		@RequestMapping(value = "/getCellData.do")
		public String getCellData(String id,HttpServletResponse response) throws IOException{
			List<RptTmplCell> list=new ArrayList<RptTmplCell>();
			String jsonStr;
			
			list = rptTmplCellService.getByTmplId(Integer.parseInt(id));
			
			jsonStr=rptTmplCellService.getJsonListStr(list);
			response.getWriter().print(jsonStr);
			return null;
		}
		
		
		@RequestMapping(value = "/getFieldsCellData.do")
		public String getFieldsCellData(String id,HttpServletResponse response) throws IOException{
			List<RptTmplFieldsCell> list=new ArrayList<RptTmplFieldsCell>();
			String jsonStr;
			
			list = rptTmplFieldsCellService.getByTmplId(Integer.parseInt(id));
			
			jsonStr=rptTmplFieldsCellService.getJsonListStr(list);
			response.getWriter().print(jsonStr);
			return null;
		}
		
		
		/**
		 * 跳转“模板样式设置”页面
		 * @param map
		 * @param id
		 * @return
		 */
		@RequestMapping(value = "/onTable.do")
		public String onTable(ModelMap map,String id) {
			logger.info( "Enter onTable.do ..." );
			
			map.put("id", id);
			return viewPath+"/model";
		}
		
		/**
		 * “模板设置”页面中“指标项”的下拉框
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/chooseItem.do")
		public String chooseItem(HttpServletResponse response) throws IOException{     
			logger.info( "Enter chooseItem.do ..." );
			List<IndicatorItems> list = indItemsService.getAll();
			String jsonStr = indItemsService.getJsonListStr(list);
			response.getWriter().print(jsonStr);
			return null;         
		}  
		
		/**
		 * “模板设置”页面中“指标项”下拉框对应的“类型”
		 * @param response
		 * @param indItemID
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/getValueType.do")
		public IndicatorItems getValueType(HttpServletResponse response,long indItemID) throws IOException{
			logger.info("Enter getValueType....");
			IndicatorItems item = indItemsService.getById(indItemID);
			JSONObject jo=new JSONObject();
			jo.put("item", item);
		    response.getWriter().print(jo.toString());
			return null;
		}
		
		/**
		 * “模板设置”页面中点击“下一步”按钮生成的table表格【暂时未用已做更改，改为调用：getData.do方法】
		 * @param response
		 * @param tableItems
		 * @return
		 * @throws IOException
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/showHtmTable.do")
		public String showHtmTable(HttpServletResponse response,String tableItems,String itemIds) throws IOException{
			logger.info("Enter showHtmTable....");
			
			Map<String, String> treemap1 = new TreeMap<String, String>();
			
			//获取所选的指标项的英文名称以及对应的行列位置
			treemap1 = this.getItemIds(itemIds);
			
			Map<String, String> resultMap1 = sortMapByKey(treemap1);    //按Key进行排序
			List<String> keylist1 = new ArrayList<String>();
			List<String> itemIdslist = new ArrayList<String>();
			
	        for (Map.Entry<String, String> entry : resultMap1.entrySet()) {
	            System.out.println(entry.getKey() + " " + entry.getValue());
	            keylist1.add(entry.getKey());
	            itemIdslist.add(entry.getValue());
	        }
			
			Map<String, String> map = new TreeMap<String, String>();
			
			//获取所选的指标项以及对应的行列位置
			map = this.getItems(tableItems);
			
			Map<String, String> resultMap = sortMapByKey(map);    //按Key进行排序

	        List<String> itemlist = new ArrayList<String>();
	        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
	            System.out.println(entry.getKey() + " " + entry.getValue());
	            itemlist.add(entry.getValue());
	        }
			
	        //生成table
	        String htmlReport = this.getHtml(itemlist,itemIdslist);
	        
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(htmlReport);
	        return null;
		}
		
		/**
		 * 生成table【暂时未用已做更改】
		 * @param list
		 * @return
		 * @throws IOException
		 */
		private String getHtml(List<String> itemlist,List<String> itemIdslist) throws IOException {
	      StringBuffer sb = new StringBuffer();
	      
	      //list 去重
//	      HashSet h = new HashSet(list);   
//	      list.clear();   
//	      list.addAll(h);   
	      
	      
	      int wid = 100;
	      int wid1 = 200;
	      int wid2 = 50;
	      int wid3 = 125;
	      
	      sb.append("<html> \r\n");
	      sb.append("<head> \r\n");
	      sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> \r\n");
	      sb.append("<title></title> \r\n");
	      sb.append("<style type=\"text/css\"> \r\n");
	      sb.append("@page { \r\n");
	      sb.append("size: 1000px 1000px; \r\n");
	      sb.append("} \r\n");
	      sb.append("</style> \r\n");
	      sb.append("</head> \r\n");
	      sb.append("<body style=\"font-family:SimSun;font-size:22px;\"> \r\n");  //字体倾斜：font-style:italic;
//	      sb.append("<div align=\"center\" >").append("<h2>巡检报告</h2>").append("</div> \r\n");
//	      sb.append(htmReport);
	      
	   // 创建开头
	      sb.append("<table align=\"center\" style=\"width:900px;border-collapse:collapse;border:0;word-wrap:break-word;word-break:break-all;font:16px/normal;\"> \r\n");

	      sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
	      sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid1).append("px;border:1px solid #000;\">指标</td> \r\n");
	      sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid2).append("px;border:1px solid #000;\">字体</td> \r\n");
	      sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid2).append("px;border:1px solid #000;\">大小</td> \r\n");
	      sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid2).append("px;border:1px solid #000;\">长度</td> \r\n");
	      sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid2).append("px;border:1px solid #000;\">颜色</td> \r\n");
	      sb.append("<td nowrap=\"nowrap\" style=\"width:").append(wid2).append("px;border:1px solid #000;\">是否加粗</td> \r\n");
	      sb.append("</tr> \r\n");
	      
	      for (int i=0; i< itemlist.size();i++) {
	  			sb.append("<tr align=\"center\"> \r\n");
	  			sb.append("<td style=\"border:1px solid #000;\" align=\"left\">").append(itemlist.get(i)).append("</td> \r\n");
	  			sb.append("<td style=\"border:1px solid #000;\" align=\"left\">").append("宋体").append("</td> \r\n");
	  			sb.append("<td style=\"border:1px solid #000;\" align=\"right\">").append("16").append("</td> \r\n");
	  			sb.append("<td style=\"border:1px solid #000;\" align=\"right\">").append("10").append("</td> \r\n");
	  			sb.append("<td style=\"border:1px solid #000;\" align=\"left\">").append("black").append("</td> \r\n");
	  			sb.append("<td style=\"border:1px solid #000;\" align=\"left\">").append("是").append("</td> \r\n");
	  			sb.append("</tr> \r\n");
	  	  }
	      sb.append("</table> \r\n");
	      
	      sb.append("</body> \r\n");
	      sb.append("</html> \r\n");

	      return sb.toString();
	  }
		
		/**
		 * “模板设置”页面中点击“下一步”按钮生成table表格，并获取表格中的值
		 * @param tableItems
		 * @param itemIds
		 * @param id
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/getData.do")
		public String getData(String tableItems,String id,String tdArr,HttpServletResponse response) throws IOException{
			List<RptTmplCell> list=new ArrayList<RptTmplCell>();
			String jsonStr;
			System.out.println(tdArr);
			String [] td = tdArr.split(",");
//			Map<String, String> treemap1 = new TreeMap<String, String>();
//			
//			//获取所选的指标项的id以及对应的行列位置
//			treemap1 = this.getItemIds(itemIds);
//			
//			Map<String, String> resultMap1 = sortMapByKey(treemap1);    //按Key进行排序
			List<String> keylist1 = new ArrayList<String>();
//			List<String> itemIdslist = new ArrayList<String>();
//			
//	        for (Map.Entry<String, String> entry : resultMap1.entrySet()) {
//	            System.out.println(entry.getKey() + " " + entry.getValue());
//	            keylist1.add(entry.getKey());
//	            itemIdslist.add(entry.getValue());
//	        }
			
			Map<String, String> map = new TreeMap<String, String>();
			
			//获取所选的指标项以及对应的行列位置
			map = this.getItems(tableItems);
			
			Map<String, String> resultMap = sortMapByKey(map);    //按Key进行排序

	        List<String> itemlist = new ArrayList<String>();
	        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
	            System.out.println(entry.getKey() + " " + entry.getValue());
	            keylist1.add(entry.getKey());
	            itemlist.add(entry.getValue());
	        }
	        
	        for (int i = 0; i < itemlist.size(); i++) {
	        	RptTmplCell cell = new RptTmplCell();
	        	
	        	IndicatorItems item = indItemsService.getById(Long.parseLong(itemlist.get(i)));
	        	cell.setCellDispName(item.getIndItemName());
	        	
	        	cell.setCellEnName(item.getIndicatorItem());
	        	cell.setValueType(item.getValueType());
	        	cell.setValueCellWidth(item.getLength());
	        	cell.setColor("black");
	        	cell.setFont("宋体");
	        	cell.setSize(16);
	        	cell.setIsBold(1);
	        	cell.setKeyWidth(Integer.parseInt(td[0]));
	        	cell.setValueWidth(Integer.parseInt(td[1]));
	        	cell.setHeight(Integer.parseInt(td[2]));
	        	cell.setTmplId(Integer.parseInt(id));
	        	cell.setAlign("right");
	        	cell.setTmplCellId(i);
	        	cell.setRowNo(Integer.parseInt((String) keylist1.get(i).subSequence(0, 1))+1);
	        	cell.setColNo(2*(Integer.parseInt((String) keylist1.get(i).subSequence(1, 2)))+1);
	        	list.add(cell);
			}
			
			jsonStr=rptTmplCellService.getJsonListStr(list);
			response.getWriter().print(jsonStr);
			return null;
		}
		
		/**
		 * 获取所选的指标项以及对应的行列位置
		 * @param tableItems
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public Map getItems(String tableItems){
			List<String> keylist = new ArrayList<String>();
			List<String> valuelist = new ArrayList<String>();
			String [] str = tableItems.split(",");
			for (int i = 0; i < str.length; i++) {
				if(i>=str.length/2){
					valuelist.add(str[i]);
				}else{
					keylist.add(str[i]);
				}
			}
			
			Map<String, String> map = new TreeMap<String, String>();
			for(int i=0;i<keylist.size();i++){
				String [] keys = keylist.get(i).split("index");
				String key = "";
				for (String st : keys) {
					key += st;
				}
				
				map.put(key, valuelist.get(i));
			}

	        return map;
		}
		
		/**
		 * 获取所选的指标项的id(为了获取对应的指标项信息)以及对应的行列位置
		 * @param itemIds
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public Map getItemIds(String itemIds){
			List<String> keylist = new ArrayList<String>();
			List<String> valuelist = new ArrayList<String>();
			String [] str = itemIds.split(",");
			for (int i = 0; i < str.length; i++) {
				if(i>=str.length/2){
					valuelist.add(str[i]);
				}else{
					keylist.add(str[i]);
				}
			}
			
			Map<String, String> map = new TreeMap<String, String>();
			for(int i=0;i<keylist.size();i++){
				String [] keys = keylist.get(i).split("index");
				String key = "";
				for (String st : keys) {
					key += st;
				}
				
				map.put(key, valuelist.get(i));
			}

	        return map;
		}
		
		/**
		 * “模板设置”中设置完模板样式后“提交保存”
		 * @param rowData
		 * @param itemIds
		 * @param id
		 * @param map
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/saveTmplCell.do")
		public String saveTmplCell(String rowData,String itemIds,String id,String jsonStr,String totalWidth,String indicatorItemID,String fieldsJsonStr,ModelMap map) {
			logger.info( "Enter saveTmplCell.do ..." );
			System.out.println(indicatorItemID);
			@SuppressWarnings("unused")
			List<RptTmplCell> celllist=JsonUtils.getDTOList(jsonStr, RptTmplCell.class);
			
			List<RptTmplFieldsCell> fieldsCelllist = null;
			if(StringUtils.isNotEmpty(fieldsJsonStr)&&!(fieldsJsonStr.equals("null"))){
				fieldsCelllist=JsonUtils.getDTOList(fieldsJsonStr, RptTmplFieldsCell.class);
			}
			
//			Map<String, String> treemap = new TreeMap<String, String>();
//			
//			//获取所选的指标项名称以及对应的行列位置
//			treemap = this.getItems(rowData);
//			
//			Map<String, String> resultMap = sortMapByKey(treemap);    //按Key进行排序
//			List<String> keylist = new ArrayList<String>();
//			List<String> itemlist = new ArrayList<String>();
//			
//	        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
//	            System.out.println(entry.getKey() + " " + entry.getValue());
//	            keylist.add(entry.getKey());
//	            itemlist.add(entry.getValue());
//	        }
	        
	        List<RptTmplCell> list = rptTmplCellService.getByTmplId(Integer.parseInt(id));
	        if(list!=null&&list.size()>0){	//若当前模板已经设置过，删除之前设置重新设置样式
	        	rptTmplCellService.deleteByTmplId(list);
	        }
	        List<RptTmplFieldsCell> fieldsList = rptTmplFieldsCellService.getByTmplId(Integer.parseInt(id));
	        if(fieldsList!=null&&fieldsList.size()>0){
	        	rptTmplFieldsCellService.deleteByTmplId(fieldsList);
	        }
	        
	        rptTmplCellService.save(celllist,id,totalWidth,fieldsCelllist,indicatorItemID);
			
	        map.put("result", "success");
			return viewPath+"/model";
		}
		
		
		/**
		 * 预览table表格
		 * @param map
		 * @param jsonStr
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/preViewTable.do")
	    public String preViewTable(ModelMap map,String preViewjsonStr,String itemsjsonStr,String tableWidth,String itemId,HttpServletResponse response) throws IOException{
	        logger.info( "Enter preViewTable.do ..." );

	        @SuppressWarnings("unused")
			List<RptTmplCell> celllist=JsonUtils.getDTOList(preViewjsonStr, RptTmplCell.class);
	        
	        List<RptTmplFieldsCell> fieldsCellList  = null;
	        if(StringUtils.isNotEmpty(itemsjsonStr)&&!(itemsjsonStr.equals("null"))){
	        	fieldsCellList = JsonUtils.getDTOList(itemsjsonStr, RptTmplFieldsCell.class);
	        }
	        
	        Map<String, RptTmplCell> treemap = new TreeMap<String, RptTmplCell>();
			
	        for (int i = 0; i < celllist.size(); i++) {
	        	treemap.put(celllist.get(i).getRowNo()+""+celllist.get(i).getColNo(), celllist.get(i));
			}
	        Map<String, RptTmplCell> resultMap = sortMapByKey2(treemap); 	//按Key进行排序
	        List<RptTmplCell> newCellList = new ArrayList<RptTmplCell>();
	        List<String> keylist = new ArrayList<String>();
	        
	        for (Map.Entry<String, RptTmplCell> entry : resultMap.entrySet()) {
	            System.out.println(entry.getKey() + " " + entry.getValue());
	            keylist.add(entry.getKey());
	            newCellList.add(entry.getValue());
	        }
	        
	        String tableView = this.getPreViewHtml(newCellList,tableWidth,itemId,fieldsCellList);
	        
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(tableView);
	        return null;
	    }
		
		
		/**
		 * 预览生成html
		 * @param id
		 * @return
		 * @throws IOException
		 */
		private String getPreViewHtml(List<RptTmplCell> rptList,String tableWidth,String itemId,List<RptTmplFieldsCell> fieldsCellList) throws IOException {
			
//			List<RptTmplCell> rptList = rptTmplCellService.getByTmplId(Integer.parseInt(id));
			
			StringBuffer sb = new StringBuffer();

	        // 创建开头
	        sb.append("<table align=\"center\" style=\"border-collapse:collapse;border:0;word-wrap:break-word;word-break:break-all;\"> \r\n");	//width:900px;
	        
//	        sb.append("<div align=\"left\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
//		    sb.append("<h5 align=\"right\" style=\"width:").append(wid1).append("px;\">指标项表格样式:</h5> \r\n");
//		    sb.append("</div> \r\n");
		    
	        int rowNo = 0 ;
	    	int colNo = 0 ;
	    	int tmplId = 0;
	        for(int i=0;i<rptList.size()-1;i++){
	        	
	        	if(rptList.get(i+1).getRowNo()>rowNo){
	        		rowNo = rptList.get(i+1).getRowNo();
	        	}
	        	if(rptList.get(i+1).getColNo()>colNo){
	        		colNo = rptList.get(i+1).getColNo();
	        	}
	        	tmplId = rptList.get(0).getTmplId();
	        }
	        
	        sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");
	        sb.append("<td align=\"left\" > ").append("指标项表格样式:").append("</td> \r\n");
	        sb.append("</tr> \r\n");
	        
	        int all = rptList.size();	//key的条数		8
	        int col = colNo ;
	        if(colNo%2>0){
	        	col = colNo+1;
	        }
			int avg = all % (col/2);
			
	        for (int i=0; i< rowNo;i++) {
	        	sb.append("<tr> \r\n");
				for (int j=0; j< col/2;j++) {
					if(i==rowNo-1){
						if(avg!=0&&j==avg){
							j=col/2;
							continue;
						}
						
					}
					System.out.print("　　"+rptList.get((col/2)*i+j).getCellDispName());
					//******key****
					if(rptList.get((col/2)*i+j).getIsBold()==1){
						sb.append("<td align=\"").append(rptList.get((col/2)*i+j).getAlign()).append("\" style=\"width:").append(rptList.get((col/2)*i+j).getKeyWidth()).append("px;").append("height:").append(rptList.get((col/2)*i+j).getHeight()).append("px;font-size:").append(rptList.get((col/2)*i+j).getSize()).append("px;color:").append(rptList.get((col/2)*i+j).getColor()).append(";font-weight:bold;font-family:").append(rptList.get((col/2)*i+j).getFont()).append(";border:1px solid #000;\">").append(rptList.get((col/2)*i+j).getCellDispName()).append(":").append("</td> \r\n");
					}else{
						sb.append("<td align=\"").append(rptList.get((col/2)*i+j).getAlign()).append("\" style=\"width:").append(rptList.get((col/2)*i+j).getKeyWidth()).append("px;").append("height:").append(rptList.get((col/2)*i+j).getHeight()).append("px;font-size:").append(rptList.get((col/2)*i+j).getSize()).append("px;color:").append(rptList.get((col/2)*i+j).getColor()).append(";font-family:").append(rptList.get((col/2)*i+j).getFont()).append(";border:1px solid #000;\">").append(rptList.get((col/2)*i+j).getCellDispName()).append(":").append("</td> \r\n");
					}
					//******key****
					
					//******value****
						sb.append("<td align=\"").append("left").append("\" style=\"width:").append(rptList.get((col/2)*i+j).getValueWidth()).append("px;").append("height:").append(rptList.get((col/2)*i+j).getHeight()).append("px;font-size:").append(rptList.get((col/2)*i+j).getSize()).append("px;color:").append(rptList.get((col/2)*i+j).getColor()).append(";font-family:").append(rptList.get((col/2)*i+j).getFont()).append(";border:1px solid #000;\">").append("value"+i+j).append("</td> \r\n");
					//******value****
					
				}
				sb.append("</tr> \r\n");
			}
	        
	        RptTemplate template =new RptTemplate();
			template = rptTemplateService.getById(tmplId);
			int wid = 0;	//列表模板手动添加的列宽
	        
	        if(fieldsCellList!=null&&fieldsCellList.size()>0){
	        	for (int i = 0; i < fieldsCellList.size(); i++) {
	        		wid = wid+fieldsCellList.get(i).getTableWidth();//列表模板手动添加的总列宽
				}
	        }
	        
	        DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
	        
	        if(fieldsCellList!=null&&fieldsCellList.size()>0){
 	        	IndicatorItems indItems = new IndicatorItems();
	        	if(StringUtils.isNotBlank(itemId)){
	        		indItems = indItemsService.getById(Long.parseLong(itemId));
	        	}else{
	        		indItems = indItemsService.getById(fieldsCellList.get(0).getIndItemID());
	        	}
	        	
		        sb.append("</table> \r\n");
		        
		        sb.append("<table align=\"center\" style=\"border-collapse:collapse;border:0;word-wrap:break-word;word-break:break-all;\"> \r\n");	//width:900px;
		        
		        sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");
		        sb.append("<td style=\"font-weight:bold;border:1px solid #000;\" colspan=\" ").append(fieldsCellList.size()).append(" \">").append(indItems.getIndItemName()).append("</td> \r\n");
		        sb.append("</tr> \r\n");
		        
		    
//		    List<IndicatorFields> fieldsList = null;
//		    if(StringUtils.isNotBlank(itemId)){
//		    	fieldsList = indicatorFieldsService.getListFieldsByIndItemID(Long.parseLong(itemId));
//		    }
		    
		        
		    	int width = 0;
		    	
		    	if(StringUtils.isNotBlank(tableWidth)){
		    		width = Integer.parseInt(tableWidth);
		    	}else{
		    		width = template.getTableWidth();	//table的总宽度
		    	}
		    	
		    	sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
		    	for (int i = 0; i < fieldsCellList.size(); i++) {
		    		String cylinders = df.format((float)(fieldsCellList.get(i).getTableWidth())/wid*width);//返回的是String类型 
		    		cylinders = cylinders.substring(0,cylinders.indexOf("."));
		    		sb.append("<td nowrap=\"nowrap\" style=\"width:").append(Integer.parseInt(cylinders)).append("px;").append("height:").append(fieldsCellList.get(i).getHeight()).append("px;font-size:").append(fieldsCellList.get(i).getSize()).append("px;color:").append(fieldsCellList.get(i).getColor()).append(";font-family:").append(fieldsCellList.get(i).getFont()).append(";border:1px solid #000;\">").append(fieldsCellList.get(i).getFieldName()).append("</td> \r\n");
		    	}
			    sb.append("</tr> \r\n");
			    
			    sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
		    	for (int i = 0; i < fieldsCellList.size(); i++) {
		    		String cylinders = df.format((float)(fieldsCellList.get(i).getTableWidth())/wid*width);//返回的是String类型 
		    		cylinders = cylinders.substring(0,cylinders.indexOf("."));
		    		sb.append("<td style=\"width:").append(Integer.parseInt(cylinders)).append("px;").append("height:").append(fieldsCellList.get(i).getHeight()).append("px;font-size:").append(fieldsCellList.get(i).getSize()).append("px;color:").append(fieldsCellList.get(i).getColor()).append(";font-family:").append(fieldsCellList.get(i).getFont()).append(";border:1px solid #000;\">").append("value"+i).append("</td> \r\n");
		    	}
			    sb.append("</tr> \r\n");
			    
			    for (int j = 0; j < 2; j++) {
			    	sb.append("<tr align=\"center\" style=\"font-weight:bold;\" > \r\n");	//style=\"font-weight:bold;\"
			    	for (int i = 0; i < fieldsCellList.size(); i++) {
			    		String cylinders = df.format((float)(fieldsCellList.get(i).getTableWidth())/wid*width);//返回的是String类型 
			    		cylinders = cylinders.substring(0,cylinders.indexOf("."));
			    		sb.append("<td style=\"width:").append(Integer.parseInt(cylinders)).append("px;").append("height:").append(fieldsCellList.get(i).getHeight()).append("px;font-size:").append(fieldsCellList.get(i).getSize()).append("px;color:").append(fieldsCellList.get(i).getColor()).append(";font-family:").append(fieldsCellList.get(i).getFont()).append(";border:1px solid #000;\">").append("...").append("</td> \r\n");
			    	}
				    sb.append("</tr> \r\n");
				}
			    
			    sb.append("</table> \r\n");
		    }
		    
		    
		    
	        
	        StringBuffer sb2 = new StringBuffer();
	        sb2.append("<html> \r\n");
	        sb2.append("<head> \r\n");
	        sb2.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> \r\n");
	        sb2.append("<title>模板展示</title> \r\n");
	        sb2.append("<style type=\"text/css\"> \r\n");
	        sb2.append("@page { \r\n");
	        sb2.append("size: 950px 200px; \r\n");
	        sb2.append("} \r\n");
	        sb2.append("</style> \r\n");
	        sb2.append("</head> \r\n");
	        sb2.append("<body style=\"font-family:SimSun;font-size:22px;\"> \r\n");  //字体倾斜：font-style:italic;
	        sb2.append("<div align=\"center\" >").append("<h2></h2>").append("</div> \r\n");	//模板展示
	        sb2.append(sb.toString());
	        sb2.append("</body> \r\n");
	        sb2.append("</html> \r\n");

	        
	        return sb2.toString();
		}
		
	    /**
	     * 使用 Map按key进行排序(Map<String ,String>)
	     * @param map
	     * @return
	     */
	    public static Map<String, String> sortMapByKey(Map<String, String> map) {
	        if (map == null || map.isEmpty()) {
	            return null;
	        }

	        Map<String, String> sortMap = new TreeMap<String, String>(
	                new MapKeyComparator());

	        sortMap.putAll(map);

	        return sortMap;
	    }
	    
	    /**
	     * 使用 Map按key进行排序(Map<String ,object>)
	     * @param map
	     * @return
	     */
	    public static Map<String, RptTmplCell> sortMapByKey2(Map<String, RptTmplCell> map) {
	        if (map == null || map.isEmpty()) {
	            return null;
	        }

	        Map<String, RptTmplCell> sortMap = new TreeMap<String, RptTmplCell>(
	                new MapKeyComparator());

	        sortMap.putAll(map);

	        return sortMap;
	    }
	    
	    
	   //巡检任务------添加中获取模板
		@RequestMapping(value = "/getTmpl.do")
		public String getTmpl(HttpServletResponse response) throws IOException{
			List<RptTemplate> rptTemplate=rptTemplateService.getAll();
			JSONObject jo=new JSONObject();
			jo.put("rptTemplate", rptTemplate);
		    response.getWriter().print(jo.toString());
			return null;
		}
		
		
		@RequestMapping(value = "/getArray2DIndItemName.do")
		public String getArray2DIndItemName(HttpServletResponse response) throws IOException{
			List<IndicatorItems> items=indItemsService.getAllArray2D();
			JSONObject jo=new JSONObject();
			jo.put("items", items);
		    response.getWriter().print(jo.toString());
			return null;
		}
		
		@RequestMapping(value = "/getItemFieldsData.do")
		public String getItemFieldsData(String itemId,String tableWidth,String listTdheight,HttpServletResponse response) throws IOException{
			
			List<IndicatorFields> fieldsList = indicatorFieldsService.getListFieldsByIndItemID(Long.parseLong(itemId));
			//String jsonStr = indicatorFieldsService.getJsonListStr(fieldsList);
			List<ParamIndicatorFields> paramFieldsList = new ArrayList<ParamIndicatorFields>();
			if(fieldsList.size()>0){
				for (int i = 0; i < fieldsList.size(); i++) {
					int size = fieldsList.size();
					ParamIndicatorFields paramFields = new ParamIndicatorFields();
					paramFields.setFieldNameEn(fieldsList.get(i).getFieldNameEn());
					paramFields.setFieldName(fieldsList.get(i).getFieldName());
					paramFields.setValueType(fieldsList.get(i).getValueType());
					paramFields.setBorderStyle(1);
					paramFields.setRowNo(1);
					paramFields.setColNo(i+1);
					paramFields.setTableWidth(Integer.parseInt(tableWidth)/size);
					paramFields.setHeight(Integer.parseInt(listTdheight));
					paramFields.setColor("black");
					paramFields.setFont("宋体");
					paramFields.setSize(16);
					paramFields.setIsBold(1);
					paramFieldsList.add(paramFields);
				}
			}
			
			String jsonStr=paramIndicatorFieldsService.getJsonListStr(paramFieldsList);
			response.getWriter().print(jsonStr);
			return null;
		}
	    
	    
	}



/**
 * 内部类
 * @author Mr.LP
 * @date 2019-7-24上午11:02:20
 * @className MapKeyComparator
 *
 * @Description TODO
 *
 */
	//比较器类（对map根据key值大小进行排序）
	class MapKeyComparator implements Comparator<String>{

	    @Override
	    public int compare(String str1, String str2) {
	        
	        return str1.compareTo(str2);
	    }
		
}
