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
<title>数据库编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx }/static/assets/js/jquery.min.js"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script src="${ctx }/static/assets/js/H-ui.admin.js"></script>

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>

<script type="text/javascript">
	$(function (){				
		<c:if test="${result=='success'}">
			window.parent.saveOK();     
			window.parent.closeDlg();
		</c:if>
		<c:if test="${result=='error'}">
			window.parent.saveOK('','${message}');     
			window.parent.closeDlg();
		</c:if>
		getOrgInfo();
		checkHostType();
		getJdbcUrl();
	});
	
//获取机构树信息
	function getOrgInfo(){
		$("#orgName").ligerComboBox({
	    	width: 196,
	    	selectBoxWidth: 196, selectBoxHeight: 200, 
	    	textField: 'orgShortName',//返回的json串中的值的属性值，是标签要显示的字段 
	    	valueFieldID: 'orgId',//提交时name对应的名称
	    	treeLeafOnly: false,
	    	initValue: '${resDB.orgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:resDB.orgId}',
	    	tree: { 
	    		url: timeURL('${ctx}/system/organ/getMinDataNotNine.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
	    		textFieldName: 'orgShortName', 
	    		checkbox: false }
	    });
	}
		
	//判断hostType，确定隐藏还是显示“宿主机”文本框
   function checkHostType(){
	   var str = $("#svrType").val();
	   if(str==0){
		   $(".resTypeCodeTR").css("display","");
		   getResTypeCode();
	   }else{
	       $(".resTypeCodeTR").css("display","none");
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
		              $("#hostId").empty();
					 $("#hostName").empty();
					 $("#hostIpLong").empty();
					 $("#hostIp").empty();
		             for (var i = 0; i < result.list.length; i++) {
		                 var host = result.list[i];
		                 if(host.ipAddress=='${resDB.hostIp}'){
		                  $("#hostIp").append("<option  value=\"" + host.ipAddress + "\" hostId=\"" + host.hostId  + "\" hostName=\"" + host.hostName  + "\" hostIpLong=\"" + host.ipLong + "\"  selected>" + host.ipAddress + "</option>");
		                 }else{
		                  $("#hostIp").append("<option  value=\"" + host.ipAddress + "\" hostId=\"" + host.hostId  + "\" hostName=\"" + host.hostName  + "\" hostIpLong=\"" + host.ipLong  + "\" >" + host.ipAddress + "</option>");
		                 }
		             }
		             getHostInfo();
		         }
		     });
		}
		
		function getHostInfo(){
			var hostId = $('#hostIp').find("option:selected").attr("hostId");
			var hostName = $('#hostIp').find("option:selected").attr("hostName");
			var hostIpLong = $('#hostIp').find("option:selected").attr("hostIpLong");
			
			$("#hostId").val(hostId);
			$("#hostName").val(hostName);
			$("#hostIpLong").val(hostIpLong);
			
			getJdbcUrl();
		}
		
		function getResTypeName(){
			$("#resTypeName").empty();
			$("#dbType").empty();
			
			var resTypeName = $('#resTypeCode').find("option:selected").text();
			$("#resTypeName").val(resTypeName);
			$("#dbType").val(resTypeName);
			
			$("#jdbcDriver").empty();
			var jdbcDriver='';
			if(resTypeName=='Oracle'){
				jdbcDriver='oracle.jdbc.driver.OracleDriver';
			}else if(resTypeName=='DB2'){
				jdbcDriver='com.ibm.db2.jcc.DB2Driver';
			}else if(resTypeName=='SQLServer'){
				jdbcDriver='com.microsoft.sqlserver.jdbc.SQLServerDriver';
			}else if(resTypeName=='Informix'){
				jdbcDriver='com.informix.jdbc.IfxDriver';
			}else if(resTypeName=='MySQL'){
				jdbcDriver='com.mysql.jdbc.Driver';
			}else if(resTypeName=='H2'){
				jdbcDriver='org.h2.Driver';
			}else{
				jdbcDriver='';
			}
			$("#jdbcDriver").val(jdbcDriver);
		}
		
		function getJdbcUrl(){
		
			var resTypeName = $('#resTypeCode').find("option:selected").text();
			var hostIp =  $("#hostIp").val();
			var dbPort =  $("#dbPort").val();
			var dbName =  $("#dbName").val();
			
			$("#jdbcUrl").empty();
			var jdbcUrl='';
			if(resTypeName=='Oracle'){
// 			jdbc:oracle:thin:@127.0.0.1:1521:XE
				jdbcUrl='jdbc:oracle:thin:@';
				if(hostIp!=null){
					jdbcUrl += hostIp;
				}
				if(dbPort!=null){
					jdbcUrl += ':'+dbPort;
				}
				if(dbName!=null){
					jdbcUrl += ':'+dbName;
				}
			}else if(resTypeName=='DB2'){
// 			jdbc:db2://localhost:50000/FYRMDB
				jdbcUrl='jdbc:db2://';
				if(hostIp!=null){
					jdbcUrl += hostIp;
				}
				if(dbPort!=null){
					jdbcUrl += ':'+dbPort+'/';
				}
				if(dbName!=null){
					jdbcUrl += dbName;
				}
			}else if(resTypeName=='SQLServer'){
// 			jdbc:sqlserver://10.1.1.127:1433;databaseName=BaseSystem
				jdbcUrl='jdbc:sqlserver://';
				if(hostIp!=null){
					jdbcUrl += hostIp;
				}
				if(dbPort!=null){
					jdbcUrl += ':'+dbPort+';';
				}
				if(dbName!=null){
					jdbcUrl += 'databaseName='+dbName;
				}
			}else if(resTypeName=='Informix'){
// 			jdbc:informix-sqli://127.0.0.1:1533/testDB
				jdbcUrl='jdbc:informix-sqli://'+hostIp+':'+dbPort+'/'+dbName;
				if(hostIp!=null){
					jdbcUrl += hostIp;
				}
				if(dbPort!=null){
					jdbcUrl += ':'+dbPort+'/';
				}
				if(dbName!=null){
					jdbcUrl += 'databaseName='+dbName;
				}
			}else if(resTypeName=='MySQL'){
// 			jdbc:mysql://192.168.99.160:13306/acsdb?useUnicode=true&characterEncoding=utf8
				jdbcUrl='jdbc:mysql://';
				if(hostIp!=null){
					jdbcUrl += hostIp;
				}
				if(dbPort!=null){
					jdbcUrl += ':'+dbPort+'/';
				}
				if(dbName!=null){
					jdbcUrl += dbName+'?useUnicode=true&characterEncoding=utf-8';
				}
			}else if(resTypeName=='H2'){
// 			jdbc:h2:tcp://localhost/~/mini-web4
				jdbcUrl='jdbc:h2:tcp://';
				if(hostIp!=null){
					jdbcUrl += hostIp;
				}
				if(dbName!=null){
					jdbcUrl += '/~/'+dbName;
				}
			}else{
				jdbcUrl='';
			}
			$("#jdbcUrl").val(jdbcUrl);
		}
		
		function onAccessVerify(){
			var jdbcUrl = $("#jdbcUrl").val();
	        if (jdbcUrl == "" || jdbcUrl == null) {
	            layer.msg('服务器IP为空不能验证!', {icon: 7, time: 1500});
	            return;
	        }
	        
	        var data = $('#formSave').serialize();
//   			alert(data); 
	        
	        var url = '${ctx}/fas/res/db/index/accessVerify.do';
	        $.ajax({
			    type: 'POST',
			    url: url, 
			    async: false,
			    data:data,
			    success: function(data){
		         if (data!= null) {
		             if (data.result == "success") {
		             	$("#verifyStatus").val(1);
		             	var dbVersion = data.dbVersion;
		             	if(dbVersion!=null &&dbVersion!=undefined && dbVersion!=''){
		             		$("#dbVersion").val(dbVersion);
		             	}
	                    layer.msg('访问验证通过!', {icon: 1, time: 1500});
	                } else {
	                	$("#verifyStatus").val(0);
	                    layer.msg('访问验证失败!', {icon: 2, time: 1500});
	                }
		         }
			    },
			    error:function(data){
			    }
			});
        }

