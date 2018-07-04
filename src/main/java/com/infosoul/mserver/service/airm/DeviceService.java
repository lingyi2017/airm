package com.infosoul.mserver.service.airm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.utils.DeviceCacheUtils;
import com.infosoul.mserver.common.utils.IdGen;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.dto.api.DeviceListRqDTO;
import com.infosoul.mserver.dto.web.DeviceMapSearchDTO;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.repository.mybatis.airm.IDeviceDao;
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
     * 所有设备
     * 
     * @param entity
     * @return
     */
    public List<Device> findAll(Device entity) {
        return deviceDao.findAll(entity);
    }

    /**
     * APP端设备列表
     * 
     * @param dto
     * @return
     */
    public Page<Device> findAppList(DeviceListRqDTO dto) {
        Page<Device> page = new Page<>();
        Device entity = new Device();
        if (null != dto) {
            if (null != dto.getPageNo() && null != dto.getPageSize()) {
                page.setPageNo(dto.getPageNo());
                page.setPageSize(dto.getPageSize());
            }
            if (StringUtils.isNotEmpty(dto.getName())) {
                entity.setName(dto.getName());
            }
            if (StringUtils.isNotEmpty(dto.getAddress())) {
                entity.setAddress(dto.getAddress());
            }
        }
        return this.findList(page, entity);
    }

    /**
     * 地图搜索提示
     * 
     * @param keyword
     * @return
     */
    public List<DeviceMapSearchDTO> mapSearchTips(String keyword) {
        return deviceDao.mapSearchTips(keyword);
    }

    /**
     * 保存
     * 
     * @param entity
     * @throws Exception
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(Device entity) throws Exception {
        entity.setId(IdGen.uuid());
        deviceDao.save(entity);
        DeviceCacheUtils.clear();
    }

    /**
     * 修改
     * 
     * @param entity
     * @throws Exception
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(Device entity) throws Exception {
        deviceDao.update(entity);
        DeviceCacheUtils.clear();
    }

    /**
     * 更新设备状态
     * 
     * @param entity
     * @throws Exception
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void updateStatus(Device entity) throws Exception {
        deviceDao.updateStatus(entity);
    }
}
