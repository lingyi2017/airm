package com.infosoul.mserver.dto.api;

import com.infosoul.mserver.dto.BaseRqDTO;

/**
 * 告警详情查询 DTO
 *
 * @author longxy
 * @date 2018-06-23 17:04
 */
public class RecordAlarmDetailRqDTO extends BaseRqDTO {

    /**
     * 告警ID
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
