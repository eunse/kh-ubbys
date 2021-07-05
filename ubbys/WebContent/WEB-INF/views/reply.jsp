<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="reply-area ">
 <%-- 회원번호 : ${loginUser.userNo} <br>
목록 : ${rList } <br>
게시글번호 : ${qna.qnaPostId} <br> --%>


<%-- 테스트 --%>
  <div class="replyList mt-5 pt-2">
  <ul class="qna-reply-content list-group col-md-9" id="replyListArea">
        <c:forEach items="${rList}" var="reply">
          <li class="list-group-item">
            <div class="d-flex justify-content-between align-items-center">
              <div class="ms-2 me-auto">
                <div class="fw-bold">
                  <img src="https://github.com/mdo.png"
                    class="user-image rounded-circle me-2">${reply.userNickname}
                </div>
              </div>
              <span class="date me-2">작성일 : ${reply.replyDate }</span>
              <c:if test="${reply.userId == sessionScope.loginUser.userNo}">
                <ul class="reply-action list-inline me-2">
                  <li class="list-inline-item">
                    <button class="btn btn-primary btn-sm ml-1" id="updateReply" onclick="showUpdateReply(${reply.replyId}, this)">수정</button>
                  </li>
                  <li class="list-inline-item">
                    <button class="btn btn-primary btn-sm ml-1" id="deleteReply" onclick="deleteReply(${reply.replyId})">삭제</button>
                  </li>
                </ul>
              </c:if>
              <button class="btn btn-outline-secondary btn-sm">
                <i class="bi bi-heart"></i> 
              </button>
            </div>
            <div class="ms-2">${reply.replyContent }</div>
          </li>
          <%-- 수정부분 --%>
          <li id="li" class="list-group-item">
          <div class="d-flex justify-content-between align-items-center">
            <div class="ms-2 me-auto">
              <div class="fw-bold"><img src="" class="user-image rounded-circle me-2">{사용자명}
              </div>
            </div>
          </div>
          <div class="input-group ms-2 my-2">
            <textarea class="form-control" id="edit-reply" rows="5">수정할 원래 댓글 내용</textarea>
            <button class="btn btn-outline-primary" onclick="updateReply(${reply.replyId})">수정</button>
          </div>
          </li>
          <%-- 수정부분 --%>
        </c:forEach>
      </ul>
      <button class="btn btn-outline-primary" onclick="selectReplyList()">목록갱신(테스트용)</button>
      <%-- 테스트용 수정부분 시작 --%>
      <style>
        #li{
            display:none; /* 화면에서 보이지 않음 */
        }
      </style>
      <ul>
      <button id="button" class="btn btn-primary btn-sm ml-1">수정</button>
      <li id="li" class="list-group-item">
          <div class="d-flex justify-content-between align-items-center">
            <div class="ms-2 me-auto">
              <div class="fw-bold"><img src="" class="user-image rounded-circle me-2">{사용자명}
              </div>
            </div>
          </div>
          <div class="input-group ms-2 my-2">
            <textarea class="form-control" id="edit-reply" rows="5">수정할 원래 댓글 내용</textarea>
            <button class="btn btn-outline-primary">수정</button>
          </div>
        </li>
      </ul>
      
      <script>
      $(function(){
        $('#button').click(function(){
          if($(this).next("#li").css("display") == "none"){
            $(this).siblings("#li.list-group-item").slideUp();
            $(this).next("#li").slideDown();
          }else{
            $(this).next("#li").slideUp();
          }
        });
      });

    </script>
    <%-- 테스트용 수정부분 끝 --%>
    
    
  
  </div>
  <hr>
    <div class="replyWrite col-md-9">
      <h4 class="h5">새로운 댓글 작성</h4>
      <div class="input-group my-2" id="replyContentArea">
        <textarea class="form-control" id="replyContent" rows="5"></textarea>
        <button class="btn btn-outline-primary" id="addReply" onclick="addReply();">작성</button>
      </div>
    </div>
    
   <button class="btn btn-outline-primary" onclick="selectReplyList()">목록갱신(테스트용)</button>
<%-- 테스트 --%>


</div>


<script>

//로그인한 회원의 회원 번호, 비로그인 시 "" (빈문자열) ""이 없으면 ;만 남아서 오류냄.
/* const loginUserId = "${loginUser.userNo}";
console.log("댓글유저No:"+loginUserId); */
//현재 게시글 번호
/*
const qnaPostId = ${qna.qnaPostId};
console.log("게시판번호 :"+qnaPostId); */
//수정 전 댓글 요소를 저장할 변수 (댓글 수정 시 사용)
let beforeReplyRow; // 전역변수임


