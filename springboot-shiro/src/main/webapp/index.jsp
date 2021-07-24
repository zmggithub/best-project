<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>index</title>
</head>
<body>
    <div class="logo">web</div>
    <h1 class ="login">系统主页V1.0</h1>

    <%-- 用户身份信息 --%>
    <h1><shiro:principal/></h1>

    <shiro:authenticated>认证之后展示的内容</shiro:authenticated> <br>

    <shiro:notAuthenticated> 没有认证之后展示的内容</shiro:notAuthenticated><br>

    <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
    <ul>
        <shiro:hasAnyRoles name="user,admin">
            <li>用户管理
            <shiro:hasPermission name="user:add:*"><li>添加</li></shiro:hasPermission>
            <shiro:hasPermission name="user:update:*"><li>修改</li></shiro:hasPermission>
            <shiro:hasPermission name="user:delete:*"><li>删除</li></shiro:hasPermission>
            <shiro:hasPermission name="user:find:*"><li>查询</li></shiro:hasPermission>
            </li>
        </shiro:hasAnyRoles>

        <shiro:hasRole name="admin">
            <li>商品管理</li>
            <li>订单管理</li>
            <li>物流管理</li>
        </shiro:hasRole>


    </ul>
</body>
</html>