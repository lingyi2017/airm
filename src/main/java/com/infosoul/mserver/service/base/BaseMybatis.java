package com.infosoul.mserver.service.base;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p> Title: 所有的Mapper继承这个接口，已经实现基本的 增,删,改,查接口,不需要重复写</p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by ACTEC </p>
 * 
 * <p> Company: Free-Lancer </p>
 * 
 * @author:  free lance
 * @Email:   free.lance@Gmail.com
 * @version: 2.0
 * @date:    2014-11-25  下午4:01:32
 *
 */
public interface BaseMybatis<T> extends Base<T> {
	/**
	 * 返回分页后的数据
	 * @param List
	 * @param t
	 * @return
	 */
	public List<T> findPages(Map<String, Object> map);
}
