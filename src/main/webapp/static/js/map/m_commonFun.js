/**
 * 通用函数
 * 
 * Created by lxy on 15-12-21.
 */

/**
 * 给发射站标注添加事件
 * 
 * @param marker
 * @param data
 */
function addEventForStationMarker(marker, data){
	var name = data.name;  // 发射站名称
	// 添加标签
	addLabel(name, marker);
	
	var oldIcon = marker.getIcon();
	// 鼠标移至
	marker.addEventListener("mouseover", function(){
		var label = marker.getLabel();
		label.setStyle({
		    display:"block"  // 显示标签
		});
		
		var newIcon = new BMap.Icon(getFszIcon("3"), new BMap.Size(30, 30));
		marker.setIcon(newIcon);
    });
	
	// 鼠标移开
	marker.addEventListener("mouseout", function(){
		var label = marker.getLabel();
		label.setStyle({
		    display:"none"  // 隐藏标签
		});
		marker.setIcon(oldIcon);
    });
	
	// 单击显示发射机列表
	marker.addEventListener("click", function(){
		getInfoWindow(marker, data);  // 添加信息窗口
    });
	
	// 右键菜单
	markerRightMenu(marker, data);
}

/**
 * 获取发射站的信息窗口
 * 
 * @param marker
 * @param data
 */
function getInfoWindow(marker, data){
	var html ='<div class="info">' +
    '<div class="content_alertInfo mCustomScrollbar light" data-mcs-theme="minimal-dark">' +
    '<ul class="select_info">';
	$.ajax({
        url : restUrl + "station/transmitter",
        data : {
        	sid : data.id
        },
        type : "GET",
        dataType: "JSON",
        async : false,
        success : function(datas) {
        	if(datas.length != 0){
        		$.each(datas, function(i) {
                    html += '<li id="li_' + datas[i].id + '" style="' + getDevBorderStyle(datas[i].status) + '" class="select_li select_result_li_alertInfo select_action_alertInfo">' +
                        '<ul class="select_ul_alertInfo">' +
                        '<li class="result_content_alertInfo">' +
                        '<ul class="result_content_body_alertInfo clear">' +
                        '<li class="result_body_alertInfo">' + datas[i].name + '</li>' +
                        '<li class="result_body_alertInfo"><a class="result_action_alertInfo" onclick="editDevInfo(' + '\'' + datas[i].id + '\'' + ',' + '\'' + data.name + '\'' + ')">修改</a>' + 
                        '<a class="result_action_alertInfo" onclick="delDev(' + '\'' + datas[i].id + '\'' + ',' + '\'' + data.id + '\'' + ')">删除</a></li>' +
                        '</ul>' +
                        '</li>' +
                        '<li class="result_content_alertInfo">' +
                        '<ul class="result_content_body_alertInfo clear">' +
                        '<li  class="result_body_alertInfo">发射功率(W)：<span class="s_color" id="po_' + datas[i].id + '">' + datas[i].masterPo + '</span></li>'+
                        '<li  class="result_body_alertInfo">反射功率(dBm)：<span class="s_color" id="pr_' + datas[i].id + '">' + datas[i].masterPr +'</span></li>'+
                        '</ul>' +
                        '</li>' +
                        '<li class="result_content_alertInfo">' +
                        '<ul class="result_content_body_alertInfo clear">'+
                        '<li class="result_body_alertInfo">驻波比：<span class="s_color" id="vswr_' + datas[i].id + '">' + datas[i].masterVswr + '</span></li>'+
                        '<li class="result_body_alertInfo"><a class="result_action_alertInfo a_css" onclick="getDevAlarm(' + '\'' + datas[i].id + '\'' + ')">告警</a>' +
                        '<a class="result_action_alertInfo a_css last_a" onclick="getDevMonitor(' + '\'' + datas[i].id + '\'' + ')">监控</a></li>' +
                        '</ul>'+
                        '</li>' +
                        '</ul>' +
                        '</li>';
            	});
        	}else{
        		html += '<li class="result_body_alertInfo" style="color:red;">该发射站下没有设备！</li>';
        	}
        }
	});
	html += '</ul></div></div>';
	var opts = {title : '<span class="title_info" onclick="getStationInfo(' + '\'' + data.id + '\'' + ')">' + data.name + '</span>' +
	'<img src="' + picUrl + 'refresh.png" style="margin-left: 35px; cursor: pointer; position: absolute; display: inline-block; top: 3px;" title="刷新" onclick="refreshInfoWindowInfo(' + '\'' + data.id + '\'' + ')"/>'};
	var infoWindow = new BMap.InfoWindow(html, opts);  // 创建信息窗口对象
	infoWindow.disableCloseOnClick();  // 关闭点击地图时关闭信息窗口
	marker.openInfoWindow(infoWindow);
	setTimeout(function(){
		$(".content_alertInfo").mCustomScrollbar();
	    $('.mCSB_scrollTools .mCSB_dragger .mCSB_dragger_bar').css("width","10px");
	}, 10);
}

