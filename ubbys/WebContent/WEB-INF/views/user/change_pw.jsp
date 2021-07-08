<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<jsp:include page="../common/header.jsp" />
<div class="container d-flex justify-content-center">
	<form method="POST" action="${contextPath}/user/changePw" class="form-signup col-4 needs-validation" onsubmit="return validate()">
		<h1 class="h3 mb-3 fw-normal">비밀번호 변경</h1>
		<div class="form-floating mb-3">
			<input type="password" class="form-control" id="inputPresentPw"
				name="inputPresentPw" placeholder="현재 비밀번호 입력" required> <label
				for="inputPresentPw">현재 비밀번호</label>
            <div>
              <span id="checkPwd"></span> 
            </div>
		</div>
		<div class="form-floating mb-3">
			<input type="password" class="form-control" id="inputChangePw"
				name="inputChangePw" placeholder="변경할 비밀번호 입력" required> <label
				for="inputChangePw">변경할 비밀번호</label>
			<div id="pwHelp" class="form-text">영문/숫자를 혼합한 8자 이상</div>
            <div>
              <span id="checkPwd1"></span>
            </div>
		</div>
		<div class="form-floating mb-3">
			<input type="password" class="form-control" id="inputChangePwConfirm"
				name="inputChangePwConfirm" placeholder="변경할 비밀번호 확인" required>
			<label for="inputChangePwConfirm">변경할 비밀번호 확인</label>
            <div>
               <span id="checkPwd2"></span> 
            </div>
		</div>
		<button class="w-100 btn btn-lg btn-primary" type="submit">비밀번호
			변경</button>
	</form>

</div>
<script src="${contextPath}/resources/js/changePw_check.js" defer></script>
<jsp:include page="../common/footer.jsp" />