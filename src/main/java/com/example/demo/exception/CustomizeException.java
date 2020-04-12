package com.example.demo.exception;

public class CustomizeException extends RuntimeException{//定义自己的错误，要runtime的，不会影响其他代码，在advice里面拦截就行了
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message=errorCode.getMessage();
    }

    public CustomizeException(String message){
        this.message=message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
