<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Rent A Ride - Customer Account</title>
</head>
<body>

	<div class=container>
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
		<div class="content">
			<div class="customerProfile">
			<p>
				<div class="nameSection">
					Welcome, <span class="customerName">${firstname} ${lastname}</span> !
				</div>
				<div class="infoSection">
					<hr/>
					<p></p>
			<form action="Login" method="GET">
			<table>
			<tr>
			    <td>First  Name</td>
				<td><input name="name" value="${firstname}" disabled /> </td>
          		<td> <input type="hidden"  name="item" value="${id}" /> </td>
				
			</tr>
			<tr>
			    <td>Last Name</td>
				<td><input name="lastname" value="${lastname}" disabled /> </td>
			</tr>
			<tr>
				<td>Address</td>
				<td><input name="address" value="${address}" /> </td>
			</tr>
			<tr>
				<td>Email</td>
				<td><input name="email" value="${email}"/> </td>
			</tr>
			
			<tr>
				<td>Membership E</td>
				<td><input name="expire" value="${memberUntil}"   disabled /> </td>
			</tr>
			
			<tr>
				<td>User Status</td>
				<td><input name="expire" value="${status}"   disabled /> </td>
			</tr>
			
			<tr>
				<td>User Name</td>
				<td><input name="username1" value="${username}"   disabled /> </td>
			</tr>
			<tr>
				<td>User Password</td>
				<td><input readonly name="pwd1"  value="${pwd}"    /> </td>
			</tr>
			
			
			
			<tr>
				<td></td><td><input    type="submit"    name="up"  value="Update" /></td>
				<td></td><td><input    type="submit"    name="ca"  value="Cancel Membership" /></td>
				<td></td><td><input    type="submit"    name="ex"  value="Extend Membership" /></td>

			</tr>
			</table>
			</form>
					
					
					
					
				</div>
			</p>
			<form action="ResetPSW" method="post">
				<input type="hidden" name="Id" value="${id}" ></input>
				<input type="submit" value="Reset Password" />
			</form>
			</div>
			
		</div>
	</div>

</body>
</html>