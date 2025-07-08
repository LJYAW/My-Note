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
			window.parent.saveOK();
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
			// getTypeResItems();
			
			 getOperationData();
			 getVendor();
			 hideUnits();
});

$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });
  
  
  
  
  //Start
  //获取操作定义数据
  function getOperationData(){
   $.getJSON(
               "${ctx}/cmdb/operation/getOperationData.do",
               function (result) {
                   if (result.operations != null) {
                       $("#opID").empty();
                       for (var i = 0; i < result.operations.length; i++) {
                       		var operation=result.operations[i];
                       		if(operation.id=='${devOp.opID}'){
                       			$("#opID").append("<option  value=\"" + operation.id+"\"   operationName=\"" + operation.operationName+"\" selected>" + operation.operation + "</option>");
                       		}else{
                       			$("#opID").append("<option  value=\"" + operation.id+"\"   operationName=\"" + operation.operationName+"\">" + operation.operation + "</option>");
                       		}
                       		
                       }
                        getOperatinName();
                        getDevOperationBySnmp();
                   }
               });
  }
  
  
  //获取设备操作数据
  function getDevOperationBySnmp(){
  var operationId=$("#opID").find("option:selected").val();
    $.getJSON(
               "${ctx}/cmdb/devOpOID/getDevOperationBySnmp.do?opId="+operationId,
               function (result) {
                   if (result.operations != null) {
                       $("#devOpID").empty();
                       for (var i = 0; i < result.operations.length; i++) {
                       		var operation=result.operations[i];
                       		if(operation.id=='${opOID.devOpID}'){
                       			$("#devOpID").append("<option  value=\"" + operation.id+"\"   desr=\"" + operation.description+"\" devClassName=\"" + operation.devClassName+"\" devTypeName=\"" + operation.devTypeName+"\" devTypeCode=\"" + operation.devTypeCode+"\" selected>" + operation.devOpName + "</option>");
                       		}else{
                       			$("#devOpID").append("<option  value=\"" + operation.id+"\"   desr=\"" + operation.description+"\" devClassName=\"" + operation.devClassName+"\" devTypeName=\"" + operation.devTypeName+"\" devTypeCode=\"" + operation.devTypeCode+"\">" + operation.devOpName + "</option>");
                       		}
                       	    
                       }
                        getDevOperatindesr();
                        getDevModel();
                   }
               });
  }
  
function getOperatinName(){
  var operationName=$("#opID").find("option:selected").attr("operationName");
  $("#operateName").val(operationName);
 }
function getDevOperatindesr(){
 var operationdesr=$("#devOpID").find("option:selected").attr("desr");
  $("#devoOperateDres").val(operationdesr);
  var devClassName=$("#devOpID").find("option:selected").attr("devClassName");
  $("#devClassName").val(devClassName);
  var devTypeName=$("#devOpID").find("option:selected").attr("devTypeName");
  $("#devTypeName").val(devTypeName);
   var devOpName=$("#devOpID").find("option:selected").text();
   $("#devOpName").val(devOpName);
   var devTypeCode=$("#devOpID").find("option:selected").attr("devTypeCode");
   $("#devTypeCode").val(devTypeCode);
}

