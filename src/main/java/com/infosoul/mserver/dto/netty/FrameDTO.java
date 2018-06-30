package com.infosoul.mserver.dto.netty;

import com.infosoul.mserver.enums.FrameTypeEnum;

/**
 * 设备协议帧 DTO
 *
 * @author longxy
 * @date 2018-06-29 23:48
 */
public class FrameDTO {

    /**
     * 开头字节
     */
    private Integer start;

    /**
     * 协议类型
     */
    private FrameTypeEnum frameType;

    /**
     * 数据长度
     */
    private Integer DataLength;

    /**
     * 协议数据
     */
    private Object data;

    /**
     * CRC-16校验和
     */
    private Integer crc16;

    public FrameDTO() {
        this.start = 0x01;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public FrameTypeEnum getFrameType() {
        return frameType;
    }

    public void setFrameType(FrameTypeEnum frameType) {
        this.frameType = frameType;
    }

    public Integer getDataLength() {
        return DataLength;
    }

    public void setDataLength(Integer dataLength) {
        DataLength = dataLength;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCrc16() {
        return crc16;
    }

    public void setCrc16(Integer crc16) {
        this.crc16 = crc16;
    }
}
