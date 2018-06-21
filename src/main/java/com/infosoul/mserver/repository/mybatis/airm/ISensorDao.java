package com.infosoul.mserver.repository.mybatis.airm;

import com.infosoul.mserver.common.persistence.BaseMybatisDao;
import com.infosoul.mserver.common.persistence.annotation.MyBatisRepository;
import com.infosoul.mserver.entity.airm.Device;
import com.infosoul.mserver.entity.airm.Sensor;

/**
 * Created by Ecloud on 2018/6/21.
 */
@MyBatisRepository
public interface ISensorDao extends BaseMybatisDao<Sensor> {

}
