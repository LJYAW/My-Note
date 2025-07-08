<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>关联指标</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

    <script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/dataFormat.js" type="text/javascript"></script>
    <script src="${ctx}/js/common.js" type="text/javascript"></script>
    <script src="${ctx}/js/mainTab.js" type="text/javascript"></script>
    <%-- <script src="${ctx}/static/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script> --%>
    <script src="${ctx}/js/index.js" type="text/javascript"></script>

    <script type="text/javascript">
    	var dataMain = {};
    	var gridMain;
        var selectRowData = null;
    	var cmdManager;
    	var snmpManager;

        $(function (){
            <c:if test="${result=='success'}">
                window.parent.saveOK();
            </c:if>
        });

        function getCmdMaingrid(snmpJsonListData){
            dataMain.Rows = snmpJsonListData;
            cmdManager=gridMain = $("#cmdMaingrid").ligerGrid({
                columns:
                    [
                        { display:'<span style="font-weight:bold; ">id</span>', name:'id', width:80,hide: true,type:'text' },
                        { display:'<span style="font-weight:bold; ">设备厂商</span>', name:'vendorName', width:80,align:'center',type:'text' },
                        { display:'<span style="font-weight:bold; ">设备分类</span>', name:'resClassName', width:80,align:'center',type:'text' },
                        { display:'<span style="font-weight:bold; ">设备类型</span>', name:'resTypeName', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">设备型号</span>', name:'prodModel', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">OS类型</span>', name:'osType', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">版本分类</span>', name:'osVersion', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">软件特征</span>', name:'osFeature', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">指标组名称</span>', name:'indGroupName', width:80,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">指标英文名称</span>', name:'indItemEnName', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">指标显示名称</span>', name:'indItemName', width:150,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">取值类型</span>', name:'valueType', width:80,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">长度</span>', name:'length', width:100,align:'left',type:'text'},
                        { display:'<span style="font-weight:bold; ">小数位数</span>', name:'decimals', width:100,align:'left',type:'text'},
                        { display:'<span style="font-weight:bold; ">度量单位</span>', name:'measureUnit', width:100,align:'left',type:'text'},
                        { display:'<span style="font-weight:bold; ">命令ID</span>', name:'prodChkCmdId', width:100,align:'left',type:'text'},
                        { display:'<span style="font-weight:bold; ">采集命令</span>', name:'checkCmd', width:100,align:'left',type:'text'}
                    ],
                data: dataMain,
                alternatingRow: 'true',
                usePager:false,
                checkbox:true,
                width: '100%',
                height:'100%',
                pageSize: '50',
                pageSizeOptions: [25, 50, 100,200],
                onSelectRow: function (data, index, dom){
                    selectRowData = data;
                }
            });
        }

        function getSnmpMaingrid(jsonListData){
            dataMain.Rows =jsonListData;
            snmpManager=gridMain = $("#cmdMaingrid").ligerGrid({
                columns:
                    [
                        { display:'<span style="font-weight:bold; ">id</span>', name:'id', width:80,hide: true,type:'text' },
                        // { display:'<span style="font-weight:bold; ">设备厂商</span>', name:'vendorName', width:80,align:'center',type:'text' },
                        { display:'<span style="font-weight:bold; ">设备分类</span>', name:'resClassName', width:80,align:'center',type:'text' },
                        { display:'<span style="font-weight:bold; ">设备类型</span>', name:'resTypeName', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">设备型号</span>', name:'prodModel', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">OS类型</span>', name:'resTypeName', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">版本分类</span>', name:'resTypeName', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">软件特征</span>', name:'resTypeName', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">指标组</span>', name:'indGroupName', width:80,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">指标英文名称</span>', name:'indicatorItem', width:120,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">指标显示名称</span>', name:'indItemName', width:150,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">取值类型</span>', name:'valueType', width:80,align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">长度</span>', name:'length', width:100,align:'left',type:'text'},
                        { display:'<span style="font-weight:bold; ">小数位数</span>', name:'decimals', width:100,align:'left',type:'text'},
                        { display:'<span style="font-weight:bold; ">度量单位</span>', name:'measureUnit', width:100,align:'left',type:'text'},
                        { display:'<span style="font-weight:bold; ">Snmp对象</span>', name:'snmpObjName', width:100,align:'left',type:'text'},
                        { display:'<span style="font-weight:bold; ">SnmpOID</span>', name:'snmpOID', width:100, align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">获取方式</span>', name:'getMethod', width:100, align:'center',type:'text'},
                        { display:'<span style="font-weight:bold; ">OID类型</span>', name:'oidFlag', width:100, align:'center',
                            render: function (row){
                                var html='';
                                if(row.oidFlag=='1'){
                                    html = '<span style=\"align:certer;line-height:22px;color:green;\">直接获取</span>';
                                }else if(row.svrType=='2'){
                                    html = '<span style=\"align:certer;line-height:22px;color:red;\">计算获取</span>';
                                }else{
                                    html = '<span style=\"align:certer;line-height:22px;color:red;\">未知</span>';
                                }
                                return html;
                            }
                        }

                    ],
                data: dataMain,
                alternatingRow: 'true',
                usePager:false,
                checkbox:true,
                width: '100%',
                height:'100%',
                pageSize: '50',
                pageSizeOptions: [25, 50, 100,200],
                onSelectRow: function (data, index, dom){
                    selectRowData = data;
                }
            });
        }

        //筛选指标
        function getFilterInds() {
            var ids = "${ids}";
            var resClassCode = "${resClassCode}";
            var resTypeCode = "${resTypeCode}";

            var options = $("#indCollMode option:selected");
            var indCollMode = options.val();

            if(indCollMode!=null && indCollMode!='' && indCollMode!=undefined){
                if(indCollMode=='cmd'){
                    document.getElementById("cmdMaingrid").setAttribute("style","display");
                    document.getElementById("snmpMaingrid").setAttribute("style","none");
                }

                if(indCollMode=='snmp'){
                    document.getElementById("cmdMaingrid").setAttribute("style","none");
                    document.getElementById("snmpMaingrid").setAttribute("style","display");
                }

                $.ajax({
                    type: "get",
                    url: '${ctx}/bpm/taskMonRes/getFilterInds.do?ids='+ids+'&resClassCode='+resClassCode+'&resTypeCode='+resTypeCode+'&indCollMode='+indCollMode,
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (data) {
                        // alert(data);
                        if(indCollMode=='cmd' && data!=null && data!=undefined){

                            getCmdMaingrid(data);
                        }
                        if(indCollMode=='snmp' && data!=null && data!=undefined){

                            getSnmpMaingrid(data);
                        }
                    },
                    error: function (e) {
                        layer.msg('关联过程中出现错误！请检查！', {icon: 2, time: 1500},function(){onRefresh();});
                    }
                });
            }
        }

      //  刷新
      function onRefresh(){
          window.location.reload();
      }

   //   关联检测指标
   function saveform(){

       var options = $("#indCollMode option:selected");
       var indCollMode = options.val();
       var devData ;
       if(indCollMode!=null && indCollMode!='' && indCollMode!=undefined){
           if(indCollMode=='cmd'){
               devData = cmdManager.getSelectedRows();
               if(devData==null||devData==""){
                   $.ligerDialog.warn("请选择至少一个cmd指标解析!");
                   return;
               }
           }

           if(indCollMode=='snmp'){
               devData = snmpManager.getSelectedRows();
               if(devData==null||devData==""){
                   $.ligerDialog.warn("请选择至少一个snmp指标解析!");
                   return;
               }else{
                   for(var i = 0;i<devData.length;i++){
                       var snmpOID = devData[i].snmpOID;
                       if(snmpOID=='' || snmpOID==null || snmpOID==undefined){
                           $.ligerDialog.warn("所选项中有未定义指标的SnmpOID，请重新选择！");
                           return;
                       }
                   }
               }
           }
       }
      if(devData==null||devData==""){
        $.ligerDialog.warn("请选择至少一个指标解析!");
        return;
      }

       if(devData!=null && devData!=""&& devData.length>0){

           //判断是否选择了多种解析方式--一个指标只能选择一种
           var indItemNameArr=new Array(devData.length);
           for(var i = 0;i<devData.length;i++){
               var indItemName = devData[i].indItemName;
               indItemNameArr[i] = indItemName;
           }
           var flag = isRepeat(indItemNameArr);
           if(flag){//说明一个指标选择了多种解析方式--应该只选一个
               $.ligerDialog.warn("一个指标只能选择一种解析方式!");
               return;
           }

           var idss = getAllSelectId();
           $("#parseIds").val(idss);
       }
         $("#formSave").submit();
   }

   // 判断一个数组中是否有重复的值
        function isRepeat(arr){
            var hash = {};
            for(var i in arr) {
                if(hash[arr[i]]){ //hash 哈希
                    return true;
                }
                hash[arr[i]] = true;
            }
            return false;
        }

    </script>
</head>
<body style="padding:0px; overflow:hidden;" onload="">
	<form id="formSave" action="${ctx}/bpm/taskMonRes/resRelateInds.do" method="post">
	 <input type="hidden" name="ids" id="ids" value="${ids}"  />
	 <input type="hidden" name="resClassCode" id="resClassCode" value="${resClassCode}"  />
	 <input type="hidden" name="resTypeCode" id="resTypeCode" value="${resTypeCode}"  />
	 <input  type="hidden" name="parseIds" id="parseIds" value=""  />
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
                <td align="right" nowrap>指标采集方式 ：</td>
                <td>
                    <select id="indCollMode" name="indCollMode"  style="width:200px;">
                        <option value="cmd">cmd</option>
                        <option value="snmp">snmp</option>
                    </select>
                </td>
                <td></td>
                <td align="right"></td>
                <td>
                    <input id="filterInds" type="button" value="筛选指标" class="button mg6" onclick="getFilterInds()"/>
                </td>
                <td></td>
            </tr>

            <tr>
                <td>&nbsp;&nbsp;</td>
            </tr>

            <tr>
                <td align="center" colspan=6 >
                    <input id="bnSave" type="button" value="保存所选指标" onclick="saveform()" class="button mg6" />
                </td>
            </tr>
        </table>
	</form>
    <%--cmd--%>
	<%--<div id="toptoolbar"></div>--%>
    <div id="cmdMaingrid" style="display:none;"></div>

    <%--snmp--%>
    <%--<div id="indToptoolbar" style="display:none;"></div>--%>
    <div id="snmpMaingrid" style="display:none;"></div>
    
</body>
</html>