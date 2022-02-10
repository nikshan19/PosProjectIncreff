
function getEmployeeUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}
function getEmployeeUrl2() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/upload/inventory";
}
//BUTTON ACTIONS
function addEmployee(event) {
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
		success: function(data, textStatus, xhr) {
			$('#brand-modal').modal('toggle');
			$("#inventory-form").trigger("reset");
			showSuccess("Product inventory added");
			getEmployeeList();     //...
		},
		error: function(data, textStatus, xhr) {

			showError("Error: " + data.responseText);
		}
	});

	return false;
}

function updateEmployee(event) {

	//Get the ID
	var b = $("#inventory-edit-form input[name=barcode]").val();
	var url = getEmployeeUrl() + "/" + b;

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
		success: function(data, textStatus, xhr) {
			$('#edit-inventory-modal').modal('toggle');
			showSuccess("Product inventory updated");
			getEmployeeList();     //...
		},
		error: function(data, textStatus, xhr) {
			showError("Error: " + data.responseText);

		}
	});

	return false;
}


function getEmployeeList() {
	var url = getEmployeeUrl();
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {

			displayEmployeeList(data);     //...
		},
		error: function() {
			showError("An error has occurred");
		}
	});
}

function deleteEmployee(id) {
	var url = getEmployeeUrl() + "/" + id;

	$.ajax({
		url: url,
		type: 'DELETE',
		success: function(data, textStatus, xhr) {
			showSuccess("Product inventory deleted");
			getEmployeeList();     //...
		},
		error: function(data, textStatus, xhr) {
			showError("Error: " + data.responseText);
		}
	});
}

//UI DISPLAY METHODS

function displayEmployeeList(data) {
	//console.log('Printing employee data');
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for (var i in data) {
		var e = data[i];
		//console.log(e.id);
		var buttonHtml = '<button type="button" class="btn btn-outline-danger border-0" data-toggle="tooltip"  title="Delete" onclick="deleteEmployee(' + e.id + ')"><i class="bi bi-trash"></i></button>'
		buttonHtml += ' <button type="button" class="btn btn-outline-primary border-0" data-toggle="tooltip"  title="Edit" onclick="displayEditEmployee(' + e.id + ')"><i class="bi bi-pen"></i></button>'
		var row = '<tr>'
			+ '<td>' + c + '</td>'
			+ '<td>' + e.name + '</td>'
			+ '<td>' + e.barcode + '</td>'
			+ '<td>' + e.quantity + '</td>'
			+ '<td>' + buttonHtml + '</td>'
			+ '</tr>';
		$tbody.append(row);
		c++;
	}
}

function displayEditEmployee(id) {
	var url = getEmployeeUrl() + "/" + id;
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {

			displayEmployee(data);     //...
		},
		error: function() {
			showError("An error has occurred");
		}
	});
}

function displayEmployee(data) {
	$("#inventory-edit-form input[name=barcode]").val(data.barcode);
	$("#inventory-edit-form input[name=quantity]").val(data.quantity);
	var span = document.getElementById("spanB");
	span.innerHTML = "Product name: " + data.name + "<br/>";

	var span = document.getElementById("spanBB");
	span.innerHTML = "Product barcode: " + data.barcode;

	$('#edit-inventory-modal').modal('toggle');
}




function formValidation() {

	var x = $("#inventory-form input[name=barcode]").val();
	var y = $("#inventory-form input[name=quantity]").val();

	if (x.length == 0) {
		showError("Fill all input fileds");
		return false;
	}

	else {
		addEmployee();
	}
}

function myFunction() {

	var x = $("#inventory-edit-form input[name=barcode]").val();
	var y = $("#inventory-edit-form input[name=quantity]").val();

	if (x.length == 0) {
		showError("Please fill all required fields");
		return false;
	}

	else {
		updateEmployee();
	}


}






function showError(msg) {

	$('#EpicToast').html('<div class="d-flex">'
		+ '<div class="toast-body">'
		+ '<span style="color:white; padding:5px; font-size: 1rem;"><i class="bi bi-x-circle"></i></span>'
		+ '' + msg + ''
		+ ' </div>'
		+ '<button type="button" class="btn-close btn-close-white me-2 m-auto" data-dismiss="toast" aria-label="Close"></button>'
		+ '</div>'

	);


	var option = {
		animation: true,
		delay: 5000
	};
	var t = document.getElementById("EpicToast");
	var tElement = new bootstrap.Toast(t, option);
	tElement.show();

}

function showSuccess(msg) {

	$('#EpicToast1').html('<div class="d-flex">'
		+ '<div class="toast-body">'
		+ '<span style="color:white; padding:5px; font-size: 1rem;"><i class="bi bi-check-circle"></i></span>'
		+ '' + msg + ''
		+ ' </div>'
		+ '<button type="button" class="btn-close btn-close-white me-2 m-auto" data-dismiss="toast" aria-label="Close"></button>'
		+ '</div>'

	);


	var option = {
		animation: true,
		delay: 2000
	};
	var t = document.getElementById("EpicToast1");
	var tElement = new bootstrap.Toast(t, option);
	tElement.show();

}

//HELPER METHOD
function toJson($form) {
	var serialized = $form.serializeArray();

	var s = '';
	var data = {};
	for (s in serialized) {
		data[serialized[s]['name']] = serialized[s]['value']
	}
	var json = JSON.stringify(data);

	return json;
}
// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData() {
	var file = $('#employeeFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results) {
	fileData = results.data;
	uploadRows();
}

function uploadRows() {
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if (processCount == fileData.length) {
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getEmployeeUrl2();

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
		error: function(response) {

			row.error = response.responseText
			errorData.push(row);
			uploadRows();

			document.getElementById('row-box').style.display = "";

			document.getElementById('download-errors').style.display = "";
		}
	});

}

function downloadErrors() {
	writeFileData(errorData);
}


function resetUploadDialog() {
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

function updateUploadDialog() {
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName() {
	var $file = $('#employeeFile');
	var fileName = $file.val();
	$('#employeeFileName').html(fileName);
}

function displayUploadData() {
	resetUploadDialog();
	$('#upload-employee-modal').modal('toggle');
}

function cc() {
	$("#upload-employee-modal").trigger("reset");
	document.getElementById('row-box').style.display = "none";
	document.getElementById('download-errors').style.display = "none";
}
function c() {
	$("#inventory-form").trigger("reset");
	document.getElementById('row-box').style.display = "none";
	document.getElementById('download-errors').style.display = "none";
}

//INITIALIZATION CODE
function init() {

	$('#update-inventory').click(myFunction);
	$('#refresh-data').click(getEmployeeList);
	$('#add').click(formValidation);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
	$('#employeeFile').on('change', updateFileName)
	$('#cc').click(cc);
	$('#c').click(c);
}

$(document).ready(init);
$(document).ready(getEmployeeList);