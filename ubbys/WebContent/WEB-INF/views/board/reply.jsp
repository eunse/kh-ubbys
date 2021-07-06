<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="reply-area ">
<%-- 회원번호 : ${loginUser.userNo} <br>
목록 : ${rList } <br>
게시글번호 : ${qna.qnaPostId} <br>  --%>

<%-- 테스트 --%>
  <div class="replyList mt-5 pt-2"  id="replyListArea">
      <ul class="qna-reply-content list-group col-md-9">
        <c:forEach items="${rList}" var="reply">
          <li class="list-group-item">
            <div class="d-flex justify-content-between align-items-center" id="div1">
              <div class="ms-2 me-auto">
                <div class="fw-bold">
                  <img src="${contextPath}/${reply.userPic}" class="user-image rounded-circle me-2">${reply.userNickname}
                </div>
              </div>
              <span class="date me-2">작성일 : ${reply.replyDate}</span>
              <c:if test="${reply.userId == sessionScope.loginUser.userNo}">
              <ul class="reply-action list-inline me-2" id="replyBtnArea">
                <li class="list-inline-item">
                  <button class="btn btn-primary btn-sm ml-1" id="updateReply" onclick="showUpdateReply(${reply.replyId}, this)">수정</button>
                </li>
                <li class="list-inline-item">
                  <button class="btn btn-primary btn-sm ml-1" id="deleteReply" onclick="deleteReply(${reply.replyId})">삭제</button>
                </li>
              </ul>
              </c:if>
              <button class="btn btn-outline-secondary btn-sm">
                <i class="bi bi-heart">${reply.replyLike }</i> 
              </button>
            </div>
            <div class="ms-2" id="lastDiv">${reply.replyContent }</div>
          </li>
          <%-- 수정창 시작 --%>
          <li class="list-group-item">
            <div class="d-flex justify-content-between align-items-center">
              <div class="ms-2 me-auto">
                <div class="fw-bold">
                  <pre>수정할 내용</pre>
                  <%--<img src="#" class="user-image rounded-circle me-2">{사용자명} --%>
                </div>
              </div>
            </div>
            <div class="input-group ms-2 my-2">
              <textarea class="form-control" id="edit-reply" rows="5"></textarea>
              <button class="btn btn-outline-primary" onclick="updateReply(${reply.replyId}, this)">수정</button>
            </div>
          </li>
          <%-- 수정창 끝 --%>
        </c:forEach>
      </ul>
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


let beforeReplyRow; 


