package com.example.NanLinkDemo.JsonTypeAdapter;

import com.example.NanLinkDemo.bean.DeviceDataMessage;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class DataDefault0Adapter implements JsonSerializer<DeviceDataMessage.Data>, JsonDeserializer<DeviceDataMessage.Data> {
    @Override
    public JsonObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json.getAsJsonObject().equals("{}") || json.getAsString().equals("null")) {//定义为String类型,如果后台返回null,则返回""
                return new DeviceDataMessage.Data();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.getAsJsonObject();
    }

    @Override
    public JsonElement serialize(DeviceDataMessage.Data src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(String.valueOf(src));
    }
}
