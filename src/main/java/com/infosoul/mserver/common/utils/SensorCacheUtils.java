package com.infosoul.mserver.common.utils;

import com.infosoul.mserver.entity.airm.Sensor;
import com.infosoul.mserver.repository.mybatis.airm.ISensorDao;
import com.infosoul.mserver.service.BaseService;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 传感器缓存
 *
 * @author longxy
 * @date 2018-07-01 11:18
 */
public class SensorCacheUtils extends BaseService {

    private static ISensorDao sensorDao = ApplicationContextHelper.getBean(ISensorDao.class);

    /**
     * 传感器名称
     */
    private static final Map<Integer, String> NAME_CACHE = new ConcurrentHashMap<>();

    public static String getName(Integer serialNum) {
        if (null == serialNum) {
            return null;
        }
        if (CollectionUtils.isEmpty(NAME_CACHE)) {
            init();
        }
        if (CollectionUtils.isEmpty(NAME_CACHE)) {
            return null;
        }
        if (NAME_CACHE.containsKey(serialNum)) {
            return NAME_CACHE.get(serialNum);
        }
        return null;
    }

    private static void init() {
        List<Sensor> sensors = sensorDao.findAll(new Sensor());
        if (!CollectionUtils.isEmpty(sensors)) {
            for (Sensor sensor : sensors) {
                NAME_CACHE.put(sensor.getSerialNum(), sensor.getName());
            }
        }
    }

    /**
     * 清空缓存
     */
    public static void clear() {
        NAME_CACHE.clear();
    }
}
