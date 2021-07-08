<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="application" value="${pageContext.servletContext.contextPath}"/>
<jsp:include page="../header.jsp" />
    <div class="container py-5">
      <h2>QNA</h2>
      
      	<div class="input-group mb-3 w-50" style="width: 150px !important; float: left; margin-right: 15px;">
          <select class="form-select" id="sortCondition">
            <option value="sortNewest">최근 작성순</option>
            <option value="sortLike">좋아요 많은 순</option>
          </select>
        </div>
        
      	<div class="input-group mb-3" style="width: 250px !important; float: left; margin-right: 15px;">
          <select name="searchCategory" id="searchCategory" class="form-select">
            <option value="" selected>카테고리 전체</option>
              <c:forEach items="${ qnaCategory }" var="qc">
                <option value="${ qc.qnaCategoryId }">${ qc.qnaCategoryName }</option>
              </c:forEach>
          </select>
        </div>
        
        <div class="input-group mb-3 w-50" style="float: right;">
          <select class="form-select" id="searchCondition" name="sc">
            <option value="" selected>검색 조건</option>
            <option value= "qnaTitle">제목</option>
            <option value="userNickname">닉네임</option>
          </select>
          <input type="text" id="searchValue" name="sv" class="form-control" placeholder="제목,닉네임으로 검색하세요">
          <button class="btn btn-outline-secondary" type="submit" id="searchQna" name="searchQna">검색</button>
        </div>
        
        <div class="container my-5">
        <table class="table table-striped table-hover w-100">
          <thead>
            <tr>
              <th scope="col">게시글 ID</th>
              <th scope="col">카테고리</th>
              <th scope="col">제목</th>
              <th scope="col">좋아요 수</th>
              <th scope="col">답변 수</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
              <th scope="col">관리</th>
            </tr>
          </thead>
          <tbody>
          	<c:choose>
          		<%-- 조회된 게시글 목록이 없는 경우 --%>
          		<c:when test="${empty qnaList }">
          			<tr>
          				<td colspan="8">게시글이 존재하지 않습니다.</td>
          			</tr>
          		</c:when>
          		
          		<c:otherwise>
          			<c:forEach items="${qnaList }" var="qna">
          				<tr>
          					<td>${qna.qnaPostId }</td>
          					<td>${qna.qnaCategoryName }</td>
          					<td>
          						<a href="qnaView?no=${ qna.qnaPostId }&cp=${ pagination.currentPage }">${qna.qnaTitle }</a>
          					</td>
          					<td>${qna.qnaLike }</td>
          					<td>${qna.qnaReplyCount }</td>
          					<td>${qna.userNickname }</td>
          					<td>${qna.qnaDate }</td>
          					<td>
          						<a href="${contextPath }/admin/qnaUpdateForm?qnaPostId=${qna.qnaPostId}&cp=${empty param.cp? 1 : param.cp}" class="btn btn-primary btn-sm">수정</a>
                				<a href="${contextPath }/admin/qnaDelete?qnaPostId=${qna.qnaPostId}&cp=${empty param.cp? 1 : param.cp}" class="btn btn-danger btn-sm">삭제</a>
          					</td>
          				</tr>
          			</c:forEach>
          		</c:otherwise>
          	</c:choose>
          </tbody>
        </table>
        </div>
          <c:choose>
        <c:when test="${ !empty param.sc && !empty param.sv }">
          <c:set var="pageURL" value="admin/qnaList?sk=${param.sc }&sv=${param.sv }"/>
        </c:when>
        <c:otherwise>
          <c:set var="pageURL" value="admin/qnaList?"/>
        </c:otherwise>
      </c:choose>
        
    <%---------------------- Pagination start----------------------%>
	<%-- 페이징 처리 시 주소를 쉽게 작성할 수 있도록 필요한 변수를 미리 선언 --%>
	
	<c:set var="pageURL" value="qnaList?"/>
    <c:set var="prev" value="${ pageURL }cp=${ pagination.prevPage }"/>
    <c:set var="next" value="${ pageURL }cp=${ pagination.nextPage }"/>

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
              <li class="page-item"><a class="page-link" href="${ pageURL }cp=${ p }">${ p }</a></li>
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
