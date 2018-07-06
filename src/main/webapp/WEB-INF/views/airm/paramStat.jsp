<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>报表统计</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <style type="text/css">
    </style>
    <script type="text/javascript">
        $(function () {
            $('#device').change(function () {
                var deviceId = $(this).val();
                $.ajax({
                    url: "/airm/rs/stat/device/sensors",
                    data: {"deviceId": deviceId},
                    type: "GET",
                    dataType: "JSON",
                    success: function (data) {
                        if (!data.success) {
                            return;
                        }

                        // 删除之前option
                        $("#sensors").find("option:selected").remove();
                        $("#sensors").html("");

                        var sensors = data.content;
                        $.each(sensors, function (index, sensor) {
                            if (index == 0) {
                                $("#sensors").append("<option selected='selected' value='" + sensor.num + "'>" + sensor.name + "</option>");
                            }
                            $("#sensors").append("<option value='" + sensor.num + "'>" + sensor.name + "</option>");
                        });
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="breadcrumb form-search">
    <div style="margin-top:8px;margin-bottom: 8px;">
        <select name="device" id="device" style="width: 160px;">
            <c:forEach items="${devices}" var="device">
                <option value="${device.deviceId}">${device.name}</option>
            </c:forEach>
        </select>&nbsp;
        <select name="sensors" id="sensors" style="width: 160px;">
            <c:forEach items="${sensors}" var="sensor">
                <option value="${sensor.num}">${sensor.name}</option>
            </c:forEach>
        </select>
        <label>日期：</label><input id="date" name="date" type="text" readonly="readonly" maxlength="20"
                                 class="input-small Wdate" value="${date}"
                                 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
        <input id="btnSubmit" class="btn btn-primary" type="button" value="统计" onclick="report();"/>
    </div>
</div>

<div id="main" style="height:400px"></div>

<script src="http://echarts.baidu.com/build/dist/echarts-all.js"></script>
<script type="text/javascript">

    var myChart = echarts.init(document.getElementById('main'));
    $(document).ready(function () {
        report();
    });

    function report() {
        var deviceId = $("#device").val();
        var sensorNum = $("#sensors").val();
        var date = $("#date").val();
        var param = {"deviceId": deviceId, "sensorNum": sensorNum, "date": date};
        $.ajax({
            url: "/airm/rs/stat/device/params",
            data: param,
            type: "GET",
            dataType: "JSON",
            success: function (data) {
                var echarts = data.content;
                option = {
                    title: {
                        text: '数据统计'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            saveAsImage: {show: true},
                            magicType: {show: true, type: ['line', 'bar']}
                        }
                    },
                    calculable: true,
                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: false,
                            data: echarts.xData
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            axisLabel: {
                                formatter: '{value} ' + echarts.series.yUnit
                            }
                        }
                    ],
                    series: [
                        {
                            name: echarts.series.name,
                            type: 'line',
                            data: echarts.series.data,
                            markPoint: {
                                data: [
                                    {type: 'max', name: '最大值'},
                                    {type: 'min', name: '最小值'}
                                ]
                            },
                            markLine: {
                                data: [
                                    {type: 'average', name: '平均值'}
                                ]
                            }
                        }
                    ]
                };
                myChart.setOption(option);
            }
        });
    }
</script>
</body>
</html>