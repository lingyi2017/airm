<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>设备信息</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                }
            });
        });
    </script>
</head>
<body>

<ul class="nav nav-tabs">
    <li><a href="${ctx}/airm/device/list">设备列表</a></li>
    <shiro:hasPermission name="airm:device:edit">
        <li class="active"><a>设备${not empty device.id ? '修改' : '添加'}</a></li>
    </shiro:hasPermission>
</ul>

<form:form id="inputForm" modelAttribute="device"
           action="${ctx }/airm/device/${empty device.id ? 'save' : 'update' }" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <tags:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">检测站:</label>
        <div class="controls">
            <form:input path="station" htmlEscape="false" maxlength="16" rangelength="2,16" class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">设备地址:</label>
        <div class="controls">
            <form:input path="address" htmlEscape="false" maxlength="256" class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">状态:</label>
        <div class="controls">
            <label class="lbl">${fns:getDictLabel(device.status, 'device_status', '无')}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">经度:</label>
        <div class="controls">
            <label class="lbl">${device.lon}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">纬度:</label>
        <div class="controls">
            <label class="lbl">${device.lat}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器1名称:</label>
        <div class="controls">
            <label class="lbl">${device.sensorName1}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器1单位:</label>
        <div class="controls">
            <label class="lbl">${device.sensorUnit1}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器2名称:</label>
        <div class="controls">
            <label class="lbl">${device.sensorName2}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器2单位:</label>
        <div class="controls">
            <label class="lbl">${device.sensorUnit2}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器3名称:</label>
        <div class="controls">
            <label class="lbl">${device.sensorName3}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器3单位:</label>
        <div class="controls">
            <label class="lbl">${device.sensorUnit3}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器4名称:</label>
        <div class="controls">
            <label class="lbl">${device.sensorName4}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器4单位:</label>
        <div class="controls">
            <label class="lbl">${device.sensorUnit4}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器5名称:</label>
        <div class="controls">
            <label class="lbl">${device.sensorName5}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器5单位:</label>
        <div class="controls">
            <label class="lbl">${device.sensorUnit5}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器6名称:</label>
        <div class="controls">
            <label class="lbl">${device.sensorName6}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器6单位:</label>
        <div class="controls">
            <label class="lbl">${device.sensorUnit6}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器7名称:</label>
        <div class="controls">
            <label class="lbl">CO2</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器7单位:</label>
        <div class="controls">
            <label class="lbl">PPM</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器8名称:</label>
        <div class="controls">
            <label class="lbl">PM1.0</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器8单位:</label>
        <div class="controls">
            <label class="lbl">μg/m3</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器9名称:</label>
        <div class="controls">
            <label class="lbl">PM2.5</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器9单位:</label>
        <div class="controls">
            <label class="lbl">μg/m3</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器10名称:</label>
        <div class="controls">
            <label class="lbl">PM10</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器10单位:</label>
        <div class="controls">
            <label class="lbl">μg/m3</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器11名称:</label>
        <div class="controls">
            <label class="lbl">温度</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器11单位:</label>
        <div class="controls">
            <label class="lbl">℃</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器12名称:</label>
        <div class="controls">
            <label class="lbl">湿度</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">传感器12单位:</label>
        <div class="controls">
            <label class="lbl">%RH</label>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="airm:device:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />"
               onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>