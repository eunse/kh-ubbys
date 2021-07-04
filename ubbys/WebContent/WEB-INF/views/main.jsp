<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="common/header.jsp" />
    <div class="p-5 mb-4 bg-primary bg-gradient">
      <div class="container py-5">
        <h1 class="display-5 fw-bold text-light">Develop<br>Share<br>Discover</h1>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <div class="col-md-8 list-apps">
          <h2 class="mb-3">recently_added_apps</h2>
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
                  <span class="btn btn-outline-secondary btn-like float-end"><i class="bi bi-heart"></i> ${apps.postLike}</span>
                  
                  <h5 class="card-title"><a href="view?no=${apps.postId}&cp=${pagination.currentPage}" class="apps-link stretched-link">${apps.postTitle}</a></h5>
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
          <a href="#" class="btn btn-outline-primary">더 보기</a>
        </div>
        <div class="col-md-4 list-sm-qna">
          <h2 class="mb-3">recently_added_qna</h2>
          <div class="list-group mb-3">
           <c:choose>
            <c:when test="${empty qnaList}">
              <p>등록된 게시물이 없습니다.</p>
            </c:when>
            <c:otherwise>
             <c:forEach items="${ qnaList }" var="qna">
              <a href="#" class="list-group-item list-group-item-action">
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
          <a href="#" class="btn btn-outline-primary">더 보기</a>
        </div>
      </div>
    </div>
<jsp:include page="common/footer.jsp" />