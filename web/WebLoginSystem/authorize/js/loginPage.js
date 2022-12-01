window.onload = () => {
    login(true);
    //更新欢迎语
    let date = new Date();
    let hour = date.getHours();
    let welcome = document.querySelector('.left h3');

    if (hour < 6) {
        welcome.innerHTML += "凌晨好";
    } else if (hour < 11) {
        welcome.innerHTML += "上午好";
    } else if (hour < 14) {
        welcome.innerHTML += "中午好";
    } else if (hour < 19) {
        welcome.innerHTML += "下午好";
    } else if (hour < 24) {
        welcome.innerHTML += "晚上好";
    }

    //更新天气
    let city = null;

    let location = document.querySelector('.weather .location span');
    let monthArray = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
        "Sep", "Oct", "Nov", "Dec"];
    let month = monthArray[date.getMonth()];
    let p = document.querySelectorAll('.weather p');
    p[0].innerHTML = "Today, " + date.getDate() + " " + month;
    let moreInfo = document.querySelectorAll('.weather .more-info');
    AMap.plugin('AMap.CitySearch', function () {
        var citySearch = new AMap.CitySearch()
        citySearch.getLocalCity(function (status, result) {
                if (status === 'complete' && result.info === 'OK') {
                    city = result.city;
                    location.innerHTML = result.province + city;
                    // console.log(result)
                    // 查询成功，result即为当前所在城市信息

                    AMap.plugin('AMap.Weather', function () {
                        //创建天气查询实例
                        let weather = new AMap.Weather();

                        //执行实时天气信息查询
                        weather.getLive(city, function (err, data) {
                                p[1].firstChild.innerHTML = data['temperature'];
                                p[2].innerHTML = data['weather'];
                                moreInfo[0].children[1].innerHTML = data['windDirection'] + "风 " + data['windPower'] + "级";
                                moreInfo[1].children[1].innerHTML = data['humidity'] + "%";
                                moreInfo[2].children[1].innerHTML = data['reportTime'].split(" ")[1];
                                let weatherIcon = document.querySelector('.weather .weather-icon img');
                                if (data['weather'].indexOf("风") !== -1) {
                                    weatherIcon.src = "images/weather/风.png";
                                } else if (data['weather'].indexOf("雨") !== -1) {
                                    weatherIcon.src = "images/weather/雨.png";
                                } else if (data['weather'].indexOf("雪") !== -1) {
                                    weatherIcon.src = "images/weather/雪.png";
                                } else if (data['weather'].indexOf("云") !== -1) {
                                    weatherIcon.src = "images/weather/云.png";
                                } else if (data['weather'].indexOf("雾") !== -1) {
                                    weatherIcon.src = "images/weather/雾.png";
                                } else if (data['weather'].indexOf("雷阵雨") !== -1) {
                                    weatherIcon.src = "images/weather/雷阵雨.png";
                                } else if (data['weather'].indexOf("雷") !== -1) {
                                    weatherIcon.src = "images/weather/雷.png";
                                } else if (data['weather'].indexOf("龙卷风") !== -1) {
                                    weatherIcon.src = "images/weather/龙卷风.png";
                                } else {
                                    weatherIcon.src = "images/weather/云.png";
                                }
                                // console.log(err, data);
                            }
                        );

                        //执行未来天气信息查询
                        weather.getForecast(city, function (err, data) {
                            console.log(err, data);
                            let forecast = document.querySelectorAll('.weather-right .weather-item');
                            for (let i = 0; i < 4; i++) {
                                forecast[i].children[0].children[0].children[0].children[1].innerHTML = data['forecasts'][i]['dayWindDir'] + data['forecasts'][i]['dayWindPower'] + "级";
                                forecast[i].children[0].children[0].children[1].children[1].innerHTML = data['forecasts'][i]['dayWeather'];
                                forecast[i].children[0].children[1].children[0].innerHTML = data['forecasts'][i]['date'].substring(5);
                                forecast[i].children[0].children[1].children[1].innerHTML = data['forecasts'][i]['dayTemp'];
                                // forecast[i].children[0].children[0].children[1].children[1].innerHTML = data['forecasts'][i]['dayWeather'];
                                // forecast[i].children[0].innerHTML =
                                //     forecast[i].children[1].innerHTML = data['forecasts'][i]['dayWeather'];
                                // forecast[i].children[2].innerHTML = data['forecasts'][i]['nightTemp'] + "°/" + data['forecasts'][i]['dayTemp'] + "°";
                            }
                        });
                    });
                }
            }
        )
    })
};