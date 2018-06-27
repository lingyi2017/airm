/**
 * 全局变量
 *
 * Created by lxy on 18-06-25.
 */

/**********************************路径常量*******************************************/
var baseUrl = "/airm";  // 基础路径
var picUrl = baseUrl + "/static/pictures/map/";  // 图片基本路径
var restUrl = baseUrl + "/rs/map/";  // 接口基本路径
var webUrl = baseUrl + "/airm/map";  // controller基本路径


/**********************************通用参数*******************************************/

/**********************************WEB SOCKET相关**************************************/
var url = GLOBAL.url.socket + "map";
var socket = {};
var ws;

/************************************MAP常量*****************************************/
// 设备参数定义
var device = {
    statusMap: {},  // 设备状态map(key-设备id; value-设备状态)
    markerMap: {}  // 标注map(key-设备id; value-设备标注)
};