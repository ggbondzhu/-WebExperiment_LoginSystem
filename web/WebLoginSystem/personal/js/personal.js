window.onload = function () {
    // addUploadAvatarListener(); //添加上传头像监听 该函数只在修改信息页面调用
    loadAvatar();
    loadBaseInfo();
}

function logout() {
    request.get("/logout", (code, res) => {
        // console.log(res)
        if (code === 200) {
            if (res['code'] === 200)
                window.location.href = "../authorize/index.html";
        } else {
            //网络或服务器错误
            alert("网络或服务器错误，请稍后再试");
        }
    });
}


// 将p标签转换为input标签，用于修改信息
function modifyInformation() {
    let baseInfo = document.getElementById("base-info");
    for (let child of baseInfo.children) {
        console.log(child.children[1].innerText);
        let data = child.children[1].innerText;
        let input = document.createElement("input");
        input.value = data;
        input.id = child.children[1].id;
        child.replaceChild(input, child.children[1]);
    }

    let element = [document.getElementById("age"),
        document.getElementById("fansNum"),
        document.getElementById("projectNum"),
        document.getElementById("assetNum"),
        document.getElementById("name")];
    for (let i = 0; i < element.length; i++) {
        let input = document.createElement("input");
        input.value = element[i].innerText;
        input.style.width = "-webkit-fill-available";
        input.id = element[i].id;
        input.style.textAlign = "center";
        element[i].parentElement.replaceChild(input, element[i]);
    }
}

// 将input标签转换为p标签，用于显示信息
function cancelModifyInformation() {
    let baseInfo = document.getElementById("base-info");
    for (let child of baseInfo.children) {
        console.log(child.children[1].innerText);
        let data = child.children[1].value;
        let input = document.createElement("p");
        input.innerHTML = data;
        input.id = child.children[1].id;
        child.replaceChild(input, child.children[1]);
    }

    let element = [document.getElementById("age"),
        document.getElementById("fansNum"),
        document.getElementById("projectNum"),
        document.getElementById("assetNum"),
        document.getElementById("name")];
    for (let i = 0; i < element.length; i++) {
        let input = document.createElement("p");
        input.innerHTML = element[i].value;
        input.style.width = "-webkit-fill-available";
        input.id = element[i].id;
        input.style.textAlign = "center";
        element[i].parentElement.replaceChild(input, element[i]);
    }

    // 重新加载基本信息，因为原始信息没保存，修改后的信息也没提交服务器，所以重新加载
    loadBaseInfo();
}

function loadBaseInfo() {
    request.get("/auth/userInfo", (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                let baseInfo = document.getElementById("base-info");
                let newBaseInfo = document.createElement("div");
                let aboutMe = document.getElementById("about-me");
                if (aboutMe)
                    aboutMe.innerText = res['data']['about'];
                newBaseInfo.id = "base-info";
                baseInfo.parentElement.replaceChild(newBaseInfo, baseInfo);
                baseInfo = document.getElementById("base-info");
                let data = {
                    "gender": "性别",
                    "nativePlace": "籍贯",
                    "school": "学校",
                    "college": "学院",
                    "major": "专业",
                    "sno": "学号",
                    "phone": "电话",
                    "email": "邮箱",
                };
                for (let item in data) {
                    let div = document.createElement("div");
                    let p1 = document.createElement("p");
                    let p2 = document.createElement("p");
                    p1.innerText = data[item] + "：";
                    if (item === "gender" && res['data'][item] === undefined !== undefined) {
                        p2.innerText = res['data'][item] === true ? "男" : "女";
                    } else
                        p2.innerText = res['data'][item] === undefined ? "未填写" : res['data'][item];
                    p2.id = item;
                    div.appendChild(p1);
                    div.appendChild(p2);
                    baseInfo.appendChild(div);
                }
                document.getElementById("age").innerText = res['data']['age'] === undefined ? "未填写" : res['data']['age'];
                document.getElementById("fansNum").innerText = res['data']['fansNum'] === undefined ? "未填写" : res['data']['fansNum'];
                document.getElementById("projectNum").innerText = res['data']['projectNum'] === undefined ? "未填写" : res['data']['projectNum'];
                document.getElementById("assetNum").innerText = res['data']['assetNum'] === undefined ? "未填写" : res['data']['assetNum'];
                document.getElementById("name").innerText = res['data']['name'] === undefined ? "未填写" : res['data']['name'];
            } else if (res['code'] === 405) {
                window.location.href = "../redirect/index.html?id=notLogin";
            } else {
                alert("获取用户基本信息失败");
            }
        } else {
            alert("网络或服务器错误，请稍后再试");
        }
    });
    // let baseInfo = document.getElementById("base-info");
    // for (let item in data) {
    //     let div = document.createElement("div");
    //     let p1 = document.createElement("p");
    //     let p2 = document.createElement("p");
    //     p1.innerText = item + "：";
    //     p2.innerText = data[item];
    //     p2.id = item;
    //     div.appendChild(p1);
    //     div.appendChild(p2);
    //     baseInfo.appendChild(div);
    // }
}

