<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link rel="stylesheet" href="path/to/bootstrap.min.css">
    <!-- Add any additional CSS here -->
</head>
<body>
    <div class="container">
        <h2>Profile Details</h2>
        <div class="row">
            <div class="col-md-6">
                <form action="/updateProfile" method="post">
                    <div class="form-group">
<%
String userName = (String) session.getAttribute("name");

%>                    
        <label for="userEmail">Email:</label>
 <input type="text" id="name" name="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" required><br>
                    </div>
                    <div class="form-group">
        <label for="userPhoneNumber">Phone Number:</label>
        <input type="text" id="userPhoneNumber" name="phonenumber" placeholder="Enter your phone number" pattern="[0-9]{10}" required><br>
                    </div>
        <input type="text" id="userName" name="name" placeholder="Enter your name"  value="<%=session.getAttribute("name")%>"  required><br>
                    <button type="submit" class="btn btn-primary">Update Profile</button>
                </form>
            </div>
        </div>
    </div>
    <script src="path/to/bootstrap.min.js"></script>
</body>
</html>
