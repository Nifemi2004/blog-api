package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long Id;

    @NotEmpty
    @Size(min = 2, message = "Post title should not be less than 2 characters")
    private String Title;

    @NotEmpty
    @Size(min =10, message = "Post description should not be less than 10")
    private String Description;

    @NotEmpty
    private String Content;
    private Set<CommentDto> comments;
}
