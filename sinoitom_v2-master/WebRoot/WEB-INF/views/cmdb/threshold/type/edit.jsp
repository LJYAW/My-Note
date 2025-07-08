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
		<c:if test="${result=='success'}">
		var action = '${action}';
		var data = ${saveData};
		window.parent.saveOK(action, data);     
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

		//$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("input").filter(".ip1").ligerTextBox({ width: 200 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});	
		
		refreshIndGroup();
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
  
  function refreshIndGroup(){
     var indClassCode=$("#indObjCode").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/prodIndItem/getIndGroup.do?indClassCode="+indClassCode,
              function (result) {
                  if (result.indGroups != null) {
                      $("#indGroupID").empty();
                      for (var i = 0; i < result.indGroups.length; i++) {
                          var group = result.indGroups[i];
                          if(group.id=='${thresholdType.indGroupID}'){
                            $("#indGroupID").append("<option  value=\"" + group.id + "\" selected>" + group.groupName + "</option>");
                          }else{
                            $("#indGroupID").append("<option  value=\"" + group.id + "\">" + group.groupName + "</option>");
                          }
                      }
                      
                  refreshNameAndEName();
                  }
              });
    }
   function refreshNameAndEName(){
   		var indClassCode=$("#indGroupID").find("option:selected").val();
   		$("#threshTypeName").val($("#indGroupID").find("option:selected").text()+"阀值")
   		$.getJSON(
              "${ctx}/cmdb/indicator/getDataById.do?idnitemId="+indClassCode,
              function (result) {
                  if (result.group != null) {
                      $("#indGroupEname").val(result.group.groupEnName);
                      $("#indGroupName").val(result.group.groupName);
                  }
              });
   }
  
  function closeWindow() {
  	window.parent.closeDlg();
  }
  
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="thresholdType" action="${ctx}/cmdb/thresholdType/save.do" method="post">
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
                <td align="right">监测对象:</td>
                <td><s:resSelect id="indObjCode" name="indObjCode" value="${thresholdType.indObjCode}" code="Cmdb_IndicatorClass" ltype="select" style="width:200px;" onchange="refreshIndGroup()"/></td>
                <td></td>
                <td align="right">指标维度:</td>
                <td><s:resSelect id="indDimCode" name="indDimCode" value="${thresholdType.indDimCode}" code="Cmdb_IndicatorDim" ltype="select" style="width:200px;" /></td>
                <td></td>
            </tr>          
            <tr>
                <td align="right">指标组名称:</td>
                <td>
                	<select name="indGroupID" id="indGroupID" style="width:202px" onchange="refreshNameAndEName()"> 
					</select> 
					<input type="hidden" id="indGroupEname" name="indGroupEname" value="${thresholdType.indGroupEname}">
					<input type="hidden" id="indGroupName" name="indGroupName" value="${thresholdType.indGroupName}">
				</td>
                <td></td>
                <td align="right">阀值分类:</td>
                <td><input id="threshTypeName" name="threshTypeName" value="${thresholdType.threshTypeName}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">缺省单位:</td>
                <td><input id="defaultUnit" name="defaultUnit" value="${thresholdType.defaultUnit}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
                <td align="right">单位名称:</td>
                <td><input id="unitName" name="unitName" value="${thresholdType.unitName}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>

            <tr>
                <td align="right">描述:</td>
                <td colspan=4><input id="description" name="description" value="${thresholdType.description}" type="text" class="ip2" ltype="text"  /></td>
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

