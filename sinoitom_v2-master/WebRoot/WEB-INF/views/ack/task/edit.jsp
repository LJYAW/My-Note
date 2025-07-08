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
<title>巡检任务---修改</title>
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
		<c:if test="${result=='success'}">
		window.parent.saveOK();
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
	
			getCheckTimes();
			refreshTmpl();
	});
	
	function getCheckTimes(){
		var taskType=$("#taskType").find("option:selected").val();
		if(taskType==0){
			$(".times").hide();
			$("#checkTimes").val("0");
		}else if(taskType==1){
			$(".times").show();
			$("#checkTimes").val("1");
		}else if(taskType==2){
			$(".times").show();
			$("#checkTimes").val("${ackTask.checkTimes} ");
			
		}

		
	}
	
	//模板
	function refreshTmpl() {
	 $.getJSON(
	     "${ctx}/cmdb/indicator/report/getTmpl.do?",
	     function (result) {
	         if (result.rptTemplate != null) {
	             $("#tmplId").empty();
	             for (var i = 0; i < result.rptTemplate.length; i++) {
	                 var tmpl = result.rptTemplate[i];
	                 if(tmpl.tmplId=='${ackTask.tmplId}'){
	                  $("#tmplId").append("<option  value=\"" + tmpl.tmplId + "\" selected>" + tmpl.tmplName + "</option>");
	                 }else{
	                  $("#tmplId").append("<option  value=\"" + tmpl.tmplId + "\">" + tmpl.tmplName + "</option>");
	                 }
	                 
	             }
	            setTmplName();
	         }
	     });
	}
	
	//刷新分类名称
	function setTmplName(){
	var tmplName=$("#tmplId").find("option:selected").text();
	$("#tmplName").val(tmplName);
	}
	
	
</script>

</head>
<body>
	<form id="formSave" modelAttribute="ackTask" action="${ctx}/ack/task/editSave.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" name="id" value="${id}"/>
		<input type="hidden" id="tmplName" name="tmplName"  value=""/>
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
				<td align="right">任务名称：</td>
				<td><input id="taskName" name="taskName" value="${ackTask.taskName}" type="text" class="ip1"/></td>
				<td></td>
				<td align="right">巡检周期：</td>
				<td>
				<input id="checkCycle" name=checkCycle value="${ackTask.checkCycle }" type="text" style="width: 100px;" />
				<select name="cycleUnit" id="cycleUnit" style="width: 90px;height:20px; margin-left: -4px;">
				
				<c:choose>
   				<c:when test="${ackTask.cycleUnit=='minute'}">
      				<option value="minute"selected="selected">分</option>
				    <option value="hour">小时</option>
				    <option value="day" >天</option>
				    <option value="week">周</option>
				    <option value="month">月</option>
   				</c:when>
   				<c:when test="${ackTask.cycleUnit=='hour'}">
      				<option value="minute">分</option>
				    <option value="hour"selected="selected">小时</option>
				    <option value="day">天</option>
				    <option value="week">周</option>
				    <option value="month">月</option>
   				</c:when>
   				<c:when test="${ackTask.cycleUnit=='day'}">
      				<option value="minute">分</option>
				    <option value="hour">小时</option>
				    <option value="day" selected="selected">天</option>
				    <option value="week">周</option>
				    <option value="month">月</option>
   				</c:when>
   				<c:when test="${ackTask.cycleUnit=='week'}">
      				<option value="minute">分</option>
				    <option value="hour">小时</option>
				    <option value="day" >天</option>
				    <option value="week"selected="selected">周</option>
				    <option value="month">月</option>
   				</c:when>
   				<c:when test="${ackTask.cycleUnit=='month'}">
      				<option value="minute">分</option>
				    <option value="hour">小时</option>
				    <option value="day">天</option>
				    <option value="week">周</option>
				    <option value="month"selected="selected">月</option>
   				</c:when>
   
  
				</c:choose>
				
				    
				</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">任务描述：</td>
				<td  colspan="5">
					<input id="taskDescr" name="taskDescr" value="${ackTask.taskDescr }" type="text" style="width: 543px"/>
				</td>
				
			</tr>
			<tr>
				<td align="right">任务类型：</td>
				<td><select name="taskType" id="taskType" style="width: 200px" onchange="getCheckTimes();">
				   <c:choose>
   					<c:when test="${ackTask.taskType==0}">
				    <option value="0"selected="selected">循环任务</option>
				    <option value="1">单次任务</option>
				    <option value="2">多次任务</option>
   					</c:when>
   					<c:when test="${ackTask.taskType==1}">
				    <option value="0">循环任务</option>
				    <option value="1"selected="selected">单次任务</option>
				    <option value="2">多次任务</option>
   					</c:when>
   					<c:when test="${ackTask.taskType==2}">
				    <option value="0">循环任务</option>
				    <option value="1">单次任务</option>
				    <option value="2"selected="selected">多次任务</option>
   					</c:when>
   					</c:choose>
				   
				    
				</select>
				</td>
				<td></td>
				<td align="right"class="times">巡检次数：</td>
				<td><input id="checkTimes" name="checkTimes" value="${ackTask.checkTimes}" type="text" class="ip1 times"/>
				</td>
				<td></td>
			</tr>
			<tr>
			   <td align="right" nowrap>首次巡检时间：</td>
			   <td><input id="firstChkTime" name="firstChkTime" value="${ackTask.firstChkTime }" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate ip1"/>
				</td>
				<td></td>
				<td align="right">模板：</td>
				 <td>
                 <select id="tmplId" name="tmplId" style="width:202px" onchange="setTmplName()" >
                    <option ></option>
                 </select>
                </td>
				<td></td>
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

