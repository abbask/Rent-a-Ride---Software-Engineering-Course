<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
  <meta charset="utf-8">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  $(function() {
    $( "#datepicker" ).datepicker({ minDate: new Date() });
  });
  </script>
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Create Reservation</title>
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
			<form action="CreateReservation" method="POST">
			<table>
			<tr>
				<td>Pickup Time</td>
				<td><input type="date" id="pickdate" name="pickdate" placeholder="yyyy-mm-dd" required><input id="picktime" name="picktime" type="time" placeholder="hh:mm:ss" required> </td>
				
			</tr>
			<tr>
				<td>Duration</td>
				<td><input name="duration" required/> </td>
			</tr>
			<tr>
				<td>RentalLocation</td>
				<td>
					<select id = "rentalLocation" name = "rentalLocation">
    					<#list rentalLocations as rentalLocation>
        					<option value = "${rentalLocation[0]}">${rentalLocation[1]}</option>
    					</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td>Vehicle Type</td>
				<td>
					<select id = "vehicleType" name = "vehicleType">
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