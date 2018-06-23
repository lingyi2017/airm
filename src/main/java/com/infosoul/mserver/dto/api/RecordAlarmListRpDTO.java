package com.infosoul.mserver.dto.api;

import java.io.Serializable;

/**
 * 告警列表响应 DTO
 *
 * @author longxy
 * @date 2018-06-23 13:11
 */
public class RecordAlarmListRpDTO implements Serializable {

    private static final long serialVersionUID = 7907571347233771721L;

    private String id;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 空气质量指数
     */
    private Integer aqi;

    /**
     * 污染等级（优、良、中、差）
     */
    private String pollutionDegree;

    /**
     * 告警是否读取：0-未读；1-已读
     */
    private String read;

    /**
     * 创建日期
     */
    protected String createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public String getPollutionDegree() {
        return pollutionDegree;
    }

    public void setPollutionDegree(String pollutionDegree) {
        this.pollutionDegree = pollutionDegree;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}