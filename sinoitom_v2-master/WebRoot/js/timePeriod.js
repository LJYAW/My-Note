function timePeriod(){
	var timeFrame = $("#timeFrame").val();
	var ajaxDis=$("#ajaxShowImg").css("display");
        if (timeFrame == "5Min"){
        		if(ajaxDis=="block"){
					$("#ajaxTimeGrid").empty();
					$("<option value='' selected>缺省</option><option value='30Second'>30秒</option><option value='1Min'>1分钟</option>").appendTo("#ajaxTimeGrid");
					}
				else if(ajaxDis=="none"){
					$("#timeGrid").empty();
			    	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Second')\" ><span>30秒</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('1Min')\" ><span>1分钟</span></a>").appendTo("#timeGrid");
					}
				else{
					$("#ajaxTimeGrid").empty();
					$("<option value='' selected>缺省</option><option value='30Second'>30秒</option><option value='1Min'>1分钟</option>").appendTo("#ajaxTimeGrid");
					$("#timeGrid").empty();
			    	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Second')\" ><span>30秒</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('1Min')\" ><span>1分钟</span></a>").appendTo("#timeGrid");
				}
	    }
	    if (timeFrame == "10Min" ){
		    	if(ajaxDis=="block"){
			    	$("#ajaxTimeGrid").empty();
			    	$("<option value='' selected>缺省</option><option value='1Min'>1分钟</option><option value='5Min'>5分钟</option>").appendTo("#ajaxTimeGrid");
		    	}
		    	else if(ajaxDis=="none"){
			    	$("#timeGrid").empty();
		        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('1Min')\" ><span>1分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>").appendTo("#timeGrid");
		    	}
		    	else{
		    		$("#ajaxTimeGrid").empty();
		    		$("<option value='' selected>缺省</option><option value='1Min'>1分钟</option><option value='5Min'>5分钟</option>").appendTo("#ajaxTimeGrid");
			    	$("#timeGrid").empty();
		        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('1Min')\" ><span>1分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>").appendTo("#timeGrid");
		    		
		    	}
        }
	    if (timeFrame == "30Min" ){
	    	if(ajaxDis=="block"){
		    	$("#ajaxTimeGrid").empty();
		    	$("<option value='' selected>缺省</option><option value='1Min'>1分钟</option><option value='5Min'>5分钟</option>").appendTo("#ajaxTimeGrid");
	    	}
	    	else if(ajaxDis=="none"){
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('1Min')\" ><span>1分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>").appendTo("#timeGrid");
	    	}
	    	else{
	    		$("#ajaxTimeGrid").empty();
	    		$("<option value='' selected>缺省</option><option value='1Min'>1分钟</option><option value='5Min'>5分钟</option>").appendTo("#ajaxTimeGrid");
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('1Min')\" ><span>1分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>").appendTo("#timeGrid");
	    		
	    	}
        }
        if (timeFrame == "Hourly"){
        	if(ajaxDis=="block"){
		    	$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='1Min'>1分钟</option><option value='5Min'>5分钟</option>").appendTo("#ajaxTimeGrid");
	    	}
	    	else if(ajaxDis=="none"){
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('1Min')\" ><span>1分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>").appendTo("#timeGrid");
	    	}
	    	else{
	    		$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='1Min'>1分钟</option><option value='5Min'>5分钟</option>").appendTo("#ajaxTimeGrid");
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('1Min')\" ><span>1分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>").appendTo("#timeGrid");
	    		
	    	}
		}
      	if (timeFrame == "Daily" ){
      		if(ajaxDis=="block"){
		    	$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
	    	}
	    	else if(ajaxDis=="none"){
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    	}
	    	else{
	    		$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    		
	    	}
        }
        if (timeFrame == "Weekly" ){
        	if(ajaxDis=="block"){
		    	$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
	    	}
	    	else if(ajaxDis=="none"){
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    	}
	    	else{
	    		$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    		
	    	}
        }
        if (timeFrame == "Monthly" ){
        	if(ajaxDis=="block"){
		    	$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
	    	}
	    	else if(ajaxDis=="none"){
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    	}
	    	else{
	    		$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    		
	    	}
        }
        if (timeFrame == "Quarterly"){
        	if(ajaxDis=="block"){
		    	$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
	    	}
	    	else if(ajaxDis=="none"){
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    	}
	    	else{
	    		$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    		
	    	}
        }
        if (timeFrame == "Halfyear"){
        	if(ajaxDis=="block"){
		    	$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
	    	}
	    	else if(ajaxDis=="none"){
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    	}
	    	else{
	    		$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    		
	    	}
        }
        if (timeFrame == "Oneyear"){
        	if(ajaxDis=="block"){
		    	$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
	    	}
	    	else if(ajaxDis=="none"){
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    	}
	    	else{
	    		$("#ajaxTimeGrid").empty();
				$("<option value='' selected>缺省</option><option value='5Min'>5分钟</option><option value='30Min'>30分钟</option>").appendTo("#ajaxTimeGrid");
		    	$("#timeGrid").empty();
	        	$("<a href =\"#\" onclick=\"javascript:setPeriod('')\" ><span>缺省</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('5Min')\" ><span>5分钟</span></a>&nbsp;&nbsp;<a href =\"#\" onclick=\"javascript:setPeriod('30Min')\" ><span>30分钟</span></a>").appendTo("#timeGrid");
	    		
	    	}
        }
};