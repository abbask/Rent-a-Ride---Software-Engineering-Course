<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Update Vehicle</title>
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
			<#list vehicles as vehicle>
			<form action="UpdateVehicle" method="POST">
			<input type="hidden" name="id" value="${vehicle[0]}">
			<table>
			
			<tr>
				<td>Make</td>
				<td><input name="make" value="${vehicle[1]}" /> </td>
			</tr>
			<tr>
				<td>Model</td>
				<td><input name="model" value="${vehicle[2]}" /> </td>
			</tr>
			<tr>
				<td>Year</td>
				<td><input name="year" value="${vehicle[3]?c}" /> </td>
			</tr>
			<tr>
				<td>RegistrationTag</td>
				<td><input name="registrationtag" value="${vehicle[4]}" /> </td>
			</tr>
			
			<tr>
				<td>Mileage</td>
				<td><input name="mileage" value="${vehicle[5]?c}" /> </td>
			</tr>
			<tr>
				<td>LastServiced</td>
				<td><input name="lastserviced" value="${vehicle[6]}" /> </td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input name="status" value="${vehicle[7]}" /> </td>
			</tr>
			<tr>
				<td>Condition</td>
				<td><input name="condition" value="${vehicle[8]}" /> </td>
			</tr>
			
			
			<tr>
				<td>Vehicle Type</td>
				<td>
					<select id = "vehicletype" name = "vehicletypeId" >
    					<#list vehicleTypes as vehicleType>
    						<#if vehicleType[0] == vehicle[9]>
    							<option selected="selected" value = "${vehicleType[0]}">${vehicleType[1]}</option>
							<#else>
        						<option value = "${vehicleType[0]}">${vehicleType[1]}</option>
        					</#if>
    					</#list>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>RentalLocation</td>
				<td>
					<select id = "rentallocation" name = "rentallocationId" >
    					<#list rentalLocations as rentalLocation>
    						<#if rentalLocation[0] == vehicle[10]>
    							<option selected="selected" value = "${rentalLocation[0]}">${rentalLocation[1]}</option>
							<#else>
        						<option value = "${rentalLocation[0]}">${rentalLocation[1]}</option>
        					</#if>
    					</#list>
					</select>
				</td>
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