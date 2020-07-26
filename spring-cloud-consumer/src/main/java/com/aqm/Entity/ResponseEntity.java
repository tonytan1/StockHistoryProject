package com.aqm.Entity;

public class ResponseEntity {
    public Integer code;
    public String message;
    public Object data;

    public ResponseEntity(){};
    
    public ResponseEntity(Integer code, String msg, Object data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\"code\":" + code + ", \"message\":" + message + ", \"data\":" + data + "}";
    }

}