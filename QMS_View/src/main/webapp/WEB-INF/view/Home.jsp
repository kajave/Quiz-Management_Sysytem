<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
body {
	background-color: aqua;
}
div{
margin-top: 10%;
align-self: center;
}

#admin,#user{
size: 50px;
box-sizing: border-box;
margin-top:10px;
padding: 1px;
text-align: center;
color: black;
} 

</style>

<meta charset="ISO-8859-1">
<title>Home Page</title>
</head>
<body>
<marquee style="color: red;" direction="right" scrollamount="20"><strong>Quiz Management System</strong></marquee>
<div>
<label>LOGIN AS</label><br>
<input type="button"  class="btn btn-primary mb-3" onclick="location.href='/login/admin'" value="Admin" id="admin">
<input type="button" class="btn btn-primary mb-5" onclick="location.href='/login/user'" value="User" id="user">
</div>
</body>
</html>