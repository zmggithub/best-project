/**
 * 
 */
package org.apache.skywalking.apm.plugin.zmg.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;
import static org.apache.skywalking.apm.agent.core.plugin.match.NameMatch.byName;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */

public class HttpObjectEncoderInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {
    private static final String ENHANCE_CLASS = "io.netty.handler.codec.http.HttpObjectEncoder";
    private static final String ENCODE_INTERCEPT_CLASS = "org.apache.skywalking.apm.plugin.netty.http.v4.EncodeInterceptor";

    @Override
    protected ClassMatch enhanceClass() {
        return byName(ENHANCE_CLASS);
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
                        return named("encode").and(takesArguments(3));
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return ENCODE_INTERCEPT_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                } };
    }
}
