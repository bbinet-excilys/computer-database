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
  <h1>Computer List</h1>
  <table>
    <thead>
      <td>ID</td>
      <td>Name</td>
      <td>Introduced</td>
      <td>Discontinued</td>
    </thead>
    <tbody>
      <c:forEach items="${computers}" var="computer">
        <tr>
          <td>
            <c:out value="${computer.id }" />
          </td>
          <td>
            <c:out value="${computer.name}" />
          </td>
          <td>
            <c:out value="${computer.introduced}" />
          </td>
          <td>
            <c:out value="${computer.discontinued}" />
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

</body>

</html>