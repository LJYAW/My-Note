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
<title>设备巡检命令---添加修改</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
$(function (){
		var flag;
var vendorId='${prodChkCmds.vendorId}';
var action='${action}';
<c:if test="${result=='success'}">
var action = '${action}';
window.parent.parent.saveOK(action);
window.parent.parent.closeDlg();
</c:if>

<c:if test="${result=='error'}">
$.ligerDialog.error('${message}');
</c:if>

refreshVendor();

$("#formSave input").filter(".ip1").ligerTextBox({width: 200});
$("input").filter(".ip2").ligerTextBox({ width: 553 });
$("#formSave").validationEngine({promptPosition: "centerRight"});	
$("#formSave").validationEngine({promptPosition: "centerRight"});
//表单验证
$("#formSave").click(function(check) {    
	if($("#formSave").validationEngine()){
	}else{
		check.preventDefault();//此处阻止提交表单  
	}
});

});






//刷新厂商
function refreshVendor() {
 $.getJSON(
     "${ctx}/cmdb/prodType/getVendor.do?date="+new Date(),
     function (result) {
         if (result.vendors != null) {
             $("#vendorId").empty();
             for (var i = 0; i < result.vendors.length; i++) {
                 var vendor = result.vendors[i];
                 if(vendor.vendorID=='${prodChkCmds.vendorId}'){
                  $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\" selected>" + vendor.dispName + "</option>");
                 }else{
                  $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                 }
                 
             }
             refreshClassCode();
            refreshOsType();
         }
     });
}

//产品分类
function refreshClassCode() {
var vendorId=$("#vendorId").find("option:selected").val();
 $.getJSON(
     "${ctx}/cmdb/prodType/getCodeByVendor.do?vendorId="+vendorId,
     function (result) {
         if (result.pClass != null) {
             $("#devClassCode").empty();
             for (var i = 0; i < result.pClass.length; i++) {
                 var pClass = result.pClass[i];
                 if(pClass.prodClassCode=='${prodChkCmds.devClassCode}'){
                   $("#devClassCode").append("<option  value=\"" + pClass.prodClassCode + "\" prodClassID=\"" + pClass.prodClassID + "\" selected>" + pClass.prodClassName + "</option>");
                 }else{
                   $("#devClassCode").append("<option  value=\"" + pClass.prodClassCode + "\" prodClassID=\"" + pClass.prodClassID + "\">" + pClass.prodClassName + "</option>");
                 }
                 
             }
             refreshProdType();
             refreshClassName();
         }
     });
}

//刷新分类名称
function refreshClassName(){
var devClassName=$("#devClassCode").find("option:selected").text();
$("#devClassName").val(devClassName);
}

//产品类型
function refreshProdType() {
var vendorId=$("#vendorId").find("option:selected").val();
var prodClassCode=$("#devClassCode").find("option:selected").val();
 $.getJSON(
     "${ctx}/cmdb/prodType/getProdTypeByVendorAndClassCode.do?vendorId="+vendorId+"&prodClassCode="+prodClassCode,
     function (result) {
         if (result.prodType != null) {
             $("#devTypeCode").empty();
             for (var i = 0; i < result.prodType.length; i++) {
                 var prodType = result.prodType[i];
                 if(prodType.typeCode=='${prodChkCmds.devTypeCode}'){
                   $("#devTypeCode").append("<option  value=\"" + prodType.typeCode + "\" typeId=\"" + prodType.prodTypeID + "\" selected>" + prodType.dispName + "</option>");
                 }else{
                   $("#devTypeCode").append("<option  value=\"" + prodType.typeCode + "\" typeId=\"" + prodType.prodTypeID + "\">" + prodType.dispName + "</option>");
                 }
                 
             }
            //refreshProdSeries();
            refreshProdModel();
            refreshTypeName();
         }
     });
}	

//刷新类型名称
function refreshTypeName(){
var typeName=$("#devTypeCode").find("option:selected").text();
$("#devTypeName").val(typeName);
}
//刷新产品系列
//function refreshProdSeries(){
//var vendorId=$("#vendorID").find("option:selected").val();
//var prodClassCode=$("#prodClassCode").find("option:selected").val();
//var prodTypeId=$("#prodTypeID").find("option:selected").val();
//$.getJSON(
//      "${ctx}/cmdb/vendorProdSeries/getData.do?vendorId="+vendorId+"&prodClassMode="+prodClassCode+"&prodTypeId="+prodTypeId,
//      function (result) {
//          if (result.seriesList != null) {
//              $("#prodSeriesID").empty();
//              for (var i = 0; i < result.seriesList.length; i++) {
//                  var series = result.seriesList[i];
//                  if(series.prodSeriesID=='${prodModel.prodSeriesID}'){
//                    $("#prodSeriesID").append("<option  value=\"" + series.prodSeriesID + "\" selected>" + series.prodSeries + "</option>");
//                  }else{
//                    $("#prodSeriesID").append("<option  value=\"" + series.prodSeriesID + "\">" + series.prodSeries + "</option>");
//                  }
                 
