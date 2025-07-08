package com.sino.monitor.config.web;

import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.StringUtil;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.common.util.WebFuncUtils;
import com.sino.base.system.entity.OrgOrganization;
import com.sino.cmdb.product.prodClass.entity.ParamTree;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import com.sino.monitor.config.entity.*;
import com.sino.monitor.config.service.DevConfigFileService;
import com.sino.monitor.config.service.MonTaskService;
import com.sino.monitor.config.service.TaskMapDevicesService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import smartlink.utils.Util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RequestMapping(value="/config/configFile")
@Controller
public class DevConfigFileAction {

	private static Logger logger= LoggerFactory.getLogger(DevConfigFileAction.class);
	public static String viewPath="/monitor/config/compare";
	
	@Autowired
	private TaskMapDevicesService taskMapDevicesService;
	
	@Autowired
	private DevConfigFileService devConfigFileService;
	
	@Autowired
	private MonTaskService monTaskService;
	
	private static List<String> index1=null;
	private static List<String> index2=null;
	
	
	@RequestMapping(value="/getTree.do")
	public String getTree(String menuId,ModelMap map){
		logger.info( "Enter DevConfigFileAction getTree.do ..." );
		List<ParamTree> total = new ArrayList<ParamTree>();
		List<OrgOrganization> orglist= SystemUtils.getLoginUserSubOrg();
		List<OrgOrganization> orgTree=new ArrayList<OrgOrganization>();
		List<String> orgIds=taskMapDevicesService.getAllOrgIds();
		
		for(int i=0;i<orglist.size();i++){
			for(int j=0;j<orgIds.size();j++ ){
				if(orglist.get(i).getOrgId().equals(orgIds.get(j))){
					orgTree.add(orglist.get(i));
				}
			}
		}
		
		
		if(!orgTree.isEmpty()){
			for(int i=0;i<orgTree.size();i++){
				ParamTree pc=new ParamTree();
				OrgOrganization o=orgTree.get(i);
				pc.setId(o.getOrgId()+"_1");
				pc.setText(o.getOrgName());
				total.add(pc);
				
				List<ResItemParam> devClass=taskMapDevicesService.getDevClassByOrgID(o.getOrgId());
				for(int j=0;j<devClass.size();j++){
					ParamTree devc=new ParamTree();
					ResItemParam c=devClass.get(j);
					devc.setPid(o.getOrgId()+"_1");
					devc.setId(o.getOrgId()+";"+c.getId()+"_2");
					devc.setText(c.getText());
					total.add(devc);
					
					List<ResItemParam> devType=taskMapDevicesService.getDevTypeByOrgIDAndClassCode(o.getOrgId(),Integer.parseInt(c.getId()));
					for(int k=0;k<devType.size();k++){
						ParamTree devt=new ParamTree();
						ResItemParam t=devType.get(k);
						devt.setPid(o.getOrgId()+";"+c.getId()+"_2");
						devt.setId(o.getOrgId()+";"+c.getId()+";"+t.getId()+"_3");
						devt.setText(t.getText());
						total.add(devt);
					}
				}
			}
			
			JSONArray json = JSONArray.fromObject(total);
			String treeData = json.toString();
			map.put("treeData", treeData);
			WebFuncUtils.titleContent(menuId,map);
		}
		
		return viewPath+"/tabMain";
	}
	
	
	@RequestMapping(value="/getMonDevTaskList.do")
	public String getMonDevTaskList(String ids, ModelMap map){
		
		logger.info( "Enter DevConfigFileAction getMonDevTaskList.do ..." );
		List<TaskMapDevices> list=new ArrayList<TaskMapDevices>();
		List<ParamDevTask> plist=new ArrayList<ParamDevTask>();
		if(!StringUtil.isNullString(ids)){
			String arr[]=ids.split("_");
			String flag=arr[1];
			String id=arr[0];
			if(flag.equals("1")){  //机构层
				list=taskMapDevicesService.getParams(id, 1);
			}else if(flag.equals("2")){  //设备分类层
				list=taskMapDevicesService.getParams(id.split(";")[0],Integer.parseInt(id.split(";")[1]), 1);
			}else{   //设备类型层
				list=taskMapDevicesService.getParams(id.split(";")[0],Integer.parseInt(id.split(";")[1]),Integer.parseInt(id.split(";")[2]), 1);
			}
		}
		
		if(!list.isEmpty()){
			for(TaskMapDevices devtask:list){
				ParamDevTask  ptask=new ParamDevTask();
				BeanUtils.copyProperties(devtask, ptask);
				MonTask task=monTaskService.getById(devtask.getTaskID());
				ptask.setTaskType(task.getTaskType());
				ptask.setTaskName(task.getTaskName());
				ptask.setStartTime(task.getStartTime());
				plist.add(ptask);
			}
		}
		String jsonStr = taskMapDevicesService.getParamJsonListStr(plist);
		map.put("jsonStr", jsonStr);
		return viewPath+"/main";
	}
	
