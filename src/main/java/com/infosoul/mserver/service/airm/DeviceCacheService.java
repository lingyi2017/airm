package com.infosoul.mserver.service.airm;

import com.infosoul.mserver.common.utils.ApplicationContextHelper;
import com.infosoul.mserver.repository.mybatis.airm.IDeviceDao;
import com.infosoul.mserver.service.BaseService;

/**
 * 设备缓存 Service
 *
 * @author longxy
 * @date 2018-07-01 0:29
 */
public class DeviceCacheService extends BaseService {

    private static IDeviceDao deviceDao = ApplicationContextHelper.getBean(IDeviceDao.class);

    public static Boolean isRegister(String deviceId){
        return false;
    }

}
