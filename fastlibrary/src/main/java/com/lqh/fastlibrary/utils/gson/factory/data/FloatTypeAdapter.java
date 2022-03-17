package com.lqh.fastlibrary.utils.gson.factory.data;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;


/**
 * <pre>
 *     description:float / Float 类型解析适配器
 *     date:2021/8/17 0017
 *     update:2021/8/17 0017  15:49
 *     version:1.0
 *     @author LQH
 * </pre>
 */

public class FloatTypeAdapter extends TypeAdapter<Float> {

    @Override
    public Float read(JsonReader in) throws IOException {
        switch (in.peek()) {
            case NUMBER:
                return (float) in.nextDouble();
            case STRING:
                String result = in.nextString();
                if (result == null || "".equals(result)) {
                    return 0F;
                }
                return Float.parseFloat(result);
            case NULL:
                in.nextNull();
                return null;
            default:
                in.skipValue();
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void write(JsonWriter out, Float value) throws IOException {
        out.value(value);
    }
}