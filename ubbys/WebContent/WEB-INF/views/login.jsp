<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="common/header.jsp" />

    <div class="container d-flex justify-content-center">
      <form class="form-signin text-center col-4" method="POST" action="${contextPath}/login">
        <img class="mb-4" src="${contextPath}/resources/img/logo.png" alt="" height="57">
        <h1 class="h3 mb-3 fw-normal">로그인</h1>

        <div class="form-floating">
          <input type="email" class="form-control" id="floatingEmail" name="inputEmail" placeholder="이메일을 입력해주세요." value="${cookie.saveId.value}" required>
          <label for="floatingEmail">이메일</label>
        </div>
        <div class="form-floating">
          <input type="password" class="form-control" id="floatingPassword" name="inputPw" placeholder="비밀번호를 입력해주세요." required>
          <label for="floatingPassword">비밀번호</label>
        </div>

        <div class="checkbox mb-3">
          <c:if test="${!empty cookie.saveId.value }">
            <c:set var="checked" value="checked"/>
          </c:if>
          <label>
            <input type="checkbox" name="saveId" id="saveId" ${checked}> Email 기억하기
          </label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button>
        <p class="mt-5 mb-3 text-muted"><a href="signup">아직 회원이 아니신가요?</a></p>
      </form>
    </div>
<jsp:include page="common/footer.jsp" />