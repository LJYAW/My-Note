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
<title>添加修改操作定义</title>
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
	$(function ()
		{				
			<c:if test="${result=='success'}">
			var action = '${action}';
			var data = ${saveData};
			window.parent.parent.saveOK(action, data);
			window.parent.parent.closeDlg();
			</c:if>
			$("#formSave").validationEngine({promptPosition: "centerRight"});
			<c:if test="${result=='error'}">
	        $.ligerDialog.error('${message}');
			</c:if>	
			$("#formSave").validationEngine();
			$("input").filter(".ip2").ligerTextBox({ width: 553 });
			
		    $("input").filter(".ip1").ligerTextBox({ width: 200 });
		    
		    
			//表单验证
			$("#formSave").click(function(check) {    
		    	if($("#formSave").validationEngine()){
		    	}else{
		    		check.preventDefault();//此处阻止提交表单  
		    	}
			});
});


   
$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });	
  function getopCNType(){
	   var opType = $("#opType").val();
	   $("#opENType").val(opType) ;
	}	
</script>

</head>
<body>
	<form id="formSave" modelAttribute="devOperation" action="${ctx}/cmdb/operation/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" name="id" value="${id}"/>
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
				<td align="right">中文操作类型：</td>
				<td>
				 <s:resSelect id="opType" name="opType" value="${operation.opType}" code="Cmdb_OpType"  ltype="select" onchange="getopCNType()" style="width:201px" />
				</td>
				<td class="required"></td>
				<td align="right">英文操作类型：</td>
				<td>
				<input id="opENType" name="" value="${operation.opType}" type="text" ltype="text" class="ip1"  readonly="readonly" />
				</td>
				<td class="required"></td>
			</tr>
			<tr>
				<td align="right">英文操作名称：</td>
				<td>
				<input id="operation" name="operation" value="${operation.operation }" type="text" ltype="text" class="ip1"  />
				</td>
				<td class="required"></td>
				<td align="right">中文操作名称：</td>
				<td>
				<input id="operationName" name="operationName" value="${operation.operationName }" type="text" ltype="text" class="ip1"  />
				</td>
				<td class="required"></td>
			</tr>
			
			
			<tr>
			    <td align="right">操作说明：</td>
				<td colspan="4">
				 <input id="description" name="description" value="${operation.description }" type="text" class="ip2" />
				</td>
				<td></td>
			</tr>
			
			<tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form>
    <br/>
</body>
</html>

