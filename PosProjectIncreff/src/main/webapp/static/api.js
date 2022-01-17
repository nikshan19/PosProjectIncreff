
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

//BUTTON ACTIONS
function addEmployee(){
	//Set the values to update
	var $form = $("#employee-form");
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

}

function updateEmployee(event){
	$('#edit-employee-modal').modal('toggle');
	//Get the ID
	var id = $("#employee-edit-form input[name=id]").val();	
	var url = getEmployeeUrl() + "/" + id;

	//Set the values to update
	var $form = $("#employee-edit-form");
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
	var $tbody = $('#employee-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteEmployee(' + e.id + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditEmployee(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
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
	$("#employee-edit-form input[name=brand]").val(data.brand);	
	$("#employee-edit-form input[name=category]").val(data.category);	
	$("#employee-edit-form input[name=id]").val(data.id);	
	$('#edit-employee-modal').modal('toggle');
}

function myFunction() {
	
  var x = $("#employee-form input[name=brand]").val();
  var y =$("#employee-form input[name=category]").val();  
  
  
  if (x==""||x==null, y==""||y==null) {
      alert("Please Fill All Required Field");
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
	$('#add-brand').click(myFunction);
	$('#update-employee').click(updateEmployee);
	$('#refresh-data').click(getEmployeeList);
}

$(document).ready(init);
$(document).ready(getEmployeeList);