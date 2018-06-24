package com.infosoul.mserver.dto.web;

import java.io.Serializable;

/**
 * 百度地图初始化 DTO
 *
 * @author longxy
 * @date 2018-06-24 21:52
 */
public class MapInitDTO implements Serializable {

    private static final long serialVersionUID = 2631156338545090112L;

    /**
     * 百度地图API地址
     */
    private String mapUrl;

    /**
     * 地图中心城市
     */
    private String mapCenter;

    /**
     * 地图级别
     */
    private Integer mapZoom;

    public MapInitDTO() {}

    public MapInitDTO(String mapUrl, String mapCenter, Integer mapZoom) {
        this.mapUrl = mapUrl;
        this.mapCenter = mapCenter;
        this.mapZoom = mapZoom;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getMapCenter() {
        return mapCenter;
    }

    public void setMapCenter(String mapCenter) {
        this.mapCenter = mapCenter;
    }

    public Integer getMapZoom() {
        return mapZoom;
    }

    public void setMapZoom(Integer mapZoom) {
        this.mapZoom = mapZoom;
    }
}
