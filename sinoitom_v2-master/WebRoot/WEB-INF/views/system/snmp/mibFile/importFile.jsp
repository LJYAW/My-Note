<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>选择文件</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 

    <script type="text/javascript">
	var result = '${result}';
	var message = '${message}';
	var returnMap = '${returnMap}';
	var t1 = null;
        $(function (){            
    		$("#formImport").ligerForm({inputWidth:200});
    		$("#formImport").validationEngine({promptPosition: "centerRight"});	
			
            if( result=='success' && message!='' ){
				var fileNameListStr = message.substring(1, message.length-1);
				var fileNameListStrsplit = fileNameListStr.split(",");
				
				for(var i = 0;i<fileNameListStrsplit.length;i++){
					$("#fileNameList").append("<div style='margin-left: 36px;' id='fileName"+i+"'>"+fileNameListStrsplit[i]+"</div>");
				}
				
// 				alert(returnMap);
				
				t1=window.setInterval("refreshCount('"+fileNameListStrsplit+"','"+returnMap+"')", 1000);
				
            }
            if( result=='error' && message!='' ){
            	$.ligerDialog.error(message);
            }
            
//             $("#bnClose").click(function(){
//         		window.parent.closeDlg();
//         	});
        });
        
        var i = 0;
        function refreshCount(fileNameListStrsplit,returnMap) {
        	
//         	alert(returnMap);
        	var returnMapJson = JSON.parse(returnMap);
        
			fileNameListStrsplit = fileNameListStrsplit.split(",");
			$("#result").append(fileNameListStrsplit[i]+"解析成功，并存入数据库，oid信息如下:<br/>");
        	
//         	alert(fileNameListStrsplit[i].split(".")[0]);
			var list = returnMapJson[fileNameListStrsplit[i].split(".")[0].trim()];

// 			alert(fileNameListStrsplit[i]);
			var listJson = JSON.stringify(list);
// 			alert("listJson---"+listJson);
			
			var jsonObj = {};
			jsonObj.Rows = list;

			$("#result").append("<div id='maingrid"+i+"' style='clear:both'></div>");
			
			$("#maingrid"+i).ligerGrid({
                columns: 
                     [
                     { display:'nodeName', name:'nodeName',type:'string',align:'left',width:220},
		             { display:'oid', name:'oid',type:'string',align:'left',width:320,height:'auto'}
               	],
               	data: jsonObj,
				alternatingRow: 'true',
                width: '60%',
                height:'80%',
                usePager:false,
            });
            $("#result").append("<br/>");
//             alert(i);
			$("#fileName"+i).empty();
			i++;
			if(i>fileNameListStrsplit.length-1){
				window.clearInterval(t1);
			}
		}
		
		function getFileNames(){
			$("#fileNameList").empty();
			$("#result").empty();
			var obj = document.getElementById("impFile").files;
			fileNameList = obj;
		    for (var i = 0; i < obj.length; i++){
		    	$("#fileNameList").append("<div style='margin-left: 36px;' id='fileName"+i+"'>"+obj[i].name+"</div>");
		    }
		}
		
</script>
</head>
<body style="padding:0px;  "> 
  	<form id="formImport" action="${ctx}/snmp/mibNode/selectFile.do" method="post" enctype="multipart/form-data">
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
        <td align="right">选择文件:</td>
        <td colspan=4><input type="file" onchange="getFileNames()" id="impFile" name="impFile" size="76" multiple="multiple" class=" ip2" /></td>
        <td></td>
      </tr>
      <tr>
        <td align="right" colspan=3 >
          <input id="bnImport" type="submit" value="解析oid" style="width:80px;" class="l-button mg6" />
        </td>
<!--         <td align="left" colspan=3> -->
<!--         <input id="bnClose" type="button" value="关闭" class="l-button mg6" /> -->
<!--         </td> -->
      </tr>
    </table>
    
     <div id="fileNameList"></div>
     <hr/>
     <div id="result" style='margin-left: 36px;'></div>
      
    </form>
</body>
</html>

