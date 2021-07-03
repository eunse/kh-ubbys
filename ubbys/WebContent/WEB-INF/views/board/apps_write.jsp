<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp" />
    <div class="container">
      <h1 class="h3 mt-5">apps</h1>
      <form class="needs-validation" id="apps-write" method="post" enctype="multipart/form-data" role="form" action="write" novalidate>
        <div class="row align-items-end">
          <div class="col-md-4">
            <div class="mb-3">
              <label for="userImage" class="form-label">앱 아이콘</label>
              <div id="uploadImagePreview" class="apps-image__preview rounded-3 mb-3" style="background-image: url(https://via.placeholder.com/150.png?text=No+Image);"></div>
              <input accept="image/*" class="form-control" type="file" id="userImageInput" onchange="loadFile(event)" name="appIcon" required>
              <div class="invalid-feedback">
                이미지를 등록해주세요.
              </div>
            </div>
          </div>
          <c:if test="${ !empty category}"> 
          <div class="col-md-4">
            <div class="mb-3">
              <label class="form-label" for="selectCategory">카테고리</label>
              <select class="form-select" id="selectCategory" name="selectCategory" required>
                <option option disabled>카테고리 선택</option>
                <c:forEach items="${category}" var="c">
                <option value="${c.categoryId}">${c.categoryName}</option>
                </c:forEach>
              </select>
              <div class="invalid-feedback">
                카테고리를 선택해주세요.
              </div>
            </div>
          </div>
          </c:if>
          <div class="col-md-4">
            <div class="mb-3">
              <label class="form-label" for="inputTitle">앱 이름</label>
              <input type="text" class="form-control" id="inputTitle" name="inputTitle" placeholder="앱 이름을 입력해주세요" required>
              <div class="invalid-feedback">
                앱/프로그램의 이름을 입력해주세요.
              </div>
            </div>
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label" for="inputContent">설명</label>
          <textarea class="form-control" id="inputContent" name="inputContent" style="height: 300px" required></textarea>
          <div class="invalid-feedback">
            내용을 입력해주세요.
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label" for="inputDownloadUrl">다운로드 URL</label>
          <input type="url" class="form-control" id="inputDownloadUrl" name="inputDownloadUrl" placeholder="https://" required>
          <div class="invalid-feedback">
            다운로드가 가능한 웹사이트의 URL을 입력해주세요.
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label" for="inputTag">태그</label>
          <input type="text" class="form-control" id="inputTag" placeholder="스페이스바 키로 추가">
          <div class="tag-list my-3 h5">
            
          </div>
          <input type="hidden" id="tagString" name="tagString" value=""/>
        </div>
      <hr>
      <button class="btn btn-outline-primary" type="submit">작성 완료</button>
      </form>
    </div>
    <script src="${contextPath}/resources/js/common_form_validation.js" defer></script>
    <script src="${contextPath}/resources/js/hashtag.js" defer></script>
<jsp:include page="../common/footer.jsp" />