package com.infosoul.mserver.service.base;

import com.infosoul.mserver.common.persistence.Page;

/**
 * 所有服务接口都要继承这个
 * 
 * @author lanyuan
 * @date 2014-2-11
 * @Email: mmm333zzz520@163.com
 * @version 1.0v
 * @param <T>
 */
public interface BaseService<T> extends Base<T> {
    /**
     * 返回分页后的数据
     * 
     * @param pageView
     * @param t
     * @return
     */
    public Page<T> findPages(Page<T> page,T t);
}
