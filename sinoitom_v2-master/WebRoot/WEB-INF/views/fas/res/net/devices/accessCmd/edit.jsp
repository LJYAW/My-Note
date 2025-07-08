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
<title>设备访问信息编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">

	$(function ()
	{		
		<c:if test="${result=='success'}">
		window.parent.saveOK();  
		</c:if>
		<c:if test="${result=='success' && closeDlg=='true'}">
		window.parent.closeDlg();  
		</c:if>
        
       // $("#formSave").ligerForm({inputWidth:200});
       $("input").filter(".ip1").ligerTextBox({ width: 200 });
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		//$("#formSave").validationEngine({promptPosition: "centerRight"});
		        
        $("#bnSave").click(function(){
        	$("#closeDlg").val("true");
        });
        
        $("#bnSaveAdd").click(function(){
        	$("#closeDlg").val("false");
        });

        $("#bnClose").click(function(){
        	window.parent.closeDlg();
        });

		<c:if test="${result=='error'}">
        $.ligerDialog.error('${message}');
		</c:if>	
		<c:if test="${result=='success' && closeDlg=='false'}">
        $.ligerDialog.success('保存成功！'); 
		</c:if>
		<c:if test="${result=='success' && action=='add'}">
		$("input[ltype='text']").val("");
		</c:if>
		$("#formSave").validationEngine();
		//表单验证
		$("#formSave").click(function(check) {    
	    	if($("#formSave").validationEngine()){
	    	}else{
	    		check.preventDefault();//此处阻止提交表单  
	    	}
		});
		
		refreshVendor();
		
		
		
	});
//加载厂商信息	
function refreshVendor(){
       	  $.getJSON(
            "${ctx}/cmdb/vendor/getVendorData.do",
              function (result) {
                  if (result.vendorlist != null) {
                      $("#vendorId").empty();
                      for (var i = 0; i < result.vendorlist.length; i++) {
                          var vendor = result.vendorlist[i];
                          if(vendor.vendorID=='${accessCmd.vendorId}'){
                            $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\" selected>" + vendor.dispName + "</option>");
                          }else{
                            $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                          }
                          
                      }
                      refreshOsType();
                  }
              });
    }	
	
	
//加载操作系统类型	
function refreshOsType(){
     var vendorID=$("#vendorId").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/ostype/getOsTypeByVendorID.do?vendorID="+vendorID,
              function (result) {
                  if (result.osTypes != null) {
                      $("#osType").empty();
                      for (var i = 0; i < result.osTypes.length; i++) {
                          var osType = result.osTypes[i];
                          if(osType.oSTypeName=='${accessCmd.osType}'){
                            $("#osType").append("<option  value=\"" + osType.oSTypeName + "\" typeCode=\"" + osType.osTypeCode + "\" selected>" + osType.oSTypeName + "</option>");
                          }else{
                            $("#osType").append("<option  value=\"" + osType.oSTypeName + "\" typeCode=\"" + osType.osTypeCode + "\" >" + osType.oSTypeName + "</option>");
                          }
                          
                      }
                      refreshOsName();
                  }
              });
    }

//加载操作系统名称	
function refreshOsName(){
     
     var vendorID=$("#vendorId").find("option:selected").val();
     var typeCode=$("#osType").find("option:selected").attr("typeCode");
       	  $.getJSON(
              "${ctx}/cmdb/os/getByVendorAndCode.do?vendorId="+vendorID+"&typeCode="+typeCode,
              function (result) {
                  if (result.osNames != null) {
                      $("#osName").empty();
                      for (var i = 0; i < result.osNames.length; i++) {
                          var osName = result.osNames[i];
                          if(osName.oSName=='${accessCmd.osName}'){
                            $("#osName").append("<option  value=\"" + osName.oSName + "\" selected>" + osName.oSName + "</option>");
                          }else{
                            $("#osName").append("<option  value=\"" + osName.oSName + "\">" + osName.oSName + "</option>");
                          }
                      }
                      refreshOsVersion();
                  }
              });
    }
    
