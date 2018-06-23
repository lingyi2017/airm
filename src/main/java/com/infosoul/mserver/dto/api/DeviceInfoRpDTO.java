package com.infosoul.mserver.dto.api;

import com.infosoul.mserver.enums.DefaultSensorEnum;

import java.io.Serializable;

/**
 * 设备信息响应DTO
 *
 * @author longxy
 * @date 2018-06-23 13:42
 */
public class DeviceInfoRpDTO implements Serializable {

    private static final long serialVersionUID = 1903051356669137135L;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 传感器1名称
     */
    private String sensorName1;

    /**
     * 传感器2名称
     */
    private String sensorName2;

    /**
     * 传感器3名称
     */
    private String sensorName3;

    /**
     * 传感器4名称
     */
    private String sensorName4;

    /**
     * 传感器5名称
     */
    private String sensorName5;

    /**
     * 传感器6名称
     */
    private String sensorName6;

    /**
     * 传感器7名称
     */
    private String sensorName7;

    /**
     * 传感器8名称
     */
    private String sensorName8;

    /**
     * 传感器9名称
     */
    private String sensorName9;

    /**
     * 传感器10名称
     */
    private String sensorName10;

    /**
     * 传感器11名称
     */
    private String sensorName11;

    /**
     * 传感器12名称
     */
    private String sensorName12;

    public DeviceInfoRpDTO() {
        this.sensorName7 = DefaultSensorEnum.CO2.getName();
        this.sensorName8 = DefaultSensorEnum.PM1.getName();
        this.sensorName9 = DefaultSensorEnum.PM25.getName();
        this.sensorName10 = DefaultSensorEnum.PM10.getName();
        this.sensorName11 = DefaultSensorEnum.TEM.getName();
        this.sensorName12 = DefaultSensorEnum.HUM.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSensorName1() {
        return sensorName1;
    }

    public void setSensorName1(String sensorName1) {
        this.sensorName1 = sensorName1;
    }

    public String getSensorName2() {
        return sensorName2;
    }

    public void setSensorName2(String sensorName2) {
        this.sensorName2 = sensorName2;
    }

    public String getSensorName3() {
        return sensorName3;
    }

    public void setSensorName3(String sensorName3) {
        this.sensorName3 = sensorName3;
    }

    public String getSensorName4() {
        return sensorName4;
    }

    public void setSensorName4(String sensorName4) {
        this.sensorName4 = sensorName4;
    }

    public String getSensorName5() {
        return sensorName5;
    }

    public void setSensorName5(String sensorName5) {
        this.sensorName5 = sensorName5;
    }

    public String getSensorName6() {
        return sensorName6;
    }

    public void setSensorName6(String sensorName6) {
        this.sensorName6 = sensorName6;
    }

    public String getSensorName7() {
        return sensorName7;
    }

    public void setSensorName7(String sensorName7) {
        this.sensorName7 = sensorName7;
    }

    public String getSensorName8() {
        return sensorName8;
    }

    public void setSensorName8(String sensorName8) {
        this.sensorName8 = sensorName8;
    }

    public String getSensorName9() {
        return sensorName9;
    }

    public void setSensorName9(String sensorName9) {
        this.sensorName9 = sensorName9;
    }

    public String getSensorName10() {
        return sensorName10;
    }

    public void setSensorName10(String sensorName10) {
        this.sensorName10 = sensorName10;
    }

    public String getSensorName11() {
        return sensorName11;
    }

    public void setSensorName11(String sensorName11) {
        this.sensorName11 = sensorName11;
    }

    public String getSensorName12() {
        return sensorName12;
    }

    public void setSensorName12(String sensorName12) {
        this.sensorName12 = sensorName12;
    }
}
