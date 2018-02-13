<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Rent Vehicle</title>
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
			<form action="RentVehicle" method="POST">
			<input type="hidden" name="reservationId" value="${reservationId}" />
			<table>			
			<tr>
				<td>Rental Location:</td>
				<td>${rentalLocationName}<input type="hidden" name="rentalLocationId" value="${rentalLocationId}" /></td>
			</tr>
			<tr>
				<td>Vehicle Type:</td>
				<td>${vehicleTypeName}<input type="hidden" name="vehicleTypeId" value="${vehicleTypeId}" /></td>						
			<tr>
				<td>Select Vehicle:</td>
				<td>
					<select id = "vehicle" name = "vehicleId">
    					<#list vehicles as vehicle>
        					<option value = "${vehicle[0]}">${vehicle[1]} - ${vehicle[2]}</option>        					        					
    					</#list>
					</select>
				</td>
			</tr>	
			<tr>
				<td></td>
				<td><input type="submit" value="Rent" /></td>
			</tr>	
			</table>
			</form>
			
		</div>
	</div>
	

</body>
</html>