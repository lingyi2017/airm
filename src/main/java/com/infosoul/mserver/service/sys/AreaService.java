/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.infosoul.mserver.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosoul.mserver.common.persistence.Parameter;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.entity.sys.Area;
import com.infosoul.mserver.repository.hibernate.sys.AreaDao;
import com.infosoul.mserver.service.BaseService;

/**
 * 区域Service
 * 
 * @author free lance
 * @version 2013-5-29
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends BaseService {

    @Autowired
    private AreaDao areaDao;

    public Area get(String id) {
        return areaDao.get(id);
    }

    public List<Area> findAll() {
        return UserUtils.getAreaList();
    }

    @Transactional(readOnly = false)
    public void save(Area area) {
        area.setParent(this.get(area.getParent().getId()));
        String oldParentIds = area.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
        area.setParentIds(area.getParent().getParentIds() + area.getParent().getId() + ",");
        areaDao.clear();
        areaDao.save(area);
        // 更新子节点 parentIds
        List<Area> list = areaDao.findByParentIdsLike("%," + area.getId() + ",%");
        for (Area e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, area.getParentIds()));
        }
        areaDao.save(list);
        UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        areaDao.deleteById(id, "%," + id + ",%");
        UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
    }
    
    /**
     * 
     * @param name 区域名称
     * @param type 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
     * @return
     */
    public List<Area> findByNameAndType(String name, String type, String parent) {
        return areaDao.find("from Area where name = :p1 and type = :p2 and parent.name = :p3", new Parameter(name, type, parent));
    }

}