/**
 * 刷新信息窗口的信息
 *
 * @param sid	发射站id
 */
function refreshInfoWindowInfo(sid) {
	$.ajax({
		url : restUrl + "station/transmitter/refresh",
		data : {
			sid : sid
		},
		type : "GET",
		dataType: "JSON",
		async : true,	// 异步请求
		success : function(datas) {
			if(datas.length != 0){
				var id;	// 发射机id
				$.each(datas, function (i) {
					id = datas[i].id;
					$("#po_" + id).html(datas[i].masterPo);	// 发射功率
					$("#pr_" + id).html(datas[i].masterPr);	// 反射功率
					$("#vswr_" + id).html(datas[i].masterVswr);	// 驻波比

					document.getElementById("li_" + id).style = "";  // 删除样式
					document.getElementById("li_" + id).style = getDevBorderStyle(datas[i].status);  // 添加样式
				});
				top.$.jBox.tip("刷新成功！", 'info');
			}
		}
	});
}

/**
 * 添加标签
 * 
 * @param name
 * @param marker
 */
function addLabel(name, marker){
	var label = new BMap.Label(
			"<div style='width: 160; white-space: nowrap; overflow: hidden; text-overflow: ellipsis'>" + name + "</div>", 
			{offset:new BMap.Size(-70,-48)});
	label.setStyle({color:"white",     // 颜色
		fontSize:"14px",               // 字号
	    border:"0",                    // 边框
	    height:"46px",                 // 高度
	    width:"169px",                 // 宽度
	    textAlign:"center",            // 文字水平居中显示
	    lineHeight:"40px",             // 行高，文字垂直居中显示
	    opacity:"0.8",				   // 透明度
	    background:"url(" + picUrl + "label.png)",  // 背景图片
	    display:"none"  			   // 默认隐藏
	});
	marker.setLabel(label);
}

/**
 * 右键菜单
 * 
 * @param marker
 * @param data
 */
function markerRightMenu(marker, data){
	var id = data.id;  // 发射站id
	var name = data.name;  // 发射站名称
	
	var edit = function(e, ee, marker){
		var position = marker.getPosition();
		var lon = position.lng;  // 经度
		var lat = position.lat;  // 纬度
		var geoc = new BMap.Geocoder();  // 逆地址解析
		geoc.getLocation(position, function(rs){
			// 两次编码
			var areaName = encodeURI(encodeURI(rs.addressComponents.district));  // 区域
			var addr = encodeURI(encodeURI(rs.address));  // 地址
			var url = baseUrl + "/map/station/form?id=" + id + "&lon=" + lon + "&lat=" + lat + "&address=" + addr + "&areaName=" + areaName;
			art.dialog.open(url, {title: '发射站信息修改', width:'60%', height:'60%', lock:true, background:'#C3C2C0', id:'id'});
		});
	};
	var del = function(e, ee, marker){
		top.$.jBox.confirm("确定要删除该发射站?", "系统提示", function(v,h,f){
			if(v=="ok"){
				delStationMarker(id, marker);
			}
		},{buttonsFocus:1, persistent:true, showClose:false});
		top.$('.jbox-body .jbox-icon').css('top','55px');
	};
	var addDev = function(e, ee, marker){
		var url = baseUrl + "/map/transmitter/form?station=" + id + "&sname=" + encodeURI(encodeURI(name));
		art.dialog.open(url, {title:'添加设备', width:'60%', height:'85%', lock:true, background:'#C3C2C0', id:'id'});
	};
	
	// 创建右键菜单
	var markerMenu=new BMap.ContextMenu();
	markerMenu.addItem(new BMap.MenuItem('<div style="font-size: 18px; margin-top: 5px;">修改</div>', edit.bind(marker)));
	markerMenu.addSeparator();  // 添加分隔符
	markerMenu.addItem(new BMap.MenuItem('<div style="font-size: 18px; margin-top: 5px;">删除</div>', del.bind(marker)));
	markerMenu.addSeparator();
	markerMenu.addItem(new BMap.MenuItem('<div style="font-size: 18px; margin-top: 5px;">添加设备</div>', addDev.bind(marker)));
	marker.addContextMenu(markerMenu);
}

