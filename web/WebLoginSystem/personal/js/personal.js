window.onload = function () {
    // addUploadAvatarListener(); //添加上传头像监听 该函数只在修改信息页面调用
    loadAvatar();
    loadBaseInfo();
}
let user_id = null;

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
                user_id = res['data']['userInfo']['userID'];
                console.log(res['data']['userInfo']);
                if (aboutMe)
                    aboutMe.innerText = res['data']['userInfo']['about'];
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
                    if (item === "gender" && res['data']['userInfo'][item] !== undefined) {
                        p2.innerText = (res['data']['userInfo'][item] === true ? "男" : "女");
                    } else
                        p2.innerText = (res['data']['userInfo'][item] === undefined ? "未填写" : res['data']['userInfo'][item]);
                    p2.id = item;
                    div.appendChild(p1);
                    div.appendChild(p2);
                    baseInfo.appendChild(div);
                }
                document.getElementById("age").innerText = (res['data']['userInfo']['age'] === undefined ? "未填写" : res['data']['userInfo']['age']);
                document.getElementById("fansNum").innerText = (res['data']['userInfo']['fansNum'] === undefined ? "未填写" : res['data']['userInfo']['fansNum']);
                document.getElementById("projectNum").innerText = (res['data']['userInfo']['projectNum'] === undefined ? "未填写" : res['data']['userInfo']['projectNum']);
                document.getElementById("assetNum").innerText = (res['data']['userInfo']['assetNum'] === undefined ? "未填写" : res['data']['userInfo']['assetNum']);
                document.getElementById("name").innerText = (res['data']['userInfo']['name'] === undefined ? "未填写" : res['data']['userInfo']['name']);
                // techang
                document.getElementById("techang").innerText = (res['data']['userInfo']['techang'] === undefined ? "未填写" : res['data']['userInfo']['techang']);

                let timeline = res['data']['timeline'];
                console.log(timeline);
                let timeline_ul = document.getElementById("timeline");
                // 清空ul内的所有li
                // timeline 信息
                timeline_ul.innerHTML = "";
                for (let i = 0; i < timeline.length; i++) {

                    //

                    // <li>
                    //     <div className="point"></div>
                    //     <div className="line"></div>
                    //     <div className="content_timeline_box">
                    //         <div className="content_timeline" id="timeline111">
                    //             <p className="date">2022年9月26日 21点15分</p>
                    //             <p className="content_timeline_real_content">
                    //                 web技术的第一个项目：个人简历，在这个项目中，用到了HMTL、CSS等相关知识。在这个过程中将理论应用于实践，我对相关技术有了更深的理解。</p>
                    //         </div>
                    //     </div>
                    // </li>
                    let li = document.createElement("li");
                    let point = document.createElement("div");
                    point.className = "point";
                    let line = document.createElement("div");
                    line.className = "line";
                    let content_timeline_box = document.createElement("div");
                    content_timeline_box.className = "content_timeline_box";
                    let content_timeline = document.createElement("div");
                    content_timeline.className = "content_timeline";
                    content_timeline.id = timeline[i]['id'];
                    let date = document.createElement("p");
                    date.className = "date";
                    date.innerText = timeline[i]['time'];
                    date.id = "date" + i;
                    let content_timeline_real_content = document.createElement("p");
                    content_timeline_real_content.className = "content_timeline_real_content";
                    content_timeline_real_content.innerText = timeline[i]['content'];
                    content_timeline_real_content.id = "content" + i;
                    content_timeline.appendChild(date);
                    content_timeline.appendChild(content_timeline_real_content);
                    content_timeline_box.appendChild(content_timeline);
                    li.appendChild(point);
                    li.appendChild(line);
                    li.appendChild(content_timeline_box);
                    timeline_ul.appendChild(li);

                }
                // educational信息
                //     <ul class="project_educational">
                // <li class="project_item" id="project3">
                //     <p class="project_item_name">2020.9-至今</p>
                //     <ul class="project_item_ul">
                //         <li><p>学校：朝阳大学</p></li>
                //         <li><p>专业：计算机科学与技术</p></li>
                //         <li><p>学历：本科学位</p></li>
                //     </ul>
                // </li>
                let educational = res['data']['educational'];
                let educational_ul = document.getElementById("project_educational");
                educational_ul.innerHTML = "";
                for (let i = 0; i < educational.length; i++) {
                    let li = document.createElement("li");
                    li.className = "project_item";
                    li.id = educational[i]['id'];
                    let project_item_name = document.createElement("p");
                    project_item_name.className = "project_item_name";
                    project_item_name.innerText = educational[i]['name'];
                    project_item_name.id = "name" + i;
                    let project_item_ul = document.createElement("ul");
                    project_item_ul.className = "project_item_ul";
                    // let project_item_ul_list = ['学校', '专业', '学历'];
                    let project_item_ul_list = ['', '', ''];
                    let project_item_ul_list_eng = ['school', 'major', 'degree'];
                    for (let j = 0; j < project_item_ul_list.length; j++) {
                        let li2 = document.createElement("li");
                        let p = document.createElement("p");
                        // p.innerText = project_item_ul_list[j] + "：" + educational[i][project_item_ul_list_eng[j]];
                        p.innerText = educational[i][project_item_ul_list_eng[j]];

                        p.id = project_item_ul_list[j] + i;
                        li2.appendChild(p);
                        project_item_ul.appendChild(li2);
                    }
                    li.appendChild(project_item_name);
                    li.appendChild(project_item_ul);
                    educational_ul.appendChild(li);
                }
                // project信息
                //     <ul class="project_experience">
                // <li class="project_item" id="project1">
                //     <p class="project_item_name">项目A</p>
                //     <ul class="project_item_ul">
                //         <li><p>项目时间：2018.9-2019.1</p></li>
                //         <li><p>项目描述：在项目A中负责了前端的搭建</p></li>
                //         <li><p>技术栈：HTML/CSS/JS</p></li>
                //         <li><p>成果：优秀项目</p></li>
                //     </ul>
                // </li>
                let experience = res['data']['project'];
                let experience_ul = document.getElementById("project_experience");
                experience_ul.innerHTML = "";
                for (let i = 0; i < experience.length; i++) {
                    let li = document.createElement("li");
                    li.className = "project_item";
                    li.id = experience[i]['id'];
                    let project_item_name = document.createElement("p");
                    project_item_name.className = "project_item_name";
                    project_item_name.innerText = experience[i]['name'];
                    project_item_name.id = "name" + i;
                    let project_item_ul = document.createElement("ul");
                    project_item_ul.className = "project_item_ul";
                    // let project_item_ul_list = ['项目时间', '项目描述', '技术栈', '成果'];
                    let project_item_ul_list = ['', '', '', ''];
                    let project_item_ul_list_eng = ['projectTime', 'projectDescription', 'stack', 'result'];
                    for (let j = 0; j < project_item_ul_list.length; j++) {
                        let li2 = document.createElement("li");
                        let p = document.createElement("p");
                        // p.innerText = project_item_ul_list[j] + "：" + experience[i][project_item_ul_list_eng[j]];
                        p.innerText = experience[i][project_item_ul_list_eng[j]];
                        p.id = project_item_ul_list[j] + i;
                        li2.appendChild(p);
                        project_item_ul.appendChild(li2);
                    }
                    li.appendChild(project_item_name);
                    li.appendChild(project_item_ul);
                    experience_ul.appendChild(li);
                }
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
        if (!['image/jpeg', 'image/png', 'image/gif', 'image/webp'].includes(file.type)) {
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
    data["userID"] = user_id;
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

function clickModifyBtn2() {
    let modify = document.querySelector(".modifying2");
    modify.classList.add("user-modifying");
    // 将p标签转换为input标签，用于修改信息
    // id为about-me，techang
    let aboutMe = document.getElementById("about-me");
    let techang = document.getElementById("techang");
    let data = aboutMe.innerText;
    let input = document.createElement("textarea");
    input.value = data;
    input.id = aboutMe.id;
    let parent = aboutMe.parentElement;
    parent.replaceChild(input, aboutMe);
    data = techang.innerText;
    input = document.createElement("textarea");
    input.value = data;
    input.id = techang.id;
    parent = techang.parentElement;
    parent.replaceChild(input, techang);
    // 找到class为content_timeline的div，其中有两个p标签，将其转换为input标签，class为date和content_timeline_real_content
    let div = document.getElementsByClassName("content_timeline");
    for (let i = 0; i < div.length; i++) {
        let date = div[i].getElementsByClassName("date")[0];
        let content = div[i].getElementsByClassName("content_timeline_real_content")[0];
        data = date.innerText;
        input = document.createElement("input");
        input.value = data;
        input.id = date.id;
        input.className = date.className;
        parent = date.parentElement;
        parent.replaceChild(input, date);
        data = content.innerText;
        input = document.createElement("textarea");
        input.value = data;
        input.id = content.id;
        input.className = content.className;
        parent = content.parentElement;
        parent.replaceChild(input, content);
        // 添加一个删除按钮
        let deleteBtn = document.createElement("button");
        deleteBtn.innerText = "删除";
        deleteBtn.className = "delete-timeline";
        deleteBtn.onclick = function () {
            let postData = {
                "type": "deleteTimeline",
                "id": div[i].id,
            }
            request.post("/auth/userInfo", postData, (code, res) => {
                if (code === 200) {
                    if (res['code'] === 200) {
                        alert("删除成功");
                        let div_list = document.getElementsByClassName("content_timeline");
                        let cur_div = div_list.item(i);
                        cur_div.remove();
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
        parent.appendChild(deleteBtn);
    }

    // 找到project的ul，其中每个li标签是一个项目，li标签内有一个project_item_name的p标签，将其转换为input标签
    // 还有project_item_ul的标签，有四个li标签，分别是项目名称、项目时间、项目描述、项目链接，每个li标签内有一个p标签，将其转换为input标签
    let project_ul_list = ['project_experience', 'project_educational']
    for (let x = 0; x < project_ul_list.length; x++) {
        // continue
        let ul = document.getElementsByClassName(project_ul_list[x]);
        let li = ul[0].getElementsByClassName("project_item");
        for (let i = 0; i < li.length; i++) {
            let name = li[i].getElementsByClassName("project_item_name")[0];
            data = name.innerText;
            input = document.createElement("input");
            input.value = data;
            input.id = name.id;
            input.className = name.className;
            parent = name.parentElement;
            parent.replaceChild(input, name);
            let ul2 = li[i].getElementsByClassName("project_item_ul")[0];
            let li2 = ul2.getElementsByTagName("li");
            for (let j = 0; j < li2.length; j++) {
                let p = li2[j].getElementsByTagName("p")[0];
                data = p.innerText;
                input = document.createElement("input");
                input.value = data;
                input.id = p.id;
                input.className = p.className;
                parent = p.parentElement;
                parent.replaceChild(input, p);
            }
            // 添加一个删除按钮
            let deleteBtn = document.createElement("button");
            deleteBtn.innerText = "删除";
            if (x === 0) {
                deleteBtn.className = "delete-experience";
                deleteBtn.onclick = function () {
                    let postData = {
                        "type": "deleteProject",
                        "id": li[i].id,
                    }
                    request.post("/auth/userInfo", postData, (code, res) => {
                        if (code === 200) {
                            if (res['code'] === 200) {
                                alert("删除成功");
                                let cur_li = li[i];
                                cur_li.remove();
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
            }
            if (x === 1) {
                deleteBtn.className = "delete-educational";
                deleteBtn.onclick = function () {
                    let postData = {
                        "type": "deleteEducational",
                        "id": li[i].id,
                    }
                    request.post("/auth/userInfo", postData, (code, res) => {
                        if (code === 200) {
                            if (res['code'] === 200) {
                                alert("删除成功");
                                let cur_li = li[i];
                                cur_li.remove();
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
            }

            parent.appendChild(deleteBtn);

        }
    }
}

function clickSaveModifyBtn2() {
    let modify = document.querySelector(".modifying2");
    modify.classList.remove("user-modifying");
    let data = {};
    let postData = {
        "type": "changeInfo",
    }
    let aboutMe = document.getElementById("about-me");
    data[aboutMe.id] = aboutMe.value;
    data['about'] = aboutMe.value;
    let techang = document.getElementById("techang");
    data[techang.id] = techang.value;
    let div = document.getElementsByClassName("content_timeline");
    data['timeline_content'] = [];
    for (let i = 0; i < div.length; i++) {
        // 获取id
        let id = div[i].id;
        let date = div[i].getElementsByClassName("date")[0];
        let content = div[i].getElementsByClassName("content_timeline_real_content")[0];
        data['timeline_content'].push({
            "id": id,
            "date": date.value,
            "content": content.value,
        });
    }
    let project_ul_list = ['project_experience', 'project_educational']
    for (let x = 0; x < project_ul_list.length; x++) {
        // continue
        data[project_ul_list[x]] = [];
        let ul = document.getElementsByClassName(project_ul_list[x]);
        let li = ul[0].getElementsByClassName("project_item");
        for (let i = 0; i < li.length; i++) {
            let name = li[i].getElementsByClassName("project_item_name")[0];
            // data[name.id] = name.value;
            let temp = {
                "id": li[i].id,
                "name": name.value,
                "data": [],
            }
            let ul2 = li[i].getElementsByClassName("project_item_ul")[0];
            let li2 = ul2.getElementsByTagName("li");
            for (let j = 0; j < li2.length; j++) {
                let p = li2[j].getElementsByTagName("input")[0];
                temp["data"].push(p.value);
            }
            data[project_ul_list[x]].push(temp);
        }
    }
    postData["data"] = data;

    console.log("提交的信息：", postData);
    // return;
    request.post("/auth/userInfo", postData, (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                alert("修改成功");
                clickCancelModifyBtn2();
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

function clickCancelModifyBtn2() {
    let modify = document.querySelector(".modifying2");
    modify.classList.remove("user-modifying");
    let aboutMe = document.getElementById("about-me");
    let input = document.createElement("p");
    input.innerHTML = aboutMe.value;
    input.id = aboutMe.id;
    let parent = aboutMe.parentElement;
    parent.replaceChild(input, aboutMe);
    let techang = document.getElementById("techang");
    input = document.createElement("p");
    input.innerHTML = techang.value;
    input.id = techang.id;
    parent = techang.parentElement;
    parent.replaceChild(input, techang);
    let div = document.getElementsByClassName("content_timeline");
    for (let i = 0; i < div.length; i++) {
        let date = div[i].getElementsByClassName("date")[0];
        let content = div[i].getElementsByClassName("content_timeline_real_content")[0];
        input = document.createElement("p");
        input.innerHTML = date.value;
        input.id = date.id;
        input.className = date.className;
        parent = date.parentElement;
        parent.replaceChild(input, date);
        input = document.createElement("p");
        input.innerHTML = content.value;
        input.id = content.id;
        input.className = content.className;
        parent = content.parentElement;
        parent.replaceChild(input, content);
    }
    let project_ul_list = ['project_experience', 'project_educational']
    for (let x = 0; x < project_ul_list.length; x++) {
        // continue
        let ul = document.getElementsByClassName(project_ul_list[x]);
        let li = ul[0].getElementsByClassName("project_item");
        for (let i = 0; i < li.length; i++) {
            let name = li[i].getElementsByClassName("project_item_name")[0];
            input = document.createElement("p");
            input.innerHTML = name.value;
            input.id = name.id;
            input.className = name.className;
            parent = name.parentElement;
            parent.replaceChild(input, name);
            let ul2 = li[i].getElementsByClassName("project_item_ul")[0];
            let li2 = ul2.getElementsByTagName("li");
            for (let j = 0; j < li2.length; j++) {
                let p = li2[j].getElementsByTagName("input")[0];
                input = document.createElement("p");
                input.innerHTML = p.value;
                input.id = p.id;
                input.className = p.className;
                parent = p.parentElement;
                parent.replaceChild(input, p);
            }
        }
    }
    loadBaseInfo();
}

function clickAddTimelineBtn() {
    let postData = {
        "type": "addTimeline",
    }

    console.log("提交的信息：", postData);

    request.post("/auth/userInfo", postData, (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                // alert("修改成功");
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

function clickAddEducationalBtn() {
    let postData = {
        "type": "addEducational",
    }

    console.log("提交的信息：", postData);

    request.post("/auth/userInfo", postData, (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                // alert("修改成功");
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

function clickAddProjectBtn() {
    let postData = {
        "type": "addProject",
    }

    console.log("提交的信息：", postData);

    request.post("/auth/userInfo", postData, (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                // alert("修改成功");
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