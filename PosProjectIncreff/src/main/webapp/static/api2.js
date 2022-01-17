
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

//BUTTON ACTIONS
function addEmployee(event){
	//Set the values to update
	var $form = $("#product-form");
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
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();	
	var url = getEmployeeUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
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
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteEmployee(' + e.id + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditEmployee(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.brandPojo + '</td>'
		+ '<td>'  + e.name + '</td>'
		+ '<td>'  + e.mrp + '</td>'
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
	$("#product-edit-form input[name=barcode]").val(data.barcode);	
	$("#product-edit-form input[name=brandPojo]").val(data.brandPojo);	
	$("#product-edit-form input[name=name]").val(data.name);	
	$("#product-edit-form input[name=mrp]").val(data.mrp);	
	$("#product-edit-form input[name=id]").val(data.id);	
	$('#edit-product-modal').modal('toggle');
}


function myFunction() {
	
  var x = $("#product-form input[name=barcode]").val();
  var y =$("#product-form input[name=brandPojo]").val();  
  var a =$("#product-form input[name=name]").val();  
  var b =$("#product-form input[name=mrp]").val();  
 pattern=/^\d+(?:\.\d{1,2})?$/;
  if (x==""||x==null, a==""||a==null) {
      alert("Please Fill All Required Fields");
      return false;
  } 
  if(y<=0||!pattern.test(b)){
	alert("Enter valid Inputs")
	return false
}
  
	addEmployee();

  
  
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
	$('#add-product').click(myFunction);
	$('#update-product').click(updateEmployee);
	$('#refresh-data').click(getEmployeeList);
}

$(document).ready(init);
$(document).ready(getEmployeeList);