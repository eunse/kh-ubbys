<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../header.jsp" />
  <div class="container py-5">
    <h2>APPS</h2>
      <div class="row">
      	<div class="col-xs-12 col-sm-3">
          <select class="form-select" id="sortCondition">
            <option value="sortNewest">최근 작성순</option>
            <option value="sortLike">좋아요 많은 순</option>
          </select>
        </div>
        
      	<div class="col-xs-12 col-sm-3">
          <select id="searchCategory" class="form-select">
            <option value="" selected>카테고리 전체</option>
              <c:forEach items="${ category }" var="c">
                <option value="${ c.categoryId }">${ c.categoryName }</option>
              </c:forEach>
          </select>
        </div>
        
        <div class="col-xs-12 col-sm-6">
          <form action="${ contextPath }/admin/appsList" method="GET" name="searchForm" id="searchForm">
            <div class="input-group mb-3">
              <select class="form-select" id="searchCondition" name="sc">
                <option value="" selected>검색 조건</option>
                <option value="postTitle">제목</option>
                <option value="userName">작성자</option>
              </select>
              <input type="text" id="searchValue" name="sv" class="form-control" placeholder="제목,닉네임으로 검색하세요">
              <button class="btn btn-outline-secondary" type="submit">검색</button>
            </div>
          </form>
        </div>
      </div>  
        <table class="table table-striped table-hover w-100">
          <thead>
            <tr>
              <th scope="col">게시글 ID</th>
              <th scope="col">카테고리</th>
              <th scope="col">제목</th>
              <th scope="col">좋아요 수</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
              <th scope="col">관리</th>
            </tr>
          </thead>
          <tbody>
          	<c:choose>
          		<c:when test="${ empty appsList }">
          			<tr>
          				<td colspan="8">게시글이 존재하지 않습니다.</td>
          			</tr>
          		</c:when>
          		
          		<c:otherwise>
          			<c:forEach items="${ appsList }" var="apps">
                        <input type="hidden" name="no" value="${ apps.postId }">
          				<tr>
          					<td>${ apps.postId }</td>
          					<td>${ apps.categoryName}</td>
          					<td>
          						<a href="apps/view?no=${ apps.postId }&cp=${ pagination.currentPage }">${ apps.postTitle }</a>
          					</td>
          					<td>${ apps.postLike }</td>
          					<td>${ apps.userName }</td>
          					<td>${ apps.postDate }</td>
          					<td>
          						<a href="${ contextPath }/admin/appsUpdateform" class="btn btn-primary btn-sm">수정</a>
                				<a href="${ contextPath }/admin/appsDeleteAlert?no=${ apps.postId }" class="btn btn-danger btn-sm">삭제</a>
          					</td>
          				</tr>
          			</c:forEach>
          		</c:otherwise>
          	</c:choose>
          </tbody>
        </table>
        
        
        
    <%-- 페이징 --%>
    <c:choose><%-- 검색 시  --%>
      <c:when test="${ !empty param.sc && !empty param.sv }">
        <c:set var="pageURL" value="appsList?sc=${ param.sc }&sv=${ param.sv }"/>
      </c:when>
      <c:otherwise>
        <c:set var="pageURL" value="appsList?"/>
      </c:otherwise>
    </c:choose>
    <c:set var="prev" value="${ pageURL }&cp=${ pagination.prevPage }"/>
    <c:set var="next" value="${ pageURL }&cp=${ pagination.nextPage }"/>

    <nav aria-label="Page navigation">
      <ul class="pagination justify-content-center">
        <c:if test="${ pagination.currentPage <= pagination.pageSize }">
          <li class="page-item disabled">
            <a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
          </li>
        </c:if>
        
        <c:if test="${ pagination.currentPage > pagination.pageSize }">
          <li class="page-item">
            <a class="page-link" href="${ prev }" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
          </li>
        </c:if>
        
        <%-- 페이지 목록 --%>
        <c:forEach var="p" begin="${ pagination.startPage }" end="${ pagination.endPage }">
          <c:choose>
            <c:when test="${ p==pagination.currentPage }">
              <li class="page-item active"><a class="page-link">${ p }</a></li>
            </c:when>
            <c:otherwise>
              <li class="page-item"><a class="page-link" href="${ pageURL }&cp=${ p }">${ p }</a></li>
            </c:otherwise>
          </c:choose>
        </c:forEach>
        
        <c:if test="${ pagination.endPage < pagination.maxPage }">
          <li class="page-item">
            <a class="page-link" href="${ next }" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
          </li> 
        </c:if>
      </ul>
    </nav>
      
    </div>
<jsp:include page="../footer.jsp" />

<%-- 정렬 sc:검색조건(최신순,좋아요순) sv:검색값(ASC, DESC) --%>
<form action="#" method="GET" name="sortReqForm">
  <input type="hidden" name="sc" value="" id="sortCond">
  <input type="hidden" name="sv" value="" id="sortVal">
</form>

<%-- 카테고리 --%>
<form action="#" method="GET" name="categoryReqForm">
  <input type="hidden" name="sc" value="" id="searchCateCond">
  <input type="hidden" name="sv" value="" id="searchCateVal">
</form>

<script src="${contextPath}/resources/js/adminApps_check_fn.js"></script>

<script>
keepSearch();
function keepSearch(){
  var sc = "${ param.sc }"
  var sv = "${ param.sv }"
  if(sv=="DESC"){
      $("#sortCondition > option").each(function(index, item){
        if($(item).val()==sc) $(item).prop("selected", true);
      });
  } 
  else if(searchCondition==""){
      $("#searchCategory > option").each(function(index, item){
        if($(item).val()==sv) $(item).prop("selected", true);
      });
  }
  else{
      $("#searchCondition > option").each(function(index, item){
        if($(item).val()==sc) $(item).prop("selected", true);
      });
      $("#searchValue").val(sv);
  }
}
</script>