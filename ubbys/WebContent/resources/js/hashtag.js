const tagObj = (function () {
  let inputTag, tagContainer, deleteTags;
  const tagString = document.getElementById('tagString');
  const regExp = /[0-1A-Za-zㄱ-힣]{0}\S+/;
  deleteTags = document.querySelectorAll('.tag');
  inputTag = document.querySelector('#inputTag');
  tagContainer = document.querySelector('.tag-list');
  
  const tagSet = new Set();
  
  for (let i in taggedList) {
    tagSet.add(taggedList[i]);
    tagString.value = Array.from(tagSet).join(',');
  } 

  inputTag.addEventListener('keyup', () => {
    if (event.keyCode == 32 && inputTag.value.length > 0) {
      if (regExp.test(inputTag.value)) {
        tagContainer.innerHTML = "";
        tagSet.add(inputTag.value.trim());
        for(let tagItem of tagSet) {
          const tagEl = document.createElement('span');
          tagContainer.appendChild(tagEl);
          tagEl.appendChild(document.createTextNode(tagItem));
          tagEl.classList.add('tag', 'badge', 'rounded-pill', 'bg-primary');
        }
      }
      inputTag.value = '';
      tagString.value = Array.from(tagSet).join(',');
    }
  });

  $(document).on('click', '.tag' ,function(e) {
      $(this).remove();
      tagSet.delete($(this)[0].innerText);
      tagString.value = Array.from(tagSet).join(',');
  })

  return tagSet;
})()


