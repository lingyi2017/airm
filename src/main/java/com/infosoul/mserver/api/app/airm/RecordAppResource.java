package com.infosoul.mserver.api.app.airm;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.dto.api.*;
import com.infosoul.mserver.dto.jpush.MessageDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.entity.airm.UserRecord;
import com.infosoul.mserver.jpush.JClient;
import com.infosoul.mserver.service.airm.DeviceService;
import com.infosoul.mserver.service.airm.RecordService;
import com.infosoul.mserver.service.airm.UserRecordService;

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

    @Autowired
    private UserRecordService userRecordService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private JClient client;

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
            if (null == record) {
                return error(ResponseRest.Status.NOT_EXIST, "记录不存在");
            }
            RecordLatestRpDTO rp = new RecordLatestRpDTO();
            BeanUtils.copyProperties(record, rp);
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
                rp.setRead(buildRead(record));
                rps.add(rp);
            }
            return success(rps, page.getCount());
        } catch (Exception e) {
            logger.error("APP端获取告警列表异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端获取告警列表异常");
        }
    }

    /**
     * 告警详情
     *
     * @param dto
     * @return
     */
    @POST
    @Path("/alarm/detail")
    public ResponseRest recordAlarmDetail(RecordAlarmDetailRqDTO dto) {
        if (null == dto || StringUtils.isEmpty(dto.getId())) {
            return error(ResponseRest.Status.BAD_REQUEST, "告警ID不能为空");
        }
        try {
            Record record = recordService.get(dto.getId());
            if (null == record) {
                return error(ResponseRest.Status.NOT_EXIST, "告警记录不存在");
            }
            RecordAlarmDetailRpDTO rp = new RecordAlarmDetailRpDTO();
            BeanUtils.copyProperties(record, rp);
            return success(rp);
        } catch (Exception e) {
            logger.error("APP端告警详情", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端告警详情");
        }
    }

    /**
     * 点击阅读
     *
     * @param dto
     * @return
     */
    @POST
    @Path("/alarm/read")
    public ResponseRest recordAlarmRead(RecordAlarmReadRqDTO dto) {
        if (null == dto || StringUtils.isEmpty(dto.getId())) {
            return error(ResponseRest.Status.BAD_REQUEST, "告警ID不能为空");
        }
        try {
            UserRecord userRecord = new UserRecord();
            userRecord.setUserId(UserUtils.getUser().getId());
            userRecord.setRecordId(dto.getId());
            userRecord.setStatus(Constant.RECORD_READ);
            String status = userRecordService.getStatus(userRecord);
            if (null == status) {
                userRecordService.save(userRecord);
            }
            return success();
        } catch (Exception e) {
            logger.error("APP端点击阅读", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "点击阅读异常");
        }
    }

    /**
     * 模拟极光推送
     *
     * @param dto
     * @return
     */
    @POST
    @Path("/alarm/push")
    public ResponseRest recordAlarmPush(PushRqDTO dto) {
        if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
            return error(ResponseRest.Status.BAD_REQUEST, "设备ID不存在");
        }
        try {
            Device device = deviceService.findByDeviceId(dto.getDeviceId());
            if (null == device) {
                return error(ResponseRest.Status.NOT_EXIST, "设备不存在");
            }
            Record record = recordService.findLatestByDeviceId(dto.getDeviceId());
            if (null == record) {
                return error(ResponseRest.Status.NOT_EXIST, "该设备没有告警记录");
            }
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
                logger.info("模拟推送成功：{}", JSON.toJSONString(messageDTO));
                return success();
            } catch (Exception e) {
                logger.error("模拟推送异常", e);
                return error(ResponseRest.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("模拟推送异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "模拟推送异常");
        }
    }

    private String buildRead(Record record) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(UserUtils.getUser().getId());
        userRecord.setRecordId(record.getId());
        return userRecordService.getStatus(userRecord);
    }
}
