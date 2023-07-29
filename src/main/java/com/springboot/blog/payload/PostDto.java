package com.springboot.blog.payload;

import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long Id;
    private String Title;
    private String Description;
    private String Content;
    private Set<CommentDto> comments;
}
