package com.infosoul.mserver.dto.api;

import com.infosoul.mserver.dto.BaseRqDTO;

/**
 * 历史记录查询 DTO
 *
 * @author longxy
 * @date 2018-06-23 15:07
 */
public class RecordListRqDTO extends BaseRqDTO {

    private static final long serialVersionUID = 9036935110091298059L;

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
