package com.consumer.sentineldemo;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.consumer.common.utils.R;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/9 15:18
 */
public class ExceptionUtil {

    /**
     * 注意： 必须为 static 函数
     */
    public static R fallback(Integer id, Throwable e){
        return R.error(-1,"===被异常降级啦===");
    }

    public static R handleException(Integer id, BlockException e){
        return R.error(-2,"===被限流啦===");
    }

}
