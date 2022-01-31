
function getEmployeeUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
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









function displayEmployeeList(data){
	console.log('Printing employee data');
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	var c=1;
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.dateTime + '</td>'
		+ '</tr>';
        $tbody.append(row);
        c++;
	}
}



function placeOrder(){
	location.href = "http://localhost:8080/PosProjectIncreff/ui/placeorder";
}






//INITIALIZATION CODE
function init(){
	
	$('#refresh-data').click(getEmployeeList);
	$('#place-order').click(placeOrder);

}

$(document).ready(init);
$(document).ready(getEmployeeList);




