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
<title>指标采集命令另存为</title>
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
<c:if test="${result=='success'}">
			window.parent.parent.saveOK();     
			window.parent.closeDlg();
		</c:if>

<c:if test="${action=='saveAs'}">
			window.parent.parent.saveOK();     
			window.parent.closeDlg();
		</c:if>
refreshProdType();
refreshOsType();
    getParseMode();


	// var parseMode=$("#parseMode").val();
    // if(parseMode==1){
    // 	$("#parseMode1").val("正则表达式");
    // 	$(".line1").show();
    // 	$(".line2").hide();
    // 	$(".line3").hide();
    // }else if(parseMode==2){
    // 	$("#parseMode1").val("字符串匹配");
    // 	$(".line1").hide();
    // 	$(".line2").show();
    // 	$(".line3").hide();
    // }else if(parseMode==3){
    // 	$("#parseMode1").val("字符分割");
    // 	$(".line1").hide();
    // 	$(".line2").hide();
    // 	$(".line3").show();
    // }
});

function getParseMode(){
    var parseMode=$("#parseMode").val();
    if(parseMode==1){
        // $("#parseMode1").val("正则表达式");
        $(".line1").show();
        $(".line2").hide();
        $(".line3").hide();
    }else if(parseMode==2){
        // $("#parseMode1").val("字符串匹配");
        $(".line1").hide();
        $(".line2").show();
        $(".line3").hide();
    }else if(parseMode==3){
        // $("#parseMode1").val("字符分割");
        $(".line1").hide();
        $(".line2").hide();
        $(".line3").show();
    }
}

