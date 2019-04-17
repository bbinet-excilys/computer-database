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
        <h1>Edit Computer</h1>
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
      <form:form method="post" modelAttribute="editComputerForm" class="uk-width-1-2 uk-form-horizontal"
        id="editComputerForm">
        <div class="uk-margin">
          <div class="uk-inline uk-width-1-1">
            <form:label path="name" cssClass="uk-form-label">Computer name</form:label>
            <form:input path="name" type="text" cssClass="uk-input" />
            <form:errors path="name" cssClass="uk-text-danger" />
          </div>
        </div>
        <div class="uk-margin">
          <div class="uk-width-1-2">
            <form:label path="introduced" cssClass="uk-form-label">Introduction date</form:label>
            <form:input path="introduced" type="date" cssClass="uk-input" />
            <form:errors path="introduced" cssClass="uk-text-danger" />
          </div>
        </div>
        <div class="uk-margin">
          <div class="uk-width-1-2">
            <form:label path="discontinued" cssClass="uk-form-label">Discontinued date</form:label>
            <form:input path="discontinued" type="date" cssClass="uk-input" />
            <form:errors path="discontinued" cssClass="uk-text-danger" />
          </div>
        </div>
        <div class="uk-margin">
          <div class="uk-inline uk-width-1-1">
            <form:label path="companyId" cssClass="uk-form-label">Company</form:label>
            <form:select path="companyId" type="select" cssClass="uk-select">
              <form:option value="" label="Company" />
              <form:options items="${companies}" itemValue="id" itemLabel="name" />
            </form:select>
            <form:errors path="companyId" cssClass="uk-text-danger" />
          </div>
        </div>
        <div class="uk-margin">
          <div class=" uk-width-1-1">
            <a href="dashboard" class="uk-button uk-button-danger">Cancel</a>
          </div>
        </div>
        <div class="uk-margin">
          <div class="uk-width-1-1">
            <input type="submit" class="uk-button uk-button-primary" value="Submit"></input>
          </div>
        </div>
      </form:form>
    </div>
  </div>
  <script src="resources/js/jquery.min.js"></script>
  <script src="resources/js/uikit.min.js"></script>
  <script src="resources/js/uikit-icons.min.js"></script>
  <script src="resources/js/computerForms.js"></script>
</body>

</html>