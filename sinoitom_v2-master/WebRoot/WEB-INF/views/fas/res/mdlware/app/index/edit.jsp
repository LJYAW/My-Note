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
	$(function ()
		{
            <c:if test="${result=='success'}">
            // var data = ${saveData};
            // window.parent.saveOK(data);
            window.parent.saveOK();
            window.parent.closeDlg();
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
<body>
	<form id="formSave" modelAttribute="middlewareMonitor" action="${ctx}/fas/res/mdlware/app/index/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
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
				<td align="right">服务器IP：</td>
				<td><input id="devIpAddr" name="devIpAddr" value="${middlewareMonitor.devIpAddr}" type="text" disabled="disabled" class="ip1"/></td>
				<td></td>
				<td align="right">端口号：</td>
				<td><input id="content" name="content" value="${middlewareMonitor.content }" type="text" disabled="disabled" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">厂商：</td>
				<td>
					<input id="vmVendor" name="vmVendor" value="${middlewareMonitor.vmVendor }" type="text" disabled="disabled" class="ip1"/>
				</td>
				<td></td>
				<td align="right">虚拟机：</td>
				<td><input id="vmName" name="vmName" value="${middlewareMonitor.vmName }" type="text" disabled="disabled" class="ip1" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">版本：</td>
				<td><input id="vmVersion" name="vmVersion" value="${middlewareMonitor.vmVersion }" type="text" disabled="disabled" ltype="text" class="ip1"  /></td>
				<td></td>
				<td align="right">连续工作时间：</td>
				<td><input id="uptime" name="uptime" value="${middlewareMonitor.uptime }" type="text" disabled="disabled" ltype="text" class="ip1"  /></td>
				<td></td>
			</tr>
			<tr>
			   	<td align="right">启动时间：</td>
				<td><input id="startTime" name="startTime" value="${middlewareMonitor.startTime }" type="text" disabled="disabled" ltype="text" class="ip1"  /></td>
				<td></td>
				<td align="right">tomcat版本：</td>
				<td><input id="tomcatVersion" name="tomcatVersion" value="${middlewareMonitor.tomcatVersion }" disabled="disabled" type="text" ltype="text" class="ip1"  /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">用户名：</td>
				<td><input id="userName" name="userName" value="${middlewareMonitor.userName }" type="text" ltype="text" class="validate[required[用户名不能为空],custom[onlyLetterNumber]] ip1"  /></td>
				<td ></td>
				<td align="right">密码：</td>
				<td><input id="psWord" name="psWord"  type="password" value="${psWord}" class="validate[required[密码不能为空],custom[onlyLetterNumber]] ip1"  /></td>
				<td ></td>
			</tr>
			<tr>
				<td align="right">间隔时间：</td>
				<td>
					<select name="time" id="time" style="width:204px">
						<c:choose>
							<c:when test="${middlewareMonitor.intervaltime==60000}">
								<option value="1Min" selected="selected">1分钟</option>
								<option value="5Min">5分钟</option>
								<option value="10Min">10分钟</option>
								<option value="30Min">30分钟</option>
								<option value="1Hours" >1小时</option>
							</c:when>
							<c:when test="${middlewareMonitor.intervaltime==300000}">
								<option value="1Min">1分钟</option>
								<option value="5Min" selected="selected">5分钟</option>
								<option value="10Min">10分钟</option>
								<option value="30Min">30分钟</option>
								<option value="1Hours" >1小时</option>
							</c:when>
							<c:when test="${middlewareMonitor.intervaltime==600000}">
								<option value="1Min">1分钟</option>
								<option value="5Min">5分钟</option>
								<option value="10Min" selected="selected">10分钟</option>
								<option value="30Min">30分钟</option>
								<option value="1Hours" >1小时</option>
							</c:when>
							<c:when test="${middlewareMonitor.intervaltime==1800000}">
								<option value="1Min">1分钟</option>
								<option value="5Min">5分钟</option>
								<option value="10Min">10分钟</option>
								<option value="30Min" selected="selected">30分钟</option>
								<option value="1Hours" >1小时</option>
							</c:when>
							<c:when test="${middlewareMonitor.intervaltime==3600000}">
								<option value="1Min">1分钟</option>
								<option value="5Min">5分钟</option>
								<option value="10Min">10分钟</option>
								<option value="30Min">30分钟</option>
								<option value="1Hours" selected="selected">1小时</option>
							</c:when>
						</c:choose>

					</select>

				</td>
				<td></td>

				<td></td>
				<td></td>
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

