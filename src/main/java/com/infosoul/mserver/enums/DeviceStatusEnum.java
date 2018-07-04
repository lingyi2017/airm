package com.infosoul.mserver.enums;

/**
 * Created by Ecloud on 2018/7/4.
 */
public enum DeviceStatusEnum {

    NORMAL("1", "正常"), ALARM("2", "告警"), OFF_LINE("3", "离线");

    DeviceStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
