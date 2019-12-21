package com.scdq.manager.model;

/**
 * 操作的响应数据
 */
public class ResponseData<T> {
    /** 成功 */
    public static final int CODE_SUCCESS = 0;

    /** 失败 */
    public static final int CODE_FAILED = 1;

    public static final ResponseData SUCCESSFUL = new ResponseData();

    /** 状态代码 */
    private int code = CODE_SUCCESS;

    /** 错误消息 */
    private String message;

    private T data;

    public ResponseData() {

    }

    public ResponseData(T data) {
        this.data = data;
    }

    public ResponseData(String message) {
        code = CODE_FAILED;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
