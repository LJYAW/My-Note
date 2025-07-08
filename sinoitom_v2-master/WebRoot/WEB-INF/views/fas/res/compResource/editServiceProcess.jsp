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
<title>进程信息维护</title>
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
	$(function ()
		{
            <c:if test="${result=='success'}">
	        	window.parent.location.reload(); 
	        	var mylay = parent.layer.getFrameIndex(window.name);
	        	//parent.layer.alert("进程信息维护成功！");
	        	parent.layer.close(mylay);
            </c:if>
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
		
</script>

</head>
<body style="overflow: hidden;">
	<form id="formSave" action="${ctx}/fas/res/host/resServiceProcess/serviceProcessSave.do" method="post">
		<input type="hidden" name="id" value="${id}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
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
				<td align="right">服务器IP：</td>
				<td><input id="hostIp" name="hostIp" value="${resServiceProcess.hostIp}" type="text" readonly="readonly" class="ip1"/></td>
				<td></td>
				<td align="right">进程ID：</td>
				<td><input id="pid" name="pid" value="${resServiceProcess.pid }" type="text" readonly="readonly" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
           		<td align="right" nowrap="nowrap">启动命令：</td>
				<td colspan=5><input id="startCmd" name="startCmd" value="${resServiceProcess.startCmd }" readonly="readonly" type="text" ltype="text" class=" ip2"  /></td>
            </tr>
			<tr>
				<td align="right">进程类型：</td>
				<td>
					<input id="procType" name="procType" value="${resServiceProcess.procType }" type="text" class="validate[required[进程类型不能为空]] ip1"/>
				</td>
				<td></td>
				<td align="right">进程名称：</td>
				<td><input id="procName" name="procName" value="${resServiceProcess.procName }" type="text"  class="validate[required[进程名称不能为空]] ip1" /></td>
				<td></td>
			</tr>
			<tr>
           		<td align="right" nowrap="nowrap">进程描述：</td>
				<td colspan=5><input id="procDescr" name="procDescr" value="${resServiceProcess.procDescr }"  type="text" ltype="text" class="validate[required[进程描述不能为空]] ip2"  /></td>
            </tr>
			<tr>
				<td align="right">进程标志：</td>
				<td>
					<select name="flag" id="flag" style="width: 200px;height:22px; ">
  						<option value="0" <c:if test="${resServiceProcess.flag==0}">selected</c:if> >系统进程</option>
					    <option value="1" <c:if test="${resServiceProcess.flag==1}">selected</c:if> >服务进程</option>   
					</select>
				</td>
				<td></td>
				<td align="right">监测状态：</td>
				<td>
					<select name="status" id="status" style="width: 200px;height:22px; ">
					    <option value="0" <c:if test="${resServiceProcess.status==0}">selected</c:if> >未监测</option>   
  						<option value="1" <c:if test="${resServiceProcess.status==1}">selected</c:if> >监测</option>
					</select>
				</td>
				<td></td>
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

