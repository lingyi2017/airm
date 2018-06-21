package com.infosoul.mserver.dto.airm;

import java.io.Serializable;

/**
 * 推送设备状态DTO
 *
 * @author longxy
 * @date 2018-06-22 0:26
 */
public class StatusPushDTO implements Serializable {

    private static final long serialVersionUID = 6077304707132707786L;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 状态：1-正常；3-离线
     */
    private String status;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
