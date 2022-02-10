
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventoryreport";
}


function getEmployeeList(){
	var url = getEmployeeUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		
	   		displayEmployeeList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}


//UI DISPLAY METHODS

function displayEmployeeList(data){
	//console.log('Printing employee data');
	var $tbody = $('#inventoryreport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}



function pdf(){
var baseUrl = $("meta[name=baseUrl]").attr("content")
	var url = baseUrl + "/api/inventoryreport/pdf";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data, textStatus, xhr) {
	   		
	   		location.href = "http://localhost:8080/PosProjectIncreff/api/inventoryreport/pdf";	    //...
	   },
	   error: function(data, textStatus, xhr){
	   		showError(data.responseText);
	   }
	});
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
	$("#pdf").click(getCSV);
}

$(document).ready(init);
$(document).ready(getEmployeeList);

function convertToCSV(objArray) {
	var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
	var str = '';

	for (var i = 0; i < array.length; i++) {
		var line = '';
		for (var index in array[i]) {
			if (line != '') line += ','

			line += array[i][index];
		}

		str += line + '\r\n';
	}

	return str;
}
function exportCSVFile(headers, items, fileTitle) {
	if (headers) {
		items.unshift(headers);
	}

	// Convert Object to JSON
	var jsonObject = JSON.stringify(items);
	//console.log(jsonObject);
	var csv = this.convertToCSV(jsonObject);

	var exportedFilenmae = fileTitle + '.csv' || 'export.csv';

	var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
	if (navigator.msSaveBlob) { // IE 10+
		navigator.msSaveBlob(blob, exportedFilenmae);
	} else {
		var link = document.createElement("a");
		if (link.download !== undefined) { // feature detection
			// Browsers that support HTML5 download attribute
			var url = URL.createObjectURL(blob);
			link.setAttribute("href", url);
			link.setAttribute("download", exportedFilenmae);
			link.style.visibility = 'hidden';
			document.body.appendChild(link);
			link.click();
			document.body.removeChild(link);
		}
	}
	//console.log("Done");
}
function getCSV() {
	var url = getEmployeeUrl();
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {

			var headers = {
				brand: 'brand'.replace(/,/g, ''),
				category: "category".replace(/,/g, ''),
				quantity: "quantity"

			};

			var itemsFormatted = [];


			data.forEach((item) => {
				itemsFormatted.push({
					brand: item.brand.replace(/,/g, ''),
					category: item.category.replace(/,/g, ''),
					quantity: item.quantity
				});
			});
			var z = convertToCSV(data);
			var today = new Date();
			var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
			var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
			var dateTime = date + ' ' + time;
			var fileTitle = 'InventoryReport_' + dateTime;
			exportCSVFile(headers, itemsFormatted, fileTitle);
		},
		error: function() {
			alert("An error has occurred");
		}
	});
}