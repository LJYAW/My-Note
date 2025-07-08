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
<title></title>
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
		window.parent.parent.saveOK();     
		window.parent.closeDlg();
		</c:if>

		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("input").filter(".ip1").ligerTextBox({ width: 200 });
		
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
		refreshIndGroup();
		getEventType();
  });
  
  //刷新指标组
    function refreshIndGroup(){
     var indClassCode=$("#indClassCode").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/prodIndItem/getIndGroup.do?indClassCode="+indClassCode,
              function (result) {
                  if (result.indGroups != null) {
                      $("#indGroupID").empty();
                      for (var i = 0; i < result.indGroups.length; i++) {
                          var group = result.indGroups[i];
                          if(group.id=='${items.indGroupID}'){
                            $("#indGroupID").append("<option  value=\"" + group.id + "\" selected>" + group.groupName + "</option>");
                          }else{
                            $("#indGroupID").append("<option  value=\"" + group.id + "\">" + group.groupName + "</option>");
                          }
                          
                      }
                     getIndGroupName();
                  }
              });
    }
  
  function getIndGroupName(){
  	 refreshName();
  }
   function refreshName(){
     var indClassCode=$("#indClassCode").find("option:selected").val();
     var indGroupID=$("#indGroupID").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/indicatorItems/getIndItemName.do?indClassCode="+indClassCode+"&indGroupID="+indGroupID,
              function (result) {
                  if (result.indicator != null) {
                      $("#indItemID").empty();
                      for (var i = 0; i < result.indicator.length; i++) {
                          var group = result.indicator[i];
                          if(group.indGroupID=='${items.indGroupID}'){
                            $("#indItemID").append("<option  value=\"" + group.indItemID + "\" selected>" + group.indItemName + "</option>");
                          }else{
                            $("#indItemID").append("<option  value=\"" + group.indItemID + "\">" + group.indItemName + "</option>");
                          }
//                            $('#indicatorItem').val(group.indicatorItem); 
                           getItem();
                      }
                  }
              });
    }
    
  function getItem(){
   var indItemID=$("#indItemID").find("option:selected").val();
    $.getJSON(
              "${ctx}/cmdb/indicatorItems/getIndicatorItem.do?indItemID="+indItemID,
              function (result) {
               $("#indicatorItem").empty();
            var group = result.indicator;
            $('#indicatorItem').val(group.indicatorItem);  
              });
  }
 function getEventType(){
         var eventClassCode=$("#eventClassCode").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/eventType/getByClassCode.do?classCode="+eventClassCode,
              function (result) {
                  if (result.types != null) {
                      $("#eventTypeID").empty();
                      for (var i = 0; i < result.types.length; i++) {
                          var type = result.types[i];
                            $("#eventTypeID").append("<option  value=\"" + type.id + "\">" + type.eventTypeName + "</option>");
                          
                      }
                  }
              });
    }
    
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="indItemEventTypeMap" action="${ctx}/cmdb/eventTypeMap/saveDate.do" method="post">
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
                <td><s:resSelect id="indClassCode" name="indClassCode" value="${items.indClassCode}" code="Cmdb_IndicatorClass"  ltype="select" onchange="refreshIndGroup()" style="width:202px"/></td>
                <td></td>
                
                <td align="right">监测指标组:</td>
                <td>
                	<select name="indGroupID" id="indGroupID" style="width:202px" onchange="getIndGroupName()"> 
					</select> 
				</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">指标名称:</td>
                 <td>
                	<select name="indItemID" id="indItemID"  style="width:202px" onchange="getItem()"> 
					</select> 
				</td>
                <td></td>
                <td align="right">指标英文名称:</td>
                <td><input id="indicatorItem" name="indicatorItem" value="${items.indicatorItem}" type="text" ltype="text" class="validate[required[指标项名称不能为空],maxSize[512]] ip1" readonly="readonly"/></td>

                <td></td>
            </tr>
            <tr>
                 <td align="right">事件分类:</td>
                <td><s:resSelect id="eventClassCode" name="eventClassCode" value="" code="Cmdb_EventClass" ltype="select" style="width:200px;" onchange="getEventType()"/></td>
                <td></td>     
                
                <td align="right">事件类型:</td>
                          <td>
                	<select name="eventTypeID" id="eventTypeID" style="width:202px" onchange="getIndItemName()"> 
					</select> 
				</td>
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

