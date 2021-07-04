<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../common/header.jsp" />

    <div class="container">
      <h1 class="h3 my-5">QNA</h1>
      <div class="row mb-4">
        <div class="col mb-sm-3">
          <span class="badge rounded-pill bg-secondary">${ qna.qnaCategoryName }</span>
          <h2 class="h4 my-1">${ qna.qnaTitle }</h2>
          <p>${ qna.userNickname }</p>
        </div>
        <div class="col-md-2 gap-3 mb-sm-3">
          <button type="button" class="btn btn-outline-danger btn-lg w-100 float-end" id="qna-like-btn">
          	<i class="bi bi-heart me-2" id="qna-like"></i><span id="qna-like-count">${ qna.qnaLike }</span>
          </button>
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
      
      <c:if test="${ qna.userId == sessionScope.loginUser.userNo }">
        <button class="btn btn-primary float-end" id="qnqUpdateBtn" onclick="fnRequest('UpdateForm');">수정</button>
        <button class="btn btn-danger float-end me-2" id="qnqDeleteBtn" onclick="fnRequest('DeleteAlert');">삭제</button>
      </c:if>
    </div>
<jsp:include page="../common/footer.jsp" />

<form action="#" method="POST" name="requestForm">
  <input type="hidden" name="qnaPostId" value="${ qna.qnaPostId }" id="qnaPostId">
  <input type="hidden" name="cp" value="${ param.cp }">
</form>

<script src="${contextPath}/resources/js/qnaView_fn.js"></script>

<script>

const loginUserId = ${loginUser.userNo};
const qnaPostId = ${ qna.qnaPostId };
let qnaLike = ${qna.qnaLike};

qnaLikeCheck();

function qnaLikeCheck(){
	
	let flag = false;
	
	$.ajax({
		url : "qnaLikeCheck",
		data : {"qnaPostId" : qnaPostId},
		type : "POST",
		dataType : "JSON",
		
		success : function(uList){
			
			$.each(uList, function(index, item){
				
				if(item.userNo == loginUserId){
					flag = true;
				}
			});
			
			if(flag){
				$("#qna-like-btn").html("");
				var i = $("<i>").addClass("bi bi-heart-fill me-2").attr("id", "qna-like");
				var span = $("<span>").attr("id", "qna-like-count").text(qnaLike);
				
				$("#qna-like-btn").append(i).append(span);
			}
		},
		error : function(e){
			console.log(e);
		}
	});
}

$("#qna-like-btn").on("click", function(){
	
	$.ajax({
		url : "qnaLike",
		data : {"qnaPostId" : qnaPostId},
		type : "POST",
		
		success : function(result){
			
			if(result > 0){
				$("#qna-like-btn").html("");
				var i = $("<i>").addClass("bi bi-heart-fill me-2").attr("id", "qna-like");
				var span = $("<span>").attr("id", "qna-like-count");
				
				$("#qna-like-btn").append(i).append(span);
			} else{
				$("#qna-like-btn").html("");
				var i = $("<i>").addClass("bi bi-heart me-2").attr("id", "qna-like");
				var span = $("<span>").attr("id", "qna-like-count");
				
				$("#qna-like-btn").append(i).append(span);
			}
			qnaLikeCount();
		},
		error : function(e){
			console.log(e);
		}
	});
});

function qnaLikeCount(){
	
	$.ajax({
		url : "qnaLikeCount",
		data : {"qnaPostId" : qnaPostId},
		type : "POST",
		
		success : function(result){
			$("#qna-like-count").text(result);
		},
		error : function(e){
			console.log(e);
		}
	});
}

</script>