package com.infosoul.mserver.dto.web;

import java.io.Serializable;

import com.infosoul.mserver.dto.api.DeviceInfoRpDTO;
import com.infosoul.mserver.dto.api.RecordLatestRpDTO;

/**
 * 地图设备最近一条记录 DTO
 *
 * @author longxy
 * @date 2018-06-27 0:09
 */
public class MapRecordLatestDTO implements Serializable {

    private static final long serialVersionUID = 5478462485940143342L;

    /**
     * 传感器信息
     */
    private DeviceInfoRpDTO header;

    /**
     * 记录数据
     */
    private RecordLatestRpDTO data;

    public MapRecordLatestDTO(DeviceInfoRpDTO header, RecordLatestRpDTO data) {
        this.header = header;
        this.data = data;
    }

    public DeviceInfoRpDTO getHeader() {
        return header;
    }

    public void setHeader(DeviceInfoRpDTO header) {
        this.header = header;
    }

    public RecordLatestRpDTO getData() {
        return data;
    }

    public void setData(RecordLatestRpDTO data) {
        this.data = data;
    }
}
