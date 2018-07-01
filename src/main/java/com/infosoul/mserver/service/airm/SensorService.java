package com.infosoul.mserver.service.airm;

import java.util.List;

import com.infosoul.mserver.common.utils.SensorCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.utils.IdGen;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.entity.airm.Sensor;
import com.infosoul.mserver.repository.mybatis.airm.ISensorDao;
import com.infosoul.mserver.service.BaseService;

/**
 * 传感器 Service
 *
 * @author longxy
 * @date 2018-06-21 22:54
 */
@Service
public class SensorService extends BaseService {

    @Autowired
    private ISensorDao sensorDao;

    public Sensor get(String id) {
        return sensorDao.findById(id);
    }

    /**
     * 传感器列表
     * 
     * @param page
     * @param entity
     * @return
     */
    public Page<Sensor> findList(Page<Sensor> page, Sensor entity) {
        if (StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_date DESC");
        }
        entity.setPage(page);
        List<Sensor> list = sensorDao.findList(entity);
        page.setList(list);
        return page;
    }

    /**
     * 所有传感器
     *
     * @param entity
     * @return
     */
    public List<Sensor> findAll(Sensor entity) {
        return sensorDao.findAll(entity);
    }

    /**
     * 保存
     * 
     * @param entity
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void save(Sensor entity) throws Exception {
        entity.setId(IdGen.uuid());
        sensorDao.save(entity);
        SensorCacheUtils.clear();
    }

    /**
     * 删除
     * 
     * @param id
     */
    @Transactional(readOnly = false)
    public void delete(String id) throws Exception {
        sensorDao.deleteById(id);
        SensorCacheUtils.clear();
    }

    /**
     * 修改
     * 
     * @param entity
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void update(Sensor entity) throws Exception {
        sensorDao.update(entity);
        SensorCacheUtils.clear();
    }
}
