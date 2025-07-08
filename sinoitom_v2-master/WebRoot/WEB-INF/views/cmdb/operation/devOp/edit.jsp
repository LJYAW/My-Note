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
<title>添加修改设备操作</title>
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
			 getTypeResItems();
			 getOperationData();
});


 function getTypeResItems(){
  	
  	var classCode=$("#devClassCode").find("option:selected").val();
  	var classCodeName=$("#devClassCode").find("option:selected").text();
  	 $("#devClassName").val(classCodeName);
  	 
  	if(classCode=="11"){
  	   resName="IT_HostType";
  	}else if(classCode=="12"){
  	  resName="IT_StorageType";
  	}else if(classCode=="13"){
  	  resName="IT_NetDevType";
  	}else if(classCode=="14"){
  	  resName="IT_PeripheralType";
  	}else if(classCode=="21"){
  	  resName="IT_OSType";
  	}else if(classCode=="22"){
  	  resName="IT_SystemType";
  	}else if(classCode=="23"){
  	  resName="IT_DBType";
  	}else if(classCode=="24"){
  	  resName="IT_MiddleWareType";
  	}else if(classCode=="25"){
  	  resName="IT_AppType";
  	}
  	
  	 $.getJSON(
               "${ctx}/cmdb/prodType/getTypeDataBygroupCode.do?groupCode="+resName,
               function (result) {
                   if (result.types != null) {
                       $("#devTypeCode").empty();
                       for (var i = 0; i < result.types.length; i++) {
                       		var type=result.types[i];
                       		  if(type.id=='${devOperation.devTypeCode}'){
                       		      $("#devTypeCode").append("<option  value=\"" + type.id+"\" selected>" + type.text + "</option>");
                       		  }else{
                       		       $("#devTypeCode").append("<option  value=\"" + type.id+"\">" + type.text + "</option>");
                       		  }
                             
                       }
                       getTypeName();
                   }
                   
               });
  	
   }
   
function getTypeName(){
  var typeName=$("#devTypeCode").find("option:selected").text();
  $("#devTypeName").val(typeName);
 }

function getOperationData(){
 $.getJSON(
               "${ctx}/cmdb/operation/getOperationData.do",
               function (result) {
                   if (result.operations != null) {
                       $("#opID").empty();
                       for (var i = 0; i < result.operations.length; i++) {
                       		var operation=result.operations[i];
                       		  if(operation.id=='${devOperation.opID}'){
                       		      $("#opID").append("<option  value=\"" + operation.id+"\"  name=\"" + operation.operationName+"\" desr=\"" + operation.description+"\"  selected>" + operation.operation + "</option>");
                       		  }else{
                       		       $("#opID").append("<option  value=\"" + operation.id+"\"  name=\"" + operation.operationName+"\" desr=\"" + operation.description+"\">" + operation.operation + "</option>");
                       		  }
                             
                       }
                        getOperatinName();
                   }
                   
               });
}

function getOperatinName(){
  var operationName=$("#opID").find("option:selected").attr("name");
  $("#operateName").val(operationName);
  var operation=$("#opID").find("option:selected").text();
  $("#operation").val(operation);
  
   var description=$("#opID").find("option:selected").attr("desr");
   if($("#count").val() != "1"  && '${action}' == "edit"){
   		$("#description").val('${devOperation.description }');
   		$("#devOpName").val('${devOperation.devOpName}');
   }else{
       $("#description").val(description);
       $("#devOpName").val(operation);
   }
   $("#count").val("1");
  	
 }

		
$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });		
</script>

</head>
<body>
	<form id="formSave" modelAttribute="devOperation" action="${ctx}/cmdb/devOperation/saveDevOP.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="count" name="count"/>
		<input type="hidden" id="devClassName" name="devClassName"  value=""/>
	    <input type="hidden" id="devTypeName" name="devTypeName"  value=""/>
	     <input type="hidden" id="operation" name="operation"  value=""/>
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
               <td align="right">设备分类 ：</td>
                <td>
                 <s:resSelect id="devClassCode" name="devClassCode" value="${devOperation.devClassCode}" code="IT_ResClass"  ltype="select" style="width:202px" onchange="getTypeResItems();"/>
                </td>
                <td></td>
                 <td align="right">设备类型：</td>
                <td>
                 <select name="devTypeCode" id="devTypeCode" style="width:202px" onchange="getTypeName();">
						<option value="-1">请选择</option>
				</select>
                </td>
               <td></td>
            </tr>
			
			<tr>
				<td align="right">访问方式：</td>
				<td>
				   <s:resSelect id="accessModeCode" name="accessModeCode" value="${devOperation.accessModeCode}" code="Cmdb_DevAccessMode"  ltype="select" style="width:202px" />
				</td>
				<td></td>
				<td align="right">操作类型：</td>
				<td>
				   <s:resSelect id="opTypeCode" name="opTypeCode" value="${devOperation.opTypeCode}" code="Cmdb_DevOpType"  ltype="select" style="width:202px" />
				</td>
				<td></td>
			</tr>
			<tr id="cli">
				<td align="right">操作名称：</td>
				<td>
				 <select name="opID" id="opID" style="width:202px" onchange="getOperatinName()">
						<option value="-1">请选择</option>
				</select>
				
				</td>
				<td class="required"></td>
				<td align="right">中文操作名称：</td>
				<td>
				<input id="operateName" name="operateName" value="${devOperation.operateName }" type="text" ltype="text" class="ip1"  readonly/>
				</td>
				<td class="required"></td>
			</tr>
			
			<tr>
			    <td align="right">设备操作名称：</td>
				<td colspan="4">
				 <input id="devOpName" name="devOpName" value="${devOperation.devOpName }" type="text" class="ip2" />
				</td>
				<td></td>
			</tr>
			
			<tr>
			    <td align="right">设备操作说明：</td>
				<td colspan="4">
				 <input id="description" name="description" value="${devOperation.description }" type="text" class="ip2" />
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

