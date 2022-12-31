package com.consumer.sentineldemo;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.util.StringUtil;
import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/13 11:29
 */
public class MyRequestOriginParser implements RequestOriginParser {

    /**
     * 通过request获取来源标识，交给授权规则进行匹配
     * @param httpServletRequest
     * @return
     */
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {

        // 标识字段名称可以自定义   serviceName = order
        String origin = httpServletRequest.getParameter("serviceName");
        if (StringUtil.isBlank(origin)){
            System.out.println("----------------: " + origin);
            if ("provider".equals(origin)) {
                throw new IllegalArgumentException("serviceName参数未指定");
            }
        }

        return origin;
    }
}
