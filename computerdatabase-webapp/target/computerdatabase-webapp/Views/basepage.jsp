<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>

<head>
  <title>
    <spring:message code="appname" />
  </title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="utf-8">
  <link href="<c:url value=" /computerdatabase-webapp/resources/css/uikit.min.css"/>" rel="stylesheet" media="screen">
  <link href="<c:url value=" /computerdatabase-webapp/resources/css/main.css"/>" rel="stylesheet">
  <c:url var="baseURL" value="/computers/" />
</head>

<body>
  <div uk-sticky="sel-target: .uk-navbar; cls-active: uk-navbar-sticky" class="uk-margin-bottom">
    <nav class="uk-navbar uk-background-secondary uk-dark" uk-navbar="mode: click; boundary-align:true">
      <div class="uk-navbar-left">
        <a class="uk-navbar-item uk-logo uk-hidden_small" href="${baseURL}">
          <spring:message code="appname" /> &nbsp;<span uk-icon="icon: desktop"></span></a>
        <ul class="uk-navbar-nav">
          <li>
            <form:form method="GET" action="${baseURL}">
              <input type="submit" value="<spring:message code='navigation.title.computer' />"
                class="uk-button uk-button-link uk-link-test" />
            </form:form>
            <div uk-dropdown>
              <ul class="uk-nav uk-dropdown-nav">
                <li>
                  <form:form method="GET" action="${baseURL}">
                    <input type="submit" value="<spring:message code='navigation.item.list' />"
                      class="uk-button uk-button-text" />
                  </form:form>
                <li>
                  <form:form method="POST" action="${baseURL}">
                    <input type="submit" value="<spring:message code='navigation.item.add' />"
                      class="uk-button uk-button-text" />
                  </form:form>
                </li>
                <li>
                  <form:form method="POST" action="${baseURL}delete">
                    <input type="submit" value="<spring:message code='navigation.item.delete' />"
                      class="uk-button uk-button-text" />
                  </form:form>
                </li>
              </ul>
            </div>
          </li>
        </ul>
      </div>
      <div class="uk-navbar-right">
        <ul class="uk-navbar-nav">
          <li class="uk-navbar-item">
            <form:form path="lang" method="GET">
              <input type="hidden" value="fr" name="lang" id="lang" />
              <input type="submit" value="fr" class="uk-button uk-button-text" style="color: #999" />
            </form:form>
          </li>
          <li class="uk-navbar-item">
            <form:form path="lang" method="GET">
              <input type="hidden" value="en" name="lang" id="lang" />
              <input type="submit" value="en" class="uk-button uk-button-text" style="color: #999" />
            </form:form>
          </li>
        </ul>
      </div>
    </nav>
  </div>