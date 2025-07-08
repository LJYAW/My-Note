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
<title>机构编辑</title>
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

<style type="text/css"> 

.l-box-select-inner{height:190px!important;}

.l-box-select{height:190px!important;} 

</style>

<script type="text/javascript">
	$(function ()
	{			
	  
	     
		<c:if test="${result=='success'}">
		var action = '${action}';
		var data = ${saveData};
		window.parent.saveOK();
		window.parent.parent.closeDlg();
		</c:if>

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
	 $("#vendorSelect").ligerComboBox({ 
			url: timeURL('${ctx}/cmdb/vendor/getVendor.do'),
        	width: 196, selectBoxWidth: 196, selectBoxHeight: 200, 
			textField: 'dispName', 
			valueFieldID: 'vendorID',
        	initValue: '${prodClass.vendorID}'
		});
	
	$("#formSave").ligerForm({inputWidth:200});
 		$("input").filter(".ip2").ligerTextBox({ width: 553 });
 		$("#formSave").validationEngine({promptPosition: "centerRight"});	
 	});
	
  	
  	
  	
  	function changeDescp(){
  	  var codeID= $("#prodClassCode").val();
  	  var url = timeURL('${ctx}/cmdb/prodClass/getResItem.do?id=' + codeID+'&resName='+'IT_ResClass');
		$.ajax({
			url : url,
			data : "date=" + new Date(),
			dataType : 'json',
			success : function(data) {
			var obj=data.result;
		    $("#description").val(obj.description);
			}
  	});
  	
 } 	

</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="prodClass" action="${ctx}/cmdb/prodClass/save.do" method="post">
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
                <td align="right">厂商名称：</td>
                <td>
                 <input id="vendorSelect" name="vendorSelect" type="text" class="ip1"/>
                </td>
                <td></td>
                <td align="right">产品分类 ：</td>
                <td>
                  <s:resSelect id="prodClassCode" name="prodClassCode" value="${prodClass.prodClassCode}" code="IT_ResClass"  ltype="select" style="width:202px" onchange="changeDescp()"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right">分类描述：</td>
                <td><input id="description" name="description" value="${prodClass.description}" type="text" ltype="text" class="validate[optional,maxSize[32]] ip1" /></td>
                <td colspan="4"></td>
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

