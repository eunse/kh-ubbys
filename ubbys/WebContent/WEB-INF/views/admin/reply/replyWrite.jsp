<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../header.jsp" />

    <div class="container">
      <h1 class="h3 mt-5">Reply 작성</h1>
      <form class="form-write" id="form-write" action="${ contextPath }/admin/replyInsert" method="POST">
      	<input type="hidden" id="reply" name="reply" value="${ reply.replyId }" />
      	<input type="hidden" id="cp" name="cp" value="${ param.cp }" />
      	<div class="row align-items-end">
          <div class="col-md-2">
            <div class="mb-3">
              <label class="form-label" for="qnaPostId">본문</label>
              <select class="form-select" id="qnaPostId" name="qnaPostId">
                <c:forEach items="${ qnaList }" var="qna">
                  	<option value="${ qna.qnaPostId }" ${ qna.qnaPostId eq reply.qnaPostId ? 'selected' : '' }>${ qna.qnaTitle }</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label" for="inputContent">댓글내용</label>
          <textarea class="form-control" id="replyContent" name="replyContent" style="height: 500px">${reply.replyContent }</textarea>
        </div>
        <button type="button" class="btn btn-outline-primary" onclick="location.href='${ contextPath }/admin/replyList?cp=${param.cp}';">이전목록</button>
        <button type="submit" class="btn btn-primary float-end">작성 완료</button>
      </form>
    </div>

<jsp:include page="../footer.jsp" />

<script>
	$(document).ready(function() {
	    $("#form-write, #form-update").on("submit", function() {
	        if($("#replyContent").val().trim().length==0){
	            alert("댓글내용을 입력해주세요.");
	            $("#replyContent").focus();
	            return false;
	        }
	        if($("#qnaPostId").val().trim().length==0){
	            alert("본문을 선택해주세요")
	            $("#qnaPostId").focus();
	            return false;
	        }
	        return true;
	    });
	
	});
</script>
