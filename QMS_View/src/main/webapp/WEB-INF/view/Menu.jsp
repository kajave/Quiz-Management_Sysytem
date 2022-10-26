<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style>
ul {
	list-style-type: none;
	position: fixed;
	width: 100%;
	top: 0;
	margin: 0;
	padding: 0px;
	overflow: hidden;
	background-color: lightgray;
}

li {
	float: left;
	border-right: 1px solid black;
}

li a {
	display: block;
	color: blue;
	font-size: 20px;
	text-align: center;
	padding: 10px 20px;
	text-decoration: none;
}

.active {
	background-color: gray;
	color: white;
}

li a:hover {
	background-color: #696969;
	color: white;
}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>

	<ul>
		<li><a href="/quiz/add">ADD QUIZ</a></li>
		<li><a href="/quiz/show">SHOW QUIZ</a></li>
		<li><a href="/quiz/edit">EDIT QUIZ</a></li>
		<li><a href="/login/logout">LOGOUT</a></li>
	</ul>
	<%
	} else {
	%>
	You are logout
	<a href="/login/">login here</a>
	<%
	}
	%>
</body>
</html>