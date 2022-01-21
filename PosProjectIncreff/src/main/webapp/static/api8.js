
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




//UI DISPLAY METHODS

function displayEmployeeList(data){
	console.log('Printing employee data');
	var $tbody = $('#salesreport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
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
	alert("Please Fill All the Input Fields");
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


//INITIALIZATION CODE
function init(){
	$('#add-employee').click(formValidation);
	
}

$(document).ready(init);
$(document).ready(show);

function show(){
	$("#datepicker1").datepicker({
  format: 'yyyy-mm-dd' // change format here
  
  });
  $("#datepicker2").datepicker({
  format: 'yyyy-mm-dd' // change format here
  
  });
}
