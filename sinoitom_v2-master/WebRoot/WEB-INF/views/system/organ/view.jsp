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
<title>机构详情</title>
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
	<form:form id="formSave" modelAttribute="organ" action="" method="post">
	
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
                <td align="right">机构名称:</td>
                <td><input id="orgName" name="orgName" value="${organ.orgName }" type="text" ltype="text" class="ip1"   style="height:20px"readonly="readonly"/></td>
                <td></td>
                <td align="right">机构类型:</td>
                <td>
                <input id="orgType" name="orgType" value="${resName}" type="text" ltype="text" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
            	<td align="right">机构简称:</td>
                <td><input id="orgShortName" name="orgShortName" value="${organ.orgShortName}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">机构编码:</td>
                <td><input id="orgCode" name="orgCode" value="${organ.orgCode}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
                
            </tr>
            <tr>
                <td align="right">地址:</td>
                <td colspan=4><input id="address" name="address" value="${organ.address}" type="text" ltype="text" class="ip2"  readonly="readonly"/></td>
                <td></td>
            </tr>
            <tr>
            	<td align="right">邮编:</td>
                <td><input id="zipCode" name="zipCode" value="${organ.zipCode}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">传真:</td>
                <td><input id="fax" name="fax" value="${organ.fax}" type="text" ltype="text" class="ip1" readonly="readonly"/></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">电话:</td>
                <td><input id="phone" name="phone" value="${organ.phone}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
                <td align="right">机构网址:</td>
                <td><input id="web" name="web" value="${organ.web}" type="text" ltype="text" class=" ip1" readonly="readonly"/></td>
                <td></td>
            </tr>  
            <tr>
                <td align="right">说明:</td>
                <td colspan=4><input id="description" name="description" value="${organ.description}" type="text" ltype="text" class="ip2"  readonly="readonly"/></td>
                <td></td>
            </tr>
            
           
		</table>
    </form:form>
</body>
</html>

