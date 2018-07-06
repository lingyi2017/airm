package com.infosoul.mserver.controller.airm;

import com.google.common.collect.Lists;
import com.infosoul.mserver.common.utils.DateUtils;
import com.infosoul.mserver.common.utils.DeviceUtils;
import com.infosoul.mserver.controller.BaseController;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.entity.airm.Sensor;
import com.infosoul.mserver.enums.SensorEnum;
import com.infosoul.mserver.service.airm.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 报表统计 Controller
 *
 * @author longxy
 * @date 2018-07-05 23:47
 */
@Controller
@RequestMapping(value = "/airm/param/stat")
public class StatController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = {""})
    public String paramStat(Model model) {
        List<Device> devices = deviceService.findAll(new Device());
        if (!CollectionUtils.isEmpty(devices)) {
            Device device = devices.get(0);
            model.addAttribute("sensors", DeviceUtils.getSensors(device));
        }
        model.addAttribute("date", DateUtils.getDate());
        model.addAttribute("devices", devices);
        return "airm/paramStat";
    }

}
