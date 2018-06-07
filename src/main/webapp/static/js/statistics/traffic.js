function log(des, value) {
	try {
		console.info(new Date() + "%c" + des, "color:blue; font-weight:bold",
				value);
	} catch (e) {
	}
}
$("#queryButton").click(function() {
	doStatistics();
});

$("#resetButton").click(function() {
	$("#startDateM").css('display','none');
	$("#startDateD").css('display','inline-block');
});

$(function() {
	radioChange();
});

//设置时间格式
function radioChange(){
	$("#commonForm").css('white-space','nowrap');
	var type = $("input[name='DateType']:checked").val();
	if(type =="DateTypeD"){
		$("#startDateM").css('display','none');
		$("#startDateD").css('display','inline-block');
	}else if(type == "DateTypeM"){
		$("#startDateD").css('display','none');
		$("#startDateM").css('display','inline-block');
	}
}

function doStatistics(){
	var ctx = "/" + window.location.pathname.split("/")[1];
	var startDateStr;
	var endDateStr;
	if( $("input[name='DateType']:checked").val() == "DateTypeD" ){
		if($("#startDateD").val().length == 0){
			startDateStr = new Date().format("yyyy-MM-dd") + " 00:00:00" ;
			endDateStr = new Date().format("yyyy-MM-dd") +" 23:59:59";
		}else{
			startDateStr = $("#startDateD").val() + " 00:00:00" ;
			endDateStr = $("#startDateD").val() +" 23:59:59";
		}
	}else{
		if($("#startDateM").val().length == 0){
			startDateStr = new Date().format("yyyy-MM") + "-01 00:00:00" ;
			endDateStr = new Date().format("yyyy-MM") + "-"
			+ getLastDay(new Date().format("yyyy-MM").split("-")[0],
					new Date().format("yyyy-MM").split("-")[1]) +" 23:59:59" ;
		}else{
			startDateStr = $("#startDateM").val() + "-01 00:00:00" ;
			endDateStr = $("#startDateM").val() + "-"
					+ getLastDay($("#startDateM").val().split("-")[0],
							$("#startDateM").val().split("-")[1]) +" 23:59:59" ;
		}
	}
	
	var chk_value =[];    
	  $('input[name="sessiontype"]:checked').each(function(){    
	   chk_value.push($(this).val());    
	  });    
	  
	$.ajax({
		url :  ctx + "/rs/statistic/item/getTrafficStats",
		data : {
			startDate : startDateStr,
			endDate : endDateStr,
			sessionTypeStr : chk_value.join(","),
		},
		type : "GET",
		async : false,
		success : function(data) {
			var chartData = data.trafficStatistics;
			var yFieldData = data.sessiontype;
			log("data",data);
			var chart = new AmCharts.AmSerialChart();
			chart.dataProvider = chartData;
			chart.valueAxes = [{
		        "integersOnly": true,
//		        "maximum": ,
		        "minimum": 0,
		        "axisAlpha": 0,
		        "dashLength": 5,
		        "gridCount": 10,
		        "position": "left",
		    }];
			chart.startDuration = 0.5;
			chart.categoryField = "date";
			chart.categoryAxis.dashLength = 1;  
			chart.categoryAxis.gridAlpha = 0.15;  
			chart.categoryAxis.labelRotation = 40; 
			for( i in yFieldData){
				var sessiontype = yFieldData[i].split("-");
				var type = sessiontype[0].split("CallTraffic");
				var graph = new AmCharts.AmGraph();  
                graph.title = sessiontype[1];  
                graph.valueField = sessiontype[0];  
                graph.balloonText = sessiontype[1]+" [[category]] \n 话务量 : [[value]]爱尔兰  \n 平均通话次数 : [["+type[0]+"CallNoOfTime"+"]] 次 \n 平均通话时长 : [["+type[0]+"CallDuration"+"]] 毫秒";  
                graph.bullet = "round";  
                graph.fillAlphas = 0;
                chart.addGraph(graph);  
			}
			chart.chartCursor = {
			        "cursorAlpha": 0,
			        "cursorPosition": "mouse",
			        "zoomable": false
			    };
			
			chart.categoryAxis = {
			        "gridPosition": "start",
			     //   "labelRotation" : 90,设置横坐标偏转
			        "axisAlpha": 0,
			        "fillAlpha": 0.05,
			        "fillColor": "#000000",
			        "gridAlpha": 0,
			    };
			chart.exportConfig = {
					"menuTop":"20px",
			        "menuRight":"20px",
			        "menuItems": [{
			            "icon":  ctx + '/static/libraries/charts/images/export.png',
			            "format": 'png' }]
				    };
            chart.legend = {
		        "useGraphSettings": true,
		        "valueWidth" : 75,
		    };  
            
			chart.write("chartdiv");

		}
	});
	
}
