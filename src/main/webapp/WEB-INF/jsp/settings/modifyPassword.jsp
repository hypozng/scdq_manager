<%@page import="org.omg.CORBA.Request"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>三川电器管理系统 - 修改密码</title>
<%@include file="../common.jsp"%>
<style type="text/css">
.main-box {
	width:316px;
	height:100%;
	margin:0px auto;
}
</style>
<script type="text/javascript">
function validate(data) {
	if (data["original"] == null || data["original"] == '') {
		return "请输入原密码";
	}
	if (data["password"] == null || data["password"] == '') {
		return "请输入新密码";
	}
	if (data["samePassword"] == null || data["samePassword"] == null) {
		return "请输入确认密码";
	}
	if (data["password"] != data["samePassword"]) {
		return "两次输入的密码不一致";
	}
}

/**
 * 提交更改
 */
function submit() {
	var data = findFields($("#modify-form"))
	var message = validate(data);
	if (message != null) {
		alert(message);
		return;
	}
	data["now"] = data["password"];
	data["samePassword"] = null;
	data["password"] = null;
	$.ajax({
		type: "POST",
		url: "sys/modifyPassword",
		data: data,
		success: function(result) {
			if (!result) {
				alert("操作失败");
				return;
			}
			alert("修改成功");
			location.reload();
		}
	});
}

</script>
</head>
<body>
	<div class="main-box" id="modify-form">
		<div class="form-field">
			<label>原密码</label>
			<input type="password" name="original" placeholder="请输入原密码" />
		</div>
		<div class="form-field">
			<label>新密码</label>
			<input type="password" name="password" placeholder="请输入新密码" />
		</div>
		<div class="form-field">
			<label>确认密码</label>
			<input type="password" name="samePassword" placeholder="请再次输入新密码" />
		</div>
		<div class="form-field">
			<label></label>
			<div class="content">
				<button onclick="javascript:submit()">修改</button>
			</div>
		</div>
	</div>
</body>
</html>