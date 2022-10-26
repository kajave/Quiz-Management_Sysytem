<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
div {
	margin-top: 20px;
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
	<div>
		<form:form action="/question/editquestion" id="form1">
			<label>Question Name:<c:out value="${question.name}"></c:out>
			</label>
			<input type="text" class="form-control" value="${question.name}"
				name="name" />

			<br>
			<label>Option</label>
			<c:forEach items="${question.list}" var="list">
				<input type="text" class="form-control" value="${list.choice}"
					name="choice">
			</c:forEach>
			<br>
			<label>Answer</label>
			<input type="text" class="form-control" value="${question.ans}"
				name="ans" />

			<label>Mark</label>
			<input type="text" class="form-control" value="${question.mark}"
				name="mark" />

			<input type="submit" class="btn btn-secondary" value="update">
		</form:form>
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