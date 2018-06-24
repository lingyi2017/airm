<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="${ctx}/static/styles/map/jquery.mCustomScrollbar.css">
    <link rel="stylesheet" href="${ctx}/static/styles/map/map.css">
    <script src="${ctx}/static/jquery/jquery-1.11.0.js"></script>
    <script src="${ctx}/static/scripts/global.js"></script>
    <script src="${ctx}/static/artDialog/artDialog.js?skin=blue"></script>
    <script src="${ctx}/static/artDialog/plugins/iframeTools.js"></script>
    <script type="text/javascript" src="${map.mapUrl}" charset="utf-8"></script>
    <style type="text/css">
        body, html, #allmap {
            width: 100%;
            height: 100%;
            overflow: hidden;
            margin: 0;
            font-family: "微软雅黑";
        }
    </style>
</head>

<body>
<div class="left_div">
    <div class="select">
        <ul id="select_title" class="select_title">
            <li class="select_title_li ">
                <div class="icon_content quyu_icon"><img class="quyu_img" src="${ctx }/static/pictures/map/quyu.png"><a
                        id="select_quyu" class="a_click" name="1">区域</a></div>
            </li>
            <li class="select_title_li title_click">
                <div class="icon_content shebei_icon"><img class="shebei_img"
                                                           src="${ctx }/static/pictures/map/keyword2.png"><a
                        id="select_shebei" name="0">设备</a></div>
            </li>
            <div class="arrow-wrap"></div>
        </ul>
        <div class="select_content">
            <input id="select_by" class="select_by_css focus" type="text" autocomplete="off" placeholder="请输入区域名称"
                   maxlength="16"/>
            <div class="input_clear"></div>
        </div>
        <ul id="select_result" class="select_result">
        </ul>
    </div>
    <div class="content_result ">
        <div class="content_result_body mCustomScrollbar light" data-mcs-theme="minimal-dark">
            <ul id="select_content_result" class="select_content_result ">
            </ul>
        </div>
    </div>
</div>
<div class="alert_info">
    <div class="alert_arrow-wrap"></div>
    <div class="content">
        <span>清除查询</span>
    </div>
</div>

<div id="allmap"></div>

<div style="width: 30px; height: 30px;"></div>

<script type="text/javascript" defer>
    // 地图初始化
    var map = new BMap.Map("allmap");
    map.centerAndZoom("${map.mapCenter}", ${map.mapZoom});

    // 页面加载完执行
    $(function () {
        //mapInit();  // 鼠标右键和控件初始化
        //wsInit();  // WEBSOCKET初始化
    });
</script>

<script src="${ctx}/static/scripts/map/jquery.mCustomScrollbar.concat.min.js"></script>
</body>
</html>