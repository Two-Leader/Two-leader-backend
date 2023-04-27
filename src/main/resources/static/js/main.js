function addUuidToButtonLink(button) {
    let id = 'button-link-' + button.value;
    let ref = document.getElementById(id).href;
    document.getElementById(id).href = ref + '/user/' + localStorage.getItem("uuid");
    console.log("link.href:" + document.getElementById(id).href);
}
