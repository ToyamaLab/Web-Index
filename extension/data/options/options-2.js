/**
 * Copyright 2012-present Thom Seddon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var saveButton, saveOtherButton, list;

/**
 * Render list
 */

var render = function (data) {
  // Accounts
  var accounts = data.accounts;
  var parent = document.getElementById('accounts');

  Object.keys(accounts).forEach(function (uid) {
    var account = accounts[uid];
    var li = document.createElement('li');
    li.innerHTML = '<label>' +
      '<input type="checkbox" data-uid="' + uid + '" ' + (account.ignored ? '' : 'checked') + '>' +
      '<img src="' + account.img + '">' +
      '<div>' + account.name  +
        '<a class="icon-trash"></a>' +
      '</div>' +
    '</label>';
    li = parent.appendChild(li);

    li.querySelector('input').addEventListener('change', function () {
      saveButton.disabled = false;
      saveButton.innerHTML = 'Save';
    });

    li.querySelector('a').addEventListener('click', function () {
      remove(uid, li);
    });
  });

  saveButton.addEventListener('click', save, false);
  saveButton.disabled = true;

  // Other settings
  var settings = data.settings;
  document.querySelectorAll('.other-settings input').forEach(function (el) {
    el.checked = settings[el.getAttribute('data-setting')] || false;

    el.addEventListener('change', function () {
      saveOtherButton.disabled = false;
      saveOtherButton.innerHTML = 'Save';
    });
  });

  saveOtherButton.addEventListener('click', saveOther, false);
  saveOtherButton.disabled = true;
};

/**
 * Save state
 */

var save = function (event) {
  var ignore = [];

  var elems = document.querySelectorAll('#accounts input[type="checkbox"]');
  for (var i = 0, len = elems.length; i < len; i++) {
    var elem = elems[i];
    if (!elem.checked)
      ignore.push(elem.getAttribute('data-uid'));
  }

  chrome.extension.sendMessage({ type: 'ignore', ignore: ignore }, function() {
    saveButton.disabled = true;
    saveButton.innerHTML = 'Saved';
  });
};

/**
 * Delete Account
 */

var remove = function (uid, li) {
  chrome.extension.sendMessage({ type: 'removeAccount', uid: uid }, function() {
    li.parentNode.removeChild(li);
  });
};

/**
 * Save other settinfs
 */

var saveOther = function (event) {
  var settings = {};
  var els = document.querySelectorAll('.other-settings input[type="checkbox"]');
  els.forEach(function (el) {
    settings[el.getAttribute('data-setting')] = el.checked;
  });

  console.log(settings)

  chrome.extension.sendMessage({ type: 'settings', settings: settings }, function() {
    saveOtherButton.disabled = true;
    saveOtherButton.innerHTML = 'Saved';
  });
};

/**
 * Init
 */

document.addEventListener('DOMContentLoaded', function () {
  saveButton = document.getElementById('save');
  saveOtherButton = document.getElementById('save-other');
  chrome.extension.sendMessage({ type: 'getAccounts' }, render);
});
