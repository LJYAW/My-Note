<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>模板样式修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/help.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/help.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>

    <link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>

<script src="${ctx }/static/assets/js/jquery.min.js"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script src="${ctx }/static/assets/js/H-ui.admin.js"></script>



<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/static/jquery-validationEngine/help.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script src="${ctx }/static/assets/js/jqgridMain.js"></script>
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-en.js"></script>

<script type="text/javascript">

var id = '${id}';
var row = '${row}';
var col = '${col}';
var action = '${action}';

var manager,gridMain;
var dataMain={};

var itemsManager = null;
var itemsGridMain;
var itemsDataMain={};
	
	$(function (){
		

		<c:if test="${result=='success'}">
			window.parent.saveOK();     
			window.parent.closeDlg();
		</c:if>
		
		$("#showHtm").attr("style","visibility:hidden;");
		getCellData(id);
		
		if(action!=""&&action!=null){
			$("#fieldsList").attr("style","visibility:visible;");//显示div
		}else{
			$("#fieldsList").attr("style","visibility:hidden;");
		}
		
		getFieldsData(id);
		
	});
	
	function getFieldsData(id)
	  { 
		
	  	var url = timeURL('${ctx}/cmdb/indicator/report/getFieldsCellData.do?id='+id);
	  	$.ajax({
	  		url: url,
	  		dataType: 'json',
	  		success: function(data) {
	     			   itemsDataMain.Rows = data;
	     			   itemsLoadData();
	  		}
	  	});  
	  	pageItemsInit();
	  }
	
	function itemsLoadData(){
	      selectRowData = null;
	      itemsGridMain.loadData();
	  } 
	
	
	function pageItemsInit(){
		
		  var fontData = [{ val: '宋体', text: '宋体' }, { val: '黑体', text: '黑体'}, { val: '微软雅黑', text: '微软雅黑'}];
			var sizeData = [{ val: '12', text: '12' }, { val: '14', text: '14'}, { val: '16', text: '16'}];
			var colorData = [{ val: 'red', text: 'red' }, { val: 'black', text: 'black'}, { val: 'green', text: 'green'}];
			var isBoldData = [{ val: '1', text: '是' }, { val: '0', text: '否'}];
			var rowData = { Rows: []};
			
			itemsManager=itemsGridMain = $("#itemsMaingrid").ligerGrid({
		        columns: 
		            [
					{ display:'字段英文名', name:'fieldNameEn', width:150, align:'left'},
					{ display:'字段名', name:'fieldName', width:150, align:'left'},
					{ display:'字段类型', name:'valueType', width:100, align:'left'},
					{ display:'字体', name:'font', width:75, align:'left', isSort: false,
		                editor: { type: 'select', data: fontData, valueColumnName: 'val' }, 
		                render: function (item)
	                    {
	                        for (var i = 0; i < fontData.length; i++)
	                        {
	                            if (fontData[i]['val'] == item.font)
	                                return fontData[i]['text']
	                        }
	                    }
		            },
		            { display:'大小', name:'size', width:75, align:'right',isSort: false,
		            	editor: { type: 'select', data: sizeData, valueColumnName: 'val' }, 
		                render: function (item)
	                    {
	                        for (var i = 0; i < sizeData.length; i++)
	                        {
	                            if (sizeData[i]['val'] == item.size)
	                                return sizeData[i]['text']
	                        }
	                    }
		            },
		            { display:'颜色', name:'color', width:75, align:'left',isSort: false,
		            	editor: { type: 'select', data: colorData, valueColumnName: 'val' }, 
		                render: function (item)
	                    {
	                        for (var i = 0; i < colorData.length; i++)
	                        {
	                            if (colorData[i]['val'] == item.color)
	                                return colorData[i]['text']
	                        }
	                    }
		            },
		            { display:'是否加粗', name:'isBold', width:75, align:'left',isSort: false,
		            	editor: { type: 'select', data: isBoldData, valueColumnName: 'val' }, 
		                render: function (item)
	                    {
	                        for (var i = 0; i < isBoldData.length; i++)
	                        {
	                            if (isBoldData[i]['val'] == item.isBold+"")
	                                return isBoldData[i]['text']
	                        }
	                    }
		            },
		            { display:'列表单元格宽度', name:'tableWidth', width:120, align:'right',editor: { type: 'text' } },
		            { display:'列表单元格高度', name:'height', width:120, align:'right' }
		       		],  
		    	data: itemsDataMain,
				alternatingRow: 'true',
				width: '90%',
				pageSize: '20',
				usePager:false,
				pageSizeOptions: [10, 20, 30, 50, 100, 200, 500],
				enabledEdit: true,
		        isScroll: false,
		        //checkbox:true,
		        enabledSort:false,
		        
				onSelectRow: function (rowdata, rowindex)
		              {
					
		                  $("#txtrowindex").val(rowindex);
		              },
		              
	<%--	        onBeforeEdit: onBeforeEdit, --%>
	<%--	        onBeforeSubmitEdit: onBeforeSubmitEdit,--%>
		        onAfterEdit: onAfterEditFields,
		        clickToEdit: true
		              
		    });
		}
	
	var regFieldsFlag1 = true ;
	  function onAfterEditFields(e){
			 if (e.column.name == "tableWidth")
	         {
				 regFieldsFlag1 = false;
				var d = e.record;
				var cellObj = itemsManager.getCellObj(d, e.column);
				//var name = d[e.column.name];
				var rowNoText = $(cellObj).text();
				
				var reg=/^[1-9]\d*$/;
				
			    var re = new RegExp(reg);
			    if (!re.test(rowNoText)) {
			    	regFieldsFlag1 = false ;
			    	layer.msg('单元格宽度只能为大于0的整数！', {icon: 2, time: 1500});
			    	 return ;
			    }
			    else {
			    	if(parseInt(rowNoText)<=0){
			    		layer.msg('请输入有效的单元格宽度!', {icon: 2, time: 1500});
			    		regFieldsFlag1 = false ;
			    		 return ;
			    	} else{
			    		regFieldsFlag1 = true ;
			    	}
			    }
				
	         } 
		}
	
	
	function getCellData(id)
    { 
    	var url = timeURL('${ctx}/cmdb/indicator/report/getCellData.do?id='+id);
    	$.ajax({
    		url: url,
    		dataType: 'json',
    		success: function(data) {
       			   dataMain.Rows = data;
       			   loadData();
    		}
    	});  
    	pageInit();
    }
	
	function loadData(){
        selectRowData = null;
        gridMain.loadData();
    } 
	
	function pageInit(){
		
		
		var fontData = [{ val: '宋体', text: '宋体' }, { val: '黑体', text: '黑体'}, { val: '微软雅黑', text: '微软雅黑'}];
		var sizeData = [{ val: '12', text: '12' }, { val: '14', text: '14'}, { val: '16', text: '16'}];
		var colorData = [{ val: 'red', text: 'red' }, { val: 'black', text: 'black'}, { val: 'green', text: 'green'}];
		var isBoldData = [{ val: '1', text: '是' }, { val: '0', text: '否'}];
<%--		var keyWidthData = [{ val: '150', text: '150' }, { val: '200', text: '200'}, { val: '250', text: '250'}];--%>
<%--		var valueWidthData = [{ val: '100', text: '100' }, { val: '150', text: '150'}, { val: '200', text: '200'}];--%>
<%--		var heightData = [{ val: '20', text: '20' }, { val: '25', text: '25'}, { val: '30', text: '30'}];--%>
		var rowData = { Rows: []};
		
		manager=gridMain = $("#maingrid").ligerGrid({
	        columns: 
	            [
				{ display:'指标项', name:'cellDispName', width:150, align:'left'},
				{ display:'英文名称', name:'cellEnName', width:150, align:'left'},
				{ display:'行位置', name:'rowNo', width:70, align:'right',editor: { type: 'text' }},
				{ display:'列位置', name:'colNo', width:70, align:'right',editor: { type: 'text' }},
	            { display:'字体', name:'font', width:75, align:'left', isSort: false,
	                editor: { type: 'select', data: fontData, valueColumnName: 'val' }, 
	                render: function (item)
                    {
                        for (var i = 0; i < fontData.length; i++)
                        {
                            if (fontData[i]['val'] == item.font)
                                return fontData[i]['text']
                        }
                    }
	            },
	            { display:'大小', name:'size', width:75, align:'right',isSort: false,
	            	editor: { type: 'select', data: sizeData, valueColumnName: 'val' }, 
	                render: function (item)
                    {
                        for (var i = 0; i < sizeData.length; i++)
                        {
                            if (sizeData[i]['val'] == item.size)
                                return sizeData[i]['text']
                        }
                    }
	            },
	            { display:'颜色', name:'color', width:75, align:'left',isSort: false,
	            	editor: { type: 'select', data: colorData, valueColumnName: 'val' }, 
	                render: function (item)
                    {
                        for (var i = 0; i < colorData.length; i++)
                        {
                            if (colorData[i]['val'] == item.color)
                                return colorData[i]['text']
                        }
                    }
	            },
	            { display:'是否加粗', name:'isBold', width:75, align:'left',isSort: false,
	            	editor: { type: 'select', data: isBoldData, valueColumnName: 'val' }, 
	                render: function (item)
                    {
                        for (var i = 0; i < isBoldData.length; i++)
                        {
                            if (isBoldData[i]['val'] == item.isBold+"")
                                return isBoldData[i]['text']
                        }
                    }
	            },
	            { display:'Key列宽', name:'keyWidth', width:70, align:'right' },
	            { display:'Value列宽', name:'valueWidth', width:70, align:'right' },
	            { display:'行高', name:'height', width:70, align:'right' }
	       		],  
	    	data: dataMain,
			alternatingRow: 'true',
			width: '90%',
			pageSize: '20',
			usePager:false,
			pageSizeOptions: [10, 20, 30, 50, 100, 200, 500],
			enabledEdit: true,
	        isScroll: false,
	        //checkbox:true,
	        enabledSort:false,
	        
			onSelectRow: function (rowdata, rowindex)
	              {
				
	                  $("#txtrowindex").val(rowindex);
	              },
	              
<%--	        onBeforeEdit: onBeforeEdit, --%>
<%--	        onBeforeSubmitEdit: onBeforeSubmitEdit,--%>
	        onAfterEdit: onAfterEdit,
	        clickToEdit: true
	              
	    });
	}
	
	var regflag1 = true ;
	var regflag2 = true ;
	function onAfterEdit(e){
		 if (e.column.name == "rowNo")
         {
			regflag1 = false;
			var d = e.record;
			var cellObj = manager.getCellObj(d, e.column);
			//var name = d[e.column.name];
			var rowNoText = $(cellObj).text();
			
			var reg=/^[1-9]\d*$/;
			
		    var re = new RegExp(reg);
		    if (!re.test(rowNoText)) {
		    	regflag1 = false ;
		    	layer.msg('行位置数只能为正整数字！', {icon: 2, time: 1500});
		    	 return ;
		    }
		    else {
		    	if(parseInt(rowNoText)<=0){
		    		layer.msg('请输入有效的行数!', {icon: 2, time: 1500});
		    		regflag1 = false ;
		    		 return ;
		    	}else if(parseInt(rowNoText)>parseInt(row)){
		    		layer.msg('请输入有效的行数，行位置数不能大于总行数!', {icon: 2, time: 1500});
		    		regflag1 = false ;
		    		return ;
		    	}else{
		    		regflag1 = true ;
		    	}
		    }
			
         } else if (e.column.name == "colNo") {
        	regflag2 = false ;
			var d = e.record;
			var cellObj = manager.getCellObj(d, e.column);
			//var name = d[e.column.name];
			var colNoText = $(cellObj).text();
			
			var reg=/^\d?[13579]$/;
			
		    var re = new RegExp(reg);
		    if (!re.test(colNoText)) {
		    	regflag2 = false ;
		    	layer.msg('请输入有效的列数,列位置数只能为正奇数！', {icon: 2, time: 1500});
		    	return ;
		    }
		    else {
		    	if(parseInt(colNoText)<=0){
		    		layer.msg('列位置数只能为正奇数!', {icon: 2, time: 1500});
		    		regflag2 = false ;
		    	}else if(parseInt(colNoText)>parseInt(col*2)){
		    		layer.msg('列位置数只能为正奇数，且列位置数不能大于总列数!', {icon: 2, time: 1500});
		    		regflag2 = false ;
		    	}else{
		    		regflag2 = true ;
		    	}
		    }
			
         }
	}

	
