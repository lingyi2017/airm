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
import com.infosoul.mserver.entity.airm.LatchConfig;
import com.infosoul.mserver.service.airm.LatchConfigService;

/**
 * 门限配置 Controller
 *
 * @author longxy
 * @date 2018-06-16 1:44
 */
@Controller
@RequestMapping(value = "/airm/latchConfig")
public class LatchConfigController extends BaseController {

    @Autowired
    private LatchConfigService latchConfigService;

    @ModelAttribute
    public LatchConfig get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return latchConfigService.get(id);
        } else {
            return new LatchConfig();
        }
    }

    /**
     * 门限配置列表
     * 
     * @param entity
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("airm:latchConfig:view")
    @RequestMapping(value = {"list", ""})
    public String list(LatchConfig entity, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<LatchConfig> page = latchConfigService.findList(new Page<>(request, response), entity);
        model.addAttribute("page", page);
        return "airm/latchConfigList";
    }

    /**
     * 配置信息
     * 
     * @param entity
     * @param model
     * @return
     */
    @RequiresPermissions("airm:latchConfig:view")
    @RequestMapping("/form")
    public String form(LatchConfig entity, Model model) {
        model.addAttribute("latchConfig", entity);
        return "airm/latchConfigForm";
    }

    /**
     * 配置保存
     * 
     * @param entity
     * @param redirAttr
     * @return
     */
    @RequiresPermissions("airm:latchConfig:edit")
    @RequestMapping(value = "save")
    public String save(LatchConfig entity, RedirectAttributes redirAttr) {
        try {
            latchConfigService.save(entity);
            addMessage(redirAttr, "保存配置'" + entity.getName() + "'成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("配置保存异常", e);
        }
        return "redirect:/airm/latchConfig/list?repage";
    }

    /**
     * 配置修改
     * 
     * @param entity
     * @param redirAttr
     * @return
     */
    @RequiresPermissions("airm:latchConfig:edit")
    @RequestMapping("/update")
    public String update(LatchConfig entity, RedirectAttributes redirAttr) {
        try {
            latchConfigService.update(entity);
            addMessage(redirAttr, "修改配置'" + entity.getName() + "'成功");
        } catch (Exception e) {
            logger.error("配置修改异常", e);
        }
        return "redirect:/airm/latchConfig/list?repage";
    }

    /**
     * 删除配置
     * 
     * @param entity
     * @param redirAttr
     * @return
     */
    @RequiresPermissions("airm:latchConfig:edit")
    @RequestMapping("/delete")
    public String delete(LatchConfig entity, RedirectAttributes redirAttr) {
        try {
            latchConfigService.delete(entity.getId());
            addMessage(redirAttr, "删除配置'" + entity.getName() + "'成功");
        } catch (Exception e) {
            logger.error("配置删除异常", e);
        }
        return "redirect:/airm/latchConfig/list";
    }
}
