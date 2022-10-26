<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
input[type=submit] {
	width: 50%;
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
	width: 50%;
	margin: 10px;
	align-self: center;
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

input[type=text], input[type=password] {
	width: 30%;
	padding: 12px 10px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

.error {
    color: red;
    font-style: italic;
}
#form1 {
	margin-left: 8px;
	margin-top: 10px;
}
</style>
<meta charset="ISO-8859-1">
<title>login page</title>
</head>
<body>
	<div>
		<form:form action="/${url}" method="POST" id="form1" modelAttribute="user">
			<label>UserName</label>
				<form:input path="username"/>
			<form:errors path="username" cssClass="error" />
			<br>
			<label for="formGroupExampleInput2">Password</label>
				<form:input type="password" path="password"/>
				<form:errors path="password" cssClass="error" />
			<br>

			<br>
			<input type="submit" value="Login">
		</form:form>
		<c:if test="${input == 'User'}">
			<a href="/login/register">For new user</a>
		</c:if>
	</div>
</body>
</html>