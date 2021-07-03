function fnRequest(address){
    document.requestForm.action="qna"+address;
    document.requestForm.submit();
}

/* function qnaLikeStatus(userID){
    console.log(userId);
    document.qnaLikeForm.action="qnaLikeStatus";
    document.qnaLikeForm.submit();
}


$("#qna-like-btn").on("click", function(){

    const qnaPostId = $("#qnaPostId").val();

     $.ajax({
        url : "qnaLike",
        data : {"qnaPostId" : qnaPostId},
        dataType : "json",
        type : "post",
    
        success : function(result){
    
            if(result>0){
                $("#qna-like").css("color","red");
                $("#qna-Like-count").text($("#qna-Like-count").text()+1);
    
            } else {
                $("#qna-like").css("color","");
                $("#qna-Like-count").val($("#qna-Like-count").val()-1);
            }
        },  
        error : function(e){
            console.log("ajax 통신 실패");
            console.log(e);
        }     
    });


}); */
