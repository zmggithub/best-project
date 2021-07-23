<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="jquery.min.js"></script>
<style>
    div {
        margin: 5px;
    }

    .login-button {
        width: 190px;
        height: 40px;
        border-width: 0px;
        border-radius: 3px;
        background: #1E90FF;
        cursor: pointer;
        outline: none;
        font-family: Microsoft YaHei;
        color: white;
        font-size: 17px;
    }
    .login-button:hover {
        background: #5599FF;
    }

    .root-div{
        margin-top: 150px;
        border: 1px solid #a5b59f;
        border-radius: 34px;
        width: 60%;
        margin-left: 20%;
    }
</style>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>index</title>
</head>
<body>
<div class="root-div">
    <h1 style="text-align:center;">项目日报表</h1>
    <div style=" text-align: center;">
        <form id="multiform"  method="post" action="${pageContext.request.contextPath}/importExcel" enctype="multipart/form-data">

            <h4>上传基础文件</h4>
            <div style=" width: 500px; margin-left: 35%;">
                <div>
                    缴交明细：<input type="file" class="form-control" name="files1"/>
                </div>
                <div>
                    交易记录：<input type="file" class="form-control" name="files2"/>
                </div>
            </div>
            <div class="btn" style="display:block;margin:30px auto;text-align:center;">
                <button class="login-button">生成日报表</button>
            </div>

        </form>
    </div>
</div>


</body>
</html>