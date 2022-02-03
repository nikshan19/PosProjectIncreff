
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventoryreport";
}


function getEmployeeList(){
	var url = getEmployeeUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		
	   		displayEmployeeList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}


//UI DISPLAY METHODS

function displayEmployeeList(data){
	console.log('Printing employee data');
	var $tbody = $('#inventoryreport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}



function pdf(){
var baseUrl = $("meta[name=baseUrl]").attr("content")
	var url = baseUrl + "/api/inventoryreport/pdf";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data, textStatus, xhr) {
	   		
	   		location.href = "http://localhost:8080/PosProjectIncreff/api/inventoryreport/pdf";	    //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError(data.responseText);
	   }
	});
}


function showError(msg){
	
	$('#EpicToast').html('<div class="d-flex">'
    			+'<div class="toast-body">'
      			+''+msg+''
   				+' </div>'
    			+'<button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>'
  				+'</div>'
				
	);
	
	
	var option={
		animation:true,
		delay:2000
	};
	var t = document.getElementById("EpicToast");
	var tElement = new bootstrap.Toast(t, option);
	tElement.show();
	
}

function showSuccess(msg){
	
	$('#EpicToast1').html('<div class="d-flex">'
    			+'<div class="toast-body">'
      			+''+msg+''
   				+' </div>'
    			+'<button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>'
  				+'</div>'
				
	);
	
	
	var option={
		animation:true,
		delay:2000
	};
	var t = document.getElementById("EpicToast1");
	var tElement = new bootstrap.Toast(t, option);
	tElement.show();
	
}


//HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
   
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    
    return json;
}


//INITIALIZATION CODE
function init(){
	$("#pdf").click(pdf);
}

$(document).ready(init);
$(document).ready(getEmployeeList);