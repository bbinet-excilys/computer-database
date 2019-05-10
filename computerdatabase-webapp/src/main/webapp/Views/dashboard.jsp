<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:url var="baseURL" value="/computers/" />
<jsp:include page="basepage.jsp" />
<div class="uk-container uk-container-center uk-margin-large-bottom">

<c:out value="${token}"></c:out>

  <div class="uk-grid">
    <div class="uk-width-1-1">
      <h1>
        <spring:message code="page.title.list.computer" arguments="${dashboardDTO.count}" />
      </h1>
    </div>
  </div>

  <div class="uk-grid uk-flex " uk-grid>
    <div class="uk-width-1-2@m uk-flex-left ">
      <form:form modelAttribute="dashboardDTO" method="GET">
        <form:hidden path="page" />
        <form:hidden path="pageSize" value="10" />
        <form:hidden path="pageMax" />
        <form:label path="searchName" cssClass="uk-form-label"></form:label>
        <form:input type="text" path="searchName" cssClass="uk-input uk-form-small" />
        <input type="submit" value="<spring:message code='button.search' />"
          class="uk-button-small uk-button-primary" />
      </form:form>
    </div>
    <div class="uk-width-1-4@m">
      <div class="uk-button-group uk-grid uk-flex-center uk-flex-middle" uk-grid>
        <form:form modelAttribute="dashboardDTO" method="GET" cssClass="uk-width-1-3">
          <form:hidden path="page" />
          <form:hidden path="pageSize" value="10" />
          <form:hidden path="pageMax" />
          <form:hidden path="searchName" />
          <input type="submit" value="10" class="uk-button  uk-text-center" />
        </form:form>
        <form:form modelAttribute="dashboardDTO" method="GET" cssClass="uk-width-1-3">
          <form:hidden path="page" />
          <form:hidden path="pageSize" value="50" />
          <form:hidden path="pageMax" />
          <form:hidden path="searchName" />
          <input type="submit" value="50" class="uk-button uk-text-center" />
        </form:form>
        <form:form modelAttribute="dashboardDTO" method="GET" cssClass="uk-width-1-3">
          <form:hidden path="page" />
          <form:hidden path="pageSize" value="100" />
          <form:hidden path="pageMax" />
          <form:hidden path="searchName" />
          <input type="submit" value="100" class="uk-button uk-text-center" />
        </form:form>
      </div>
    </div>
    <div class=" uk-width-1-4@m">
      <form:form method="POST" cssClass="uk-width-1-3" action="computers">
        <input type="submit" class="uk-button uk-button-primary uk-button-small uk-flex-right"
          value="<spring:message code='page.title.create.computer' />" />
      </form:form>
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
              <button class="uk-button uk-button-danger uk-button-small uk-padding-small-left">
                <span uk-icon="icon: trash"></span>
              </button>
            </th>
            <th class="uk-width-small"><input class="uk-checkbox" type="checkbox" id="selectall" /></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${computers }" var="computer">
            <tr>
              <td style="font-weight: bold; color: black">${computer.id}</td>
              <td><a href="${baseURL}${computer.id}" class="uk-link uk-link-heading">${computer.name}</a>
              </td>
              <td>${computer.introduced}</td>
              <td>${computer.discontinued}</td>
              <td>${computer.companyName}</td>
              <td>
                <form:form method="POST" action="${baseURL}${computer.id}">
                  <input type="submit" class="uk-button uk-button-primary uk-button-small"
                    value="<spring:message code='navigation.item.update' />" />
                </form:form>
              </td>
              <td>
                <form:form method="POST" action="${baseURL}delete/${computer.id}">
                  <input type="hidden" name="submitted" value="true" />
                  <input type="submit" class="uk-button uk-button-primary uk-button-small"
                    value="<spring:message code='navigation.item.delete' />" />
                </form:form>
              </td>
              <td><input type="checkbox" name="cb" class="uk-checkbox" value="${computer.id}"></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <div class="uk-width-1-1">
      <ul class="uk-pagination uk-flex-center">
        <div class="uk-grid uk-flex-center uk-flex-middle" uk-grid>
          <c:set var="previousDisabled" value="${dashboardDTO.page<=1?'disabled':''}" />
          <c:set var="nextDisabled" value="${dashboardDTO.page>=dashboardDTO.pageMax?'disabled':''}" />
          <li class="uk-width-1-4">
            <form:form modelAttribute="dashboardDTO" method="GET">
              <form:hidden path="page" value="${dashboardDTO.page-1}" />
              <form:hidden path="pageSize" />
              <form:hidden path="pageMax" />
              <form:hidden path="searchName" />
              <input type="submit" value="<spring:message code='pagination.previous' />"
                class="uk-button uk-button-primary uk-width-1-1 uk-text-center" ${previousDisabled} />
            </form:form>
          </li>
          <li class="uk-width-1-4">
            <form:form modelAttribute="dashboardDTO" method="GET">
              <form:hidden path="page" value="1" />
              <form:hidden path="pageSize" />
              <form:hidden path="pageMax" />
              <form:hidden path="searchName" />
              <input type="submit" value="<spring:message code='pagination.first' />"
                class="uk-button uk-button-primary  uk-width-1-1 uk-text-center" />
            </form:form>
          </li>
          <li class="uk-width-1-4">
            <form:form modelAttribute="dashboardDTO" method="GET">
              <form:hidden path="page" value="${dashboardDTO.pageMax}" />
              <form:hidden path="pageSize" />
              <form:hidden path="pageMax" />
              <form:hidden path="searchName" />
              <input type="submit" value="<spring:message code='pagination.last' />"
                class="uk-button uk-button-primary  uk-width-1-1 uk-text-center" />
            </form:form>
          </li>
          <li class="uk-width-1-4">
            <form:form modelAttribute="dashboardDTO" method="GET">
              <form:hidden path="page" value="${dashboardDTO.page+1}" />
              <form:hidden path="pageSize" />
              <form:hidden path="pageMax" />
              <form:hidden path="searchName" />
              <input type="submit" value="<spring:message code='pagination.next' />"
                class="uk-button uk-button-primary  uk-width-1-1 uk-text-center" ${nextDisabled} />
            </form:form>
          </li>
        </div>
      </ul>
    </div>

  </div>
</div>
<script src="<c:url value=' /computerdatabase-webapp/resources/js/jquery.min.js'/>"> </script>
<script src="<c:url value=' /computerdatabase-webapp/resources/js/uikit.min.js'/>"> </script>
<script src="<c:url value=' /computerdatabase-webapp/resources/js/uikit-icons.min.js'/>"> </script>
<script src="<c:url value=' /computerdatabase-webapp/resources/js/sortable.min.js'/>"> </script>
<script src="<c:url value=' /computerdatabase-webapp/resources/js/dashboard.js'/>"> </script>
</body>

</html>