/**
 * 设备富标注开关
 * 调用位置：
 * 	  1. 全省发射站告警情况，点击发射站显示或隐藏设备富标注
 * 	  2. 按区域查询中，点击列表或发射站显示或隐藏设备富标注
 * 
 * @param id 发射站id
 */
function devRichMarkerSwitch(id){
	var haveOpen = true;  // 默认发射站下有打开的设备
	var idArr = station.devMarkerIdMap[id];
	for(var index in idArr){
		var dmId = idArr[index];
		var status = station.devMarkerStatusMap[dmId];
		if(status == 1){  // 找到了 打开 的设备
			haveOpen = true;
			break;
		}
		if(index == (idArr.length - 1)){  // 未找到 打开 的设备
			haveOpen = false;
		}
	}
	if(haveOpen){  // 有打开的
		for(var index in idArr){  // 隐藏所有
			closeDevRichMarker(idArr[index]);  
		}
	}else{
		for(var index in idArr){  // 打开所有
			openDevRichMarker(idArr[index]);
		}
	}
}

/**
 * 获取设备告警信息
 * 
 * @param id  设备id
 */
function getDevAlarm(id){
	var url = baseUrl + "/map/alarm/list?tid=" + id;
	art.dialog.open(url, {title:'设备告警信息', width:'70%', height:'80%', lock:true, background:'#C3C2C0', id:'id'});
}

/**
 * 获取设备监控信息
 * 
 * @param id  设备id
 */
function getDevMonitor(id){
	var url = baseUrl + "/deviceStatus/transmitter?id=" + id;
	art.dialog.open(url, {title:'设备监控信息', width:'85%', height:'95%', lock:true, background:'#C3C2C0', id:'id'});
}

/**
 * 获取设备信息
 * 
 * @param id
 */
function getDevInfo(id){
	var url = baseUrl + "/map/dev/info?id=" + id;
	art.dialog.open(url, {title:'设备信息', width:'60%', height:'60%', lock:true, background:'#C3C2C0', id:'id'});
}

/**
 * 刷新信息窗口--设备添加 | 修改
 * 
 * @param data
 */
function refreshInfoWindow(data){
	var marker = city.markerMap[data.id];
	if(map.getInfoWindow() == '[object InfoWindow]'){  // 信息窗口开启
		getInfoWindow(marker, data);
	}
}

/**
 * 修改设备信息
 * 
 * @param id
 */
function editDevInfo(id, name){
	var url = baseUrl + "/map/transmitter/form?id=" + id + "&sname=" + encodeURI(encodeURI(name));
	art.dialog.open(url, {title:'设备信息修改', width:'60%', height:'85%', lock:true, background:'#C3C2C0', id:'id'});
}

/**
 * 删除设备
 * 
 * @param id	设备id
 * @param sid	发射站id
 */
function delDev(id, sid){
	top.$.jBox.confirm("确定要删除该设备?", "系统提示", function(v,h,f){
		if(v=="ok"){
			$.ajax({  
		        type : "POST",  
		        async: false,  // 同步
		        url : "/fsj/map/transmitter/del",  
		        data : {"id" : id, "sid" : sid},  
		        dataType: "JSON",  
		        success : function(rs) {
		        	if(rs.val == "success"){
		        		refreshInfoWindow($.parseJSON(rs.data));
		        		top.$.jBox.tip("删除成功！", 'info');
		        	}else{
		        		top.$.jBox.tip("删除失败！", 'info');
		        	}
		        }  
		    });
		}
	},{buttonsFocus:1, persistent:true, showClose:false});
	top.$('.jbox-body .jbox-icon').css('top','55px');
}

/**
 * 获取发射站信息
 * 
 * @param id
 */
function getStationInfo(id){
	var url = baseUrl + "/map/station/info?id=" + id;
	art.dialog.open(url, {title:'发射站信息', width:'60%', height:'60%', lock:true, background:'#C3C2C0', id:'id'});
}

/**
 * 发射站标注添加
 * 
 * @param data	 发射站信息
 * @param status 2-地图上画点添加
 */
