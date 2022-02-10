
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/salesreport";
}


function getEmployeeList(){
	var $form = $("#salesreport-form");
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
	   		
	   		displayEmployeeList(data);     //...
	   },
	   error: function(data, textStatus, xhr){
		showError("Error: "+data.responseText);
   
	   }
	});
}




//UI DISPLAY METHODS

function displayEmployeeList(data){
	//console.log('Printing employee data');
	var $tbody = $('#salesreport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>'  + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function formValidation(){
	
	var x = $("#salesreport-form input[name=startdate]").val();
  	var y = $("#salesreport-form input[name=endadate]").val();  
  	var a = $("#salesreport-form input[name=brand]").val();
  	var b = $("#salesreport-form input[name=category]").val(); 
  	
  
  	
  	
  	if(x==""||x==null,y==""||y==null,a==""||a==null,b==""||b==null){
	showError("Please Fill All the Input Fields");
	return false;
}



else{
	getEmployeeList();
}
	
	
}



//HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
    //console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    //console.log(json);
    return json;
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

function refresh(){
	location.reload();
}

var vall = 0;
function validate(){
	vall=1;
	getEmployeeList();
	document.getElementById("pdf").disabled = false;
}

function csv(){
	
	if(vall==1){
		getCSV();
	}
	else{
		showError("This action is not supported");
	}
}


//INITIALIZATION CODE
function init(){
	$('#add-employee').click(validate);
	$('#refresh-data').click(refresh);
	$("#pdf").click(csv);
	document.getElementById("pdf").disabled = true;
}

$(document).ready(init);
$(document).ready(show);

function show(){
	$("#datepicker1").datepicker({
  format: 'yyyy-mm-dd',
  maxDate: '0' // change format here
  
  });
  $("#datepicker2").datepicker({
  format: 'yyyy-mm-dd',
  maxDate: '0'// change format here
  
  });
}



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
	var $form = $("#salesreport-form");
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

			var headers = {
				brand: 'brand'.replace(/,/g, ''),
				category: "category".replace(/,/g, ''),
				quantity: "quantity".replace(/,/g, ''),
				revenue: "revenue"

			};

			var itemsFormatted = [];


			data.forEach((item) => {
				itemsFormatted.push({
					brand: item.brand.replace(/,/g, ''),
					category: item.category.replace(/,/g, ''),
					quantity: item.quantity.toString().replace(/,/g, ''),
					revenue: item.revenue
				});
			});
			var z = convertToCSV(data);
			var today = new Date();
			var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
			var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
			var dateTime = date + ' ' + time;
			var fileTitle = 'SalesReport_' + dateTime;
			exportCSVFile(headers, itemsFormatted, fileTitle);
			vall=0;
		},
		error: function(data, textStatus, xhr){
		showError("Error: "+data.responseText);
		vall=0;
		document.getElementById("pdf").disabled = true;
		}
	});
}

