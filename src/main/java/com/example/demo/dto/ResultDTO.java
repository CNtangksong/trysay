package com.example.demo.dto;

import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer code;//来判断登陆情况
    private String message;
    private T data;//泛型，因为有可能不是list，可能是user之类的

    public static ResultDTO errorOf(Integer code,String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e){
        return errorOf(e.getCode(),e.getMessage());
    }

    public static <T> ResultDTO okOf(T t){//泛型
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
