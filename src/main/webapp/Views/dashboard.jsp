<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
  <title>Computer Database</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="utf-8">
  <link href="css/uikit.min.css" rel="stylesheet" media="screen">
  <link href="css/main.css" rel="stylesheet">
</head>

<body>
  <div uk-sticky="sel-target: .uk-navbar; cls-active: uk-navbar-sticky" class="uk-margin-bottom">
    <nav class="uk-navbar uk-background-secondary uk-dark" uk-navbar="mode: click; boundary-align:true">
      <div class="uk-navbar-left">
        <a class="uk-navbar-item uk-logo uk-hidden_small" href="dashboard">Computer
          DataBase &nbsp;<span uk-icon="icon: desktop"></span></a>
        <ul class="uk-navbar-nav">
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
  </div>
  <div class="uk-container uk-container-center uk-margin-large-bottom">


    <div class="uk-grid">
      <div class="uk-width-1-1">
        <h1>Computer list - ${count} found</h1>
      </div>
    </div>

    <div class="uk-grid uk-flex-left" uk-grid>
      <div class="uk-width-1-3">
        <form class="uk-form-horizontal">
          <div>
            <label class="uk-form-label" for="searchComputerByName">Search by Name </label>
            <div class="uk-form-controls uk-form-controls-text">
              <input class="uk-input uk-form-width-medium uk-form-small" type="text" id="searchComputerByName" /></div>
          </div>
        </form>
      </div>
      <div class="uk-width-1-3">
        <div class="uk-button-group">
          <a class="uk-button uk-button-default" href="?page=1&pageSize=10">10</a>
          <a class=" uk-button uk-button-default" href="?page=1&pageSize=50">50</a>
          <a class=" uk-button uk-button-default" href="?page=1&pageSize=100">100</a>
        </div>
      </div>
      <div class=" uk-width-1-3">
        <a class=" uk-button uk-button-primary uk-button-small uk-flex-right" href="addComputer">Add Computer</a>
      </div>
    </div>

    <div class="uk-grid">
      <div class="uk-width-1-1">
        <table class="uk-table uk-table-striped uk-margin uk-padding">
          <thead class=" uk-background-secondary">
            <tr>
              <th class="uk-width-small  uk-text-primary">ID</th>
              <th class="uk-width-medium uk-text-primary">Name</th>
              <th class="uk-width-medium uk-text-primary">Introduction Date</th>
              <th class="uk-width-medium uk-text-primary">Discontinuation Date</th>
              <th class="uk-width-medium uk-text-primary">Company</th>
              <th class="uk-width-small  uk-text-primary"></th>
              <th class="uk-width-small  uk-text-primary">
                <button class="uk-button uk-button-danger uk-button-small uk-padding-small-left"><span
                    uk-icon="icon: trash"></span></button>
              </th>
              <th class="uk-width-small">
                <input class="uk-checkbox" type="checkbox" id="selectall" />
              </th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${computers }" var="computer">
              <tr>
                <td style="font-weight: bold; color:black">${computer.id}</td>
                <td><a href="detailsComputer?computerId=${computer.id}" class="uk-link-heading">${computer.name}</a>
                </td>
                <td>${computer.introduced}</td>
                <td>${computer.discontinued}</td>
                <td>${computer.company.name}</td>
                <td><a href="editcomputer?computerId=${computer.id}"
                    class="uk-button uk-button-primary uk-button-small uk-padding-small-left"><span
                      uk-icon="icon: pencil"></span></a></td>
                <td><button class="uk-button uk-button-danger uk-button-small uk-padding-small-left"><span
                      uk-icon="icon: trash"></span></button></td>
                <td>
                  <input type="checkbox" name="cb" class="uk-checkbox" value="${computer.id}">
                </td>
                <!-- <td class="editMode"><input type="checkbox" name="cb" class="cb" value="${computer.id}"></td> -->
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>

      <div class="uk-width-1-1">
        <ul class="uk-pagination uk-flex-center">
          <c:if test="${page>1}">
            <li><a href="?page=${page-1}&pageSize=${pageSize}"><span uk-pagination-previous></span></a></li>
          </c:if>
          <c:if test="${!(page>1)}">
            <li class="uk-disabled"><a><span uk-pagination-previous></span></a></li>
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
      </div>

    </div>
  </div>
  <script src="js/jquery.min.js"></script>
  <script src="js/uikit.min.js"></script>
  <script src="js/uikit-icons.min.js"></script>
  <script src="js/dashboard.js"></script>
</body>

</html>