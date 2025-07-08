<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>拓扑图编辑</title>
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
	{	
	    $(".load1").hide();	
	    $(".bgPictureName").hide();		
		<c:if test="${result=='success'}">
		window.parent.editOK();    
		window.parent.closeDlg();
		</c:if>
		var Time = "${topoGraph.createDate}";
// 		var str = format(Time,"yyyy-MM-dd HH:mm:ss");
// 		alert(str);
	    document.getElementById("createTime").value=Time;
	});

	
    function format(time, format){
         var t = new Date(time);
         var tf = function(i){
         return (i < 10 ? '0' : '') + i
         };
         return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
        switch(a){
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    })
}
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
	            $.ligerDialog.confirm('您上传的文件名已存在，是否替换？', function (yes){
	            if(yes){
	            editPictureName();
	            }
	            })
	            }     	
      		}
      	});
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
        if(window.FileReader){//chrome,firefox7+,opera,IE10,IE9，IE9也可以用滤镜来实现
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
            if (window.ie6) pic.src = reallocalpath; //IE6浏览器设置img的src为本地路径可以直接显示图片
            else { //非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现，IE10浏览器不支持滤镜，需要用FileReader来实现，所以注意判断FileReader先
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
     $(".bgPicture").hide();
     $(".save").hide();   
        var pic = document.getElementById("pic1");
        var file = document.getElementById("bgPicture");
        if(window.FileReader){//chrome,firefox7+,opera,IE10,IE9，IE9也可以用滤镜来实现
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
            if (window.ie6) pic.src = reallocalpath; //IE6浏览器设置img的src为本地路径可以直接显示图片
            else { //非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现，IE10浏览器不支持滤镜，需要用FileReader来实现，所以注意判断FileReader先
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
    $.ligerDialog.warn('暂不支持中文名称图片的上传<br/>请您先修改图片名称');
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
    function reseteditName(){
     window.location.reload();
    }
function checkFileExists(editName){
    $.ajax({
      	    type: "POST",
      	    url:'${ctx}/topo/graphManage/checkFileExists.do?editName='+editName,
      	    contentType: "application/x-www-form-urlencoded; charset=utf-8",
			  
      		success: function(data) {
      			if(data.result=='success'){
	             document.getElementById("editName").value=editName;
	             $(".save").show();
	            }
	            if(data.result=='error'){
	            $.ligerDialog.confirm('您上传的文件名已存在,是否覆盖？', function (yes){
	            if(yes){
	            document.getElementById("editName").value=editName;
	            $(".save").show();
	            
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
<body>
	<form:form id="formSave" modelAttribute="topoGraph"
		action="${ctx}/topo/graphManage/save.do" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="action" value="${action}" />
		<input type="hidden" name="id" value="${topoGraph.graphId}" />
		<input type="hidden" name="width" id="width" value="" />
		<input type="hidden" name="height" id="height" value="" />
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
				<td align="right">拓扑图名称:</td>
				<td><input id="graphName" name="graphName"
					value="${topoGraph.graphName}" type="text" ltype="text"
					class="validate[required,maxSize[32]] ip1" />
				</td>
				<td></td>
				<td align="right">种子节点:</td>
				<td><input id="seedNodeId" name="seedNodeIp"
					value="${topoGraph.seedNodeIp}" type="text" ltype="text"
					readonly="readonly" class="validate[required,maxSize[32]] ip1" />
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td align="right">拓扑图说明:</td>
				<td><input id="graphDesc" name="graphDesc"
					value="${topoGraph.graphDesc}" type="text" ltype="text"
					class="validate[required,maxSize[32]] ip1" />
				</td>
				<td></td>
				<td align="right"><div class="bgPicture">背景图:</div>
					<div class="bgPictureName">修改文件名:</div>
				</td>
				<td>
					<div class="bgPicture">
						<input id="bgPicture" name="picture" value="" type="file"
							ltype="file" class="validate[required,maxSize[32]] ip1"
							onchange="change('pic1','bgPicture',1)" />
					</div>
					<div class="bgPictureName">
						<input id="editPicName" name="editPicName"
							value="请修改您的文件名,修改时请使用英文" type="text" ltype="text"
							class="validate[required,maxSize[32]] ip1"
							onchange="savePictureName()" />

					</div></td>
				<td>
					<div class="bgPictureName">
						<input id="" type="button" value="确定" class="l-button mg6"
							onclick="savePictureName()" /> <input id="" type="button"
							value="取消" class="l-button mg6" onclick="reseteditName()" />
					</div>
				</td>
			</tr>
			<tr>
				<td align="right">创建人:</td>
				<td><input id="creator" name="creator"
					value="${topoGraph.creator}" type="text" ltype="text"
					readonly="readonly" class="validate[required,maxSize[32]] ip1" />
				</td>
				<td></td>
				<td align="right">创建时间:</td>
				<td><input id="createTime" name="time" value="" type="text"
					ltype="text" readonly="readonly"
					class="validate[required,maxSize[32]] ip1" />
				</td>
				<td></td>
			</tr>


			<tr>
				<td align="center" colspan=3>
					<div class="save">
						<input id="bnSave" type="button" value="保 存" class="l-button mg6"
							onclick="submitForm()" />
					</div></td>
				<td align="center" colspan=3>
					<div class="save">
						<input id="bnreset" type="reset" value="取 消" class="l-button mg6" />
					</div></td>
			</tr>

			<tr>



			</tr>
			<tr>
				<td align="center" colspan=6><div class="load1">
						<div>
							<img src="" id="pic1" style="width:100%;height:auto;">
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center" colspan=6>
					<div class="load1">
						<div>
							<input type="button" value="删除"
								onclick="delPic('pic1','bgPicture',1)" />
						</div>
					</div></td>

			</tr>
		</table>
	</form:form>
</body>
</html>

