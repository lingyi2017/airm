package com.infosoul.mserver.dto.api;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * APP端设备列表响应信息
 *
 * @author longxy
 * @date 2018-06-22 23:01
 */
public class DeviceListRpDTO implements Serializable {

    private static final long serialVersionUID = -1233456395648210094L;

    /**
     * 设备名称
     */
    private String name;

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
