var categories; // recense les différentes catégories d'armes

$(document).ready(function(){
	
	// appelle la méthode pour charger la base données dans la datatable
	loadDatatable();
	
	
	
	// déclaration d'une variable table;
	var table = $('#livrokazTable').DataTable();
	console.log(table);
	
	//Double click sur une ligne
	$('#livrokazTable tbody').on( 'dblclick', 'tr', function () {
	    let dataRow = table.row( this ).data();
	   // console.log(dataRow);
	    $("#id").val(dataRow.bookId);
		$("#title").val(dataRow.title);
		$("#price").val(dataRow.price);
		
		getAuthors();
		
		
	} );
	
	
	//Click sur Ajouter
	$("#btn-post").click(function() { 
		ajouterLivre($("#btn-post"), "GET", table);
	});
	
	//Click sur Modifier
	$("#btn-put").click(function() { 
		modifierLivre($("#btn-put"), "PUT");
	});
	
	//click sur Supprimer
	$("#btn-delete").click(function() {
		supprimerLivre();
		resetForm();
	});
	
	//click sur Reset
	$("#btn-reset").click(function() {
		resetForm(); // méthode qui met les valeurs des zones de textes du formulaire à blanc
	});

});

/**
 * Charge les données dans la DataTable (JQuery)
 */
function loadDatatable() {
	$('#livrokazTable').DataTable({
		"searching": false,
		"info": false,
		"columnDefs": [
	            {
	                "targets": [ 0 ],
	                "sortable" : true,
	                "visible": true
	            },
	            {
	                "targets": [ 1 ],
	                "sortable" : true,
	                "visible": true,
	            },
	            {
	                "targets": [ 2 ],
	                "sortable" : true,
	                "visible": true
	            }
	        ],
		"ajax" : {
			url : '/livrokaz/books',
			dataSrc : ''
		},
		"columns" : [ 
			{"data" : "bookId"},
			{"data" : "categorie"},
			{"data" : "title"}, ]
	});
	
}

function getAuthors() {
    var id = $("#id").val(); //on récupère la variable du formulaire 
    
  //Vidage de la liste déroulante
	$("#listeDeroulanteAuthors").children().remove(); 

    // on lance la méthode ajax pour faire le lien avec les méthodes back du constructeur
	$.ajax({
		type : "GET",						    // méthode GET
		contentType : "application/json",		// type de données
		url : "/livrokaz/authors/" + id,	
		data : {},		                        // tableau vide pour recevoir la reponse body du controleur
		dataType : 'json',						// on précise le mode de transfert
		cache : false,							// pas de cache sollicité
		timeout : 600000,						// délai d'attente
		success : function(data) {				// si ok

			authors = data;
			
			var listeDeroulante = document.getElementById("listeDeroulanteAuthors");
			$("#listeDeroulanteAuthors").children().remove(); 
			for (var i = 0; i < authors.length; i++) {
				
		        option = document.createElement("option");
		        option.textContent = authors[i].fullName;
		        listeDeroulante.appendChild(option);
		        
		        
		    }
			
			
			
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});
    
    // on recharge les données via la méthode reload()
	/*setTimeout( function () {
		var table = $('#livrokazTable').DataTable();
	    table.ajax.reload();
	}, 600 ); */	
}


function modifierLivre(button, httpVerb) {
	
	// on récupère les valeurs saisies	
	var id = $("#id").val();
	var title = $("#title").val();
	var price = $("#price").val();
	
	
	// on initialise l'url du back
	var url = "/livrokaz/updateBook/title="+title+"&price="+price+"&id="+id;
	
	
	// on désactive le bouton en cours 
	button.prop("disabled", true);
	
	console.log(url);

	// on lance la méthode ajax pour faire le lien avec les méthodes back du constructeur
	$.ajax({
		type : httpVerb,						// méthode GET ou PUT
		contentType : "application/json",		
		url : url,								
		data : {},		
		dataType : 'json',						
		cache : false,							
		timeout : 600000,						
		success : function(data) {				

			console.log("SUCCESS : ", data);
			button.prop("disabled", false);

			resetForm()
			
		},
		error : function(e) {			

			console.log("ERROR : ", e);
			button.prop("disabled", false);

		}
	});
	
	setTimeout( function () {
	var table = $('#livrokazTable').DataTable();
    table.ajax.reload();
}, 600 ); 
}


function supprimerLivre() {
    var id = $("#id").val(); //on récupère la variable du formulaire 

    // on lance la méthode ajax pour faire le lien avec les méthodes back du constructeur
    $.ajax({
        type : "DELETE",                            // méthode GET
        contentType : "application/json",        // type de données
        url : "/livrokaz/delbook/" + id,                // url destinatrice
        data : {},                                // tableau vide pour recevoir la reponse body du controleur
        dataType : 'json',                        // on précise le mode de transfert
        cache : false,                            // pas de cache sollicité
        timeout : 600000,                        // délai d'attente
        success : function(data) {                // si ok

            console.log("SUCCESS : ", data);
            resetForm()
        },
        error : function(e) {
            console.log("ERROR : ", e);
        }
    });
    
 // on recharge les données via la méthode reload()
	setTimeout( function () {
		var table = $('#livrokazTable').DataTable();
	    table.ajax.reload();
	}, 600 ); 
}

function resetForm() {
	$('#livrokaz-form')[0].reset();
	$("#listeDeroulanteAuthors").children().remove(); 
	
}






