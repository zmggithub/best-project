/**
 * 
 */
package org.apache.skywalking.apm.plugin.netty.http.v4;

import io.netty.handler.codec.http.HttpRequest;
import java.net.SocketAddress;

import org.apache.skywalking.apm.agent.core.context.AbstractTracerContext;
import org.apache.skywalking.apm.agent.core.context.CarrierItem;
import org.apache.skywalking.apm.agent.core.context.ContextCarrier;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceConstructorInterceptor;

import io.netty.channel.AbstractChannel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ServerChannel;
import static org.apache.skywalking.apm.plugin.netty.http.v4.Constants.COMPONENT_NETTY_HTTP_SERVER;
import static org.apache.skywalking.apm.plugin.netty.http.v4.Constants.KEY_CONTEXT;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */

public class ChannelConstructorInterceptor implements InstanceConstructorInterceptor {

    @Override
    public void onConstruct(EnhancedInstance objInst, Object[] allArguments) {
        if (objInst instanceof ServerChannel) {
            return;
        }

        System.out.println("ChannelConstructorInterceptor");
        AbstractChannel channel = (AbstractChannel) objInst;
        channel.pipeline().addLast(new ErrorHandler()); /* 是不是可能有顺序问题 */
    }

    private static class ErrorHandler extends ChannelDuplexHandler {
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            System.out.println("sdfsdfsdfd!!!!!!!!!!!!!!!!!!");
            ContextCarrier contextCarrier = new ContextCarrier();
            CarrierItem next = contextCarrier.items();
            while (next.hasNext()) {
                next = next.next();
                next.setHeadValue(next.getHeadValue());
            }
            System.out.println(next);
            AbstractSpan span = ContextManager.createEntrySpan("localhost:8995", contextCarrier);
            Tags.URL.set(span, "localhost:8995");
            Tags.HTTP.METHOD.set(span,"request.method().name()");
            span.setComponent(COMPONENT_NETTY_HTTP_SERVER);
            SpanLayer.asHttp(span);
            System.out.println(span);

            ctx.channel().attr(KEY_CONTEXT).set(null);
            System.out.println(ctx);

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
}