var dlg = null;

function showDlg(title, width, height, url)
{
	dlg = $.ligerDialog.open({ title:title, width:width, height:height, url:url, isHidden:false, showMax: true, isResize: true });
}

function closeDlg()
{
	if( dlg ){
		//关闭页面刷新
		window.location.reload();
		dlg.close();
	}
}

function onlyCloseDlg(){//只关闭页面，不刷新
	if( dlg ){
		dlg.close();
	}
}

function saveOK(action, id)
{
	getActWin().saveOK(action, id); 
}

function doResult(data)
{
	getActWin().doResult(data); 
}

function loadData()
{
	getActWin().loadData(); 
}

function onAbout()
{
	var url = timeURL('about.do');
	window.showDlg('关于', 600, 400, url);
}

function onlineCheck() {
	$.ajax({
		url: timeURL("online.do"),
		dataType: 'json',
		success: function(data) {
			$("#onlineNum").text('在线用户数：'+data.onlineNum);			
		}
	});        			
}

function onDeadline() {
	$.ajax({
		url: timeURL("getDeadline.do"),
		dataType: 'json',
		success: function(data) {
			if(data.deadline!=null&&data.deadline!=''){
				if(parseInt(data.deadline)>0&&parseInt(data.deadline)<90){
					$("#deadlineWarn").css({display:''});
					$("#deadline").text(data.deadline+' 天').css({color:'red'});
				}else{
					$("#deadlineWarn").css({display:'none'});
				}
			}else{
				$("#deadlineWarn").css({display:'none'});
			}			
		}
	});        			
}
