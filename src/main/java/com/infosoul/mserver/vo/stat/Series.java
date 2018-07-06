package com.infosoul.mserver.vo.stat;

import java.util.List;

/**
 * series
 *
 * @author longxy
 * @date 2018-07-05 23:47
 */
public class Series {

    /**
     * 参数名称
     */
    public String name;

    /**
     * 参数集合
     */
    public List<Double> data;

    /**
     * 参数单位
     */
    private String yUnit;

    public Series(String name, List<Double> data, String yUnit) {
        this.name = name;
        this.data = data;
        this.yUnit = yUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public String getyUnit() {
        return yUnit;
    }

    public void setyUnit(String yUnit) {
        this.yUnit = yUnit;
    }
}
