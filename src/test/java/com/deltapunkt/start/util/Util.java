package com.deltapunkt.start.util;

import alexh.Fluent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import static alexh.Unchecker.uncheckedGet;

public class Util {
    private static ObjectMapper objectMapper = new ObjectMapper()
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

    public static String toJson(Object object) {
        return uncheckedGet(() -> objectMapper.writer().writeValueAsString(object));
    }

    public static <T> T fromJson(String content, Class<T> valueType) {
        return uncheckedGet(() -> objectMapper.readValue(content, valueType));
    }

    public static Fluent.Map<String, Object> fluentMap() {
        return new Fluent.HashMap<>();
    }
}
