<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>桌面设备</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${ctx}/static/ipv4lib.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script src="${ctx}/js/main.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var dataMain = {};
    	var gridMain;
        var selectRowData = null;
    	var dlgSearch = null;
    	var orgId='${orgId}';
    	var switchId='${switchId}';
    	if(switchId==null||switchId==""){
    		switchId=-1;
    	}
    	if(orgId==null||orgId==''){
    		orgId = '${LOGIN_USER_MAIN_ORGAN.orgId}';
    	}
    	var countA=0;
    	baseUrl='${ctx}/fas/res/dailyMaintain/desktopDevice';
    	var tempOrgId;
        $(function ()
        {
	        $("#orgTreeBox").ligerComboBox({
	        	width: 200, selectBoxWidth: 200, selectBoxHeight: 200, 
	        	textField: 'orgName',
	        	valueFieldID: 'orgId',
	        	treeLeafOnly: false,
	        	initValue: orgId,
	        	tree: { url: timeURL('${ctx}/system/organ/getMinData.do?treeCode=${LOGIN_USER_MAIN_ORGAN.treeCode}'), 
	        		textFieldName: 'orgName', checkbox: false },
		        onSelected: function (orgId)
		        {
		        	 if( orgId != null&&orgId != "" ){
					     if( countA>=1 ){
					        tempOrgId=orgId;
					     	getSwitchIp(orgId);
					     }
				         else{
				          countA++;
				        }
     		 		}
		        }
	        });
	        
            $("#toptoolbar").ligerToolBar({ 
				items: 
            		[
                	{ text:'添加设备', click: onView, icon:'add'},
                	{ line:true },
                	{ text:'更换设备', click: onSwapDevice, icon:'edit'},
                	{ line:true },
                	{ text:'更换位置', click: onSwapLocation, icon:'refresh' },
                	{ line:true },
                	{ text:'IP地址变更', click: onRefresh, icon:'refresh' },
                	{ line:true },
                	{ text:'Mac地址绑定', click: onBinds, icon:'refresh' },
                	{ line:true },
                	{ text:'Mac地址解绑', click: onUnbinds, icon:'refresh' },
                	{ line:true },
                	/* { text:'信息点匹配', click: onRefreshInfoNo, icon:'refresh' },
                	{ line:true }, */
                	{ text:'查询', click: onSearch, icon:'search' },
                	{ line:true },
                	{ text:'导出', click: onExport, icon:'export' },
                	{ line:true },     
                	{ text:'刷新', click: onRefresh, icon:'refresh' }
                	]
            });
            dataMain.Rows =null;
            gridMain = $("#maingrid").ligerGrid({
                columns: 
                    [
                    { display:'机构名称', name:'orgName', width:100, align:'left' },
                    { display:'使用人', name:'loginName', width:80, align:'left' },
                    { display:'设备类型', name:'ipHostType', width:90, align:'left' },
                    { display:'IP地址', name:'ipAddr', width:110, align:'left',type:'ip' },
                    { display:'MAC地址', name:'macAddr', width:120, align:'left' },
                    { display:'信息点编号', name:'infoNo', width:90, align:'right',type:'text',
                    	render: function (row)
						 	{  
						 	    if(isNullStr(row.infoNo))
						 			return ''
						 		else	
						 	    return '<span class="linkNormal" onmouseover="linkMouseOver(this);" onmouseout="linkMouseOut(this);" onclick="viewCablingInfo(\'' + row.infoPointNo + '\');">'+row.infoNo+'</span>';
						 	}
                     },
                    { display:'接入交换机', name:'switchIpLong', width:110, align:'left',
                      render:function(row){
                        return row.switchIp;
                      }
                     },
                     
                     { display:'switchIp', name:'switchIp', hide:true},
                     
                    { display:'交换机接口', name:'ipIndex', width:80, align:'left',
                    	render: function (row)
						 	{
						 	    return row.switchIfIndex;
						 	}
                    },
                     { display:'switchIfIndex', name:'switchIfIndex', hide:true},
                    { display:'Vlan', name:'vlanId', width:70, align:'center' },
                    { display:'Vlan名称', name:'vlanName', width:90, align:'center' },
                    { display:'接口状态',name:'adminStatus', width:110,
                     render: function (row)
	                        {
	                        	var html='';
	                        	if(row.adminStatus=='up'){
	                        		html = '<span style=\"line-height:22px;color:green;\">'+row.adminStatus+'</span>';
	                        	}else{
	                        		html = '<span style=\"line-height:22px;color:red;\">'+row.adminStatus+'</span>';
	                        	}
	                        	if(row.adminStatus!=''&&row.operStatus!='')
	                        	html = html+'/';
	                        	if(row.operStatus=='up'){
	                        		html =html +'<span style=\"line-height:22px;color:green;\">'+row.operStatus+'</span>';
	                        	}else{
	                        		html =html +'<span style=\"line-height:22px;color:red;\">'+row.operStatus+'</span>';
	                        	}
	                            return html;
	                        }
                    },
                      { display:'最后发现时间', name:'modify_Time', width:150, align:'center' }
               		],  
// 				data: dataMain,
// 				alternatingRow: 'true',
//                 width: '100%',
//                 height:'100%', 
//                 pageSize: '100',
//                 rownumbers:true,
//                 checkbox:true,
//                 isChecked: f_isChecked,
//                 onCheckRow: f_onCheckRow, 
//                 onCheckAllRow: f_onCheckAllRow,
//                 pageSizeOptions: [100, 200, 300, 500, 100],
//                 onSelectRow: function (data, index, dom){
//                 	selectRowData = data;
//                 }
		               
				    	url: timeURL(baseUrl+'/searchDeskPage.do?orgId='+orgId+'&switchId='+switchId),	
				    	sortName: 'orgName',sortOrder: 'asc',
						root: 'result', record: 'totalItems',
						pageParmName: 'pageNo', pagesizeParmName: 'pageSize', 
						sortnameParmName: 'orderBy', sortorderParmName: 'orderDir',
						checkbox: 'true', alternatingRow: 'true',
						width: '100%', height:'100%', 
						pageSize: '50', pageSizeOptions: [50, 100, 200, 500,1000],
						 rownumbers:true,
						isChecked: f_isChecked,
                       onCheckRow: f_onCheckRow, 
              		   onCheckAllRow: f_onCheckAllRow,
						onSelectRow : function(data, index, dom) {
								selectRowData = data;
							}
            });

           // getData();//初始化查询
            
//             $("#formSearch").ajaxForm({
//             	dataType: 'json',
//             	success: function(data) { 
//             	alert(data);
//            		dataMain.Rows = data;
// 				loadData();
//             	dlgSearch.hide();
//             	}
//             }); 
            
             $("#bnSearch").click(f_bnSearch);
            
            $("#bnReset").click(function()
            {
            	window.parent.$("input[ltype='text'][name*='filter_']").val("");
            	window.parent.$("select[name*='filter_']").val("");
            });
        });
        
        /*
       	 	实现 表单分页多选
        	即利用onCheckRow将选中的行记忆下来，并利用isChecked将记忆下来的行初始化选中
        */
        var checkedItem = [];
        function f_onCheckAllRow(checked)
        {
            for (var rowid in this.records)
            {
                if(checked)
                    addCheckedItem(this.records[rowid]['id']);
                else
                    removeCheckedItem(this.records[rowid]['id']);
            }
        }
        function findCheckedItem(ItemID)
        {
            for(var i =0;i<checkedItem.length;i++)
            {
                if(checkedItem[i] == ItemID) return i;
            }
            return -1;
        }
        function addCheckedItem(ItemID)
        {
            if(findCheckedItem(ItemID) == -1)
                checkedItem.push(ItemID);
        }
        function removeCheckedItem(ItemID)
        {
            var i = findCheckedItem(ItemID);
            if(i==-1) return;
            checkedItem.splice(i,1);
        }
        function f_isChecked(rowdata)
        {
            if (findCheckedItem(rowdata.id) == -1)
                return false;
            return true;
        }
        function f_onCheckRow(checked, data)
        {
            if (checked) addCheckedItem(data.id);
            else removeCheckedItem(data.id);
        }
        function f_getChecked()
        {
            alert(checkedItem.join(','));
        }
        
        
        function loadData(){
            selectRowData = null;
            gridMain.loadData();
        }
        
        
       //         导出
        function onExport(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.warn('请选择一行数据！');
        	}else{
				var row = gridMain.getSelectedRows();
				var rows = JSON.stringify(row);
//         		location.href='${ctx}/fas/res/dailyMaintain/desktopDevice/exportExcel.do?rows='+rows;
        		$("#rows").val(rows);
        		$("#exportForm").submit();
        	} 
        }
        
        function onView()
        {
            if (!selectRowData) { 
            	window.parent.$.ligerDialog.error('请选择一行数据！'); 
            	return; 
            }
            viewRow(selectRowData.id);
        }
        
        function viewRow(id)
        {
        	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/add.do?id='+id);
        	window.parent.showDlg('添加设备', 800, 350, url);
        }
		
        function saveOK(action, data)
        {
        	if( action=='add' ){
        		dataMain.Rows.push(data);
        		loadData();
        	}
        	else{
                for (var i = 0; i < dataMain.Rows.length; i++)
                {
                    if (dataMain.Rows[i].id == data.id){
                    	dataMain.Rows[i] = data;
                    	break;
                   }
                }
         		loadData();
        	}
        }
        
        function multiSaveOK(data)
        {
              for (var i = 0; i < dataMain.Rows.length; i++)
              {
              	for(var j=0;j<data.length;j++){
	                  if (dataMain.Rows[i].id == data[j].id){
	                  	dataMain.Rows[i] = data[j];
	                  	break;
	                 }
                 }
              }
       		loadData();
        }

        function onDelete()
        {
        	 if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一个IP Host设备！');
        	}else{
            	var ids = checkedItem.join(',');
            	deleteRow(ids);
        	} 
        }
        
        function deleteRow(ids)
        {
        	window.parent.$.ligerDialog.confirm('是否确认删除数据？', function (yes)
        	{
        		if(yes) delRow(ids);
        	});
        }
        
        function delRow(ids)
        {
        	var url = timeURL('${ctx}/fas/res/host/ipHost/delete.do?id='+ids);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			if( data.result == 'success' ){
        				onRefresh();
        			}
        		}
        	});
        }
        
		function isolationByOne(id,switchId,ifIndexId,flag){
			if(flag=='yes'){
				if(isNullStr(switchId)||isNullStr(ifIndexId)){
               		window.parent.$.ligerDialog.error('隔离失败！'); 
         			return; 
             	}else{
					window.parent.$.ligerDialog.confirm('是否确认隔离设备？', function (yes)
		        	{
		        		if(yes) isolationRow(id,switchId,ifIndexId,flag);
		        	});
	        	}
			}else{
				if(isNullStr(switchId)||isNullStr(ifIndexId)){
               		window.parent.$.ligerDialog.error('恢复失败！'); 
         			return;
            	}else{
					window.parent.$.ligerDialog.confirm('是否确认恢复隔离的设备？', function (yes)
		        	{
		        		if(yes) isolationRow(id,switchId,ifIndexId,flag);
		        	});
	        	}
			}
		}
		
		function isolationRow(ipHostIds,switchIds,ifIndexIds,flag){
			var url = timeURL('${ctx}/fas/res/host/ipHost/isolation.do?switchIds='+switchIds+'&ipHostIds='+ipHostIds+'&ifIndexIds='+ifIndexIds+'&flag='+flag);
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
       				if(!isNullStr(data.message)){
	                		window.parent.$.ligerDialog.error(data.message); 
           					return; 
	                	}
                      for (var i = 0; i < dataMain.Rows.length; i++)
		                {
		                  for(var j=0;j<data.listIphost.length;j++){
		                	if(data.listIphost[j].isoStatus=='1'&&flag=='yes'){
		                		window.parent.$.ligerDialog.error('隔离失败！'); 
            					return; 
		                	}
		                	else if(data.listIphost[j].isoStatus=='2'&&flag=='no'){
		                		window.parent.$.ligerDialog.error('恢复失败！'); 
            					return;
		                	}
		                    if (dataMain.Rows[i].id == data.listIphost[j].id){
		                    	if(data.listIphost[j].isoStatus=='2'&&flag=='yes'){
		                			window.parent.$.ligerDialog.success('隔离成功！');
			                	}
			                	else if(data.listIphost[j].isoStatus=='1'&&flag=='no'){
			                		window.parent.$.ligerDialog.success('恢复隔离成功！'); 
			                	}
		                    	dataMain.Rows[i] = data.listIphost[j];
		                    	break;
		                   	}
		                   }
		                }
             		loadData();
        		}
        	});
		}
		
		function onIsolation(){
			window.parent.$.ligerDialog.confirm('是否确认隔离设备？', function (yes)
        	{
        		if(yes) isolationRows(selectRowData.accessPathId);
        	});
		}
		
		function onRestore(){
			window.parent.$.ligerDialog.confirm('是否确认恢复隔离的设备？', function (yes)
        	{
        		if(yes) restoreRows(selectRowData.accessPathId);
        	});
		}
		
        function onSearch()
        {
        	if( dlgSearch ){
        		dlgSearch.show();
        	}
        	else{
        		$("#formSearch input[ltype='text']").ligerTextBox({inputWidth:200}); $("#formSearch input").filter(".ip2").ligerTextBox({ width: 553 });
            	dlgSearch = window.parent.$.ligerDialog.open({ title:'查询', width:760, height:300, target: $("#searchDlg") });
        	}
        }
        
        function onRefresh(){
        	window.location.reload();
        }
		
		function onSwapDevice(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一台设备！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行更换设备命令......</br>请稍候！！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/swapDevice.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			if( data.result == 'success' ){
                			window.parent.$.ligerDialog.success('更换设备成功！');
                			loadData();            				
            			}
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			}
            		}
            	});        			       		
        	}
        }
        
        function onSwapLocation(){
        	if( checkedItem.length == 0 ||checkedItem.length == 1){
        		window.parent.$.ligerDialog.error('请选择至少两台设备！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行更换位置命令......</br>请稍候！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/swapLocation.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			if( data.result == 'success' ){
                			window.parent.$.ligerDialog.success('更换位置成功！');
                			loadData();            				
            			}
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			}
            		}
            	});        			       		
        	}
        }
        
        function onBinds(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一个设备！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行绑定命令......</br>请稍候！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/binds.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			/* if( data.result == 'success' ){ */
               			//window.parent.$.ligerDialog.success(data.message);
               			$.ligerDialog.open({ height: 300, content:data.message, width: 620, showMax: true,isResize: true, slide: false }); 
               			loadData();         				
            			/* }
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			} */
            		}
            	});        			       		
        	}
        }
        
        function onUnbinds(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一个设备！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行解绑命令......</br>请稍候！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/unbinds.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			//if( data.result == 'success' ){
                		$.ligerDialog.open({ height: 300, content:data.message, width: 620, showMax: true,isResize: true, slide: false }); 
               			loadData();  
                		/* 	loadData();            				
            			}
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			} */
            		}
            	});        			       		
        	}
        }
        
          function onRefreshDev(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一个交换机！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在执行Snmp刷新命令......</br>请稍候！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/host/ipHost/reflesh.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			if( data.result == 'success' ){
                			window.parent.$.ligerDialog.success('Snmp刷新命令发送成功！'); 
                			loadData();           				
            			}
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			}
            		}
            	});        			       		
        	}
        }
        
        function getData()
        {
        	$("#maingrid").hide();
        	$("#pageloading").show();
        	var orgId=$("#orgId").val();
        	if(orgId==null||orgId==''){
        		orgId='${LOGIN_USER_MAIN_ORGAN.orgId}';
        	}
        	var sId=$("#switchIp").val();
        	var url = timeURL('${ctx}/fas/res/host/ipHost/getMainDataBy.do');
        	$.ajax({
        		url: url,
        		data: "orgId="+orgId+"&switchId="+sId+"&date="+new Date(),
        		dataType: 'json',
        		success: function(data) {
           			dataMain.Rows = data;
           			$("#maingrid").show();
           			$("#pageloading").hide();
           			loadData();
        		}
        	});        			
        };
        
        function viewCablingInfo(infoNo){
       		var url = timeURL('${ctx}/fas/res/cablingInfo/view.do?id='+infoNo);
       		window.parent.showDlg('信息点连接信息', 700, 320, url);
    	}
    	
    	function portManage(id,ip,macIp,ifIndex,switchIp){
       		var url = timeURL('${ctx}/fas/res/host/ipHost/macManage.do?ip='+ip+'&macIp='+macIp+'&ifIndex='+ifIndex+'&switchIp='+switchIp+'&id='+id);
       		window.parent.showDlg('端口安全', 700, 320, url);
		//window.open(url,'', 'height=300, width=724, top=160, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
    	}
    	
    	function getSwitchIpData(){
    	  getSwitchIp(tempOrgId);
    	}
    	
    	function getSwitchIp(orgId)
        {
            var checkBox=false;
            if($("#chargeRatio").attr('checked')==undefined){
    	         checkBox=false;
    	      }else{
    	        checkBox=true;
    	     }
    	     
    	     
        	var url = timeURL('${ctx}/fas/res/net/switch/getDataBySubOrg.do');
        	$.ajax({
        		url: url,
        		data: "orgId="+orgId+"&checkBox="+checkBox,
        		dataType: 'json',
        		success: function(data) {
        			$("#switchIp").empty();
       				 if(data.listData!=null&&data.listData!=''){
       				 	$("#switchIp").append("<option value='-1'>所有交换机IP</option>");
	        			 $.each(data.listData,function(i,value){
	        			 	if(switchId==value.switchId){
	        			 		$("#switchIp").append("<option value="+value.switchId+" selected>"+value.switchIp+"</option>");
	        			 	}else{
	        			 		$("#switchIp").append("<option value="+value.switchId+">"+value.switchIp+"</option>");
	        			 	}
	       				 });
       				 }else{
       				 	$("#switchIp").append("<option value='-1'>所有交换机IP</option>");
       				 }
        		}
        	});        			
        };
        
        function onRefreshInfoNo(){
        	if( checkedItem.length == 0 ){
        		window.parent.$.ligerDialog.error('请选择至少一个设备！');
        	}
        	else{
        		var loading= $.ligerDialog.loading('正在刷新信息点编号......</br>请稍候！');
            	var ids = checkedItem.join(',');
            	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/refreshInfoPoint.do?ids='+ids);
            	$.ajax({
            		url: url,
            		dataType: 'json',
            		success: function(data) {
            			loading.close();
            			if( data.result == 'success' ){
                			window.parent.$.ligerDialog.success('信息点编号刷新成功！');            				
            			}
            			else{
                    		window.parent.$.ligerDialog.error(data.message);
            			}
            		}
            	});        			       		
        	}
        }
        
        function onSnmpRefresh(){
           	var orgId=$("#orgId").val();
        	if(orgId==null||orgId==''){
        		orgId='${LOGIN_USER_MAIN_ORGAN.orgId}';
        	}
        	var sId=$("#switchIp").val();
           	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/reflesh.do?orgId='+orgId+'&switchId='+sId);
           	sRefresh();//调用loading数据
           	$.ajax({
           		url: url,
           		dataType: 'json',
           		success: function(data) {
           			if( data.result == 'success' ){
               			//window.parent.$.ligerDialog.success('刷新成功！');            				
           			}
           			else{
                   		window.parent.$.ligerDialog.error(data.message);
           			}
           		}
           	});        			       		
        }
        
        function onSetupMsg(){
        	var sId=$("#switchIp").val();
        	var sIp=$("#switchIp").find("option:selected").text();  
        	if(sId==null||sId==''||sId=='-1'){
        		alert("请选择交换机IP地址！");
        	}else{
//         		var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/setupMsg.do?switchIp='+sIp);
//         		window.open(url,'', 'height=400, width=760, top=160, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
        	var url = timeURL('${ctx}/fas/res/dailyMaintain/desktopDevice/setupMsgMain.do?switchIp='+sIp);
        	window.open(url,'', 'height=400, width=760, top=160, left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
        	
        	}
        }
        
        function sRefresh(){
        	var url = timeURL('${ctx}/fas/res/net/switch/sRefresh.do');
			window.open(url,'', 'height=400, width=760, top=160, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
        }
        
      function sendform(){
       var orgId=$("#orgId").val();
        	if(orgId==null||orgId==''){
        		orgId='${LOGIN_USER_MAIN_ORGAN.orgId}';
        	}
       var sId=$("#switchIp").val();
       $("#organId").val(orgId);
       $("#switchId").val(sId);
	  $("#search").submit();
	}	 
    </script>
  </head>
  
  <body style="padding:0px; overflow:hidden; ">
  <form id="search" name="search" action="${ctx}/fas/res/dailyMaintain/desktopDevice/searchMain.do" style="display:none" method="post">
   <input type="hidden" name="organId" id="organId" value="" />
   <input type="hidden" name="switchId" id="switchId" value="" />
  </form>
  	<form name="sysForm" id="sysForm">
		<div  style="height:36px;width:100%;background:#D6E3EF">
			<table style="height:28px;MARGIN-LEFT: 20px">
			<tr>
				<td>选择机构：</td>
				<td width="200px">
					 <input type="text" id="orgTreeBox"/>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				 <TD align=left>
					<INPUT type="checkbox" id="chargeRatio" name="chargeRatio" onclick="getSwitchIpData()"/>
				</TD>
				<TD align=left >&nbsp;包括下属机构</TD>
				
				<td align="right" width="90">交换机IP：</td>
				<td>
					<select id="switchIp" name="switchIp" style="width:160px">
						<option value="-1" selected>所有交换机IP</option>
					</select>
				</td>
				<td align="right" width="120px">
           		<input type="button" value="查询" class="l-button mg6" style="width:70px;" onclick="sendform()"/>
           		</td>
           		<td align="right" width="130px">
           		<input type="button" value="交换机刷新" class="l-button mg6" style="width:100px;" onclick="onSnmpRefresh()"/>
           		</td>
           		<td align="right" width="130px">
           		<input type="button" value="交换机配置" class="l-button mg6" style="width:100px;" onclick="onSetupMsg()"/>
           		</td>
			</tr>
		</table>
		</div>
	</form>
<!--   	<div class="l-loading" style="display:block" id="pageloading"></div> -->
    <div id="toptoolbar"></div> 
    <div id="maingrid"></div>
    <div style="display:none;">
    <div id="searchDlg" style="padding:0px;">
    	<form style="padding:0px;" id="formSearch" action="" method="post">
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
                <td align="right">所属机构:</td>
                <td><s:objSelect name="filter_EQS_orgId" objName="OrgOrganization" textAtt="orgName" valueAtt="orgId" treeCodeAtt="treeCode" prompt="全部" lclass="ip1"  ltype="select"/></td>
                <td></td>
                <td align="right">IP地址:</td>
                <td><input name="filter_LIKES_ipAddr" class="ip1" type="text" ltype="text"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">MAC地址:</td>
                <td><input name="filter_LIKES_macAddr" class="ip1" type="text" ltype="text"/></td>
                <td></td>
                <td align="right">信息点编号:</td>
                <td><input name="filter_LIKES_infoPointNo" class="ip1" type="text" ltype="text"/></td>
                <td></td>
            </tr>   
            <tr>
                <td colspan=3 align="right" style="padding:10px">
                <input id="bnSearch" type="submit" value="查 询" class="l-button" /> 
                </td>
                <td colspan=3 style="padding:10px">
                <input id="bnReset" type="button" value="重 置" class="l-button" />
                </td>
            </tr>   
        </table>
        </form>
    </div>
    </div>
    
    <form action="${ctx}/fas/res/dailyMaintain/desktopDevice/exportExcel.do" method="post" id="exportForm"  style="display:none">
     	<input type="hidden" name="rows" id="rows" value=""/>
    </form>
  </body>
</html>
