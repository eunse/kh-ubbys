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
          <c:when test="${ empty user.userPic }">
            <img class="user-image rounded-circle mb-3 mx-auto d-block"
              id="userImagePreview"
              src="https://via.placeholder.com/150" />
          </c:when>
          <c:otherwise>
            <img class="user-image rounded-circle mb-3 mx-auto d-block"
              id="userImage"
              src="${sessionScope.loginUser.userPic}">
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
    <h2 class="mb-3">내 apps 게시글</h2>
      <div class="row">
        <c:choose>
          <c:when test="${empty appsList}">
            <p>등록된 게시물이 없습니다.</p>
          </c:when>
          <c:otherwise>
            <c:forEach items="${appsList}" var="apps">
              <div class="col-md-6 mb-3">
                <div class="card">
                  <div class="card-body">
                    <img src="${apps.appsIconUrl}" width="45" height="45" class="rounded-3 float-start me-2" alt="">
                    <span class="btn btn-outline-secondary btn-like float-end"><i class="bi bi-heart"></i>
                      ${apps.postLike}</span>
        
                    <h5 class="card-title"><a href="apps/view?no=${apps.postId}&cp=${pagination.currentPage}"
                        class="apps-link stretched-link">${apps.postTitle}</a></h5>
                    <h6 class="card-subtitle mb-2 text-muted">${apps.categoryName}</h6>
        
                    <p class="card-text">${apps.appsSummary}</p>
                  </div>
                  <div class="card-footer">
                    <c:forEach items="${apps.tagList}" var="tag">
                      <a href="#" class="card-hashtag">#${tag.tagName}</a>
                    </c:forEach>
                  </div>
                </div>
              </div>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </div>
      <a href="apps/list" class="btn btn-outline-primary">더 보기</a>
    </div>

    <div class="col-md-4 list-sm-qna">
      <h2 class="mb-3">내 qna 게시글</h2>
      <div class="list-group mb-3">
        <c:choose>
          <c:when test="${empty qnaList}">
            <p>등록된 게시물이 없습니다.</p>
          </c:when>
          <c:otherwise>
            <c:forEach items="${ qnaList }" var="qna">
              <a href="qnaView?no=${ qna.qnaPostId }" class="list-group-item list-group-item-action">
                <div class="d-flex w-100 justify-content-between">
                  <h5 class="mb-1">${ qna.qnaTitle }</h5>
                </div>
                <small>${ qna.userNickname }</small>
                <small><i class="bi bi-heart"></i> ${ qna.qnaLike }</small>
                <small><i class="bi bi-chat"></i> ${ qna.qnaReplyCount }</small>
              </a>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </div>
      <a href="qnaList" class="btn btn-outline-primary">더 보기</a>
    </div>
  </div>
  <div class="col-12">
    <h2 class="h4 mb-3">내가 쓴 댓글</h2>
    <c:choose>
      <c:when test="${empty myReplyList }">
        <p>댓글이 존재하지 않습니다.</p>
      </c:when>
      <%-- 내 댓글 목록 --%>
      <c:otherwise>
        <div class="list-group">
          <c:forEach items="${myReplyList }" var="myReplyList">
            <a href="qnaView?no=${myReplyList.qnaPostId }&cp=${pagination.currentPage}" class="list-group-item list-group-item-action">
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