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
<title>修改</title>
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
    var passWord = '${psWord}';
    var orgName = '${orgName}';
	$(function ()
		{
            <c:if test="${result=='success'}">
            // var data = ${saveData};
            // window.parent.saveOK(data);
            window.parent.saveOK();
            window.parent.closeDlg();
            </c:if>
            
            getOrgInfo();
            checkHostType();
            getResTypeName();
            $("#action").val("edit");
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
	});
	
	//获取机构树信息
	function getOrgInfo(){
		$("#orgName").ligerComboBox({
        	width: 196,
        	selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgShortName',//返回的json串中的值的属性值，是标签要显示的字段 
        	valueFieldID: 'orgID',//提交时name对应的名称
        	treeLeafOnly: false,
        	initValue: '${resMdlMare.orgID==null ? LOGIN_USER_MAIN_ORGAN.orgId:resMdlMare.orgID}',
        	tree: { 
        		url: timeURL('${ctx}/system/organ/getMinDataNotNine.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgShortName', 
        		checkbox: false }
        		
<%--            onselected:function(row){--%>
<%--            	--%>
<%--            }		alert(row);--%>
        });
	}
	
	//判断hostType，确定隐藏还是显示“宿主机”文本框
    function checkHostType(){
 	   var str = $("#svrType").val();
 	   if(str==0){
 		   $(".resTypeCodeTR").css("display","");
 		   //$("#typeFlag").val("宿主机");
 		   getResTypeCode();
 	   }else{
 	       $(".resTypeCodeTR").css("display","none");
 	      
 	      $("#homedHostIp").val("");
 	      $("#homedHostId").val("");
 	      
 	       //$("#typeFlag").val("非宿主机");
 	       getSvrIpAddr();
 	   }
    }
  
  //获取宿主机下拉框
    function getResTypeCode(){
 		  var myname="homedHostId";
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
  
 // 获得服务器IP地址
    function getSvrIpAddr(){
	    var homedHostId=null;
	    var svrType=$("#svrType").find("option:selected").val();
	    if(svrType==0){
	    	homedHostId=$("#homedHostId").find("option:selected").val();
	    }
	     $.getJSON(
	         "${ctx}/fas/res/host/resHosts/getSvrIpAddr.do?homedHostId="+homedHostId+"&hostType="+svrType,
	         function (result) {
	             if (result.list != null) {
	                 $("#svrIpAddr").empty();
	                 for (var i = 0; i < result.list.length; i++) {
	                     var list = result.list[i];
	                     if(list.ipAddress=='${resMdlMare.svrIpAddr}'){
	                       $("#svrIpAddr").append("<option  value=\"" + list.ipAddress + "\"  selected>" + list.ipAddress + "</option>");
	                     }else{
	                       $("#svrIpAddr").append("<option  value=\"" + list.ipAddress + "\" >" + list.ipAddress + "</option>");
	                     }
	                     
	                 }
             }
         });
    }
 
 function getResTypeName(){
	 var resTypeName=$("#resTypeCode").find("option:selected").text();
	 $("#resTypeName").val(resTypeName);
 }
		
</script>

</head>
<body style="overflow: hidden;">
	<form id="formSave" modelAttribute="resMdlMare" action="${ctx}/fas/res/mdlware/editSave.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" id="resTypeName" name="resTypeName" />
<%--		<input type="hidden" id="orgID" name="orgID" />--%>
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
					<td align="right">IT资源分类：</td>
					<td><input readonly="readonly" id="resClassName" name="resClassName" value="中间件" type="text" ltype="text" class=" ip1"/></td>
					<td></td>
					
					<td align="right">所属机构：</td>
	                <td>
						<input id="orgName" name="orgName" value="${orgName}" type="text"  class="ip1" />
					</td>
	                <td></td>
				</tr>
				<tr>
					<td align="right">中间件类型：</td>
					<td><s:resSelect id="resTypeCode" name="resTypeCode" value="${resMdlMare.resTypeCode}" code="IT_MdlWareType" ltype="select" style="width: 200px;" onchange="getResTypeName();"/></td>
					<td></td>
					
					<td align="right">厂商/组织：</td>
	                <td>
						<input id="vendorName" name="vendorName" value="${resMdlMare.vendorName}" type="text"  class="ip1" />
					</td>
	                <td></td>
				</tr>
				<tr>
					<td align="right">品牌名称：</td>
					<td><input id="brandName" name="brandName" value="${resMdlMare.brandName}" type="text"  class="ip1" /></td>
					<td></td>
					
					<td align="right">中间件名称：</td>
	                <td>
						<input readonly="readonly" id="prodName" name="prodName" value="${resMdlMare.prodName}" type="text"  class="ip1" />
					</td>
	                <td></td>
				</tr>
             
             <tr> 
				<td align="right">服务器类型：</td>
				<td>
					<select disabled="disabled" name="svrType" id="svrType" style="width:200px;height: 25px;" >
						<option value="0"  <c:if test="${resMdlMare.svrType==0}">selected</c:if> >虚拟机</option>
						<option value="1"  <c:if test="${resMdlMare.svrType==1}">selected</c:if>  >物理机</option>
						<option value="3"  <c:if test="${resMdlMare.svrType==3}">selected</c:if> >集群</option>
					</select>
				</td>
				<td></td>
				
				<td align="right" class="resTypeCodeTR">宿主机：</td>
				<td>
					<input readonly="readonly" id="homedHostIp" name="homedHostIp" value="${resMdlMare.homedHostIp}" class="resTypeCodeTR" />
					<input type="hidden" id="homedHostId" name="homedHostId" value="${resMdlMare.homedHostId}" class="resTypeCodeTR" />
				</td>
				<td></td>
			</tr>
             
             <tr>
                <td align="right">服务器IP地址：</td>
                <td>
                	<input readonly="readonly" id="svrIpAddr" name="svrIpAddr" value="${resMdlMare.svrIpAddr}" class="ip1" />
                </td>
               <td></td>
               <td align="right">监测端口：</td>
               <td>
                  <input id="monPort" name="monPort" value="${resMdlMare.monPort}" type="text" ltype="text" class=" ip1"/>
                </td>
               <td></td>
            </tr>

				<tr>
					<td align="right">用户名：</td>
					<td>
						<input id="userName" name="userName" value="${resMdlMare.userName}"  type="text" class="validate[required[用户名不能为空],custom[onlyLetterNumber]]  ip1" />
					</td>
					<td ></td>

					<td align="right">密码：</td>
					<td>
						<input id="passWord" name="passWord" value="${psWord}"  type="password"  class="validate[required[密码不能为空],custom[onlyLetterNumber]]  ip1" />
					</td>
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
    </form>
    <br/>
</body>
</html>

