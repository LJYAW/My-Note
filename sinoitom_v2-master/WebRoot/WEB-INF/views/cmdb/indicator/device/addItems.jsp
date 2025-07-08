<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加巡检设备</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var dataMain = {};
    	var indDataMain = {};
    	var AllDevData={};
    	var gridMain;
    	var indGridMain;
        var selectRowData = null;
    	var dlgSearch = null;
    	var orgId=null;
    	var manager;
    	var indManager;
    	var devClassCode="${devClassCode}";
    	var devTypeCode="${devTypeCode}";
    	//var threshData=${jsonThresh};
    	//var result='${result}';
    	//if(result=="success"){
    	//	alert();
    	//window.parent.$.ligerDialog.success("监测设备添加成功!");
    	// window.parent.parent.saveOK();
    	 // window.parent.closeDlg();
    	//}
    	
        $(function ()
        {	
        	
        	<c:if test="${result=='success'}">
    		window.parent.parent.saveOK();     
    		window.parent.closeDlg();
    		</c:if>
    		
    		
        	$("#toptoolbar").ligerToolBar({ 
				items: 
            		[
                	{ text:'关联', click: selectInd, icon:'add' },
                	{ line:true },
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });
            dataMain.Rows =${jsonListData};
            manager=gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'<span style="font-weight:bold; ">监测对象</span>', name:'indClassName', width:70,align:'center',type:'text' },
                    { display:'<span style="font-weight:bold; ">指标组</span>', name:'indGroupName', width:110,align:'center',type:'text' },
                    { display:'<span style="font-weight:bold; ">指标英文名称</span>', name:'indicatorItem', width:140,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">指标中文名称</span>', name:'indItemName', width:140,align:'center',type:'text'},
                    { display:'<span style="font-weight:bold; ">指标说明</span>', name:'remark', width:180,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">取值类型</span>', name:'valueType', width:60,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">长度</span>', name:'length', width:60,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">小数位数</span>', name:'decimals', width:60,align:'left',type:'text'},
                    { display:'<span style="font-weight:bold; ">度量单位</span>', name:'measureUnit', width:60,align:'left',type:'text'},
               	],   
				data: dataMain,
				alternatingRow: 'true',
				usePager:false,
				checkbox:true, 
                width: '100%',
                height:'100%', 
                pageSize: '50',
                pageSizeOptions: [25, 50, 100,200],
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });
            
            refreshIndClass();
           // refreshIndGroup();
            });
            
      
        function onRefresh(){
			window.location.reload();
		}
		
      
        
      function loadData(){
            selectRowData = null;
            gridMain.loadData();
           
        }
      function selectInd(){
    	    
    	    
    	      var indData = manager.getSelectedRows();
    	      $("#indJsonStrs").val(JSON.stringify(indData));
    	      
    	      if(indData==null||indData==""){
    	         $.ligerDialog.warn("请选择关联指标项");
    	         return;
    	      }
    	      
    	      if(indData!=null&&indData!=""){
    	         $("#formSave").submit();
    	      }
    	      
    	   }    
    
      //刷新检测对象
      function refreshIndClass(){
         	  $.getJSON(
                "${ctx}/cmdb/indicator/device/getIndClassCode.do?devTypeCode="+devTypeCode+"&devClassCode="+devClassCode,
                function (result) {
                    if (result.indClass != null) {
                    	 $("#indClassCode").empty();
                         for (var i = 0; i < result.indClass.length; i++) {
                             var group = result.indClass[i];
                             if(group.indClassCode=='${indClassCode}'){
                            $("#indClassCode").append("<option  value=\"" + group.indClassCode +"\"  selected>" + group.indClassName + "</option>");
                            
                          }else{
                            $("#indClassCode").append("<option  value=\"" + group.indClassCode + "\" >" + group.indClassName + "</option>");
                          }
                              
                             
                         }
                         refreshIndGroup();
                    }
                });
      }
    //刷新指标组
     function refreshIndGroup(){
     var indClassCode=$("#indClassCode").find("option:selected").val();
<%--       	  $.getJSON(--%>
<%--              "${ctx}/cmdb/indicator/device/getIndGroups.do?devTypeCode="+devTypeCode+"&devClassCode="+devClassCode+"&indClassCode="+indClassCode,--%>
<%--              function (result) {--%>
<%--                  if (result.indGroups != null) {--%>
<%--                      $("#indGroupID").empty();--%>
<%--                      for (var i = 0; i < result.indGroups.length; i++) {--%>
<%--                          var group = result.indGroups[i];--%>
<%--                          alert(group.indGroupID);--%>
<%--                            $("#indGroupID").append("<option  value=\"" + group.indGroupID + "\">" + group.indGroupName + "</option>");--%>
<%--                          --%>
<%--                      }--%>
<%--                  }--%>
<%--              });--%>
		$.getJSON(
		        "${ctx}/cmdb/prodIndItem/getIndGroup.do?indClassCode="+indClassCode,
		        function (result) {
		            if (result.indGroups != null) {
		                $("#indGroupID").empty();
		                for (var i = 0; i < result.indGroups.length; i++) {
		                    var group = result.indGroups[i];
		                    
		                    if(group.id=='${indGroupID}'){
                            $("#indGroupID").append("<option  value=\"" + group.id +"\"  selected>" + group.groupName + "</option>");
                            
                          }else{
                           $("#indGroupID").append("<option  value=\"" + group.id + "\">" + group.groupName + "</option>");
                          }
                          
                          
		                      
		                    
		                }
		            }
		        });
    }
    
    	 
         function searchItemsData()
         {
        	 var indClassCode=$("#indClassCode").find("option:selected").val();
        	 var indGroupID=$("#indGroupID").find("option:selected").val();
<%--        	 alert(indClassCode);--%>
<%--        	 alert(indGroupID);--%>
<%--        	 --%>
<%--        	 alert(devClassCode);--%>
<%--        	 alert(devTypeCode);--%>
         	var url = timeURL('${ctx}/cmdb/indicator/device/searchItemsData.do?devClassCode='+devClassCode+'&devTypeCode='+devTypeCode+'&indGroupID='+indGroupID+'&indClassCode='+indClassCode);
         	$.ajax({
         		url: url,
         		dataType: 'json',
         		success: function(data) {
            			dataMain.Rows = data;
            			loadData();
         		}
         	});        			
         };
    </script>
