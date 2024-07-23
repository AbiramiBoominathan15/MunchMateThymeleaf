<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.chainsys.munchmate.model.Cart"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Delivered Orders</title>
<style>
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background-color: #f3f3f3;
	margin: 0;
	padding: 0;
}
.container {
	width: 80%;
	margin: 20px auto;
	background-color: #fff;
	padding: 20px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
}

.nav {
	background-color: #9fd3c7;
	padding: 15px 80px;
	backdrop-filter: blur(10px);
	top: 0;
	left: 0;
	right: 0;
	z-index: 1000;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.nav .logo img {
	width: 85px;
	height: auto;
	margin-right: 10px;
}

.nav .logo h1 {
	font-weight: 600;
	font-size: 24px;
	margin: 0;
}

.nav ul {
	display: flex;
	list-style: none;
	margin: 0;
	padding: 0;
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

.nav ul li a:hover {
	color: #ff511c;
}

h2 {
	text-align: center;
	color: #333;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	border: 1px solid #ddd;
	padding: 10px;
	text-align: center;
}
th {
	background-color: #f2f2f2;
	color: #333;
}
.logo {
	display: inline-block;
	vertical-align: middle;
}
.title {
	display: inline-block;
	vertical-align: middle;
}
.menuu {
	width: 60%;
}
tr:nth-child(even) {
	background-color: #f9f9f9;
}
.status-delivered {
	color: #28a745;
}
.status-on_process {
	color: #ffc107;
}
.status-cancelled {
	color: #dc3545;
}
</style>
</head>
<body>
	<div class="nav">
		<div class="logo">
			<img src="Picture/logomuchmate1.png" alt="Logo">
			<h1 class="title">
				Munch<b>Mate</b>
			</h1>
		</div>
		<ul class="menuu">
			<li><a class="home" href="index.jsp">Home</a></li>
			<li><a href="aboutPageUser.jsp">AboutUs</a></li>
			<li><a href="contactPageUser.jsp">Contact</a></li>
		</ul>
	</div>
	<div class="container">
		<h2>Delivered Orders</h2>
		<table>
			<thead>
				<tr>
					<th>Food Name</th>
					<th>Quantity</th>
					<th>Total Price</th>
					<th>Mealtime</th>
					<th>Delivery Date</th>
					<th>Payment Status</th>
					<th>Delivery Status</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<Cart> carts = (List<Cart>) request.getAttribute("deliveredOrders");
				for (Cart cartItem : carts) {
				%>
				<tr>
					<td><%=cartItem.getFoodName()%></td>
					<td><%=cartItem.getQuantity()%></td>
					<td>$ <%=cartItem.getTotalPrice()%></td>
					<td><%=cartItem.getFoodSession()%></td>
					<td><%=cartItem.getCurrentdate()%></td>
					<td><%=cartItem.getPaymentStatus()%></td>
					<td class="<%=getStatusClass(cartItem.getDeliveryStatus())%>"><%=cartItem.getDeliveryStatus()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>

<%!public String getStatusClass(String deliveryStatus) {
		if ("Delivered".equalsIgnoreCase(deliveryStatus)) {
			return "status-delivered";
		} else if ("on_process".equalsIgnoreCase(deliveryStatus)) {
			return "status-on_process";
		} else if ("Cancelled".equalsIgnoreCase(deliveryStatus)) {
			return "status-cancelled";
		} else {
			return "";
		}
	}%>

	
	
	
	
	