package com.infosoul.mserver.api.app.airm;

import java.util.Date;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.utils.*;
import com.infosoul.mserver.common.utils.airm.LatchConfigCacheUtils;
import com.infosoul.mserver.common.utils.airm.PpmConversionUtils;
import com.infosoul.mserver.common.utils.aqi.AqiUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.constant.SensorConsts;
import com.infosoul.mserver.dto.api.DeviceGeoDTO;
import com.infosoul.mserver.dto.api.DevicePushDTO;
import com.infosoul.mserver.dto.api.RecordPushDTO;
import com.infosoul.mserver.dto.api.StatusPushDTO;
import com.infosoul.mserver.dto.jpush.MessageDTO;
import com.infosoul.mserver.dto.web.DeviceMapDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.enums.SensorEnum;
import com.infosoul.mserver.jpush.JClient;
import com.infosoul.mserver.service.airm.DeviceService;
import com.infosoul.mserver.service.airm.RecordService;
import com.infosoul.mserver.websocket.MapWebsocket;

/**
 * 数据推送 RESTFull接口
 *
 * @author longxy
 * @date 2018-06-21 23:47
 */
@Path("/push")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class PushResource extends BaseResource {

    @Autowired
    private JClient client;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RecordService recordService;

    /**
     * 设备上线
     *
     * @param dto
     * @return
     */
    @POST
    @Path("/device/status")
    public ResponseRest status(StatusPushDTO dto) {
        try {
            logger.info("==设备登录成功== {}", JSON.toJSONString(dto));
            if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
                return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
            }
            Device device = deviceService.findByDeviceId(dto.getDeviceId());
            if (null == device) {
                device = new Device();
                BeanUtils.copyProperties(dto, device);
                device.setRegister(Constant.DEVICE_UNREGISTERED);
                deviceService.save(device);
            } else {
                if (!dto.getStatus().equals(device.getStatus())) {
                    device.setStatus(dto.getStatus());
                    deviceService.updateStatus(device);
                    // web 推送
                    webPush(device);
                }
            }
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设备上线异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "设备上线异常");
        }
    }

    /**
     * 设备信息
     *
     * @param dto
     * @return
     */
    @POST
    @Path("/device/info")
    public ResponseRest deviceInfo(DevicePushDTO dto) {
        try {
            logger.info("==收到设备信息数据== {}", JSON.toJSONString(dto));
            if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
                return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
            }
            Device device = deviceService.findByDeviceId(dto.getDeviceId());
            if (null == device) {
                return error(ResponseRest.Status.NOT_EXIST, "设备没上线");
            }
            updateDevice(device, dto);
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("推送设备信息异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "推送设备信息发生异常");
        }
    }

    /**
     * 设备记录
     * 
     * @param dto
     * @return
     */
    @POST
    @Path("/device/record")
    public ResponseRest record(RecordPushDTO dto) {
        try {
            logger.info("==收到设备记录数据== {}", JSON.toJSONString(dto));
            if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
                return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
            }
            Device device = deviceService.findByDeviceId(dto.getDeviceId());
            if (null == device) {
                return error(ResponseRest.Status.NOT_EXIST, "设备ID对应设备不存在");
            }

            Record record = new Record();
            BeanUtils.copyProperties(dto, record);
            record.setCreateDate(new Date());
            record.setDeviceName(device.getName());
            buildRecord(record, device);
            analystRecord(record, device);
            buildAqi(record, device);
            recordService.save(record);
            if (Constant.RECORD_ALARM.equals(record.getStatus())) {
                // 更新设备状态
                device.setStatus("2");
                deviceService.updateStatus(device);

                // 告警推送
                Map<String, String> paramMap = Maps.newHashMap();
                paramMap.put("deviceId", device.getDeviceId());
                paramMap.put("startDate", DateUtils.reduceMin(new Date(), -30));
                Integer count = recordService.getRecordCount(paramMap);
                if (null != count && count <= 1) {
                    // 推送web端
                    webPush(device);
                    // 推送APP端
                    appPush(device, record);
                }
            }
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("推送设备历史记录异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "推送设备历史记录发生异常");
        }
    }

    /**
     * 设备位置
     * 
     * @param dto
     * @return
     */
    @POST
    @Path("/device/geo")
    @Deprecated
    public ResponseRest geo(DeviceGeoDTO dto) {
        try {
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("推送设备位置异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "推送设备位置发生异常");
        }
    }

    /**
     * 更新设备信息
     * 
     * @param device
     * @param dto
     * @throws Exception
     */
    private void updateDevice(Device device, DevicePushDTO dto) throws Exception {
        device.setRegister(Constant.DEVICE_REGISTER);
        BeanUtils.copyProperties(dto, device);
        buildSensorName(device, dto);
        buildSensorUnit(device, dto);
        deviceService.update(device);
    }

    private void buildRecord(Record record, Device device) {

        // 初始值
        Double sensorVal1 = record.getSensorVal1();
        // 小数位处理
        Double decimalVal1 = DeviceUtils.decimalDeal(sensorVal1, device.getSensorDecimal1());
        if (0 == decimalVal1) {
            record.setSensorVal1(0D);
        }
        // ppm或ppb转换成ug/m3
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum1())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum1())) {
            if (null != device.getSensorUnitNum1() && null != device.getSensorNameNum1()) {
                int unitNum1 = device.getSensorUnitNum1();
                int nameNum1 = device.getSensorNameNum1();
                record.setSensorVal1(PpmConversionUtils.retUgVal((byte) unitNum1, (byte) nameNum1, decimalVal1));
            }
        }

        Double sensorVal2 = record.getSensorVal2();
        Double decimalVal2 = DeviceUtils.decimalDeal(sensorVal2, device.getSensorDecimal2());
        if (0 == decimalVal2) {
            record.setSensorVal2(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum2())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum2())) {
            if (null != device.getSensorUnitNum2() && null != device.getSensorNameNum2()) {
                int unitNum2 = device.getSensorUnitNum2();
                int nameNum2 = device.getSensorNameNum2();
                record.setSensorVal2(PpmConversionUtils.retUgVal((byte) unitNum2, (byte) nameNum2, decimalVal2));
            }
        }

        Double sensorVal3 = record.getSensorVal3();
        Double decimalVal3 = DeviceUtils.decimalDeal(sensorVal3, device.getSensorDecimal3());
        if (0 == decimalVal3) {
            record.setSensorVal3(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum3())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum3())) {
            if (null != device.getSensorUnitNum3() && null != device.getSensorNameNum3()) {
                int unitNum3 = device.getSensorUnitNum3();
                int nameNum3 = device.getSensorNameNum3();
                record.setSensorVal3(PpmConversionUtils.retUgVal((byte) unitNum3, (byte) nameNum3, decimalVal3));
            }
        }

        Double sensorVal4 = record.getSensorVal4();
        Double decimalVal4 = DeviceUtils.decimalDeal(sensorVal4, device.getSensorDecimal4());
        if (0 == decimalVal4) {
            record.setSensorVal4(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum4())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum4())) {
            if (null != device.getSensorUnitNum4() && null != device.getSensorNameNum4()) {
                int unitNum4 = device.getSensorUnitNum4();
                int nameNum4 = device.getSensorNameNum4();
                record.setSensorVal4(PpmConversionUtils.retUgVal((byte) unitNum4, (byte) nameNum4, decimalVal4));
            }
        }

        Double sensorVal5 = record.getSensorVal5();
        Double decimalVal5 = DeviceUtils.decimalDeal(sensorVal5, device.getSensorDecimal5());
        if (0 == decimalVal5) {
            record.setSensorVal5(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum5())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum5())) {
            if (null != device.getSensorUnitNum5() && null != device.getSensorNameNum5()) {
                int unitNum5 = device.getSensorUnitNum5();
                int nameNum5 = device.getSensorNameNum5();
                record.setSensorVal5(PpmConversionUtils.retUgVal((byte) unitNum5, (byte) nameNum5, decimalVal5));
            }
        }

        Double sensorVal6 = record.getSensorVal6();
        Double decimalVal6 = DeviceUtils.decimalDeal(sensorVal6, device.getSensorDecimal6());
        if (0 == decimalVal6) {
            record.setSensorVal6(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum6())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum6())) {
            if (null != device.getSensorUnitNum6() && null != device.getSensorNameNum6()) {
                int unitNum6 = device.getSensorUnitNum6();
                int nameNum6 = device.getSensorNameNum6();
                record.setSensorVal6(PpmConversionUtils.retUgVal((byte) unitNum6, (byte) nameNum6, decimalVal6));
            }
        }
    }

    private void analystRecord(Record record, Device device) {

        Double val = record.getSensorVal1();
        Integer serialNum = device.getSensorNameNum1();
        Double maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal2();
        serialNum = device.getSensorNameNum2();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal3();
        serialNum = device.getSensorNameNum3();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal4();
        serialNum = device.getSensorNameNum4();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal5();
        serialNum = device.getSensorNameNum5();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal6();
        serialNum = device.getSensorNameNum6();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal7();
        serialNum = SensorEnum.CO2.getSensorNameNum();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal8();
        serialNum = SensorEnum.PM1.getSensorNameNum();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal9();
        serialNum = SensorEnum.PM25.getSensorNameNum();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal10();
        serialNum = SensorEnum.PM10.getSensorNameNum();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal11();
        serialNum = SensorEnum.TEM.getSensorNameNum();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }

        val = record.getSensorVal12();
        serialNum = SensorEnum.HUM.getSensorNameNum();
        maxVal = LatchConfigCacheUtils.getMaxVal(serialNum);
        if (null != maxVal && null != val && val > maxVal) {
            record.setStatus(Constant.RECORD_ALARM);
            return;
        }
        record.setStatus("1");
    }

    private void buildAqi(Record record, Device device) {
        double so2 = DeviceUtils.getSensorVal(record, device, 0x0A);
        double no2 = DeviceUtils.getSensorVal(record, device, 0x16);
        double pm10 = record.getSensorVal10() == null ? 0 : record.getSensorVal10();
        double co = DeviceUtils.getSensorVal(record, device, 0x02);
        double o3 = DeviceUtils.getSensorVal(record, device, 0x15);
        double pm25 = record.getSensorVal9() == null ? 0 : record.getSensorVal9();
        Map<String, Object> aqiMap =
                AqiUtils.getRealTime((float) so2, (float) no2, (float) pm10, (float) co, (float) o3, (float) pm25);
        Object aqi = aqiMap.get("aqi");
        if (null != aqi) {
            record.setAqi((int) aqi);
        }
        Object level = aqiMap.get("level");
        if (null != level) {
            record.setPollutionDegree((String) level);
        }
    }

    private void buildSensorName(Device device, DevicePushDTO dto) {
        device.setSensorName1(SensorCacheUtils.getName(dto.getSensorNameNum1()));
        device.setSensorName2(SensorCacheUtils.getName(dto.getSensorNameNum2()));
        device.setSensorName3(SensorCacheUtils.getName(dto.getSensorNameNum3()));
        device.setSensorName4(SensorCacheUtils.getName(dto.getSensorNameNum4()));
        device.setSensorName5(SensorCacheUtils.getName(dto.getSensorNameNum5()));
        device.setSensorName6(SensorCacheUtils.getName(dto.getSensorNameNum6()));
    }

    private void buildSensorUnit(Device device, DevicePushDTO dto) {
        device.setSensorUnit1(SensorConsts.getUnit(dto.getSensorUnitNum1()));
        device.setSensorUnit2(SensorConsts.getUnit(dto.getSensorUnitNum2()));
        device.setSensorUnit3(SensorConsts.getUnit(dto.getSensorUnitNum3()));
        device.setSensorUnit4(SensorConsts.getUnit(dto.getSensorUnitNum4()));
        device.setSensorUnit5(SensorConsts.getUnit(dto.getSensorUnitNum5()));
        device.setSensorUnit6(SensorConsts.getUnit(dto.getSensorUnitNum6()));
    }

    private void appPush(Device device, Record record) {
        MessageDTO messageDTO = new MessageDTO();
        String title = "德科大气监测系统";
        StringBuilder content = new StringBuilder(32);
        content.append(device.getAddress());
        content.append("地点");
        content.append(device.getStation());
        content.append("空气检测微站检测到空气异常，详情请点击查看。");
        Map<String, String> extras = Maps.newHashMap();
        extras.put("recordId", record.getId());
        extras.put("deviceId", device.getDeviceId());

        messageDTO.setTitle(title);
        messageDTO.setMsgContent(content.toString());
        messageDTO.setExtras(extras);
        try {
            client.initMessage(messageDTO).push();
            logger.info("极光推送成功：{}", JSON.toJSONString(messageDTO));
        } catch (Exception e) {
            logger.error("极光推送异常", e);
        }
    }

    private void webPush(Device device) {
        try {
            DeviceMapDTO deviceMapDTO = new DeviceMapDTO();
            BeanUtils.copyProperties(device, deviceMapDTO);
            MapWebsocket.socketServerPush(deviceMapDTO);
        } catch (Exception e) {
            logger.error("web socket 推送异常", e);
        }
    }
}
