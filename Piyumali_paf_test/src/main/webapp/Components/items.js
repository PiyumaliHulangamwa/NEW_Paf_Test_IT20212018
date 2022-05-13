$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
 	{
 		$("#alertSuccess").hide();
 	}
 	$("#alertError").hide();
 	
});


// SAVE =======================================================================
$(document).on("click", "#btnSave", function(event)
{
	
	// Clear alerts---------------------
 	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();
 	
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true)
 	{
	
		$("#alertError").text(status);
		$("#alertError").show();
 		return;
 	}
 	

	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	
 	$.ajax(
 	{
 		url : "ItemsAPI",
 		type : type,
 		data : $("#formItem").serialize(),
 		dataType : "text",
 		complete : function(response, status)
 		{
 			onItemSaveComplete(response.responseText, status);
 		}
 	});
});

function onItemSaveComplete(response, status)
{
	if (status == "success")
 	{
 		var resultSet = JSON.parse(response);
 		if (resultSet.status.trim() == "success")
 		{
	 		$("#alertSuccess").text("Successfully saved.");
	 		$("#alertSuccess").show();
	 		$("#divItemsGrid").html(resultSet.data);
 		
 		} else if (resultSet.status.trim() == "error")
 		{
	 		$("#alertError").text(resultSet.data);
	 		$("#alertError").show();
 		}
 	
 	} else if (status == "error")
 	{
		 $("#alertError").text("Error while saving.");
		 $("#alertError").show();
 	} else
 	{
		 $("#alertError").text("Unknown error while saving..");
		 $("#alertError").show();
 	}
 
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}




// UPDATE======================================================================
$(document).on("click", ".btnUpdate", function(event)
{
		$("#hidItemIDSave").val($(this).data("userid"));
		$("#name").val($(this).closest("tr").find('td:eq(0)').text());
		$("#district").val($(this).closest("tr").find('td:eq(1)').text());
		$("#units").val($(this).closest("tr").find('td:eq(2)').text());
		$("#dues").val($(this).closest("tr").find('td:eq(3)').text());
		$("#date").val($(this).closest("tr").find('td:eq(4)').text());
		
});




// DELETE======================================================================
$(document).on("click", ".btnRemove", function(event)
{
 		$.ajax(
 		{
			 url : "ItemsAPI",
			 type : "DELETE",
			 data : "userID=" + $(this).data("userid"),
			 dataType : "text",
			 complete : function(response, status)
			 {
			 		onItemDeleteComplete(response.responseText, status);
			 }
 		});
});

function onItemDeleteComplete(response, status)
{
	if (status == "success")
	 {
	 		var resultSet = JSON.parse(response);
	 		
	 		if (resultSet.status.trim() == "success")
	 		{
				 $("#alertSuccess").text("Successfully deleted.");
				 $("#alertSuccess").show();
				 
				 $("#divItemsGrid").html(resultSet.data);
	 		} else if (resultSet.status.trim() == "error")
	 		{
				 $("#alertError").text(resultSet.data);
				 $("#alertError").show();
	 		}
	 		
	 } else if (status == "error")
	 {
			 $("#alertError").text("Error while deleting.");
			 $("#alertError").show();
	 } else
	 {
			 $("#alertError").text("Unknown error while deleting..");
			 $("#alertError").show();
	 }
}





// CLIENT-MODEL================================================================
function validateItemForm()
{
		// NAME
		if ($("#name").val().trim() == "")
 		{
			return "Insert User Name.";
 		}
 		
		// DISTRICT
		if ($("#district").val().trim() == "")
 		{
 			return "Insert District.";
 		}
 		
 		// UNITS-------------------------------
		if ($("#units").val().trim() == "")
 		{
 			return "Insert units.";
 		}
 		
		// is numerical value
		var tmpPrice = $("#units").val().trim();
		if (!$.isNumeric(tmpPrice))
 		{
 			return "Insert a numerical value for units.";
 		}
 		
		// convert to decimal price
 		$("#units").val(parseFloat(tmpPrice).toFixed(2));
 		
		// DUES-------------------------------
		if ($("#dues").val().trim() == "")
 		{
 			return "Insert dues.";
 		}
 		
		// is numerical value
		var tmpPrice = $("#dues").val().trim();
		if (!$.isNumeric(tmpPrice))
 		{
 			return "Insert a numerical value for dues.";
 		}
 		
		// convert to decimal price
 		$("#dues").val(parseFloat(tmpPrice).toFixed(2));
 		
		// DATE------------------------
		if ($("#date").val().trim() == "")
 		{
 			return "Insert Date.";
 		}
		return true;
}

