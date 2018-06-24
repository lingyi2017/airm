package com.infosoul.mserver.controller.airm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosoul.mserver.controller.BaseController;
import com.infosoul.mserver.dto.web.MapInitDTO;

/**
 * 地图展示 Controller
 *
 * @author longxy
 * @date 2018-06-24 21:41
 */
@Controller
@RequestMapping(value = "/airm/map")
public class MapController extends BaseController {

    @Value("${map.baidu.url}")
    private String mapUrl;

    @Value("${map.baidu.center}")
    private String mapCenter;

    @Value("${map.baidu.zoom}")
    private String mapZoom;

    /**
     * 地图首页初始化
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"init", ""})
    public String init(Model model) {
        try {
            MapInitDTO dto = new MapInitDTO(mapUrl, mapCenter, Integer.parseInt(mapZoom));
            model.addAttribute("map", dto);
        } catch (Exception e) {
            logger.error("地图初始化异常", e);
        }
        return "/airm/map";
    }
}
