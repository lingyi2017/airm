package com.infosoul.mserver.common.utils;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wdl on 14-3-21.
 */
public class HttpAttributeHelper {

    public static void postMenuId(HttpServletRequest request, Model model) {
        String menuId = request.getParameter("id");
        model.addAttribute("menuId", menuId);
    }

}
