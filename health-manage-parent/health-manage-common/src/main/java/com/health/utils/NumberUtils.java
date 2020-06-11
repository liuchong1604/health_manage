package com.health.utils;

import java.util.Random;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/1/8 15:36
 */
public class NumberUtils {

    /**
     * 生成指定位数的随机数字
     * @param len
     * @return
     */
    public static String generateCode(int len){
        len = Math.min(len, 8);
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0,len);
//        if(len == 0){
//            throw new RuntimeException("验证码长度大于0");
//        }
//        StringBuilder builder = new StringBuilder();
//        for(int i=0;i<len;i++){
//            double doubleRandom = Math.random();
//            int integerRandom = (int)(doubleRandom*10);
//            builder.append(integerRandom);
//        }
//        return builder.toString();
    }
}
