<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Update Parameters</title>
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
			<form action="UpdateParameters" method="post">
			<table>
			<tr>
				<td>Membership Price</td>
				<td><input name="membershipPrice" value="${membershipPrice}" /> </td>
			</tr>
			<tr>
				<td>Overtime Penalty</td>
				<td><input name="overtimePenalty" value="${overtimePenalty}" /> </td>
			</tr>

			<tr>
				<td></td><td><input type="submit" value="Update" /></td>
			</tr>
			</table>
			</form>
		</div>
	</div>

</body>
</html>