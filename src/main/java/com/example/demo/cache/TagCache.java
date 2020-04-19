package com.example.demo.cache;

import com.example.demo.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("计算机");
        program.setTags(Arrays.asList("java","php","html","css","python","c","c++","javascript","mysql","h2","linux","Golang","易语言","其他计算机有关"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("游戏");
        framework.setTags(Arrays.asList("steam","epic","暴雪","P社","腾讯","其他游戏"));
        tagDTOS.add(framework);

        TagDTO resourse = new TagDTO();
        resourse.setCategoryName("资源");
        resourse.setTags(Arrays.asList("音乐资源","影视资源","游戏资源","软件资源","图片资源"));
        tagDTOS.add(resourse);

        TagDTO aa = new TagDTO();
        aa.setCategoryName("日常");
        aa.setTags(Arrays.asList("音乐分享","影视分享","避坑指南","聊天灌水"));
        tagDTOS.add(aa);

        return tagDTOS;
    }

    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());//把二层数组循环出来
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));//取出有错误的
        return invalid;
    }
}
