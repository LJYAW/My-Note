<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>取消关联服务器 </title>

		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css" />
		<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
		
		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css" />
		<link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
		<link rel="stylesheet" href="${ctx }/css/assets/css/layer.css" />
		<!-- ace settings handler -->
		<script src="${ctx }/static/assets/js/ace-extra.min.js"></script>
		
		<script src="${ctx }/static/assets/js/jquery.min.js"></script>
		<script src="${ctx }/static/assets/js/layer.js"></script>
	    <script  src="${ctx }/static/assets/js/H-ui.admin.js"></script> 
		
		<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
		<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
		<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
		<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
		<script src="${ctx }/static/assets/js/resize.js"></script>

	</head>
    <script type="text/javascript">
    
    var clusterId = '${clusterId}';
        var gridHeight;		
        var dlgSearch=null;
		$(function(){ 
			 gridHeight= $(document).height()-120-$('#btn').height();
			pageInit();	
			resize();  //自动调整页面Grid	  
		 });
		 
		   function pageInit(){
			jQuery("#list").jqGrid( {
				data:  ${jsonListData},
            datatype: "local",  
          
    	    colModel : [ 
    	        	 {label:'服务器类型',name : 'hostType',index : 'hostType',width : 70,
    	        		 formatter:function(cellvalue, options, row){
    	        			 var html = '';
    	        			 if (row.hostType == "0") {
                                 html += '<span >虚拟机</span>';
                             } else if (row.hostType == "1") {
                                 html += '<span>物理机</span>';
                             } else if (row.hostType == "2") {
                                 html += '<span>宿主机</span>';
                             } else if (row.hostType == "3") {
                                 html += '<span>集群</span>';
                             } 
    	        			 return html;
    	        		 }	 
    	        	 },
    	        	 {label:'服务器名称',name : 'hostName',index : 'hostName',width : 100},
    	        	 {label:'服务器IP',name : 'ipAddress',index : 'ipAddress',width : 100
    	        	}], 
    	        	
    	        	 viewrecords: true,
    	        	 rowNum : 50, rowList : [ 50, 100, 150 ], 
    			 		pager : '#pager',
    			 		height:gridHeight,
    					altRows: true,
    					toolbar:[true,'top'],
    					multiselect: true,
    					multiboxonly: true,
    					loadComplete : function() {
    						var table = this;
    							setTimeout(function(){
    							updatePagerIcons(table);
    							}, 0);
    					},
    	        	});
    		jQuery("#list").jqGrid('navGrid', '#pager', {edit : false,add : false,del : false});
    							$("#t_list").css({"height":30});
    		            $("#btn").appendTo("#t_list");

    	} 

         // $("#bnSearch").click(f_bnSearch);
            $("#bnReset").click(function()
            {
            	window.$("input[ltype='text'][name*='filter_']").val("");
            	window.$("select[name*='filter_']").val("");
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
        
        function sendform(){
        	var ids = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
            if (ids==""||ids==null) { 
            	 layer.msg('请选择要取消关联的服务器!', {icon: 7, time: 1500});
            }else if(ids.length>0){
            	relationSave(ids);
            }
        }
        
        function relationSave(ids){
        	var url = '${ctx}/res/host/clusterHosts/cancelRelationSave.do?ids='+ids+'&&clusterId='+clusterId;
        	$.ajax({
        		url: url,
        		dataType: 'json',
        		success: function(data) {
        			if( data.result == 'success' ){
        				layer.msg('取消关联成功!',{icon:1, time: 1000},function(){
        					window.parent.saveOK();
            		        window.parent.closeDlg();
        	            });
        				
        			}else{
        				layer.msg('取消关联失败!',{icon:7, time: 1000},function(){
        					window.parent.saveOK();
            		        window.parent.closeDlg();
        	            });
        			}
        		}
        	});
        }
		
        
    </script>
<body style="overflow: hidden;">  

<input type="hidden" name="clusterId" value="${clusterId}"/>

<div>
	<div class="col-xs-12" style="overflow: hidden;position:relative;z-index:1;margin-top: -30px;">
	    <table id="list"></table>
	    <div id="pager"></div>
	</div>
	
	<div style="height: 10px;"></div>
	
	<div align="center" >
		<input id="bnSave" type="button" value="取消关联" onclick="sendform()" class="l-button mg6" />
	</div>
</div>
	
  </body>
</html>
