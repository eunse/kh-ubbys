$(document).ready(function(){

    $("#searchForm").on("submit", function(){
        var tc = "";

        if(!$("#searchForm").find("option:selected").val()){
            tc = "검색 조건을 선택해주세요.";
            searchAlert(tc);
            return false;
        }

        if($("#searchValue").val().trim().length==0){
            tc = "검색어를 입력해주세요.";
            searchAlert(tc);
            return false;
        }
        if($("#searchValue").val().trim().length<2){
            tc = "검색어는 두 글자 이상 입력해주세요.";
            searchAlert(tc);
            return false;
        }
        return true;
    });

    $("#searchCategory").on("change", function(){
        var sc = "qnaCategoryId";
        var sv = $(this).val();
        categoryRequest(sc, sv);
    });

    $("#sortCondition").on("change", function(){
        var sc = $(this).val();
        var sv = "DESC";
        sortRequest(sc, sv);
    });
});

function categoryRequest(sc, sv){
    document.categoryReqForm.searchCateCond.value=sc;
    document.categoryReqForm.searchCateVal.value=sv;
    document.categoryReqForm.action="qnaList";
    document.categoryReqForm.submit();
};

function sortRequest(sc, sv){
    document.sortReqForm.sortCond.value=sc;
    document.sortReqForm.sortVal.value=sv;
    document.sortReqForm.action="qnaList";
    document.sortReqForm.submit();
}

function searchAlert(tc) {
    document.querySelector('.modal-title').textContent = "검색";
    document.querySelector('.modal-body').children[0].textContent = tc;
    modal.show();
}
