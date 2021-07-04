<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div id="reply-area ">
회원번호 : ${loginUser.userNo} <br>
목록 : ${rList } <br>
게시글번호 : ${qna.qnaPostId} <br>

<hr>
  <!-- 댓글 작성 부분 -->
  <%--  <div class="replyWrite">
    <table align="center">
      <tr>
        <td id="replyContentArea">
          <textArea rows="3" id="replyContent"></textArea>
        </td>
        <td id="replyBtnArea">
          <button class="btn btn-primary" id="addReply" onclick="addReply();">댓글<br>등록</button>
        </td>
      </tr>
    </table>
  </div>
  
시작
  <!-- 댓글 출력 부분 -->
  <div class="replyList mt-5 pt-2">
    <ul id="replyListArea">
      <c:forEach items="${rList}" var="reply">
        <li class="reply-row">
          <div>
            <p class="rWriter">${reply.userNickname}</p>
            <p class="rDate">
                            작성일 : ${reply.replyDate }
            </p>
          </div>

          <p class="rContent">${reply.replyContent }</p> 
          
          <c:if test="${reply.userId == loginUser.userNo}">
          <div class="replyBtnArea">
            <button class="btn btn-primary btn-sm ml-1" id="updateReply" onclick="showUpdateReply(${reply.replyId}, this)">수정</button>
            <%-- this수정버튼 의미 
            <button class="btn btn-primary btn-sm ml-1" id="deleteReply" onclick="deleteReply(${reply.replyId})">삭제</button>
            <%-- 어떤 댓글인지 알아야 수정 가능하므로
          </div>
          </c:if>
        </li>
      </c:forEach>
    </ul>
  </div>
끝
--%>
<%-- 테스트 --%>
  <div class="replyList mt-5 pt-2">
  <ul class="qna-reply-content list-group col-md-9" id="replyListArea">
        <c:forEach items="${rList}" var="reply">
          <li class="rlist-group-item">
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
                  <li class="rlist-inline-item">
                    <button class="btn btn-primary btn-sm ml-1" id="deleteReply" onclick="deleteReply(${reply.replyId})">삭제</button>
                  </li>
                </ul>
              </c:if>
              <button type="button" class="btn btn-outline-secondary btn-sm">
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
            <button class="btn btn-outline-primary">수정</button>
          </div>
          </li>
          <%-- 수정부분 --%>
        </c:forEach>
      </ul>
      
      <%-- 수정참고 시작 --%>
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
          if($(this).parent("#li").css("display") == "none"){
            $(this).siblings("#li.list-group-item").slideUp();
            $(this).parent("#li").slideDown();
          }else{
            $(this).parent("#li").slideUp();
          }
        });
      });

    </script>
    <%-- 수정참고 끝 --%>
    
    <%-- 수정부분 시작 --%>
    <style>
    #div {
        width:300px;
        height:30px;
        background:yellowgreen;
        color:orangered;
        border-radius:10px;
        text-align:center;
        line-height: 30px;
        border:1px solid green;
        cursor:pointer;
    }
    #p.contents{
        border:1px solid lightgray;
        width:300px;
        height:200px;
        display:none; /* 화면에서 보이지 않음 */
    }
    </style>
    
    <div id="div">첫 번째 메뉴</div>
    <p id="p" class="contents">1</p>
    <script>
      $(function(){
        $('#div').click(function(){
          if($(this).next("#p").css("display") == "none"){
            $(this).siblings("#p.contents").slideUp();
            $(this).next().slideDown();
          }else{
            $(this).next().slideUp();
          }
        });
      });

  	</script>
  <%-- 수정부분 끝 --%>
  
  </div>
  <hr>
    <div class="replyWrite col-md-9">
      <h4 class="h5">새로운 댓글 작성</h4>
      <div class="input-group my-2" id="replyContentArea">
        <textarea class="form-control" id="replyContent" rows="5"></textarea>
        <button class="btn btn-outline-primary" id="addReply" onclick="addReply();">작성</button>
      </div>
    </div>
    
   
<%-- 테스트 --%>


</div>


<script>

//로그인한 회원의 회원 번호, 비로그인 시 "" (빈문자열) ""이 없으면 ;만 남아서 오류냄.
const loginUserId = "${loginUser.userNo}";
console.log("댓글유저No:"+loginUserId);
//현재 게시글 번호
const qnaPostId = ${qna.qnaPostId};
console.log("게시판번호 :"+qnaPostId);
//수정 전 댓글 요소를 저장할 변수 (댓글 수정 시 사용)
let beforeReplyRow; // 전역변수임




