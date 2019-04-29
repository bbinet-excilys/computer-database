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
        <spring:message code='page.title.read.computer' />
      </h1>
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
      <dt>
        <spring:message code='table.header.name' />:</dt>
      <dd>${computerDTO.name}</dd>
      <dt>
        <spring:message code='table.header.introduced' />:</dt>
      <dd>${computerDTO.introduced}</dd>
      <dt>
        <spring:message code='table.header.discontinued' />:</dt>
      <dd>${computerDTO.discontinued}</dd>
      <dt>
        <spring:message code='table.header.companyname' />:</dt>
      <dd>${computerDTO.companyName}</dd>
    </dl>

  </div>
</div>

<script src="<c:url value=' /computerdatabase/resources/js/jquery.min.js'/>"> </script>
<script src="<c:url value=' /computerdatabase/resources/js/uikit.min.js'/>"> </script>
<script src="<c:url value=' /computerdatabase/resources/js/uikit-icons.min.js'/>"> </script>
</body>

</html>