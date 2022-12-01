window.onload = function () {
    getAllUser();
    let select = document.getElementById("select");
    select.addEventListener("change", function () {
        let value = select.value;
        curPageSize = value;
        curPage = 1;
        getAllUser(curPage, value);
    });
}

let tableData = [];

let curSelect = "user";
let curPage = 1;
let curPageSize = 10;

function getAllUser(page, pageSize) {
    request.get("/auth/getAllUsers?page=" + page + "&pageSize=" + pageSize, (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                console.log(res)
                tableData = res['data']['result'];
                if (curSelect === "user") {
                    updateTable();
                } else if (curSelect === "personal") {
                    updatePersonalInfoTable();
                }
                if (res['data']['total']) {
                    // console.log(res['data']['total'])
                    let total = document.getElementById("total");
                    total.innerHTML = res['data']['total'];

                    //更新ul
                    let ul = document.getElementById("page");
                    // console.log(ul)
                    let newUl = document.createElement("ul");
                    newUl.setAttribute("id", "page");
                    ul.parentElement.replaceChild(newUl, ul);
                    for (let i = 0; i < (res['data']['total']) / curPageSize; i++) {
                        let li = document.createElement("li");
                        li.innerHTML = i + 1 + "";
                        if (i === curPage - 1) {
                            li.classList.add("active");
                        }
                        li.setAttribute("onclick", "clickPage(this)");
                        newUl.appendChild(li);
                    }
                }
            } else if (res['code'] === 405) {
                window.location.href = "../redirect/index.html?id=notLogin";
            } else {
                alert("获取用户信息失败，请稍后再试");
            }
        } else {
            //网络或服务器错误
            //info.firstChild.nodeValue = "网络或服务器错误，请稍后再试";
        }
    })
}

function clickPage(page) {
    let pages = document.querySelectorAll("#page li");
    for (let i = 0; i < pages.length; i++) {
        pages[i].classList.remove("active");
    }
    page.classList.add("active");
    curPage = page.innerHTML;
    getAllUser(curPage, curPageSize);
}

function getUserBySearch(query) {
    request.get("/auth/getAllUsers?query=" + query, (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                console.log(res)
                tableData = res['data']['result'];
                if (curSelect === "user") {
                    updateTable();
                } else if (curSelect === "personal") {
                    updatePersonalInfoTable();
                }
            } else if (res['code'] === 405) {
                window.location.href = "../../redirect/index.html?id=notLogin";
            } else {
                alert("获取用户信息失败，请稍后再试");
            }
        } else {
            //网络或服务器错误
            //info.firstChild.nodeValue = "网络或服务器错误，请稍后再试";
        }
    })
}

function updateTable() {
    let thead = document.getElementById("table-head");
    let newHead = document.createElement("thead");
    newHead.setAttribute("id", "table-head");
    thead.parentNode.replaceChild(newHead, thead);
    thead = document.getElementById("table-head");
    let th = [];
    for (let i = 0; i < 8; i++) {
        let newTh = document.createElement("th");
        th.push(newTh);
    }
    let newTh = document.createElement("th");
    newTh.innerHTML = "选择";
    thead.appendChild(newTh);
    th[0].innerHTML = "ID";
    th[1].innerHTML = "用户名";
    th[2].innerHTML = "电话";
    th[3].innerHTML = "邮箱";
    th[4].innerHTML = "用户创建时间";
    th[5].innerHTML = "管理员";
    th[6].innerHTML = "已激活";
    th[7].innerHTML = "操作";
    let btn3 = document.createElement("button");
    btn3.innerHTML = "删除选定用户";
    btn3.setAttribute("onclick", "clickButton(this, 'delete')");
    btn3.classList.add("delete-btn");
    th[7].appendChild(btn3);
    for (let i = 0; i < 8; i++) {
        thead.appendChild(th[i]);
    }


    let tbody = document.getElementById("table-body");
    let newTbody = document.createElement("tbody");
    newTbody.setAttribute("id", "table-body");
    tbody.parentNode.replaceChild(newTbody, tbody);
    tbody = document.getElementById("table-body");
    for (let i = 0; i < tableData.length; i++) {
        let tr = document.createElement("tr");
        let checkbox = document.createElement("input");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("data-username", tableData[i]['userName']);
        checkbox.classList.add("checkbox");
        let td1 = document.createElement("td");
        td1.innerHTML = tableData[i]['userID'];
        let td2 = document.createElement("td");
        td2.innerHTML = tableData[i]['userName'];
        let td3 = document.createElement("td");
        td3.innerHTML = tableData[i]['phone'] === undefined ? "未填" : tableData[i]['phone'];
        let td4 = document.createElement("td");
        td4.innerHTML = tableData[i]['email'] === undefined ? "未填" : tableData[i]['email'];
        let td5 = document.createElement("td");
        td5.innerHTML = new Date(tableData[i]['createTime']).toLocaleString();
        let td6 = document.createElement("td");
        td6.innerHTML = tableData[i]['admin'] === true ? "是" : "否";
        let td7 = document.createElement("td");
        td7.innerHTML = tableData[i]['enable'] === true ? "是" : "否";
        let td8 = document.createElement("td");
        let btn = document.createElement("button");
        btn.innerHTML = "删除";
        btn.setAttribute("onclick", "clickButton(this, 'delete')");
        btn.setAttribute("data-username", tableData[i]['userName']);
        btn.classList.add("delete-btn");
        let btn2 = document.createElement("button");
        btn2.innerHTML = tableData[i]['enable'] === true ? "禁用" : "启用";
        btn2.setAttribute("onclick", "clickButton(this, 'enable')");
        btn2.setAttribute("data-username", tableData[i]['userName']);
        btn2.classList.add("disable-btn");
        td8.appendChild(btn);
        td8.appendChild(btn2);
        tr.appendChild(checkbox);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
        tr.appendChild(td8);
        tbody.appendChild(tr);
    }
}

