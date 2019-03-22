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
  <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
  <!-- <link href="css/font-awesome.css" rel="stylesheet" media="screen"> -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
    integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
  <link href="css/main.css" rel="stylesheet">
</head>

<body>
  <div class="container-fluid">
    <header>
      <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
        <a class="navbar-brand" href="Dashboard">Computer DB</a>
      </nav>
    </header>

    <section id="main" class="mt-6">

      <div class="container-fluid pt-5 mt-5">
        <div id="actions" class="form-horizontal">
          <div class="pull-left">
            <form id="searchForm" action="#" method="GET" class="form-inline">
              <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
              <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
            </form>
          </div>
          <div class="pull-right">
            <a class="btn btn-success" id="addComputer" href="addComputer.html">Add Computer</a> <a
              class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
          </div>
        </div>
      </div>

      <div class="container-fluid py-3">
        <table class="table table-striped my-3">
          <thead class="thead-dark">
            <tr>
              <th class="editMode" scope="col">
                <div>
                  <input type="checkbox" id="selectall" /> - <a href="#" id="deleteSelected"
                    onclick="$.fn.deleteSelected();">
                    <i class="fa fa-trash-o fa-lg"></i>
                  </a>
                </div>
              </th>
              <th scope="col">ID</th>
              <th scope="col">Name</th>
              <th scope="col">Introduction Date</th>
              <th scope="col">Discontinuation Date</th>
              <th scope="col">Company</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${computers }" var="computer">
              <tr>
                <td class="editMode"><input type="checkbox" name="cb" class="cb" value="${computer.id}"></td>
                <th scope="row">${computer.id}</th>
                <td>${computer.name}</td>
                <td>${computer.introduced}</td>
                <td>${computer.discontinued}</td>
                <td>${computer.company.name}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </section>

    <footer>
      <div class="navbar navbar-expand-lg fixed-bottom navbar-dark bg-dark">
        <div class="container text-center">
          <ul class="pagination">
            <c:if test="${page>1}">
              <li class="page-item"><a class="page-link" href="?page=${page-1}&pageSize=${pageSize}"><i
                    class="fas fa-caret-left"></i></a>
              </li>
            </c:if>
            <li class="page-item"><a class="page-link" href="?page=1&pageSize=${pageSize}">First</a></li>
            <li class="page-item"><a class="page-link" href="?page=${pageMax}&pageSize=${pageSize}">Last</a>
            </li>
            <c:if test="${page<pageMax}">
              <li class="page-item"><a class="page-link" href="?page=${page+1}&pageSize=${pageSize}"><i
                    class="fas fa-caret-right"></i></a>
              </li>
            </c:if>
          </ul>
          <div class="btn-group btn-group-sm pull-right" role="group">
            <button type="button" class="btn btn-light">10</button>
            <button type="button" class="btn btn-light">50</button>
            <button type="button" class="btn btn-light">100</button>
          </div>
        </div>
      </div>

    </footer>
  </div>
  <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/dashboard.js"></script>

</body>

</html>