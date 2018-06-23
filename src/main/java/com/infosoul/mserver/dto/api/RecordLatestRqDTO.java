package com.infosoul.mserver.dto.api;

import com.infosoul.mserver.dto.BaseRqDTO;

/**
 * 设备最近一条记录查询
 *
 * @author longxy
 * @date 2018-06-23 0:56
 */
public class RecordLatestRqDTO extends BaseRqDTO {

    private static final long serialVersionUID = 1577614042478809975L;

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