let first = true;

function clickDeleteBtn() {
    if (first) {
        let deleteBtn = document.querySelector(".modify-btn");
        let span = deleteBtn.nextElementSibling;
        span.style.display = "inline";
        let num = 5;
        deleteBtn.setAttribute("disabled", "disabled");
        deleteBtn.innerText = num + "s后可点击";
        deleteBtn.style.cursor = "not-allowed";
        num--;
        first = false;
        let timer = setInterval(() => {
            if (num === 0) {
                deleteBtn.removeAttribute("disabled");
                deleteBtn.innerText = "注销账号";
                deleteBtn.style.cursor = "pointer";
                clearInterval(timer);
            } else {
                deleteBtn.setAttribute("disabled", "disabled");
                deleteBtn.innerText = num + "s后可点击";
                num--;
            }
        }, 1000);
    } else {
        first = true;
        let deleteBtn = document.querySelector(".modify-btn");
        let span = deleteBtn.nextElementSibling;
        span.innerHTML = "注销中，等待服务器响应..."
        deleteAccount();
    }
}

function dataURLtoBlob(data) {
    let arr = data.split(","),
        mime = arr[0].match(/:(.*?);/)[1],
        bst = atob(arr[1]),
        n = bst.length,
        u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bst.charCodeAt(n);
    }
    return new Blob([u8arr],);
}

function addUploadAvatarListener() {
    let fileInput = document.getElementById('file');
    let info = document.getElementById('info');
    let preview = document.getElementById('image-preview');
    // 监听change事件:
    fileInput.addEventListener('change', function () {
        if (!fileInput.value) {
            info.innerHTML = '没有选择文件';
            return;
        }
        let file = fileInput.files[0];
        let size = file.size;
        if (size >= 1 * 1024 * 1024) {
            alert('文件大小超出限制');
            info.innerHTML = '文件大小超出限制';
            return false;
        }
        // 获取File信息:
        info.innerHTML = `文件名称:  + ${file.name}<br>文件大小: ${file.size} <br>上传时间: ${file.lastModifiedDate}`;
        if (!['image/jpeg', 'image/png', 'image/gif'].includes(file.type)) {
            alert('不是有效的图片文件!');
            return;
        }
        // 读取文件:
        let reader = new FileReader();
        reader.onload = function (e) {
            let data = e.target.result;
            // console.log(preview)
            preview.src = data;
            // new Blob([data], {type: file.type});
        };
        // 以DataURL的形式读取文件:
        reader.readAsDataURL(file);
    });
}

function clickModifyBtn() {
    let modify = document.querySelector(".modifying");
    modify.classList.add("user-modifying");
    modifyInformation();
}

function clickCancelModifyBtn() {
    let modify = document.querySelector(".modifying");
    modify.classList.remove("user-modifying");
    cancelModifyInformation();
}

