package com.infosoul.mserver.repository.mybatis.airm;

import com.infosoul.mserver.common.persistence.BaseMybatisDao;
import com.infosoul.mserver.common.persistence.annotation.MyBatisRepository;
import com.infosoul.mserver.entity.airm.Device;

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
}
