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

    });

    // 单击显示发射机列表
    /*marker.addEventListener("click", function(){
     getInfoWindow(marker, data);  // 添加信息窗口
     });*/

    // 右键菜单
    //markerRightMenu(marker, data);
}

/**
 * 获取发射站的信息窗口
 *
 * @param marker
 * @param data
 */
function openInfoWindow(marker, data) {
    var recordHTM = "";
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

    var opts = {
        width: 280,     // 信息窗口宽度
        height: 180,     // 信息窗口高度
        title: "设备最近记录"  // 信息窗口标题
    }
    var infoWindow = new BMap.InfoWindow(recordHTM, opts);  // 创建信息窗口对象
    marker.openInfoWindow(infoWindow);
}

/**
 * 设备图标
 *
 * @param status
 * @returns {string}
 */
function getDeviceIcon(status) {
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