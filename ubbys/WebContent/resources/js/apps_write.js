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

  ClassicEditor.create(document.querySelector('#inputContent'), {
      toolbar: {
        items: [
          'heading',
          '|',
          'bold',
          'italic',
          'link',
          'bulletedList',
          'numberedList',
          '|',
          'outdent',
          'indent',
          '|',
          'blockQuote',
          'insertTable',
          'mediaEmbed',
          'undo',
          'redo'
        ]
      },
      language: 'ko',
      table: {
        contentToolbar: [
          'tableColumn',
          'tableRow',
          'mergeTableCells'
        ]
      },
      licenseKey: '',
    })
    .then(editor => {
      window.editor = editor;
    })
    .catch(error => {
      console.error('Oops, something went wrong!');
      console.error('Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:');
      console.warn('Build id: 5y8b948gyt5-jfha1cexgplv');
      console.error(error);
    });
})()