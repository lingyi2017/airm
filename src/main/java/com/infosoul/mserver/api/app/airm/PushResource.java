package com.infosoul.mserver.api.app.airm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.SensorCacheUtils;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.constant.SensorConsts;
import com.infosoul.mserver.dto.api.DevicePushDTO;
import com.infosoul.mserver.dto.api.DeviceGeoDTO;
import com.infosoul.mserver.dto.api.RecordPushDTO;
import com.infosoul.mserver.dto.api.StatusPushDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.jpush.JClient;
import com.infosoul.mserver.service.airm.DeviceService;
import com.infosoul.mserver.service.airm.RecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.web.MediaTypes;

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
                saveDevice(dto);
            } else {
                updateDevice(device, dto);
            }
            return success();
        } catch (Exception e) {
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
            /// client.initMessage().push();
            /// 推WEB
            if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
                return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
            }
            Record record = new Record();
            BeanUtils.copyProperties(dto, record);
            recordService.save(record);
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("推送设备历史记录异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "推送设备历史记录发生异常");
        }
    }

    /**
     * 设备状态
     * 
     * @param dto
     * @return
     */
    @POST
    @Path("/device/status")
    public ResponseRest status(StatusPushDTO dto) {
        try {
            if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
                return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
            }
            Device device = new Device();
            BeanUtils.copyProperties(dto, device);
            deviceService.update(device);
            return success();
        } catch (Exception e) {
            logger.error("推送设备状态异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "推送设备状态发生异常");
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

    private void saveDevice(DevicePushDTO dto) throws Exception {
        Device device = new Device();
        device.setDeviceId(dto.getDeviceId());
        device.setRegister(Constant.DEVICE_REGISTER);
        device.setStatus("1");
        buildDevice(device, dto);
        deviceService.save(device);
    }

    private void updateDevice(Device device, DevicePushDTO dto) throws Exception {
        device.setRegister(Constant.DEVICE_REGISTER);
        buildDevice(device, dto);
        deviceService.update(device);
    }

    private Device buildDevice(Device device, DevicePushDTO dto) {
        device.setSensorName1(SensorCacheUtils.getName(dto.getSensorNameNum1()));
        device.setSensorUnit1(SensorConsts.getUnit(dto.getSensorUnitNum1()));

        device.setSensorName2(SensorCacheUtils.getName(dto.getSensorNameNum2()));
        device.setSensorUnit2(SensorConsts.getUnit(dto.getSensorUnitNum2()));

        device.setSensorName3(SensorCacheUtils.getName(dto.getSensorNameNum3()));
        device.setSensorUnit3(SensorConsts.getUnit(dto.getSensorUnitNum3()));

        device.setSensorName4(SensorCacheUtils.getName(dto.getSensorNameNum4()));
        device.setSensorUnit4(SensorConsts.getUnit(dto.getSensorUnitNum4()));

        device.setSensorName5(SensorCacheUtils.getName(dto.getSensorNameNum5()));
        device.setSensorUnit5(SensorConsts.getUnit(dto.getSensorUnitNum5()));

        device.setSensorName6(SensorCacheUtils.getName(dto.getSensorNameNum6()));
        device.setSensorUnit6(SensorConsts.getUnit(dto.getSensorUnitNum6()));
        return device;
    }
}