function updatePersonalInfoTable() {
    let thead = document.getElementById("table-head");
    let newHead = document.createElement("thead");
    newHead.setAttribute("id", "table-head");
    thead.parentNode.replaceChild(newHead, thead);
    thead = document.getElementById("table-head");
    let th = [];
    for (let i = 0; i < 15; i++) {
        let newTh = document.createElement("th");
        th.push(newTh);
    }
    th[0].innerHTML = "ID";
    th[1].innerHTML = "用户名";
    th[2].innerHTML = "姓名";
    th[3].innerHTML = "电话";
    th[4].innerHTML = "邮箱";
    th[5].innerHTML = "性别";
    th[6].innerHTML = "年龄";
    th[7].innerHTML = "学校";
    th[8].innerHTML = "学院";
    th[9].innerHTML = "专业";
    th[10].innerHTML = "学号";
    th[11].innerHTML = "籍贯";
    th[12].innerHTML = "项目数";
    th[13].innerHTML = "粉丝数";
    th[14].innerHTML = "资产";
    for (let i = 0; i < 15; i++) {
        thead.appendChild(th[i]);
    }

    let tbody = document.getElementById("table-body");
    let newTbody = document.createElement("tbody");
    newTbody.setAttribute("id", "table-body");
    tbody.parentNode.replaceChild(newTbody, tbody);
    tbody = document.getElementById("table-body");

    for (let i = 0; i < tableData.length; i++) {
        let tr = document.createElement("tr");
        let td1 = document.createElement("td");
        td1.innerHTML = tableData[i]['userID'];
        let td2 = document.createElement("td");
        td2.innerHTML = tableData[i]['userName'];
        let td3 = document.createElement("td");
        td3.innerHTML = tableData[i]['phone'] === undefined ? "未填" : tableData[i]['phone'];
        let td4 = document.createElement("td");
        td4.innerHTML = tableData[i]['email'] === undefined ? "未填" : tableData[i]['email'];
        let td5 = document.createElement("td");
        td5.innerHTML = tableData[i]['gender'] === true ? "男" : "女";
        let td6 = document.createElement("td");
        td6.innerHTML = tableData[i]['age'];
        let td7 = document.createElement("td");
        td7.innerHTML = tableData[i]['school'] === undefined ? "未填" : tableData[i]['school'];
        let td8 = document.createElement("td");
        td8.innerHTML = tableData[i]['college'] === undefined ? "未填" : tableData[i]['college'];
        let td9 = document.createElement("td");
        td9.innerHTML = tableData[i]['major'] === undefined ? "未填" : tableData[i]['major'];
        let td10 = document.createElement("td");
        td10.innerHTML = tableData[i]['sno'] === undefined ? "未填" : tableData[i]['sno'];
        let td11 = document.createElement("td");
        td11.innerHTML = tableData[i]['nativePlace'] === undefined ? "未填" : tableData[i]['nativePlace'];
        let td12 = document.createElement("td");
        td12.innerHTML = tableData[i]['projectNum'];
        let td13 = document.createElement("td");
        td13.innerHTML = tableData[i]['fansNum'];
        let td14 = document.createElement("td");
        td14.innerHTML = tableData[i]['assetNum'];
        let td15 = document.createElement("td");
        td15.innerHTML = tableData[i]['name'] === undefined ? "未填" : tableData[i]['name'];
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td15);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
        tr.appendChild(td8);
        tr.appendChild(td9);
        tr.appendChild(td10);
        tr.appendChild(td11);
        tr.appendChild(td12);
        tr.appendChild(td13);
        tr.appendChild(td14);
        tbody.appendChild(tr);
    }
}

function clickMenu(index) {
    if (index === 0) {
        updateTable();
        curSelect = "user";
    } else {
        updatePersonalInfoTable();
        curSelect = "personal";
    }
    let left = document.querySelector(".left");
    for (let i = 0; i < left.children.length; i++) {
        left.children[i].classList.remove("active");
    }
    left.children[index].classList.add("active");
}

function clickButton(button, type) {
    // console.log(button);
    let postData;
    if (button.getAttribute("data-username")) {
        postData = {
            "username": [button.getAttribute("data-username")],
        }
    } else {//说明点击的是删除选定用户按钮
        let checkboxes = document.querySelectorAll(".checkbox");
        let usernames = [];
        // console.log(checkboxes);
        for (let i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                usernames.push(checkboxes[i].getAttribute("data-username"));
            }
        }
        postData = {
            "username": usernames,
        }
        // console.log(usernames);
    }
    request.post("/auth/delete?type=" + type, postData, (code, res) => {
        if (code === 200) {
            if (res['code'] === 200) {
                console.log(res)
                alert(res['msg']);
                // let tr = button.parentNode.parentNode;
                // let table = tr.parentNode;
                // table.removeChild(tr);

                getAllUser(curPage, curPageSize);
            } else {
                alert(res['msg']);
            }
        } else {
            alert("操作失败");
            //网络或服务器错误
            //info.firstChild.nodeValue = "网络或服务器错误，请稍后再试";
        }
    })
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
