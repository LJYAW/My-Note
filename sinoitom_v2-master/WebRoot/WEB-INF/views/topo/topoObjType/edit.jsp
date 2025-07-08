<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>机构编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 

<script type="text/javascript">
	var objNames = new Array();
	$(function() {

		<c:if test="${result=='success'}">
		
		window.parent.saveOK();
		</c:if>
        $("input").filter(".ip2").ligerTextBox({ width: 194 });
		//获取一级菜单
		getDevClass();
		//获取图元类型
		
		$(".load1").hide();
		$(".bgPictureName").hide();
		$(".buttons").hide();
	});
	//获取一级菜单
	function getDevClass() {
		$.getJSON(
						"${ctx}/topo/objType/getclassName.do?date="
								+ new Date(),
						function(result) {

							
								$("#classCode").empty();

								for ( var i = 0; i < result.objName.length; i++) {
									var objName = result.objName[i];
									
										if (objName.resCode == '${ topoObjectType.classCode}') {
											$("#classCode").append(
													"<option  value=\"" + objName.resCode + "\" selected>"
															+ objName.resName
															+ "</option>");
										} else {
											$("#classCode").append(
													"<option  value=\"" + objName.resCode + "\">"
															+ objName.resName
															+ "</option>");
										}
									
								

							}
							getTypeName();
						});
	}
	//加载二级菜单
	function getTypeName() {

		var classCode = $("#classCode").find("option:selected").val();

		if (classCode == "11") {//计算资源
			resName = "IT_HostType";
		} else if (classCode == "12") {//储存资源
			resName = "IT_StorageType";
		} else if (classCode == "13") {//网络资源
			resName = "IT_NetDevType";
		} else if (classCode == "14") {//外围设备
			resName = "";
		} else if (classCode == "21") {//操作系统
			resName = "IT_OSType";
		} else if (classCode == "22") {//系统软件
			resName = "IT_SysSoftware";
		} else if (classCode == "23") {//数据库系统
			resName = "IT_DBType";
		} else if (classCode == "24") {//中间件
			resName = "IT_MdlWareType";
		} else if (classCode == "25") {//服务进程
			resName = "IT_SvcProcType";
		} else if (classCode == "26") {//应用服务
			resName = "IT_AppSvcType";
		} else if (classCode == "27") {//应用系统
			resName = "IT_BizSysType";
		} else if (classCode == "-1") {
			resName = "";
		}
		if (resName == "") {
			$("#typeCode").empty();
		} else {
			$.getJSON("${ctx}/topo/objType/gettypeName.do?groupCode="
					+ resName, function(result) {
				$("#typeCode").empty();
				var objName = result.objName;
				for ( var i = 0; i < objName.length; i++) {
					var type = objName[i];
						if (type.resCode == '${ topoObjectType.typeCode}') {

							$("#typeCode").append(
									"<option  value=\"" + type.resCode + "\" selected>"
											+ type.resName + "</option>");
						} else {
							$("#typeCode").append(
									"<option  value=\"" + type.resCode + "\">"
											+ type.resName + "</option>");
						}
					

				}

			});
		}

	}

	function change(picId, fileId, count) {

		var str = $("#objIcon").val();
		$.ajax({
			type : "get",
			url : '${ctx}/topo/objType/checkFileExists.do?picName=' + str,
			contentType : "application/x-www-form-urlencoded; charset=utf-8",

			success : function(data) {
				if (data.result == 'success') {
					CheckFileName(picId, fileId, count);
				}
				if (data.result == 'error') {
					$.ligerDialog.confirm('您上传的图片名已存在，是否替换？', function(yes) {
						if (yes) {
							CheckFileName(picId, fileId, count);

						} else {
							editPictureName();
						}
					});
				}
			}
		});
	}

	function CheckFileName(picId, fileId, count) {
		var str = $("#objIcon").val();
		var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;

		if (reg.test(str)) {
			editPictureName();
			$.ligerDialog.warn('请上传不包含中文的图片');
		} else {
			$(".load1").show();
			var pic = document.getElementById(picId);
			var file = document.getElementById(fileId);
			if (window.FileReader) {//chrome,firefox7+,opera,IE10,IE9，IE9也可已用滤镜来实现
				oFReader = new FileReader();
				oFReader.readAsDataURL(file.files[0]);
				oFReader.onload = function(oFREvent) {
					pic.src = oFREvent.target.result;

				};
			} else if (document.all) {//IE8-
				file.select();
				var reallocalpath = document.selection.createRange().text;//IE下获取实际的本地文件路径
				if (window.ie6)
					pic.src = reallocalpath; //IE6浏览器设置img的src为本地路径可已直接显示图片
				else { //非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可已通过滤镜来实现，IE10浏览器不支持滤镜，需要用FileReader来实现，所已注意判断FileReader先
					pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\""
							+ reallocalpath + "\")";
					pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';//设置img的src为base64编码的透明图片，要不会显示红xx
				}
			}

		}
	}
	
	function editPictureName() {

		$(".load1").show();
		$(".bgPictureName").show();
		$(".buttons").show();
		$(".save").hide();
		$(".pic_file").hide();
		var pic = document.getElementById("pic1");
		var file = document.getElementById("objIcon");
		if (window.FileReader) {//chrome,firefox7+,opera,IE10,IE9，IE9也可已用滤镜来实现
			oFReader = new FileReader();
			oFReader.readAsDataURL(file.files[0]);
			oFReader.onload = function(oFREvent) {
				pic.src = oFREvent.target.result;

			};
		} else if (document.all) {//IE8-
			file.select();
			var reallocalpath = document.selection.createRange().text;//IE下获取实际的本地文件路径
			if (window.ie6)
				pic.src = reallocalpath; //IE6浏览器设置img的src为本地路径可已直接显示图片
			else { //非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可已通过滤镜来实现，IE10浏览器不支持滤镜，需要用FileReader来实现，所已注意判断FileReader先
				pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\""
						+ reallocalpath + "\")";
				pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';//设置img的src为base64编码的透明图片，要不会显示红xx
			}
		}
	}

	//验证图片名称
	function savePictureName() {

		$.ligerDialog.confirm('确定设为背景图？', function(yes) {

			if (yes) {
                $(".buttons").show();
				var name = $("#objIcon").val();

				var arrays = name.split(".");
				//后缀名
				var filenameExtension = arrays[arrays.length - 1];
				//中文的正则表达式
				var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
				var editName = document.getElementById("editPicName").value;

				if (editName != "" || editName != null) {
					if (editName == "请修改您的文件名,修改时请使用英文") {
						$.ligerDialog.warn('请您输入图片名称,要使用英文或者数字');
					} else if (reg.test(editName)) {
						$.ligerDialog.warn('您输入的图片名称中含有中文');
					} else {
						var arr = editName.split(".");
						var editNameExtension = arr[arr.length - 1];
						if (filenameExtension != editNameExtension) {
							editName = editName + "." + filenameExtension;
							checkFileExists(editName);

						} else if (filenameExtension == editNameExtension) {
							var editName = document
									.getElementById("editPicName").value;
							checkFileExists(editName);

						}
					}
				}
			} else {
				window.location.reload();
			}
		});
	}

	function checkFileExists(editName) {

		$.ajax({
					type : "get",
					url : '${ctx}/topo/objType/checkFileExists.do?editName='
							+ editName,
					contentType : "application/x-www-form-urlencoded; charset=utf-8",
					success : function(data) {
						if (data.result == 'success') {
							document.getElementById("editName").value = editName;
                            $(".buttons").hide();
							$(".save").show();
						}
						if (data.result == 'error') {
							$.ligerDialog
									.confirm(
											'您上传的文件名已存在,是否覆盖？',
											function(yes) {
												if (yes) {
													document
															.getElementById("editName").value = editName;
                                                    $(".buttons").hide();
													$(".save").show();
												} else {
													editPictureName();
												}
										});
						}
					}
				});
	}

	function refreshTypeName() {
		var typeName = $("#typeCode").find("option:selected").text();
		$("#typeName").val(typeName);

	}

	function refreshClassName() {
		var className = $("#classCode").find("option:selected").text();
		$("#className").val(className);
	}
	function submitForm() {

		refreshTypeName();
		refreshClassName();
		$("#formSave").submit();

	}
	function reseteditName(){
	    $(".load1").show();
		$(".bgPictureName").hide();
		$(".buttons").hide();
		$(".save").show();
		$(".pic_file").show();
	}
