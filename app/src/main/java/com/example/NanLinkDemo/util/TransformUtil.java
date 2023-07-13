package com.example.NanLinkDemo.util;

public class TransformUtil {

    //byte转二进制
    public static String byte2bit(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    //二进制转十进制
    public static int bit2int(String two) {
        return Integer.parseInt(two, 2);
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
