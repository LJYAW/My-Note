function setPeriodNew(){ 		
			var timeFrame = document.sysForm.timeFrame.value;
			var currentDate = new Date();			      	
            var yesterDate = new Date(); 
            if (timeFrame == "5Min"){
            	yesterDate = new Date(currentDate.getTime() - parseInt("493E0", 16));
			}
	      	if (timeFrame == "10Min" ){
                yesterDate = new Date(currentDate.getTime() - parseInt("927C0", 16));
            }
            if (timeFrame == "30Min" ){
                yesterDate = new Date(currentDate.getTime() - parseInt("1B7740", 16));
            }
            if (timeFrame == "Hourly"){
            	yesterDate = new Date(currentDate.getTime() - parseInt("36ee80", 16));
			}
            if (timeFrame == "SixHours"){
            	yesterDate = new Date(currentDate.getTime() - parseInt("1499700", 16));
			}
            if (timeFrame == "TwelveHours"){
            	yesterDate = new Date(currentDate.getTime() - parseInt("2932E00", 16));
			}
	      	if (timeFrame == "Daily" ){
                yesterDate = new Date(currentDate.getTime() - parseInt("5265c00", 16));
            }
            if (timeFrame == "Weekly" ){
                yesterDate = new Date(currentDate.getTime() - parseInt("240c8400", 16));
            }
            if (timeFrame == "Monthly" ){
                yesterDate = new Date(currentDate.getTime() - parseInt("9a7ec800", 16));
            }
            if (timeFrame == "Quarterly"){
                yesterDate = new Date(currentDate.getTime() - parseInt("1cf7c5800", 16));
            }
            if (timeFrame == "Halfyear"){
                yesterDate = new Date(currentDate.getTime() - parseInt("39EF8B000", 16));
            }
            if (timeFrame == "Oneyear"){
                yesterDate = new Date(currentDate.getTime() - parseInt("73DF16000", 16));
            }
            var startingYear = yesterDate.getFullYear();
            var startingMonth = yesterDate.getMonth();
            var startingDate = yesterDate.getDate();
            var startingHour = yesterDate.getHours();
            var startingMinute = yesterDate.getMinutes();
            var endingYear = currentDate.getFullYear();
            var endingMonth = currentDate.getMonth();
            var endingDate = currentDate.getDate();
            var endingHour = currentDate.getHours();
            var endingMinute = currentDate.getMinutes();
            var  sDate = new Date(startingYear, startingMonth,
                                  startingDate, startingHour,
                                  startingMinute);
            var  eDate = new Date(endingYear, endingMonth, endingDate,
                                  endingHour, endingMinute);
            var startTimeFull = sDate.getTime();
            var endTimeFull = eDate.getTime();
 			Date.prototype.format = function(format){   
		        var o = {   
		            "M+"   :   this.getMonth()+1,   //month   
		            "d+"   :   this.getDate(),         //day   
		            "h+"   :   this.getHours(),       //hour   
		            "m+"   :   this.getMinutes(),   //minute   
		            "s+"   :   this.getSeconds(),   //second   
		            "q+"   :   Math.floor((this.getMonth()+3)/3),     //quarter   
		            "S"   :   this.getMilliseconds()   //millisecond   
		        }   
				if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
					for(var k in o){
						if(new RegExp("(" + k + ")").test(format)){   
							format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ( "00" + o[k]).substr((""+ o[k]).length));
						}
					}  
				return format;   
			} 
			var startingTime = new Date(startTimeFull).format("yyyy-MM-dd hh:mm");
            var endingTime = new Date(endTimeFull).format("yyyy-MM-dd hh:mm");
            document.getElementById("startTime").value=startingTime;
			document.getElementById("endTime").value=endingTime;
		};

	function linkedGrid(){
		var index=$("#indexOption").val();
		if(index =="volume"){
			$("#unitsValue").empty();
			$("<option value='B'>B</option><option value='KB'>KB</option><option value='MB' selected>MB</option><option value='GB'>GB</option><option value='TB'>TB</option>").appendTo("#unitsValue");
			$("#pieTitle").empty();
			$("<h2>总吞吐量（Volume）排名——访问业务应用系统（Top）</h2>").appendTo("#pieTitle");
			$("#trendTitle").empty();
			$("<h2>流量排名占比</h2>").appendTo("#trendTitle");
		}
		if(index =="speed"){
			$("#unitsValue").empty();
			$("<option value='Bps'>Bps</option><option value='Kbps'>Kbps</option><option value='Mbps' selected>Mbps</option><option value='Gbps'>Gbps</option><option value='Tbps'>Tbps</option>").appendTo("#unitsValue");
			$("#pieTitle").empty();
			$("<h2>总吞吐量（Volume）排名——访问业务应用系统（Top）</h2>").appendTo("#pieTitle");
			$("#trendTitle").empty();
			$("<h2>速率趋势图</h2>").appendTo("#trendTitle");
		}
		if(index =="packet"){
			$("#unitsValue").empty();
			$("<option value='个'>个</option>").appendTo("#unitsValue");
			$("#pieTitle").empty();
			$("<h2>数据包（Packet）排名——访问业务应用系统（Top）</h2>").appendTo("#pieTitle");
			$("#trendTitle").empty();
			$("<h2>数据包趋势图</h2>").appendTo("#trendTitle");
		}
	};
	
	function currTime(){
      	var currentDate = new Date();
      	var endingYear = currentDate.getFullYear();
        var endingMonth = currentDate.getMonth();
        var endingDate = currentDate.getDate();
        var endingHour = currentDate.getHours();
        var endingMinute = currentDate.getMinutes();
        var  eDate = new Date(endingYear, endingMonth, endingDate,
                                  endingHour, endingMinute);
        var endTimeFull = eDate.getTime();
        Date.prototype.format = function(format){   
		        var o = {   
		            "M+"   :   this.getMonth()+1,   //month   
		            "d+"   :   this.getDate(),         //day   
		            "h+"   :   this.getHours(),       //hour   
		            "m+"   :   this.getMinutes(),   //minute   
		            "s+"   :   this.getSeconds(),   //second   
		            "q+"   :   Math.floor((this.getMonth()+3)/3),     //quarter   
		            "S"   :   this.getMilliseconds()   //millisecond   
		        }   
				if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
					for(var k in o){
						if(new RegExp("(" + k + ")").test(format)){   
							format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ( "00" + o[k]).substr((""+ o[k]).length));
						}
					}  
				return format;   
			} 
		var endingTime = new Date(endTimeFull).format("yyyy-MM-dd hh:mm");
		$("#endTime").val(endingTime);
      }