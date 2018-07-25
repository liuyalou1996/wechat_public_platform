package com.iboxpay.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Copyright (C) 2011-2015 ShenZhen iBOXCHAIN Information Technology Co.,Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary
 * information of iBoxChain Company of China.
 * ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement
 * you entered into with iBoxchain inc.
 * <p>
 * @author: liuyalou
 * @date: 2018年7月25日
 * <p>
 * Description:
 * 对象到json字符串转化的工具类
 */
public class JsonUtils {

  /**
   * 将对象转换为json字符串
   * @param obj 转换的对象
   * @return
   */
  public static String toJsonString(Object obj) {
    return toJsonString(obj, false);
  }

  /**
   * 对象转换为json字符串，可允许转换空值
   * @param obj 转换的对象
   * @param isNullValueAllowed 是允许空值
   * @return
   */
  public static String toJsonString(Object obj, boolean isNullValueAllowed) {
    if (obj == null) {
      return null;
    }

    if (!isNullValueAllowed) {
      return JSON.toJSONString(obj);
    }

    return JSON.toJSONString(obj, SerializerFeature.WRITE_MAP_NULL_FEATURES);
  }

  /**
   * 将json字符串转换为javaBean
   * @param jsonStr json字符串
   * @param clazz 运行时对象
   * @return
   */
  public static <T> T toJavaBean(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseObject(jsonStr, clazz);
  }

  /**
   * 字符串转换为list
   * @param jsonStr json字符串
   * @param clazz 运行时对象
   * @return
   */
  public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseArray(jsonStr, clazz);
  }

  /**
   * 将json字符串转换为map
   * @param jsonStr json字符串
   * @return
   */
  public static Map<String, Object> toMap(String jsonStr) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }

    return JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {});
  }

  /**
   * 将javaBean转换为map
   * @param obj 转换的对象
   * @return
   */
  public static Map<String, Object> javaBeanToMap(Object obj) {
    if (obj == null) {
      return null;
    }

    return toMap(toJsonString(obj));
  }

  /**
   * 将map转换为javaBean
   * @param map map实例
   * @param clazz 运行时对象
   * @return
   */
  public static <T> T mapToJavaBean(Map<String, ? extends Object> map, Class<T> clazz) {
    if (CollectionUtils.isEmpty(map)) {
      return null;
    }

    String jsonStr = JSON.toJSONString(map);
    return JSON.parseObject(jsonStr, clazz);
  }

  public static void main(String[] args) {
  }
}
