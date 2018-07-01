/**
 * 查询相关
 * 
 * Created by lxy on 15-12-15.
 */

var devVal = '';  // 保存设备查询上次的输入值
var areaVal = '';  // 保存区域查询上次的输入值
var queryParamId = ''; // 上次查询参数id
var queryType = 1; // 按设备查询-0; 按区域查询-1

$(function(){
    var type=1;//type=0,代表以后得查询的结果都和设备有关；type=1，代表以后得查询结果都和区域有关。

    ////查询与删除的切换
    //$("#select_go").click(function(){
    //    $(".select_title").css("display","block");
    //    $('#select_go').css("display","none");
    //    $('#select_del').css("display","block");
    //
    //});
    //$('#select_del').click(function(){
    //    $(".select_title").css("display","none");
    //    $('#select_go').css("display","block");
    //    $('#select_del').css("display","none");
    //    $('#select_result').html('');
    //    $('#select_result').css("display","none");
    //});
    //随着输入条件加载添加
    var searchText = "";
    function getResult(){
        var select_char= $.trim($('#select_by').val());
        searchText = select_char;
        if (select_char === "") {
            $('.input_clear').css("display","none");
            $('#select_result').children().remove();
            $('#select_content_result').children().remove();
            $('#select_result').removeClass('add_select_result');
            
        	if(!isOpen && type == 1){  // 显示全部发射站
        		getAllStation4Query();
        	}
            return;
        }
        $('.input_clear').css("display","block");
        $('#select_content_result').children().remove();
        $('#select_result').children().remove();   //清空select_result里面的查询所有内容
        var s = searchText.toLowerCase();
        if(type==0){
            $.ajax({
                type: "GET",
                url: restUrl + "query/dev/item",
                data : {
    	        	keyword : s   	
    	        },
                dataType: "json",
                success: function(datas){
                    $('#select_result').children().remove();   //清空select_result里面的查询所有内容
                    var data = $.grep(datas, function (item) {
                        for (var key in item) {
                            if ((typeof item[key] === 'string' ||
                                typeof item[key] === 'number')) {
                                return true;
                            }
                        }
                        return false;
                    });
                    var html = '';
                    var a=data.length;
                    if(a==0){
                        html += '<li class="select_result_li"><span>没有搜索到相关内容</span></li>';
                        $('#select_result').append(html);
                        $('#select_result').addClass('add_select_result');
                        return;
                    }
                    //对模糊查询进行限制
                    if(a<=10){
                        $.each(data, function(commentIndex, comment){
                            html += '<li class="select_result_li select_action select_result_li_hover"><span name=' + comment['id'] + '>'+comment['name']+'</span></li>';
                        });
                    }else{
                        var newdata=[];
                        for(var i=0;i<10;i++){
                            newdata.push(data[i]);
                        }
                        $.each(newdata, function(commentIndex, comment){
                            html += '<li class="select_result_li select_action select_result_li_hover"><span name=' + comment['id'] + '>'+comment['name']+'</span></li>';
                        });
                    }
                    $('#select_result').append(html);
                    $('#select_result').addClass('add_select_result');
                }
            });
        }else{
            $.ajax({
                type: "GET",
                url: restUrl + "query/area/item",
                data : {
    	        	name : s   	
    	        },
                dataType: "json",
                success: function(datas){
                    $('#select_result').children().remove();   //清空select_result里面的查询所有内容
                    var data = $.grep(datas, function (item) {
                        for (var key in item) {
                            if ((typeof item[key] === 'string' ||
                                typeof item[key] === 'number') &&
                                (item[key] + '').toLowerCase().indexOf(s) !== -1) {
                                return true;
                            }
                        }
                        return false;
                    });
                    var html = '';
                    var a=data.length;
                    if(a==0){
                        html += '<li class="select_result_li"><span>没有搜索到相关内容</span></li>';
                        $('#select_result').append(html);
                        $('#select_result').addClass('add_select_result');
                        return;
                    }
                    //对模糊查询进行限制
                    if(a<=9){
                        $.each(data, function(commentIndex, comment){
                            html += '<li class="select_result_li select_action select_result_li_hover"><span name=' + comment['id'] + '>'+ comment['name'] +'</span></li>';
                        });
                    }else{
                        var newdata=[];
                        for(var i=0;i<9;i++){
                            newdata.push(data[i]);
                        }
                        $.each(newdata, function(commentIndex, comment){
                            html += '<li class="select_result_li select_action select_result_li_hover"><span name=' + comment['id'] + '>'+comment['name']+'</span></li>';
                        });
                    }
                    $('#select_result').append(html);
                    $('#select_result').addClass('add_select_result');
                }
            });
        }

    }
    var timeoutId = 0;
    $('#select_by').off('keyup').on('keyup', function (event) {
    	if(!$(this).hasClass("focus")){
    		$(this).addClass("focus");
    	}
        clearTimeout(timeoutId); // doesn't matter if it's 0
        timeoutId = setTimeout($.proxy(getResult), 100, event); // 100ms
    });
    $('#select_by').off('keydown').on('keydown', function (event) {
    	if(!$(this).hasClass("focus")){
    		$(this).addClass("focus");
    	}
    });
    //清除input框内容
    $(".input_clear").mouseover(function(){
        $(".alert_info").animate({
            "left":"323.5px",
            "top":"95px"
        },0.0001);
        $(".alert_info").css("display","block");
    }).mouseleave(function(){
        $(".alert_info").css("display","none");
    });
    $(".input_clear").click(function(){
    	$(".content_result").css("display", "none");
        $('#select_by').val('');
        $('.input_clear').css("display","none");
        $('#select_result').children().remove();   //清空select_result里面的查询所有内容
        $('#select_result').removeClass('add_select_result');
        
        map.clearOverlays();  // 删除地图覆盖物
        if(!isOpen && type == 1){  // 显示全部发射站
        	getAllStation4Query();
        }
    });
    //获取相关设备或区域函数
    function get_relevantResult(id){
    	ws.onclose();  // 关闭WEBSOCKET
    	map.enableScrollWheelZoom();  // 启用滚轮缩放
    	// 删除全国、全省、全市界面覆盖物
    	map.clearOverlays();  // 删除告警覆盖物
    	clearCountryUI();
		clearProvinceUI();
		clearCityUI();
		
    	queryParamId = id;
        if(type==0){
            $.ajax({
                type: "GET",
                url: restUrl + "query/dev/getById?id=" + id,
                dataType: "json",
                success: function(data){
    				map.setZoom(16);  // 先修改地图缩放等级
    				setTimeout(function(){
	                    var html = '';
	                    html = '<li class="select_result_li"><ul class="shebei_ul shebei_ul_title"><li class="shebei_li li_img">状态</li><li class="shebei_li li_name">设备名称</li><li class="shebei_li li_address">发射站</li></ul></li>';
	                    $.each(data, function(commentIndex, comment){
	                		html += '<li class="select_result_li li_hover " onclick="openDevRichMarkerForQuery(' + "\'" + comment['id'] + "\'" + "," + "\'" + comment['lon'] + "\'" + "," + "\'" + comment['lat'] + "\'" + ')"><ul class="shebei_ul shebei_list"><li class="shebei_li li_img"><img src="' + getFsjIcon(comment['status']) + '"></li><li class="shebei_li li_name" title="' + comment['name'] + '" >'+comment['name']+'</li><li class="shebei_li li_address" >'+comment['sname']+'</li></ul></li>';
	                    	addDeviceMarker(comment);  // 添加设备标注
	                    	map.panTo(new BMap.Point(comment['lon'], comment['lat']));  // 定位
	                    });
	                    $('#select_result').append(html);
	                    $('#select_result').find(".shebei_list").on("click",function(){
	                    	$('#select_result .shebei_list').removeClass("shebeiClicked");
	                    	$(this).addClass("shebeiClicked");
	                    });
    				}, 10);
                }
            });
        }else{
        	$(".content_result").css("display", "block");
            $.ajax({
                type: "GET",
                url: restUrl + "report/area?id=" + id,
                dataType: "json",
                success: function(data){
    				var html='';
                    html = '<li class="select_quyu_result_li "><ul class="quyu_ul quyu_ul_title"><li class="quyu_li">区域名称</li><li class="quyu_li">总数（设备）</li><li class="quyu_li">正常</li><li class="quyu_li">警告</li></ul></li>';
                    var a=data.length;
                    if(a==0){
                        html += '<li class="select_result_li"><ul class="quyu_ul"><li class="quyu_li quyu_li_0">没有搜索到相关内容</li></ul></li>';
                    } else{
                        $.each(data, function(commentIndex, comment){
                            if(commentIndex==0){
                                html += '<li class="select_result_li "><ul class="quyu_ul quyu_ul_action"><li class="quyu_li ">'+comment['name']+'</li><li class="quyu_li">'+comment['sum']+'</li><li  class="quyu_li" >'+comment['normal']+'</li><li class="quyu_li quyu_action action_open">'+comment['alarm']+'</li></ul>';
                                html+='<ul class="chl_content" style="display: block;"><li class="chl_li chl_title chl_li_01">发射站</li><li class="chl_li chl_title chl_li_02">总数</li><li class="chl_li chl_title chl_li_02">正常</li><li class="chl_li chl_title chl_li_03 ">警告</li></ul>';
                                var new_data=comment['sList'];
                                $.each(new_data, function(commentIndex, comment){
                                    html+='<ul class="chl_content" style="display: block;" onclick="getStationInfoWindowForQuery(' + '\'' + comment['id'] + '\'' + ',' + '\'' +comment['alarm'] + '\'' + ')"><li class="chl_li chl_li_01">'+comment['name']+'</li><li class="chl_li chl_li_02">'+comment['sum']+'</li><li class="chl_li chl_li_02">'+comment['normal']+'</li><li class="chl_li chl_li_03">'+comment['alarm']+'</li></ul>';
                                });
                                html+='</li>';
                            }else{
                                html += '<li class="select_result_li "><ul class="quyu_ul quyu_ul_action"><li class="quyu_li ">'+comment['name']+'</li><li class="quyu_li">'+comment['sum']+'</li><li  class="quyu_li" >'+comment['normal']+'</li><li class="quyu_li quyu_action action_close">'+comment['alarm']+'</li></ul>';
                                html+='<ul class="chl_content"><li class="chl_li chl_title chl_li_01">发射站</li><li class="chl_li chl_title chl_li_02">总数</li><li class="chl_li chl_title chl_li_02">正常</li><li class="chl_li chl_title chl_li_03 ">警告</li></ul>';
                                var new_data=comment['sList'];
                                $.each(new_data, function(commentIndex, comment){
                                    html+='<ul class="chl_content" onclick="getStationInfoWindowForQuery(' + '\'' + comment['id'] + '\'' + ',' + '\'' + comment['alarm'] + '\'' + ')"><li class="chl_li chl_li_01">'+comment['name']+'</li><li class="chl_li chl_li_02">'+comment['sum']+'</li><li class="chl_li chl_li_02">'+comment['normal']+'</li><li class="chl_li chl_li_03">'+comment['alarm']+'</li></ul>';
                                });
                                html+='</li>';
                            }
                        });
                    }
                    $('#select_content_result').append(html);
                    $('#select_content_result').find(".quyu_ul_action").on("click",function(){
                        if($(this).find(".quyu_action").hasClass("action_open")){
                        	$(this).find(".quyu_action").removeClass("action_open");
                        	$(this).find(".quyu_action").addClass("action_close");
                        	$(this).find(".quyu_action").parent().parent().find('.chl_content').css({"display":"none"});
                            return;
                        }
                        if($(this).find(".quyu_action").hasClass("action_close")){
                        	$(this).find(".quyu_action").removeClass("action_close");
                        	$(this).find(".quyu_action").addClass("action_open");
                        	$(this).find(".quyu_action").parent().parent().find('.chl_content').css({"display":"block"});
                            return;
                        }
                    });
                    $('#select_content_result').find(".chl_content").on("click",function(){
                    	$('#select_content_result .chl_content').removeClass("clicked");
                    	$(this).addClass("clicked");
                    });
                }
            });
        }
    }
    //所有设备加载添加
    $(".shebei_icon").mouseover(function(){
        $("#select_shebei").addClass("a_click");
        $(".shebei_img").attr("src","../static/pictures/map/keyword.png");
    }).mouseleave(function(){
        if($("#select_shebei").attr("name")!='1'){
            $("#select_shebei").removeClass("a_click");
            $(".shebei_img").attr("src","../static/pictures/map/keyword2.png");
        }
    });
    $(".shebei_icon").click(function(){
        type=0;
        queryType = 0;
        areaVal = $("#select_by").val();
        $(".content_result").css("display", "none");
        $("#select_shebei").attr("name","1");
        $(".shebei_img").attr("src","../static/pictures/map/keyword.png");
        $(".quyu_img").attr("src","../static/pictures/map/area2.png")
        $("#select_quyu").attr("name","0");
        $("#select_quyu").removeClass("a_click");
        $("#select_shebei").addClass("a_click");
        $(".arrow-wrap").animate({"left":"178px"});
        $('#select_by').attr("placeholder","请输入设备名称");
        $('#select_by').val(devVal);
        $('#select_result').children().remove();   //清空select_result里面的查询所有内容
        $('#select_content_result').children().remove();
    });
    //所有区域加载添加
    $(".quyu_icon").mouseover(function(){
        $("#select_quyu").addClass("a_click");
        $(".quyu_img").attr("src","../static/pictures/map/area.png");
    }).mouseleave(function(){
        if($("#select_quyu").attr("name")!='1'){
            $("#select_quyu").removeClass("a_click");
            $(".quyu_img").attr("src","../static/pictures/map/area2.png")
        }
    });
    $(".quyu_icon").click(function(){
        type=1;
        queryType = 1;
        devVal = $("#select_by").val();
        $("#select_shebei").attr("name","0");
        $("#select_quyu").attr("name","1");
        $(".shebei_img").attr("src","../static/pictures/map/keyword2.png");
        $(".quyu_img").attr("src","../static/pictures/map/area.png")
        $("#select_shebei").removeClass("a_click");
        $("#select_quyu").addClass("a_click");
        $(".arrow-wrap").animate({"left":"0"});
        $('#select_by').attr("placeholder","请输入设备地址");
        $('#select_by').val(areaVal);
        $('#select_result').children().remove();   //清空select_result里面的查询所有内容
        $('#select_content_result').children().remove();
    });
    //动态绑定
    $('#select_result').on('click','.select_action',function(){
        var char=$(this).text();
        var id=$(this).children().attr("name");
        $('#select_by').val(char);
        $('#select_result').children().remove();
        $('#select_result').removeClass('add_select_result');
        get_relevantResult(id);
    });
    
    //键盘事件
    $(document).keydown(function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        var limit_i=$('.select_action').length;
        if(e.keyCode!=40&&e.keyCode!=38&&e.keyCode!=13){
            $('#select_by').off('keyup').on('keyup', function (event) {
                clearTimeout(timeoutId); // doesn't matter if it's 0
                timeoutId = setTimeout($.proxy(getResult), 100, event); // 100ms
                i_count=0;
            });
        }else{
            if(0<=i_count<=limit_i){
                function key_action(){
                    if(e.keyCode==40){ // 按 向下箭头
                    	$('#select_by').removeClass("focus");
                        $('#select_by').off('keyup');
                        $('.select_action').removeClass("hover");
                        if(i_count<limit_i){
                            $('.select_result').find("li").eq(i_count).addClass("hover");
                            var char=$('.select_result').find("li").eq(i_count).text();
                            $('#select_by').val(char);
                            i_count++;
                        }else{
                            i_count=0;
                            $('.select_result').find("li").eq(i_count).addClass("hover");
                            var char=$('.select_result').find("li").eq(i_count).text();
                            $('#select_by').val(char);
                            i_count++;
                        }
                    }
                    if(e.keyCode==38){ // 按  向上箭头
                    	$('#select_by').removeClass("focus");
                        $('#select_by').off('keyup');
                        if(i_count>=1){
                            i_count--;
                            $('.select_action').removeClass("hover");
                            $('.select_result').find("li").eq(i_count-1).addClass("hover");
                            var char=$('.select_result').find("li").eq(i_count-1).text();
                            $('#select_by').val(char);
                        }
                        if(i_count==0){
                            i_count=limit_i;
                            $('.select_action').removeClass("hover");
                            $('.select_result').find("li").eq(i_count-1).addClass("hover");
                            var char=$('.select_result').find("li").eq(i_count-1).text();
                            $('#select_by').val(char);
                        }
                    }
                    if(e.keyCode==13){ // enter 键
                    	if( $('#select_by').hasClass("focus")&&i_count==0){
                            $('#select_by').off('keyup');
                            clearTimeout(timeoutId); // doesn't matter if it's 0
                            timeoutId = setTimeout($.proxy(getResult), 100, event); // 100ms
                            var len=$('#select_result').find('.select_action').length;
                            if(len>0){
                            	clearTimeout(timeoutId);
                            	var id=$('#select_result').find('.select_action').eq(0).find("span").attr("name");
                            	$('#select_result').html("");
                            	setTimeout($.proxy(get_relevantResult(id)), 100, event);
                            }
                    	}else{
                            $('#select_by').off('keyup');
                            if(1<=i_count&&i_count<=limit_i){
                                var num=i_count-1;
                                var name=$('.select_result').find("li").eq(num).text();
                                var id=$('.select_result').find("li").eq(num).find('span').attr('name');
                                $('#select_by').val(name);
                                $('#select_result').children().remove();
                                $('#select_result').removeClass('add_select_result');
                                get_relevantResult(id);
                                if(i_count==limit_i){
                                    i_count++;
                                }
                            }
                    	}
                    }
                }
                key_action();
            }
            if(i_count>limit_i){
                i_count=0;
            }
        }
    });

});

