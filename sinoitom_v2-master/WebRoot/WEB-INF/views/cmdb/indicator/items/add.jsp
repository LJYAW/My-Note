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
<title>编辑指标项</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>
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

<script type="text/javascript">
var manager,gridMain;
var dataMain={};

	$(function ()
	{		
		checkValueType();
		
		<c:if test="${result=='success'}">
		var action = '${action}';
		var data = ${saveData};
		window.parent.parent.saveOK(action, data);     
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
	});
	$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
		refreshIndGroup();
  });
  
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
                            $("#indGroupID").append("<option  value=\"" + group.id + "\">" + group.groupName + "</option>");
                          }
                          
                      }
                     getIndGroupName();
                  }
              });
    }
  
  function getIndGroupName(){
  	 var indGroupName=$("#indGroupID").find("option:selected").text();
  	 $('#indGroupName').val(indGroupName);
  }
  
  function checkValueType(){
	  var valueType=$("#valueType").find("option:selected").text();
		if(valueType!="二维数组"){
			$(".show_td").css("display","none");
			$(".show_td2").css("display","");
		}else{
			$(".show_td2").css("display","none");
			$(".show_td").css("display","");
		}
  }
  
  function showTable(){
	  var fieldQty = $("#fieldQty").val();
		
		var reg=/^[1-9]\d*$/;
		
	    var re = new RegExp(reg);
	    if (re.test(fieldQty)) {
	    	$("#main").attr("style","visibility:visible;");//显示div
	    	getData(fieldQty);
	    }else{
	    	layer.msg('字段个数只能为非0正整数！', {icon: 2, time: 1500});
	    	return;
	    }
  }
  
  function getData(fieldQty)
  { 
  	var url = timeURL('${ctx}/cmdb/indicatorItems/getData.do?fieldQty='+fieldQty);
  	$.ajax({
  		url: url,
  		dataType: 'json',
  		success: function(data) {
     			   dataMain.Rows = data;
     			   loadData();
  		}
  	});  
  	pageInit();
  }
  
  function loadData(){
      selectRowData = null;
      gridMain.loadData();
  } 
  
  function pageInit(){
		
		
		var valueTypeData = [{ val: 'String', text: 'String' }, { val: 'Integer', text: 'Integer'}, { val: 'Long', text: 'Long'}];
		var rowData = { Rows: []};
		
		manager=gridMain = $("#maingrid").ligerGrid({
	        columns: 
	            [
				{ display:'字段英文名', name:'fieldNameEn', width:150, align:'left',editor: { type: 'text' }},
				{ display:'字段名', name:'fieldName', width:150, align:'left',editor: { type: 'text' }},
				{ display:'字段类型', name:'valueType', width:100, align:'left', isSort: false,
	                editor: { type: 'select', data: valueTypeData, valueColumnName: 'val' }, 
	                render: function (item)
                  {
                      for (var i = 0; i < valueTypeData.length; i++)
                      {
                          if (valueTypeData[i]['val'] == item.valueType)
                              return valueTypeData[i]['text']
                      }
                  }
	            },
				{ display:'字段长度', name:'length', width:100, align:'right',editor: { type: 'text' }},
				{ display:'小数位数', name:'decimals', width:100, align:'right',editor: { type: 'text' }},
				{ display:'度量单位', name:'measureUnit', width:100, align:'right',editor: { type: 'text' }}
	       		],  
	    	data: dataMain,
			alternatingRow: 'true',
			width: '90%',
			pageSize: '20',
			usePager:false,
			pageSizeOptions: [10, 20, 30, 50, 100, 200, 500],
			enabledEdit: true,
	        isScroll: false,
	        //checkbox:true,
	        enabledSort:false,
	        
			onSelectRow: function (rowdata, rowindex)
	              {
				
	                  $("#txtrowindex").val(rowindex);
	              },
	              
<%--	        onBeforeEdit: onBeforeEdit, --%>
<%--	        onBeforeSubmitEdit: onBeforeSubmitEdit,--%>
	        onAfterEdit: onAfterEdit,
	        clickToEdit: true
	              
	    });
	}
  
    var regflag1 = true ;
	var regflag2 = true ;
	var regflag3 = true ;
	var regflag4 = true ;
	function onAfterEdit(e){
		 if (e.column.name == "length")
       {
			regflag1 = false;
			var d = e.record;
			var cellObj = manager.getCellObj(d, e.column);
			//var name = d[e.column.name];
			var lengthText = $(cellObj).text();
			
			var reg=/^[1-9]\d*$/;
			
		    var re = new RegExp(reg);
		    if (!re.test(lengthText)) {
		    	regflag1 = false ;
		    	layer.msg('字段长度只能为非0正整数字！', {icon: 2, time: 1500});
		    	 return ;
		    }
		    else {
		    	if(parseInt(lengthText)<=0){
		    		layer.msg('字段长度只能为非0正整数!', {icon: 2, time: 1500});
		    		regflag1 = false ;
		    		 return ;
		    	}else{
		    		regflag1 = true ;
		    	}
		    }
			
       } else if (e.column.name == "decimals") {
      	regflag2 = false ;
			var d = e.record;
			var cellObj = manager.getCellObj(d, e.column);
			//var name = d[e.column.name];
			var decimalsText = $(cellObj).text();
			
			var reg=/^(0|[1-9][0-9]*)$/;
			
		    var re = new RegExp(reg);
		    if (!re.test(decimalsText)) {
		    	regflag2 = false ;
		    	layer.msg('小数位数只能为大于等于0的整数！', {icon: 2, time: 1500});
		    	return ;
		    }
		    else {
		    	regflag2 = true ;
		    }
			
       } else if (e.column.name == "fieldNameEn") {
      	regflag3 = false ;
			var d = e.record;
			var cellObj = manager.getCellObj(d, e.column);
			//var name = d[e.column.name];
			var fieldNameEnText = $(cellObj).text();
			if(fieldNameEnText!=""&&fieldNameEnText!=null){
				regflag3 = true ;
			}else{
				layer.msg('字段英文名不能为空！', {icon: 2, time: 1500});
		    	return ;
			}
			
       } else if (e.column.name == "fieldName") {
         	regflag4 = false ;
			var d = e.record;
			var cellObj = manager.getCellObj(d, e.column);
			//var name = d[e.column.name];
			var fieldNameText = $(cellObj).text();
			if(fieldNameText!=""&&fieldNameText!=null){
				regflag4 = true ;
			}else{
				layer.msg('字段名不能为空！', {icon: 2, time: 1500});
		    	return ;
			}
			
      }
		 
	}
	
	function submitForm(){
		var indicatorItem = $("#indicatorItem").val();
		var fieldQty = $("#fieldQty").val();
		if(isBlank(indicatorItem)){
			layer.msg('指标英文名称不能为空！', {icon: 2, time: 1500});
		}else{
			if(isBlank(fieldQty)){
				layer.msg('字段数不能为空！', {icon: 2, time: 1500});
			}else{
				var reg=/^[1-9]\d*$/;
				
			    var re = new RegExp(reg);
			    if (re.test(fieldQty)) {
			    	if(regflag1&&regflag2&&regflag3&&regflag4){
						var data = manager.getData();
			    		$("#rowData").val(JSON.stringify(data));
			    		$("#formSave").submit();
			    		
					}else{
						layer.msg('请检查列表项输入是否合法！', {icon: 2, time: 1500});
					}
			    }else{
			    	layer.msg('字段个数只能为非0正整数！', {icon: 2, time: 1500});
			    	return;
			    }
			}
		}
		
	}
	
	function isBlank(str) {
		return (!str || /^\s*$/.test(str));
	}
  
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="IndicatorItems" action="${ctx}/cmdb/indicatorItems/addSave.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="indGroupName" name="indGroupName" value="">
		<input type="hidden" name="rowData" id="rowData" />
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
                <td align="right" nowrap>监测对象分类:</td>
                <td><s:resSelect id="indClassCode" name="indClassCode" value="${items.indClassCode}" code="Cmdb_IndicatorClass"  ltype="select" onchange="refreshIndGroup()" style="width:202px"/></td>
                <td></td>
                
                <td align="right">指标组名称:</td>
                <td>
                	<select name="indGroupID" id="indGroupID" style="width:202px" onchange="getIndGroupName()"> 
					</select> 
				</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">指标英文名称:</td>
                <td><input id="indicatorItem" name="indicatorItem" value="${items.indicatorItem}" type="text" ltype="text" class="validate[required[指标项名称不能为空],maxSize[512]] ip1" /></td>
                <td class="required"></td>
                
                <td align="right">指标显示名称:</td>
                <td><input id="indItemName" name="indItemName" value="${items.indItemName}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">取值类型:</td>
                 <td><s:resSelect id="valueType" name="valueType" value="${items.valueType}" code="Cmdb_IndValueType"  ltype="select" onchange="checkValueType();"  style="width:202px"/></td>
                <td></td>  
                   
                <td class="show_td" align="right">字段个数:</td>
                <td class="show_td" ><input id="fieldQty" name="fieldQty" value="${items.fieldQty}" type="text" ltype="text" onblur="showTable();" class="validate[required[字段数不能为空],custom[onlyPosInt]] ip1" /></td>
                <td class="required show_td"></td>
                 
                <td class="show_td2" align="right">度量单位:</td>
                <td class="show_td2"><input id="measureUnit" name="measureUnit" value="${items.measureUnit}" type="text" ltype="text" class="ip1" /></td>
                <td class="show_td2"></td>                  
            </tr>
            <tr class="show_td2">
                <td align="right">取值长度:</td>
                <td><input id="length" name="length" value="${items.length}" type="text" ltype="text" class="validate[required[取值长度不能为空]] ip1"/></td>
                <td class="required"></td>
                
                <td align="right">小数位数:</td>
                <td><input id="decimals" name="decimals" value="${items.decimals}" type="text" ltype="text" class="validate[required[小数位数不能为空]] ip1"/></td>
                <td class="required"></td>
            </tr>
            <tr>
                <td align="right">指标说明:</td>
                <td colspan=4><input id="remark" name="remark" value="${items.remark}" type="text" class="validate[optional,maxSize[128]] ip2" ltype="text"  /></td>
                <td></td>
            </tr>
            
            <tr class="show_td2">
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
            <tr style="height: 10px;"></tr>
		</table>
		<div class="show_td" id="main">
			<div id="maingrid" align="center" style="width: 700px;margin:0 0 0 50px;">
				
			</div>
		</div>
		<div style="height: 15px;"></div>
		<div class="show_td" align="center" style="height: 35px;">
			<button style="width: 60px;height: 30px;border:1px solid #000000;background-color: #F0FFFF; " id="but0" type="button" onclick="submitForm();" >
	                                    保 存
	        </button>
	    </div>
    </form:form>
</body>
</html>

