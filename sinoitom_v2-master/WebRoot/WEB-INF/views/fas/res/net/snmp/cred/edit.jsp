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
<title>Snmp信息编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">

var count = 0;
<c:if test="${action=='add'}">
count++;
</c:if>
var orgName = '';
var varName = '';

	$(function ()
	{		
		<c:if test="${result=='success'}">
		window.parent.saveOK();  
		</c:if>
		<c:if test="${result=='success' && closeDlg=='true'}">
		window.parent.closeDlg();  
		</c:if>

		varName = $("#snmpVersion").find("option:selected").text();

		var orgId = '${snmpCred.orgId==null ? LOGIN_USER_MAIN_ORGAN.orgId:snmpCred.orgId}';
        $("#mainOrg").ligerComboBox({
        	width: 196, selectBoxWidth: 196, selectBoxHeight: 200, 
        	textField: 'orgName',
        	valueFieldID: 'orgId',
        	treeLeafOnly: false,
        	initValue: orgId,
        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgName', checkbox: false },
		    onSelected: function (id, name)
		    {
		    	orgName = name;     
		    	if( id != "" && id != null ){
		    		if( count>=2 ){
		    			autoSetName();
		    		}
	    	    	else{
	    	    		count++;
	    	    	}
		    	}
		    }
        });
        
		$("#snmpVersion").change(function(){
			if(this.value==3){
				$(".devSnmp12").addClass("dN");
				$(".devSnmp3").removeClass("dN");
			}
			else{
				$(".devSnmp3").addClass("dN");
				$(".devSnmp12").removeClass("dN");
	        }
		});
		
        $("#formSave").ligerForm({inputWidth:200});
		$("input").filter(".ip2").ligerTextBox({ width: 553 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});
		
		$("#snmpVersion").ligerGetComboBoxManager().bind('selected', function (id, name){
			varName = name;
			autoSetName();
		});
		
        $("#snmpRoStr").change(function(){
        	autoSetName();
        });
		        
        $("#credName").change(function(){
        	autoSetName();
        });
		        
        $("#credUserName").change(function(){
        	autoSetName();
        });
		        
        $("#bnSave").click(function(){
        	$("#closeDlg").val("true");
        });
        
        $("#bnSaveAdd").click(function(){
        	$("#closeDlg").val("false");
        });

        $("#bnClose").click(function(){
        	window.parent.closeDlg();
        });

		<c:if test="${result=='error'}">
        $.ligerDialog.error('${message}');
		</c:if>	
		<c:if test="${result=='success' && closeDlg=='false'}">
        $.ligerDialog.success('保存成功！'); 
		</c:if>
		<c:if test="${result=='success' && action=='add'}">
		$("input[ltype='text']").val("");
		</c:if>
	});
	
    function autoSetName(){
    	var name = '';
    	if( varName == "V3" ){
            name = orgName+"-"+varName+"-"+$("#credName").val()+"-"+$("#credUserName").val();
    	}
    	else{
            name = orgName+"-"+varName+"-"+$("#snmpRoStr").val()+"-RO";
    	}
        $("#snmpCredName").val(name);    		
    }
	
</script>

</head>
<body> 
	<form id="formSave" action="${ctx}/fas/res/net/snmp/cred/save.do" method="post">
		<input type="hidden" name="action" value="${action}"/>
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
		<input type="hidden" name="id" value="${snmpCred.snmpCredId}"/>
		<input type="hidden" id="orgId" name="orgId" value="${snmpCred.orgId}"/>
		<input type="hidden" id="oprType" name="oprType" value="${oprType}"/>
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
                <td align="right">所属机构:</td>
                <td><input id="mainOrg" type="text" class="ip1"/></td>
                <td></td>
                <td align="right">下级是否可见:</td>
                <td><s:resSelect name="lowerVisable" value="${snmpCred.lowerVisable}" code="lowerVisable" ltype="select" /></td>
                <td></td>
            </tr>   
            <tr class="devSnmp123">
                <td align="right">Snmp版本:</td>
                <td><s:resSelect id="snmpVersion" name="snmpVersion" value="${snmpCred.snmpVersion}" code="snmpCred_snmpVersion" ltype="select" /></td>
                <td></td>
                <td align="right">Snmp端口:</td>
                <td><input id="snmpPort" name="snmpPort" value="${snmpCred.snmpPort}" ltype='spinner' ligerui="{type:'int'}" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp12 dN">
                <td align="right">SNMP只读串:</td>
                <td><input id="snmpRoStr" name="snmpRoStr" value="${snmpCred.snmpRoStr}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
                <td align="right">SNMP读写串:</td>
                <td><input id="snmpRwStr" name="snmpRwStr" value="${snmpCred.snmpRwStr}" type="text" ltype="text" class="validate[optional,maxSize[32] ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">凭证名称:</td>
                <td><input id="credName" name="credName" value="${snmpCred.credName}" type="text" ltype="text" class="validate[optional,maxSize[64] ip1" /></td>
                <td></td>
                <td align="right">凭证描述:</td>
                <td><input id="credDesc" name="credDesc" value="${snmpCred.credDesc}" type="text" class="validate[optional,maxSize[128] ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">用户名:</td>
                <td><input id="credUserName" name="credUserName" value="${snmpCred.credUserName}" type="text" ltype="text" class="validate[optional,maxSize[16] ip1" /></td>
                <td></td>
                <td align="right">上下文名称:</td>
                <td><input id="credContextName" name="credContextName" value="${snmpCred.credContextName}" type="text" ltype="text" class="validate[optional,maxSize[64] ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">认证协议:</td>
                <td><s:resSelect name="credAuthProt" value="${snmpCred.credAuthProt}" code="CredAuthProtocol" ltype="select" /></td>
                <td></td>
                <td align="right">认证密码:</td>
                <td><input id="credAuthPassword" name="credAuthPassword" value="${snmpCred.credAuthPassword}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            <tr class="devSnmp3 dN">
                <td align="right">认证加密协议:</td>
                <td><s:resSelect name="credEncryptProt" value="${snmpCred.credEncryptProt}" code="CredEncryptProtocol" ltype="select" /></td>
                <td></td>
                <td align="right">认证加密密码:</td>
                <td><input id="credEncryptPassword" name="credEncryptPassword" value="${snmpCred.credEncryptPassword}" type="text" ltype="text" class="ip1" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">SNMP信息命名:</td>
                <td><input id="snmpCredName" name="snmpCredName" value="${snmpCred.snmpCredName}" type="text" ltype="text" class="validate[required,maxSize[64]] ip1"/></td>
                <td></td>
                <td align="right">状态:</td>
                <td><s:resSelect name="status" value="${snmpCred.status}" code="snmpCred_status" ltype="select" /></td>
                <td></td>
            </tr>   
            <tr>
                <td align="right">SNMP信息说明:</td>
                <td colspan=4><input id="snmpCredDesc" name="snmpCredDesc" value="${snmpCred.snmpCredDesc}" type="text" ltype="text" class="validate[optional,maxSize[128]] ip2"/></td>
            </tr>
            <tr>
            	<td colspan=6 align="center">
            	<table align="center">
            		<tr>
            	<td><input id="bnSave" type="submit" value="保 存" class="l-button mg6" /></td>
            	<c:if test="${action=='add'}">
                <td><input id="bnSaveAdd" type="submit" value="保存并继续添加" class="l-button mg6"  style="width:108px;"/></td>
                </c:if>
                <td><input id="bnClose" type="button" value="取 消" class="l-button mg6" /></td>
            		</tr>
            	</table>
                </td>
            </tr>
		</table>
    </form>
</body>
</html>

