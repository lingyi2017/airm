package com.infosoul.mserver.controller.airm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.controller.BaseController;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.service.airm.DeviceService;

/**
 * 设备 Controller
 *
 * @author longxy
 * @date 2018-06-21 00:25
 */
@Controller
@RequestMapping(value = "/airm/device")
public class DeviceController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    @ModelAttribute
    public Device get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return deviceService.get(id);
        } else {
            return new Device();
        }
    }

    /**
     * 设备列表
     * 
     * @param entity
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("airm:device:view")
    @RequestMapping(value = {"list", ""})
    public String list(Device entity, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Device> page = deviceService.findList(new Page<>(request, response), entity);
        model.addAttribute("page", page);
        return "airm/deviceList";
    }

    /**
     * 设备信息
     * 
     * @param entity
     * @param model
     * @return
     */
    @RequiresPermissions("airm:device:view")
    @RequestMapping("/form")
    public String form(Device entity, Model model) {
        model.addAttribute("device", entity);
        return "airm/deviceForm";
    }

    /**
     * 设备修改
     * 
     * @param entity
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("airm:device:edit")
    @RequestMapping("/update")
    public String update(Device entity, RedirectAttributes redirectAttributes) {
        try {
            deviceService.update(entity);
            addMessage(redirectAttributes, "保存设备'" + entity.getName() + "'成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设备修改异常", e);
        }
        return "redirect:/airm/device/?repage";
    }
}
