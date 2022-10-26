<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
input[type=text], select {
	width: 40%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type=submit] {
	width: 25%;
	background-color: #4CAF50;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=submit]:hover {
	background-color: #45a049;
}

div {
	margin: 40px;
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 40px;
	border-radius: 5px;
}
</style>
<meta charset="ISO-8859-1">
<title>QuizTemplate page</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>
	<div>
		<jsp:include page="Menu.jsp" />
		<form action="/quiz/addquiz">
			<c:if test="${input=='first'}">
				<label>Enter Quiz Name:</label>
				<input type="text" placeholder="Enter quiz name" name="name">
				<br>
			</c:if>

			<c:if test="${input=='secound'}">
				<label>Quiz Name:</label>
				<input type="text" name="name" value="${name}">
				<br>
			</c:if>
			<br> <input type="submit" value="Add Question">
		</form>
	</div>
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