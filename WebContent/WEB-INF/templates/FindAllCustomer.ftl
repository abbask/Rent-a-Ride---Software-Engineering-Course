<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Administrator Panel</title>
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
		<div class="content contentScroll">
			<p></p>
			<table id="CustomerListTable">
				<tr>
																	
					<th>Id</td>
					<th>FisrtName</th>
					<th>LastName</th>
					<th>UserName</th>
					<th>EmailAddress</th>
					<th>Password</th>
					<th>CreatedDate</th>
					<th>ResidenceAddress</th>
					<th>UserStatus</th>
					<th>MembershipExpiration</th>
					<th>LicenseState</th>
					<th>LicenseNumber</th>
					<th>CrediteCardNumber</th>
					<th>CreditCardExpiration</th>
					<th>Reservation</th>
					<th>Action</th>
					
				</tr>
				<#list customers as customer>
        		<tr>
        			<td>${customer[0]}</td>
        			<td>${customer[1]}</td>
        			<td>${customer[2]}</td>
        			<td>${customer[3]}</td>
        			<td>${customer[4]}</td>
        			<td>${customer[5]}</td>
        			<td>${customer[6]}</td>
        			<td>${customer[7]}</td>
        			<td>${customer[8]}</td>
        			<td>${customer[9]}</td>
        			<td>${customer[10]}</td>
        			<td>${customer[11]}</td>
        			<td>${customer[12]}</td>
        			<td>${customer[13]}</td>
        			 	
        		<form method="GET" action="DeleteCustomer">
        				<input type="hidden" name="Id" value="${customer[0]}">
        				<td><input type="submit"  value="Delete" /></td>
        			</form>
        			
        		</tr>
    			</#list>
					<form method="GET" action="FindAllCustomer">
					<tr>
						<td></td>
						<td><input name="firstName" type="input" placeholder="First Name" /></td>
						<td><input name="lastName" type="input" placeholder="Last Name"/> </td>
						<td></td>
						<td><input name="emailAddress" type="input" placeholder="Email Address" /> </td>
						<td><input name="name" type="submit" value="search" /></td>
					</tr>
					</form
					
			</table>
			<p></p>

		</div>
	</div>

</body>
</html>