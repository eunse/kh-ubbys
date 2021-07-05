(function () {
  'use strict'

  const forms = document.querySelectorAll('.needs-validation')
  Array.prototype.slice.call(forms).forEach(function (form) {
    form.addEventListener('submit', function (event) {
      for(const key in checkObj) {
        if (!checkObj[key]) {
          document.querySelector('.modal-title').textContent = "회원가입 불가"; 
          document.querySelector('.modal-body').children[0].textContent = "입력한 모든 항목을 다시 확인해주세요.";
          modal.show();
          event.preventDefault();
          event.stopPropagation();
        }
      }
    }, false)
  })

  const checkObj = {
    "inputEmail": false,
    "inputPw": false,
    "inputPwConfirm": false
  }

  const inputEmail = document.getElementById('inputEmail');
  const checkEmail = document.getElementById('checkEmail');
  inputEmail.addEventListener('input', function() {
    const regExp = /^[\w]{4,}@[\w]+(\.[\w]+){1,3}$/;
    const email = inputEmail.value.trim();

    if(regExp.test(email)) {
      var xhr = new XMLHttpRequest();
      xhr.open('get', 'signup/idDupCheck?inputEmail=' + email);
      xhr.send();
      xhr.onload = function (e) {
        if (xhr.status === 200) {
          if(xhr.responseText > 0) {
            inputEmail.classList.remove('is-valid');
            inputEmail.classList.add('is-invalid');
            checkEmail.innerText = "이미 사용중인 아이디입니다."
            checkObj.inputEmail = false;
          } else {
            inputEmail.classList.remove('is-invalid');
            inputEmail.classList.add('is-valid');
            checkObj.inputEmail = true;
          }
        } else {
          console.log('통신 오류');
        }
      };
    } else {
      inputEmail.classList.remove('is-valid');
      inputEmail.classList.add('is-invalid');
      checkEmail.innerText = "올바른 이메일 형식을 입력하세요."
      checkObj.inputEmail = false;
    }
  })

  const inputPw = document.getElementById('inputPw');
  const checkPw = document.getElementById('checkPw');
  inputPw.addEventListener('input', function() {
    const regExp = /^[a-zA-Z0-9!#-_]{8,}$/;
    const password = inputPw.value.trim();

    if(regExp.test(password)) {
      inputPw.classList.remove('is-invalid');
      inputPw.classList.add('is-valid');
      checkObj.inputPw = true;
    } else {
      inputPw.classList.remove('is-valid');
      inputPw.classList.add('is-invalid');
      checkPw.innerText = "비밀번호는 알파벳, 숫자, 특수문자(!#-_)를 포함한 8자리 이상으로 입력하세요."
      checkObj.inputPw = false;
    }
  })

  const inputPwConfirm = document.getElementById('inputPwConfirm');
  const checkPwConfirm = document.getElementById('checkPwConfirm');
  const inputPwAll = document.querySelectorAll('input[type="password"]');
  inputPwAll.forEach(item => {
    item.addEventListener('input', function() {
      const pwd1 = inputPw.value;
      const pwd2 = inputPwConfirm.value;
      if (pwd1.trim() == "" && pwd2.trim() == "") {
        item.classList.remove('is-valid');
        item.classList.add('is-invalid');
        checkObj.inputPwConfirm = false;
      } else if (pwd1 == pwd2) {
        inputPwConfirm.classList.remove('is-invalid');
        inputPwConfirm.classList.add('is-valid');
        checkPwConfirm.innerText = "비밀번호가 일치합니다."
        checkObj.inputPwConfirm = true;
      } else {
        inputPwConfirm.classList.remove('is-valid');
        inputPwConfirm.classList.add('is-invalid');
        checkPwConfirm.innerText = "비밀번호가 일치하지 않습니다."
        checkObj.inputPwConfirm = false;
      }
    })
  })

})()