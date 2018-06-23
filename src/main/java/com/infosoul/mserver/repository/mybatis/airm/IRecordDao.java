package com.infosoul.mserver.repository.mybatis.airm;

import com.infosoul.mserver.common.persistence.BaseMybatisDao;
import com.infosoul.mserver.common.persistence.annotation.MyBatisRepository;
import com.infosoul.mserver.entity.airm.Record;

/**
 * Created by Ecloud on 2018/6/21.
 */
@MyBatisRepository
public interface IRecordDao extends BaseMybatisDao<Record> {
    /**
     * 设备最近一条历史记录
     * 
     * @param deviceId
     * @return
     */
    Record findLatestByDeviceId(String deviceId);
}
