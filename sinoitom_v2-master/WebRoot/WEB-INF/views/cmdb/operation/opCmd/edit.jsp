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
               <td align="right">厂商 ：</td>
                <td>
                 <input id="vendor" name="vendor" value="${vendor.dispName }" type="text" class="ip1"  disabled="disabled"/>
                </td>
                <td></td>
                 <td align="right">设备类型：</td>
                <td>
                 <input id="devTypeName" name="devTypeName" value="${devOperation.description }" type="text" class="ip1"  disabled="disabled"/>
                </td>
               <td></td>
            </tr>
			
			<tr>
				<td align="right">产品型号OID：</td>
				<td>
				   <input id="modelOID" name="modelOID" value="${opCmd.modelOID }" type="text" class="ip1"  disabled="disabled"/>
				</td>
				<td></td>
				<td align="right">操作系统：</td>
				<td>
				    <input id="osName" name="osName" value="${opCmd.osName }" type="text" class="ip1"  disabled="disabled"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">版本号：</td>
				<td>
				<input id="osVersion" name="osVersion" value="${opCmd.osVersion }" type="text" class="ip1"  disabled="disabled"/>
				
				</td>
				<td></td>
				<td align="right">设备操作：</td>
				<td>
				<input id="operateName" name="operateName" value="${devOperation.operateName }" type="text" ltype="text" class="ip1" disabled="disabled" />
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td align="right">命令类型：</td>
                <td >
                 <s:resSelect id="cmdTypeCode" name="cmdTypeCode" value="${opCmd.cmdTypeCode}" code="Cmdb_DevCmdType"  ltype="select" style="width:202px" onchange=""/>
                </td>
                <td colspan="4"></td>
			</tr>
			
			
			
			<tr>
				<td align="right">参数个数：</td>
				<td>
				<input id="paramFlag" name="paramFlag" value="${opCmd.paramFlag }" type="text" class="ip1"  />
				</td>
				<td></td>
				<td align="right">期待提示符：</td>
				<td>
				<input id="expectPrompt" name="expectPrompt" value="${opCmd.expectPrompt }" type="text" ltype="text" class="ip1"  />
				</td>
				<td></td>
			</tr>
			
			<tr>
			   <td align="right">操作命令：</td>
				<td colspan="4">
				 <input id="command" name="command" value="${opCmd.command }" type="text" class="ip2" />
				</td>
				<td></td>
			</tr>
			
			<tr>
			  <td align="right">命令说明：</td>
				<td colspan="4">
				 <input id="description" name="description" value="${opCmd.description }" type="text" class="ip2" />
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