//产品类型
function refreshProdType() {
var vendorId='${prodChkCmdItems.vendorID}';
var prodClassCode='${prodChkCmdItems.devClassCode}';
 $.getJSON(
     "${ctx}/cmdb/prodType/getProdTypeByVendorAndClassCode.do?vendorId="+vendorId+"&prodClassCode="+prodClassCode,
     function (result) {
         if (result.prodType != null) {
             $("#resTypeCode").empty();
             for (var i = 0; i < result.prodType.length; i++) {
                 var prodType = result.prodType[i];
                 if(prodType.typeCode=='${prodCmdCheckItems.resTypeCode}'){
                   $("#resTypeCode").append("<option  value=\"" + prodType.typeCode + "\" typeId=\"" + prodType.prodTypeID + "\" selected>" + prodType.dispName + "</option>");
                 }else{
                   $("#resTypeCode").append("<option  value=\"" + prodType.typeCode + "\" typeId=\"" + prodType.prodTypeID + "\">" + prodType.dispName + "</option>");
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
var typeName=$("#resTypeCode").find("option:selected").text();
$("#resTypeName").val(typeName);
}
//产品型号
function refreshProdModel(){
var vendorId='${prodChkCmdItems.vendorID}';
var prodClassCode='${prodChkCmdItems.devClassCode}';
var prodTypeId=$("#resTypeCode").find("option:selected").attr("typeId");
$.getJSON(
     "${ctx}/cmdb/vendorProdModel/getData.do?vendorId="+vendorId+"&prodClassMode="+prodClassCode+"&prodTypeId="+prodTypeId,
     function (result) {
         if (result.modelOIDlist != null) {
             $("#prodModelID").empty();
             $("#prodModelID").append("<option value=\"-1\" modelOID=\"X.X\" selected>所有型号</option>");
             for (var i = 0; i < result.modelOIDlist.length; i++) {
                 var model = result.modelOIDlist[i];
                 if(model.prodModelID=='${prodCmdCheckItems.prodModelID}'){
                   $("#prodModelID").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.prodModelOid + "\" selected>" + model.prodModelName + "</option>");
                 }else{
                   $("#prodModelID").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.prodModelOid + "\">" + model.prodModelName + "</option>");
                 }
                 
             }
            refreshModelName();
         }
     });
}
//刷新型号名称
function refreshModelName(){
	var prodModelId=$("#prodModelID").find("option:selected").val();
if(prodModelId=="-1"){
	$("#prodModel").val("所有型号");
	$("#modelOID").val("X.X");
}else{
	var prodModel=$("#prodModelID").find("option:selected").text();
	$("#prodModel").val(prodModel);
	var modelOID=$("#prodModelID").find("option:selected").attr("modelOID");
	$("#modelOID").val(modelOID);
}

}
//操作系统类型
//根据厂商名称  查   操作系统类型 
function refreshOsType(){
	var vendorID='${prodChkCmdItems.vendorID}';
	    $.getJSON(
	              "${ctx}/cmdb/vendorOSVersion/getOSNameById.do?vendorID="+vendorID,
	              function (result) {
	                  if (result.vendorOSVersionlist != null) {
	                	  
	                      $("#osType").empty();
	                      for (var i = 0; i < result.vendorOSVersionlist.length; i++) {
	                          var osType = result.vendorOSVersionlist[i];
	                          if(osType.osName=='${prodCmdCheckItems.osType}'){
	                           $("#osType").append("<option  value=\"" + osType.osType + "\" selected>" + osType.osType + "</option>");
	                          }else{
	                           $("#osType").append("<option  value=\"" + osType.osType + "\" >" + osType.osType + "</option>");
	                          }
	                          
	                      }
	                  }
	                  refreshOsVersion();
	              });
}

//根据厂商名称和操作系统类型   查 软件版本
function refreshOsVersion(){
var vendorId='${prodChkCmdItems.vendorID}';
var osType=$("#osType").find("option:selected").val();
 $.getJSON(
     "${ctx}/cmdb/vendorOSVersion/getOsVersionByVendorIdAndOsType.do?vendorID="+vendorId+"&osName="+osType,
     function (result) {
         if (result.list != null) {
             $("#osVersion").empty();
             $("#osVersion").append("<option value=\"V.R\" selected>V.R(所有版本)</option>");
             for (var i = 0; i < result.list.length; i++) {
                 var prodType = result.list[i];
                 if(prodType.osVersion=='${prodCmdCheckItems.osVersion}'){
                   $("#osVersion").append("<option  value=\"" + prodType.osVersion + "\" typeId=\"" + prodType.osVersion + "\" selected>" + prodType.osVersion + "</option>");
                 }else{
                   $("#osVersion").append("<option  value=\"" + prodType.osVersion + "\" typeId=\"" + prodType.osVersion + "\">" + prodType.osVersion + "</option>");
                 }
             }
             refreshOsFeature();
         }
      
     });
 
}

function refreshOsFeature(){
	var vendorId=$("#vendorID").val();
	var devClassCode=$("#devClassCode").val();
	var devTypeCode=$("#resTypeCode").find("option:selected").val();
	var osType=$("#osType").find("option:selected").val();
	var osVersion=$("#osVersion").find("option:selected").val();
	var modelOID=$("#prodModelID").find("option:selected").attr("modelOID");
	$.getJSON(
		     "${ctx}/cmdb/prodChkCmds/getOsFeature.do?vendorId="+vendorId+"&devClassCode="+devClassCode+"&devTypeCode="+devTypeCode+"&modelOID="+modelOID+"&osType="+osType+"&osVersion="+osVersion,
		     function (result) {
		         if (result.list != null) {
		             $("#osFeature").empty();
		             for (var i = 0; i < result.list.length; i++) {
		                 var prodType = result.list[i];
		                 if(prodType!=null&&prodType!="defined"){
		                   	var ressmple=prodType.resultSample.replace(/"/g,"");
		                 if(prodType.checkCmd=='${prodCmdCheckItems.checkCmd}'){
		                    $("#osFeature").append("<option  value=\"" + prodType.osFeature + "\"  selected>" + prodType.osFeature + "</option>");
		                 }else{
		                    $("#osFeature").append("<option  value=\"" + prodType.osFeature + "\" >" + prodType.osFeature + "</option>");
		                 }
		                 }
		             }
		             
		         }
		     });
	
}

function changeParseMode() {
    var parseMode = $("#parseMode").find("option:selected").val();
    if(parseMode==1){
        // $("#parseMode1").val("正则表达式");
        $(".line1").show();
        $(".line2").hide();
        $(".line3").hide();
    }else if(parseMode==2){
        // $("#parseMode1").val("字符串匹配");
        $(".line1").hide();
        $(".line2").show();
        $(".line3").hide();
    }else if(parseMode==3){
        // $("#parseMode1").val("字符分割");
        $(".line1").hide();
        $(".line2").hide();
        $(".line3").show();
    }
}

</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodChkCmdItems" action="${ctx}/cmdb/indicator/resIndCmdParse/saveAs.do" method="post">
	<%--<input type="hidden" id="parseMode" name="parseMode" value="${prodChkCmdItems.parseMode}"/>--%>
	<input type="hidden" id="vendorID" name="vendorID" value="${prodChkCmdItems.vendorID}"/>
	<input type="hidden" id="resTypeName" name="resTypeName" value=""/>
	<input type="hidden" id="prodModel" name="prodModel" value=""/>
	
	<input type="hidden" id="indGroupID" name="indGroupID" value="${prodChkCmdItems.indGroupID}">
	<input type="hidden" id="devClassCode" name="devClassCode"  value="${prodChkCmdItems.devClassCode}"/>
<!-- 	<input type="hidden" id="devTypeCode" name="devTypeCode"  value="${prodChkCmdItems.devTypeCode}"/> -->
	<input type="hidden" id="indClassCode" name="indClassCode"  value="${prodChkCmdItems.indClassCode}"/>
	   
<!-- 	<input type="hidden" id="prodModelId" name="prodModelId" value="${prodChkCmdItems.prodModelId}"/> -->
	<input type="hidden" id="modelOID" name="modelOID" value=""/>
	<input type="hidden" id="prodChkCmdId" name="prodChkCmdId" value="${prodChkCmdItems.prodChkCmdId}"/>
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
             
                 <td><input id="vendorId1" name="" type="text" value="${cmdbVendor.dispName}" class=" ip1" readonly="readonly"/></td>
                <td ></td>
               <td align="right">产品分类 ：</td>
     
                 <td><input id="resClassName" name="resClassName" value="${prodChkCmdItems.resClassName}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td ></td>
            </tr>
             <tr>
                <td align="right">产品类型：</td>
                 <td>
                 <select id="resTypeCode" name="resTypeCode" style="width:202px" onchange=" refreshProdModel();refreshTypeName()">
                  </select>
                 </td>
                <td ></td>
               <td align="right">产品型号：</td>
               <td>
               <select id="prodModelID" name="prodModelID" style="width:202px" onchange="refreshModelName();">
                  </select>
               </td>
                <td ></td>
            </tr>
            
			
            <tr>
                <td align="right">操作系统类型：</td>
               <td>
               <select id="osType" name="osType" style="width:202px" onchange="refreshOsVersion()">
                  </select>
               </td>
                <td ></td>
              
                  <td align="right">软件版本：</td>
               <td>
                  <select id="osVersion" name="osVersion" style="width:202px" onchange="refreshOsFeature()">
                  </select>
               </td>  
               <td></td>
            </tr>
            
             <tr>
               <td align="right">软件特征：</td>
               <td>
                  <select id="osFeature" name="osFeature" style="width:202px">
                  </select>
               </td>  
               <td></td>
            </tr>
            
            
             <tr>
                <td align="right">监测对象：</td>
                <td>
                <input id="indClassName" name="indClassName" value="${prodChkCmdItems.indClassName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
                <td></td>
                
                <td align="right">指标组：</td>
                <td>
                <input id="indGroupName" name="indGroupName" value="${prodChkCmdItems.indGroupName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">指标项：</td>
                <td>
                <input id="indItemEnName" name="indItemEnName" value="${prodChkCmdItems.indItemEnName}" type="text" ltype="text" class=" ip1"readonly="readonly"/>
                </td>
                <td></td>
                <td align="right">指标名称：</td>
                <td><input id="indItemName" name="indItemName" value="${prodChkCmdItems.indItemName}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">取值类型：</td>
                <td><input id="valueType" name="valueType" value="${prodChkCmdItems.valueType}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
                
                <td align="right">度量单位：</td>
                <td><input id="measureUnit" name="measureUnit" value="${prodChkCmdItems.measureUnit}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">取值长度：</td>
                <td><input id="length" name="length" value="${prodChkCmdItems.length}" type="text" ltype="text" class="ip1"readonly="readonly"/></td>
                <td ></td>
                
                <td align="right">小数位数：</td>
                <td><input id="decimals" name="decimals" value="${prodChkCmdItems.decimals}" type="text" ltype="text" class="ip1"readonly="readonly" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">指标说明：</td>
                <td colspan="4"><input id="remark" name="remark" value="${prodChkCmdItems.remark}" type="text" ltype="text" style="width:549px;" readonly="readonly"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">采集命令：</td>
                <td colspan="4">
                <input id="checkCmd" name="checkCmd" value="${prodChkCmdItems.checkCmd}" type="text" ltype="text" style="width:550px;"/>
                </td>
                <td></td>
            </tr>
            
             <tr>
                <td align="right">结果样本：</td>
               <td colspan="4">
                <textarea style="width:553px;height:150px;"id="resultSample" name="resultSample" >${prodChkCmdItems.resultSample}</textarea>
                </td>
                <td ></td>
            </tr>
             <tr>
                <td align="right">样本关键字：</td>
                <td colspan="4"><input id="resultKeyWords" name="resultKeyWords" value="${prodChkCmdItems.resultKeyWords}" type="text" ltype="text" class="ip1" /></td>
                <td ></td>
            </tr>
             <tr>
                <td align="right" nowrap>指标解析方式：</td>
                <%--<td>--%>
                	<%--<input id="parseMode1" name="" value="${prodChkCmdItems.parseMode}" type="text" ltype="text" class=" ip1"/>--%>
                <%--</td>--%>
                 <td>
                     <select id="parseMode" name="parseMode" style="width:200px;" onchange="changeParseMode();">
                         <option value="1"  <c:if test="${prodChkCmdItems.parseMode==1}">selected</c:if> >正则表达式</option>
                         <option value="2"  <c:if test="${prodChkCmdItems.parseMode==2}">selected</c:if> >字符串匹配</option>
                         <option value="3"  <c:if test="${prodChkCmdItems.parseMode==3}">selected</c:if> >字符分割</option>
                         <%--<option value="4"  <c:if test="${prodChkCmdItems.parseMode==4}">selected</c:if> >模版解析</option>--%>
                     </select>
                 </td>
                <td ></td>
                
                <td align="right" class="line1" nowrap>正则解析规则：</td>
                <td class="line1"><input id="regEx" name="regEx" value="${prodChkCmdItems.regEx}" type="text" ltype="text" class="ip1" /></td>
                <td class="line1"></td>
            </tr>
             <tr class="line2">
                <td align="right">开始关键字：</td>
                <td><input id="startKeyWords" name="startKeyWords" value="${prodChkCmdItems.startKeyWords}" type="text" ltype="text" class="ip1" /></td>
                <td ></td>
                
                <td align="right">结束关键字：</td>
                <td><input id="endKeyWords" name="endKeyWords" value="${prodChkCmdItems.endKeyWords}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
             <tr class="line3">
                <td align="right">分隔符：</td>
                <td><input id="delimiter" name="delimiter" value="${prodChkCmdItems.delimiter}" type="text" ltype="text" class="ip1" /></td>
                <td ></td>
                
                <td align="right">取值索引号：</td>
                <td><input id="valueIndex" name="valueIndex" value="${prodChkCmdItems.valueIndex}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
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

