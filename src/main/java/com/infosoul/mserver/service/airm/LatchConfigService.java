package com.infosoul.mserver.service.airm;

import java.util.List;

import com.infosoul.mserver.common.utils.IdGen;
import com.infosoul.mserver.common.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.entity.airm.LatchConfig;
import com.infosoul.mserver.repository.mybatis.airm.ILatchConfigDao;
import com.infosoul.mserver.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 门限配置 Service
 *
 * @author longxy
 * @date 2018-06-16 1:39
 */
@Service
public class LatchConfigService extends BaseService {

    @Autowired
    private ILatchConfigDao latchConfigDao;

    public LatchConfig get(String id) {
        return latchConfigDao.findById(id);
    }

    /**
     * 门限配置列表
     * 
     * @param page
     * @param entity
     * @return
     */
    public Page<LatchConfig> findList(Page<LatchConfig> page, LatchConfig entity) {
        if (StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_date DESC");
        }
        entity.setPage(page);
        List<LatchConfig> list = latchConfigDao.findList(entity);
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
    public void save(LatchConfig entity) throws Exception {
        entity.setId(IdGen.uuid());
        latchConfigDao.save(entity);
    }

    /**
     * 删除
     * 
     * @param id
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void delete(String id) throws Exception {
        latchConfigDao.deleteById(id);
    }

    /**
     * 修改
     * 
     * @param entity
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void update(LatchConfig entity) throws Exception {
        latchConfigDao.update(entity);
    }
}
