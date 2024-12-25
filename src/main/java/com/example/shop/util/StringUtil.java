package com.example.shop.util;

public class StringUtil {
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }
}
