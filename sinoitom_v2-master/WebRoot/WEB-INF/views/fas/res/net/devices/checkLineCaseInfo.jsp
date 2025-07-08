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
<title>命令执行结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/js/plugin/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<%-- <script src="${ctx}/static/ligerUI1.2.2/js/ligerui.min.js" type="text/javascript"></script> --%>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">
$(function (){
	exeCmds();
	var timer;
	
});
var ip = '${ip}';
// 	获取结果
	function exeCmds(){
		
		$.ajax({
		   url:"${ctx}/fas/res/net/devices/exeCmds.do",
		   dataType:"json",
		   success:function(data){
		   		if(data.connError!='' && data.connError!=null && data.connError!=undefined){
		   			$("#emptyData").hide();
		   			window.parent.$.ligerDialog.confirm(data.connError, function (yes){
		 				if(yes) {
		                    window.close();
						}
					 });
		   		}
		   }
		 });
	   timer=setInterval("showTopoLogs()",2000);//1000为1秒钟
	}
	
    function getScrollTop() {
		document.getElementById("message").scrollIntoView(false);
		document.getElementById("emptyData").scrollIntoView(false);
	} 
	
	
	function showTopoLogs(){
      $.ajax({
        url:"${ctx}/fas/res/net/devices/outputLogs.do",
        dataType:"json",
        success:function(data){
          var obj=data.outputstr;
  		  var str = JSON.stringify(obj) ;
		  str=str.replace(new RegExp("\"",'g'),"");//把双引号去掉
  		  if(str.length==0){
  		      str="...";
  		      logStr+=str;
  		  }else{
  		  		var logStr="<h4>"+ip+"命令执行开始.......</h4>";
  		  		
  		      logStr+="</br>"+str;
  		       $("#message").html(logStr);
  		  }
          
          getScrollTop();
           if(logStr.indexOf("命令执行结束")>0){  //搜索完成
            $("#emptyData").hide();
            window.parent.$.ligerDialog.success("信息输出完毕...！");
            clearInterval(timer);
          }
			
        }
      });
    }
</script>
</head>
<body>


	<h3 id="ip" style="text-align: center;"></h3>
	
	<div id="message" style="padding-left: 20px;"></div>
	<div  id="emptyData" align="center">
		<img style="margin: 50px;" src="${ctx}/img/loading.gif"/>
  	</div>
			 
</body>
</html>

