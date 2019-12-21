(function(win, $) {
    if (win == null || $ == null) {
        return;
    }

    // ---------------- 公共逻辑代码部分start ----------------
    var _$instanceKey = "_$my_ui_instances";
    var _$instanceIdAttribute = "my-ui-id";

    /**
     * 获取或设置一个ui实例
     * @param id 实例的ID，在实例池中，实例ID是实例的唯一标识符
     * @param instance ui实例，若instance为undefined则返回通过id参数在实例池中找到的实例
     * @returns {*}
     * @private
     */
    var _$instance = function(id, instance) {
        win[_$instanceKey] = win[_$instanceKey] || {};
        if (instance === undefined) {
            return win[_$instanceKey][id];
        }
        win[_$instanceKey][id] = instance;
    };

    /**
     * 创建控制器
     * @param createInstance 实例Builder
     * @returns {Function} 控制器
     * @private
     */
    var _$createController = function(createInstance) {
        return function(field) {
            var instance = _$instance(this.attr(_$instanceIdAttribute));
            if (instance == null) {
                var target = this[0];
                instance = createInstance(field, function(instance) {
                    instance.target = target;
                });
                do {
                    instance.id = Math.random().toString();
                } while(_$instance(instance.id) != null);
                this.attr(_$instanceIdAttribute, instance.id);
                _$instance(instance.id, instance);
            }
            if (typeof field === 'string') {
                if (typeof instance[field] === 'function') {
                    var args = [];
                    for (var i = 0; i < arguments.length - 1; ++i) {
                        args[i] = arguments[i + 1];
                    }
                    return instance[field](args[0], args[1], args[2], args);
                }
                return instance[field];
            }
            return instance;
        }
    };

    /**
     * 创建构造器
     * @private
     */
    var _$createBuilder = function(initialize) {
        return function(options, beforeInitialize) {
            initialize = initialize || function() {};
            beforeInitialize = beforeInitialize || function () {};
            var instance = {
                /**
                 * 初始化程序
                 */
                "initialize": initialize,

                /**
                 * 默认加载的数据
                 */
                "data": null,

                /**
                 * 加载数据的url
                 */
                "url": "",

                /**
                 * 网络请求方式
                 */
                "requestMethod": "GET",

                /**
                 * 从网络请求数据时传递的消息
                 */
                "requestParams": {},

                /**
                 * 加载数据时显示的消息
                 */
                "loadingMessage": "加载中...",

                /**
                 * 获取或设置实例中字段的值
                 * @param field 字段名
                 * @param value 值(若值为undefined则获取该字段的值)
                 * @returns {*} 当value为undefined时返回该字段的值
                 */
                "attr": function(field, value) {
                    if (value === undefined) {
                        return this[field];
                    }
                    this[field] = value;
                },

                /**
                 * 批量设置属性的值
                 * @param fields
                 * @param replaceNull
                 */
                "attrs": function(fields, replaceNull) {
                    if (fields == null || typeof fields !== 'object') {
                        return;
                    }
                    for (var i in fields) {
                        if (replaceNull && this[i] != null) {
                            continue;
                        }
                        this[i] = fields[i];
                    }
                },

                /**
                 * 从网络加载数据
                 * @param url
                 */
                "request": function(url) {
                    if (url) {
                        this.url = url;
                    }
                    if (!this.url) {
                        return;
                    }
                    if (this.beforeRequest() === false) {
                        return;
                    }
                    var _this = this;
                    $.ajax({
                        "type": _this.requestMethod,
                        "url": _this.url,
                        "data": _this.requestParams,
                        "success": function(data) {
                            _this.requestSuccess(data);
                        }
                    });
                },

                /**
                 * 发送网络请求之前执行
                 */
                "beforeRequest": function() {

                },

                /**
                 * 网络请求结束之后执行
                 */
                "afterRequest": function() {

                },

                /**
                 * 网络请求成功之后执行
                 * @param data 网络请求的数据
                 */
                "requestSuccess": function(data) {
                    this.load(data);
                },

                /**
                 * 网络请求失败时调用
                 * @param message
                 */
                "requestFailed": function(message) {

                },

                /**
                 * 加载数据
                 * @param data
                 */
                "load": function(data) {

                },

                /**
                 * 为true时组件初始化完成自动调用url载入数据
                 */
                "autoLoad": true
            };
            instance.attrs(options);
            beforeInitialize(instance);
            instance.initialize();
            if (instance.autoLoad) {
                if (instance.data) {
                    instance.load(instance.data);
                }
                instance.request();
            }
            return instance;
        };
    };

    /**
     * 将插件注册到jQuery中
     * @param config 相关配置信息
     * @param builder 插件实例构造器
     * @private
     */
    var _$register = function(method, initialize) {
        var builder = _$createBuilder(initialize);
        $.fn[method] = _$createController(builder);
    };

    /**
     * 循环遍历数组中的元素
     * @type {jQuery.each|each}
     * @private
     */
    var _$each = jQuery.each;

    /**
     * 根据属性名获取model中相应属性的值
     * @param model 数据
     * @param field
     * @returns {null|*}
     * @private
     */
    var _$findAttribute = function(model, field) {
        if (!model || !field) {
            return null;
        }
        if (model[field]) {
            return model[field];
        }
        var fields = field.toString().split(".");
        var result = model;
        _$each(fields, function(i, e) {
            if (!(result = result[e])) {
                return false;
            }
        });
        return result;
    };

    // ---------------- 公共逻辑代码部分end ----------------

    // 表格组件
    _$register("myGrid", function() {
        var _this = this;

        /**
         * 创建一行单元格
         * @param data
         * @returns {*}
         */
        this.buildRow = function(cellInitialize) {
            var $row = $("<div></div>").addClass("grid-row");
            for (var i in this.columns) {
                var col = this.columns[i];
                var $cell = $("<div></div>").addClass("grid-cell");
                if (col.width != null) {
                    $cell.width(col.width);
                }
                cellInitialize($cell, col);
                $row.append($cell);
            }
            return $row;
        };

        /**
         * 初始化grid的结构
         */
        this.initGrid = function() {
            var $target = $(this.target).addClass("grid").empty();
            var $head = this.buildRow(function($cell, col) {
                $cell.text(col.title);
            });
            $head.addClass("head").appendTo($target);
            $head.children().each(function(i, e) {
                _this.columns[i].width = $(e).width();
            });
        };

        /**
         * 加载数据
         * @param data
         */
        this.load = function(data) {
            this.data = data || [];
            var $target = $(this.target);
            $target.children().remove(":not(:first)");
            for (var i in this.data) {
                var $row = this.buildRow(function($cell, col) {
                    $cell.click(function(e) {
                        $(e.target).parent().addClass("selected").siblings().removeClass("selected");
                    });
                    var value = data[i][col.field];
                    if (value == null) {
                        return;
                    }
                    if (col.formatter) {
                        value = col.formatter(value);
                    }
                    $cell.text(value);
                });
                $row.attr("row-index", i).appendTo($target);
            }
        };

        this.beforeRequest = function() {
            var $target = $(this.target);
            $target.children().remove(":not(:first)");
            var $message = $("<div></div>").addClass("grid-message")
                .text(this.loadingMessage).appendTo($target);
        };

        /**
         * 获取选中的行的相关数据
         */
        this.getSelected = function() {
            var $selected = $(this.target).find(".grid-row.selected");
            if ($selected.length == 0) {
                return null;
            }
            return this.data[$selected.attr("row-index")];
        };

        this.initGrid();
    });

    // 选项卡组件
    _$register("myTab", function() {
        var _this = this;

        /**
         * 加载数据
         * @param data
         */
        this.load = function(data) {
            this.tabBar.empty();
            this.data = data || this.data;
            if (!this.data) {
                return;
            }
            for (var i in data) {
                var menu = data[i];
                var $tab = $("<div></div>").addClass("tab")
                    .attr("tab-index", i)
                    .text(menu[this.titleField]);
                $tab.click(function(e) {
                    var $tab = $(e.target);
                    $tab.addClass("selected").siblings().removeClass("selected");
                    var menu = _this.data[$tab.attr("tab-index")];
                    if (_this.onTabClick(menu) === false) {
                        return;
                    }
                    if (_this.tabContent) {
                        _this.tabContent[0].src = menu.url || "";
                    }
                }).appendTo(this.tabBar);
            }
            if (this.tabIndex != -1) {
                this.tabBar.children().eq(this.tabIndex).click();
            }
        };

        // 初始化选项卡容器
        this.initTab = function() {
            var $target = $(this.target);
            var $tabs = $(this.tabBar);
            if (!$tabs.length) {
                $tabs = $("<div></div>").appendTo($target);
            }
            this.tabBar = $tabs.addClass("tabs " + this.tabLocation);
            if (this.tabContent) {
                var $content = $(this.tabContent);
                if (!$content.length) {
                    $content = $("<iframe></iframe>").appendTo($target);
                }
                this.tabContent = $content.addClass("tab-content");
            }
        };

        this.beforeRequest = function() {
            this.tabBar.empty().text(this.loadingMessage);
        };

        this.attrs({
            "tabBar": ".tabs",
            "tabContent": ".tab-content",
            "titleField": "title",
            "tabLocation": "left",
            "tabIndex": 0,
            "onTabClick": function() {

            }
        }, true);
        this.initTab();
    });

    // 表单组件
    _$register("myForm", function() {
        var _this = this;
        var $target = $(this.target);

        /**
         * 创建字段
         * @param field
         */
        this.buildField = function(field) {
            var $field = $("<div></div>").addClass("form-field");
            var $title = $("<label></label>").text(field.title).appendTo($field);
            var $content;
            if (field.type == "select") {
                $content = $("<select></select>");
                if (this.useDefaultPrompt && field.options && !field.options.prompt) {
                    field.options.prompt = "请选择" + field.title;
                }
                $content.mySelect(field.options);
            } else {
                var prompt = field.prompt;
                if (this.useDefaultPrompt) {
                    prompt = prompt || "请输入" + field.title;
                }
                $content = $("<input/>").attr("type", "text")
                    .attr("placeholder", prompt);
            }
            $content.attr("name", field.name).val(field.defaultValue).appendTo($field);
            return $field;
        };

        /**
         * 初始化表单组件
         */
        this.initForm = function() {
            $target.addClass("form").empty();
            _$each(this.fields, function(i, field) {
                _this.buildField(field).appendTo($target);
                var validate = field.validate;
                field.validate = function(value) {
                    if (field.notEmpty && !value) {
                        return false;
                    }
                    if (validate) {
                        return validate(value);
                    }
                    return true;
                }
            });
        };

        /**
         * 加载数据
         * @param data
         */
        this.load = function(data) {
            this.data = data || this.data;
            _$each(this.fields, function(i, e) {
                var $field = $target.find("[name=\""+e.name+"\"]");
                $field.val(_$findAttribute(_this.data, e.name));
            });
        };

        /**
         * 获取表单数据
         */
        this.getData = function() {
            var result = {};
            _$each(this.fields, function(i, e) {
                result[e.name] = $target.find("[name=\""+e.name+"\"]").val();
            });
            return result;
        };

        /**
         * 验证表单数据
         */
        this.validate = function() {
            var data = this.getData();
            var failed = false;
            _$each(this.fields, function(i, field) {
                if (!field.validate) {
                    return;
                }
                var value = _$findAttribute(data, field.name);
                if (field.validate(value) === false) {
                    failed = true;
                    if (_this.validateFailed) {
                        _this.validateFailed(field, value);
                    }
                    return false;
                }
            });
            return !failed;
        };

        this.attrs({
            "fields": [],
            "useDefaultPrompt": true,
            "validateFailed": function() {

            }
        }, true);
        this.initForm();
    });

    _$register("mySelect", function() {
        var _this = this;
        var $target = $(this.target);

        this.initSelect = function() {
            $target.addClass("select").empty();
            if (this.prompt) {
                $("<option></option>").attr("value", "").text(this.prompt).addClass("prompt").appendTo($target);
            }
        };

        this.load = function(data) {
            $target.children(":not(.prompt)").remove();
            this.data = data || this.data;
            _$each(this.data, function(i, e) {
                $("<option></option>").text(e[_this.textField])
                    .attr("value", e[_this.valueField])
                    .appendTo($target);
            });
        };

        this.attrs({
            "data": [],
            "valueField": "value",
            "textField": "text",
            "prompt": "请选择"
        }, true);
        this.initSelect();
    });
})(window, jQuery);