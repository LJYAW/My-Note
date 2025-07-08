var systemName;
function getSystemName(name){
    systemName=name;
}


function pwdLevel(value) {
    var pattern_1 = /^.*([\W_])+.*$/i;
    var pattern_2 = /^.*([a-zA-Z])+.*$/i;
    var pattern_3 = /^.*([0-9])+.*$/i;
    var level = 0;
    if (value.length > 10) {
        level++;
    }
    if (pattern_1.test(value)) {
        level++;
    }
    if (pattern_2.test(value)) {
        level++;
    }
    if (pattern_3.test(value)) {
        level++;
    }
    if (level > 3) {
        level = 3;
    }
    return level;
}

function verc() {
    $("#JD_Verification1").click();
}

var validateRegExp = {
		decmal:"^([+-]?)\\d*\\.\\d+$", //浮点数
		decmal1:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$", //正浮点数
		decmal2:"^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$", //负浮点数
		decmal3:"^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$", //浮点数
		decmal4:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$", //非负浮点数（正浮点数 + 0）
		decmal5:"^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$", //非正浮点数（负浮点数 + 0）
		intege:"^-?[1-9]\\d*$", //整数
		intege1:"^[1-9]\\d*$", //正整数
		intege2:"^-[1-9]\\d*$", //负整数
		num:"^([+-]?)\\d*\\.?\\d+$", //数字
		num1:"^[1-9]\\d*|0$", //正数（正整数 + 0）
		num2:"^-[1-9]\\d*|0$", //负数（负整数 + 0）
		ascii:"^[\\x00-\\xFF]+$", //仅ACSII字符
		chinese:"^[\\u4e00-\\u9fa5]+$", //仅中文
		color:"^[a-fA-F0-9]{6}$", //颜色
		date:"^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$", //日期
		email:"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", //邮件
		idcard:"^[1-9]([0-9]{14}|[0-9]{17})$", //身份证
		ip4:"^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$", //ip地址
		letter:"^[A-Za-z]+$", //字母
		letter_l:"^[a-z]+$", //小写字母
		letter_u:"^[A-Z]+$", //大写字母
		mobile:"^0?(13|15|18|14)[0-9]{9}$", //手机
		notempty:"^\\S+$", //非空
		password:"^.*[A-Za-z0-9\\w_-]+.*$", //密码
		fullNumber:"^[0-9]+$", //数字
		englishAndNumber:"^[A-Za-z0-9]+$",//英文和数字
		picture:"(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$", //图片
		qq:"^[1-9]*[1-9][0-9]*$", //QQ号码
		rar:"(.*)\\.(rar|zip|7zip|tgz)$", //压缩文件
		phone:"^[0-9\-()（）]{7,18}$", //电话号码的函数(包括验证国内区号,国际区号,分机号)
		url:"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$", //url
		username:"^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$", //用户名
		deptname:"^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$", //单位名
		zipcode:"^\\d{6}$", //邮编
		realname:"^[A-Za-z\\u4e00-\\u9fa5]+$", // 真实姓名
		comName:"^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",
		nickName:"^[A-Za-z0-9_()（）\\-\\u4e00-\\u9fa5]+$",//昵称
		companyaddr:"^[A-Za-z0-9_()（）\\#\\-\\u4e00-\\u9fa5]+$",
		companysite:"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&#=]*)?$" 
};
//主函数
(function ($) {
    $.fn.jdValidate = function (option, callback, def) {
        var ele = this;
        var id = ele.attr("id");
        var type = ele.attr("type");
        var rel = ele.attr("rel");
        var _onFocus = $("#" + id + validateSettings.onFocus.container);
        var _succeed = $("#" + id + validateSettings.succeed.container);
        var _isNull = $("#" + id + validateSettings.isNull.container);
        var _error = $("#" + id + validateSettings.error.container);
        if (def == true) {
            var str = ele.val();
            var tag = ele.attr("sta");
            if (str == "" || str == "-1") {
                validateSettings.isNull.run({
                    prompts:option,
                    element:ele,
                    isNullEle:_isNull,
                    succeedEle:_succeed
                }, option.isNull);
            } else if (tag == 1 || tag == 2) {
                return;
            } else {
                callback({
                    prompts:option,
                    element:ele,
                    value:str,
                    errorEle:_error,
                    succeedEle:_succeed
                });
            }
        } else {
        	if(def == "allowNull"){
        		ele.attr("sta", validateSettings.succeed.state);
        		return;
        	}
            if (typeof def == "string") {
                ele.val(def);
            }
            if (type == "checkbox" || type == "radio") {
                if (ele.attr("checked") == true) {
                    ele.attr("sta", validateSettings.succeed.state);
                }
            }
            switch (type) {
                case "text":
                case "password":
                    ele.bind("focus", function () {
                        var str = ele.val();
                        if (str == def) {
                            ele.val("");
                        }
                        if (id == "pwd") {
                            $("#pwdstrength").hide();
                        }
                        validateSettings.onFocus.run({
                            prompts:option,
                            element:ele,
                            value:str,
                            onFocusEle:_onFocus,
                            succeedEle:_succeed
                        }, option.onFocus);
                    })
                        .bind("blur", function () {
                            var str = ele.val();
                            if (str == "") {
                                ele.val(def);
                            }
                            if (validateRules.isNull(str)) {
                                validateSettings.isNull.run({
                                    prompts:option,
                                    element:ele,
                                    value:str,
                                    isNullEle:_isNull,
                                    succeedEle:_succeed
                                }, "");
                            } else {
                                callback({
                                    prompts:option,
                                    element:ele,
                                    value:str,
                                    errorEle:_error,
                                    isNullEle:_isNull,
                                    succeedEle:_succeed
                                });
                            }
                        });
                    break;
                default:
                    if (rel && rel == "select") {
                        ele.bind("change", function () {
                            var str = ele.val();
                            callback({
                                prompts:option,
                                element:ele,
                                value:str,
                                errorEle:_error,
                                isNullEle:_isNull,
                                succeedEle:_succeed
                            });
                        })
                    } else {
                        ele.bind("click", function () {
                            callback({
                                prompts:option,
                                element:ele,
                                errorEle:_error,
                                isNullEle:_isNull,
                                succeedEle:_succeed
                            });
                        })
                    }
                    break;
            }
        }
    }
})(jQuery);

