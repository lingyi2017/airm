package com.infosoul.mserver.webservice.rest.report;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.infosoul.mserver.common.mapper.JsonMapper;
import com.infosoul.mserver.common.utils.Collections3;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.service.report.ItemReportService;
import com.infosoul.mserver.vo.report.AxisVo;
import com.infosoul.mserver.vo.report.Echarts;
import com.infosoul.mserver.vo.report.Series;
import com.infosoul.mserver.webservice.rest.Result.Status;

@Path("/report/item")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class ItemReportRestResource {

    @Autowired
    ItemReportService thisService;

    private static Logger logger = LoggerFactory.getLogger(ItemReportRestResource.class);

    /**
     * 商品数据统计chart/table
     * 
     * @param startDate
     * @param endDate
     * @param period
     *            : daily,weekly,monthly
     * @param type
     *            : chart/table
     * @return
     */
    @GET
    @Path("/amountChart")
    public String getItemReport(
            @QueryParam("beginDate") String beginDate, 
            @QueryParam("endDate") String endDate,
            @DefaultValue("daily") @QueryParam("period") String period,
            @DefaultValue("chart") @QueryParam("type") String type) {
        try {
            // beginDate = "2015-01-01";
            // endDate = "2015-02-28";
            // timeType = "monthly";
            // reportType = "table";
            if (StringUtils.isBlank(beginDate) || StringUtils.isBlank(endDate)) {
                String errorString = JsonMapper.getInstance().toJson(
                        Result.buildErrorResult(Status.BAD_REQUEST, "未输入起止时间！"));
                return errorString;
            }

            List<String> legendDataList = Lists.newArrayList();
            if (StringUtils.equals(type, "table")) {
                legendDataList.add("时间");
            }
            legendDataList.add("新增商品数");
            legendDataList.add("上架商品数");
            legendDataList.add("下架商品数");
            legendDataList.add("商品库数"); // 累积商品数

            // X轴数据：时间值
            List<String> xAxisDataList = Lists.newArrayList();
            List<Integer> itemNewData = Lists.newArrayList();
            List<Integer> itemUpData = Lists.newArrayList();
            List<Integer> itemDownData = Lists.newArrayList();
            List<Integer> itemSumData = Lists.newArrayList();
            // 日报、周报、月报
            List<AxisVo> itemNewList = Lists.newArrayList();
            List<AxisVo> itemUpList = Lists.newArrayList();
            List<AxisVo> itemDownList = Lists.newArrayList();
            if (StringUtils.equals(period, "daily")) {
                itemNewList = thisService.getItemNewAmountByDaily(beginDate, endDate);
                itemUpList = thisService.getItemUpDownAmountByDaily(beginDate, endDate, "0");
                itemDownList = thisService.getItemUpDownAmountByDaily(beginDate, endDate, "1");
            } else if (StringUtils.equals(period, "weekly")) {
                itemNewList = thisService.getItemNewAmountByWeekly(beginDate, endDate);
                itemUpList = thisService.getItemUpDownAmountByWeekly(beginDate, endDate, "0");
                itemDownList = thisService.getItemUpDownAmountByWeekly(beginDate, endDate, "1");
            } else if (StringUtils.equals(period, "monthly")) {
                itemNewList = thisService.getItemNewAmountByMonthly(beginDate, endDate);
                itemUpList = thisService.getItemUpDownAmountByMonthly(beginDate, endDate, "0");
                itemDownList = thisService.getItemUpDownAmountByMonthly(beginDate, endDate, "1");
            }
            if (!Collections3.isEmpty(itemNewList)) {
                for (int i = 0; i < itemNewList.size(); i++) {
                    AxisVo axisVo = itemNewList.get(i);
                    xAxisDataList.add(axisVo.getxAxisData());
                    itemNewData.add(axisVo.getyAxisData());
                    // 求 item累加数
                    int sum = axisVo.getyAxisData();
                    if (i == 0) {
                        itemSumData.add(i, sum);
                    } else {
                        itemSumData.add(i, sum += itemSumData.get(i - 1));
                    }
                }
                // 新增商品数sum
                if (StringUtils.equals(type, "table")) {
                    int sum = 0;
                    for (Integer iii : itemNewData) {
                        int temp = iii;
                        sum += temp;
                    }
                    itemNewData.add(sum);

                    int itemSumDataSum = 0;
                    for (Integer iii : itemSumData) {
                        int temp = iii;
                        itemSumDataSum += temp;
                    }
                    itemSumData.add(itemSumDataSum);
                }
            } else {
                itemNewData.add(0);
                itemSumData.add(0);
            }
            if (!Collections3.isEmpty(itemUpList)) {
                for (AxisVo axisVo : itemUpList) {
                    itemUpData.add(axisVo.getyAxisData());
                }
                // 上架商品数sum
                if (StringUtils.equals(type, "table")) {
                    int sum = 0;
                    for (Integer iii : itemUpData) {
                        int temp = iii;
                        sum += temp;
                    }
                    itemUpData.add(sum);
                }
            } else {
                itemUpData.add(0);
            }
            if (!Collections3.isEmpty(itemDownList)) {
                for (AxisVo axisVo : itemDownList) {
                    itemDownData.add(axisVo.getyAxisData());
                }
                // 下架商品数sum
                if (StringUtils.equals(type, "table")) {
                    int sum = 0;
                    for (Integer iii : itemDownData) {
                        int temp = iii;
                        sum += temp;
                    }
                    itemDownData.add(sum);
                }
            } else {
                itemDownData.add(0);
            }
            if (StringUtils.equals(type, "table")) {
                xAxisDataList.add("总计");
            }

            List<Series> seriesList = Lists.newArrayList();
            if (StringUtils.equals(type, "table")) {
                seriesList.add(new Series(legendDataList.get(1), itemNewData));
                seriesList.add(new Series(legendDataList.get(2), itemUpData));
                seriesList.add(new Series(legendDataList.get(3), itemDownData));
                seriesList.add(new Series(legendDataList.get(4), itemSumData));
            } else if (StringUtils.equals(type, "chart")) {
                seriesList.add(new Series(legendDataList.get(0), itemNewData));
                seriesList.add(new Series(legendDataList.get(1), itemUpData));
                seriesList.add(new Series(legendDataList.get(2), itemDownData));
                seriesList.add(new Series(legendDataList.get(3), itemSumData));
            }
            
            // Echarts echarts = new Echarts(legendDataList, axis, seriesList);
            Echarts echarts = new Echarts(legendDataList, xAxisDataList, seriesList);
            String echarts2Json = JsonMapper.getInstance().toJson(echarts);

            return echarts2Json;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }

    }

}
