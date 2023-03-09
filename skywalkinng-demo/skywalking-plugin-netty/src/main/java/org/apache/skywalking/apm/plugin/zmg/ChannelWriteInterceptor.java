package org.apache.skywalking.apm.plugin.zmg;

import io.netty.channel.Channel;
import java.lang.reflect.Method;
import org.apache.skywalking.apm.agent.core.context.AbstractTracerContext;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import static org.apache.skywalking.apm.plugin.zmg.Constants.KEY_CONTEXT;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */

public class ChannelWriteInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {
        Channel channel = (Channel) objInst;
        if (channel.attr(KEY_CONTEXT).get() == null && ContextManager.isActive()) {
            AbstractTracerContext tracingContext = TracingHelper.getTracingContext();
            channel.attr(KEY_CONTEXT).set(tracingContext);
        }
        return;
    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {
        return;
    }
}