<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	width: 30%;
	margin: 10px;
	align-self: center;
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>
	<form:form action="/user/validate" style="align-self: center;" method="POST"
		id="form">
		<label>Quiz Name:</label><input type="text"
			style="display: inline-block;" value="${name}" name="quizname"
			contenteditable="false">
		<div class="form-group">
			<label>Question Name</label> <input type="text"
				style="display: inline-block;" class="form-control"
				value="${question.name}" name="name" />
		</div>
		<br>
		<div class="form-group">
			<label>Enter Answer</label><br>
			<%
			char c = 'A';
			%>
			<c:forEach items="${question.list}" var="list">

				<input type="radio" name="choice" style="margin-left: 3px"
					value="<%=c%>">
				<c:out value="${list}"></c:out>
				<br>
				<%
				c++;
				%>
			</c:forEach>
		</div>
		<input type="text" name="ans" value="${question.ans}" hidden="true">
		<input type="text" name="mark" value="${question.mark}" hidden="true">

		<input type="submit" class="btn btn-secondary" value="Sumit">
	</form:form>
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