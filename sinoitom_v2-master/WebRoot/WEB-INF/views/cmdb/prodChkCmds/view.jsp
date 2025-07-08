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
<title>巡检命令详情</title>
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
	
});



</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodChkCmds" action="" method="post">
	
		<table align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
			
                <td align="right" nowrap="nowrap">厂商名称：</td>
             
                 <td><input id="vendorId" name="vendorId" type="text" value="${cmdbVendor.dispName}" class=" ip1" readonly="readonly"/></td>
                <td ></td>
               <td align="right" norawp>产品分类 ：</td>
     
                 <td><input id="devClassName" name="devClassName" value="${prodChkCmds.devClassName}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td ></td>
            </tr>
             <tr>
                <td align="right" norawp>产品类型：</td>
                 <td><input id="devTypeName" name="devTypeName" value="${prodChkCmds.devTypeName}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
               <td align="right" norawp>产品型号：</td>
               <td><input id="prodModel" name="prodModel" value="${prodChkCmds.prodModel}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
            </tr>
            
			
            <tr>
                <td align="right" nowrap="nowrap">操作系统类型：</td>
               <td><input id="osType" name="osType" value="${prodChkCmds.osType}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
               <td align="right" norawp>软件版本：</td>
               <td><input id="osVersion" name="osVersion" value="${prodChkCmds.osVersion}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
            </tr>
            <tr>
                <td align="right" nowrap="nowrap">操作系统类型：</td>
               	<td><input id="osFeature" name="osFeature" value="${prodChkCmds.osFeature}" type="text" ltype="text" class=" ip1"readonly="readonly"/></td>
                <td ></td>
                <td ></td>
                <td ></td>
                <td ></td>
            </tr>
            
              <tr>
                <td align="right" norawp>采集命令：</td>
                <td colspan="4"><input id="checkCmd" name="checkCmd" value="${prodChkCmds.checkCmd}" type="text" ltype="text" class=" ip2"readonly="readonly"/></td>
                <td ></td>
            </tr>
            <tr>
                <td align="right" norawp>命令用途：</td>
                <td colspan="4"><input id="cmdUsage" name="cmdUsage" value="${prodChkCmds.cmdUsage}" type="text" ltype="text" class=" ip2"readonly="readonly"/></td>
                <td ></td>
            </tr>
            <tr>
                <td align="right" norawp>结果样本：</td>
                <td colspan="4">
                <textarea style="width:553px;height:200px;"id="resultSample" name="resultSample" readonly="readonly">${prodChkCmds.resultSample}</textarea>
                </td>
                <td ></td>
            </tr>
            <tr>
                <td align="right" norawp>备注：</td>
                <td colspan="4"><input id="remark" name="remark" value="${prodChkCmds.remark}" type="text" ltype="text" class=" ip2"readonly="readonly"/></td>
                <td ></td>
            </tr>
           
            
          
            
            <tr>
            	<td>&nbsp;&nbsp;</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

