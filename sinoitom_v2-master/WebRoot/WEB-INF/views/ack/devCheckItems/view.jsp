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
<title>巡检指标详情</title>
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
	var parseMode=$("#parseMode").val();
    if(parseMode==1){
    	$("#parseMode").val("正则表达式");
    	$(".line1").show();
    	$(".line2").hide();
    	$(".line3").hide();
    }else if(parseMode==2){
    	$("#parseMode").val("字符串匹配");
    	$(".line1").hide();
    	$(".line2").show();
    	$(".line3").hide();
    }else if(parseMode==3){
    	$("#parseMode").val("字符分割");
    	$(".line1").hide();
    	$(".line2").hide();
    	$(".line3").show();
    }
});



</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodChkCmdItems" action="" method="post">
	
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
			
                <td align="right">设备IP：</td>
             
                 <td><input id="mgmtIP" name="mgmtIP" type="text" value="${ackResources.mgmtIP}" class=" ip1" readonly="readonly"/></td>
                <td ></td>
               <td align="right">设备名称：</td>
     
                 <td><input id="resName" name="resName" value="${ackResources.resName}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td ></td>
            </tr>
             <tr>
			
                <td align="right">厂商：</td>
             
                 <td><input id="vendorName" name="vendorName" type="text" value="${ackResources.vendorName}" class=" ip1" readonly="readonly"/></td>
                <td ></td>
               <td align="right">设备类型：</td>
     
                 <td><input id="resTypeName" name="resTypeName" value="${ackResources.resTypeName}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td ></td>
            </tr>
            <tr>
                <td align="right">产品型号：</td>
                 <td><input id="prodModel" name="prodModel" value="${ackResources.prodModel}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
               <td align="right">型号OID：</td>
               <td><input id="modelOID" name="modelOID" value="${ackResources.modelOID}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
            </tr>
            
			
            <tr>
                <td align="right" nowrap="nowrap">操作系统类型：</td>
               <td><input id="osType" name="osType" value="${ackResources.osType}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
               <td align="right">软件版本：</td>
               <td><input id="osVersion" name="osVersion" value="${ackResources.osVersion}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
            </tr>
             <tr>
                <td align="right">指标组：</td>
                 <td><input id="indGroupName" name="indGroupName" value="${ackDevCheckItems.indGroupName}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
               <td align="right">指标英文名称：</td>
               <td><input id="indItemEnName" name="indItemEnName" value="${ackDevCheckItems.indItemEnName}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
            </tr>
            
			
            <tr>
                <td align="right">指标显示名称：</td>
               <td><input id="indItemName" name="indItemName" value="${ackDevCheckItems.indItemName}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
               <td></td>
               <td></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">数据类型：</td>
                <td><input id="valueType" name="valueType" value="${ackDevCheckItems.valueType}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
                
                <td align="right">度量单位：</td>
                <td><input id="measureUnit" name="measureUnit" value="${ackDevCheckItems.measureUnit}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">长度：</td>
                <td><input id="length" name="length" value="${ackDevCheckItems.length}" type="text" ltype="text" class="ip1"readonly="readonly"/></td>
                <td ></td>
                
                <td align="right">小数位数：</td>
                <td><input id="decimals" name="decimals" value="${ackDevCheckItems.decimals}" type="text" ltype="text" class="ip1"readonly="readonly" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">巡检命令：</td>
                <td colspan="4"><input id="checkCmd" name="checkCmd" value="${ackDevCheckItems.checkCmd}" type="text" ltype="text"style="width:550px;" readonly="readonly"/></td>
                <td></td>
            </tr>
           
		</table>
    </form:form>
</body>
</html>

