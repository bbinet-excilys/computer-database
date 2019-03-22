<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
  <title>Computer Database</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="utf-8">
  <!-- Bootstrap -->
  <!-- <link href="css/bootstrap.min.css" rel="stylesheet" media="screen"> -->
  <link href="css/uikit.min.css" rel="stylesheet" media="screen">
  <!-- <link href="css/font-awesome.css" rel="stylesheet" media="screen"> -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
    integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
  <link href="css/main.css" rel="stylesheet">
</head>

<body>

  <!-- <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Computer Database</a>
    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
      aria-haspopup="true" aria-expanded="false">
      Computer
    </a>
    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
      <a class="dropdown-item" href="Dashboard">List</a>
      <a class="dropdown-item" href="AddComputer">Add</a>
      <a class="dropdown-item" href="DeleteComputer">Delete</a>
    </div>
  </nav> -->

  <nav class="uk-navbar uk-navbar-attached uk-background-secondary uk-dark"
    uk-navbar="mode: click; boundary-align:true">
    <div class="uk-container">
      <a class="uk-navbar-brand" href="Dashboard"></a>
      <ul class="uk-navbar-nav">
        <li class="uk-parent"><a href="Dashboard">Home</a></li>
        <li>
          <a href="Dashboard">Computer</a>
          <div uk-dropdown>
            <ul class="uk-nav uk-dropdown-nav">
              <li><a href="Dashboard">List</a></li>
              <li><a href="addComputer">Add</a></li>
              <li><a href="deleteComputer">Delete</a></li>
            </ul>
          </div>
        </li>
        <li>
          <a href="listCompany">Company</a>
          <div uk-dropdown>
            <ul class="uk-nav uk-dropdown-nav">
              <li><a href="listCompany">List</a></li>
            </ul>
          </div>
        </li>
      </ul>
    </div>
  </nav>

  <div class="uk-flex uk-flex-center">
    <form class="uk-form-horizontal">
      <div>
        <label class="uk-form-label" for="searchComputerByName">Search by Name </label>
        <div class="uk-form-controls uk-form-controls-text"><input class="uk-input uk-form-width-medium uk-form-small"
            type="text" id="searchComputerByName" /></div>
      </div>
    </form>
  </div>

  <table class="uk-table uk-table-striped uk-margin uk-padding">
    <thead>
      <tr>
        <th class="uk-width-small">ID</th>
        <th class="uk-width-medium">Name</th>
        <th class="uk-width-small">Introduction Date</th>
        <th class="uk-width-small">Discontinuation Date</th>
        <th class="uk-width-medium">Company</th>
        <th class="editMode"></th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${computers }" var="computer">
        <tr>
          <td style="font-weight: bold; color:black">${computer.id}</td>
          <td>${computer.name}</td>
          <td>${computer.introduced}</td>
          <td>${computer.discontinued}</td>
          <td>${computer.company.name}</td>
          <!-- <td class="editMode"><input type="checkbox" name="cb" class="cb" value="${computer.id}"></td> -->
        </tr>
      </c:forEach>
  </table>
  </tbody>
  <ul class="uk-pagination uk-flex-center">
    <c:if test="${page>1}">
      <li><a href="?page=${page-1}&pageSize=${pageSize}"><span uk-pagination-previous></span></a></li>
    </c:if>
    <c:if test="${!(page>1)}">
      <li class="uk-disabled"><a><span uk-pagination-previous></a></span></li>
    </c:if>
    <li><a href="?page=1&pageSize=${pageSize}">First</a></li>
    <li><a href="?page=${pageMax}&pageSize=${pageSize}">Last</a></li>
    <c:if test="${page<pageMax}">
      <li><a href="?page=${page+1}&pageSize=${pageSize}"><span uk-pagination-next></span></a></li>
    </c:if>
    <c:if test="${!(page<pageMax)}">
      <li class="uk-disabled"><a><span uk-pagination-next></span></a></li>
    </c:if>
  </ul>

  <script src="js/jquery.min.js"></script>
  <!-- <script src="js/popper.min.js"></script> -->
  <!-- <script src="js/bootstrap.bundle.min.js"></script> -->
  <script src="js/uikit.min.js"></script>
  <script src="js/uikit-icons.min.js"></script>
  <script src="js/dashboard.js"></script>

</body>

</html>