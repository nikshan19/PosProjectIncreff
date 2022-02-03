
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}
function getOrderItem2Url(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem/edit";
}

var oId;
function getOrderItemList(id){
	oId = id;
	var url = getOrderItemUrl()+"/"+id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   			
	   		displayOrderItemList(data);
	   		     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}
function displayOrderItemList(data){
	console.log('Printing orderitem data');
	var $tbody = $('#editorder-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button type="button" class="btn btn-outline-danger border-0" onclick="deleteOrderItem(' + e.id + ')"><i class="bi bi-trash"></i></button>'
		buttonHtml += ' <button type="button" class="btn btn-outline-primary border-0" onclick="displayEditOrderItem(' + e.id + ')"><i class="bi bi-pen"></i></button>';
		var row = '<tr>'
		+ '<td>' + c + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
        c++;
	}
	$('#edit-order-modal').modal('toggle');
}

function displayEditOrderItem(id){
	
	var url = getOrderItem2Url() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		
	   		displayOrderItem(data);     //...
	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});	
}
function getOrderItem(id){
	
	var url = getOrderItem2Url() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		
			if(data.length==0){
				getOrderItemList(data.orderId);
			}
			else{
	   		getOrderItemList(data.orderId);
	   		$('#edit-order-modal').modal('toggle');
	   		}
	   		    //...
	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});	
}

function getOrderId(id){
	var url = getOrderItem2Url() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		return data.orderId;

	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});	

}


function displayOrderItem(data){
	$("#cart-edit-form input[name=barcode]").val(data.barcode);	
	$("#cart-edit-form input[name=quantity]").val(data.quantity);
	$("#cart-edit-form input[name=mrp]").val(data.mrp);	
	$("#cart-edit-form input[name=id]").val(data.id);	
	$('#edit-cart-modal').modal('toggle');
}

function deleteOrderItem(id){
	var r = getOrderId(id);
	console.log(r);
	var url = getOrderItem2Url() + "/" + id;
	
	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data, textStatus, xhr) {
	   		showSuccess("Product deleted from cart");
	   			getOrderItem(r);
	   		    //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});
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


function updateOrderItem(event){
	$('#edit-cart-modal').modal('toggle');
	//Get the ID
	var id = $("#cart-edit-form input[name=id]").val();	
	var url = getOrderItem2Url() + "/" + id;

	//Set the values to update
	var $form = $("#cart-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(data, textStatus, xhr) {
	   		showSuccess("Order Updated");
	   		getOrderItem(id);		
	   		     //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});

	return false;
}






function displayEmployeeList(data){
	console.log('Printing employee data');
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	var c=1;
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button type="button" class="btn btn-outline-primary border-0" onclick="addE(' + e.id + ')"><i class="bi bi-receipt"></i></button>'
		if(e.toggle==0){
		buttonHtml+= '<button type="button"  class="btn btn-outline-primary border-0" onclick="getOrderItemList(' + e.id + ')"><i class="bi bi-pencil"></i></button>'
		}
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.dateTime + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
        c++;
	}
}

function addE(id){
	
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	var url = baseUrl + "/api/order/pdf/"+id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function() {
	   		updateOrder(id);
	   		location.href = "http://localhost:8080/PosProjectIncreff/api/order/pdf/"+id;	
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
	
	
}
	
function updateOrder(id){
	var url = getEmployeeUrl() + "/update/" + id;

	$.ajax({
	   url: url,
	   type: 'GET',
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(data, textStatus, xhr) {
	   		showSuccess("Order Updated");
	   		getEmployeeList();		
	   		     //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});

	return false;
	
}



function placeOrder(){
	location.href = "http://localhost:8080/PosProjectIncreff/ui/placeorder";
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
	
	$('#refresh-data').click(getEmployeeList);
	$('#place-order').click(placeOrder);
	$('#update-product').click(updateOrderItem);
}

$(document).ready(init);
$(document).ready(getEmployeeList);