</script>

</head>
<body> 
	<form:form id="formSave"  action="${ctx}/fas/res/db/index/save.do" method="post">
        <input type="hidden"id="action" name="action" value="${action}"/>
        <input type="hidden"id="orgId" name="orgId" value="${resDB.orgId}"/>
        <input type="hidden"id="resTypeName" name="resTypeName" value="${resDB.resTypeName}"/>
        <input type="hidden"id="dbType" name="dbType" value="${resDB.dbType}"/>
        <input type="hidden"id="hostId" name="hostId" value="${resDB.hostId}"/>
        <input type="hidden"id="hostName" name="hostName" value="${resDB.hostName}"/>
        <input type="hidden"id="hostIpLong" name="hostIpLong" value="${resDB.hostIpLong}"/>
        <input  type="hidden" id="resClassCode" name="resClassCode" value="23" />
        <input type="hidden"id="auditStatus" name="auditStatus" value="${resDB.auditStatus}"/>
        <input type="hidden"id="verifyStatus" name="verifyStatus" value="${resDB.verifyStatus}"/>
        <input type="hidden"id="dbsId" name="dbsId" value="${resDB.dbsId}"/>
		
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
                <td align="right" nowrap>IT资源类型:</td>
                <td>
                	<input id="resClassName" name="resClassName" value="数据库" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
                <td align="right">所属机构:</td>
                <td>
                	<input id="orgName" name="orgName" value="" type="text"  class="ip1" />
                </td>
                <td></td>
            </tr>
             <tr>
                <td align="right" nowrap>服务器类型：</td>
                <td>
                 <select id="svrType" name="svrType" style="width:202px" onchange="checkHostType();getSvrIpAddr();">
                    <option value="0"  <c:if test="${resBizSystem.svrType==0}">selected</c:if> >虚拟机</option>
					<option value="1"  <c:if test="${resBizSystem.svrType==1}">selected</c:if>  >物理机</option>
					<option value="2"  <c:if test="${resBizSystem.svrType==2}">selected</c:if> >集群</option>
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
                <td align="right">服务器IP：</td>
                <td>
                 <select id="hostIp" name="hostIp"  style="width:202px" onchange="getHostInfo();getJdbcUrl()">
                 
                 </select>
                </td>
               <td></td>
               <td align="right">访问方式：</td>
               <td>
                  <select name="accessMode" id="accessMode">
                  	<option value="jdbc">jdbc</option>
                  	<option value="odbc">odbc</option>
                  </select>
                </td>
               <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>数据库类型:</td>
                <td><s:resSelect style="width:195px" id="resTypeCode" name="resTypeCode" value="${resDB.resTypeCode}" code="IT_DBType" ltype="select" onchange="getResTypeName();getJdbcUrl()" /></td>
                <td></td>
                <td align="right">数据库版本:</td>
                <td>
                	<input id="dbVersion" name="dbVersion" value="${resDB.dbVersion}" class="ip1" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>数据库名称:</td>
                <td>
                	<input id="dbName" name="dbName" value="${resDB.dbName}" onchange="getJdbcUrl()"  class="ip1" />
                </td>
                <td></td>
                <td align="right">服务端口:</td>
                <td>
                	<input id="dbPort" name="dbPort" onchange="getJdbcUrl()"  value="${resDB.dbPort}" class="ip1" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>用户名:</td>
                <td>
                	<input id="userName" name="userName" value="${resDB.userName}" class="ip1" />
                </td>
                <td></td>
                <td align="right">密码:</td>
                <td>
                	<input id="password" name="password" value="${resDB.password}" class="ip1" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>数据库驱动:</td>
                <td colspan=4><input id="jdbcDriver" name="jdbcDriver" value="${resDB.jdbcDriver}" type="text" class="ip2" ltype="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right" nowrap>访问URL:</td>
               <td colspan="4">
				 <textarea rows="4" cols="90"id="jdbcUrl" name="jdbcUrl" value="${resDB.jdbcUrl}">${resDB.jdbcUrl}</textarea>
                </td>
                <td></td>
            </tr>   
            <tr>
           		<td align="right" colspan=3 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
           		<td align="left" colspan=3 >
           		<input id="svc" type="button" value="访问验证" class="l-button mg6" style="width:70px" onclick="onAccessVerify()"/>
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

