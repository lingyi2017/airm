<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title><spring:message code=''/><spring:message code='user.manage'/></title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <style type="text/css">.sort {
        color: #0663A2;
        cursor: pointer;
    }</style>
    <script type="text/javascript">
        $(document).ready(function () {
            // 表格排序
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
                    $("#orderBy").val(order + " DESC" != order + " " + sort ? "" : order + " " + sort);
                } else {
                    $("#orderBy").val(order + " ASC");
                }
                page();
            });
            $("#btnExport").click(function () {
                top.$.jBox.confirm("确认要导出用户数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        $("#searchForm").attr("action", "${ctx}/sys/user/export").submit();
                    }
                }, {buttonsFocus: 1});
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });
            $("#btnImport").click(function () {
                $.jBox($("#importBox").html(), {
                    title: "导入数据", buttons: {"关闭": true},
                    bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"
                });
            });
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").attr("action", "${ctx}/sys/user/").submit();
            return false;
        }
    </script>
</head>
<body>
<div id="importBox" class="hide">
    <form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
          style="padding-left:20px;text-align:center;" class="form-search" onsubmit="loading('正在导入，请稍等...');"><br/>
        <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
        <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
        <a href="${ctx}/sys/user/import/template">下载模板</a>
    </form>
</div>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/user/"><spring:message code='user.list'/></a></li>
    <shiro:hasPermission name="sys:user:edit">
        <li><a href="${ctx}/sys/user/form"><spring:message code='user.add'/></a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
    <div>
        <spring:message code='login.name'/>：</label><form:input path="loginName" htmlEscape="false" maxlength="50"
                                                                class="input-small"/>
        <label><spring:message code='telephone'/>：</label><form:input path="phone" htmlEscape="false" maxlength="50"
                                                                      class="input-small"/>
        <label><spring:message code='user.name'/>：</label><form:input path="name" htmlEscape="false" maxlength="50"
                                                                      class="input-small"/>&nbsp;
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />"
               onclick="return page();"/>
    </div>
</form:form>
<tags:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th class="sort login_name"><spring:message code='login.name'/></th>
        <th class="sort name"><spring:message code='user.name'/></th>
        <th><spring:message code='telephone'/></th>
        <th><spring:message code='role'/></th>
        <th>用户类型</th>
        <th>状态</th>
        <shiro:hasPermission name="sys:user:edit">
            <th><spring:message code='operation'/></th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="user">
        <tr>
            <td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
            <td>${user.name}</td>
            <td>${user.phone}</td>
            <td>${user.roleNames}</td>
            <td>${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</td>
            <td>${fns:getDictLabel(user.delFlag, 'del_flag', '无')}</td>
            <shiro:hasPermission name="sys:user:edit">
                <td>
                    <a href="${ctx}/sys/user/form?id=${user.id}"><spring:message code='update'/></a>
                    <a href="${ctx}/sys/user/delete?id=${user.id}"
                       onclick="return confirmx('确认要删除该用户吗？', this.href)"><spring:message code='delete'/></a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<table id="contentTable" class="table">
    <thead>
    <tr>
        <th>测试</th>
        <th>测试</th>
    </tr>
    </thead>
</table>
<div class="pagination">${page}</div>
</body>
</html>