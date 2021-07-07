<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="../common/header.jsp" />
    <div class="container">
      <c:if test="${ !empty loginUser }">
        <a href="${ contextPath }/qnaWrite?cp=${ pagination.currentPage }" class="btn btn-primary float-end">작성하기</a>
      </c:if>
      <h1 class="h3 my-5">QNA</h1>
      <div class="row">
        <div class="col-xs-12 col-sm-4">
          <select class="form-select" id="sortCondition">
            <option selected value="sortNewest">최근 작성순</option>
            <option value="sortLike">좋아요 많은 순</option>
          </select>
        </div>
        <div class="col-xs-12 col-sm-4">
          <select class="form-select" id="searchCategory">
            <option value="" selected>카테고리 전체</option>
              <c:forEach items="${ qnaCategory }" var="qc">
                <option value="${ qc.qnaCategoryId }">${ qc.qnaCategoryName }</option>
              </c:forEach>
          </select>
        </div>

        <div class="col-xs-12 col-sm-4">
          <form action="${ contextPath }/qnaList" method="GET" name="searchForm" id="searchForm">
            <div class="input-group mb-3" class="qna-search-area">
                <select class="form-select" id="searchCondition" name="sc">
                  <option value="" selected>검색 조건</option>
                  <option value="qnaTitle">제목</option>
                  <option value="userNickname">작성자</option>
                </select>
                <input type="text" class="form-control" placeholder="검색어 입력" id="searchValue" name="sv">
                <button class="btn btn-outline-secondary" id="searchQnaBtn"><i class="bi bi-search"></i></button>
            </div>
          </form>
        </div>
      </div>
      <div class="qna-list list-group mb-3">

      	<c:choose>
          <c:when test="${ empty qnaList }">
            <div class="align-self-center">작성된 게시글이 없습니다.</div>
          </c:when>
          
          <c:otherwise>
            <c:forEach items="${ qnaList }" var="qna">
              <a href="qnaView?no=${ qna.qnaPostId }&sc=${param.sc }&sv=${param.sv }&cp=${ pagination.currentPage }" class="list-group-item list-group-item-action">
                <div class="d-flex flex-wrap justify-content-between">
                  <div class="category align-self-center">
                    <span class="badge bg-primary">${ qna.qnaCategoryName }</span>
                    <p class="date my-1"><c:out value="${fn:substring(qna.qnaDate,0,10)}"/><br><c:out value="${fn:substring(qna.qnaDate,11,19)}"/></p>
                  </div>
                  <div class="flex-md-grow-1 align-self-center">
                    <h5 class="title mb-1">${ qna.qnaTitle }</h5>
                    <p class="text-muted mb-0">${ qna.userNickname }</p>
                  </div>
                  <div class="board-meta d-flex align-self-center">
                    <div class="board-meta-like" id="qnaList-like-area">
                    <c:choose>
                      <c:when test="${ qna.likeFlag }">
                        <i class="bi bi-heart-fill"></i>
                      </c:when>
                      <c:otherwise>
                        <i class="bi bi-heart"></i>
                      </c:otherwise>
                    </c:choose>
                      <p class="mb-0">${ qna.qnaLike }</p>
                    </div>
                    <div class="board-meta-readcount">
                      <i class="bi bi-chat-right-text"></i>
                      <p class="mb-0">${ qna.qnaReplyCount }</p>
                    </div>
                  </div>
                </div>
              </a>
            </c:forEach>
          </c:otherwise>
      	</c:choose>
      </div>
      
      <c:choose>
        <c:when test="${ !empty param.sc && !empty param.sv }">
          <c:set var="pageURL" value="qnaList?sc=${param.sc }&sv=${param.sv }"/>
        </c:when>
        <c:otherwise>
          <c:set var="pageURL" value="qnaList?"/>
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

<jsp:include page="../common/footer.jsp" />

<!-- 카테고리 검색 시 -->
<form action="#" method="GET" name="categoryReqForm">
  <input type="hidden" name="sc" value="" id="searchCateCond">
  <input type="hidden" name="sv" value="" id="searchCateVal">
</form>

<!-- 정렬 시 -->
<form action="#" method="GET" name="sortReqForm">
  <input type="hidden" name="sc" value="" id="sortCond">
  <input type="hidden" name="sv" value="" id="sortVal">
</form>
          
<script src="${ contextPath }/resources/js/qnaSearch_check_fn.js"></script>

<script>

keepSearch();

function keepSearch(){
	
	var searchCondition = "${ param.sc }"
	var searchValue = "${ param.sv }"

	if(searchValue=="DESC"){
  		$("#sortCondition > option").each(function(index, item){
  			if($(item).val()==searchCondition) $(item).prop("selected", true);
  		});
	} 
	else if(searchCondition=="qnaCategoryId"){
  		$("#searchCategory > option").each(function(index, item){
  			if($(item).val()==searchValue) $(item).prop("selected", true);
  		});
	}
	else{
  		$("#searchCondition > option").each(function(index, item){
  			if($(item).val()==searchCondition) $(item).prop("selected", true);
  		});
  		$("#searchValue").val(searchValue);
	}
}

</script>