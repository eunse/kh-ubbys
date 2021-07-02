$(document).ready(function() {
    $("#form-write").on("submit", function() {
        if($("#inputTitle").val().trim().length==0){
            alert("제목을 입력해주세요.");
            $("#inputTitle").focus();
            return false;
        }
        if($("#inputContent").val().trim().length==0){
            alert("본문을 입력해주세요.")
            $("#inputContent").focus();
            return false;
        }
        return true;
    });

});

