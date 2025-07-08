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
<title>配置文件信息比对</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style>
	html{height:100%;font-size: 14px}
</style>
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function (){	
					
		
		var windowH=document.body.clientHeight;
			$("#file1").height(windowH);		
			$("#file2").height(windowH);
			
			
		$("#file1").scroll(function() {
       		 $("#file2").scrollTop($("#file1").scrollTop());
         });	
		
       });

		
</script>

</head>
<body style="height:100%;overflow: hidden;">
	<div style="width:100%;height:100%;">
	
		<div id="file1" style="width:50%;float:left;overflow-y:scroll">
		    <h3>${file1.cfgFileName }${baseline1 } </h3>
			${result1 }
		</div>
		
		<div id="file2" style="width:50%;float:left;overflow:hidden;">
		   <h3>${file2.cfgFileName } ${baseline2 }</h3>
			${result2 }
		</div>
	</div>
</body>
</html>
