package com.example.demo.exception;

/*方便不同的service的错误使用*/
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"你找的帖子不在了，逛逛别的帖子吧~"),
    TARGET_PARAM_NOT_FOUND(2002,"没找到想回复的帖子"),
    NO_LOGIN(2003,"尚未登录，请先登录后再试"),
    SYS_ERROR(2004,"服务器打瞌睡了，等一会再试试吧"),
    TYPE_PARAM_WRONG(2005,"评论的类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空")
    ;//枚举类型,来实现I~

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode(){
        return code;
    }

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