//	提交表单
	function submitForm(){
<%--		$("#rowData").val(arrAll);--%>
<%--		$("#itemIds").val(arrAllItemIds);--%>
		$("#id").val(id);

		var rows = gridMain.getData();
		var rowarray=new Array();
		for(var i=0;i<rows.length;i++){
			rowarray.push(rows[i].rowNo+""+rows[i].colNo);
		}
		
		
			if(regflag1&&regflag2&&regFieldsFlag1){
				var data = manager.getData();
	    		$("#jsonStr").val(JSON.stringify(data));
	    		
	    		var  nary=rowarray.slice().sort();
	    		for(let i=0;i<rowarray.length;i++){
	    		    if (nary[i]==nary[i+1]){
	    		        layer.msg('行、列位置数组合不能重复！', {icon: 2, time: 1500});
	    		        return ;
	    		    }
	    		}   
	    		
	    		var itemsData = itemsManager.getData();
	    		$("#fieldsJsonStr").val(JSON.stringify(itemsData));
	    		
				$("#formSave").submit();
			}else{
				layer.msg('请检查行、列位置以及列表单元格宽度输入是否有效！', {icon: 2, time: 1500});
				 return ;
			}
			
	}
	
	//预览
	function showHtml(){
<%--		$("#rowData").val(arrAll);--%>
<%--		$("#itemIds").val(arrAllItemIds);--%>
<%--		$("#id").val(id);--%>

		var rows = gridMain.getData();
		
		var rowarray=new Array();
		for(var i=0;i<rows.length;i++){
			rowarray.push(rows[i].rowNo+""+rows[i].colNo);
		}
		
			if(regflag1&&regflag2&&regFieldsFlag1){
				
				var data = manager.getData();
				$("#preViewjsonStr").val(JSON.stringify(data));
	    		
	    		var  nary=rowarray.slice().sort();
	    		for(let i=0;i<rowarray.length;i++){
	    		    if (nary[i]==nary[i+1]){
	    		        layer.msg('行、列位置组合不能重复！', {icon: 2, time: 1500});
	    		        return ;
	    		    }
	    		} 
	    		
	    		var itemsData = itemsManager.getData();
	    		$("#itemsjsonStr").val(JSON.stringify(itemsData));
	    		
	    		$("#showHtm").attr("style","visibility:visible;");//显示div
	    		
				var jsonStr = JSON.stringify(data);
				var heig = rows.length * 30 ;
				$("#preViewform").submit();
				
			}else{
				layer.msg('请检查行、列位置以及列表单元格宽度输入是否有效！', {icon: 2, time: 1500});
			}
	}
	
  
