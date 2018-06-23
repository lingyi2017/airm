package com.infosoul.mserver.dto.api;

import com.infosoul.mserver.dto.BaseRqDTO;

/**
 * 设备信息查询DTO
 *
 * @author longxy
 * @date 2018-06-23 13:40
 */
public class DeviceInfoRqDTO extends BaseRqDTO {

    private static final long serialVersionUID = -1968028654682354570L;

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
