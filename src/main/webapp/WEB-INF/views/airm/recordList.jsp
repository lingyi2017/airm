<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>记录列表</title>
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

            initTableHeader();
        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function initTableHeader() {
            var deviceId = $("#deviceId").val();
            $.ajax({
                url: "/airm/rs/device/info",
                data: {
                    deviceId: deviceId
                },
                type: "GET",
                dataType: "JSON",
                async: false,
                success: function (datas) {
                    if (datas.success) {
                        var header = datas.content;
                        if (header) {
                            var headerHTML = "<tr>";
                            headerHTML += "<th>" + header.sensorName1 + "</th>";
                            headerHTML += "<th>" + header.sensorName2 + "</th>";
                            headerHTML += "<th>" + header.sensorName3 + "</th>";
                            headerHTML += "<th>" + header.sensorName4 + "</th>";
                            headerHTML += "<th>" + header.sensorName5 + "</th>";
                            headerHTML += "<th>" + header.sensorName6 + "</th>";
                            headerHTML += "<th>" + header.sensorName7 + "</th>";
                            headerHTML += "<th>" + header.sensorName8 + "</th>";
                            headerHTML += "<th>" + header.sensorName9 + "</th>";
                            headerHTML += "<th>" + header.sensorName10 + "</th>";
                            headerHTML += "<th>" + header.sensorName11 + "</th>";
                            headerHTML += "<th>" + header.sensorName12 + "</th>";
                            headerHTML += "<tr>"
                            $("#js-head").html(headerHTML);
                        }
                    }
                }
            });
        }

    </script>
</head>
<body>
<tags:message content="${message}"/>
<form:form id="searchForm" modelAttribute="record" action="${ctx}/airm/map/record/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
    <input id="deviceId" name="deviceId" type="hidden" value="${deviceId}"/>

    <div style="margin-top:8px;">
        <label>开始时间：</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20"
                                   class="input-small Wdate"
                                   value="${beginDate}"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        <label>结束时间：</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20"
                                   class="input-small Wdate"
                                   value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />"
               onclick="return page();"/>
    </div>
</form:form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead id="js-head">
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="record">
        <tr>
            <td>${record.sensorVal1}</td>
            <td>${record.sensorVal2}</td>
            <td>${record.sensorVal3}</td>
            <td>${record.sensorVal4}</td>
            <td>${record.sensorVal5}</td>
            <td>${record.sensorVal6}</td>
            <td>${record.sensorVal7}</td>
            <td>${record.sensorVal8}</td>
            <td>${record.sensorVal9}</td>
            <td>${record.sensorVal10}</td>
            <td>${record.sensorVal11}</td>
            <td>${record.sensorVal12}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>