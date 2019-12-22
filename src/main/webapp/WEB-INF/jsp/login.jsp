<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>三川电器管理系统 - 登录</title>
    <%@include file="common.jsp" %>
    <style type="text/css">
        .window {
            width: 400px;
        }

        .window .center {
            height: 120px;
            text-align: center;
        }

        button {
            height: 30px;
        }
    </style>
    <script type="text/javascript">
        var win = window;
        while (win.location.href != win.parent.location.href) {
            win = win.parent;
        }
        if (win.location.href != window.location.href) {
            win.location.reload();
        }
    </script>
    <script type="text/javascript">
        function validate(data) {
            if (data["account"] == null || data["account"] == '') {
                return "请输入账号";
            }
            if (data["password"] == null || data["password"] == '') {
                return "请输入密码";
            }
        }

        /**
         * 发送登录请求，登录成功后跳转到首页
         */
        function login() {
            var data = findFields($("#login-form"));
            var message = validate(data);
            if (message != null) {
                alert(message);
                return;
            }
            $.ajax({
                type: "POST",
                url: "<%=basePath%>sys/login",
                data: data,
                success: function (result) {
                    if (result == null || result.code == 1) {
                        alert("登录失败: " + result.message);
                        return;
                    }
                    location.href = "web";
                }
            });
        }
    </script>
</head>
<body>
<div class="window static" id="login-form">
    <div class="head">
        <img class="logo"/>

        <div class="title">登录</div>
    </div>
    <div class="center">
        <div class="form-field">
            <label>账号</label>
            <input type="text" name="account" placeholder="请输入用户名/手机号/邮箱"/>
        </div>
        <div class="form-field">
            <label>密码</label>
            <input type="password" name="password" placeholder="请输入密码"/>
        </div>
        <button onclick="javascript:login();">登录</button>
    </div>
</div>
</body>
</html>