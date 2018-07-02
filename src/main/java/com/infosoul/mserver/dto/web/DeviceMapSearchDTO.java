package com.infosoul.mserver.dto.web;

import java.io.Serializable;

/**
 * 地图搜索提示 DTO
 *
 * @author longxy
 * @date 2018-07-03 0:13
 */
public class DeviceMapSearchDTO implements Serializable {

    private static final long serialVersionUID = 4323095351805607965L;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String name;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
