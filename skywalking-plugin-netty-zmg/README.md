## skywalking的netty http的插件

没有使用ContextManager的线程变量来处理AbstractTracerContext, 在netty的io模型中只使用线程变量没有办法正确记录span stack.     
现在是把AbstractTracerContext和netty的`io.netty.channel.AbstractChannel`绑定, 造成的结果是没有多trace segment     

## 来源说明
这个项目是在别人项目基础上改的。   
fork from https://github.com/otyyyywangwenbin/skywalking-plugin-netty-http

## 改动说明
主要调整如下：   
* compiler的版本从1.6调整成1.8了
* skywalking.version的版本从6.0.0-GA调整成8.3.0
* 为maven-shade-plugin增加了groupId和version标签
* 对部分代码的作用域进行调整

## 编译打包
使用 mvn clean package 即可，将 target目录下的 apm-netty-http-4.x-plugin-0.0.1.jar 放入目标服务器的 /your/path/agent/plugins/ 目录下就可以了。

## 注意事项
如果 JAVA_TOOL_OPTIONS 中已经设置 -javaagent:/agent/skywalking-agent.jar 了，那么在启动的脚本中的java命令的后边就不能再加 -javaagent:/agent/skywalking-agent.jar了，否则启动会报 Java.lang.ClassFormatError: Duplicate interface name "org/apache/skywalking/apm/agent/core/plugin/interceptor/enhance/EnhancedInstance" 错误