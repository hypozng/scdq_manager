<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>三川电器管理系统 - 出售</title>
    <%@include file="../common.jsp" %>
    <style type="text/css">
        #goods-tab {
            width: 150px;
        }
        #goods-tab .tab {
            font-size: 10px;
        }
        #order-box {
            float: left;
            margin-left: 10px;
        }
        #order-grid {
            width: 100%;
            float: none;
        }
        .grid-cell button {
            width: auto;
        }
        .grid-cell input {
            width: 100%;
        }
        #create-order-button {
            margin: 10px auto;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#categories-tab").myTab({
                "tabBar": "#categories-tab",
                "tabContent": "",
                "titleField": "name",
                "onTabClick": function(category) {
                    $("#goods-tab").myTab("requestParams").categoryId = category.id;
                    $("#goods-tab").myTab("request");
                }
            });
            $("#goods-tab").myTab({
                "tabBar": "#goods-tab",
                "url": "goods/getCommoditiesByCategory",
                "autoLoad": false,
                "tabContent": "",
                "tabIndex": -1,
                "titleField": function (goods) {
                    var brand = (goods && goods.brand && goods.brand.name) || "";
                    return brand + goods.model;
                },
                "onTabClick": function(goods) {
                    addGoodsCount(goods, 1);
                }
            });
            $("#categories-tab").myTab("request", "goods/getCategories");
            $("#goods-form").myForm({
                "fields": [{
                    "name": "id",
                    "type": "hidden"
                },{
                    "name": "category.name",
                    "title": "类型",
                    "readonly": true
                }, {
                    "name": "brand.name",
                    "title": "品牌",
                    "readonly": true
                }, {
                    "name": "model",
                    "title": "型号",
                    "readonly": true
                }, {
                    "name": "property",
                    "title": "性能",
                    "readonly": true
                }, {
                    "name": "purchasePrice",
                    "title": "进价",
                    "readonly": true
                }, {
                    "name": "salePrice",
                    "title": "卖价",
                    "readonly": true
                }, {
                    "name": "preferentialPrice",
                    "title": "优惠价",
                    "readonly": true
                }, {
                    "name": "remark",
                    "title": "备注",
                    "readonly": true
                }, {
                    "type": "action",
                    "actions": [{
                        "title": "关闭",
                        "onClick": function() {
                            $("#goods-window").myWindow("hide");
                        }
                    }]
                }]
            });
            $("#order-grid").myGrid({
                "columns": [{
                    "field": "goods.model",
                    "title": "商品",
                    "width": 200,
                    "formatter": function(val, goods) {
                        goods = goods.goods;
                        var category = (goods.category && goods.category.name) || "";
                        var brand = (goods.brand && goods.brand.name) || "";
                        var model = goods.model || "";
                        return brand + model + category;
                    },
                    "renderer": function(val, goods, index) {
                        return "<a href=\"javascript:showGoodsDetail(" + index + ");\">" + val + "</a>"
                    }
                }, {
                    "field": "count",
                    "title": "数量",
                    "width": 100,
                    "renderer": function(value, model, index) {
                        return "<button onclick=\"javascript:addGoodsCount(" + index + ", -1)\">-</button>"
                            + "<span style=\"margin:0px 5px\">" + value + "</span>"
                            + "<button onclick=\"javascript:addGoodsCount(" + index + ", 1)\">+</button>";
                    }
                }, {
                    "field": "finalPrice",
                    "title": "成交价",
                    "width": 100,
                    "renderer": function(value, model, index) {
                        return "<input type=\"text\" value=\"" + value + "\" oninput=\"javascript:updateOrderFinalPrice(" + index + ", this.value)\">";
                    }
                }]
            });

            $("#goods-window").myWindow({
                "title": "商品信息",
                "height": 320
            });

            $("#order-form").myForm({
                "fields": [{
                    "name": "customerName",
                    "title": "客户姓名",
                }, {
                    "name": "customerPhone",
                    "title": "客户电话"
                }, {
                    "name": "customerAddress",
                    "title": "客户住址"
                }, {
                    "name": "finalPrice",
                    "title": "成交价",
                    "dataType": "float"
                }, {
                    "name": "remark",
                    "title": "备注"
                }, {
                    "type": "action",
                    "actions": [{
                        "title": "提交",
                        "onClick": submitOrder
                    }, {
                        "title": "取消",
                        "onClick": function () {
                            $("#order-window").myWindow("hide");
                        }
                    }]
                }]
            });

            $("#order-window").myWindow({
                "title": "提交订单",
                "height": 400
            });

            $("#create-order-button").click(function() {
                var order = $("#order-form").myForm("data") || {};
                console.log(order);
                $("#order-window").myWindow("show")
            });

            adjustLayout();
        });

        /**
         * 调整布局
         */
        function adjustLayout() {
            var width = $(document).width() - 20;
            var orderGridWidth = width - 10
                - $("#categories-tab")[0].offsetWidth
                - $("#goods-tab")[0].offsetWidth;
            $("#order-box").width(orderGridWidth);
        }

        /**
         * 添加商品数量
         * @param goods 行号
         * @param amount 数量
         */
        function addGoodsCount(goods, amount) {
            var data = $("#order-grid").myGrid("data") || [];
            var orderGoods;
            var index;
            if (typeof goods == "object") {
                $.each(data, function(i, e) {
                    if (e.goods.id == goods.id) {
                        orderGoods = e;
                        index = i;
                        return false;
                    }
                });
                if (!orderGoods) {
                    orderGoods = {
                        "goods": goods,
                        "finalPrice": 0,
                        "count": 0
                    };
                    data.push(orderGoods);
                }
            } else if (typeof goods == "number") {
                index = goods;
                var data = $("#order-grid").myGrid("data");
                if (data) {
                    orderGoods = data[goods];
                }
            }
            if (!orderGoods) {
                return;
            }
            orderGoods.count += amount;
            orderGoods.finalPrice += orderGoods.goods.salePrice * amount;
            if (orderGoods.count == 0) {
                data.splice(index, 1);
            }
            $("#order-grid").myGrid("load", data);
            adjustLayout();
        }

        function updateOrderFinalPrice(index, price) {
            var data = $("#order-grid").myGrid("data");
            if (!data || !data[index] || isNaN(price)) {
                return;
            }
            data[index].finalPrice = parseFloat(price);
        }

        /// 查看商品详细信息
        function showGoodsDetail(index) {
            var data = $("#order-grid").myGrid("data");
            if (!data || !data[index]) {
                return;
            }
            $("#goods-form").myForm("load", data[index].goods);
            $("#goods-window").myWindow("show");
        }

        function deleteGoods(index) {
            var data = $("#order-grid").myGrid("data");
            if (!data) {
                return;
            }
            if (index < 0 || index >= data.length) {
                return;
            }
            data.splice(index, 1);
        }

        /**
         * 提交订单
         */
        function submitOrder() {
            if (!confirm("确认提交订单?")) {
                return;
            }
            var order = $("#order-form").myForm("getData");
            var goodsList = [];
            $.each($("#order-grid").myGrid("data"), function(i, e) {
                goodsList.push({
                    "commodityId": e.goods.id,
                    "count": e.count,
                    "finalPrice": e.finalPrice
                });
            });
            order.commodities = goodsList;
            postApi("sale/addOrder", order, function(result) {
                if (result.code == 1) {
                    alert("添加订单失败\r\n" + result.message);
                    return;
                }
                alert("添加订单成功");
                $("#order-grid").myGrid("load", []);
                $("#order-form").myForm("clear");
                $("#order-window").myWindow("hide");
            });
        }
    </script>
</head>
<body>
<div id="categories-tab"></div>
<div id="goods-tab"></div>
<div id="order-box">
    <div id="order-grid"></div>
    <button id="create-order-button">创建订单</button>
</div>
<div id="goods-window">
    <div id="goods-form"></div>
</div>
<div id="order-window">
    <div id="order-form"></div>
</div>
</body>
</html>