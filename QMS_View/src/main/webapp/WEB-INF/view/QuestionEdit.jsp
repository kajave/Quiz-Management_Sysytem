<%@page import="com.epam.quiz.model.QuizDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.epam.quiz.model.QuestionDTO"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
table, td, th {
	border: 2px solid black;
}

#table1 {
	margin-left: 20%;
	margin-top: 7%;
	border-spacing: 7px;
	border-color: blue;
	table-layout: auto;
	text-align: center;
	align-self: center;
}

form {
	align-self: center;
}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>
	<jsp:include page="Menu.jsp" />

	<table class="table table-bordered table-striped" id="table1">
		<caption>Question Information</caption>
		<thead class="thead-dark">

			<tr>
				<th scope="col">Question</th>
				<th scope="col">Answer</th>
				<th scope="col">Mark</th>
				<th scope="col">Delete</th>
				<th scope="col">update</th>
			</tr>
		</thead>

		<%
		QuizDTO quiz = (QuizDTO) request.getAttribute("quiz");
		List<QuestionDTO> questions = quiz.getList();
		for (QuestionDTO q : questions) {
			session.setAttribute("que", q);
		%>
		<form:form action="/question/edit/${que}">
		<tr>
			<td><input type="text" value="<%=q.getName()%>" name="name"
				id="name"></td>
			<%-- <td><c:forEach items="${questions.list}" var="list">
							<input type="text" value="${list.choice}" name="choice">

						</c:forEach></td> --%>
			<td><input type="text" value="<%=q.getAns()%>" name="ans"></td>
			<td><input type="text" value="<%=q.getMark()%>" name="mark"></td>
			<td><a href="/question/deleteQuestion?name=<%=q.getName()%>">delete</a>
			
			<td><a href="/question/edit?question=${que}"> action</a></tr>
		</form:form>

		<%
		}
		%>


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