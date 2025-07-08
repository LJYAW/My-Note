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
<title>刷新设备信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
	var searchMsg = null;
	var intvMsg = null;
	var dataMain = {};
	var gridMain;
	var selectRowData = null;
	$(function ()
	{	
	     $("#trTxt").removeClass("dN");
         $("#showlogs").css("display","");
        timer=setInterval("showTopoLogs()",2000);//1000为1秒钟
	});
	
  function showTopoLogs(){
      $.ajax({
        url:"${ctx}/fas/res/net/search/discoverDerviceOutputLogs.do",
        dataType:"json",
        success:function(data){
          var obj=data.outputstr;
//           var status=data.status;
  		  var str = JSON.stringify(obj) ;
		  str=str.replace(new RegExp("\"",'g'),"");
  		  if(str.length==0){
//   		      str="...";
  		      logStr+=str;
  		  }else{
  		     var logStr="设备搜索开始.......";
  		      logStr+="</br>"+str;
  		      $("#showlogs").html(logStr);
  		  }
  	   
          $("#showlogs").html(logStr);
          getScrollTop();
           if(logStr.indexOf("设备搜索结束")>0){  //搜索完成
            window.parent.$.ligerDialog.success("设备搜索结束！");
            clearInterval(timer);
            $("#trTxt").addClass("dN");
            window.parent.editOK(); 
          }
			
        }
      });
      count++;
    }
	function getScrollTop() {
		document.getElementById("showlogs").scrollIntoView(false);
	}
</script>

</head>
<body> 
	  <div style="margin:0 auto;width:96%;">
			<div id="showlogs" style="text-align: left;width:500px;height:auto;padding-left:15px;margin-left: auto;margin-right: auto;"></div>
			<div id="trTxt" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:center">
           		<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;搜索中...
           		</span>
            </div>
		</div>
</body>
</html>

