package com.infosoul.mserver.controller.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infosoul.mserver.common.config.Global;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.controller.BaseController;
import com.infosoul.mserver.entity.sys.Office;
import com.infosoul.mserver.entity.sys.Role;
import com.infosoul.mserver.entity.sys.User;
import com.infosoul.mserver.service.sys.OfficeService;
import com.infosoul.mserver.service.sys.SystemService;

/**
 * 角色Controller
 *
 * @author free lance
 * @version 2013-5-15 update 2013-06-08
 */
@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private OfficeService officeService;

    @ModelAttribute("role")
    public Role get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return systemService.getRole(id);
        } else {
            return new Role();
        }
    }

    @RequiresPermissions("sys:role:view")
    @RequestMapping(value = {"list", ""})
    public String list(Role role, Model model) {
        List<Role> list = systemService.findAllRole();
        model.addAttribute("list", list);
        return "/sys/roleList";
    }

    @RequiresPermissions("sys:role:view")
    @RequestMapping(value = "form")
    public String form(Role role, Model model) {
        if (role.getOffice() == null) {
            role.setOffice(UserUtils.getUser().getOffice());
        }
        model.addAttribute("role", role);
        model.addAttribute("menuList", systemService.findAllMenu());
//        model.addAttribute("officeList", officeService.findAll());
        return "/sys/roleForm";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "save")
    public String save(Role role, Model model, String oldName, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, role)) {
            return form(role, model);
        }
        if (!"true".equals(checkName(oldName, role.getName()))) {
            addMessage(model, "保存角色'" + role.getName() + "'失败, 角色名已存在");
            return form(role, model);
        }
        systemService.saveRole(role);
        addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
        return "redirect:/sys/role/?repage";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "delete")
    public String delete(@RequestParam String id, RedirectAttributes redirectAttributes) {
        if (Role.isAdmin(id)) {
            addMessage(redirectAttributes, "删除角色失败, 不允许内置角色或编号空");
        } else {
            systemService.deleteRole(id);
            addMessage(redirectAttributes, "删除角色成功");
        }
        return "redirect:/sys/role/?repage";
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "assign")
    public String assign(Role role, Model model) {
        List<User> users = role.getUserList();
        model.addAttribute("users", users);
        return "/sys/roleAssign";
    }

    @RequiresPermissions("sys:role:view")
    @RequestMapping(value = "usertorole")
    public String selectUserToRole(Role role, Model model) {
        model.addAttribute("role", role);
        model.addAttribute("selectIds", role.getUserIds());
        model.addAttribute("officeList", officeService.findAll());
        return "/sys/selectUserToRole";
    }

    @RequiresPermissions("sys:role:view")
    @ResponseBody
    @RequestMapping(value = "users")
    public List<Map<String, Object>> users(String officeId, HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        List<Map<String, Object>> mapList = Lists.newArrayList();
        Office office = officeService.get(officeId);
        List<User> userList = office.getUserList();
        for (User user : userList) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", user.getId());
            map.put("pId", 0);
            map.put("name", user.getName());
            mapList.add(map);
        }
        return mapList;
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "outrole")
    public String outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
        Role role = systemService.getRole(roleId);
        User user = systemService.getUser(userId);
        if (user.equals(UserUtils.getUser())) {
            addMessage(redirectAttributes, "无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
        } else {
            Boolean flag = systemService.outUserInRole(role, userId);
            if (!flag) {
                addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
            } else {
                addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
            }
        }
        return "redirect:/sys/role/assign?id=" + role.getId();
    }

    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "assignrole")
    public String assignRole(Role role, String[] idsArr, RedirectAttributes redirectAttributes) {
        StringBuilder msg = new StringBuilder();
        int newNum = 0;
        for (int i = 0; i < idsArr.length; i++) {
            User user = systemService.assignUserToRole(role, idsArr[i]);
            if (null != user) {
                msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + role.getName() + "】！");
                newNum++;
            }
        }
        addMessage(redirectAttributes, "已成功分配 " + newNum + " 个用户" + msg);
        return "redirect:/sys/role/assign?id=" + role.getId();
    }

    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        if (name != null && name.equals(oldName)) {
            return "true";
        } else if (name != null && systemService.findRoleByName(name) == null) {
            return "true";
        }
        return "false";
    }

}