//-----------------------------------------------------------------------------------------
//댓글 등록
function addReply() {
// 작성된 댓글 내용 얻어오기
const replyContent = $("#replyContent").val();
// 로그인이 되어있지 않은 경우
if(loginUserId == ""){
 swal("로그인 후 이용해 주세요.");
}else{
 if(replyContent.trim() == ""){ // 작성된 댓글이 없을 경우
   swal("댓글 작성 후 클릭해 주세요.")
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
         swal({"icon" : "success" , "title" : "댓글 등록 성공"});
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
        // 왜? 새로 읽어온 댓글 목록으로 다시 만들어서 출력하려고 기존 내용(댓글)을 전부 초기화한다
        
        $.each(rlist, function(index, item){
     // $.each() : jQuery의 반복문
     // rlist : ajax 결과로 받은 댓글이 담겨있는 객체 배열
     // index : 순차 접근시 현재 인덱스
     // item : 순차 접근시 현재 접근한 배열 요소(댓글 객체 하나)
     
          
           var li = $("<li>").addClass("reply-row");
        
           // 작성자, 작성일, 수정일 영역 
           var div = $("<div>");
           var rWriter = $("<p>").addClass("rWriter").text(item.userNickname);
           var rDate = $("<p>").addClass("rDate").text("작성일 : " + item.replyDate);
           div.append(rWriter).append(rDate)
           //append는 자식으로 추가한다는 의미
           
           
           // 댓글 내용
           var rContent = $("<p>").addClass("rContent").html(item.replyContent);
           
           
           // 대댓글, 수정, 삭제 버튼 영역
           var replyBtnArea = $("<div>").addClass("replyBtnArea");
           
           // 현재 댓글의 작성자와 로그인한 멤버의 아이디가 같을 때 버튼 추가
           if(item.userId == loginUserId){
              
              // ** 추가되는 댓글에 onclick 이벤트를 부여하여 버튼 클릭 시 수정, 삭제를 수행할 수 있는 함수를 이벤트 핸들러로 추가함. 
              var showUpdate = $("<button>").addClass("btn btn-primary btn-sm ml-1").text("수정").attr("onclick", "showUpdateReply("+item.replyId+", this)");
              var deleteReply = $("<button>").addClass("btn btn-primary btn-sm ml-1").text("삭제").attr("onclick", "deleteReply("+item.replyId+")");
              
              replyBtnArea.append(showUpdate).append(deleteReply);
           }
           
           
           // 댓글 요소 하나로 합치기
           li.append(div).append(rContent).append(replyBtnArea);
           //li 태그 안에 들어가는 자식태그 만드는 것
           
           // 합쳐진 댓글을 화면에 배치
           $("#replyListArea").append(li);
           // 후아..
        });
   
 },
 
 error : function(){
   console.log("댓글 목록 조회 실패");
 }
 
});
}



//-----------------------------------------------------------------------------------------
//일정 시간마다 댓글 목록 갱신
/*const replyInterval = setInterval(function(){
selectReplyList();
}, 5000);*/


//-----------------------------------------------------------------------------------------
//댓글 수정 폼

function showUpdateReply(replyId, el){
// el : 수정 버튼 클릭 이벤트가 발생한 요소

// 이미 열려있는 댓글 수정 창이 있을 경우 닫아주기
if($(".replyUpdateContent").length > 0){
 $(".replyUpdateContent").eq(0).parent().html(beforeReplyRow);
}
 

// 댓글 수정화면 출력 전 요소를 저장해둠.
beforeReplyRow = $(el).parent().parent().html();



// 작성되어있던 내용(수정 전 댓글 내용) 
var beforeContent = $(el).parent().prev().html();

// 이전 댓글 내용의 크로스사이트 스크립트 처리 해제, 개행문자 변경
// -> 자바스크립트에는 replaceAll() 메소드가 없으므로 정규 표현식을 이용하여 변경
beforeContent = beforeContent.replace(/&amp;/g, "&");   
beforeContent = beforeContent.replace(/&lt;/g, "<");   
beforeContent = beforeContent.replace(/&gt;/g, ">");   
beforeContent = beforeContent.replace(/&quot;/g, "\"");   

beforeContent = beforeContent.replace(/<br>/g, "\n");   


// 기존 댓글 영역을 삭제하고 textarea를 추가 
$(el).parent().prev().remove();
var textarea = $("<textarea>").addClass("replyUpdateContent").attr("rows", "3").val(beforeContent);
$(el).parent().before(textarea);


// 수정 버튼
var updateReply = $("<button>").addClass("btn btn-primary btn-sm ml-1 mb-4").text("댓글 수정").attr("onclick", "updateReply(" + replyId + ", this)");

// 취소 버튼
var cancelBtn = $("<button>").addClass("btn btn-primary btn-sm ml-1 mb-4").text("취소").attr("onclick", "updateCancel(this)");

var replyBtnArea = $(el).parent();

$(replyBtnArea).empty(); 
$(replyBtnArea).append(updateReply); 
$(replyBtnArea).append(cancelBtn); 
}

//-----------------------------------------------------------------------------------------
//댓글 수정 취소 시 원래대로 돌아가기
function updateCancel(el){
$(el).parent().parent().html( beforeReplyRow );
}


//-----------------------------------------------------------------------------------------
//댓글 수정
function updateReply(replyId, el){ // el은 여기서 댓글 수정 버튼

// 수정된 댓글 내용
const replyContent = $(el).parent().prev().val();

$.ajax({
 url : "${contextPath}/replyUpdateReply",
 type : "POST",
 data : {"replyId" : replyId,
     "replyContent" : replyContent },
 success : function(result){
   if(result > 0 ){
     swal({"icon" : "success" , "title" : "댓글 수정 성공"});
     selectReplyList();
   }
 },
 error : function(){
   console.log("댓글 수정 실패");
 }
   
});
}


//-----------------------------------------------------------------------------------------
//댓글 삭제
function deleteReply(replyId){
if(confirm("정말로 삭제하시겠습니까?")){
 var url = "${contextPath}/replyDeleteReply";
 
 $.ajax({
   url : url,
   data : {"replyId" : replyId},
   success : function(result){
     if(result > 0){
       selectReplyList(qnaPostId);
       
       swal({"icon" : "success" , "title" : "댓글 삭제 성공"});
     }
     
   }, error : function(){
     console.log("ajax 통신 실패");
   }
 });
}

}



</script>

