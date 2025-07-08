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
<title>配置文件比较</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
<style>
	html{height:100%;}
</style>
<script type="text/javascript">

        var height=window.screen.height;
        var width=window.screen.width;
	$(function (){	
					
		var windowH=document.body.clientHeight;
			$("#file1").height(windowH);		
			$("#file2").height(windowH);	
       });

		window.onresize = function(){
			var windowH=document.body.clientHeight;
			$("#file1").height(windowH);		
			$("#file2").height(windowH);	
		}
		
		
		function compare(){
		   var ids="";
		   var count1=0;
		   var count2=0;
		   var objs1=document.getElementsByName("file1");
		    for(var i=0;i<objs1.length;i++){
		      if($(objs1[i]).attr("checked")){
				ids+=$(objs1[i]).val()+";";
				count1++;
				}
  			}
  			
  			if(count1<=0){
  			  $.ligerDialog.warn("请选择设备IP:"+'${devMap1.devIP }'+"</br>的一个配置文件");
  			  return;
  			}else if(count1>1){
  			  $.ligerDialog.warn("设备IP:"+'${devMap2.devIP }'+"</br>只能选择一个配置文件");
  			  return;
  			}
  			
  			var objs2=document.getElementsByName("file2");
		    for(var i=0;i<objs2.length;i++){
		      if($(objs2[i]).attr("checked")){
				ids+=$(objs2[i]).val()+";";
				count2++;
				}
  			}
  			
  			if(count2<=0){
  			  $.ligerDialog.warn("请选择设备IP:"+'${devMap2.devIP }'+"</br>的一个配置文件");
  			  return;
  			}else if(count2>1){
  			   $.ligerDialog.warn("设备IP:"+'${devMap2.devIP }'+"</br>只能选择一个配置文件");
  			  return;
  			}
  			
  			  var url = timeURL('${ctx}/config/configFile/compare.do?ids='+ids);
         		 window.open(url,"文件对比", "height="+height+", width="+width+", top=160, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no");
		}
</script>

</head>
<body style="height:100%;overflow: hidden;">
	<div style="width:100%;height:100%;">
	
		<div  style="width:99%;text-align: center">
		<table  align="center">
		  <tr>
		    <td>比较规则：两设备各选中一个配置文件进行比较</td>
		  </tr>
		  <tr>
		    <td align="center"><input id="bnSave" type="submit" value="比较" class="l-button mg6" onclick="compare()"/></td>
		  </tr>
		</table>
		</div>
	    
		<div id="file1" style="width:50%;float:left;overflow:auto">
		    <h4 align="center">设备${devMap1.devIP }配置文件 </h4>
		    <table align="center">
		      <c:forEach items="${file1s }" var="file">
		      	 <tr>
		          <td><input type="checkbox" name="file1" value="${file.id}" >${file.cfgFileName }</input></td>
		        </tr>
		   	  </c:forEach>
		    </table>
		</div>
		
		<div id="file2" style="width:50%;float:left;overflow: auto">
		   <h4 align="center">设备${devMap2.devIP }配置文件  </h4>
		    <table align="center" >
		      <c:forEach items="${file2s }" var="file">
		      	 <tr>
		          <td><input type="checkbox" name="file2" value="${file.id}">${file.cfgFileName }</input></td>
		        </tr>
		   	  </c:forEach>
		    </table>
		</div>
	</div>
</body>
</html>
