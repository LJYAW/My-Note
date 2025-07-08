/* 回车判断，跳转到指定焦点 */
function next_ctrl_onfocus(next_ctrl, evt) {
	var value = evt.keyCode;
	if (value == 13) {
		next_ctrl.focus();
		return false;
	}
	return true;
}

//给url加时间戳方法    
function timeURL(url){    
	//获取时间戳    
	var timestamp=(new Date()).valueOf();
	//将时间戳附加到url上    
	if(url.indexOf("?")>=0){
		url=url+"&t="+timestamp;
	}else{
		url=url+"?t="+timestamp;
	}
	return url;
}

function joinURL(url, param)
{
	if(url.indexOf("?")>=0){
		url += "&" + param;
	}else{
		url += "?" + param;
	}
	return url;
}

function linkMouseOver(link)
{
  if( link.className!="linkActive" )
  {
	  link.className = "linkActive";
  }
}

function linkMouseOut(link)
{
  if( link.className!="linkNormal" )
  {
	  link.className = "linkNormal";
  }
}

function ltrim(s){
	return s.replace( /^\s*/, "");
}

function rtrim(s){
	return s.replace( /\s*$/, "");
}
	
function trim(s){
	return rtrim(ltrim(s));
}
function trimall(s){
	return s.replace(/[ ]/g,""); 
}

function strFilter(s) 
{ 
	var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]")
	var rs = ""; 
	for (var i = 0; i < s.length; i++) { 
		rs = rs+s.substr(i, 1).replace(pattern,''); 
	} 
	return trimall(rs); 
} 


function isNullStr(str){ 
	if(typeof(str) == "undefined") return true;
	if(str==null) return true;
	if ( trim(str) == "" ) return true; 
	if ( trim(str) =="null") return true;
	var regu = "^[ ]+$"; 
	var re = new RegExp(regu); 
	return re.test(str); 
	
} 
	//自定义分页
	function emptyPage(){
		$("#pageSizeDef").val('');
		$("#pageDef").val('');
	$(".pageInput:first").val('1');
	var manager = $("#maingrid").ligerGetGridManager();     
		manager.changePageDef('inputDef');
	}	
	
	function swapShow(status){
		switch (status){
			case 'maingrid':{
				$("#emptyData").hide();
	        	$("#ajaxEmptyData").hide();
	        	$("#queryPrompt").hide();
	        	$("#tableHeader").show();
	        	$("#maingrid").show();
	        	$("#maingrid1").show();
			break;
			}
			case 'queryPrompt':{
				$("#emptyData").hide();
	        	$("#ajaxEmptyData").hide();
	        	$("#tableHeader").hide();
	        	$("#maingrid").hide();
	        	$("#maingrid1").hide();
	        	$("#queryPrompt").show();
			break;
			}
			case 'ajaxEmptyData':{
				$("#emptyData").hide();
	        	$("#queryPrompt").hide();
	        	$("#tableHeader").hide();
	        	$("#maingrid").hide();
	        	$("#maingrid1").hide();
	        	$("#ajaxEmptyData").show();
        break;
			}
			case 'emptyData':{
	        	$("#queryPrompt").hide();
	        	$("#tableHeader").hide();
	        	$("#maingrid").hide();
	        	$("#maingrid1").hide();
	        	$("#ajaxEmptyData").hide();
	        	$("#emptyData").show();
			break;
			}
		}
	}
function setBizProtocol(bizID,parprotocolId){
	if(parseInt(bizID)<5){
   		if(parseInt(bizID)==1){
   			$("#protid").val(6);
   			$("#protid").attr("disabled","disabled");
   		}
   		else if(parseInt(bizID)==2){
   			$("#protid").val(17);
   			$("#protid").attr("disabled","disabled");
   		}
   		else if(parseInt(bizID)==3){
   			$("#protid").val(1);
   			$("#protid").attr("disabled","disabled");
   		}
   		else if(parseInt(bizID)==4){
   			$("#protid").val(10000);
   			$("#protid").attr("disabled","disabled");
   		}
   		else{
   			$("#protid").removeAttr("disabled");
   		}
   	}else{
   		if(parseInt(parprotocolId)==6)
   			$("#protid").val(6);
   		else if(parseInt(parprotocolId)==17)
   			$("#protid").val(17);
   		else if(parseInt(parprotocolId)==1)
   			$("#protid").val(1);
   		else if(parseInt(parprotocolId)==10000)
   			$("#protid").val(10000);
   		else
   			$("#protid").removeAttr("disabled");
   	}
	
	if(!isNullStr(bizID)&&parseInt(bizID)>4){
   		$("#appportId").show();
   		$("#servicePort").hide();
   		$("#servicePort").val("");
   		$("#serverPort").hide();
   		$("#serverPort").val("");
   	}else{
   		$("#appportId").hide();
   		$("#appportId").val("");
   		$("#servicePort").show();
   		$("#serverPort").show();
   	}
}

function setFlowDir(dir){
	if(isNullStr(dir))
		$("#flowDir").val("inout");
	if(parseInt(parflowDir)==0||parseInt(parflowDir)==2)
		$("#flowDir").val("in");
	else if(parseInt(parflowDir)==1||parseInt(parflowDir)==3)
		$("#flowDir").val("out");
	else
		$("#flowDir").val("inout");
}

function setNetProtocol(protId){
	if(parseInt(protId)==6)
		$("#protid").val(6);
	else if(parseInt(protId)==17)
		$("#protid").val(17);
	else if(parseInt(protId)==1)
		$("#protid").val(1);
	else if(parseInt(protId)==10000)
		$("#protid").val(10000);
}

function _int2iP(num) 
{
	if(num==null||num==0)
		return '';
    var str;
    var tt = new Array();
    tt[0] = (num >> 24 & 0xFF);
    tt[1] = (num >> 16 & 0xFF);
    tt[2] = (num >> 8 & 0xFF);
    tt[3] = (num & 0xFF);
    str = String(tt[0]) + "." + String(tt[1]) + "." + String(tt[2]) + "." + String(tt[3]);
    return str;
}