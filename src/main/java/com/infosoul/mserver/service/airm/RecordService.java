package com.infosoul.mserver.service.airm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.utils.IdGen;
import com.infosoul.mserver.common.utils.StringUtils;
import com.infosoul.mserver.entity.airm.Record;
import com.infosoul.mserver.repository.mybatis.airm.IRecordDao;
import com.infosoul.mserver.service.BaseService;

/**
 * 历史记录 Service
 *
 * @author longxy
 * @date 2018-06-21 23:34
 */
@Service
public class RecordService extends BaseService {

    @Autowired
    private IRecordDao recordDao;

    public Record get(String id) {
        return recordDao.findById(id);
    }

    /**
     * 历史记录列表
     * 
     * @param page
     * @param entity
     * @return
     */
    public Page<Record> findList(Page<Record> page, Record entity) {
        if (StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_date DESC");
        }
        entity.setPage(page);
        List<Record> list = recordDao.findList(entity);
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
    public void save(Record entity) throws Exception {
        entity.setId(IdGen.uuid());
        recordDao.save(entity);
    }
}
