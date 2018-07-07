package com.infosoul.mserver.dto.web;

import java.io.Serializable;
import java.util.List;

/**
 * web socket 推送消息DTO
 *
 * @author longxy
 * @date 2018-07-08 1:51
 */
public class WsPushDTO implements Serializable {

    private static final long serialVersionUID = 3782724362919533878L;

    /**
     * 推送类型：1-数据初始化；2-socket server 推送
     */
    private String type;

    /**
     * 数据初始化
     */
    private List<DeviceMapDTO> devices;

    /**
     * socket server 推送数据
     */
    private DeviceMapDTO device;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DeviceMapDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceMapDTO> devices) {
        this.devices = devices;
    }

    public DeviceMapDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceMapDTO device) {
        this.device = device;
    }
}
