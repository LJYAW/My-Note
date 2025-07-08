<!DOCTYPE html>
<%@ page import="com.sino.base.common.Global"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>背景图设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js"
	type="text/javascript"></script>
<script
	src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">

var canOrgSelect = false;

	$(function ()
	{	$(".load1").hide();
	    $(".bgPictureName").hide();			
		<c:if test="${result=='success'}">
		
		window.parent.editOK();    
		window.parent.closeDlg();
		</c:if>
		
	});
	function submitForm(){
	
    $("#formSave").submit();
    
	}
	function change(picId,fileId,count){
		
   
	var str =$("#bgPicture").val();
	  $.ajax({
      	    type: "POST",
      	    url:'${ctx}/topo/graphManage/checkFileExists.do?picName='+str,
      	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			  
      		success: function(data) {
      			if(data.result=='success'){
	            CheckFileName(picId,fileId,count);
	            }
	            if(data.result=='error'){
	            $.ligerDialog.confirm('您上传的文件名已存在,是否替换？', function (yes){
	            if(yes){
	            CheckFileName(picId,fileId,count);
	            
	            }else{
	            editPictureName();
	            }
	            })
	            }     	
      		}
      	}); 
      	CheckFileName(picId,fileId,count);
      	}
 function CheckFileName(picId,fileId,count){ 
    var str =$("#bgPicture").val();    	
	var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/
	if(reg.test(str)){
     editPictureName();
     $.ligerDialog.warn('暂不支持中文名称图片的上传<br/>请您先修改图片名称');
    }else{
      $(".load1").show();
        var pic = document.getElementById(picId);
        var file = document.getElementById(fileId);
        if(window.FileReader){//chrome,firefox7+,opera,IE10,IE9，IE9也可已用滤镜来实现
           oFReader = new FileReader();
           oFReader.readAsDataURL(file.files[0]);
           oFReader.onload = function (oFREvent) {
          	 pic.src = oFREvent.target.result;
          	 
          	 //获取图片尺寸
          	var image = new Image();
				image.src = pic.src;

 				document.getElementById("width").value = image.width
 				document.getElementById("height").value = image.height

           };        
        }
        else if (document.all) {//IE8-
            file.select();
            var reallocalpath = document.selection.createRange().text//IE下获取实际的本地文件路径
            if (window.ie6) pic.src = reallocalpath; //IE6浏览器设置img的src为本地路径可已直接显示图片
            else { //非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可已通过滤镜来实现，IE10浏览器不支持滤镜，需要用FileReader来实现，所已注意判断FileReader先
                pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
                pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';//设置img的src为base64编码的透明图片，要不会显示红xx
            }
        }
        
        }
    }
    function delPic(picid,fileid,count){
     $("#"+fileid).val("");
     $(".load1").hide();
    }
    //修改图片名称
    function editPictureName(){
    
    $(".load1").show();
    $(".bgPictureName").show();
     $(".save").hide();   
        var pic = document.getElementById("pic1");
        var file = document.getElementById("bgPicture");
        if(window.FileReader){//chrome,firefox7+,opera,IE10,IE9，IE9也可已用滤镜来实现
           oFReader = new FileReader();
           oFReader.readAsDataURL(file.files[0]);
           oFReader.onload = function (oFREvent) {
          	 pic.src = oFREvent.target.result;
          	 
          	 //获取图片尺寸
          	var image = new Image();
				image.src = pic.src;

 				document.getElementById("width").value = image.width
 				document.getElementById("height").value = image.height

           };        
        }
        else if (document.all) {//IE8-
            file.select();
            var reallocalpath = document.selection.createRange().text//IE下获取实际的本地文件路径
            if (window.ie6) pic.src = reallocalpath; //IE6浏览器设置img的src为本地路径可已直接显示图片
            else { //非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可已通过滤镜来实现，IE10浏览器不支持滤镜，需要用FileReader来实现，所已注意判断FileReader先
                pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
                pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';//设置img的src为base64编码的透明图片，要不会显示红xx
            }
        }	
    }
    
    //验证图片名称
    function savePictureName(){
    
    $.ligerDialog.confirm('确定设为背景图？', function (yes){
        		
     if(yes){ 
        	
    var name = document.getElementById("bgPicture").value;
    
    var arrays = name.split(".");
    //后缀名
	var  filenameExtension = arrays[arrays.length-1];
	//中文的正则表达式
	var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/
    var editName = document.getElementById("editPicName").value;
    
    if(editName!="" || editName!=null){
    if(editName=="请修改您的文件名,修改时请使用英文"){
    $.ligerDialog.warn('请您输入图片名称,要使用英文或者数字');
    }else if(reg.test(editName)){
    $.ligerDialog.error('暂不支持中文名称图片的上传<br/>请您先修改图片名称');
    }else{
    var arr = editName.split(".");
    var editNameExtension = arr[arr.length-1];
    if(filenameExtension != editNameExtension){
    editName=editName+"."+filenameExtension;
    checkFileExists(editName);
   
    }else if(filenameExtension == editNameExtension){
    var editName = document.getElementById("editPicName").value;
     checkFileExists(editName);
    
        } 
      }
     }
    }else{
    window.location.reload();
    }
    });
    }
    
    function checkFileExists(editName){
    $.ajax({
      	    type: "POST",
      	    url:'${ctx}/topo/graphManage/checkFileExists.do?editName='+editName,
      	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			  
      		success: function(data) {
      			if(data.result=='success'){
	             document.getElementById("editName").value=editName;
	             submitForm();
	            }
	            if(data.result=='error'){
	            $.ligerDialog.confirm('您上传的文件名已存在,是否覆盖？', function (yes){
	            if(yes){
	            document.getElementById("editName").value=editName;
	             submitForm();
	            
	            }else{
	            editPictureName();
	            }
	            })
	            }     	
      		}
      	});
    }
