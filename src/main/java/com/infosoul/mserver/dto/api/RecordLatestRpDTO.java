package com.infosoul.mserver.dto.api;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备最近一条记录响应 DTO
 *
 * @author longxy
 * @date 2018-06-23 13:11
 */
public class RecordLatestRpDTO implements Serializable {

    private static final long serialVersionUID = 7907571347233771721L;

    /**
     * 空气指数
     */
    private Integer aqi;

    /**
     * 传感器1采样值
     */
    private Double sensorVal1;

    /**
     * 传感器2采样值
     */
    private Double sensorVal2;

    /**
     * 传感器3采样值
     */
    private Double sensorVal3;

    /**
     * 传感器4采样值
     */
    private Double sensorVal4;

    /**
     * 传感器5采样值
     */
    private Double sensorVal5;

    /**
     * 传感器6采样值
     */
    private Double sensorVal6;

    /**
     * 传感器7采样值
     */
    private Double sensorVal7;

    /**
     * 传感器8采样值
     */
    private Double sensorVal8;

    /**
     * 传感器9采样值
     */
    private Double sensorVal9;

    /**
     * 传感器10采样值
     */
    private Double sensorVal10;

    /**
     * 传感器11采样值
     */
    private Double sensorVal11;

    /**
     * 传感器12采样值
     */
    private Double sensorVal12;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 添加时间
     */
    private String addDate;

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public Double getSensorVal1() {
        return sensorVal1;
    }

    public void setSensorVal1(Double sensorVal1) {
        this.sensorVal1 = sensorVal1;
    }

    public Double getSensorVal2() {
        return sensorVal2;
    }

    public void setSensorVal2(Double sensorVal2) {
        this.sensorVal2 = sensorVal2;
    }

    public Double getSensorVal3() {
        return sensorVal3;
    }

    public void setSensorVal3(Double sensorVal3) {
        this.sensorVal3 = sensorVal3;
    }

    public Double getSensorVal4() {
        return sensorVal4;
    }

    public void setSensorVal4(Double sensorVal4) {
        this.sensorVal4 = sensorVal4;
    }

    public Double getSensorVal5() {
        return sensorVal5;
    }

    public void setSensorVal5(Double sensorVal5) {
        this.sensorVal5 = sensorVal5;
    }

    public Double getSensorVal6() {
        return sensorVal6;
    }

    public void setSensorVal6(Double sensorVal6) {
        this.sensorVal6 = sensorVal6;
    }

    public Double getSensorVal7() {
        return sensorVal7;
    }

    public void setSensorVal7(Double sensorVal7) {
        this.sensorVal7 = sensorVal7;
    }

    public Double getSensorVal8() {
        return sensorVal8;
    }

    public void setSensorVal8(Double sensorVal8) {
        this.sensorVal8 = sensorVal8;
    }

    public Double getSensorVal9() {
        return sensorVal9;
    }

    public void setSensorVal9(Double sensorVal9) {
        this.sensorVal9 = sensorVal9;
    }

    public Double getSensorVal10() {
        return sensorVal10;
    }

    public void setSensorVal10(Double sensorVal10) {
        this.sensorVal10 = sensorVal10;
    }

    public Double getSensorVal11() {
        return sensorVal11;
    }

    public void setSensorVal11(Double sensorVal11) {
        this.sensorVal11 = sensorVal11;
    }

    public Double getSensorVal12() {
        return sensorVal12;
    }

    public void setSensorVal12(Double sensorVal12) {
        this.sensorVal12 = sensorVal12;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
