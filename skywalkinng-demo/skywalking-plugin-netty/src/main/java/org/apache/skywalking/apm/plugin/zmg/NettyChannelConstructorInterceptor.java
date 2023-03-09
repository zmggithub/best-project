package org.apache.skywalking.apm.plugin.zmg;

import io.netty.channel.AbstractChannel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ServerChannel;
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
import static org.apache.skywalking.apm.plugin.zmg.Constants.KEY_CONTEXT;
import static org.apache.skywalking.apm.plugin.zmg.TracingHelper.getTracingContext;

/**
 * 当前类是主要是对目标实例的构造方法进行增强
 *
 * @Author zmgab@qq.com
 * @Date 2023/3/1 15:20
 */
public class NettyChannelConstructorInterceptor implements InstanceConstructorInterceptor {

    @Override
    public void onConstruct(EnhancedInstance enhancedInstance, Object[] objects) throws Throwable {
        if (enhancedInstance instanceof ServerChannel) {
            return;
        }
        AbstractChannel channel = (AbstractChannel) enhancedInstance;
        channel.pipeline().addLast(new GatewayInterceptorHandler());
    }

    private static class GatewayInterceptorHandler extends ChannelDuplexHandler {

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            ContextCarrier contextCarrier = new ContextCarrier();
            CarrierItem next = contextCarrier.items();
            while (next.hasNext()) {
                next = next.next();
                next.setHeadValue(next.getHeadValue());
            }
            String socketAddress = null == ctx.channel().remoteAddress() ?"/0.0.0.0:0000": ctx.channel().remoteAddress().toString();
            AbstractSpan span = ContextManager.createEntrySpan(socketAddress.substring(1), contextCarrier);
            Tags.URL.set(span, socketAddress);
            Tags.HTTP.METHOD.set(span,ctx.name());
            span.setComponent(Constants.COMPONENT_NETTY_KAYAK);
            SpanLayer.asHttp(span);
            ctx.channel().attr(Constants.KEY_CONTEXT).set(getTracingContext());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
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

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            AbstractTracerContext tracingContext = ctx.channel().attr(KEY_CONTEXT).get();
            if (tracingContext != null) {
                AbstractSpan span = tracingContext.activeSpan();
                tracingContext.stopSpan(span);
            }
            super.close(ctx,promise);
        }
//
//        @Override
//        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
//            super.bind(ctx, localAddress, promise);
//        }
//
//        @Override
//        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
//            super.disconnect(ctx, promise);
//        }
//
//        @Override
//        public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
//            super.deregister(ctx, promise);
//        }
//
//        @Override
//        public void read(ChannelHandlerContext ctx) throws Exception {
//            super.read(ctx);
//        }
//
//        @Override
//        public void flush(ChannelHandlerContext ctx) throws Exception {
//            super.flush(ctx);
//        }
//
//        @Override
//        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//            super.channelRegistered(ctx);
//        }
//
//        @Override
//        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//            super.channelUnregistered(ctx);
//        }
//
//        @Override
//        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            super.channelActive(ctx);
//        }
//
//        @Override
//        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//            super.channelInactive(ctx);
//        }
//
//        @Override
//        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//            super.channelReadComplete(ctx);
//        }
//
//        @Override
//        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//            super.userEventTriggered(ctx, evt);
//        }
//
//        @Override
//        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//            super.channelWritabilityChanged(ctx);
//        }
//
//        @Override
//        protected void ensureNotSharable() {
//            super.ensureNotSharable();
//        }
//
//        @Override
//        public boolean isSharable() {
//            return super.isSharable();
//        }
//
//        @Override
//        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//            super.handlerRemoved(ctx);
//        }
    }

}
