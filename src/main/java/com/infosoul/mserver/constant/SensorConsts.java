package com.infosoul.mserver.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 传感器相关常量
 *
 * @author longxy
 * @date 2018-06-14 23:00
 */
public class SensorConsts {

    /**
     * 传感器单位
     */
    public static final Map<Integer, String> unit = new ConcurrentHashMap<>();

    static {
        unit.put(0x00, "%LEL");
        unit.put(0x01, "%VOL");
        unit.put(0x02, "ppm");
        unit.put(0x03, "ppb");
    }
}
