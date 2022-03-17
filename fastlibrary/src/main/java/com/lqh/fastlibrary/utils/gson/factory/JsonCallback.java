package com.lqh.fastlibrary.utils.gson.factory;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;


/**
 * <pre>
 *     description:Json 解析异常监听器
 *     date:2021/8/17 0017
 *     update:2021/8/17 0017  15:51
 *     version:1.0
 *     @author LQH
 * </pre>
 */

public interface JsonCallback {

    /**
     * 类型解析异常
     *
     * @param typeToken 类型 Token
     * @param fieldName 字段名称
     * @param jsonToken 后台给定的类型
     */
    void onTypeException(TypeToken<?> typeToken, String fieldName, JsonToken jsonToken);
}