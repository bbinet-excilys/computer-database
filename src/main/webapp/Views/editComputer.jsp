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
        <h1>Computer Edition</h1>
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
    <form class="uk-width-1-2" method="POST" id="addComputerForm">
      <fieldset class="uk-fieldset">
        <div class="uk-margin">
          <div class="uk-inline uk-width-1-1">
            <span class="uk-form-icon uk-form-icon-flip" data-uk-icon="icon: desktop"></span>
            <input class="uk-input" placeholder="Computer Name" type="text" id="computerName" name="computerName"
              value="${computer.name}">
          </div>
        </div>
        <div class="uk-margin">
          <div class="uk-inline uk-width-1-1">
            <span class="uk-form-icon uk-form-icon-flip" data-uk-icon="icon: calendar"></span>
            <input class="uk-input" placeholder="Introdction Date" type="date" id="computerIntroduced"
              name="computerIntroduced" value="computer.introduced">
          </div>
        </div>
        <div class="uk-margin">
          <div class="uk-inline uk-width-1-1">
            <span class="uk-form-icon uk-form-icon-flip" data-uk-icon="icon: calendar"></span>
            <input class="uk-input" placeholder="Discontinued Date" type="date" id="computerDiscontinued"
              name="computerDiscontinued" value="computer.discontinued">
          </div>
        </div>
        <div class="uk-margin">
          <select class="uk-select" name="computerCompanyId" id="computerCompanyId">
            <option value="">Company</option>
            <c:forEach items="${companies}" var="company">
              <c:if test="${company.id == computer.companyId}">
                <option value="${company.id}" selected="selected" class="uk-label-danger">
                  ${company.name}
                </option>
              </c:if>
              <c:if test="${company.id != computer.companyId}">
                <option value="${company.id}">${company.name}</option>
              </c:if>
            </c:forEach>
          </select>
        </div>
        <input type="hidden" id="computerCompanyName" name="computerCompanyName" value="" />
      </fieldset>
      <input type="submit" class="uk-button uk-button-primary uk-width-1-3" value="Update" />
      <a class="uk-button uk-button-danger uk-width-1-3" href="dashboard">Cancel</a>
    </form>
  </div>

  <script src="js/jquery.min.js"></script>
  <script src="js/uikit.min.js"></script>
  <script src="js/uikit-icons.min.js"></script>
  <script src="js/computerForms.js"></script>
</body>

</html>