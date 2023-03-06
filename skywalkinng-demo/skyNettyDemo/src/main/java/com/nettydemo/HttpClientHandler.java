package com.nettydemo;

import io.opentracing.ActiveSpan;
import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * TODO
 *
 * @Author zmgab@qq.com
 * @Date 2023/3/3 15:12
 */
public class HttpClientHandler {

    private static Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    public static ResponseEntity<String> sendHttp(){

        String url = "http://localhost:9527/clientRequest";
        HttpEntity<Object> request = new HttpEntity<>(null,null);
        String traceId = TraceContext.traceId();

        logger.info(traceId + "  !!@@@@#@!@!#!#");
        HttpHeaders headers = request.getHeaders();

        ActiveSpan activeSpan = new SkywalkingTracer().activeSpan();
        logger.info(activeSpan + "  -----------------------------------");

        RestTemplate restTemplate = new RestTemplate();
        return new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
    }
}
