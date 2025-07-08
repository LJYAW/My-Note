<!DOCTYPE html>

<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap-datepicker3.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css"/>
    <link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>
	
	

		<!-- basic scripts -->
<script src="${ctx }/static/assets/js/jquery.min.js"></script>
<script src="${ctx }/static/assets/js/layer.js"></script>
<script src="${ctx }/static/assets/js/H-ui.admin.js"></script>

<script src="${ctx }/static/assets/js/jquery-2.1.4.min.js"></script>
<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
<!-- page specific plugin scripts -->
<!-- 		<script src="${ctx }/static/assets/js/bootstrap-datepicker.min.js"></script> -->
<script src="${ctx }/static/assets/js/jquery.jqGrid.min.js"></script>
<script src="${ctx }/static/assets/js/grid.locale-zh.js"></script>
<script src="${ctx }/static/assets/js/resize.js"></script>
<script src="${ctx }/static/assets/js/bootbox.js"></script>
    <style type="text/css">
        .middle {
            margin-top: 80px;
        }

        .middle div {
            display: block;
            width: 30px;
            height: 30px;
            cursor: pointer;
            margin-left: 25%;
            margin-top: 50%;

        }

        .middle .moveToLeft {
            background: url('${ctx}/img/moveToRight.png') no-repeat;
            background-size: 30px;
        }

        .jqgfirstrow td {
            padding: 0;
        }

    </style>
</head>

<script type="text/javascript">

var mydata;
var resId = '${id}';
var aa ;
var row2Ids;
    $(document).ready(function () {
        <c:if test="${result=='success'}">
            var rowData = '${rowData}';
            window.parent.saveOK(rowData);
        </c:if>

  refreshHostIp(); 
  //pageInit();
  pageInit1();
getData();
  
       
      

    });
    
    function gettext(mydata){
	   	jQuery('#jqGrid2').jqGrid('clearGridData');
	         jQuery('#jqGrid2').jqGrid(
	                'setGridParam',{  
	                datatype:'local',  
	       data:mydata
	   }).trigger('reloadGrid');
    }
    
    function getData(aa){
    	//alert(row2Ids);
    var hostIp;
     if(aa=="000"){
      hostIp="000";
     }else{
      hostIp=$("#hostIp").find("option:selected").val();
     }
//     alert(hostIp);
   		$.ajax({
   			async:false,
   			type:"post",
   			dataType:"json",
   			url:'${ctx}/fas/res/host/appService/getListByHostIp.do?hostIp='+hostIp+'&resId='+resId+'&row2Ids='+row2Ids,
   			success:function(data){
   				//data=JSON.stringify(data);
   				mydata=data;
   			}
   		});
   		// pageInit();
   		gettext(mydata);
   }
    //右边
    function pageInit(){
        $("#jqGrid2").jqGrid({
            datatype: "local",
            data : mydata,
            colModel: [
            		 {label:'hostIp',name :'hostIp',index : 'hostIp',width : 120},
               		 {label:'id',name : 'id',index : 'id',width : 50,hidden:true},
               		 {label:'协议号',name : 'protNo',index : 'protNo',width : 50},
    	        	 {label:'协议名称',name : 'protName',index : 'protName',width : 60},
    	        	 {label:'服务端口',name : 'svcPort',index : 'svcPort',width : 70},
    	        	 {label:'服务名称',name : 'svcName',index : 'svcName',width : 70},
    	        	 {label:'服务分类',name : 'svcClass',index : 'svcClass',width : 70,
    	        		 formatter:function(cellvalue, options, row){
    	        			 var html = '';
    	        			 if (row.svcClass == "1") {
                                 html += '<span>Web</span>';
                             } else if (row.svcClass == "2") {
                                 html += '<span>App</span>';
                             } else if (row.svcClass == "3") {
                                 html += '<span>DB</span>';
                             } 
    	        			 return html;
    	        		 }	
    	        	 },
    	        	 {label:'应用服务名称',name : 'appSvcName',index : 'appSvcName',width : 85},
            ],
            loadComplete: function () {
                var rows = jQuery("#jqGrid2").jqGrid("getRowData");
                jQuery("#jqGrid2").jqGrid('sortableRows');
                if (rows.length > 0) {
                    changeTitles("jqGrid2");
                    resize();
                }
            },
            rownumbers: true,
             multiselect: true,
            loadonce: true,
            width: 650,
            height: 500,
            rowNum: 10000,
            shrinkToFit: true,
            altRows: true,
            pager: "#jqGridPager2"
        });
        }
        
        //左边
         function pageInit1(){
           $("#jqGrid1").jqGrid({
             url: '${ctx}/fas/res/host/appService/getListByAppSvcId.do?resId='+resId,
            mtype: "post",
            datatype: "json",
            colModel: [
            		{label:'hostIp',name : 'hostIp',index : 'hostIp',width : 120},
            		{label:'id',name : 'id',index : 'id',width : 50,hidden:true},
                    {label:'协议号',name : 'protNo',index : 'protNo',width : 50},
    	        	 {label:'协议名称',name : 'protName',index : 'protName',width : 60},
    	        	 {label:'服务端口',name : 'svcPort',index : 'svcPort',width : 70},
    	        	 {label:'服务名称',name : 'svcName',index : 'svcName',width : 70},
    	        	 {label:'服务分类',name : 'svcClass',index : 'svcClass',width : 70,
    	        		 formatter:function(cellvalue, options, row){
    	        			 var html = '';
    	        			 if (row.svcClass == "1") {
                                 html += '<span>Web</span>';
                             } else if (row.svcClass == "2") {
                                 html += '<span>App</span>';
                             } else if (row.svcClass == "3") {
                                 html += '<span>DB</span>';
                             } 
    	        			 return html;
    	        		 }	
    	        	 },
    	        	 {label:'应用服务名称',name : 'appSvcName',index : 'appSvcName',width : 85},
            ],
            loadonce: true, //如果为ture则数据只从服务器端抓取一次，之后所有操作都是在客户端执行，翻页功能会被禁用
            viewrecords: true,
            multiselect: true,
            width: 600,
            height: 500,
            rowNum: 100,
            pager: "#jqGridPager1",
            loadComplete: function () {
                var rows = jQuery("#jqGrid1").jqGrid("getRowData");
                if (rows.length > 0) {
                    changeTitles("jqGrid1");
                    resize();
                }
            }
        });
//           jQuery("#jqGrid2").jqGrid('sortableRows');
         
         }
        
    //获取hostIp下拉框
 	function refreshHostIp() {
          $.getJSON(
              "${ctx}/fas/res/host/appService/getHostIp.do",
              function (result) {
                  if (result.list != null) {
                       $("#hostIp").append("<option value=\"-1\"  selected>请选择</option>");
                      for (var i = 0; i < result.list.length; i++) {
                          var list = result.list[i];                  
                           $("#hostIp").append("<option  value=\"" + list + "\">" + list + "</option>");
                          
                          
                      }
                      pageInit();
                  }
              });
  	}
    

