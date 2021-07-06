(function () {
  const tagString = document.getElementById('tagString');
  const appsWrite = document.getElementById('apps-write');
  const inputTag = document.getElementById('inputTag');
  appsWrite.addEventListener('submit', function(e) {
    if(tagString.value == "") {
      event.preventDefault();
      event.stopPropagation();
      inputTag.classList.remove('is-valid');
      inputTag.classList.add('is-invalid')
    } else {
      inputTag.classList.remove('is-invalid');
      inputTag.classList.add('is-valid')
    }
  })
})()