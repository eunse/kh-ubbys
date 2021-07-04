<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../header.jsp" />

    <div class="container">
      <h1 class="h3 mt-5">QNA 글수정</h1>
      <form class="form-update" id="form-update" action="${ contextPath }/admin/qnaUpdate?cp=${ param.cp }" method="POST">
        <div class="row align-items-end">
          <div class="col-md-2">
            <div class="mb-3">
              <label class="form-label" for="selectCategory">카테고리</label>
              <select class="form-select" id="selectCategory" name="qnaCategoryId">
                <c:forEach items="${ qnaCategory }" var="qc">
                  <option value="${ qc.qnaCategoryId }" ${ qc.qnaCategoryName eq qna.qnaCategoryName ? 'selected' : '' }>${ qc.qnaCategoryName }</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-md-10">
            <div class="mb-3">
              <label class="form-label" for="inputTitle">제목</label>
              <input type="text" class="form-control" id="inputTitle" name="inputTitle" value="${ qna.qnaTitle }">
            </div>
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label" for="inputContent">본문</label>
          <textarea class="form-control" id="inputContent" name="inputContent" style="height: 500px">${ qna.qnaContent }</textarea>
        </div>
        <button type="button" class="btn btn-outline-primary" onclick="history.back();">취소</button>
        <button type="submit" class="btn btn-primary float-end">수정 완료</button>
        
        <input type="hidden" name="qnaPostId" value="${ qna.qnaPostId }">
        <input type="hidden" name="cp" value="${ param.cp }">
      </form>
    </div>

<jsp:include page="../footer.jsp" />
<script>
  const qnaCategoryName = "${ qna.qnaCategoryName }";
  $("#selectCategory > option").each(function(index, item){
      
      if($(item).text()==qnaCategoryName){
          $(item).prop("selected", true);
      }
  });
</script>

<script src="${contextPath}/resources/js/qnaUpdate_fn.js"></script>