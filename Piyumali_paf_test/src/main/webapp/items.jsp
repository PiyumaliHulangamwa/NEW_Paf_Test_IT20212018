<%@page import="com.Item"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Item Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.js"></script>
<script src="Components/items.js"></script>

</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-6">

			<h1>Payment Management</h1>
			<form id="formItem" name="formItem" method="post" action="Items.jsp">
			
		 		User name:
				<input id="name" name="name" type="text" class="form-control form-control-sm"><br> 
				
				District:
				<input id="district" name="district" type="text" class="form-control form-control-sm"><br> 
				
				No.OF Units:
				<input id="units" name="units" type="text" class="form-control form-control-sm"><br> 	
				
				No.OF Dues:
				<input id="dues" name="dues" type="text" class="form-control form-control-sm"><br>
				
				Date:
				<input id="date" name="date" type="text" class="form-control form-control-sm"><br>
				
				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
				<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
				
			</form>
			
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
			<br>
			<div id="divItemsGrid">
 				<%
					 Item itemObj = new Item();
					 out.print(itemObj.readItems());
				%>
</div>
			
		</div>
	</div>
</div>

</body>
</html>