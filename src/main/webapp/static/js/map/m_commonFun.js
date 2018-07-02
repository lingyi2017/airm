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
                recordHTM += "<table id='contentTable' class='table' style='width: 110%;margin-top: 5px;'>" +
                    "<tr>" +
                    "<td>" + header.sensorName1 + "</td>" +
                    "<td>" + header.sensorName2 + "</td>" +
                    "<td>" + header.sensorName3 + "</td>" +
                    "<td>" + header.sensorName4 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + data.sensorVal1 + "</td>" +
                    "<td>" + data.sensorVal2 + "</td>" +
                    "<td>" + data.sensorVal3 + "</td>" +
                    "<td>" + data.sensorVal4 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + header.sensorName5 + "</td>" +
                    "<td>" + header.sensorName6 + "</td>" +
                    "<td>" + header.sensorName7 + "</td>" +
                    "<td>" + header.sensorName8 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + data.sensorVal5 + "</td>" +
                    "<td>" + data.sensorVal6 + "</td>" +
                    "<td>" + data.sensorVal7 + "</td>" +
                    "<td>" + data.sensorVal8 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + header.sensorName9 + "</td>" +
                    "<td>" + header.sensorName10 + "</td>" +
                    "<td>" + header.sensorName11 + "</td>" +
                    "<td>" + header.sensorName12 + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>" + data.sensorVal9 + "</td>" +
                    "<td>" + data.sensorVal10 + "</td>" +
                    "<td>" + data.sensorVal11 + "</td>" +
                    "<td>" + data.sensorVal12 + "</td>" +
                    "</tr>" +
                    "</table>";
            }

        }
    });

    var titleHTM = "<span style='margin-right: 115px'>设备最近记录</span>" + addDate;
    var opts = {
        width: 280,     // 信息窗口宽度
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
    if('' == status || null == status){
        return picUrl + 'device_normal.png';
    }
    switch (status) {
        case '1':
            return picUrl + 'device_normal.png';
            break;
        case '2':
            return picUrl + 'device_normal.png';
            break;
        case '3':
            return picUrl + 'device_normal.png';
            break;
        default:
            break;
    }
}