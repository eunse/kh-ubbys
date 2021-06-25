let inputTag, tagContainer;

inputTag = document.querySelector('#inputTag');
tagContainer = document.querySelector('.tag-list');

inputTag.addEventListener('keyup', () => {
  if (event.which == 13 && inputTag.value.length > 0) {
    var tagText = document.createTextNode(inputTag.value);
    var tagEl = document.createElement('span');
    tagContainer.appendChild(tagEl);
    tagEl.appendChild(tagText);
    tagEl.classList.add('tag', 'badge', 'rounded-pill', 'bg-primary');
    inputTag.value = '';

    let deleteTags = document.querySelectorAll('.tag');

    for (let i = 0; i < deleteTags.length; i++) {
      deleteTags[i].addEventListener('click', () => {
        tagContainer.removeChild(deleteTags[i]);
      });
    }
  }
});