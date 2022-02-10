
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}

function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}


function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getOrderItem2Url(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem2";
}





var d;
function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   			
	   		d=data;
	   		//displayProductList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}



function formValidate1(){
	
		var a = $("#order-form input[name=barcode]").val()
		var b = $("#order-form input[name=quantity]").val()
		var c = $("#order-form input[name=mrp]").val()
		
		
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
			showError("Invalid price entered");
			return false;
			
		}
		else{
			addOrderItem();
		}
		
	}
	


function cartPage(){
	
	
	location.href = "http://localhost:8080/PosProjectIncreff/ui/cart";
	
}


function addOrderItem(event){
	
	
	var $form = $("#order-form");
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
	   		showSuccess("Product added to cart");	
	   		$('#order-form').trigger("reset");
	   		getOrderItemList();
	   		    //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   		
	   }
	});
	


return false;

// from here cart begins

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
	   		

	   		getOrderItemList();
	   		location.href = "http://localhost:8080/PosProjectIncreff/ui/order2";
	   		showError("Order Placed!");

	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});

return false;
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
	   		
	   		displayOrderItem(data);    
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


function formValidate11(){
	
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

function val(){
	$("#pd").hide();
	$("#detail-form").hide();
	
	var a = $("#order-form input[name=barcode]").val()
	if(a==""||a==null){
			showError("Enter barcode");
			return false;
		}
	else{
		
		display(a);
	}	
	
	
}

function display(barcode){
	
	var urll = getProductUrl();
	$.ajax({
	   url: urll,
	   type: 'GET',
	   success: function(data) {
			var name;
			var mrp;
			var id;
	   		var t=0;
		for(n in data){
		var e = data[n];
		//console.log(e.barcode+" "+barcode);
		if(e.barcode == barcode){
			//console.log(e.barcode+" "+barcode);
			name=e.name;
			mrp=e.mrp
			id = e.id
			t=1;
			break;
		}
		
	}
	if(t==0){
		showError("Product with given barcode doesnot exist");
		return false;
	}
	
	var span1 = document.getElementById("spanBB");
	span1.innerHTML = "Name: "+name;
	
	var span2 = document.getElementById("spanBBB");
	span2.innerHTML = "MRP: "+mrp;

	var url = getInventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		
	   		var span3 = document.getElementById("spanBBBB");
			span3.innerHTML = "Quantity: "+data.quantity;
	   		$("#pd").show();
			$("#detail-form").show();
	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});	
	
	
	
	
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	
	
	
	
	
	
}
function refresh(){
	location.reload();
}


//INITIALIZATION CODE
function init(){
	
	$('#add-order').click(formValidate1);
	$('#cart-data').click(cartPage);
	//$('#update-employee').click(formValidate2);
	$('#fetch').click(val);
	$('#refresh-data').click(refresh);
	$("#pd").hide();
	$("#detail-form").hide();
	$('#place-order').click(addOrder);
	$('#update-product').click(formValidate11);
	

}

$(document).ready(init);

$(document).ready(getOrderItemList);
