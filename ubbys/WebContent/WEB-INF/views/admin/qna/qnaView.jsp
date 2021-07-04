<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../header.jsp" />

    <div class="container">
      <h1 class="h3 my-5">QNA</h1>
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
      <div>
        <small class="float-end">작성일시 : ${ qna.qnaDate }</small><br>
      </div>



      <!-- 댓글이 include될 부분 -->




      <a href="qnaList?cp=${ param.cp }" class="btn btn-outline-primary">이전 목록</a>

        <button class="btn btn-primary float-end" id="qnqUpdateBtn" onclick="fnRequest('UpdateForm');">수정</button>
        <button class="btn btn-danger float-end me-2" id="qnqDeleteBtn" onclick="fnRequest('Delete');">삭제</button>
    </div>
<jsp:include page="../footer.jsp" />

<form action="#" method="POST" name="requestForm">
  <input type="hidden" name="qnaPostId" value="${ qna.qnaPostId }">
  <input type="hidden" name="cp" value="${ param.cp }">
</form>

<script src="${contextPath}/resources/js/qnaView_fn.js"></script>