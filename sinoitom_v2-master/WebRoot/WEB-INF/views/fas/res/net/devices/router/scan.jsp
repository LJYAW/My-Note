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
<title>搜索路由器</title>
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
<script src="${ctx}/js/common.js" type="text/javascript"></script>

<script type="text/javascript">
	var searchMsg = null;
	var intvMsg = null;
	var dataMain = {};
	var gridMain;
	var selectRowData = null;
	var timer;
	$(function ()
	{				
		dataMain.Rows =null;
		gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'设备IP', name:'deviceIp', width:100, align:'left' },
                    { display:'发现状态', name:'status', width:80, align:'left' },
                    { display:'设备名称', name:'deviceName', width:130, align:'left' },
                    { display:'设备类型', name:'deviceType', width:110, align:'left' },
                    { display:'版本', name:'deviceVersion', width:130, align:'left' },
                    { display:'接口数量', name:'interfaceNum', width:80, align:'left' },
                    { display:'发现过程', name:'discoveryType', width:110, align:'left' }
               		],  
				data: dataMain,
				alternatingRow: 'true',
                width: '100%', 
                height:'310px',
                usePager:false,
                checkbox: false,
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });
            
		$("#formSearch").ligerForm({inputWidth:520});
		$("#formSearch").validationEngine({promptPosition: "centerRight"});	
		
//         $("#formSearch").ajaxForm({
//         	dataType: 'json',
//         	success: function(data) { 
//         		if( data.result == "success" ){
//         			searchMsg = "";
//         			intvMsg = setInterval(getSearchMsg, 5000); 
//         		}
//         		else{
//            			$.ligerDialog.error(data.message);
//            			$("#bnSearch").removeAttr("disabled");
//            			$("#trTxt").addClass("dN");
//            			$("#trMsg").addClass("dN");
//         		}
//         	}
//         });
        

        $("#bnParamCfg").click(function(){
        	var url = '${ctx}/system/param/group/itemValue.do?grpCode=netSearchParam'+'&oprType='+'device';
        	window.parent.showDlg('修改搜索参数', 720, 420, timeURL(url));     
     	});

	});
	
// 	function getSearchMsg() 
// 	{
// 		$.ajax({
// 			url: timeURL("${ctx}/fas/res/net/search/message.do"),
// 			dataType: 'json',
// 			success: function(data) {
// 				if( data.result){
// 					dataMain.Rows=data.result;
// 				 	loadData();
// 				}
// 				if( data.status == 'true' ){
// 					clearInterval(intvMsg);
//            			$.ligerDialog.success('搜索完毕！');
//            			$("#trTxt").addClass("dN");
//            			$("#bnSearch").removeAttr( "disabled");
//            			window.parent.parent.saveOK();
// 				}
// 			}
// 		});        			
// 	}
	
	function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
     
      function searchDevice(){
       $.ajax({
			     type: "POST",
			     url: "${ctx}/fas/res/net/devices/routerScan.do",
			     contentType: "application/x-www-form-urlencoded; charset=utf-8",
			     data: $("#formSearch").serialize(),
			     success: function (date) {
			              
			              },
			     });
       $("#trTxt").removeClass("dN");
       $("#showlogs").html("");
       $("#showlogs").css("display","");
       timer=setInterval("showTopoLogs()",2000);//1000为1秒钟
    }    
        
    
    var flag=true;
    var count=0;
    var s=0;
    function showTopoLogs(){
      $.ajax({
        url:"${ctx}/fas/res/net/search/discoverDerviceOutputLogs.do",
        dataType:"json",
        success:function(data){
          var obj=data.outputstr;
//           var status=data.status;
  		  var str = JSON.stringify(obj) ;
		  str=str.replace(new RegExp("\"",'g'),"");
  		  if(str.length==0){
//   		      str="...";
  		      logStr+=str;
  		  }else{
  		     var logStr="设备搜索开始.......";
  		      logStr+="</br>"+str;
  		      $("#showlogs").html(logStr);
  		  }
  	   
          
          $("#showlogs").html(logStr);
          getScrollTop();
           if(logStr.indexOf("设备搜索结束")>0){  //搜索完成
             $.ligerDialog.success('设备搜索完毕！');
            clearInterval(timer);
            $("#trTxt").addClass("dN");
          }
			
        }
      });
      count++;
    }
	function getScrollTop() {
		document.getElementById("showlogs").scrollIntoView(false);
	}     
</script>

</head>
<body> 
	<form id="formSearch" action="${ctx}/fas/res/net/device/scan.do" method="post">
		<table id="tbSearch" align="center" class="tb_edit">
			<tr>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
				<td class="td_w1"></td>
				<td class="td_w2"></td>
				<td class="td_w3"></td>
			</tr>
            <tr>
                <td align="right">设备IP地址：</td>
                <td colspan=4><input id="searchIp" name="searchIp" value="" type="text" ltype="text" class="validate[required] ip2" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">snmp通讯串：</td>
                <td colspan=4><input id="snmpRoKey" name="snmpRoKey" value="" type="text" ltype="text" class="validate[optional,maxSize[512]] ip2" /></td>
                <td></td>
            </tr>
            <tr>
           		<td align="right" colspan=3 >
           		<input id="bnSearch" type="button" value="搜索添加"  onclick="searchDevice()" class="l-button mg6" style="width:88px;"/>
           		</td>
           		<td align="left" colspan=3 >
           		<input id="bnParamCfg" type="button" value="搜索参数修改" class="l-button mg6" style="width:110px;"/>
           		</td>
            </tr>
             <tr>
            	<td class="td_w1 hR vT">说明：</td>
           		<td colspan=5 class="td_w5">
           		搜索范围支持网段和单个IP搜索，多个搜索范围以分号","间隔;<br/>
           		网段支持10.1.1.0/24,10.1.1.0/255.255.255.0,10.1.1.1-10.1.1.254三种格式;<br/>
           		snmp通讯串多个以分号","间隔，为空的话，系统会以缺省参数设置进行搜索。
           		</td>
            </tr>
            <tr><td></td></tr>
            <tr><td></td></tr>
            <tr><td></td></tr>
		</table>
		
		<div style="margin:0 auto;width:96%;">
		 <div id="showlogs" style="text-align: left;width:500px;height:auto;padding-left:15px;margin-left: auto;margin-right: auto;"></div>
			<div id="trTxt" class="dN" style="border:0px;height:31px;line-heigth:31px;text-align:center">
           		<span id="searching">
           			<img src="${ctx}/img/scaning.gif" alt="#" />&nbsp;&nbsp;搜索中...
           		</span>
            </div>
<!--             <div id="trMsg" class="dN" > -->
<!--             	<div id="maingrid" ></div>  -->
<!--             </div> -->
			
		</div>
    </form>
</body>
</html>

