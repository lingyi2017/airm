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
    public static final Map<Integer, String> UNIT = new ConcurrentHashMap<>();

    static {
        UNIT.put(0x00, "%LEL");
        UNIT.put(0x01, "%VOL");
        UNIT.put(0x02, "PPM");
        UNIT.put(0x03, "PPB");
    }

    public static String getUnit(Integer sensorUnitNum) {
        if (null == sensorUnitNum) {
            return null;
        }
        if (UNIT.containsKey(sensorUnitNum)) {
            return UNIT.get(sensorUnitNum);
        }
        return null;
    }
}
