package com.lqh.fastlibrary.utils.gson.factory.data;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;


 /**
  * <pre>
  *     description:BigDecimal 类型解析适配器，参考：{@link com.google.gson.internal.bind.TypeAdapters#BIG_DECIMAL}
  *     date:2021/8/17 0017
  *     update:2021/8/17 0017  15:50
  *     version:1.0
  *     @author LQH
  * </pre>
  */

public class BigDecimalTypeAdapter extends TypeAdapter<BigDecimal> {

    @Override
    public BigDecimal read(JsonReader in) throws IOException {
        switch (in.peek()) {
            case NUMBER:
            case STRING:
                String result = in.nextString();
                if (result == null || "".equals(result)) {
                    return new BigDecimal(0);
                }
                return new BigDecimal(result);
            case NULL:
                in.nextNull();
                return null;
            default:
                in.skipValue();
                return null;
        }
    }

    @Override
    public void write(JsonWriter out, BigDecimal value) throws IOException {
        out.value(value);
    }
}