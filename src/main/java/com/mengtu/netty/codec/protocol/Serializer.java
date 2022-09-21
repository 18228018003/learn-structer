package com.mengtu.netty.codec.protocol;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public interface Serializer {
    //反序列化方法
    <T> T deserializer(Class<T> clazz,byte[] bytes) throws IOException, ClassNotFoundException;

    //序列化方法
    <T> byte[] serializer(T object) throws IOException;

    enum Algorithm implements Serializer{

        Java{
            @Override
            public <T> T deserializer(Class<T> clazz, byte[] bytes) {
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    return (T)ois.readObject();
                }catch (Exception e){
                    throw new RuntimeException("反序列化失败",e);
                }
            }

            //序列化
            @Override
            public <T> byte[] serializer(T object){
                try{
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                }catch (IOException e){
                    throw new RuntimeException("序列化失败");
                }
            }
        },

        Json{
            @Override
            public <T> T deserializer(Class<T> clazz, byte[] bytes) {
                String json = new String(bytes, StandardCharsets.UTF_8);
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCode()).create();
                return gson.fromJson(json,clazz);
            }

            @Override
            public <T> byte[] serializer(T object){
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCode()).create();
                String json = gson.toJson(object);
                return json.getBytes(StandardCharsets.UTF_8);
            }
        }
    }
    class ClassCode implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

        @Override
        public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String str = json.getAsString();
            try {
                return Class.forName(str);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        @Override
        public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {

            return new JsonPrimitive(src.getName());
        }
    }
}
