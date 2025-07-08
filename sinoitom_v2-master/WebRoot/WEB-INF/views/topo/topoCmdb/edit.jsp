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
<title>业务关系图编辑</title>
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
			window.parent.saveOK();     
		</c:if>
		<c:if test="${result=='error'}">
			window.parent.saveOK('','${message}');     
			window.parent.closeDlg();
		</c:if>

		getBizNameByBizTypeCode();

// 		$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip1").ligerTextBox({ width: 200 });
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
// 		$("#formSave").validationEngine({promptPosition: "centerRight"});		


	});
	
// 	获取业务系统
	function getBizNameByBizTypeCode(){
		var typeCode = $('#bizTypeCode').val();
		 $.getJSON(
		     "${ctx}/res/biz/getListByResTypeCode.do?typeCode="+typeCode,
		     function (result) {
		         if (result!= null) {
		             $("#bizResId").empty();
		             for (var i = 0; i < result.length; i++) {
		                 var bizSystem = result[i];
		                 if(bizSystem.id=='${graph.bizResId}'){
		                   $("#bizResId").append("<option  value=\"" + bizSystem.id + "\" sysName=\"" + bizSystem.sysName + "\"  selected>" + bizSystem.sysEnName + "</option>");
		                 }else{
		                   $("#bizResId").append("<option  value=\"" + bizSystem.id + "\" sysName=\"" + bizSystem.sysName + "\" >" + bizSystem.sysEnName + "</option>");
		                 }
		                 
		             }
		             getSysName();
		         }
		     });
		}
		
		function getSysName(){
			var resId = $('#bizResId').find("option:selected").val();
			var sysName = $('#bizResId').find("option:selected").attr("sysName");
			$('#bizName').val(sysName);
			$('#resId').val(resId);
			$('#bizTypeName').val($('#bizTypeCode').find("option:selected").text());
		}

</script>

</head>
<body> 
	<form:form id="formSave"  action="${ctx}/cmdb/graph/autoCreate.do" method="post">
        <input type="hidden"id="action" name="action" value="${action}"/>
		<input type="hidden" name="graphId" id="graphId" value="${graph.graphId}"/>
		<input type="hidden" name="bizTypeName" id="bizTypeName" value="${graph.bizTypeName}"/>
		
		<input type="hidden" name="resId" id="resId" value=""/>
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
                <td align="right">系统类型：</td>
                <td>
                  <s:resSelect id="bizTypeCode" name="bizTypeCode" value="" code="IT_bizSysType"  ltype="select" style="width:198px" onchange=" getBizNameByBizTypeCode()"/>
                </td>
               <td></td>
            </tr>
            <tr>
             <td align="right">系统英文名称:</td>
                <td>
                	<select id="bizResId" name="bizResId" class="ip1" onchange="getSysName()"></select>
                </td>
                <td></td>
               <td align="right">系统中文名称:</td>
                <td>
                	<input id="bizName" name="bizName" value="" type="text"  class="ip1" />
                </td>
                <td></td>  
            </tr>
            <tr>
                <td align="right" nowrap>关系图名称:</td>
                <td colspan=4><input id="graphName" name="graphName" value="${graph.graphName}" type="text" class="validate[optional,maxSize[128]] ip2" ltype="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>关系图描述:</td>
                <td colspan=4><input id="graphDescr" name="graphDescr" value="${graph.graphDescr}" type="text" class="validate[optional,maxSize[128]] ip2" ltype="text"  /></td>
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

