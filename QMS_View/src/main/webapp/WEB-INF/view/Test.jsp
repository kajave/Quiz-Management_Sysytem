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
h4{
align-content: center;
color: green;
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
<title>test page</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>
	<h4>Available Quiz</h4>
	<table class="table table-bordered table-striped" id="table1"><caption>Take Quiz</caption>
		<thead class="thead-dark">
			<tr>
				<th scope="col">Name</th>
				<th scope="col">Action</th>
			</tr>
		</thead>

		<c:forEach items="${quizName}" var="quiz">
			<tbody>
				<tr>
					<%-- <th scope="row">${quiz.id}</th> --%>
					<td><c:out value=" ${quiz}" /></td>
					<td><a href="/user/quiz?quizName=${quiz}">Take test</a></td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
	<input type="button" style="margin-left: 10px;"
		class="btn btn-primary mb-3" onclick="location.href='/login/logout'"
		value="Logout">

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