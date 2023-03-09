package org.apache.skywalking.apm.plugin.zmg;

import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;

import java.lang.reflect.Method;

/**
 * 拦截器，用于拦截方法的调用。目标方法将在 ClassEnhancePluginDefine's 子类中定义，最有可能在 ClassInstanceMethodsEnhancePlugindefine 中定义
 *
 *@Author zmgab@qq.com
 *@Date 2023/3/1 15:22
*/
public class NettyChannelHandlerInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, MethodInterceptResult methodInterceptResult) throws Throwable {
    }

    @Override
    public Object afterMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Object o) {
        return o;
    }

    @Override
    public void handleMethodException(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Throwable throwable) {
        ContextManager.activeSpan().log(throwable);
    }
}
