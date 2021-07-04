<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="common/header.jsp" />

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
      <jsp:include page="reply.jsp"></jsp:include> 
    
    
    
      <a href="qnaList?cp=${ param.cp }" class="btn btn-outline-primary">이전 목록</a>
      
      <c:if test="${ qna.userId == sessionScope.loginUser.userNo }">
        <button class="btn btn-primary float-end" id="qnqUpdateBtn" onclick="fnRequest('UpdateForm');">수정</button>
        <button class="btn btn-danger float-end me-2" id="qnqDeleteBtn">삭제</button>
      </c:if>
    </div>
<jsp:include page="common/footer.jsp" />

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


/*댓글 관련 스크립트  */

// 로그인한 회원의 회원 번호, 비로그인 시 "" (빈문자열) ""이 없으면 ;만 남아서 오류냄.
const loginUserId = "${loginUser.userNo}";
console.log("댓글유저No:"+loginUserId);
// 현재 게시글 번호
const qnaPostId = ${qna.qnaPostId};
console.log("게시판번호 :"+qnaPostId);
// 수정 전 댓글 요소를 저장할 변수 (댓글 수정 시 사용)
let beforeReplyRow; // 전역변수임

const rlist = "${rlist}";
console.log(rlist);


//-----------------------------------------------------------------------------------------
// 댓글 등록
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
    success : function(rlist){
      console.log(rlist);
      
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



// -----------------------------------------------------------------------------------------
// 일정 시간마다 댓글 목록 갱신
/*const replyInterval = setInterval(function(){
  selectReplyList();
}, 5000);*/


// -----------------------------------------------------------------------------------------
// 댓글 수정 폼

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
// 댓글 수정
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




