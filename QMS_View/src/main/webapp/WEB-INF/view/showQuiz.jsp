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
<title>Quiz show</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>
	<jsp:include page="Menu.jsp" />
	<table class="table table-bordered table-striped" id="table1"><caption>Quiz Information</caption>
		<thead class="thead-dark">

			<tr>
				<th scope="col">Question</th>
				<th scope="col">Option</th>
				<th scope="col">Answer</th>
				<th scope="col">Mark</th>
			</tr>
		</thead>

		<c:forEach items="${quiz.list}" var="questions">
			<tr>
				<td><c:out value=" ${questions.name}" /></td>
				<td><c:forEach items="${questions.list}" var="list">
						<input type="text" value="${list.choice}" name="choice">

					</c:forEach></td>
				<td><c:out value=" ${questions.ans}" /></td>
				<td><c:out value=" ${questions.mark}" /></td>
			</tr>
		</c:forEach>



	</table>
	<form action="/quiz">
		<input type="submit" value="back">
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