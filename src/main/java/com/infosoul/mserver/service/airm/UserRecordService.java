package com.infosoul.mserver.service.airm;

import com.infosoul.mserver.common.utils.IdGen;
import com.infosoul.mserver.entity.airm.UserRecord;
import com.infosoul.mserver.repository.mybatis.airm.IUserRecordDao;
import com.infosoul.mserver.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户-设备告警记录 Service
 *
 * @author longxy
 * @date 2018-06-23 23:16
 */
@Service
public class UserRecordService extends BaseService {

    @Autowired
    private IUserRecordDao userRecordDao;

    /**
     * 保存
     *
     * @param entity
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void save(UserRecord entity) throws Exception {
        entity.setId(IdGen.uuid());
        userRecordDao.save(entity);
    }

    /**
     * 获取记录阅读状态
     * 
     * @param userRecord
     * @return
     */
    public String getStatus(UserRecord userRecord) {
        return userRecordDao.getStatus(userRecord);
    }

    /**
     * 更新记录阅读状态
     * 
     * @param userRecord
     */
    public void updateStatus(UserRecord userRecord) {
        userRecordDao.updateStatus(userRecord);
    }

}
