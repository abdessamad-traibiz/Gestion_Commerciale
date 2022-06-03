
	function openFormulaireUpdateUser(id) { 
		if(id!=null) getUser(id);
		$("#formulaireNouveauUser").hide();
		$("#formulaireUpdateUser").show();
	} 
	
	function closeFormulaireUpdateUser() { 
	    $("tr").css("background-color","");
		$("#formulaireUpdateUser").hide();
	} 
	
	function openFormulaireNouveauUser() {  
		$("tr").css("background-color","");
		$("#formulaireUpdateUser").hide(); 
		$("#formulaireNouveauUser").show();
	} 
	
	function closeFormulaireNouveauUser() { 
		$("#formulaireNouveauUser").hide(); 
	} 
	
	function getUser(un)
	{ 
		$.ajax({ 
			url : wwwpath+"/users/getuser", 
			data : {    username : un+''  }, 
			type : "POST",
			dataType : "json",  
			success : function( json ) {  
				$("#usernameUserToUpdate").html(json.username); 
				document.formulaireUpdateUser.username.value=json.username; 
				document.formulaireUpdateUser.lastUsername.value=json.username;  
				$("#formulaireUpdateUser input[value='"+json.active+"']").prop('checked', true);
				$("tr").css("background-color","");
				$("#tr"+un).css("background-color", "yellow");
			},
			error : function( xhr, status ) { alert("Erreur dans le serveur..."); } 
		});
	}
	
	
	function showAddedOk(id,role)
	{
		if(role==null) role=""; 
		$("#ba_"+id).hide();
		$("#td1_"+id).html( $("<i class='fa fa-check text-success  float-right'>") ); 
		$("#br_"+id).remove();
		$("#td2_"+id).append( 
			$("<a id='br_"+id+"' class='fa fa-minus text-light btn-sm btn-secondary float-right' onclick='removeUserRole(\""+role+"\","+id+")'>")
		);	 
	}
	function addUserRole(role,id)
	{
		$.ajax({
	        type: "POST",
	        url: wwwpath+"/users/storeuserrole",
	        dataType: "json",
	        data: { "role": role },
	        success: function (result) 
	        {  
	        	if(result.length >=1){
	        		$("#alertUserRole").html(result[0]); return;
	        	} 
	        	showAddedOk(id,role);
				$("#divAddedRoles").append(
					"<div id='divAddedRole"+id+"' ><i class='fa fa-check'></> "+role+"</div>"	
				);
	        },
	        error:function(xhr, status){ alert("error serveur !"); console.log() }
		});
	}
	
	
	function removeUserRole(role,id)
	{
		$.ajax({
	        type: "POST",
	        url: wwwpath+"/users/removeuserrole",
	        dataType: "json",
	        data: { "role": role,"id":id },
	        success: function (result) 
	        {   
				$("#ba_"+id).show();
				$("#br_"+id).hide(); 
				$("#td1_"+id).html("");
				$("#divAddedRole"+id).remove();
	        },
	        error:function(xhr, status){ alert("error serveur !"); console.log(xhr); }
		});
	}
	
	function updateRole(id)
	{
		$("#alertUserRole").html("");
		$.ajax({
	        type: "POST",
	        url: wwwpath+"/users/updaterole",
	        dataType: "json",
	        data: { "id": id, "newrole":$("#trRole"+id+" span[class='su'] input").val()  },
	        success: function (result) 
	        {     
	        	if(result.length==1) { 
	        		console.log(result[0]);
	        		$("#alertRole span").html(result[0]);
		        	$("#alertRole").show(); 
		        	return;
	        	}  
	        	hideUR(id);
	        	$("#trRole"+id+" span[class='sr']").text($("#trRole"+id+" span[class='su'] input").val());
	        },
	        error:function(xhr, status){ alert("error serveur !"); console.log(xhr); }
		});
	}
	
	function addRole()
	{
		var nom = $("#inputNewRole").val();
		$.ajax({
	        type: "POST",
	        url: wwwpath+"/users/addrole",
	        dataType: "json",
	        data: { "role": nom },
	        success: function (result) 
	        {     
	        	if(result.length==1){
		        	$("#alertRole span").html(result[0]); 
	        		$("#alertRole").show();
	        	}
	        	else {
	        		var id = result[1];
	        		var tr = 
	        			"<tr id='trRole"+id+"'><td>"+
						"<div>"+
							'<a onclick="showUR('+id+');" class="fa-cog fa btn-sm btn-light"></a>'+
							'<span class="su" style="display:none">'+
								'<a onclick="hideUR('+id+');" class="fa fa-close btn-sm btn-light mr-2"></a>'+
								'<input value="'+nom+'" />'+
								'<a href="javascript:updateRole('+id+');"'+
									'class="fa fa-check btn-sm btn-primary"></a>'+ 
								'<a onclick="deleteRole('+id+')"'+
									'class="fa fa-trash btn-sm btn-danger text-light"></a>'+
							'</span> '+
							'<span class="sr">'+nom+'</span>'+
						'</div>'+
					'</td>'+
					'<td id="td1_'+id+'"></td>'+
					'<td id="td2_'+id+'">'+
						'<a  id="ba_'+id+'" class="btn-sm btn-success text-light float-right" '+
							'onclick="addUserRole(\''+nom+'\','+id+')">'+
							'<i class="fa fa-plus"></i>'+
						'</a>'+
					'</td></tr>';

	        		$("#tableRoles").append(tr);
	        	}
	        },
	        error:function(xhr, status){ alert("error serveur !"); console.log(xhr); }
		});
	}
	
	function deleteRole(id)
	{
		$.ajax({
	        type: "POST",
	        url: wwwpath+"/users/deleterole",
	        dataType: "json",
	        data: { "id": id },
	        success: function (result) 
	        {    
	        	if(result.length==0) $("#trRole"+id).remove();
	        },
	        error:function(xhr, status){ alert("error serveur !"); console.log(xhr); }
		});
	}