	@RequestMapping(value="/getHistoryData.do")
	public String getHistoryData(String devID, ModelMap map){
		logger.info( "Enter DevConfigFileAction getHistoryData.do ..." );
		System.out.println(".......");
		List<DevConfigFile> files=devConfigFileService.getByDevID(Long.parseLong(devID));
		String jsonStr = devConfigFileService.getJsonListStr(files);
		map.put("jsonStr", jsonStr);
		return viewPath+"/history";
	}
	
	@RequestMapping(value="/view.do")
	public String view(String id, ModelMap map){
		logger.info( "Enter DevConfigFileAction view.do ..." );
		DevConfigFile file=devConfigFileService.getByID(Long.parseLong(id));
		map.put("file", file);
		map.put("cfgContent", file.getCfgContent().replaceAll("\n", "<br>"));
		return viewPath+"/view";
	}
	
	@RequestMapping(value="/deletes.do")
	public String deletes(String ids,HttpServletResponse response) throws IOException{
		logger.info( "Enter DevConfigFileAction delete.do ..." );
		
		List<Long> idlist=new ArrayList<Long>();
		String [] array=ids.split(";");
		for(String id:array){
			idlist.add(Long.parseLong(id));
		}
		devConfigFileService.deletes(idlist);
		JSONObject jo=new JSONObject();
		jo.put("result", "success");
		response.getWriter().print(jo.toString());
		return null;
		
	}
	