</script>

<style type="text/css">
			.td_input{
		border:1px solid #000000;
<%--		background-color: #F0FFFF;--%>
		}
		td input{
		border:none;
		width:100%;
		text-align: center;
		}
		.lightGray {
		  background-color: lightGray;
		  color: #000000;
		}
</style>

</head>
<body> 
<form id="formSave" modelAttribute="rptTemplate" action="${ctx}/cmdb/indicator/report/saveTmplCell.do" method="post">
	<input type="hidden" name="rowData" id="rowData" />
	<input type="hidden" name="id" id="id" />
	<input type="hidden" name="indicatorItem" id="indicatorItem" />
	<input type="hidden" name="itemIds" id="itemIds" />
	
	<input type="hidden" name="jsonStr" id="jsonStr" />
	<input type="hidden" name="fieldsJsonStr" id="fieldsJsonStr" />
	
	<div style="height: 5px;"></div>
	<div style="height: 20px;">
		<h3 style="font-weight:normal;">指标项表格模板样式：</h3>
	</div>
	
	<div style="height: 5px;"></div>
	<div id="main">
		<div id="maingrid" align="center" style="width: 900px;margin:0 0 0 50px;">
			
		</div>
	</div>
	
	<div style="height: 5px;"></div>
	
	<div id="fieldsList">
		<div style="height: 30px;">
			<h3 style="font-weight:normal;">字段项列表模板样式：</h3>
		</div>
		<div style="height: 5px;"></div>
		
		<div id="itemsmain">
			<div id="itemsMaingrid" align="center" style="width: 900px;margin:0 0 0 50px;">
				
			</div>
		</div>
	</div>
	
	<div style="height: 10px;"></div>
		
	<div id="sunButt" align="center" style="height: 25px;">
		<button style="width: 50px;height: 20px;border:1px solid #000000;background-color: #F0FFFF; " id="but0" type="button" onclick="showHtml();" >
                                    预览
        </button>
        <button style="width: 50px;height: 20px;border:1px solid #000000;background-color: #F0FFFF; " id="but1" type="button" onclick="submitForm();" >
                                    提交
        </button>
    </div>
 </form>   
    <div style="height: 5px;"></div>
    
<form id="preViewform" action="${ctx}/cmdb/indicator/report/preViewTable.do" method="post" target="form_iframe">
	<input type="hidden" name="preViewjsonStr" id="preViewjsonStr" />
	<input type="hidden" name="itemsjsonStr" id="itemsjsonStr" />
	
	<div id="htm" style="height: 210px;">
		<div id="showHtm" align="center" style="width: 1000px;height:210px;margin:0 0 0 10px;">
			<iframe  name="form_iframe" width="985" height="200" scrolling="auto" ></iframe>
		</div>
	</div>
	
</form>
</body>
</html>

