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
import com.infosoul.mserver.entity.airm.Sensor;
import com.infosoul.mserver.service.airm.SensorService;

/**
 * 传感器 Controller
 *
 * @author longxy
 * @date 2018-06-16 22:56
 */
@Controller
@RequestMapping(value = "/airm/sensor")
public class SensorController extends BaseController {

    @Autowired
    private SensorService sensorService;

    @ModelAttribute
    public Sensor get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return sensorService.get(id);
        } else {
            return new Sensor();
        }
    }

    /**
     * 传感器列表
     * 
     * @param entity
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("airm:sensor:view")
    @RequestMapping(value = {"list", ""})
    public String list(Sensor entity, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Sensor> page = sensorService.findList(new Page<>(request, response), entity);
        model.addAttribute("page", page);
        return "airm/sensorList";
    }

    /**
     * 传感器信息
     * 
     * @param entity
     * @param model
     * @return
     */
    @RequiresPermissions("airm:sensor:view")
    @RequestMapping("/form")
    public String form(Sensor entity, Model model) {
        model.addAttribute("sensor", entity);
        return "airm/sensorForm";
    }

    /**
     * 传感器保存
     * 
     * @param entity
     * @param redirAttr
     * @return
     */
    @RequiresPermissions("airm:sensor:edit")
    @RequestMapping(value = "save")
    public String save(Sensor entity, RedirectAttributes redirAttr) {
        try {
            sensorService.save(entity);
            addMessage(redirAttr, "保存传感器'" + entity.getName() + "'成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("传感器保存异常", e);
        }
        return "redirect:/airm/sensor/list/?repage";
    }

    /**
     * 传感器修改
     * 
     * @param entity
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("airm:sensor:edit")
    @RequestMapping("/update")
    public String update(Sensor entity, RedirectAttributes redirectAttributes) {
        try {
            sensorService.update(entity);
            addMessage(redirectAttributes, "修改传感器'" + entity.getName() + "'成功");
        } catch (Exception e) {
            logger.error("传感器修改异常", e);
        }
        return "redirect:/airm/sensor/?repage";
    }

    /**
     * 删除传感器
     * 
     * @param entity
     * @param redirAttr
     * @return
     */
    @RequiresPermissions("airm:sensor:edit")
    @RequestMapping("/delete")
    public String delete(Sensor entity, RedirectAttributes redirAttr) {
        try {
            sensorService.delete(entity.getId());
            addMessage(redirAttr, "删除传感器'" + entity.getName() + "'成功");
        } catch (Exception e) {
            logger.error("传感器删除异常", e);
        }
        return "redirect:/airm/sensor/list/?repage";
    }
}
