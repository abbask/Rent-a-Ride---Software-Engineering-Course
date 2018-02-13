<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - List of Hourly Prices</title>
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
			<table id="HourlyPriceListTable">
				<tr>
					<th>Id</td>
					<th>Minimum Hours</th>
					<th>Maximum Hours</th>
					<th>Price</th>
					<th>Vehicle Type</th>
					<th>Action</th>
					
				</tr>
				<#list hourlyPrices as hourlyPrice>
        		<tr>
        			<td>${hourlyPrice[0]}</td>
        			<td>${hourlyPrice[1]}</td>
        			<td>${hourlyPrice[2]}</td>
        			<td>${hourlyPrice[3]}</td>
        			<td>${hourlyPrice[4]}</td>
        			<form method="GET" action="UpdateHourlyPrice">
        				<input type="hidden" name="Id" value="${hourlyPrice[0]}">
        				<td><input type="submit"  value="Update" /></td>
        			</form>
        			<form method="GET" action="DeleteHourlyPrice">
        				<input type="hidden" name="Id" value="${hourlyPrice[0]}">
        				<td><input type="submit"  value="Delete" /></td>
        			</form>
        		</tr>
    			</#list>
				<form method="GET" action="FindAllHourlyPrice">
				<tr>														
					<td></td>
					<td><input name="minHours" type="input" placeholder="Minimum Hours" /></td>
					<td><input name="maxHours" type="input" placeholder="Maximum Hours"/> </td>
					<td><input name="Price" type="input" placeholder="Price" /> </td>
					<td>
					<select id = "vehicleTypeSelId" name = "vehicleTypeSelId">
							<option selected="selected" value="0">---</option>
						<#list vehicleTypes as vehicleType>
        					<option value = "${vehicleType[0]}">${vehicleType[1]}</option>
    					</#list>
					</select>
					</td>
					<td><input name="name" type="submit" value="search" /></td>
				</tr>
				</form>
			</table>
			<p></p>
			<form action="CreateHourlyPrice" method="GET">
				<input type="submit" value="Create New" />
			</form>
		</div>
	</div>

</body>
</html>