package org.apache.skywalking.apm.plugin.channelbound.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.named;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;
import static org.apache.skywalking.apm.agent.core.plugin.match.NameMatch.byName;

/**
 * 定义增强类
 *
 * @Author zmgab@qq.com
 * @Date 2023/3/1 15:16
 */
public class ChannelInboundHandlerEnhance extends ClassInstanceMethodsEnhancePluginDefine {

    private static final String ENHANCE_CLASS = "io.netty.channel.AbstractChannel";
    // private static final String ENHANCE_CLASS = "io.netty.channel.AbstractChannel";

    private static final String CHANNEL_WRITE_INTERCEPT_CLASS = "org.apache.skywalking.apm.plugin.channelbound.ChannelBoundWriteInterceptor";
    private static final String CONSTRUCTOR_INTERCEPT_CLASS = "org.apache.skywalking.apm.plugin.channelbound.ChannelBoundConstructorInterceptor";

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
                        return named("write").or(named("writeAndFlush"));
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return CHANNEL_WRITE_INTERCEPT_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                } };
    }
}
