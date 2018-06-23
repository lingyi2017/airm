package com.infosoul.mserver.entity.airm;

import com.infosoul.mserver.common.persistence.IdEntity;

/**
 * 历史记录
 *
 * @author longxy
 * @date 2018-06-14 23:41
 */
public class Record extends IdEntity<Record> {

    private static final long serialVersionUID = 736316585031116281L;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 状态：1-正常；2-告警
     */
    private String status;

    /**
     * 传感器1采样值
     */
    private Double sensorVal1;

    /**
     * 传感器1状态
     */
    private String sensorStatus1;

    /**
     * 传感器2采样值
     */
    private Double sensorVal2;

    /**
     * 传感器2状态
     */
    private String sensorStatus2;

    /**
     * 传感器3采样值
     */
    private Double sensorVal3;

    /**
     * 传感器3状态
     */
    private String sensorStatus3;

    /**
     * 传感器4采样值
     */
    private Double sensorVal4;

    /**
     * 传感器4状态
     */
    private String sensorStatus4;

    /**
     * 传感器5采样值
     */
    private Double sensorVal5;

    /**
     * 传感器5状态
     */
    private String sensorStatus5;

    /**
     * 传感器6采样值
     */
    private Double sensorVal6;

    /**
     * 传感器6状态
     */
    private String sensorStatus6;

    /**
     * 传感器7采样值
     */
    private Double sensorVal7;

    /**
     * 传感器7状态
     */
    private String sensorStatus7;

    /**
     * 传感器8采样值
     */
    private Double sensorVal8;

    /**
     * 传感器8状态
     */
    private String sensorStatus8;

    /**
     * 传感器9采样值
     */
    private Double sensorVal9;

    /**
     * 传感器9状态
     */
    private String sensorStatus9;

    /**
     * 传感器10采样值
     */
    private Double sensorVal10;

    /**
     * 传感器10状态
     */
    private String sensorStatus10;

    /**
     * 传感器11采样值
     */
    private Double sensorVal11;

    /**
     * 传感器11状态
     */
    private String sensorStatus11;

    /**
     * 传感器12采样值
     */
    private Double sensorVal12;

    /**
     * 传感器12状态
     */
    private String sensorStatus12;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSensorVal1() {
        return sensorVal1;
    }

    public void setSensorVal1(Double sensorVal1) {
        this.sensorVal1 = sensorVal1;
    }

    public String getSensorStatus1() {
        return sensorStatus1;
    }

    public void setSensorStatus1(String sensorStatus1) {
        this.sensorStatus1 = sensorStatus1;
    }

    public Double getSensorVal2() {
        return sensorVal2;
    }

    public void setSensorVal2(Double sensorVal2) {
        this.sensorVal2 = sensorVal2;
    }

    public String getSensorStatus2() {
        return sensorStatus2;
    }

    public void setSensorStatus2(String sensorStatus2) {
        this.sensorStatus2 = sensorStatus2;
    }

    public Double getSensorVal3() {
        return sensorVal3;
    }

    public void setSensorVal3(Double sensorVal3) {
        this.sensorVal3 = sensorVal3;
    }

    public String getSensorStatus3() {
        return sensorStatus3;
    }

    public void setSensorStatus3(String sensorStatus3) {
        this.sensorStatus3 = sensorStatus3;
    }

    public Double getSensorVal4() {
        return sensorVal4;
    }

    public void setSensorVal4(Double sensorVal4) {
        this.sensorVal4 = sensorVal4;
    }

    public String getSensorStatus4() {
        return sensorStatus4;
    }

    public void setSensorStatus4(String sensorStatus4) {
        this.sensorStatus4 = sensorStatus4;
    }

    public Double getSensorVal5() {
        return sensorVal5;
    }

    public void setSensorVal5(Double sensorVal5) {
        this.sensorVal5 = sensorVal5;
    }

    public String getSensorStatus5() {
        return sensorStatus5;
    }

    public void setSensorStatus5(String sensorStatus5) {
        this.sensorStatus5 = sensorStatus5;
    }

    public Double getSensorVal6() {
        return sensorVal6;
    }

    public void setSensorVal6(Double sensorVal6) {
        this.sensorVal6 = sensorVal6;
    }

    public String getSensorStatus6() {
        return sensorStatus6;
    }

    public void setSensorStatus6(String sensorStatus6) {
        this.sensorStatus6 = sensorStatus6;
    }

    public Double getSensorVal7() {
        return sensorVal7;
    }

    public void setSensorVal7(Double sensorVal7) {
        this.sensorVal7 = sensorVal7;
    }

    public String getSensorStatus7() {
        return sensorStatus7;
    }

    public void setSensorStatus7(String sensorStatus7) {
        this.sensorStatus7 = sensorStatus7;
    }

    public Double getSensorVal8() {
        return sensorVal8;
    }

    public void setSensorVal8(Double sensorVal8) {
        this.sensorVal8 = sensorVal8;
    }

    public String getSensorStatus8() {
        return sensorStatus8;
    }

    public void setSensorStatus8(String sensorStatus8) {
        this.sensorStatus8 = sensorStatus8;
    }

    public Double getSensorVal9() {
        return sensorVal9;
    }

    public void setSensorVal9(Double sensorVal9) {
        this.sensorVal9 = sensorVal9;
    }

    public String getSensorStatus9() {
        return sensorStatus9;
    }

    public void setSensorStatus9(String sensorStatus9) {
        this.sensorStatus9 = sensorStatus9;
    }

    public Double getSensorVal10() {
        return sensorVal10;
    }

    public void setSensorVal10(Double sensorVal10) {
        this.sensorVal10 = sensorVal10;
    }

    public String getSensorStatus10() {
        return sensorStatus10;
    }

    public void setSensorStatus10(String sensorStatus10) {
        this.sensorStatus10 = sensorStatus10;
    }

    public Double getSensorVal11() {
        return sensorVal11;
    }

    public void setSensorVal11(Double sensorVal11) {
        this.sensorVal11 = sensorVal11;
    }

    public String getSensorStatus11() {
        return sensorStatus11;
    }

    public void setSensorStatus11(String sensorStatus11) {
        this.sensorStatus11 = sensorStatus11;
    }

    public Double getSensorVal12() {
        return sensorVal12;
    }

    public void setSensorVal12(Double sensorVal12) {
        this.sensorVal12 = sensorVal12;
    }

    public String getSensorStatus12() {
        return sensorStatus12;
    }

    public void setSensorStatus12(String sensorStatus12) {
        this.sensorStatus12 = sensorStatus12;
    }
}
