package com.reditClone.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import com.reditClone.main.models.Post;
import com.reditClone.main.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
   
    private Long postId;
    private Instant createdDate;
    private String text;
    private String userName;
}