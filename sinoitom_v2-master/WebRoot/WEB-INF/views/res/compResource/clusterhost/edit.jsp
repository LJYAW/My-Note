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
<title>添加/修改集群信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />

<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/selectMultip.js" type="text/javascript"></script>

<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>


<script type="text/javascript">

var flag = false;
	$(function ()
		{
		
		<c:if test="${result=='success'}">
		window.parent.saveOK();
        window.parent.closeDlg();
    	</c:if>
    	
    	<c:if test="${result=='error'}">
        $.ligerDialog.error('${message}');
		</c:if>
		
        getOrgInfo();
		
		var ipNetMaskSelect = $("#ipNetMaskSelect").ligerComboBox({
        	width: 200,
        	selectBoxWidth: 200, selectBoxHeight: 300, 
        	valueFieldID: 'ipNetMask',
        	treeLeafOnly: false,
        	initValue: '${resHosts.ipNetMask}',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=subnetMask'), 
        		checkbox: false, nodeWidth: 120 },
	    	onSelected: function (id, text){
	    		//$("#ipNetMaskSelect").val(id);
	        }
        });
		ipNetMaskSelect.selectValue("255.255.255.0");
        
		<c:if test="${action=='add'}">
		selectMultip.register();
    	</c:if>
    	<c:if test="${action=='edit'}">
		selectMultip.register();
		selectMultip.setVal("purpose",'${purpose}');
    	</c:if>
		
		
	  	//$("#formSave").ligerForm({inputWidth:200});
	  	$("input").filter(".ip1").ligerTextBox({ width: 200 });
		$("#formSave").validationEngine({promptPosition: "centerRight"});
		
		<c:if test="${result=='error'}">
	        //$.ligerDialog.error('${message}');
	        layer.msg('${message}', {icon: 7, time: 3000});
	        $("#trTxt").addClass("dN");
			$(".Tr").removeAttr('disabled'); 
			$("#resTypeCode").removeAttr('disabled'); 
			$("#osClass").removeAttr('disabled'); 
			$("#bnCheck").attr("disabled",false);
			$("#bnSave").attr("disabled",false);
		</c:if>	
		
		<c:if test="${result=='CheckSuccess'}">
			$("#trTxt").addClass("dN");
			$(".Tr").removeAttr('disabled'); 
			$("#resTypeCode").removeAttr('disabled'); 
			$("#osClass").removeAttr('disabled'); 
			$("#bnCheck").attr("disabled",false);
			$("#bnSave").attr("disabled",false);
			
			$("#inpu2").prop('checked',true);
			$("#devAcsTr").css("display","");
			$("#devAcsTr2").css("display","");
			$("#resTypeTR").css("display","");
			
			$("#inputAccess").css("display","");
	    	$("#usnamePsTr").css("display","none");
	    	$("#seleAccess").css("display","none");
	    	$("#checkform").css("display","");
	    	$("#addform").css("display","none");
			
	        //$.ligerDialog.success('${message}');
	        layer.msg('${message}', {icon: 1, time: 1500});
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
	
	function getOrgInfo(){
		var orgId = '${resHosts.orgID==null ? LOGIN_USER_MAIN_ORGAN.orgId:resHosts.orgID}';
        $("#mainOrg").ligerComboBox({
        	width: 200, selectBoxWidth: 200, selectBoxHeight: 200, 
        	textField: 'orgShortName',
        	valueFieldID: 'orgID',
        	treeLeafOnly: false,
        	initValue: orgId,
        	tree: { url: timeURL('${ctx}/system/organ/getMinDataNotNine.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
        		textFieldName: 'orgShortName', checkbox: false },
	        onSelected: function (id, text)
	        {
	     	 	$("#orgName").val(text);
	        }
        });
	}
	
		
		function sendform(){
			
			checkIP();
	        if(flag){
	        	
		        $("#formSave").submit();
	        }			
			
		}
		
		
   
   function checkIP(){
		if($("#ipAddress").val()!="" && $("#ipAddress").val()!=null){
		   var devIpAddr=$("#ipAddress").val();
		   if (!ipok(devIpAddr)) {
			   flag = false;
				//$.ligerDialog.warn('请输入合法IP地址！');
				layer.msg('请输入合法IP地址！', {icon: 7, time: 2000});
              $("#ipAddress").val("");
              return ;
			}else{
				flag = true;
			}
		}else{
			//$.ligerDialog.warn('IP地址不能为空！');
			layer.msg('IP地址不能为空！', {icon: 7, time: 2000});
			flag = false;
			 return ;
		}
   }
   
   
</script>

</head>
<body style="overflow: hidden;">
	<form id="formSave" modelAttribute="resHosts" action="${ctx}/res/host/clusterHosts/save.do" method="post">
		<input type="hidden" name="action" value="${action}" />
		<input type="hidden" name="id" value="${id}"/>
		<input type="hidden" name="hostType" value="3"/>
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
				<td align="right">所属机构：</td>
				<td><input id="mainOrg" name="mainOrg" type="text" class="ip1 Tr" /></td>
				<td></td>
				<td align="right">服务器类型：</td>
				<td><input value="集群" id="hostTypeName" name="hostTypeName" type="text" readonly="readonly" class="ip1 Tr" /></td>
				<td></td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="right">集群访问IP：</td>
				<td><input id="ipAddress" name="ipAddress" value="${resHosts.ipAddress}" type="text" ltype="text" onchange="checkIP();" class="ip1 Tr"  /></td>
				<td></td>
				<td align="right">IP地址掩码：</td>
				<td><input id="ipNetMaskSelect" name="ipNetMaskSelect" value="${resHosts.ipNetMask}" type="text" class="ip1 Tr" /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">集群名称：</td>
				<td><input id="hostName" name="hostName"  value="${resHosts.hostName}" type="text" ltype="text" class="ip1 Tr"  /></td>
				<td></td>
				<td align="right">用途分类：</td>
				<td>
					<select multip name="purpose" id="purpose" style="width:200px;height: 25px;" class="Tr">  
					        <option value="WebService" choose >WebService</option>  
					        <option value="AppService">AppService</option>  
					        <option value="DBService">DBService</option>  
					</select>
				</td>
				<td nowrap="nowrap"><span style="color: red">*可多选</span> </td>
			</tr>
			<tr>
           		<td align="right" nowrap="nowrap">集群说明：</td>
				<td colspan=5><input id="description" name="description" value="${resHosts.description }"  type="text" ltype="text" class="ip2"  /></td>
            </tr>
			
			<tr>
           		<td align="center" colspan=6 >
           		<input id="bnSave" type="button" onclick="sendform()" value="保 存" class="l-button mg6" />
           		</td>
            </tr>
				
				
		</table>
    </form>
    <br/>
</body>
</html>

