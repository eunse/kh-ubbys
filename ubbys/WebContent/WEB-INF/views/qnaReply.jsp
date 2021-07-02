<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="application"
  value="${pageContext.servletContext.contextPath}" />

<jsp:include page="common/header.jsp" />

<div class="container">


  <!-- 댓글 시작 -->
  <hr>
  <c:choose>
    <c:when test="${empty qnaReplyList }">
      <h2 class="h2 mb-3">댓글이 존재하지 않습니다.</h2>
    </c:when>
    <c:otherwise>
      <h4 class="h5">총 3개의 댓글</h4>
      <ul class="qna-reply-content list-group col-md-9">
        <c:forEach items="${qnaReplyList }" var="qnaReply">
          <li class="list-group-item">
            <div
              class="d-flex justify-content-between align-items-center">
              <div class="ms-2 me-auto">
                <div class="fw-bold">
                  <img src="https://github.com/mdo.png"
                    class="user-image rounded-circle me-2">${qnaReply.userNickname}
                </div>
              </div>
              <span class="date me-2">${qnaReply.replyDate }</span>
                <c:if test="${qnaReply.userId == sessionScope.loginUser.userNo }">
                  <ul class="reply-action list-inline me-2">
                    <li class="list-inline-item"><a href="#">수정</a></li>
                    <li class="list-inline-item"><a href="#">삭제</a></li>
                  </ul>
                </c:if>
              <button type="button"
                class="btn btn-outline-secondary btn-sm">
                <i class="bi bi-heart"></i> ${qnaReply.replyLike }
              </button>
            </div>
            <div class="ms-2">${qnaReply.replyContent }</div>
          </li>
        </c:forEach>
    </c:otherwise>
  </c:choose>
  <li class="list-group-item">
    <div class="d-flex justify-content-between align-items-center">
      <div class="ms-2 me-auto">
        <div class="fw-bold">
          <img src="https://github.com/mdo.png"
            class="user-image rounded-circle me-2">{사용자명}
        </div>
      </div>
      <span class="date me-2">0000년 00월 00일 00:00</span>
      <ul class="reply-action list-inline me-2">
        <li class="list-inline-item"><a href="#">수정</a></li>
        <li class="list-inline-item"><a href="#">삭제</a></li>
      </ul>
      <button type="button" class="btn btn-outline-secondary btn-sm">
        <i class="bi bi-heart"></i> 123
      </button>
    </div>
    <div class="ms-2">Cras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odioCras justo odioCras justo
      odioCras justo odioCras justo odio</div>
  </li>
  <li class="list-group-item">
    <div class="d-flex justify-content-between align-items-center">
      <div class="ms-2 me-auto">
        <div class="fw-bold">
          <img src="https://github.com/mdo.png"
            class="user-image rounded-circle me-2">{사용자명}
        </div>
      </div>
    </div>
    <div class="input-group ms-2 my-2">
      <textarea class="form-control" id="edit-reply" rows="5">수정할 원래 댓글 내용</textarea>
      <button class="btn btn-outline-primary">수정</button>
    </div>
  </li>
  </ul>
  <hr>
  <div class="reply-write col-md-9">
    <h4 class="h5">새로운 댓글 작성</h4>
    <div class="input-group my-2">
      <textarea class="form-control" id="edit-reply" rows="5"></textarea>
      <button class="btn btn-outline-primary">작성</button>
    </div>
  </div>
  <hr>
  <a href="#" class="btn btn-outline-primary">수정</a> <a href="#"
    class="btn btn-outline-danger">삭제</a> <small class="float-end">작성일시:
    0000년 00월 00일 00시 00분</small>
</div>

<jsp:include page="common/footer.jsp" />
