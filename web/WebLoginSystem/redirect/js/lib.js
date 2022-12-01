function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substring(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

window.onload = () => {
    if (GetQueryString("code") === '404') {
        document.querySelector(".content h1").innerHTML = "404";
        document.querySelector(".content h2").innerHTML = "未找到资源";
    } else {
        let id = GetQueryString("id");
        let tipInfo = document.querySelectorAll(".tip-info span");
        // console.log(tipInfo);
        let num = 5;
        let targetUrl = GetQueryString("target");
        if (targetUrl === null) {
            targetUrl = "../authorize/index.html";
        }
        if (id === "alreadyLogin") {
            tipInfo[0].innerHTML = "您已登录，将在";
            tipInfo[2].innerHTML = "后跳转到个人主页/管理员主页";
        } else if (id === "resetPwd") {
            tipInfo[0].innerHTML = "密码重置成功，将在";
            tipInfo[2].innerHTML = "后跳转到登录页面";
        } else if (id === "notLogin") {
            tipInfo[0].innerHTML = "您没有登录或者登录已过期，将在";
            tipInfo[2].innerHTML = "后跳转到登录页面";
        } else if (id === "delete") {
            tipInfo[0].innerHTML = "账号已注销成功，将在";
            tipInfo[2].innerHTML = "后跳转到登录页面";
        }
        setInterval(() => {
            if (num <= 0) {
                window.location.href = targetUrl;
            } else
                tipInfo[1].innerHTML = num-- + "秒";
        }, 1000);
    }

}