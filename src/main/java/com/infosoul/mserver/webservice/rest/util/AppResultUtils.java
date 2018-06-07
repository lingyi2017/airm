package com.infosoul.mserver.webservice.rest.util;

import java.util.List;
import java.util.Map;

import com.infosoul.mserver.api.Result;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Maps;
import com.infosoul.mserver.common.mapper.JsonMapper;
import com.infosoul.mserver.common.utils.DateUtils;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.entity.sys.User;

/**
 * 处理返回给客户端数据工具类
 *
 * @author longxy
 * @version V1.0
 * @date 2015-3-13 下午3:26:49
 */
public class AppResultUtils {

    /**
     * 处理服务端返回的集合数据
     *
     * @param pageList           服务端的结果集
     * @param notNeedValues      客户端不需要的参数集
     * @param stringToTimeValues 字符串转换成long型的参数列集
     * @param message            错误提示信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String handelList(Object pageList, String[] notNeedValues,
                                    String[] stringToTimeValues, String message) {

        try {
            JsonMapper mapper = JsonMapper.getInstance();  // 初始化
            JavaType javaType = mapper.contructMapType(Map.class, String.class, Object.class);  // 转化类型
            String listStr = mapper.toJson(pageList);  // 转为json字符串
            Map<String, Object> mapRes = mapper.fromJson(listStr, javaType);  // 转为map对象

            // 删除map对象中不需要的值
            mapRes.remove("html");

            List<Map<String, Object>> mapList = (List<Map<String, Object>>) mapRes.get("list");

            // 删除集合中不需要的值
            if (notNeedValues.length > 0 && null != mapList) {
                for (Map<String, Object> map : mapList) {
                    for (String value : notNeedValues) {
                        map.remove(value);  // 删除集合中不需要的值
                    }
                }
            }

            // 将服务端结果集中的时间字符串成long型
            if (stringToTimeValues.length > 0 && null != mapList) {
                for (String value : stringToTimeValues) {
                    for (Map<String, Object> map : mapList) {
                        String val = (String) map.get(value);
                        if (StringUtils.isNotBlank(val)) {  // 值不为空
                            map.put(value, DateUtils.parseDate(val).getTime());
                        } else {
                            map.put(value, 0);
                        }
                    }
                }
            }

            mapRes.put("list", mapList);
            mapRes.put("status", Result.Status.OK.getCode());  // 状态码
            mapRes.put("message", message);  // 描述信息
            return mapper.toJson(mapRes);
        } catch (Exception e) {
            return toString(Result.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 处理服务端返回的单个数据
     *
     * @param serverResult       服务端的结果集
     * @param notNeedValues      客户端不需要的参数列集
     * @param stringToTimeValues 字符串转换成long型的参数列集
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String handelSingle(Object serverResult,
                                      String[] notNeedValues, String[] stringToTimeValues) {
        try {
            JsonMapper mapper = JsonMapper.getInstance();  // 初始化
            JavaType javaType = mapper.contructMapType(Map.class, String.class, Object.class);  // 转化类型
            String listStr = mapper.toJson(serverResult);  // 转为json字符串
            Map<String, Object> mapRes = mapper.fromJson(listStr, javaType);  // 转为map对象

            Map<String, Object> map = (Map<String, Object>) mapRes.get("content");

            // 删除集合中不需要的值
            if (notNeedValues.length > 0) {
                for (String value : notNeedValues) {
                    map.remove(value);
                }
            }

            // 将服务端结果集中的时间字符串成long型
            if (stringToTimeValues.length > 0) {
                for (String value : stringToTimeValues) {
                    String val = (String) map.get(value);
                    if (StringUtils.isNotBlank(val)) {  // 值不为空
                        map.put(value, DateUtils.parseDate(map.get(value)).getTime());
                    } else {
                        map.put(value, 0);
                    }
                }
            }

            mapRes.put("status", Result.Status.OK.getCode());  // 状态码
            mapRes.put("content", map);
            return mapper.toJson(mapRes);
        } catch (Exception e) {
            return toString(Result.Status.INTERNAL_SERVER_ERROR);
        }


    }

    /**
     * 自定义提示信息
     *
     * @param status 枚举
     * @return
     */
    public static String toString(Result.Status status) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", status.getCode());
        map.put("message", status.getReason());
        return JsonMapper.getInstance().toJson(map);
    }

    /**
     * 自定义提示信息
     *
     * @param status  枚举
     * @param message 提示内容
     * @return
     */
    public static String toString(Result.Status status, String message) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", status.getCode());
        map.put("message", message);
        return JsonMapper.getInstance().toJson(map);
    }

    /**
     * 自定义提示信息
     *
     * @param status 枚举
     * @return map
     */
    public static Map<String, Object> toMap(Result.Status status) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", status.getCode());
        map.put("message", status.getReason());
        return map;
    }

    /**
     * 自定义提示信息
     *
     * @param status  枚举
     * @param message 提示内容
     * @return map
     */
    public static Map<String, Object> toMap(Result.Status status, String message) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", status.getCode());
        map.put("message", message);
        return map;
    }

    /**
     * 判定当前用户登录是否失效
     *
     * @return
     */
    public static boolean loginAuth() {

        User user = UserUtils.getUser();

        System.out.println("id--> " + user.getId());

        if (user == null || user.getId() == null) {  // 登录失效
            return true;
        } else {  // 已经登录
            return false;
        }
    }

    /**
     * 计算地球上两点间的直线距离
     *
     * @param lon_1
     * @param lat_1
     * @param lon_2
     * @param lat_2
     * @return
     */
    public static double getDistance(String lon_1, String lat_1, String lon_2, String lat_2) {
        int radius = 6378140;
        double lon1 = Double.parseDouble(lon_1);
        double lat1 = Double.parseDouble(lat_1);
        double lon2 = Double.parseDouble(lon_2);
        double lat2 = Double.parseDouble(lat_2);
        double d = (2 * Math.atan2(Math.sqrt(Math.sin((lat1 - lat2) * Math.PI / 180 / 2)
                        * Math.sin((lat1 - lat2) * Math.PI / 180 / 2) +
                        Math.cos(lat2 * Math.PI / 180) * Math.cos(lat1 * Math.PI / 180)
                                * Math.sin((lon1 - lon2) * Math.PI / 180 / 2)
                                * Math.sin((lon1 - lon2) * Math.PI / 180 / 2)),
                Math.sqrt(1 - Math.sin((lat1 - lat2) * Math.PI / 180 / 2)
                        * Math.sin((lat1 - lat2) * Math.PI / 180 / 2)
                        + Math.cos(lat2 * Math.PI / 180) * Math.cos(lat1 * Math.PI / 180)
                        * Math.sin((lon1 - lon2) * Math.PI / 180 / 2)
                        * Math.sin((lon1 - lon2) * Math.PI / 180 / 2)))) * radius;

        return d;
    }
}
