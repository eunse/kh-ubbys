<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/header.jsp" />
    <div class="container">
      <a href="#" class="btn btn-primary float-end">작성하기</a>
      <h1 class="h3 my-5">apps</h1>
      <div class="row">
        <div class="col-xs-12 col-sm-4">
          <select class="form-select">
            <option selected value="sortNewest">최근 작성순</option>
            <option value="sortLike">좋아요 많은 순</option>
          </select>
        </div>
        <div class="col-xs-12 col-sm-4">
          <select class="form-select">
            <option selected>카테고리 전체</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
        </div>
        <div class="col-xs-12 col-sm-4">
          <div class="input-group mb-3">
            <input type="text" class="form-control" placeholder="이름 혹은 해시태그로 검색">
            <button class="btn btn-outline-secondary" type="button" id="searchApps" name="searchApps"><i class="bi bi-search"></i> 검색</button>
          </div>
        </div>
      </div>
      <div class="row">
       <c:choose>
        <c:when test="${empty appsList}">
          <p>등록된 게시물이 없습니다.</p>
        </c:when>
        <c:otherwise>       
         <c:forEach items="${appsList}" var="apps">
          <div class="col-lg-4 col-md-6 mb-3">
           <div class="card">
            <div class="card-body">
              <img src="${apps.appsIconUrl}" width="45" height="45" class="rounded-3 float-start me-2" alt="">
              <button class="btn btn-outline-secondary btn-like float-end"><i class="bi bi-heart"></i> {apps.postLike}</button>
              <h5 class="card-title">{apps.postTitle}</h5>
              <h6 class="card-subtitle mb-2 text-muted">{apps.categoryName}</h6>
        
              <p class="card-text">{apps.appsSummary}</p>
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
      <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
          <li class="page-item disabled">
            <a class="page-link" href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li class="page-item active"><a class="page-link" href="#">1</a></li>
          <li class="page-item"><a class="page-link" href="#">2</a></li>
          <li class="page-item"><a class="page-link" href="#">3</a></li>
          <li class="page-item">
            <a class="page-link" href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
<jsp:include page="../common/footer.jsp" />