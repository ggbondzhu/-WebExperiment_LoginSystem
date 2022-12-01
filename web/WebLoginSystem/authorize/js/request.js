class Request {
    constructor() {
        this.xhr = new XMLHttpRequest();
        this.baseURL = "http://localhost:8080";
        this.xhr.withCredentials = true;    //允许跨域携带cookie
    }

    get(url, callback) {
        this.xhr.open("GET", this.baseURL + url);

        //响应结束
        this.xhr.onload = () => {
            callback(this.xhr.status, JSON.parse(this.xhr.responseText));
        }

        this.xhr.send();
    }

    post(url, data, callback) {
        this.xhr.open("POST", this.baseURL + url);
        //将返回的数据转换为json
        this.xhr.onload = () => {
            callback(this.xhr.status, JSON.parse(this.xhr.responseText));
        }
        this.xhr.send(JSON.stringify(data));
    }
}

class RequestByte {
    constructor() {
        this.xhr = new XMLHttpRequest();
        this.baseURL = "http://localhost:8080";
        this.xhr.withCredentials = true;    //允许跨域携带cookie
    }

    postByte(url, data, callback) {
        this.xhr.open("POST", this.baseURL + url);
        this.xhr.responseType = "text";
        //将返回的数据转换为json
        this.xhr.onload = () => {
            callback(this.xhr.status, JSON.parse(this.xhr.responseText));
        }
        this.xhr.send(data);
    }

    getByte(url, callback) {
        this.xhr.open("GET", this.baseURL + url);
        this.xhr.responseType = "blob";
        //响应结束
        this.xhr.onload = () => {
            callback(this.xhr.status, this.xhr.response);
        }

        this.xhr.send();
    }
}

function createInstance() {
    return new Request();
}

let request = createInstance();
let requestByte = new RequestByte();