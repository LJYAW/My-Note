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
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>

<style type="text/css"> 


.l-text-field {
    background: none repeat scroll 0% 0% #FFF!important;
}

</style>



<script type="text/javascript">

	var gridMain;
	var dataMain={};
	var manager;

	$(function ()
	{				
		<c:if test="${result=='success'}">
		var action = '${action}';
		var data = ${saveData};
		window.parent.saveOK(action, data);     
		window.parent.closeDlg();
		</c:if>
		
		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
		
			//$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 579 });
		$("input").filter(".ip1").ligerTextBox({ width: 200 });
		//$("#formSave").validationEngine({promptPosition: "centerRight"});	


	
		//$("#formSave").validationEngine();
		//表单验证
// 		$("#formSave").click(function(check) {    
// 	    	if($("#formSave").validationEngine()){
// 	    	}else{
// 	    		check.preventDefault();//此处阻止提交表单  
// 	    	}
// 		});
        
		reThreshTypeID();
		reLevel();
		$("#indObjCode1").val($("#indObjCode").find("option:selected").text());
		
	});
	
	
	$(document).ready(function() {
		$(".required").each(function() {
			var $this  = $(this);
			$(this).html($this.html()+"<font color='red' style='font-weight:bold'>*</font>");
		});
  });
  
 
   
   function reThreshTypeID(){
   		$.getJSON(
              "${ctx}/cmdb/thresholdType/getAllType.do",
              function (result) {
                  if (result.type != null) {
                      for (var i = 0; i < result.type.length; i++) {
                          var group = result.type[i];
                          if(group.threshTypeID=='${info.threshTypeID}'){
                            $("#indObjCode11").val(group.indObjName);
                            $("#indObjCode111").val(group.indObjCode);
                          }
                      }
                      reTypeName();
                  }
              });
             // refreshThresholdName();
   }
  
  function closeWindow() {
  	window.parent.closeDlg();
  }
  
  
  function reTypeName() {
  	var indClassCode = $("#indObjCode111").val();
       	  $.getJSON(
              "${ctx}/cmdb/thresholdType/getByObjCode.do?objCode="+indClassCode,
              function (result) {
                  if (result.code != null) {
                      $("#threshTypeID").empty();
                      for (var i = 0; i < result.code.length; i++) {
                          var group = result.code[i];
                          if(group.id==${info.threshTypeID}){
                            $("#threshTypeID11").val(group.threshTypeName);
                            $("#threshTypeID111").val(group.id);
                          }
                      }
                       reUnit();
                  }
              });
            
  }
  
  function reUnit(){
      var indClassCode=$("#threshTypeID111").val();	
      $.getJSON(
              "${ctx}/cmdb/thresholdType/getByOID.do?id="+indClassCode,
              function (result) {
                  if (result.code != null) {
                      $("#defaultUnit").val(result.code.defaultUnit);
                      $("#unitName").val(result.code.unitName);
                  }
              });
  }
  
  function reLevel(){
  	  if(${info.thresholdLevel} != -1){
      	$('#thresholdLevel option:eq(${info.thresholdLevel})').attr('selected','selected');
      	if(${jsonListData} != "editdone"){
      	dataMain.Rows = ${jsonListData};
      		manager=gridMain = $("#maingrid").ligerGrid({
	        columns: 
	            [
	            { display:'阀值层级', name:'number', width:55, align:'center'},
	            { display: '指标范围', columns:
	                [
	                	{ display: '阀值下限', name: 'lowValue', align: 'center', width: 50, editor: { type: 'string'}  },
	                    { display: '比较符', name: 'compareLow', align: 'center', width: 45 }, 
	                    { display: '指标值', name: 'indiName', width: 45, align: 'center' },
	                    { display: '比较符', name: 'compareHigh', align: 'center', width: 45 }, 
	                    { display: '阀值上限', name: 'highValue', width: 50,editor: { type: 'string'}  },
	                ]
	            },
	            { display:'发生次数', name:'count', width:55, align:'center', editor: { type: 'int',minValue:1}  },
	            { display:'告警级别', name:'severity_ID', width:58, align:'center', 
	            	editor: { type: 'int',maxValue:5,minValue:1}
	            },
	            { display:'英文级别名称', name:'severityEName', width:90, align:'right', },
	            { display:'中文级别名称', name:'severity_Name', width:75, align:'right', },
	            { display:'处理优先级', name:'priority', width:61, align:'center', editor: { type: 'string'}  },
	       		],  
		    	data: dataMain,
				alternatingRow: false,
	 			width: '72%',
	//			height: '100%', 
				pageSize: '5',
				enabledEdit: false,
		        //isScroll: false,
				usePager:false,
			});
  		}
      }
  }
  
  function showGrid(){
  		
  		dataMain.Rows=null;
  		var thresholdLevel=$("#thresholdLevel").find("option:selected").val();
  		
  		$.ajax({
             type: "POST",
             url: "${ctx}/cmdb/thresholdInfo/batchAdd.do?date="+new Date()+"&num="+thresholdLevel,
             contentType: "application/x-www-form-urlencoded; charset=utf-8",
             success: function (data) {
               if(data.jsonData){
          			dataMain.Rows = data.jsonData;
          			loadData();
      		}else{
   				return ;
         		} 
             }
         });
  		manager=gridMain = $("#maingrid").ligerGrid({
        columns: 
            [
            { display:'阀值层级', name:'number', width:55, align:'center'},
            { display: '指标范围', columns:
                [
                	{ display: '阀值下限', name: 'lowValue', align: 'center', width: 50, editor: { type: 'string'}  },
                    { display: '比较符', name: 'compareLow', align: 'center', width: 45 }, 
                    { display: '指标值', name: 'indiName', width: 45, align: 'center' },
                    { display: '比较符', name: 'compareHigh', align: 'center', width: 45 }, 
                    { display: '阀值上限', name: 'highValue', width: 50,editor: { type: 'string'}  },
                ]
            },
            { display:'发生次数', name:'count', width:55, align:'center', editor: { type: 'int',minValue:1}  },
            { display:'告警级别', name:'severity_ID', width:58, align:'center', 
            	editor: { type: 'int',maxValue:5,minValue:1}
            },
            { display:'英文级别名称', name:'severityEName', width:90, align:'right', },
            { display:'中文级别名称', name:'severity_Name', width:75, align:'right', },
            { display:'处理优先级', name:'priority', width:61, align:'center', editor: { type: 'string'}  },
       		],  
	    	data: dataMain,
			alternatingRow: false,
 			width: '72%',
//			height: '100%', 
			pageSize: '5',
			enabledEdit: true,
			onAfterEdit: f_onAfterEdit,
	        //isScroll: false,
			usePager:false,
		});
  	
  	 document.getElementById("tdgrid").setAttribute("style","display");
  	 //setTimeout(refreshThresholdName(),1000)
  	 //refreshThresholdName();  
  	 
  }
  
  $.ligerDefaults.Grid.editors['numberbox'] = {
            create: function (container, editParm) {
                var column = editParm.column;
                var precision = column.editor.precision;
                var input = $("<input type='text' style='text-align:right' class='l-text' />");
                input.bind('keypress', function (e) {
                    var keyCode = window.event ? e.keyCode : e.which;
                    return keyCode >= 48 && keyCode <= 57 || keyCode == 46 || keyCode == 8;
                });
                input.bind('blur', function () {
                    var value = input.val();
                    input.val(parseFloat(value).toFixed(precision));
                });
                container.append(input);
                return input;
            },
            getValue: function (input, editParm) {
	            if(input.val()!=null){
	              return parseFloat(input.val());
	            }else{
	              return 0.00;
	            }
            },
            setValue: function (input, value, editParm) {
                var column = editParm.column;
                var precision = column.editor.precision;
                if(isNaN(value)){
                   value=0;
                }
                input.val(value.toFixed(precision));
            },
            resize: function (input, width, height, editParm) {
                input.width(width ).height(height );
            }
        };

        //扩展 numberbox 类型的格式化函数
        $.ligerDefaults.Grid.formatters['numberbox'] = function (value, column) {
            var precision = column.editor.precision;
            if(isNaN(value)){
               value=0;
            }
            return value.toFixed(precision);
        };
  
  
  function sendform(){
	var devData = manager.getData();
	var level=$("#thresholdLevel").find("option:selected").val();	
	$("#gridNewData").val(JSON.stringify(devData));
	$("#descript").val($("#description").val());
	$("#formSave").submit();
 }
  
  function loadData(){
            selectRowData = null;
            gridMain.loadData();
        } 
        
      
        
         
         function refreshThresholdName() {
         	var str="";
         	var ty = $("#threshTypeID").find("option:selected").text();
         	var level = $("#thresholdLevel").find("option:selected").val();
         	var unit = $("#defaultUnit").val();
         	//alert("level" + level);
         	
         	if(level == -1 ){
         		str=str +ty;
         	}else{
         		str = str +ty+"-"+level+"_";
         		for(var i=0;i<level;i++){
				var ro = dataMain.Rows[i].lowValue;
				str = str + ro +unit+"~";	         	
         		}
         	}
         	$("#thresholdName").val(str);
         }
         
         
         
  
