package com.infosoul.mserver.api.web;

import java.util.List;
import java.util.Map;

import javax.ws.rs.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.utils.DateUtils;
import com.infosoul.mserver.common.utils.DeviceUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.service.airm.DeviceService;
import com.infosoul.mserver.service.airm.RecordService;
import com.infosoul.mserver.vo.stat.Echarts;
import com.infosoul.mserver.vo.stat.Series;

/**
 * 统计RESTFull接口
 *
 * @author longxy
 * @date 2018-07-06 22:45
 */
@Path("/stat")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class StatResource extends BaseResource {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RecordService recordService;

    /**
     * 获取设备传感器信息
     * 
     * @param deviceId
     * @return
     */
    @GET
    @Path("/device/sensors")
    public ResponseRest getDeviceSensors(@QueryParam(value = "deviceId") String deviceId) {
        if (StringUtils.isBlank(deviceId)) {
            return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
        }
        try {
            Device device = deviceService.findByDeviceId(deviceId);
            return success(DeviceUtils.getSensors(device));
        } catch (Exception e) {
            logger.error("获取设备传感器信息异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "获取设备传感器信息异常");
        }
    }

    /**
     * 获取设备传感器平均值
     *
     * @param deviceId
     * @return
     */
    @GET
    @Path("/device/params")
    public ResponseRest deviceParamsStat(@QueryParam(value = "deviceId") String deviceId,
            @QueryParam(value = "sensorNum") Integer sensorNum, @QueryParam(value = "date") String date) {
        if (StringUtils.isBlank(deviceId)) {
            return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
        }
        try {
            List<String> segmentTimes = DateUtils.getHourSegmentByDay(DateUtils.parseDate(date), 24);
            List<Double> data = Lists.newArrayList();
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("deviceId", deviceId);
            paramMap.put("sensor", "sensor_val" + sensorNum);
            for (int i = 0; i < segmentTimes.size() - 1; i++) {
                paramMap.put("beginDate", segmentTimes.get(i));
                paramMap.put("endDate", segmentTimes.get(i + 1));
                Double paramValue = recordService.getSensorParamAvg(paramMap);
                if (null == paramValue) {
                    data.add(0D);
                } else {
                    data.add(paramValue);
                }
            }
            segmentTimes.remove(0);

            Device device = deviceService.findByDeviceId(deviceId);
            String sensorName = DeviceUtils.getSensorName(device, sensorNum);
            String sensorUnit = DeviceUtils.getSensorUnit(device, sensorNum);
            Series series = new Series(sensorName, data, sensorUnit);
            List<String> xData = Lists.newArrayList();
            for (String segmentTime : segmentTimes) {
                xData.add(DateUtils.formatDate(DateUtils.parseDate(segmentTime), "HH:mm"));
            }
            Echarts echarts = new Echarts(xData, series);
            return success(echarts);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取设备传感器信息异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "获取设备传感器信息异常");
        }
    }
}
