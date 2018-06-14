package com.infosoul.mserver.enums;

/**
 * 默认传感器枚举
 *
 * @author longxy
 * @date 2018-06-14 22:54
 */
public enum DefaultSensorEnum {

    CO2("CO2", "PPM"), PM1("PM1.0", "μg/m3"), PM25("PM2.5", "μg/m3"), PM10("PM10", "μg/m3"), TEM("温度", "℃"), HUM("湿度",
            "%RH");

    /**
     * 传感器名称
     */
    private String name;

    /**
     * 传感器单位
     */
    private String unit;

    DefaultSensorEnum(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
