<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/info"><spring:message code="user.information" /></a></li>
		<li><a href="${ctx}/sys/user/modifyPwd"><spring:message code="update.password" /></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal">
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><spring:message code="user.name" />:</label>
			<div class="controls">
				<label class="lbl">${user.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code='user.nick' />:</label>
			<div class="controls">
				<label class="lbl">${user.nick}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
				<label class="lbl">${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="email" />:</label>
			<div class="controls">
				<label class="lbl">${user.email}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="telephone" />:</label>
			<div class="controls">
				<label class="lbl">${user.phone}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="memo" />:</label>
			<div class="controls">
				<label class="lbl">${user.remarks}</label>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label"><spring:message code="user.role" />:</label>
			<div class="controls">
				<label class="lbl">${user.roleNames}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="last.login" />:</label>
			<div class="controls">
				<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="last.login.time" />：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>
		<div class="form-actions">
		</div>
	</form:form>
</body>
</html>