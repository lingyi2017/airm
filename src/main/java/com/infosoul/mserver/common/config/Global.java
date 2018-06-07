package com.infosoul.mserver.common.config;

import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.infosoul.mserver.common.utils.PropertiesLoader;


/**
 * 全局配置类
 * @author free lance
 * @version 2013-03-23
 */
public class Global {
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("application.properties");
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value);
		}
		return value;
	}

	/////////////////////////////////////////////////////////
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		String dir = getConfig("adminPath");
        Assert.hasText(dir, "配置文件里没有配置adminPath属性");

        return dir;
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		String dir = getConfig("frontPath");
        Assert.hasText(dir, "配置文件里没有配置frontPath属性");

        return dir;
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		String dir = getConfig("urlSuffix");
        Assert.hasText(dir, "配置文件里没有配置urlSuffix属性");

        return dir;
	}
	
}
