<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../header.jsp" />

    <div class="container">
      <h1 class="h3 mt-5">QNA 글작성</h1>
      <form class="form-write" id="form-write" action="${ contextPath }/admin/qnaInsert" method="POST">
        <div class="row align-items-end">
          <div class="col-md-2">
            <div class="mb-3">
              <label class="form-label" for="selectCategory">카테고리</label>
              <select class="form-select" id="selectCategory" name="qnaCategoryId">
                <c:forEach items="${ qnaCategory }" var="qc">
                  <option value="${ qc.qnaCategoryId }">${ qc.qnaCategoryName }</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-md-10">
            <div class="mb-3">
              <label class="form-label" for="inputTitle">제목</label>
              <input type="text" class="form-control" id="inputTitle" name="inputTitle" placeholder="제목을 입력하세요.">
            </div>
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label" for="inputContent">본문</label>
          <textarea class="form-control" id="inputContent" name="inputContent" style="height: 500px"></textarea>
        </div>
        <button type="button" class="btn btn-outline-primary" onclick="location.href='${ contextPath }/admin/qnaList?cp=${param.cp}';">이전목록</button>
        <button type="submit" class="btn btn-primary float-end">작성 완료</button>
      </form>
    </div>

<jsp:include page="../footer.jsp" />

<script src="${contextPath}/resources/js/qnaWrite_check.js"></script>