function clickSaveModifyBtn() {
    let modify = document.querySelector(".modifying");
    modify.classList.remove("user-modifying");
    //cancelModifyInformation();

    //上传新信息
    let data = {};
    let postData = {
        "type": "changeInfo",
    }
    let baseInfo = document.getElementById("base-info");
    for (let child of baseInfo.children) {
        data[child.children[1].id] = (child.children[1].value === "未填写" ? null : child.children[1].value);
        if (child.children[1].value === "男" || child.children[1].value === "女") {
            data[child.children[1].id] = (child.children[1].value === "男" ? true : false);
        }
    }

    let element = [document.getElementById("age"),
        document.getElementById("fansNum"),
        document.getElementById("projectNum"),
        document.getElementById("assetNum"),
        document.getElementById("name")];
    for (let i = 0; i < element.length; i++) {
        data[element[i].id] = (element[i].value === "未填写" ? null : element[i].value);
    }
    postData["data"] = data;

    console.log("提交的信息：", postData);

    request.post("/auth/userInfo", postData, (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                alert("修改成功");
                cancelModifyInformation();
            } else if (res['code'] === 405) {
                alert("您还未登录或登录已过期，请先登录");
            } else {
                alert(res['msg']);
            }
        } else {
            //网络或服务器错误
            alert("网络或服务器错误，请稍后再试");
        }
    });
}

function deleteAccount() {
    request.get("/auth/delete", (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                window.location.href = "../redirect/index.html?id=delete";
            } else if (res['code'] === 405) {
                window.location.href = "../redirect/index.html?id=notLogin";
            } else {
                let deleteBtn = document.querySelector(".modify-btn");
                let span = deleteBtn.nextElementSibling;
                span.innerHTML = "注销失败，请稍后再试";
            }
        } else {
            alert("网络或服务器错误，请稍后再试");
        }
    });
}

function clickModifyPasswordBtn() {
    let password = document.getElementById('password-register').value;
    let password2 = document.getElementById('password-again-register').value;
    let oldPassword = document.getElementById('password-old-register').value;
    if (password !== password2) {
        alert("两次密码不一致");
        return;
    }
    let data = {
        'type': 'changePassword',
        'password': hex_md5(password),
        'oldPassword': hex_md5(oldPassword),
    };
    request.post("/auth/userInfo", data, (code, res) => {
        let info = document.getElementById("password-info");
        info.firstChild.nodeValue = "";
        if (code === 200) {
            if (res['code'] === 200) {
                info.firstChild.nodeValue = "密码修改成功，请牢记您的账号和密码";
            } else if (res['code'] === 405) {
                info.firstChild.nodeValue = "您还未登录或登录已过期，请先登录";
            } else {
                info.firstChild.nodeValue = res['msg'];
            }
        } else {
            //网络或服务器错误
            info.firstChild.nodeValue = "网络或服务器错误，请稍后再试";
        }
    });
}

//不用此方法
function arrayBufferToBase64(buffer) {
    let binary = '';
    let bytes = new Uint8Array(buffer);
    let len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
}

function loadAvatar() {
    requestByte.getByte("/auth/avatar", (code, res) => {
        if (code === 200) {
            if (res['type'] === 'application/json') {
                return;
            }
            let avatar = document.getElementById("avatar");
            avatar.src = URL.createObjectURL(res);
            let preview = document.getElementById('image-preview');
            if (preview) {
                preview.src = URL.createObjectURL(res);
            }
            //avatar.src = res//'data:image/png;base64,' + arrayBufferToBase64(res);//res//"data:img/png;base64," + ;
            // console.log(res)
        } else {
            alert("网络或服务器错误，请稍后再试");
        }
    });
}

function clickUploadAvatarBtn() {
    let data = document.getElementById('image-preview').src;
    requestByte.postByte("/auth/avatar", dataURLtoBlob(data), (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                alert("上传成功，头像已修改");
                loadAvatar();
            } else if (res['code'] === 405) {
                alert("您还未登录或登录已过期，请先登录");
            } else {
                alert(res['msg']);
            }
        }
    });
}