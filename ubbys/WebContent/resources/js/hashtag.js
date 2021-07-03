(function () {
  let inputTag, tagContainer;
  const tagString = document.getElementById('tagString');

  const regExp = /[0-1A-Za-zㄱ-힣]{0}\S+/;
  inputTag = document.querySelector('#inputTag');
  tagContainer = document.querySelector('.tag-list');

  function Tag() {}
  tags = new Tag()
  Tag.prototype.toString = function TagsToString() {
    return Object.getOwnPropertyNames(this);
  }
  inputTag.addEventListener('keyup', () => {
    if (event.keyCode == 32 && inputTag.value.length > 0) {
      if (regExp.test(inputTag.value)) {
        var tagText = document.createTextNode(inputTag.value);
        var tagEl = document.createElement('span');
        tagContainer.appendChild(tagEl);
        tagEl.appendChild(tagText);
        tags[inputTag.value.trim()] = inputTag.value;
        tagString.value = tags.toString().toString();

        tagEl.classList.add('tag', 'badge', 'rounded-pill', 'bg-primary');

        let deleteTags = document.querySelectorAll('.tag');
        for (let i = 0; i < deleteTags.length; i++) {
          deleteTags[i].addEventListener('click', () => {
            tagContainer.removeChild(deleteTags[i]);
            delete tags[deleteTags[i].innerText.trim()];
            tagString.value = tags.toString().toString();
          });
        }
      }
      inputTag.value = '';
    }
  });
})()


