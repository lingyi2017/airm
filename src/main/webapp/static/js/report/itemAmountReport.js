$(function() {

	var CONFIG = {
			typeTitle: {
				daily: "日报数据",
				weekly: "周报数据",
				monthly: "月报数据"
			}
	}
	
    function query() {
    	
    	$(".j-title").text(CONFIG.typeTitle[$(".j-choose-type:checked").attr("data-type")]);
    	
    	//生成统计表格
        $.ajax({
            url : ctx + "/rs/report/item/amountChart",
            data : {
//                beginDate : $("#startDate").val()==""? "1987-01-01 00:00:00":$("#startDate").val(),// 字符日期转毫秒
//                endDate : $("#endDate").val()==""? new Date().format("yyyy-MM-dd hh:mm:ss"):$("#endDate").val(),
            	role: $(".j-choose-user:checked").attr("data-type"),
            	type: "table",
            	beginDate: $(".j-query-bar .Wdate:visible").eq(0).val(),// 字符日期转毫秒
            	endDate: $(".j-query-bar .Wdate:visible").eq(1).val(),
            	period: $(".j-choose-type:checked").attr("data-type")
            },
            type : "GET",
            success : function(data) {
            	
            	//生成统计表格
            	var html = "", t1 = 0, t2 = 0;
            	
            	//补全为空的数据，以保证数据格式是符合echart需要的 
                for(var i = 0; i < data.series.length; i++){
                	var len = data.series[i].data.length;
                	for(var j = 0; j < data.axis.length - len; j++){
                		var newLen = data.series[i].data.length;
                		data.series[i].data.splice(newLen - 1, 0, 0);
                	}
                }
            	
            	for(var i = 0; i < data.axis.length; i++){
            		var c1 = data.series[0].data[i] || 0,
            			c2 = data.series[1].data[i] || 0,
            			c3 = data.series[2].data[i] || 0,
            			c4 = data.series[3].data[i] || 0;
            		html += "<tr><td>" + data.axis[i] + "</td><td>" + c1 + "</td><td>" + c2 + "</td><td>" + c3 + "</td><td>" + c4 + "</td></tr>";
            	}
//            	html += "<tr><td>总计</td><td>" + t1 + "</td><td>" + t2 + "</td></tr>";
                $(".j-tbody").html(html);
                
            }

        });
        
        //生成统计折线图 
        $.ajax({
            url : ctx + "/rs/report/item/amountChart",
            data : {
//                beginDate : $("#startDate").val()==""? "1987-01-01 00:00:00":$("#startDate").val(),// 字符日期转毫秒
//                endDate : $("#endDate").val()==""? new Date().format("yyyy-MM-dd hh:mm:ss"):$("#endDate").val(),
            	role: $(".j-choose-user:checked").attr("data-type"),
            	type: "chart",
            	beginDate: $(".j-query-bar .Wdate:visible").eq(0).val(),// 字符日期转毫秒
            	endDate: $(".j-query-bar .Wdate:visible").eq(1).val(),
            	period: $(".j-choose-type:checked").attr("data-type")
            },
            type : "GET",
            success : function(data) {
            	
                var option = {
                        tooltip : {
                            trigger: 'axis'
                        },
                        legend: {
                            data:[]
                        },
                        xAxis : [
                            {
                                type : 'category',
//                                boundaryGap : false,
                                data : []
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value'
                            }
                        ],
                        series : []
                    };

                
                //生成统计折线表
             // 路径配置
                require.config({
                    paths: {
                        echarts: 'http://echarts.baidu.com/build/dist'
                    }
                });

                // 使用
                require(
                        [
                            'echarts',
                            'echarts/chart/line' // 使用折线图就加载line模块，按需加载
                        ],
                        function (ec) {
                        	
                            // 基于准备好的dom，初始化echarts图表
                            var myChart = ec.init($(".j-echart")[0]);

                            option.legend.data = data.legend;
                            option.xAxis[0].data = data.axis;
                            
                            //补全为空的数据，以保证数据格式是符合echart需要的 
                            for(var i = 0; i < data.series.length; i++){
                            	var len = data.series[i].data.length;
                            	for(var j = 0; j < data.axis.length - len; j++){
                            		var newLen = data.series[i].data.length;
                            		data.series[i].data.splice(newLen - 1, 0, 0);
                            	}
                            }
                            
                            option.series = data.series;

                            // 为echarts对象加载数据
                            myChart.setOption(option);
                        }
                );
            }

        });
    };

//    query();

    $("#queryButton").bind("click", function() {
    	var ok = true;
    	$(".j-query-bar .Wdate:visible").each(function(index, dom){
    		if(dom.value === ""){
    			ok = false;
    			return false;
    		}
    	});
    	if(ok){
    		query();
    	}else{
    		alert("查询起止时间段不能为空");
    	}
    });


});

