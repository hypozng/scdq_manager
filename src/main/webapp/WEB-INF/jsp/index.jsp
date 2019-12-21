<%@page import="org.omg.CORBA.Request"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>三川电器管理系统</title>
<%@include file="common.jsp"%>
<style type="text/css">
	.window {
		width: 400px;
	}

	.window .center {
		height: 100px;
		line-height: 100px;
		text-align: center;
		font-size: 24px;
	}

	button.left{
		margin-left:100px;
	}

	button.right {
		margin-left:40px;
	}
</style>

</head>
<body>
	<div class="window static">
		<div class="head">
			<img class="logo" />
			<div class="title">三川电器</div>
		</div>
		<div class="center">三川电器管理系统</div>
		<div class="foot">
			<button class="left">?</button>

			<button class="right" onclick="javascript:location.href='login'">进入系统</button>
		</div>
	</div>
</body>
</html>