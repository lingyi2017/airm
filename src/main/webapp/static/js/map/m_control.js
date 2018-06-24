/**
 * 自定义控件
 *
 * Created by longxy on 16-09-28.
 */

/**
 * 发射站添加控件
 *
 */
function StationAddControl() {
    // 默认停靠位置和偏移量
    this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
    this.defaultOffset = new BMap.Size(75, 20);
}

// 通过JavaScript的prototype属性继承于BMap.Control
StationAddControl.prototype = new BMap.Control();

// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
StationAddControl.prototype.initialize = function (map) {
    // 创建一个DOM元素
    var div = document.createElement("div");

    // 添加操作图标
    div.innerHTML = "<div style='width: 50px; height: 40px; text-align: center; cursor:pointer; float: left;' title='退出添加'><img id='c_station_add_quit' style='padding-top: 7px;' src='" + picUrl + "quit_add1.png'/></div>" +
        "<div style='width: 1px; height: 26px; margin-top: 7px; background-color: gainsboro; float: left;'></div>" +
        "<div style='width: 50px; height: 40px; text-align: center; cursor:pointer; float: left;' title='添加站点'><img id='c_station_add' style='padding-top: 7px;' src='" + picUrl + "add2.png'/></div>";
    // 设置样式
    div.style.width = "101px";
    div.style.height = "40px";
    div.style.backgroundColor = "white";
    div.style.boxShadow = "1px 2px 1px rgba(0,0,0,.15)";

    // 添加DOM元素到地图中
    map.getContainer().appendChild(div);

    // 绑定事件
    var add_flag = 0;  // 0：退出；1：添加

    // 退出图标相关事件
    $("#c_station_add_quit").mouseover(function () {  // 鼠标移至
        if (add_flag == 1) {
            $("#c_station_add_quit").attr("src", picUrl + "quit_add1.png");
        }
    });
    $("#c_station_add_quit").mouseout(function () {  // 鼠标移开
        if (add_flag == 1) {
            $("#c_station_add_quit").attr("src", picUrl + "quit_add2.png");
        }
    });
    $("#c_station_add_quit").click(function () {  // 点击
        if (add_flag == 1) {
            $("#c_station_add_quit").attr("src", picUrl + "quit_add1.png");
            $("#c_station_add").attr("src", picUrl + "add2.png");
            map.setDefaultCursor("pointers");
            map.removeEventListener("click", c_station_add);
            add_flag = 0;
        }
    });

    // 添加图标相关事件
    $("#c_station_add").mouseover(function () {  // 鼠标移至
        if (add_flag == 0) {
            $("#c_station_add").attr("src", picUrl + "add.png");
        }
    });
    $("#c_station_add").mouseout(function () {  // 鼠标移开
        if (add_flag == 0) {
            $("#c_station_add").attr("src", picUrl + "add2.png");
        }
    });
    $("#c_station_add").click(function () {  // 点击
        if (add_flag == 0) {
            $("#c_station_add_quit").attr("src", picUrl + "quit_add2.png");
            $("#c_station_add").attr("src", picUrl + "add.png");
            map.setDefaultCursor("crosshair");
            map.addEventListener("click", c_station_add);
            add_flag = 1;
        }
    });

    function c_station_add(e) {
        var pt = e.point;
        var marker = new BMap.Marker(pt);
        addStationUI(marker);
        //map.removeOverlay(marker);  // 删除标注
    }

    // 将DOM元素返回
    return div;
};

/**
 * 标注拖动控件
 *
 */
function MarkerDrayControl() {
    // 默认停靠位置和偏移量
    this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
    this.defaultOffset = new BMap.Size(20, 20);
}

// 通过JavaScript的prototype属性继承于BMap.Control
MarkerDrayControl.prototype = new BMap.Control();

// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
MarkerDrayControl.prototype.initialize = function (map) {
    // 创建一个DOM元素
    var div = document.createElement("div");
    // 添加文字说明
    //div.appendChild(document.createTextNode("<p id='markerDrayCtrl'>启用标注拖动</p>"));
    div.innerHTML = "<div style='width: 50px; height: 40px; text-align: center; cursor:pointer;' title='启用标注拖动'><img id='c_marker_dray_ctrl' class='c_disable' style='padding-top: 7px;' src='" + picUrl + "undrag.png'/></div>";
    // 设置样式
    div.style.width = "50px";
    div.style.height = "40px";
    div.style.backgroundColor = "white";
    div.style.boxShadow = "1px 2px 1px rgba(0,0,0,.15)";

    // 绑定事件
    /*div.onclick = function(e){
     var val = document.getElementById("markerDrayCtrl").value;
     var markerMap = city.markerMap;
     var marker;
     if(val == 'disable'){  // 禁用拖动
     div.innerHTML="<div style='width: 30px; height: 30px;' title='启用标注拖动'><input id='markerDrayCtrl' value='enable' style='display:none;' /></div>";
     //div.style.backgroundImage = "url(" + picUrl + "fsz_alarm.gif)";

     for(var key in markerMap){
     marker = markerMap[key];
     marker.disableDragging();
     }
     }else{  // 启用拖动
     div.innerHTML="<div style='width: 30px; height: 30px;' title='禁用标注拖动'><input id='markerDrayCtrl' value='disable' style='display:none;' /></div>";
     //div.style.backgroundImage = "url(" + picUrl + "fsz_normal.png)";

     for(var key in markerMap){
     marker = markerMap[key];
     marker.enableDragging();
     }
     }
     };*/
    // 添加DOM元素到地图中
    map.getContainer().appendChild(div);

    // 绑定事件
    var cls = $("#c_marker_dray_ctrl").attr("class");
    $("#c_marker_dray_ctrl").mouseover(function () {  // 鼠标移至
        if (cls == 'c_disable') {  // 之前禁用，现在启用
            $("#c_marker_dray_ctrl").attr("src", picUrl + "drag.png");
        } else if (cls == 'c_enable') {
            $("#c_marker_dray_ctrl").attr("src", picUrl + "undrag.png");
        }
    });
    $("#c_marker_dray_ctrl").mouseout(function () {  // 鼠标移开
        if (cls == 'c_disable') {  // 之前禁用
            $("#c_marker_dray_ctrl").attr("src", picUrl + "undrag.png");
        } else if (cls == 'c_enable') {
            $("#c_marker_dray_ctrl").attr("src", picUrl + "drag.png");
        }
    });
    $("#c_marker_dray_ctrl").click(function () {  // 点击
        var markerMap = city.markerMap;
        var marker;
        if (cls == 'c_disable') {  // 之前禁用，现在启用
            $("#c_marker_dray_ctrl").removeClass("c_disable");
            $("#c_marker_dray_ctrl").addClass("c_enable");
            $("#c_marker_dray_ctrl").parent().attr("title", "禁用标注拖动");
            $("#c_marker_dray_ctrl").attr("src", picUrl + "drag.png");

            for (var key in markerMap) {
                marker = markerMap[key];
                marker.enableDragging();
            }

            cls = $("#c_marker_dray_ctrl").attr("class");
        } else if (cls == 'c_enable') {  //  之前启用，现在禁用
            $("#c_marker_dray_ctrl").removeClass("c_enable");
            $("#c_marker_dray_ctrl").addClass("c_disable");
            $("#c_marker_dray_ctrl").parent().attr("title", "启用标注拖动");
            $("#c_marker_dray_ctrl").attr("src", picUrl + "undrag.png");

            for (var key in markerMap) {
                marker = markerMap[key];
                marker.disableDragging();
            }

            cls = $("#c_marker_dray_ctrl").attr("class");
        }
    });

    // 将DOM元素返回
    return div;
};