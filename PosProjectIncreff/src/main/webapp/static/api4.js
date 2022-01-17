
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addEmployee(event){
	//Set the values to update
	var $form = $("#inventory-form");
	var json = toJson($form);
	var url = getEmployeeUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		console.log("Employee created");	
	   		getEmployeeList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}

function updateEmployee(event){
	$('#edit-inventory-modal').modal('toggle');
	//Get the ID
	var id = $("#inventory-edit-form input[name=id]").val();	
	var url = getEmployeeUrl() + "/" + id;

	//Set the values to update
	var $form = $("#inventory-edit-form");
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
	   		getEmployeeList();     //...
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
	   		getEmployeeList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

//UI DISPLAY METHODS

function displayEmployeeList(data){
	console.log('Printing employee data');
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteEmployee(' + e.id + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditEmployee(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.quantity + '</td>'
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
	   		displayEmployee(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});	
}

function displayEmployee(data){
	$("#inventory-edit-form input[name=id]").val(data.id);	
	$("#inventory-edit-form input[name=quantity]").val(data.quantity);	
	$('#edit-inventory-modal').modal('toggle');
}




function formValidation(){
	
	var x = $("#inventory-form input[name=id]").val();
  	var y = $("#inventory-form input[name=quantity]").val();  
  	
  	if(x<=0||y<=0){
	alert("Enter valid inputs");
	return false;
}
else{
	addEmployee();
}
	
	
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
	
	$('#update-inventory').click(updateEmployee);
	$('#refresh-data').click(getEmployeeList);
	$('#add').click(formValidation);
}

$(document).ready(init);
$(document).ready(getEmployeeList);