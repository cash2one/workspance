// JavaScript Document
$(document).ready(function(e) {
   	nllist = $("#nl_list");
	if(nllist.mouseover()){
			nllist.find("li")
			.mouseover(function(){
					$(this).css({'background-color':'#F6F6F6'});
				})
			.mouseout(function(){
					$(this).css({'background-color':''});

				})
		}
});