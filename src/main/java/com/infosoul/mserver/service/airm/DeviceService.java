package com.infosoul.mserver.service.airm;

import java.util.List;

import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.repository.mybatis.airm.IDeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.utils.IdGen;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.entity.airm.LatchConfig;
import com.infosoul.mserver.repository.mybatis.airm.ILatchConfigDao;
import com.infosoul.mserver.service.BaseService;

/**
 * 设备 Service
 *
 * @author longxy
 * @date 2018-06-21 00:14
 */
@Service
public class DeviceService extends BaseService {

    @Autowired
    private IDeviceDao deviceDao;

    public Device get(String id) {
        return deviceDao.findById(id);
    }

    /**
     * 通过deviceId获取
     * 
     * @param deviceId
     * @return
     */
    public Device findByDeviceId(String deviceId) {
        return deviceDao.findByDeviceId(deviceId);
    }

    /**
     * 设备列表
     * 
     * @param page
     * @param entity
     * @return
     */
    public Page<Device> findList(Page<Device> page, Device entity) {
        if (StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_date DESC");
        }
        entity.setPage(page);
        List<Device> list = deviceDao.findList(entity);
        page.setList(list);
        return page;
    }

    /**
     * 保存
     * 
     * @param entity
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void save(Device entity) throws Exception {
        entity.setId(IdGen.uuid());
        deviceDao.save(entity);
    }

    /**
     * 修改
     * 
     * @param entity
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void update(Device entity) throws Exception {
        deviceDao.update(entity);
    }
}