/**
 * 添加设备标注
 * 
 */
function addDeviceMarker(device){
	var point = new BMap.Point(device.lon, device.lat);
    var myIcon = new BMap.Icon(getDeviceIcon(device.status), new BMap.Size(26, 26));
    var marker = new BMap.Marker(point, {icon: myIcon}); // 创建标注
    map.addOverlay(marker); // 将标注添加到地图中
    query.markerMap[device.id] = marker;
    
    // 添加标签
	addLabel(device.name, marker);
	
	var oldIcon = marker.getIcon();
	// 鼠标移至
	marker.addEventListener("mouseover", function(){
		var label = marker.getLabel();
		label.setStyle({
		    display:"block"  // 显示标签
		});
		var newIcon = new BMap.Icon(getDeviceIcon("4"), new BMap.Size(26, 26));
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
    
	// 添加富标注
    addDevRichMarker(device);
    
    // 单击打开富标注
    marker.addEventListener("click", function(){
    	openDevRichMarkerForQuery(device.id, device.lon, device.lat);
    });
}

/**
 * 设备添加富标注
 * 
 * @param devInfo
 */
function addDevRichMarker(devInfo){
	var htm = "<div class='window' style='display:none;" + getDevBorderStyle(devInfo.status) + "' id='" + devInfo.id + "'>" + 
		"<div class='window_1'>" + 
			"<p title='" + devInfo.name + "'>" + devInfo.name + "</p>" + 
			"<div class='window_1_close'>" + 
				"<input type='button' value='×' onclick='closeDevRichMarkerForQuery(" + "\"" + devInfo.id + "\"" + ")' onfocus='this.blur()'/>" + 
			"</div>" + 
		"</div>" +
		"<div class='window_2'>" + 
		"<div class='window_2_left'>" + 
	    "<div class='window_2_left_top'>" + 
	        "<p>发射功率</p>" + 
	    "</div>" +
	    "<div class='window_2_left_bottom'>" + 
	        "<p>" + devInfo.masterPo + "</p>" +
	    "</div>" +
	    "</div>" + 
	    "<div class='window_2_right'>" +
	    "<div class='window_2_right_top'>" + 
	        "<p>反射功率</p>" +
	    "</div>" +
	    "<div class='window_2_right_bottom'>" +
	        "<p>" + devInfo.masterPr + "</p>" +
	    "</div>" +
	    "</div>" +
	    "</div>" + 
	    "<div class='window_3'>" + 
	        "<table>" +
	            "<tr>" +
	                "<td>驻波比</td>" +
	                "<td>温度</td>" +
	                "<td>湿度</td>" +
	            "</tr>" + 
	            "<tr>" +
	                "<td>" + devInfo.masterVswr + "</td>" +
	                "<td>" + devInfo.masterTemperature + "℃</td>" +
	                "<td>40%</td>" +
	            "</tr>" +
	        "</table>" +
	    "</div>" +
	    "<div class='window_4'>" + 
	        "<input type='image' title='告警信息' onclick='getDevAlarm(" + "\"" + devInfo.id + "\"" + ")' src='" + picUrl + "alarm_btn.png'>" + 
	        "<input type='image' title='监控信息' onclick='getDevMonitor(" + "\"" + devInfo.id + "\"" + ")' src='" + picUrl + "monitor_btn.png'>" +
	        "<input type='image' title='设备信息' onclick='getDevInfo(" + "\"" + devInfo.id + "\"" + ")' src='" + picUrl + "station_btn.png'>" +
	        "<input type='image' title='发射站信息' onclick='getStationInfo(" + "\"" + devInfo.sid + "\"" + ")' src='" + picUrl + "device_btn.png'>" + 
	    "</div>" +
	    "</div>";
		
	var point = new BMap.Point(devInfo.lon, devInfo.lat);
	var myRichMarkerObject = new BMapLib.RichMarker(htm, point, {"anchor": new BMap.Size(20, -5), "enableDragging": true});
	map.addOverlay(myRichMarkerObject);
}

/**
 * 关闭设备富标注--查询
 * 
 * @param id
 */
function closeDevRichMarkerForQuery(id){
	$("#" + id).hide();
}

/**
 * 打开设备富标注--查询
 * 
 * @param id
 * @param lon
 * @param lat
 */
function openDevRichMarkerForQuery(id, lon, lat){
	map.setZoom(16);  // 先修改地图缩放等级
	setTimeout(function(){
		map.panTo(new BMap.Point(lon, lat));  // 地图移动到当前位置
		$("#" + id).show();
	}, 10);
}

/**
 * 获取发射站信息窗口--查询
 * 
 * @param id   发射站id
 * @param num  发射站告警数量
 */
function getStationInfoWindowForQuery(id, num){
	$.ajax({
        type: "GET",
        url: restUrl + "/station/info?sid=" + id,
        dataType: "json",
        success: function(data){
        	map.setZoom(16);  // 先修改地图缩放等级
        	map.clearOverlays();  // 删除覆盖物
        	reloadMarkerDrayControl();  // 重新加载标注拖动插件
        	
        	setTimeout(function(){
        		var point = new BMap.Point(data.lon, data.lat);
            	map.panTo(point);  // 定位
            	var myIcon;
            	if(num == 0){
            		myIcon = new BMap.Icon(getFszIcon('0'), new BMap.Size(30, 30));
            	}else{
            		myIcon = new BMap.Icon(getFszIcon('1'), new BMap.Size(30, 30));
            	}
                
                var marker = new BMap.Marker(point, {icon: myIcon}); // 创建标注
                //marker.enableDragging();  // 开启拖拽
                
                map.addOverlay(marker); // 将标注添加到地图中
                
                getInfoWindow(marker, data);  // 初始化信息窗口
                
                addEventForStationMarker(marker, data);  // 给发射站标注添加事件
                
                city.markerMap[data.id] = marker;  // 保存标注
        	}, 10);
        }
    });
}

/**
 * 添加发射站标注
 * 
 * @param data
 * @param status
 */
function addStationMarker4Query(data, status){
	var point = new BMap.Point(data.lon, data.lat);
	var myIcon = new BMap.Icon(getFszIcon(status), new BMap.Size(60, 30));
    var marker = new BMap.Marker(point, {icon: myIcon}); // 创建标注
    //marker.enableDragging();  // 开启拖拽
    
    map.addOverlay(marker); // 将标注添加到地图中
    
    initInfoWindow(marker, data);  // 初始化信息窗口
    
    addEventForStationMarker(marker, data);  // 给发射站标注添加事件
    
    city.markerMap[data.id] = marker;  // 保存标注
    
	refreshAreaPannel(queryParamId);
}

/**
 * 修改发射站标注信息
 * 
 * @param data
 */
function editStationMarker4Query(data){
	var marker = city.markerMap[data.id];
	addEventForStationMarker(marker, data);
	if(map.getInfoWindow() == '[object InfoWindow]'){  // 信息窗口开启
		getInfoWindow(marker, data);
	}
	
	refreshAreaPannel(queryParamId);
}

/**
 * 刷新区域查询面板
 * 
 * @param queryParamId  最近一次查询的区域id
 */
function refreshAreaPannel(queryParamId){
	if(queryType == 0){  // 对设备进行相关操作时不刷新区域面板
		return ;
	}
	
	$('#select_content_result').html("");
	$(".content_result").css("display", "block");
	
    $.ajax({
        type: "GET",
        url: restUrl + "report/area?id=" + queryParamId,
        dataType: "json",
        success: function(data){
			var html='';
            html = '<li class="select_quyu_result_li "><ul class="quyu_ul quyu_ul_title"><li class="quyu_li">区域名称</li><li class="quyu_li">总数（设备）</li><li class="quyu_li">正常</li><li class="quyu_li">警告</li></ul></li>';
            var a=data.length;
            if(a==0){
                html += '<li class="select_result_li"><ul class="quyu_ul"><li class="quyu_li quyu_li_0">没有搜索到相关内容</li></ul></li>';
            } else{
                $.each(data, function(commentIndex, comment){
                    if(commentIndex==0){
                        html += '<li class="select_result_li "><ul class="quyu_ul quyu_ul_action"><li class="quyu_li ">'+comment['name']+'</li><li class="quyu_li">'+comment['sum']+'</li><li  class="quyu_li" >'+comment['normal']+'</li><li class="quyu_li quyu_action action_open">'+comment['alarm']+'</li></ul>';
                        html+='<ul class="chl_content" style="display: block;"><li class="chl_li chl_title chl_li_01">发射站</li><li class="chl_li chl_title chl_li_02">总数</li><li class="chl_li chl_title chl_li_02">正常</li><li class="chl_li chl_title chl_li_03 ">警告</li></ul>';
                        var new_data=comment['sList'];
                        $.each(new_data, function(commentIndex, comment){
                            html+='<ul class="chl_content" style="display: block;" onclick="getStationInfoWindowForQuery(' + '\'' + comment['id'] + '\'' + ',' + '\'' +comment['alarm'] + '\'' + ')"><li class="chl_li chl_li_01">'+comment['name']+'</li><li class="chl_li chl_li_02">'+comment['sum']+'</li><li class="chl_li chl_li_02">'+comment['normal']+'</li><li class="chl_li chl_li_03">'+comment['alarm']+'</li></ul>';
                        });
                        html+='</li>';
                    }else{
                        html += '<li class="select_result_li "><ul class="quyu_ul quyu_ul_action"><li class="quyu_li ">'+comment['name']+'</li><li class="quyu_li">'+comment['sum']+'</li><li  class="quyu_li" >'+comment['normal']+'</li><li class="quyu_li quyu_action action_close">'+comment['alarm']+'</li></ul>';
                        html+='<ul class="chl_content"><li class="chl_li chl_title chl_li_01">发射站</li><li class="chl_li chl_title chl_li_02">总数</li><li class="chl_li chl_title chl_li_02">正常</li><li class="chl_li chl_title chl_li_03 ">警告</li></ul>';
                        var new_data=comment['sList'];
                        $.each(new_data, function(commentIndex, comment){
                            html+='<ul class="chl_content" onclick="getStationInfoWindowForQuery(' + '\'' + comment['id'] + '\'' + ',' + '\'' + comment['alarm'] + '\'' + ')"><li class="chl_li chl_li_01">'+comment['name']+'</li><li class="chl_li chl_li_02">'+comment['sum']+'</li><li class="chl_li chl_li_02">'+comment['normal']+'</li><li class="chl_li chl_li_03">'+comment['alarm']+'</li></ul>';
                        });
                        html+='</li>';
                    }
                });
            }
            $('#select_content_result').append(html);
            $('#select_content_result').find(".quyu_ul_action").on("click",function(){
                if($(this).find(".quyu_action").hasClass("action_open")){
                	$(this).find(".quyu_action").removeClass("action_open");
                	$(this).find(".quyu_action").addClass("action_close");
                	$(this).find(".quyu_action").parent().parent().find('.chl_content').css({"display":"none"});
                    return;
                }
                if($(this).find(".quyu_action").hasClass("action_close")){
                	$(this).find(".quyu_action").removeClass("action_close");
                	$(this).find(".quyu_action").addClass("action_open");
                	$(this).find(".quyu_action").parent().parent().find('.chl_content').css({"display":"block"});
                    return;
                }
            });
            $('#select_content_result').find(".chl_content").on("click",function(){
            	$('#select_content_result .chl_content').removeClass("clicked");
            	$(this).addClass("clicked");
            });
        }
    });
}

/*
 * 获取当前用户下所有发射站
 * 
 */
function getAllStation4Query(){
	$.ajax({
        type: "GET",
        url: restUrl + "station/all/info",
        dataType: "json",
        success: function(datas){
        	map.clearOverlays();  // 删除覆盖物
        	var data;
        	$.each(datas, function(i) {
        		data = datas[i];
        		var point = new BMap.Point(data.lon, data.lat);
            	var myIcon;
            	if(data.alarm == 0){  // 没有告警
            		myIcon = new BMap.Icon(getFszIcon('0'), new BMap.Size(30, 30));
            	}else{
            		myIcon = new BMap.Icon(getFszIcon('1'), new BMap.Size(30, 30));
            	}
                var marker = new BMap.Marker(point, {icon: myIcon}); // 创建标注
                //marker.enableDragging();  // 开启拖拽
                
                map.addOverlay(marker); // 将标注添加到地图中
                
                addEventForStationMarker(marker, data);  // 给发射站标注添加事件
                
                city.markerMap[data.id] = marker;  // 保存标注
        	});
        }
    });
}
