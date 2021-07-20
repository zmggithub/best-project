<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>index</title>
</head>
<body>
    <h1 class ="login">用户登录</h1>

<form action="${pageContext.request.contextPath}/user/user" method="post">
     用户名：<input tpye="text" name="username"> <br/>
    密码：<input type="password" name="password"><br/>
    <input type="submit" value="登录">
</form>
</body>
</html>