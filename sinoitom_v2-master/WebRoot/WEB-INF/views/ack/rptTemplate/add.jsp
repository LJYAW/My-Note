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
<title>报告模板---添加</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${ctx}/js/plugin/My97DatePicker/WdatePicker.js"></script>

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">

	$(function ()
		{	
			$("#td1").hide();
			$("#td2").hide();
			
			<c:if test="${result=='success'}">
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
			
			<c:if test="${action=='add'}">
				$("#td2").hide();
				$("#td1").show();
			</c:if>
			<c:if test="${action=='edit'}">
				$("#td1").hide();
				$("#td2").show();
			</c:if>
			
				//checkType();
	
			$("#tmplName").blur(function(){
				  var flag='${action}';
				  if(flag=='add'){
					var url = timeURL('${ctx}/cmdb/indicator/report/getTmplName.do');
					$.ajax({
						url : url,
						data : "date=" + new Date(),
						dataType : 'json',
						success : function(data) {
							for(var i=0;i<data.length;i++){
							   var tmplName=$("#tmplName").val();
							   if(tmplName==data[i].tmplName){
								   $("#tmplName").val("");
							      $.ligerDialog.warn("该模板名称已存在！");
							      return false;
							   }
							}
					        //document.getElementById("formSave").submit();
						}
			  	    });
				  }else{
				     //document.getElementById("formSave").submit();
				  }
				});
	});
	
<%--	function checkType(){--%>
<%--		var tmplType=$("#tmplType").find("option:selected").val();--%>
<%--		alert(tmplType);--%>
<%--	}--%>
	
	
</script>

</head>
<body scroll="no" >
		
	<form id="formSave" modelAttribute="rptTemplate" action="${ctx}/cmdb/indicator/report/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
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
				<td align="right">模板名称：</td>
				<td><input id="tmplName" name="tmplName" value="${rptTemplate.tmplName}" type="text" class="validate[required[模板名称不能为空]]  ip1" /></td>
				<td></td>
				<td align="right">模板类型：</td>
				<td id="td1">
				<select name="tmplType" id="tmplType" style="width: 202px;height:22px; margin-left: -4px;">
				    <option value="1" selected="selected">普通模板</option>
				    <option value="2">符号模板</option>
				</select>
				</td>
				<td id="td2">
					<select name="tmplType" id="tmplType" style="width: 202px;height:22px; margin-left: -4px;" onchange="checkType();">
					    <c:choose>
		   					<c:when test="${rptTemplate.tmplType==1}">
							    <option value="1">普通模板</option>
							    <option value="2">符号模板</option>
		   					</c:when>
		   					<c:when test="${rptTemplate.tmplType==2}">
							    <option value="2">符号模板</option>
						    	<option value="1">普通模板</option>
		   					</c:when>
	   					</c:choose>
					</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">标题：</td>
				<td  colspan="5">
					<input id="tabTitle" name="tabTitle" value="${rptTemplate.tabTitle }" type="text" style="width: 543px"/>
				</td>
				
			</tr>
			<tr>
				<td colspan="6"></td>
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

