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
  <div uk-sticky="sel-target: .uk-navbar-container; cls-active: uk-navbar-sticky">
    <nav class="uk-navbar uk-navbar-attached uk-background-secondary uk-dark uk-margin-large-bottom"
      uk-navbar="mode: click; boundary-align:true">
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
        <h1>Computer Details</h1>
      </div>
    </div>
  </div>

  <c:if test="${!empty message}">
    <div class="uk-alert-${message.type}" uk-alert>
      <a class="uk-alert-close" uk-close></a>
      <h4 class="uk-text-bold">${message.title}</h4>
      <p>${message.content}</p>
    </div>
  </c:if>

  <div class="uk-grid uk-flex-center uk-flex-middle" uk-grid>
    <div class="uk-width-1-2">
      <dl class="uk-description-list uk-description-list-divider">
        <dt>Name :</dt>
        <dd>${computer.name}</dd>
        <dt>Introduction date :</dt>
        <dd>${computer.introduced}</dd>
        <dt>Discontinuation date :</dt>
        <dd>${computer.discontinued}</dd>
        <dt>Company :</dt>
        <dd>${computer.company.name}</dd>
      </dl>

    </div>
  </div>

  <script src="js/jquery.min.js"></script>
  <script src="js/uikit.min.js"></script>
  <script src="js/uikit-icons.min.js"></script>
</body>

</html>