(function ($) {
    $.fn.extend({
        helpInfo: function (options) {
            var settings = $.extend({ delay: 500, autoHide: false, pause: 100, animationSpeed: 100, placement: 'left', theme: 'blue', text: '' }, options);
            return this.each(function () {
                var control = $(this);
                var iconDirection = 'top';
                if (settings.placement == 'top')
                    iconDirection = 'bottom';
                if (settings.placement == 'bottom')
                    iconDirection = 'top';
                if (settings.placement == 'left')
                    iconDirection = 'right';
                if (settings.placement == 'right')
                    iconDirection = 'left';

                var closebtn = '';
                if (!settings.autoHide)
                    
                var toolTipContainer = $('<div class="xuf-container"><p class="xuf-body ' + settings.theme + '">'  +settings.text + '' +
                                        '' + '</p></div>');

                control.before(toolTipContainer);
                var delay = settings.delay;
                var toolTip = toolTipContainer;
                toolTip.css({display:'none'}).find('div').css({ display: 'none', opacity: 0 });
                var toolTipBody = $('.xuf-body', toolTipContainer);
                var toolTipIcon = $('.xuf-icon', toolTipContainer);
                var placement = settings.placement;
                var interval;
                control.mouseover(function () {
                    var position = $(this).offset();
                    var left = position.left;
                    var top = position.top;
                    if (placement == 'top') {
                        top -= toolTip.height();
                        var iconTop = toolTip.height();
                        toolTipIcon.css('top', iconTop - toolTipIcon.outerHeight());
                    }
                    if (placement == 'bottom') {
                        top += $(this).height() + toolTipIcon.outerHeight();
                        var iconTop = toolTip.position().top;
                        toolTipIcon.css('top', -toolTipIcon.outerHeight());
                    }
                    if (placement == 'left') {
                        //triangle placement
                        left -= toolTip.outerWidth();
                        var iconLeft = toolTipBody.position().left + toolTipBody.outerWidth() +100;
                        toolTipIcon.css('left', iconLeft);
                    }
                    if (placement == 'right') {
                        left += $(this).outerWidth();
                        //triangle placement
                        toolTipBody.css('left', toolTipIcon.outerWidth());
                        toolTipIcon.css('left', 0);
                    }
                    toolTip.css({ left: left, top: top });
                    interval = setTimeout(function () {
                        showToolTip(toolTip);
                    }, delay);
                }).mouseout(function () {
                    if (!settings.autoHide) {
                        clearTimeout(interval);
                    }
                }).keydown(function () {
                    clearTimeout(interval);
                });

                $('.xuf-close', toolTipContainer).click(function () {
                    hideToolTip(toolTip);
                });

                function showToolTip(toolTip) {
                    //toolTip.fadeIn('slow');
                    toolTip.css({ display: '' }).find('div').css('display', '').stop(false, true).animate({ opacity: 1 }, settings.animationSpeed, function () {
                        if (settings.autoHide) {
                            setTimeout(function () {
                                hideToolTip(toolTip);
                            }, settings.pause);
                        }
                    });
                }
                function hideToolTip(toolTip) {
                    //                    toolTip.fadeOut('slow');
                    toolTip.css({ display: 'none' }).find('div').stop(false, true).animate({ opacity: 0 }, settings.animationSpeed, function () {
                        $(this).css('display', 'none');
                    });
                }

            });
        }
    });
})(jQuery);