//-----------------------------------------------------------------------------------------
//댓글 등록
function addReply() {

	const replyContent = $("#replyContent").val();
    if(loginUserId == ""){
    	console.log("로그인 필요");
    }else{
     if(replyContent.trim() == ""){ 
       console.log("댓글 작성 후 클릭해 주세요.");
     }else{
    	 
       $.ajax({ 
         url : "${contextPath}/replyInsertReply", 
         type : "POST",
         data : {"userId" : loginUserId,
             "qnaPostId" : qnaPostId,
             "replyContent" : replyContent },
         success : function(result){
           if(result > 0){ 
        	 console.log("댓글 등록 성공");
             $("#replyContent").val(""); 
             
             selectReplyList(); 
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
     dataType : "JSON",  
     success : function(rList){
       console.log(rList);
       
            $("#replyListArea").html(""); 
            
            
            $.each(rList, function(index, item){
            	
              // console.log(item.userNickname); 
              var topDiv = $("<div>").addClass("replyList mt-5 pt-2").attr("id", "replyListArea");
          	  var topUl = $("<ul>").addClass("qna-reply-content list-group col-md-9");
    
              var li = $("<li>").addClass("list-group-item");
    
              var div1 = $("<div>").addClass("d-flex justify-content-between align-items-center").attr("id", "div1");
              var lastDiv = $("<div>").addClass("ms-2").html(item.replyContent).attr("id", "lastDiv");
              var div2 = $("<div>").addClass("ms-2 me-auto");
    
              var div3 = $("<div>").addClass("fw-bold");
              div2.append(div3);
    
              var rWriter = $("<img>").attr("src","${contextPath}"+"/"+item.userPic).addClass("user-image rounded-circle me-2"); 
              div3.append(rWriter).append(item.userNickname);
    
              var rDate = $("<span>").addClass("date me-2").text("작성일 : "+item.replyDate);
    
               if (item.userId == loginUserId) { 
    
                 var ul = $("<ul>").addClass("reply-action list-inline me-2").attr("id", "replyBtnArea");
      
                 var childLi1 = $("<li>").addClass("list-inline-item");
                 var showUpdate = $("<button>").addClass("btn btn-primary btn-sm ml-1").text("수정").attr("id", "updateReply").attr("onclick", "showUpdateReply()");
                 childLi1.append(showUpdate);
      
                 var childLi2 = $("<li>").addClass("list-inline-item");
                 var deleteReply = $("<button>").addClass("btn btn-primary btn-sm ml-1").text("삭제").attr("id", "deleteReply").attr("onclick", "deleteReply()");
                 childLi2.append(deleteReply);
      
                 ul.append(childLi1).append(childLi2);
               } 
    
              var button = $("<button>").addClass("btn btn-outline-secondary btn-sm");
              var i = $("<i>").addClass("bi bi-heart").text((item.replyLike));
              button.append(i);
              div1.append(div2).append(rDate).append(ul).append(button);
    
              
              
              var listButton = $("<button>").addClass("btn btn-outline-primary").text("목록갱신(테스트용)").attr("onclick", "selectReplyList()");
              
              li.append(div1).append(lastDiv);
              
            //수정창 시작-----------------------------------------------------------------------------------------
              var updateLi = $("<li>").addClass("list-group-item").attr("id", "updateLi");
              var uDivTop = $("<div>").addClass("d-flex justify-content-between align-items-center");

              var uDivch = $("<div>").addClass("ms-2 me-auto");
              uDivTop.append(uDivch);

              var uDivchch = $("<div>").addClass("fw-bold");
              uDivch.append(uDivchch); 
			
              var updateC = $("<pre>").text("수정할 내용");
              uDivchch.append(updateC);
              /* var uImg = $("<img>").attr("src","#").addClass("user-image rounded-circle me-2").text("닉네임 : ");
              uDivchch.append(uImg); */

              var uDivBottom = $("<div>").addClass("input-group ms-2 my-2");

              var uTextarea = $("<textarea>").addClass("form-control").attr("id", "edit-reply").attr("rows","5");
              var uButton = $("<button>").addClass("btn btn-outline-primary").text("수정").attr("onclick", "updateReply(" + item.replyId + ", this)");
              uDivBottom.append(uTextarea).append(uButton);
              
              //수정창 마무리
              updateLi.append(uDivTop).append(uDivBottom);
            //수정창 끝-------------------------------------------------------------------------------------------

              //댓글 + 수정창
              topUl.append(li).append(updateLi);
    
              // 마지막 마무리
              $("#replyListArea").append(topUl);
              //$("#replyListArea").append(topUl).append(listButton); // 목록 버튼
              
            });
       
     },
     
     error : function(){
       console.log("댓글 목록 조회 실패");
     }
     
    });
}
// ---------------------------
// 댓글 수정 기능
function updateReply(replyId, el){
	
	const replyContent = $(el).prev().val();
	
	$.ajax({
		url : "${contextPath}/replyUpdateReply",
		type : "POST",
		data : {"replyId" : replyId,
				"replyContent" : replyContent},
		success : function(result){
			if(result > 0 ){
				console.log("댓글 수정 성공");
				selectReplyList();
			}
		},
		error : function(){
			console.log("댓글 수정 실패");
		}
	});
}


// ---------------------------
// 댓글 삭제 기능
function deleteReply(replyId){

    $.ajax({
      url :"${contextPath}/replyDeleteReply",
      type : "POST",
      data : {"replyId" : replyId},
      success : function(result){
        if(result > 0){
          console.log("댓글 삭제 성공");
          selectReplyList();
        }
      },
      error : function(){
        console.log("댓글 삭제 실패");
      }
    });
}


</script>

