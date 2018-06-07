package com.infosoul.mserver.common.utils;

/**
 * 图片工具类
 * 
 * @author longxy
 * @date 2015-10-28 上午11:01:07
 * @version V1.0
 */
public class PhotoUtil {

	public static String getPhoto(String photo){
		if(StringUtils.isNotBlank(photo)){
			photo = photo.split("\\*")[0];
		}
		return photo;
	}
}
