package com.example.demo.enums;

public enum  CommentTypeEnum {
    QUESTION(1),//评论的对象可以是问题
    COMMENT(2);//也可以是评论
    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType()==type){//判断类型是否一致
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
