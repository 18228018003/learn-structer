package com.mengtu.netty.codec.config;

import com.mengtu.netty.codec.protocol.Serializer;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    static Properties properties;

    static {
        try (InputStream in = Config.class.getResourceAsStream("/application.properties")){
            properties = new Properties();
            properties.load(in);
        }catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static int getServerPort(){
        String port = properties.getProperty("server.port");
        if (port == null){
            return 8100;
        }else {
            return Integer.parseInt(port);
        }
    }

    public static Serializer.Algorithm getSerializerAlgorithm(){
        String serializerType = properties.getProperty("serializer.algorithm");
        if (serializerType == null){
            return Serializer.Algorithm.Json;
        }else {
            return Serializer.Algorithm.valueOf(serializerType);
        }
    }
}
