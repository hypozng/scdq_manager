<%@page import="org.omg.CORBA.Request"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>三川电器管理系统 - 备忘录</title>
<%@include file="common.jsp"%>
	<style type="text/css">
		.main-box {
			width: 100%;
			height: 500px;
			position: relative;
		}

		.content {
			width: auto;
			height: 100%;
			float: left;
		}

		.grid {
			width: 630px;
			margin-top: 10px;
		}

	</style>
	<script type="text/javascript" src="res/js/my-ui.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#todo-grid").myGrid({
			"url": "todo/getTodos",
			"columns": [{
				"title": "状态",
				"field": "status",
				"width": 100,
				"formatter": todoStatus2text
			}, {
				"title": "创建时间",
				"field": "createTime",
				"width": 200,
				"formatter": timestamp2text
			}, {
				"title": "待办内容",
				"field": "content",
				"width": 270
			}]
		});
	});

	/**
	 * 显示待办窗口
	 */
	function showTodoWindow(edit) {
		var $row = $(".grid-row.selected");
		var todo = null;
		if (edit) {
			if ($row.length == 0) {
				alert("请选择要修改的代办");
				return;
			}
			if (window.todos != null) {
				todo = window.todos[$row.attr("data-id")];
			}
			if (todo == null) {
				alert("未找到要修改的代办");
				return;
			}
		}
		var $todoWindow = $("#todo.window");
		$todoWindow.find(".title").text(edit ? "修改待办信息" : "新增待办");
		$todoWindow.attr("edit", edit ? edit : null);
		$todoWindow.attr("todoId", edit ? todo.id : null);
		if (edit) {
			$todoWindow.find("[name='content']").val(todo.content);
		} else {
			$todoWindow.find("[name]").val("");
		}
		$todoWindow.show();
	}

	/**
	 * 提交更改
	 */
	function submitTodo() {
		var $todoWindow = $("#todo.window");
		var data = findFields($todoWindow);
		if (data.content == '') {
			alert("请输入待办内容");
			$todoWindow.find("[name='content']").focus();
			return;
		}
		var url = "todo/addTodo";
		if ($todoWindow.attr("edit")) {
			url = "todo/updateTodo";
			data.id = $todoWindow.attr("todoId");
		}
		$.ajax({
			type: "POST",
			url: url,
			data: data,
			success: function(result) {
				if (!result) {
					alert("操作失败");
					return;
				}
				$todoWindow.hide();
				refresh();
			}
		});
	}

	/**
	 * 删除待办
	 */
	function deleteTodo() {
		var todo = $("#todo-grid").myGrid("getSelected");
		if (todo == null) {
			alert("未找到要删除的待办信息");
			return;
		}
		if (!confirm("确定删除此待办信息?")) {
			return;
		}
		$.ajax({
			type: "POST",
			url: "todo/deleteTodo",
			data: {
				"todoId": todo.id
			},
			success: function(result) {
				if (!result) {
					alert("操作失败");
					return;
				}
				refresh();
			}
		});
	}
	</script>
</head>
<body>
	<div class="main-box">
		<div class="content">
			<div class="menu">
				<button onclick="javascript:showTodoWindow()">添加</button>
				<button onclick="javascript:showTodoWindow(true)">修改</button>
				<button onclick="javascript:deleteTodo()">删除</button>
			</div>

			<div id="todo-grid"></div>
		</div>
		
		<div class="window" id="todo">
			<div class="head">
				<img class="logo"/>
				<div class="title"></div>
			</div>
			<div class="center">
				<div class="form-field">
					<label>状态</label>
					<select name="status">
						<option value="1" selected="selected">未开始</option>
						<option value="2">进行中</option>
						<option value="3">已完成</option>
					</select>
				</div>
				<div class="form-field">
					<label>内容</label>
					<input type="text" name="content"/>
				</div>
				<div class="form-field">
					<label></label>
					<div class="content">
						<button onclick="javascript:submitTodo()">提交</button>
						<button onclick="javascript:$('#todo.window').hide()">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>