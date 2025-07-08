
/*
 * Superfish v1.4.8 - jQuery menu widget
 * Copyright (c) 2008 Joel Birch
 *
 * Dual licensed under the MIT and GPL licenses:
 * 	http://www.opensource.org/licenses/mit-license.php
 * 	http://www.gnu.org/licenses/gpl.html
 *
 * CHANGELOG: http://users.tpg.com.au/j_birch/plugins/superfish/changelog.txt
 */

;(function($){
	$.fn.superfish = function(op){

		var sf = $.fn.superfish,
			c = sf.c,
			$arrow = $(['<span class="',c.arrowClass,'"> &#187;</span>'].join('')),
			over = function(){
				var $$ = $(this), menu = getMenu($$);
				clearTimeout(menu.sfTimer);
				$$.showSuperfishUl().siblings().hideSuperfishUl();
			},
			out = function(){
				var $$ = $(this), menu = getMenu($$), o = sf.op;
				clearTimeout(menu.sfTimer);
				menu.sfTimer=setTimeout(function(){
					o.retainPath=($.inArray($$[0],o.$path)>-1);
					$$.hideSuperfishUl();
					if (o.$path.length && $$.parents(['li.',o.hoverClass].join('')).length<1 ){over.call(o.$path);}
				},o.delay);	
			},
			getMenu = function($menu){
				var menu = $menu.parents(['ul.',c.menuClass,':first'].join(''))[0];
				sf.op = sf.o[menu.serial];
				return menu;
			},
			addArrow = function($a){ $a.addClass(c.anchorClass).append($arrow.clone()); };
			
		return this.each(function() {  
			var s = this.serial = sf.o.length;
			var o = $.extend({},sf.defaults,op);
			o.$path = $('li.'+o.pathClass,this).slice(0,o.pathLevels).each(function(){
				$(this).addClass([o.hoverClass,c.bcClass].join(' '))
					.filter('li:has(ul)').removeClass(o.pathClass);
			});
			sf.o[s] = sf.op = o;
			
			$('li:has(ul)',this)[($.fn.hoverIntent && !o.disableHI) ? 'hoverIntent' : 'hover'](over,out).each(function() {
				if (o.autoArrows) addArrow( $('>a:first-child',this) );
			})
			.not('.'+c.bcClass)
				.hideSuperfishUl();
			
			var $a = $('a',this);
			$a.each(function(i){
				var $li = $a.eq(i).parents('li');
				$a.eq(i).focus(function(){over.call($li);}).blur(function(){out.call($li);});
			});
			o.onInit.call(this);
			
		}).each(function() {
			var menuClasses = [c.menuClass];
			if (sf.op.dropShadows  && !($.browser.msie && $.browser.version < 7)) menuClasses.push(c.shadowClass);
			$(this).addClass(menuClasses.join(' '));
		});
	};

	var sf = $.fn.superfish;
	sf.o = [];
	sf.op = {};
	sf.IE7fix = function(){
		var o = sf.op;
		if ($.browser.msie && $.browser.version > 6 && o.dropShadows && o.animation.opacity!=undefined)
			this.toggleClass(sf.c.shadowClass+'-off');
		};
	sf.c = {
		bcClass     : 'sf-breadcrumb',
		menuClass   : 'sf-js-enabled',
		anchorClass : 'sf-with-ul',
		arrowClass  : 'sf-sub-indicator',
		shadowClass : 'sf-shadow'
	};
	sf.defaults = {
		hoverClass	: 'sfHover',
		pathClass	: 'overideThisToUse',
		pathLevels	: 1,
		delay		: 800,
		animation	: {opacity:'show'},
		speed		: 'normal',
		autoArrows	: true,
		dropShadows : true,
		disableHI	: false,		// true disables hoverIntent detection
		onInit		: function(){}, // callback functions
		onBeforeShow: function(){},
		onShow		: function(){},
		onHide		: function(){}
	};
	$.fn.extend({
		hideSuperfishUl : function(){
			var o = sf.op,
				not = (o.retainPath===true) ? o.$path : '';
			o.retainPath = false;
			var $ul = $(['li.',o.hoverClass].join(''),this).add(this).not(not).removeClass(o.hoverClass)
					.find('>ul').hide().css('visibility','hidden');
			o.onHide.call($ul);
			return this;
		},
		showSuperfishUl : function(){
			var o = sf.op,
				sh = sf.c.shadowClass+'-off',
				$ul = this.addClass(o.hoverClass)
					.find('>ul:hidden').css('visibility','visible');
			sf.IE7fix.call($ul);
			o.onBeforeShow.call($ul);
			$ul.animate(o.animation,o.speed,function(){ sf.IE7fix.call($ul); o.onShow.call($ul); });
			return this;
		}
	});

})(jQuery);

