<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../header.jsp" />

    <div class="container">
      <h1 class="h3 my-5">댓글</h1>
      <div class="row mb-4">
        <div class="col mb-sm-3">
          <span class="badge rounded-pill bg-secondary">${ qna.qnaCategoryName }</span>
          <span class="badge rounded-pill bg-primary">${ qna.qnaTitle }</span>
          <h2 class="h4 my-1">${ reply.userNickname }님의 댓글</h2>
        </div>
        <div class="col-md-2 gap-3 mb-sm-3">
          <button type="button" class="btn btn-outline-secondary btn-lg w-100 float-end"><i class="bi bi-heart"></i> ${ reply.replyLike }</button>
        </div>
      </div>
      <div class="qna-content col-md-9">
        <p>${ reply.replyContent }</p>
      </div>
      <hr>
      <div>
        <small class="float-end">작성일시 : ${ reply.replyDate }</small><br>
      </div>

      <a href="replyList?cp=${ param.cp }" class="btn btn-outline-primary">이전 목록</a>
        <button class="btn btn-primary float-end" id="qnqUpdateBtn" onclick="fnRequest('Write');">수정</button>
        <button class="btn btn-danger float-end me-2" id="qnqDeleteBtn" onclick="fnRequest('Delete');">삭제</button>
    </div>
<jsp:include page="../footer.jsp" />

<form action="#" method="POST" name="requestForm">
  <input type="hidden" name="replyId" value="${ reply.replyId }">
  <input type="hidden" name="cp" value="${ param.cp }">
</form>

<script>
function fnRequest(address){
    document.requestForm.action="reply"+address;
    document.requestForm.submit();
}

</script>