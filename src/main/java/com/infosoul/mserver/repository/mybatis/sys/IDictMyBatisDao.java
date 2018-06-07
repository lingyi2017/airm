package com.infosoul.mserver.repository.mybatis.sys;

import java.util.List;

import com.infosoul.mserver.common.persistence.annotation.MyBatisRepository;
import com.infosoul.mserver.entity.sys.Dict;

/**
 * MyBatis字典DAO接口
 * @author L.J
 * @version 2013-8-23
 */
@MyBatisRepository
public interface IDictMyBatisDao {
	
    Dict get(String id);
    
    List<Dict> find(Dict dict);
    
}
