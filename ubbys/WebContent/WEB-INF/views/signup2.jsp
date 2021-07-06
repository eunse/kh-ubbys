<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="common/header.jsp" />
    <div class="container d-flex justify-content-center">
      <form class="form-signup col-4" method="POST" enctype="multipart/form-data" role="form" action="${contextPath}/signup/add" novalidate>
        <h1 class="h3 mb-3 fw-normal">선택 정보 입력</h1>
        <div class="mb-3">
          <label for="userImage" class="form-label">프로필 사진</label>
          <div id="uploadImagePreview" class="user-image__preview rounded-circle mb-3"
            style="background-image: url(https://via.placeholder.com/150.png?text=No+Image);"></div>
          <input accept="image/*" class="form-control" type="file" id="userImageInput" onchange="loadFile(event)" name="appIcon">

        </div>
        <div class="form-floating mb-3">
          <input type="url" class="form-control" id="inputWebsite" name="inputWebsite" placeholder="https://...">
          <label for="inputWebsite">웹사이트 (선택사항)</label>
        </div>
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="inputInterest" name="inputInterest" placeholder="예) Javascript, Go...">
          <label for="inputInterest">관심분야 (선택사항)</label>
        </div>
        <div class="form-floating mb-3">
          <textarea class="form-control" id="inputIntroduce" name="inputIntroduce" style="height: 100px"></textarea>
          <label for="inputIntroduce">자기소개</label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit">정보 입력</button>
      </form>
    </div>
    <script src="${contextPath}/resources/js/common_form_validation.js" defer></script>
<jsp:include page="common/footer.jsp" />