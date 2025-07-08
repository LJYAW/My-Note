
function resize() {
	var parent_column = $("#list").closest('[class*="col-"]');

	// resize to fit page size
	$(window).on('resize.jqGrid', function() {
		$("#list").jqGrid('setGridWidth', parent_column.width());
	});

	// resize on sidebar collapse/expand
	$(document).on(
			'settings.ace.jqGrid',
			function(ev, event_name, collapsed) {
				if (event_name === 'sidebar_collapsed'
						|| event_name === 'main_container_fixed') {
					// setTimeout is for webkit only to give time for DOM
					// changes and then redraw!!!
					setTimeout(function() {
                        var width = parent_column.width();
						$("#list").jqGrid('setGridWidth', parent_column.width());
								
					}, 20);
				}
			});

	// trigger window resize to make the grid get the correct size
	$(window).triggerHandler('resize.jqGrid');

	$(document).one('ajaxloadstart.page', function(e) {
		$.jgrid.gridDestroy(grid_selector);
		$('.ui-jqdialog').remove();
	});
}

function resizeById(jqGridId) {
	var parent_column = $("#"+jqGridId).closest('[class*="ui-jqgrid-view "]');

	// resize to fit page size
	$(window).on('resize.jqGrid', function() {
		$("#"+jqGridId).jqGrid('setGridWidth', parent_column.width());
	});

	// trigger window resize to make the grid get the correct size
	$(window).triggerHandler('resize.jqGrid');
}

function updatePagerIcons(table) {
	var replacement = {
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
			.each(function() {
				var icon = $(this);
				var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
//				alert('ui-icon '+replacement[$class]);
				if ($class in replacement)
					icon.attr('class', 'ui-icon ' + replacement[$class]);
			})
}

function changeTitles(listId) {
    var trId = "#" + listId + " tbody tr:nth-child(2)";
    var id = $(trId).attr('id');
    trId = "#" + id + " > td";
    var sss = $(trId);
    for (var i = 0; i < sss.length; i++) {
        var j = i + 1;
        var tdId = "#" + id + ' > td:nth-child(' + j + ')';
        var tdName = $(tdId).attr('aria-describedby');
        var tdStyle = $(tdId).attr('style');
        if (tdStyle.indexOf("text-align\:") >= 0) {
            var textAlign = tdStyle.split("text-align:")[1].split(";")[0];
            var thId = "#jqgh_" + tdName;
            $(thId).attr("style", 'text-align:' + textAlign);
        }
    }
}