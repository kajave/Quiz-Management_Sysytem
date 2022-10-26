<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
table, td, th {
	border: 2px solid black;
}

h4 {
	margin-top: 3px;
	text-align: center;
	color: red;
}

#table1 {
	margin-left: 30%;
	margin-top: 7%;
	border-spacing: 7px;
	border-color: blue;
	table-layout: auto;
	text-align: center;
	align-self: center;
}
</style>
<meta charset="ISO-8859-1">
<title>quiz name</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>
	<jsp:include page="Menu.jsp" />
	<h4>Available Quiz</h4>
	<table class="table table-bordered table-striped" id="table1"><caption>Quiz Information</caption>
		<thead class="thead-dark">
			<tr>
				<th scope="col">Name</th>
				<th scope="col">Action</th>
			</tr>
		</thead>

		<c:forEach items="${quizName}" var="quiz">
			<tbody>
				<tr>
					<td><c:out value=" ${quiz}" /></td>
					<td><a href="/quiz/loadquiz?quizName=${quiz}">show</a></td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
	<%
	} else {
	%>
	You are logout
	<a href="/login/logout">login here</a>
	<%
	}
	%>
</body>
</html>