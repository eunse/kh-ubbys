var Hashtag = (function () {

  if (!Array.prototype.forEach) {
    Array.prototype.forEach = function (fun) {
      var len = this.length;
      if (typeof fun != "function")
        throw new TypeError();

      var thisp = arguments[1];
      for (var i = 0; i < len; i++) {
        if (i in this)
          fun.call(thisp, this[i], i, this);
      }
    };
  }

  var template = '<span class="tag badge rounded-pill bg-primary">{#}</span>';
  var templates = {};

  function repl(elem, t) {
    var elems = document.querySelectorAll(elem);
    [].forEach.call(elems, function (elem) {
      var html = elem.innerHTML;
      var matched = html.match(/(\S*#\[[^\]]+\])|(\S*#\S+)/gi);
      [].forEach.call(matched, function (m) {
        var templ;
        if (t) templ = templates[t];
        else templ = template;
        templ = templ.replace('{#}', m);
        templ = templ.replace('{#n}', m.slice(1));
        html = html.replace(m, templ);
      });
      elem.innerHTML = html;
    });
  }

  function setopts(opts) {
    if (opts.template) template = opts.template;
    if (opts.templates) templates = opts.templates;
  }

  return {
    replaceTags: repl,
    setOptions: setopts
  }

}());

Hashtag.replaceTags('#myDiv');