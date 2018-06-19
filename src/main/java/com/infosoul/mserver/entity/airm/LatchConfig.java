package com.infosoul.mserver.entity.airm;

import com.infosoul.mserver.common.persistence.IdEntity;

/**
 * 门限配置
 *
 * @author longxy
 * @date 2018-06-14 23:37
 */
public class LatchConfig extends IdEntity<LatchConfig> {

    private static final long serialVersionUID = -314700294393604698L;

    /**
     * 传感器编号
     */
    private Integer serialNum;

    /**
     * 传感器名称
     */
    private String name;

    /**
     * 参数最大值
     */
    private Double maxVal;

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Double maxVal) {
        this.maxVal = maxVal;
    }
}
