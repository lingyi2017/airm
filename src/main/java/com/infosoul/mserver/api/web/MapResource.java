package com.infosoul.mserver.api.web;

import javax.ws.rs.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.dto.api.DeviceInfoRpDTO;
import com.infosoul.mserver.dto.api.RecordLatestRpDTO;
import com.infosoul.mserver.dto.web.MapRecordLatestDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.service.airm.DeviceService;
import com.infosoul.mserver.service.airm.RecordService;

/**
 * 地图RESTFull接口
 *
 * @author longxy
 * @date 2018-06-26 23:54
 */
@Path("/map")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class MapResource extends BaseResource {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RecordService recordService;

    /**
     * 设备最近一条记录
     * 
     * @param deviceId
     * @return
     */
    @GET
    @Path("/record/latest")
    public ResponseRest getLatestRecord(@QueryParam(value = "deviceId") String deviceId) {
        if (StringUtils.isBlank(deviceId)) {
            return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
        }
        try {
            Device device = deviceService.findByDeviceId(deviceId);
            DeviceInfoRpDTO header = new DeviceInfoRpDTO();
            BeanUtils.copyProperties(device, header);

            Record record = recordService.findLatestByDeviceId(deviceId);
            RecordLatestRpDTO data = new RecordLatestRpDTO();
            BeanUtils.copyProperties(record, data);

            return success(new MapRecordLatestDTO(header, data));
        } catch (Exception e) {
            logger.error("地图获取最近一条历史记录异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "地图获取最近一条历史记录异常");
        }
    }
}
