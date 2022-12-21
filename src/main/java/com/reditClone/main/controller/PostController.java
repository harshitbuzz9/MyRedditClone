package com.reditClone.main.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.reditClone.main.dto.PostRequest;
import com.reditClone.main.models.Post;
import com.reditClone.main.service.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	@Autowired
	private PostService postService;

	@PostMapping
	public ResponseEntity createPost(@RequestBody PostRequest postRequest) {
		this.postService.savePost(postRequest);
		return new ResponseEntity("Post Created",HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Post getPost(@PathVariable Long id) {
		return this.postService.getPostById(id);
		 
	}

	@GetMapping
	public List<Post> getAllPost() {
		return this.postService.getAllPost();
	}

	@GetMapping("by-subreddit/{id}")
	public List<Post> getPostsBySubreddit(Long id) {
		return this.postService.getPostsBySubreddit(id);
	}

	@GetMapping("by-user/{name}")
	public List<Post> getPostsByUsername(String username) {
		return this.postService.getPostsByUsername(username);
	}
}
