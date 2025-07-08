(function($){
    $.fn.validationEngineLanguage = function(){
    };
    $.validationEngineLanguage = {
        newLang: function(){
            $.validationEngineLanguage.allRules = {
                "required": { // Add your regex rules here, you can take telephone as an example
                    "regex": "none",
                    "alertText": "* 此处不能为空",
                    "alertTextCheckboxMultiple": "* 请选择一个项目",
                    "alertTextCheckboxe": "* 您必须钩选此栏",
                    "alertTextDateRange": "* 日期范围不能为空"
                },
                "dateRange": {
                    "regex": "none",
                    "alertText": "* 无效的 ",
                    "alertText2": " 日期范围"
                },
                "dateTimeRange": {
                    "regex": "none",
                    "alertText": "* 无效的 ",
                    "alertText2": " 时间范围"
                },
                "minSize": {
                    "regex": "none",
                    "alertText": "* 最少 ",
                    "alertText2": " 个字符"
                },
                "maxSize": {
                    "regex": "none",
                    "alertText": "* 最多 ",
                    "alertText2": " 个字符"
                },
				"groupRequired": {
                    "regex": "none",
                    "alertText": "* 你必需选填其中一个栏位"
                },
                "min": {
                    "regex": "none",
                    "alertText": "* 最小值为 "
                },
                "max": {
                    "regex": "none",
                    "alertText": "* 最大值为 "
                },
                "past": {
                    "regex": "none",
                    "alertText": "* 日期必需早于 "
                },
                "future": {
                    "regex": "none",
                    "alertText": "* 日期必需晚于 "
                },	
                "maxCheckbox": {
                    "regex": "none",
                    "alertText": "* 最多选取 ",
                    "alertText2": " 个项目"
                },
                "minCheckbox": {
                    "regex": "none",
                    "alertText": "* 请选择 ",
                    "alertText2": " 个项目"
                },
                "password": {
                    "regex": "^[a-zA-Z]\w{5,17}$",
                    "alertText": "* 密码必须以字母开头，长度在6~18之间，只能包含字符、数字和下划线"
                },
                "wrongBack": {
	                	"regex": "^[a-zA-Z]\w{5,17}$",
	                	"alertText": "* 密码必须以字母开头，长度在6~18之间，只能包含字符、数字和下划线"
                },
                "equals": {
                    "regex": "none",
                    "alertText": "* 请输入与上面相同的密码"
                },
                "creditCard": {
                    "regex": "none",
                    "alertText": "* 无效的信用卡号码"
                },
                "phone": {
                    "regex": /^((0\d{2,3})-)(\d{7,8})(-(\d{3}))?$/,
                    "alertText": "* 无效的电话号码"
                },
                
                "phone1": {
                    "regex":  /^[0-9-]{1,20}$/,
                    "alertText": "* 由数字、-（减号）组成，最长20个字符"
                },
                
                "hotLine": {
                	"regex": /^([\+][0-9]{1,3}[ \.\-])?([\(]{1}[0-9]{2,6}[\)])?([0-9 \.\-\/]{3,16})((x|ext|extension)[ ]?[0-9]{1,4})?$/,
                	"alertText": "* 无效的热线电话"
                },
                
                "fax":{
                	"regex": /^((0\d{2,3})-)(\d{7,8})(-(\d{3}))?$/,
                	"alertText": "* 无效的传真号码"
                },
                "fax1":{
                	"regex": /^[0-9-]{1,20}$/,
                	"alertText": "* 由数字、-（减号）组成，最长20个字符"
                },
                
                "mobile":{   
                    "regex":/^(13[0-9]|15[0|3|6|7|8|9]|18[6|8|9])\d{8}$/,
                    "alertText": "* 无效的手机号码"
                 },
                 "mobile2":{  
                     "regex":/^[0-9]{11}$/,
                     "alertText": "* 无效的手机号码"
                  },
                  "mobile3":{  
                      "regex":/^[0-9-]{1,16}$/,
                      "alertText": "* 由数字、-（减号）组成，最长16个字符"
                   },
                 "zipCode":{  
                     "regex":/^[0-9]{6}$/,
                     "alertText": "* 无效的邮政编码"
                  },
                  
                  "color":{  
                      "regex":/^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/,
                      "alertText": "* 无效的颜色代码"
                   },
                 
                "email": {
                    // Shamelessly lifted from Scott Gonzalez via the Bassistance Validation plugin http://projects.scottsplayground.com/email_address_validation/
                    "regex": /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
                    "alertText": "* 邮件地址无效"
                },
                "emails": {
                	"regex": /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
                	"alertText": "* 第",
                	"alertText2": " 个邮件地址无效",
                	"alertText3": "* 邮件地址无效",
                	"alertText4": "* 只能包含英文的;"
                }, 
                "identitys":{  
                    "regex":/(^\d{15}$)|(^\d{17}([0-9]|X)$)/,  
                    "alertText":"* 请输入有效的身份证号码"
                 },
                "integer": {
                    "regex": /^[\-\+]?\d+$/,
                    "alertText": "* 不是有效的整数"
                },
                "number": {
                    // Number, including positive, negative, and floating decimal. credit: orefalo
                    "regex": /^[\-\+]?(([0-9]+)([\.,]([0-9]+))?|([\.,]([0-9]+))?)$/,
                    "alertText": "* 无效的数字"
                },
                "date": {
                    "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/,
                    "alertText": "* 无效的日期，格式必需为 YYYY-MM-DD"
                },
                "ipv4": {
                    "regex": /^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$/,
                    "alertText": "* 无效的 IP 地址"
                },
                "ipv4_Long": {
                	"regex": /^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\/(0[1-9]|[1-2][0-9]|30)$/,
                	"alertText": "* 无效的 IP 地址"
                },
                "subPool": {
                	"regex": /^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\,(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])([.\n\r]*))*$/,
                	"alertText": "* 地址池格式不正确"
                },
                "subDNS": {
                	"regex": /^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])([.\n\r]*))*$/,
                	"alertText": "* DNS格式不正确"
                },
                "macAddr": {
                    "regex": /^([0-9a-fA-F]{2})(([/\s:-][0-9a-fA-F]{2}){5})$/,
                    "alertText": "* 无效的 MAC 地址"
                },
                "url": {
                    "regex": /^((([hH][tT][tT][pP][sS]?|[fF][tT][pP])\:\/\/)?([\w\.\-]+(\:[\w\.\&%\$\-]+)*@)?((([^\s\(\)\<\>\\\"\.\[\]\,@;:]+)(\.[^\s\(\)\<\>\\\"\.\[\]\,@;:]+)*(\.[a-zA-Z]{2,4}))|((([01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}([01]?\d{1,2}|2[0-4]\d|25[0-5])))(\b\:(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|[1-5]\d{4}|[1-9]\d{0,3}|0)\b)?((\/[^\/][\w\.\,\?\'\\\/\+&%\$#\=~_\-@]*)*[^\.\,\?\"\'\(\)\[\]!;<>{}\s\x7F-\xFF])?)$/,
                    "alertText": "* 无效的网址"
                },
                "onlyNumberSp": {
                    "regex": /^[0-9\ ]+$/,
                    "alertText": "* 只能填数字"
                },
                "onlyPosInt": {
                    "regex": /^[1-9]\d*$/,
                    "alertText": "* 只能填非0正整数"
                },
                "positiveInteger": {
                    "regex": /^[1-9]$|^[1-9]\d$|^[1-9]\d\d$|^[1-3]\d\d\d$|^40[0-9][0-6]$/,
                    "alertText": "* 输入信息类型错误！必须是整数,（取值范围：1--4096）"
                },
                "onlyLetterSp": {
                    "regex": /^[a-zA-Z\ \']+$/,
                    "alertText": "* 只接受英文字母大小写"
                },
                "onlyCNNumber": {
                    "regex": /^[0-9a-zA-Z \u4E00-\u9FA5]+$/,
                    "alertText": "* 只接受中、英、数字"
                },
                "onlyCNEN": {
                    "regex": /^([\u4E00-\uFA29]*[a-z]*[A-Z]*)+$/,
                    "alertText": "* 只接受中、英文"
                },
                "onlyCN": {
                    "regex":/^[\u4E00-\u9FA5]+$/,
                    "alertText": "* 只接受中文"
                },"onlyDecimals": {
                    "regex":/^[01](\.\d*)?$/,
                    "alertText": "* 只接受0-1的小数"
                },
                "onlyLetterNumber": {
                    "regex": /^[0-9a-zA-Z]+$/,
                    "alertText": "* 只接受数字、字母"
                },
                "onlyLetter-Number": {
                    "regex": /^[0-9a-zA-Z-]+$/,
                    "alertText": "* 只接受数字、字母、减号"
                },
                "onlyFloat": {
                    "regex": /^[1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/,
                    "alertText": "* 请输入正整数或者正浮点数"
                },
                "excel": {
                    "regex":  /\.xls|\.xlsx$/,
                    "alertText": "* 请导入 .xlsx或.xls 后缀 的文件"
                },
                "onlyLetter_Number": {
                    "regex": /^[0-9a-zA-Z_]+$/,
                    "alertText": "* 只接受数字、字母、下划线"
                },
                "onlyLetter_Number_Name": {
                	"regex": /^[a-zA-Z][0-9a-zA-Z_\-]{3,47}$/,
                	"alertText": "* 名称必须以字母开头，长度在4~48之间，只能包含字符、数字、减号和下划线"
                },
                "onlyLetter_Number_Name1": {
                	"regex": /^[a-zA-Z][0-9a-zA-Z_\-\ \(\)]{3,31}$/,
                	"alertText": "* 名称必须以字母开头，长度在4~32之间，只能包含字符、数字、减号、下划线、 空格、( )"
                },
                "onlyLess1500": {
                	"regex": /^(\d|\d{2}|\d{3}|1[0-4]\d{2}|1500)$/,
                	"alertText": "* 必须小于或等于1500的整数型"
                },
                "onlyEven_Number": {
                	"regex": /^\d*[02468]$/,
                	"alertText": "* 只接受正确的空间大小"
                },
                
                "SerialNumberCustom": {
                	//"regex": /^[0-9a-zA-Z_]+$/,
                	"regex": /^[A-Z]{3}[0-9]{4}[A-Z0-9]{3,4}/,
                    "alertText": "* 无效的序列号"
                },
                
                // --- CUSTOM RULES -- Those are specific to the demos, they can be removed or changed to your likings
                "ajaxAssetNo": {
                    "url": "checkAssetNo.do",
                    // you may want to pass extra data on the ajax call
                    "alertText": "* 此资产编码已被其他设备使用",
                },
                "ajaxHostName": {
	                	"url": "checkHostName.do",
	                	// you may want to pass extra data on the ajax call
	                	"alertText": "* 此主机名称已被其他设备使用",
                },
                "ajaxNetworkName": {
	                	//ajax检查网络名称是否存在
	                	"url": "checkNetworkName.do",
	                	"alertText": "* 此网络名称已使用",
                },
                "ajaxSubnetName": {
	                	"url": "checkSubnetName.do",
	                	"alertText": "* 此子网名称已使用",
                },
                "ajaxSubnetName": {
                	"url": "checkSubnetName.do",
                	"alertText": "* 此子网名称已使用",
                },
	             "ajaxMobile": {
	                "url": "checkMobile.do",
	                "alertText": "* 此电话号码已存在",
                },
                
                "ajaxEmail": {
	                "url": "checkEmail.do",
	                "alertText": "* 此邮箱已存在",
                },
                "ajaxMobileCon": {//供应商联系人手机号唯一性
	                "url": "checkMobileCon.do",
	                "alertText": "* 此电话号码已存在",
                },
                
                "ajaxEmailCon": {
	                "url": "checkEmailCon.do",
	                "alertText": "* 此邮箱已存在",
                },
                "ajaxBankAcc": {
	                "url": "checkBankAcc.do",
	                "alertText": "* 此银行账号已存在",
                },
                "ajaxServiceItem": {
                	"url": "checkServiceItem.do",
                	"alertText": "* 此服务编号已使用",
            },
            "ajaxNetMethod": {
            	"url": "checkNetMethod.do",
            	"alertText": "* 此接入方式已使用",
        },
                
                "ajaxOrgName": {
                	"url": "checkOrgName.do",
                	"alertText": "* 此数据中心名称已使用",
                },
                "ajaxSerialNumber": {
                	"url": "checkSerialNumber.do",
                	"alertText": "* 此序列号已使用",
                },
                "ajaxSubnet": {
	                	"url": "checkSubnet.do",
	                	"alertText": "* 此子网地址已被使用",
                },
                "ajaxFieldName": {
                	"url": "checkFieldName.do",
                	"alertText": "* 此字段名称已使用",
                },
                "ajaxNameCall": {
                    // remote json service location
                    "url": "ajaxValidateFieldName",
                    // error
                    "alertText": "* 此名称可以使用",
                    // if you provide an "alertTextOk", it will show as a green prompt when the field validates
                    "alertTextOk": "* 此名称已被其他人使用",
                    // speaks by itself
                    "alertTextLoad": "* 正在确认名称是否有其他人使用，请稍等。"
                },
                "ajaxWebCustName": {
                	"url": "checkCustName.do",
                	"alertText": "* 此客户名称已注册，请检查、确认"
                },
                "ajaxWebCustCode": {
                	"url": "checkCustCode.do",
                	"alertText": "* 此客户编码已使用，请检查、确认"
                },
                "ajaxCustName": {
                	"url": "checkCustName.do",
                	"alertText": "* 此客户名称已注册，请检查、确认"
                },
                "ajaxCustCode": {
                	"url": "checkCustCode.do",
                	"alertText": "* 此客户编码已使用，请检查、确认"
                },
                "validate2fields": {
                    "alertText": "* 请输入 HELLO"
                },
	            //tls warning:homegrown not fielded 
                "dateFormat":{
                    "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:0?[1-9]|1[0-2])(\/|-)(?:0?[1-9]|1\d|2[0-8]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(0?2(\/|-)29)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$/,
                    "alertText": "* 无效的日期格式"
                },
                //tls warning:homegrown not fielded 
				"dateTimeFormat": {
	                "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])\s+(1[012]|0?[1-9]){1}:(0?[1-5]|[0-6][0-9]){1}:(0?[0-6]|[0-6][0-9]){1}\s+(am|pm|AM|PM){1}$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^((1[012]|0?[1-9]){1}\/(0?[1-9]|[12][0-9]|3[01]){1}\/\d{2,4}\s+(1[012]|0?[1-9]){1}:(0?[1-5]|[0-6][0-9]){1}:(0?[0-6]|[0-6][0-9]){1}\s+(am|pm|AM|PM){1})$/,
                    "alertText": "* 无效的日期或时间格式",
                    "alertText2": "可接受的格式： ",
                    "alertText3": "mm/dd/yyyy hh:mm:ss AM|PM 或 ", 
                    "alertText4": "yyyy-mm-dd hh:mm:ss AM|PM"
	            },
                "dateTime": {
	                "regex": /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))(\s(([01]\d{1})|(2[0123])):([0-5]\d):([0-5]\d))?$/,
	                "alertText": "无效的日期格式，格式为:yyyy-mm-dd hh:mm:ss",
	            }
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);
