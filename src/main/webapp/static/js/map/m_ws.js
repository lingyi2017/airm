/**
 * WEBSOCKET相关
 * 
 * Created by lxy on 15-12-15.
 */

/**
 * WEBSOCKET初始化
 * 
 */
function wsInit(){
	if(isOpenHistory != '1'){  // 未开启
		if(userAreaType == area.country){  // 用户区域类型为 国家
			areaType = area.country;  // 设置当前区域类型
		}else if(userAreaType == area.province){  // 用户区域类型为 省、直辖市
			areaType = area.province;
		}else if(userAreaType == area.city){  // 用户区域类型为 市
			areaType = area.city;
		}
	}
	setParam(areaId);
    socket.connect(url);
}

//创建websocket连接
socket.connect = (function(url){
	// 建立连接
	if ('WebSocket' in window) {
		ws = new WebSocket(url);   
	} else if ('MozWebSocket' in window) {
		ws = new MozWebSocket(url);
	} else {
	    alert("当前浏览器不支持websocket！");
	}
	// 连接成功
	ws.onopen = function() {
		ws.send(requestParam);
		console.log("map WebSocket is Open!");
	};
	// 收到服务端消息
	ws.onmessage = function( event ){
		var datas = $.parseJSON(event.data);
		
		//console.log("---请求参数--->" + requestParam);
		
		if(datas.length == 0){  // 服务端返回数据为空不执行
			return;
		}
		var zoom = map.getZoom();
		if(userAreaType == area.country){  // 用户区域类型-国家
			if(zoom == cyBoundary || zoom == prBoundary){
				map.disableScrollWheelZoom();  // 禁用滚轮缩放
			}else{
				map.enableScrollWheelZoom();  // 启用滚轮缩放
			}
			if(zoom <= cyBoundary){  // 全国界面
				if(!country.isInit){
					updateHistoryParam(historyParam.zoom, zoom);
					areaType = area.country;
					setParam(userAreaId);
					ws.send(requestParam);  // 发送页面数据
				}
				
				// 删除全省和全市界面覆盖物
				clearProvinceUI();
				clearCityUI();
				
				if(area.country == datas[0].areaType){  // 防止数据混乱
					countryUI(datas);
					country.isInit = true;  // 初始化完成
					country.isDel = false;  // 未被删除
				}
			}else if(zoom > cyBoundary && zoom <= prBoundary){  // 省、直辖市界面
				if(!province.isInit){
					updateHistoryParam(historyParam.zoom, zoom);
					areaType = area.province;
					setParam(proAreaId);
					ws.send(requestParam);  // 发送页面数据
				}
				
				// 删除全国和全市界面覆盖物
				clearCountryUI();
				clearCityUI();
				
				if(area.province == datas[0].areaType){
					// 画省边界
					if(boundary.isInit == false){
						drawBoundary(datas[0].prName, datas[0].status);
						boundary.isInit = true;  // 边界初始化完成
						
						updateHistoryParam(historyParam.areaName, datas[0].prName);
					}
					
					provinceUI(datas);
					if(initIndex >= 1){
						province.isInit = true;  // 标注初始化完成
						province.isDel = false;  // 未被删除
					}
					initIndex++;
				}
			}else if(zoom > prBoundary){  // 市级界面
				updateHistoryParam(historyParam.zoom, zoom);
				if(!city.isInit){
					areaType = area.city;
					setParam(areaId);  // 市界面数据
					ws.send(requestParam);  // 发送页面数据
				}
				
				// 清除全国和全省界面覆盖物
				clearCountryUI();
				clearProvinceUI();
				
				if(area.city == datas[0].areaType){
					cityUI(datas);
					city.isInit = true;  // 标注初始化完成
					city.isDel = false;  // 未被删除
				}
			}
		}else if(userAreaType == area.province){  // 用户区域类型-省、直辖市
			if(zoom == prBoundary){
				map.disableScrollWheelZoom();  // 禁用滚轮缩放
			}else{
				map.enableScrollWheelZoom();  // 启用滚轮缩放
			}
			if(zoom <= prBoundary){  // 省、直辖市界面
				if(!province.isInit){
					updateHistoryParam(historyParam.zoom, zoom);
					areaType = area.province;
					setParam(userAreaId);
					ws.send(requestParam);  // 发送页面数据
				}
				
				// 删除全市界面覆盖物
				clearCityUI();
				
				if(area.province == datas[0].areaType){
					// 画省边界
					if(boundary.isInit == false){
						drawBoundary(datas[0].prName, datas[0].status);
						boundary.isInit = true;  // 边界初始化完成
						
						updateHistoryParam(historyParam.areaName, datas[0].prName);
					}
					
					provinceUI(datas);
					if(initIndex >= 1){
						province.isInit = true;  // 标注初始化完成
						province.isDel = false;  // 未被删除
					}
					initIndex++;
				}
			}else if(zoom > prBoundary){  // 市级界面
				updateHistoryParam(historyParam.zoom, zoom);
				if(!city.isInit){
					areaType = area.city;
					setParam(areaId);  // 市界面数据
					ws.send(requestParam);  // 发送页面数据
				}
				
				// 清除全省界面覆盖物
				clearProvinceUI();
				
				if(area.city == datas[0].areaType){
					cityUI(datas);
					city.isInit = true;  // 标注初始化完成
					city.isDel = false;  // 未被删除
				}
			}
		}else if(userAreaType == area.city){  // 用户区域类型-市
			updateHistoryParam(historyParam.zoom, zoom);
			cityUI(datas);
			city.isInit = true;  // 标注初始化完成
			city.isDel = false;  // 未被删除
			map.enableScrollWheelZoom();  // 启用滚轮缩放
		}
	};
	// 关闭连接
	ws.onclose = function( event ) {
		ws.close();
		isOpen = false;
		console.log("map WebSocket closed!");
	};
	// 异常
	ws.onerror = function( event ) {
	    console.log("map WebSocket occurs Error!");
	};
});

