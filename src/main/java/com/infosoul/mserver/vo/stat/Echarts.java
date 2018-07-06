package com.infosoul.mserver.vo.stat;

import java.util.List;


/**
 * echarts对象
 *
 * @author longxy
 * @date 2018-07-05 23:47
 */
public class Echarts {

    /**
     * X轴数据
     */
    private List<String> xData;

    /**
     * 
     */
    private Series series;

    public Echarts(List<String> xData, Series series) {
        this.xData = xData;
        this.series = series;
    }

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
