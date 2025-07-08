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
<title>用户编辑</title>
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
		var action = '${action}';
		var data = ${saveData};
		//window.parent.parent.saveOK(action, data);     
		window.parent.parent.saveOK();     
		window.parent.closeDlg();
		</c:if>
		
		$("#mainOrg").ligerComboBox({
        	width: 196,
        	selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'mainOrgId',
        	treeLeafOnly: false,
        	initValue: '${user.mainOrgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:user.mainOrgId}',
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false }
        });

		//$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("input").filter(".ip1").ligerTextBox({ width: 200 });
		//$("#formSave").validationEngine({promptPosition: "centerRight"});	
		
		
		$("#chkEditPasswd").change(function()
	    {
			if( this.checked ) {
				$("#isEditPasswd").attr("value","1");
				$("#trEditPasswd").css({display:''});
			}else{
				$("#isEditPasswd").attr("value","0");
				$("#trEditPasswd").css({display:'none'});
			}
        });

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
		
		refreshIndItems();
		getTypeResItems();
		
		refreshIndClassCode();
		refreshIndGroup();
		
	});
	$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
// 		refreshIndGroup();
  });
  
  //刷新指标组
//     function refreshIndGroup(){
//      var indClassCode=$("#indClassCode").find("option:selected").val();
//        	  $.getJSON(
//               "${ctx}/cmdb/prodIndItem/getIndGroup.do?indClassCode="+indClassCode,
//               function (result) {
//                   if (result.indGroups != null) {
//                       $("#indGroupID").empty();
//                       for (var i = 0; i < result.indGroups.length; i++) {
//                           var group = result.indGroups[i];
//                           if(group.id=='${items.indGroupID}'){
//                             $("#indGroupID").append("<option  value=\"" + group.id + "\" selected>" + group.groupName + "</option>");
//                           }else{
//                           	if(group.id=='${indGroupID}'){
//                           		$("#indGroupID").append("<option  value=\"" + group.id + "\"selected>" + group.groupName + "</option>");
//                           	}else{
//                           		$("#indGroupID").append("<option  value=\"" + group.id + "\">" + group.groupName + "</option>");
//                           	}
                            
//                           }
                          
