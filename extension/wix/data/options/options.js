var saveButton, saveOtherButton;

/**
 * Init
 */
saveButton = document.getElementById('save-default');

var defaultBookmarks = {
    'Wikipedia-ja' : '1',
    'Wikipedia-en' : '2',
    'Blog' : '3',
    'Company' : '4',
    'EJ Dict' : '5'
}

for (var key in defaultBookmarks) {
  var default_ui = document.getElementById('defaultBookmarks');
  var default_li = document.createElement('li');
  var default_id = 'default_' + defaultBookmarks[key];
  //console.log(localStorage[default_id]);
  //console.log('hello');
  if (localStorage[default_id] == 'true') {
    checked = ' checked="checked"';
  } else {
    checked = '';
  }
  default_li.innerHTML = '<input type="checkbox" name="' + default_id + '"' + checked + '>' + key;
  default_li = default_ui.appendChild(default_li);
}
/*
saveButton.addEventListener('click', save, false);

var save = function (event) {
  console.log('hello');
};*/

//ボタンクリックでカスタマイズ情報を保存
$("#save-default").click(function () {
  for (var key in defaultBookmarks) {
    var default_id = 'default_' + defaultBookmarks[key];
    var default_key = $('input[name="' + default_id + '"]').val();
    if ($('input[name="' + default_id + '"]').prop("checked")) { //checked -> true
      //save state
      //localStorage[default_id] = true;
      chrome.runtime.sendMessage({method: 'setItem', key: default_id, value: true});
    }else {
      //localStorage[default_id] = false;
      chrome.runtime.sendMessage({method: 'setItem', key: default_id, value: false});
    }
  }
  location.reload();
});

$("#save_wix_library").click(function() {
  console.log('library');
  // フォームデータを取得
  var formdata = new FormData($('#wix_library_form').get(0));
  //POSTでアップロード
  $.ajax({
        url  : "http://trezia.db.ics.keio.ac.jp/WIXLibrary_0.2.1/upload",
        type : "POST",
        data : formdata,
        cache       : false,
        contentType : false,
        processData : false,
        dataType    : "html"
    })
    .done(function(data, textStatus, jqXHR){
        var author = document.getElementById("author");
        var upfile = document.getElementById("upfile");
        author.value = "";
        upfile.value = "";
        alert('WIX File was uploaded');
    })
    .fail(function(jqXHR, textStatus, errorThrown){
        alert('Uploading WIX File was failed.');
    });
});
