package com.hafiz.expense.buddy.utils;

import android.graphics.Color;

public class ColorUtils {
    public static String generateLightColor(String darkHex) {

        int color = Color.parseColor(darkHex);

        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        // move color toward white
        red = (red + 255) / 2;
        green = (green + 255) / 2;
        blue = (blue + 255) / 2;

        return String.format("#%02X%02X%02X", red, green, blue);
    }
}
