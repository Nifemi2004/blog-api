package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty(message = "cannot be empty")
    @Size(min = 3)
    private String name;

    @NotEmpty(message = "cannot be empty")
    @Email
    private String email;

    @NotEmpty(message = "cannot be empty")
    @Size(min = 10)
    private String body;
}
