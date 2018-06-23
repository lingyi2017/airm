package com.infosoul.mserver.dto.api;

import com.infosoul.mserver.dto.BaseRqDTO;

/**
 * 用户点击阅读告警 DTO
 *
 * @author longxy
 * @date 2018-06-23 23:44
 */
public class RecordAlarmReadRqDTO extends BaseRqDTO {

    private static final long serialVersionUID = -3635316427598867439L;

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
