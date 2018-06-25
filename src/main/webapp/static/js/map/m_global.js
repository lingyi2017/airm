/**
 * 全局变量
 * 
 * Created by lxy on 18-06-25.
 */

/**********************************路径常量*******************************************/
var baseUrl = "/airm";  // 基础路径
var picUrl = baseUrl + '/static/pictures/map/';  // 图片基本路径
var restUrl = baseUrl + '/rs/map/';  // 接口基本路径


/**********************************通用参数*******************************************/

/**********************************WEB SOCKET相关**************************************/
var url = GLOBAL.url.socket + "map";
var socket = {};
var ws;

/************************************MAP常量*****************************************/
// 发射站参数定义
var station = {
	devMarkerIdMap : {},  // 设备标注id map(key-发射站id; value-设备id数组)	
	devMarkerStatusMap : {}  // 设备标注状态map(key-设备id; value-设备状态[0:关闭；1:打开])	
};

// 查询界面参数定义
var query = {
	markerMap : {},  // 标注map(key-设备id; value-设备标注)
	infoWindowMap : {}  // 信息窗口map(key-设备id; value-设备信息窗口)
};