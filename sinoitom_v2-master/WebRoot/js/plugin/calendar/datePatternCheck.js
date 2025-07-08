//$Id:
//This is to validate the Custom Time selection using Calendar & time edited by the user

function validateDateValues(formName)
{
	var time1 = "document." + formName + ".startingTime";
	var time2 = "document." + formName + ".endingTime";
        var sTime= "document." + formName + ".stH";
        var eTime= "document." + formName + ".edH";
        var enterfromtime= "document." + formName + ".EnterFromTime";
	var entertotime="document." + formName + ".EnterToTime";
	var fromtimelesser="document." + formName + ".FromTimeLesser";
	var validfromtime="document." + formName + ".ValidFromTime";
	var validtotime="document." + formName + ".ValidToTime";
        
        

	if(eval(time1).value == '')
	{
		alert(eval(enterfromtime).value);
		eval(time1).focus();
		return false;
	}
	else if(eval(time2).value == '')
	{
		alert(eval(entertotime).value);
		eval(time2).focus();
		return false;
	}
	else
	{
		var date1 = getDate(time1, eval(time1).value, validfromtime);
		
		if(date1 == false)
		{
			eval(time1).focus();
			return false;
		}

		var date2 = getDate(time2, eval(time2).value, validtotime);
		if(date2 == false)
		{
			eval(time2).focus();
			return false;
		}
		timeDiff = date2 - date1;
		if(timeDiff < 0)
		{
			alert(eval(fromtimelesser).value);
			eval(time1).focus();
			return false;
		}
	}
	var click = "document."+formName+".clickedGo";
	eval(click).value="true";
       // if(formName=='traffic')
        // {
          	if((eval(time2).value !=0 ||eval(eTime).value!=0)&& (eval(time1).value>eval(time2).value||eval(sTime).value>eval(eTime).value))
	{
		alert("End Time should be greater than StartTime or 00:00 ");
                return false;
	}
	
      //  }
	return true;
}

function getDate(strObj, strValue, FromTo)
{
	timeArray = new Array();
	startArray1 = new Array();
	startArray2 = new Array();
	date = new Date();
	
	var checkFormat = checkDateTimeFormat(strObj, strValue, FromTo);
	if(checkFormat == false)
		return false;
		
	timeArray = strValue.split(" ");
	var startStr1 = timeArray[0];
	var startStr2 = timeArray[1];
	startArray1 = startStr1.split("-");
	var yr = startArray1[0];
	var mon = startArray1[1];
	var day = startArray1[2];
	startArray2 = startStr2.split(":");
	var hr = startArray2[0];
	var min = startArray2[1];

	if(day<1 || day>31 || mon<1 || mon>12 || yr<1 || yr<1980)
	{
		alert(eval(FromTo).value);
		return false;
	}

	if((mon==2) && (day>29)) //checking of no. of days in february month
	{
		alert(eval(FromTo).value);
		return false;
	}

	if((mon==2) && (day>28) && ((yr%4)!=0)) //leap year checking
	{
		alert(eval(FromTo).value);
		return false;
	}

	if(hr<0 || hr>23 || min<0 || min>59)
	{
		alert(eval(FromTo).value);
		return false;
	}

	date = new Date(yr, (mon-1), day, hr, min);
	return date;
}


function checkDateTimeFormat(timeObj, timeValue, fromto)
{
	var dateTimeRe = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})$/;
	if(!dateTimeRe.test(timeValue))
	{
		alert(eval(fromto).value);               
		eval(timeObj).focus();
		return false;
	}else
	{
		return true;
	}

}


