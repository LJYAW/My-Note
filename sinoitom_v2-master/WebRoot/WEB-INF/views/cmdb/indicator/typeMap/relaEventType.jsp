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
<title>关联事件类型</title>
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

var resName="IT_NetDevType";
	$(function ()
	{			
	  
	     
		<c:if test="${result=='success'}">
// 		var action = '${action}';
// 		var data = ${saveData};
		window.parent.$.ligerDialog.success("事件类型关联成功!");
		window.close();
		//window.parent.parent.saveOK(action, data);
		 
		</c:if>

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
		
	
	    $("#formSave input").filter(".ip1").ligerTextBox({width: 200});
 		$("input").filter(".ip2").ligerTextBox({ width: 553 });
 		$("#formSave").validationEngine({promptPosition: "centerRight"});	
 		$("#formSave").validationEngine({promptPosition: "centerRight"});
 		//表单验证
		$("#formSave").click(function(check) {    
	    	if($("#formSave").validationEngine()){
	    	}else{
	    		check.preventDefault();//此处阻止提交表单  
	    	}
		});
		getTypeResItems();
 	});
	
  	
   function getTypeResItems(){
  	
  	var classCode=$("#eventClassCode").find("option:selected").val();
  	 $.getJSON(
               "${ctx}/cmdb/eventType/getByClassCode.do?classCode="+classCode,
               function (result) {
                   if (result.types != null) {
                       $("#eventTypeID").empty();
                       for (var i = 0; i < result.types.length; i++) {
                       		var type=result.types[i];
                              $("#eventTypeID").append("<option  value=\"" + type.id+"\">" + type.eventTypeName + "</option>");
                       }
                   }
                   
               });
  	
   }

  	 	
</script>

</head>
<body> 
	<form:form id="formSave"  action="${ctx}/cmdb/eventTypeMap/saveRelaEventType.do" method="post">
	<input type="hidden" name="itemsIds" value="${itemsIds}"/>
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
               <td align="right">事件分类：</td>
               <td><s:resSelect id="eventClassCode" name="eventClassCode" value="${type.eventClassCode}" code="Cmdb_EventClass" ltype="select" style="width:200px;" onchange="getTypeResItems()"/></td>
               <td></td>
               
                <td align="right">事件类型：</td>
                <td>
                 <select name="eventTypeID" id="eventTypeID" style="width:202px">
						<option value="-1">请选择</option>
				</select>
                </td>
               <td></td>
            </tr>
            
            <tr>
            	<td>&nbsp;&nbsp;</td>
            </tr>
      
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="确认关联" class="button mg6" />
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

