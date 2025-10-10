package com.jaya.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class JsonTemplates {
    public static String load(String relativePath) {
        try {
            String normalized = relativePath.replace("\\","/");
            String full = "src/test/resources/" + normalized;
            Path p = Path.of(full);
            return Files.readString(p, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load template: " + relativePath, e);
        }
    }
    public static String apply(String template, Map<String,String> values) {
        String result = template;
        for (var entry : values.entrySet()) {
            result = result.replace("{{"+entry.getKey()+"}}", escape(entry.getValue()));
        }
        return result;
    }
    private static String escape(String v) {
        if (v == null) return "";
        return v.replace("\\","\\\\").replace("\"","\\\"");
    }
}
