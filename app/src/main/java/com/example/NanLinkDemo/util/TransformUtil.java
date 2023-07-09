package com.example.NanLinkDemo.util;

public class TransformUtil {

    public static String byte2string() {
        return "";
    }

    public static String updateCH(int CH) {
        if (CH <= 0) {
            return "";
        } else if (CH < 10) {
            return "00" + CH;
        } else if (CH < 100) {
            return "0" + CH;
        }
        return String.valueOf(CH);
    }
}
