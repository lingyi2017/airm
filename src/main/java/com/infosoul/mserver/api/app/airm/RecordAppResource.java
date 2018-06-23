package com.infosoul.mserver.api.app.airm;

import javax.ws.rs.*;

import com.google.common.collect.Lists;
import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.DateUtils;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.dto.BaseRqDTO;
import com.infosoul.mserver.dto.api.*;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.service.airm.RecordService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.service.airm.DeviceService;

import java.util.List;

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
        if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
            return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
        }
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

    /**
     * 历史记录列表
     *
     * @param dto
     * @return
     */
    @POST
    @Path("/list")
    public ResponseRest recordList(RecordListRqDTO dto) {
        if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
            return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
        }
        try {
            Page<Record> page = recordService.findAppList(dto);
            if (null == page.getList()) {
                return success(null, 0L);
            }
            List<Record> records = page.getList();
            List<RecordListRpDTO> rps = Lists.newArrayList();
            for (Record record : records) {
                RecordListRpDTO rp = new RecordListRpDTO();
                BeanUtils.copyProperties(record, rp);
                rp.setCreateDate(DateUtils.dateToStr(record.getCreateDate(), Constant.FORMAT));
                rps.add(rp);
            }
            return success(rps, page.getCount());
        } catch (Exception e) {
            logger.error("APP端获取设备历史记录列表异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端获取设备历史记录列表异常");
        }
    }

    /**
     * 告警记录列表
     *
     * @param dto
     * @return
     */
    @POST
    @Path("/alarm/list")
    public ResponseRest recordAlarmList(RecordListRqDTO dto) {
        try {
            dto.setDeviceId(null);
            Page<Record> page = recordService.findAlarmAppList(dto);
            if (null == page.getList()) {
                return success(null, 0L);
            }
            List<Record> records = page.getList();
            List<RecordAlarmListRpDTO> rps = Lists.newArrayList();
            for (Record record : records) {
                RecordAlarmListRpDTO rp = new RecordAlarmListRpDTO();
                BeanUtils.copyProperties(record, rp);
                rp.setAqi(60);
                rp.setPollutionDegree("优");
                rp.setCreateDate(DateUtils.dateToStr(record.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
                rps.add(rp);
            }
            return success(rps, page.getCount());
        } catch (Exception e) {
            logger.error("APP端获取告警列表异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端获取告警列表异常");
        }
    }
}
