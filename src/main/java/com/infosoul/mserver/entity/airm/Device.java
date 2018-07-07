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
     * 设备名称
     */
    private String name;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备是否注册：0-未注册；1-注册
     */
    private String register;

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
     * 设备地址（人为录入或逆地址解析）
     */
    private String address;

    /**
     * 传感器1名称
     */
    private String sensorName1;

    /**
     * 传感器1单位
     */
    private String sensorUnit1;

    /**
     * 传感器1名称编号
     */
    private Integer sensorNameNum1;

    /**
     * 传感器1单位编号
     */
    private Integer sensorUnitNum1;

    /**
     * 传感器1小数位（0:无小数位; 1:1位小数; 2:2位小数; 3:3位小数; 4:4位小数）
     */
    private Integer sensorDecimal1;

    /**
     * 传感器2名称
     */
    private String sensorName2;

    /**
     * 传感器2单位
     */
    private String sensorUnit2;

    /**
     * 传感器2名称编号
     */
    private Integer sensorNameNum2;

    /**
     * 传感器2单位编号
     */
    private Integer sensorUnitNum2;

    /**
     * 传感器2小数位
     */
    private Integer sensorDecimal2;

    /**
     * 传感器3名称
     */
    private String sensorName3;

    /**
     * 传感器3单位
     */
    private String sensorUnit3;

    /**
     * 传感器3名称编号
     */
    private Integer sensorNameNum3;

    /**
     * 传感器3单位编号
     */
    private Integer sensorUnitNum3;

    /**
     * 传感器3小数位
     */
    private Integer sensorDecimal3;

    /**
     * 传感器4名称
     */
    private String sensorName4;

    /**
     * 传感器4单位
     */
    private String sensorUnit4;

    /**
     * 传感器4名称编号
     */
    private Integer sensorNameNum4;

    /**
     * 传感器4单位编号
     */
    private Integer sensorUnitNum4;

    /**
     * 传感器4小数位
     */
    private Integer sensorDecimal4;

    /**
     * 传感器5名称
     */
    private String sensorName5;

    /**
     * 传感器5单位
     */
    private String sensorUnit5;

    /**
     * 传感器5名称编号
     */
    private Integer sensorNameNum5;

    /**
     * 传感器5单位编号
     */
    private Integer sensorUnitNum5;

    /**
     * 传感器5小数位
     */
    private Integer sensorDecimal5;

    /**
     * 传感器6名称
     */
    private String sensorName6;

    /**
     * 传感器6单位
     */
    private String sensorUnit6;

    /**
     * 传感器6名称编号
     */
    private Integer sensorNameNum6;

    /**
     * 传感器6单位编号
     */
    private Integer sensorUnitNum6;

    /**
     * 传感器6小数位
     */
    private Integer sensorDecimal6;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getSensorNameNum1() {
        return sensorNameNum1;
    }

    public void setSensorNameNum1(Integer sensorNameNum1) {
        this.sensorNameNum1 = sensorNameNum1;
    }

    public Integer getSensorUnitNum1() {
        return sensorUnitNum1;
    }

    public void setSensorUnitNum1(Integer sensorUnitNum1) {
        this.sensorUnitNum1 = sensorUnitNum1;
    }

    public Integer getSensorDecimal1() {
        return sensorDecimal1;
    }

    public void setSensorDecimal1(Integer sensorDecimal1) {
        this.sensorDecimal1 = sensorDecimal1;
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

    public Integer getSensorNameNum2() {
        return sensorNameNum2;
    }

    public void setSensorNameNum2(Integer sensorNameNum2) {
        this.sensorNameNum2 = sensorNameNum2;
    }

    public Integer getSensorUnitNum2() {
        return sensorUnitNum2;
    }

    public void setSensorUnitNum2(Integer sensorUnitNum2) {
        this.sensorUnitNum2 = sensorUnitNum2;
    }

    public Integer getSensorDecimal2() {
        return sensorDecimal2;
    }

    public void setSensorDecimal2(Integer sensorDecimal2) {
        this.sensorDecimal2 = sensorDecimal2;
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

    public Integer getSensorNameNum3() {
        return sensorNameNum3;
    }

    public void setSensorNameNum3(Integer sensorNameNum3) {
        this.sensorNameNum3 = sensorNameNum3;
    }

    public Integer getSensorUnitNum3() {
        return sensorUnitNum3;
    }

    public void setSensorUnitNum3(Integer sensorUnitNum3) {
        this.sensorUnitNum3 = sensorUnitNum3;
    }

    public Integer getSensorDecimal3() {
        return sensorDecimal3;
    }

    public void setSensorDecimal3(Integer sensorDecimal3) {
        this.sensorDecimal3 = sensorDecimal3;
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

    public Integer getSensorNameNum4() {
        return sensorNameNum4;
    }

    public void setSensorNameNum4(Integer sensorNameNum4) {
        this.sensorNameNum4 = sensorNameNum4;
    }

    public Integer getSensorUnitNum4() {
        return sensorUnitNum4;
    }

    public void setSensorUnitNum4(Integer sensorUnitNum4) {
        this.sensorUnitNum4 = sensorUnitNum4;
    }

    public Integer getSensorDecimal4() {
        return sensorDecimal4;
    }

    public void setSensorDecimal4(Integer sensorDecimal4) {
        this.sensorDecimal4 = sensorDecimal4;
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

    public Integer getSensorNameNum5() {
        return sensorNameNum5;
    }

    public void setSensorNameNum5(Integer sensorNameNum5) {
        this.sensorNameNum5 = sensorNameNum5;
    }

    public Integer getSensorUnitNum5() {
        return sensorUnitNum5;
    }

    public void setSensorUnitNum5(Integer sensorUnitNum5) {
        this.sensorUnitNum5 = sensorUnitNum5;
    }

    public Integer getSensorDecimal5() {
        return sensorDecimal5;
    }

    public void setSensorDecimal5(Integer sensorDecimal5) {
        this.sensorDecimal5 = sensorDecimal5;
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

    public Integer getSensorNameNum6() {
        return sensorNameNum6;
    }

    public void setSensorNameNum6(Integer sensorNameNum6) {
        this.sensorNameNum6 = sensorNameNum6;
    }

    public Integer getSensorUnitNum6() {
        return sensorUnitNum6;
    }

    public void setSensorUnitNum6(Integer sensorUnitNum6) {
        this.sensorUnitNum6 = sensorUnitNum6;
    }

    public Integer getSensorDecimal6() {
        return sensorDecimal6;
    }

    public void setSensorDecimal6(Integer sensorDecimal6) {
        this.sensorDecimal6 = sensorDecimal6;
    }
}
