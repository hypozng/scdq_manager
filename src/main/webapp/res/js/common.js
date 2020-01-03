/**
 * 将时间戳转换为文本显示
 * @param time 时间戳
 * @returns 转换后的文本
 */
function timestamp2text(time) {
	var date = new Date(time);
	var y = date.getFullYear(),
		M = date.getMonth() + 1,
		d = date.getDate(),
		H = date.getHours(),
		m = date.getMinutes(),
		s = date.getSeconds();
	
	return y + "-" + (M < 10 ? "0" : "") + M
		+ "-" + (d < 10 ? "0" : "") + d
		+ " " + (H < 10 ? "0" : "") + H
		+ ":" + (m < 10 ? "0" : "") + m
		+ ":" + (s < 10 ? "0" : "") + s;
}

/**
 * 将待办状态转换为相应的文本显示
 * @param status
 * @returns
 */
function todoStatus2text(status) {
	switch (status) {
	case 1:
		return "未开始";
	case 2:
		return "进行中";
	case 3:
		return "已完成";
	}
}

/**
 * 查找字段数据
 * @param $form
 * @returns
 */
function findFields($form) {
	if ($form == null || $form.length == 0) {
		return;
	}
	var data = {};
	$form.find("[name]").each(function(i, e) {
		var $e = $(e);
		data[$e.attr("name")] = $e.val();
	});
	return data;
}

function postApi(url, data, success) {
	$.ajax({
		"url": url,
		"type": "post",
		"contentType": "application/json",
		"data": JSON.stringify(data),
		"dataType": "json",
		"success": success,
		"error": function(e) {
			console.log(e);
			alert(e);
		}
	});
}