function addStationMarker(data, status){
	reloadMarkerDrayControl();  // 重新加载标注拖动插件
	if(isOpen){  // WEBSOCKET开启，在告警界面
		addStationMarker4WS(data, status);
	}else{  // 查询界面
		addStationMarker4Query(data, status);
	}
}

/**
 * 发射站标注更新
 * 
 * @param data	发射站信息
 */
function updateStationMarker(data){
	if(isOpen){  // WEBSOCKET开启，在告警界面
		editStationMarker4WS(data);
	}else{  // 查询界面
		editStationMarker4Query(data);
	}
}

/**
 * 删除发射站标注
 * 
 * @param sid	  发射站id
 * @param marker  发射站标注
 */
function delStationMarker(sid, marker) {
	var param = {"sid" : sid};
	$.ajax({  
        type : "POST",  
        async: false,  // 同步
        url : baseUrl + "/map/station/del",  
        data : param,  
        dataType: "JSON",  
        success : function(rs) {
        	if(rs.val == "0"){
        		top.$.jBox.tip("删除成功！", 'info');
        		map.removeOverlay(marker);
        		if(!isOpen){  // 查询界面
        			refreshAreaPannel(queryParamId);
        		}
        	}else if(rs.val == "1"){
        		top.$.jBox.tip("删除失败，发射站不存在！", 'info');
        	}else if(rs.val == "2"){
        		top.$.jBox.tip("删除失败，发射站下有发射机！", 'info');
        	}else{
        		top.$.jBox.tip("删除异常！", 'info');
        	}
        }  
    });
}

/**
 * 根据行政区域的名称画边界
 * 
 * @param name
 * @param status
 */
function drawBoundary(name, status){
	var bdary = new BMap.Boundary();
	bdary.get(name, function(rs){       // 获取行政区域
		var count = rs.boundaries.length; // 行政区域点的个数
		if (count === 0) {
			var msg = '未能获取到【' + name + '】的区域信息';
			console.log(msg);
			return ;
		}
		
		var plyArr = [];  // 多变形覆盖物数组
		for (var i = 0; i < count; i++) {
			var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: getStrokeColor(status)}); // 建立多边形覆盖物
			ply.setFillOpacity(0.01);  // 设置填充透明度
			ply.disableMassClear();  // 禁止覆盖物在map.clearOverlays()中被清除
			map.addOverlay(ply);  // 添加覆盖物
			plyArr[i] = ply;
		}
		boundary.plyArr = plyArr;
	});
}

/**
 * 重新加载标注拖动控件
 * 
 */
function reloadMarkerDrayControl(){
	map.removeControl(markerDray);  // 删除控件
	markerDray = new MarkerDrayControl();
	map.addControl(markerDray);  // 添加控件
}

/**
 * 获取区域边线颜色
 * 
 * @param status
 * @returns {String}
 */
function getStrokeColor(status){
	switch (status) {
		case '0':
			return '#00FF00';
			break;
		case '1':
			return '#FF0000';
			break;
		default:
			break;
	}
}

/**
 * 获取发射站图标
 * 
 * @param status
 */
function getFszIcon(status){
	switch (status) {
		case '0':
			return picUrl + 'fsz_normal.png';
			break;
		case '1':
			return picUrl + 'fsz_alarm.gif';
			break;
		case '2':
			return picUrl + 'fsz_new2.png';
			break;
		case '3':
			return picUrl + 'fsz_over.png';
			break;
		default:
			break;
	}
}

/**
 * 获取发射机图标
 * 
 * @param status
 */
function getFsjIcon(status){
	switch (status) {
		case '0':
			return picUrl + 'fsj_normal.png';
			break;
		case '1':
			return picUrl + 'fsj_alarm.png';
			break;
		case '2':
			return picUrl + 'fsj_power_off.png';
			break;
		case '3':
			return picUrl + 'fsj_wlj.png';
			break;
		case '4':
			return picUrl + 'fsj_over.png';
			break;
		default:
			break;
	}
}

/**
 * 获取设备边框样式
 * 
 * @param status
 */
function getDevBorderStyle(status){
	switch (status) {
		case '0':
			return 'border : 2px solid #00FF00';
			break;
		case '1':
			return 'border : 2px solid #FF0000';
			break;
		case '2':
			return 'border : 2px solid #FFFF00';
			break;
		case '3':
			return 'border : 2px solid #C0C0C0';
			break;
		default:
			break;
	}
}