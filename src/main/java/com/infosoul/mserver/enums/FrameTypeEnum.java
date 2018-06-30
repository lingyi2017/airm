package com.infosoul.mserver.enums;

/**
 * Created by Ecloud on 2018/6/29.
 */
public enum FrameTypeEnum {
    LOGIN("登录", 0x14), HEARTBEAT("心跳", 0x15), SENSOR_INFO("读传感器信息", 0x16), SENSOR_DATA("读传感器数据",
            0x17), DEVICE_GEO("设备定位信息", 0x18);

    private String name;

    private int code;

    FrameTypeEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
