<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
#form1 {
	margin-top: 15px;
}

div {
	margin-top: 12px;
}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	if (session.getAttribute("username") != null) {
	%>
	<div>
		<form action="/quiz/changename" method="post" id="form1">
			<label>Prevoius Name: <input type="text"
				value="<c:out value="${name}"/>" name="oldname">
			</label> <label>New Name: </label> <input type="text" name="newname"
				id="change"> <br> <input type="submit"
				value="change name">
		</form>
	</div>
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