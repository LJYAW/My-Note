<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

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

.l-text-field {
    background: none repeat scroll 0% 0% #FFF!important;
}

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
		
		$("#ID").ligerComboBox({ 
			url: timeURL('${ctx}/cmdb/vendor/getVendor.do'),
        	width: 196, selectBoxWidth: 196, selectBoxHeight: 200, 
			textField: 'dispName', 
			valueFieldID: 'vendorID',
        	initValue: '${oid.vendorID}',
        	onSelected: function(value){
        		$("#vendorID").val(value);
       			$("#venID").val(value);
       			$("#vendorOID").val(".1.3.6.1.4.1."+value);
        	}
		});
	
		refreshOrgFlag();
	
		$("#formSave").ligerForm({inputWidth:200});
 		$("input").filter(".ip2").ligerTextBox({ width: 553 });
 		$("#formSave").validationEngine({promptPosition: "centerRight"});	

 	});
	
  	
  
  	
  	function refreshVendor() {
        $.getJSON(
            "${ctx}/cmdb/vendor/allVendor.do",
            function (result) {
                if (result.vendors != null) {
                    for (var i = 0; i < result.vendors.length; i++) {       	            
                        var vendor = result.vendors[i]; 
                        if(vendor.vendorID=='${Venid}'){
                         $("#vendorID").append("<option  value=\"" + vendor.vendorID + "\" selected>" + vendor.dispName + "</option>");
                        }else{
                         $("#vendorID").append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                        }
                    }
                }
            });
  	}

	function refreshVenId() {
		var ven=$("#vendorID").find("option:selected").val();
		$("#venID").val(ven);
		$("#vendorOID").val(".1.3.6.1.4.1."+ven);
	}

	function refreshOrgFlag() {
		if(1=='${oid.oidFlag}'){
			$("#oidFlag option[value='1']").attr("selected", true);
		}else if(0=='${oid.oidFlag}' && "" != '${oid.vendorID}'){
			$("#oidFlag option[value='0']").attr("selected", true);
		}
	}
	
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="oid" action="${ctx}/cmdb/vendorOID/save.do" method="post">
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
			</tr>
            <tr>
                <td align="right">设备厂商：</td>
                <td>
                  <input id="ID" name="ID" type="text" class="ip1"/>
                  <input id="vendorID" name="vendorID" type="hidden" />
                </td>
                <td></td>
                <td align="right">厂商标识 ：</td>
                <td>
                  <input id="venID" name="venID" value="${oid.vendorID}"  type="text" class="validate[required[厂商标识不能为空，请选择设备厂商]] ip1" readonly/>
                </td>
                <td class="required"></td>
            </tr>
            <tr>
                <td align="right">厂商OID：</td>
                <td>
                	<input id="vendorOID" name="vendorOID" value="${oid.vendorOID}" type="text" class="ip1" />
                </td>
                <td></td>
                <td align="right">状态 ：</td>
                <td>
                  <select id="oidFlag" name="oidFlag" style="width:202px" >
                    <option value="1">有效</option>
                    <option value="0">无效</option>
                  </select>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right">备注：</td>
                <td colspan="4"><input id="remark" name="remark" value="${oid.remark}" type="text" ltype="text"  class="validate[optional,maxSize[32]] ip2" /></td>
                <td></td>
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

