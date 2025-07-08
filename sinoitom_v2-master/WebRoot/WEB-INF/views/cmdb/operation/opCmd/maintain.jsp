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
<title>维护设备操作命令</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">
var cmdTypeList=${cmdTypeStr};

var manager,gridMain;
var dataMain={};
var id='${id}';

var devTypeCode='${devOperation.devTypeCode}';
	$(function ()
		{				
			<c:if test="${result=='success'}">
// 			var action = '${action}';
// 			var data = ${saveData};
// 			window.parent.parent.saveOK(action, data);
			window.parent.parent.$.ligerDialog.success("命令保存成功！"); 
			window.parent.parent.closeDlg();
			</c:if>
			$("#formSave").validationEngine({promptPosition: "centerRight"});
			<c:if test="${result=='error'}">
	        $.ligerDialog.error('${message}');
			</c:if>	
	manager=gridMain = $("#maingrid").ligerGrid({
        columns: 
            [
            { display:'命令类型', name:'cmdType', width:100, align:'center', isSort: false,
                editor: { type: 'select', data: cmdTypeList, valueField: 'resName', textField: 'resName' }, render: function (item)
                {
                var count=0;
                    for (var i = 0; i < cmdTypeList.length; i++)
                    {   
                       if (cmdTypeList[i]['resName'] == item.cmdType){
	                        for (var j = 0;j < dataMain.Rows.length; j++)
			                {
			                	if(item.id==dataMain.Rows[j].id){
			                    dataMain.Rows[j].cmdTypeCode = cmdTypeList[i]['resCode'];
// 			                    count++;
// 			                    break; 
			                    }
			                }
                        }
                        if(count>0) break;
                         
                    }
                    return item.cmdType;
                } 
            },
            { display:'命令类型Code', name:'cmdTypeCode', width:80, align:'left',hide:'0'},
            { display:'操作命令', name:'command', width:300, align:'left',isSort: false,editor: { type: 'text'}},
            { display:'期待提示符', name:'expectPrompt', width:100, align:'left',isSort: false,editor: { type: 'text'}},
            { display:'命令参数', name:'paramFlag', width:170, align:'left',isSort: false,editor: { type: 'text'}},
            { display:'命令说明', name:'description', width:150, align:'left',isSort: false,editor: { type: 'text'}},
            { display:'操作', name:'', width:106, align:'center',
              render:function(row){
                  var html='';
                  html+="<img src='${ctx}/static/ligerUI/skins/icons/add.gif' style='CURSOR: pointer;' onclick='addNewRow();'>";html += ' | ';
                  html+="<img src='${ctx}/static/ligerUI/skins/icons/delete.gif' style='CURSOR: pointer;' onclick='deleteRow();'>";html += ' | ';
                  html+="<img src='${ctx}/img/arrow_up.png' style='CURSOR: pointer;' onclick='up();'>";html += ' | ';
                  html+="<img src='${ctx}/img/arrow_down.png' style='CURSOR: pointer;' onclick='down();'>";
                  return html;
              }
            }
       		],  
    	data: dataMain,
		alternatingRow: 'true',
		width: '95%',
		pageSize: '20',
		usePager:false,
		pageSizeOptions: [10, 20, 30, 50, 100, 200, 500],
		enabledEdit: true,
        isScroll: false,checkbox:true,
       // onAfterEdit: f_onAfterEdit,
        
		onSelectRow: function (rowdata, rowindex)
              {
                  $("#txtrowindex").val(rowindex);
              }
              
    });
    getVendor();	
	//getData();	
});
 function getData()
        { 
            var vendorID=$("#vendor").find("option:selected").val();
            var oSName=$("#osID").find("option:selected").text();
            var osID=$("#osID").find("option:selected").val();
            var osVersion=encodeURI(encodeURI($("#osVersion").find("option:selected").val()));
            var modelOID=$("#modelOID").val();
        	var url = timeURL('${ctx}/cmdb/devOpCmd/getData.do?id='+id+'&vendorID='+vendorID+'&osID='+osID+'&oSName='+oSName+'&osVersion='+osVersion+'&modelOID='+modelOID);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
           			   dataMain.Rows = data;
           			   loadData();
        		}
        	});        			
        };

	function getVendor() {  //厂商
       var myname="vendor";
           $.getJSON(
               "${ctx}/cmdb/prodType/getVendor.do?date="+new Date(),
               function (result) {
                   if (result.vendors != null) {
                       $("#" + myname).empty();
                       $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                       for (var i = 0; i < result.vendors.length; i++) {
                          var vendor= result.vendors[i];
                    	  $("#" + myname).append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                       }
                   }
               });
   	}        
	function getOSName() {  //操作系统名称
       var myname="osID";
       var vendorID=$("#vendor").val();
           $.getJSON(
               "${ctx}/cmdb/os/getOSNameByVendorID.do?id="+vendorID+"&date="+new Date(),
               function (result) {
                   if (result.oSNamelist != null) {
                       $("#" + myname).empty();
                       $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                       for (var i = 0; i < result.oSNamelist.length; i++) {
                          var oSName= result.oSNamelist[i];
                    	  $("#" + myname).append("<option  value=\"" + oSName.osTypeID + "\">" + oSName.osType + "</option>");
                       }
                   }
               });
   	}
   	function getDevModel() {  //型号OID
       var myname="devModel";
       var vendorID=$("#vendor").val();
           $.getJSON(
               "${ctx}/cmdb/vendorProdModel/getModelOIDByVendorIDAndDevTypeCode.do?id="+vendorID+"&devTypeCode="+devTypeCode,
               function (result) {
                   if (result.modelOIDlist != null) {
                       $("#" + myname).empty();
                       $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                       $("#" + myname).append("<option value=\"X\" modelOId=\"X\" >所有型号</option>");
                       for (var i = 0; i < result.modelOIDlist.length; i++) {
                          var modelOIDObj= result.modelOIDlist[i];
                    	  $("#" + myname).append("<option  value=\"" + modelOIDObj.prodModelName + "\"  modelOId=\"" + modelOIDObj.prodModelOid + "\">" + modelOIDObj.prodModelName + "</option>");
                       }
                   }
                    getModelOID();
               });
   	}
   	
   	
   function	getModelOID(){
      var modelOID=$("#devModel").find("option:selected").attr("modelOId");
      $("#modelOID").val(modelOID);
   }
   	
   	
   	function getOSVersion(){
   	   var myname="osVersion";
       var vendorID=$("#vendor").find("option:selected").val();
       var oSName=$("#osID").find("option:selected").text();
           $.getJSON(
               "${ctx}/cmdb/vendorOSVersion/getOSVersion.do?vendorID="+vendorID+"&oSName="+oSName+"&date="+new Date(),
               function (result) {
                   if (result.vendorOSVersionlist != null) {
                       $("#" + myname).empty();
                       $("#" + myname).append("<option value=\"-1\"  selected>请选择</option>");
                       $("#" + myname).append("<option value=\"X.X\"  selected>所有版本</option>");
                       for (var i = 0; i < result.vendorOSVersionlist.length; i++) {
                          var vendorOSVersion= result.vendorOSVersionlist[i];
                    	  $("#" + myname).append("<option  value=\"" + vendorOSVersion.osVersion + "\">" + vendorOSVersion.osVersion + "</option>");
                       }
                   }
               });
   	}
  function loadData(){
            selectRowData = null;
            gridMain.loadData();
        } 
   function deleteRow()
        { 
            manager.deleteSelectedRow();
        }
    
   function addNewRow()
        {
            manager.addEditRow();
        }
        
   function up (rowParm)
        {
            var rowdata = manager.getSelectedRow();
            var listdata = manager._getParentChildren(rowdata);
            var index = $.inArray(rowdata, listdata);
            if (index == -1 || index == 0) return;
            var selected = manager.getSelected();
            manager.move(rowdata, listdata[index - 1], false);
            manager.select(selected);
        }     
   function down (rowParm)
        {
            var rowdata =  manager.getSelectedRow();
            var listdata = manager._getParentChildren(rowdata);
            var index = $.inArray(rowdata, listdata);
            if (index == -1 || index == listdata.length - 1) return;
            var selected = manager.getSelected();
            manager.move(rowdata, listdata[index + 1], true);
            manager.select(selected);
        }          	    		
   function sendForm(){
        	var data = manager.getData();
    		$("#jsonStr").val(JSON.stringify(data));
//     		alert(JSON.stringify(data));
    		var osVersion=$("#osVersion").find("option:selected").val();
    		var osName=$("#osID").find("option:selected").text();
    		var devModel=$("#devModel").find("option:selected").val();
    		var sel=$("#osVersion").find("option:selected").val();
    		var OID=$("#modelOID").val();
    		if(sel=='请选择'){
    		    window.parent.$.ligerDialog.warn('无操作系统版本值，不能提交！');
    		}else if(OID=='-1'){
    		    window.parent.$.ligerDialog.warn('无型号OID，不能提交！');
    		}else{
	    		$("#osVersionText").val(osVersion);
	    		$("#osName").val(osName);
	    		$("#formSave").submit();
    		}
        }		
