<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Registere page</title>
</head>
<body>
	<form:form action="/login/register" method="POST" class="px-4 py-3">

		<div class="form-group">
			<label for="formGroupExampleInput">UserName</label> <input
				type="text" class="form-control" placeholder="username"
				name="username" />
		</div>
		<br>

		<div class="form-group">
			<label for="formGroupExampleInput2">Password</label> <input
				type="password" class="form-control" placeholder="password"
				name="password">
		</div>
		<input type="submit" class="btn btn-secondary" value="login">
	</form:form>
	<a href="/login/user">back</a>
</body>
</html>