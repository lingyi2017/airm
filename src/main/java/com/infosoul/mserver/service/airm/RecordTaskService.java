package com.infosoul.mserver.service.airm;

import java.util.List;

import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.enums.DeviceStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.infosoul.mserver.common.utils.DeviceCacheUtils;
import com.infosoul.mserver.common.utils.NettyUtils;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.service.BaseService;

/**
 * 定时获取设备信息
 *
 * @author longxy
 * @date 2018-07-01 1:32
 */
@Component
@Lazy(value = false)
public class RecordTaskService extends BaseService {

    @Autowired
    private DeviceService deviceService;

    @Scheduled(cron = "${web.socket.task}")
    public void getRecords() {
        try {
            List<Device> devices = deviceService.findAll(new Device());
            if (!CollectionUtils.isEmpty(devices)) {
                for (Device device : devices) {
                    if (DeviceStatusEnum.OFF_LINE.getCode().equals(device.getStatus())) {
                        continue;
                    }
                    try {
                        if (Constant.DEVICE_UNREGISTERED.equals(device.getRegister())) {
                            NettyUtils.sensorInfo(device.getDeviceId());
                        } else if (Constant.DEVICE_REGISTER.equals(device.getRegister())) {
                            NettyUtils.sensorData(device.getDeviceId());
                        }
                    } catch (Exception e) {
                        logger.error("设备定时读取异常 device id : " + device.getId(), e);
                    }

                }
            }
        } catch (Exception e) {
            logger.error("定时获取设备数据异常", e);
        }
    }
}
