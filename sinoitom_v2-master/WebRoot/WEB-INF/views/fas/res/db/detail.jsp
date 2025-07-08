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
<title>数据库编辑</title>
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
</head>
<body> 
	<form:form id="formSave"  action="" method="post">
		
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
                <td align="right" nowrap>机构名称:</td>
                <td>
                	<input id="orgName" name="orgName" value="${orgName}" type="text" readonly="readonly"  class="ip1" />
                </td>
                <td></td>
                <td align="right">服务器IP:</td>
                <td>
                	<input id="hostIp" name="hostIp" value="${resDB.hostIp}" type="text" readonly="readonly"  class="ip1" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>数据库类型:</td>
                <td>
                	<input id="resTypeName" name="resTypeName" value="${resDB.resTypeName}" type="text" readonly="readonly"  class="ip1" />
                </td>
                <td></td>
                <td align="right">数据库版本:</td>
                <td>
                	<input id="dbVersion" name="dbVersion" value="${resDB.dbVersion}" readonly="readonly" class="ip1" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>数据库名称:</td>
                <td>
                	<input id="dbName" name="dbName" value="${resDB.dbName}" class="ip1" readonly="readonly" />
                </td>
                <td></td>
                <td align="right">服务端口:</td>
                <td>
                	<input id="dbPort" name="dbPort" value="${resDB.dbPort}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>用户名:</td>
                <td>
                	<input id="userName" name="userName" value="${resDB.userName}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right">密码:</td>
                <td>
                	<input id="password" name="password" value="${resDB.password}" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>数据库驱动:</td>
                <td colspan=4><input readonly="readonly" id="jdbcDriver" name="jdbcDriver" value="${resDB.jdbcDriver}" type="text" class="validate[optional,maxSize[64]] ip2" ltype="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>访问URL:</td>
               <td colspan="4">
				 <textarea rows="7" cols="90"id="jdbcUrl" name="jdbcUrl" value="${resDB.jdbcUrl}" readonly="readonly">${resDB.jdbcUrl}</textarea>
                </td>
                <td></td>
            </tr>   
            <tr>
           		<td align="right" colspan=3 >
           		<input id="bnSave" type="button" value="关闭" class="l-button mg6" onclick="window.parent.saveOK();"/>
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

