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
<title>交换机搜索</title>
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
                height:'280px',
                usePager:false,
                checkbox: false,
                onSelectRow: function (data, index, dom){
                	selectRowData = data;
                } 
            });
            
		//$("#formSearch").ligerForm({inputWidth:320});
		$("#formSearch").validationEngine({promptPosition: "centerRight"});	
		
//        $("#formSearch").click({
//         	dataType: 'json',
//         	beforeSubmit: function() {
//         		$("#bnSearch").attr("disabled","disabled");
// 				$("#trMsg").removeClass("dN");
//             	$("#trTxt").removeClass("dN");
//             	dataMain.Rows=null;
//             	loadData();
//         	},
//         	success: function(data) { 
//         		if( data.result == "success" ){
//         			searchMsg = "";
//         			定时器5秒显示
        			
//         		}
//         		else{
//            			$.ligerDialog.error(data.message);
//            			$("#bnSearch").removeAttr("disabled");
//            			$("#trTxt").addClass("dN");
//            			$("#trMsg").addClass("dN");
//         		}
//         	}
//         	timer=setInterval("showTopoLogs()",3000);//1000为1秒钟
//         }); 

        $("#bnParamCfg").click(function(){
        	var url = '${ctx}/system/param/group/itemValue.do?grpCode=netSearchParam'+'&oprType='+'switch';
        	window.parent.showDlg('修改搜索参数', 720, 420, timeURL(url));     
     	});

	});
	
	
		
	function searchDevice(){
       $.ajax({
			     type: "POST",
			     url: "${ctx}/fas/res/net/devices/scanQuery.do",
			     contentType: "application/x-www-form-urlencoded; charset=utf-8",
			     data: $("#formSearch").serialize(),
			     success: function (date) {
			              
			              },
			     });
       $("#trTxt").removeClass("dN");
       $("#showlogs").css("display","");
       timer=setInterval("showTopoLogs()",2000);//1000为1秒钟
    }
	
	
	function getSearchMsg() 
	{
		$.ajax({
			url: timeURL("${ctx}/fas/res/net/search/message.do"),
			dataType: 'json',
			success: function(data) {
				if( data.result){
					dataMain.Rows=data.result;
				 	loadData();
				}
				if( data.status == 'true' ){
					clearInterval(intvMsg);
           			$.ligerDialog.success('搜索完毕！');
           			$("#trTxt").addClass("dN");
           			$("#bnSearch").removeAttr( "disabled");
           			window.parent.parent.parent.saveOK();
				}
			}
		});        			
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
            window.parent.$.ligerDialog.success("设备搜索结束！");
            clearInterval(timer);
            $("#trTxt").addClass("dN");
            window.parent.editOK(); 
          }
			
        }
      });
      count++;
    }
	function getScrollTop() {
		document.getElementById("showlogs").scrollIntoView(false);
	} 
	//输入一个input另外一个input值改变
    function keydown(){
    	var value=$("#snmpRoKey").val();
  		$("#gatewayStr").val(value);
 	}
 	
 	function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
</script>

</head>
<body> 
	<form id="formSearch" action="${ctx}/fas/res/net/switch/scanQuery.do" method="post">
		<table id="tbSearch" align="center" class="tb_edit1">
            <tr>
                <td align="right">交换机IP地址：</td>
                <td colspan=4><input id="searchIp" style="width:200px" name="searchIp" value="" type="text" ltype="text" class="validate[required,custom[ipv4]] ip2" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">Snmp团体字：</td>
                <td colspan=4><input id="snmpRoKey" style="width:200px" name="snmpRoKey" onkeyup="keydown()" value="" type="text" ltype="text" class="validate[optional,maxSize[512]] ip2" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">缺省网关：</td>
                <td colspan=4><input id="gatewayIp" style="width:200px" name="gatewayIp" value="" type="text" ltype="text" class="validate[required,custom[ipv4]] ip2" />&nbsp;&nbsp;用来获取arp表信息</td>
                <td></td>
            </tr>
            <tr>
                <td align="right">ARP表获取方式：</td>
                <td colspan=4>
	                <input type="radio" name="arpGetMethod" id="snmp" value="snmp"  checked/>&nbsp;&nbsp;snmp&nbsp;&nbsp;&nbsp;&nbsp;
	                <input type="radio" name="arpGetMethod" id="telnet" value="telnet"  />&nbsp;&nbsp;telnet&nbsp;&nbsp;&nbsp;&nbsp;
	                <input type="radio" name="arpGetMethod" id="ssh" value="ssh"  />&nbsp;&nbsp;ssh&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
                <td ></td>
            </tr>
             <tr id="trGatewayStr" >
                <td align="right">网关团体字：</td>
                <td colspan=4><input id="gatewayStr" style="width:200px" name="gatewayStr" value="" type="text" ltype="text" class="ip2" /></td>
                <td></td>
            </tr>
            <tr>
           		<td align="right" colspan=3 >
           		<input id="bnSearch" type="button"  onclick="searchDevice()" value="搜索添加" class="l-button mg6" style="width:88px;"/>
           		</td>
           		<td align="left" colspan=3 >
           		<input id="bnParamCfg" type="button" value="搜索参数修改" class="l-button mg6" style="width:110px;"/>
           		</td>
            </tr>
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

