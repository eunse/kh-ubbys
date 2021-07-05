/*
 게시판 상세용 공통 스크립트
 author: 백승훈
*/
(function () {
  const deleteConfirmBtn = document.createElement('a');
  deleteConfirmBtn.classList.add('btn', 'btn-danger');
  deleteConfirmBtn.appendChild(document.createTextNode('삭제'));
  const btnContainer = document.querySelector('.modal-footer');
  btnContainer.appendChild(deleteConfirmBtn);
  deleteConfirmBtn.setAttribute('href', 'delete?no=' + searchParam('no'));
  document.querySelector('.modal-title').textContent = "게시글 삭제";
  document.querySelector('.modal-body').children[0].textContent = "정말로 삭제하시겠습니까?";

  const deleteBtn = document.getElementById('deleteBtn');
  deleteBtn.addEventListener('click', function(event) {
    modal.show();
    event.preventDefault();
  })

  function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
  };
})()