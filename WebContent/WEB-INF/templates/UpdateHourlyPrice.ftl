<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Update Hourly Price</title>
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
			<#list hourlyPrices as hourlyPrice>
			<form action="UpdateHourlyPrice" method="POST">
			<input type="hidden" name="id" value="${hourlyPrice[0]}">
			<table>
			<tr>
				<td>Minimum Hours</td>
				<td><input name="minHours" value="${hourlyPrice[1]}" /> </td>
			</tr>
			<tr>
				<td>Maxmum Hours</td>
				<td><input name="maxHours" value="${hourlyPrice[2]}" /> </td>
			</tr>
			<tr>
				<td>Price</td>
				<td><input name="price" value="${hourlyPrice[3]}" /> </td>
			</tr>
			<tr>
				<td>Vehicle Type</td>
				<td>
					<select id = "rentalLocation" name = "rentalLocation" >
    					<#list vehicleTypes as vehicleType>
    						<#if vehicleType[0] == hourlyPrice[4]>
    							<option selected="selected" value = "${vehicleType[0]}">${vehicleType[1]}</option>
							<#else>
        						<option value = "${vehicleType[0]}">${vehicleType[1]}</option>
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