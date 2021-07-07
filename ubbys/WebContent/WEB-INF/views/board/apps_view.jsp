<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/header.jsp" />
    <div class="container apps-view">
      <h1 class="h3 my-5">apps</h1>
      <div class="row mb-4">
        <div class="col mb-sm-3">
          <img src="${apps.appsIconUrl}" class="app-image float-start me-3 rounded"/>
          <span class="badge rounded-pill bg-secondary">${apps.categoryName}</span>
          <h2 class="h4 my-1">${apps.postTitle}</h2>
          <p>${apps.userName}</p>
          <c:forEach items="${apps.tagList}" var="tag">
            <a href="#" class="card-hashtag">#${tag.tagName}</a>
          </c:forEach> 
        </div>
        <div class="col-md-2 d-grid gap-3 mb-sm-3">
          <a href="${apps.appsLink}" target="_blank" class="btn btn-primary btn-lg"><i class="bi bi-download"></i> 다운로드</a>
          <c:choose>
          <c:when test="${like.postId == apps.postId}">
          <button type="button" class="btn btn-outline-secondary btn-lg" id="btnLike"><i class="bi bi-heart-fill text-danger"></i> <span id="likeCount">${apps.postLike}</span></button>
          <script>function getLikeId() {return ${like.postId}}</script>
          </c:when>
          <c:otherwise>
          <button type="button" class="btn btn-outline-secondary btn-lg" id="btnLike"><i class="bi bi-heart"></i> <span id="likeCount">${apps.postLike}</span></button>
          <script>function getLikeId() { return 0 }</script>
          </c:otherwise>
          </c:choose>
        </div>
      </div>
      <div class="app-content col-md-9">${apps.postContent}</div>
      <hr>
      <c:if test="${loginUser.userNo == apps.userNo }">
      <a href="write?no=${apps.postId}" class="btn btn-outline-primary">수정</a>
      <button type="button" id="deleteBtn" class="btn btn-outline-danger">삭제</button>
      </c:if>
      <small class="float-end">작성일시: ${apps.postDate}</small>
    </div>
    <script src="${contextPath}/resources/js/board_view.js" defer></script>
<jsp:include page="../common/footer.jsp" />