package org.apache.skywalking.apm.plugin.channelbound;

import io.netty.channel.AbstractChannel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.lang.reflect.Method;
import java.net.SocketAddress;

import io.netty.channel.ServerChannel;
import org.apache.skywalking.apm.agent.core.context.AbstractTracerContext;
import org.apache.skywalking.apm.agent.core.context.CarrierItem;
import org.apache.skywalking.apm.agent.core.context.ContextCarrier;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.TracingContext;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceConstructorInterceptor;

import static org.apache.skywalking.apm.plugin.channelbound.Constants.KEY_CONTEXT;

/**
 * 当前类是主要是对目标实例的构造方法进行增强
 *
 * @Author zmgab@qq.com
 * @Date 2023/3/1 15:20
 */
public class ChannelBoundConstructorInterceptor implements InstanceConstructorInterceptor {


    @Override
    public void onConstruct(EnhancedInstance enhancedInstance, Object[] objects) throws Throwable {
        if (enhancedInstance instanceof ServerChannel) {
            return;
        }

        System.out.println("ChannelConstructorInterceptor");
        AbstractChannel channel = (AbstractChannel) enhancedInstance;
        channel.pipeline().addLast(new ErrorHandler()); /* 是不是可能有顺序问题 */
        // channel.pipeline().addLast(new boundHandler()); /* 是不是可能有顺序问题 */

    }

    private static class ErrorHandler extends ChannelDuplexHandler {

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            System.out.println("handlerAdded!!!!!!!!!!!!handlerAdded!!!!!!");
            ContextCarrier contextCarrier = new ContextCarrier();
            CarrierItem next = contextCarrier.items();
            while (next.hasNext()) {
                next = next.next();
                next.setHeadValue(next.getHeadValue());
            }
            System.out.println(next);
            AbstractSpan span = ContextManager.createEntrySpan("localhost:9527", contextCarrier);
            Tags.URL.set(span, "localhost:9527");
            Tags.HTTP.METHOD.set(span,"request.method().name()");
            span.setComponent(Constants.COMPONENT_NETTY_HTTP_SERVER);
            SpanLayer.asHttp(span);
            System.out.println(span);

            ctx.channel().attr(Constants.KEY_CONTEXT).set(null);
            System.out.println(ctx);

        }


        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("channelRead!!!!!channelRead!!!!!!!!channelRead!!!!!");


            super.channelRead(ctx, msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            TracingHelper.onException(cause, ctx);
            ctx.fireExceptionCaught(cause);
        }

        @Override
        public void connect(final ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            ctx.connect(remoteAddress, localAddress, promise.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (future.cause() != null) {
                        TracingHelper.onException(future.cause(), ctx);
                    }
                }
            }));
        }

        @Override
        public void write(final ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
            ctx.write(msg, promise.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (future.cause() != null) {
                        TracingHelper.onException(future.cause(), ctx);
                    }
                }
            }));
        }
    }

    protected String buildMethodName(Method method) {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();

        return className + "." + methodName;
    }
}
