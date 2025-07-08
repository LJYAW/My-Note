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
<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />

<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/static/formatter.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>


<script type="text/javascript">

var flag = false;
var flag2 = false;
var linuxOSType ;

	$(function ()
		{
		
		<c:if test="${result=='success'}">
		window.parent.saveOK();
        window.parent.closeDlg();
		</c:if>
		
		<c:if test="${result=='searchSuccess'}">

        window.parent.saveOK();
        window.parent.closeDlg();
        </c:if>
		
        getOrgInfo();
		
		var ipNetMaskSelect = $("#ipNetMaskSelect").ligerComboBox({
        	width: 200,
        	selectBoxWidth: 200, selectBoxHeight: 300, 
        	valueFieldID: 'ipNetMask',
        	treeLeafOnly: false,
        	initValue: '${ipHost.ipNetMask}',
        	tree: { url: timeURL('${ctx}/system/res/item/getMinData.do?grpCode=subnetMask'), 
        		checkbox: false, nodeWidth: 120 },
	    	onSelected: function (id, text){
	    		//$("#ipNetMaskSelect").val(id);
	        }
        });
		ipNetMaskSelect.selectValue("255.255.255.0");
        
		getOsType();
		getDevAccess();
		getHostType();
		checkHostType();
		$("#inpu2").prop('checked',true);
		$("#addform").css("display","none");
		$("#inputAccess").css("display","");
		$("#usnamePsTr").css("display","none");
		$("#seleAccess").css("display","none");
		
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
		var orgId = '${ipHost.orgID==null ? LOGIN_USER_MAIN_ORGAN.orgId:ipHost.orgID}';
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
			//checkAccessPort();
			var ipAddress =$("#ipAddress").val();
			var devAcsUserId = $("#devAcsUserId").val();
			//if(flag&&flag2){
			if(flag){
				$("#trTxt2").removeClass("dN");
				$("#bnCheck").attr("disabled",true);
				$("#bnSave").attr("disabled",true);
				var flagForm = "subForm";
				var url = timeURL('${ctx}/fas/res/host/resHosts/checkForm.do');
				$.ajax({
					type: "POST",
		              dataType: 'json',
		              url: url,
		              contentType: "application/x-www-form-urlencoded; charset=utf-8",
		              data: "ipAddress="+ipAddress+"&devAcsUserId="+devAcsUserId+"&flagForm="+flagForm,
	        		success: function(data) {
	        			if(data.success!=null){
	        				
	        				var action = "Sub";
	        				$("#action").val(action);
	        				var str = $("#resTypeCode option:checked").text();
	        				$("#resTypeName").val(str);
	        				$("#osFeature").val($("#osVersionType").find("option:selected").attr("osFeature"));
	        				var osClassName=$("#osClass").find("option:selected").text();
	        				$("#osClassName").val(osClassName);
	        				
	        				linuxOSType = data.linuxOSType;
	        				$("#osType").val(linuxOSType);
	        				
	        				$("#formSave").submit();
	        			}else if(data.error!=null){
	        				//$.ligerDialog.error(data.error);
	        				layer.msg(data.error, {icon: 7, time: 3000});
	        				$("#trTxt2").addClass("dN");
							$("#bnCheck").attr("disabled",false);
							$("#bnSave").attr("disabled",false);
	        			}else{
	        				//$.ligerDialog.error(data.message);
	        				layer.msg(data.message, {icon: 7, time: 3000});
	        				$("#trTxt2").addClass("dN");
							$("#bnCheck").attr("disabled",false);
							$("#bnSave").attr("disabled",false);
	        			}
				    	 
	        		}
	        	}); 
			}
			
		}
		
		
		function checkform(){
			
			checkIP();
			//checkAccessPort();
			var ipAddress =$("#ipAddress").val();
			var devAcsUserId = $("#devAcsUserId").val();
			//if(flag&&flag2){
			if(flag){
				$("#trTxt").removeClass("dN");
				$(".Tr").attr("disabled","disabled");
				$("#resTypeCode").attr("disabled","disabled");
				$("#osClass").attr("disabled","disabled");
				$("#bnCheck").attr("disabled",true);
				$("#bnSave").attr("disabled",true);
				var flagForm = "checkForm";
				var url = timeURL('${ctx}/fas/res/host/resHosts/checkForm.do');
				$.ajax({
					type: "POST",
		              dataType: 'json',
		              url: url,
		              contentType: "application/x-www-form-urlencoded; charset=utf-8",
		              data: "ipAddress="+ipAddress+"&devAcsUserId="+devAcsUserId+"&flagForm="+flagForm,
	        		success: function(data) {
	        			if(data.success!=null){
	        				linuxOSType = data.linuxOSType;
	        				$("#osType").val(linuxOSType);
	        				//$.ligerDialog.success(data.success);
	        				layer.msg(data.success, {icon: 1, time: 1500});
	        			}else if(data.error!=null){
	        				//$.ligerDialog.error(data.error);
	        				layer.msg(data.error, {icon: 7, time: 3000});
	        			}else{
	        				//$.ligerDialog.error(data.message);
	        				layer.msg(data.error, {icon: 7, time: 3000});
	        			}
				    	 
				    	$("#trTxt").addClass("dN");
				    	$(".Tr").removeAttr('disabled'); 
						$("#resTypeCode").removeAttr('disabled'); 
						$("#osClass").removeAttr('disabled'); 
						$("#bnCheck").attr("disabled",false);
						$("#bnSave").attr("disabled",false);
						
	        		}
	        	}); 
			}
			
		}
		
		
   
   function getOsType(){
	   var myname="osType";
	   var osClassCode=$("#osClass").find("option:selected").val();
	   var osClass=$("#osClass").find("option:selected").text();
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
		  var osClass=$("#osClass").find("option:selected").text();
		  var myname="osVersionType";
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
	                         
	                   }
	               }); 
		}
   
   
   function getDevAccess(){
	   var myname="devAcsUserId";
	   var devAcsUserId = null;
	   $.ajaxSettings.async = false;    //(同步执行)
           $.getJSON(
               "${ctx}/devices/accessUser/getData.do",
               function (result) {
                   if (result != null) {
                       $("#" + myname).empty();
                       for (var i = 0; i < result.length; i++) {
                          var access= result[i];
                          if(access.id=='${ipHost.devAcsUserId}'){
                            $("#" + myname).append("<option  value=\"" + access.id + "\" selected>" + access.acsUserName + "</option>");
                          }else{
                            $("#" + myname).append("<option  value=\"" + access.id + "\">" + access.acsUserName + "</option>");
                            devAcsUserId = $("#devAcsUserId").val();
                          }
                    	  
                       }
                   }
               });
           getAccessmode(devAcsUserId);
           getAccessport(devAcsUserId);
	} 
   
	function getAccessmode(devAcsUserId){
		var userId = null;
		if(devAcsUserId!=null&&devAcsUserId!=undefined){
			userId = devAcsUserId ;
		}else{
			var id = $("#devAcsUserId").val();
			userId = id; 
		}
		$.ajaxSettings.async = false;    //(同步执行)
        $.getJSON(
            "${ctx}/devices/accessUser/getData.do",
            function (result) {
                if (result != null) {
                    $("#accessmode").empty();
                    for (var i = 0; i < result.length; i++) {
                       var access= result[i];
                       if(access.id==userId){
                    	   var s = access.accessTool;
                    	   $("#accessmode").val(s);
                       }
                 	  
                    }
                }
            });
		
   }
   
	function getAccessport(devAcsUserId){
		
		var userId = null;
		if(devAcsUserId!=null&&devAcsUserId!=undefined){
			userId = devAcsUserId ;
		}else{
			var id = $("#devAcsUserId").val();
			userId = id; 
		}
		$.ajaxSettings.async = false;    //(同步执行)
        $.getJSON(
            "${ctx}/devices/accessUser/getData.do",
            function (result) {
                if (result != null) {
                    $("#accessPort").empty();
                    for (var i = 0; i < result.length; i++) {
                       var access= result[i];
                       if(access.id==userId){
                    	   var s = access.accessPort;
                    	   $("#accessPort").val(s);
                       }
                 	  
                    }
                }
            });
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
   
   //注意下面注释内容
   function checkAccessPort(){
	   //添加凭证时使用验证端口号输入是否合法（注意此处的端口号字段为accessPort 1 ）
	   if($("#accessPort1").val()!="" && $("#accessPort1").val()!=null){
		   var accessPort=$("#accessPort1").val();
		   var reg=/^[1-9]\d*$/;
		    var re = new RegExp(reg);
		    if (!re.test(accessPort)) {
		    	flag2 = false;
				//$.ligerDialog.warn('访问端口只能输入正整数！');
				layer.msg('访问端口只能输入正整数！', {icon: 7, time: 2000});
	            $("#accessPort1").val("");
	            return ;
		    }else{
				flag2 = true;
			}
		}else{
			//$.ligerDialog.warn('访问端口不能为空！');
			layer.msg('访问端口不能为空！！', {icon: 7, time: 2000});
			flag2 = false;
			 return ;
		}
   }
   
   function getHostType(){
	   var str = $("#resTypeCode").val();
	   if(str==1){
		   $("#resTypeTR").css("display","");
		   $("#typeFlag").val("服务器");
	   }else{
	       $("#resTypeTR").css("display","none");
	       $("#typeFlag").val("非服务器");
	       
	       	$("#hostType").val("");
	        $("#homedHostId").val("");
	        $("#homedHostIp").val("");
	        
	   }
	   var resTypeName = $("#resTypeCode option:checked").text();
		$("#resTypeName").val(resTypeName);
   }
   
   //判断hostType，确定隐藏还是显示“宿主机”文本框
   function checkHostType(){
	   var str = $("#hostType").val();
	   if(str==0){
		   $(".resTypeCodeTR").css("display","");
		   $("#typeFlag").val("宿主机");
		   getResTypeCode();
	   }else{
	       $(".resTypeCodeTR").css("display","none");
	       $("#typeFlag").val("非宿主机");
	       $("#homedHostId").val("");
	        $("#homedHostIp").val("");
	   }
   }
   
   //获取宿主机下拉框
   function  getResTypeCode(){
		  var myname="homedHostId";
		  async: false ,
	           $.getJSON(
	               "${ctx}/fas/res/host/resHosts/getHostByHostType.do",
	               function (result) {
	                   if (result != null) {
	                       $("#" + myname).empty();
	                       for (var i = 0; i < result.list.length; i++) {
	                          var host= result.list[i];
	                          if(host.hostId=='${ipHost.hostId}'){
	                              $("#" + myname).append("<option  value=\"" + host.hostId + "\" selected>" + host.ipAddress + "</option>");  
	                          }else{
	                              $("#" + myname).append("<option  value=\"" + host.hostId + "\">" + host.ipAddress + "</option>");
	                          }
	                       }
	                         
	                   }
	               }); 
		  getHomedHostIp();
		}
   
   function getHomedHostIp(){
	   var homedHostIp=$("#homedHostId").find("option:selected").text();
	   $("#homedHostIp").val(homedHostIp);
   }
   
   function addform(){
	   checkIP();
	   checkAccessPort();
	   if(flag&&flag2){
		   var accessmode = $("#accessmode1").val();
		   $("#accessmode").val(accessmode);
		   var accessPort = $("#accessPort1").val();
		   $("#accessPort").val(accessPort);
		   
		   $("#action").val("addForm");
		   
		   $("#formSave").submit();
	   }
   }
   
   
</script>

</head>
<body style="overflow: hidden;">
	<form id="formSave" modelAttribute="resHosts" action="${ctx}/fas/res/host/resHosts/editSave.do" method="post">
		<input type="hidden" name="action" id="action" />
		<input type="hidden" name="id" value="${id}"/>
		<input type="hidden" name="resTypeName" id="resTypeName" />
		<input type="hidden" name="osClassName" id="osClassName" />
		<input type="hidden" name="osFeature" id="osFeature" />
		<input type="hidden" name="homedHostIp" id="homedHostIp" /> 
		<input type="hidden" name="typeFlag" id="typeFlag" />
		<input type="hidden" name="orgName" id="orgName" />
		<input type="hidden" id="closeDlg" name="closeDlg" value="true"/>
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
				<td align="right">设备类型：</td>
				<td><s:resSelect id="resTypeCode" name="resTypeCode" value="${ipHost.resTypeCode}" code="IT_HostType" ltype="select" style="width: 200px;" onchange="getHostType();"/></td>
				<td></td>
			</tr>
			
			<tr id="resTypeTR"> 
				<td align="right">服务器类型：</td>
				<td>
					<select name="hostType" id="hostType" style="width:200px;height: 25px;" class="Tr" onchange="checkHostType();">
						<option value="0" <c:if test="${ipHost.hostType==0}">selected</c:if> >虚拟机</option>
						<option value="1" <c:if test="${ipHost.hostType==1}">selected</c:if> >物理机</option>
						<option value="2" <c:if test="${ipHost.hostType==2}">selected</c:if> >宿主机</option>
						<option value="3" <c:if test="${ipHost.hostType==3}">selected</c:if> >集群</option>
					</select>
					
				</td>
				<td></td>
				<td align="right" class="resTypeCodeTR">宿主机：</td>
				<td>
<%--					<s:resSelect id="resTypeCode" name="resTypeCode"  code="IT_HostType" ltype="select" style="width: 200px;"/>--%>
<%--			宿主机从表res_hosts中查询hostType=2的数据列出ipAdderss		--%>
					<select id="homedHostId" name="homedHostId" value="${ipHost.homedHostId}" style="width:200px;height: 25px;" class="Tr resTypeCodeTR" >
                  	</select>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td align="right">IP地址：</td>
				<td><input id="ipAddress" name="ipAddress" readonly="readonly" value="${ipHost.ipAddress}" type="text" ltype="text" onchange="checkIP();" class="ip1 Tr"  /></td>
				<td></td>
				<td align="right">IP地址掩码：</td>
				<td><input id="ipNetMaskSelect" name="ipNetMaskSelect" value="${ipHost.ipNetMask}" type="text" class="ip1 Tr" /></td>
				<td></td>
			</tr>
			
			<tr>
				<td align="right" nowrap="nowrap">操作系统分类：</td>
				<td>
					<input readonly="readonly" id="osClass" name="osClass" value="${ipHost.osClass}" code="IT_OSType" class="ip1 Tr" />
				</td>
				<td></td>
				<td align="right" nowrap="nowrap">操作系统类型：</td>
				<td>
					<input readonly="readonly" id="osType" name="osType" value="${ipHost.osType}" type="text" ltype="text" class="ip1 Tr"  />
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td align="right" id="devAcsTr">用户凭证：</td>
				<td id="devAcsTr2">
					<select id="devAcsUserId" name="devAcsUserId" style="width:200px;height: 25px;" class="Tr" onchange="getAccessmode();getAccessport();">
                  	</select>
                </td>
                <td></td> 
                <td></td> 
                <td></td> 
                <td></td> 
			</tr>
			
			
			<tr id="inputAccess">
				<td align="right">访问方式：</td>
				<td>
					<input readonly="readonly" id="accessmode" name="accessmode"  type="text" class="ip1 Tr" />
				</td>
				<td></td>
				<td align="right">访问端口：</td>
				<td>
					<input readonly="readonly" id="accessPort" name="accessPort"  type="text" ltype="text" class="ip1 Tr"  />
				</td>
				<td></td>
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

