<%@ page import="com.scdq.manager.sys.model.User" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>三川电器管理系统 - 首页</title>
    <%@include file="common.jsp" %>
    <style type="text/css">
        .window.static {
            width: 800px;
            margin-top: 20px;
        }

        .user-box {
            float: right;
            color: white;
        }

        .user-box a {
            color: #D0D0D0;
            font-size: 12px;
            text-decoration: none;
        }

        .window .center {
            height: 600px;
        }

        #menu-content {
            width: 100%;
        }

        #menus-1 {
            width: 80px;
            height: 200px;
            overflow: hidden;
            position: absolute;
            background-color: white;
            left: 5px;
            border: 1px orange solid;
            border-radius: 5px;
            box-shadow: 0 0 10px;
        }

        #menus-1 .tab {
            margin: 0;
            height: 50px;
            line-height: 50px;
            text-align: center;
        }

        #menus-2 {
            /*height: 540px;*/
            display: none;
            margin-top: 10px;
        }

        #menus-2.show {
            display: block;
        }

    </style>
    <script type="text/javascript" src="res/js/my-ui.js"></script>
    <script type="text/javascript">

        function logout() {
            $.ajax({
                "type": "POST",
                "url": "<%=basePath%>sys/logout",
                "success": function (result) {
                    if (result == null || result.code == 1) {
                        alert("操作失败: " + result.message);
                        return;
                    }
                    location.reload();
                }
            });
        }

        function adjustLayout() {
            var $doc = $(document);
            var $dom = $("#menus-1");
            $dom.css("top", ($doc.height() - $dom.height()) / 2 + "px");

            var $center = $(".center");
            var $menu2 = $("#menus-2");
            var $menuContent = $("#menu-content");
            $menuContent.height($center.height() - $menu2.height());
        }

        $(document).ready(function () {
            adjustLayout();
            $("#menus-2").myTab({
                "tabBar": "#menus-2",
                "tabContent": "#menu-content",
                "titleField": "name",
                "tabLocation": "top"
            });
            $("#menus-1").myTab({
                "tabBar": "#menus-1",
                "tabContent": "#menu-content",
                "url": "sys/getMenus",
                "requestSuccess": function (data) {
                    this.load(data.data);
                },
                "titleField": "name",
                "onTabClick": function (menu) {
                    if (menu.subMenus) {
                        $("#menus-2").addClass("show");
                        $("#menus-2").myTab("load", menu.subMenus);
                    } else {
                        $("#menus-2").removeClass("show");
                    }
                }
            });
        });
    </script>
</head>
<body>
<div id="menus-1"></div>
<div class="window static">
    <div class="head">
        <img class="logo"/>

        <div class="title">三川电器</div>
        <div class="user-box">
            <%=((User) session.getAttribute("LOGIN_USER")).getUsername()%>
            <a href="javascript:logout()">退出</a>
        </div>
    </div>
    <div class="center">
        <div id="menus-2"></div>
        <iframe id="menu-content"></iframe>
    </div>
</div>
</body>
</html>