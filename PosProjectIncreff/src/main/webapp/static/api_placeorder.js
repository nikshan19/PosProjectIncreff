
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
	   		getEmployeeList(); 
	   		    //...
	   },
	   error: function(data, textStatus, xhr){
			$('#edit-order-modal').modal('toggle');
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





var d;
function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   			
	   		d=data;
	   		displayProductList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}


function displayProductList(data){
	console.log('Printing product data');
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
	var barcode;
	var mrp;
	for(var n in d ){
		var e = d[n];
		
		if(e.id==id){
			barcode = e.barcode;
			mrp = e.mrp;
		}
	}
	
	
	$("#order-edit-form input[name=barcode]").val(barcode);	
	$("#order-edit-form input[name=mrp]").val(mrp);	
	$('#edit-order-modal').modal('toggle');
	
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



function formValidate1(){
	
		var a = $("#order-form input[name=barcode]").val()
		var b = $("#order-form input[name=quantity]").val()
		var c = $("#order-form input[name=mrp]").val()
		
		
		var pattern= /^\d+(?:\.\d{1,2})?$/;
		if(a==""||a==null){
			showError("All input fields must be filled");
			return false;
		}
		else if(b<=0||c<=0){
			showError("Quantity and Mrp cannot be zero");
			return false;
		}
		else if(!pattern.test(c)){
			showError("Mrp has to be in '0.00' format");
			return false;
			
		}
		else{
			addOrderItem();
		}
		
	}
	

function formValidate2(){
	
		var a = $("#order-edit-form input[name=barcode]").val()
		var b = $("#order-edit-form input[name=quantity]").val()
		var c = $("#order-edit-form input[name=mrp]").val()
		
		
		var pattern= /^\d+(?:\.\d{1,2})?$/;
		if(a==""||a==null){
			showError("All input fields must be filled");
			return false;
		}
		else if(b<=0||c<=0){
			showError("Quantity and Mrp cannot be zero");
			return false;
		}
		else if(!pattern.test(c)){
			showError("Mrp has to be in '0.00' format");
			return false;
			
		}
		else{
			addOrderItem2();
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
	   		getEmployeeList(); 
	   		    //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   		
	   }
	});
	


return false;



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

function val(){
	
	var a = $("#order-form input[name=barcode]").val()
	if(a==""||a==null){
			showError("Enter barcode");
			return false;
		}
	else{
		$("#pd").show();
		$("#detail-form").show();
		display(a);
	}	
	
	
}

function display(barcode){
	var name;
	var mrp;
	var id;
	var t=0;
	for(n in d){
		var e = d[n];
		console.log(e.barcode+" "+barcode);
		if(e.barcode == barcode){
			console.log(e.barcode+" "+barcode);
			name=e.name;
			mrp=e.mrp
			id = e.id
			t=1;
			break;
		}
		
	}
	if(t==0){
		showError("Product with barcode doesnot exist");
		return false;
	}
	
	$("#detail-form input[name=name]").val(name);	
	$("#detail-form input[name=mrp]").val(mrp);

	var url = getInventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		
	   		$("#detail-form input[name=quantity]").val(data.quantity);
	   		   //...
	   },
	   error: function(){
	   		showError("An error has occurred");
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
	$('#update-employee').click(formValidate2);
	$('#fetch').click(val);
	$('#refresh-data').click(refresh);
	$("#pd").hide();
	$("#detail-form").hide();

}

$(document).ready(init);
$(document).ready(getProductList);

