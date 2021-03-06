
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
			
	   		showError("Error: "+data.responseText);
	   }
	});

	return false;
}

function updateEmployee(event){
	
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
		$('#edit-product-modal').modal('toggle');
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
		var buttonHtml = '<button type="button" class="btn btn-outline-danger border-0" data-toggle="tooltip"  title="Delete"  onclick="deleteEmployee(' + e.id + ')"><i class="bi bi-trash"></i></button>'
		buttonHtml += ' <button type="button" class="btn btn-outline-primary border-0" data-toggle="tooltip"  title="Edit" onclick="displayEditEmployee(' + e.id + ')"><i class="bi bi-pen"></i></button>'
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
	   		dropdown2(data.brandCategory);
	   		displayEmployee(data);     //...
	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});	
}

function displayEmployee(data){
	$("#product-edit-form input[name=barcode]").val(data.barcode);	
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
  	if ((x.length==0)|| (a.length==0)) {
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

function myFunction2() {
	
  var x = $("#product-edit-form input[name=barcode]").val();
  var a =$("#product-edit-form input[name=name]").val();  
  var b =$("#product-edit-form input[name=mrp]").val();  
 pattern=/^\d+(?:\.\d{1,2})?$/;
 pattern2=/^\d*/;
  	if ((x.length==0)|| (a.length==0)) {
      showError("Please Fill All Required Fields");
      return false;
  } 
  	else if(b<=0){
	showError("MRP should be greater than zero");
	return false
}
	else if(!pattern.test(b)){
		showError("Invalid MRP entered");
		return false
	}
	
  else{
	updateEmployee();

  }
  
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
	   		
			document.getElementById('row-box').style.display = "";
			
	   },
	   error: function(response){
			
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   		
	   		document.getElementById('row-box').style.display = "";
	   		
	   		document.getElementById('download-errors').style.display = "";
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
function cc(){
	$("#upload-employee-modal").trigger("reset");
	document.getElementById('row-box').style.display = "none";
	document.getElementById('download-errors').style.display = "none";
}
function c(){
	$("#product-form").trigger("reset");
	document.getElementById('row-box').style.display = "none";
	document.getElementById('download-errors').style.display = "none";
}

//INITIALIZATION CODE
function init(){
	$('#add-product').click(myFunction);
	$('#update-product').click(myFunction2);
	$('#refresh-data').click(getEmployeeList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#employeeFile').on('change', updateFileName)
    $('#cc').click(cc);
    $('#c').click(c);
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

function dropdown2(bc){
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
		if(bc==e.id){
			opt.selected='selected';
		}
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
