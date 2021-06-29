<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />    

<div class="container d-flex justify-content-center">
  <form method="POST" action="${contextPath}/user/deleteAccount" class="form-signup col-4 needs-validation" novalidate>
    <h1 class="h3 mb-3 fw-normal">회원 탈퇴</h1>
    <div class="form-floating mb-3">
      <input type="password" class="form-control" id="inputPresentPw" name="inputPresentPw" placeholder="현재 비밀번호 입력" required>
      <label for="inputPresentPw">현재 비밀번호</label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" type="submit">회원 탈퇴</button>
  </form>

</div>

<jsp:include page="../common/footer.jsp" />