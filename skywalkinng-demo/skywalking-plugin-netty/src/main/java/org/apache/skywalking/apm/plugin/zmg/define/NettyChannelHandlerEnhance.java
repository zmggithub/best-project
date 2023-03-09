package org.apache.skywalking.apm.plugin.zmg.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.apache.skywalking.apm.agent.core.plugin.match.NameMatch.byName;

/**
 * 定义增强类
 *
 * @Author zmgab@qq.com
 * @Date 2023/3/1 15:16
 */
public class NettyChannelHandlerEnhance extends ClassInstanceMethodsEnhancePluginDefine {

    private static final String ENHANCE_CLASS = "io.netty.channel.AbstractChannel";
    private static final String CONSTRUCTOR_INTERCEPT_CLASS = "org.apache.skywalking.apm.plugin.zmg.NettyChannelConstructorInterceptor";
    private static final String CHANNEL_HANDLER_INTERCEPT_CLASS = "org.apache.skywalking.apm.plugin.zmg.NettyChannelHandlerInterceptor";

    @Override
    protected ClassMatch enhanceClass() {
        return byName(ENHANCE_CLASS);
    }

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[] {
                new ConstructorInterceptPoint() {
                    @Override
                    public String getConstructorInterceptor() {
                        return CONSTRUCTOR_INTERCEPT_CLASS;
                    }

                    @Override
                    public ElementMatcher<MethodDescription> getConstructorMatcher() {
                        return any();
                    }
                } };
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[] {
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named("connect").or(named("close"));
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return CHANNEL_HANDLER_INTERCEPT_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }
}