</script>

</head>
<body>

	<form:form id="formSave" modelAttribute="org"
		action="${ctx}/topo/objType/save.do" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="action" value="${action}" />
		
		<input type="hidden" name="id" value="${topoObjectType.objTypeID}" />
		<input type="hidden" id="className" name="className" value="" />
		<input type="hidden" id="typeName" name="typeName" value="" />
		<input type="hidden" name="editName" id="editName" value="" />
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
				<td align="right">对象分类:</td>
				<td><select name="classCode" id="classCode" style="width:196px"
					onchange="getTypeName()" >
				</select>
				</td>
				<td></td>
				<td align="right">对象类型:</td>
				<td><select name="typeCode" id="typeCode" style="width:196px" >

				</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">类型英文名称:</td>
				<td><input name="classEnName" id="classEnName"
					style="width:194px" value="${ topoObjectType.classEnName}" class="ip2"/> </select>
				</td>
				<td></td>
				<td align="right">说明:</td>
				<td><input name="description" id="description"
					style="width:194px" value="${ topoObjectType.description}" class="ip2"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">图元类型:</td>
				<td><select name="objType" id="objType" style="width:196px" >
				<c:choose>
				<c:when test="${topoObjectType.objType=='0'}">
		        <option value="1">节点</option>
				<option value="0" selected>子图</option>
		        </c:when>
		        
		        <c:otherwise>
		        <option value="1" selected>节点</option>
				<option value="0">子图</option>
		        </c:otherwise>
		        
		        </c:choose>
				</select>
				</td>
				<td></td>
				<td align="right">图元显示名称:</td>
				<td><input name="objName" id="objName" style="width:194px"
					value="${ topoObjectType.objName}" class="ip2"/>
				</td>
				<td></td>
			</tr>
			<tr>


				<td></td>

				<td align="right" colspan="2"><div class="pic_file">上传图片:</div>
				</td>
				<td align="left" colspan="2">
					<div class="pic_file">
						<input id="objIcon" name="picture" value="" type="file"
							ltype="file" class="validate[required,maxSize[32]]"
							onchange="change('pic1','objIcon',1)" />
					</div>
				</td>

				<td></td>

			</tr>

			<tr>


				<td></td>

				<td align="right" colspan="2"><div class="bgPictureName">修改图片名:</div>
				</td>
				<td align="left" colspan="2">
					<div class="bgPictureName">
						<input id="editPicName" name="editPicName"
							value="请修改您的文件名,修改时请使用英文" type="text" ltype="text"
							class="validate[required,maxSize[32]] ip1" onchange="savePictureName()"/>

					</div>
				</td>

				<td><div class="buttons">
						<input id="" type="button" value="确定" class="l-button mg6"
							onclick="savePictureName()" /> <input id="" type="button"
							value="取消" class="l-button mg6" onclick="reseteditName()" />
					</div></td>

			</tr>
			<tr>


				<td align="center" colspan="6">

					<div class="load1">

						<img src="" id="pic1" class="load1"
							style="width:100%;height:auto;">
					</div>
				</td>


			</tr>
			<tr>
				<td align="center" colspan=6>
					<div class="save" id="save">

						<input type="button" value="保存" onclick="submitForm()" class="l-button mg6" />
					</div></td>

			</tr>
		</table>
	</form:form>
</body>
</html>

