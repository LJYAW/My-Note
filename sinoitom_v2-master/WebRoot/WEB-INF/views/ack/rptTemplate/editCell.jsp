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
<title>模板设置</title>
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

var lastRow;
var lastCell;


	var num  ;
	var index ;
	
	var array = new Array();
	var array2 = new Array();
	var arrItemIds = new Array();
	var arrAllItemIds = new Array();
	
	var arrAll = new Array();
	
	var slist = '${slist}';
	slist=JSON.parse(slist);	//此为解析json串的方法（通用）
	var names = "";
	for(var i=0;i<slist.length;i++){
		names += slist[i];
	}
	var newNamesArr = new Array();
	var nameArr = new Array();
	nameArr = names.split(",");
	array = names.split(",");
	//alert(array);
	var typelist = '${typelist}';
	typelist=JSON.parse(typelist);	//此为解析json串的方法（通用）
	var types = "";
	for(var i=0;i<typelist.length;i++){
		types += typelist[i];
	}
	
	
	$(function (){
		
<%--		$("#main").attr("style","visibility:hidden;");//隐藏div--%>

		<c:if test="${result=='success'}">
			window.parent.saveOK();     
			window.parent.closeDlg();
		</c:if>
		
		//$("#sunButt").attr("style","visibility:hidden;");
		$("#showHtm").attr("style","visibility:hidden;");
		
		//$("#but1").css("display","none");
		
		$("#listBut").attr("style","visibility:hidden;");
		$("#listTab").attr("style","visibility:hidden;display:none");
		
		if(action=="fields"){
			$("#inpu1").prop('checked',true);
			$("#listTab").attr("style","visibility:visible;");//显示div
			getArray2DIndItemName();
			getFieldsData(id);
		}else{
			$("#inpu2").prop('checked',true);
		}
		//$("#but1").attr("style","display:none;");
		
		getNewTable();
		
		//createTable();
		getCellData(id);
		
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
	
	function createTable(){
		num = 0;
		
		var sss = "";
		for(var i=0;i<row;i++){
			
			var trObj2 = document.createElement("tr");
			trObj2.innerHTML = "<td colspan=6 style='height: 5px;'></td>";
			document.getElementById("tb2").appendChild(trObj2);
			
			var trObj = document.createElement("tr");
			trObj.id = new Date().getTime();
			for(var j=0;j<col;j++){
<%--				index = trObj.id+j+i;--%>
<%--				index = "Row_"+i+"Col_"+j;--%>
				index = "index"+i+"index"+j;
				sss = sss + "<td style='width: 150px;' class='td_input'><select class='sele' name='indItemID' id='"+index+"'  onchange='chooseItem("+index+",valueType_"+index+");' style='width: 150px;'><option value='-1' >——请选择——</option>"
					+"</select></td><td style='width: 150px;' class='td_input'><input type='text' id='valueType_"+index+"' name='valueType'  readonly='readonly'/></td>";
					
				chooseItem(index,"valueType_"+index);
			}
			
			trObj.innerHTML = sss ;
			document.getElementById("tb2").appendChild(trObj);
			
			sss = "";
		}
		//$(".td_input").css("display","none");
	}
	
	function unique2(arr){
		  var hash=[];
		  for (var i = 0; i < arr.length; i++) {
		     if(arr.indexOf(arr[i])==i){
		      hash.push(arr[i]);
		     }
		  }
		  return hash;
		}

	//指标项的下拉框
	function chooseItem(str,valu){
		array = unique2(array);
		num++;
		var flag = row * col ;
		if(num>flag){
			
			str = str.id;
			valu = valu.id ;
			
			var a = $("#"+str).find("option:selected").val();
			var ss = $("#"+str+" option:checked").text();
			
			var paramArr = new Array();
			
			for(var i=0;i<row;i++){
				for(var j=0;j<col;j++){
					
					var ind = "index"+i+"index"+j;
					
					var sstr = $("#"+ind+" option:checked").text();
					
					paramArr.push(sstr);	
					
				}
			}
			
			
			//临时数组存放
			var tempArray1 = [];//临时数组1
			var tempArray2 = [];//临时数组2

			for(var i=0;i<paramArr.length;i++){
			    tempArray1[paramArr[i]]=true;//将数paramArr 中的元素值作为tempArray1 中的键，值为true；
			}

			for(var i=0;i<array.length;i++){
			    if(!tempArray1[array[i]]){
			        tempArray2.push(array[i]);//过滤array 中与paramArr 相同的元素；
			    }
			}
			
			var r = "";
			r = tempArray2.filter(function (s) {
			    return s && s.trim(); // 注：IE9(不包含IE9)以下的版本没有trim()方法
			});
			//r=tempArray2是原来列中被替换掉的选项
			//array.splice(array.indexOf(r),1,ss); 
			
			if(array.indexOf("") > -1){
				array.splice(array.indexOf(" "),1,ss);
				array.splice(array.length-1,1);
			}
			
			if(a=="-1"){
				$("#"+str).val("");
				$("#nextBut").attr("disabled","disabled");
				array.splice(array.indexOf(r),1," ");
				return ;
			}else{
				if(array.indexOf(ss) > -1){
					layer.msg('指标项：'+'"'+ss+'"'+'已被选择，不能重复选择！', {icon: 7, time: 1500});
					$("#"+str).val("");
					$("#nextBut").attr("disabled","disabled");
					
					array.splice(array.indexOf(r),1," "); 
				    return ;
				}else{
					if(array.indexOf(" ") > -1){
						array.splice(array.indexOf(" "),1,ss);
					}
					array.splice(array.indexOf(r),1,ss);
					//array.splice(array.length-1,1);
					$("#nextBut").removeAttr("disabled");
					//alert(array+"***3");
				}
			}
			
			//$("#"+str).attr("disabled","disabled");
		}
		
		$.ajax({
			type:"post",	
			dataType:"json",
			url:"${ctx}/cmdb/indicator/report/chooseItem.do",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success:function(data){
<%--				$("#indItemID").empty();--%>
					$.each(data,function(index,value){
						if(num>flag){
							if (value.id == "") {
								$("#"+str).append("<option  value=\"-1\" selected>-----------------请选择-----------------</option>");
							 }
			                if(value.id==item.id){
			                	$("#"+str).append("<option  value=\"" + value.id + "\"  selected>" + value.indItemName + "</option>");
			                }else{
			                    $("#"+str).append("<option  value=\"" + value.id + "\">" + value.indItemName + "</option>");
			                }
						}else{
							var cont = 0;
							for(var i=0;i<row;i++){
								for(var j=0;j<col;j++){
									
									var name = names.split(",")[cont];
									var type = types.split(",")[cont];
									//alert(name+"name");
									var r = "index"+i+"index"+j;
									//alert(r+"---");
									if(r==str){
										if (value.indItemName == "") {
											$("#"+str).append("<option  value=\"-1\" selected>-----------------请选择-----------------</option>");
										 }
						                if(value.indItemName==name){
						                	$("#"+str).append("<option  value=\"" + value.id + "\"  selected>" + value.indItemName + "</option>");
						                }else{
						                    $("#"+str).append("<option  value=\"" + value.id + "\">" + value.indItemName + "</option>");
						                }
						                
						                $("#"+valu).empty();
					                      $("#"+valu).val(type);
									}
									
					                cont ++;
								}
								
							}
						}
						
						
		          	});
			}
		});
		var total = row * col ;
		if(total==array.length){
			$("#nextBut").removeAttr("disabled");
		}
		
		getValueType(str,valu);
	}
	
	function trimSpace(array){  
	     for(var i = 0 ;i<array.length;i++)  
	     {  
	         if(array[i] == " " || array[i] == null || typeof(array[i]) == "undefined")  
	         {  
	                  array.splice(i,1);  
	                  i= i-1;  

	         }  
	     }  
	     return array;  
	}  
	
	//指标项下拉框中值对应的类型	
	function getValueType(str,valu){
		$("#nextBut").removeAttr("disabled");
		var indItemID=$("#"+str).val();
		$.getJSON(
	              "${ctx}/cmdb/indicator/report/getValueType.do?indItemID="+indItemID,
	              function (result) {
	                  if (result.item != null) {
	                      $("#"+valu).empty();
	                      var item = result.item;
	                      $("#"+valu).val(item.valueType);
	                      var itemId = item.indItemID;
	                      arrItemIds.push(itemId);
	                  }else{
	                	  $("#"+valu).val("");
	                  }
	              });
	}
	
	function createTable2(){
			
		var trObj2 = document.createElement("tr");
		trObj2.innerHTML = "<td colspan=6 style='height: 10px;'></td>";
		document.getElementById("tb3").appendChild(trObj2);
			
		var trObj = document.createElement("tr");
		trObj.innerHTML = "<td colspan=6 align='center' style='height: 30px;'><input type='button' id='nextBut' style='width: 50px;height: 25px;border:1px solid #000000;background-color: #F0FFFF; ' value='下一步' onclick='getNextTable();'></td>";
		document.getElementById("tb3").appendChild(trObj);
			
	}
	
	
	function getNewTable(){
		
		$("#tb2 tr").remove();
		
		createTable();
		
		createTable2();
		
	}
	
	var tdArr = new Array();
	var flagtdkey = true;
	function checkKeyWidth(){
		$("#nextBut").removeAttr("disabled");
		var tdkey = $("#tdkey").val();
		
		var reg=/^[1-9]\d*$/;
		
	    var re = new RegExp(reg);
	    if (!re.test(tdkey)) {
	    	flagtdkey = false;
	    	layer.msg('只能输入正整数！', {icon: 2, time: 1500});
	    	return ;
	    }else{
	    	flagtdkey = true;
	    }
	}
	
	var flagtdvalue = true;
	function checkValueWidth(){
		$("#nextBut").removeAttr("disabled");
		var tdvalue = $("#tdvalue").val();
		
		var reg=/^[1-9]\d*$/;
		
	    var re = new RegExp(reg);
	    if (!re.test(tdvalue)) {
	    	flagtdvalue = false;
	    	layer.msg('只能输入正整数！', {icon: 2, time: 1500});
	    	return ;
	    }else{
	    	flagtdvalue = true;
	    }
	}
	
	var flagtdheight = true;
	function checkTDHeight(){
		$("#nextBut").removeAttr("disabled");
		var tdheight = $("#tdheight").val();
		
		var reg=/^[1-9]\d*$/;
		
	    var re = new RegExp(reg);
	    if (!re.test(tdheight)) {
	    	flagtdheight = false;
	    	layer.msg('只能输入正整数！', {icon: 2, time: 1500});
	    	return ;
	    }else{
	    	flagtdheight = true;
	    }
	}
	
	//下一步
	function getNextTable(){
		
		if(flagtdkey&&flagtdvalue&&flagtdheight){
			
			var arrarr = new Array();
			array2.splice(0,array2.length);
			for(var i=0;i<row;i++){
				for(var j=0;j<col;j++){
					
					var r = "index"+i+"index"+j;
					
					var a = $("#"+r).find("option:selected").val();
					var ss = $("#"+r+" option:checked").text();
					
					
					array2.push(r);	
					arrarr.push(a);	
					
				}
				
			}
			
			$("#nextBut").attr("disabled","disabled");
			$(".sele").attr("disabled","disabled");
			
			 $("#main").attr("style","visibility:visible;");//显示div
			 $("#sunButt").attr("style","visibility:visible;");//显示div
			 
				//$("#but1").attr("style","display:block;");
			 
				//$("#but1").css("display","");
			 
			 //alert(array2);
			 arrAll = array2.concat(arrarr);
			 
			 arrAllItemIds = array2.concat(arrItemIds);
			 
			//document.getElementById("maingrid").innerHTML = "";
			 
			var tdkey = $("#tdkey").val();
			 var tdvalue = $("#tdvalue").val();
			 var tdheight = $("#tdheight").val();
			 
			 tdArr.splice(0,tdArr.length);
			 
			 tdArr.push(tdkey);
			 tdArr.push(tdvalue);
			 tdArr.push(tdheight);
		     getData(arrAll,id,tdArr);
		}else{
			 layer.msg('请检查行列宽高输入是否规范！', {icon: 2, time: 1500});
		 }
		
	}
	
	function getData(arrAll,id,tdArr)
    { 
    	var url = timeURL('${ctx}/cmdb/indicator/report/getData.do?tableItems='+arrAll+'&id='+id+'&tdArr='+tdArr);
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
		var rowData = { Rows: []};
		
		manager=gridMain = $("#maingrid").ligerGrid({
	        columns: 
	            [
				{ display:'指标项', name:'cellDispName', width:150, align:'left'},
				{ display:'英文名称', name:'cellEnName', width:150, align:'left'},
				{ display:'行位置', name:'rowNo', width:75, align:'right',editor: { type: 'text' }},
				{ display:'列位置', name:'colNo', width:75, align:'right',editor: { type: 'text' }},
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
	
		var tableArr = new Array();
		var tdkey = $("#tdkey").val();
		var tdvalue = $("#tdvalue").val();
		tableArr.push(tdkey);
		tableArr.push(tdvalue);
		tableArr.push(col);
		//alert(tableArr);
		$("#totalWidth").val(tableArr);
	
		$("#rowData").val(arrAll);
		$("#itemIds").val(arrAllItemIds);
		$("#id").val(id);
		
		var indicatorItemID = $("#indItemName").val();
		$("#indicatorItemID").val(indicatorItemID);
		
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
	    		
	    		if(itemsManager!=null){
	    			var itemsData = itemsManager.getData();
		    		$("#fieldsJsonStr").val(JSON.stringify(itemsData));
	    		}
	    		
				$("#formSave").submit();
			}else{
				layer.msg('请检查行、列位置数以及列表单元格宽度输入是否有效！', {icon: 2, time: 1500});
				 return ;
			}
			
	}
	
	//预览【暂时未用】(在当前页面生成table显示模式——结合最下边注销的form表单使用[注意方法名称对应body中的方法名])
	function showNowHtml(){
<%--		$("#rowData").val(arrAll);--%>
<%--		$("#itemIds").val(arrAllItemIds);--%>
<%--		$("#id").val(id);--%>

		var rows = gridMain.getData();
		
		var rowarray=new Array();
		for(var i=0;i<rows.length;i++){
			rowarray.push(rows[i].rowNo+""+rows[i].colNo);
		}
		
			if(regflag1&&regflag2){
				
				var data = manager.getData();
				$("#preViewjsonStr").val(JSON.stringify(data));
	    		
	    		var  nary=rowarray.slice().sort();
	    		for(let i=0;i<rowarray.length;i++){
	    		    if (nary[i]==nary[i+1]){
	    		        layer.msg('行、列位置数组合不能重复！', {icon: 2, time: 1500});
	    		        return ;
	    		    }
	    		} 
	    		
				$("#showHtm").attr("style","visibility:visible;");//显示div
				var jsonStr = JSON.stringify(data);
				var heig = row * 50 ;
				$("#preViewform").submit();
				
			}else{
				layer.msg('请检查行、列位置数输入是否有效！', {icon: 2, time: 1500});
			}
	}
	
	//预览(弹窗显示table模式)
	function showHtml(){
		
		//***//
		
		var itemId= $("#indItemName").val();//指标项ID
		
		var tdkey = $("#tdkey").val();
		var tdvalue = $("#tdvalue").val();
		var listTdheight = $("#listTdheight").val();
		var tableWidth = (parseInt(tdkey)+parseInt(tdvalue))*col;	//生成列表格式表格的总宽度
		//***//
		
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
			        layer.msg('行、列位置数组合不能重复！', {icon: 2, time: 1500});
			        return ;
			    }
			} 
			
		var preViewjsonStr = JSON.stringify(data);
		var itemsjsonStr = null;
		if(itemsManager!=null){
			var itemsData = itemsManager.getData();
			$("#itemsjsonStr").val(JSON.stringify(itemsData));
			itemsjsonStr = JSON.stringify(itemsData);
		}
			
			$.ajax({
				type: 'POST',
				url: '${ctx}/cmdb/indicator/report/preViewTable.do',//发送请求
				data: {preViewjsonStr:preViewjsonStr,itemsjsonStr:itemsjsonStr,tableWidth:tableWidth,itemId:itemId},
				dataType : "html",
				success: function(result) {
					var htmlCont = result;//返回的结果页面
					layer.open({
						type: 1,//弹出框类型
						title: '样式效果预览',
						shadeClose: false, //点击遮罩关闭层
						area : ['95%' , '50%'],
						shift:1,
						content: htmlCont,//将结果页面放入layer弹出层中
						success: function(layero, index){
									        	  
						}
					});
				}
			});
			
		}else{
			layer.msg('请检查行、列位置数以及列表单元格宽度输入是否有效！', {icon: 2, time: 1500});
		}
			
	}
	
	function  getRadio(){ 
   		var e  =  event.srcElement;
        if(e.value=='0'){	//是
        	$("#listBut").attr("style","visibility:visible;");//显示div
        	$("#sunButt").attr("style","visibility:hidden;");//隐藏预览和提交按钮
        	$("#listCell").removeAttr("disabled");
	    }else if(e.value=='1'){		//否
	    	$("#listBut").attr("style","visibility:hidden;");
	    	$("#listTab").attr("style","visibility:hidden;display:none");
	    	$("#itemsmain").attr("style","visibility:hidden;display:none");
	    	itemsManager = null;
	    	$("#sunButt").attr("style","visibility:visible;");//显示预览和提交按钮
	    }
 	}
	
	//点击“设置列表样式”按钮
	function showList(){
		$("#listTab").attr("style","visibility:visible;");//显示div
		$("#listCell").attr("disabled","disabled");
		getArray2DIndItemName();
		$("#sunButt").attr("style","visibility:hidden;");//隐藏预览和提交按钮
	}
	
	function getArray2DIndItemName(){
		 $.getJSON(
	              "${ctx}/cmdb/indicator/report/getArray2DIndItemName.do",
	              function (result) {
	                  if (result.items != null) {
	                      //$("#indItemName").empty();
	                      for (var i = 0; i < result.items.length; i++) {
	                          var item = result.items[i];
	                          //alert(item.indItemID+"**");
	                          //alert('${indItems.indItemID}'+"***");
	                          
	                          if(item.indItemID=='${indItems.indItemID}'){
	                            $("#indItemName").append("<option  value=\"" + item.indItemID + "\" selected>" + item.indItemName + "</option>");
	                          }else{
	                            $("#indItemName").append("<option  value=\"" + item.indItemID + "\">" + item.indItemName + "</option>");
	                          }
	                          
	                      }
	                     getItemName('${indItems.indItemID}');
	                  }
	              });
		 
	}
	
	//指标项名称
	function getItemName(id){
		var indItemName=$("#indItemName").find("option:selected").text();
		$("#indItemName").val(id);
	}
	
	//列表样式确定按钮
	function getListTable(){
<%--		var field = $("#field option:checked").text();	//字段数--%>
<%--		$("#field").attr("disabled","disabled");--%>
		//$("#listTdheight").attr("readonly","readonly");
		
		var listTdheight = $("#listTdheight").val();
		if(listTdheight!=null&&listTdheight!=""){
			var reg=/^[1-9]\d*$/;
			
		    var re = new RegExp(reg);
		    if (!re.test(listTdheight)) {
		    	//flagtdkey = false;
		    	layer.msg('只能输入大于0的整数！', {icon: 2, time: 1500});
		    	return ;
		    }else{
		    	var itemId= $("#indItemName").val();
				//alert(itemId);
				var a = $("#indItemName").find("option:selected").text();
				//alert(a);
				//$("#indItemName").attr("disabled","disabled");
				 
				
				//$("#listFine").css("display","none");
				
				//$("#listtb tr").remove();
				$("#itemsmain").attr("style","visibility:visible;");//显示div
				
				$("#sunButt").attr("style","visibility:visible;");//显示预览和提交按钮
				//createListTable();
				getEditData(itemId);
		    }
		}else{
			layer.msg('请输入列表单元格高度！', {icon: 2, time: 1500});
	    	return ;
		}
		
	}
	
	
	function getEditData(itemId)
	  { 
		var tdkey = $("#tdkey").val();
		var tdvalue = $("#tdvalue").val();
		var listTdheight = $("#listTdheight").val();
		var tableWidth = (parseInt(tdkey)+parseInt(tdvalue))*col;	//生成列表格式表格的总宽度
		
	  	var url = timeURL('${ctx}/cmdb/indicator/report/getItemFieldsData.do?itemId='+itemId+'&tableWidth='+tableWidth+'&listTdheight='+listTdheight);
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
	<input type="hidden" name="totalWidth" id="totalWidth" />
	<input type="hidden" name="indicatorItemID" id="indicatorItemID" />
	
	<input type="hidden" name="jsonStr" id="jsonStr" />
	<input type="hidden" name="fieldsJsonStr" id="fieldsJsonStr" />
	<div>
		<table align="center" >
			
			<tbody id="tb2">
				
			</tbody>
			
			<tr style="height: 10px;"></tr>
			
		</table>
		<table align="center">
			<tr>
				<td style="width: 150px;" align="right" class="td_input">指标名称单元格宽度：</td>
				<td style="width: 150px;" class="td_input"><input style="text-align:right;" type="text" value="${tdkey}" id="tdkey" onchange="checkKeyWidth();" name="tdkey" /></td>
				<td style="width: 150px;" align="right" class="td_input">指标值单元格宽度：</td>
				<td style="width: 150px;" class="td_input"><input style="text-align:right;" type="text" value="${tdvalue}" id="tdvalue" onchange="checkValueWidth();" name="tdvalue" /></td>
				<td style="width: 150px;" align="right" class="td_input">单元格高度：</td>
				<td style="width: 150px;" class="td_input"><input style="text-align:right;" type="text" value="${tdheight}" id="tdheight" onchange="checkTDHeight();" name="tdvalue" /></td>
				
			</tr>
			
			<tr style="height: 15px;"></tr>
			
			<tbody id="tb3">
				
			</tbody>
			
			<tr style="height: 15px;"></tr>
			
		</table>
	</div>
	
	<div id="main">
		<div id="maingrid" align="center" style="width: 900px;margin:0 0 0 50px;">
			
		</div>
	</div>
	
	<div style="height: 15px;"></div>
	
	<%-- 修改从这里开始 --%>
	
	<div id="showradio" style="width: 30%;height: 20px;" align="center">
		是否设置列表样式
		<span onclick="getRadio();">
			<input id="inpu1" type="radio" name="checkradio" value="0" >是
	    	<input id="inpu2" type="radio" name="checkradio" value="1">否
		</span>
	</div>
	
	<div id="listBut" align="center" style="height: 25px;">
		<button style="width: 100px;height: 20px;border:1px solid #000000;background-color: #F0FFFF; " id="listCell" type="button" onclick="showList();" >
                                    设置列表样式
        </button>
	</div>
	
	<div id="listTab" align="center">
		<table align="center" >
			<thead>
				<tr style="height: 10px;"></tr>
				
	            <tr>
<%--	                <td align="right">字段数:</td>--%>
<%--	                <td>--%>
<%--	                	<select name="field" id="field" style="width: 150px;">--%>
<%--							<option value="2">2</option>--%>
<%--							<option value="4" selected="selected">4</option>--%>
<%--							<option value="6">6</option>--%>
<%--							<option value="8">8</option>--%>
<%--							<option value="10">10</option>--%>
<%--	                    </select>--%>
<%--					</td>--%>
					<td align="right">指标项选择:</td>
	                <td>
	                	<select name="indItemName" id="indItemName" style="width:150px" > 
						</select>
					</td>
					
	                <td align="right" >列表单元格高度:</td>
					<td style="width: 150px;" class="td_input"><input class="td_input2" style="text-align:right;" type="text" value="${height }" id="listTdheight" onchange="checkListTDHeight();" name="listTdheight" /></td>
				
					<td></td>
					<td>
	                	<input type="button" id="listFine" style="width: 50px;height: 25px;border:1px solid #000000;background-color: #F0FFFF; " value="确定" onclick="getListTable();" />
	                </td>
	                
				</tr>
				
				<tr style="height: 15px;"></tr>
			</thead>
			
<%--			<tbody id="listtb">--%>
<%--				--%>
<%--			</tbody>--%>
			
			</table>
	</div>
	
	<div id="itemsmain">
		<div id="itemsMaingrid" align="center" style="width: 900px;margin:0 0 0 50px;">
			
		</div>
	</div>
	
	<div style="height: 15px;"></div>
	
	<div id="sunButt" align="center" style="height: 25px;">
		<button style="width: 50px;height: 20px;border:1px solid #000000;background-color: #F0FFFF; " id="but0" type="button" onclick="showHtml();" >
                                    预览
        </button>
        <button style="width: 50px;height: 20px;border:1px solid #000000;background-color: #F0FFFF; " id="but1" type="button" onclick="submitForm();" >
                                    保存
        </button>
    </div>
    
    </form>  
  
    <div style="height: 15px;"></div>
    
<%--<form id="preViewform" action="${ctx}/cmdb/indicator/report/preViewTable.do" method="post" target="form_iframe">--%>
<%--	<input type="hidden" name="preViewjsonStr" id="preViewjsonStr" />--%>
<%--	<div id="htm" style="height: 120px;">--%>
<%--		<div id="showHtm" align="center" style="width: 1000px;height:200px;margin:0 0 0 10px;">--%>
<%--			<iframe  name="form_iframe" width="985" height="150" scrolling="auto" ></iframe>--%>
<%--		</div>--%>
<%--	</div>--%>
<%--	--%>
<%--</form>--%>
</body>
</html>

