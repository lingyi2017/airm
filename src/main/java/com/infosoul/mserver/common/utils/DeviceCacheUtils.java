package com.infosoul.mserver.common.utils;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.repository.mybatis.airm.IDeviceDao;
import com.infosoul.mserver.service.BaseService;

/**
 * 设备缓存
 *
 * @author longxy
 * @date 2018-07-01 0:29
 */
public class DeviceCacheUtils extends BaseService {

    private static IDeviceDao deviceDao = ApplicationContextHelper.getBean(IDeviceDao.class);

    /**
     * 未注册设备缓存
     */
    public static final List<String> UNREGISTERED_CACHE = Lists.newArrayList();

    /**
     * 设备是否注册：false-未注册；true-注册
     *
     * @param deviceId
     * @return
     */
    public static Boolean isRegister(String deviceId) {
        if (CollectionUtils.isEmpty(UNREGISTERED_CACHE)) {
            init();
        }
        if (CollectionUtils.isEmpty(UNREGISTERED_CACHE)) {
            return false;
        }
        if (UNREGISTERED_CACHE.contains(deviceId)) {
            return false;
        }
        return true;
    }

    /**
     * 清空缓存
     */
    public static void clear() {
        UNREGISTERED_CACHE.clear();
    }

    private static void init() {
        List<Device> devices = deviceDao.findAll(new Device());
        if (!CollectionUtils.isEmpty(devices)) {
            for (Device device : devices) {
                if (Constant.DEVICE_UNREGISTERED.equals(buildRegister(device.getRegister()))) {
                    UNREGISTERED_CACHE.add(device.getDeviceId());
                }
            }
        }
    }

    private static String buildRegister(String register) {
        if (StringUtils.isEmpty(register)) {
            return Constant.DEVICE_UNREGISTERED;
        }
        return register;
    }

}