$(document).ready(function() {
	$('.sf-menu>li').click(function(event) {
		$(this).addClass('current').siblings('li').removeClass('current');
		/*alert(123)*/
	});
	// $('.sf-menu>li>ul>li').click(function(event) {
	// 	$(this).parents('ul').siblings('a').addClass('current').parents('li').siblings('li').children('a').removeClass('current');
	// 	/*alert(123)*/
	// });

	// $('.sf-menu>li>ul>li>ul>li').click(function(event) {
	// 	$(this).parents('.sf-menu>li>ul').siblings('a').addClass('current').parents('li').siblings('li').children('a').removeClass('current');
	// 	/*alert(123)*/
	// });
	// var sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*$('.sf-menu li').length;
	var sfBoxUl=(parseInt($('.sf-menu li').css('width'))+10)*parseInt($('.sf-menu li').length);
	if($('.sf-menu li').length>10){
		var sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*10;
		$('.sf-menu-box').css('width',''+sfBoxW+'px');
	$('.sf-menu-box-arrow').css('width',''+sfBoxW+'px');
	}else{
		var sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*$('.sf-menu li').length;
		$('.sf-menu-box').css('width',''+sfBoxW+'px');
	$('.sf-menu-box-arrow').css('width',''+sfBoxW+'px');
	}
	var dis=-(parseInt($('.sf-menu li').css('width'))+10);
	
	
	$('.sf-menu').css('width',''+sfBoxUl+'px');
	$('.Scrollbtnr').click(function(event) {
		var sfMunuNum=parseInt(parseInt($('.sf-menu-box').css('width'))/parseInt($('.sf-menu li').css('width')));
		if(Math.abs(parseInt($('.sf-menu').css('left')))>=Math.abs(parseInt($('.sf-menu').css('width')))-(parseInt($('.sf-menu li').css('width'))+10)*sfMunuNum){
			
			$('.sf-menu').css('left',''+(parseInt($('.sf-menu').css('left')))+'px');
			
		}else{
						
			$('.sf-menu').css('left',''+(parseInt($('.sf-menu').css('left'))+dis)+'px');
		}
	});
	$('.Scrollbtnl').click(function(event) {
		if(parseInt($('.sf-menu').css('left'))==0){
			$('.sf-menu').css('left','0px');
		}else{
			$('.sf-menu').css('left',''+(parseInt($('.sf-menu').css('left'))-dis)+'px');
		}
	});
	window.onresize=function(){
		if(window.innerWidth<1366&&window.innerWidth>1150){
			sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*6;
			$('.sf-menu-box').css('width',''+sfBoxW+'px');
			$('.sf-menu-box-arrow').css('width',''+sfBoxW+'px');
			$('#menu_bar').css('width','50%');
			$('.top_logo').css({'width':'23%','display':'block',});
			$('.top_qbar').css({'width':'27%','display':'block',});
		}else if(window.innerWidth<1150&&window.innerWidth>1000){
			sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*5;
			$('.sf-menu-box').css('width',''+sfBoxW+'px');
			$('.sf-menu-box-arrow').css('width',''+sfBoxW+'px');
			$('#menu_bar').css('width','47%');
			$('.top_logo').css({'width':'23%','display':'block',});
			$('.top_qbar').css({'width':'30%','display':'block',});
		}else if(window.innerWidth<1000&&window.innerWidth>875){
			sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*4;
			$('.sf-menu-box').css('width',''+sfBoxW+'px');
			$('.sf-menu-box-arrow').css('width',''+sfBoxW+'px');
			$('#menu_bar').css('width','45%');
			$('.top_logo').css({'width':'23%','display':'block',});
			$('.top_qbar').css({'width':'32%','display':'block',});
		}else if(window.innerWidth<875&&window.innerWidth>660){
			sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*3;
			$('.sf-menu-box').css('width',''+sfBoxW+'px');
			$('.sf-menu-box-arrow').css('width',''+sfBoxW+'px');
			$('#menu_bar').css('width','45%');
			$('.top_logo').css('display','none');
			$('.top_qbar').css({'width':'55%','display':'block',});
		}else if(window.innerWidth<660&&window.innerWidth>450){
			sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*3;
			$('.sf-menu-box').css('width',''+sfBoxW+'px');
			$('.sf-menu-box-arrow').css('width',''+sfBoxW+'px');
			$('#menu_bar').css('width','100%');
			$('.top_logo').css('display','none');
			$('.top_qbar').css('display','none');
		}else if(window.innerWidth>=1366&&$('.sf-menu li').length<=10){
			var sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*$('.sf-menu li').length;

			$('.sf-menu-box').css('width',''+sfBoxW+'px');
			$('.sf-menu-box-arrow').css('width',''+sfBoxW+'px');
			$('#menu_bar').css('width','65%');
			$('.top_logo').css({'width':'15%','display':'block',});
			$('.top_qbar').css({'width':'20%','display':'block',});
		}else if(window.innerWidth>=1366&&$('.sf-menu li').length>10){
			var sfBoxW=(parseInt($('.sf-menu li').css('width'))+10)*10;

			$('.sf-menu-box').css('width',''+sfBoxW+'px');
			$('.sf-menu-box-arrow').css('width',''+sfBoxW+'px');
			$('#menu_bar').css('width','65%');
			$('.top_logo').css({'width':'15%','display':'block',});
			$('.top_qbar').css({'width':'20%','display':'block',});
		}
		if($('.sf-menu li').length<=10&&window.innerWidth>=1366){
			$('.Scrollbtnl').css('display','none');
			$('.Scrollbtnr').css('display','none');
			$('.sf-menu').css('left','0');
		}else{
			$('.Scrollbtnl').css('display','block');
			$('.Scrollbtnr').css('display','block');
			$('.sf-menu').css('left','0');
		}

}
	if(window.innerWidth>=1366 && $('.sf-menu li').length<=10){
		$('.Scrollbtnl').css('display','none');
		$('.Scrollbtnr').css('display','none');
		$('.sf-menu').css('left','0');
	}else{
		$('.Scrollbtnl').css('display','block');
		$('.Scrollbtnr').css('display','block');
		$('.sf-menu').css('left','0');
	}
});
