<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript">
	function Fun() {
		var c = document.getElementById("count").value;
		for (i = 0; i < c; i++) {
			var y = document.createElement("INPUT");
			y.setAttribute("type", "text");
			y.setAttribute("Name", "option" + i);
			y.setAttribute("Placeholder", "Enter option");
			document.getElementById("myForm").appendChild(y);
		}
	}
</script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form:form action="${url}" method="POST" modelAttribute="obj">
Quiz Name:
	<input type="text" value="${quizname}" name="quizname" contenteditable="false">
		<br> Enter Question Name:
	<form:input path="name"/>
		<br> Enter Amount of option:
	<input type="number" name="count" id="count">
		<input type="button" value="create options" onclick="Fun()">
		<br>
		<p id="myForm"></p>

	Enter Answer:
	<form:input  path="ans"/>
		<br> Enter mark:
	<form:input  path="mark"/>
		<br>
		<input type="submit" value="Create">
	</form:form>
</body>
</html>