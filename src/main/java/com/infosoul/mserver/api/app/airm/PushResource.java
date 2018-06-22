package com.infosoul.mserver.api.app.airm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.infosoul.mserver.dto.api.DevicePushDTO;
import com.infosoul.mserver.dto.api.GeoPushDTO;
import com.infosoul.mserver.dto.api.RecordPushDTO;
import com.infosoul.mserver.dto.api.StatusPushDTO;
import com.infosoul.mserver.jpush.JClient;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 设备信息
     *
     * @param dto
     * @return
     */
    @POST
    @Path("/deviceInfo")
    public ResponseRest deviceInfo(DevicePushDTO dto) {
        try {

            return success();
        } catch (Exception e) {
            logger.error("推送设备信息异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "推送设备信息发生异常");
        }
    }

    /**
     * 历史记录
     * 
     * @param dto
     * @return
     */
    @POST
    @Path("/record")
    public ResponseRest record(RecordPushDTO dto) {
        try {

            // client.initMessage().push();
            return success();
        } catch (Exception e) {
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
    @Path("/status")
    public ResponseRest status(StatusPushDTO dto) {
        try {

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
    @Path("/geo")
    public ResponseRest geo(GeoPushDTO dto) {
        try {

            return success();
        } catch (Exception e) {
            logger.error("推送设备位置异常", e);
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "推送设备位置发生异常");
        }
    }
}