//                       }
//                      getIndGroupName();
//                   }
//               });
//     }
  
  function getIndGroupName(){
  	 var indGroupName=$("#indGroupID").find("option:selected").text();
  	 $('#indGroupName').val(indGroupName);
  }
  
  
    function getTypeResItems(){
  	
  	var classCode=$("#devClassCode").find("option:selected").val();
  	var classCodeName=$("#devClassCode").find("option:selected").text();
  	 $("#devClassName").val(classCodeName);
  	 
  	if(classCode=="11"){
  	   resName="IT_HostType";
  	}else if(classCode=="12"){
  	  resName="IT_StorageType";
  	}else if(classCode=="13"){
  	  resName="IT_NetDevType";
  	}else if(classCode=="14"){
  	  resName="IT_PeripheralType";
  	}else if(classCode=="21"){
  	  resName="IT_OSType";
  	}else if(classCode=="22"){
  	  resName="IT_SystemType";
  	}else if(classCode=="23"){
  	  resName="IT_DBType";
  	}else if(classCode=="24"){
  	  resName="IT_MiddleWareType";
  	}else if(classCode=="25"){
  	  resName="IT_AppType";
  	}
  	
  	
  	 $.getJSON(
               "${ctx}/cmdb/prodType/getTypeDataBygroupCode.do?groupCode="+resName,
               function (result) {
                   if (result.types != null) {
                       $("#devTypeCode").empty();
                       for (var i = 0; i < result.types.length; i++) {
                       		var type=result.types[i];
	                       		if(type.id=='${items.devTypeCode}'){
	                            $("#devTypeCode").append("<option  value=\"" + type.id + "\" selected>" + type.text + "</option>");
	                          }else{
// 	                          		if(type.id=='${devTypeCode}'){
// 	                          			$("#devTypeCode").append("<option  value=\"" + type.id+"\" selected>" + type.text + "</option>");
// 	                          		}else{
	                          		$("#devTypeCode").append("<option  value=\"" + type.id+"\">" + type.text + "</option>");
// 	                          		}
	                           
	                          }
                              
                       }
                       getTypeName();
                   }
                   
               });
  	
   }
  	
 function getTypeName(){
  var typeName=$("#devTypeCode").find("option:selected").text();
  $("#devTypeName").val(typeName);
 }
  
    //刷新指标组
    function refreshIndGroup(){
     var indClassCode=$("#indClassCode").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/prodIndItem/getIndGroup.do?indClassCode="+indClassCode,
              function (result) {
                  if (result.indGroups != null) {
                      $("#indGroupID").empty();
                      for (var i = 0; i < result.indGroups.length; i++) {
                          var group = result.indGroups[i];
                          if(group.id=='${items.indGroupID}'){
                            $("#indGroupID").append("<option  value=\"" + group.id + "\" selected>" + group.groupName + "</option>");
                          }else{
//                             if(group.id=='${indGroupID}'){
//                           		$("#indGroupID").append("<option  value=\"" + group.id + "\"selected>" + group.groupName + "</option>");
//                           	}else{
                          		$("#indGroupID").append("<option  value=\"" + group.id + "\">" + group.groupName + "</option>");
//                           	}
                          }
                          
                      }
                     refreshIndItems();
                
                     refreshIndGroupName();
                  }
              });
    }
    
    	//刷新指标组名称
  	function refreshIndGroupName(){
  	  var groupName=$("#indGroupID").find("option:selected").text();
  	 $("#indGroupName").val(groupName);
  	}
  	//刷新指标项
  	
  	function refreshIndItems(){
     var indClassCode=$("#indClassCode").find("option:selected").val();
     var indGroupID=$("#indGroupID").find("option:selected").val();
       	  $.getJSON(
              "${ctx}/cmdb/prodIndItem/getIndItems.do?indClassCode="+indClassCode+"&indGroupID="+indGroupID,
              function (result) {
                  if (result.indItems != null) {
                      $("#indItemID").empty();
                      for (var i = 0; i < result.indItems.length; i++) {
                          var item = result.indItems[i];
                          if(item.id=='${items.indItemID}'){
                            $("#indItemID").append("<option  value=\"" + item.id + "\"  indItemName=\"" + item.indItemName + "\" remark=\"" + item.remark + "\" valueType=\"" + item.valueType + "\" measureUnit=\"" + item.measureUnit + "\" length=\"" + item.length + "\" decimals=\"" + item.decimals + "\"  selected>" + item.indicatorItem + "</option>");
                            
                          }else{
                            $("#indItemID").append("<option  value=\"" + item.id + "\"  indItemName=\"" + item.indItemName + "\" remark=\"" + item.remark + "\" valueType=\"" + item.valueType + "\" measureUnit=\"" + item.measureUnit + "\" length=\"" + item.length + "\" decimals=\"" + item.decimals + "\" >" + item.indicatorItem + "</option>");
                          }
                          
                      }
                       
                     
                      refreshIndItem();
                      if('${action}'=="add"){
                       refreshIndItemProt();
                      }
                     
                  }
              });
    }
    
    function refreshIndItem(){
      $("#indItemName").val($("#indItemID").find("option:selected").attr("indItemName"));
      $("#indicatorItem").val($("#indItemID").find("option:selected").text());
    }
    
    
    //刷新指标项属性值
    function refreshIndItemProt(){
    				 
				    $("#valueType").val($("#indItemID").find("option:selected").attr("valueType"));
				    $("#measureUnit").val($("#indItemID").find("option:selected").attr("measureUnit"));
				    $("#length").val($("#indItemID").find("option:selected").attr("length"));
				    $("#decimals").val($("#indItemID").find("option:selected").attr("decimals"));
				     $("#remark").val($("#indItemID").find("option:selected").attr("remark"));
    } 
    
  //刷新检测对象分类
  	function refreshIndClassCode(){
       	  $.getJSON(
              "${ctx}/cmdb/indicatorItems/getIndClassCode.do?",
              function (result) {
                  if (result.indicator != null) {
                      $("#indClassCode").empty();
                      for (var i = 0; i < result.indicator.length; i++) {
                          var item = result.indicator[i];
                          if(item.indClassCode=='${items.indClassCode}'){
                            $("#indClassCode").append("<option  value=\"" + item.indClassCode +"\"  selected>" + item.indClassName + "</option>");
                            
                          }else{
                            $("#indClassCode").append("<option  value=\"" + item.indClassCode + "\" >" + item.indClassName + "</option>");
                          }
                          
                      }
                       
                     
                      refreshIndGroup();
                     
                  }
              });
    }
  
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="devIndItems" action="${ctx}/cmdb/indicator/device/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="id" name="id" value="${id}">
		<input type="hidden" id="indGroupName" name="indGroupName" value="">
		<input type="hidden" id="devClassName" name="devClassName"  value=""/>
	    <input type="hidden" id="devTypeName" name="devTypeName"  value=""/>
	    <input type="hidden" id="indClassName" name="indClassName" value=""/>
	   <input type="hidden" id="indicatorItem" name="indicatorItem" value=""/>
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
               <td align="right">设备分类 ：</td>
               
                 <td>
                 <s:resSelect id="devClassCode" name="devClassCode" code="IT_ResClass" value="${items.devClassCode}" ltype="select" style="width:202px" onchange="getTypeResItems();"/>
                </td>
                <td></td>
                 <td align="right">设备类型：</td>
                <td>
                 <select name="devTypeCode" id="devTypeCode" style="width:202px" onchange="getTypeName();">
						<option value="-1">请选择</option>
				</select>
                </td>
               <td></td>
            </tr>
			
             <tr>
                <td align="right" nowrap="nowrap">监测对象分类：</td>
                <td>
                <select id="indClassCode" name="indClassCode" style="width:200px;"onchange="refreshIndGroup();">
                </select>
                </td>
                <td></td>
                
                <td align="right">指标组：</td>
                <td>
                <select id="indGroupID" name="indGroupID" style="width:200px;" onchange="refreshIndItems();refreshIndGroupName();">
                </select>
                </td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">指标项：</td>
                <td>
                <select id="indItemID" name="indItemID" style="width:200px;" onchange="refreshIndItemProt();refreshIndItem();">
                </select>
                </td>
                <td></td>
                 <td align="right">指标名称：</td>
                <td>
                <input id="indItemName" name="indItemName" value="${items.indItemName}" type="text" ltype="text" class="ip1" readonly="readonly"/>
                </td>
                <td></td>
            </tr>
            
             <tr>
                <td align="right">指标说明：</td>
                <td colspan="4"><input id="remark" name="remark" value="${items.remark}" type="text" ltype="text" class="ip2" /></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">取值类型：</td>
                <td><input id="valueType" name="valueType" value="${items.valueType}" type="text" ltype="text" class=" ip1"/></td>
                <td ></td>
                
                <td align="right">度量单位：</td>
                <td><input id="measureUnit" name="measureUnit" value="${items.measureUnit}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">取值长度：</td>
                <td><input id="length" name="length" value="${items.length}" type="text" ltype="text" class="ip1"/></td>
                <td ></td>
                
                <td align="right">小数位数：</td>
                <td><input id="decimals" name="decimals" value="${items.decimals}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form:form>
</body>
</html>

