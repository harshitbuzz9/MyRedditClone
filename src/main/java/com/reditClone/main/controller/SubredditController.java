package com.reditClone.main.controller;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.reditClone.main.dto.SubredditDto;
import com.reditClone.main.models.Subreddit;
import com.reditClone.main.service.SubredditService;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {

	@Autowired
	SubredditService subredditService;

	@PostMapping
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
	}

	@GetMapping
	public ResponseEntity<List<Subreddit>> getAllSubreddits() {
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
	}

	@GetMapping("/{id}")
	public Subreddit apiGetSubreddit(@PathVariable Long id) {
		return this.subredditService.getSubreddit(id);
	}
}
