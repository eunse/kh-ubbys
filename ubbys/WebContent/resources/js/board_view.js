/*
 게시판 상세용 공통 스크립트
 author: 백승훈
*/

// 보안 등의 여러가지 이유로 즉시실행 함수를 만들어 쓰는 것이 좋습니다.
// 전역 변수가 만들어지는 건 보안상 좋지 않아요.
(function () {
  const modalFooter = document.querySelector('.modal-footer'); // class 이름이 modal-footer인 요소를 선택합니다.
  const modalBody = document.querySelector('.modal-body')
  const modalTitle = document.querySelector('.modal-title')
  var addtionalBtn = null;

  // 게시판 상세 버튼에서 '삭제' 버튼을 눌렀을 때 모달이 출력될 수 있도록 eventListener 추가.
  // html 태그 안에 onclick 같은 속성으로 직접 액션을 지정하는 건 사실 좋은 방법이 아니라..
  // 이렇게 하는게 좋습니다. 
  if (document.getElementById('deleteBtn') != null) {
    // 모달에 '닫기' 이외의 액션이 필요한 경우
    // 일단 버튼부터 생성
    var addtionalBtn = document.createElement('a'); // a 태그를 생성해서 상수에 저장합니다.
    addtionalBtn.classList.add('btn', 'btn-danger'); // a에 class 목록을 추가합니다. (부트스트랩 btn)
    addtionalBtn.appendChild(document.createTextNode('삭제')); // a 태그의 자식을 생성하는데, 그게 textNode이고 값은 '삭제'!

    // 버튼이 들어갈 자리를 지정하는 단계
    modalFooter.appendChild(addtionalBtn); // 아까 만든 버튼을 위에서 선택한 modal-footer 안에 삽입!
    addtionalBtn.setAttribute('href', 'delete?no=' + searchParam('no')); // 버튼에 href 속성이랑 그 값 추가

    // 모달 안의 텍스트 추가
    modalTitle.textContent = "게시글 삭제"; //  .modal-title 요소 선택해서 그 안에 textContent 값을 바꿈.
    modalBody.children[0].textContent = "정말로 삭제하시겠습니까?"; // querySelector로 선택하면 배열은 아니지만 배열과 유사한 것이 반환되는데 하나만 선택하더라도 인덱스를 가지고 있어서 0번째라고 지정해주어야 합니다.
  // "modal-body의 0번째 자식의 textContent(이 경우 modal-body안의 p태그)"의 값을 바꿉니다.
    const deleteBtn = document.getElementById('deleteBtn');
    deleteBtn.addEventListener('click', function (event) {
      modal.show();
      event.preventDefault(); // 이건 요소가 원래 가지고 있는, 요소를 작동시켰을 때 기대할 수 있는 액션들. 예를 들면 button의 경우 submit 이 되겠지요. 아무튼 그런걸 막아주는 기능을 합니다.
    })
  }


  // 자바스크립트에서 현재 쿼리스트링을 찾아올 수 있는 함수...
  function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
  };

  const btnLike = document.getElementById('btnLike');
  btnLike.addEventListener('click', function() {
    const postId = searchParam('no');
    const likePostId = getLikeId();
    console.log(postId);
    console.log(likePostId);
    $.ajax({
      url: "like",
      data: { 
        "no": postId,
        "likePostId": likePostId
      },
      type: "POST",
      dataType: "JSON",
      success: function (result) {
        if (result == 0) {
          console.log("비로그인")
          alert("로그인 후에 이용해주세요.")
        // } else if (result.upDownFlag == -1) {
        //   console.log("빼기 성공")
        //   console.log(result)
        //   $('#btnLike').html(`<i class="bi bi-heart"></i> <span id="likeCount">${result.currentLikeCount}</span>`)
        // } else if (result.upDownFlag == 1) {
        //   console.log("더하기 성공")
        //   $('#btnLike').html(`<i class="bi bi-heart-fill text-danger"></i> <span id="likeCount">${result.currentLikeCount}</span>`)
        } else {
          location.reload(); // 모든걸 포기했다.. 
        }
      },
      error: function (e) {
        console.log(e);
      }
    });
    // const xhr = new XMLHttpRequest();
    // xhr.open('post', 'like');
    // xhr.setRequestHeader('Content-type', 'application/json');
    // const data = { 
    //   no: 60,
    //   likeId: 1
    // };
    // xhr.send(JSON.stringify(data));
    // xhr.onload = function(e) {
    //   if(xhr.status === 200) {
    //     if(xhr.responseText > 0) {
    //       console.log("성공")
    //     } else {

    //       console.log(xhr.status)
    //       alert("로그인 후 이용 가능합니다.")
    //     }
    //   } else {
    //     console.log("좋아요를 눌렀으나 좋지 않은 결과가...(서버 응답 적으로...)")
    //     alert("통신 오류가 발생했습니다. 나중에 다시 시도해주세요.")
    //   }
    // }
  })
  function getLikeCount(postId){
    let currentLike = $('#likeCount').text();
    $.ajax({
      url: "like",
      data: { "no": postId},
      type: "GET",
      dataType: "JSON",
      success: function (result) {
        if(currentLike > result) {
          $('#btnLike').html(`<i class="bi bi-heart-fill"></i> <span id="likeCount">${result}</span>`)
        } else {
          $('#btnLike').html(`<i class="bi bi-heart-fill"></i> <span id="likeCount">${result}</span>`)
        }
      },
      error: function (e) {
        console.log(e);
      }
    });
  }

  
})()