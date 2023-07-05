package com.springboot.blog.payload;

import lombok.Data;

@Data
public class PostDto {
    private Long Id;
    private String Title;
    private String Description;
    private String Content;
}
