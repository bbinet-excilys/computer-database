<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:include page="basepage.jsp" />
<div class="uk-container uk-container-center uk-margin-large-bottom">


  <div class="uk-grid">
    <div class="uk-width-1-1">
      <h1>
        <spring:message code="page.title.list.computer" arguments="${count}" />
      </h1>
    </div>
  </div>

  <div class="uk-grid uk-flex" uk-grid>
    <div class="uk-width-3-5">
      <form class="uk-form-horizontal" method="GET" id="searchForm">
        <div class="uk-width-1-2">
          <label class="uk-form-label" for="searchComputerByName">
            <spring:message code="form.label.search" /></label>
          <div class="uk-form-controls uk-form-controls-text">
            <input class="uk-input uk-form-width-medium uk-form-small" type="text" id="searchName" name="searchName"
              value="${param.searchName}" />
          </div>
        </div>
        <input type="hidden" id="page" value="1" name="page" />
        <input type="hidden" id="pageSize" value="${pageSize}" name="pageSize" />
        <input type="submit" class="uk-button uk-button-primary uk-width-1-3"
          value="<spring:message code='button.search' />" />
      </form>
    </div>
    <div class="uk-width-1-5">
      <div class="uk-button-group">
        <a class="uk-button uk-button-default" href="?page=1&pageSize=10&searchName=${searchName}">10</a>
        <a class=" uk-button uk-button-default" href="?page=1&pageSize=50&searchName=${searchName}">50</a>
        <a class=" uk-button uk-button-default" href="?page=1&pageSize=100&searchName=${searchName}">100</a>
      </div>
    </div>
    <div class=" uk-width-1-5">
      <a class=" uk-button uk-button-primary uk-button-small uk-flex-right" href="addComputer">
        <spring:message code="page.title.create.computer" /></a>
    </div>
  </div>

  <div class="uk-grid">
    <div class="uk-width-1-1">
      <table class="uk-table uk-table-striped uk-margin uk-padding" id="computerTable" data-sortable>
        <thead class=" uk-background-secondary">
          <tr>
            <th class="uk-width-small uk-text-primary">
              <spring:message code="table.header.id" />
            </th>
            <th class="uk-width-medium uk-text-primary">
              <spring:message code="table.header.name" />
            </th>
            <th class="uk-width-medium uk-text-primary">
              <spring:message code="table.header.introduced" />
            </th>
            <th class="uk-width-medium uk-text-primary">
              <spring:message code="table.header.discontinued" />
            </th>
            <th class="uk-width-medium uk-text-primary">
              <spring:message code="table.header.companyname" />
            </th>
            <th class="uk-width-small uk-text-primary"></th>
            <th class="uk-width-small uk-text-primary">
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
              <td>${computer.companyName}</td>
              <td><a href="editcomputer?computerId=${computer.id}"
                  class="uk-button uk-button-primary uk-button-small uk-padding-small-left"><span
                    uk-icon="icon: pencil"></span></a></td>
              <td><button class="uk-button uk-button-danger uk-button-small uk-padding-small-left"><span
                    uk-icon="icon: trash"></span></button></td>
              <td>
                <input type="checkbox" name="cb" class="uk-checkbox" value="${computer.id}">
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <div class="uk-width-1-1">
      <ul class="uk-pagination uk-flex-center">
        <c:if test="${page>1}">
          <li><a href="?page=${page-1}&pageSize=${pageSize}&searchName=${param.searchName}"><span
                uk-pagination-previous></span></a></li>
        </c:if>
        <c:if test="${!(page>1)}">
          <li class="uk-disabled"><a><span uk-pagination-previous></span></a></li>
        </c:if>
        <li><a href="?page=1&pageSize=${pageSize}&searchName=${param.searchName}">
            <spring:message code="pagination.first" /></a></li>
        <li><a href="?page=${pageMax}&pageSize=${pageSize}&searchName=${param.searchName}">
            <spring:message code="pagination.last" /></a></li>
        <c:if test="${page<pageMax}">
          <li><a href="?page=${page+1}&pageSize=${pageSize}&searchName=${param.searchName}"><span
                uk-pagination-next></span></a></li>
        </c:if>
        <c:if test="${!(page<pageMax)}">
          <li class="uk-disabled"><a><span uk-pagination-next></span></a></li>
        </c:if>
      </ul>
    </div>

  </div>
</div>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/uikit.min.js"></script>
<script src="resources/js/uikit-icons.min.js"></script>
<script src="resources/js/sortable.min.js"></script>
<script src="resources/js/dashboard.js"></script>
</body>

</html>