function getVendor() {  //厂商
       var myname="snmpEntID";
           $.getJSON(
               "${ctx}/cmdb/prodType/getVendor.do?date="+new Date(),
               function (result) {
                   if (result.vendors != null) {
                       $("#" + myname).empty();
                       $("#" + myname).append("<option value=\"-1\"  selected>所有厂商</option>");
                       for (var i = 0; i < result.vendors.length; i++) {
                          var vendor= result.vendors[i];
                          if(vendor.vendorID=='${opOID.snmpEntID}'){
                        	  $("#" + myname).append("<option  value=\"" + vendor.vendorID + "\" selected>" + vendor.dispName + "</option>");
                          }else{
                        	  $("#" + myname).append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                          }
                       }
                      getDevModel();
                   }
               });
   	} 
   	
	function getOSName() {  //操作系统名称
       var myname="osID";
       var vendorID=$("#snmpEntID").find("option:selected").val();
           $.getJSON(
               "${ctx}/cmdb/os/getOSNameByVendorID.do?id="+vendorID+"&date="+new Date(),
               function (result) {
                   if (result.oSNamelist != null) {
                       $("#" + myname).empty();
                       $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                       for (var i = 0; i < result.oSNamelist.length; i++) {
                          var oSName= result.oSNamelist[i];
                    	  $("#" + myname).append("<option  value=\"" + oSName.oSNameID + "\">" + oSName.oSName + "</option>");
                       }
                   }
               });
   	} 
   	
   	function getDevModel() {  //型号OID
       var myname="devModel";
       var vendorID=$("#snmpEntID").find("option:selected").val();
        var devTypeCode=$("#devOpID").find("option:selected").attr("devTypeCode");
           $.getJSON(
               "${ctx}/cmdb/vendorProdModel/getModelOIDByVendorIDAndDevTypeCode.do?id="+vendorID+"&devTypeCode="+devTypeCode,
               function (result) {
                   if (result.modelOIDlist != null) {
                	   $("#" + myname).empty();
                      // $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                       $("#" + myname).append("<option value=\"X\" modelOId=\"X\" >所有型号</option>");
                       for (var i = 0; i < result.modelOIDlist.length; i++) {
                          var modelOIDObj= result.modelOIDlist[i];
                          if(modelOIDObj.modelOID=='${opOID.model_OID}'){
                        	  $("#" + myname).append("<option   value=\"" + modelOIDObj.prodModel + "\"  modelOId=\"" + modelOIDObj.modelOID + "\" selected>" + modelOIDObj.prodModel + "</option>");
                          }else{
                        	  $("#" + myname).append("<option   value=\"" + modelOIDObj.prodModel + "\"  modelOId=\"" + modelOIDObj.modelOID + "\">" + modelOIDObj.prodModel + "</option>");
                          }
                    	  
                       }
                   }
                    getModelOID();
               });
   	} 
   	
   	 function	getModelOID(){
      var modelOID=$("#devModel").find("option:selected").attr("modelOId");
      $("#model_OID").val(modelOID);
      var modelName=$("#devModel").find("option:selected").text();
      $("#modelName").val(modelName);
   } 
   
   	function getOSVersion(){
   	   var myname="osVersion";
       var vendorID=$("#snmpEntID").find("option:selected").val();
       var oSName=$("#osID").find("option:selected").text();
           $.getJSON(
               "${ctx}/cmdb/vendorOSVersion/getOSVersion.do?vendorID="+vendorID+"&oSName="+oSName+"&date="+new Date(),
               function (result) {
                   if (result.vendorOSVersionlist != null) {
                       $("#" + myname).empty();
                       $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                       $("#" + myname).append("<option value=\"X.X\"  selected>所有版本</option>");
                       for (var i = 0; i < result.vendorOSVersionlist.length; i++) {
                          var vendorOSVersion= result.vendorOSVersionlist[i];
                    	  $("#" + myname).append("<option  value=\"" + vendorOSVersion.osVersion + "\">" + vendorOSVersion.osVersion + "</option>");
                       }
                   }
               });
   	}
   	
   	function hideUnits(){
   		var getMethod=$("#getMethod").val();
   		if(getMethod=="SnmpGet"){
   			$("#units1").show();
   			$("#units2").show();
   			$("#units3").show();
   			$("#setValue1").hide();
   			$("#setValue2").hide();
   			$("#setValue3").hide();
   		}else if(getMethod=="SnmpSet"){
   			$("#units1").hide();
   			$("#units2").hide();
   			$("#units3").hide();
   			$("#setValue1").show();
   			$("#setValue2").show();
   			$("#setValue3").show();
   		}else{
   			$("#setValue1").show();
   			$("#setValue2").show();
   			$("#setValue3").show();
   			$("#units1").show();
   			$("#units2").show();
   			$("#units3").show();
   		}
   	}
   	
  	function saveAdd(){
   		var url = timeURL('${ctx}/cmdb/devOpOID/saveAdd.do');
   		$.ajax({
    	    type: "POST",
    	    url: url,
    	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
		    data: $("#formSave").serialize(),
    		//dataType: 'json',
    		success: function(data) {
    			 if(data.saveData!=null){
		           	    window.parent.$.ligerDialog.success("添加成功!");
		           	    window.parent.saveOK();
		           	    $("#objectName").val("");
		           	    $("#snmpObject").val("");
		           	    $("#snmpOID").val("");
		           	    $("#units").val("");
		           	    $("#setValue").val("");
		           	    
			      	}
    		}
    	});   
   	}
  	
</script>

</head>
<body>
<form id="formSave" modelAttribute="devOpOID" action="${ctx}/cmdb/devOpOID/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" name="id" value="${id}"/>
		<input type="hidden" name="devOpName" id="devOpName" value=""/>
		<input type="hidden" name="modelName" id="modelName" value=""/>
		<input type="hidden" name="devTypeCode" id="devTypeCode" value=""/>
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
				<td align="right">操作名称：</td>
				<td>
				    <select name="opID" id="opID" onchange="getOperatinName();getDevOperationBySnmp();" style="width:202px">
						<option value="-1">请选择</option>
				    </select>
				</td>
				<td></td>
				<td align="right">操作中文名称：</td>
				<td>
				   <input id="operateName" name="operateName" value="" type="text" class="ip1" disabled="disabled"/>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td align="right">设备操作名称：</td>
				<td>
				    <select name="devOpID" id="devOpID" onchange="getDevOperatindesr();" style="width:202px">
						<option value="-1">请选择</option>
				    </select>
				</td>
				<td></td>
				<td align="right">设备操作描述：</td>
				<td>
				   <input id="devoOperateDres" name="devoOperateDres" value="" type="text" class="ip1" disabled="disabled"/>
				</td>
				<td></td>
			</tr>
			
			
			<tr>
			   <td align="right">设备分类：</td>
				<td>
				   <input id="devClassName" name="devClassName" value="" type="text" class="ip1" disabled="disabled"/>
				</td>
			   <td></td>
			  <td align="right">设备类型：</td>
				<td>
				   <input id="devTypeName" name="devTypeName" value="" type="text" class="ip1" disabled="disabled"/>
				</td>
			   <td></td>
			  
			</tr>
			
			<tr>
				<td align="right">厂商名称：</td>
				<td>
				    <select name="snmpEntID" id="snmpEntID" onchange="getOSName();getDevModel();" style="width:202px">
						<option value="-1">请选择</option>
				    </select>
				</td>
				<td></td>
				
			</tr>
			
			<tr>
			 <td align="right">设备型号：</td>
				<td>
				    <select name="devModel" id="devModel" style="width:202px" onchange="getModelOID()">
						<option value="-1">请选择</option>
				    </select>
				</td>
				<td></td>
			 <td align="right">设备OID：</td>
				<td>
				    <input id="model_OID" name="model_OID" value="" type="text" class="ip1" readonly="readonly"/>
				</td>
				<td></td>
			</tr>
			
