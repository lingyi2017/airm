package com.infosoul.mserver.api.app.airm;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.infosoul.mserver.api.BaseResource;
import com.infosoul.mserver.api.ResponseRest;
import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.common.web.MediaTypes;
import com.infosoul.mserver.dto.api.DeviceInfoRpDTO;
import com.infosoul.mserver.dto.api.DeviceInfoRqDTO;
import com.infosoul.mserver.dto.api.DeviceListRpDTO;
import com.infosoul.mserver.dto.api.DeviceListRqDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.service.airm.DeviceService;
import com.infosoul.mserver.service.airm.RecordService;

/**
 * APP端设备RESTFull接口
 *
 * @author longxy
 * @date 2018-06-22 22:56
 */
@Path("/app/device")
@Consumes(MediaTypes.JSON_UTF_8)
@Produces(MediaTypes.JSON_UTF_8)
@Component
public class DeviceAppResource extends BaseResource {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RecordService recordService;

    /**
     * 设备列表
     * 
     * @param dto
     * @return
     */
    @POST
    @Path("/list")
    public ResponseRest deviceList(DeviceListRqDTO dto) {
        try {
            Page<Device> page = deviceService.findAppList(dto);
            if (null == page.getList()) {
                return success(page.getList(), 0L);
            }
            List<Device> devices = page.getList();
            List<DeviceListRpDTO> rpDTOS = Lists.newArrayList();
            for (Device device : devices) {
                DeviceListRpDTO rpDTO = new DeviceListRpDTO();
                BeanUtils.copyProperties(device, rpDTO);
                Record record = recordService.findLatestByDeviceId(device.getDeviceId());
                if (null != record) {
                    rpDTO.setAqi(record.getAqi());
                } else {
                    rpDTO.setAqi(0);
                }
                rpDTOS.add(rpDTO);
            }
            return success(rpDTOS, page.getCount());
        } catch (Exception e) {
            logger.error("APP端获取设备列表异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端获取设备列表异常");
        }
    }

    /**
     * 设备信息
     * 
     * @param dto
     * @return
     */
    @POST
    @Path("/info")
    public ResponseRest deviceInfo(DeviceInfoRqDTO dto) {
        if (null == dto || StringUtils.isEmpty(dto.getDeviceId())) {
            return error(ResponseRest.Status.BAD_REQUEST, "设备ID不能为空");
        }
        try {
            Device device = deviceService.findByDeviceId(dto.getDeviceId());
            if (null == device) {
                return error(ResponseRest.Status.NOT_EXIST, "设备ID : " + dto.getDeviceId() + "对应的设备不存在");
            }
            DeviceInfoRpDTO rp = new DeviceInfoRpDTO();
            BeanUtils.copyProperties(device, rp);
            buildDeviceInfoRp(device, rp);
            rp.setAqi("aqi");
            return success(rp);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("APP端获取设备信息异常", e.getMessage());
            return error(ResponseRest.Status.INTERNAL_SERVER_ERROR, "APP端获取设备信息异常");
        }
    }

    private void buildDeviceInfoRp(Device device, DeviceInfoRpDTO dto) {

        if (null == device.getSensorUnitNum1() || null == device.getSensorUnit1()) {
            dto.setSensorUnit1("");
        } else {
            if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum1())
                    || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum1())) {
                dto.setSensorUnit1(Constant.SENSOR_CONVERT_UNIT);
            }
        }

        if (null == device.getSensorUnitNum2() || null == device.getSensorUnit2()) {
            dto.setSensorUnit2("");
        } else {
            if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum2())
                    || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum2())) {
                dto.setSensorUnit2(Constant.SENSOR_CONVERT_UNIT);
            }
        }

        if (null == device.getSensorUnitNum3() || null == device.getSensorUnit3()) {
            dto.setSensorUnit3("");
        } else {
            if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum3())
                    || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum3())) {
                dto.setSensorUnit3(Constant.SENSOR_CONVERT_UNIT);
            }
        }

        if (null == device.getSensorUnitNum4() || null == device.getSensorUnit4()) {
            dto.setSensorUnit4("");
        } else {
            if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum4())
                    || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum4())) {
                dto.setSensorUnit4(Constant.SENSOR_CONVERT_UNIT);
            }
        }

        if (null == device.getSensorUnitNum5() || null == device.getSensorUnit5()) {
            dto.setSensorUnit5("");
        } else {
            if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum5())
                    || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum5())) {
                dto.setSensorUnit5(Constant.SENSOR_CONVERT_UNIT);
            }
        }

        if (null == device.getSensorUnitNum6() || null == device.getSensorUnit6()) {
            dto.setSensorUnit6("");
        } else {
            if (Constant.SENSOR_UNIT_PPM.equals(device.getSensorUnitNum6())
                    || Constant.SENSOR_UNIT_PPB.equals(device.getSensorUnitNum6())) {
                dto.setSensorUnit6(Constant.SENSOR_CONVERT_UNIT);
            }
        }
    }
}
