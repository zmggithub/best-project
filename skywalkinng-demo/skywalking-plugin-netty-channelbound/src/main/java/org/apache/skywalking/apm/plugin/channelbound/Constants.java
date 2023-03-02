/**
 * 
 */
package org.apache.skywalking.apm.plugin.channelbound;

import io.netty.util.AttributeKey;
import org.apache.skywalking.apm.agent.core.context.AbstractTracerContext;
import org.apache.skywalking.apm.network.trace.component.Component;
import org.apache.skywalking.apm.network.trace.component.ComponentsDefine;

/**
 * TODO
 *
 *@Author zmgab@qq.com
 *@Date 2023/3/1 15:22
 */
public class Constants {
    public static final AttributeKey<AbstractTracerContext> KEY_CONTEXT = AttributeKey.valueOf("SW_CONTEXT");

    public static final Component COMPONENT_NETTY_HTTP_SERVER = ComponentsDefine.TOMCAT/*为了支持ui的Topology Map展示*/; /*new OfficialComponent(201, "netty-http-server");*/
    public static final Component COMPONENT_NETTY_HTTP_CLIENT = ComponentsDefine.HTTPCLIENT/*为了支持ui的Topology Map展示*/; /*new OfficialComponent(202, "netty-http-client");*/
}
