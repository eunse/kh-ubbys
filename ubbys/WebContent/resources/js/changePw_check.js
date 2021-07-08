// 각 유효성 검사 결과를 저장할 객체
const checkObj = {
    "inputPresentPw": false,
	"inputChangePw": false,
    "inputChangePwConfirm": false,
	"changePwConfirm": false
};

// // 현재 비밀번호 유효성 검사
// // 조건 : 영어, 숫자, 특수기호 8글자 이상
$("#inputPresentPw").on("input", function () {
    const regExp = /^[a-zA-Z0-9!#-_]{8,}$/;

    const pwd1 = $(this).val().trim();

    if (regExp.test(pwd1)) {
       $("#checkPwd").text("유효한 비밀번호 입니다.").css("color", "green");
        checkObj.inputPresentPw = true;
    } else {
        $("#checkPwd").text("비밀번호는 영어, 숫자 10자리로 입력하세요.").css("color", "red");
        checkObj.inputPresentPw = false;
    }
});

// // 새 비밀번호 유효성 검사
// // 조건 : 영어, 숫자, 특수기호 8글자 이상
$("#inputChangePw").on("input", function () {
    const regExp = /^[a-zA-Z0-9!#-_]{8,}$/;

    const pwd1 = $(this).val().trim();

    if (regExp.test(pwd1)) {
       $("#checkPwd1").text("유효한 비밀번호 입니다.").css("color", "green");
        checkObj.inputChangePw = true;
    } else {
        $("#checkPwd1").text("비밀번호는 알파벳, 숫자, 특수문자(!#-_)를 포함한 8자리 이상으로 입력하세요.").css("color", "red");
        checkObj.inputChangePw = false;
    }
});

// 새 비밀번호, 새 비밀번호 확인 일치 여부 판단
$("#inputChangePw, #inputChangePwConfirm").on("input", function () {

    const pwd1 = $("#inputChangePw").val();

    const pwd2 = $("#inputChangePwConfirm").val();

    if (pwd1.trim() == "" && pwd2.trim() == "") { // 둘다 비어있을 때
        $("#checkPwd1").html("&nbsp;");
        $("#checkPwd2").html("&nbsp;");
        checkObj.inputChangePwConfirm = false;
    } else if (pwd1 == pwd2) {
        $("#checkPwd2").text("비밀번호 일치").css("color", "green");
        checkObj.inputChangePwConfirm = true;
    } else {
        $("#checkPwd2").text("비밀번호 불일치").css("color", "red");
        checkObj.inputChangePwConfirm = false;
    }
});

// 현재 비밀번호와 새 비밀번호가 같은 경우
$("#inputPresentPw, #inputChangePw").on("focusout", function () {

    const presentPw = $("#inputPresentPw").val();

    const changePw = $("#inputChangePw").val();

    if (presentPw.trim() == "" && changePw.trim() == "") {
        $("#checkPwd").html("&nbsp;");
        $("#checkPwd1").html("&nbsp;");
        checkObj.changePwConfirm = false;
    } else if (presentPw != changePw && changePw != "") {
        $("#checkPwd1").text("사용 가능한 비밀번호입니다.").css("color", "green");
        checkObj.changePwConfirm = true;
    } else if( presentPw == changePw){
        $("#checkPwd1").text("현재 비밀번호와 일치합니다 변경해 주세요.").css("color", "red");
        checkObj.changePwConfirm = false;
    }
});

function validate() {

    for (const key in checkObj) {
        if (!checkObj[key]) { 

            let msg;
            switch (key) {
                case "inputPresentPw":
                    msg = "현재 비밀번호가 유효하지 않습니다.";
                    break;
                case "inputChangePw":
                    msg = "새 비밀번호가 유효하지 않습니다.";
                    break;
                case "inputChangePwConfirm":
                    msg = "비밀번호가 일치하지 않습니다. ";
                    break;
				case "changePwConfirm":
					msg = "현재 비밀번호와 같습니다. 변경해 주세요.";
					break;
            }

            swal(msg).then(function () {
                const selector = "#" + key;
                $(selector).focus();
            });
            return false; 
        }
    }
}
