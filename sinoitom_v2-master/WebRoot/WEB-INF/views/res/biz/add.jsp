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
<title>业务系统---添加</title>
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
$(function (){

<c:if test="${result=='success'}">
var action = '${action}';
window.parent.saveOK(action);
window.parent.closeDlg();
</c:if>

<c:if test="${result=='error'}">
$.ligerDialog.error('${message}');
</c:if>

// getOrg();
getOrgInfo();
checkHostType();
 getResTypeName();
 getSvrIpAddr();
 checkAccessProt();//
 getBizURL();//自动拼接地址
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

// var orgId = '${resBizSystem.orgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:resBizSystem.orgId}';
//         $("#orgName").ligerComboBox({
//         	width: 200, selectBoxWidth: 200, selectBoxHeight: 200, 
//         	textField: 'orgName',
//         	valueFieldID: 'orgId',
//         	treeLeafOnly: false,
//         	initValue: orgId,
//         	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
//         		textFieldName: 'orgName', checkbox: false },
// 	        onSelected: function (id, text)
// 	        {
// 	        }
//         });

});

//获取机构树信息
function getOrgInfo(){
		$("#orgName").ligerComboBox({
        	width: 196,
        	selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgShortName',//返回的json串中的值的属性值，是标签要显示的字段 
        	valueFieldID: 'orgId',//提交时name对应的名称
        	treeLeafOnly: false,
        	initValue: '${resBizSystem.orgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:resBizSystem.orgId}',
        	tree: { 
        		url: timeURL('${ctx}/system/organ/getMinDataNotNine.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgShortName', 
        		checkbox: false }
        });
	}


function getOrg(){
 $.getJSON(
     "${ctx}/system/organ/getOrganNameNotNine.do",
     function (result) {
         if (result.orglist != null) {
             $("#orgId").empty();
             for (var i = 0; i < result.orglist.length; i++) {
                 var orglist = result.orglist[i];
                 if(orglist.id=='${resBizSystem.orgId}'){
                   $("#orgId").append("<option  value=\"" + orglist.id + "\"  selected>" + orglist.orgShortName + "</option>");
                 }else{
                   $("#orgId").append("<option  value=\"" + orglist.id + "\" >" + orglist.orgShortName + "</option>");
                 }
                 
             }
             getOrgName();
         }
     });
}

// 获得服务器IP地址
function getSvrIpAddr(){
var svrId="";
var svrType=$("#svrType").find("option:selected").val();

if(svrType==0){
 svrId=$("#svrId").find("option:selected").val();
}


 $.getJSON(
     "${ctx}/fas/res/host/resHosts/getSvrIpAddr.do?homedHostId="+svrId+"&hostType="+svrType,
     function (result) {
         if (result.list != null) {
             $("#svrIpAddr").empty();
             for (var i = 0; i < result.list.length; i++) {
                 var list = result.list[i];
                 if(list.ipAddress=='${resBizSystem.svrIpAddr}'){
                   $("#svrIpAddr").append("<option  value=\"" + list.ipAddress + "\"  selected>" + list.ipAddress + "</option>");
                 }else{
                   $("#svrIpAddr").append("<option  value=\"" + list.ipAddress + "\" >" + list.ipAddress + "</option>");
                 }
                 
             }
            
         }
     });
}

function getOrgName(){
  	 	var desc=$("#orgId").find("option:selected").text();
  	 	$("#orgName").val(desc);
  	 	
  	}
  function getResTypeName(){
  	 	var desc=$("#resTypeCode").find("option:selected").text();
  	 	$("#resTypeName").val(desc);
  	 	
  	}
  	
  	
  	//判断系统访问方式值，确定隐藏还是显示“访问协议”文本框
   function checkAccessProt(){
	   var accessMode = $("#accessMode").val();
	   if(accessMode=="C/S"){
	   $(".port").css("display","none"); 
	   }else{
	      $(".port").css("display",""); 
	   }
   }
   //自动拼接地址
   function getBizURL(){
    var accessMode = $("#accessMode").val();
    var accessProt = $("#accessProt").val();
    var svrIpAddr = $("#svrIpAddr").val();
    var svcPort = $("#svcPort").val();
	 if(accessMode=="B/S"){
	 	if(svcPort=="80"){
	 		$("#bizURL").val(accessProt+'://'+svrIpAddr);
	 	}else{
	 		 $("#bizURL").val(accessProt+'://'+svrIpAddr+':'+svcPort);
	 	}
	  
	 }else{
	 	$("#bizURL").val(" ");
	 }
   		
   }
   
//判断hostType，确定隐藏还是显示“宿主机”文本框
   function checkHostType(){
	   var str = $("#svrType").val();
	   if(str==0){
		   $(".resTypeCodeTR").css("display","");
// 		   $("#typeFlag").val("宿主机");
		   getResTypeCode();
	   }else{
	       $(".resTypeCodeTR").css("display","none");
// 	       $("#typeFlag").val("非宿主机");
	   }
   }
   
   //获取宿主机下拉框
   function  getResTypeCode(){
		  var myname="svrId";
		  async: false ,
	           $.getJSON(
	               "${ctx}/fas/res/host/resHosts/getHostByHostType.do",
	               function (result) {
	                   if (result != null) {
	                       $("#" + myname).empty();
	                       for (var i = 0; i < result.list.length; i++) {
	                          var host= result.list[i];
	                          if(host.hostId=='${ipHost.hostId}'){
	                              $("#" + myname).append("<option  value=\"" + host.hostId + "\" selected>" + host.ipAddress + "</option>");  
	                          }else{
	                              $("#" + myname).append("<option  value=\"" + host.hostId + "\">" + host.ipAddress + "</option>");
	                          }
	                       }
	                       getSvrIpAddr();
	                         
	                   }
	               }); 
		  
		}

//保存
function add(){
if("edit" != '${action}'){
	$("#action").val("add");
}
$("#formSave").submit();
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
	<form:form id="formSave" modelAttribute="resBizSystem" action="${ctx}/res/biz/save.do" method="post">
	<input type="hidden" id="action" name="action" value="${action}"/>
	<input type="hidden" id="id" name="id" value="${id}"/>
	<input type="hidden" id="resClassCode" name="resClassCode" value="27"/>
	<input type="hidden" id="orgId" name="orgId" value="${resBizSystem.orgId}"/>
	<input type="hidden" id="resTypeName" name="resTypeName" value=""/>
<!-- 	<input type="hidden" id="bizTypeCode" name="bizTypeCode" value="2"/> -->
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
			 	<td align="right">IT资源分类 ：</td> 
                <td>
                 <input id="resClassName" name="resClassName" value="业务系统" type="text" ltype="text" class=" ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right">所属机构：</td>
                <td>
					<input id="orgName" name="orgName" value="${resBizSystem.orgName}" type="text"  class="ip1" />
				</td>
                <td></td>
              
                
            </tr>
             <tr>
                <td align="right">系统类型：</td>
                <td>
                  <s:resSelect id="resTypeCode" name="resTypeCode" value="${resBizSystem.resTypeCode}" code="IT_bizSysType"  ltype="select" style="width:200px" onchange=" getResTypeName();"/>
                </td>
               <td></td>
               <td align="right">系统子类：</td>
               <td>
<!--                   <select id="bizTypeName" name="bizTypeName" style="width:202px" > -->
<!--                   <option value="对公业务系统">对公业务系统</option> -->
<!--                   </select> -->
                  <input id="bizTypeName" name="bizTypeName" value="${resBizSystem.bizTypeName}" type="text"  class="ip1" />
               </td>  
               <td></td>
            </tr>
            
			
            <tr>
                <td align="right" nowrap>系统英文名称：</td>
                <td>
                <input id="sysEnName" name="sysEnName" value="${resBizSystem.sysEnName}" type="text" ltype="text" class="validate[required[系统英文名称不能为空]] ip1"/>
                </td>
               <td  class="required"></td>
               <td align="right" nowrap>系统中文名称：</td>
                <td>
                <input id="sysName" name="sysName" value="${resBizSystem.sysName}" type="text" ltype="text" class="validate[required[系统中文名称不能为空]] ip1"/>
                </td>
               <td  class="required"></td>
            </tr>
             <tr>
                <td align="right">业务系统描述：</td>
                <td colspan="4"><input id="bizDesc" name="bizDesc" value="${resBizSystem.bizDesc}" type="text" ltype="text" class=" ip2"/></td>
                <td ></td>
            </tr>
            
             <tr>
                <td align="right">系统访问方式：</td>
                <td>
                 <select id="accessMode" name="accessMode" style="width:202px" onchange="checkAccessProt();getBizURL();" >
                    <option value="B/S" <c:if test="${resBizSystem.accessMode=='B/S'}">selected</c:if> >B/S</option>
                    <option value="C/S" <c:if test="${resBizSystem.accessMode=='C/S'}">selected</c:if> >C/S</option>
                 </select>
                </td>
               <td></td>
               <td align="right" class="port">访问协议：</td>
               <td>
                  <select id="accessProt" name="accessProt" style="width:202px"class="port" onchange="getBizURL();">
                  	<option value="http" <c:if test="${resBizSystem.accessProt=='http'}">selected</c:if>>http</option>
                    <option value="https" <c:if test="${resBizSystem.accessProt=='https'}">selected</c:if>>https</option>
                  </select>
               </td>  
               <td></td>
            </tr>
            <tr>
                <td align="right">服务器类型：</td>
                <td>
                 <select id="svrType" name="svrType" style="width:202px" onchange="checkHostType();getSvrIpAddr();">
                    <option value="0"  <c:if test="${resBizSystem.svrType==0}">selected</c:if> >虚拟机</option>
					<option value="1"  <c:if test="${resBizSystem.svrType==1}">selected</c:if>  >物理机</option>
					<option value="3"  <c:if test="${resBizSystem.svrType==3}">selected</c:if> >集群</option>
                 </select>
                </td>
               <td></td>
               <td align="right" class="resTypeCodeTR">宿主机：</td>
               <td>
                  <select id="svrId" name="svrId" style="width:202px"  class="resTypeCodeTR" onchange="getSvrIpAddr();">
                  </select>
               </td>  
               <td></td>
            </tr>
             <tr>
                <td align="right">服务器IP地址：</td>
                <td>
                 <select id="svrIpAddr" name="svrIpAddr"  style="width:202px" onchange="getBizURL();">
                 
                 </select>
                </td>
               <td></td>
               <td align="right">服务端口：</td>
               <td>
                  <input id="svcPort" name="svcPort" value="${resBizSystem.svcPort}" type="text" ltype="text" class="validate[custom[onlyNumberSp]] ip1"onchange="getBizURL();"/>
                </td>
               <td  class="required"> </td>
            </tr>
            <tr>
                <td align="right" nowrap>访问地址(URL)：</td> 
                <td colspan="4"><input id="bizURL" name="bizURL" value="${resBizSystem.bizURL}" type="text" ltype="text" class="validate[custom[url]]  ip2"/></td>
                <td  class="required"></td>
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

