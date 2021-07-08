<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="application"
	value="${pageContext.servletContext.contextPath}" />
<jsp:include page="header.jsp" />

<div class="container d-flex justify-content-center">
  <form method="POST" action="${contextPath}/user/adminDeleteAccount" class="form-signup col-4 needs-validation" novalidate>
    <h1 class="h3 mb-3 fw-normal">회원 탈퇴</h1>
    
    <button class="w-100 btn btn-lg btn-primary" type="submit">회원 탈퇴</button>
  </form>

</div>

<jsp:include page="footer.jsp" />

