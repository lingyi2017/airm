package com.infosoul.mserver.entity.airm;

import com.infosoul.mserver.common.persistence.IdEntity;

/**
 * 传感器
 *
 * @author longxy
 * @date 2018-06-14 22:39
 */
public class Sensor extends IdEntity<Sensor> {

    private static final long serialVersionUID = 7536319556209713866L;

    /**
     * 传感器编号
     */
    private Integer serialNum;

    /**
     * 传感器名称
     */
    private String name;

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
}
