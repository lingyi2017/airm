<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>传感器信息</title>
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
    <li><a href="${ctx}/airm/sensor/list">传感器列表</a></li>
    <shiro:hasPermission name="airm:sensor:edit">
        <li class="active"><a>传感器${not empty sensor.id ? '修改' : '添加'}</a></li>
    </shiro:hasPermission>
</ul>

<form:form id="inputForm" modelAttribute="sensor"
           action="${ctx }/airm/sensor/${empty sensor.id ? 'save' : 'update' }" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <tags:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">名称:</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="16" rangelength="2,16" class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">编号:</label>
        <div class="controls">
            <form:input path="serialNum" htmlEscape="false"/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="airm:sensor:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='save' />"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="<spring:message code='return' />"
               onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>