function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substring(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

window.onload = () => {
    let username = GetQueryString("username");
    let tipInfo = document.getElementById("username-register");
    tipInfo.setAttribute("placeHolder", username);
}

function submitNewPassword() {
    let username = document.getElementById('username-register').getAttribute("placeHolder");
    let password = document.getElementById('password-register').value;
    let password2 = document.getElementById('password-again-register').value;
    if (password !== password2) {
        alert("两次密码不一致");
        return;
    }
    let data = {
        'type': 'password',
        'username': username,
        'password': hex_md5(password)
    };
    request.post("/forget", data, (code, res) => {
        let info = document.querySelectorAll('.right .info')[0];
        info.firstChild.nodeValue = "";
        if (code === 200) {
            if (res['code'] === 200) {
                info.firstChild.nodeValue = "密码重置成功，请牢记您的账号和密码";
                setTimeout(() => {
                    window.location.href = "../redirect/index.html?id=resetPwd&target=../authorize/index.html";
                }, 1500);
            } else {
                info.firstChild.nodeValue = res['msg'];
            }
        } else {
            //网络或服务器错误
            info.firstChild.nodeValue = "网络或服务器错误，请稍后再试";
        }
    })
}