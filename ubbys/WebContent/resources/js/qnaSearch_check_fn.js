$(document).ready(function(){

    $("#searchForm").on("submit", function(){

        if(!$("#searchForm").find("option:selected").val()){
            alert("검색조건을 선택해주세요.");
            return false;
        }
        if($("#searchValue").val().trim().length==0){
            alert("검색어를 입력해주세요.");
            return false;
        }
        if($("#searchValue").val().trim().length<2){
            alert("두 글자 이상 입력해주세요.");
            return false;
        }
        return true;
    });


    $("#searchCategory").on("change", function(){
        var sc = "qnaCategoryId";
        var sv = $(this).val();
        categoryRequest(sc, sv);
    });

});

function categoryRequest(sc, sv){
    document.categoryReqForm.searchCateCond.value=sc;
    document.categoryReqForm.searchCateVal.value=sv;
    document.categoryReqForm.action="qnaList";
    document.categoryReqForm.submit();
};