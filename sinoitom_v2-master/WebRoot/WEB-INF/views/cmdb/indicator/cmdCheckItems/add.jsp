<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="http://www.sino-bridge.com/jsp/common" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加设备指标采集命令解析规则</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link href="${ctx}/static/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet"
          type="text/css"/>
    <link href="${ctx}/css/common.css" rel="stylesheet" type="text/css"/>


    <link rel="stylesheet" href="${ctx }/css/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/ui.jqgrid.min.css"/>
    <link href="${ctx }/css/assets/css/jquery.toolbar.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="${ctx }/css/assets/css/layer.css"/>

    <script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
    <script src="${ctx }/static/assets/js/layer.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js"
            type="text/javascript" charset="utf-8"></script>
    <script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="${ctx}/js/common.js" type="text/javascript"></script>
    <script src="${ctx}/js/plugin/jquery.serializeObject.min.js" type="text/javascript"></script>

    <script src="${ctx }/static/assets/js/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="${ctx }/static/assets/js/grid.locale-zh.js" type="text/javascript"></script>
    <script src="${ctx }/static/assets/js/resize.js" type="text/javascript"></script>
    <script src="${ctx }/static/assets/js/bootbox.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            refreshVendor();
            changeParseMode();
            refreshIndClassName();
        });

        function saveAndContinue() {
            $.ajax({
                type: "POST",
                url: "${ctx}/cmdb/prodCmdCheckItems/saveProdCmdCheckItems.do",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: $("#formSave").serialize(),
                success: function (result) {
                    if (result === false) {
                        layer.alert("保存失败！", {icon: 2, time: 3000});
                    } else {
                        layer.alert("保存成功，请继续添加！", {icon: 1, time: 3000});
                    }
                },
                error: function () {
                    layer.alert("保存失败，请联系管理员！", {icon: 1, time: 3000})
                }
            });
        }

        function save() {
            $.ajax({
                type: "POST",
                url: "${ctx}/cmdb/prodCmdCheckItems/saveProdCmdCheckItems.do",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: $("#formSave").serialize(),
                success: function (result) {
                    if (result === false) {
                        layer.alert("保存失败！", {icon: 2, time: 3000});
                    } else {
                        window.parent.saveOK(true);
                    }
                },
                error: function () {
                    layer.alert("保存失败，请联系管理员！", {icon: 1, time: 3000})
                }
            });
        }

        //刷新厂商
        function refreshVendor() {
            $.getJSON(
                "${ctx}/cmdb/prodType/getVendor.do?date=" + new Date(),
                function (result) {
                    if (result.vendors != null) {
                        $("#vendorId").empty();
                        for (var i = 0; i < result.vendors.length; i++) {
                            var vendor = result.vendors[i];
                            if (vendor.vendorID == '${prodCmdCheckItems.vendorId}') {
                                $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\" selected>" + vendor.dispName + "</option>");
                            } else {
                                $("#vendorId").append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                            }

                        }
                        refreshClassCode();
                        refreshOsType();
                        refreshOsFeature();
                    }
                });
        }

        //产品分类
        function refreshClassCode() {
            var vendorId = $("#vendorId").find("option:selected").val();
            $.getJSON(
                "${ctx}/cmdb/prodType/getCodeByVendor.do?vendorId=" + vendorId,
                function (result) {
                    if (result.pClass != null) {
                        $("#devClassCode").empty();
                        for (var i = 0; i < result.pClass.length; i++) {
                            var pClass = result.pClass[i];
                            if (pClass.prodClassCode == '${prodCmdCheckItems.devClassCode}') {
                                $("#devClassCode").append("<option  value=\"" + pClass.prodClassCode + "\" prodClassID=\"" + pClass.prodClassID + "\" selected>" + pClass.prodClassName + "</option>");
                            } else {
                                $("#devClassCode").append("<option  value=\"" + pClass.prodClassCode + "\" prodClassID=\"" + pClass.prodClassID + "\">" + pClass.prodClassName + "</option>");
                            }

                        }
                        refreshProdType();
                        refreshClassName();
                        refreshOsFeature();
                    }
                });
        }

        //刷新分类名称
        function refreshClassName() {
            var devClassName = $("#devClassCode").find("option:selected").text();
            $("#devClassName").val(devClassName);
        }

        //产品类型
        function refreshProdType() {
            var vendorId = $("#vendorId").find("option:selected").val();
            var prodClassCode = $("#devClassCode").find("option:selected").val();
            $.getJSON(
                "${ctx}/cmdb/prodType/getProdTypeByVendorAndClassCode.do?vendorId=" + vendorId + "&prodClassCode=" + prodClassCode,
                function (result) {
                    if (result.prodType != null) {
                        $("#devTypeCode").empty();
                        for (var i = 0; i < result.prodType.length; i++) {
                            var prodType = result.prodType[i];
                            if (prodType.typeCode == '${prodCmdCheckItems.devTypeCode}') {
                                $("#devTypeCode").append("<option  value=\"" + prodType.typeCode + "\" typeId=\"" + prodType.prodTypeID + "\" selected>" + prodType.dispName + "</option>");
                            } else {
                                $("#devTypeCode").append("<option  value=\"" + prodType.typeCode + "\" typeId=\"" + prodType.prodTypeID + "\">" + prodType.dispName + "</option>");
                            }

                        }
                        //refreshProdSeries();
                        refreshIndGroup();
                        refreshProdModel();
                        refreshTypeName();
                        refreshOsFeature();
                    }
                });
        }

        //刷新类型名称
        function refreshTypeName() {
            var typeName = $("#devTypeCode").find("option:selected").text();
            $("#devTypeName").val(typeName);
        }

        //刷新产品型号
        function refreshProdModel() {
            var vendorId = $("#vendorId").find("option:selected").val();
            var prodClassCode = $("#devClassCode").find("option:selected").val();
            var prodTypeId = $("#devTypeCode").find("option:selected").attr("typeId");
            $.getJSON(
                "${ctx}/cmdb/vendorProdModel/getData.do?vendorId=" + vendorId + "&prodClassMode=" + prodClassCode + "&prodTypeId=" + prodTypeId,
                function (result) {
                    if (result.modelOIDlist != null) {
                        $("#prodModelId").empty();
                        $("#prodModelId").append("<option value=\"-1\" modelOID=\"X.X\" selected>所有型号</option>");
                        for (var i = 0; i < result.modelOIDlist.length; i++) {
                            var model = result.modelOIDlist[i];
                            if (model.prodModelID == '${prodCmdCheckItems.prodModelId}') {
                                $("#prodModelId").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.prodModelOid + "\" selected>" + model.prodModelName + "</option>");
                            } else {
                                $("#prodModelId").append("<option  value=\"" + model.prodModelID + "\" modelOID=\"" + model.prodModelOid + "\">" + model.prodModelName + "</option>");
                            }

                        }
                        refreshModelName();
                        refreshOsFeature();
                    }
                });
        }

        //刷新型号名称
        function refreshModelName() {
            var prodModelId = $("#prodModelId").find("option:selected").val();
            if (prodModelId == "-1") {
                $("#prodModel").val("所有型号");
                $("#modelOID").val("X.X");
            } else {
                var prodModel = $("#prodModelId").find("option:selected").text();
                $("#prodModel").val(prodModel);
                var modelOID = $("#prodModelId").find("option:selected").attr("modelOID");
                $("#modelOID").val(modelOID);
            }

        }


        //根据厂商名称  查   操作系统类型
        function refreshOsType() {
            var vendorID = $("#vendorId").find("option:selected").val();
            $.getJSON(
                "${ctx}/cmdb/vendorOSVersion/getOSNameById.do?vendorID=" + vendorID,
                function (result) {
                    if (result.vendorOSVersionlist != null) {

                        $("#osType").empty();
                        for (var i = 0; i < result.vendorOSVersionlist.length; i++) {
                            var osType = result.vendorOSVersionlist[i];
                            if (osType.osName == '${prodCmdCheckItems.osType}') {
                                $("#osType").append("<option  value=\"" + osType.osType + "\" selected>" + osType.osType + "</option>");
                            } else {
                                $("#osType").append("<option  value=\"" + osType.osType + "\" >" + osType.osType + "</option>");
                            }

                        }
                    }
                    refreshOsVersion();
                    refreshOsFeature();
                });
        }


        //根据厂商名称和操作系统类型   查 软件版本
        function refreshOsVersion() {
            var vendorId = $("#vendorId").find("option:selected").val();
            var osType = $("#osType").find("option:selected").val();
            $.getJSON(
                "${ctx}/cmdb/vendorOSVersion/getOsVersionByVendorIdAndOsType.do?vendorID=" + vendorId + "&osName=" + osType,
                function (result) {
                    if (result.list != null) {
                        $("#osVersion").empty();
                        $("#osVersion").append("<option value=\"V.R\" selected>V.R(所有版本)</option>");
                        for (var i = 0; i < result.list.length; i++) {
                            var prodType = result.list[i];
                            if (prodType.osVersion == '${prodCmdCheckItems.osVersion}') {
                                $("#osVersion").append("<option  value=\"" + prodType.osVersion + "\" typeId=\"" + prodType.osVersion + "\" selected>" + prodType.osVersion + "</option>");
                            } else {
                                $("#osVersion").append("<option  value=\"" + prodType.osVersion + "\" typeId=\"" + prodType.osVersion + "\">" + prodType.osVersion + "</option>");
                            }

                        }
                    }
                    refreshOsFeature();
                });

        }


        function refreshOsFeature() {
            var vendorId = $("#vendorId").find("option:selected").val();
            var devClassCode = $("#devClassCode").find("option:selected").val();
            var devTypeCode = $("#devTypeCode").find("option:selected").val();
            var osType = $("#osType").find("option:selected").val();
            var osVersion = $("#osVersion").find("option:selected").val();
            var modelOID = $("#prodModelId").find("option:selected").attr("modelOID");
            $.getJSON(
                "${ctx}/cmdb/prodChkCmds/getOsFeature.do?vendorId=" + vendorId + "&devClassCode=" + devClassCode + "&devTypeCode=" + devTypeCode + "&modelOID=" + modelOID + "&osType=" + osType + "&osVersion=" + osVersion,
                function (result) {
                    if (result.list != null) {
                        $("#osFeature").empty();
                        for (var i = 0; i < result.list.length; i++) {
                            var prodType = result.list[i];
                            if (prodType != null && prodType != "defined") {
                                var ressmple = prodType.resultSample.replace(/"/g, "");
                                if (prodType.checkCmd == '${prodCmdCheckItems.checkCmd}') {
                                    $("#osFeature").append("<option  value=\"" + prodType.osFeature + "\"  selected>" + prodType.osFeature + "</option>");
                                } else {
                                    $("#osFeature").append("<option  value=\"" + prodType.osFeature + "\" >" + prodType.osFeature + "</option>");
                                }
                            }
                            refreshCheckCmd();
                        }
                    }
                });
        }


        //获取采集命令下拉框
        function refreshCheckCmd() {
            var vendorId = $("#vendorId").find("option:selected").val();
            var devClassCode = $("#devClassCode").find("option:selected").val();
            var devTypeCode = $("#devTypeCode").find("option:selected").val();
            var osType = $("#osType").find("option:selected").val();
            var osVersion = $("#osVersion").find("option:selected").val();
            var modelOID = $("#prodModelId").find("option:selected").attr("modelOID");
            var osFeature = $("#osFeature").find("option:selected").val();
            $.getJSON(
                "${ctx}/cmdb/prodChkCmds/getCheckCmdAndCmdUsage.do?vendorId=" + vendorId + "&devClassCode=" + devClassCode + "&devTypeCode=" + devTypeCode + "&modelOID=" + modelOID + "&osType=" + osType + "&osVersion=" + osVersion + "&osFeature=" + osFeature,
                function (result) {
                    if (result.list != null) {
                        $("#checkCmd").empty();
                        for (var i = 0; i < result.list.length; i++) {
                            var prodType = result.list[i];
                            if (prodType != null && prodType != "defined") {
                                var ressmple = prodType.resultSample.replace(/"/g, "");
                                if (prodType.checkCmd == '${prodCmdCheckItems.checkCmd}') {
                                    $("#checkCmd").append("<option  value=\"" + prodType.checkCmd + "\" resultSample=\"" + ressmple + "\" prodChkCmdId=\"" + prodType.prodChkCmdId + "\" selected>" + prodType.checkCmd + "</option>");
                                } else {
                                    $("#checkCmd").append("<option  value=\"" + prodType.checkCmd + "\" resultSample=\"" + ressmple + "\" prodChkCmdId=\"" + prodType.prodChkCmdId + "\">" + prodType.checkCmd + "</option>");
                                }
                            }

                        }

                    }
                    refreshCmdUsage();
                });

        }

        function refreshCmdUsage() {

            var resultSample = $("#checkCmd").find("option:selected").attr("resultSample");
            $("#resultSample").val(resultSample);
            var prodChkCmdId = $("#checkCmd").find("option:selected").attr("prodChkCmdId");
            $("#prodChkCmdId").val(prodChkCmdId);
        }

        //刷新指标组
        function refreshIndGroup() {
            var devClassCode = $("#devClassCode").find("option:selected").val();
            var devTypeCode = $("#devTypeCode").find("option:selected").val();
            var indClassCode = $("#indClassCode").find("option:selected").val();
            $.getJSON(
                "${ctx}/cmdb/prodCmdCheckItems/getIndGroup.do?devClassCode=" + devClassCode + "&devTypeCode=" + devTypeCode + "&indClassCode=" + indClassCode,
                function (result) {
                    if (result.indGroups != null) {
                        $("#indGroupId").empty();
                        for (var i = 0; i < result.indGroups.length; i++) {
                            var group = result.indGroups[i];
                            if (group.indGroupID == '${items.indGroupID}') {
                                $("#indGroupId").append("<option  value=\"" + group.indGroupID + "\" selected>" + group.indGroupName + "</option>");
                            } else {
                                $("#indGroupId").append("<option  value=\"" + group.indGroupID + "\">" + group.indGroupName + "</option>");
                            }

                        }
                        refreshIndGroupName();
                        refreshIndItems();
                    }
                });
        }

        //刷新指标项
        function refreshIndItems() {
            var devClassCode = $("#devClassCode").find("option:selected").val();
            var devTypeCode = $("#devTypeCode").find("option:selected").val();
            var indClassCode = $("#indClassCode").find("option:selected").val();
            var indGroupID = $("#indGroupId").find("option:selected").val();
            $.getJSON(
                "${ctx}/cmdb/prodCmdCheckItems/getIndItems.do?devClassCode=" + devClassCode + "&devTypeCode=" + devTypeCode + "&indClassCode=" + indClassCode + "&indGroupID=" + indGroupID,
                function (result) {
                    if (result.indItems != null) {
                        $("#indItemID").empty();
                        for (var i = 0; i < result.indItems.length; i++) {
                            var item = result.indItems[i];
                            if (item.indItemID == '${items.indItemID}') {
                                $("#indItemID").append("<option  value=\"" + item.indItemID + "\"  indItemName=\"" + item.indItemName + "\" remark=\"" + item.remark + "\" valueType=\"" + item.valueType + "\" measureUnit=\"" + item.measureUnit + "\" length=\"" + item.length + "\" decimals=\"" + item.decimals + "\"  selected>" + item.indicatorItem + "</option>");

                            } else {
                                $("#indItemID").append("<option  value=\"" + item.indItemID + "\"  indItemName=\"" + item.indItemName + "\" remark=\"" + item.remark + "\" valueType=\"" + item.valueType + "\" measureUnit=\"" + item.measureUnit + "\" length=\"" + item.length + "\" decimals=\"" + item.decimals + "\" >" + item.indicatorItem + "</option>");
                            }
                        }
                        refreshIndItem();
                        refreshIndItemProt();
                    }
                });
        }

        function refreshIndItem() {
            var indItemName = $("#indItemID").find("option:selected").attr("indItemName");
            $("#indItemName").val(indItemName);
            $("#indItemEnName").val($("#indItemID").find("option:selected").text());
        }

        function refreshIndItemProt() {

            $("#valueType").val($("#indItemID").find("option:selected").attr("valueType"));
            $("#measureUnit").val($("#indItemID").find("option:selected").attr("measureUnit"));
            $("#length").val($("#indItemID").find("option:selected").attr("length"));
            $("#decimals").val($("#indItemID").find("option:selected").attr("decimals"));
            $("#remark").val($("#indItemID").find("option:selected").attr("remark"));
        }

        //刷新指标组名称
        function refreshIndGroupName() {
            var groupName = $("#indGroupId").find("option:selected").text();
            $("#indGroupName").val(groupName);
        }

        function refreshIndClassName() {
            var indClassName = $("#indClassCode").find("option:selected").text();
            $("#indClassName").val(indClassName);
        }

        function getFormData($form) {
            var unindexed_array = $form.serializeArray();
            var indexed_array = {};

            $.map(unindexed_array, function (n, i) {
                indexed_array[n['name']] = n['value'];
            });

            return indexed_array;
        }

        //验证
        function checkResults() {
            var parseMode = Number($("#parseMode").val());
            if (parseMode === 4) {
                var valueType = $("#valueType").val();
                if (valueType !== "Array2D") {
                    layer.alert('模版解析使用的取值类型必须为Array2D，请选择其他解析方式！', {icon: 2, time: 3000});
                    return;
                }
            }
            $.ajax({
                type: "POST",
                url: "${ctx}/cmdb/prodCmdCheckItems/checkResult.do",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify($("#formSave").serializeObject()),
                success: function (data) {
                    var status = data.status;
                    if (parseInt(status) === 200) {
                        if (parseMode === 4 ) {
                            var map = data.result;
                            gridInit(map);
                        } else {
                            $("#checkResult").val(data.result);
                        }
                    } else {
                        layer.alert(data.message, {icon: 2, time: 3000});
                    }
                },
                error: function (res) {
                    console.info(res);
                }
            });
        }

        function gridInit(r) {
            $("#resultList").jqGrid("clearGridData");
            var names = [];
            var models = [];
            //此处因为数据源数组中的结构相同且不为空，直接遍历索引为0的数据即可
            var result_list = [];
            for (var key in r) {
                var inMap = r[key];
                var model = {"id": key};
                for (var k in inMap) {
                    model[k] = inMap[k];
                }
                result_list.push(model);
            }
            $.each(result_list[0], function (key, value) {
                names.push(key);
                models.push({
                    name: key,
                    index: key
                });
            });
            //创建jqGrid组件
            $("#resultList").jqGrid(
                {
                    datatype: "json",//请求数据返回的类型。可选json,xml,txt
                    colNames: names,//jqGrid的列显示名字
                    colModel: models,
                    rowNum: 10,//一页显示多少条
                    rowList: [10, 20, 30],//可供用户选择一页显示多少条
                    pager: '#resultPager',//表格页脚的占位符(一般是div)的id
                    sortname: 'id',//初始化的时候排序的字段
                    sortorder: "desc",//排序方式,可选desc,asc
                    viewrecords: true,
                    caption: "验证结果"//表格的标题名字,
                });
            $("#t_list").css({"height": 30});
            //将jqdata的值循环添加进jqGrid
            for (var i = 0; i <= result_list.length; i++) {
                $("#resultList").jqGrid('addRowData', i + 1, result_list[i]);
            }
        }

        function changeParseMode() {
            var parseMode = $("#parseMode").find("option:selected").val();
            if (parseMode === "1") {
                $(".regex").show();
                $(".targetKey").show();
                $(".stringMatch").hide();
                $(".stringSplit").hide();
                $(".template").hide();
                $(".verifySingleResult").show();
                $(".verifyMultiResult").hide();
            } else if (parseMode === "2") {
                $(".regex").hide();
                $(".targetKey").show();
                $(".stringMatch").show();
                $(".stringSplit").hide();
                $(".template").hide();
                $(".verifySingleResult").show();
                $(".verifyMultiResult").hide();
            } else if (parseMode === "3") {
                $(".regex").hide();
                $(".targetKey").show();
                $(".stringMatch").hide();
                $(".stringSplit").show();
                $(".template").hide();
                $(".verifySingleResult").show();
                $(".verifyMultiResult").hide();
            } else if (parseMode === "4") {
                $(".template").show();
                $(".targetKey").hide();
                $(".regex").hide();
                $(".stringMatch").hide();
                $(".stringSplit").hide();
                $(".verifySingleResult").hide();
                $(".verifyMultiResult").show();
            }
        }

    </script>

</head>
<body>


<form:form id="formSave" action="" method="post">
    <input type="hidden" id="indGroupName" name="indGroupName" value="">
    <input type="hidden" id="devClassName" name="devClassName" value=""/>
    <input type="hidden" id="devTypeName" name="devTypeName" value=""/>
    <input type="hidden" id="indClassName" name="indClassName" value=""/>

    <input type="hidden" id="indItemEnName" name="indItemEnName" value=""/>
    <input type="hidden" id="prodModel" name="prodModel" value=""/>
    <input type="hidden" id="modelOID" name="modelOID" value=""/>
    <input type="hidden" id="prodChkCmdId" name="prodChkCmdId" value=""/>
    <input type="hidden" id="status" name="status" value="0"/>
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
            <td align="right">厂商名称：</td>
            <td>
                <select id="vendorId" name="vendorId" style="width:202px"
                        onchange="refreshClassCode();refreshOsType();">
                    <option value="-1">请选择</option>
                </select>
            </td>
            <td></td>
            <td align="right">产品分类 ：</td>
            <td>
                <select id="devClassCode" name="devClassCode" style="width:202px"
                        onchange="refreshProdType();refreshClassName();">
                    <option value="-1">请选择</option>
                </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td align="right">产品类型：</td>
            <td>
                <select id="devTypeCode" name="devTypeCode" style="width:202px"
                        onchange=" refreshProdModel();refreshTypeName();refreshIndGroup()">
                    <option value="-1">请选择</option>
                </select>
            </td>
            <td></td>
            <td align="right">产品型号：</td>
            <td>
                <select id="prodModelId" name="prodModelId" style="width:202px" onchange="refreshModelName();">
                </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td align="right" nowrap>操作系统类型：</td>
            <td>
                <select id="osType" name="osType" style="width:202px" onchange="refreshOsVersion()">

                </select>
            </td>
            <td></td>
            <td align="right">软件版本：</td>
            <td>
                <select id="osVersion" name="osVersion" style="width:202px" onchange="refreshOsFeature()">
                </select>
            </td>
            <td></td>
        </tr>

        <tr>
            <td align="right">软件特征：</td>
            <td>
                <select id="osFeature" name="osFeature" style="width:202px" onchange="refreshCheckCmd()">
                </select>
            </td>
            <td></td>
        </tr>

        <tr>
            <td align="right">监测对象：</td>
            <td>
                <s:resSelect id="indClassCode" name="indClassCode" value="" code="Cmdb_IndicatorClass" ltype="select"
                             style="width:200px;" onchange="refreshIndGroup();refreshIndClassName()"/>
            </td>
            <td></td>

            <td align="right">指标组：</td>
            <td>
                <select id="indGroupId" name="indGroupId" style="width:200px;"
                        onchange="refreshIndItems();refreshIndGroupName();">
                </select>
            </td>
            <td></td>
        </tr>

        <tr>
            <td align="right">指标项：</td>
            <td>
                <select id="indItemID" name="indItemID" style="width:200px;"
                        onchange="refreshIndItemProt();refreshIndItem();">
                </select>
            </td>
            <td></td>
            <td align="right">指标名称：</td>
            <td><input id="indItemName" name="indItemName" value="" type="text" ltype="text" class="ip1"
                       readonly="readonly"/></td>
            <td></td>
        </tr>

        <tr>
            <td align="right">取值类型：</td>
            <td><input id="valueType" name="valueType" value="" type="text" ltype="text" class=" ip1"
                       readonly="readonly"/></td>
            <td></td>

            <td align="right">度量单位：</td>
            <td><input id="measureUnit" name="measureUnit" value="" type="text" ltype="text" class="ip1"
                       readonly="readonly"/></td>
            <td></td>
        </tr>

        <tr>
            <td align="right">取值长度：</td>
            <td><input id="length" name="length" value="" type="text" ltype="text" class="ip1" readonly="readonly"/>
            </td>
            <td></td>

            <td align="right">小数位数：</td>
            <td><input id="decimals" name="decimals" value="" type="text" ltype="text" class="ip1" readonly="readonly"/>
            </td>
            <td></td>
        </tr>
        <tr>
            <td align="right">指标说明：</td>
            <td colspan="4"><input id="remark" name="remark" value="" type="text" ltype="text" class="ip2"
                                   readonly="readonly"/></td>
            <td></td>
        </tr>
        <tr>
            <td align="right">采集命令：</td>
            <td colspan="4">
                <select id="checkCmd" name="checkCmd" style="width:550px;" onchange="refreshCmdUsage()">
                </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td align="right">结果样本：</td>
            <td colspan="4" style="overflow-y:hidden;overflow-x:hidden">
                <textarea style="width:553px;height:150px;" id="resultSample" name="resultSample" readonly></textarea>
            </td>
            <td></td>
        </tr>
        <tr>
            <td align="right" nowrap>指标解析方式：</td>
            <td>
                <select id="parseMode" name="parseMode" style="width:200px;" onchange="changeParseMode();">
                    <option value="1">正则表达式</option>
                    <option value="2">字符串匹配</option>
                    <option value="3">字符分割</option>
                    <option value="4">模版解析</option>
                </select>
            </td>
            <td></td>
            <td class="targetKey" align="right">目标行关键字：</td>
            <td class="targetKey">
                <input id="resultKeyWords" name="resultKeyWords" value="" type="text" ltype="text"
                       class="ip1"/>
            </td>
            <td></td>
        </tr>
        <tr class="template">
            <td align="right">空行分隔符：</td>
            <td>
                <input id="nullLineDelimiter" name="blankLineDelimiter" type="text" class="ip1"/>
            </td>
            <td></td>
            <td align="right">行分隔符：</td>
            <td><input id="lineDelimiter" name="lineDelimiter" value="" type="text" ltype="text" class="ip1"/></td>
            <td></td>
        </tr>
        <tr class="template">
            <td align="right">字段分隔符：</td>
            <td>
                <input id="fileDelimiter" name="fileDelimiter" type="text" ltype="text" class="ip1"/>
            </td>
            <td></td>
            <td align="right">key/value分隔符：</td>
            <td><input id="kvDelimiter" name="kvDelimiter" value="" type="text" ltype="text" class="ip1"/></td>
            <td></td>
        </tr>
        <tr class="regex">
            <td align="right">正则解析规则：</td>
            <td colspan="4" class="regex">
                <input id="regEx" name="regEx" value="" type="text" ltype="text" class="ip1"/>
            </td>
            <td></td>
        </tr>
        <tr class="regex">
            <td align="right">group序列号：</td>
            <td>
                <input id="regExGroupNum" name="regExGroupNum" value="1" type="text" class="ip1"/>
            </td>
            <td></td>

                <%--            <td align="right">测试结果：</td>--%>
                <%--            <td >--%>
                <%--                <input id="testResult" name="testResult" type="text" readonly/>--%>
                <%--                                <input type="button" onclick="test(this);" value="测试" class="l-button-multi-small"/>--%>
                <%--            </td>--%>
        </tr>
            <%--        <script type="text/javascript">--%>
            <%--            function test(t){--%>
            <%--                var prev = $(t).prev();--%>
            <%--                prev.val("123");--%>
            <%--            }--%>
            <%--        </script>--%>
        <tr class="stringMatch">
            <td align="right">开始关键字：</td>
            <td><input id="startKeyWords" name="startKeyWords" value="" type="text" ltype="text" class="ip1"/></td>
            <td></td>

            <td align="right">结束关键字：</td>
            <td><input id="endKeyWords" name="endKeyWords" value="" type="text" ltype="text" class="ip1"/></td>
            <td></td>
        </tr>
        <tr class="stringSplit">
            <td align="right">分隔符：</td>
            <td><input id="delimiter" name="delimiter" value="" type="text" ltype="text" class="ip1"/></td>
            <td></td>

            <td align="right">取值索引号：</td>
            <td><input id="valueIndex" name="valueIndex" value="" type="text" ltype="text" class="ip1"/></td>
            <td></td>
        </tr>
        <tr class="verifySingleResult">
            <td align="right">验证结果：</td>
            <td colspan="4" style="overflow-y:hidden;overflow-x:hidden">
                <input class="ip2" id="checkResult" readonly/>
            </td>
            <td></td>
        </tr>
    </table>
</form:form>
<div style="width: 100%;">
    <div class="verifyMultiResult">
        <table id="resultList" align="center" style="width: 700px;margin:0 0 0 50px;"></table>
        <div id="resultPager"></div>
    </div>
    <div style="height: 15px;"></div>
    <div class="show_td" align="center" style="height: 35px;">
        <button class="btn btn-info btn-bold" style="margin-right: 80px;" type="button" onclick="save();">
            保 存
        </button>
        <button class="btn btn-info btn-bold" style="margin-right: 40px;" type="button" onclick="saveAndContinue();">
            保存并继续添加
        </button>
        <button class="btn btn-warning btn-bold" style="margin-left: 40px;" type="button" onclick="checkResults();">
            验证
        </button>
    </div>
</div>
</body>
</html>

