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
<title>设备操作OID详情</title>
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
</script>

</head>
<body>
	<form id="formSave" modelAttribute="devOperation" action="${ctx}/cmdb/devOpCmd/saveEditDevOpCmd.do" method="post">
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
				   <input id="operateName" name="operateName" value="${operation.operation }" type="text" class="ip1" disabled="disabled"/>
				</td>
				<td></td>
				<td align="right">操作中文名称：</td>
				<td>
				   <input id="operateName" name="operateName" value="${operation.operationName }" type="text" class="ip1" disabled="disabled"/>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td align="right">设备操作名称：</td>
				<td>
				   <input id="devoOpName" name="devoOpName" value="${devOp.devOpName }" type="text" class="ip1" disabled="disabled"/>
				</td>
				<td></td>
				<td align="right">设备操作描述：</td>
				<td>
				   <input id="devoOperateDres" name="devoOperateDres" value="${devOp.operation }" type="text" class="ip1" disabled="disabled"/>
				</td>
				<td></td>
			</tr>
			
			 <tr>
               <td align="right">厂商 ：</td>
                <td>
                 <input id="vendor" name="vendor" value="${vendor.dispName }" type="text" class="ip1"  disabled="disabled"/>
                </td>
                <td></td>
                 <td align="right">设备类型：</td>
                <td>
                 <input id="devTypeName" name="devTypeName" value="${devOp.devTypeName }" type="text" class="ip1"  disabled="disabled"/>
                </td>
               <td></td>
            </tr>
			
			<tr>
			    <td align="right">设备型号：</td>
				<td>
				    <input id="modelName" name="modelName" value="${opOID.modelName }" type="text" class="ip1"  disabled="disabled"/>
				</td>
				<td></td>
				<td align="right">设备OID：</td>
				<td>
				   <input id="model_OID" name="model_OID" value="${opOID.model_OID }" type="text" class="ip1"  disabled="disabled"/>
				</td>
				<td></td>
				
			</tr>
					<tr>
			   <td align="right">程序变量名称：</td>
			   <td>
				    <input id="objectName" name="objectName" value="${opOID.objectName }" type="text" class="ip1" disabled="disabled"/>
			   </td>
			   <td></td>
			   <td align="right">Snmp对象：</td>
			   <td>
				    <input id="snmpObject" name="snmpObject" value="${opOID.snmpObject }" type="text" class="ip1" disabled="disabled"/>
			   </td>
			   <td></td>
			</tr>
			<tr>
			   <td align="right">SnmpOID：</td>
			   <td colspan="4">
				    <input id="snmpOID" name="snmpOID" value="${opOID.snmpOID }" type="text" class="ip2" disabled="disabled"/>
			   </td>
			   <td></td>
			</tr>
			
			<tr>
			   <td align="right">取值类型：</td>
			   <td>
				    <input id="valueType" name="valueType" value="${valueType }" type="text" class="ip1" disabled="disabled"/>
			   </td>
			   <td></td>
			   <td align="right">Snmp操作：</td>
			   <td>
				   <select name="getMethod" id="getMethod" style="width:202px" disabled="disabled">
						<option value="SnmpGet" selected="selected">SnmpGet</option>
						<option value="SnmpSet">SnmpSet</option>
						<option value="SnmpWalk">SnmpWalk</option>
				    </select>
			   </td>
			   <td></td>
			</tr>
			<tr>
			   <td align="right"  id="units1">取值单位：</td>
			   <td id="units2">
				    <input id="units" name="units" value="${opOID.units }" type="text" class="ip1" disabled="disabled"/>
			   </td>
			   <td id="units3"></td>
			    <td align="right" id="setValue1">缺省赋值：</td>
			   <td id="setValue1">
				    <input id="setValue" name="setValue" value="${opOID.setValue }" type="text" class="ip1" disabled="disabled"/>
			   </td>
			   <td id="setValue1"></td>
			</tr>
			
		</table>
    </form>
    <br/>
</body>
</html>

