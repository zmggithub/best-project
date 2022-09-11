package com.consumer.sentineldemo;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import com.consumer.common.utils.R;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2022/9/9 15:19
 */
@Slf4j
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {

        log.info("BlockExceptionHandler BlockException================"+e.getRule());
        R r = null;

        if (e instanceof FlowException) { // 接口限流
            r = R.error(100,"接口限流了");
        } else if (e instanceof DegradeException) { // 服务降级
            r = R.error(101,"服务降级了");
        } else if (e instanceof ParamFlowException) { // 热点参数限流
            r = R.error(102,"热点参数限流了");
        } else if (e instanceof SystemBlockException) { // 触发系统保护规则
            r = R.error(103,"触发系统保护规则了");
        } else if (e instanceof AuthorityException) { // 授权规则不通过
            r = R.error(104,"授权规则不通过");
        }

        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), r);
    }
}
