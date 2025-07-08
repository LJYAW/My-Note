<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>指标项详情</title>
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
<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css"/>
<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet"/>
<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>

<script src="${ctx }/static/assets/js/jquery.min.js"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script src="${ctx }/static/assets/js/H-ui.admin.js"></script>
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/static/jquery-validationEngine/help.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
<script src="${ctx }/static/assets/js/resize.js"></script>
<script src="${ctx }/static/assets/js/bootbox.js"></script>

<script type="text/javascript">
	$(function ()
	{		
		checkValueType();
		
		$("#indClassCode").attr("disabled","disabled");
		$("#valueType").attr("disabled","disabled");

		//$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("input").filter(".ip1").ligerTextBox({ width: 200 });
		//$("#formSave").validationEngine({promptPosition: "centerRight"});	
		
		

	});
	
	function checkValueType(){
		  var valueType=$("#valueType").find("option:selected").text();
			if(valueType!="二维数组"){
				$(".show_td").css("display","none");
				$(".show_td2").css("display","");
			}else{
				$(".show_td2").css("display","none");
				$(".show_td").css("display","");
				pageInit();
			}
	  }
	
	function pageInit(){
		jQuery("#list").jqGrid( {
			 data:  ${jsonListData},
				//  datatype:从服务器端返回的数据类型，默认xml。可选类型：xml，local(客户端数据（数组）)，json，jsonnp，script，xmlstring，jsonstring，clientside
				 datatype : "local", 
     
     
	 colNames : [ '字段英文名', '字段名', '字段类型', '字段长度', '小数位数', '度量单位' ],
	 colModel : [ 
	        	 {name : 'fieldNameEn',index : 'fieldNameEn',width : 150},
	        	 {name : 'fieldName',index : 'fieldName',width : 150}, 
	        	 {name : 'valueType',index : 'valueType',width : 100},
	        	 {name : 'length',index : 'length',width:100},
	        	 {name : 'decimals',index : 'decimals',width : 100},
	        	 {name : 'measureUnit',index : 'measureUnit',width : 100
	        	}], 
	        	 	viewrecords: true,
	        	 	rowNum : 50, rowList : [ 50, 100, 200 ], 
			 		//pager : '#pager',
			 		height:200,
			 		width:700,
					altRows: true,
					toolbar:[true,'top'],
					multiselect: false,
					loadComplete : function() {
						var table = this;
							setTimeout(function(){
							updatePagerIcons(table);
							}, 0);
					},
	        	});
		jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
							$("#t_list").css({"height":30});
		            $("#btn").appendTo("#t_list");

	}
  
</script>

</head>
<body> 
		<table align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
            <tr>
                <td align="right" nowrap>监测对象分类:</td>
                <td>
                	<s:resSelect id="indClassCode" name="indClassCode" value="${items.indClassCode}" code="Cmdb_IndicatorClass"  ltype="select" style="width:198px"/>
                </td>
                <td></td>
                
                <td align="right">指标组名称:</td>
                <td>
                	<input readonly="readonly" id="indGroupID" name="indGroupID" value="${items.indGroupName}" type="text" ltype="text" class="ip1" />
				</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">指标英文名称:</td>
                <td><input readonly="readonly" id="indicatorItem" name="indicatorItem" value="${items.indicatorItem}" type="text" ltype="text" class="ip1" /></td>
                <td ></td>
                
                <td align="right">指标显示名称:</td>
                <td><input readonly="readonly" id="indItemName" name="indItemName" value="${items.indItemName}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">取值类型:</td>
                 <td>
                 	<s:resSelect id="valueType" name="valueType" value="${items.valueType}" code="Cmdb_IndValueType"  ltype="select"  style="width:198px"/>
                 </td>
                <td></td>     
                
                <td class="show_td" align="right">字段个数:</td>
                <td class="show_td"><input readonly="readonly" id="fieldQty" name="fieldQty" value="${items.fieldQty}" type="text" ltype="text" class="ip1" /></td>
                <td class="show_td"></td>   
                
                <td class="show_td2" align="right">度量单位:</td>
                <td class="show_td2"><input readonly="readonly" id="measureUnit" name="measureUnit" value="${items.measureUnit}" type="text" ltype="text" class="ip1" /></td>
                <td class="show_td2"></td>                
            </tr>
            <tr class="show_td2">
                <td align="right">取值长度:</td>
                <td><input id="length" readonly="readonly" name="length" value="${items.length}" type="text" ltype="text" class="ip1"/></td>
                <td ></td>
                
                <td align="right">小数位数:</td>
                <td><input readonly="readonly" id="decimals" name="decimals" value="${items.decimals}" type="text" ltype="text" class="ip1"/></td>
                <td ></td>
            </tr>
            <tr>
                <td align="right">指标说明:</td>
                <td colspan=4><input readonly="readonly" id="remark" name="remark" value="${items.remark}" type="text" class="ip2" ltype="text"  /></td>
                <td></td>
            </tr>
            
		</table>
		<div class="col-xs-12" style="overflow: hidden;">
		
			<table id="list"></table>
			
<%--			<div id="pager"></div>--%>
	
		</div>
</body>
</html>

