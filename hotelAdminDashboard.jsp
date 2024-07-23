<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login Page</title>
<style>
body {
	margin: 0;
	padding: 0;
	justify-content: center;
	align-items: center;
	height: 100vh;
	/* background-image: url("https://static.vecteezy.com/system/resources/previews/024/214/119/non_2x/enjoy-your-meal-illustration-a-variety-of-delicious-food-in-home-or-restaurant-in-flat-cartoon-hand-drawn-landing-page-background-templates-vector.jpg");
            background-size: cover; 
            background-position: center; */
	font-family: Arial, sans-serif;
}

.main {
	background-image:
		url("https://static.vecteezy.com/system/resources/previews/024/214/119/non_2x/enjoy-your-meal-illustration-a-variety-of-delicious-food-in-home-or-restaurant-in-flat-cartoon-hand-drawn-landing-page-background-templates-vector.jpg");
	background-size: cover;
	background-position: center;
	height: 135%;
}

.nav {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 20px 100px;
	background-color: #9fd3c7;
	backdrop-filter: blur(10px);
}

.nav .logo {
	display: flex;
	align-items: center;
}

.nav .logo img {
	width: 96px;
	height: auto;
	margin-right: 10px;
}

.nav .logo h1 {
	font-weight: 600;
	font-size: 24px;
	color: #333;
}

.nav ul {
	display: flex;
	list-style: none;
}

.nav ul li {
	margin-right: 30px;
}

.nav ul li a {
	text-decoration: none;
	color: #333;
	font-weight: 500;
	font-size: 17px;
	transition: color 0.3s ease;
}

.nav :hover {
	color: #ff511c;
}

.signin {
	color: #ff511c;
	text-decoration: none;
	padding: 8px 16px;
	border: 2px solid #ff511c;
	border-radius: 5px;
	transition: background-color 0.3s ease, color 0.3s ease;
}

.signin:hover {
	background-color: #ff511c;
	color: white;
}

.footer {
	background-color: #9fd3c7;
	padding: 20px;
	text-align: center;
	color: #333;
}

.order {
	border: none;
	background-color: #9fd3c7;
	font-size: 17px;
	line-height: 0;
}
.purchase {
	border: none;
	background-color: #9fd3c7;
	font-size: 17px;
}

button.order:hover {
	color: #ff511c;
}

input.purchase:hover {
	color: #ff511c;
}
</style>
</head>
<body>
	<section class="menu">
		<div class="nav">
			<div class="logo">
				<img src="Picture/logomuchmate1.png" alt="Logo">
				<h1>
					Munch<b style="color: #ff511c">Mate</b>
				</h1>
			</div>
			<ul>
				<li><a class="home" href="hotelAdminDashboard.jsp">Home</a></li>
				<li>
					<form action="hotelmenu.jsp" method="post">
						<button class="order" type="submit">Orders</button>
						</a><br> <br>
					</form>
				</li>
				<li><a href="/foodList">Menu</a></li>
				<form action="/orderView" method="post">
					<input type="hidden" id="hotelid" name="hotelid"
						value="<%=session.getAttribute("hotelId")%>" required>
					<li><input type="submit" class="purchase" href=""
						value="Purchase"></li>
				</form>
				<li><a href="contactPage.jsp">Contact</a></li>
				<li><a href="aboutPage.jsp">AboutUs</a></li>
				</form>
			</ul>
			<a class="signin" href="index.jsp">Log Out</a>
		</div>
	</section>
	<section class="main"></section>
	<section class="footer">
		<p>&copy; 2024 MunchMate. All rights reserved.</p>
	</section>

</body>
</html>
