function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}


function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getOrderItem2Url(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem2";
}


function getOrderItemList(){
	var url = getOrderItemUrl()+"/"+0;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   			
	   		displayOrderItemList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}



function displayOrderItemList(data){
	//console.log('Printing orderitem data');
	var $tbody = $('#editorder-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button type="button" class="btn btn-outline-danger border-0" data-toggle="tooltip"  title="Delete" onclick="deleteOrderItem(' + e.id + ')"><i class="bi bi-trash"></i></button>'
		buttonHtml += ' <button type="button" class="btn btn-outline-primary border-0" data-toggle="tooltip"  title="Edit" onclick="displayEditOrderItem(' + e.id + ')"><i class="bi bi-pen"></i></button>';
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
}

function addOrder(event){
	//Set the values to update
	
	
	var $form = $("#demo-form");
	var json = toJson($form);
	var url = getOrderUrl();


	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(data, textStatus, xhr) {
	   		
	   		//location.reload();
	   		getOrderItemList();
	   		//getEmployeeList(); 
	   		location.href = "http://localhost:8080/PosProjectIncreff/ui/order2";
	   		showError("Order Placed!");
	 		//count=0;
	   		    //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});

return false;
}

function updateOrderItem(event){
	
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
			$('#edit-cart-modal').modal('toggle');
	   		showSuccess("Cart Updated");		
	   		getOrderItemList();     //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});

	return false;
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

function displayOrderItem(data){
	$("#cart-edit-form input[name=barcode]").val(data.barcode);	
	var span = document.getElementById("spanB");
	span.innerHTML = "Product barcode: "+data.barcode;
	$("#cart-edit-form input[name=quantity]").val(data.quantity);
	$("#cart-edit-form input[name=mrp]").val(data.mrp);	
	$("#cart-edit-form input[name=id]").val(data.id);	
	$('#edit-cart-modal').modal('toggle');
}


function deleteOrderItem(id){
	var url = getOrderItem2Url() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data, textStatus, xhr) {
	   		showSuccess("Product deleted from cart");	
	   		getOrderItemList();     //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});
}




//HELPER METHOD

function formValidate1(){
	
		var a = $('#cart-edit-form input[name=barcode]').val()
		var b = $('#cart-edit-form input[name=quantity]').val()
		var c = $('#cart-edit-form input[name=mrp]').val()
		
		var pattern= /^\d+(?:\.\d{1,2})?$/;
		if(a.length==0){
			showError("All input fields must be filled");
			return false;
		}
		else if(b<=0){
			showError("Quantity should be greater than zero");
			return false;
		}
		else if(c<=0){
			showError("Price should be greater than zero");
			return false;
		}
		else if(!pattern.test(c)){
			showError("Invalid Price entered");
			return false;
			
		}
		else{
			updateOrderItem();
		}
		
	}
	

function showError(msg){
	
	$('#EpicToast').html('<div class="d-flex">'
    			+'<div class="toast-body">'
    			+'<span style="color:white; padding:5px; font-size: 1rem;"><i class="bi bi-x-circle"></i></span>'
      			+''+msg+''
   				+' </div>'
    			+'<button type="button" class="btn-close btn-close-white me-2 m-auto" data-dismiss="toast" aria-label="Close"></button>'
  				+'</div>'
				
	);
	
	
	var option={
		animation:true,
		delay:5000
	};
	var t = document.getElementById("EpicToast");
	var tElement = new bootstrap.Toast(t, option);
	tElement.show();
	
}

function showSuccess(msg){
	
	$('#EpicToast1').html('<div class="d-flex">'
    			+'<div class="toast-body">'
    			+'<span style="color:white; padding:5px; font-size: 1rem;"><i class="bi bi-check-circle"></i></span>'
      			+''+msg+''
   				+' </div>'
    			+'<button type="button" class="btn-close btn-close-white me-2 m-auto" data-dismiss="toast" aria-label="Close"></button>'
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

function refresh(){
	location.reload();
}



function addOrderItem2(event){
	

	var $form = $("#order-edit-form");
	
	var json = toJson($form);
	
	var url = getOrderItemUrl();


	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(data, textStatus, xhr) {
			$('#edit-order-modal').modal('toggle');
			$("#order-edit-form").trigger("reset");
	   		showSuccess("Product added to cart");	
			getOrderItemList();
	   		
	   		    //...
	   },
	   error: function(data, textStatus, xhr){
			
	   		showError("Error: "+data.responseText);
	   		
	   }
	});
	
return false;

}

function displayOrderList(id){
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		
	   		displayOrderItemList(data);
	   		
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});	
}


function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   	
	   		displayProductList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function displayProductList(data){
	//console.log('Printing product data');
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for(var n in data){
		var e = data[n];
		var buttonHtml = '<button type="button" class="btn btn-outline-primary border-0" onclick="addE(' + e.id + ')">Add to Cart</button>';
		var row = '<tr>'
		+ '<td>' + c + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
        c++;
	}
}

function addE(id){
	
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		
		var barcode;
		var mrp;
		for(var n in data ){
			var e = data[n];
		
			if(e.id==id){
				barcode = e.barcode;
				mrp = e.mrp;
		}
	}
	
	
	$("#order-edit-form input[name=barcode]").val(barcode);	
	var span = document.getElementById("spanBB");
	span.innerHTML = "Product barcode: "+barcode;
	$("#order-edit-form input[name=mrp]").val(mrp);	
	$('#edit-order-modal').modal('toggle');
	   	
	   		  
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
	
	
	
	
}

function displayEditEmployee(id){
	var url = getEmployeeUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   			
	   		displayEmployee(data);
	   		displayOrderItem();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});	
}

function displayEmployee(data){
	$("#order-edit-form input[name=dateTime]").val(data.dateTime);	
	$("#order-edit-form input[name=id]").val(data.id);
		
	$('#edit-order-modal').modal('toggle');
}

function formValidate2(){
	
		var a = $("#order-edit-form input[name=barcode]").val()
		var b = $("#order-edit-form input[name=quantity]").val()
		var c = $("#order-edit-form input[name=mrp]").val()
		
		
		var pattern= /^\d+(?:\.\d{1,2})?$/;
		if(a.length==0){
			showError("All input fields must be filled");
			return false;
		}
		else if(b<=0){
			showError("Quantity should be greater than zero");
			return false;
		}
		else if(c<=0){
			showError("Price should be greater than zero");
			return false;
		}
		else if(!pattern.test(c)){
			showError("Invalid Price entered");
			return false;
			
		}
		else{
			addOrderItem2();
		}
		
	}






//INITIALIZATION CODE
function init(){
	
	
	$('#place-order').click(addOrder);
	$('#update-product').click(formValidate1);
	$('#refresh-data').click(refresh);
	$('#update-employee').click(formValidate2);
}
$(document).ready(init);
$(document).ready(getOrderItemList);
$(document).ready(getProductList);



