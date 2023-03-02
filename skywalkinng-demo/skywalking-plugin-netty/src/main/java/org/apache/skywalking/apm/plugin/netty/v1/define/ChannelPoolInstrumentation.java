/**
 * 
 */
package org.apache.skywalking.apm.plugin.netty.v1.define;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;
import static org.apache.skywalking.apm.agent.core.plugin.match.MultiClassNameMatch.byMultiClassMatch;

import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */

public class ChannelPoolInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {
    private static final String[] ENHANCE_CLASSES = new String[] {
            "io.netty.channel.pool.SimpleChannelPool",
            "io.netty.channel.pool.FixedChannelPool" };

    private static final String ACQUIRE_INTERCEPT_CLASS = "org.apache.skywalking.apm.plugin.netty.v1.ChannelPoolAcquireInterceptor";

    @Override
    protected ClassMatch enhanceClass() {
        return byMultiClassMatch(ENHANCE_CLASSES);
    }

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return null;
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[] {
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named("acquire").and(takesArguments(1));
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return ACQUIRE_INTERCEPT_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                } };
    }
}