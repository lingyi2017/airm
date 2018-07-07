package com.infosoul.mserver.api.app.airm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.infosoul.mserver.common.utils.DeviceUtils;
import com.infosoul.mserver.common.utils.SensorCacheUtils;
import com.infosoul.mserver.common.utils.airm.LatchConfigCacheUtils;
import com.infosoul.mserver.common.utils.airm.PpmConversionUtils;
import com.infosoul.mserver.constant.SensorConsts;
import com.infosoul.mserver.enums.SensorEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.dto.api.DeviceGeoDTO;
import com.infosoul.mserver.dto.api.DevicePushDTO;
import com.infosoul.mserver.dto.api.RecordPushDTO;
import com.infosoul.mserver.dto.api.StatusPushDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.jpush.JClient;
import com.infosoul.mserver.service.airm.DeviceService;
import com.infosoul.mserver.service.airm.RecordService;

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
            System.out.println("==设备登录成功==" + JSON.toJSONString(dto));
            if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
                return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
            }
            Device device = deviceService.findByDeviceId(dto.getDeviceId());
            if (null == device) {
                device = new Device();
                BeanUtils.copyProperties(dto, device);
                deviceService.save(device);
            } else {
                device.setStatus(dto.getStatus());
                deviceService.updateStatus(device);
                try {
                    // TODO 离线告警推送
                    push(device);
                } catch (Exception e) {
                    logger.error("设备上线推送异常", e);
                }
            }
            return success();
        } catch (Exception e) {
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
            if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
                return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
            }
            Device device = deviceService.findByDeviceId(dto.getDeviceId());
            if (null == device) {
                return error(ResponseRest.Status.NOT_EXIST, "设备ID对应设备不存在");
            }

            Record record = new Record();
            BeanUtils.copyProperties(dto, record);
            record.setDeviceName(device.getName());
            buildRecord(record, device);
            analystRecord(record, device);
            // TODO AQI计算
            recordService.save(record);
            try {
                // TODO 告警推送
                if (Constant.RECORD_ALARM.equals(record.getStatus())) {
                    push(record);
                }
            } catch (Exception e) {
                logger.error("设备记录推送异常", e);
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
            int unitNum1 = device.getSensorUnitNum1();
            int nameNum1 = device.getSensorNameNum1();
            record.setSensorVal1(PpmConversionUtils.retUgVal((byte) unitNum1, (byte) nameNum1, decimalVal1));
        }

        Double sensorVal2 = record.getSensorVal1();
        Double decimalVal2 = DeviceUtils.decimalDeal(sensorVal2, device.getSensorDecimal2());
        if (0 == decimalVal2) {
            record.setSensorVal2(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum2())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum2())) {
            int unitNum2 = device.getSensorUnitNum2();
            int nameNum2 = device.getSensorNameNum2();
            record.setSensorVal2(PpmConversionUtils.retUgVal((byte) unitNum2, (byte) nameNum2, decimalVal2));
        }

        Double sensorVal3 = record.getSensorVal3();
        Double decimalVal3 = DeviceUtils.decimalDeal(sensorVal3, device.getSensorDecimal3());
        if (0 == decimalVal3) {
            record.setSensorVal3(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum3())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum3())) {
            int unitNum3 = device.getSensorUnitNum3();
            int nameNum3 = device.getSensorNameNum3();
            record.setSensorVal3(PpmConversionUtils.retUgVal((byte) unitNum3, (byte) nameNum3, decimalVal3));
        }

        Double sensorVal4 = record.getSensorVal4();
        Double decimalVal4 = DeviceUtils.decimalDeal(sensorVal4, device.getSensorDecimal4());
        if (0 == decimalVal4) {
            record.setSensorVal4(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum4())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum4())) {
            int unitNum4 = device.getSensorUnitNum4();
            int nameNum4 = device.getSensorNameNum4();
            record.setSensorVal4(PpmConversionUtils.retUgVal((byte) unitNum4, (byte) nameNum4, decimalVal4));
        }

        Double sensorVal5 = record.getSensorVal5();
        Double decimalVal5 = DeviceUtils.decimalDeal(sensorVal5, device.getSensorDecimal5());
        if (0 == decimalVal5) {
            record.setSensorVal5(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum5())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum5())) {
            int unitNum5 = device.getSensorUnitNum5();
            int nameNum5 = device.getSensorNameNum5();
            record.setSensorVal5(PpmConversionUtils.retUgVal((byte) unitNum5, (byte) nameNum5, decimalVal5));
        }

        Double sensorVal6 = record.getSensorVal6();
        Double decimalVal6 = DeviceUtils.decimalDeal(sensorVal6, device.getSensorDecimal6());
        if (0 == decimalVal6) {
            record.setSensorVal6(0D);
        }
        if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum6())
                || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum6())) {
            int unitNum6 = device.getSensorUnitNum6();
            int nameNum6 = device.getSensorNameNum6();
            record.setSensorVal2(PpmConversionUtils.retUgVal((byte) unitNum6, (byte) nameNum6, decimalVal6));
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

    private void push(Object obj) {
        // TODO
    }

}