/**
 * 全国界面地图展示
 * 
 * @param datas
 */
function countryUI(datas){
	var prId;  // 省id
	var status;  // 告警情况
	map.clearOverlays();  // 删除告警覆盖物
	$.each(datas, function(i) {
		prId = datas[i].id;
		status = datas[i].status;
		
		if(country.isInit == false){  // 地图数据没有初始化
			country.statusMap[prId] = status;
			drawPrBoundary(prId, datas[i].name, status);
		}else{  // 已经初始化
			if((country.statusMap[prId] == '1' && status == '1') || 
					(country.statusMap[datas[i].id] == '1' && status == '0')){  // 之前有告警并且现在告警未消除(闪烁) || 之前有告警并且现在告警消除(不闪烁)
				country.statusMap[prId] = status;
				drawPrBoundary(prId, datas[i].name, status);
			}else if(country.statusMap[prId] == '0' && status == '1'){  // 之前没有告警但现在发生了告警(闪烁)
				country.statusMap[prId] = status;
				
				// 移除上次覆盖物，只能通过该种方式删除
				var plyArr = country.plyMap[prId];
				for(var index in plyArr){
					map.removeOverlay(plyArr[index]);
				}
				
				drawPrBoundary(prId, datas[i].name, status);
			}
		}
	});
}

/**
 * 画省、直辖市边界
 * 
 * @param prId
 * @param prName
 * @param status
 */
function drawPrBoundary(prId, prName, status){       
	var bdary = new BMap.Boundary();
	bdary.get(prName, function(rs){       // 获取行政区域
		var count = rs.boundaries.length; // 行政区域点的个数
		if (count === 0) {
			var msg = '未能获取到【' + prName + '】的区域信息';
			console.log(msg);
			return ;
		}
		
		var plyArr = [];  // 单个省 多边形覆盖物数组
		for (var i = 0; i < count; i++) {
			var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 1, strokeColor: getStrokeColor(status)}); // 建立多边形覆盖物
			ply.setFillOpacity(0.1);  // 设置填充透明度
			if(status == '1'){  // 有告警
				ply.setFillColor("#FF0000");
			}else{  // 无告警
				ply.disableMassClear();  // 禁止覆盖物在map.clearOverlays()中被清除
				ply.setFillColor("#00FF00");
			}
			map.addOverlay(ply);  // 添加覆盖物
			plyArr[i] = ply;
			
			// 覆盖物添加单击事件
			ply.addEventListener("click", function(){
				// 重新定位地图视图
				map.centerAndZoom(prName, prBoundary);
				initIndex = 0;
				
				areaId = prId;
				proAreaId = prId;  // 区域id为省id
			});
		}
		country.plyMap[prId] = plyArr;
	});
}

