package com.infosoul.mserver.api.app.airm;

import java.util.List;

import javax.ws.rs.*;

import com.google.common.collect.Lists;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.dto.api.DeviceInfoRpDTO;
import com.infosoul.mserver.dto.api.DeviceInfoRqDTO;
import com.infosoul.mserver.dto.api.DeviceListRpDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.dto.api.DeviceListRqDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.service.airm.DeviceService;

/**
 * APP端设备RESTFull接口
 *
 * @author longxy
 * @date 2018-06-22 22:56
 */
@Path("/app/device")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class DeviceAppResource extends BaseResource {

    @Autowired
    private DeviceService deviceService;

    /**
     * 设备列表
     * 
     * @param dto
     * @return
     */
    @POST
    @Path("/list")
    public ResponseRest deviceList(DeviceListRqDTO dto) {
        try {
            Page<Device> page = deviceService.findAppList(dto);
            if (null == page.getList()) {
                return success(page.getList(), 0L);
            }
            List<Device> devices = page.getList();
            List<DeviceListRpDTO> rpDTOS = Lists.newArrayList();
            for (Device device : devices) {
                DeviceListRpDTO rpDTO = new DeviceListRpDTO();
                BeanUtils.copyProperties(device, rpDTO);
                rpDTOS.add(rpDTO);
            }
            return success(rpDTOS, page.getCount());
        } catch (Exception e) {
            logger.error("APP端获取设备列表异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端获取设备列表异常");
        }
    }

    /**
     * 设备信息
     * 
     * @param dto
     * @return
     */
    @POST
    @Path("/info")
    public ResponseRest deviceInfo(DeviceInfoRqDTO dto) {
        if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
            return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
        }
        try {
            Device device = deviceService.findByDeviceId(dto.getDeviceId());
            if(null == device){
                return success();
            }
            DeviceInfoRpDTO rp = new DeviceInfoRpDTO();
            BeanUtils.copyProperties(device, rp);
            return success(rp);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("APP端获取设备信息异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端获取设备信息异常");
        }
    }
}
