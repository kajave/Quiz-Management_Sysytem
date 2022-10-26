<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
input[type=submit] {
	width: 30%;
	background-color: #4CAF50;
	color: white;
	padding: 14px 10px;
	margin: 8px 0;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=submit]:hover {
	background-color: #45a049;
}

div {
	width: 35%;
	margin: 15px;
	align-self: center;
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

input[type=text], input[type=password] {
	width: 20%;
	padding: 12px 10px;
	margin: 8px 0;
	display: inline-block;
	border: none;
}
</style>
<meta charset="ISO-8859-1">
<title>Result page</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>
	<form action="/user" style="align-self: center;">
		<label>Quiz Name:</label> <input type="text" value="${quizname}"
			contenteditable="false"><br> <label>Mark:</label> <input
			type="text" value="${quizmark}" contenteditable="false"><br>
		<input type="submit" value="Back">

	</form>
	<%
	} else {
	%>
	You are logout
	<a href="/login">login here</a>
	<%
	}
	%>
</body>
</html>