package com.infosoul.mserver.dto.web;

import java.io.Serializable;

/**
 * 传感器 DTO
 *
 * @author longxy
 * @date 2018-07-06 22:30
 */
public class SensorDTO implements Serializable {

    private static final long serialVersionUID = 4604940400901833965L;

    /**
     * 传感器序号
     */
    private Integer num;

    /**
     * 传感器名称
     */
    private String name;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
