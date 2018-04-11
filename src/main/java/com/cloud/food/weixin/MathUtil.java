package com.cloud.food.weixin;

public class MathUtil {

    private static Double RANGE = 0.01;

    public static boolean compare(Double d1, Double d2) {
        double range = Math.abs(d1 - d2);
        if (range < RANGE) {
            return true;
        }else{
            return false;
        }

    }


}
