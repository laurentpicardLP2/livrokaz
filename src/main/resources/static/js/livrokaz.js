var categories; // recense les différentes catégories d'armes

$(document).ready(function(){
	
	// appelle la méthode pour charger la base données dans la datatable
	loadDatatable();
	
	
	
	// déclaration d'une variable table;
	var table = $('#livrokazTable').DataTable();
	
	//Double click sur une ligne
	$('#livrokazTable tbody').on( 'dblclick', 'tr', function () {
	    let dataRow = table.row( this ).data();
	    console.log(dataRow);
	    $("#id").val(dataRow.bookId);
		$("#title").val(dataRow.title);
		$("#categorie").val(dataRow.categorie);
		
		//Vidage de la liste déroulante
		$("#listeDeroulanteAuthor").children().remove(); 
		
		//Remplissage liste déroulante
		var listeDeroulante = document.getElementById("listeDeroulanteAuthor");
		
		/*for (var i = 0; i < armes.length; i++) {
            option = document.createElement("option");
            option.textContent = armes[i].model;
            listeDeroulante.appendChild(option);
            
            for(var j = 0; j < categories.length; j++) {
            	if (armes[i].model == categories[j].description){
                    document.getElementById("checkboxes-" + j).checked = true;
                }
            }
        }*/
		
		
	} );
	
	
	//Click sur Ajouter
	$("#btn-post").click(function() { 
		ajouterJedi($("#btn-post"), "GET", table);
	});
	
	//Click sur Modifier
	$("#btn-put").click(function() { 
		ajouterJedi($("#btn-post"), "PUT", table);
	});
	
	//click sur Supprimer
	$("#btn-delete").click(function() {
		supprimerJedi(table);
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
		paging: false,
		"searching": false,
		"info": false,
		"columnDefs": [
	            {
	                "targets": [ 0 ],
	                "sortable" : true
	            },
	            {
	                "targets": [ 1 ],
	                "sortable" : true,
	                "visible": true
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

function getPublisher() {
    var id = $("#id").val(); //on récupère la variable du formulaire 

    // on lance la méthode ajax pour faire le lien avec les méthodes back du constructeur
    $.ajax({
        type : "DELETE",                            // méthode GET
        contentType : "application/json",        // type de données
        url : "/jedi/deluser/" + id,                // url destinatrice
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
	    table.ajax.reload();
	}, 300 ); 
}



function loadCheckboxes() {
	// on lance la méthode ajax pour faire le lien avec les méthodes back du constructeur
	$.ajax({
		type : "GET",						    // méthode GET
		contentType : "application/json",		// type de données
		url : "/jedi/categories",				// url destinatrice
		data : {},		                        // tableau vide pour recevoir la reponse body du controleur
		dataType : 'json',						// on précise le mode de transfert
		cache : false,							// pas de cache sollicité
		timeout : 600000,						// délai d'attente
		success : function(data) {				// si ok

			categories = data;
			//Vidage des checkboxes
			$("#checkboxes").children().remove(); 
			
			//Remplissage checkboxes
			var divCheckboxes = document.getElementById("checkboxes");
			var div = "";
			var label = "";
			var input = "";
		
			for (var i = 0; i < categories.length; i++) {
				div = document.createElement("div");
				div.className = "form-check";
				divCheckboxes.appendChild(div);
				label = document.createElement("label");
				label.className = "form-check-label";
				label.setAttribute("for", "checkboxes-" + i);
				input = document.createElement("input");
				input.className = "form-check-input";
				input.id = "checkboxes-" + i;
				input.setAttribute("type", "checkbox");
				div.appendChild(input);
				div.appendChild(label);
				label.textContent = categories[i].description + " (puissance : " + categories[i].puissance + ")";
				console.log(document.getElementById("checkboxes-" + i));
			}
		},
		error : function(e) {
			console.log("ERROR : ", e);
		}
	});
}




