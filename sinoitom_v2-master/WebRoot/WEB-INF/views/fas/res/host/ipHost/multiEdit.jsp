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
<title>IP Host批量修改</title>
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
var osClass;
	$(function ()
	{				
		<c:if test="${result=='success'}">
		var data = ${saveData};
		window.parent.multiSaveOK(data);
		window.parent.closeDlg();
		</c:if>
		
		var orgId = '${LOGIN_USER_MAIN_ORGAN.orgId}';
        $("#mainOrg").ligerComboBox({
        	width: 200, selectBoxWidth: 200, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'orgId',
        	treeLeafOnly: false,
        	initValue: orgId,
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false },
        });
        
		$("#ipNetMaskSelect").ligerComboBox({
        	width: 200,
        	selectBoxWidth: 200, selectBoxHeight: 300, 
        	valueFieldID: 'ipNetMask',
        	treeLeafOnly: false,
        	initValue: '',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=subnetMask'), 
        		checkbox: false, nodeWidth: 120 },
	    	onSelected: function (id, text){
	    		//checkIpMask();
	        }
        });
        
        $("#osClass").ligerComboBox({
        	width: 200,
        	selectBoxWidth: 200, selectBoxHeight: 200, 
        	valueFieldID: 'osClass',
        	treeLeafOnly: false,
        	initValue: '1',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=HostOSType'), 
        		checkbox: false, nodeWidth: 120 },
	    	onSelected: function (id, text){
	    	$("#osClassCode").val(id);
	    	 	osClass=text;
	    		getOsType();
	        }
        });
        
         $("#ipHostTypeSelect").ligerComboBox({
        	width: 200,
        	selectBoxWidth: 200, selectBoxHeight: 200, 
        	valueFieldID: 'ipHostTypeId',
        	treeLeafOnly: false,
        	initValue: '1',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=IpHostType'), 
        		checkbox: false, nodeWidth: 120 },
	    	onSelected: function (id, text){
	        }
        });
        
		
// 	  	$("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip1").ligerTextBox({ width: 200 });
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});
		
		<c:if test="${result=='error'}">
        $.ligerDialog.error('${message}');
		</c:if>	
		
		$("#bnClose").click(function(){
        	window.parent.closeDlg();
        });
         getDevAccess();
		});
		
		function sendform(){
			var editoption =[];
			$('input[name="editOption"]:checked').each(function(){
       		 	editoption.push($(this).val());   	
            });
            if(editoption.length>0){
            	document.getElementById("formSave").submit();
            }else{
            	$.ligerDialog.error('请选中修改项！');
            	return;
            }
		}
		
	  function getDevAccess(){
	   var myname="devAccessId";
           $.getJSON(
               "${ctx}/devices/accessUser/getData.do",
               function (result) {
                   if (result != null) {
                       $("#" + myname).empty();
                       for (var i = 0; i < result.length; i++) {
                          var access= result[i];
                         $("#" + myname).append("<option  value=\"" + access.id + "\">" + access.acsUserName + "</option>");
                       }
                   }
               }); 
	} 
	
	
	function getOsType(){
	   var myname="osType";
	   async: false ,
           $.getJSON(
               "${ctx}/cmdb/os/getByOsClass.do?osClass="+osClass,
               function (result) {
                   if (result != null) {
                       $("#" + myname).empty();
                       for (var i = 0; i < result.osTypes.length; i++) {
                          var osType= result.osTypes[i];
                          if(osType.osType=='${ipHost.osType}'){
                            $("#" + myname).append("<option  value=\"" + osType.osType + "\" selected>" + osType.osType + "</option>");
                          }else{
                            $("#" + myname).append("<option  value=\"" + osType.osType + "\">" + osType.osType + "</option>");
                          }
                       }
                       getOsVersion();
                   }
               }); 
	} 
	
	function  getOsVersion(){
	  var osType=$("#osType").find("option:selected").val();
	  var myname="osVersion";
	  async: false ,
           $.getJSON(
               "${ctx}/cmdb/vendorOSVersion/getOsVersionByOsClassAndOsType.do?osClass="+osClass+"&osType="+osType,
               function (result) {
                   if (result != null) {
                       $("#" + myname).empty();
                       for (var i = 0; i < result.list.length; i++) {
                          var version= result.list[i];
                          if(version.osVersion=='${ipHost.osVersion}'){
                            $("#" + myname).append("<option  value=\"" + version.osVersion + "\" osFeature=\"" + version.osFeature + "\" selected>" + version.osVersion + "</option>");
                          }else{
                            $("#" + myname).append("<option  value=\"" +version.osVersion + "\" osFeature=\"" + version.osFeature + "\">" + version.osVersion + "</option>");
                          }
                       }
                       
                       $("#osFeature").val($("#osVersion").find("option:selected").attr("osFeature"));
                   }
               }); 
	} 	
