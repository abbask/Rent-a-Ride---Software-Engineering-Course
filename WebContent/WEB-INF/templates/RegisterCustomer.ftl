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
					
				</tr>
			</table>

		</div>
		<div class="content">
			<p></p>
			<form action="RegisterCustomer" method="POST">
			<table>
			<tr>
				<td>FisrtName</td>
				<td><input name="FisrtName" required></td>
				
			</tr>
			<tr>
				<td>LastName</td>
				<td><input name="LastName" required/> </td>
			</tr>
			<tr>
				<td>UserName</td>
				<td><input name="UserName" required/> </td>
			</tr>
			<tr>
				<td>EmailAddress</td>
				<td><input name="EmailAddress" required/> </td>
			</tr>
			<tr>
				<td>ResidenceAddress</td>
				<td><input name="ResidenceAddress" required/> </td>
			</tr>

						<tr>
				<td>LicenseNumber</td>
				<td><input name="LicenseNumber" required/> </td>
			</tr>
							<td>LicenseState</td>
				<td><input name="LicenseState" required/> </td>
			</tr>
						<tr>
				<td>CrediteCardNumber</td>
				<td><input name="CrediteCardNumber" required/> </td>
			</tr>
									<tr>
				<td>CrediteCardExpireDate</td>
				<td><input type="date" name="CrediteCardExpireDate" placeholder="yyyy-mm" required /></td>
			</tr>

			<tr>
				<td></td><td><input type="submit" value="Register" required/></td>
			</tr>
			</table>
			</form>
		</div>
	</div>

</body>
</html>