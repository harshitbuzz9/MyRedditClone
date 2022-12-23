package com.reditClone.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reditClone.main.dto.VoteDto;
import com.reditClone.main.service.VoteService;

@RestController
@RequestMapping("/api/votes/")
public class VoteController {
	@Autowired
	VoteService voteService;
	
	@PostMapping
	public ResponseEntity<String> vote(@RequestBody VoteDto voteDto){
		this.voteService.vote(voteDto);
		return new ResponseEntity<>("You have : "+voteDto.getVoteType(),HttpStatus.OK);
	}
}