	@RequestMapping(value="/devCompare.do")
	public String devCompare(String devIds, String devMapIds, ModelMap map){
		logger.info( "Enter DevConfigFileAction devCompare.do ..." );
		
		String [] maparr=devMapIds.split(";");
		TaskMapDevices devMap1=taskMapDevicesService.getByID(Long.parseLong(maparr[0]));
		TaskMapDevices devMap2=taskMapDevicesService.getByID(Long.parseLong(maparr[1]));
		
		String [] idarr=devIds.split(";");
		List<DevConfigFile> file1s=devConfigFileService.getByDevID(Long.parseLong(idarr[0]));
		List<DevConfigFile> file2s=devConfigFileService.getByDevID(Long.parseLong(idarr[1]));
		
		map.put("devMap1", devMap1);
		map.put("devMap2", devMap2);
		
		map.put("file1s", file1s);
		map.put("file2s", file2s);
		return viewPath+"/devFiles";
	}
	
	

	
	@RequestMapping(value="/compare.do")
	public String compare(String ids, ModelMap map){
		
		logger.info( "Enter DevConfigFileAction compare.do ..." );
		
		//记录文本差异处
		index1=new ArrayList<String>();
		index2=new ArrayList<String>();
		
		
		//记录两文件空行下标
		List<Integer> emptyIndex1=new ArrayList<Integer>();
		List<Integer> emptyIndex2=new ArrayList<Integer>();
		
		
		String [] idarr=ids.split(";");
		DevConfigFile file1=devConfigFileService.getByID(Long.parseLong(idarr[0]));
		DevConfigFile file2=devConfigFileService.getByID(Long.parseLong(idarr[1]));
		
		int baseline1=file1.getBaseLine();
		int baseline2=file2.getBaseLine();
		
		
	    String [] data1=file1.getCfgContent().split("\n");
		String [] data2=file2.getCfgContent().split("\n");
		
		String result1="";
		String result2="";
		
		String lastResult1="";
		String lastResult2="";
		
		String diffResult="";
		
		List<String> diffType=new ArrayList<String>();  //差异处类型
		
		//找文本差异个数
		List     diffs  = (new Diff(data1, data2)).diff();
        
        Iterator it     = diffs.iterator();
        while (it.hasNext()) {
            Difference diff     = (Difference)it.next();
            int        delStart = diff.getDeletedStart();
            int        delEnd   = diff.getDeletedEnd();
            int        addStart = diff.getAddedStart();
            int        addEnd   = diff.getAddedEnd();
            String     from     = toString(delStart, delEnd);
            String     to       = toString(addStart, addEnd);
            String     type     = delEnd != Difference.NONE && addEnd != Difference.NONE ? "c" : (delEnd == Difference.NONE ? "a" : "d");

            //type a:右边有多出来的内容       d:左边有多出来的内容 
            
            System.out.println(from + type + to);
            diffResult= from + type + to;
            
            if(diffResult.contains("c")){
            	diffType.add(diffResult);
            }
            
            if (delEnd != Difference.NONE) {
                printLines(delStart, delEnd, "file1", data1,type);
                if (addEnd != Difference.NONE) {
                    System.out.println("---");
                }
            }
            if (addEnd != Difference.NONE) {
                printLines(addStart, addEnd, "file2", data2,type);
            }
        }	
        
        
        
        
       for(int i=0;i<data1.length;i++){
    	  for(int j=0;j<index1.size();j++){
    		  if(i==Integer.parseInt(index1.get(j).split(";")[0])){
    			  data1[i]="<span style='background:#C0D9D9'>"+data1[i]+"</span>";
    			  if(index1.get(j).split(";")[2].equals("a")){  //右边内容有多于处，记录下该位置并添加到一个集合里
     				  emptyIndex1.add(i);
     			   }else if(index1.get(j).split(";")[2].equals("d")){
     				  emptyIndex2.add(i);
     			   }
    		  }
    		  
    	  }
    	  result1+=data1[i]+"<br>";
       }

        
       for(int i=0;i<data2.length;i++){
    	   
    	   for(int j=0;j<index2.size();j++){
    		   if(i==Integer.parseInt(index2.get(j).split(";")[0])){
    			   data2[i]="<span style='background:#C0D9D9'>"+data2[i]+"</span>";
    			   if(index2.get(j).split(";")[2].equals("a")){
     				 emptyIndex1.add(i);
     			   }else if(index2.get(j).split(";")[2].equals("d")){
     				  emptyIndex2.add(i);
     			   }
    	    	 }
    	   }
    	   result2+=data2[i]+"<br>";
      }
       
       
       for(int i=0;i<diffType.size();i++){    
    	   String [] arr1=diffType.get(i).split("c"); 
    	   if(arr1[0].contains(",")&&!arr1[1].contains(",")){ //左边有多出内容
    		   String [] arr2=arr1[0].split(",");
    		   for(int j=Integer.parseInt(arr2[0]);j<Integer.parseInt(arr2[1]);j++){
    			   emptyIndex2.add(j);
    		   }
    	   }else if(arr1[1].contains(",")&&!arr1[0].contains(",")){ //右边有多出内容
    		   String [] arr2=arr1[1].split(",");
    		   for(int j=Integer.parseInt(arr2[0]);j<Integer.parseInt(arr2[1]);j++){
    			   emptyIndex1.add(j);
    		   }
    	   }
       }
       

       List<String> temp1=new ArrayList<String>();
       List<String> temp2=new ArrayList<String>();
      
       String []temparr1=result1.split("<br>");
       String []temparr2=result2.split("<br>");
       
       for(int i=0;i<temparr1.length;i++){
    	   temp1.add(temparr1[i]);
       }
       
       for(int i=0;i<temparr2.length;i++){
    	   temp2.add(temparr2[i]);
       }
       
       
       for(int i=0;i<temp1.size();i++){
    	   
    	   for(int j=0;j<emptyIndex1.size();j++){
    		   if(i==emptyIndex1.get(j)){
    			   if(emptyIndex1.size()>1&&emptyIndex2.size()>0&&emptyIndex1.size()>emptyIndex2.size()){
    				   temp1.add(emptyIndex1.get(j)+1, " ");
    			   }else{
    				   temp1.add(emptyIndex1.get(j), " ");
    			   }
    			   
    		   }
    	   }
       }
       
       for(int i=0;i<temp2.size();i++){
    	   for(int j=0;j<emptyIndex2.size();j++){
    		   if(i==emptyIndex2.get(j)){
    			   if(emptyIndex2.size()>1&&emptyIndex1.size()>0&&emptyIndex1.size()<emptyIndex2.size()){
    				   temp2.add(emptyIndex2.get(j)+1, " ");
    			   }else{
    				   temp2.add(emptyIndex2.get(j), " ");
    			   }
    			  
    		   }
    	   }
       }

       
       for(int i=0;i<temp1.size();i++){
    	   lastResult1+=temp1.get(i)+"<br>";
       }
       
       for(int i=0;i<temp2.size();i++){
    	   lastResult2+=temp2.get(i)+"<br>";
       }
       
//       System.out.println("result1--->"+result1);
//       System.out.println("result2--->"+result2);
       	map.put("result1", lastResult1);
		map.put("result2", lastResult2);
		
		if(baseline1==1){
			map.put("baseline1", "<span style='color:red'>(基线配置)</span>");
		}else{
			map.put("baseline1","");
		}
		
		if(baseline2==1){
			map.put("baseline2", "<span style='color:red'>(基线配置)</span>");
		}else{
			map.put("baseline2","");
		}
		
		map.put("file1", file1);
		map.put("file2", file2);
		return viewPath+"/result";
  }
	
	
	@RequestMapping(value="/setBaseLine.do")
	public String setBaseLine(String id,HttpServletResponse response ) throws IOException{
		
		devConfigFileService.setBaseLine(Long.parseLong(id));
		response.getWriter().print("success");
		return null;
	}
	
