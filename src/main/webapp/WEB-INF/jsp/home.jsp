<%@page import="org.omg.CORBA.Request"%>
<%@ page import="com.scdq.manager.model.User" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>三川电器管理系统 - 首页</title>
<%@include file="common.jsp"%>
	<style type="text/css">
	.window.static {
		width: 800px;
		margin-top: 20px;
	}

	.user-box {
		float: right;
		color: white;
	}

	.user-box a{
		color: #D0D0D0;
		font-size:12px;
		text-decoration:none;
	}

	.window .center {
		height: 600px;
	}

	.tab-content {
		width:100%;
		height:550px;
	}

    #menus-2 {
        height: 540px;
        display: none;
        margin-top: 10px;
    }

    #menus-2.show {
        display: block;
    }

    #menus-2.show+.tab-content {
        width: 668px;
        margin-left: 10px;
    }
	</style>
	<script type="text/javascript" src="res/js/my-ui.js"></script>
	<script type="text/javascript">
	var menus = [{
		"id": "goods",
		"title": "商品信息",
		"url": "home/goods"
	}, {
		"id": "todo",
		"title": "备忘录",
        "sub": [{
            "id": "normalTodo",
            "title": "普通待办",
            "url": "home/todo"
        }]
	}, {
		"id": "dictionary",
		"title": "数据字典",
		"url": "home/dictionary"
	}, {
		"id": "settings",
		"title": "设置",
		"sub": [{
			"id": "modifyPassword",
			"title": "修改密码",
			"url": "home/settings/modifyPassword"
		}]
	}];

	function logout() {
		$.ajax({
			"type": "POST",
			"url": "<%=basePath%>sys/logout",
			"success": function(result) {
				if (result == null || result.code == 1) {
					alert("操作失败: " + result.message);
					return;
				}
				location.reload();
			}
		});
	}

	$(document).ready(function() {
        $("#menus-2").myTab({
            tabBar: "#menus-2",
            tabContent: "#menu-content"
        });
		$("#menus-1").myTab({
            tabBar: "#menus-1",
            tabContent: "#menu-content",
			data: menus,
			tabLocation: "top",
            onTabClick: function(menu) {
                if (menu.sub) {
                    $("#menus-2").addClass("show");
                    $("#menus-2").myTab("load", menu.sub);
                } else {
                    $("#menus-2").removeClass("show");
                }
            }
		});
	});
	</script>
</head>
<body>
	<div class="window static">
		<div class="head">
			<img class="logo" />
			<div class="title">三川电器</div>
            <div class="user-box">
                <%=((User)session.getAttribute("LOGIN_USER")).getUsername()%>
                <a href="javascript:logout()">退出</a>
            </div>
		</div>
		<div class="center">
			<div id="menus-1"></div>
            <div id="menus-2"></div>
            <iframe id="menu-content"></iframe>
		</div>
	</div>
</body>
</html>