/**
 * 全省界面地图展示
 * 
 * @param datas
 */
function provinceUI(datas){
	var cityId;  // 市id
	var status;  // 告警情况
	map.clearOverlays();  // 删除告警覆盖物
	$.each(datas, function(i) {
		if(i > 0){  // 第一组数据为该界面的基础数据
			cityId = datas[i].id;
			status = datas[i].status;
			
			if(province.isInit == false){  // 地图数据没有初始化
				province.statusMap[cityId] = status;
				drawCityBoundary(cityId, datas[i].name, status);
			}else{  // 已经初始化
				if((province.statusMap[cityId] == '1' && status == '1') || 
						(province.statusMap[datas[i].id] == '1' && status == '0')){  // 之前有告警并且现在告警未消除(闪烁) || 之前有告警并且现在告警消除(不闪烁)
					province.statusMap[cityId] = status;
					drawCityBoundary(cityId, datas[i].name, status);
				}else if(province.statusMap[cityId] == '0' && status == '1'){  // 之前没有告警但现在发生了告警(闪烁)
					province.statusMap[cityId] = status;
					
					// 移除上次覆盖物，只能通过该种方式删除
					var plyArr = country.plyMap[cityId];
					for(var index in plyArr){
						map.removeOverlay(plyArr[index]);
					}
					
					drawCityBoundary(cityId, datas[i].name, status);
				}
			}
		}
	});
}

/**
 * 画市边界
 * 
 * @param cityId
 * @param cityName
 * @param status
 */
function drawCityBoundary(cityId, cityName, status){
	var bdary = new BMap.Boundary();
	bdary.get(cityName, function(rs){       // 获取行政区域
		var count = rs.boundaries.length; // 行政区域点的个数
		if (count === 0) {
			var msg = '未能获取到【' + cityName + '】的区域信息';
			console.log(msg);
			return ;
		}
		
		var plyArr = [];  // 单个省 多边形覆盖物数组
		for (var i = 0; i < count; i++) {
			var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 1, strokeColor: getStrokeColor(status)}); // 建立多边形覆盖物
			ply.setFillOpacity(0.1);  // 设置填充透明度
			if(status == '1'){  // 有告警
				ply.setFillColor("#FF0000");
			}else{  // 无告警
				if(initIndex >= 2){  // 无告警，绘制两次
					ply.disableMassClear();  // 禁止覆盖物在map.clearOverlays()中被清除
				}
				ply.setFillColor("#00FF00");
			}
			map.addOverlay(ply);  // 添加覆盖物
			plyArr[i] = ply;
			
			// 覆盖物添加单击事件
			ply.addEventListener("click", function(){
				// 重新定位地图视图
				map.centerAndZoom(cityName, 12);
				reloadMarkerDrayControl();  // 重新加载标注拖动插件
				
				areaId = cityId;
			});
		}
		province.plyMap[cityId] = plyArr;
	});
}

/**
 * 市界面地图展示
 * 
 * @param datas
 */
function cityUI(datas){
	var id;
	var status;
	$.each(datas, function(i) {
		if(i >= 2){  // 第一组数据为区域类型，第二组数据为区域名称
			id = datas[i].id;
			status = datas[i].status;
			if(city.isInit == false){  // 标注未初始化
				addStationMarker4WS(datas[i], status);
			}else if(city.isInit == true){  // 已初始化
				if((city.statusMap[id] == '1' && status == '0') || 
						(city.statusMap[id] == '0' && status == '1')){  // 之前告警但当前已经恢复或者之前没有告警现在有告警
					// 更新标注
					map.removeOverlay(city.markerMap[id]);
					addStationMarker4WS(datas[i], status);
				}
			}
		}else if(!city.isInit && i == 1){
			updateHistoryParam(historyParam.areaName, datas[i].cityName);
		}
	});
}

/**
 * 添加发射站标注
 * 
 * @param data
 * @param status
 */
