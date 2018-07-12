package com.infosoul.mserver.dto.api;

import com.infosoul.mserver.dto.BaseRqDTO;

/**
 * 手动推送 DTO
 *
 * @author longxy
 * @date 2018-07-12 19:58
 */
public class PushRqDTO extends BaseRqDTO {

    private static final long serialVersionUID = -2877910492041215385L;

    /**
     * 设备ID
     */
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