</script>

</head>
<body>
	<form id="formSave" modelAttribute="devOpCmd" action="${ctx}/cmdb/devOpCmd/saveDevCmd.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="devOpID" value="${id}"/>
		<input type="hidden" name="osVersionText" id="osVersionText" value=""/>
		<input type="hidden" name="osName" id="osName" value=""/>
		<input type="hidden" name="jsonStr" id="jsonStr" value=""/>
		<input type="hidden" name="newDev" id="newDev" value="newDev"/>
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
				<td align="right">设备操作：</td>
				<td>
				   <input id="operation" name="operation" value="${devOperation.operation }" type="text" class="ip1" disabled="disabled"/>
				</td>
				<td></td>
				<td align="right">操作名称：</td>
				<td>
				   <input id="operateName" name="operateName" value="${devOperation.operateName }" type="text" class="ip1" disabled="disabled"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">厂商名称：</td>
				<td>
				    <select name="vendor" id="vendor" onchange="getOSName();getDevModel();" style="width:202px">
						<option value="-1">请选择</option>
				    </select>
				</td>
				<td></td>
				<td align="right">设备类型：</td>
				<td>
				   <input id="devTypeName" name="devTypeName" value="${devOperation.devTypeName }" type="text" class="ip1" disabled="disabled"/>
				</td>
				<td></td>
			</tr>
			
			<tr>
			 <td align="right">设备型号：</td>
				<td>
				    <select name="devModel" id="devModel" style="width:202px" onchange="getModelOID()">
						<option value="-1">请选择</option>
				    </select>
				</td>
				<td></td>
			 <td align="right">设备OID：</td>
				<td>
				    <input id="modelOID" name="modelOID" value="" type="text" class="ip1" readonly="readonly"/>
				</td>
				<td></td>
			</tr>
			
			<tr>
			    <td align="right">操作系统：</td>
				<td>
				    <select name="osID" id="osID" onchange="getOSVersion();" style="width:202px">
						<option value="-1">请选择</option>
				    </select>
				</td>
				<td></td>
				<td align="right">版本号：</td>
				<td>
				    <select name="osVersion" id="osVersion" style="width:202px">
						<option value="-1">请选择</option>
				    </select>
				</td>
				<td></td>
			</tr>
			<tr>
			  <td align="right" colspan=3 >
           		<input type="button" value="查询命令" onclick="getData();" class=" mg6" />
           	  </td>
           	  <td align="left" colspan=3 >
           		<input id="bnSave" type="button" onclick="sendForm()" value="保存命令" class=" mg6" />
           	  </td>
			</tr>
		</table>
		</br>
	  <div align="center" id=""><b>设备操作命令组</b></div>
      <div id="maingrid" style="margin:0 0 0 10px;"></div>
    </form>
    <br/>
</body>
</html>

