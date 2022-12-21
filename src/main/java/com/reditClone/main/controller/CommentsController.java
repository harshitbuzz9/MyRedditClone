package com.reditClone.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reditClone.main.dto.CommentRequest;
import com.reditClone.main.models.Comment;
import com.reditClone.main.service.CommentService;

@RestController
@RequestMapping("/api/comments/")
public class CommentsController {
	@Autowired
	CommentService commentService;

	@PostMapping
	public ResponseEntity creatComment(CommentRequest commentRequest) {
		this.commentService.saveComment(commentRequest);
		return new ResponseEntity("Ok", HttpStatus.OK);
	}

	@GetMapping("/by-post/{postId}")
	public List<Comment> getAllCommentsForPost(@PathVariable Long postId) {
		return commentService.getAllCommentsForPost(postId);
	}

	@GetMapping("/by-user/{userName}")
	public List<Comment> getAllCommentsForUser(@PathVariable String userName) {
		return commentService.getAllCommentsForUser(userName);
	}

}
