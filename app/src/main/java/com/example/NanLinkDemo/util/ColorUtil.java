package com.example.NanLinkDemo.util;

import android.graphics.Color;

import androidx.core.graphics.ColorUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ColorUtil {


    public static int HsiSatToColor(int Hsi, int Sat){
        float[] hsv = new float[3];
        hsv[0] = Hsi;
        hsv[1] = Sat / 100f;
        hsv[2] = 1;
        return Color.HSVToColor(hsv);
    }

    public static int XYToColor(int X, int Y){
        return ColorUtils.XYZToColor(X / 10000.0f, Y / 10000.0f, 100 - (X + Y) / 10000.0f);
    }

    public static int cctGmToColor(int cct, int gm){
        int r,g,b;
        double tmp;

        if (cct < 1000){
            cct = 1000;
        }
        if (cct > 40000){
            cct = 40000;
        }
        cct = cct / 100;

        if (cct <= 66){
            r = 255;
        }else {
            tmp = cct - 60;
            tmp = 329.698727446 * (Math.pow(tmp, -0.1332047592));
            r = (int)(tmp + 0.5);
            if (r < 0){
                r = 0;
            }
            if (r > 255){
                r = 255;
            }
        }

        if (cct <= 66){
            tmp = cct;
            tmp = 99.4708025861 * Math.log(tmp) - 161.1195681661;
            g = (int)(tmp + 0.5);
            if (g < 0){
                g = 0;
            }
            if (g > 255){
                g = 255;
            }
        }else {
            tmp = cct - 60;
            tmp = 288.1221695283 * (Math.pow(tmp, -0.0755148492));
            g = (int)(tmp + 0.5);
            if (g < 0){
                g = 0;
            }
            if (g > 255){
                g = 255;
            }
        }

        if (cct >= 66){
            b = 255;
        } else if (cct <= 19) {
            b = 0;
        }else {
            tmp = cct - 10;
            tmp = 138.5177312231 * Math.log(tmp) - 305.0447927307;
            b = (int)(tmp + 0.5);
            if (b < 0){
                b = 0;
            }
            if (b > 255){
                b = 255;
            }
        }
        return Color.rgb(r, g, b);
    }
}
