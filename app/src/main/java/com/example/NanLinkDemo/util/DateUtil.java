package com.example.NanLinkDemo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Long getTime(){
        return System.currentTimeMillis();
    }

    public static String getDate(Long time){
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date(time));
    }

}
