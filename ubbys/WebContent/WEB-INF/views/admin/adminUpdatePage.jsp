<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
<div class="container">
    
   
  <form enctype="multipart/form-data" method="POST" action="/ubbys/adminUser/list"
    class="form-signup col-4 mx-auto needs-validation" novalidate>
    <h1 class="h3 mb-3 fw-normal">회원 정보 수정</h1>
    <div class="form-floating mb-3">
    
      <input type="email" class="form-control" id="inputEmail"
        name="inputEmail" value="${user.userEmail}"
        disabled> <label for="inputEmail">이메일</label>
        
    </div>
    <div class="form-floating mb-3">
      <input type="text" class="form-control" id="inputName"
        name="inputName" placeholder="닉네임 입력"
        value="${user.userNickname}" required>
      <label for="inputName">닉네임</label>
    </div>
    <div class="mb-3">
      <a href="${contextPath}/user/changePw" class="btn btn-primary">비밀번호
        변경하기</a>
    </div>
    <div class="mb-3">
      <c:choose>
      <c:when test="${ empty u.userPic }">
      <label for="userImage" class="form-label">프로필 사진 (선택사항)</label> 
        <img class="user-image rounded-circle mb-3" id="userImagePreview"
          src="https://via.placeholder.com/150" /> 
      </c:when>
      <c:otherwise>
        <img class="user-image mb-3 mx-auto d-block" src="${contextPath}/${user.userPic}" height="150">
      </c:otherwise>
      </c:choose>
        <input accept="image/*" class="form-control" type="file" id="userImageInput" 
        name="userImageInput" onchange="loadFile(event)">
    </div>
    <div class="form-floating mb-3">
      <input type="url" class="form-control" id="inputWebsite"
        name="inputWebsite" placeholder="https://..." value="${user.userLink}"> <label
        for="inputWebsite">웹사이트 (선택사항)</label>
    </div>
    <div class="form-floating mb-3">
      <input type="text" class="form-control" id="inputInterest"
        name="inputInterest" placeholder="예) Javascript, Go..." value="${u.userInterest}">
      <label for="inputInterest">관심분야 (선택사항)</label>
    </div>
    <div class="form-floating mb-3">
      <textarea class="form-control" id="inputIntroduce"
        name="inputIntroduce" style="height: 100px">${user.userIntroduce}</textarea>
      <label for="inputIntroduce">자기소개</label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" type="submit">정보
      수정</button>
  </form>
  <div class="col-4 mx-auto mt-10">
    <a href="${contextPath}/user/deleteAccount" class="text-danger">회원
      탈퇴</a>
  </div>
  
</div>

<jsp:include page="../common/footer.jsp" />