</script>
<body>
<form:form id="formSave" action="${ctx}/res/biz/resAppServiceMap/saveAppService.do" method="post">
    <input type="hidden" name="bizAppId" id="bizAppId" value="${id}">
    <input type="hidden" name="rowData" id="rowData" value="">
    <input type="hidden" name="idList" id="idList" value="">
<!--     <input type="hidden" name="custReqId" id="custReqId" value="${custReqId}"> -->

    
    <table>
      <tr>
        <td>
        <h4>已关联服务</h4>
        <table id="jqGrid1"></table>
        </td>
        <td>  
	        <div style="width: 5%;float:left;" class="middle">
	            <div onclick="moveToLeft()" class="moveToLeft"></div>
	        </div>
        </td>
        <td>
         <h5>
			<select name="hostIp" id="hostIp" onchange="getData();">
			</select>
         </h5>
        <table id="jqGrid2"></table>
        </td>
      </tr>
      <tr align="right">
        <td>
             <div class="col-md-offset-5">
        <button type="button" class="l-button" style="display: inline-block;" onclick="submitForm()">
            <i class="ace-icon fa fa-check bigger-110"></i>
          		  关 联
        </button>
        &nbsp; &nbsp; &nbsp;
        <button type="button" class="l-button" style="display: inline-block;" onclick="parent.layer.closeAll();">
            <i class="ace-icon fa fa-undo bigger-110"></i>
           		 取 消
        </button>
    </div>
        </td>
      </tr>
    </table>
    
    <script type="text/javascript">

        function moveToLeft() {
        	
            var grid1 = $("#jqGrid1");//左侧
            var grid2 = $("#jqGrid2");//右侧
            var array = new Array();
            row2Ids = grid2.getGridParam("selarrrow");//右侧    选中的appSvcId
            var row1Ids = grid1.getDataIDs();//获取已关联服务的appSvcId   左侧
           
           
           var row3Ids = grid2.getDataIDs();//获取已关联服务的appSvcId   右侧
           
            
            
            
            
            //左侧push
            if(row1Ids.length > 0){
                for (var i = 0; i < row1Ids.length; i++) {
                    var rowData = grid1.getRowData(row1Ids[i]);
                    array.push({
                       	hostIp:rowData.hostIp,
                       	id:rowData.id,
                       	protNo:rowData.protNo,
                        protName:rowData.protName,
                        svcPort:rowData.svcPort,
                        svcName:rowData.svcName,
                        svcClass:rowData.svcClass,
                        appSvcName:rowData.appSvcName,

                    });
                }
            }
            if (row2Ids.length < 1)
                layer.msg('请选择服务信息', {icon: 2, time: 1200});
            else {
                var selectedIDs = grid2.getGridParam("selarrrow");
                
                
                
                for (var i = 0; i < selectedIDs.length;i++) {
                for (var j =0;j< row1Ids.length;  j++) {
                   if (selectedIDs[i]==(row1Ids[j])) {
                       selectedIDs.remove(i);
                   }
                }
            }
            
            
                for (var i = 0; i < selectedIDs.length; i++) {
                    var rowData = grid2.getRowData(selectedIDs[i]);
 						
                    array.push({
                    	hostIp:rowData.hostIp,
                        id:rowData.id,
                        protNo:rowData.protNo,
                        protName:rowData.protName,
                        svcPort:rowData.svcPort,
                        svcName:rowData.svcName,
                        svcClass:rowData.svcClass,
                        appSvcName:rowData.appSvcName,
                    });
                }
            }
            $("#jqGrid1").jqGrid('setGridParam', {
                dataType: 'json',
                data:array,
                loadComplete:function(){
                    var rows = jQuery("#jqGrid1").jqGrid("getRowData");
                   
                    if (rows.length > 0) {
                        changeTitles("jqGrid1");
                        resize();
                    }
                }
            }).trigger('reloadGrid');

            $("#jqGrid2").jqGrid("resetSelection");
            
            //you侧push
          var array1 = new Array();
          
                for (var i = 0; i < selectedIDs.length;i++) {
//                  alert("selectedIDs:"+selectedIDs[i]);
                for (var j =0;j< row3Ids.length;  j++) {
//                alert("row1Ids："+row1Ids[j]);
                   if (selectedIDs[i]==(row3Ids[j])) {
                       //selectedIDs.remove(i);
                       row3Ids.splice(j, 1);
//                        alert("剩余："+selectedIDs);
                   }
                }
            }
            
            
                for (var i = 0; i < row3Ids.length; i++) {
                    var rowData = grid2.getRowData(row3Ids[i]);
                    array1.push({
                     id:row3Ids[i],
                        protNo:rowData.protNo,
                        hostIp:rowData.hostIp,
                        protName:rowData.protName,
                        svcPort:rowData.svcPort,
                        svcName:rowData.svcName,
                        svcClass:rowData.svcClass,
                        appSvcName:rowData.appSvcName,
                    });
                }
                aa = "000";
                getData(aa);
                
            $("#jqGrid2").jqGrid('setGridParam', {
                dataType: 'json',
                data:array1,
                loadComplete:function(){
                    var rows = jQuery("#jqGrid2").jqGrid("getRowData");
                    if (rows.length > 0) {
                        changeTitles("jqGrid2");
                        resize();
                    }
                }
            }).trigger('reloadGrid');

            $("#jqGrid2").jqGrid("resetSelection");
            
            
            
            

        }

        function deleteRow(id) {
            layer.confirm('是否确认删除所选产品？', {
                btn: ['确认', '取消']
            }, function () {
                $('#jqGrid2').jqGrid('delRowData', id);
                layer.msg('删除成功', {icon: 6, time: 1200});
            }, function () {
                return;
            });
        }


        function submitForm() {
            var rowData = jQuery("#jqGrid1").jqGrid('getRowData');
            var row1Ids = jQuery("#jqGrid1").jqGrid('getDataIDs');
            rowData = JSON.stringify(rowData);
            $("#rowData").val(rowData);
            $("#idList").val(row1Ids);
           
            var length = strlen(rowData);
            if (length > 2) {
                $("#formSave").submit();
            } else {
                layer.msg('请先选择服务信息！', {icon: 2, time: 1200});
            }
        }

        function strlen(str) {
            var len = 0;
            for (var i = 0; i < str.length; i++) {
                var c = str.charCodeAt(i);
                //单字节加1
                if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                    len++;
                }
                else {
                    len += 2;
                }
            }
            return len;
        }


    </script>
 
    </form:form>
</body>
</html>

