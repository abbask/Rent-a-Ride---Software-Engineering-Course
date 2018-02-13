<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Administrator Panel</title>
</head>
<body>

	<div class=container>
		<#if isAdmin == "yes">
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
				<#else>
				<div class="logo">
					<img class="logo" src="img/car.jpg" alt="...">
				</div>
				<div class="header">
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
				</#if>
		<div class="content">
			<p></p>
			<table id="VehicleListTable">
				<tr>
					<th>Id</th>
					<th>Make</th>
					<th>Model</th>
					<th>Year</th>
					<th>RegistrationTag</th>
					<th>Mileage</th>
					<th>LastServiced</th>
					<th>Status</th>
					<th>Condition</th>
					<th>VehicleType</th>
					<th>RentalLocation</th>
					<th>Action</th>
					
				</tr>
				<#list vehicles as vehicle>
        		<tr>
        			<td>${vehicle[0]}</td>
        			<td>${vehicle[1]}</td>
        			<td>${vehicle[2]}</td>
        			<td>${vehicle[3]?c}</td>
        			<td>${vehicle[4]}</td>
        			<td>${vehicle[5]}</td>
        			<td>${vehicle[6]}</td>
        			<td>${vehicle[7]}</td>
        			<td>${vehicle[8]}</td>
        			<td>${vehicle[9]}</td>
        			<td>${vehicle[10]}</td>
        			
        			      <form method="GET" action="UpdateVehicle">
        				<input type="hidden" name="Id" value="${vehicle[0]}">
        				<#if isAdmin == "yes">
						  <td><input type="submit"  value="Update" /></td>
						<#else>
						<td></td>
						</#if>
						</td>
        			</form>
        			      <form method="GET" action="DeleteVehicle">
        				<input type="hidden" name="Id" value="${vehicle[0]}">
        				<td><#if isAdmin == "yes">
							<td><input type="submit"  value="Delete" /></td>
						<#else>
						<td></td>												  
						</#if>
					</td>
        			</form>
        			
        			
        		</tr>
    			</#list>
				<#if isAdmin == "yes">
				  <form method="GET" action="FindAllVehicle">
				<#else>
				  <form method="GET" action="ListAllVehicle">
				</#if>
				<tr>
					<td></td>					
					<td><input class="smallInput" name="make" type="input" placeholder="Make" /></td>
					<td><input class="smallInput" name="model" type="input" placeholder="Model"/> </td>
					<td><input class="smallInput" name="year" type="input" placeholder="Year" /> </td>
					<td><input class="smallInput" name="registrationTag" type="input" placeholder="Registration Tag" /> </td>
					<td><input class="smallInput" name="mileage" type="input" placeholder="Mileage" /> </td>
					<td><input class="smallInput" name="name" type="submit" value="search" /></td>
				</tr>
				</form
			</table>
			<p></p>
			<#if isAdmin == "yes">
				<form action="CreateVehicle" method="GET">
					<input type="submit" value="Create New" />
				</form>				
			</#if>
			
		</div>
	</div>

</body>
</html>