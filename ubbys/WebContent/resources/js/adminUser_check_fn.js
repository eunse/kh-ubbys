$(document).ready(function(){

    $("#userSearchForm").on("submit", function(){
        var tc = "";

        if(!$("#userSearchForm").find("option:selected").val()){
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

    $("#sortKey").on("change", function(){
        var sk = $(this).val();
        var sv = "";
        if(sk=="sortYoung"){
            sv = "DESC";
        } else{
            sv = "ASC"
        }
        sortRequest(sk, sv);
    });
});

function sortRequest(sk, sv){
    document.sortReqForm.sortKey.value=sk;
    document.sortReqForm.sortVal.value=sv;
    document.sortReqForm.action="list";
    document.sortReqForm.submit();
}

function searchAlert(tc) {
    document.querySelector('.modal-title').textContent = "검색";
    document.querySelector('.modal-body').children[0].textContent = tc;
    modal.show();
}