//-----------------------------------------------------------------------------------------
//댓글 등록
function addReply() {
// 작성된 댓글 내용 얻어오기
const replyContent = $("#replyContent").val();
// 로그인이 되어있지 않은 경우
if(loginUserId == ""){
	console.log("로그인 필요");
}else{
 if(replyContent.trim() == ""){ // 작성된 댓글이 없을 경우
   console.log("댓글 작성 후 클릭해 주세요.");
 }else{
   // 욕설 필터 위치
   // 로그인 O, 댓글 작성 O
   $.ajax({ 
     url : "${contextPath}/replyInsertReply", // ajax 필수 속성(반드시 작성) // 넘길 파라미터(키:밸류)형태로 넘김
     type : "POST",
     data : {"userId" : loginUserId,
         "qnaPostId" : qnaPostId,
         "replyContent" : replyContent },
     success : function(result){
       if(result > 0){ // 댓글 삽입 성공
    	 console.log("댓글 등록 성공");
         $("#replyContent").val(""); // 댓글 작성 내용 삭제
         
         selectReplyList(); // 비동기로 댓글 목록 갱신
       }
     },
     error : function(){
       console.log("댓글 삽입 실패");
     }
   });
   
 }
}
} 



//-----------------------------------------------------------------------------------------
//해당 게시글 댓글 목록 조회
function selectReplyList(){
$.ajax({ 
 url : "${contextPath}/replyList",
 data : {"qnaPostId" : qnaPostId},
 type : "POST",
 dataType : "JSON",  // 응답되는 데이터의 형식이 JSON임을 알려줌 -> 자바스크립트 객체로 변환됨
 success : function(rList){
   console.log(rList);
   
        $("#replyListArea").html(""); // 기존 정보 초기화 
        //여기까지 성공
        
        $.each(rList, function(index, item){
     // $.each() : jQuery의 반복문
     // rlist : ajax 결과로 받은 댓글이 담겨있는 객체 배열
     // index : 순차 접근시 현재 인덱스
     // item : 순차 접근시 현재 접근한 배열 요소(댓글 객체 하나)
       console.log(item.userNickname);
      	  var topUl = $("<ul>").addClass("qna-reply-content list-group col-md-9");

          var li = $("<li>").addClass("list-group-item");

          var div1 = $("<div>").addClass("d-flex justify-content-between align-items-center");
          var lastDiv = $("<div>").addClass("ms-2").text(item.replyContent);
          var div2 = $("<div>").addClass("ms-2 me-auto");

          var div3 = $("<div>").addClass("fw-bold").text((item.userNickname));
          div2.append(div3);

          var rWriter = $("<img>").addClass("user-image rounded-circle me-2").attr("src","#"); 
          div3.append(rWriter);

          var rDate = $("<span>").addClass("date me-2").text("작성일 : "+item.replyDate);

           if (item.userId == loginUserId) { 

            var ul = $("<ul>").addClass("reply-action list-inline me-2");

            var childLi1 = $("<li>").addClass("list-inline-item");
            var showUpdate = $("<button>").addClass("btn btn-primary btn-sm ml-1").text("수정").attr("onclick", "showUpdateReply()");
            childLi1.append(showUpdate);

            var childLi2 = $("<li>").addClass("list-inline-item");
            var deleteReply = $("<button>").addClass("btn btn-primary btn-sm ml-1").text("삭제").attr("onclick", "deleteReply()");
            childLi2.append(deleteReply);

            ul.append(childLi1).append(childLi2);
           } 

          var button = $("<button>").addClass("btn btn-outline-secondary btn-sm");
          var i = $("<i>").addClass("bi bi-heart").text((item.replyLike));
          button.append(i);
          div1.append(div2).append(rDate).append(ul).append(button);

          
          
          var listButton = $("<button>").addClass("btn btn-outline-primary").text("목록갱신(테스트용)").attr("onclick", "selectReplyList()");
          
          li.append(div1).append(lastDiv);
          
          topUl.append(li);

          $("#replyListArea").append(topUl);
          //$("#replyListArea").append(topUl).append(listButton); // 목록 버튼
          
        });
   
 },
 
 error : function(){
   console.log("댓글 목록 조회 실패");
 }
 
});
}




</script>

