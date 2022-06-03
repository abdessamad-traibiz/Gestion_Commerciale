
	var ok = true;
	
	function openNav() {  
	    document.getElementById("mySidenav").style.width = "260px";
	    document.getElementById("main").style.marginLeft = "260px"; 
	    ok=true;
	}
	
	function closeNav() {
		$("#btnNav").show();
	    document.getElementById("mySidenav").style.width = "0";
	    document.getElementById("main").style.marginLeft= "0"; 
	    ok=true;
	} 
	
	$(window).resize(function(e) {
		var viewportWidth = $(this).width();
		if(viewportWidth<=920) closeNav();
		if(viewportWidth>=920) openNav(); 
	}); 
	 
	$(document).ready(function(){
		$(".fa-angle-up,.fa-angle-down").parent().click(function(){ 
			$(this).find("i.fa-angle-down,i.fa-angle-up").toggleClass("fa-angle-down fa-angle-up");
		});
	}); 