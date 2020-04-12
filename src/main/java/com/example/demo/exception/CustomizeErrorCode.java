package com.example.demo.exception;

/*方便不同的service的错误使用*/
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND("你找的帖子不在了，逛逛别的帖子吧~");//枚举类型,来实现I~

    @Override
    public String getMessage() {
        return message;
    }

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
