package com.infosoul.mserver.repository.mybatis.airm;

import com.infosoul.mserver.common.persistence.BaseMybatisDao;
import com.infosoul.mserver.common.persistence.annotation.MyBatisRepository;
import com.infosoul.mserver.dto.web.DeviceMapSearchDTO;
import com.infosoul.mserver.entity.airm.Device;

import java.util.List;

/**
 * Created by Ecloud on 2018/6/20.
 */
@MyBatisRepository
public interface IDeviceDao extends BaseMybatisDao<Device> {

    /**
     * 通过deviceId查找
     * 
     * @param deviceId
     * @return
     */
    Device findByDeviceId(String deviceId);

    /**
     * 更新设备状态
     * 
     * @param device
     */
    void updateStatus(Device device);

    /**
     * 地图搜索提示
     * 
     * @param keyword
     * @return
     */
    List<DeviceMapSearchDTO> mapSearchTips(String keyword);
}
