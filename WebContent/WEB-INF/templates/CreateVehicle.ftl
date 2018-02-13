<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Create Vehicle</title>
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
			<form action="CreateVehicle" method="POST">
			<table>
			<tr>
				<td>Make</td>
				<td><input name="make"  /> </td>
			</tr>
			<tr>
				<td>Model</td>
				<td><input name="model"  /> </td>
			</tr>
			<tr>
				<td>Year</td>
				<td><input name="year" /> </td>
			</tr>
			<tr>
				<td>RegistrationTag</td>
				<td><input name="registrationtag"  /> </td>
			</tr>
			
			<tr>
				<td>Mileage</td>
				<td><input name="mileage"  /> </td>
			</tr>
			<tr>
				<td>LastServiced</td>
				<td><input name="lastserviced"  /> </td>
			</tr>
			<tr>
				<td>Status</td>
				<td>
				<select id = "status" name = "status">
					<#list lstVehicleStatus as vs>
    					<option value = "${vs}">${vs}</option>
					</#list>
				</select>
				<!--<input name="status" /> -->
				</td>
			</tr>
			<tr>
				<td>Condition</td>
				<td>
				<select id = "condition" name = "condition">
					<#list lstVehicleCondition as vc>
    					<option value = "${vc}">${vc}</option>
					</#list>
				</select>
				<!--<input name="condition"  /> -->
				</td>
			</tr>
			
			
			<tr>
				<td>Vehicle Type</td>
				<td>
					<select id = "vehicleType" name = "vehicletypeId">
    					<#list vehicleTypes as vehicleType>
        					<option value = "${vehicleType[0]}">${vehicleType[1]}</option>
    					</#list>
					</select>
				</td>
			</tr>
			
						<tr>
				<td>Rental Location</td>
				<td>
					<select id = "rentalLocation" name = "rentallocationId">
    					<#list rentalLocations as rentalLocation>
        					<option value = "${rentalLocation[0]}">${rentalLocation[1]}</option>
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