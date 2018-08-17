function $(selector) {
    return document.querySelector(selector);
}

function $all(selector) {
    return document.querySelectorAll(selector);
}

function isEmpty(value) {
    return value.length == 0
}

function fetchManager({url, method, body, headers, callback}) {
    fetch(url, {method, body, headers, credentials: "same-origin"})
        .then((response) => {
        callback(response)
    })
}

function getData(url, callback) {
  fetchManager({
    url: url,
    method: "GET",
    headers: { "content-type": "application/json" },
    callback: callback
  });
}

function addEventListenerToTarget(target, event, handler) {
    if(!target)
        return;
    target.addEventListener(event, handler)
}