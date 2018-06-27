package com.infosoul.mserver.controller.airm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.controller.BaseController;
import com.infosoul.mserver.dto.web.MapInitDTO;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.service.airm.RecordService;

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

    @Autowired
    private RecordService recordService;

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

    /**
     * 设备记录列表
     * 
     * @param entity
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "record/list")
    public String findRecordList(Record entity, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Record> page = recordService.findList(new Page<>(request, response), entity);
        model.addAttribute("page", page);
        return "/airm/recordList";
    }
}
