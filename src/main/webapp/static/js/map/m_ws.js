/**
 * WEB SOCKET相关JS
 *
 * Created by longxy on 18-06-25.
 */

// WEB SOCKET初始化
function wsInit() {
    socket.connect(url);
}

// 创建WEB SOCKET连接
socket.connect = (function (url) {
    // 建立连接
    if ('WebSocket' in window) {
        ws = new WebSocket(url);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(url);
    } else {
        alert("当前浏览器不支持websocket！");
    }
    // 连接成功
    ws.onopen = function () {
        console.log("map WebSocket is Open!");
    };
    // 收到服务端消息
    ws.onmessage = function (event) {
        var datas = $.parseJSON(event.data);

        console.log("==收到客户端消息==" + datas);

        // 服务端返回数据为空不执行
        if (datas.length == 0) {
            return;
        }

        //cityUI(datas);
    };

    // 关闭连接
    ws.onclose = function () {
        ws.close();
        console.log("map WebSocket closed!");
    };
    // 异常
    ws.onerror = function () {
        console.log("map WebSocket occurs Error!");
    };
});

/**
 * 市界面地图展示
 *
 * @param datas
 */
function cityUI(datas) {
    var id;
    var status;
    $.each(datas, function (i) {
        if (i >= 2) {  // 第一组数据为区域类型，第二组数据为区域名称
            id = datas[i].id;
            status = datas[i].status;
            if (city.isInit == false) {  // 标注未初始化
                addStationMarker4WS(datas[i], status);
            } else if (city.isInit == true) {  // 已初始化
                if ((city.statusMap[id] == '1' && status == '0') ||
                    (city.statusMap[id] == '0' && status == '1')) {  // 之前告警但当前已经恢复或者之前没有告警现在有告警
                    // 更新标注
                    map.removeOverlay(city.markerMap[id]);
                    addStationMarker4WS(datas[i], status);
                }
            }
        } else if (!city.isInit && i == 1) {
            updateHistoryParam(historyParam.areaName, datas[i].cityName);
        }
    });
}

/**
 * 添加发射站标注
 *
 * @param data
 * @param status
 */
function addStationMarker4WS(data, status) {
    var point = new BMap.Point(data.lon, data.lat);
    var myIcon;
    if (status == '2') {  // 在地图上直接添加的标注
        myIcon = new BMap.Icon(getFszIcon(status), new BMap.Size(60, 30));
    } else {
        myIcon = new BMap.Icon(getFszIcon(status), new BMap.Size(30, 30));
    }

    var marker = new BMap.Marker(point, {icon: myIcon}); // 创建标注
    marker.disableMassClear();
    //marker.enableDragging();  // 开启拖拽

    map.addOverlay(marker); // 将标注添加到地图中

    initInfoWindow(marker, data);  // 初始化信息窗口

    addEventForStationMarker(marker, data);  // 给发射站标注添加事件

    city.statusMap[data.id] = status;  // 保存状态
    city.markerMap[data.id] = marker;  // 保存标注
}

/**
 * 初始化信息窗口
 * 说明：用于解决第一次点击信息窗口滚动控件失效的问题
 *
 * @param marker
 * @param data
 */
function initInfoWindow(marker, data) {
    var html = '<div class="info">' +
        '<div class="content_alertInfo mCustomScrollbar light" data-mcs-theme="minimal-dark">' +
        '<ul class="select_info"></ul></div></div>';
    new BMap.InfoWindow(html);  // 创建信息窗口对象
    $(".content_alertInfo").mCustomScrollbar();  // 激活滚动条控件
    $('#mCSB_1').css("max-height", "351.5px");
}

/**
 * 清除全市界面数据
 *
 */
function clearCityUI() {
    if (city.isDel == false) {  // 市界面删除标注
        for (var key in city.markerMap) {
            var maker = city.markerMap[key];
            map.removeOverlay(maker);
        }
        city.isDel = true;
        city.isInit = false;  // 市界面初始化标记复位
    }
}