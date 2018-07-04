package com.infosoul.mserver.dto.api;

import com.infosoul.mserver.dto.BaseRqDTO;

/**
 * 设备列表查询DTO
 *
 * @author longxy
 * @date 2018-06-22 23:08
 */
public class DeviceListRqDTO extends BaseRqDTO {

    private static final long serialVersionUID = 107794442733805988L;

    /**
     * 设备名称或地址
     */
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
