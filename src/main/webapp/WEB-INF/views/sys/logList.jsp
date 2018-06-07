<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='log.manage' /></title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/log/"><spring:message code='log.list' /></a></li>
	</ul>
	<form:form id="searchForm" action="${ctx}/sys/log/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label><spring:message code='user.id' />：</label><input id="createById" name="createById" type="text" maxlength="50" class="input-small" value="${createById}"/>
			<label>URI：</label><input id="requestUri" name="requestUri" type="text" maxlength="50" class="input-small" value="${requestUri}"/>
			&nbsp;
			<label><spring:message code='start.date' />：</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<label><spring:message code='end.date' />：</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
				value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<label for="exception"><input id="exception" name="exception" type="checkbox"${exception eq '2'?' checked':''} value="2"/><spring:message code='exception.info' /></label>
			&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="<spring:message code='query' />"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><spring:message code='organization' /></th><th><spring:message code='department' /></th><th><spring:message code='operation.user' /></th><th>URI</th><th><spring:message code='submit.type' /></th><th><spring:message code='operatives.IP' /></th><th><spring:message code='creation.time' /></th></thead>
		<tbody>
		<c:forEach items="${page.list}" var="log">
			<tr>
				<td>${log.createBy.company.name}</td>
				<td>${log.createBy.office.name}</td>
				<td>${log.createBy.name}</td>
				<td><strong>${log.requestUri}</strong></td>
				<td>${log.method}</td>
				<td>${log.remoteAddr}</td>
				<td><fmt:formatDate value="${log.createDate}" type="both"/></td>
			</tr>
			<tr>
				<td colspan="8"><spring:message code='user.proxy' />: ${log.userAgent}<br/><spring:message code='submit.parameter' />: ${fns:escapeHtml(log.params)}
				<c:if test="${not empty log.exception}"><br/><spring:message code='exception.info' />: <br/><%request.setAttribute("strEnter", "\n"); %>
				${fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>')}</c:if></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>