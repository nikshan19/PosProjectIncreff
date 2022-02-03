function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}


function getOrderList(){
	var loc = window.location.href;
	var id = loc.substring(loc.lastIndexOf('/') + 1)
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Employee data fetched");
	   		console.log(data);	
	   		displayOrderItemList(data); 
	   		    //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}
function updateOrderItem(event){
	var loc = window.location.href;
	var id = loc.substring(loc.lastIndexOf('/') + 1)
		
	var url = getOrderItemUrl()+"/"+id;
	//Set the values to update
	var $form = $("#editorder-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(data, textStatus, xhr) {
	   		
	   		
	   		getOrderList();
	   		    //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});

	return false;
}


function displayOrderItemList(data){
	console.log('Printing employee data');
	var $tbody = $('#editorder-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		//var buttonHtml = '<button onclick="deleteEmployee(' + e.id + ')">delete</button>'
		//buttonHtml += ' <button onclick="editOrder(' + e.id + ')">edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.mrp + '</td>'
		
		+ '</tr>';
        $tbody.append(row);
	}
}


function formValidation(){
	
	var x = $("#editorder-form input[name=id]").val();
  	var y = $("#editorder-form input[name=barcode]").val();  
  	var a = $("#editorder-form input[name=quantity]").val();
  	var b = $("#editorder-form input[name=mrp]").val();  
  	var pattern= /^\d+(?:\.\d{1,2})?$/;
  	if(y==""||y==null){
	showError("Please fill all the input fields");
	return false;
}
	else if(isNaN(x)||x<=0){
		showError("Enter valid Id");
		return false;
	}
	else if(isNaN(a)||a<=0){
		showError("Enter valid Quantity");
		return false;
	}
	else if(!pattern.test(b)||b<=0 ){
		showError("Enter valid Mrp");
		return false;
	}
else{
	updateOrderItem();
}
	
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

function pdf(){
	var loc = window.location.href;
	var id = loc.substring(loc.lastIndexOf('/') + 1)
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	var url = baseUrl + "/api/pdf" + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function() {
	   		
	   		location.href = "http://localhost:8080/PosProjectIncreff/api/pdf/"+id;	    //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
	
	
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
	$('#add-editorder').click(formValidation);
	$('#pdf-data').click(pdf);
}

$(document).ready(init);
$(document).ready(getOrderList);
