]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='role.manage' /></title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/role/"><spring:message code='role.list' /></a></li>
		<shiro:hasPermission name="sys:role:edit"><li><a href="${ctx}/sys/role/form"><spring:message code='role.add' /></a></li></shiro:hasPermission>
	</ul>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th><spring:message code='role.name' /></th><shiro:hasPermission name="sys:role:edit"><th><spring:message code='operation' /></th></shiro:hasPermission></tr>
		<c:forEach items="${list}" var="role">
			<tr>
				<td><a href="form?id=${role.id}">${role.name}</a></td>
				<shiro:hasPermission name="sys:role:edit"><td>
					<a href="${ctx}/sys/role/assign?id=${role.id}" style="display: none;"><spring:message code='assign' /></a>
					<a href="${ctx}/sys/role/form?id=${role.id}"><spring:message code='update' /></a>
					<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)"><spring:message code='delete' /></a>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
	</table>
</body>
</html>