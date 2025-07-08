/**
 * Created by Administrator on 2016/4/5.
 */
$(function(){
 
	
	 
});
function showPopup(w,h){
    var popUp = document.getElementById("topology");
    popUp.style.top = "0px";
    popUp.style.left = "1px";
    popUp.style.width = w + "px";
    popUp.style.height = h + "px";
//    popUp.style.visibility = "visible";
    popUp.style.display = "block";
}
$(function(){
    $('.topology_center_close').click(function(){
        hidePopup();

    });
});
function hidePopup(){
    var popUp = document.getElementById("topology");
//    popUp.style.visibility = "hidden";
    popUp.style.display = "none";
}


function getGraph(graphId){
	$.getJSON(
            "../../topo/graph/getGraphDatas.do?graphId="+graphId,
            function (result) {
                if (result.graphStr != null) {
                    $("#graphName").empty();
                    for (var i = 0; i < result.graphStr.length; i++) {
                    		var graph=result.graphStr[i];
                    		$("#graphName").append("<option  value=\"" + graph.graphId+"\">" + graph.graphName + "</option>");
                     }
                }
            });
}


function saveSubnetNode(graphId,selectGraphId,selectGraphName,subnetStr){
	 var params ={'subnetStr':subnetStr,'graphId':graphId,'selectGraphId':selectGraphId,'selectGraphName':selectGraphName};
	$.ajax({
		url:"../../topo/topoNode/saveSubnetNode.do",
		type:'post',         //数据发送方式
	    dataType:'json',     //接受数据格式
	    data:params,         //要传递的数据
		success:function(data){
			if(data.result=="success"){
				loadTopo();
			}
		}
	});
}


 
