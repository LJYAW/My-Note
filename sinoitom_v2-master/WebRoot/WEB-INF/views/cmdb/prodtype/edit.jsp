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
<title>机构编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="${ctx}/static/ligerUI/skins/Aqua/css/pspcss/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validationEngine/css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/ligerUI/js/ligerui-1.9.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validationEngine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validationEngine/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script> 
<script src="${ctx}/static/ligerUI/js/plugins/ligerListBox.js" type="text/javascript"></script>
<script src="${ctx}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function ()
	{			
	  
	     
		<c:if test="${result=='success'}">
	    var action = '${action}';
		var data = ${saveData};
		window.parent.parent.saveOK(action,data);
		window.parent.parent.closeDlg();
		</c:if>

		<c:if test="${result=='error'}">
		$.ligerDialog.error('${message}');
		</c:if>
	 refreshVendor();
	 
	  $("#listbox1,#listbox2").ligerListBox({
	  
                isShowCheckBox: false, 
                isMultiSelect: true,
                width:260,
                height:200
               
            });
	 
		$("#formSave input").filter(".ip1").ligerTextBox({width: 200});
 		$("input").filter(".ip2").ligerTextBox({ width: 553 });
 		$("#formSave").validationEngine({promptPosition: "centerRight"});	
 		
 	});
	
  	function changeDescp(){
  	  var codeID= $("#prodClassCode").val();
  	  var url = timeURL('${ctx}/cmdb/prodClass/getResItem.do?id=' + codeID);
		$.ajax({
			url : url,
			data : "date=" + new Date(),
			dataType : 'json',
			success : function(data) {
			var obj=data.result;
		    $("#description").val(obj.description);
			}
  	});
 } 	
 
 	function refreshVendor() {
          $.getJSON(
              "${ctx}/cmdb/prodType/getVendor.do?date="+new Date(),
              function (result) {
                  if (result.vendors != null) {
                      $("#vendorID").empty();
                      for (var i = 0; i < result.vendors.length; i++) {
                          var vendor = result.vendors[i];                  
                          if(vendor.vendorID=='${prodType.vendorID}'){
                           $("#vendorID").append("<option  value=\"" + vendor.vendorID + "\" selected>" + vendor.dispName + "</option>");
                          }else{
                           $("#vendorID").append("<option  value=\"" + vendor.vendorID + "\">" + vendor.dispName + "</option>");
                          }
                          
                          
                      }
                      refreshClassCode();
                  }
              });
  	}
  	
  	function refreshClassCode() {
  	
  	var vendorId=$("#vendorID").find("option:selected").val();
          $.getJSON(
              "${ctx}/cmdb/prodType/getCodeByVendor.do?vendorId="+vendorId,
              function (result) {
                  if (result.pClass != null) {
                      $("#prodClassID").empty();
                      for (var i = 0; i < result.pClass.length; i++) {
                          var pClass = result.pClass[i];
                          if('${prodType.prodClassCode}' == pClass.prodClassCode||'${pcCode}'==pClass.prodClassCode){
                              $("#prodClassID").append("<option  value=\"" + pClass.prodClassID + "\" classCode=\""+pClass.prodClassCode +"\"selected >" + pClass.prodClassName + "</option>");
                          }else{
                              $("#prodClassID").append("<option  value=\"" + pClass.prodClassID + "\" classCode=\""+pClass.prodClassCode +"\">" + pClass.prodClassName + "</option>");
                          }
                      }
                      changeDesc();
                      getResItems();
                      selectedProductType();
                  }
              });
  	}
  	
  	//给描述带值
  	function changeDesc(){
  	 	var desc=$("#prodClassID").find("option:selected").text();
  	 	var vendorId=$("#vendorID").find("option:selected").val();
  	    var prodClassId=$("#prodClassID").find("option:selected").val();
  	     var classId=$("#prodClassID").find("option:selected").attr("classCode");
  	 	$("#description").val(desc);
  	 	$("#prodClassName").val(desc);
  	 	$("#prodClassId").val(prodClassId);
  	 	$("#vendorId").val(vendorId);
  	 	$("#classId").val(classId);
  	}
  	
  	
  	//获取字典想内容
  	function getResItems(){
  	var vendorId=$("#vendorID").find("option:selected").val();
  	var classId=$("#prodClassID").find("option:selected").val();
  	var classCode=$("#prodClassID").find("option:selected").attr("classCode");
  	
  	var resName="";
  	
  	if(classCode=="11"){
  	   resName="IT_HostType";
  	}else if(classCode=="12"){
  	  resName="IT_StorageType";
  	}else if(classCode=="13"){
  	  resName="IT_NetDevType";
  	}else if(classCode=="14"){
  	  resName="IT_PeripheralType";
  	}else if(classCode=="21"){
  	  resName="IT_OSType";
  	}else if(classCode=="22"){
  	  resName="IT_SystemType";
  	}else if(classCode=="23"){
  	  resName="IT_DBType";
  	}else if(classCode=="24"){
  	  resName="IT_MiddleWareType";
  	}else if(classCode=="25"){
  	  resName="IT_AppType";
  	}
  	  
  	 var url = timeURL('${ctx}/cmdb/prodType/getResItem.do?groupCode=' + resName+'&vendorId='+vendorId+'&classId='+classId);
		$.ajax({
			url : url,
			data : "date=" + new Date(),
			dataType : 'json',
			success : function(data) {
			var obj=data.pItems;
			 liger.get("listbox2").clearContent();
		     liger.get("listbox2").setData(obj);
		     
			}
  	});
  	}
  	
  	
  	//获取已选产品类型
  	function selectedProductType(){
  	  var vendorId=$("#vendorID").find("option:selected").val();
  	  var classId=$("#prodClassID").find("option:selected").val();
  	 
  	  var url = timeURL('${ctx}/cmdb/prodType/selectedProductType.do?vendorId=' + vendorId+'&classId='+classId);
		$.ajax({
			url : url,
			data : "date=" + new Date(),
			dataType : 'json',
			success : function(data) {
			var obj=data.prodType;
			liger.get("listbox1").clearContent();
		    liger.get("listbox1").setData(obj);
		    if(obj==""){
	            liger.get("listbox1").data=null;
	        
		      }
			}
  	});
  	}
 
 
  
        function moveToLeft()
        {
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box2.getSelectedItems();
            if (!selecteds || !selecteds.length) return;
            box2.removeItems(selecteds);
            box1.addItems(selecteds);
        }
        function moveToRight()
        {
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box1.getSelectedItems();
            if (!selecteds || !selecteds.length) return;
            box1.removeItems(selecteds);
            box2.addItems(selecteds);
        }
        function moveAllToLeft()
        { 
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box2.data;
            if (!selecteds || !selecteds.length) return;
            box1.addItems(selecteds);
            box2.removeItems(selecteds); 
        }
        function moveAllToRight()
        { 
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box1.data;
            if (!selecteds || !selecteds.length) return;
            box2.addItems(selecteds);
            box1.removeItems(selecteds);
            
        }
        
 function sendform(){
	 var box2 = liger.get("listbox1");
	  var selecteds = box2.data;
	  if(selecteds!=''&&selecteds!=null){
		  $("#jsonStr").val(JSON.stringify(selecteds));
		  $("#formSave").submit();
	  }else{
	      window.parent.$.ligerDialog.warn("请添加关联项!");
	  }
	}	

