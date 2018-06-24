/**
 * 全局变量
 * 
 * Created by lxy on 15-12-21.
 */

/**********************************路径常量*******************************************/
var baseUrl = "/fsj";  // 基础路径
var picUrl = baseUrl + '/static/pictures/map/';  // 图片基本路径
var restUrl = baseUrl + '/rs/map/';  // 接口基本路径


/**********************************通用参数*******************************************/
var requestParam;  // 请求参数
var userId;  // 用户Id
var userAreaId;  // 用户区域id
var userAreaType;  // 用户区域类型
var userAreaName;  // 用户区域名称
var userAreaZoom;  // 用户区域缩放级别
var areaId;  // 当前区域id 
var proAreaId;  // 省区域id
var areaType;  // 区域类型
var isOpenHistory;  // 是否开启地图历史状态功能。0：不开启；1：开启；
var cyBoundary = 6;  // 全国<->省 界面边界线
var prBoundary = 9;  // 省<->市 界面边界线
var isOpen = true;  // WEBSOCKET是否开启，默认开启
var initIndex = 0;  // 地图数据初始化索引。如果区域没有告警，让其连续绘制两次地图，主要用于解决绘制外边界后，边界内的行政区域无法点击的bug。
var markerDray;  // 标注拖动控件

/**********************************WEBSOCKET相关**************************************/
var url = GLOBAL.url.socket + "map";
var socket = {};
var ws;

/************************************MAP常量*****************************************/
// 区域类型
var area = {
	country : "1",
	province : "2",
	city : "3"
};

// 全国界面参数定义
var country = {
	statusMap : {},  // 告警状态 map(key-省id; value-省告警状态)
	plyMap : {},  // 覆盖物 map(key-省id; value-省覆盖物数组)
	isInit : false,  // 覆盖物初始化标记(默认未初始化)
	isDel : false  // 覆盖物删除标记(未删除)
};

// 省、直辖市界面参数定义
var province = {
	statusMap : {},  // 告警状态 map(key-市id; value-省告警状态)
	plyMap : {},  // 覆盖物 map(key-市id; value-市覆盖物数组)
	isInit : false,  // 覆盖物初始化标记(默认未初始化)
	isDel : false  // 覆盖物删除标记(未删除)
};

// 市界面参数定义
var city = {
	statusMap : {},  // 告警状态 map(key-发射站id; value-发射站告警状态)
	markerMap : {},  // 标注map(key-发射站id; value-发射站标注)
	isInit : false,  // 标注初始化标记(默认未初始化)
	isDel : false  // 覆盖物删除标记(未删除)
};

// 发射站参数定义
var station = {
	devMarkerIdMap : {},  // 设备标注id map(key-发射站id; value-设备id数组)	
	devMarkerStatusMap : {}  // 设备标注状态map(key-设备id; value-设备状态[0:关闭；1:打开])	
};

// 行政区域边界参数定义
var boundary = {
	isInit : false,  // 边界初始化标记(默认未初始化)
	plyArr : []  // 多变形数组(默认未null) 
};

// 查询界面参数定义
var query = {
	markerMap : {},  // 标注map(key-设备id; value-设备标注)
	infoWindowMap : {}  // 信息窗口map(key-设备id; value-设备信息窗口)
};

// 历史参数定义
var historyParam = {
	areaId : "1",
	areaName : "2",
	areaType : "3",
	zoom : "4"
};