<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:include page="basepage.jsp" />
<div class="uk-container uk-container-center uk-margin-large-bottom">

  <div class="uk-grid uk-flex-center" uk-grid>
    <div class="uk-width-1-1">
      <h1>
        <spring:message code='page.title.update.computer' />
      </h1>
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
          <form:label path="name" cssClass="uk-form-label">
            <spring:message code='form.label.name' />
          </form:label>
          <form:input path="name" type="text" cssClass="uk-input" />
          <form:errors path="name" cssClass="uk-text-danger" />
        </div>
      </div>
      <div class="uk-margin">
        <div class="uk-width-1-2">
          <form:label path="introduced" cssClass="uk-form-label">
            <spring:message code='form.label.introduced' />
          </form:label>
          <form:input path="introduced" type="date" cssClass="uk-input" />
          <form:errors path="introduced" cssClass="uk-text-danger" />
        </div>
      </div>
      <div class="uk-margin">
        <div class="uk-width-1-2">
          <form:label path="discontinued" cssClass="uk-form-label">
            <spring:message code='form.label.discontinued' />
          </form:label>
          <form:input path="discontinued" type="date" cssClass="uk-input" />
          <form:errors path="discontinued" cssClass="uk-text-danger" />
        </div>
      </div>
      <div class="uk-margin">
        <div class="uk-inline uk-width-1-1">
          <form:label path="companyId" cssClass="uk-form-label">
            <spring:message code='form.label.company' />
          </form:label>
          <form:select path="companyId" type="select" cssClass="uk-select">
            <form:option value="" label="Company" />
            <form:options items="${companies}" itemValue="id" itemLabel="name" />
          </form:select>
          <form:errors path="companyId" cssClass="uk-text-danger" />
        </div>
      </div>
      <div class="uk-margin">
        <div class=" uk-width-1-1">
          <a href="dashboard" class="uk-button uk-button-danger">
            <spring:message code='button.cancel' /></a>
        </div>
      </div>
      <div class="uk-margin">
        <div class="uk-width-1-1">
          <input type="submit" class="uk-button uk-button-primary"
            value="<spring:message code='button.submit' />"></input>
        </div>
      </div>
      <form:hidden path="id" />
    </form:form>
  </div>
</div>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/uikit.min.js"></script>
<script src="resources/js/uikit-icons.min.js"></script>
<script src="resources/js/computerForms.js"></script>
</body>

</html>