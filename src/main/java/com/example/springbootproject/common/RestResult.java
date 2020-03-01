package com.example.springbootproject.common;

import com.alibaba.fastjson.JSON;

public class RestResult {
    private int code;
    private String message;
    private Object data;
    public RestResult setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public RestResult setMessage(String message) {
        this.message = message;
        return this;
    }
    public Object getData() {
        return data;
    }
    public RestResult setData(Object data) {
        this.data = data;
        return this;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
