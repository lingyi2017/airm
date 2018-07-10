package com.infosoul.mserver.api.web;

import javax.ws.rs.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.dto.api.DeviceInfoRpDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.service.airm.DeviceService;

/**
 * WEB端设备RESTFull接口
 *
 * @author longxy
 * @date 2018-07-01 22:56
 */
@Path("/device")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class DeviceWebResource extends BaseResource {

    @Autowired
    private DeviceService deviceService;

    /**
     * 设备信息
     * 
     * @param deviceId
     * @return
     */
    @GET
    @Path("/info")
    public ResponseRest deviceInfo(@QueryParam(value = "deviceId") String deviceId) {
        if (StringUtils.isEmpty(deviceId)) {
            return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
        }
        try {
            Device device = deviceService.findByDeviceId(deviceId);
            if (null == device) {
                return error(ResponseRest.Status.NOT_EXIST, "设备不存在");
            }
            DeviceInfoRpDTO rp = new DeviceInfoRpDTO();
            BeanUtils.copyProperties(device, rp);
            return success(rp);
        } catch (Exception e) {
            logger.error("WEB端获取设备信息异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "WEB端获取设备信息异常");
        }
    }
}
