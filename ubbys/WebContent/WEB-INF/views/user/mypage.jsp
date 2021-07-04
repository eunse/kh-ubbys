<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="application"
  value="${pageContext.servletContext.contextPath}" />
<jsp:include page="../common/header.jsp" />
<div class="container">
  <h1 class="h3 mt-5">마이 페이지</h1>
  <div class="mypage-info mb-3">
    <div class="row d-flex justify-content-center">
      <div class="col-2">
        <c:choose>
          <c:when test="${ empty sessionScope.loginUser.userPic }">
            <img class="user-image rounded-circle mb-3 mx-auto d-block"
              id="userImagePreview"
              src="https://via.placeholder.com/150" />
          </c:when>
          <c:otherwise>
            <img class="user-image rounded-circle mb-3 mx-auto d-block"
              id="userImage"
              src="${contextPath}/${sessionScope.loginUser.userPic}">
          </c:otherwise>
        </c:choose>
        <h2 class="h4 text-center">${sessionScope.loginUser.userNickname}</h2>
      </div>
      <div class="col-3">
        <ul class="list-unstyled">
          <li class="bi bi-envelope">${sessionScope.loginUser.userEmail}</li>
          <li class="bi bi-house-door">${sessionScope.loginUser.userLink}</li>
          <li class="bi bi-palette">${sessionScope.loginUser.userInterest}</li>
          <li class="bi bi-card-text">${sessionScope.loginUser.userIntroduce}
          </li>
        </ul>
        <a href="${contextPath}/user/update" class="btn btn-primary">내
          정보 수정</a>
      </div>
    </div>
  </div>
  <div class="row mb-5">
    <%-- 내 appsList --%>
    <div class="col-md-8 list-apps">
      <div class="row">
        <c:choose>
          <c:when test="${empty appsList }">
            <h2 class="h4 mb-3">게시글이 존재하지 않습니다.</h2>
          </c:when>

          <c:otherwise>
            <h2 class="h4 mb-3">내 apps 게시글</h2>
            <c:forEach items="${appsList }" var="apps">
              <div class="col-6 mb-3">
                <div class="card">
                  <div class="card-body">
                    <img src="https://via.placeholder.com/150"
                      width="45" height="45"
                      class="rounded-3 float-start me-2" alt="">
                    <button
                      class="btn btn-outline-secondary btn-like float-end">
                      <i class="bi bi-heart"></i> 123
                    </button>
                    <h5 class="card-title">${apps.appsTitle }</h5>
                    <h6 class="card-subtitle mb-2 text-muted">카테고리
                      : ${apps.categoryName }</h6>
                    <p class="card-text">본문 : ${apps.appsContent }</p>
                    <a href="#" class="card-hashtag">#${apps.hashTag}</a> <a href="#" class="card-hashtag">#해시태그</a>
                  </div>
                </div>
              </div>
            </c:forEach>
            <a href="#" class="btn btn-outline-primary">더 보기</a>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <div class="col-md-4 list-sm-qna">
      <c:choose>
        <c:when test="${empty myQnaList }">
          <h2 class="h4 mb-3">게시글이 존재하지 않습니다.</h2>
        </c:when>
        <%-- 내 qnaList --%>
        <c:otherwise>
          <h2 class="h4 mb-3">내 qna 게시글</h2>
          <c:forEach items="${myQnaList }" var="qna">
            <div class="list-group mb-3">
              <a href="#" class="list-group-item list-group-item-action">
                <div class="d-flex w-100 justify-content-between">
                  <h5 class="mb-1">${ qna.qnaTitle }</h5>
                </div> <small><i class="bi bi-heart"></i> ${ qna.qnaLike }</small>
                <small><i class="bi bi-chat"></i> ${ qna.qnaReplyCount }</small>
              </a>
            </div>
          </c:forEach>
          <a href="#" class="btn btn-outline-primary" id="load">더 보기</a>
        </c:otherwise>
      </c:choose>
    </div>


  </div>
  <div class="col-12">
    <c:choose>
      <c:when test="${empty myReplyList }">
        <h2 class="h4 mb-3">게시글이 존재하지 않습니다.</h2>
      </c:when>
      <%-- 내 댓글 목록 --%>
      <c:otherwise>
        <h2 class="h4 mb-3">내가 쓴 댓글</h2>
        <div class="list-group">
          <c:forEach items="${myReplyList }" var="myReplyList">
            <a href="#" class="list-group-item list-group-item-action">
              <p class="mb-1">${myReplyList.replyContent }</p> 
              <small class="text-muted">${myReplyList.replyDate }</small>
            </a>
          </c:forEach>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</div>
<jsp:include page="../common/footer.jsp" />