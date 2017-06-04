var basepath = '/web/test/';

/**
 * 获取get参数
 * @Author   Maying
 * @DateTime 2017-04-22
 * @E-mail   1977905246@qq.com
 * @param    name 获取的key
 * @return   value
 */
function getParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return "";
}

/**
 * 跳转
 * @Author   Maying
 * @DateTime 2017-04-22
 * @E-mail   1977905246@qq.com
 * @param    obj uri 请求的参数
 * @return   直接跳转
 */
function u (uri) {
	var param = new Array();
	for (var i in uri.data) {
		param.push(i + '=' + escape(uri.data[i]));
	}
	var	href = basepath+uri.url+'?' + param.join('&');
	window.location.href = href;

}
$("#1").click(function(){
	var id=parseInt(getParam("id"));
	u({
		url:"douban.html",
		data:{
			id:id,
		}
	});
})
$("#2").click(function(){
	var id=parseInt(getParam("id"));
	u({
		url:"sort.html",
		data:{
			id:id,
		}
	});
})
$("#3").click(function(){
	var id=parseInt(getParam("id"));
	var x="NaN";
	if(id==x){
		u({
		url:"shop.html",
		data:{'id':id},
	});
	}else{
		u({
		url:"shopcar.html",
		data:{'id':id},
	});
	}
	
})
$("#4").click(function(){
	var id=parseInt(getParam("id"));
	u({
		url:"mine.html",
		data:{
			id:id,
		}
	});
})
	









