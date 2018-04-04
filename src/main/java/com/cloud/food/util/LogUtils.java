package com.cloud.food.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    public static Logger getLogger(Class t){
        return LoggerFactory.getLogger(t.getClass());
    }
}
