<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Update VehicleType</title>
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
			<#list vehicleTypes as vehicleType>
			<form action="UpdateVehicleType" method="POST">
			<input type="hidden" name="id" value="${vehicleType[0]}">
			<table>
			<tr>
				<td>name</td>
				<td><input name="name" value="${vehicleType[1]}" /> </td>
			</tr>

			<tr>
				<td></td><td><input type="submit" value="Update" /></td>
			</tr>
			</table>
			</form>
			</#list>
		</div>
	</div>

</body>
</html>