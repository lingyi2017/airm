<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='area.manage' /></title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 5});
		});
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
		<li class="active"><a href="${ctx}/sys/area/"><spring:message code='area.list' /></a></li>
		<shiro:hasPermission name="sys:area:edit"><li><a href="${ctx}/sys/area/form"><spring:message code='area.add' /></a></li></shiro:hasPermission>
	</ul>
	<tags:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<tr><th><spring:message code='area.name' /></th><th><spring:message code='area.number' /></th><th><spring:message code='area.type' /></th><th><spring:message code='memo' /></th><shiro:hasPermission name="sys:area:edit"><th><spring:message code='operation' /></th></shiro:hasPermission></tr>
		<c:forEach items="${list}" var="area">
			<tr id="${area.id}" pId="${area.parent.id ne requestScope.area.id?area.parent.id:'0'}">
				<td><a href="${ctx}/sys/area/form?id=${area.id}">${area.name}</a></td>
				<td>${area.code}</td>
				<td>${fns:getDictLabel(area.type, 'sys_area_type', '无')}</td>
				<td>${area.remarks}</td>
				<shiro:hasPermission name="sys:area:edit">
					<td>
						<a href="${ctx}/sys/area/form?id=${area.id}"><spring:message code='update' /></a>
						<a href="${ctx}/sys/area/delete?id=${area.id}" onclick="return confirmx('要删除该区域及所有子区域项吗？', this.href)"><spring:message code='delete' /></a>
						<a href="${ctx}/sys/area/form?parent.id=${area.id}"><spring:message code='add.child.area' /></a> 
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
	</table>
</body>
</html>