//配置
var validateSettings = {
    onFocus:{
        state:null,
        container:"_error",
        style:"focus",
        run:function (option, str) {
            if (!validateRules.checkType(option.element)) {
                option.element.removeClass(validateSettings.INPUT_style2).addClass(validateSettings.INPUT_style1);
            }
            option.onFocusEle.removeClass().addClass(validateSettings.onFocus.style).html(str);
        }
    },
    isNull:{
        state:0,
        container:"_error",
        style:"null",
        run:function (option, str) {
            option.element.attr("sta", 0);
            if (!validateRules.checkType(option.element)) {
                if (str != "") {
                    option.element.removeClass(validateSettings.INPUT_style1).addClass(validateSettings.INPUT_style2);
                } else {
                    option.element.removeClass(validateSettings.INPUT_style2).removeClass(validateSettings.INPUT_style1);
                }
            }
            option.succeedEle.removeClass(validateSettings.succeed.style);
            option.isNullEle.removeClass().addClass(validateSettings.isNull.style).html(str);
        }
    },
    isAllowNull:{
        state:0,
        container:"_succeed",
        style:"succeed",
        run:function (option, str) {
            option.element.attr("sta", 2);
            if (!validateRules.checkType(option.element)) {
                if (str != "") {
                    option.element.removeClass(validateSettings.INPUT_style1).addClass(validateSettings.INPUT_style2);
                } else {
                    option.element.removeClass(validateSettings.INPUT_style2).removeClass(validateSettings.INPUT_style1);
                }
            }
            option.succeedEle.removeClass(validateSettings.succeed.style);
            option.isNullEle.removeClass().addClass(validateSettings.isNull.style).html(str);
        }
    },
    error:{
        state:1,
        container:"_error",
        style:"error",
        run:function (option, str) {
            option.element.attr("sta", 1);
            if (!validateRules.checkType(option.element)) {
                option.element.removeClass(validateSettings.INPUT_style1).addClass(validateSettings.INPUT_style2);
            }
            option.succeedEle.removeClass(validateSettings.succeed.style);
            option.errorEle.removeClass().addClass(validateSettings.error.style).html(str);
        }
    },
    succeed:{
        state:2,
        container:"_succeed",
        style:"succeed",
        run:function (option) {
            option.element.attr("sta", 2);
            option.errorEle.empty();
            if (!validateRules.checkType(option.element)) {
                option.element.removeClass(validateSettings.INPUT_style1).removeClass(validateSettings.INPUT_style2);
            }
            if (option.element.attr("id") == "schoolinput" && $("#schoolid").val() == "") {
                return;
            }
            option.succeedEle.addClass(validateSettings.succeed.style);
        }
    },
    INPUT_style1:"highlight1",
    INPUT_style2:"highlight2"
};

