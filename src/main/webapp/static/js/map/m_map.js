/**
 * 地图相关
 *
 * Created by lxy on 18-06-25.
 */

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