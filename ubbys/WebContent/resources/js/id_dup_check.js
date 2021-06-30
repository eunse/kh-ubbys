// 회원가입 유효성 검사
// 각 유효성 검사 결과를 저장할 객체
const checkObj = {
    "inputEmail": false,
    "inputPw": false,
    "name": false
};

$("#inputEmail").on("input", function () {
    // 이메일 유효성 검사
    // 조건 : 아이디 4글자 이상, 이메일 형식  ->   /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/;
    const regExp = /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/;

    // 입력된 이메일(아이디) 변수 
    const email = $("#inputEmail").val().trim();

    // 입력된 이메일이 유효값인 경우 
    if (regExp.test(email)) {

        $.ajax({
            url: "signup/idDupCheck", // 요청 주소(필수로 작성!)
            data: { "inputEmail": email }, // 전달하려는 값 (파라미터)
            type: "post", // 데이터 전달 방식 

            success: function (result) {
                // 매개변수 result : 서버에서 비동기로 전달 받은 응답 데이터

                console.log(result);

                if (result > 0) { // id가 중복되는 경우 

                    $("#checkEmail").text("이미 사용중인 아이디 입니다.").css("color", "red");
                    checkObj.inputEmail = false;

                } else { // id가 중복되지 않는 경우

                    $("#checkEmail").text("사용가능한 아이디 입니다.").css("color", "green");
                    checkObj.inputEmail = true;

                }

            }, // 비동기 통신 성공 시 동작
            error: function (e) {

                console.log("ajax 통신 실패"); 
                console.log(e);

            } // 비동기 통신 실패 시 동작
            // 매개변수 e : 예외 발생 시 Exception 객체를 전달 받을 변수

        });

    } else {
        $("#checkEmail").text("올바른 이메일 형식을 입력하세요. 이메일은 @가 포함되어있어야 합니다.").css("color", "red");
        checkObj.inputEmail = false;
    }

});

// // 비밀번호 유효성 검사
// // 조건 : 영어, 숫자, 10글자 이상
$("#inputPw").on("input", function () {
    const regExp = /^[a-zA-Z0-9]{10,}$/;

    const pwd1 = $(this).val().trim();

    if (regExp.test(pwd1)) {
       $("#checkPwd1").text("유효한 비밀번호 입니다.").css("color", "green");
        checkObj.pwd1 = true;
    } else {
        $("#checkPwd1").text("비밀번호는 영어, 숫자, 특수문자 포함 6~20자리로 입력하세요.").css("color", "red");
        checkObj.pwd1 = false;
    }
});

// 비밀번호, 비밀번호 확인 일치 여부 판단
$("#inputPw, #inputPwConfirm").on("input", function () {

    const pwd1 = $("#inputPw").val();

    const pwd2 = $("#inputPwConfirm").val();

    if (pwd1.trim() == "" && pwd2.trim() == "") { // 둘다 비어있을 때
        $("#checkPwd2").html("&nbsp;");
        $("#checkPwd1").html("&nbsp;");
        checkObj.pwd2 = false;
    } else if (pwd1 == pwd2) {
        $("#checkPwd2").text("비밀번호 일치").css("color", "green");
        checkObj.pwd2 = true;
    } else {
        $("#checkPwd2").text("비밀번호 불일치").css("color", "red");
        checkObj.pwd2 = false;
    }
});


function validate() {

    // 아이디 중복 검사를 진행했는지 확인
    // * input 태그 값을 모두 String으로 반환됨
    // if ($("#idDup").val() != "true") { // 중복검사를 안한 경우
    //     swal("아이디 중복검사를 진행해 주세요").then(function () {
    //         $("#idDupCheck").focus(); // 중복 검사 버튼으로 포커스 이동
    //     });
    //     return false; // submit 이벤트 제거 
    // } 

    // checkObj에 작성된 속성들이 모두 true인지 확인

    // 객체 내 속성을 순차접근하는 반복문 : for in 구문
    for (const key in checkObj) {
        if (!checkObj[key]) { // false 값을 가진 속성이 있을 경우

            // 필수정보가 모두 입력된 경우(입력값이 전부 true일 경우)
            let msg;
            switch (key) {
                case "inputEmail":
                    msg = "아이디가 유효하지 않습니다.";
                    break;
                case "pwd1":
                    msg = "비밀번호가 유효하지 않습니다.";
                    break;
                case "pwd2":
                    msg = "비밀번호가 일치하지 않습니다. ";
                    break;
                // case "name":
                //     msg = "이름이 유효하지 않습니다.";
                //     break;
            }

            // msg에 담긴 내용을 출력
            swal(msg).then(function () {
                const selector = "#" + key;
                $(selector).focus();
                // 유효하지 않은 값을 입력한 부분으로 포커스 이동
            });
            return false; // submit이벤트 제거(회원가입 실행X)
        }
    }
}
