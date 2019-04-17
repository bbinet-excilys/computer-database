<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
  <title>Computer Database</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="utf-8">
  <link href="<c:url value=" resources/css/uikit.min.css"/>" rel="stylesheet" media="screen">
  <link href="<c:url value=" resources/css/main.css"/>" rel="stylesheet">
</head>

<body>
  <div uk-sticky="sel-target: .uk-navbar; cls-active: uk-navbar-sticky" class="uk-margin-bottom">
    <nav class="uk-navbar uk-background-secondary uk-dark" uk-navbar="mode: click; boundary-align:true">
      <div class="uk-navbar-left">
        <a class="uk-navbar-item uk-logo uk-hidden_small" href="dashboard">Computer
          DataBase &nbsp;<span uk-icon="icon: desktop"></span></a>
        <ul class="uk-navbar-nav">
          <li>
            <a href="dashboard">Computer</a>
            <div uk-dropdown>
              <ul class="uk-nav uk-dropdown-nav">
                <li><a href="dashboard">List</a></li>
                <li><a href="addcomputer">Add</a></li>
                <li><a href="deletecomputer">Delete</a></li>
              </ul>
            </div>
          </li>
          <li>
            <a href="#">Company</a>
            <div uk-dropdown>
              <ul class="uk-nav uk-dropdown-nav">
                <li><a href="#">List</a></li>
              </ul>
            </div>
          </li>
        </ul>
      </div>
    </nav>
  </div>
  <div class="uk-container uk-container-center uk-margin-large-bottom">

    <div class="uk-grid uk-flex-center" uk-grid>
      <div class="uk-width-1-1">
        <h1>Delete Computer</h1>
      </div>
    </div>

    <c:if test="${!empty message}">
      <div class="uk-alert-${message.type}" uk-alert>
        <a class="uk-alert-close" uk-close></a>
        <h4 class="uk-text-bold">${message.title}</h4>
        <p>${message.content}</p>
      </div>
    </c:if>

    <div class="uk-grid uk-flex-center uk-flex-middle uk-margin" uk-grid>
      <form:form method="POST" class="uk-width-1-2" modelAttribute="computerDTOModel" id="deleteComputerForm">
        <form:select class="uk-select uk-width-1-1" path="id">
          <form:options items="${computers}" itemValue="id" itemLabel="name"></form:options>
        </form:select>
        <div class="uk-grid uk-flex-right uk-margin" uk-grid>
          <input type="submit" class="uk-button uk-button-danger uk-text-center" value="Delete" />
        </div>
      </form:form>
    </div>
  </div>

  <script src="resources/js/jquery.min.js"></script>
  <script src="resources/js/uikit.min.js"></script>
  <script src="resources/js/uikit-icons.min.js"></script>
</body>

</html>