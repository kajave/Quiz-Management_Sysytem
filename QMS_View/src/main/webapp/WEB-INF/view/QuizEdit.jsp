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
<title>QuizEdit page</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>
	<jsp:include page="Menu.jsp" />
	<table class="table table-bordered table-striped" id="table1"><caption>Quiz Information</caption>
		<thead class="thead-dark">
			<tr>

				<th scope="col">Name</th>
				<th scope="col">Delete Quiz</th>
				<th scope="col">Change Name</th>
				<th scope="col">Update Question</th>
				<th scope="col">Add Question</th>
			</tr>
		</thead>

		<c:forEach items="${quizName}" var="quiz">
			<tbody>
				<tr>

					<td><c:out value=" ${quiz}" /></td>
					<td><a href="/quiz/deletequiz?quizName=${quiz}">Take
							Action</a></td>
					<td><a href="/quiz/changename?quizName=${quiz}">Take
							Action</a></td>
					<td><a href="/question/updatequestion?quizName=${quiz}">Take
							Action</a></td>
					<td><a href="/question/add?quizName=${quiz}">Take Action</a></td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
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