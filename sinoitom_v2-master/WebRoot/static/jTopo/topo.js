/* Navigation 左边导航*/

$(function(){

  $(window).resize(function()
  {
    if($(window).width() >= 765){
      $(".sidebar #nav").slideDown(350);
    }
    else{
      $(".sidebar #nav").slideUp(350); 
    }
  });


  $("#nav > li > a").on('click',function(e){
      if($(this).parent().hasClass("has_sub")) {
        e.preventDefault();
      }   

      if(!$(this).hasClass("subdrop")) {
        // hide any open menus and remove all other classes
        $("#nav li ul").slideUp(350);
        $("#nav li a").removeClass("subdrop");
        
        // open our new menu and add the open class
        $(this).next("ul").slideDown(350);
        $(this).addClass("subdrop");
      }
      
      else if($(this).hasClass("subdrop")) {
        $(this).removeClass("subdrop");
        $(this).next("ul").slideUp(350);
      } 
      if($(this).children('span').children('i').hasClass('icon-chevron-down')){
        $(this).children('span').children('i').removeClass('icon-chevron-down');
        $(this).children('span').children('i').addClass('icon-chevron-up');
        $(this).parent('li').siblings('li').children('a').children('span').children('i').addClass('icon-chevron-down')
      }else{
        $('#nav li a').children('span').children('i').removeClass('icon-chevron-up');
        $('#nav li a').children('span').children('i').addClass('icon-chevron-down');
      }
      /*else if($('#nav li a').children('span').children('i').hasClass('icon-chevron-up')){
        $('#nav li a').children('span').children('i').removeClass('icon-chevron-up');
        $('#nav li a').children('span').children('i').addClass('icon-chevron-down');
      }
*/
  });
});