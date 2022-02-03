
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}
function getEmployeeUrl2(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/upload/brand";
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
	   success: function(data, textStatus, xhr) {
			$('#brand-modal').modal('toggle');
			$("#employee-form").trigger("reset");
	   		showSuccess("Brand added");		
	   		getEmployeeList();     //...
	   },
	   error: function(data, textStatus, xhr){
			$('#brand-modal').modal('toggle');
	   		showError("Error: "+data.responseText);
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
	   success: function(data, textStatus, xhr) {
	   		showSuccess("Brand Updated");		
	   		getEmployeeList();     //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
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
	   success: function(data, textStatus, xhr) {
	   		showSuccess("Brand deleted");	
	   		getEmployeeList();     //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError("Error: "+data.responseText);
	   }
	});
}

//UI DISPLAY METHODS

function displayEmployeeList(data){
	console.log('Printing employee data');
	var $tbody = $('#employee-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button type="button" class="btn btn-outline-danger border-0" onclick="deleteEmployee(' + e.id + ')"><i class="bi bi-trash"></i></button>'
		buttonHtml += ' <button type="button" class="btn btn-outline-primary border-0" onclick="displayEditEmployee(' + e.id + ')"><i class="bi bi-pen"></i></button>'
		var row = '<tr>'
		+ '<td>' + c + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
        c++;
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
	   		showError("An error has occurred");
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
      showError("Please Fill All Required Field");
      return false;
  } 
  else{
	addEmployee();
}
  
  
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


function start(){
	var cur = window.location.href;
	 var last = cur.substring(cur.lastIndexOf("/") + 1);
	 
	 document.getElementById(last).style.color="blue"
	
	
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#employeeFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getEmployeeUrl2();
	console.log(json);
	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
			uploadRows();
			getEmployeeList();  
			$("#row-box").toggle();
			
	   },
	   error: function(response){
			
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   		$("#row-box").toggle();
	   		$("#download-errors").toggle();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}


function resetUploadDialog(){
	//Reset file name
	var $file = $('#employeeFile');
	$file.val('');
	$('#employeeFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#employeeFile');
	var fileName = $file.val();
	$('#employeeFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-employee-modal').modal('toggle');
}

//INITIALIZATION CODE
function init(){
	$('#add-brand').click(myFunction);
	$('#update-employee').click(updateEmployee);
	$('#refresh-data').click(getEmployeeList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#employeeFile').on('change', updateFileName)
    
   
    
}

$(document).ready(init);
$(document).ready(getEmployeeList);