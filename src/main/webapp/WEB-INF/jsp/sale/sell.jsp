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
        #order-grid {
            float:right;
            width: 500px;
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
                "url": "goods/getGoodsByCategory",
                "autoLoad": false,
                "tabContent": "",
                "tabIndex": -1,
                "requestSuccess": function(data) {
                    this.load(data);
                    if (data == null || data.length == 0) {
                        $("#goods-form").hide();
                        return;
                    }
                    $("#goods-form").show();
                },
                "titleField": function (goods) {
                    var brand = (goods && goods.brand && goods.brand.name) || "";
                    return brand + goods.model;
                },
                "onTabClick": function(goods) {
                    var orderData = $("#order-grid").myGrid("data");
                    if (orderData == null) {
                        orderData = [];
                        $("#order-grid").myGrid("attr", "data", orderData);
                    }
                    var flag = false;
                    $.each(orderData, function(i, e) {
                        if (e.goods.id == goods.id) {
                            flag = true;
                            ++e.count;
                            return false;
                        }
                    });
                    if (!flag) {
                        orderData.push({
                            "goods": goods,
                            "count": 1
                        });
                    }
                    $("#order-grid").myGrid("load");
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
                        "onClick": function(_this) {
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
                    }
                }, {
                    "field": "count",
                    "title": "数量"
                }]
            });

            $("#goods-window").myWindow({
                "title": "商品信息",
                "height": 320
            });
        });
    </script>
</head>
<body>
<div id="categories-tab"></div>
<div id="goods-tab"></div>
<div id="order-grid"></div>

<div id="goods-window">
    <div id="goods-form"></div>
</div>
</body>
</html>