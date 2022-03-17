package com.lqh.fastlibrary.utils.gson.factory.element;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * <pre>
 *     description:反射字段捆绑类
 *     date:2021/8/17 0017
 *     update:2021/8/17 0017  15:50
 *     version:1.0
 *     @author LQH
 * </pre>
 */

public abstract class ReflectiveFieldBound {

    /**
     * 字段名称
     */
    private final String mFieldName;
    /**
     * 序列化标记
     */
    private final boolean mSerialized;
    /**
     * 反序列化标记
     */
    private final boolean mDeserialized;

    public ReflectiveFieldBound(String name, boolean serialized, boolean deserialized) {
        mFieldName = name;
        mSerialized = serialized;
        mDeserialized = deserialized;
    }

    public String getFieldName() {
        return mFieldName;
    }

    public boolean isDeserialized() {
        return mDeserialized;
    }

    public boolean isSerialized() {
        return mSerialized;
    }

    public abstract void write(JsonWriter writer, Object value) throws IOException, IllegalAccessException;

    public abstract void read(JsonReader reader, Object value) throws IOException, IllegalAccessException;

    public abstract boolean writeField(Object value) throws IOException, IllegalAccessException;
}