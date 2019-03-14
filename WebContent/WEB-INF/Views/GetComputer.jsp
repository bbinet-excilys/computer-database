<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Computer</title>
</head>
<body>

<c:forEach items="${computers}" var="computer" >
    ${computer.toString() }
</c:forEach>

</body>
</html>