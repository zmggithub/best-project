package org.apache.skywalking.apm.plugin.channelbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;

/**
 * 拦截器，用于拦截方法的调用。目标方法将在 ClassEnhancePluginDefine's 子类中定义，最有可能在 ClassInstanceMethodsEnhancePlugindefine 中定义
 *
 *@Author zmgab@qq.com
 *@Date 2023/3/1 15:22
*/
public class ChannelReadInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, MethodInterceptResult methodInterceptResult) throws Throwable {
        System.out.println("ChannelBoundWriteInterceptor beforeMethod!!!");
    }

    @Override
    public Object afterMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Object o) throws Throwable {
        System.out.println("ChannelBoundWriteInterceptor afterMethod@@@");
        ChannelHandlerContext context = (ChannelHandlerContext) objects[0];
        List<Object> out = (List<Object>) objects[2];
        int size = out.size();
        for (int i = size - 1; i >= 0; i--) {
            Object obj = out.get(i);
            if (obj instanceof HttpRequest) {
                TracingHelper.onServerRequest((HttpRequest) obj, context);
                break;
            } else if (obj instanceof HttpResponse) {
                TracingHelper.onClientResponse((HttpResponse) obj, context);
                break;
            }
        }
        return o;
    }

    @Override
    public void handleMethodException(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Throwable throwable) {

    }
}
