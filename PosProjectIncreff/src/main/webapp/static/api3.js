
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}


function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}

var count=0;
var flag = 1;
function addOrderItem(event){
	
	for(let i=0;i<=count;i++){
	var $form = $("#order-form"+i);
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
	   		console.log("OrderItem created");	
	   		getEmployeeList(); 
	   		    //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   		flag=0;
	   }
	});
	
}

return false;



}

//BUTTON ACTIONS
//var count = 0;
function addEmployee(event){
	//Set the values to update
	
	
	var $form = $("#demo-form");
	var json = toJson($form);
	var url = getEmployeeUrl();
	//var url1 = getOrderItemUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		console.log("Employee created");
	   		//location.reload();
	   		addOrderItem();
	   		//getEmployeeList(); 
	   	
	 		//count=0;
	   		    //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

return false;
}



function updateEmployee(event){
	$('#edit-order-modal').modal('toggle');
	//Get the ID
	var id = $("#order-edit-form input[name=id]").val();	
	var url = getEmployeeUrl() + "/" + id;

	//Set the values to update
	var $form = $("#order-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		console.log("Employee update");	
	   		getEmployeeList();
	   		    //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}


function getEmployeeList(){
	var url = getEmployeeUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Employee data fetched");
	   		console.log(data);	
	   		displayEmployeeList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function deleteEmployee(id){
	var url = getEmployeeUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		console.log("Employee deleted");
	   		deleteOrderList(id);
	   		getEmployeeList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function deleteOrderList(id){
	
	var url = getOrderItemUrl()+"/"+id;
	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		console.log("Employee deleted");
	   		     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
	
}

//UI DISPLAY METHODS

function editOrder(id){
	
	location.href = "http://localhost:8080/PosProjectIncreff/ui/editorder/"+id;
	
}

function displayOrderList(id){
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Employee data fetched");
	   		console.log(data);	
	   		displayOrderItemList(data);
	   		
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});	
}

function displayOrderItemList(data){
	console.log('Printing employee data');
	var $tbody = $('#editorder-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		
		var row = '<tr>'
		+ '<td>' + $("#editorder-form input[name=barcode]").val(); + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}




function displayEmployeeList(data){
	console.log('Printing employee data');
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteEmployee(' + e.id + ')">delete</button>'
		buttonHtml += ' <button onclick="editOrder(' + e.id + ')">more</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.dateTime + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditEmployee(id){
	var url = getEmployeeUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Employee data fetched");
	   		console.log(data);	
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
	for(let i = 0;i<=count;i++){
		var a = document.getElementById("inputBarcode"+i).value;
		var b = document.getElementById("inputQuantity"+i).value;
		var c = document.getElementById("inputMrp"+i).value;
		
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
		
	}
	formValidate2();
	
}


function formValidate2(){
	const l = [];
	for(let i =0;i<=count;i++){
		var v = document.getElementById("inputBarcode"+i).value;
		if(l.length!=0 && l.includes(v)){
			msg = "barcodes cannot be repeated in a single order";
			showError(msg);
			return false;
		}
		else{
			l.push(v);
		}
		
	}
	addEmployee();
	
	
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




//HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    console.log(json);
    return json;
}


//INITIALIZATION CODE
function init(){
	
	$('#add-order').click(formValidate1);
	$('#update-order').click(updateEmployee);
	$('#refresh-data').click(getEmployeeList);

}

$(document).ready(init);
$(document).ready(getEmployeeList);
$(document).ready();

$(document).ready(rows);

function rows(){
	
      var i=1;
     $("#add_row").click(function(){
	
      $('#order-form'+i).html('<div class = "row">'
				+'<div class="col">'
				+ '<label for="inputBarcode'+i+'" class="col-sm-2 col-form-label">Barcode</label>'
			  +  '<div class="col">'
			     + '<input type="text" class="form-control" name="barcode" id="inputBarcode'+i+'" placeholder="enter barcode">'
			    +'</div>'
			 + '</div>'
			 + '<div class="col">'
			    +'<label for="inputQuantity'+i+'" class="col-sm-2 col-form-label">Quantity</label>'
			    +'<div class="col">'
			     + '<input type="number" class="form-control" name="quantity" id="inputQuantity'+i+'" placeholder="enter quantity" min="0" value="0">'
			   + '</div>'
			  +'</div>'
			 + '<div class="col">'
			   + '<label for="inputMrp'+i+'" class="col-sm-2 col-form-label">Mrp</label>'
			    +'<div class="col">'
			     + '<input type="number" class="form-control" name="mrp" id="inputMrp'+i+'" placeholder="enter mrp" min="0" value="0" step="0.01" pattern="/^\d+(?:\.\d{1,2})?$/">'
			   + '</div>'
			 + '</div>'
			 +'</div>'
			 );

      $('#bef-form').append('<form id="order-form'+(i+1)+'"></form>');
      i++; 
      count++;
  });
     $("#delete_row").click(function(){
         if(i>1){
         $("#order-form"+(i-1)).html('');
         i--;
         count--;
         }
     });

}

