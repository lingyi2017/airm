/**
 * 通用函数
 *
 * Created by longxy on 18-06-26.
 */


/**
 * 给设备标注添加事件
 *
 * @param marker
 * @param data
 */
function addEventForDeviceMarker(marker, data) {

    // 鼠标移至
    marker.addEventListener("mouseover", function () {
        openInfoWindow(marker, data);
    });

    // 鼠标移开
    marker.addEventListener("mouseout", function () {
        // 关闭信息窗口
        //marker.closeInfoWindow();
    });

    // 单击显示发射机列表
    marker.addEventListener("click", function () {
        marker.closeInfoWindow();
        getRecordList(data.deviceId);
    });
}

/**
 * 获取发射站的信息窗口
 *
 * @param marker
 * @param data
 */
function openInfoWindow(marker, data) {
    var recordHTM = "";
    var addDate = "";
    $.ajax({
        url: restUrl + "record/latest",
        data: {
            deviceId: data.deviceId
        },
        type: "GET",
        dataType: "JSON",
        async: false,
        success: function (datas) {
            if (datas.success) {
                var header = datas.content.header;
                var data = datas.content.data;
                addDate = data.addDate;
                var sensorVal1 = null == data.sensorVal1 ? 0 : data.sensorVal1;
                var sensorVal2 = null == data.sensorVal2 ? 0 : data.sensorVal2;
                var sensorVal3 = null == data.sensorVal3 ? 0 : data.sensorVal3;
                var sensorVal4 = null == data.sensorVal4 ? 0 : data.sensorVal4;
                var sensorVal5 = null == data.sensorVal5 ? 0 : data.sensorVal5;
                var sensorVal6 = null == data.sensorVal6 ? 0 : data.sensorVal6;
                var sensorVal7 = null == data.sensorVal7 ? 0 : data.sensorVal7;
                var sensorVal8 = null == data.sensorVal8 ? 0 : data.sensorVal8;
                var sensorVal9 = null == data.sensorVal9 ? 0 : data.sensorVal9;
                var sensorVal10 = null == data.sensorVal10 ? 0 : data.sensorVal10;
                var sensorVal11 = null == data.sensorVal11 ? 0 : data.sensorVal11;
                var sensorVal12 = null == data.sensorVal12 ? 0 : data.sensorVal12;
                recordHTM += "<table id='contentTable' class='table' style='width: 105%;margin-top: 5px;'>" +
                    "<tr>" +
                    "<td>" + header.sensorName1 + "</td>" +
                    "<td>" + header.sensorName2 + "</td>" +
                    "<td>" + header.sensorName3 + "</td>" +
                    "<td>" + header.sensorName4 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + sensorVal1 + "</td>" +
                    "<td>" + sensorVal2 + "</td>" +
                    "<td>" + sensorVal3 + "</td>" +
                    "<td>" + sensorVal4 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + header.sensorName5 + "</td>" +
                    "<td>" + header.sensorName6 + "</td>" +
                    "<td>" + header.sensorName7 + "</td>" +
                    "<td>" + header.sensorName8 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + sensorVal5 + "</td>" +
                    "<td>" + sensorVal6 + "</td>" +
                    "<td>" + sensorVal7 + "</td>" +
                    "<td>" + sensorVal8 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + header.sensorName9 + "</td>" +
                    "<td>" + header.sensorName10 + "</td>" +
                    "<td>" + header.sensorName11 + "</td>" +
                    "<td>" + header.sensorName12 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + sensorVal9 + "</td>" +
                    "<td>" + sensorVal10 + "</td>" +
                    "<td>" + sensorVal11 + "</td>" +
                    "<td>" + sensorVal12 + "</td>" +
                    "</tr>" +
                    "</table>";
            }

        }
    });

    var titleHTM = "<span style='margin-right: 170px'>设备最近记录</span>" + addDate;
    var opts = {
        width: 330,     // 信息窗口宽度
        height: 180,     // 信息窗口高度
        title: titleHTM  // 信息窗口标题
    }
    var infoWindow = new BMap.InfoWindow(recordHTM, opts);  // 创建信息窗口对象
    marker.openInfoWindow(infoWindow);
}

/**
 * 设备历史记录
 *
 * @param deviceId
 */
function getRecordList(deviceId) {
    var url = webUrl + "/record/list?deviceId=" + deviceId;
    art.dialog.open(url, {title: '历史记录', width: '70%', height: '75%', lock: true, background: '#C3C2C0', id: 'id'});
}

/**
 * 设备图标
 *
 * @param status
 * @returns {string}
 */
function getDeviceIcon(status) {
    if ('' == status || null == status) {
        return picUrl + 'device_normal.png';
    }
    switch (status) {
        case '1':
            return picUrl + 'device_normal.png';
            break;
        case '2':
            return picUrl + 'device_alarm.png';
            break;
        case '3':
            return picUrl + 'device_offline.png';
            break;
        default:
            break;
    }
}