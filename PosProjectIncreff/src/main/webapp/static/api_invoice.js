

function pdf(){
	
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	var url = baseUrl + "/api/pdf";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function() {
	   		console.log("Pdf done");
	   		location.href = "http://localhost:8080/PosProjectIncreff/api/pdf";	    //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
	
	
}











//INITIALIZATION CODE
function init(){
	
	$('#pdf').click(pdf);


}

$(document).ready(init);





