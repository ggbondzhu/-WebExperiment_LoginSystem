function getVilificationCode() {
    let email = document.getElementById('email-email').value;
    let data = {
        'type': 'requestCode',
        'email': email,
    };
    request.post("/forget", data, (code, res) => {
            let info = document.querySelectorAll('.right .info')[0];
            info.firstChild.nodeValue = "";
            if (code === 200) {
                if (res['code'] === 200) {
                    info.firstChild.nodeValue = "验证码已发送";
                    let button = document.getElementById('getCodeButton');
                    button.disabled = true;
                    let num = 60;
                    let timer = setInterval(() => {
                        if (num <= 0) {
                            button.disabled = false;
                            button.innerHTML = "获取验证码";
                            clearInterval(timer);
                        } else
                            button.innerHTML = num-- + "秒后再次发送";
                    }, 1000);
                } else {
                    info.firstChild.nodeValue = res['msg'] + "，" + res['data']['message'];
                }
            } else {
                //网络或服务器错误
                info.firstChild.nodeValue = "网络或服务器错误，请稍后再试";
            }
        }
    )
}

function submitEmailVilification() {
    let email = document.getElementById('email-email').value;
    let code = document.getElementById('email-code').value;
    let data = {
        'type': 'code',
        'email': email,
        'code': code
    };
    request.post("/forget", data, (code, res) => {
            let info = document.querySelectorAll('.right .info')[0];
            info.firstChild.nodeValue = "";
            if (code === 200) {
                if (res['code'] === 200) {
                    window.location.href = "changePwd.html?username=" + res['msg'];
                } else {
                    info.firstChild.nodeValue = res['msg'];
                }
            } else {
                //网络或服务器错误
                info.firstChild.nodeValue = "网络或服务器错误，请稍后再试";
            }
        }
    )
}

function submitInfoVilification() {
    let email = document.getElementById('info-email').value;
    let phone = document.getElementById('info-phone').value;
    let username = document.getElementById('info-username').value;
    let data = {
        'type': 'info',
        'email': email,
        'username': username,
        'phone': phone
    };
    request.post("/forget", data, (code, res) => {
            let info = document.querySelectorAll('.right .info')[1];
            info.firstChild.nodeValue = "";
            if (code === 200) {
                if (res['code'] === 200) {
                    window.location.href = "./changePwd.html?username=" + res['msg'];
                } else {
                    info.firstChild.nodeValue = res['msg'];
                }
            } else {
                //网络或服务器错误
                info.firstChild.nodeValue = "网络或服务器错误，请稍后再试";
            }
        }
    )
}