<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - List of Vehicle Type</title>
</head>
<body>

	<div class=container>
		<div class="logo">
			<img class="logo" src="img/car.jpg" alt="...">
		</div>
		<div class="adminHeader">
			<table class="tableHeader">
				<tr>
					<td><a href="FindAllVehicleType">VehicleType</a></td>
					<td><a href="FindAllRentalLocation">RentalLocation</a></td>
					<td><a href="FindAllVehicle">Vehicle</a></td>
					<td><a href="FindAllHourlyPrice">Hourly Price</a></td>
					<td><a href="FindAllCustomer">Customer</a></td>
					<td><a href="ListAllReservation">ListAllReservation</a></td>
					<td><a href="UpdateParameters">Settings</a></td>
				</tr>
			</table>
			<div class="floatRight">
				<form method="post" action="AdminLogout">
					<input type="submit" name="logout" id="logout" value="Log out" />
				</form>
			</div>
		</div>
		<div class="content">
			<p></p>
			<table id="HourlyPriceListTable">
				<tr>
					<th>Id</td>
					<th>Type</th>
					<th>Action</th>
					
				</tr>
				<#list vehicleTypes as vehicleType>
        		<tr>
        			<td>${vehicleType[0]}</td>
        			<td>${vehicleType[1]}</td>
        			<form method="GET" action="UpdateVehicleType">
        				<input type="hidden" name="Id" value="${vehicleType[0]}">
        				<td><input type="submit"  value="Update" /></td>
        			</form>
        			<form method="GET" action="DeleteVehicleType">
        				<input type="hidden" name="Id" value="${vehicleType[0]}">
        				<td><input type="submit"  value="Delete" /></td>
        			</form>
        		</tr>
    			</#list>
				<form method="GET" action="FindAllVehicleType">
				<tr>
					<td></td>					
					<td><input class="type" name="type" type="input" placeholder="Type Name" /></td>					
					<td><input class="smallInput" name="name" type="submit" value="search" /></td>
				</tr>
				</form
			</table>
			<p></p>
			<form action="CreateVehicleType" method="GET">
				<input type="submit" value="Create New" />
			</form>
		</div>
	</div>

</body>
</html>