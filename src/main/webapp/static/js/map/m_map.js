/**
 * 地图相关
 *
 * Created by lxy on 15-12-15.
 */

/**
 * 地图相关初始化
 *
 */
function mapInit() {
    rightMenu();
    addControl();
}

/**
 * 右键菜单
 *
 */
function rightMenu() {
    var menu = new BMap.ContextMenu();
    var txtMenuItem = [
        {
            text: '<div style="font-size: 18px;">返回全景</div>',
            callback: function () {  // 点击右键后触发的事件
                map.clearOverlays(); // 清除地图覆盖物
                map.centerAndZoom(userAreaName, userAreaZoom);
                reloadMarkerDrayControl();  // 重新加载标注拖动插件
                initIndex = 0;
                if (userAreaType == area.city) {
                    clearCityUI();
                }
                if (!isOpen) {  // WEBSOCKET已经断开
                    socket.connect(url);
                    isOpen = true;
                }
            }
        }
    ];
    for (var i = 0; i < txtMenuItem.length; i++) {
        menu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
    }
    map.addContextMenu(menu);
}

/**
 * 添加地图控件
 *
 */
function addControl() {
    var mapType = new BMap.MapTypeControl({
        mapTypes: [BMAP_NORMAL_MAP, BMAP_SATELLITE_MAP],
        anchor: BMAP_ANCHOR_BOTTOM_RIGHT, offset: new BMap.Size(25, 0)
    });
    var overView = new BMap.OverviewMapControl({offset: new BMap.Size(103, 0)});
    var stationAdd = new StationAddControl();
    markerDray = new MarkerDrayControl();
    map.addControl(mapType);  // 地图类型控件
    map.addControl(overView);  // 缩略地图控件
    map.addControl(stationAdd);  // 发射站添加控件
    map.addControl(markerDray);  // 标注拖动控件
}

/**
 * 添加发射站
 *
 */
/*var drawingManagerObject = new BMapLib.DrawingManager(map, {
 isOpen: false,
 enableDrawingTool: true,
 drawingType: BMAP_DRAWING_MARKER,
 enableCalculate: false,
 drawingToolOptions: {
 anchor: BMAP_ANCHOR_TOP_RIGHT,
 offset: new BMap.Size(5, 5),
 drawingModes : [
 BMAP_DRAWING_MARKER
 ]
 },
 polylineOptions: {
 strokeColor: "#333"
 }
 });*/

/**
 * 画点事件结束
 *
 */
/*drawingManagerObject.addEventListener("markercomplete", function(e, marker) {
 addStationUI(marker);
 map.removeOverlay(marker);  // 删除标注
 });*/

/**
 * 添加发射站界面
 *
 * @param marker
 */
function addStationUI(marker) {
    var position = marker.getPosition();
    var lon = position.lng;  // 经度
    var lat = position.lat;  // 纬度
    var geoc = new BMap.Geocoder();  // 逆地址解析
    geoc.getLocation(position, function (rs) {
        // 两次编码
        var areaName = encodeURI(encodeURI(rs.addressComponents.district));  // 区域
        var addr = encodeURI(encodeURI(rs.address));  // 地址
        var url = baseUrl + "/map/station/form?lon=" + lon + "&lat=" + lat + "&address=" + addr + "&areaName=" + areaName;
        art.dialog.open(url, {
            title: '添加发射站',
            width: '60%',
            height:'65%',
            lock: true,
            background: '#C3C2C0',
            id: 'id'
        });
    });
}

//------------------导航栏展示内容的放缩操作----------------------------------------
function click_close_open() {
    var left = document.getElementById("left");
    var close_open = document.getElementById("close_open");
    var allmap = document.getElementById("allmap");
    var input_close_open = document.getElementById("input_close_open");

    if (left.clientWidth == "350") {
        left.style.width = "0px";
        left.style.transitionDelay = "100ms";
        left.style.transitionDuration = "500ms";
        left.style.transitionTimingFunction = "linear";
        left.style.transitionProperty = "width";
        close_open.style.left = "0px";
        close_open.style.transitionDelay = "100ms";
        close_open.style.transitionDuration = "500ms";
        close_open.style.transitionTimingFunction = "linear";
        close_open.style.transitionProperty = "left";
        allmap.style.margin = "0";
        allmap.style.transitionDelay = "100ms";
        allmap.style.transitionDuration = "500ms";
        allmap.style.transitionTimingFunction = "linear";
        allmap.style.transitionProperty = "margin";
        input_close_open.setAttribute("value", ">");
    } else {
        left.style.width = "350px";
        left.style.transitionDelay = "0ms";
        left.style.transitionDuration = "500ms";
        left.style.transitionProperty = "width";
        close_open.style.left = "350px";
        close_open.style.transitionDelay = "0ms";
        close_open.style.transitionDuration = "500ms";
        close_open.style.transitionTimingFunction = "linear";
        close_open.style.transitionProperty = "left";
        allmap.style.margin = "0 0 0 350px";
        allmap.style.transitionDelay = "0ms";
        allmap.style.transitionDuration = "500ms";
        allmap.style.transitionProperty = "margin";
        input_close_open.setAttribute("value", "<");
    }
}

//--------点击导航栏对应层的显示与影藏的操作----------------------------
var nav1 = document.getElementById("nav1");
var nav2 = document.getElementById("nav2");
var nav3 = document.getElementById("nav3");
var nav1_quyu = document.getElementById("nav1_quyu");
var nav2_shebeimingc = document.getElementById("nav2_shebeimingc");
var nav3_zhengjia = document.getElementById("nav3_zhengjia");

function show_nav1() {
    nav1.style.visibility = "visible";
    nav2.style.visibility = "hidden";
    nav3.style.visibility = "hidden";
    nav1_quyu.style.backgroundColor = "#eee";
    nav2_shebeimingc.style.backgroundColor = "#fff";
    nav3_zhengjia.style.backgroundColor = "#fff";
}
function show_nav2() {
    // 关闭区域选择框
    var list = art.dialog.list;
    for (var i in list) {
        list[i].close();
    }
    ;
    nav1.style.visibility = "hidden";
    nav2.style.visibility = "visible";
    nav3.style.visibility = "hidden";
    nav1_quyu.style.backgroundColor = "#fff";
    nav2_shebeimingc.style.backgroundColor = "#eee";
    nav3_zhengjia.style.backgroundColor = "#fff";
}
function show_nav3() {
    // 关闭区域选择框
    var list = art.dialog.list;
    for (var i in list) {
        list[i].close();
    }
    ;
    nav1.style.visibility = "hidden";
    nav2.style.visibility = "hidden";
    nav3.style.visibility = "visible";
    nav1_quyu.style.backgroundColor = "#fff";
    nav2_shebeimingc.style.backgroundColor = "#fff";
    nav3_zhengjia.style.backgroundColor = "#eee";
}
//---------------------------版面布局动画结束------------------------------------