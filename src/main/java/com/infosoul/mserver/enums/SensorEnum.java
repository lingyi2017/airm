package com.infosoul.mserver.enums;

/**
 * 默认传感器枚举
 *
 * @author longxy
 * @date 2018-06-14 22:54
 */
public enum SensorEnum {

    // TODO 默认传感器具体编号
    CO2("CO2", "PPM", 0x07, 0x02), PM1("PM1.0", "μg/m3", 0x54, 0x04), PM25("PM2.5", "μg/m3", 0x54, 0x04), PM10("PM10",
            "μg/m3", 0x54, 0x04), TEM("温度", "℃", 0x54, 0x04), HUM("湿度", "%RH", 0x54, 0x04);

    /**
     * 传感器名称
     */
    private String name;

    /**
     * 传感器单位
     */
    private String unit;

    /**
     * 传感器名称编号
     */
    private Integer sensorNameNum;

    /**
     * 传感器单位编号
     */
    private Integer sensorUnitNum;

    SensorEnum(String name, String unit, Integer sensorNameNum, Integer sensorUnitNum) {
        this.name = name;
        this.unit = unit;
        this.sensorNameNum = sensorNameNum;
        this.sensorUnitNum = sensorUnitNum;
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

    public Integer getSensorNameNum() {
        return sensorNameNum;
    }

    public void setSensorNameNum(Integer sensorNameNum) {
        this.sensorNameNum = sensorNameNum;
    }

    public Integer getSensorUnitNum() {
        return sensorUnitNum;
    }

    public void setSensorUnitNum(Integer sensorUnitNum) {
        this.sensorUnitNum = sensorUnitNum;
    }
}
