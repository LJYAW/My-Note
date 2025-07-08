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
<title>添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />


<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>



	<script type="text/javascript">
	$(function (){

        <c:if test="${result=='success'}">
        // var data = ${saveData};
        // window.parent.saveOK(data);

        window.parent.saveOK();
        window.parent.closeDlg();
        </c:if>
        
        getOrgInfo();
        checkHostType();
        //getSvrIpAddr();
        
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
	        });
		}
  

      function sendform(){
		   var devIpAddr;
		   if($("#devIpAddr").val()!="" && $("#devIpAddr").val()!=null){
		   devIpAddr=$("#devIpAddr").val();
		   }else{
		   devIpAddr="ip_kong";
		   }
		   var content;
		   if($("#content").val()!="" && $("#content").val()!=null){
		   content=$("#content").val();
		   }else{
		   content="!@!@";
		   }
      
      
       // var str = startTime+","+endTime+","+facilitys+","+severity+","+devIpAddr+","+content;

       // window.parent.saveOK(str);
       // window.location.href="${ctx}/fas/res/tom/index/showTomcatMessage.do";
	   // window.parent.closeDlg();


      
    }

    function checkIP(){
		if($("#devIpAddr").val()!="" && $("#devIpAddr").val()!=null){
		   var devIpAddr=$("#devIpAddr").val();
		   if (!ipok(devIpAddr)) {

				$.ligerDialog.warn('请输入合法IP地址');
               $("#devIpAddr").val("");
			}
		}
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
 	       //$("#typeFlag").val("非宿主机");
 	       $("#homedHostIp").val("");
 	      $("#homedHostId").val("");
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
	    var homedHostId="";
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
 
 
 //访问验证按钮
 function onAccessVerify(){
	 
	 $("#trTxt2").removeClass("dN");
	 
	 var svrIpAddr = $("#svrIpAddr").find("option:selected").text();
	 var monPort = $("#monPort").val();
	 var userName = $("#userName").val();
	 var passWord = $("#passWord").val();
	 
	 var url = '${ctx}/fas/res/mdlware/accessVerify.do';
		$.ajax({
 		url: url,
 		type:"POST",
 		data:{
 			svrIpAddr:svrIpAddr,
 			monPort:monPort,
 			userName:userName,
 			passWord:passWord,
 		},
 		dataType: 'json',
 		success: function(data) {
 			if( data.result == 'success' ){
 				layer.msg("验证成功！", {icon: 1, time: 1500});
 				$("#trTxt2").addClass("dN");
 			}else{
 				layer.msg(data.message, {icon: 7, time: 1500});
 				$("#trTxt2").addClass("dN");
 			}
 		}
 	});
 }
 
 
 //监测按钮
 function cleckMonitor(){
	 $("#trTxt1").removeClass("dN");
	 
	 var svrIpAddr = $("#svrIpAddr").find("option:selected").text();
	 var monPort = $("#monPort").val();
	 var userName = $("#userName").val();
	 var passWord = $("#passWord").val();
	 
	 var str = $("#svrType").val();
	   if(str==0){
		   var homedHostIp = $("#homedHostId").find("option:selected").text();
			 $("#homedHostIp").val(homedHostIp);
	   }else{
			 $("#homedHostIp").val("");
			 $("#homedHostId").val("");
	   }
	   var resTypeName = $("#resTypeCode").find("option:selected").text();
		 $("#resTypeName").val(resTypeName);
	 
	 var url = '${ctx}/fas/res/mdlware/accessVerify.do';
		$.ajax({
 		url: url,
 		type:"POST",
 		data:{
 			svrIpAddr:svrIpAddr,
 			monPort:monPort,
 			userName:userName,
 			passWord:passWord,
 		},
 		dataType: 'json',
 		success: function(data) {
 			if( data.result == 'success' ){
 				$("#formSave").submit();
 			}else{
 				layer.msg(data.message, {icon: 7, time: 1500});
 				$("#trTxt1").addClass("dN");
 			}
 		}
 	});
		
	 
 }

</script>

</head>
<body style="overflow: hidden;">
	<form:form id="formSave"  action="${ctx}/fas/res/mdlware/save.do" method="post">
		
		<input type="hidden" id="homedHostIp" name="homedHostIp" />
		<input type="hidden" id="resTypeName" name="resTypeName" />
		
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
						<input id="orgName" name="orgName" type="text"  class="ip1" />
					</td>
	                <td></td>
				</tr>
				<tr>
					<td align="right">中间件类型：</td>
					<td><s:resSelect id="resTypeCode" name="resTypeCode"  code="IT_MdlWareType" ltype="select" style="width: 200px;" /></td>
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
						<input readonly="readonly" id="prodName" name="prodName" value="Tomcat" type="text"  class="ip1" />
					</td>
	                <td></td>
				</tr>
             
             <tr> 
				<td align="right">服务器类型：</td>
				<td>
					<select name="svrType" id="svrType" style="width:200px;height: 25px;" onchange="checkHostType();">
						<option value="0"  <c:if test="${resMdlMare.svrType==0}">selected</c:if> >虚拟机</option>
						<option value="1"  <c:if test="${resMdlMare.svrType==1}">selected</c:if>  >物理机</option>
						<option value="3"  <c:if test="${resMdlMare.svrType==3}">selected</c:if> >集群</option>
					</select>
				</td>
				<td></td>
				
				<td align="right" class="resTypeCodeTR">宿主机：</td>
				<td>
					<select id="homedHostId" name="homedHostId" style="width:200px;height: 25px;" class="resTypeCodeTR" onchange="getSvrIpAddr();" >
                  	</select>
				</td>
				<td></td>
			</tr>
             
             <tr>
                <td align="right">服务器IP地址：</td>
                <td>
                 <select id="svrIpAddr" name="svrIpAddr"  style="width:202px" >
                 
                 </select>
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
						<input id="userName" name="userName" value="${resMdlMare.userName}" placeholder="请输入用户名" type="text" class="validate[required[用户名不能为空],custom[onlyLetterNumber]]  ip1" />
					</td>
					<td ></td>

					<td align="right">密码：</td>
					<td>
						<input id="passWord" name="passWord" value="${resMdlMare.passWord}" placeholder="请输入密码" type="password"  class="validate[required[密码不能为空],custom[onlyLetterNumber]]  ip1" />
					</td>
					<td></td>
				</tr>
				
				<tr>
	           		<td align="right" colspan=3 >
	           			<input id="svc" type="button" value="访问验证" class="l-button mg6" class="l-button mg6" onclick="onAccessVerify()"/>
	           		</td>
	           		<td align="left" colspan=3 >
		           		<input id="bnSave" type="button" value="监 测" onclick="cleckMonitor();"  class="l-button mg6" />
	           		</td>
	            </tr>

			</table>
			<div id="trTxt1" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:left">
				<div style="width: 50%" align="right">
				<span id="searching">
	           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;搜索监测中...
	           		</span>
				</div>
       		</div>
			<div id="trTxt2" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:left">
				<div style="width: 50%" align="right">
				<span id="searching">
	           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;验证中...
	           		</span>
				</div>
       		</div>
	</form:form>

</body>
</html>