//验证规则
var validateRules = {
    isNull:function (str) {
        return (str == "" || typeof str != "string");
    },
    isAllowNull:function (str) {
        return (str == "" || typeof str != "string");
    },
    betweenLength:function (str, _min, _max) {
        return (str.length >= _min && str.length <= _max);
    },
    mustLength:function (str, _num) {
        return (str.length == _num);
    },
    isUid:function (str) {
        return new RegExp(validateRegExp.username).test(str);
    },
    fullNumberName:function (str) {
        return new RegExp(validateRegExp.fullNumber).test(str);
    },
    isPwd:function (str) {
        return /^.*([\W_a-zA-z0-9-])+.*$/i.test(str);
    },
    isPwd2:function (str1, str2) {
        return (str1 == str2);
    },
    isEmail:function (str) {
        return new RegExp(validateRegExp.email).test(str);
    },
    isTel:function (str) {
        return new RegExp(validateRegExp.phone).test(str);
    },
    isMobile:function (str) {
        return new RegExp(validateRegExp.mobile).test(str);
    },
    checkType:function (element) {
        return (element.attr("type") == "checkbox" || element.attr("type") == "radio" || element.attr("rel") == "select");
    },
    isChinese:function (str) {
        return new RegExp(validateRegExp.chinese).test(str);
    },
    isRealName:function (str) {
        return new RegExp(validateRegExp.realname).test(str);
    },
    isRealUserName:function (str) {
        return new RegExp(validateRegExp.realname).test(str);
    },
    isDeptname:function (str) {
        return new RegExp(validateRegExp.deptname).test(str);
    },
    
    
    isCompanyname:function (str) {
        return new RegExp(validateRegExp.comName).test(str);
    },
    isCompanyaddr:function (str) {
        return new RegExp(validateRegExp.companyaddr).test(str);
    },
    
    isBusinessLicense:function (str) {
        return new RegExp(validateRegExp.fullNumber).test(str);
    },
    isBusinessDeadline:function (str) {
        return new RegExp(validateRegExp.fullNumber).test(str);
    },
    isOrgCode:function (str) {
        return new RegExp(validateRegExp.fullNumber).test(str);
    },
    isTaxCode:function (str) {
        return new RegExp(validateRegExp.fullNumber).test(str);
    },
    
    isEnglishOrNum:function (str) {
        return new RegExp(validateRegExp.englishAndNumber).test(str);
    },
    
    isCompanysite:function (str) {
        return new RegExp(validateRegExp.companysite).test(str);
    },
    simplePwd:function (str) {
        var pin = null;
        if ($("#username").length > 0) {
            pin = $("#username").val();
        }
        if ($("#emType").length > 0) {
            var emType = $("#emType").val();
            if (emType == "mobile") {
                pin = $("#mobileInfo").val();
            } else {
                pin = $("#email").val();
            }
        }
        if (pin!=null&&pin.length > 0) {
            pin = strTrim(pin);
            if (pin == str) {
                return true;
            }
        }
        return pwdLevel(str) == 1;
    }
};
//验证文本
var validatePrompt = {
		username:{
		onFocus:"4-20位字符，可由中文、英文、数字及“_”、“-”组成",
		succeed:"",
		isNull:"请输入用户名",
		error:{
		beUsed:"该用户名已被使用，请使用其它用户名注册，如果您是&quot;userame&quot;，请<a href='../web/customer/customerUser/usedlogin.do?usedName=userame' class='flk13'>登录</a>",
		badLength:"用户名长度只能在4-20位字符之间",
		badFormat:"用户名只能由中文、英文、数字及“_”、“-”组成",
		fullNumberName:"用户名不能全为数字"
		}
		},
//		mobileInfo:{
//		onFocus:"完成注册后，您可以用手机号登录京东，暂不支持海外手机号",
//		succeed:"",
//		isNull:"请输入手机号",
//		error:{
//		beUsed:"该手机号已被使用，请更换号码",
//		badLength:"用户名长度只能在4-20位字符之间",
//		badFormat:"手机号码格式有误，请输入以13/14/15/18开头的11位数字。"
//		}
//		},
		pwd:{
		onFocus:"6-20位字符，可使用字母、数字或符号的组合",
		succeed:"",
		isNull:"请输入密码",
		error:{
		badLength:"密码长度只能在6-20位字符之间",
		badFormat:"密码只能由英文、数字及标点符号组成",
		simplePwd:"密码太弱，有被盗风险，建议设置多种字符组成的复杂密码"
		}
		},
		pwd2:{
		onFocus:"请再次输入密码",
		succeed:"",
		isNull:"请输入密码",
		error:{
		badLength:"密码长度只能在6-20位字符之间",
		badFormat2:"两次输入密码不一致",
		badFormat1:"密码只能由英文、数字及标点符号组成"
		}
		},
//		mail:{
//		onFocus:"请输入常用的邮箱，将用来找回密码、接收订单通知等",
//		succeed:"",
//		isNull:"请输入邮箱",
//		error:{
//		beUsed:"该邮箱已被使用，请更换其它邮箱",
//		badFormat:"请输入有效的邮箱地址",
//		badLength:"您填写的邮箱过长，邮箱地址只能在50个字符以内"
//		}
//		},
		authcode:{
		onFocus:"请输入图片中的字符，不区分大小写",
		succeed:"",
		isNull:"请输入验证码",
		error:"验证码错误"
		},
		mobile:{
			onFocus:"请输入手机号",
			succeed:"",
			isNull:"请输入手机号",
			error:{
			badLength:"手机号长度只能是11位",
			badFormat:"手机号码格式有误，请输入以13/14/15/18开头的11位数字。"
			}
		},
		phone:{
			onFocus:"请输入电话号码",
			succeed:"",
			isNull:"请输入电话号码",
			error:{
				badFormat:"电话号码格式有误"
			}
		},		
//		mobileCode:{
//		succeed:"",
//		isNull:"请输入校验码",
//		error:"校验码错误"
//		},
//		protocol:{
//		onFocus:"",
//		succeed:"",
//		isNull:"请先阅读并同意《京东商城用户协议》",
//		error:""
//		},
//		referrer:{
//		onFocus:"如您注册并完成订单，推荐人有机会获得积分",
//		succeed:"",
//		isNull:"",
//		error:""
//		},
//		schoolinput:{
//		onFocus:"您可以用简拼、全拼、中文进行校名模糊查找",
//		succeed:"",
//		isNull:"请填选学校名称",
//		error:"请填选学校名称"
//		},
		
		//--------------------------- 公司 merchant  注册  begin-------------------------------------//
		    companyName:{
		    	onFocus:"请填写工商局注册的全称。4-40位字符，可由中英文及数字组成",
		        succeed:"",
		        isNull:"请输入企业名称",
		        error:{
		            badLength:"企业名称长度只能在4-40位字符之间",
		            badFormat:"企业名称只能由中文、英文、数字及“_”、“-”、()、（）组成"
		        }
		    },
		    nickName:{
		    	onFocus:"请填写昵称。4-40位字符，可由中英文及数字组成",
		        succeed:"",
		        isNull:"请输入昵称",
		        error:{
		            badLength:"昵称长度只能在4-40位字符之间",
		            badFormat:"昵称只能由中文、英文、数字及“_”、“-”、()、（）组成"
		        }
		    },
		    
		    
		    realname:{
		    	onFocus:"请填写真实姓名。4-40位字符，可由中英文及数字组成",
		        succeed:"",
		        isNull:"请输入真实姓名",
		        error:{
		            badLength:"真实姓名长度只能在4-40位字符之间",
		            badFormat:"真实姓名只能由中文、英文、数字及“_”、“-”、()、（）组成"
		        }
		    },
		    presentPlace:{
		        onFocus:"请详细填写所在详址　如：北京市海淀区苏州街20号银丰大厦B座3层",
		        succeed:"",
		        isNull:"请输入所在详址",
		        error:{
		            badLength:"所在详址长度只能在4-50位字符之间",
		            badFormat:"所在详址只能由中文、英文、数字及“_”、“-”、()、（）、#组成"
		        }
		    },
		    businessLicense:{
		        onFocus:"请填写营业执照注册号",
		        succeed:"",
		        isNull:"请填写营业执照注册号",
		        error:{
		            badLength:"营业执照注册号为15位字符",
		            badFormat:"营业执照注册号只能由数字组成"
		        }
		    },
		    regAddress:{
		        onFocus:"请详细填写公司经营地址　如：北京市海淀区苏州街20号银丰大厦B座3层",
		        succeed:"",
		        isNull:"请输入公司地址",
		        error:{
		            badLength:"公司地址长度只能在4-50位字符之间",
		            badFormat:"公司地址只能由中文、英文、数字及“_”、“-”、()、（）、#组成"
		        }
		    },
		    
		    businessDeadline:{
		        onFocus:"请填写公司营业期限",
		        succeed:"",
		        isNull:"请填写公司营业期限",
		        error:{
		        }
		    },
		    busLicImgUrl:{
		        onFocus:"请上传公司营业执照副本扫描件",
		        succeed:"",
		        isNull:"请上传公司营业执照副本扫描件",
		    },
		    orgCode:{
		        onFocus:"请填写公司组织机构代码",
		        succeed:"",
		        isNull:"请填写公司组织机构代码",
		    },
		    taxCode:{
		        onFocus:"请填写公司税号",
		        succeed:"",
		        isNull:"请填写公司税号",
		        error:{
		        }
		    },
		    name:{
		    	onFocus:"4-20位字符，可由中文、英文、数字及“_”、“-”组成",
				succeed:"",
				isNull:"请输入公司法人名称",
				error:{
				badLength:"法人名称长度只能在4-20位字符之间",
				badFormat:"法人名称只能由中文、英文、数字及“_”、“-”组成",
				fullNumberName:"法人名称不能全为数字"
				}
		    },
		    mobile:{
				onFocus:"请输入你的手机号码",
				succeed:"",
				isNull:"请输入你的手机号码",
				error:{
				badLength:"手机号长度只能是11位",
				badFormat:"手机号码格式有误，请输入以13/14/15/18开头的11位数字。"
				}
			},
			phone:{
				onFocus:"请输入公司法人电话号码",
				succeed:"",
				isNull:"请输入公司法人电话号码",
				error:{
					badFormat:"电话号码格式有误"
				}
			},		
			mail:{
				onFocus:"请输入公司法人的邮箱",
				succeed:"",
				isNull:"请输入邮箱",
				error:{
				badFormat:"请输入有效的邮箱地址",
				badLength:"您填写的邮箱过长，邮箱地址只能在50个字符以内"
				}
				},
			email:{
					onFocus:"请输入邮箱地址",
					succeed:"",
					isNull:"请输入邮箱地址",
					error:{
					beUsed:"该邮箱已被使用，请更换其它邮箱",
					badFormat:"请输入有效的邮箱地址",
					badLength:"您填写的邮箱过长，邮箱地址只能在50个字符以内"
					}
					},					
			idImgUrl:{
				onFocus:"请上传身份证扫描件",
				succeed:"",
				isNull:"请上传身份证扫描件",
			},	
			regPlace:{
				onFocus:"请填写法人户籍所在地",
				succeed:"",
				isNull:"请填写法人户籍所在地",
			},	
		//--------------------------- 公司 merchant  注册  begin-------------------------------------//
			
	//--------------------------- 商家加盟    批发商   零售商   注册  begin-------------------------------------//
			
			mertName:{
				onFocus:"请输入公司名称！（4-32位字符，可由中英文及数字组成）",
		        succeed:"",
		        isNull:"请输入公司名称！",
		        error:{
		        	beUsed:"该公司名称已经被使用，请确认或电话咨询！",
		            badLength:"名称长度只能在4-32位字符之间",
		            badFormat:"名称只能由中文、英文、数字及“_”、“-”、()、（）组成",
		    		fullNumberName:"公司名称不能全为数字"
		        }
			},
			shortName:{
				onFocus:"请输入公司简称。4-32位字符，可由中英文及数字组成",
		        succeed:"",
		        isNull:"请输入公司简称",
		        error:{
		        	  beUsed:"该简称已经被使用，请重新填写",
		        	  badLength:"名称长度只能在4-32位字符之间",
			          badFormat:"名称只能由中文、英文、数字及“_”、“-”、()、（）组成",
			    	  fullNumberName:"公司名称不能全为数字"
		        }
			},
			mertCode:{
				onFocus:"请输入公司编码。4-16位英文字母，是公司的唯一业务识别码！",
		        succeed:"",
		        isNull:"请输入公司编码",
		        error:{
		        	 beUsed:"该编码已经被使用，请重新填写",
		            badLength:"公司编码长度只能在4-32位字符之间",
		            badFormat:"公司编码只能是英文字母或数字",
	            	fullNumberName:"公司编码不能全为数字"
		        }
			},
			taxRegCtfNo:{
				isAllowNull:"",
		        succeed:"",
		        error:{
		            badLength:"税务登记证号长度不正确",
		            badFormat:"税务登记证号只能是数字",
		        }
			},
			mobileOrPhone:{
				onFocus:"请输入电话号码或手机号",
		        succeed:"",
		        isNull:"请输入电话号码或手机号",
		        error:{
		            badFormat:"格式不正确",
		        }
			},
			joinAddress:{//商家加盟 地址
			        onFocus:"请详细填写经营地址　如：北京市海淀区苏州街20号银丰大厦B座3层",
			        succeed:"",
			        isNull:"请输入经营地址",
			        error:{
			            badLength:"经营地址长度只能在4-50位字符之间",
			        }
			    },
	//---------------------------  商家加盟   批发商   零售商   注册   end---------------------------------------//			    
		empty:{
		onFocus:"",
		succeed:"",
		isNull:"",
		error:""
		},
			    
   //---------------------------- 商家申请人注册--------------------------------------------------
	 loginname:{
		 onFocus:"4-20位字符，可由中文、英文、数字及“_”、“-”组成",
			succeed:"",
			isNull:"请输入用户名",
			error:{
			beUsed:"该用户名已被使用，请使用其它用户名注册，如果您是&quot;userame&quot;，请<a href='../login/mertApplyLogin.do?usedName=userame' class='flk13'>登录</a>",
			badLength:"用户名长度只能在4-20位字符之间",
			badFormat:"用户名只能由中文、英文、数字及“_”、“-”组成",
			fullNumberName:"用户名不能全为数字"
			}
	 },
	 
};