</script>

</head>
<body> 
	<form:form id="formSave" modelAttribute="threshold" action="${ctx}/cmdb/thresholdInfo/saveInfo.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="id" name="id" value="${id}">
		<input type="hidden" id="uuid" name="uuid" value="${uuid}">
		<input type="hidden" id="status" name="status" value="${info.status}">
		<input type="hidden" id="gridNewData" name="gridNewData" >
		<input type="hidden" id="thName" name="thName" >
		<input type="hidden" id="descript" name="descript" >
		<table align="center" class="tb_edit" style="width:750px;">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
            <tr>
                <td align="right">监测对象:</td>
                <td>
                <input id="indObjCode11" name="indObjCode11"  type="text" ltype="text" class="ip1" readonly/>
                <input id="indObjCode111" name="indObjCode111"  type="hidden" />
                </td>
                <td></td>
                <td align="right">阀值分类:</td>
                <td>
                <input id="threshTypeID11" name="threshTypeID11"  type="text" ltype="text" class="ip1" readonly/>
                <input id="threshTypeID111" name="threshTypeID111"  type="hidden" />
                </td>
                <td></td>
            </tr>          
            <tr>
                <td align="right">缺省单位:</td>
                <td><input id="defaultUnit" name="defaultUnit" value="${thresholdType.threshTypeName}" type="text" ltype="text" class="ip1" readonly/></td>
                <td></td>
                <td align="right">单位名称:</td>
                <td><input id="unitName" name="unitName" value="${thresholdType.threshTypeName}" type="text" ltype="text" class="ip1" readonly/></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="right">阀值级数:</td>
                <td colspan=4>
                <input value="${info.thresholdLevel}" type="text" ltype="text" class="ip1" readonly/>
                </td>
                <td></td>
            </tr>
            
		</table>
		
    </form:form>
    <div id="maingrid" style="margin:0 auto;"></div>
    <table align="center" class="tb_edit" style="width:750px;margin-top:1px">
    	<tr>
			<td class="td_w1"></td>
			<td class="td_w2"></td>
			<td class="td_w3"></td>
			<td class="td_w1"></td>
			<td class="td_w2"></td>
			<td class="td_w3"></td>
		</tr>
    	
    	<tr>
    		<td align="right">阀值名称:</td>
    		<td colspan=4><input id="thresholdName" name="thresholdName" value="${info.thresholdName}" type="text" ltype="text" class="ip2" readonly/></td>
    		<td></td>
    	</tr>
    	
    	<tr>
    		<td align="right">描述:</td>
    		<td colspan=4><input id="description" name="description" value="${info.description}" type="text" class="ip2" ltype="text"  readonly/></td>
    		<td></td>
    	</tr>
    	
	</table>
    <div align="center">
    </div>
</body>
</html>

