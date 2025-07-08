(function ($, window) {
    //自定义属性
    var _customerAttr1 = "dialogid";//dialogid的
    var _customerAttr2 = "dialogidfilter";//dialogidfilter背景框的
    var _customerAttr3 = "dialogidcontent";//显示内容的
    var _guidArray = [];
    //生成GUID
    var _newGuid = function newGuid() {
        var guid = "";
        for (var i = 1; i <= 32; i++) {
            var n = Math.floor(Math.random() * 16.0).toString(16);
            guid += n;
            if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
                guid += "-";
        }
        var flag = true;
        for (var i = 0; i < _guidArray.length; i++) {
            if (_guidArray[i] == guid) {
                flag = false;
                break;
            }
        }
        if (flag == false) {
            guid = _newGuid();
        } else {
            _guidArray.push(guid);
        }
        return guid;
    }
    //关闭
    var _close = function closeDialogid(ele)
    {
        var _self = $(ele);
        var _guid = _self.attr(_customerAttr3);
        //动画关闭
        $("[" + _customerAttr1 + "='" + _guid + "'],[" + _customerAttr2 + "='" + _guid + "']").animate({
            height: 'toggle'
        });
    }


    $.fn.acedialogclose = function (options) {
        // 默认参数
        var defaults = {
           
        };
        _close(this);
        
        // 插件配置
        var opt = $.extend(defaults, options);
        return this;
    }
   


    $.fn.acedialog = function (options) {
        // 默认参数
        var defaults = {
            title: "",
            width:"400px",
            height:"200px",
            buttons: null
        };
       
        
        var _self = $(this);
        // 插件配置
        var opt = $.extend(defaults, options);
        
        if (_self.attr(_customerAttr3) == undefined) {
            var _guid = _customerAttr1 + _newGuid();
            _self.attr(_customerAttr3, _guid);
            //第一次打开
            var containerDiv = $("<div " + _customerAttr1 + "=\"" + _guid + "\" class=\"widget-container-span modal fade in\" style=\"overflow:hidden\"></div>");
            //设置宽度
            var widgetBoxDiv = $("<div class=\"widget-box\" style=\"width: " + opt.width + "; margin: 10px auto;border:solid 1px #7db4d8;\"></div>");
            containerDiv.append(widgetBoxDiv);
            var widgetHeaderDiv = $("<div class=\"widget-header header-color-blue\" style=\"background-color:#7db4d8;\"></div>");
            widgetBoxDiv.append(widgetHeaderDiv);
            //标题
            widgetHeaderDiv.append("<h5>" + opt.title + "</h5>");
            var widgetToolbarDiv = $(" <div class=\"widget-toolbar\"></div>");
            widgetHeaderDiv.append(widgetToolbarDiv);
            //工具栏
            widgetToolbarDiv.append("<a href=\"#\" data-action=\"collapse\"><i class=\"icon-chevron-up\"></i></a>&nbsp;&nbsp;");
            var closeBtn = $("<a href=\"#\" style=\"color:#ffd9d5;\"><i class=\"icon-remove\"></i></a>");
            widgetToolbarDiv.append(closeBtn);
            closeBtn.bind("click", function () {
                _close(_self);
                return false;
            });


            var widgetBodyDiv = $("<div class=\"widget-body\"></div>")
            widgetBoxDiv.append(widgetBodyDiv);
            var widgetMainDiv = $("<div class=\"widget-main slim-scroll\" data-height=\"" + opt.height + "\"></div>");
            widgetBodyDiv.append(widgetMainDiv);
            //添加主体内容
            _self.show();
            widgetMainDiv.append(_self);
            if (opt.buttons && opt.buttons.length == 2) {
                //添加按钮
                var widgetToolboxDiv = $("<div class=\"widget-toolbox padding-8 clearfix\"></div>");
                widgetBodyDiv.append(widgetToolboxDiv);
                //左按钮
                var leftButton = $("<button type=\"button\" class=\"btn btn-xs btn-danger pull-right\"></button>");
                widgetToolboxDiv.append(leftButton);
                leftButton.append("<i class=\"icon-remove\"></i>");
                leftButton.append("<span class=\"bigger-110\">" + opt.buttons[0].title + "</span>");
                leftButton.bind("click", function () {
                    if ($.isFunction(opt.buttons[0].callback)) {
                        //返回button对象
                        opt.buttons[0].callback.apply(this);
                    }
                });
                //右按钮
                var rightButton = $("<button type=\"button\" class=\"btn btn-xs btn-success pull-right\" style=\"margin-right:10px;\"></button>");
                widgetToolboxDiv.append(rightButton);
                rightButton.append("<i class=\"icon-ok icon-on-right\"></i>");
                rightButton.append("<span class=\"bigger-110\">" + opt.buttons[1].title + "</span>");


                rightButton.bind("click", function () {
                    if ($.isFunction(opt.buttons[1].callback)) {
                        //返回button对象
                        opt.buttons[1].callback.apply(this);
                    }
                });
            }
            containerDiv.hide();
            ////动画显示
            $("body").append(containerDiv);
            containerDiv.animate({
                height: 'toggle'
            });
            // 滚动条
            containerDiv.find(".slim-scroll").each(function () {
                var $this = $(this);
                $this.slimScroll({
                    height: $this.data('height') || 100,
                    railVisible: true
                });
            });
            //动画显示
            var modalBackdropDiv = $("<div " + _customerAttr2 + "=\"" + _guid + "\" class=\"modal-backdrop fade in\"></div>");
            modalBackdropDiv.hide();
            $("body").append(modalBackdropDiv);
            modalBackdropDiv.animate({
                height: 'toggle'
            });
        } else {
            //显示
            _close(this);
        }
        return this;
    }


})(jQuery, window);