</script>

</head>
<body>
	<form id="formSave" modelAttribute="ipHost" action="${ctx}/fas/res/host/ipHost/multiSave.do" method="post">
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="osClassCode" id="osClassCode" value=""/>
		<input type="hidden" id="ipHostIds" name="ipHostIds" value="${ipHostIds}"/>
		<table align="left" class="tb_edit">
			<tr>
				<td style="width:200px;text-align:right;"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				
			</tr>
			<tr>
				<td align="right"><input type="checkbox" name="editOption" value="orgOption" /></td>
				<td align="right">所属机构：</td>
				<td><input id="mainOrg" name="mainOrg" type="text" class="ip1"/></td>
			</tr>
			<tr>
				<td align="right"><input type="checkbox" name="editOption" value="hostTypeOption" /></td>
				<td align="right">设备类型：</td>
				<td><input id="ipHostTypeSelect" type="text" class="ip1" /></td>
			</tr>
			<tr>
				<td align="right"><input type="checkbox" name="editOption" value="snmpRoStrOption" /></td>
				<td align="right">SNMP只读串：</td>
				<td><input id="snmpRoString" name="snmpRoString" value="" type="text" ltype="text" class="validate[optional,maxSize[128]] ip1" /></td>
			</tr>
			<tr>
				<td align="right"><input type="checkbox" name="editOption" value="ipMaskOption" /></td>
				<td align="right">IP地址掩码：</td>
				<td><input id="ipNetMaskSelect" name="ipNetMaskSelect" type="text" class="ip1" /></td>
			</tr>
			<tr>
				<td align="right"><input type="checkbox" name="editOption" value="osClassOption" /></td>
				<td align="right">操作系统：</td>
				<td><input id="osClass" name="osClass" type="text" class="ip1" /></td>
			</tr>
			
			<tr>
				<td align="right"><input type="checkbox" name="editOption" value="osTypeOption" /></td>
				<td align="right">操作系统类型：</td>
				<td>
					<select id="osType" name="osType"  style="width:200px;" onchange="getOsVersion()"></select>
				</td>
			</tr>
			
			
			<tr>
				<td align="right"><input type="checkbox" name="editOption" value="osVersionOption" /></td>
				<td align="right">操作系统版本：</td>
				<td>
					<select id="osVersion" name="osVersion"  style="width:200px;" onchange=""></select>
				</td>
			</tr>
			
			<tr>
				<td align="right"><input type="checkbox" name="editOption" value="osFeatureOption" /></td>
				<td align="right">操作系统特征：</td>
				<td><input id="osFeature" name="osFeature" type="text" class="ip1" /></td>
			</tr>
			
			<tr>
				<td align="right"><input type="checkbox" name="editOption" value="accessnOption" /></td>
				<td align="right">访问方式：</td>
				<td>
				<select id="devAccessId" name="devAccessId" style="width:200px" >
                  	</select>
				</td>
			</tr>
			
			
			
			<tr>
				<td></td>
           		<td align="right" >
           		<input id="bnSave" type="button" value="保 存" onclick="sendform()" class="l-button mg6" />
           		</td>
           		<td align="left">
           		<input id="bnClose" type="button" value="取 消" class="l-button mg6" />
           		</td>
            </tr>
		</table>
    </form>
    <br/>
</body>
</html>

