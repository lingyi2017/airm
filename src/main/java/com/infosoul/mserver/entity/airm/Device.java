package com.infosoul.mserver.entity.airm;

import com.infosoul.mserver.common.persistence.IdEntity;

import java.math.BigDecimal;

/**
 * 设备表
 *
 * @author longxy
 * @date 2018-06-14 0:03
 */
public class Device extends IdEntity<Device> {

    private static final long serialVersionUID = -9001310146656303179L;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备ime号
     */
    private String ime;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 状态：1-正常；2-告警；3-离线
     */
    private String status;

    /**
     * 检测站
     */
    private String station;

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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
}
