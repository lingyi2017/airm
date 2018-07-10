package com.infosoul.mserver.dto.api;

import java.io.Serializable;

import com.infosoul.mserver.enums.SensorEnum;

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
     * 传感器1单位
     */
    private String sensorUnit1;

    /**
     * 传感器2名称
     */
    private String sensorName2;

    /**
     * 传感器2单位
     */
    private String sensorUnit2;

    /**
     * 传感器3名称
     */
    private String sensorName3;

    /**
     * 传感器3单位
     */
    private String sensorUnit3;

    /**
     * 传感器4名称
     */
    private String sensorName4;

    /**
     * 传感器4单位
     */
    private String sensorUnit4;

    /**
     * 传感器5名称
     */
    private String sensorName5;

    /**
     * 传感器5单位
     */
    private String sensorUnit5;

    /**
     * 传感器6名称
     */
    private String sensorName6;

    /**
     * 传感器6单位
     */
    private String sensorUnit6;

    /**
     * 传感器7名称
     */
    private String sensorName7;

    /**
     * 传感器7单位
     */
    private String sensorUnit7;

    /**
     * 传感器8名称
     */
    private String sensorName8;

    /**
     * 传感器8单位
     */
    private String sensorUnit8;

    /**
     * 传感器9名称
     */
    private String sensorName9;

    /**
     * 传感器9单位
     */
    private String sensorUnit9;

    /**
     * 传感器10名称
     */
    private String sensorName10;

    /**
     * 传感器10单位
     */
    private String sensorUnit10;

    /**
     * 传感器11名称
     */
    private String sensorName11;

    /**
     * 传感器11单位
     */
    private String sensorUnit11;

    /**
     * 传感器12名称
     */
    private String sensorName12;

    /**
     * 传感器12单位
     */
    private String sensorUnit12;

    /**
     * 空气指数
     */
    private String aqi;

    public DeviceInfoRpDTO() {
        this.sensorName7 = SensorEnum.CO2.getName();
        this.sensorUnit7 = SensorEnum.CO2.getUnit();
        this.sensorName8 = SensorEnum.PM1.getName();
        this.sensorUnit8 = SensorEnum.PM1.getUnit();
        this.sensorName9 = SensorEnum.PM25.getName();
        this.sensorUnit9 = SensorEnum.PM25.getUnit();
        this.sensorName10 = SensorEnum.PM10.getName();
        this.sensorUnit10 = SensorEnum.PM10.getUnit();
        this.sensorName11 = SensorEnum.TEM.getName();
        this.sensorUnit11 = SensorEnum.TEM.getUnit();
        this.sensorName12 = SensorEnum.HUM.getName();
        this.sensorUnit12 = SensorEnum.HUM.getUnit();
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

    public String getSensorUnit1() {
        return sensorUnit1;
    }

    public void setSensorUnit1(String sensorUnit1) {
        this.sensorUnit1 = sensorUnit1;
    }

    public String getSensorName2() {
        return sensorName2;
    }

    public void setSensorName2(String sensorName2) {
        this.sensorName2 = sensorName2;
    }

    public String getSensorUnit2() {
        return sensorUnit2;
    }

    public void setSensorUnit2(String sensorUnit2) {
        this.sensorUnit2 = sensorUnit2;
    }

    public String getSensorName3() {
        return sensorName3;
    }

    public void setSensorName3(String sensorName3) {
        this.sensorName3 = sensorName3;
    }

    public String getSensorUnit3() {
        return sensorUnit3;
    }

    public void setSensorUnit3(String sensorUnit3) {
        this.sensorUnit3 = sensorUnit3;
    }

    public String getSensorName4() {
        return sensorName4;
    }

    public void setSensorName4(String sensorName4) {
        this.sensorName4 = sensorName4;
    }

    public String getSensorUnit4() {
        return sensorUnit4;
    }

    public void setSensorUnit4(String sensorUnit4) {
        this.sensorUnit4 = sensorUnit4;
    }

    public String getSensorName5() {
        return sensorName5;
    }

    public void setSensorName5(String sensorName5) {
        this.sensorName5 = sensorName5;
    }

    public String getSensorUnit5() {
        return sensorUnit5;
    }

    public void setSensorUnit5(String sensorUnit5) {
        this.sensorUnit5 = sensorUnit5;
    }

    public String getSensorName6() {
        return sensorName6;
    }

    public void setSensorName6(String sensorName6) {
        this.sensorName6 = sensorName6;
    }

    public String getSensorUnit6() {
        return sensorUnit6;
    }

    public void setSensorUnit6(String sensorUnit6) {
        this.sensorUnit6 = sensorUnit6;
    }

    public String getSensorName7() {
        return sensorName7;
    }

    public void setSensorName7(String sensorName7) {
        this.sensorName7 = sensorName7;
    }

    public String getSensorUnit7() {
        return sensorUnit7;
    }

    public void setSensorUnit7(String sensorUnit7) {
        this.sensorUnit7 = sensorUnit7;
    }

    public String getSensorName8() {
        return sensorName8;
    }

    public void setSensorName8(String sensorName8) {
        this.sensorName8 = sensorName8;
    }

    public String getSensorUnit8() {
        return sensorUnit8;
    }

    public void setSensorUnit8(String sensorUnit8) {
        this.sensorUnit8 = sensorUnit8;
    }

    public String getSensorName9() {
        return sensorName9;
    }

    public void setSensorName9(String sensorName9) {
        this.sensorName9 = sensorName9;
    }

    public String getSensorUnit9() {
        return sensorUnit9;
    }

    public void setSensorUnit9(String sensorUnit9) {
        this.sensorUnit9 = sensorUnit9;
    }

    public String getSensorName10() {
        return sensorName10;
    }

    public void setSensorName10(String sensorName10) {
        this.sensorName10 = sensorName10;
    }

    public String getSensorUnit10() {
        return sensorUnit10;
    }

    public void setSensorUnit10(String sensorUnit10) {
        this.sensorUnit10 = sensorUnit10;
    }

    public String getSensorName11() {
        return sensorName11;
    }

    public void setSensorName11(String sensorName11) {
        this.sensorName11 = sensorName11;
    }

    public String getSensorUnit11() {
        return sensorUnit11;
    }

    public void setSensorUnit11(String sensorUnit11) {
        this.sensorUnit11 = sensorUnit11;
    }

    public String getSensorName12() {
        return sensorName12;
    }

    public void setSensorName12(String sensorName12) {
        this.sensorName12 = sensorName12;
    }

    public String getSensorUnit12() {
        return sensorUnit12;
    }

    public void setSensorUnit12(String sensorUnit12) {
        this.sensorUnit12 = sensorUnit12;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }
}
