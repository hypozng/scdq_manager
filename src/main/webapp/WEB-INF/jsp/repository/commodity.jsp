<%@page import="org.omg.CORBA.Request"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>三川电器管理系统 - 商品</title>
<%@include file="../common.jsp"%>
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
	width: 630px;
	margin-top: 10px;
}
</style>
    <script type="text/javascript" src="res/js/my-ui.js"></script>
    <script type="text/javascript">
	$(document).ready(function() {
		/**
		 * 商品种类列表
		 */
	    $("#categories-tab").myTab({
            "tabBar": "#categories-tab",
            "tabContent": "",
            "titleField": "name",
            "onTabClick": function(menu) {
                $("#goods-grid").myGrid("requestParams").categoryId = menu.id;
                $("#goods-grid").myGrid("request");
            }
        });

		/**
		 * 商品信息表格
		 */
	    $("#goods-grid").myGrid({
            "url": "repository/getCommoditiesByCategory",
			"autoLoad": false,
			"columns": [{
				"title": "品牌",
				"field": "brand.name"
			}, {
				"title": "型号",
				"field": "model",
                "width": 100
			}, {
				"title": "性能",
				"field": "property"
			}, {
				"title": "进价",
				"field": "purchasePrice"
			}, {
				"title": "优惠价",
				"field": "preferentialPrice"
			}, {
				"title": "卖价",
				"field": "salePrice"
			}, {
				"title": "库存",
				"field": "count"
			}, {
				"title": "备注",
				"field": "remark"
			}]
		});

	    /**
		 * 商品信息表单
		 */
		$("#goods-form").myForm({
			"validateFailed": function(field) {
				console.log(field);
			},
			"submitUrl": "repository/saveCommodity",
			"submitSuccess": function(data) {
				$("#goods-window").myWindow("hide");
				$("#goods-grid").myGrid("request");
			},
			"fields": [{
				"name": "id",
				"type": "hidden"
			}, {
				"name": "category.id",
				"title": "类型",
				"type": "select",
				"options": {
					"url": "repository/getCategories",
					"valueField": "id",
					"textField": "name"
				}
			}, {
				"name": "brand.id",
				"title": "品牌",
				"type": "select",
				"options": {
					"url": "repository/getBrands",
					"valueField": "id",
					"textField": "name"
				}
			}, {
				"name": "model",
				"title": "型号",
				"notEmpty": true
			}, {
				"name": "barCode",
				"title": "条码",
				"notEmpty": true
			}, {
				"name": "property",
				"title": "性能",
				"notEmpty": true
			}, {
				"name": "purchasePrice",
				"title": "进价",
				"notEmpty": true
			}, {
				"name": "salePrice",
				"title": "卖价",
				"notEmpty": true
			}, {
				"name": "preferentialPrice",
				"title": "优惠价",
				"notEmpty": true
			}, {
				"name": "remark",
				"title": "备注"
			}, {
				"type": "action",
				"actions": [{
					"title": "提交",
					"onClick": function() {
						$("#goods-form").myForm("submit");
					}
				}, {
					"title": "取消",
					"onClick": function() {
						$("#goods-window").myWindow("hide");
					}
				}]
			}]
		});

		$("#goods-window").myWindow({
			"height": 350
		});

		/**
		 * 加载初始数据
		 */
        $("#categories-tab").myTab("request", "repository/getCategories");
	});

	/**
	 * 显示商品信息窗口
	 * edit为true表示修改商品信息
	 */
	function showGoodsWindow(edit) {
		$("#goods-form").myForm("clear");
		if (edit) {
			var goods = $("#goods-grid").myGrid("getSelected");
			if (!goods) {
				alert("请选择要修改的商品");
				return;
			}
			$("#goods-form").myForm("load", goods);
		}
		$("#goods-window").myWindow("setTitle", edit ? "修改商品信息" : "新增商品信息");
		$("#goods-window").myWindow("show");
	}

	/**
	 * 提交商品信息
	 */
	function submitGoods() {
		var data = $("#goods-form").myForm("getData");
		$.ajax({
			type : "POST",
			url : "repository/saveCommodity",
			data : data,
			success : function(result) {
				if (result.code == 1) {
					alert("操作失败");
					return;
				}

			}
		});
	}
	
	/**
	 * 商品入库
	 */
	function storeGoods() {
		$row = $(".grid-row.selected");
		if ($row.length == 0) {
			alert("请选择需要入库的商品");
			return;
		}
		var goods = null;
		if (window.goodsMap != null) {
			goods = window.goodsMap[$row.attr("data-id")];
		}
		if (goods == null) {
			alert("未找到商品信息");
			return;
		}
		var num = prompt("请输入入库数量");
		if (num == null) {
			return;
		}
		if (num == '' || isNaN(num)) {
			alert("入库失败，入库数量输入错误");
			return;
		}
		$.ajax({
			type: "POST",
			url: "goods/storeGoods",
			data: {
				"goodsId": goods.id,
				"count": num
			},
			success: function(result) {
				if (!result) {
					alert("入库失败");
					return;
				}
				loadGoods($(".tab.selected").attr("data-id"));
			}
		});
	}
</script>
</head>
<body>
	<div class="main-box">
		<div id="categories-tab"></div>
		<div class="content">
			<div class="menu">
				<button onclick="javascript:showGoodsWindow()">添加</button>
				<button onclick="javascript:showGoodsWindow(true)">修改</button>
				<button onclick="javascript:storeGoods()">入库</button>
			</div>
			<div id="goods-grid"></div>
		</div>
		<div id="goods-window">
			<div id="goods-form"></div>
		</div>
	</div>
</body>
</html>