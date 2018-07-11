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
        var pushData = $.parseJSON(event.data);
        var type = pushData.type;
        if ("1" == type) {  // 初始化
            var datas = pushData.devices;
            if (datas.length == 0) {
                return;
            }
            initDeviceMarker(datas);
        } else if ("2" == type) {   // 告警
            var data = pushData.device;
            var marker = device.markerMap[data.deviceId];
            var newIcon = new BMap.Icon(getDeviceIcon(data.status), new BMap.Size(30, 60));
            marker.setIcon(newIcon);
        }
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
 * 初始化设备标注
 *
 * @param datas
 */
function initDeviceMarker(datas) {
    $.each(datas, function (i) {
        addDeviceMarker(datas[i]);
    });
}

/**
 * 添加设备标注
 *
 * @param data
 */
function addDeviceMarker(data) {
    if (undefined == data.lon || undefined == data.lat) {
        return;
    }
    var point = new BMap.Point(data.lon, data.lat);
    var myIcon = new BMap.Icon(getDeviceIcon(data.status), new BMap.Size(30, 60));

    // 创建标注
    var marker = new BMap.Marker(point, {icon: myIcon});
    var x = 10;
    var aqi = data.aqi;
    if (10 <= aqi && aqi < 100) {
        x = 7;
    } else if (100 <= aqi && aqi < 1000) {
        x = 4;
    }
    var label = new BMap.Label(aqi, {
        offset: new BMap.Size(x, 20)
    });
    label.setStyle({
        background: 'none', color: '#fff', border: 'none'
    });
    marker.setLabel(label)
    // 将标注添加到地图中
    map.addOverlay(marker);

    // 给发射站标注添加事件
    addEventForDeviceMarker(marker, data);

    device.statusMap[data.deviceId] = data.status;  // 保存状态
    device.markerMap[data.deviceId] = marker;  // 保存标注
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