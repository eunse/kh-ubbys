$(document).ready(function(){

    $("#sortCondition").on("change", function(){
        var sc = $(this).val();
        var sv = "DESC";
        sortRequest(sc, sv);
    });

    $("#searchCategory").on("change", function(){
        var sc = "categoryId";
        var sv = $(this).val();
        categoryRequest(sc, sv);
    });

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
});

function sortRequest(sc, sv){
    document.sortReqForm.sortCond.value=sc;
    document.sortReqForm.sortVal.value=sv;
    document.sortReqForm.action="appsList";
    document.sortReqForm.submit();
}
function categoryRequest(sc, sv){
    document.categoryReqForm.searchCateCond.value=sc;
    document.categoryReqForm.searchCateVal.value=sv;
    document.categoryReqForm.action="appsList";
    document.categoryReqForm.submit();
};

function searchAlert(tc) {
    document.querySelector('.modal-title').textContent = "검색";
    document.querySelector('.modal-body').children[0].textContent = tc;
    modal.show();
}
