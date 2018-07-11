package com.infosoul.mserver.dto.web;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 地图展示设备DTO
 *
 * @author longxy
 * @date 2018-06-25 23:41
 */
public class DeviceMapDTO implements Serializable {

    private static final long serialVersionUID = -2712518813355306771L;

    /**
     * 设备ID
     */
    private String deviceId;

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
     * 空气指数
     */
    private Integer aqi;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }
}
