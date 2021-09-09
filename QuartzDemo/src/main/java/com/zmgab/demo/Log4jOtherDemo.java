package com.zmgab.demo;

import org.apache.log4j.Logger;
/**
 * log4j.addivity.Log4jOtherDemo=true
 * log4j.additivity. mylogger1 = false
 * 有2个参数么
 * 不同输出打印到不同日志中
 */
public class Log4jOtherDemo {

    private static Logger logger = Logger.getLogger("Log4jOtherDemo");

    public static void main(String[] args) {
        logger.debug("记录debug级别的信息");
        logger.info("记录info级别的信息");
        logger.error("记录error级别的信息");
    }
}
