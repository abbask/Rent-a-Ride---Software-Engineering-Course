<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Update Reservation Confirmation</title>
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
			<form action="UpdateReservationConfirm" method="POST">
			<input type="hidden" name="reservationId" value="${reservationId}" />
			<table>			
			<tr>
				<td>Rental Duration:</td>
				<td>${rentalDuration}<input type="hidden" name="rentalDuration" value="${rentalDuration}" /></td>
			</tr>
			<tr>
				<td>Rental Duration:</td>
				<td>${pickupTime?date?string("yyyy-MM-dd hh:mm:ss")}<input type="hidden" name="pickupTime" value="${pickupTime?date?string("yyyy-MM-dd hh:mm:ss")}" /></td>
			</tr>
			<tr>
				<td>Rental Location</td>
				<td>${rentalLocationName}: ${rentalLocationAddres}</td>

			</tr>
			<tr>
				<td>Vehicle Type</td>
				<td>${vehicleTypeName}</td>
			</tr>
			<tr>
				<td>Total Price</td>
				<td>$${totalPrice}</td>
			</tr>
			<tr>
				<td>Credit Card Number</td>
				<td>${CCNumber}</td>
			</tr>
			
<!--			<tr>
				<td>Select Vehicle:</td>
				<td>
					<select id = "vehicle" name = "vehicleType">
    					<#list vehicles as vehicle>
        					<option value = "${vehicle[0]}">${vehicle[1]} - ${vehicle[2]}</option>
        					
        					
    					</#list>
					</select>
				</td>
			</tr>
			-->
			<tr>
				<td>
					<input type="hidden" name="rentalLocationId" value="${rentalLocationId}"/>      			
        		</td>        		
        		<td>
        			<input type="hidden" name="vehicleTypeId" value="${vehicleTypeId}" />
        		</td>
			</tr>		
			<tr>
				<td></td>
				<td><input type="submit" value="Confirm" /></td>
			</tr>	
			</table>
			</form>
			
		</div>
	</div>
	

</body>
</html>