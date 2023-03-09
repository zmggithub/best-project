package org.apache.skywalking.apm.plugin.zmg;

import io.netty.util.AttributeKey;
import org.apache.skywalking.apm.agent.core.context.AbstractTracerContext;
import org.apache.skywalking.apm.network.trace.component.Component;
import org.apache.skywalking.apm.network.trace.component.ComponentsDefine;
import org.apache.skywalking.apm.network.trace.component.OfficialComponent;

/**
 * TODO
 *
 *@Author zmgab@qq.com
 *@Date 2023/3/1 15:22
 */
public class Constants {

    public static final AttributeKey<AbstractTracerContext> KEY_CONTEXT = AttributeKey.valueOf("SW_CONTEXT");
    public static final Component COMPONENT_NETTY_KAYAK = new OfficialComponent(555,"k-gateway-netty");
    public static final Component COMPONENT_NETTY_HTTP_SERVER = ComponentsDefine.TOMCAT;
    public static final Component COMPONENT_NETTY_HTTP_CLIENT = ComponentsDefine.HTTPCLIENT;

}
