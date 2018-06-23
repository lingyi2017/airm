package com.infosoul.mserver.repository.mybatis.airm;

import com.infosoul.mserver.common.persistence.BaseMybatisDao;
import com.infosoul.mserver.common.persistence.annotation.MyBatisRepository;
import com.infosoul.mserver.entity.airm.UserRecord;

/**
 * Created by Ecloud on 2018/6/23.
 */
@MyBatisRepository
public interface IUserRecordDao extends BaseMybatisDao<UserRecord> {

    /**
     * 获取记录阅读状态
     * 
     * @param userRecord
     * @return
     */
    String getStatus(UserRecord userRecord);

    /**
     * 更新记录阅读状态
     * 
     * @param userRecord
     */
    void updateStatus(UserRecord userRecord);
}