</head>
<body style="padding:0px; overflow:hidden;" onload="">
	<form id="formSave" modelAttribute="snmpRes" action="${ctx}/cmdb/indicator/device/saveItems.do" method="post">
	<input  type="hidden" name="indJsonStrs" id="indJsonStrs" value=""  />
	<input  type="hidden" name="devClassName" id="devClassName" value="${devClassName}"  />
	<input  type="hidden" name="devTypeName" id="devTypeName" value="${devTypeName}"  />
	<input  type="hidden" name="devClassCode" id="devClassCode" value="${devClassCode}"  />
	<input  type="hidden" name="devTypeCode" id="devTypeCode" value="${devTypeCode}"  />
		<div  style="height:80px;width:100%;background:#D6E3EF">
			<table style="height:58px;MARGIN-LEFT: 20px">
		      <tr>
		       
		      </tr>
		      
		      <tr>
		        <td align="right">设备分类 ：</td>
                <td>
                <input id="devClassName" name="devClassName" value="${devClassName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
               
                <td>设备类型：</td> 
				<td>
					<input id="devTypeName" name="devTypeName" value="${devTypeName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
				</td>
				<td style="width:30px"></td>
                <td></td>
                <td></td>
		      </tr>
		      <tr>
		       <td align="right">检测对象:</td>
                <td>
                	<select name="indClassCode" id="indClassCode" style="width:202px" onchange="refreshIndGroup()"> 
					</select> 
				</td>
               
                 <td align="right">指标组:</td>
                <td>
                	<select name="indGroupID" id="indGroupID" style="width:202px" > 
					</select> 
				</td>
                <td>
                	<input id="bnSave" type="button" value="筛选指标" class="l-button mg6" onclick="searchItemsData()"/>
                </td>
                 <td>
                 </td>
                </tr>
		</table>
		</div>
	</form>
	<div id="toptoolbar"></div> 
    <div id="maingrid"></div>
    
    <div id="indToptoolbar" style="display:none;">性能指标项列表</div> 
    <div id="indMaingrid" style="display:none;"></div>
    
	<div style="display:none;">
	
	
	
    </div>
</body>
</html>