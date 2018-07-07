package com.infosoul.mserver.common.utils.airm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.CollectionUtils;

import com.infosoul.mserver.common.utils.ApplicationContextHelper;
import com.infosoul.mserver.entity.airm.LatchConfig;
import com.infosoul.mserver.repository.mybatis.airm.ILatchConfigDao;
import com.infosoul.mserver.service.BaseService;

/**
 * 门限配置缓存
 *
 * @author longxy
 * @date 2018-07-08 23:18
 */
public class LatchConfigCacheUtils extends BaseService {

    private static ILatchConfigDao latchConfigDao = ApplicationContextHelper.getBean(ILatchConfigDao.class);

    /**
     * 门限值
     */
    private static final Map<Integer, Double> VAL_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取门限值
     * 
     * @param serialNum
     * @return
     */
    public static Double getMaxVal(Integer serialNum) {
        if (null == serialNum) {
            return null;
        }
        if (CollectionUtils.isEmpty(VAL_CACHE)) {
            init();
        }
        if (CollectionUtils.isEmpty(VAL_CACHE)) {
            return null;
        }
        if (VAL_CACHE.containsKey(serialNum)) {
            return VAL_CACHE.get(serialNum);
        }
        return null;
    }

    private static void init() {
        List<LatchConfig> latchConfigs = latchConfigDao.findAll(new LatchConfig());
        if (!CollectionUtils.isEmpty(latchConfigs)) {
            for (LatchConfig latchConfig : latchConfigs) {
                VAL_CACHE.put(latchConfig.getSerialNum(), latchConfig.getMaxVal());
            }
        }
    }

    /**
     * 清空缓存
     */
    public static void clear() {
        VAL_CACHE.clear();
    }
}
