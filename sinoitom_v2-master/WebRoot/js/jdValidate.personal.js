$.extend(validateFunction, {
    FORM_validate: function () {
//    	$("#loginname").jdValidate(validatePrompt.loginname, validateFunction.loginname, true);
    	
        $("#username").jdValidate(validatePrompt.username, validateFunction.username, true);
        $("#pwd").jdValidate(validatePrompt.pwd, validateFunction.pwd, true);
        $("#pwd2").jdValidate(validatePrompt.pwd2, validateFunction.pwd2, true);
       // $("#email").jdValidate(validatePrompt.email, validateFunction.mail, true);
        
//        $("#mobile").jdValidate(validatePrompt.mobile, validateFunction.mobileInfo,true);
        
        $("#authcode").jdValidate(validatePrompt.authcode,validateFunction.authcode,true);
        return validateFunction.FORM_submit(["#username", "#pwd", "#pwd2","#authcode"])
    }
});
var isSubmit = false;
//调用
setTimeout(function () {
    $("#username").get(0).focus();
}, 0);

$("#loginname").jdValidate(validatePrompt.loginname, validateFunction.loginname);
$("#mobile").jdValidate(validatePrompt.mobile, validateFunction.mobileInfo);


$("#username").jdValidate(validatePrompt.username, validateFunction.username);
$("#pwd").bind("keyup",
    function () {
        validateFunction.pwdstrength();
    }).jdValidate(validatePrompt.pwd, validateFunction.pwd)
$("#pwd2").jdValidate(validatePrompt.pwd2, validateFunction.pwd2);
$("#email").jdValidate(validatePrompt.email, validateFunction.mail);
$("#authcode").jdValidate(validatePrompt.authcode, validateFunction.authcode);
$("#referrer").bind("keydown",
    function () {
        $(this).css({"color": "#333333", "font-size": "14px"});
    }).bind("keyup",
    function () {
        if ($(this).val() == "" || $(this).val() == "可不填") {
            $(this).css({ "color": "#999999", "font-size": "12px" }).jdValidate(validatePrompt.referrer, validateFunction.referrer, "可不填");
        }
    }).bind("blur",
    function () {
        if ($(this).val() == "" || $(this).val() == "可不填") {
            $(this).css({"color": "#999999", "font-size": "12px"}).jdValidate(validatePrompt.referrer, validateFunction.referrer, "可不填");
        }
    }).bind("focus", function () {
        if ($(this).val() == "可不填") {
            $(this).val("")
        }

    })
//$("#authcode").jdValidate(validatePrompt.authcode, validateFunction.authcode);
$("#viewpwd").bind("click", function () {
    if ($(this).attr("checked") == true) {
        validateFunction.showPassword("text");
        $("#o-password").addClass("pwdbg");
    } else {
        validateFunction.showPassword("password");
        $("#o-password").removeClass("pwdbg");
    }
});

$("#registsubmit").click(function () {
    if (isSubmit) {
        return
    }
    isSubmit = true;
    $(this).attr({ "disabled": "disabled" }).attr({ "value": "提交中,请稍等" });
    var flag = validateFunction.FORM_validate();
    if (flag) {
    	  $.ajax({
              type: "POST",
              url: "../web/customer/customerUser/register.do",
              contentType: "application/x-www-form-urlencoded; charset=utf-8",
              data: $("#formpersonal").serialize(),
              success: function (data) {
                  if (data) {
                	  var obj = eval('('+data+')');
//                      if (obj.authCode) {
//                          changeVerc();
//                          changeValCode(this);
//                          alert(obj.authCode);
//                          $("#registsubmit").removeAttr("disabled");
//                          $("#registsubmit").attr({ "value": "同意以下协议，提交" });
//                          isSubmit = false;
//                          return;
//                      }
                      if (obj.resultCode=100) {
                          window.location = obj.result;
                          return;
                      }
                  }
              }
          });
    } else {
        $("#registsubmit").removeAttr("disabled");
        $("#registsubmit").attr({ "value": "同意以下协议，提交" });
        isSubmit = false;
    }
});

//$("#mertapplyregistsubmit").click(function () {
//    if (isSubmit) {
//        return
//    }
//    isSubmit = true;
//    $(this).attr({ "disabled": "disabled" }).attr({ "value": "提交中,请稍等" });
//    var flag = validateFunction.FORM_validate();
//    if (flag) {
//    	  $.ajax({
//              type: "POST",
//              url: "../register/mertApplyRegister.do",
//              contentType: "application/x-www-form-urlencoded; charset=utf-8",
//              data: $("#formpersonal").serialize(),
//              success: function (data) {
//                  if (data) {
//                	  var obj = eval('('+data+')');
////                      if (obj.authCode) {
////                          changeVerc();
////                          changeValCode(this);
////                          alert(obj.authCode);
////                          $("#registsubmit").removeAttr("disabled");
////                          $("#registsubmit").attr({ "value": "同意以下协议，提交" });
////                          isSubmit = false;
////                          return;
////                      }
//                      if (obj.resultCode=100) {
//                          window.location = obj.result;
//                          return;
//                      }
//                  }
//              }
//          });
//    } else {
//        $("#mertapplyregistsubmit").removeAttr("disabled");
//        $("#mertapplyregistsubmit").attr({ "value": "同意以下协议，提交" });
//        isSubmit = false;
//    }
//});

function verc() {
    $("#JD_Verification1").click();
    $("#authcode").focus();
}

/*$("#authcode").bind('keyup', function (event) {
    if (event.keyCode == 13) {
        $("#registsubmit").click();
    }
});*/
//$("#registsubmitframe").click(function () {
//    var flag = validateFunction.FORM_validate();
//    //window.parent.pageTracker._trackEvent('Button', 'Regist', 'iFrame');
//    if (flag) {
//        $(this).attr({ "disabled": "disabled" }).attr({ "value": "提交中..." });
//        var uuid = $("#JD_Verification1").attr("src").split("&uid=")[1].split("&")[0];
//        $.ajax({
//            type: "POST",
//            url: "../reg/doRegister?rtype=personal&nr=1&uuid=" + uuid + location.search.replace('?', '&'),
//            contentType: "application/x-www-form-urlencoded; charset=utf-8",
//            data: $("#formpersonalframe").serialize(),
//            success: function (result) {
//                if (result) {
//                    var obj = eval(result);
//                    if (obj.info) {
//                        verc();
//                        $("#registsubmitframe").removeAttr("disabled");
//                        $("#registsubmitframe").attr({ "value": "注册" });
//                        alert(obj.info);
//                        return;
//                    }
//                    if (obj.noAuth) {
//                        verc();
//                        window.parent.location = obj.noAuth;
//                        return;
//                    }
//
//                    if (obj.success == true) {
//                        if (obj.notnr) {
//                            window.parent.jdModelCallCenter.init(true);
//                            return;
//                        }
//
//                        try {
//                            $.ajax({
//                                type: "GET",
//                                url: obj.dispatchUrl,
//                                dataType: "jsonp",
//                                timeout: 1000,
//                                success: function (result) {
//                                    window.parent.jdModelCallCenter.init(true);
//                                    return;
//                                }
//                            });
//                        } catch (e) {
//                            window.parent.jdModelCallCenter.init(true);
//                            return;
//                        }
//                    }
//                }
//            }
//        });
//    }
//});
//$("#protocol").click(function () {
//    if ($("#protocol").attr("checked") != true) {
//        $("#registsubmitframe").attr({ "disabled": "disabled" });
//    }
//    else {
//        $("#registsubmitframe").removeAttr("disabled");
//    }
//});