<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
  <meta charset="utf-8">

<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Update Reservation</title>
</head>
<body>

	<div class=container>
		<div class="logo">
			<img class="logo" src="img/car.jpg" alt="...">
		</div>
		<div class="adminHeader">
			<table class="tableHeader">
				<tr>
					<td><a href="Login">Profile</a></td>
					<td><a href="Reservation">Reservation</a></td>
					<td><a href="ListAllRentalLocation">Rental Location</a></td>
					<td><a href="ListAllVehicle">Vehicle</a></td>
					<td></td>
				</tr>
			</table>
			<div class="floatRight">
				<form method="post" action="Logout">
					<input type="submit" name="logout" id="logout" value="Log out" />
				</form>
			</div>
		</div>
		<div class="content">
			<p></p>
			<form action="UpdateReservation" method="POST">
			<table>
			<input type="hidden" name="reservationId" value="${reservationId}" />
			<tr>
				<td>Pickup Time</td>
				<td><input type="date" id="pickdate" name="pickdate" value="${pickupTime?date?string("yyyy-MM-dd")} required"><input id="picktime" name="picktime" type="time" value="${pickupTime?date?string("hh:mm:ss")} required"> </td>
				
			</tr>
			<tr>
				<td>Duration</td>
				<td><input name="duration" value="${rentalDuration}" required/> </td>
			</tr>
			<tr>
				<td>RentalLocation</td>
				<td>
					<select id = "rentalLocation" name = "rentalLocation">
    					<#list rentalLocations as rentalLocation>
	    					<#if rentalLocation[0] == rentalLocationId>
	        					<option selected="selected" value = "${rentalLocation[0]}">${rentalLocation[1]}</option>
	        				<#else>
	        					<option value = "${rentalLocation[0]}">${rentalLocation[1]}</option>
	        				</#if>
	    				</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td>Vehicle Type</td>
				<td>
					<select id = "vehicleType" name = "vehicleType">
    					<#list vehicleTypes as vehicleType>
    						<#if vehicleType[0] == vehicleTypeId>
        						<option selected="selected" value = "${vehicleType[0]}">${vehicleType[1]}</option>
        					<#else>
        						<option value = "${vehicleType[0]}">${vehicleType[1]}</option>	
        					</#if>
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