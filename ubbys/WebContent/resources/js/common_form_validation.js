(function () {
  'use strict'

  const forms = document.querySelectorAll('.needs-validation')
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }
        form.classList.add('was-validated')
      }, false)
    })
})()

const loadFile = function (event) {
  const output = document.getElementById('uploadImagePreview');
  output.style.backgroundImage = "url('" + URL.createObjectURL(event.target.files[0]) + "')"
  output.addEventListener('onload', function() {
    URL.revokeObjectURL(output.style.backgroundImage);
  })
};