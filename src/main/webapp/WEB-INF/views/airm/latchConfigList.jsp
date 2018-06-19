<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>门限配置</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <style type="text/css">.sort {
        color: #0663A2;
        cursor: pointer;
    }</style>
    <script type="text/javascript">
        $(document).ready(function () {
            var orderBy = $("#orderBy").val().split(" ");
            $("#contentTable th.sort").each(function () {
                if ($(this).hasClass(orderBy[0])) {
                    orderBy[1] = orderBy[1] && orderBy[1].toUpperCase() == "DESC" ? "down" : "up";
                    $(this).html($(this).html() + " <i class=\"icon icon-arrow-" + orderBy[1] + "\"></i>");
                }
            });

            $("#contentTable th.sort").click(function () {
                var order = $(this).attr("class").split(" ");
                var sort = $("#orderBy").val().split(" ");
                for (var i = 0; i < order.length; i++) {
                    if (order[i] == "sort") {
                        order = order[i + 1];
                        break;
                    }
                }
                if (order == sort[0]) {
                    sort = (sort[1] && sort[1].toUpperCase() == "DESC" ? "ASC" : "DESC");
                    $("#orderBy").val(order + " " + sort);
                } else {
                    $("#orderBy").val(order + " ASC");
                }
                page();
            });
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>

<ul class="nav nav-tabs">
    <li class="active"><a>配置列表</a></li>
    <shiro:hasPermission name="airm:latchConfig:edit">
        <li><a href="${ctx}/airm/latchConfig/form">配置添加</a></li>
    </shiro:hasPermission>
</ul>
<tags:message content="${message}"/>
<form:form id="searchForm" modelAttribute="latchConfig" action="${ctx}/airm/latchConfig/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>

    <div style="margin-top:8px;">
        <label>名称：</label><form:input path="name" htmlEscape="false" maxlength="16" class="input-small"/>&nbsp;
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />"
               onclick="return page();"/>
    </div>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th class="sort a.name">名称</th>
        <th>编号</th>
        <th>门限值</th>
        <shiro:hasPermission name="airm:latchConfig:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="latchConfig">
        <tr>
            <td>${latchConfig.name}</td>
            <td>${latchConfig.serialNum}</td>
            <td>${latchConfig.maxVal}</td>
            <shiro:hasPermission name="airm:latchConfig:edit">
                <td>
                    <a class="btn btn-primary btn-mini"
                       href="${ctx}/airm/latchConfig/form?id=${latchConfig.id}"><spring:message code='update'/></a>
                    <a class="btn btn-mini" href="${ctx}/airm/latchConfig/delete?id=${latchConfig.id}"
                       onclick="return confirmx('确认要删除该配置吗？', this.href)"><spring:message code='delete'/></a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>