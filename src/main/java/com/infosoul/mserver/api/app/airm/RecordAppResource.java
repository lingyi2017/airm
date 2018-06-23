package com.infosoul.mserver.api.app.airm;

import javax.ws.rs.*;

import com.infosoul.mserver.common.utils.DateUtils;
import com.infosoul.mserver.dto.api.RecordLatestRpDTO;
import com.infosoul.mserver.dto.api.RecordLatestRqDTO;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.service.airm.RecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.service.airm.DeviceService;

/**
 * APP端历史记录RESTFull接口
 *
 * @author longxy
 * @date 2018-06-23 00:32
 */
@Path("/app/record")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class RecordAppResource extends BaseResource {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RecordService recordService;

    /**
     * 设备最近一条记录
     * 
     * @param dto
     * @return
     */
    @POST
    @Path("/latest")
    public ResponseRest latestRecord(RecordLatestRqDTO dto) {
        try {
            Record record = recordService.findLatestByDeviceId(dto.getDeviceId());
            RecordLatestRpDTO rp = new RecordLatestRpDTO();
            BeanUtils.copyProperties(record, rp);
            rp.setCreateDate(DateUtils.dateToStr(record.getCreateDate(), "HH:mm:ss"));
            return success(rp);
        } catch (Exception e) {
            logger.error("APP端获取最近一条历史记录异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端获取最近一条历史记录异常");
        }
    }
}
