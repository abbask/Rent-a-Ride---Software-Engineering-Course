<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="js/reservation.js"></script>
<title>Rent A Ride - Reservation</title>
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
			<table id="ReservationListTable">
				<tr>
					<th>Id</td>
					<th class="longDateTime">Pick up</th>
					<th>Lenght</th>
					<th>Rental Location Name</th>
					<th>Vehicle Type Name</th>
					<th>Action</th>
					
				</tr>
				<#list reservations as reservation>
        		<tr>
        			<td>${reservation[0]}</td>
        			<td class="longDateTime">${reservation[1]?datetime}</td>
        			<td>${reservation[2]}</td>
        			<td>${reservation[3]}</td>
        			<td>${reservation[4]}</td>
        			
        			<form method="GET" action="UpdateReservation">
        				<input type="hidden" name="Id" value="${reservation[0]}">
        				<#if isAdmin == "no">
        					<td><input type="submit"  value="Update" /></td>
        					<#else>
						<td></td>
						</#if>
        			</form>
        			<form method="GET" action="DeleteReservation">
        				<input type="hidden" name="Id" value="${reservation[0]}">
        				<#if isAdmin == "no">
        					<td><input type="submit"  value="Delete" /></td>
        					<#else>
						<td></td>
						</#if>
        					
        			</form>
        			<#if isAdmin == "no">
        			<#if reservation[5] != 0>
						<td>Disabled</td>
					<#else>
						<form method="GET" action="RentVehicle">
        					<input type="hidden" name="Id" value="${reservation[0]}">
        					<td><input type="submit"  value="Rent" /></td>
        				</form>
					</#if>	
					<#if !reservation[6]?? && reservation[5] != 0 >
						<form method="GET" action="ReturnVehicle">
        					<input type="hidden" name="Id" value="${reservation[5]}">
        					<td><input type="submit"  value="Retrun" /></td>
        				</form>
					<#else>
						<td>Disabled</td>
					</#if>	
					</#if>				
						
					
        			
        		</tr>
    			</#list>
				
			</table>
			<p></p>
			<#if isAdmin == "no">
			<form action="CreateReservation" method="GET">
				<input type="submit" value="Create New" />
			</form>
			</#if>
			
		</div>
	</div>

</body>
</html>