<!-- 			<tr> -->
<!-- 			    <td align="right">操作系统：</td> -->
<!-- 				<td> -->
<!-- 				    <select name="osID" id="osID" onchange="getOSVersion();" style="width:202px"> -->
<!-- 						<option value="-1">请选择</option> -->
<!-- 				    </select> -->
<!-- 				</td> -->
<!-- 				<td></td> -->
<!-- 				<td align="right">版本号：</td> -->
<!-- 				<td> -->
<!-- 				    <select name="osVersion" id="osVersion" style="width:202px"> -->
<!-- 						<option value="-1">请选择</option> -->
<!-- 				    </select> -->
<!-- 				</td> -->
<!-- 				<td></td> -->
<!-- 			</tr> -->
			<tr>
			   <td align="right">程序变量名称：</td>
			   <td>
				    <input id="objectName" name="objectName" value="${opOID.objectName }" type="text" class="ip1"/>
			   </td>
			   <td></td>
			   <td align="right">Snmp对象：</td>
			   <td>
				    <input id="snmpObject" name="snmpObject" value="${opOID.snmpObject }" type="text" class="ip1"/>
			   </td>
			   <td></td>
			</tr>
			<tr>
			   <td align="right">SnmpOID：</td>
			   <td colspan="4">
				    <input id="snmpOID" name="snmpOID" value="${opOID.snmpOID }" type="text" class="ip2"/>
			   </td>
			   <td class="required"></td>
			</tr>
			
			<tr>
			   <td align="right">取值类型：</td>
			   <td><s:resSelect id="valueType" name="valueType" value="${opOID.valueType }" code="Cmdb_IndValueType"  ltype="select"  style="width:202px"/></td>
			   <td></td>
			   <td align="right">Snmp操作：</td>
			   <td>
				   <select name="getMethod" id="getMethod" style="width:202px" onchange="hideUnits()">
				   
				     <c:choose>
				        <c:when test="${opOID.getMethod eq 'SnmpGet' }">  
                           <option value="SnmpGet" selected="selected">SnmpGet</option>
                           <option value="SnmpSet">SnmpSet</option>
                           <option value="SnmpWalk">SnmpWalk</option>
                        </c:when> 
                        <c:when test="${opOID.getMethod eq 'SnmpSet' }">  
                         <option value="SnmpGet">SnmpGet</option>
                         <option value="SnmpSet" selected="selected">SnmpSet</option>
                          <option value="SnmpWalk">SnmpWalk</option>
                        </c:when> 
                          <c:when test="${opOID.getMethod eq 'SnmpWalk' }">  
                           <option value="SnmpGet">SnmpGet</option>
                           <option value="SnmpSet">SnmpSet</option>
                           <option value="SnmpWalk" selected="selected">SnmpWalk</option>
                          </c:when> 
                        <c:otherwise>
                          <option value="SnmpGet" selected="selected">SnmpGet</option>
                           <option value="SnmpSet">SnmpSet</option>
                           <option value="SnmpWalk">SnmpWalk</option>
                        </c:otherwise>
				     </c:choose>
				   
				    </select>
			   </td>
			   <td></td>
			</tr>
			<tr>
			   <td align="right"  id="units1">取值单位：</td>
			   <td id="units2">
				    <input id="units" name="units" value="${opOID.units }" type="text" class="ip1"/>
			   </td>
			   <td id="units3"></td>
			    <td align="right" id="setValue1">缺省赋值：</td>
			   <td id="setValue2">
				    <input id="setValue" name="setValue" value="${opOID.setValue }" type="text" class="ip1"/>
			   </td>
			   <td id="setValue3"></td>
			</tr>
			<tr>
           		<td colspan=6 align="center">
            	<table align="center">
            	<tr>
            		<!-- <td><input id="bnTest" type="button" value="测试" class="mg6 dN" /></td> -->
            	<td><input id="bnSave" type="submit" value="保 存" class="mg6" /></td>
            	<c:if test="${action=='add'}">
                <td><input id="bnSaveAdd" type="button" value="保存并继续添加" class="mg6" onclick="saveAdd()"/></td>
                </c:if>
            		</tr>
            	</table>
           		</td>
            </tr>
		</table>
    </form>
</body>
</html>

