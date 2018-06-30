package com.infosoul.mserver.api.app.airm;

import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.dto.api.DeviceGeoDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.service.airm.DeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * 设备位置上传 Resource
 *
 * @author longxy
 * @date 2018-06-30 23:55
 */
@Path("/geo")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class GeoAppResource extends BaseResource {

    @Autowired
    private DeviceService deviceService;

    /**
     * 设备位置（不需要登录）
     *
     * @param dto
     * @return
     */
    @POST
    @Path("/device")
    @Deprecated
    public ResponseRest geo(DeviceGeoDTO dto) {
        try {
            if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
                return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
            }
            if (StringUtils.isEmpty(dto.getAddress())) {
                return error(ResponseRest.Status.BAD_REQUEST, "设备地址不能为空");
            }
            Device device = deviceService.findByDeviceId(dto.getDeviceId());
            // 设备不存在。新增设备
            if (null == device) {
                device = new Device();
                BeanUtils.copyProperties(dto, device);
                device.setRegister(Constant.DEVICE_UNREGISTERED);
                deviceService.save(device);
            } else { // 设备存在。更新位置信息
                device.setLon(dto.getLon());
                device.setLat(dto.getLat());
                device.setAddress(dto.getAddress());
                deviceService.update(device);
            }
            return success();
        } catch (Exception e) {
            logger.error("APP端设备位置上报异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端设备位置上报异常");
        }
    }
}