</script>

</head>
<body align="center">
	<form:form id="formSave" modelAttribute="topoGraph"
		action="${ctx}/topo/graphManage/savebgPicture.do" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="action" value="${action}" />
		<input type="hidden" name="id" value="${topoGraph.graphId}" />
		<input type="hidden" name="width" id="width" value="" />
		<input type="hidden" name="height" id="height" value="" />
		<input type="hidden" name="editName" id="editName" value="" />


		<table align="center">



			<tr>
				<td align="right"><div class="save">背景图:</div>
				</td>
				<td><div class="save">
						<input id="bgPicture" name="picture" value="" type="file"
							ltype="file" class="validate[required,maxSize[32]] ip1"
							onchange="change('pic1','bgPicture',1)" />
					</div>
				</td>
				<td><div class="save">
						<input id="bnSave" type="button" value="保 存" class="l-button mg6"
							onclick="submitForm()" />
					</div>
				</td>

			</tr>
			<tr>
				<td align="right"><div class="bgPictureName">修改文件名:</div>
				</td>
				<td><div class="bgPictureName">
						<input id="editPicName" name="editPicName"
							value="请修改您的文件名,修改时请使用英文" type="text" ltype="text"
							class="validate[required,maxSize[32]] ip1"
							onchange="savePictureName()" />
					</div>
				</td>
				<td><div class="bgPictureName">
						<input id="" type="button" value="确定" class="l-button mg6"
							onclick="savePictureName()" />
					</div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="center"><div class="load1">预览:</div>
				</td>
				<td></td>
			</tr>

			<tr>
				<td align="center" colspan=3>
					<div class="load1">
						<div>
							<img src="" id="pic1" style="width:100%;height:auto;">
						</div>
					</div></td>

			</tr>

			<tr>
				<td align="center" colspan=3>
					<div class="load1">
						<div>
							<input type="button" value="删除"
								onclick="delPic('pic1','bgPicture',1)">
						</div>
					</div></td>

			</tr>
		</table>
	</form:form>
</body>



</html>

