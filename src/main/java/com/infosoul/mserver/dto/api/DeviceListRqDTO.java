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
     * 设备名称
     */
    private String name;

    /**
     * 设备地址
     */
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
