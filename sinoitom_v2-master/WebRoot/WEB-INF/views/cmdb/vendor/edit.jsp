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
<title>添加厂商信息</title>
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
			window.parent.parent.closeDlg();
			</c:if>
			$("#formSave").validationEngine({promptPosition: "centerRight"});
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
		$("#vendorID").blur(function(){
		  var flag='${action}';
		  if(flag=='add'){
			var url = timeURL('${ctx}/cmdb/vendor/getVendorID.do');
			$.ajax({
				url : url,
				data : "date=" + new Date(),
				dataType : 'json',
				success : function(data) {
					for(var i=0;i<data.length;i++){
					   var vendorID=$("#vendorID").val();
					   if(vendorID==data[i].id){
					      $.ligerDialog.warn("该厂商标识已存在！");
					      return false;
					   }
					}
			        //document.getElementById("formSave").submit();
				}
	  	    });
		  }else{
		     //document.getElementById("formSave").submit();
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
	<form id="formSave" modelAttribute="cmdbVendor" action="${ctx}/cmdb/vendor/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
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
				<td align="right">厂商标识：</td>
				<td><input id="vendorID" name="vendorID" value="${cmdbVendor.vendorID}" type="text" class="validate[required[厂商标识不能为空],custom[onlyNumberSp]]  ip1"/></td>
				<td class="required"></td>
				<td align="right">厂商名称：</td>
				<td><input id="vendorName" name="vendorName" value="${cmdbVendor.vendorName }" type="text" class="validate[required[厂商名称不能为空]] ip1" /></td>
				<td  class="required"></td>
			</tr>
			<tr>
				<td align="right">显示名称：</td>
				<td>
					<input id="dispName" name="dispName" value="${cmdbVendor.dispName }" type="text" class="validate[required[显示名称不能为空]] ip1"/>
				</td>
				<td class="required"></td>
				<td align="right">厂商全称：</td>
				<td><input id="fullName" name="fullName" value="${cmdbVendor.fullName }" type="text" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">网站地址：</td>
				<td><input id="webURL" name="webURL" value="${cmdbVendor.webURL }" type="text" ltype="text" class="validate[custom[url]] ip1"  /></td>
				<td></td>
				<td align="right">厂商状态：</td>
				<td><select name="flag" id="flag" style="width: 200px">
				    <option value="1">有效</option>
				    <option value="0">停用</option>
				</select>
				</td>
				<td></td>
			</tr>
			<tr>
			   <td align="right">描述：</td>
			   <td colspan="5"><textarea id="description" name="description" type="text" ltype="text" cols="180" style="width: 544px">${cmdbVendor.description }</textarea></td>
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
    </form>
    <br/>
</body>
</html>

