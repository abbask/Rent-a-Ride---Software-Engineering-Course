<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Create Hourly Price</title>
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
			<form action="CreateHourlyPrice" method="POST">
			<table>
			<tr>
				<td>Minimum Hours</td>
				<td><input name="minHours" /> </td>
			</tr>
			<tr>
				<td>Maxmum Hours</td>
				<td><input name="maxHours" /> </td>
			</tr>
			<tr>
				<td>Price</td>
				<td><input name="price" /> </td>
			</tr>
			<tr>
				<td>Vehicle Type</td>
				<td>
					<select id = "rentalLocation" name = "rentalLocation">
    					<#list vehicleTypes as vehicleType>
        					<option value = "${vehicleType[0]}">${vehicleType[1]}</option>
    					</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td></td><td><input type="submit" value="Create" /></td>
			</tr>
			</table>
			</form>
		</div>
	</div>

</body>
</html>