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
	<title>获取配置信息</title>
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
        var ip = '${ip}';
        var id = '${id}';
        $(function ()
        {

            <c:if test="${result=='success'}">
            window.parent.saveOK();
            window.parent.closeDlg();
            </c:if>

            <c:if test="${result=='error'}">
            layer.msg('${message}', {icon: 7, time: 3000});
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

            $("#inpu2").prop('checked',true);

            //$("#formSave").ligerForm({inputWidth:200});
            $("input").filter(".ip1").ligerTextBox({ width: 200 });
            $("#formSave").validationEngine({promptPosition: "centerRight"});


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
            if($("#accessPort").val()!="" && $("#accessPort").val()!=null){
                var accessPort=$("#accessPort").val();
                var reg=/^[1-9]\d*$/;
                var re = new RegExp(reg);
                if (!re.test(accessPort)) {
                    flag2 = false;
                    //$.ligerDialog.warn('访问端口只能输入正整数！');
                    layer.msg('访问端口只能输入正整数！', {icon: 7, time: 2000});
                    $("#accessPort").val("");
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


        function addform(){
            checkIP();
            checkAccessPort();


            var devIpAddr=$("#ipAddress").val();
            if(devIpAddr==ip){
                var accessPort = $("#accessPort").val();
                var userName = $("#userName").val();
                var passWord = $("#passWord").val();
                if(flag&&flag2&&accessPort!=""){
                    if(userName!=""&&passWord!=""){
                        var str = $("#hostTypeValue").val();
                        $("#resTypeName").val(str);
                        if(id!=null){
                            $("#id").val(id);
                        }
                        $("#formSave").submit();
                        $("#bnSave").attr("disabled",true);
                        $("#trTxt").removeClass("dN");
                    }else{
                        layer.msg('用户名密码不能为空！', {icon: 7, time: 2000});
                    }

                }
			}else{
                var url = timeURL('${ctx}/fas/res/compHosts/checkHost.do');
                $.ajax({
                    type: "POST",
                    dataType: 'json',
                    url: url,
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    data: "ipAddress="+devIpAddr,
                    success: function(data) {
                        if(data.success!=null){
                            var accessPort = $("#accessPort").val();
                            var userName = $("#userName").val();
                            var passWord = $("#passWord").val();
                            if(flag&&flag2&&accessPort!=""){
                                if(userName!=""&&passWord!=""){
                                    var str = $("#hostTypeValue").val();
                                    $("#resTypeName").val(str);
                                    $("#formSave").submit();
                                    $("#bnSave").attr("disabled",true);
                                    $("#trTxt").removeClass("dN");
								}else{
                                    layer.msg('用户名密码不能为空！', {icon: 7, time: 2000});
								}

                            }
                        }else if(data.error!=null){
                            //$.ligerDialog.error(data.error);
                            layer.msg(data.error, {icon: 7, time: 3000});
                        }else{
                            //$.ligerDialog.error(data.message);
                            layer.msg(data.message, {icon: 7, time: 3000});
                        }

                    }
                });
			}
        }


	</script>

</head>
<body style="overflow: hidden;">
<form id="formSave" modelAttribute="resHosts" action="${ctx}/fas/res/compHosts/addVmInfoSave.do" method="post">
	<input type="hidden" name="action" id="action" />
	<input type="hidden" name="id" value="${id}"/>
	<input type="hidden" name="devAcsUserId" value="${devAcsUserId}"/>
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
			<td>
				<input type="text" style="display:none" id="resTypeCode" name="resTypeCode" value="1" readonly="readonly" />
				<input type="text" id="hostTypeValue" name="hostTypeValue" value="服务器" readonly="readonly" class="ip1 Tr" />
			</td>
			<td></td>
		</tr>

		<tr id="resTypeTR">
			<td align="right">服务器类型：</td>
			<td>
					<select name="hostType" id="hostType" style="width:200px;height: 25px;" class="Tr" disabled>
						<option value="0" <c:if test="${ipHost.hostType==0}">selected</c:if> >虚拟机</option>
						<option value="1" <c:if test="${ipHost.hostType==1}">selected</c:if> >物理机</option>
						<option value="2" <c:if test="${ipHost.hostType==2}">selected</c:if> >宿主机</option>
						<option value="3" <c:if test="${ipHost.hostType==3}">selected</c:if> >集群</option>
					</select>
			</td>
			<td></td>
			<td align="right" nowrap="nowrap">虚拟化软件：</td>
			<td>
				<select name="virtualizeSoft" id="virtualizeSoft" style="width:200px;height: 25px;" class="Tr" >
					<option value="VMware" <c:if test="${ipHost.virtualizeSoft=='VMware'}">selected</c:if> >VMware</option>
					<option value="OpenStack" <c:if test="${ipHost.virtualizeSoft=='OpenStack'}">selected</c:if> >OpenStack</option>

				</select>
			</td>
			<td></td>
		</tr>

		<tr>
			<td align="right">IP地址：</td>
			<td><input id="ipAddress" name="ipAddress" type="text" ltype="text" value="${ipHost.ipAddress}" onchange="checkIP();" class="ip1 Tr"  /></td>
			<td></td>
			<td align="right">IP地址掩码：</td>
			<td><input id="ipNetMaskSelect" name="ipNetMaskSelect" value="${ipHost.ipNetMask}" type="text" class="ip1 Tr" /></td>
			<td></td>
		</tr>

		<%--<tr>--%>
			<%--<td align="right" nowrap="nowrap">操作系统分类：</td>--%>
			<%--<td>--%>
				<%--<input readonly="readonly" id="osClass" name="osClass" value="${ipHost.osClass}" type="text" ltype="text" class="ip1 Tr"  />--%>
			<%--</td>--%>
			<%--<td></td>--%>
			<%--<td align="right" nowrap="nowrap">操作系统类型：</td>--%>
			<%--<td>--%>
				<%--<input readonly="readonly" id="osType" name="osType" value="${ipHost.osType}" type="text" ltype="text" class="ip1 Tr"  />--%>
			<%--</td>--%>
			<%--<td></td>--%>
		<%--</tr>--%>

		<tr>
			<td align="right">访问方式：</td>
			<td>
				<select name="accessmode" id="accessmode" style="width:200px;height: 25px;" class="Tr">
					<option value="SSH" <c:if test="${ipHost.accessmode=='SSH'}">selected</c:if>>SSH</option>
					<option value="Telnet" <c:if test="${ipHost.accessmode=='Telnet'}">selected</c:if>>Telnet</option>
				</select>
			</td>
			<td></td>
			<td align="right">访问端口：</td>
			<td><input id="accessPort" name="accessPort" type="text" value="${ipHost.accessPort}" class="ip1 Tr" onchange="checkAccessPort();"/></td>
			<td></td>
		</tr>

		<tr>
			<td align="right">用户名：</td>
			<td>
				<input id="userName" name="userName" value="${devAccess.userName}" placeholder="请输入用户名" type="text" class="validate[required[用户名不能为空],custom[onlyLetterNumber]]  ip1 Tr" />
			</td>
			<td ></td>
			<td align="right">密码：</td>
			<td>
				<%--<input id="passWord" name="passWord" value="${devAccess.passWord}" placeholder="请输入密码" type="password"  class="validate[required[密码不能为空],custom[onlyLetter-Number]]  ip1 Tr" />--%>
				<input id="passWord" name="passWord" value="${devAccess.passWord}" placeholder="请输入密码" type="password"  class="validate[required[密码不能为空]]  ip1 Tr" />
			</td>
			<td></td>
		</tr>

		<tr>
			<td align="center" colspan=6 >
				<input id="bnSave" type="button" value="提 交" onclick="addform()" class="l-button mg6" />
			</td>
		</tr>


	</table>
	<div id="trTxt" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:left">
		<div style="width: 50%" align="right">
			<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;获取配置信息中...
           		</span>
		</div>

	</div>
</form>
<br/>
</body>
</html>

