package com.infosoul.mserver.repository.mybatis.airm;

import com.infosoul.mserver.common.persistence.BaseMybatisDao;
import com.infosoul.mserver.common.persistence.annotation.MyBatisRepository;
import com.infosoul.mserver.entity.airm.Record;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

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

    /**
     * 获取传感器的平均值
     * 
     * @param paramMap
     * @return
     */
    Double getSensorAvg(Map<String, String> paramMap);

    /**
     * 30min内的告警数
     * 
     * @param paramMap
     * @return
     */
    Integer getRecordCount(Map<String, String> paramMap);
}
