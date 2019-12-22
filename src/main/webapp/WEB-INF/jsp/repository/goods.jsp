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

#goods.window .center {
	height: auto;
}
</style>
    <script type="text/javascript" src="res/js/my-ui.js"></script>
    <script type="text/javascript">
    function adjustLayout() {
        var $doc = $(document);

        var $tabs = $("#categories-tab");
        console.log($tabs.width());
    }
	$(document).ready(function() {
	    $("#categories-tab").myTab({
            "tabBar": "#categories-tab",
            "tabContent": "",
            "titleField": "name",
            "requestSuccess": function (data) {
                this.load(data);
                adjustLayout();
            },
            "onTabClick": function(menu) {
                $("#goods-grid").myGrid("requestParams").categoryId = menu.id;
                $("#goods-grid").myGrid("request");
            }
        });

	    $("#goods-grid").myGrid({
            "url": "goods/getGoodsByCategory",
			"autoLoad": false,
			"columns": [{
				"title": "品牌",
				"field": "brand",
                "formatter": function(brand) {
                    return brand.name;
                }
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

        $("#categories-tab").myTab("request", "goods/getCategories");

        $("#goods-form").myForm({
			"validateFailed": function(field) {
				console.log(field);
			},
			"fields": [{
				"name": "barCode",
				"title": "条码",
				"notEmpty": true
			}, {
				"name": "category.id",
				"title": "类型",
				"notEmpty": true,
				"type": "select",
				"options": {
					"url": "goods/getCategories",
					"valueField": "id",
					"textField": "name"
				}
			}, {
				"name": "brand.id",
				"title": "品牌",
				"notEmpty": true,
				"type": "select",
				"options": {
					"url": "goods/getBrands",
					"valueField": "id",
					"textField": "name"
				}
			}, {
				"name": "model",
				"title": "型号",
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
			}]
		});
        $("#goods.window").show();
	});

    function test() {
        console.log($("#goods-grid").myGrid("getSelected"));
    }

	/**
	 * 显示新增商品的窗口
	 */
	function showGoodsWindow(edit) {
		var $goodsWindow = $("#goods.window");
		var goods = $("#goods-grid").myGrid("getSelected");
		if (edit && !goods) {
			alert("请选择要修改的商品");
			return;
		}
		$goodsWindow.find(".title").text(edit ? "修改商品信息" : "新增商品");
		$goodsWindow.attr("edit", edit ? edit : null);
		if (edit) {
			$("#goods-form").myForm("load", goods);
		} else {
			$("#goods-from").myForm("clear");
		}
		$goodsWindow.show();
	}

	/**
	 * 提交商品信息
	 */
	function submitGoods() {
		console.log($("#goods-form").myForm("getData"));
		console.log($("#goods-form").myForm("validate"));
		return;
		var $goodsWindow = $("#goods.window");
		var data = findFields($goodsWindow);
		if (data.model == '') {
			alert("请输入产品型号");
			$("[name='model']").focus();
			return;
		}
		if (data.purchasePrice == '' || isNaN(data.purchasePrice)) {
			alert("请输入正确的进价");
			$("[name='purchasePrice']").focus();
			return;
		}
		if (data.salePrice == '' || isNaN(data.salePrice)) {
			alert("请输入正确的卖价");
			$("[name='salePrice']").focus();
			return;
		}
		if (data.preferentialPrice == '' || isNaN(data.preferentialPrice)) {
			alert("请输入正确的优惠价");
			$("[name='preferentialPrice']").focus();
			return;
		}
		var $goodsWindow = $("#goods.window");
		var url = "goods/addGoods";
		if ($goodsWindow.attr("edit")) {
			url = "goods/updateGoods";
			data.id = $goodsWindow.attr("goodsId");
		}
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			success : function(rows) {
				if (!rows) {
					alert("操作失败");
					return;
				}
				$goodsWindow.hide();
				loadGoods($(".tab.selected").attr("data-id"));
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
				<button onclick="javascript:test()">测试</button>
			</div>
			<div id="goods-grid"></div>
		</div>
		<div id="goods" class="window">
			<div class="head">
				<img class="logo" /> <span class="title">新增商品</span>
			</div>
			<div class="center">
<%--				<div class="form-field">--%>
<%--					<label>条码</label> <input type="text" name="barCode"--%>
<%--						 placeholder="请输入商品条码" />--%>
<%--				</div>--%>
<%--				<div class="form-field">--%>
<%--					<label>类型</label> <select name="category.id">--%>
<%--						<option value="0">请选择商品类型</option>--%>
<%--					</select>--%>
<%--				</div>--%>
<%--				<div class="form-field">--%>
<%--					<label>品牌</label> <select name="brand.id">--%>
<%--						<option value="0">请选择商品品牌</option>--%>
<%--					</select>--%>
<%--				</div>--%>
<%--				<div class="form-field">--%>
<%--					<label>型号</label> <input type="text" name="model"--%>
<%--						placeholder="请输入商品型号" />--%>
<%--				</div>--%>
<%--				<div class="form-field">--%>
<%--					<label>性能</label> <input type="text" name="property"--%>
<%--						placeholder="请输入商品性能" />--%>
<%--				</div>--%>
<%--				<div class="form-field">--%>
<%--					<label>进价</label> <input type="text" name="purchasePrice"--%>
<%--						placeholder="请输入商品进价" />--%>
<%--				</div>--%>
<%--				<div class="form-field">--%>
<%--					<label>卖价</label> <input type="text" name="salePrice"--%>
<%--						placeholder="请输入商品卖价" />--%>
<%--				</div>--%>
<%--				<div class="form-field">--%>
<%--					<label>优惠价</label> <input type="text" name="preferentialPrice"--%>
<%--						placeholder="请输入商品优惠价" />--%>
<%--				</div>--%>
<%--				<div class="form-field">--%>
<%--					<label>备注</label> <input type="text" name="remark"--%>
<%--						placeholder="请输入备注信息" />--%>
<%--				</div>--%>
				<div id="goods-form"></div>
				<div class="form-field">
					<label></label>
					<div class="content">
						<button onclick="javascript:submitGoods()">提交</button>
						<button onclick="javascript:$('#goods.window').hide()">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>