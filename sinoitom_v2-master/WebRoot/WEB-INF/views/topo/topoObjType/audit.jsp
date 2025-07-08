<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sino.base.common.Global"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>机构编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 

<script type="text/javascript">

	$(function() {

		<c:if test="${result=='success'}">
		
		window.parent.saveOK();
		</c:if>
        $("input").filter(".ip2").ligerTextBox({ width: 194 });
	});
	
</script>

</head>
<body>

	<form:form id="formSave" modelAttribute=""
		action="${ctx}/topo/objType/saveTopoType.do" method="post"
		enctype="multipart/form-data">
		
		<input type="hidden" name="id" value="${topoObjectType.objTypeID}" />
		
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
				<td align="right">对象分类:</td>
				<td>
				<input name="className" id="className"
					style="width:194px" value="${ topoObjectType.className}" class="ip2" readonly="readonly"/>
				</td>
				<td></td>
				<td align="right">对象类型:</td>
				<td>
				<input name="typeName" id="typeName"
					style="width:194px" value="${ topoObjectType.typeName}" class="ip2" readonly="readonly"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">类型英文名称:</td>
				<td><input name="classEnName" id="classEnName"
					style="width:194px" value="${ topoObjectType.classEnName}" class="ip2" readonly="readonly"/> 
				</td>
				<td></td>
				<td align="right">说明:</td>
				<td><input name="description" id="description"
					style="width:194px" value="${ topoObjectType.description}" class="ip2" readonly="readonly"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">图元类型:</td>
				<td>
				
				<c:choose>
				<c:when test="${topoObjectType.objType=='0'}">
		        <input name="" id="objType"
					style="width:194px" value="子图" class="ip2" readonly="readonly"/>
		        </c:when>
		        
		        <c:otherwise>
		        <input name="" id="objType"
					style="width:194px" value="节点" class="ip2" readonly="readonly"/>
		        </c:otherwise>
		        
		        </c:choose>
				</td>
				<td></td>
				<td align="right">图元显示名称:</td>
				<td><input name="objName" id="objName" style="width:194px"
					value="${ topoObjectType.objName}" class="ip2" readonly="readonly"/>
				</td>
				<td></td>
			</tr>
			
            <tr>
				<td align="center" colspan=6>
					<div align="center">
	                <img id="bgPicture" src="<%=Global.LOAD_DEV_IMG_URL%>${topoObjectType.objIcon}" alt="" width=200 height=180 />
                    </div>
					</td>
       
			</tr>
			
			<tr>
				<td align="center" colspan=6>
					<div class="save" id="save">
						<input type="submit" value="审核通过" class="l-button mg6" />
					</div>
					</td>
       
			</tr>
		</table>
	</form:form>
</body>
</html>

