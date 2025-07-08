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
<title>机构编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/help.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/static/jquery-validationEngine/help.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/edit.js" type="text/javascript"></script>
<style type="text/css">
.help{
	background: url(../../img/helpmsg_tag.png) no-repeat center;
	background-size:15px;
	width: 17px;
	height: 15px;
	float: left;
	margin-top: 1px;
	cursor:pointer;
}
.required{
	background: url(../../img/required_tag.png) no-repeat center;
	background-size:15px;
	width: 17px;
	height:15px;
	float: left;
	margin-top: 1px;
	cursor:pointer;
	position: absolute;
}
</style>
<script type="text/javascript">

var parOrganId='${parentOrgan.orgId}';
var orgName='${organ.orgName}';
var Pid='${organ.parentId}';

	$(function ()
	{				
		<c:if test="${result=='success'}">
		window.parent.saveOK();  
		window.parent.closeDlg();
		</c:if>

		$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
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
		$('#orgNameHelp').helpInfo({text:'机构名称不能为空' });
		$('#phoneHelp').helpInfo({ text:'示例：010-62168888-123' });
	});
	
  
   //检查机构名称是否有重复
  function checkOrgan(){
   var organName=$("#orgName").val();
   var treeCode='${treeCode}';
    $.getJSON(
              "${ctx}/system/organ/checkOrgan.do?organName="+organName+"&treeCode="+treeCode,
              function (result) {
                  if (result.organs != null&&result.organs !="") {
                       window.parent.$.ligerDialog.error('该机构名称已存在,请重新填写！'); 
                       $("#orgName").val("");
                     }   
              });
   
  }
  function submitForm(){
      var Oname=$("#orgName").val();
	   if(Pid==parOrganId&&Oname==orgName){
	       $("#formSave").submit();
	   }else{
	        checkOrgName(Pid,Oname);
	   }
	 }
   function checkOrgName(parentId,orgName){
   
     $.getJSON(
               "${ctx}/system/organ/checkOrgName.do?parentId="+parentId+"&orgName="+orgName,
               function (data) {
                   if (data.result=="true") {
                        window.parent.$.ligerDialog.error('该机构名称已存在,请重新填写！'); 
                      } else{
                         $("#formSave").submit();
                      }  
               });
   
    }

 
     function orgNameOver(){
    		$("#orgNameHelp").prev('div').css("display","block");
    }
    function orgNameOut(){
    		$("#orgNameHelp").prev('div').css("display","none");
    } 
     function phoneOver(){
	$("#phoneHelp").prev('div').css("display","block");
	}
	function phoneOut(){
		$("#phoneHelp").prev('div').css("display","none");
	}
</script>

</head>
<body> 
	<div class="wFull hL">上级机构：${parentOrgan.orgName}</div>
	<form:form id="formSave" modelAttribute="org" action="${ctx}/system/organ/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" name="parentId" value="${organ.parentId}"/>
		<input type="hidden" name="id" value="${organ.orgId}"/>
		<input type="hidden" name="state" value="${organ.state}"/>
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
                <td align="right">机构名称:</td>
                <td><input id="orgName" name="orgName" value="${organ.orgName}" type="text" ltype="text" class="validate[required[机构名称不能为空],maxSize[64]] ip1" onblur="checkOrgan()"/></td>
                <td class="required" onmouseover="orgNameOver()" onmouseout="orgNameOut()" id="orgNameHelp"></td>
                <td align="right">机构类型:</td>
                <td><s:resSelect name="orgType" value="${organ.orgType}" code="organType" filter="resCode='9' or resCode > '${parentOrgan.orgType}'" ltype="select" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">机构编码:</td>
                <td><input id="orgCode" name="orgCode" value="${organ.orgCode}" type="text" ltype="text" class="validate[optional,maxSize[32]] ip1" /></td>
                <td></td>
                <td align="right">邮编:</td>
                <td><input id="zipCode" name="zipCode" value="${organ.zipCode}" type="text" ltype="text" class="validate[optional,maxSize[6],custom[zipCode]] ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">地址:</td>
                <td colspan=4><input id="address" name="address" value="${organ.address}" type="text" ltype="text" class="validate[optional,maxSize[64]] ip2"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">电话:</td>
                <td><input id="phone" name="phone" value="${organ.phone}" type="text" ltype="text" class="validate[optional,custom[phone]] ip1" /></td>
                <td onmouseover="phoneOver()" onmouseout="phoneOut()" id="phoneHelp" align="left" class="help"></td>
                <td align="right">传真:</td>
                <td><input id="fax" name="fax" value="${organ.fax}" type="text" ltype="text" class="validate[optional] ip1" /></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">说明:</td>
                <td colspan=4><input id="description" name="description" value="${organ.description}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip2"  /></td>
                <td></td>
            </tr>
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="button" value="保 存" class="l-button mg6" onclick="submitForm()"/>
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