//              }
            
//          }
//      });

//}

//刷新产品型号
function refreshProdModel(){
var vendorId=$("#vendorId").find("option:selected").val();
var prodClassCode=$("#devClassCode").find("option:selected").val();
var prodTypeId=$("#devTypeCode").find("option:selected").attr("typeId");
$.getJSON(
     "${ctx}/cmdb/vendorProdModel/getData.do?vendorId="+vendorId+"&prodClassMode="+prodClassCode+"&prodTypeId="+prodTypeId,
     function (result) {
         if (result.modelOIDlist != null) {
             $("#prodModelId").empty();
             $("#prodModelId").append("<option value=\"-1\" modelOID=\"X\" selected>所有型号</option>");
             for (var i = 0; i < result.modelOIDlist.length; i++) {
                 var model = result.modelOIDlist[i];
                 if(model.prodModelID=='${prodChkCmds.prodModelId}'){
                   $("#prodModelId").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.prodModelOid + "\" selected>" + model.prodModelName + "</option>");
                 }else{
                   $("#prodModelId").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.prodModelOid + "\">" + model.prodModelName + "</option>");
                 }
                 
             }
            refreshModelName();
         }
     });
}

//刷新型号名称
function refreshModelName(){
	var prodModelId=$("#prodModelId").find("option:selected").val();
if(prodModelId=="-1"){
	$("#prodModel").val("所有型号");
	$("#modelOID").val("X.X");
}else{
	var prodModel=$("#prodModelId").find("option:selected").text();
	$("#prodModel").val(prodModel);
	var modelOID=$("#prodModelId").find("option:selected").attr("modelOID");
	$("#modelOID").val(modelOID);
}

}


//根据厂商名称  查   操作系统类型 
function refreshOsType(){
	 var vendorID=$("#vendorId").find("option:selected").val();
	    $.getJSON(
	              "${ctx}/cmdb/vendorOSVersion/getOSNameById.do?vendorID="+vendorID,
	              function (result) {
	                  if (result.vendorOSVersionlist != null) {
	                	  
	                      $("#osType").empty();
	                      for (var i = 0; i < result.vendorOSVersionlist.length; i++) {
	                          var osType = result.vendorOSVersionlist[i];
	                          if(osType.osType=='${prodChkCmds.osType}'){
	                           $("#osType").append("<option  value=\"" + osType.osType + "\" selected>" + osType.osType + "</option>");
	                          }else{
	                           $("#osType").append("<option  value=\"" + osType.osType + "\" >" + osType.osType + "</option>");
	                          }
	                          
	                      }
	                  }
	                  refreshOsVersion();
	                  //refreshOsName();
<%--	                  var osTypeName=$("#osTypeCode").find("option:selected").text();--%>
<%--	                  $("#osTypeName").val(osTypeName);--%>
	              });
}



//根据厂商名称和操作系统类型   查 软件版本
function refreshOsVersion(){
var vendorId=$("#vendorId").find("option:selected").val();
var osType=$("#osType").find("option:selected").val();
 $.getJSON(
     "${ctx}/cmdb/vendorOSVersion/getOsVersionByVendorIdAndOsType.do?vendorID="+vendorId+"&osName="+osType,
     function (result) {
         if (result.list != null) {
             $("#osVersion").empty();
             $("#osVersion").append("<option value=\"V.R\" selected>V.R(所有版本)</option>");
             for (var i = 0; i < result.list.length; i++) {
                 var prodType = result.list[i];
                 if(prodType.osVersion=='${prodChkCmds.osVersion}'){
                   $("#osVersion").append("<option  value=\"" + prodType.osVersion + "\" typeId=\"" + prodType.osVersion + "\" selected>" + prodType.osVersion + "</option>");
                 }else{
                   $("#osVersion").append("<option  value=\"" + prodType.osVersion + "\" typeId=\"" + prodType.osVersion + "\">" + prodType.osVersion + "</option>");
                 }
             }
             refeshosFeature();
         }
     });
}

function refeshosFeature(){
  var vendorId=$("#vendorId").find("option:selected").val();
  var osType=$("#osType").find("option:selected").val();
   var osVersion=$("#osVersion").find("option:selected").val();
 $.getJSON(
     "${ctx}/cmdb/vendorOSVersion/getOsFeature.do?vendorID="+vendorId+"&osName="+osType+"&osVersion="+osVersion,
     function (result) {
         if (result.list != null) {
             $("#osFeature").empty();
             for (var i = 0; i < result.list.length; i++) {
                 var osFeature = result.list[i].osFeature;
                 if(osFeature=='${prodChkCmds.osFeature}'){
                   $("#osFeature").append("<option  value=\"" + osFeature + "\"  selected>" + osFeature + "</option>");
                 }else{
                   $("#osFeature").append("<option  value=\"" + osFeature + "\" >" + osFeature + "</option>");
                 }
                 
             }
         }
     });
}

