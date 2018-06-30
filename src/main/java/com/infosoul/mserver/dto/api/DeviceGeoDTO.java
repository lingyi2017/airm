package com.infosoul.mserver.dto.api;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * APP端设备位置上报DTO
 *
 * @author longxy
 * @date 2018-06-22 0:30
 */
public class DeviceGeoDTO implements Serializable {

    private static final long serialVersionUID = 2804606281593278840L;

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
     * 设备地址
     */
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
