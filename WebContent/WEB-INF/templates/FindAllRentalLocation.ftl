<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - List of Hourly Prices</title>
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
			<table id="HourlyPriceListTable">
				<tr>
					<th>Name</td>
					<th>Address</th>
					<th>Capacity</th>
					<th>ID</th>
				    <th>Action</th>
					
					
				</tr>
				<#list rentalLocations as rl>
        		<tr>
        			<td>${rl[0]}</td>
        			<td>${rl[1]}</td>
        			<td>${rl[2]}</td>
        			<td>${rl[3]}</td>
        			
        			
        			
        			<form method="GET" action="UpdateRentalLocation">
        				<input type="hidden" name="name" value="${rl[0]}">
        				<input type="hidden" name="address" value="${rl[1]}">
          				<input type="hidden" name="capacity" value="${rl[2]}">
          				<input type="hidden" name="Id" value="${rl[3]}">
        				<#if isAdmin == "yes">
						  <td><input type="submit"  value="Update" /></td>
						<#else>
						<td></td>
						</#if>
        				
        				
        			</form>
       			    <form method="GET" action="DeleteRentalLocation">
        				<input type="hidden" name="name" value="${rl[0]}">
        				<input type="hidden" name="address" value="${rl[1]}">
          				<input type="hidden" name="capacity" value="${rl[2]}">
          				<input type="hidden" name="Id" value="${rl[3]}">
          				<#if isAdmin == "yes">
							<td><input type="submit"  value="Delete" /></td>
						<#else>
						<td></td>												  
						</#if>
        				
        			</form>
        			
        			

        		</tr>
    			</#list>
				<#if isAdmin == "yes">
				  <form method="GET" action="FindAllRentalLocation">
				<#else>
				  <form method="GET" action="ListAllRentalLocation">
				</#if>
				
				<tr>
					<td><input name="name" type="input" placeholder="Name" /></td>
					<td><input name="address" type="input" placeholder="Address"/> </td>
					<td><input name="capacity" type="input" placeholder="Capacity" /> </td>
					<td><input name="name" type="submit" value="search" />
							<input type="hidden" name="isAdmin" value="${isAdmin}"
							</td>
					
				</tr>
				</form>
			</table>
			<p></p>
			<#if isAdmin == "yes">
				<form action="CreateRentalLocation" method="GET">
					<input type="submit" value="Create New" />
				</form>				
			</#if>
			
		</div>
	</div>

</body>
</html>