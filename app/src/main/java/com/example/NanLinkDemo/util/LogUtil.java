package com.example.NanLinkDemo.util;

import android.util.Log;



public class LogUtil {

    private static String className;
    private static String methodName;
    private static int lineName;
    private static boolean DEBUG = true;

    public static void v(String message){
        if (DEBUG){
            createLogName(new Throwable().getStackTrace());
            Log.v(""+className+""+methodName+""+lineName+"     ", message);
        }
    }

    public static void d(String message){
        if (DEBUG){
            createLogName(new Throwable().getStackTrace());
            Log.d(""+className+""+methodName+""+lineName+"     ", message);
        }
    }

    public static void i(String message){
        if (DEBUG){
            createLogName(new Throwable().getStackTrace());
            Log.i(""+className+""+methodName+""+lineName+"     ", message);
        }
    }

    public static void w(String message){
        if (DEBUG){
            createLogName(new Throwable().getStackTrace());
            Log.w(""+className+""+methodName+""+lineName+"     ", message);
        }
    }

    public static void e(String message){
        if (DEBUG){
            createLogName(new Throwable().getStackTrace());
            Log.e(""+className+""+methodName+""+lineName+"     ", message);
        }
    }



    private static void createLogName(StackTraceElement[] stackTrace){
        className = stackTrace[1].getClassName();
        methodName = stackTrace[1].getMethodName();
        lineName = stackTrace[1].getLineNumber();
    }
}
