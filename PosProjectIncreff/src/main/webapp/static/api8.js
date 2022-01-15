
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
	$('#add-employee').click(getEmployeeList);
	
}

$(document).ready(init);
