package com.infosoul.mserver.service.airm;

import java.util.List;
import java.util.Map;

import com.infosoul.mserver.common.utils.Constant;
import com.infosoul.mserver.dto.BaseRqDTO;
import com.infosoul.mserver.dto.api.RecordListRqDTO;
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
        if (StringUtils.isNotEmpty(entity.getBeginDate()) && StringUtils.isNotEmpty(entity.getEndDate())) {
            entity.setBeginDate(entity.getBeginDate() + " 00:00:00");
            entity.setEndDate(entity.getEndDate() + " 23:59:59");
        }
        if (StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_date DESC");
        }
        entity.setPage(page);
        List<Record> list = recordDao.findList(entity);
        page.setList(list);
        return page;
    }

    /**
     * APP端历史记录列表
     *
     * @param dto
     * @return
     */
    public Page<Record> findAppList(RecordListRqDTO dto) {
        Page<Record> page = new Page<>();
        Record entity = new Record();
        entity.setDeviceId(dto.getDeviceId());
        if (null != dto.getPageNo() && null != dto.getPageSize()) {
            page.setPageNo(dto.getPageNo());
            page.setPageSize(dto.getPageSize());
        }
        return this.findList(page, entity);
    }

    /**
     * APP端告警记录列表
     *
     * @param dto
     * @return
     */
    public Page<Record> findAlarmAppList(RecordListRqDTO dto) {
        Page<Record> page = new Page<>();
        Record entity = new Record();
        entity.setStatus(Constant.RECORD_ALARM);
        if (null != dto.getPageNo() && null != dto.getPageSize()) {
            page.setPageNo(dto.getPageNo());
            page.setPageSize(dto.getPageSize());
        }
        return this.findList(page, entity);
    }

    /**
     * 设备最近一条历史记录
     * 
     * @param deviceId
     * @return
     */
    public Record findLatestByDeviceId(String deviceId) {
        return recordDao.findLatestByDeviceId(deviceId);
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

    /**
     * 获取传感器的平均值
     * 
     * @param paramMap
     * @return
     */
    public Double getSensorParamAvg(Map<String, String> paramMap) {
        return recordDao.getSensorAvg(paramMap);
    }

    /**
     * 30min内的告警数
     * 
     * @param paramMap
     * @return
     */
    public Integer getRecordCount(Map<String, String> paramMap) {
        return recordDao.getRecordCount(paramMap);
    }
}
