<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title><spring:message code='organization.manage' /></title>
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
		<li class="active"><a href="${ctx}/sys/office/"><spring:message code='organization.list' /></a></li>
		<shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/form"><spring:message code='organization.add' /></a></li></shiro:hasPermission>
	</ul>
	<tags:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<tr><th><spring:message code='organization.name' /></th><th><spring:message code='organization.district' /></th><th><spring:message code='organization.number' /></th><th><spring:message code='organization.type' /></th><th><spring:message code='memo' /></th><shiro:hasPermission name="sys:office:edit"><th><spring:message code='operation' /></th></shiro:hasPermission></tr>
		<c:forEach items="${list}" var="office">
			<tr id="${office.id}" pId="${office.parent.id ne requestScope.office.id?office.parent.id:'0'}">
				<td><a href="${ctx}/sys/office/form?id=${office.id}">${office.name}</a></td>
				<td>${office.area.name}</td>
				<td>${office.code}</td>
				<td>${fns:getDictLabel(office.type, 'sys_office_type', '无')}</td>
				<td>${office.remarks}</td>
				<shiro:hasPermission name="sys:office:edit"><td>
					<a href="${ctx}/sys/office/form?id=${office.id}"><spring:message code='update' /></a>
					<a href="${ctx}/sys/office/delete?id=${office.id}" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)"><spring:message code='delete' /></a>
					<a href="${ctx}/sys/office/form?parent.id=${office.id}"><spring:message code='add.child.organization' /></a> 
				</td></shiro:hasPermission>
				<td class="officetype" style="display:none;">${office.type}</td>
			</tr>
		</c:forEach>
	</table>
	<script type="text/javascript">
		//log
		function log(des, value) {
			try {
				console.info(new Date() + "%c" + des, "color:blue; font-weight:bold", value);
			} catch (e) {
			}
		}
	
		$(document).ready(function() {
			$(".officetype").each(function(){
			if($(this).text()=="2"){
				log("rowdata", $(this).prev().children().next().next());
				$(this).prev().children().next().next().css('display','none');
			}
			});
		});
	</script>
</body>
</html>