//加载操作系统版本	
function refreshOsVersion(){
     var vendorID=$("#vendorId").find("option:selected").val();
     var typeCode=$("#osType").find("option:selected").attr("typeCode");
     var osName=$("#osName").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/vendorOSVersion/getOSVersionByVendorAndCodeAndOsName.do?vendorID="+vendorID+"&typeCode="+typeCode+"&oSName="+osName,
              function (result) {
                  if (result.vendorOSVersionlist != null) {
                      $("#osVersion").empty();
                      $("#osVersion").append("<option value=\"所有版本\">所有版本</option>");
                      for (var i = 0; i < result.vendorOSVersionlist.length; i++) {
                          var osVersion = result.vendorOSVersionlist[i];
                          if(osVersion.osVersion=='${accessCmd.osVersion}'){
                            $("#osVersion").append("<option  value=\"" + osVersion.osVersion + "\" selected>" + osVersion.osVersion + "</option>");
                          }else{
                            $("#osVersion").append("<option  value=\"" + osVersion.osVersion + "\">" + osVersion.osVersion + "</option>");
                          }
                          
                      }
                  }
              });
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
	<form id="formSave" action="${ctx}/devices/accessCmd/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="id" value="${accessCmd.devAcsCmdId}"/>
		<input type="hidden" id="vendorOid" name="vendorOid" value="${accessCmd.vendorOid}"/>
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
                <td align="right">设备厂商：</td>
                <td>
                 <select id="vendorId" name="vendorId" style="width:202px" onchange="refreshOsType()"></select>
				</td>
                <td></td>
                <td align="right">操作系统类型：</td>
                <td>
                 <select id="osType" name="osType" style="width:202px" onchange="refreshOsName()"></select>
                </td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">操作系统名称：</td>
                <td>
              		<select id="osName" name="osName" style="width:202px" onchange="refreshOsVersion()"></select>
                </td>
                <td ></td>
                <td align="right">操作系统版本：</td>
                <td>
               		<select id="osVersion" name="osVersion" style="width:202px" onchange=""></select>
                </td>
                <td></td>
            </tr>
            
             <tr>
               
                <td align="right">用户登录提示符：</td>
                <td><input id="userAcsPrompt" name="userAcsPrompt" value="${accessCmd.userAcsPrompt}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
                 <td align="right">输入密码提示符：</td>
                <td><input id="passwdPrompt" name="passwdPrompt" value="${accessCmd.passwdPrompt}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">用户模式提示符：</td>
                <td><input id="userModePrompt" name="userModePrompt" value="${accessCmd.userModePrompt}" type="text" ltype="text" class="validate[optional,maxSize[32]] ip1"/></td>
                <td></td>
                <td align="right">特权模式提示符：</td>
                <td>
                <input id="privModePrompt" name="privModePrompt" value="${accessCmd.privModePrompt}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right">特权模式命令：</td>
                <td><input id="privModeCmd" name="privModeCmd" value="${accessCmd.privModeCmd}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
                <td align="right">特权密码提示符：</td>
                <td><input id="privPasswdPrompt" name="privPasswdPrompt" value="${accessCmd.privPasswdPrompt}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
            </tr>
            
             <tr>
                <td align="right">命令执行模式：</td>
                <td>
                	<c:choose>
                	  <c:when test="${accessCmd.cmdExecMode==0}">
                	   <select name="cmdExecMode" style="width:200px">
                	    <option value="0" selected="selected">普通模式</option>
                	    <option value="1">特权模式</option>
                	   </select>
                	  </c:when>
                	  <c:otherwise>
                	     <select name="cmdExecMode" style="width:200px">
                	    	<option value="0">普通模式</option>
                	    	<option value="1" selected="selected">特权模式</option>
                	     </select>
                	  </c:otherwise>
                	</c:choose>
                </td>
                <td></td>
                <td align="right">错误命令提示：</td>
                <td><input id="errorCmdPrompt" name="errorCmdPrompt" value="${accessCmd.errorCmdPrompt}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
               
            </tr>
            
            
             <tr>
                <td align="right">退出命令：</td>
                <td><input id="logoutCmd" name="logoutCmd" value="${accessCmd.logoutCmd}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
                <td align="right">分页提示信息：</td>
                <td><input id="morePrompt" name="morePrompt" value="${accessCmd.morePrompt}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
                
            </tr>
            
           
            <tr>
               <td align="right">翻页命令：</td>
                <td><input id="moreCmd" name="moreCmd" value="${accessCmd.moreCmd}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
                <td align="right">不分页命令：</td>
                <td><input id="noMoreCmd" name="noMoreCmd" value="${accessCmd.noMoreCmd}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
              
            </tr>
          
          <tr>
                <td align="right">备注：</td>
                <td><input id="remark" name="remark" value="${accessCmd.remark}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
            </tr>
           
            <tr>
            	<td colspan=6 align="center">
            	<table align="center">
            		<tr>
            	<td><input id="bnSave" type="submit" value="保 存" class="l-button mg6" /></td>
            	<c:if test="${action=='add'}">
                <td><input id="bnSaveAdd" type="submit" value="保存并继续添加" class="l-button mg6"  style="width:108px;"/></td>
                </c:if>
                <td><input id="bnClose" type="button" value="取 消" class="l-button mg6" /></td>
            		</tr>
            	</table>
                </td>
            </tr>
		</table>
    </form>
</body>
</html>

