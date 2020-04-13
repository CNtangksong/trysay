package com.example.demo.exception;

public class CustomizeException extends RuntimeException{//定义自己的错误，要runtime的，不会影响其他代码，在advice里面拦截就行了
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.code=errorCode.getCode();
        this.message=errorCode.getMessage();
    }

    @Override
    public String getMessage(){
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
