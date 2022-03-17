package com.lqh.fastlibrary.utils.gson.factory.element;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.lqh.fastlibrary.utils.gson.factory.GsonFactory;
import com.lqh.fastlibrary.utils.gson.factory.JsonCallback;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * <pre>
 *     description:Array 解析适配器
 *     date:2021/8/17 0017
 *     update:2021/8/17 0017  15:49
 *     version:1.0
 *     @author LQH
 * </pre>
 */

public class CollectionTypeAdapter<E> extends TypeAdapter<Collection<E>> {

    private final TypeAdapter<E> mElementTypeAdapter;
    private final ObjectConstructor<? extends Collection<E>> mObjectConstructor;

    private TypeToken<?> mTypeToken;
    private String mFieldName;

    public CollectionTypeAdapter(Gson gson, Type elementType, TypeAdapter<E> elementTypeAdapter, ObjectConstructor<? extends Collection<E>> constructor) {
        mElementTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(gson, elementTypeAdapter, elementType);
        mObjectConstructor = constructor;
    }

    public void setReflectiveType(TypeToken<?> typeToken, String fieldName) {
        mTypeToken = typeToken;
        mFieldName = fieldName;
    }

    @Override
    public Collection<E> read(JsonReader in) throws IOException {
        JsonToken jsonToken = in.peek();

        if (jsonToken == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        if (jsonToken != JsonToken.BEGIN_ARRAY) {
            in.skipValue();
            JsonCallback callback = GsonFactory.getJsonCallback();
            if (callback != null) {
                callback.onTypeException(mTypeToken, mFieldName, jsonToken);
            }
            return null;
        }

        Collection<E> collection = mObjectConstructor.construct();
        in.beginArray();
        while (in.hasNext()) {
            E instance = mElementTypeAdapter.read(in);
            collection.add(instance);
        }
        in.endArray();
        return collection;
    }

    @Override
    public void write(JsonWriter out, Collection<E> collection) throws IOException {
        if (collection == null) {
            out.nullValue();
            return;
        }

        out.beginArray();
        for (E element : collection) {
            mElementTypeAdapter.write(out, element);
        }
        out.endArray();
    }
}