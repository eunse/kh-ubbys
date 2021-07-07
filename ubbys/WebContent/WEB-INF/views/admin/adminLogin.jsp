<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="application"
	value="${pageContext.servletContext.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ubbys admin</title>
<!-- Bootstrap core CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
	crossorigin="anonymous"></script>

<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>
</head>

<body>
	<main>


		<div class="container d-flex justify-content-center">
			<form class="form-signin text-center col-4" method="POST"
				action="${contextPath}/adminLogin">

				<h1 class="h3 mb-3 fw-normal">관리자 로그인</h1>

				<div class="form-floating">
					<input type="email" class="form-control" id="floatingEmail"
						name="inputEmail" placeholder="이메일을 입력해주세요." required> <label
						for="floatingEmail">이메일</label>
				</div>
				<div class="form-floating">
					<input type="password" class="form-control" id="floatingPassword"
						name="inputPw" placeholder="비밀번호를 입력해주세요." required> <label
						for="floatingPassword">비밀번호</label>
				</div>

				<div class="checkbox mb-3">
					<label> <input type="checkbox" name="saveId" id="saveId">
						Email 기억하기
					</label>
				</div>
				<button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button>

			</form>
		</div>
		<jsp:include page="footer.jsp" />

	