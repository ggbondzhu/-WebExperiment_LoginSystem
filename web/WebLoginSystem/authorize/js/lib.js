function login(isCheck) {
    let data = {
        'username': '',
        'password': '',
        'remember': ''
    };
    if (isCheck === undefined) {
        let username = document.getElementById('username').value;
        let password = document.getElementById('password').value;
        let remember = document.getElementById('remember').checked;
        data = {
            'username': username,
            'password': hex_md5(password),
            'remember': remember
        };
    }
    request.post("/login", data, (code, res) => {
        let info = document.querySelector('.right .info');
        info.firstChild.nodeValue = "";
        if (code === 200) {
            if (res['code'] === 200) {
                if (isCheck !== undefined)  //如果是自动检查登录状态，跳转到重定向页提示用户已登录
                {
                    if (res['data']['admin'] === true)
                        window.location.href = "../redirect/index.html?id=alreadyLogin&target=../administrator/index.html";
                    else
                        window.location.href = "../redirect/index.html?id=alreadyLogin&target=../personal/index.html";
                } else {    //用户点击登录，直接跳转
                    if (res['data']['admin'] === true)
                        window.location.href = "../administrator/index.html";
                    else
                        window.location.href = "../personal/index.html";
                }
            } else if (isCheck === undefined) {
                info.firstChild.nodeValue = res['msg'];
            }
        } else if (isCheck === undefined) {
            //网络或服务器错误
            info.firstChild.nodeValue = "网络或服务器错误";
        }
    })
}

function register() {
    let username = document.getElementById('username-register').value;
    let password = document.getElementById('password-register').value;
    let password2 = document.getElementById('password-again-register').value;
    if (password !== password2) {
        alert("两次密码不一致");
        return;
    }
    let data = {
        'username': username,
        'password': hex_md5(password)
    };
    request.post("/register", data, (code, res) => {
        let info = document.querySelectorAll('.right .info')[1];
        info.firstChild.nodeValue = "";
        if (code === 200) {
            if (res['code'] === 200)
                info.firstChild.nodeValue = "注册成功，请牢记您的账号和密码";
            else {
                info.firstChild.nodeValue = res['msg'];
            }
        } else {
            //网络或服务器错误
            info.firstChild.nodeValue = "网络或服务器错误，请稍后再试";
        }
    })
}

function clickHeaderItem(target) {
    let headerItems = document.querySelectorAll(".item");
    let inputArea = document.getElementsByClassName("input-area");
    // console.log(inputArea);
    for (let i = 0; i < headerItems.length; i++) {
        if (headerItems[i] === target) {
            headerItems[i].classList.add('active');
            inputArea[i].classList.add('active');
        } else {
            headerItems[i].classList.remove('active');
            inputArea[i].classList.remove('active');
        }

    }
}

function changeToGrey() {
    let item = document.getElementsByTagName("html")[0];
    if (item.classList.contains("grey"))
        item.classList.remove("grey");
    else
        item.classList.add("grey");
}