var nameold, emailold, authcodeold;
var namestate = false, emailstate = false, authcodestate = false;
//回调函数
var validateFunction = {
		
		 loginname:function (option) {
		        var format = validateRules.isUid(option.value);
		        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 20);
		        if (!length && format) {
		            validateSettings.error.run(option, option.prompts.error.badLength);
		        }
		        else if (!length && !format) {
		            validateSettings.error.run(option, option.prompts.error.badFormat);
		        }
		        else if (length && !format) {
		            validateSettings.error.run(option, option.prompts.error.badFormat);
		        } else if (validateRules.fullNumberName(option.value)) {
		            validateSettings.error.run(option, option.prompts.error.fullNumberName);
		        } else {
		            if (!namestate || nameold != option.value) {
		                if (nameold != option.value) {
		                    nameold = option.value;
		                    option.errorEle.html("<span style='color:#999'>检验中……</span>");
		                    $.getJSON("../register/isSysUsedName.do?name=" + escape(option.value) + "&r=" + Math.random(), function (data) {
		                    	 if (data.resultCode == 101) {
		                    		 validateSettings.error.run(option, option.prompts.error.beUsed.replace(/userame/g, option.value));
		                             namestate = false;
		                         } else {
		                             validateSettings.succeed.run(option);
		                             namestate = true;
		                         }
		                    })
		                }
		                else {
		                    validateSettings.error.run(option, option.prompts.error.beUsed.replace(/userame/g, option.value));
		                    namestate = false;
		                }
		            }
		            else {
		                validateSettings.succeed.run(option);
		            }
		        }
		    },
		    
		    userName:function (option) {
		        var format = validateRules.isUid(option.value);
		        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 20);
		        if (!length && format) {
		            validateSettings.error.run(option, option.prompts.error.badLength);
		        }
		        else if (!length && !format) {
		            validateSettings.error.run(option, option.prompts.error.badFormat);
		        }
		        else if (length && !format) {
		            validateSettings.error.run(option, option.prompts.error.badFormat);
		        } else if (validateRules.fullNumberName(option.value)) {
		            validateSettings.error.run(option, option.prompts.error.fullNumberName);
		        } else {
		            if (!namestate || nameold != option.value) {
		                if (nameold != option.value) {
		                    nameold = option.value;
		                    option.errorEle.html("<span style='color:#999'>检验中……</span>");
		                    $.getJSON("../register/isUsedName.do?name=" + option.value + "&r=" + Math.random(), function (data) {
		                    	 if (data.resultCode == 101) {
		                    		 validateSettings.error.run(option, option.prompts.error.beUsed.replace(/userame/g, option.value));
		                             namestate = false;
		                         } else {
		                             validateSettings.succeed.run(option);
		                             namestate = true;
		                         }
		                    })
		                }
		                else {
		                    validateSettings.error.run(option, option.prompts.error.beUsed.replace(/userame/g, option.value));
		                    namestate = false;
		                }
		            }
		            else {
		                validateSettings.succeed.run(option);
		            }
		        }
		    },
		    
		
		
    username:function (option) {
        var format = validateRules.isUid(option.value);
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 20);
        if (!length && format) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        }
        else if (!length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        }
        else if (length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else if (validateRules.fullNumberName(option.value)) {
            validateSettings.error.run(option, option.prompts.error.fullNumberName);
        } else {
            if (!namestate || nameold != option.value) {
                if (nameold != option.value) {
                    nameold = option.value;
                    option.errorEle.html("<span style='color:#999'>检验中……</span>");
                    $.getJSON(systemName+"/web/customer/customerUser/isUsedName.do?name=" + option.value + "&r=" + Math.random(), function (data) {
                    	 if (data.resultCode == 101) {
                    		 validateSettings.error.run(option, option.prompts.error.beUsed.replace(/userame/g, option.value));
                             namestate = false;
                         } else {
                             validateSettings.succeed.run(option);
                             namestate = true;
                         }
                    })
                }
                else {
                    validateSettings.error.run(option, option.prompts.error.beUsed.replace(/userame/g, option.value));
                    namestate = false;
                }
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    
    
    realname:function (option) {
        var format = validateRules.isUid(option.value);
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 20);
        if (!length && format) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        }
        else if (!length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        }
        else if (length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else if (validateRules.fullNumberName(option.value)) {
            validateSettings.error.run(option, option.prompts.error.fullNumberName);
        } 
        else {
        	 validateSettings.succeed.run(option);
        }
    },
    
    
    

    pwd:function (option) {
        var str1 = option.value;
        var str2 = $("#pwd2").val();
        var format = validateRules.isPwd(option.value);
        var length = validateRules.betweenLength(option.value, 6, 20);
        $("#pwdstrength").hide();
        if (!length && format) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else if (!length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else if (length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else if (validateRules.simplePwd(str1)) {
            validateSettings.error.run(option, option.prompts.error.simplePwd);
        } else {
            validateSettings.succeed.run(option);
            validateFunction.pwdstrength();
        }
        if (str2 == str1) {
            $("#pwd2").focus();
        }
    },
    pwd2:function (option) {
        var str1 = option.value;
        var str2 = $("#pwd").val();
        var length = validateRules.betweenLength(option.value, 6, 20);
        var format2 = validateRules.isPwd2(str1, str2);
        var format1 = validateRules.isPwd(str1);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format1) {
                validateSettings.error.run(option, option.prompts.error.badFormat1);
            } else {
                if (!format2) {
                    validateSettings.error.run(option, option.prompts.error.badFormat2);
                }
                else {
                    validateSettings.succeed.run(option);
                }
            }
        }
    },
    mail:function (option) {
        var format = validateRules.isEmail(option.value);
        var format2 = validateRules.betweenLength(option.value, 0, 50);
        if (!format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else {
            if (!format2) {
                validateSettings.error.run(option, option.prompts.error.badLength);
            } else {
                if (!emailstate || emailold != option.value) {
                    if (emailold != option.value) {
                        emailold = option.value;
                        option.errorEle.html("<span style='color:#999'>检验中……</span>");
                        $.getJSON("../web/customer/customUser/valEmail.do?email=" + escape(option.value), function (data) {
                            if (data.resultCode == 102) {
                                validateSettings.error.run(option, option.prompts.error.beUsed);
                                emailstate = false;
                            } else {
                            	 validateSettings.succeed.run(option);
                                 emailstate = true;
                            }
                        })
                    }
                    else {
                        validateSettings.error.run(option, option.prompts.error.beUsed);
                        emailstate = false;
                    }
                }
                else {
                    validateSettings.succeed.run(option);
                }
            }
        }
    },
    authcode:function (option) {
        if (!authcodestate || authcodeold != option.value) {
            if (authcodeold != option.value) {
                authcodeold = option.value;
                option.errorEle.html("<span style='color:#999'>检验中……</span>");
                $.getJSON("../register/checkAuthCode.do?authcode=" + escape(option.value), function(data) {
                    if (data.resultCode == 103) {
                    	validateSettings.error.run(option, option.prompts.error);
                        authcodestate = false;
                    } else {
                        validateSettings.succeed.run(option);
                        authcodestate = true;
                    }
                })
            }
            else {
                validateSettings.error.run(option, option.prompts.error);
                authcodestate = false;
            }
        }
        else {
            validateSettings.succeed.run(option);
        }
        validateSettings.succeed.run(option);
        authcodestate = true;
    },
//    email:function (option) {
//        var format = validateRules.isEmail(option.value);
//        var format2 = validateRules.betweenLength(option.value, 0, 50);
//        if (!format) {
//            validateSettings.error.run(option, option.prompts.error.badFormat);
//        } else {
//            if (!format2) {
//                validateSettings.error.run(option, option.prompts.error.badLength);
//            } else {
//                if (!emailstate || emailold != option.value) {
//                    if (emailold != option.value) {
//                        emailold = option.value;
//                        option.errorEle.html("<span style='color:#999'>检验中……</span>");
//                        $.getJSON("../register/valEmail.do?email=" + escape(option.value), function (date) {
//                            if (date.success == 0) {
//                                validateSettings.succeed.run(option);
//                                emailstate = true;
//                            } else {
//                                validateSettings.error.run(option, option.prompts.error.beUsed);
//                                emailstate = false;
//                            }
//                        })
//                    }
//                    else {
//                        validateSettings.error.run(option, option.prompts.error.beUsed);
//                        emailstate = false;
//                    }
//                }
//                else {
//                    validateSettings.succeed.run(option);
//                }
//            }
//        }
//    },
    mobileInfo:function (option) {
        var mobileValue = option.value;
        if (validateRules.isNull(mobileValue)) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
            return;
        }
        mobileValue = strTrim(mobileValue);
        var isMobile = validateRules.isMobile(mobileValue);
        if (!isMobile || mobileValue.length > 11) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else {
            if (!namestate || nameold != option.value) {
                if (nameold != option.value) {
                    nameold = option.value;
                    option.errorEle.html("<span style='color:#999'>检验中……</span>");
                    alert(option.value);
                    $.getJSON("../register/isMobileEngaged.do?mobile=" + option.value + "&r=" + Math.random(), function (data) {
                    	if (data.resultCode == 101) {
                   		 validateSettings.error.run(option, option.prompts.error.beUsed.replace(/userame/g, option.value));
                            namestate = false;
                        } else {
                            validateSettings.succeed.run(option);
                            namestate = true;
                        }
                    })
                }
                else {
                    validateSettings.error.run(option, option.prompts.error.beUsed);
                    namestate = false;
                }
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    companyName:function (option) {
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 40);
        var format = validateRules.isCompanyname(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        }
        else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            } else {
                validateSettings.succeed.run(option);
            }
        }
    },
    businessLicense:function (option) {
    	 var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 15);
         var format = validateRules.isBusinessLicense(option.value);
         if (!length) {
             validateSettings.error.run(option, option.prompts.error.badLength);
         } else {
             if (!format) {
                 validateSettings.error.run(option, option.prompts.error.badFormat);
             }
             else {
                 validateSettings.succeed.run(option);
             }
         }
    },
    regAddress:function (option) {
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 50);
        var format = validateRules.isCompanyaddr(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    businessDeadline:function (option) {
        var format = validateRules.isBusinessDeadline(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    orgCode:function (option) {
        var format = validateRules.isOrgCode(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    taxCode:function (option) {
        var format = validateRules.isTaxCode(option.value);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } else {
            if (!format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    phone:function (option) {
        var format = validateRules.isTel(option.value);
        if(!format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        }
        else {
            validateSettings.succeed.run(option);
        }
    },

    referrer:function (option) {
        var bool = validateRules.isNull(option.value);
        if (bool) {
            option.element.val("可不填");
            return;
        } else {
            validateSettings.succeed.run(option);
        }
    },
    schoolinput:function (option) {
        var bool = validateRules.isNull(option.value);
        if (bool) {
            validateSettings.error.run(option, option.prompts.error);
            return;
        } else {
            validateSettings.succeed.run(option);
        }
    },
    mobileCode:function (option) {
        var bool = validateRules.isNull(option.value);
        if (bool) {
            validateSettings.error.run(option, option.prompts.error);
            return;
        } else {
            validateSettings.succeed.run(option);
        }
    },
   
//    protocol:function (option) {
//        if (option.element.attr("checked") == true) {
//            option.element.attr("sta", validateSettings.succeed.state);
//            option.errorEle.html("");
//        } else {
//            option.element.attr("sta", validateSettings.isNull.state);
//            option.succeedEle.removeClass(validateSettings.succeed.style);
//        }
//    },
    pwdstrength:function () {
        var element = $("#pwdstrength");
        var value = $("#pwd").val();
        if (value.length >= 6 && validateRules.isPwd(value)) {
            $("#pwd_error").empty();
            element.show();
            var level = pwdLevel(value);
            switch (level) {
                case 1:
                    element.removeClass().addClass("strengthA");
                    break;
                case 2:
                    element.removeClass().addClass("strengthB");
                    break;
                case 3:
                    element.removeClass().addClass("strengthC");
                    break;
                default:
                    break;
            }
        } else {
            element.hide();
        }
    },
//    checkGroup:function (elements) {
//        for (var i = 0; i < elements.length; i++) {
//            if (elements[i].checked) {
//                return true;
//            }
//        }
//        return false;
//    },
//    checkSelectGroup:function (elements) {
//        for (var i = 0; i < elements.length; i++) {
//            if (elements[i].value == -1) {
//                return false;
//            }
//        }
//        return true;
//    },
//    showPassword:function (type) {
//        var v1 = $("#pwd").val(), s1 = $("#pwd").attr("sta"), c1 = document.getElementById("pwd").className, t1 = $("#pwd").attr("tabindex");
//        var v2 = $("#pwd2").val(), s2 = $("#pwd2").attr("sta"), c2 = document.getElementById("pwd2").className, t2 = $("#pwd2").attr("tabindex");
//        var P1 = $("<input type='" + type + "' value='" + v1 + "' sta='" + s1 + "' class='" + c1 + "' id='pwd' name='pwd' tabindex='" + t1 + "'/>");
//        $("#pwd").after(P1).remove();
//        $("#pwd").bind("keyup",
//            function () {
//                validateFunction.pwdstrength();
//            }).jdValidate(validatePrompt.pwd, validateFunction.pwd)
//        var P2 = $("<input type='" + type + "' value='" + v2 + "' sta='" + s2 + "' class='" + c2 + "' id='pwd2' name='pwd2' tabindex='" + t2 + "'/>");
//        $("#pwd2").after(P2).remove();
//        $("#pwd2").jdValidate(validatePrompt.pwd2, validateFunction.pwd2);
//    },
    
  //--------------------------------商家加盟      批发商     零售店    名称 --------------------------------
    mertName:function (option) {
        var format = validateRules.isUid(option.value);
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 32);
        if (!length && format) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        }
        else if (!length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        }
        else if (length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else if (validateRules.fullNumberName(option.value)) {
            validateSettings.error.run(option, option.prompts.error.fullNumberName);
        } else {
            if (!namestate || nameold != option.value) {
                if (nameold != option.value) {
                    nameold = option.value;
//                    alert(option.value);
                    option.errorEle.html("<span style='color:#999'>检验中……</span>");
                    $.getJSON("../../mertApply/isUsedMertName.do?propertyName=mertName&name=" + option.value, function (data) {
                    	 if (data.resultCode == 121) {
                    		 validateSettings.error.run(option, option.prompts.error.beUsed);
                             namestate = false;
                         } else {
                             validateSettings.succeed.run(option);
                             namestate = true;
                         }
                    })
                }
                else {
                    validateSettings.error.run(option, option.prompts.error.beUsed);
                    namestate = false;
                }
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    shortName:function (option) {
        var format = validateRules.isUid(option.value);
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 32);
        if (!length && format) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        }
        else if (!length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        }
        else if (length && !format) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        } else if (validateRules.fullNumberName(option.value)) {
            validateSettings.error.run(option, option.prompts.error.fullNumberName);
        } else {
            if (!namestate || nameold != option.value) {
                if (nameold != option.value) {
                    nameold = option.value;
                    option.errorEle.html("<span style='color:#999'>检验中……</span>");
                    $.getJSON("../../mertApply/isUsedMertName.do?propertyName=shortName&name=" + option.value, function (data) {
                    	 if (data.resultCode == 121) {
                    		 validateSettings.error.run(option, option.prompts.error.beUsed);
                             namestate = false;
                         } else {
                             validateSettings.succeed.run(option);
                             namestate = true;
                         }
                    })
                }
                else {
                    validateSettings.error.run(option, option.prompts.error.beUsed);
                    namestate = false;
                }
            }
            else {
                validateSettings.succeed.run(option);
            }
        }
    },
    //机构编码
    mertCode:function (option) {
        var format = validateRules.isEnglishOrNum(option.value);
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 32);
        if (!length && format) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        }
        else if (!length && !format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
        } else if (length && !format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
        }else if (validateRules.fullNumberName(option.value)) {
                validateSettings.error.run(option, option.prompts.error.fullNumberName);
        }else {
//                validateSettings.succeed.run(option);
        	 if (!namestate || nameold != option.value) {
                 if (nameold != option.value) {
                     nameold = option.value;
                     option.errorEle.html("<span style='color:#999'>检验中……</span>");
//                     alert(option.value);
                     $.getJSON("../../mertApply/isUsedMertName.do?propertyName=mertCode&name=" + option.value, function (data) {
                     	 if (data.resultCode == 121) {
                     		 validateSettings.error.run(option, option.prompts.error.beUsed);
                              namestate = false;
                          } else {
                              validateSettings.succeed.run(option);
                              namestate = true;
                          }
                     })
                 }
                 else {
                     validateSettings.error.run(option, option.prompts.error.beUsed);
                     namestate = false;
                 }
             }
             else {
                 validateSettings.succeed.run(option);
             }
         }
    },
    //税号
    taxRegCtfNo:function (option) {
        var format = validateRules.fullNumberName(option.value);
        var length = validateRules.mustLength(option.value.replace(/[^\x00-\xff]/g, "**"), 15);
        if (!length && format) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        }
        else if (!length && !format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
        } else if (length && !format) {
                validateSettings.error.run(option, option.prompts.error.badFormat);
        }else {
                validateSettings.succeed.run(option);
         }
    },
    // 手机或者电话
    mobileOrPhone:function (option) {
        var format1 = validateRules.isTel(option.value);
        var format2 = validateRules.isMobile(option.value);
        if(!format1 && !format2) {
            validateSettings.error.run(option, option.prompts.error.badFormat);
        }
        else {
            validateSettings.succeed.run(option);
        }
    },
    
    joinAddress:function (option) {
        var length = validateRules.betweenLength(option.value.replace(/[^\x00-\xff]/g, "**"), 4, 50);
        if (!length) {
            validateSettings.error.run(option, option.prompts.error.badLength);
        } 
           else {
                validateSettings.succeed.run(option);
            }
    },
    
    FORM_submit:function (elements) {
        var bool = true;
        for (var i = 0; i < elements.length; i++) {
            if ($(elements[i]).attr("sta") == 2) {
                bool = true;
            } else {
                bool = false;
                break;
            }
        }
        
        return bool;
    }
};

//function sendMobileCode() {
//    var mobile = $("#mobileInfo").val();
//    if (validateRules.isNull(mobile) || !validateRules.isMobile(mobile)) {
//        $("#mobileInfo_error").text("手机号码格式有误，请输入以13/14/15/18开头的11位数字。");
//        $("#mobileInfo_error").removeClass().addClass("error");
//        $("#mobileInfo").removeClass().addClass("text highlight2");
//        $("#mobileInfo_error").show();
//
//        return;
//    }
//
//    if ($("#sendMobileCode").attr("disabled")) {
//        return;
//    }
//
//    $("#sendMobileCode").attr("disabled", "disabled");
//
//    jQuery.ajax({
//        type:"get",
//        url:"sendMobileCode?mobile=" + $("#mobileInfo").val() + "&r=" + Math.random(),
//        success:function (result) {
//            if (result) {
//                var obj = eval(result);
//                if (obj.rs == 1) {
//                    $("#mobileCode_error").hide();
//                    $(".ftx-01").text(120);
//                    $("#countDown").show();
//                    setTimeout(countDown, 1000);
//                    $("#sendMobileCode").removeClass().addClass("btn btn-15").attr("disabled", "disabled");
//                    $("#mobileCode").removeAttr("disabled");
//                }
//                if (obj.rs == -1) {
//                    $("#mobileInfo_error").text("手机号码格式有误，请输入以13/14/15/18开头的11位数字。");
//                    $("#mobileInfo_error").removeClass().addClass("error");
//                    $("#mobileInfo_error").show();
//                    $("#mobileInfo").removeClass().addClass("text highlight2");
//                }
//                if (obj.info) {
//                    mobileCodeError(obj.info);
//                }
//                if (obj.remain) {
//                    $(".ftx-01").text(120);
//                    $("#countDown").show();
//                    setTimeout(countDown, 1000);
//                    $("#sendMobileCode").removeClass().addClass("btn btn-15").attr("disabled", "disabled");
//                    $("#mobileCode").removeAttr("disabled");
//                    $("#mobileCode_error").text(obj.remain);
//                    $("#mobileCode_error").removeClass().addClass("error");
//                    $("#mobileCode_error").show();
//                }
//                if (obj.rs == -2) {
//                    mobileCodeError("网络繁忙，请稍后重新获取验证码");
//                }
//            }
//        }
//    });
//}

//function mobileCodeError(content) {
//    $("#mobileCode_error").text(content);
//    $("#mobileCode_error").removeClass().addClass("error");
//    $("#mobileCode_error").show();
//    $("#sendMobileCode").removeClass().addClass("btn").removeAttr("disabled");
//}

//function countDown() {
//    var time = $(".ftx-01").text();
//    $(".ftx-01").text(time - 1);
//    if (time == 1) {
//        $("#countDown").hide();
//        $("#mobileCode_error").hide();
//        $("#sendMobileCode").removeClass().addClass("btn").removeAttr("disabled");
//    } else {
//        setTimeout(countDown, 1000);
//    }
//}

function strTrim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

function changeVerc() {
    if ($("#emType").val == "email") {
        $("#authcode").focus();
    }
}