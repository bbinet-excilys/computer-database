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
        <spring:message code='page.title.delete.computer' />
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

  <div class="uk-grid uk-flex-center uk-flex-middle uk-margin" uk-grid>
    <form:form method="POST" class="uk-width-1-2" modelAttribute="computerDTO" id="deleteComputerForm">
      <form:select class="uk-select uk-width-1-1" path="id">
        <form:options items="${computers}" itemValue="id" itemLabel="name"></form:options>
      </form:select>
      <input type="hidden" name="submitted" value="true" />
      <div class="uk-grid uk-flex-right uk-margin" uk-grid>
        <input type="submit" class="uk-button uk-button-danger uk-text-center"
          value="<spring:message code='button.delete' />" />
      </div>
    </form:form>
  </div>
</div>

<script src="<c:url value=' /computerdatabase/resources/js/jquery.min.js'/>"> </script>
<script src="<c:url value=' /computerdatabase/resources/js/uikit.min.js'/>"> </script>
<script src="<c:url value=' /computerdatabase/resources/js/uikit-icons.min.js'/>"> </script>
</body>

</html>