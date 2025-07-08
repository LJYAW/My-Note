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
<title>用户编辑</title>
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
	var classCode='${classCode}'			
		<c:if test="${result=='success'}">
		var action = '${action}';
		var data = ${saveData};
		window.parent.parent.saveOK(action, data);     
		window.parent.closeDlg();
		</c:if>

		$("#mainOrg").ligerComboBox({
        	width: 196,
        	selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'mainOrgId',
        	treeLeafOnly: false,
        	initValue: '${user.mainOrgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:user.mainOrgId}',
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false }
        });

		$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("input").filter(".ip1").ligerTextBox({ width: 200 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});	
		
		
		$("#chkEditPasswd").change(function()
	    {
			if( this.checked ) {
				$("#isEditPasswd").attr("value","1");
				$("#trEditPasswd").css({display:''});
			}else{
				$("#isEditPasswd").attr("value","0");
				$("#trEditPasswd").css({display:'none'});
			}
        });

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
		$("#formSave").validationEngine();
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
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="IndicatorGroup" action="${ctx}/cmdb/indicator/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="id" name="id" value="${id}">
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
                <td align="right" nowrap="nowrap">监测对象分类:</td>
                <td><s:resSelect id="indClassCode" name="indClassCode" value="${IndicatorGroup.indClassCode}" code="Cmdb_IndicatorClass"  ltype="select" style="width:200px;"/></td>
                <td></td>
            </tr>           
            <tr>
                <td align="right" nowrap>指标组英文名称:</td>
                <td><input id="groupEnName" name="groupEnName" value="${IndicatorGroup.groupEnName}" type="text" ltype="text" class="validate[required[指标组英文名不能为空]] ip1" /></td>
                <td class="required"></td>
                <td align="right">指标组名称:</td>
                <td><input id="groupName" name="groupName" value="${IndicatorGroup.groupName}" type="text" ltype="text" class="validate[required[指标组名称不能为空]] ip1" /></td>
                <td class="required"></td>
            </tr>

            <tr>
                <td align="right">指标组描述:</td>
                <td colspan=4><input id="description" name="description" value="${IndicatorGroup.description}" type="text" class="validate[optional,maxSize[128]] ip2" ltype="text"  /></td>
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

