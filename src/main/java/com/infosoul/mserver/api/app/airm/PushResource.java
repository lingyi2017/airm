package com.infosoul.mserver.api.app.airm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.infosoul.mserver.common.utils.PpmConversionUtils;
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
                    // 离线告警推送
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
            if (null != device) {
                updateDevice(device, dto);
            }
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
            Record record = new Record();
            BeanUtils.copyProperties(dto, record);
            buildRecord(record);
            recordService.save(record);
            try {
                push(record);
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

    private void updateDevice(Device device, DevicePushDTO dto) throws Exception {
        device.setRegister(Constant.DEVICE_REGISTER);
        BeanUtils.copyProperties(dto, device);
        deviceService.update(device);
    }

    private void buildRecord(Record record) {
        Device device = deviceService.findByDeviceId(record.getDeviceId());

        Double sensorVal1 = record.getSensorVal1();
        sensorVal1 = decimalDeal(sensorVal1, device.getSensorDecimal1());
    }

    private Double decimalDeal(Double val, Integer decimal) {
        if (null == val) {
            return null;
        }
        switch (decimal) {
            case 0:
                return val;
            case 1:
                return val / 10;
            case 2:
                return val / 100;
            case 3:
                return val / 1000;
            case 4:
                return val / 10000;
            default:
                return val;
        }
    }

    private void push(Object obj) {
        // TODO
    }

}