</script>
<style type="text/css">
        .middle input {
            display: block;width:50px; margin-top:30px;
        }
        .middle2 input {
            display: block;width:50px; margin-top:55px;
           
   
        }
        
        
    </style>
</head>
<body> 
	<form:form id="formSave" action="${ctx}/cmdb/prodType/save.do" method="post">
	 <input type="hidden" name="jsonStr" id="jsonStr" value="">
	 <input type="hidden" name="vendorId" id="vendorId" value="">
	 <input type="hidden" name="classId" id="classId" value="">
	 <input type="hidden" name="prodClassId" id="prodClassId" value="">
	 
	 <input type="hidden" name="prodClassName" id="prodClassName" value="">
	 <input type="hidden" name="desc" id="desc" value="">
	 
	<input type="hidden" name="action" value="${action}"/>
	<input type="hidden" name="id" value="${id}"/>
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
                 <select id="vendorID" name="vendorID" style="width:202px" onchange="refreshClassCode()">
                    <option value="-1">请选择</option>
                 </select>
                </td>
                <td></td>
                <td align="right">产品分类 ：</td>
                <td>
                  <select id="prodClassID" name="prodClassID" style="width:202px" onchange="changeDesc();getResItems();selectedProductType();">
                      <option value="-1">请选择</option>
                  </select>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right">分类描述：</td>
                <td><input id="description" name="description" value="${prodClass.description}" type="text" ltype="text" class="validate[optional,maxSize[32]] ip1" readonly="readonly"/></td>
                <td colspan="4"></td>
            </tr>
            <tr>
            	<td>&nbsp;&nbsp;</td>
            </tr>
<!--             <tr> -->
<!--            		<td align="center" colspan=6 > -->
<!--            		<input id="bnSave" type="submit" value="保 存" class="l-button mg6" /> -->
<!--            		</td> -->
<!--             </tr> -->
		</table>
		<table>
		  <div style=" width:650px;margin:0 auto;padding-left:55px;"> 
		    <div>
		       <div style="margin:4px;float:left;">
			         <div align="center"><h4>已选产品类型</h4></div>
			         <div id="listbox1"  style="border:1px solid #CCC; overflow-Y:auto;font-size: 13px;"></div>  
			  </div>   
		  
		    
		       <div style="margin:4px;float:left;" class="middle">
			         <input type="button" onclick="moveToLeft()" value="<--" class="l-button bn2 mg6"/>
			          <input type="button" onclick="moveToRight()" value="-->" class="l-button bn2 mg6"/>
			          <input type="button" onclick="moveAllToLeft()" value="<<--" class="l-button bn2 mg6"/>
			         <input type="button" onclick="moveAllToRight()" value="-->>" class="l-button bn2 mg6"/>
			     </div>
		   
		       <div style="margin:4px;float:left;">
			         <div align="center"><h4>可选产品类型</h4></div>
			        <div id="listbox2" style="border:1px solid #CCC;overflow-Y:auto;font-size: 13px;"></div> 
			    </div>
			   
	  <div>
	  <div style="padding-top:260px;padding-left:273px;">
	    <input id="bnSave" type="button" onclick="sendform();" value="保 存" class="l-button bn2 mg6" />
	  <div>
  </div>
		</table>
    </form:form>
</body>
</html>

