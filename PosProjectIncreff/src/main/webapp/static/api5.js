function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}


function getOrderList(){
	var loc = window.location.href;
	var id = loc.substring(loc.lastIndexOf('/') + 1)
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Employee data fetched");
	   		console.log(data);	
	   		displayOrderItemList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}
function updateOrderItem(event){
	var loc = window.location.href;
	var id = loc.substring(loc.lastIndexOf('/') + 1)
		
	var url = getOrderItemUrl()+"/"+id;
	//Set the values to update
	var $form = $("#editorder-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		console.log("Employee update");	
	   		
	   		getOrderList();
	   		    //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}


function displayOrderItemList(data){
	console.log('Printing employee data');
	var $tbody = $('#editorder-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		//var buttonHtml = '<button onclick="deleteEmployee(' + e.id + ')">delete</button>'
		//buttonHtml += ' <button onclick="editOrder(' + e.id + ')">edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.mrp + '</td>'
		
		+ '</tr>';
        $tbody.append(row);
	}
}


function formValidation(){
	
	var x = $("#editorder-form input[name=id]").val();
  	var y = $("#editorder-form input[name=barcode]").val();  
  	var a = $("#editorder-form input[name=quantity]").val();
  	var b = $("#editorder-form input[name=mrp]").val();  
  	
  	if(x==""||x==null,y==""||y==null,a==""||a==null,b==""||b==null){
	alert("Please fill all the input fields");
	return false;
}
else{
	updateOrderItem();
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
	$('#add-editorder').click(formValidation);
}

$(document).ready(init);
$(document).ready(getOrderList);
