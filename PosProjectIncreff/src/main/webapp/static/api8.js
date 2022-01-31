
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
	   		console.log("Employee data fetched");
	   		console.log(data);	
	   		displayEmployeeList(data);     //...
	   },
	   error: function(data, textStatus, xhr){
		showError("Error: "+data.responseText);
   
	   }
	});
}




//UI DISPLAY METHODS

function displayEmployeeList(data){
	console.log('Printing employee data');
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





//INITIALIZATION CODE
function init(){
	$('#add-employee').click(getEmployeeList);
	
}

$(document).ready(init);
$(document).ready(show);

function show(){
	$("#datepicker1").datepicker({
  format: 'yyyy-mm-dd',
  maxDate: 0 // change format here
  
  });
  $("#datepicker2").datepicker({
  format: 'yyyy-mm-dd',
  maxDate: 0// change format here
  
  });
}
