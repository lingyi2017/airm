<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
 var ctx = "${ctx}";
</script>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html" charset="UTF-8" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css?DEV=<%=new java.util.Random().nextInt()%>">
	<script type="text/javascript">
	  function funccc(){
		$dp.$('startDateW').value=$dp.cal.getP('y')+$dp.cal.getP('W','WW'); 
		}
	</script>
	<style>
		.grid{border: 1px solid #D4D4D4;}
        .query-bar .firstRow{padding: 5px 0;}
        .list-table{margin-top: 15px;}
        .list-table .panel-head{padding-left: 5px;}
        .list-table td{border: none !important;}
        .echart-wrap{margin-top: 20px;}
    </style>
</head>

<body>
	<!--页面主体Start-->
	<div class="content">

		<!--面板Start -->
		<div class="panel">
			<div class="panel-head">
				<img class="panel-icon" src="${ctx}/static/images/panel-icon.png" /> <span class="panel-title">商品数据统计</span>
			</div>
			<div class="panel-body">
				<form class="commonForm">
					<table class="facadeTable">
						<tr class="query-bar j-query-bar">
							<td class="firstRow">&nbsp;&nbsp;
							<input id="i3" class="j-choose-type" checked type="radio" name="type" data-type="daily"/><label for="i3">按天统计 </label>&nbsp;
							<input id="i4" class="j-choose-type" type="radio" name="type" data-type="weekly" /><label for="i4">按周统计 </label>&nbsp;
							<input id="i5" class="j-choose-type" type="radio" name="type" data-type="monthly" /><label for="i5">按月统计</label>
							<span class="delimiter"></span>
								统计时间: 
								<input type="text" id="startDateD" class="Wdate" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',isShowClear:false})" onClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',isShowClear:false})" value="${dateD}" />
								至
								<input type="text" id="endDateD" class="Wdate" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',isShowClear:false})" onClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',isShowClear:false})" value="${dateD}" />
								<shiro:hasPermission name="report:item:view">
									<!-- <button id="resetButton" class="functionButton" type="reset">
										<span class="edit"></span>
										重置
									</button> -->
									<button id="queryButton" class="btn btn-primary G-ML5" type="button">
										<span class="find"></span>
										统计
									</button>
								</shiro:hasPermission>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- 面板End -->

		<!-- 表格Start -->
        <div class="list-table panel">
            <h2 class="panel-head j-title">日报数据</h2>
            <div class="G-P10">
	            <table id="summaryTable" class="grid hover">
	                <thead>
	                    <th class="j-title2">时间</th>
	                    <th>新增商品数</th>
	                    <th>上架商品数</th>
	                    <th>下架商品数</th>
	                    <th>积累商品数</th>
	                </thead>
	                <tbody class="j-tbody">
	                </tbody>
	            </table>
            </div>
            <div class="echart-wrap j-echart" style="width: 100%; min-height: 300px;"></div>
        </div>

		<!-- 表格End -->
		<br />
	</div>
	<!--JavaScript libraries Reference Start-->
	<script src="${ctx}/static/jquery/jquery-1.9.1.js"></script>
	<script src="${ctx}/static/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
	<script src="${ctx}/static/js/report/itemAmountReport.js?t=<%=new java.util.Date()%>"></script>
	<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
	<!--JavaScript libraries Reference End-->
</body>
</html>