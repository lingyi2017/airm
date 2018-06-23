package com.infosoul.mserver.entity.airm;

import com.infosoul.mserver.common.persistence.IdEntity;

/**
 * 用户-设备告警记录表
 *
 * @author longxy
 * @date 2018-06-23 22:56
 */
public class UserRecord extends IdEntity<UserRecord> {

    private static final long serialVersionUID = -510145503280478571L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 设备告警记录ID
     */
    private String recordId;

    /**
     * 状态：0-未读；1-已读
     */
    private String status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