function addStationMarker4WS(data, status){
	var point = new BMap.Point(data.lon, data.lat);
	var myIcon;
	if(status == '2'){  // 在地图上直接添加的标注
		myIcon = new BMap.Icon(getFszIcon(status), new BMap.Size(60, 30));
	}else{
		myIcon = new BMap.Icon(getFszIcon(status), new BMap.Size(30, 30));
	}
    
    var marker = new BMap.Marker(point, {icon: myIcon}); // 创建标注
    marker.disableMassClear();
    //marker.enableDragging();  // 开启拖拽
    
    map.addOverlay(marker); // 将标注添加到地图中
    
    initInfoWindow(marker, data);  // 初始化信息窗口
    
    addEventForStationMarker(marker, data);  // 给发射站标注添加事件
    
    city.statusMap[data.id] = status;  // 保存状态
    city.markerMap[data.id] = marker;  // 保存标注
}

/**
 * 修改发射站标注
 * 
 * @param data
 * @param status
 */
function editStationMarker4WS(data, status){
	var marker = city.markerMap[data.id];
	addEventForStationMarker(marker, data);
	if(map.getInfoWindow() == '[object InfoWindow]'){  // 信息窗口开启
		getInfoWindow(marker, data);
	}
}

/**
 * 初始化信息窗口
 * 说明：用于解决第一次点击信息窗口滚动控件失效的问题
 * 
 * @param marker
 * @param data
 */
function initInfoWindow(marker, data){
	var html ='<div class="info">' +
    '<div class="content_alertInfo mCustomScrollbar light" data-mcs-theme="minimal-dark">' +
    '<ul class="select_info"></ul></div></div>';
	new BMap.InfoWindow(html);  // 创建信息窗口对象
	$(".content_alertInfo").mCustomScrollbar();  // 激活滚动条控件
    $('#mCSB_1').css("max-height","351.5px");
}

/**
 * 关闭设备富标注
 * 
 * @param id
 */
function closeDevRichMarker(id){
	$("#" + id).hide();
	station.devMarkerStatusMap[id] = 0;  // 设为 关闭
}

/**
 * 打开设备富标注
 * 
 * @param id
 */
function openDevRichMarker(id){
	$("#" + id).show();
	station.devMarkerStatusMap[id] = 1;  // 设为 打开
}

/**
 * 清除全国界面数据
 * 
 */
function clearCountryUI(){
	if(country.isDel == false){  
		map.clearOverlays();
		for(var key in country.plyMap){  
			var plyArr = country.plyMap[key];
			for(var index in plyArr){
				map.removeOverlay(plyArr[index]);
			}
		}
		country.isDel = true;  // 全国界面删除标记复位
		country.isInit = false;  // 全国界面初始化标记复位
	}
}

/**
 * 清除全省界面数据
 * 
 */
function clearProvinceUI(){
	if(province.isDel == false){  
		map.clearOverlays();
		for(var key in province.plyMap){  
			var plyArr = province.plyMap[key];
			for(var index in plyArr){
				map.removeOverlay(plyArr[index]);
			}
		}
		province.isDel = true;  // 全省界面删除标记复位
		province.isInit = false;  // 全省界面初始化标记复位
	}
	
	// 边界复位
	var plyArr = boundary.plyArr;
	for(var key in plyArr){
		map.removeOverlay(plyArr[key]);
	}
	boundary.isInit = false;
	boundary.plyArr = [];
}

/**
 * 清除全市界面数据
 * 
 */
function clearCityUI(){
	if(city.isDel == false){  // 市界面删除标注
		for(var key in city.markerMap){
			var maker = city.markerMap[key];
			map.removeOverlay(maker);
		}
		city.isDel = true;
		city.isInit = false;  // 市界面初始化标记复位
	}
}

/**
 * 设置参数
 * 
 * @param areaId
 */
function setParam(areaId){
	requestParam = "{\"userId\":\"" + userId + "\", \"areaId\":\"" + areaId + "\", \"areaType\":\"" + areaType + "\"}";
	
	updateHistoryParam(historyParam.areaId, areaId);
	updateHistoryParam(historyParam.areaType, areaType);
}

/**
 * 更新历史参数
 * 
 * @param key  更新参数的key
 * @param val  更新参数的value
 */
function updateHistoryParam(key, val){
	if(isOpenHistory == '1'){
		var param = JSON.stringify({"key":key, "val":val});
		$.ajax({
	        type: "POST",
	        url: restUrl + "update/history/param",
	        data : param,
	        dataType: "JSON",
	        contentType : "application/json; charset=UTF-8",
	        success: function(data){
	        	
	        }
	    });
	}
}