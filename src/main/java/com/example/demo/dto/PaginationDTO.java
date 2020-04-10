package com.example.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {//此dto用来控制返回页面的帖子信息，在service中组合
    private List<QuestionDTO> questions;//页面信息数组
    private boolean showPrevious;//上页
    private boolean showFirstPage;//首页
    private boolean showNext;//下页
    private boolean showEndPage;//末页
    private Integer page;//当前页
    private List<Integer>pages=new ArrayList<>();//页码数组
    private Integer totalPage;


    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage=totalPage;
        this.page=page;

        this.page=page;
        pages.add(page);//加当前页
        for (int i = 1; i <=3; i++) {//加上旁边的页数
            if(page-i>0){
                pages.add(0,page-i);//每次加最前
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //是否展示首页
        if(page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }

        //是否展示末页
        if(page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }

        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }

        //是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }

    }
}
