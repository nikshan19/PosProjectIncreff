
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

function deleteOrder(id){
	
	var url = getEmployeeUrl() + "/" + id;
	
	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data, textStatus, xhr) {
	   			showSuccess("Order Cancelled");
	   			getEmployeeList();
	   
	   		    //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});
}





var oId;
function getOrderItemList(id){
	oId = id;
	var url = getOrderItemUrl()+"/"+id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {

			if(data.length>0){
				$('#mf').html('<button type="button" class=" close btn btn-outline-danger" id="cancel-order" onclick="deleteOrder(' + id + ')"'+'data-dismiss="modal">Cancel Order</button>'
						+'<button type="button" class=" close btn btn-outline-secondary" id="close-r"'
							+'data-dismiss="modal">Close</button>'
	);
			}
			else{
				
				$('#edit-order-modal').modal('toggle');
				
			}
			
	   			
	   		displayOrderItemList(data);
	   		     //...
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
		var buttonHtml = '<button type="button" class="btn btn-outline-danger border-0" data-toggle="tooltip"  title="Delete" onclick="deleteOrderItem(' + e.id +','+e.orderId+ ')"><i class="bi bi-trash"></i></button>'
		buttonHtml += ' <button type="button" class="btn btn-outline-primary border-0" data-toggle="tooltip"  title="Edit" onclick="displayEditOrderItem(' + e.id + ')"><i class="bi bi-pen"></i></button>';
		var row = '<tr id="'+e.id+'">'
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
	var el = document.getElementById(""+id+"");
	el.style.backgroundColor="red";
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
	var span = document.getElementById("spanB");
	span.innerHTML = data.barcode;
	$("#cart-edit-form input[name=quantity]").val(data.quantity);
	$("#cart-edit-form input[name=mrp]").val(data.mrp);	
	$("#cart-edit-form input[name=id]").val(data.id);	
	$('#edit-cart-modal').modal('toggle');
}

function deleteOrderItem(id, orderId){
	
	var url = getOrderItem2Url() + "/" + id;
	
	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data, textStatus, xhr) {
	   		showSuccess("OrderItem deleted");
	   			getOrderItemList(orderId);
	   			$('#edit-order-modal').modal('toggle');
	   			getEmployeeList();
	   
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
	   		showSuccess("Order Updated");
	   		var el = document.getElementById(""+id+"");
			el.style.backgroundColor="transparent";	
	   		getOrderItem(id);
	   			
	   		     //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   		f1();
	   }
	});

	return false;
}






function displayEmployeeList(data){
	//console.log('Printing employee data');
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	var c=1;
	for(var i in data){
		var e = data[i];
		var buttonHtml='';
		var statusHtml='';
		if(e.toggle==1){
		buttonHtml += '<button type="button" class="btn btn-outline-primary border-0" onclick="addE(' + e.id + ')">Download Invoice</button>'
		
		statusHtml+='<span class="text-success">Confirmed</span>'
		}
		if(e.toggle==0){
		buttonHtml+= '<button type="button"  class="btn btn-outline-primary border-0" onclick="addF(' + e.id + ')">Generate Invoice</button>'	
		buttonHtml+= '<button type="button"  class="btn btn-outline-primary border-0" data-toggle="tooltip"  title="Edit" onclick="getO(' + e.id + ')"><i class="bi bi-pencil"></i></button>'
		statusHtml+='<span class="text-info">in progress</span>'
}
		if(e.toggle==2){
			statusHtml+='<span class="text-danger">Cancelled</span>'
		}
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.dateTime + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '<td>' + statusHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
        c++;
	}
}

function getO(id){
	
	
	
	
	getOrderItemList(id);
}

function addF(id){
	updateOrder(id);
	addE(id);
	
}

function addE(id){
	
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	var url = baseUrl + "/api/order/pdf/"+id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function() {
	   		
	   		location.href = "http://localhost:8080/PosProjectIncreff/api/order/pdf/"+id;
	   		showSuccess("Invoice Downloaded");	
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
	   		showSuccess("Invoice Generated");
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
function f1(){
	
	var table = document.getElementById("editorder-table");
	for (var i = 0, row; row = table.rows[i]; i++) {
  	if(row.style.backgroundColor=="red"){
	row.style.backgroundColor = "transparent"
}
		    
}

	
}

function formValidation(){
	var x = $('#cart-edit-form input[name=quantity]').val();
	var y = $('#cart-edit-form input[name=mrp]').val();
	pattern=/^\d+(?:\.\d{1,2})?$/;
	
	if(x<=0){
		showError("Quantity should be greater than zero");
		
	}
	else if(y<=0){
		showError("Price should be greater than zero");
		return false;
	}
	else if(!pattern.test(y)){
		showError("Invalid Price entered");
		return false
	}
	else{
		updateOrderItem();
	}
	
}

//INITIALIZATION CODE
function init(){
	
	$('#refresh-data').click(getEmployeeList);
	$('#place-order').click(placeOrder);
	$('#update-product').click(formValidation);
	$('#cancel-eo').click(f1);
	
	
}

$(document).ready(init);
$(document).ready(getEmployeeList);

