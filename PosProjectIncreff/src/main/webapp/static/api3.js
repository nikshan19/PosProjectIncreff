
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
	   success: function(response) {
	   		console.log("OrderItem created");	
	   		getEmployeeList(); 
	   		    //...
	   },
	   error: function(){
	   		alert("An error has occurred");
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
	   	
	 		count=0;
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

/*

function updateOrder2(event){
	$('#edit-order-modal').modal('toggle');
	//Get the ID
	
	for(let i=0;i<=count;i++){
	var barcode = $("#order-edit-form"+i+" input[id=inputBarcode"+i+"]").val();	
	var barcode = $("#order-edit-form"+i+" input[name=id]").val();	
	var url = getEmployeeUrl() + "/" +id+ "/" + barcode;

	//Set the values to update
	var $form = $("#order-edit-form"+i);
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
	   		   //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}
	return false;
}

*/



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
		buttonHtml += ' <button onclick="editOrder(' + e.id + ')">edit</button>';
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
	
	$('#add-order').click(addEmployee);
	$('#update-order').click(updateEmployee);
	$('#refresh-data').click(getEmployeeList);
}

$(document).ready(init);
$(document).ready(getEmployeeList);

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
			     + '<input type="text" class="form-control" name="quantity" id="inputQuantity'+i+'" placeholder="enter quantity">'
			   + '</div>'
			  +'</div>'
			 + '<div class="col">'
			   + '<label for="inputMrp'+i+'" class="col-sm-2 col-form-label">Mrp</label>'
			    +'<div class="col">'
			     + '<input type="text" class="form-control" name="mrp" id="inputMrp'+i+'" placeholder="enter mrp">'
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

