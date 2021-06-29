<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="common/header.jsp" />

    <div class="container">
      <h1 class="h3 my-5">apps</h1>
      <div class="row mb-4">
        <div class="col mb-sm-3">
          <span class="badge rounded-pill bg-secondary">${ qna.qnaCategoryName }</span>
          <h2 class="h4 my-1">${ qna.qnaTitle }</h2>
          <p>${ qna.userNickname }</p>
        </div>
        <div class="col-md-2 gap-3 mb-sm-3">
          <button type="button" class="btn btn-outline-secondary btn-lg w-100 float-end"><i class="bi bi-heart"></i> ${ qna.qnaLike }</button>
        </div>
      </div>
      <div class="qna-content col-md-9">
        <p>${ qna.qnaContent }</p>
      </div>
      <hr>
    
    
      <!-- 댓글이 include될 부분 -->
    
    
      <c:if test="${ qna.userId == sessionScope.loginUser.userNo }">
        <a href="#" class="btn btn-outline-primary">수정</a>
        <a href="#" class="btn btn-outline-danger">삭제</a>
      </c:if>
      <small class="float-end">작성일시 : ${ qna.qnaDate }</small>
    </div>
<jsp:include page="common/footer.jsp" />