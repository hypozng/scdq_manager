<%@page import="org.omg.CORBA.Request"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>三川电器管理系统 - 数据字典</title>
<%@include file="../common.jsp" %>
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
	margin-left: 10px;
}

.grid {
	margin-top: 10px;
}

.grid-cell.name {
	width:100px;
}

.grid-cell.create-time {
	width:200px;
}

.grid-cell.remark {
	width:270px;
}

#goods.window .center {
	height: auto;
}
</style>
    <script type="text/javascript" src="res/js/my-ui.js"></script>
    <script type="text/javascript">
    var configs = [{
        "name": "分类",
        "model": "category",
        "addTitle": "新增分类",
        "updateTitle": "修改分类信息",
        "queryUrl": "goods/getCategories",
        "addUrl": "goods/addCategory",
        "updateUrl": "goods/updateCategory",
        "deleteUrl": "goods/deleteCategory",
        "deleteParam": "categoryId"
    }, {
        "name": "品牌",
        "model": "brand",
        "addTitle": "新增品牌",
        "updateTitle": "修改品牌信息",
        "queryUrl": "goods/getBrands",
        "addUrl": "goods/addBrand",
        "updateUrl": "goods/updateBrand",
        "deleteUrl": "goods/deleteBrand",
        "deleteParam": "brandId"
    }];
    var currentConfig = configs[0];

    $(document).ready(function() {

        $("#info-tab").myTab({
            "tabBar": "#info-tab",
            "tabContent": "",
            "titleField": "name",
            "onTabClick": function(config) {
                currentConfig = config;
                refresh();
            }
        });

        $("#data-grid").myGrid({
            "columns": [{
                "title": "名称",
                "field": "name",
                "width": 100
            }, {
                "title": "创建时间",
                "field": "createTime",
                "width": 200,
                "formatter": timestamp2text
            }, {
                "title": "备注",
                "field": "remark",
                "width": 270
            }]
        });

        $("#info-tab").myTab("load", configs);
    });

    /**
     * 刷新页面数据
     */
    function refresh() {
        $("#data-grid").myGrid("request", currentConfig.queryUrl);
    }

    /**
     * 显示数据窗体
     */
    function showModelWindow(edit) {
        var $row = $(".grid-row.selected");
        var data = null;
        if (edit) {
            if ($row.length == 0) {
                alert("请选择要修改的" + urls[model].name);
                return;
            }
            if (window.models != null) {
                data = window.models[$row.attr("data-id")];
            }
            if (data == null) {
                alert("未找到要修改的" + uls[model].name);
                return;
            }
        }
        var $modelWindow = $("#model.window");
        $modelWindow.find(".title").text(edit ? urls[model].updateTitle : urls[model].addTitle);
        $modelWindow.attr("edit", edit ? true : null);
        if (edit) {
            $modelWindow.find("[name='name']").val(data.name);
            $modelWindow.find("[name='remark']").val(data.remark);
            $modelWindow.attr("modelId", data.id);
        } else {
            $modelWindow.find("[name]").val("");
            $modelWindow.attr("modelId", null);
        }
        $modelWindow.show();
    }

    /**
     * 提交更改信息
     */
    function submitModel() {
        var data = {};
        var $modelWindow = $("#model.window");
        $modelWindow.find("[name]").each(function(i, e) {
            var $e = $(e);
            data[$e.attr("name")] = $e.val();
        });
        var url = urls[model].addUrl
        if ($modelWindow.attr("edit")) {
            data.id = $modelWindow.attr("modelId");
            url = urls[model].updateUrl;
        }
        if (data.name == '') {
            alert("请输入" + urls[model].name + "名称");
            $modelWindow.find("[name='name']").focus();
            return;
        }
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            success: function(result) {
                if (!result || result == 0) {
                    alert("操作失败");
                    return;
                }
                refresh();
                $modelWindow.hide();
            }
        });
    }

    /**
     * 删除
     */
    function deleteModel() {
        var $row = $(".grid-row.selected");
        if ($row.length == 0) {
            alert("请选择要删除的" + urls[model].name);
            return;
        }
        var data = null;
        if (window.models != null) {
            data = window.models[$row.attr("data-id")];
        }
        if (data == null) {
            alert("未找到要删除的" + urls[model].name);
            return;
        }
        if (!confirm("确定要删除该" + urls[model].name + "?")) {
            return;
        }
        var _data = {};
        _data[urls[model].deleteParam] = data.id;
        $.ajax({
            type: "POST",
            url: urls[model].deleteUrl,
            data: _data,
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
		<div id="info-tab"></div>
		<div class="content">
			<div class="menu">
				<button onclick="javascript:showModelWindow()">新增</button>
				<button onclick="javascript:showModelWindow(true)">修改</button>
				<button onclick="javascript:deleteModel()">删除</button>
			</div>
			<div id="data-grid"></div>
		</div>
		<div id="model" class="window">
			<div class="head">
				<img class="logo"/>
				<div class="title">新增商品</div>
			</div>
			<div class="center">
				<div class="form-field">
					<label>名称</label>
					<input type="text" name="name" />
				</div>
				<div class="form-field">
					<label>备注</label>
					<input type="text" name="remark"/>
				</div>
				<div class="form-field">
					<label></label>
					<div class="content">
						<button onclick="javascript:submitModel()">提交</button>
						<button onclick="javascript:$('#model.window').hide()">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>