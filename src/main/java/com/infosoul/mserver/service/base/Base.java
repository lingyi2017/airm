package com.infosoul.mserver.service.base;

import java.util.List;

/**
 * 
 * <p> Title: Base基类，Hibernate外使用</p>
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
 * @date:    2014-11-25  下午4:01:45
 *
 */
public interface Base<T> {
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
    public T findById(String id);
    
	public List<T> findAll(T t);
	
	public void add(T t) throws Exception;
	
	public void update(T t) throws Exception;
	
	public void delete(T t) throws Exception;
	
	public void deleteById(String id) throws Exception;
	
}