//保存并继续
function saveAndContinue(){
$("#action").val("saveAndContinue");
$("#formSave").submit();
}

//保存
function add(){
if("edit" != '${action}'){
	$("#action").val("add");
}
$("#formSave").submit();
}

	
$(document).ready(function() {
$(".required").each(function() {
	var $this  = $(this);
	$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
});
});

function getIndType(flag){  //0
if(flag==0){
$("input[name='indcheck']:eq(0)").attr("checked",0); 
$("input[name='indcheck']:eq(1)").removeAttr("checked");
}else{
$("input[name='indcheck']:eq(1)").attr("checked",1); 
$("input[name='indcheck']:eq(0)").removeAttr("checked");
}

var indcheck=$('input[name="indcheck"]:checked ').val(); 
if(indcheck==0){  //通用
$("#special1").hide();
$("#special2").hide();
}else{    //专用指标
$("#special1").show();
$("#special2").show();
}
	  }
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodChkCmds" action="${ctx}/cmdb/prodChkCmds/save.do" method="post">
	<input type="hidden" id="action" name="action" value="${action}"/>
	<input type="hidden" name="id" value="${id}"/>
	
	<input type="hidden" id="devClassName" name="devClassName" value=""/>
	<input type="hidden" id="devTypeName" name="devTypeName" value=""/>
	<input type="hidden" id="prodModel" name="prodModel" value=""/>
	<input type="hidden" id="modelOID" name="modelOID" value=""/>
	<input type="hidden" id="status" name="status" value="0"/>
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
                <td align="right">厂商名称：</td>
                <td>
                 <select id="vendorId" name="vendorId" style="width:202px" onchange="refreshClassCode();refreshOsType();">
                    <option value="-1">请选择</option>
                 </select>
                </td>
                <td></td>
               <td align="right">产品分类 ：</td>
                <td>
                  <select id="devClassCode" name="devClassCode" style="width:202px" onchange="refreshProdType();refreshClassName()">
                      <option value="-1">请选择</option>
                  </select>
                </td>
                <td></td>
            </tr>
             <tr>
                <td align="right">产品类型：</td>
                <td>
                 <select id="devTypeCode" name="devTypeCode" style="width:202px" onchange=" refreshProdModel();refreshTypeName();">
                    <option value="-1">请选择</option>
                 </select>
                </td>
               <td></td>
               <td align="right">产品型号：</td>
               <td>
                  <select id="prodModelId" name="prodModelId" style="width:202px" onchange="refreshModelName();">
                  </select>
               </td>  
               <td></td>
            </tr>
            
			
            <tr>
                <td align="right" nowrap>操作系统类型：</td>
                <td>
                 <select id="osType" name="osType" style="width:202px" onchange="refreshOsVersion()" >
                    
                 </select>
                </td>
               <td></td>
               <td align="right">软件版本：</td>
               <td>
                  <select id="osVersion" name="osVersion" style="width:202px" onchange="refeshosFeature()">
                  </select>
               </td>  
               <td></td>
            </tr>
            
             <tr>
                <td align="right" nowrap>操作系统特征：</td>
                <td>
                 <select id="osFeature" name="osFeature" style="width:202px" >
                    
                 </select>
                </td>
               <td></td>
            </tr>
            
            
              <tr>
                <td align="right">采集命令：</td>
                <td colspan="4"><input id="checkCmd" name="checkCmd" value="${prodChkCmds.checkCmd}" type="text" ltype="text" class=" ip2"/></td>
                <td ></td>
            </tr>
            <tr>
                <td align="right">命令用途：</td>
                <td colspan="4"><input id="cmdUsage" name="cmdUsage" value="${prodChkCmds.cmdUsage}" type="text" ltype="text" class=" ip2"/></td>
                <td ></td>
            </tr>
            <tr>
                <td align="right">结果样本：</td>
               <td colspan="4">
                <textarea style="width:553px;height:200px;"id="resultSample" name="resultSample">${prodChkCmds.resultSample}</textarea>
                </td>
                <td ></td>
            </tr>
            <tr>
                <td align="right">备注：</td>
                <td colspan="4"><input id="remark" name="remark" value="${prodChkCmds.remark}" type="text" ltype="text" class=" ip2"/></td>
                <td ></td>
            </tr>
           
            
          
            
            <tr>
            	<td>&nbsp;&nbsp;</td>
            </tr>
      
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

