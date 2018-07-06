package com.infosoul.mserver.common.utils;

import com.google.common.collect.Lists;
import com.infosoul.mserver.dto.web.SensorDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.enums.SensorEnum;
import org.apache.ibatis.annotations.Case;

import java.util.List;

/**
 * 设备工具类
 *
 * @author longxy
 * @date 2018-07-06 22:25
 */
public class DeviceUtils {

    public static List<SensorDTO> getSensors(Device device) {
        if (null == device) {
            return null;
        }
        List<SensorDTO> sensors = Lists.newArrayList();

        SensorDTO sensor1 = new SensorDTO();
        sensor1.setNum(1);
        sensor1.setName(device.getSensorName1());
        sensors.add(sensor1);

        SensorDTO sensor2 = new SensorDTO();
        sensor2.setNum(2);
        sensor2.setName(device.getSensorName2());
        sensors.add(sensor2);

        SensorDTO sensor3 = new SensorDTO();
        sensor3.setNum(3);
        sensor3.setName(device.getSensorName3());
        sensors.add(sensor3);

        SensorDTO sensor4 = new SensorDTO();
        sensor4.setNum(1);
        sensor4.setName(device.getSensorName4());
        sensors.add(sensor4);

        SensorDTO sensor5 = new SensorDTO();
        sensor5.setNum(5);
        sensor5.setName(device.getSensorName5());
        sensors.add(sensor5);

        SensorDTO sensor6 = new SensorDTO();
        sensor6.setNum(6);
        sensor6.setName(device.getSensorName6());
        sensors.add(sensor6);

        SensorDTO sensor7 = new SensorDTO();
        sensor7.setNum(7);
        sensor7.setName(SensorEnum.CO2.getName());
        sensors.add(sensor7);

        SensorDTO sensor8 = new SensorDTO();
        sensor8.setNum(8);
        sensor8.setName(SensorEnum.PM1.getName());
        sensors.add(sensor8);

        SensorDTO sensor9 = new SensorDTO();
        sensor9.setNum(9);
        sensor9.setName(SensorEnum.PM25.getName());
        sensors.add(sensor9);

        SensorDTO sensor10 = new SensorDTO();
        sensor10.setNum(10);
        sensor10.setName(SensorEnum.PM10.getName());
        sensors.add(sensor10);

        SensorDTO sensor11 = new SensorDTO();
        sensor11.setNum(11);
        sensor11.setName(SensorEnum.TEM.getName());
        sensors.add(sensor11);

        SensorDTO sensor12 = new SensorDTO();
        sensor12.setNum(12);
        sensor12.setName(SensorEnum.HUM.getName());
        sensors.add(sensor12);
        return sensors;
    }

    /**
     * 获取传感器名称
     * 
     * @param device
     * @param sensorNum
     * @return
     */
    public static String getSensorName(Device device, Integer sensorNum) {
        // TODO 使用反射
        switch (sensorNum) {
            case 1:
                return device.getSensorName1();
            case 2:
                return device.getSensorName2();
            case 3:
                return device.getSensorName3();
            case 4:
                return device.getSensorName4();
            case 5:
                return device.getSensorName5();
            case 6:
                return device.getSensorName6();
            case 7:
                return SensorEnum.CO2.getName();
            case 8:
                return SensorEnum.PM1.getName();
            case 9:
                return SensorEnum.PM25.getName();
            case 10:
                return SensorEnum.PM10.getName();
            case 11:
                return SensorEnum.TEM.getName();
            case 12:
                return SensorEnum.HUM.getName();
            default:
                return "";
        }
    }

    /**
     * 获取传感器单位
     *
     * @param device
     * @param sensorNum
     * @return
     */
    public static String getSensorUnit(Device device, Integer sensorNum) {
        switch (sensorNum) {
            case 1:
                return device.getSensorUnit1();
            case 2:
                return device.getSensorUnit2();
            case 3:
                return device.getSensorUnit3();
            case 4:
                return device.getSensorUnit4();
            case 5:
                return device.getSensorUnit5();
            case 6:
                return device.getSensorUnit6();
            case 7:
                return SensorEnum.CO2.getUnit();
            case 8:
                return SensorEnum.PM1.getUnit();
            case 9:
                return SensorEnum.PM25.getUnit();
            case 10:
                return SensorEnum.PM10.getUnit();
            case 11:
                return SensorEnum.TEM.getUnit();
            case 12:
                return SensorEnum.HUM.getUnit();
            default:
                return "";
        }
    }
}
