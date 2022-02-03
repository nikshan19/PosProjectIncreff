
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}
function getEmployeeUrl2(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/upload/product";
}
function getEmployeeUrl3(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
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
	   success: function(data, textStatus, xhr) {
			$('#brand-modal').modal('toggle');
			$("#product-form").trigger("reset");
	   		showSuccess("Product added");		
	   		getEmployeeList();     //...
	   },
	   error: function(data, textStatus, xhr){
			$('#brand-modal').modal('toggle');
	   		showError("Error: "+data.responseText);
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
	   success: function(data, textStatus, xhr) {
	   		showSuccess("Product updated");	
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
	   		showError("An error has occurred");
	   }
	});
}

function deleteEmployee(id){
	var url = getEmployeeUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data, textStatus, xhr) {
	   		showSuccess("Product deleted");	
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
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button type="button" class="btn btn-outline-danger border-0"  onclick="deleteEmployee(' + e.id + ')"><i class="bi bi-trash"></i></button>'
		buttonHtml += ' <button type="button" class="btn btn-outline-primary border-0" onclick="displayEditEmployee(' + e.id + ')"><i class="bi bi-pen"></i></button>'
		var row = '<tr>'
		+ '<td>' + c + '</td>'
		+ '<td>'  + e.name + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>'  + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
        c++
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
	   		dropdown2();
	   		displayEmployee(data);     //...
	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});	
}

function displayEmployee(data){
	$("#product-edit-form input[name=barcode]").val(data.barcode);	
	let element = document.getElementById("inputBrandCategory2");
    element.value = data.brandCategory;
	$("#product-edit-form input[name=name]").val(data.name);	
	$("#product-edit-form input[name=mrp]").val(data.mrp);	
	$("#product-edit-form input[name=id]").val(data.id);	
	$('#edit-product-modal').modal('toggle');
}


function myFunction() {
	
  var x = $("#product-form input[name=barcode]").val();
  var a =$("#product-form input[name=name]").val();  
  var b =$("#product-form input[name=mrp]").val();  
 pattern=/^\d+(?:\.\d{1,2})?$/;
 pattern2=/^\d*/;
  	if (x==""||x==null, a==""||a==null) {
      showError("Please Fill All Required Fields");
      return false;
  } 
  	else if(b<=0){
	showError("MRP cannot be zero");
	return false
}
	else if(!pattern.test(b)){
		showError("Mrp has to be in '0.00' format");
		return false
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
	   },
	   error: function(response){
			
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
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


//INITIALIZATION CODE
function init(){
	$('#add-product').click(myFunction);
	$('#update-product').click(updateEmployee);
	$('#refresh-data').click(getEmployeeList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#employeeFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getEmployeeList);
$(document).ready(dropdown);

function dropdown(){
	console.log("dropdown starts");
	
	var url = getEmployeeUrl3();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		var select = document.getElementById('inputBrandCategory');
		for(var i in data){
		var e = data[i];	
		var opt = document.createElement('option');
		opt.value = e.id;
		opt.innerHTML = e.brand+'-'+e.category;
		select.appendChild(opt);
	}
		
	
		console.log("dropdown stops");

	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});
	
	
} 

function dropdown2(){
	console.log("dropdown starts");
	
	var url = getEmployeeUrl3();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		var select = document.getElementById('inputBrandCategory2');
		$("#inputBrandCategory2").empty();
		for(var i in data){
		var e = data[i];	
		var opt = document.createElement('option');
		opt.value = e.id;
		opt.innerHTML = e.brand+'-'+e.category;
		select.appendChild(opt);
	}
		
	
		console.log("dropdown stops");

	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});
	
	
} 