	@RequestMapping(value="/saveConfig.do")
	public String saveConfig(String devIp,String cfgContent,HttpServletResponse response ) throws IOException{
		DevConfigFile file=new DevConfigFile();
		file.setDevID(Util.getCRC32(devIp));
		file.setDevIpAddr(devIp);
		file.setCfgFileType(0);
		
		long now=System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		file.setCfgFileName("Running-Config"+"-"+sdf.format(now)+".txt");
		
		file.setCfgContent(cfgContent.replaceAll("<br>", "\n"));
		file.setCollection_Time(new Date(now));
		file.setBaseLine(0);
		devConfigFileService.save(file);
		response.getWriter().print("success");
		return null;
		
		
	}
	
	  protected String toString(int start, int end)
	    {

	        StringBuffer buf = new StringBuffer();

	        buf.append(end == Difference.NONE ? start : (start));
	        
	        if (end != Difference.NONE && start != end) {
	            buf.append(",").append(end);
	        }
	        return buf.toString();
	    }
	  
	  protected void printLines(int start, int end, String ind, String[] lines,String type)
	    {
		  
		  if(ind.equals("file1")){
			  for (int lnum = start; lnum <= end; ++lnum) {
		        	
//		            System.out.println(ind + " " + lines[lnum]);
		            index1.add(lnum+";"+lines[lnum]+";"+type);
		        }
		  }else if(ind.equals("file2")){
			  for (int lnum = start; lnum <= end; ++lnum) {
		        	
//		            System.out.println(ind + " " + lines[lnum]);
		            index2.add(lnum+";"+lines[lnum]+";"+type);
		        }
		  }
	        
	    }
	  
}
