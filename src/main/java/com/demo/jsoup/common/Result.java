package com.demo.jsoup.common;

/**
 * 返回结果
 *
 * @author: zyw9527
 * @version: v1.0  Created in 2019年03月15日  14:17 by zyw9527
 */

public class Result<T> {

    private String code;

    private String message;

    private T data;

    public Result( T data) {
        this.code = "200";
        this.message = "SUCCESS";
        this.data = data;
    }


    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;

    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;

    }

    public void setData(T data) {
        this.data = data;
    }
}

