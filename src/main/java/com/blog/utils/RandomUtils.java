package com.blog.utils;

import java.util.Random;

/**
 * Created by t on 2017/4/17.
 * 目标：编写一个单例类，生成不重复的随机数
 * 情况：未完成
 * 原因：需要自己实现随机数的生成
 */
public class RandomUtils {
//    private RandomUtils(){
//
//    }
    private static class RandomHolder{
        private static RandomUtils instance=new RandomUtils();
    }
    private RandomUtils getInstance(){
        return RandomHolder.instance;
    }
    public Random getRandom(){
        return new Random(10);
    }
}
